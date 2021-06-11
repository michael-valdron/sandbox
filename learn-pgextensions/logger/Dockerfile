FROM postgres:12

RUN apt-get update && apt-get install build-essential libpq-dev postgresql-server-dev-12 -y

COPY ./logger /logger

WORKDIR /logger

RUN PATH=/usr/local/pgsql/bin:$PATH make USE_PGXS=1

RUN PATH=/usr/local/pgsql/bin:$PATH make USE_PGXS=1 install
