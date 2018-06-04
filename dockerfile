#Docker file para basico spring boot project
FROM anapsix/alpine-java
VOLUME /tmp
ADD target/SeguridadGeneralRest-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8071 7071

#secccion para la ejecucion
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/app.jar"]

#############################################################################
#-------------------------NOTAS --------------------------------------------# 
#############################################################################

#-----------------------------------------------------------
#BUILD
# docker build -t "seguridad-api:dockerfile" .

#RUN PROFILES (local o dev)
# docker run -e"SPRING_PROFILES_ACTIVE=local" [IMAGE_ID]

#RUN PROFILES pasando como variables los puertos a exponer
# docker run -e"SPRING_PROFILES_ACTIVE=local" [IMAGE_ID]-d -p 8071:8071 
