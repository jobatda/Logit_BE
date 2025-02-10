## jar 파일 빌드
FROM gradle:8.12.1-jdk-21-and-23-alpine AS builder

WORKDIR /app
COPY . .
RUN ./gradlew build -x test

## 배포를 위해 가벼운 이미지로 실행
FROM amazoncorretto:21-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]