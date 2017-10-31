FROM spotify/cassandra

RUN mkdir -p /app/dump/
COPY dump/cassandra_schema.cql /app/dump/

WORKDIR /app/dump

RUN cassandra & \
    sleep 15 && \
    cqlsh -f cassandra_schema.cql && \
    ps -wef | grep cassandra | grep -v grep | awk '{print $2}' | xargs kill -9