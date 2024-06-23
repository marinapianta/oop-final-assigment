# Programação Orientada a Objetos

# Trabalho Final: Locação de Robôs
### Nomes: Marina e Isadora

## 1. Enunciado Geral
A ACMERobots é uma empresa que fabrica e faz locações de robôs para clientes empresariais ou individuais. Os robôs podem ser para uso doméstico, industrial ou agrícola. Este trabalho envolve o desenvolvimento de um sistema para gerenciar robôs, clientes e locações.

### Classes Iniciais Definidas
- Robô
- Cliente
- Locação

### Características do Sistema
- O cálculo de uma locação depende do número de dias da locação, dos robôs locados e do cliente.
- Situações de uma locação: CADASTRADA, EXECUTANDO, FINALIZADA, CANCELADA.
- Valor final da locação = valor de locação de robôs - desconto do cliente.
- Valores de locação diária:
    - Doméstico nível 1: R$ 10,00
    - Doméstico nível 2: R$ 20,00
    - Doméstico nível 3: R$ 50,00
    - Industrial: R$ 90,00
    - Agrícola: R$ 10,00 por m² da área
- Descontos:
    - Empresarial: 3% (2-9 robôs), 7% (>10 robôs)
    - Individual: 5% (>1 robô)

### Funcionalidades do Sistema
- **Cadastrar novo robô:** Solicita e cadastra os dados de um robô. Se o id já existir, mostra mensagem de erro. Robôs mantidos em ordem crescente de id.
- **Cadastrar novo cliente:** Solicita e cadastra os dados de um cliente. Se o código já existir, mostra mensagem de erro. Clientes mantidos em ordem crescente de código.
- **Cadastrar nova locação:** Seleção de cliente e robôs para a locação. Se o número da locação já existir, mostra mensagem de erro. Nova locação é colocada em uma fila de locações pendentes.
- **Processar locações:** Processamento automático das locações pendentes. Se todos os robôs solicitados forem locados, a locação passa para EXECUTANDO. Se algum robô não estiver disponível, a locação retorna para a fila de locações pendentes.
- **Mostrar relatório geral:** Mostra todos os dados de robôs, clientes e locações cadastrados.
- **Consultar todas as locações:** Mostra todos os dados das locações, incluindo dados do cliente e valor final da locação.
- **Alterar situação de uma locação:** Solicita o número da locação e a nova situação.
- **Realizar carga de dados iniciais do sistema:** Carrega os dados dos arquivos para o sistema.
- **Salvar dados:** Salva todos os dados cadastrados no sistema em um ou mais arquivos.
- **Carregar dados:** Carrega todos os dados de um ou mais arquivos para o sistema.
- **Finalizar sistema:** Termina a execução do sistema.

## 2. Definição do Trabalho
O objetivo do trabalho é implementar um sistema de gerenciamento de robôs, clientes e locações, atendendo às seguintes restrições:
- Criação de novas classes, métodos, atributos e relacionamentos permitida.
- Tratamento de exceções para evitar interrupções na execução.
- Uso de coleções da API Java (exceto array []) para armazenamento de objetos.
- Atributos privados (private).
- Nenhum atributo ou método de classe (static).
- Interface gráfica para interação com o usuário.

## 3. Critérios de Avaliação
- Nota do Trabalho = 10% apresentação + 90% implementação.
- Implementação envolve:
    - Relatório do projeto: 1 ponto.
    - Persistência de dados em arquivos: 1 ponto.
    - Tratamento de exceções: 1 ponto.
    - Interface gráfica com o usuário: 1 ponto.
    - Implementação e uso de herança, polimorfismo e coleções: 1 ponto.
    - Implementação e execução do sistema conforme a descrição: 5 pontos.
- Ponto extra: 1 ponto se o sistema ler e gravar dados em JSON ou XML.

## 4. Entrega
- Entregar o código-fonte do sistema e o relatório do projeto.
- Arquivo compactado (.zip ou .rar) deve ser entregue pelo Moodle da disciplina.
- Entrega individual mesmo que desenvolvido em grupo.
- Data da entrega: 3/7/2024.

## 5. Apresentação
- Datas de apresentação: 3/7/2024 e 8/7/2024.

## 6. Considerações Finais
- Trabalho pode ser desenvolvido individualmente, em dupla ou trio.
- Seguir Java Code Conventions.
- Trabalhos com erros de compilação terão nota zerada.
- Cópia parcial ou completa resultará em nota zero.
- MOSS será utilizado para análise de similaridade.

## Apêndice: Exemplos de Arquivos de Carga de Dados Iniciais

### Arquivo de Robôs (EXEMPLO-ROBOS.CSV)
```csv
id;modelo;tipo;nivel_setor_area;uso
22;T-800;2;mecanico
33;T-1000;3;33.33;semeador
11;T-X;1;1
```
### Arquivo de Clientes (EXEMPLO-CLIENTES.CSV)
```csv
codigo;nome;tipo;cpf_ano
222;ACME Contractor;2;2020
111;Maria da Silva;1;479.328.350-03
```

### Arquivo de Locações (EXEMPLO-LOCACOES.CSV)
```csv
numero;situacao;datainicio;datafim;codigo;idsrobos
1111;CADASTRADA;10/10/2024;12/12/2024;111;22;33
2222;CADASTRADA;11/11/2024;12/12/2024;222;11;22;33
```
