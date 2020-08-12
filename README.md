# Iniciar aplicação

## Preparando a base de dados

- Primeiramente é preciso criar o container que ficará a aplicação da base de dados.
para isso execute os seguintes comandos:

`docker volume create pg_data`

`docker network create pg_data`

`docker run --name pg_data --network pg_data --volume pg_data -p 5432 -e POSTGRES_HOST_AUTH_METHOD=trust -d postgres:9.6`

- Após executado os comandos acima é necessário criar a base de dados executando o comando abaixo:

`docker exec -i pg_data psql -U postgres -c "CREATE DATABASE provaqa"`
- Logo depois exportar os dados para a base criada.

Linux: `cat script.sql | docker exec -i pg_data psql -U postgres -d provaqa`
Windows: `type script.sql | docker exec -i pg_data psql -U postgres -d provaqa`

## Subindo a aplicação

- Dentro da pasta Docker execute o seguinte comando.

Caso Linux e MAC:
`./build.sh`

Caso Windows:
`build.bat`

- Feito isso a aplicação pode ser acessada no endereço `http://localhost:8080/prova-java`

```
Obs.: Caso o arquivo build.sh não possua permissão de execução você poderá dar essa permissão através do comando chmod
```

## Startar a aplicação após os passos anteriores

- Para startar a aplicação numa segunda vez por exemplo, você executar:

1)  Rodar o comando `docker start pg_data` para subir o container do banco de dados; 
2)  Na sequência, dentro da pasta Docker execute o seguinte comando.

Isto irá executar todos os demais passos necessários
