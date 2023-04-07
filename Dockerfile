FROM openjdk:17-alpine
ADD /target/AgrarMarket-0.0.1-SNAPSHOT.jar agrar_market.jar
ENTRYPOINT ["java","-jar" , "agrar_market.jar"]
EXPOSE 8080