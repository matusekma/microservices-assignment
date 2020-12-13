FROM python:3.8-slim
WORKDIR /app
COPY . /app
RUN pip install --trusted-host pypi.python.org -r requirements.txt
RUN python -m spacy download en
EXPOSE 80
CMD ["python", "app.py"]