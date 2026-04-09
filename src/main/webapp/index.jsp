<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.HotelReservation.model.Owner" %>
<%@ page import="com.HotelReservation.model.Hotel" %>
<%@ page import="com.HotelReservation.dao.HotelDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%
    Owner owner = (Owner) session.getAttribute("owner");
    if (owner == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Logger logger = LoggerFactory.getLogger("index.jsp");
    logger.info("Loading index.jsp, session ID: {}", session.getId());
    logger.info("Owner logged in: {} (ID: {})", owner.getName(), owner.getId());

    Integer ownerId = owner.getId();
    String ownerName = owner.getName();

    try {
        List<Hotel> hotels = new HotelDAO().getHotelsByOwner(ownerId);
        request.setAttribute("hotels", hotels);
        logger.info("Fetched {} hotels for ownerId: {}", hotels.size(), ownerId);
    } catch (Exception e) {
        logger.error("Error fetching hotels for ownerId: {}", ownerId, e);
        request.setAttribute("error", "Error loading hotels: " + e.getMessage());
    }
%>
<html>
<head>
    <title>Owner Dashboard - HotelSync</title>
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

        h2, h4 {
            color: #2d3748;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 2px;
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

        .btn-primary, .btn-outline-primary, .btn-warning, .btn-danger, .btn-info, .btn-success, .btn-outline-success, .btn-outline-info {
            border-radius: 25px;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        .btn-primary:hover, .btn-outline-primary:hover, .btn-warning:hover, .btn-danger:hover, .btn-info:hover, .btn-success:hover, .btn-outline-success:hover, .btn-outline-info:hover {
            transform: scale(1.05);
        }

        .btn-primary {
            background-color: #3182ce;
            border: none;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
        }

        .btn-warning {
            background-color: #d69e2e;
            border: none;
        }

        .btn-warning:hover {
            background-color: #b7791f;
        }

        .btn-danger {
            background-color: #dc3545;
            border: none;
        }

        .btn-danger:hover {
            background-color: #c53030;
        }

        .btn-info {
            background-color: #17a2b8;
            border: none;
        }

        .btn-info:hover {
            background-color: #138496;
        }

        .btn-success {
            background-color: #28a745;
            border: none;
        }

        .btn-success:hover {
            background-color: #218838;
        }

        .btn-outline-primary {
            color: #3182ce;
            border-color: #3182ce;
        }

        .btn-outline-primary:hover {
            background-color: #3182ce;
            color: #fff;
        }

        .btn-outline-secondary {
            color: #6c757d;
            border-color: #6c757d;
        }

        .btn-outline-secondary:hover {
            background-color: #6c757d;
            color: #fff;
        }

        .btn-outline-success {
            color: #28a745;
            border-color: #28a745;
        }

        .btn-outline-success:hover {
            background-color: #28a745;
            color: #fff;
        }

        .btn-outline-info {
            color: #17a2b8;
            border-color: #17a2b8;
        }

        .btn-outline-info:hover {
            background-color: #17a2b8;
            color: #fff;
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
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">HotelSync</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerProfile.jsp"><i class="fas fa-user"></i> My Profile</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerSignup.jsp"><i class="fas fa-user-plus"></i> Add New Owner</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerLogout"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Welcome, ${owner.name}!</h2>
        <p>Manage your hotels and bookings</p>
    </div>

    <h4>Your Hotels</h4>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>
    <a href="hotel_owner/addHotel.jsp" class="btn btn-primary mb-3"><i class="fas fa-plus"></i> Add New Hotel</a>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:choose>
            <c:when test="${empty hotels}">
                <div class="col-12 text-center">
                    <p class="text-muted">No hotels found. Add a new hotel to get started.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="hotel" items="${hotels}">
                    <div class="col">
                        <div class="hotel-card card h-100">
                            <img src="${pageContext.request.contextPath}/${hotel.imagePath}"
                                 alt="${hotel.name} Image"
                                 class="card-img-top"
                                 onerror="this.src='${pageContext.request.contextPath}/Uploads/default.jpg';">
                            <div class="card-body">
                                <h5 class="card-title">ID: ${hotel.id} - ${hotel.name}</h5>
                                <p class="card-text text-muted">Location: ${hotel.location}</p>
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
                                <button class="btn btn-sm details-btn" type="button" data-bs-toggle="collapse" data-bs-target="#details-${hotel.id}" aria-expanded="false" aria-controls="details-${hotel.id}">
                                    <i class="fas fa-chevron-down"></i> Details
                                </button>
                                <div class="d-flex gap-2 flex-wrap mt-2">
                                    <a href="hotel_owner/editHotel.jsp?id=${hotel.id}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i> Edit</a>
                                    <a href="DeleteHotelServlet?id=${hotel.id}" class="btn btn-sm btn-danger" onclick="return confirm('Delete this hotel?');"><i class="fas fa-trash-alt"></i> Delete</a>
                                    <a href="${pageContext.request.contextPath}/ListRoomsServlet?hotelId=${hotel.id}" class="btn btn-sm btn-info"><i class="fas fa-bed"></i> Rooms</a>
                                    <a href="hotel_owner/bookings.jsp?hotelId=${hotel.id}" class="btn btn-sm btn-success"><i class="fas fa-book"></i> Bookings</a>
                                    <a href="ViewReviewsServlet" class="btn btn-sm btn-outline-info"><i class="fas fa-comments"></i> Reviews</a>
                                </div>
                            </div>
                            <div class="collapse collapse-details" id="details-${hotel.id}">
                                <div class="hotel-details">
                                    <h6>Description</h6>
                                    <p>${hotel.description}</p>
                                    <h6>Amenities</h6>
                                    <p>${hotel.amenities}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="mt-5">
        <h4>Quick Actions</h4>
        <div class="d-flex gap-2 flex-wrap">
            <a href="${pageContext.request.contextPath}/hotel_owner/addHotel.jsp" class="btn btn-outline-primary"><i class="fas fa-plus"></i> Add Hotel</a>
            <a href="${pageContext.request.contextPath}/ownerProfile.jsp" class="btn btn-outline-secondary"><i class="fas fa-user"></i> My Profile</a>
            <a href="${pageContext.request.contextPath}/ownerSignup.jsp" class="btn btn-outline-success"><i class="fas fa-user-plus"></i> Add Owner</a>
            <a href="ProfileServlet" class="btn btn-outline-info"><i class="fas fa-cog"></i> Profile Settings</a>
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