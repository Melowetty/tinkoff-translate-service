# Сборка и запуск
todo...
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
