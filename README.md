# Imobiliaria Fuzzy
[![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid)

### Sobre
Imobiliaria Fuzzy é um aplicativo capaz de realizar uma operação que calcula a porcentagem de qualidade de uma casa. 

###Introdução
Este documento consiste na apresentação do trabalho prático feito para a disciplina Inteligência
Artigicial, com o tema de qualificação de imóveis para imobiliárias. O programa foi feito
especificamente para imóveis residenciais (casas) e o terreno também é tratato. Consiste em coletar
os atributos do imóvel – sendo as variáveis: Valor, número de vagas na garagem, área construída,
área extra (o que restou do terreno) e localidade.

###Entrevista
Foram feitas várias entrevistas com um profissional da área, um corretor de imóveis, para o auxílio
na construção do programa, a fim de oferecer ajuda com as variáveis, com as regras – para que estas
sejam incorporadas à lógica fuzzy, no qual foi o método utilizado para efetuar a medição da
qualidade da casa – e, também, para uma explicação generalizada de como é este processo dentro de
uma imobiliária, para ter-se esse conhecimento e aplicar ao programa.

###Implementação
A implementação foi jeita na linguagem java e foram feitas classes implementandos os tipos de
gráficos, utilizando as formas de trapézio, triângulo e linha; as classes de controle, que são as
classes Casa e Imobiliaria, a primeira recebe todos os atributos que serão tratados pela fuzzy e a
segunda, trata os atributos com a lógica, a fim de efetuar a saída da qualidade final; por fim, a classe
Principal que é a interface com o usuário, para tornar a utilização mais cômoda. Foi efetuado,
também, um dataset com alguns exemplos de entrada.

As variáveis de entrada são:

- Valor:
    1. Barato – até R$ 350.000,00
    2. Mediano – de R$ 300.000,00 até R$ 500.000,00
    3. Caro – a partir de R$ 450.000,00 (utilizando trapézio na lógica fuzzy);
    
- Área construída:
    1. Pequena – até 150 m²
    2. Mediana – de 120 m² até 350 m²
    3. Grande – a partir de 300 m² (utilizando trapézio na lógica fuzzy);
    
- Área extra/externa:
    1. Pequena – até 350 m²
    2. Mediana – de 300 m² até 600 m²
    3. Grande – a partir de 550 m²;
    
- Vagas:
    1. Pouca – até 2
    2. Mediana – de 1 até 2,5 (podendo haver meia vaga também)
    3. Muita – a partir de 4;
    
- Localidade: 
    1. Ruim – até 4
    2. Mediana – de 3 até 7
    3. Boa a partir de 6.
    
O intervalo de qualificação da localidade é de 1 à 10, levando em conta vários fatores: se o imóvel está
perto do centro comercial (escolas, hospitais, supermercados, linhas de ônibus), se está perto
de algum estabelecimento que é responsável por alguma poluição sonora e como é a
infraestrutura (calçamento, luz, água, esgoto).

- Qualidade (saída):
    1. Ruim – até 3
    2. Mediana – de 2,5 até 6
    3. Boa – a partir de 5,2.

### Erros
Atualmente o sistema fuzzy está gerando valores pequenos devido a especificação rígida das regras dispostas, estamos codificando regras adicionais.
