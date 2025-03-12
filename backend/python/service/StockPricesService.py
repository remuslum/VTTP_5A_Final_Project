from repository.MAStrategy import MAStrategy
from repository.RSIStrategy import RSIStrategy

class StockPricesService :

    # empty constructor
    def __init__(self):
        pass

    def get_MA_Scores(self, symbol:str):
        stockPricesRepo = MAStrategy(symbol)
        stock_returns, strategy_returns, benchmark_returns = stockPricesRepo.getReturns()
        stock_std, strategy_std, benchmark_std = stockPricesRepo.getStd()
        return {"stock" : {
            "returns" : stock_returns,
            "std" : stock_std
        }, "strategy" : {
            "returns" : strategy_returns,
            "std" : strategy_std
        }, "benchmark" : {
            "returns" : benchmark_returns,
            "std" : benchmark_std
        }}
    
    def get_RSI_scores(self, symbol:str):
        rsi_repo = RSIStrategy(symbol)
        stock_returns, strategy_returns, benchmark_returns = rsi_repo.getReturns()
        stock_std, strategy_std, benchmark_std = rsi_repo.getStd()
        return {"stock" : {
            "returns" : stock_returns,
            "std" : stock_std
        }, "strategy" : {
            "returns" : strategy_returns,
            "std" : strategy_std
        }, "benchmark" : {
            "returns" : benchmark_returns,
            "std" : benchmark_std
        }} 