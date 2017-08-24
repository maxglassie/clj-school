FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/clj-school.jar /clj-school/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/clj-school/app.jar"]
