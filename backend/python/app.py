from fastapi import FastAPI
from fastapi.responses import JSONResponse
from service.StockPricesService import StockPricesService
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

allowed_origins = [
    "http://localhost:8080" # localhost URL
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=allowed_origins,  # Only these origins are allowed
    allow_credentials=True,
    allow_methods=["*"],  # Allow all methods (GET, POST, etc.)
    allow_headers=["*"],  # Allow all headers
)

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
    

