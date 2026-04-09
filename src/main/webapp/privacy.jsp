<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Privacy Policy | HotelSync</title>
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
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 35px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            animation: fadeIn 1s ease-in-out;
            text-align: center;
        }

        h2, h3 {
            color: #2d3748;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        h5 {
            color: #2d3748;
            font-weight: 500;
            margin-top: 20px;
        }

        p, ul {
            color: #4a5568;
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
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="touristDashboard.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link active" href="privacy.jsp">Privacy Policy</a></li>
                <li class="nav-item"><a class="nav-link" href="contact.jsp">Contact</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h3>Privacy Policy</h3>
    </div>

    <p>At <strong>Hotel Reservation System for Tourists</strong>, we respect your privacy and are committed to protecting your personal information. This policy explains how we handle and safeguard your data.</p>

    <h5>1. Information We Collect</h5>
    <ul>
        <li>Personal details such as name, email, and contact number during registration.</li>
        <li>Hotel booking and payment details.</li>
        <li>Optional feedback or reviews submitted by users.</li>
    </ul>

    <h5>2. How We Use Your Information</h5>
    <ul>
        <li>To process bookings and payments securely.</li>
        <li>To personalize your experience within the system.</li>
        <li>To improve system functionality and user satisfaction.</li>
    </ul>

    <h5>3. Data Protection</h5>
    <p>We use modern security practices to protect user data from unauthorized access, disclosure, or alteration. Sensitive data such as passwords and payment details are encrypted.</p>

    <h5>4. Sharing Information</h5>
    <p>We do not share your personal data with third parties except when required by law or to fulfill booking services.</p>

    <h5>5. Policy Updates</h5>
    <p>This privacy policy may be updated periodically. Any changes will be reflected on this page.</p>

    <h5>6. Contact</h5>
    <p>If you have questions about our privacy practices, please visit our <a href="contact.jsp">Contact Page</a>.</p>

    <p class="text-muted mt-4 text-center">© 2025 HotelSync</p>
</div>

<footer class="footer">
    <p>&copy; 2025 HotelSync. All rights reserved. |
        <a href="privacy.jsp">Privacy Policy</a> |
        <a href="contact.jsp">Contact Us</a>
    </p>
</footer>
</body>
</html>