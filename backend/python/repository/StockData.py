import yfinance as yf

class StockData:
    def __init__(self):
        self.start_period = "2024-01-01"
        self.end_period = "2024-12-31"
        self.interval = "1d"

        '''
        For this application, we compare our strategy against the S&P 500 index
        '''
        self.benchmark = "^GSPC"

    def getStockPrices(self,symbol:str):
        stock_data = yf.Ticker(symbol).history(start = self.start_period, end = self.end_period, interval = self.interval)
        if stock_data.empty :
            raise ValueError(f"No data found for the symbol: {symbol}")
        return stock_data
        