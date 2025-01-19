FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y \
    libxrender1 \
    xvfb \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    libfontconfig1 \
    && apt-get clean
COPY target/swing-ui-1.0-SNAPSHOT.jar swing-ui-1.0-SNAPSHOT.jar
ENV BACKEND_API_URL=http://backend:8080
ENV DISPLAY=host.docker.internal:0.0
ENTRYPOINT ["java", "-jar", "swing-ui-1.0-SNAPSHOT.jar"]
