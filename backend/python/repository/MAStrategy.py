from repository.StockData import StockData
from ta.trend import SMAIndicator

import warnings
warnings.filterwarnings('ignore', category=FutureWarning, module='pandas')
import pandas as pd
import numpy as np

class MAStrategy:

    # Constructor
    def __init__(self, symbol:str):
        '''
        Public attributes
        '''
        self.data = None
        self.symbol = symbol
        self.stock_data = StockData()

        '''
        Invoke strategy once object is created
        '''
        self.__strategy()
    
    def __get_MA(self,data, ST_window, LT_window):
      '''
      This function calculates the MA for the given window
      '''
      indicator_1 = SMAIndicator(close = data["Close_Price"], window= ST_window, fillna= False)
      STMA= indicator_1.sma_indicator()
      indicator_2 = SMAIndicator(close = data["Close_Price"], window= LT_window, fillna= False)
      LTMA = indicator_2.sma_indicator()
      return STMA, LTMA
    
    def __prepare_data(self):
        '''
        This function populates the table with the strategy returns
        '''
        stock_prices = self.stock_data.getStockPrices(self.symbol)
        benchmark_prices = self.stock_data.getStockPrices(self.stock_data.benchmark)

        bt_data = pd.DataFrame()
        '''
        Calculate the indicator values, signals and positions
        '''
        bt_data["Close_Price"] = stock_prices["Close"]
        bt_data["Benchmark_Price"] = benchmark_prices["Close"]
        bt_data['STMA'], bt_data['LTMA'] = self.__get_MA(bt_data, 10, 50)
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
        bt_data["Signal"] = bt_data['Position'].diff()
        bt_data = bt_data.dropna()

        bt_data['Stock_Returns'] = np.log(bt_data["Close_Price"] / bt_data["Close_Price"].shift(1))
        bt_data["Strategy_Returns"] = bt_data["Stock_Returns"] * bt_data["Position"].shift(1)
        bt_data['Benchmark_Returns'] = np.log(bt_data["Benchmark_Price"] / bt_data["Benchmark_Price"].shift(1))
        
        # Populate the data with the necessary columns
        self.data = bt_data

    def getReturns(self):
        daily_ret = self.data[["Stock_Returns", "Strategy_Returns", "Benchmark_Returns"]].mean()
        annual_ret = daily_ret * 252

        # Convert the daily returns to regular returns
        annual_regular_ret = np.exp(annual_ret) - 1 
        return annual_regular_ret["Stock_Returns"], annual_regular_ret["Strategy_Returns"],annual_regular_ret["Benchmark_Returns"]
    
    def getStd(self):
        # Calculate the regular standard deviation
        daily_regular_std = (np.exp(self.data[["Stock_Returns", "Strategy_Returns", "Benchmark_Returns"]])-1).std()
        annual_regular_std = daily_regular_std * np.sqrt(252)
        return annual_regular_std["Stock_Returns"],annual_regular_std["Strategy_Returns"],annual_regular_std["Benchmark_Returns"]
