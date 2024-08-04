# Сборка и запуск
### Сборка проекта
```
gradle build
```
или
```
./gradlew build
```
### Сборка и запуск через docker-compose (желательно)
```
docker-compose up -d --build
```
### Сборка и запуск через docker
```docker
docker build -t translate-service .
docker run -p 8080:8080 -t translate-service
```
Примечание: в данном случае нужно, чтобы был доступ к базе данных PostgreSQL по порту 5432, с пользователем root и паролем root. Название базы данных: tinkoff-translate-service
# Доступные эндпоинты
## Получение всех доступных языков

Пример запроса
```curl
curl -X GET "http://localhost:8080/languages"
```

Пример ответа OK(200)
```json
[
    {
        "code": "ru",
        "name": "русский"
    },
    {
        "code": "sq",
        "name": null
    }
]
```

## Перевести текст

Пример запроса
```curl
curl -X POST "http://localhost:8080/translate" \
    -H "Content-Type: application/json" \
    -d '{
          "sourceLanguage" : "ru",
          "targetLanguage" : "en",
          "text" : "Привет мир!"
        }'
```

Пример ответа OK(200)
```json
{
    "translated": "Hi world"
}
```
Пример ответа с ошибкой Bad Request(400)
```json
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Не найден требуемый язык",
    "instance": "/translate"
}
```
Пример ответа с ошибкой Internal Server Error(500)
```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "Ошибка доступа к ресурсу для перевода",
    "instance": "/translate"
}
```
