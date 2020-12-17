 For more info : https://www.youtube.com/watch?v=FA3VdlBo0nA

Docker bilds

docker build -f Dockerfile -t gateway-service .
docker build -f Dockerfile -t auth-service .
docker build -f Dockerfile -t databaseservice .
docker build -f Dockerfile -t registryservice .


to run composite files:

docker-compose up -d
