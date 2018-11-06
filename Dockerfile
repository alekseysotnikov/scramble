# Intermediate build image
FROM clojure:lein-2.8.1-alpine as builder
COPY . /scramble
RUN cd /scramble \
    && lein package

# Production image
FROM openjdk:8-jre-alpine
WORKDIR /scramble
COPY --from=builder /scramble/target/scramble.jar .

# Default startup command line
CMD java -jar scramble.jar -h 0.0.0.0 -p 3001