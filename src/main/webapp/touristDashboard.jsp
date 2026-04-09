<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Tourist Dashboard - HotelSync</title>
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
            max-width: 1200px;
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

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background: linear-gradient(90deg, #3182ce, #2b6cb0);
            color: #fff;
            border-radius: 10px 10px 0 0;
        }

        .hotel-card {
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            height: 100%;
        }

        .hotel-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        .hotel-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 10px 10px 0 0;
        }

        .card-body {
            padding: 15px;
        }

        .rating {
            color: #ffd700;
            font-size: 1.2rem;
            margin-top: 5px;
        }

        .rating .fa-star {
            color: #ffd700;
        }

        .rating .fa-star-o {
            color: #ccc;
        }

        .btn-primary {
            background-color: #3182ce;
            border: none;
            border-radius: 25px;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
            transform: scale(1.05);
        }

        .details-btn {
            color: #6c757d;
            border-color: #6c757d;
            border-radius: 25px;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        .details-btn:hover {
            background-color: #6c757d;
            color: #fff;
            transform: scale(1.05);
        }

        .collapse-details {
            transition: height 0.3s ease;
        }

        .hotel-details {
            padding: 15px;
            background: #f8f9fa;
            border-top: 1px solid #e9ecef;
            border-radius: 0 0 10px 10px;
        }

        .hotel-details h6 {
            color: #2d3748;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .hotel-details p {
            margin-bottom: 5px;
            font-size: 0.9rem;
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

        .search-form {
            margin-bottom: 20px;
        }

        .search-form .input-group {
            max-width: 500px;
            margin: 0 auto;
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
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="TouristProfileServlet"><i class="fas fa-user"></i> My Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ViewBookingsServlet"><i class="fas fa-book"></i> My Bookings</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="MyReviewsServlet"><i class="fas fa-star"></i> My Reviews</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Welcome, <%= session.getAttribute("touristName") %>!</h2>
        <p>Explore hotels and manage your bookings and reviews</p>
    </div>

    <div class="d-flex gap-3 mb-4">
        <a href="MyReviewsServlet" class="btn btn-outline-primary"><i class="fas fa-star"></i> My Reviews</a>
        <a href="ViewBookingsServlet" class="btn btn-outline-success"><i class="fas fa-book"></i> My Bookings</a>
        <a href="ViewPaymentsServlet" class="btn btn-outline-info"><i class="fas fa-credit-card"></i> Payment History</a>
    </div>

    <!-- Search Form -->
    <div class="search-form">
        <form action="TouristDashboardServlet" method="get" class="input-group">
            <input type="text" name="searchQuery" class="form-control" placeholder="Search hotels by name..." value="${fn:escapeXml(searchQuery)}">
            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
            <c:if test="${not empty searchQuery}">
                <a href="TouristDashboardServlet" class="btn btn-outline-secondary ms-2"><i class="fas fa-times"></i> Clear</a>
            </c:if>
        </form>
    </div>

    <!-- Available Hotels Section -->
    <div class="card">
        <div class="card-header">
            <h3 class="mb-0">Available Hotels</h3>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty hotels}">
                    <p class="text-muted">No hotels available at the moment<c:if test="${not empty searchQuery}"> matching "${fn:escapeXml(searchQuery)}"</c:if>.</p>
                </c:when>
                <c:otherwise>
                    <div class="row row-cols-1 row-cols-md-3 g-4">
                        <c:forEach var="h" items="${hotels}">
                            <div class="col">
                                <div class="hotel-card card h-100">
                                    <img src="${pageContext.request.contextPath}/${h.imagePath}"
                                         alt="${h.name} Image"
                                         class="card-img-top"
                                         onerror="this.src='${pageContext.request.contextPath}/Uploads/default.jpg';">
                                    <div class="card-body">
                                        <h5 class="card-title">${h.name}</h5>
                                        <p class="card-text text-muted">Location: ${h.location}</p>
                                        <div class="rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= 5}">
                                                        <i class="fas fa-star"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="far fa-star"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                        <button class="btn btn-sm details-btn" type="button" data-bs-toggle="collapse" data-bs-target="#details-${h.id}" aria-expanded="false" aria-controls="details-${h.id}">
                                            <i class="fas fa-chevron-down"></i> Details
                                        </button>
                                        <div class="mt-auto mt-2">
                                            <a href="ViewHotelRoomsServlet?hotelId=${h.id}" class="btn btn-primary"><i class="fas fa-hotel"></i> View Rooms & Book</a>
                                            <a href="ViewReviewsServlet?hotelId=${h.id}" class="btn btn-sm btn-outline-info"><i class="fas fa-comments"></i> Reviews</a>
                                        </div>
                                    </div>
                                    <div class="collapse collapse-details" id="details-${h.id}">
                                        <div class="hotel-details">
                                            <h6>Description</h6>
                                            <p>${h.description}</p>
                                            <h6>Amenities</h6>
                                            <p>${h.amenities}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
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