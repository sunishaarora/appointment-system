# Central Appointment Management System

The Central Appointment Management System interacts with the User and Appointment Microservices to create a unified appointment management solution.

### Requirements
- Java Development Kit (JDK) 11 or higher
- MySQL Databases for User and Appointment Microservices

### Local Setup
1. Clone the repository: `git clone https://github.com/sunishaarora/appointment-system.git`
2. Open the project in your preferred IDE (e.g., IntelliJ, Eclipse).
3. Configure the database connections for User and Appointment Microservices in `src/main/resources/application.properties`.

### How to Run
Before running this central system, make sure the following components are running:
- [User Microservice](https://github.com/sunishaarora/appointment-system-user) at http://localhost:8100
- [Appointment Microservice](https://github.com/sunishaarora/appointment-system-appt) at http://localhost:8200

Run the main class `com.perficient.apptsystem.ApptSystemApplication` to start the central system. It will run on port 8080.

### YouTube Demo
Watch the project demonstration on YouTube: [Appointment Management System Demo](https://youtu.be/Yy6mHlTzyC8)

### What I Learned
- Integrating multiple microservices into a centralized system
- Using Spring Cloud Config Server for configuration management
- Handling API calls and data retrieval from distributed microservices
- Gained insights into building a scalable and maintainable application
