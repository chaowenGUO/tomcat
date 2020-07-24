FROM python:latest
COPY . /
RUN ["ls", "-al"]
RUN ["pip", "install", "-r", "requirements.txt"]
ENTRYPOINT ["python", "server.py"]
