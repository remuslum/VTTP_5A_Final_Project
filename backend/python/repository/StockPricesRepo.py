import yfinance as yf
# import all libraries
from IPython.display import display
from ta.utils import dropna
# from ta.volatility import BollingerBands
# from ta.trend import ADXIndicator
# from ta.volatility import AverageTrueRange
from ta.trend import SMAIndicator
# from ta.momentum import RSIIndicator
# from ta.volume import VolumeWeightedAveragePrice
# from ta.trend import MACD

import warnings
warnings.filterwarnings('ignore', category=FutureWarning, module='pandas')

import math
import pandas as pd
import numpy as np
import yfinance as yf

class StockPricesRepo:

    # Constructor
    def __init__(self, symbol:str):
        '''
        Privates attributes
        '''
        self.__start_period = "2024-01-01"
        self.__end_period = "2024-12-31"
        self.__interval = "1d"

        '''
        For this application, we compare our strategy against the S&P 500 index
        '''
        self.__benchmark = "^GSPC"

        '''
        Public attributes
        '''
        self.data = None

        '''
        Invoke strategy once object is created
        '''
        self.__strategy()

    # Make start and end period read only
    @property
    def start_period(self):
        return self.__start_period

    @property
    def end_period(self):
        return self.__end_period
    
    @property
    def interval(self):
        return self.__interval
    
    @property
    def benchmark(self):
        return self.__benchmark
    
    def __get_stock_data(self, symbol:str):
        return yf.Ticker(symbol).history(start = self.__start_period, end = self.__end_period, interval = self.__interval)

    def __get_MA(data, ST_window, LT_window):
      '''
      This function calculates the MA for the given window
      '''
      indicator_1 = SMAIndicator(close = data["Close_Price"], window= ST_window, fillna= False)
      STMA= indicator_1.sma_indicator()
      indicator_2 = SMAIndicator(close = data["Close_Price"], window= LT_window, fillna= False)
      LTMA = indicator_2.sma_indicator()
      return STMA, LTMA
    
    def __prepare_data(self, symbol:str):
        '''
        This function populates the table with the strategy returns
        '''
        stock_prices = self.__get_stock_data(symbol)
        benchmark_prices = self.__get_stock_data(self.__benchmark)

        bt_data = pd.DataFrame()
        '''
        Calculate the indicator values, signals and positions
        '''
        bt_data["Close_Price"] = stock_prices["Close"]
        bt_data["Benchmark_Price"] = benchmark_prices["Close"]
        bt_data['STMA'], bt_data['LTMA'] = self.__get_MA(bt_data, 5, 30)
        bt_data["Position"] = np.where(bt_data['STMA']>bt_data['LTMA'], 1.0, -1.0)
        bt_data["Signal"] = bt_data['Position'].diff()

        '''
        Calculate the returns
        '''
        bt_data['Stock_Returns'] = np.log(bt_data["Close_Price"] / bt_data["Close_Price"].shift(1))
        bt_data["Strategy_Returns"] = bt_data["Stock_Returns"] * bt_data["Position"].shift(1)
        bt_data['Benchmark_Returns'] = np.log(bt_data["Benchmark_Price"] / bt_data["Benchmark_Price"].shift(1))
        bt_data["Gross_Cum_Returns"] = bt_data["Strategy_Returns"].cumsum().apply(np.exp)
        bt_data["Cum_Max"] = bt_data["Gross_Cum_Returns"].cummax()
        bt_data = bt_data.dropna()

        return bt_data

    def __strategy(self):
        bt_data = self.__prepare_data()
        bt_data["Position"] = np.where(bt_data['STMA']>bt_data['LTMA'], 1.0, -1.0)
        bt_data["Position"] = np.where(bt_data['RSI'] < 30, 1.0, bt_data["Position"])
        bt_data["Position"] = np.where(bt_data['RSI'] > 70, -1.0, bt_data["Position"])
        bt_data["Signal"] = bt_data['Position'].diff()
        bt_data = bt_data.dropna()

        bt_data['Stock_Returns'] = np.log(bt_data["Close_Price"] / bt_data["Close_Price"].shift(1))
        bt_data["Strategy_Returns"] = bt_data["Stock_Returns"] * bt_data["Position"].shift(1)
        bt_data['Benchmark_Returns'] = np.log(bt_data["Benchmark_Price"] / bt_data["Benchmark_Price"].shift(1))
        
        # Populate the data with the necessary columns
        self.data = bt_data

    





        
        