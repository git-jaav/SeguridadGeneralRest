#Docker file para basico spring boot project
FROM anapsix/alpine-java
VOLUME /tmp
ADD target/SeguridadGeneralRest-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9000

#secccion para la ejecucion
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]
 
###NOTAS####
#RUN PROFILES pasando como variables
# docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" --name rest-api dockerImage:latest

#BUILD
# docker build -t "seguridad-api:dockerfile" .