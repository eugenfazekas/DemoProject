FROM nginx:1.21.4-alpine

RUN apk --no-cache add curl

COPY default.conf /etc/nginx/conf.d/default.conf
COPY /dist/Angular-MyProject01  /usr/share/nginx/html
COPY service_check.sh /docker-entrypoint.d/
COPY ssl/domain.crt /usr/local/nginx/conf/domain.crt
COPY ssl/domain.key /usr/local/nginx/conf/domain.key


RUN chown root /usr/share/nginx/html/*
RUN chmod 755 /usr/share/nginx/html/*

EXPOSE 80

EXPOSE 443

CMD ["nginx", "-g", "daemon off;"]

