FROM openjdk:8
COPY ./target/rinha-perigosa-1.0.0-jar-with-dependencies.jar /app/rinha-perigosa-1.0.0-jar-with-dependencies.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "rinha-perigosa-1.0.0-jar-with-dependencies.jar"]
EXPOSE 3000