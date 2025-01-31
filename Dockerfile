FROM openjdk:17-jdk-slim
COPY target/creditModule-0.0.1-SNAPSHOT.jar creditModule.jar
EXPOSE 8080
CMD ["java", "-jar", "creditModule-0.0.1.jar", "--spring.profiles.active=test"]
ENTRYPOINT ["java", "-jar", "creditModule.jar"]