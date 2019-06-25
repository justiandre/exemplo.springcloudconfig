# exemplo.springcloudconfig

Aplicação de exemplo das tecnologia Spring Cloud Config Server e Client, esse exemplo foi usado na apresentação: [MicroServices: Configurações](ADICIONAR) 

## Links
 
- [Apresentação - MicroServices: Configurações](ADICIONAR) 
- [Repositório com as configurações do exemplo](https://github.com/justiandre/exemplo.springcloudconfig.config.repo)

## Artefatos
- `docker-compose.yml`: Arquivo responsável por orquestrar o deploy e execução das aplicações 
- `exemplo.springcloudconfig.server` (diretório): Projeto Java que utiliza Spring Boot e Spring Cloud Config Server para prover um gerenciador de configurações para outras aplicações, chamado de Config Server
- `exemplo.springcloudconfig.service01` (diretório): Projeto Java que Spring Boot e Spring Cloud Config Client que consome dados da aplicação Config Server para se configurar
- `exemplo.springcloudconfig.service02` (diretório): Projeto Java que Spring Boot e Spring Cloud Config Client que consome dados da aplicação Config Server para se configurar
- `exemplo.springcloudconfig.config.repo` ([repositório](https://github.com/justiandre/exemplo.springcloudconfig.config.repo)): Repositório com os arquivos de configuração das aplicações, chamado de Config Repository

## Tecnologias utilizadas no exemplo (não são todas necessárias para execução do exemplo)

- [Java](https://www.java.com/pt_BR/download/faq/java8.xml)
- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com)
- [Docker Compose](https://docs.docker.com/compose/)
- [Spring](https://spring.io)
- [Spring Boot](https://projects.spring.io/spring-boot/)
- [Spring Cloud Config Server/Client](https://cloud.spring.io/spring-cloud-config/)

## Como executar

**Obs:** Necessário ter apenas **Docker** e **Docker Compose** instalados para execução.

### Clonar o projeto

```shell
git clone https://github.com/justiandre/exemplo.springcloudconfig.git
```

### Executar os serviços

```shell
docker-compose up -d --build
```
### Acesso

Para visualizar a aplicação funcionando existem alguns endpoints:

- **Config Server**
    - [http://localhost:9999/service01-default.yml](http://localhost:9999/service01-default.yml): Lista as configurações `default` da aplicação `service01`
    - [http://localhost:9999/service01-prod.yml](http://localhost:9999/service01-prod.yml): Lista as configurações do perfil `prod` da aplicação `service01`
    - [http://localhost:9999/service02-default.yml](http://localhost:9999/service02-default.yml): Lista as configurações `default` da aplicação `service02`
    - [http://localhost:9999/service02-prod.yml](http://localhost:9999/service02-prod.yml): Lista as configurações do perfil `prod` da aplicação `service02`
- **Service01**
    - [http://localhost:8080/teste](http://localhost:8080/teste): Mostra o conteúdo da configuração `config.valor-test` na aplicação `service01` que foi carregado do `ConfigServer`
- **Service02**
    - [http://localhost:8081/teste](http://localhost:8081/teste): Mostra o conteúdo da configuração `config.valor-test` na aplicação `service02` que foi carregado do `ConfigServer`

### Recarregamento das configurações em tempo real

**Obs:** Para executar esse exemplo é necessário fazer um fork do [repositório de configurações](https://github.com/justiandre/exemplo.springcloudconfig.config.repo) e para iniciar o teste é necessário a url deste repositório.

1. Crie um arquivo [env](https://docs.docker.com/compose/env-file/) na raiz do projeto (`exemplo.springcloudconfig`) com o seguinte conteúdo:

```
SPRING_CLOUD_CONFIG_SERVER_GIT_URI=URL_DO_REPOSITORIO_CRIADO
```
2. Execute o projeto 

```shell
docker-compose up -d --build
```

3. Acesse a url do sistema `Service01`: [http://localhost:8080/teste](http://localhost:8080/teste)

4. Altere o valor da propriedade `config.valor-test` do projeto `Service01` no repositório criado, arquivo: `$REPO/service01/service01.yml`

5. Notifique o `ConfigServer` para que ele informe as aplicações clientes que as configurações mudaram

```shell
curl -X POST \
  http://admin:admin@localhost:9999/bus/refresh \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{}'
```

6. Acesse novamente o sistema `Service01`: [http://localhost:8080/teste](http://localhost:8080/teste) para ver a configuração atualizada

