<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>My Reviews - HotelSync</title>
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
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .review-card {
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            height: 100%;
        }

        .review-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        .review-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 10px 10px 0 0;
        }

        .card-body {
            padding: 15px;
        }

        .rating {
            color: #f6e05e;
            margin-bottom: 10px;
        }

        .fa-star {
            font-size: 1.2rem;
        }

        .fas {
            color: #f6e05e;
        }

        .far {
            color: #e2e8f0;
        }

        .alert {
            border-radius: 10px;
            margin-bottom: 20px;
        }

        .btn-danger {
            border-radius: 25px;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        .btn-danger:hover {
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

        @keyframes slideIn {
            0% { opacity: 0; transform: translateY(50px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        .back-button {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/TouristDashboardServlet">HotelSync</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="${pageContext.request.contextPath}/ProfileServlet"><i class="fas fa-user"></i> My Profile</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>My Reviews</h2>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty reviews}">
            <p class="text-muted">You have not submitted any reviews yet.</p>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="r" items="${reviews}">
                    <div class="col">
                        <div class="review-card card h-100">
                            <c:set var="defaultPath" value="${r.imagePath != null && !r.imagePath.isEmpty() ? r.imagePath : 'Uploads/reviews/default.jpg'}" />
                            <img src="${pageContext.request.contextPath}/${defaultPath}?t=${System.currentTimeMillis()}"
                                 alt="${r.hotelName} Review Image"
                                 class="card-img-top"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/Uploads/reviews/default.jpg?t=${System.currentTimeMillis()}'; console.log('Image load failed for: ${pageContext.request.contextPath}/${defaultPath}');"
                                 loading="lazy"
                            />
                            <div class="card-body">
                                <h5 class="card-title">Hotel: <c:out value="${r.hotelName != null ? r.hotelName : 'Hotel ID ' + r.hotelId}" /></h5>
                                <div class="rating">
                                    <c:forEach begin="1" end="5" var="i">
                                        <i class="fa-star ${i <= r.rating ? 'fas' : 'far'}"></i>
                                    </c:forEach>
                                </div>
                                <p class="card-text">${fn:escapeXml(r.description)}</p>
                                <p class="card-text"><small class="text-muted">Submitted on: ${r.createdAt}</small></p>
                                <a href="DeleteReviewServlet?reviewId=${r.id}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Are you sure you want to delete this review?')">
                                    <i class="fas fa-trash"></i> Delete
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="back-button">
        <a href="${pageContext.request.contextPath}/TouristDashboardServlet" class="btn btn-primary">Back to Dashboard</a>
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