
# 🛒 BasketService API

![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x.x-green?logo=spring)
![OpenFeign](https://img.shields.io/badge/OpenFeign-Declarative%20REST-orange)

Uma API RESTful desenvolvida em Java e Spring Boot para gerenciar um carrinho de compras.

Este projeto foi criado como um estudo prático de duas habilidades essenciais no desenvolvimento backend:
1.  **Criação de uma API** com lógica de negócio (gerenciamento de um carrinho).
2.  **Consumo de uma API externa** de forma limpa e desacoplada.

Para simular um ambiente real, os produtos adicionados ao carrinho são validados e buscados através do **OpenFeign**, que consome os dados da API pública [https://fakestoreapi.com/](https://fakestoreapi.com/).

---

## 🚀 Principais Funcionalidades

* **Criação de Carrinho:** Inicia um novo carrinho de compras.
* **Adicionar Itens:** Adiciona produtos a um carrinho existente, validando o `productId` contra a API externa.
* **Visualizar Carrinho:** Retorna o conteúdo completo de um carrinho, incluindo os detalhes dos produtos (buscados da API externa).
* **Remover Item:** Remove um item específico do carrinho.
* **Limpar Carrinho:** Remove todos os itens de um carrinho.
* **Integração com OpenFeign:** Abstrai a lógica de chamada HTTP à `fakestoreapi.com` em uma interface simples.

---

## 🛠️ Tecnologias Utilizadas

* **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)**
* **[Spring Boot 3](https://spring.io/projects/spring-boot)**: Framework principal para a criação da API REST.
* **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**: Para persistência de dados do carrinho.
* **[Banco de Dados H2](https://www.h2database.com/html/main.html)**: Banco de dados em memória para facilitar a execução e os testes do estudo.
* **[Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)**: Para o consumo declarativo da API de produtos.
* **[Maven](https://maven.apache.org/)**: Gerenciador de dependências e build.
* **[Lombok](https://projectlombok.org/)**: Para reduzir o boilerplate (construtores, getters, setters).

---

## 💡 O Destaque do Estudo: OpenFeign

O principal objetivo deste projeto foi aplicar o **OpenFeign** para o consumo de APIs. Em vez de usar `RestTemplate` ou `WebClient` manualmente, o OpenFeign permite criar um cliente REST de forma declarativa, apenas com uma interface.

Isso torna o `Service` muito mais limpo, delegando toda a complexidade da comunicação HTTP (construção de URL, serialização de JSON, tratamento de resposta) para o Feign.

**Exemplo do Cliente Feign (`ProductClient.java`):**
```java
@FeignClient(name = "fake-store-api", url = "[https://fakestoreapi.com](https://fakestoreapi.com)")
public interface ProductClient {

    /**
     * Busca um produto por ID na FakeStoreAPI.
     * O Feign cuida de toda a requisição GET e desserialização.
     */
    @GetMapping("/products/{id}")
    ProductDTO findProductById(@PathVariable("id") Long id);
}
````

Depois, no `BasketService`, basta injetar e usar:

```java
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    
    private final ProductClient productClient;
    
    public void addItem(Long basketId, ItemRequest itemRequest) {
        // 1. Valida se o produto existe usando o Feign
        ProductDTO product = productClient.findProductById(itemRequest.getProductId());
        
        // 2. Restante da lógica para adicionar ao carrinho...
    }
}
```

-----

## 🏁 Como Executar o Projeto

**Pré-requisitos:**

  * Java 17 (ou superior)
  * Maven

**Passos:**

1.  Clone este repositório:

    ```bash
    git clone [https://github.com/gabrielmoreiradevs/BasketService.git](https://github.com/gabrielmoreiradevs/BasketService.git)
    ```

2.  Navegue até o diretório do projeto:

    ```bash
    cd BasketService
    ```

3.  Execute a aplicação com o Maven:

    ```bash
    mvn spring-boot:run
    ```

4.  A API estará disponível em `http://localhost:8080`.

-----

## 📦 Endpoints da API

Abaixo estão os principais endpoints disponíveis para interagir com o `BasketService`.

### 1\. Criar um novo carrinho

Cria um carrinho vazio e retorna o `basketId`.

  * **Método:** `POST`
  * **URL:** `/baskets`
  * **Resposta (Exemplo):**
    ```json
    {
      "basketId": 1,
      "items": [],
      "totalValue": 0.0
    }
    ```

### 2\. Adicionar Item ao Carrinho

Adiciona um produto e uma quantidade ao carrinho. O `productId` é validado contra a API externa.

  * **Método:** `POST`
  * **URL:** `/baskets/{basketId}/items`
  * **Corpo da Requisição (Exemplo):**
    ```json
    {
      "productId": 1,
      "quantity": 2
    }
    ```

### 3\. Visualizar Carrinho

Retorna o conteúdo completo do carrinho, com os dados dos produtos buscados da `fakestoreapi.com`.

  * **Método:** `GET`
  * **URL:** `/baskets/{basketId}`
  * **Resposta (Exemplo):**
    ```json
    {
      "basketId": 1,
      "items": [
        {
          "productId": 1,
          "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
          "price": 109.95,
          "quantity": 2
        }
      ],
      "totalValue": 219.90
    }
    ```

### 4\. Remover Item do Carrinho

Remove um item específico (baseado no `productId`) do carrinho.

  * **Método:** `DELETE`
  * **URL:** `/baskets/{basketId}/items/{productId}`

### 5\. Limpar Carrinho

Remove todos os itens de um carrinho.

  * **Método:** `DELETE`
  * **URL:** `/baskets/{basketId}/clear`

-----

## 👨‍💻 Autor

  * **Gabriel Moreira**
  * [GitHub](https://www.google.com/search?q=https://github.com/gabrielmoreiradevs)
  * [LinkedIn](https://www.google.com/search?q=https://www.linkedin.com/in/gabriel-moreira-fermino-a9319a221/)


```
```
