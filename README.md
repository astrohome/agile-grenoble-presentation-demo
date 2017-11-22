# agile-grenoble-presentation-demo

### install docker
https://docs.docker.com/docker-for-mac/install/#download-docker-for-mac

### Modify your /etc/hosts file
vi /etc/hosts
127.0.0.1       kafka zookeeper cassandra mysql

### Get repo
git clone https://github.com/astrohome/agile-grenoble-presentation-demo.git

### Setup the project
cd ./web
npm ./install

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
