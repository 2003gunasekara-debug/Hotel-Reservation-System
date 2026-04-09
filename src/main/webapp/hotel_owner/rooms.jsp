<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Manage Rooms - HotelSync</title>
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
            padding: 20px;
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

        .room-card {
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            height: 100%;
        }

        .room-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        .room-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 10px 10px 0 0;
        }

        .card-body {
            padding: 15px;
        }

        .amenities-tags {
            display: flex;
            gap: 5px;
            flex-wrap: wrap;
            margin-top: 5px;
        }

        .amenity-tag {
            background-color: #e2e8f0;
            color: #2d3748;
            padding: 2px 8px;
            border-radius: 5px;
            font-size: 0.9rem;
        }

        .btn-warning, .btn-danger {
            border-radius: 25px;
            padding: 6px 12px;
            transition: all 0.3s ease;
        }

        .btn-warning:hover {
            background-color: #b7791f;
            transform: scale(1.05);
        }

        .btn-danger:hover {
            background-color: #c53030;
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
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">HotelSync</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerProfile.jsp"><i class="fas fa-user"></i> My Profile</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Manage Rooms for Hotel</h2>
    </div>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/hotel_owner/addRoom.jsp?hotelId=${hotelId}" class="btn btn-primary mb-3"><i class="fas fa-plus"></i> Add New Room</a>

    <c:choose>
        <c:when test="${empty rooms}">
            <p class="text-muted">No rooms available for this hotel.</p>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="room" items="${rooms}">
                    <div class="col">
                        <div class="room-card card h-100">
                            <img src="${pageContext.request.contextPath}/${room.imagePath}"
                                 alt="${room.roomNumber} Image"
                                 class="card-img-top"
                                 onerror="this.src='${pageContext.request.contextPath}/uploads/default_room.jpg';">
                            <div class="card-body">
                                <h5 class="card-title">Room ${room.roomNumber}</h5>
                                <p class="card-text">Type: ${room.roomType}</p>
                                <p class="card-text">Price: $${room.price}</p>
                                <div class="amenities-tags">
                                    <c:forEach var="amenity" items="${room.amenityTags}">
                                        <span class="amenity-tag">${amenity}</span>
                                    </c:forEach>
                                </div>
                                <div class="mt-auto">
                                    <a href="${pageContext.request.contextPath}/EditRoomServlet?roomId=${room.id}&hotelId=${room.hotelId}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i> Edit</a>
                                    <a href="${pageContext.request.contextPath}/DeleteRoomServlet?roomId=${room.id}&hotelId=${room.hotelId}" class="btn btn-sm btn-danger" onclick="return confirm('Delete this room?');"><i class="fas fa-trash-alt"></i> Delete</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<footer class="footer">
    <p>&copy; 2025 HotelSync. All rights reserved. |
        <a href="../privacy.jsp">Privacy Policy</a> |
        <a href="../contact.jsp">Contact Us</a>
    </p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>