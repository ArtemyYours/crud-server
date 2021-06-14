FROM openjdk:11
ADD build/libs/balance_tracker_crud-server-0.1-SNAPSHOT.jar balance_tracker_crud-server-0.1-SNAPSHOT.jar
EXPOSE 8082
CMD ["java", "-jar", "balance_tracker_crud-server-0.1-SNAPSHOT.jar", "&", "disown"]