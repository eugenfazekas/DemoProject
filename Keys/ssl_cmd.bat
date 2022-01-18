d:

cd D:\Programing\Git\Demo_Project\Keys\

del /S /F /Q export\*

echo ######################## CERTIFICATE AUTHORITY ########################

echo ## Generates a private key for the certificate authority
openssl genrsa -passout pass:"authoritypassword" -out export/ca/ca_key.pem 2048

echo ## Generates a public key for the certificate authority
openssl  req -new -passin pass:"authoritypassword" -key export/ca/ca_key.pem -x509 -days 3650 -out export/ca/ca_cert.pem -subj "/CN=example.com" 



echo ######################## Ingress ########################

echo ##Generates a private key for the Ingress
openssl genrsa -passout pass:"ingresspassword" -out export/ingress/app_key.pem 2048

echo ##Generates certificate signing request (CSR) for the Ingress 
openssl req -passin pass:"ingresspassword" -new -key export/ingress/app_key.pem -out export/ingress/csr-for-app -subj "/CN=example.com" -config csr.cnf 

echo ##Generates Ingress certificate, which is signed by the CA	
openssl x509 -req -passin pass:"authoritypassword" -days 3650 -in export/ingress/csr-for-app -CA export/ca/ca_cert.pem -CAkey export/ca/ca_key.pem -set_serial 01 -out export/ingress/app_cert.pem  -extfile crt.cnf 



echo ######################## Angular ########################

echo ##Generates a private key for the Angular
openssl genrsa -passout pass:"angularpassword" -out export/angular/app_key.pem 2048

echo ##Generates certificate signing request (CSR) for the Angular 
openssl req -passin pass:"angularpassword" -new -key export/angular/app_key.pem -out export/angular/csr-for-app -subj "/CN=example.com" -config csr.cnf 

echo ##Generates Angular certificate, which is signed by the CA	
openssl x509 -req -passin pass:"authoritypassword" -days 3650 -in export/angular/csr-for-app -CA export/ca/ca_cert.pem -CAkey export/ca/ca_key.pem -set_serial 01 -out export/angular/app_cert.pem  -extfile crt.cnf 




echo ######################## Gateway Server ########################

echo ##Generates a private key for the Gateway Server
openssl genrsa -passout pass:"gatewayserverpassword" -out export/gateway_server/app_key.pem 2048

echo ##Generates certificate signing request (CSR) for the Gateway Server 
openssl req -passin pass:"gatewayserverpassword" -new -key export/gateway_server/app_key.pem -out export/gateway_server/csr-for-app -subj "/CN=example.com" -config csr.cnf

echo ##Generates Gateway Server certificate, which is signed by the CA	
openssl x509 -req -passin pass:"authoritypassword" -days 3650 -in export/gateway_server/csr-for-app -CA export/ca/ca_cert.pem -CAkey export/ca/ca_key.pem -set_serial 02 -out export/gateway_server/app_cert.pem -extfile crt.cnf

echo ##Creates a Java keystore with Gateway Server private/public keys
openssl rsa -passin pass:"gatewayserverpassword" -in export/gateway_server/app_key.pem -out export/gateway_server/app_key.pem 

openssl pkcs12 -export -inkey export/gateway_server/app_key.pem -in export/gateway_server/app_cert.pem -out export/gateway_server/app.p12 -password pass:gatewayserverpassword

keytool -importkeystore -srcstorepass gatewayserverpassword -srckeystore export/gateway_server/app.p12 -srcstoretype pkcs12 -deststorepass gatewayserverpassword -destkeystore export/gateway_server/gateway_server.jks -deststoretype JKS

keytool -changealias -alias "1" -destalias "gateway_server" -keystore export/gateway_server/gateway_server.jks -storepass gatewayserverpassword



echo ######################## Authentication Service ########################

echo ##Generates a private key for the Authentication Service
openssl genrsa -passout pass:"authenticationservicepassword" -out export/authentication_service/app_key.pem 2048

echo ##Generates certificate signing request (CSR) for the Authentication Service 
openssl req -passin pass:"authenticationservicepassword" -new -key export/authentication_service/app_key.pem -out export/authentication_service/csr-for-app -subj "/CN=example.com" -config csr.cnf

echo ##Generates Authentication Service certificate, which is signed by the CA	
openssl x509 -req -passin pass:"authoritypassword" -days 3650 -in export/authentication_service/csr-for-app -CA export/ca/ca_cert.pem -CAkey export/ca/ca_key.pem -set_serial 03 -out export/authentication_service/app_cert.pem -extfile crt.cnf

echo ##Creates a Java keystore with Authentication Service private/public keys
openssl rsa -passin pass:"authenticationservicepassword" -in export/authentication_service/app_key.pem -out export/authentication_service/app_key.pem 

openssl pkcs12 -export -inkey export/authentication_service/app_key.pem -in export/authentication_service/app_cert.pem -out export/authentication_service/app.p12 -password pass:authenticationservicepassword

keytool -importkeystore -srcstorepass authenticationservicepassword -srckeystore export/authentication_service/app.p12 -srcstoretype pkcs12 -deststorepass authenticationservicepassword -destkeystore export/authentication_service/authentication_service.jks -deststoretype JKS

keytool -changealias -alias "1" -destalias "authentication_service" -keystore export/authentication_service/authentication_service.jks -storepass authenticationservicepassword



echo ######################## Resource Service ########################

echo ##Generates a private key for the Resource Service
openssl genrsa -passout pass:"resourceservicepassword" -out export/resource_service/app_key.pem 2048

echo ##Generates certificate signing request (CSR) for the Resource Service 
openssl req -passin pass:"resourceservicepassword" -new -key export/resource_service/app_key.pem -out export/resource_service/csr-for-app -subj "/CN=example.com" -config csr.cnf 

echo ##Generates Resource Service certificate, which is signed by the CA	
openssl x509 -req -passin pass:"authoritypassword" -days 3650 -in export/resource_service/csr-for-app -CA export/ca/ca_cert.pem -CAkey export/ca/ca_key.pem -set_serial 04 -out export/resource_service/app_cert.pem -extfile crt.cnf

echo ##Creates a Java keystore with Resource Service private/public keys
openssl rsa -passin pass:"resourceservicepassword" -in export/resource_service/app_key.pem -out export/resource_service/app_key.pem 

openssl pkcs12 -export -inkey export/resource_service/app_key.pem -in export/resource_service/app_cert.pem -out export/resource_service/app.p12 -password pass:resourceservicepassword 

keytool -importkeystore -srcstorepass resourceservicepassword -srckeystore export/resource_service/app.p12 -srcstoretype pkcs12 -deststorepass resourceservicepassword -destkeystore export/resource_service/resource_service.jks -deststoretype JKS

keytool -changealias -alias "1" -destalias "resource_service" -keystore export/resource_service/resource_service.jks -storepass resourceservicepassword



echo ######################## Importing certificates to Gateway Server Keystore ########################

keytool -importcert -file export/angular/app_cert.pem                  -alias angular                -noprompt  -keystore export/gateway_server/gateway_server.jks -storepass gatewayserverpassword

keytool -importcert -file export/authentication_service/app_cert.pem   -alias authentication_service -noprompt  -keystore export/gateway_server/gateway_server.jks -storepass gatewayserverpassword

keytool -importcert -file export/resource_service/app_cert.pem         -alias resource_service       -noprompt  -keystore export/gateway_server/gateway_server.jks -storepass gatewayserverpassword



echo ######################## Importing certificates to Authentication Service Keystore ########################

keytool -importcert -file export/gateway_server/app_cert.pem    -alias gateway_server    -noprompt  -keystore export/authentication_service/authentication_service.jks -storepass authenticationservicepassword

keytool -importcert -file export/resource_service/app_cert.pem  -alias resource_service  -noprompt  -keystore export/authentication_service/authentication_service.jks -storepass authenticationservicepassword



echo ######################## Importing certificates to Resource Service Keystore ########################


keytool -importcert -file export/gateway_server/app_cert.pem         -alias gateway_server         -noprompt  -keystore export/resource_service/resource_service.jks -storepass resourceservicepassword

keytool -importcert -file export/authentication_service/app_cert.pem -alias authentication_service -noprompt  -keystore export/resource_service/resource_service.jks -storepass resourceservicepassword



echo ######################## Deleting CERTIFICATE's  from truststore ########################

keytool -delete  -alias ca                      -cacerts -storepass  changeit 



echo ######################## Adding CA CERTIFICATE to truststore ########################

keytool -importcert  -file export/ca/ca_cert.pem                         -alias ca                      -cacerts -storepass  changeit  -trustcacerts  -noprompt




echo  ######################## Copy-ing keystores and truststore-s ########################

copy "export\angular\app_cert.pem" "..\Angular_Ui\ssl\angular\"

copy "export\angular\app_key.pem" "..\Angular_Ui\ssl\angular\"

copy "export\ca\ca_cert.pem" "..\Angular_Ui\ssl\ca\"

copy "export\ingress\app_cert.pem" "..\Angular_Ui\ssl\ingress\"

copy "export\gateway_server\app_cert.pem" "..\Angular_Ui\ssl\gateway_server\"

copy "export\gateway_server\gateway_server.jks"                  "..\Gateway_Server\src\main\resources\keystore\"

copy  "export\authentication_service\authentication_service.jks" "..\OAuth2_JWT_Authentication_Server\src\main\resources\keystore\"

copy  "export\resource_service\resource_service.jks"             "..\OAuth2_JWT_Resource_Server\src\main\resources\keystore\"

copy  "C:\Program Files\Java\jdk-11.0.11\lib\security\cacerts"   "..\Gateway_Server\src\main\resources\keystore\"

copy  "C:\Program Files\Java\jdk-11.0.11\lib\security\cacerts"   "..\OAuth2_JWT_Authentication_Server\src\main\resources\keystore\"

copy  "C:\Program Files\Java\jdk-11.0.11\lib\security\cacerts"   "..\OAuth2_JWT_Resource_Server\src\main\resources\keystore\"


pause





