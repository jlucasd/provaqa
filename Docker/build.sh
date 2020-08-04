#!/bin/bash

docker_dir=$PWD
project_dir=$(dirname "$docker_dir")
echo $docker_dir
echo "Iniciando o build --------------- "

echo "Efetuando o clean..."
mvn clean -f $project_dir/pom.xml

echo "Gerando o WAR..."
mvn package -DskipTests -f $project_dir/pom.xml
rc=$?
if [ $rc -ne 0 ] ; then
  echo Could not perform mvn package, exit code [$rc]; exit $rc
fi

if [ "$1" = "--test" ]; then
	echo "Executando os testes..."
    mvn test -f $project_dir/pom.xml

    rc=$?
    if [ $rc -ne 0 ] ; then
        echo Could not perform mvn test, exit code [$rc]; exit $rc
    fi
fi

echo "Removendo o artefado anterior...."
rm prova-java.war

echo "Adicionando o novo artefato...."
cp $project_dir/target/prova-java.war $docker_dir


docker build -t prova-java .
docker-compose up
echo "=========FIM============"
