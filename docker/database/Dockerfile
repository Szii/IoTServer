FROM mysql:8

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=diplomova_prace_db
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=root

COPY dp.sql /docker-entrypoint-initdb.d/

EXPOSE 3306