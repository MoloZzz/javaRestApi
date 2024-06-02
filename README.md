## Lab 1 OOP Java 2024
## Back-end:
Servlets(http Запити), Filters, JDBC(робота з бд PostgreSQL)
Please use Keycloak(авторизація), Auth0 (jwt, авторизація)

Logging – log4j.
REST. Ломбок, mapstruct.
MVC pattern. GOF patterns. Not more then 5-6 tables.
Web-server: tomcat.
CVS: github, gitlab, pulumi, AWS ECS
## Система Періодичні видання.
1) Адміністратор здійснює ведення каталогу Періодичних видань.
2) Читач може оформити Передплату,
попередньо вибравши Періодичні видання зі списку. 
3) Система підраховує суму для оплати та реєструє Платіж.

## Для розробників
Create subscription json:
{
"userId": 1,
"publicationId":1
}
Create publication json:
{
"title": "",
"description": "",
"price": 4.00
}
Create payment json:
{
"userId": 5,
"publicationId": 1
}
