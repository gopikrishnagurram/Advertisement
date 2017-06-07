FROM java:8
ADD target/advertisement.jar advertisement.jar
ENTRYPOINT ["java","-jar","advertisement.jar"]