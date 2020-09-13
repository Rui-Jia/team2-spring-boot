FROM openjdk:11.0.8

COPY target/hackathon-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8111

# Set an environment variable, specifying the URL of MongoDB server we want to connect to (image name).
ENV EMPLOYEE_MONGODB_URI=mongodb://mymongodb:27017/test

ENTRYPOINT ["java","-jar","/app.jar"]