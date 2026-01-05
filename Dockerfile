FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY project-management/pom.xml .
COPY project-management/src ./src

RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    apk del maven

EXPOSE 8080

CMD ["java", "-jar", "target/project-management-0.0.1-SNAPSHOT.jar"]
