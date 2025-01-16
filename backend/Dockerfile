FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /backend
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /backend
COPY --from=build /backend/target/backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
