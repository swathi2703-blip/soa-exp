# Build the Spring Boot application with the Java version declared in pom.xml.
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Run only the packaged application in a smaller Java runtime image.
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/config-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
