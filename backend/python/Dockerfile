# Step 1: Use the official Python image from Docker Hub
FROM python:3.9-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the requirements.txt file into the container
COPY requirements.txt .

# Step 4: Upgrade pip
RUN pip install --upgrade pip

# Step 5: Install dependencies listed in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Step 6: Copy the rest of your application code into the container
COPY . /app

# Step 7: Expose the port that FastAPI will run on (default is 8000)
EXPOSE 8000

# Step 8: Start the FastAPI application using Uvicorn as the ASGI server
CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "8000"]