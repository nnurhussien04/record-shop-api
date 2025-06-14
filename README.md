📀** Record Shop Application**
This application is designed for managing a record shop's album inventory. It allows the shop to add, edit, and delete albums, as well as view detailed information for each one.

🎯 Key Features
Add, update, and delete album entries

View album details including:
Album name
Artist
Stock availability
Total sales

🧰 Tech Stack
Backend: Java with Spring Boot
Frontend: Android Studio using Java
Database: PostgreSQL

▶️ How to Run the Application
Clone the Repositories
Download both the frontend (Android app) and backend (Spring Boot API) projects from the repository.

1. Set Up the Database:
    Create a new database in your PostgreSQL (or preferred) database server.
    Copy and execute the provided SQL script to create the necessary tables and insert initial data.
    Verify that the tables and data have been created successfully.
2. Run the Backend:
    Open the backend project in your IDE (e.g., IntelliJ, Eclipse).
    Ensure your application.properties or application.yml is configured with the correct database credentials.
    Start the Spring Boot application and confirm that the local server is running (typically on http://localhost:8080).
3. Run the Frontend (Android App):
    Open the frontend project in Android Studio.
    Make sure the base URL for API calls in your Retrofit service matches your backend server address.
    Run the app on an emulator or physical device.
    You should now be able to view, add, edit, and delete albums through the mobile interface.
