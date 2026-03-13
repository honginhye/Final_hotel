FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/Final_hotel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9081

ENTRYPOINT ["java", "-jar", "app.jar"]