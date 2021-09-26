FROM mariadb
ENV MYSQL_ROOT_PASSWORD 12345
ENV MYSQL_DATABASE mydb
COPY ./mysql_data /var/lib/mysql
EXPOSE 3306
ENTRYPOINT ["mysqld", "--user=root"]