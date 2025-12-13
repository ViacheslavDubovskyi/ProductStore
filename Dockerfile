FROM eclipse-temurin:21-jdk
EXPOSE 8080
ENV JAR_FILE=target/ProductStore-*.jar

COPY ${JAR_FILE} /ProductStore.jar

ENTRYPOINT ["java", "-jar", "/ProductStore.jar"]