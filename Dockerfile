FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

CMD ["java", "-jar", "$(ls target/*.jar | head -n 1)"]
