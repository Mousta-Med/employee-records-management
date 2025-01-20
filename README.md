# Employee Records Management System

## Overview
The **Employee Records Management System (ERMS)** is a comprehensive platform designed to centralize and manage employee data across various departments. This system provides HR teams, managers, and administrators with efficient tools for handling employee information, maintaining audit logs, and ensuring secure access control.

### Key Features:
- **Employee Data Management**: Efficiently store and manage employee information such as name, ID, job title, department, hire date, employment status, contact details, and address.

- **User Roles & Permissions**:
  - **HR Personnel**: Full access (CRUD) to all employee data.
  - **Managers**: View and update limited employee details within their department.
  - **Administrators**: Full system control, including user permissions and configuration management.

- **Audit Trail**: Track all changes made to employee records, capturing user and timestamp information for transparency and accountability.

- **Search & Filtering**:
  - Search employees by name, ID, department, or job title.
  - Filter employees by employment status, department, and hire date.

- **Data Validation**: Enforce data integrity with features like valid email formats and unique employee IDs.

- **Reporting**: Generate basic reports such as department headcounts and employee lists.

- **RESTful API**: Expose key functionalities through a RESTful API with clear Swagger documentation.

- **Desktop User Interface**: A user-friendly Swing-based UI for local interaction with the system.

---

## Technologies Used

- **Backend**:
  - **Java 17**: Modern version of Java for the backend.
  - **Spring Boot Framework**: Rapid application development and configuration management.
  - **Oracle SQL Database**: Store employee data in a relational database.
  - **Docker & Docker Compose**: Containerize the application for easy deployment.

- **Frontend**:
  - **Swing**: Java GUI framework for building the desktop user interface.
  - **MigLayout/GridBagLayout**: Layout managers used to create a responsive UI.

- **Testing**:
  - **JUnit & Mockito**: Unit and integration testing.
  - **Postman**: API validation and testing.

---

## Project Structure

```plaintext
├── .env
├── .gitignore
├── backend
│   ├── Dockerfile
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── med
│   │   │   │           └── backend
│   │   │   │               ├── BackendApplication.java
│   │   │   │               ├── config
│   │   │   │               │   ├── ModelMapperConfig.java
│   │   │   │               │   ├── OpenApiConfig.java
│   │   │   │               │   └── SecurityConfig.java
│   │   │   │               ├── controller
│   │   │   │               │   ├── AuditLogController.java
│   │   │   │               │   ├── EmployeeController.java
│   │   │   │               │   └── UserController.java
│   │   │   │               ├── exception
│   │   │   │               │   └── ResourceNotFoundException.java
│   │   │   │               ├── model
│   │   │   │               │   ├── entity
│   │   │   │               │   │   ├── AuditLog.java
│   │   │   │               │   │   ├── Employee.java
│   │   │   │               │   │   └── User.java
│   │   │   │               │   ├── enums
│   │   │   │               │   │   └── Role.java
│   │   │   │               │   ├── request
│   │   │   │               │   │   ├── AuditLogReq.java
│   │   │   │               │   │   ├── EmployeeReq.java
│   │   │   │               │   │   └── UserReq.java
│   │   │   │               │   └── response
│   │   │   │               │       ├── AuditLogRes.java
│   │   │   │               │       ├── EmployeeRes.java
│   │   │   │               │       └── UserRes.java
│   │   │   │               ├── repository
│   │   │   │               │   ├── AuditLogRepository.java
│   │   │   │               │   ├── EmployeeRepository.java
│   │   │   │               │   └── UserRepository.java
│   │   │   │               ├── service
│   │   │   │               │   ├── AuditLogService.java
│   │   │   │               │   ├── BaseService.java
│   │   │   │               │   ├── EmployeeService.java
│   │   │   │               │   ├── Impl
│   │   │   │               │   │   ├── AuditLogServiceImpl.java
│   │   │   │               │   │   ├── EmployeeServiceImpl.java
│   │   │   │               │   │   ├── UserDetailsServiceImpl.java
│   │   │   │               │   │   └── UserServiceImpl.java
│   │   │   │               │   └── UserService.java
│   │   │   │               └── util
│   │   │   │                   ├── DataLoader.java
│   │   │   │                   └── GlobalExceptionHandler.java
├── compose.yaml
├── Employee Management System Api.postman_collection.json
├── README.md
```
# Getting Started  

## Prerequisites  

Before starting the project, ensure the following tools are installed on your machine:  
- - **Docker**: For containerization of the application. You can install Docker from [here](https://www.docker.com/get-started). 
- - **VCXSRV**: The SwingUi requires VCXSRV to run App Ui. You can download it from [here](https://sourceforge.net/projects/vcxsrv/files/latest/). 
- ## Clone the Repository  
```git clone https://github.com/Mousta-Med/employee-records-management.git cd employee-records-management```
## Build & Run the Application
### Configuration  
1. **Set up the environment variables:**  
   In the root directory of the project, create a `.env` file with the following content. Be sure to substitute your database credentials where necessary:
```env    
ORACLEDB_DATABASE=dbName    
ORACLEDB_USER=user    
ORACLEDB_ROOT_PASSWORD=password
```
2. **Set the DISPLAY variable for GUI:**
- **Open** **VCXSRV** to allow graphical applications in Docker containers.
- Then, in your PowerShell or terminal, run the following command to set up the `DISPLAY` variable:

```powershell  
$ENV=DISPLAY host.docker.internal:0.0 
```

### Using Docker Compose

The easiest way to run the application is by using Docker Compose, which will set up both the backend and the Oracle database.

*   Build and start the containers:

`docker-compose up --build`

This will start the application and the database in the background. The application will be available at [http://localhost:8080](http://localhost:8080) for the backend. For the UI, a popup will appear displaying the Swing UI.

## Accessing the Application

### API Documentation (Swagger UI)

Once the backend is running, you can interact with the API via Swagger UI at:

[http://localhost:8080/swagger\-ui.html](http://localhost:8080/swagger-ui.html)

This UI will provide you with all the available API endpoints, along with the ability to test them directly from the browser.

### Postman Collection

We have included a Postman Collection for easy API testing. To import it:

1.  Open **Postman**.
2.  Click on the "Import" button in the top left.
3.  Choose the `Employee Management System Api.postman_collection.json` file from the project directory.

You can now use Postman to test the various API endpoints.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
