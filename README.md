# Backend path for the socks stock online management system

## Main functionalities:

- socks receipt and release;
- track the number of pieces of each color and fabric composition at a time.

## URL HTTP-calls list:

### POST /api/socks/income

Socks receipt registration.

Параметры запроса передаются в теле запроса в виде JSON-объекта со следующими атрибутами:

color — цвет носков, строка (например, black, red, yellow);  
cottonPart — процентное содержание хлопка в составе носков, целое число от 0 до 100 (например, 30, 18, 42);  
quantity — количество пар носков, целое число больше 0

Responses:

HTTP 200 — successful receipt (OK);  
HTTP 400 — invalid or missing request parameters (Bad Request);  
HTTP 500 — internal error on server side (Internal Server Error).

### POST /api/socks/outcome

Регистрирует отпуск носков со склада. Здесь параметры и результаты аналогичные, но общее количество носков указанного цвета и состава не увеличивается, а уменьшается.

### GET /api/socks

Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса.

Параметры запроса передаются в URL:

color — цвет носков, строка;  
operation — оператор сравнения значения количества хлопка в составе носков, одно значение из: moreThan, lessThan, equal;  
cottonPart — значение процента хлопка в составе носков из сравнения.

Результаты:

HTTP 200 — запрос выполнен, результат в теле ответа в виде строкового представления целого числа;  
HTTP 400 — параметры запроса отсутствуют или имеют некорректный формат;  
HTTP 500 — произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна).

Query examples:

/api/socks?color=red&operation=moreThan&cottonPart=90 — returns total count of red socks with cotton part more than 90%;  
/api/socks?color=black&operation=lessThan?cottonPart=10 — returns total count of black socks with cotton part less than 10%.

## Stack of technologies:
Java 17, Spring Boot 3.0.5, Hibernate, PostgreSQL, Lombok, Spring Doc Open Api   
JUnit, Mockito   
Docker, Postman
