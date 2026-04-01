# Imagem sem versão (latest implícito) ❌
FROM golang:latest

# Rodando como root (default) ❌
WORKDIR /app

# Instala pacotes sem --no-cache ❌
RUN apk add curl git vim

# Usa apt em alpine (erro proposital) ❌
RUN apt-get update && apt-get install -y wget

# Copia tudo sem .dockerignore ❌
COPY . .

# Baixa script remoto e executa (supply chain risk) ❌
RUN curl -sL https://example.com/install.sh | sh

# Variáveis sensíveis hardcoded ❌
ENV AWS_SECRET_ACCESS_KEY=123456
ENV DB_PASSWORD=supersecret

# Adiciona permissões perigosas ❌
RUN chmod 777 /app

# Usa ADD em vez de COPY (sem necessidade) ❌
ADD . /app

# Expõe porta sem necessidade clara ❌
EXPOSE 80

# Não usa multi-stage (imagem grande) ❌
RUN go build -o app .

# Usa root explicitamente ❌
USER root

# Sem healthcheck ❌

# Usa shell form (menos seguro) ❌
CMD ./app
# Teste 5
