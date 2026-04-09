<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Room - HotelSync</title>
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

        .form-control, .form-select {
            border-radius: 10px;
        }

        .btn-primary, .btn-secondary {
            border-radius: 25px;
            padding: 10px 20px;
            transition: all 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
            transform: scale(1.05);
        }

        .btn-secondary:hover {
            background-color: #4a5568;
            transform: scale(1.05);
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

        .is-invalid {
            border-color: #dc3545;
        }

        .invalid-feedback {
            display: none;
            color: #dc3545;
            font-size: 0.875rem;
        }

        .is-invalid ~ .invalid-feedback {
            display: block;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">HotelSync</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerProfile.jsp"><i class="fas fa-user"></i> My Profile</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/ownerLogout"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h2>Add New Room</h2>
    </div>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/AddRoomServlet" method="post" enctype="multipart/form-data" id="addRoomForm" novalidate>
        <input type="hidden" name="hotelId" value="${param.hotelId}">
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="roomNumber" class="form-label">Room Number *</label>
                <input type="text" id="roomNumber" name="roomNumber" class="form-control" required>
                <div class="invalid-feedback">Room number is required.</div>
            </div>
            <div class="col-md-6">
                <label for="roomType" class="form-label">Room Type *</label>
                <select id="roomType" name="roomType" class="form-select" required>
                    <option value="">Select Room Type</option>
                    <option value="Standard">Standard</option>
                    <option value="Deluxe">Deluxe</option>
                    <option value="Suite">Suite</option>
                    <option value="Executive">Executive</option>
                    <option value="Family">Family</option>
                    <option value="Presidential">Presidential</option>
                </select>
                <div class="invalid-feedback">Room type is required.</div>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="price" class="form-label">Price ($) *</label>
                <input type="number" id="price" name="price" class="form-control" placeholder="0.00" step="0.01" min="0" required>
                <div class="invalid-feedback">Price must be greater than 0.</div>
            </div>
            <div class="col-md-6">
                <label for="image" class="form-label">Room Image</label>
                <input type="file" id="image" name="image" class="form-control" accept="image/*">
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-12">
                <label for="amenities" class="form-label">Amenities (Beds)</label>
                <input type="text" id="amenities" name="amenities" class="form-control" placeholder="e.g., How many beds">
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-md-6">
                <button type="submit" class="btn btn-primary w-100"><i class="fas fa-save"></i> Add Room</button>
            </div>
            <div class="col-md-6">
                <a href="${pageContext.request.contextPath}/ListRoomsServlet?hotelId=${param.hotelId}" class="btn btn-secondary w-100"><i class="fas fa-times"></i> Cancel</a>
            </div>
        </div>
    </form>
</div>

<footer class="footer">
    <p>&copy; 2025 HotelSync. All rights reserved. |
        <a href="../privacy.jsp">Privacy Policy</a> |
        <a href="../contact.jsp">Contact Us</a>
    </p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('addRoomForm').addEventListener('submit', function(event) {
        event.preventDefault();
        let isValid = true;

        // Room Number validation (non-empty)
        const roomNumber = document.getElementById('roomNumber');
        if (!roomNumber.value.trim()) {
            roomNumber.classList.add('is-invalid');
            isValid = false;
        } else {
            roomNumber.classList.remove('is-invalid');
        }

        // Room Type validation (non-empty)
        const roomType = document.getElementById('roomType');
        if (!roomType.value) {
            roomType.classList.add('is-invalid');
            isValid = false;
        } else {
            roomType.classList.remove('is-invalid');
        }

        // Price validation (greater than 0)
        const price = document.getElementById('price');
        if (!price.value || parseFloat(price.value) <= 0) {
            price.classList.add('is-invalid');
            isValid = false;
        } else {
            price.classList.remove('is-invalid');
        }

        if (isValid) {
            this.submit();
        }
    });
</script>
</body>
</html>