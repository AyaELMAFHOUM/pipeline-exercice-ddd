## 🗂️ Database Design

![Database Design](./docs/db-design.png)

### 1. **Running PostgreSQL with Docker and Adding Scouts**

To run PostgreSQL using Docker and manually add scouts to the database, use the following command:

```bash
docker run --name postgres-container -e POSTGRES_PASSWORD=tmpPass -e POSTGRES_USER=postgres -e POSTGRES_DB=scoutingdb -p 5432:5432 -d postgres:13 && \
docker exec -it postgres-container psql -U postgres -d scoutingdb -c "INSERT INTO scout (username) VALUES ('Yanis');" && \
docker exec -it postgres-container psql -U postgres -d scoutingdb -c "INSERT INTO scout (username) VALUES ('Imad');" && \
docker exec -it postgres-container psql -U postgres -d scoutingdb -c "INSERT INTO scout (username) VALUES ('Aya');"
```

This will:
- Start a PostgreSQL container on port `5432` with the database `scoutingdb`.
- Automatically add scouts (`Yanis`, `Imad`, and `Aya`) to the `scouts` table.

### 2. **Running the Project**

After setting up the database and adding the scouts, follow these steps to run the Spring Boot application:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/imadovetch/Exercice-DDD/ Demo-test
   cd Demo-test
   ```

2. **Run the Spring Boot application:**
   ```bash
   ./mvnw spring-boot:run
   ```

> **PS:** Make sure PostgreSQL is running before you start the app.

---

### ✅ **Sample `curl` Request for Reporting**

> ⚠️ **Important:**  
> - If the player is being reported for the **first time**, you must provide:
>   - `age`
>   - `position` (must be a valid enum value, e.g., `ATTAQUANT`, `DEFENSEUR`, etc.)
> 
> - The scout must be registered in the database in order to submit a report.  
>  Since there is no login system yet, you need to manually create the scout first using the command below.
Once the scouts are added to the database, they can submit reports using the `/reports` endpoint. Here’s an example `curl` request to submit a report:

```bash
curl -X POST http://localhost:8080/reports \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Mbappé",
    "prenom": "Kylian",
    "age": 25,
    "position": "ATTAQUANT",
    "note": 100,
    "observation": "Très bon finisseur",
    "match": "PSG vs OM",
    "scoutUsername": "Aya"
  }' | jq .
```

---


