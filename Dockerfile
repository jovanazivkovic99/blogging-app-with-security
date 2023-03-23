FROM eclipse-mosquitto
EXPOSE 8081
ADD target/blog.jar blog.jar
ENTRYPOINT ["java","-jar","/blog.jar"]