# API BEACH-PRODUCT-RENTAL

## Requisitos

Para que seja possível rodar essa aplicação, é necessário atender a alguns requisitos básicos:

- Java 11
- Maven 3.8+
- Mysql 8.0
- Docker (Opcional, apenas para execução via Docker)

## Instruções de Uso

Você pode rodar a aplicação de duas maneiras: **via Docker** ou **via Maven**.

### Rodar via Docker

Se você preferir usar o Docker, siga estes passos:

1. **Certifique-se de que o Docker está instalado e em execução.**
2. **Configure o banco de dados Mysql** (verifique o arquivo `docker-compose.yml` para detalhes de configuração).
3. **Execute o comando abaixo para construir e iniciar a aplicação:**

    ```bash
    $ docker-compose up --build
    ```

   Isso irá construir a imagem Docker e iniciar os containers definidos no arquivo `docker-compose.yml`. A aplicação estará disponível em `http://localhost:8080`.

### Rodar via Maven

Se preferir rodar a aplicação diretamente via Maven, siga estes passos:

1. **Certifique-se de que o Mysql está instalado e em execução localmente.**
2. **Configure o banco de dados Mysql**:
    - **Verifique o arquivo `application.properties`** da aplicação para garantir que as credenciais e a URL do banco de dados estão corretas. As configurações que você deve usar são:

      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/beach_product
      spring.datasource.username=root
      spring.datasource.password=root
      ```

    - **Assegure-se de que o banco de dados `mysql` está criado** e que as credenciais fornecidas têm acesso a ele.
3. **Clone o repositório da aplicação.**
4. **Abra o projeto na sua IDE.**
5. **Compile e execute o projeto utilizando Maven:**

    ```bash
    $ mvn spring-boot:run
    ```

   A aplicação será iniciada e estará disponível em `http://localhost:8080`.

## Endpoints

### Orders

- **Buscar por nome funcionário**

    - **URL:** `http://localhost:8080/orders?userName=Pedro`
    - **Método HTTP:** GET

- **Criar um novo produto**

    - **URL:** `http://localhost:8080/orders/create`
    - **Método HTTP:** POST
    - **Exemplo de JSON:**
      ```json
      {
            "userName": "Teste",
            "productType": "SURFBOARD",
            "timeHour": 4
      }
      ```
- **Buscar por filtro**

    - **URL:** `http://localhost:8080/orders/filter?filterField=userName&filterValue=Pedro`
    - **Método HTTP:** GET
