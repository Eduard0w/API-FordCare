# multi-stage build
# Criação base, maven + JDK para o funcionamento da aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Diretóriodo container onde tudo que for cópiado ficará
WORKDIR /app

# O que será copiado (Nesse caso, tudo)
COPY . .

# Comando que prepara a primeira parte (nesse caso, compacta a aplicação java usando a ferramenta maven. Criando uma pasta target, onde o executavel se encontra)
RUN ./mvnw clean package -DskipTest

# Inicia uma nova imagem limpa (runtime), descartando o código fonte e ferramentas de build (Maven) da etapa anterior.
FROM eclipse-temurin:21

WORKDIR /app

# Copia o que foi "buildado" na primeira etapa pós último comando
COPY --from=build /app/target/*.jar app.jar

# Documenta que a aplicação escuta na porta 8080 (Ainda precisa liberar a porta ao rodar o container).
EXPOSE 8080

# Comando que roda a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
