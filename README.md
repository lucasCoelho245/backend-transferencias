# Transferências Bancárias API

API para o funcionamento do front-end de agendamento de transferências bancárias.

## Requisitos do Sistema

- Java 11
- Spring Boot 2.7.14
- Maven 3.9.5

## Instalação

1. Clone o repositório para sua máquina local.
    ```bash
    git clone https://github.com/lucasCoelho245/backend-transferencias.git
    ```

2. Navegue até o diretório do projeto.
    ```bash
    cd backend-transferencias
    ```

3. Compile o projeto e baixe as dependências.
    ```bash
    mvn clean install
    ```

4. Inicie a aplicação.
    ```bash
    mvn spring-boot:run
    ```

## Uso

A API estará disponível em `http://localhost:8081`. Você pode utilizar ferramentas como Postman ou cURL para interagir com a API.

### Endpoints Principais

#### Transferências
- `POST /transferencias` - Agenda uma nova transferência.
- `GET /transferencias` - Retorna todas as transferências agendadas.

### Regras de Negócio

- O cálculo da taxa segue a seguinte lógica:
   - **Mesma data (D+0)**: 2,5% do valor + taxa fixa de R$ 3,00.
   - **Entre 1 e 10 dias (D+1 a D+10)**: Taxa fixa de R$ 12,00.
   - **Entre 11 e 20 dias (D+11 a D+20)**: 8,2% do valor.
   - **Entre 21 e 30 dias (D+21 a D+30)**: 6,9% do valor.
   - **Entre 31 e 40 dias (D+31 a D+40)**: 4,7% do valor.
   - **Entre 41 e 50 dias (D+41 a D+50)**: 1,7% do valor.
   - **Transferências acima de 50 dias não são permitidas.**

### Exemplo de Requisição

#### Criar uma Transferência
```json
{
  "id": 1,
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.00,
  "taxa": 12.00,
  "dataTransferencia": "2025-03-10",
  "dataAgendamento": "2025-02-04"
}
