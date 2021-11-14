FROM nginx:1.21.4-alpine

RUN apk --no-cache add curl

COPY default.conf /etc/nginx/conf.d/default.conf
COPY /dist/Angular-MyProject01  /usr/share/nginx/html
COPY service_check.sh /docker-entrypoint.d/

EXPOSE 80



