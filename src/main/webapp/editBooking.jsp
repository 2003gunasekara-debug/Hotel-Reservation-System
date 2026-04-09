<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Booking - HotelSync</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: url('https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80') no-repeat center center fixed;
            background-size: cover;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            padding-top: 60px;
            padding-bottom: 60px;
            position: relative;
        }

        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: -1;
        }

        .navbar {
            background: linear-gradient(90deg, #2a4365, #1a202c);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            padding: 8px 0;
        }

        .navbar-brand {
            font-size: 1.5rem;
            font-weight: 700;
            color: #f7fafc !important;
            letter-spacing: 1px;
            padding-left: 20px;
        }

        .container {
            max-width: 600px;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            padding: 40px;
            animation: slideIn 0.8s ease-out;
        }

        .welcome-message {
            background: linear-gradient(90deg, #f6e05e, #d69e2e);
            color: #1a202c;
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 35px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            animation: fadeIn 1s ease-in-out;
            text-align: center;
        }

        h2 {
            color: #2d3748;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            color: #2d3748;
            font-weight: 500;
        }

        .form-control, .form-select {
            border-radius: 10px;
            border: 1px solid #e2e8f0;
            transition: border-color 0.3s ease;
        }

        .form-control:focus, .form-select:focus {
            border-color: #3182ce;
            box-shadow: 0 0 5px rgba(49, 130, 206, 0.3);
        }

        .btn-primary {
            background-color: #3182ce;
            border: none;
            border-radius: 25px;
            padding: 10px 20px;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
            transform: scale(1.05);
        }

        .btn-secondary {
            background-color: #4a5568;
            border: none;
            border-radius: 25px;
            padding: 10px 20px;
            transition: all 0.3s ease;
        }

        .btn-secondary:hover {
            background-color: #2d3748;
            transform: scale(1.05);
        }

        .footer {
            background: linear-gradient(90deg, #2a4365, #1a202c);
            color: #f7fafc;
            text-align: center;
            padding: 10px 0;
            position: fixed;
            bottom: 0;
            width: 100%;
            box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.2);
            font-size: 0.9rem;
            z-index: 1000;
        }

        .footer a {
            color: #f6e05e;
            text-decoration: none;
            margin: 0 10px;
            transition: color 0.3s ease;
        }

        .footer a:hover {
            color: #d69e2e;
        }

        @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(-30px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        @keyframes slideIn {
            0% { opacity: 0; transform: translateY(50px); }
            100% { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="touristDashboard.jsp">HotelSync</a>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Edit Booking</h2>
        <p>Update your booking details</p>
    </div>

    <form action="EditBookingServlet" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class="form-label">Check-in Date</label>
            <input type="date" name="checkIn" class="form-control" required>
        </div>
        <div class="form-group">
            <label class="form-label">Check-out Date</label>
            <input type="date" name="checkOut" class="form-control" required>
        </div>
        <div class="form-group">
            <label class="form-label">Number of Guests</label>
            <input type="number" name="guests" min="1" class="form-control" required>
        </div>
        <div class="form-group">
            <label class="form-label">Status</label>
            <select name="status" class="form-select">
                <option value="Pending">Pending</option>
                <option value="Confirmed">Confirmed</option>
                <option value="Cancelled">Cancelled</option>
            </select>
        </div>
        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Update Booking</button>
            <a href="TouristDashboardServlet" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back to Dashboard</a>
        </div>
    </form>
</div>

<footer class="footer">
    <p>&copy; 2025 HotelSync. All rights reserved. |
        <a href="privacy.jsp">Privacy Policy</a> |
        <a href="contact.jsp">Contact Us</a>
    </p>
</footer>
</body>
</html>