CREATE KEYSPACE stats WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
CREATE TABLE IF NOT EXISTS stats.product_view (
  user_id int,
  product_id int,
  time timestamp,
  PRIMARY KEY (user_id, product_id, time)
);