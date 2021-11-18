FROM maven AS builder
WORKDIR /usr/src/backend

COPY . .
RUN mvn -f pom.xml package -DskipTests

FROM openjdk
WORKDIR /app

COPY --from=builder /usr/src/backend/target/places-app-backend-1.0.0.jar places-app-backend-1.0.0.jar
EXPOSE 5000
CMD ["java", "-jar", "places-app-backend-1.0.0.jar"]