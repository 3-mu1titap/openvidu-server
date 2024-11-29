
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/OpenVidu-adaptors-0.0.1-SNAPSHOT.jar openvidu-server.jar

EXPOSE 4443

ENTRYPOINT ["java", "-jar", "openvidu-server.jar"]

ENV TZ=Asia/Seoul