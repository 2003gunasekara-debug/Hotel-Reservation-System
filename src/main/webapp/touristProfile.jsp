<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tourist Profile - HotelSync</title>
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
            max-width: 800px;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            padding: 40px;
            animation: slideIn 0.8s ease-out;
        }

        .welcome-message {
            background: linear-gradient(90deg, #f6e05e, #d69e2e);
            color: #1a202c;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        h2 {
            color: #2d3748;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .form-label {
            font-weight: 500;
            color: #2d3748;
        }

        .form-control {
            border-radius: 10px;
        }

        .btn-primary, .btn-danger, .btn-secondary {
            border-radius: 25px;
            padding: 10px 20px;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
            transform: scale(1.05);
        }

        .btn-danger:hover {
            background-color: #c53030;
            transform: scale(1.05);
        }

        .btn-secondary:hover {
            background-color: #4a5568;
            transform: scale(1.05);
        }

        .alert {
            border-radius: 10px;
            margin-bottom: 20px;
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

        @keyframes slideIn {
            0% { opacity: 0; transform: translateY(50px); }
            100% { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/touristDashboard.jsp">HotelSync</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="${pageContext.request.contextPath}/TouristDashboardServlet"><i class="fas fa-user"></i> Dashboard</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Tourist Profile</h2>
    </div>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>

    <c:if test="${not empty tourist}">
        <form action="${pageContext.request.contextPath}/UpdateTouristProfileServlet" method="post">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">Name *</label>
                    <input type="text" id="name" name="name" class="form-control" value="${tourist.name}" required>
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Email *</label>
                    <input type="email" id="email" name="email" class="form-control" value="${tourist.email}" required>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="password" class="form-label">Password (leave blank to keep unchanged)</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-save"></i> Update Profile</button>
                </div>
            </div>
        </form>
    </c:if>

    <div class="col-md-6">
        <form action="${pageContext.request.contextPath}/DeleteTouristAccountServlet" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
            <button type="submit" class="btn btn-danger w-100"><i class="fas fa-trash"></i> Delete Account</button>
        </form>
    </div>

    <c:if test="${empty tourist}">
        <div class="alert alert-danger">
            Tourist profile not found.
        </div>
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back to Login</a>
    </c:if>
</div>

<footer class="footer">
    <p>&copy; 2025 HotelSync. All rights reserved. |
        <a href="privacy.jsp">Privacy Policy</a> |
        <a href="contact.jsp">Contact Us</a>
    </p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>