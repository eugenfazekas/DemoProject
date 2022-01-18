FROM nginx:1.21.4-alpine

RUN apk --no-cache add curl

COPY default.conf /etc/nginx/conf.d/default.conf
COPY /dist/Angular-MyProject01  /usr/share/nginx/html
COPY service_check.sh /docker-entrypoint.d/

ADD ssl/ /usr/local/nginx/conf/ssl/

RUN chown root /usr/share/nginx/html/*
RUN chmod 755 /usr/share/nginx/html/*

EXPOSE 80

EXPOSE 443

EXPOSE 2443

CMD ["nginx", "-g", "daemon off;"]

