# agile-grenoble-presentation-demo

### install docker
https://www.docker.com

### Modify your /etc/hosts file
vi /etc/hosts
127.0.0.1       kafka zookeeper cassandra mysql

### Get repo
git clone https://github.com/astrohome/agile-grenoble-presentation-demo.git

### Setup the project
cd ./web
npm install

cd docker
docker-compose build
docker-compose up

### Run the UI
cd web
npm start


### Compile and run test
mvn install

### Run container
mvn docker:start

### Stop container
mvn docker:stop


### Usefull links

microservice testing : https://martinfowler.com/articles/microservice-testing/

fabric8 : https://dmp.fabric8.io

docker : https://www.docker.com

cucumber : https://cucumber.io

maven : https://maven.apache.org

