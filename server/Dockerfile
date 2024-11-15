FROM python:3.10

WORKDIR /app

COPY payment.py /app

RUN pip install Flask

EXPOSE 8080

CMD ["python", "payment.py"]