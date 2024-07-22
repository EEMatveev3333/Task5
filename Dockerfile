#Dockerfile нужен для того чтобы на основании его мы создали образ,
#а уже на основании образа запустили контейнер с нашим бэкендом.
#Здесь мы используем многоэтапную сборку,
#
#на первом этапе мы получаем jar-ник нашего проекта
FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

#, а на втором мы его уже запускаем.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/*.jar"]

