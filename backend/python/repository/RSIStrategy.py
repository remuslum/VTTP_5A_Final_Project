from repository.StockData import StockData
from ta.momentum import RSIIndicator
import yfinance as yf
import pandas as pd
import numpy as np


class RSIStrategy:

    # Constructor
    def __init__(self, symbol:str):
        '''
        Public attributes
        '''
        self.data = None
        self.symbol = symbol
        self.stock_data = StockData()

        self.__strategy()

    def __get_RSI(self, data, window):
        C = RSIIndicator(close = data['Close_Price'], window= window, fillna = False)
        return round(C.rsi(),2)
    
    def __prepare_data(self):
        stock_prices = self.stock_data.getStockPrices(self.symbol)
        benchmark_prices = self.stock_data.getStockPrices(self.stock_data.benchmark)

        bt_data = pd.DataFrame()
        '''
        Calculate the indicator values, signals and positions
        '''
        bt_data["Close_Price"] = stock_prices["Close"]
        bt_data["Benchmark_Price"] = benchmark_prices["Close"]
        bt_data['RSI'] = self.__get_RSI(bt_data, 10)
        bt_data["Position"] = np.where(bt_data['RSI'] < 30, 1.0, 0)
        bt_data["Position"] = np.where(bt_data['RSI'] > 70, -1.0, 0)
        bt_data["Signal"] = bt_data['Position'].diff()

        bt_data['Stock_Returns'] = np.log(bt_data["Close_Price"] / bt_data["Close_Price"].shift(1))
        bt_data["Strategy_Returns"] = bt_data["Stock_Returns"] * bt_data["Position"].shift(1)
        bt_data['Benchmark_Returns'] = np.log(bt_data["Benchmark_Price"] / bt_data["Benchmark_Price"].shift(1))
        bt_data["Gross_Cum_Returns"] = bt_data["Strategy_Returns"].cumsum().apply(np.exp)
        bt_data["Cum_Max"] = bt_data["Gross_Cum_Returns"].cummax()
        bt_data = bt_data.dropna()

        return bt_data
    
    def __strategy(self):
        bt_data = self.__prepare_data()
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




    