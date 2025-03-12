from fastapi import FastAPI
from fastapi.responses import JSONResponse
from service.StockPricesService import StockPricesService

app = FastAPI()

@app.get("/")
def root():
    return {"Hello":"World"}

@app.get("/api/ma")
def getMAReturns(symbol:str):
    stockService = StockPricesService()
    try :
        response = stockService.get_MA_Scores(symbol)
        return JSONResponse(status_code=200, content=response)
    except ValueError as e:
        return JSONResponse(status_code=404, content={"Error":f"Symbol {symbol} not found, please try again"})
    

@app.get("/api/rsi")
def getRSIReturns(symbol:str):
    stockService = StockPricesService()
    try :
        response = stockService.get_RSI_scores(symbol)
        return JSONResponse(status_code=200, content=response)
    except ValueError as e:
        return JSONResponse(status_code=404, content={"Error":f"Symbol {symbol} not found, please try again"})
    

