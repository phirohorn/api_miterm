# Employee & Product API (Spring Boot + JWT)

Backend for the Flutter app: JWT login/logout, product list, employee CRUD.

## Run

1. Start PostgreSQL (Docker Desktop must be running):
   ```
   cd postgres
   docker compose up -d
   ```
2. Start the API:
   ```
   ./gradlew bootRun
   ```
   It runs on `http://localhost:33033`. Tables are created automatically, and a
   login account plus demo products and employees are added when the tables are empty.

Swagger UI: http://localhost:33033/swagger-ui.html (log in, then click **Authorize** and paste the access token).

**Login:** `admin` / `Admin@1234`

> Flutter on the Android emulator: use `http://10.0.2.2:33033` instead of `localhost`.

## Endpoints

All endpoints except `login` and `refresh` need the header `Authorization: Bearer <accessToken>`.

| Method | URL | Body | Result |
|---|---|---|---|
| POST | `/api/auth/login` | `{"username","password"}` | `{accessToken, refreshToken, tokenType, expiresIn, user}` |
| POST | `/api/auth/refresh` | `{"refreshToken"}` | new tokens (same shape as login) |
| GET | `/api/auth/me` | | `{id, username, email, roles}` |
| POST | `/api/auth/logout` | | `{message}`. The access token and refresh tokens stop working |
| GET | `/api/products?search=` | | list of products |
| GET | `/api/products/{id}` | | one product |
| GET | `/api/employees?search=` | | list of employees (newest first) |
| GET | `/api/employees/{id}` | | one employee |
| POST | `/api/employees` | employee JSON | created employee (201) |
| PUT | `/api/employees/{id}` | employee JSON | updated employee |
| DELETE | `/api/employees/{id}` | | `{message}` |

### Employee JSON

```json
{
  "employeeCode": "EMP010",
  "firstName": "Sok",
  "lastName": "Dara",
  "gender": "MALE",
  "dateOfBirth": "2000-05-01",
  "email": "dara@example.com",
  "phoneNumber": "012345678",
  "position": "Developer",
  "department": "IT",
  "salary": 900,
  "hireDate": "2025-01-10",
  "address": "Phnom Penh",
  "photoUrl": null,
  "status": "ACT"
}
```

Required: `employeeCode`, `firstName`, `lastName`, `email`, `phoneNumber`.
`gender` is `MALE` / `FEMALE` / `OTHER`; `status` is `ACT` / `INA`; dates are `yyyy-MM-dd`.

### Errors

Every error has the same shape. `errors` is only present for validation failures:

```json
{ "status": 400, "message": "Validation failed",
  "errors": { "email": "Email is not valid" }, "timestamp": "..." }
```

| Status | Meaning |
|---|---|
| 400 | validation failed / bad JSON |
| 401 | wrong login, missing/expired/logged-out token |
| 404 | employee or product not found |
| 409 | duplicate employee code, or record still linked to other data |
# api_miterm
