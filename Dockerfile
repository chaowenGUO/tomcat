FROM python:latest
COPY . /
RUN python -c "import pathlib; print(*pathlib.Path('/').iterdir())"
RUN ["pip", "install", "-r", "requirements.txt"]
ENTRYPOINT ["python", "server.py"]
