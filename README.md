# Task Management System
## Инструкция для запуска
### 1. Создание .env файла
Создайте файл .env в корне вашего проекта, и поместите в него переменные окружения. Пример:
```
POSTGRES_DATABASE=spring_web_task_system  #Название базы данных
POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=postgres
JWT_SECRET=c2prZmhvaWFlZnVnYX #Секретная строка для генерации токенов
```

### 2. Запуск Docker-compose
Выполните команду `docker-compose up` в корне вашего проекта.
Сервер запустится на порту 8080

## Документация API
Документация API доступна по ссылке: http://localhost:8080/swagger-ui/index.html#/
