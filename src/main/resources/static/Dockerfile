FROM python:latest
COPY *.html *.js *.sql server.py requirements.txt /usr/local/src/
RUN ["pip", "install", "-r", "/usr/local/src/requirements.txt"]
ENTRYPOINT ["python", "/usr/local/src/server.py"]
