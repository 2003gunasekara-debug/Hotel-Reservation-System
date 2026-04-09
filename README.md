# 🏨 HotelSync — Hotel Reservation System

<div align="center">

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Servlet](https://img.shields.io/badge/Servlet_4.0-blue?style=for-the-badge)
![JSP](https://img.shields.io/badge/JSP-Pages-orange?style=for-the-badge)
![JSTL](https://img.shields.io/badge/JSTL-1.2-green?style=for-the-badge)
![Bootstrap](https://img.shields.io/badge/Bootstrap_5.3-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![SQL Server](https://img.shields.io/badge/SQL_Server-CC2927?style=for-the-badge&logo=microsoft-sql-server&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

**A full-stack hotel reservation and management web application built with Java Servlets, JSP, and Microsoft SQL Server — featuring multi-role authentication, room booking, payments, reviews, and an admin dashboard.**

[Features](#-features) • [Tech Stack](#-tech-stack) • [Architecture](#-architecture) • [Project Structure](#-project-structure) • [Setup](#-setup--installation) • [Database](#-database-setup) • [Usage](#-usage)

</div>

---

## ✨ Features

### 🔐 Multi-Role Authentication
- **Tourist Registration & Login** — Tourists can sign up and access their personalized dashboard
- **Hotel Owner Signup & Login** — Hotel owners register and manage their properties
- **Admin Dashboard** — Centralized admin panel with system-wide statistics and management controls
- **Session-Based Security** — Secure session management with role-based access control

### 🏢 Hotel Management (Owner)
- **Add Hotels** — Register new hotel properties with name, location, description, amenities, and images
- **Edit Hotels** — Update hotel details, images, and amenity listings
- **Delete Hotels** — Remove hotel listings with confirmation
- **Owner Dashboard** — Overview of all owned hotels with quick action buttons

### 🛏️ Room Management
- **Add Rooms** — Create rooms with number, type, price, amenities, and images
- **Room Types** — Standard, Deluxe, Suite, Executive, Family, and Presidential categories
- **Edit/Delete Rooms** — Full CRUD operations on room listings
- **Room Availability** — Real-time availability tracking and updates
- **Room Validation** — Strategy Pattern-based validation (Standard & Strict modes)
- **Builder Pattern** — Fluent room creation with the Builder design pattern

### 📋 Booking System
- **Create Bookings** — Book rooms with check-in/check-out dates and guest count
- **View Bookings** — Tourists can view their booking history; Owners can see hotel bookings
- **Edit Bookings** — Modify existing booking details
- **Cancel Bookings** — Cancel reservations with proper status updates
- **Booking Status Tracking** — Pending → Confirmed → Cancelled workflow

### 💳 Payment Processing
- **Secure Payments** — Process card payments with card number, expiry, and CVV
- **Payment History** — View all past transactions
- **Cancel Payments** — Payment cancellation with refund tracking

### ⭐ Review System
- **Submit Reviews** — Rate hotels (1–5 stars) with descriptions and photo uploads
- **View Reviews** — Browse all community reviews per hotel
- **My Reviews** — Tourists can view and manage their own reviews
- **Delete Reviews** — Remove reviews when necessary

### 👤 Profile Management
- **Owner Profile** — View and update hotel owner profile details
- **Tourist Profile** — Manage personal information and preferences
- **Account Deletion** — Self-service account removal for both owners and tourists

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java 17 |
| **Backend** | Java Servlets (javax.servlet 4.0.1) |
| **Frontend** | JSP (JavaServer Pages) + JSTL 1.2 |
| **UI Framework** | Bootstrap 5.3 + Font Awesome 6.4 |
| **Database** | Microsoft SQL Server (primary) / MySQL (profile) |
| **Build Tool** | Apache Maven 3.x |
| **Server** | Apache Tomcat 9.x |
| **Logging** | SLF4J + Logback |
| **Testing** | JUnit 4.13.2 |
| **Font** | Google Fonts (Poppins) |

---

## 🏗 Architecture

```
┌─────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│     Browser      │────▶│    Servlets       │────▶│   SQL Server     │
│  (JSP + BS5)     │◀────│  (Controllers)    │◀────│   (Database)     │
└─────────────────┘     └──────────────────┘     └──────────────────┘
                               │     │
                        ┌──────┘     └──────┐
                        │                   │
                 ┌──────┴──────┐    ┌───────┴───────┐
                 │   Models    │    │   DAOs        │
                 │  (POJOs)    │    │ (Data Access) │
                 └─────────────┘    └───────────────┘
                                           │
                                    ┌──────┴──────┐
                                    │  Strategy   │
                                    │ (Validation)│
                                    └─────────────┘
```

### Design Patterns Used

| Pattern | Where | Purpose |
|---------|-------|---------|
| **MVC** | Servlets → Models → JSPs | Separation of concerns |
| **DAO** | `dao/` package (9 DAOs) | Abstracts all database operations |
| **Strategy** | `strategy/` package | Interchangeable room validation rules |
| **Builder** | `Room.Builder` class | Fluent, readable Room object construction |

---

## 📂 Project Structure

```
hotel/
├── pom.xml                                    # Maven build configuration
├── .gitignore                                 # Git ignore rules
├── README.md                                  # This file
└── src/
    └── main/
        ├── java/com/HotelReservation/
        │   ├── model/                         # Data Models (POJOs)
        │   │   ├── Admin.java                 # Admin entity
        │   │   ├── Owner.java                 # Hotel Owner entity
        │   │   ├── Tourist.java               # Tourist/Guest entity
        │   │   ├── Hotel.java                 # Hotel entity
        │   │   ├── Room.java                  # Room entity (with Builder)
        │   │   ├── Booking.java               # Booking entity
        │   │   ├── Payment.java               # Payment entity
        │   │   ├── Review.java                # Review entity
        │   │   └── Availability.java          # Room availability entity
        │   │
        │   ├── dao/                           # Data Access Objects
        │   │   ├── AdminDAO.java              # Admin CRUD operations
        │   │   ├── OwnerDAO.java              # Owner CRUD operations
        │   │   ├── TouristDAO.java            # Tourist CRUD operations
        │   │   ├── HotelDAO.java              # Hotel CRUD operations
        │   │   ├── RoomDAO.java               # Room CRUD operations
        │   │   ├── BookingDAO.java            # Booking CRUD operations
        │   │   ├── PaymentDAO.java            # Payment CRUD operations
        │   │   ├── ReviewDAO.java             # Review CRUD operations
        │   │   └── AvailabilityDAO.java       # Availability operations
        │   │
        │   ├── servlets/                      # HTTP Servlets (33 Controllers)
        │   │   ├── LoginServlet.java          # Multi-role authentication
        │   │   ├── OwnerSignupServlet.java    # Owner registration
        │   │   ├── TouristRegisterServlet.java # Tourist registration
        │   │   ├── AdminDashboardServlet.java # Admin panel
        │   │   ├── TouristDashboardServlet.java # Tourist dashboard
        │   │   ├── AddHotelServlet.java       # Create hotel
        │   │   ├── EditHotelServlet.java      # Update hotel
        │   │   ├── DeleteHotelServlet.java    # Remove hotel
        │   │   ├── AddRoomServlet.java        # Create room
        │   │   ├── EditRoomServlet.java       # Update room
        │   │   ├── DeleteRoomServlet.java     # Remove room
        │   │   ├── ListRoomsServlet.java      # List hotel rooms
        │   │   ├── AddBookingServlet.java     # Create booking
        │   │   ├── EditBookingServlet.java    # Update booking
        │   │   ├── DeleteBookingServlet.java  # Cancel booking
        │   │   ├── ViewBookingsServlet.java   # View bookings
        │   │   ├── PaymentServlet.java        # Process payment
        │   │   ├── ViewPaymentsServlet.java   # Payment history
        │   │   ├── CancelPaymentServlet.java  # Cancel payment
        │   │   ├── AddReviewServlet.java      # Submit review
        │   │   ├── ViewReviewsServlet.java    # View reviews
        │   │   ├── MyReviewsServlet.java      # User's reviews
        │   │   ├── DeleteReviewServlet.java   # Remove review
        │   │   ├── ProfileServlet.java        # Owner profile
        │   │   ├── UpdateProfileServlet.java  # Update owner profile
        │   │   ├── TouristProfileServlet.java # Tourist profile
        │   │   ├── UpdateTouristProfileServlet.java # Update tourist profile
        │   │   ├── DeleteAccountServlet.java  # Delete owner account
        │   │   ├── DeleteTouristAccountServlet.java # Delete tourist account
        │   │   ├── BuyRoomServlet.java        # Room purchase flow
        │   │   ├── ViewHotelRoomsServlet.java # Public room browsing
        │   │   ├── UpdateAvailabilityServlet.java # Room availability
        │   │   └── OwnerLogoutServlet.java    # Logout handler
        │   │
        │   ├── strategy/                      # Strategy Pattern
        │   │   ├── RoomValidationStrategy.java       # Validation interface
        │   │   ├── StandardRoomValidationStrategy.java # Basic validation
        │   │   └── StrictRoomValidationStrategy.java  # Advanced validation
        │   │
        │   └── util/                          # Utilities
        │       └── DBUtil.java                # Database connection manager
        │
        ├── resources/                         # Application resources
        │
        └── webapp/                            # Web Application Root
            ├── index.jsp                      # Owner Dashboard (landing)
            ├── login.jsp                      # Login page (multi-role)
            ├── touristRegister.jsp             # Tourist registration
            ├── ownerSignup.jsp                # Owner registration
            ├── touristDashboard.jsp            # Tourist main dashboard
            ├── adminDashboard.jsp              # Admin control panel
            ├── hotelRooms.jsp                 # Browse hotel rooms
            ├── bookingForm.jsp                # Create booking form
            ├── editBooking.jsp                # Edit booking form
            ├── touristBookings.jsp             # Tourist booking history
            ├── payment.jsp                    # Payment processing page
            ├── touristPayments.jsp             # Payment history
            ├── touristProfile.jsp              # Tourist profile settings
            ├── ownerProfile.jsp               # Owner profile settings
            ├── addReview.jsp                  # Submit review form
            ├── viewReviews.jsp                # Browse all reviews
            ├── myReviews.jsp                  # User's review history
            ├── contact.jsp                    # Contact page
            ├── privacy.jsp                    # Privacy policy
            ├── hotel_owner/                   # Owner-Specific Pages
            │   ├── addHotel.jsp               # Add new hotel form
            │   ├── editHotel.jsp              # Edit hotel form
            │   ├── addRoom.jsp                # Add new room form
            │   ├── editRoom.jsp               # Edit room form
            │   ├── rooms.jsp                  # Room management view
            │   ├── bookings.jsp               # Hotel bookings view
            │   └── availability.jsp           # Room availability manager
            └── WEB-INF/
                └── web.xml                    # Deployment descriptor
```

---

## 🚀 Setup & Installation

### Prerequisites

| Requirement | Version |
|-------------|---------|
| Java JDK | 17 or higher |
| Apache Maven | 3.6+ |
| Apache Tomcat | 9.x |
| Microsoft SQL Server | 2019+ |
| IDE (recommended) | IntelliJ IDEA |

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/HotelReservation.git
   cd HotelReservation
   ```

2. **Set up the database** *(see [Database Setup](#-database-setup) below)*

3. **Configure database connection**

   Update `src/main/java/com/HotelReservation/util/DBUtil.java`:
   ```java
   private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Hotel_Website;encrypt=true;trustServerCertificate=true";
   private static final String USER = "your_db_username";
   private static final String PASSWORD = "your_db_password";
   ```

4. **Build the project**
   ```bash
   mvn clean package
   ```

5. **Deploy to Tomcat**
   - Copy `target/hotel-reservation.war` to Tomcat's `webapps/` directory
   - Or configure your IDE (IntelliJ Smart Tomcat / Eclipse) to deploy directly

6. **Start Tomcat and visit**
   ```
   http://localhost:8080/hotel-reservation/
   ```

---

## 🗄 Database Setup

Create the database and required tables in SQL Server:

```sql
-- Create Database
CREATE DATABASE Hotel_Website;
GO

USE Hotel_Website;
GO

-- Admin table
CREATE TABLE admins (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL
);

-- Hotel Owners table
CREATE TABLE owners (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL
);

-- Tourists table
CREATE TABLE tourists (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL
);

-- Hotels table
CREATE TABLE hotels (
    id INT IDENTITY(1,1) PRIMARY KEY,
    owner_id INT NOT NULL,
    name NVARCHAR(200) NOT NULL,
    location NVARCHAR(300),
    description NVARCHAR(MAX),
    image_path NVARCHAR(500),
    amenities NVARCHAR(MAX),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- Rooms table
CREATE TABLE rooms (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hotel_id INT NOT NULL,
    room_number NVARCHAR(10) NOT NULL,
    room_type NVARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_path NVARCHAR(500) DEFAULT 'uploads/default_room.jpg',
    amenities NVARCHAR(MAX),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);

-- Bookings table
CREATE TABLE bookings (
    id INT IDENTITY(1,1) PRIMARY KEY,
    room_id INT NOT NULL,
    tourist_id INT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    guests INT DEFAULT 1,
    status NVARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (tourist_id) REFERENCES tourists(id)
);

-- Payments table
CREATE TABLE payments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tourist_id INT NOT NULL,
    room_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    card_number NVARCHAR(20),
    expiry NVARCHAR(10),
    cvv NVARCHAR(5),
    payment_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tourist_id) REFERENCES tourists(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- Reviews table
CREATE TABLE reviews (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tourist_id INT NOT NULL,
    hotel_id INT NOT NULL,
    booking_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    description NVARCHAR(MAX),
    image_path NVARCHAR(500) DEFAULT 'Uploads/reviews/default.jpg',
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tourist_id) REFERENCES tourists(id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- Availability table
CREATE TABLE availability (
    id INT IDENTITY(1,1) PRIMARY KEY,
    room_id INT NOT NULL,
    available_date DATE NOT NULL,
    is_available BIT DEFAULT 1,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);
```

---

## 💡 Usage

### Getting Started
1. **Visit the login page** — Navigate to the application URL
2. **Register** — Create an account as a Tourist or Hotel Owner
3. **Login** — Access your role-specific dashboard

### 🧳 As a Tourist
- Browse available hotels and their rooms
- Book rooms with check-in/check-out dates
- Make payments via card
- View and manage your bookings
- Submit reviews with star ratings and photos
- Update profile or delete account

### 🏢 As a Hotel Owner
- Add and manage your hotel properties
- Create rooms with types (Standard → Presidential)
- Set room availability
- View bookings made by tourists
- Monitor and respond to guest reviews
- Manage your profile settings

### 🛡️ As an Admin
- Access the centralized admin dashboard
- View system-wide statistics (total hotels, rooms, bookings, etc.)
- Monitor all bookings and payments
- Oversee reviews and user activities

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgements

- [Bootstrap 5](https://getbootstrap.com/) — Frontend framework
- [Font Awesome](https://fontawesome.com/) — Icon library
- [Google Fonts](https://fonts.google.com/) — Poppins font family
- [SLF4J](https://www.slf4j.org/) + [Logback](https://logback.qos.ch/) — Logging framework
- [Apache Maven](https://maven.apache.org/) — Build automation
- [Microsoft SQL Server](https://www.microsoft.com/en-us/sql-server) — Database engine

---

<div align="center">

**⭐ Star this repo if you found it helpful!**

Made with ❤️ for the HotelSync project

</div>
