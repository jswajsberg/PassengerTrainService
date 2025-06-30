# Passenger Train Service REST API

This project is a RESTful web service for managing a fictional passenger train system. It is built using **Java**, **Jakarta REST (JAX-RS)** with **Jersey**, and is designed for deployment in a servlet container such as Apache Tomcat.

## 📦 Features

- View available train routes with schedule information
- Manage bookings (view by ID, create via HTML forms)
- JSON and HTML response formats
- Simple, themed HTML forms for basic interaction (for demo/testing)
- Hardcoded sample data to simulate backend logic

## 🛠 Technologies Used

- Java SE
- Jakarta RESTful Web Services (JAX-RS)
- Jersey (JAX-RS implementation)
- Apache Tomcat (or other servlet containers)
- Maven (for build management)
- Postman (for API testing)

## 📁 Project Structure

```
src/
├── model/
│   └── Train.java
├── service/
│   └── TrainService.java
├── resource/
│   ├── TrainResource.java
│   └── BookingResource.java
├── web/
│   └── forms/
│       ├── booking_form.html
│       ├── search_form.html
│       └── cancel_form.html
```

> Note: Structure may vary slightly depending on project layout and Maven packaging.

## 🚂 Sample API Endpoints

| Endpoint                        | Method | Description                     | Response Format |
|-------------------------------|--------|---------------------------------|-----------------|
| `/trains`                     | GET    | List all available trains       | JSON            |
| `/trains/{id}`                | GET    | Get a train by ID               | JSON            |
| `/bookings/html/{id}`         | GET    | Display booking in HTML format  | HTML            |
| `/forms/booking`              | GET    | Display train booking form      | HTML            |
| `/forms/search`               | GET    | Display train search form       | HTML            |
| `/forms/cancel`              | GET    | Display booking cancellation    | HTML            |

## 🧪 How to Test

You can test the REST API using:
- **Postman**: for sending GET/POST requests and viewing JSON/HTML responses.
- **Browser**: for accessing HTML forms directly.

### Example:
```bash
GET http://localhost:8080/passenger-train-service/api/trains
```

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/passenger-train-service.git
   ```

2. Import into Eclipse or your preferred IDE as a Maven project.

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Deploy the generated WAR file to your servlet container (e.g., Tomcat `webapps/` folder).

5. Start your server and visit:
   ```
   http://localhost:8080/passenger-train-service/api/trains
   ```

## 📌 Notes

- All train and booking data is **hardcoded** for demonstration purposes.
- The HTML forms are basic but styled consistently for a simple and professional look.
- No database connection is required.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).