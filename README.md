Pontifícia Universidade Católica de Minas Gerais
Bacharelado em Engenharia de Software
Programação Modular
Atividade Avaliativa Prática 1
Analise o cenário abaixo e desenvolva um sistema para atender à demanda solicitada.
Seu programa deve seguir todos os conceitos da Programação Orientada a Objetos e
implementá-los de forma correta. Leia com atenção todo o enunciado antes de iniciar.
Valor: 3 pontos
Critérios de Correção:
• Aplicação correta dos conceitos de modularização e POO: 60%
• Implementação correta das funcionalidades solicitadas: 30%
• Interação com o usuário e boas práticas de programação: 10%
Motivos que podem zerar sua prova:
• O código não está em Java
• O código não está orientado a objetos
• O código é cópia de terceiros
• Você acessou a internet durante a prova
Cenário
Uma oficina mecânica deseja informatizar o sistema de gerenciamento de boxes e ordens
de serviço.
Cada mecânico possui as seguintes informações: nome, CPF, especialidade e telefone.
Cada box possui as seguintes informações: número, tipo de serviço permitido, capacidade
máxima de veículos e localização.
Cada box está vinculado a um mecânico responsável.
Para cada ordem de serviço, devem ser armazenadas informações como: código, nome do
cliente, modelo do veículo, placa do veículo, data, status da ordem (aberta, em execução,
finalizada), valor estimado.
O sistema também deve permitir armazenar informações do serviço associado a cada ordem.
O serviço possui as seguintes informações: nome, tempo estimado, valor e categoria.
Cada box pode receber várias ordens de serviço, contanto que estas ordens estejam associ-
adas ao mesmo tipo de serviço.
1
Funcionalidades do Sistema
1. Cadastrar ordem de serviço
2. Associar um mecânico a um box
3. Atribuir ordem de serviço a um box
4. Exibir todas as ordens atribuídas a um box específico. Informe também o total de
ordens ao final.
5. Informar a quantidade total de ordens finalizadas por cada box
6. Buscar ordens por status. É necessário exibir os detalhes da ordem, incluindo box e
mecânico.
7. Exibir os detalhes completos de uma ordem específica
O usuário deve acessar essas funcionalidades através de um menu.
Faça um método na classe main para criar 3 mecânicos e 3 boxes assim que o programa
for executado.
Regras de negócio
1. Um mecânico pode ser responsável por apenas um box.
2. Um box pode possuir várias ordens de serviço, mas a ordem não depende do box.
3. Ordens com status "aberta"não possuem box atribuído.
4. Ordens com status "finalizada"precisam manter a informação do box utilizado. Po-
rém, o box não precisa manter a informação desta ordem.# Atividade-Avaliativa-Pr-tica-1
