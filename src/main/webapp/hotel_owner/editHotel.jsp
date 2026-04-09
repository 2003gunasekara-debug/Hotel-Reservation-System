<%@ page import="com.HotelReservation.model.Hotel,com.HotelReservation.dao.HotelDAO,com.HotelReservation.model.Owner" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Use implicit session object instead of redeclaring
    Owner owner = (Owner) session.getAttribute("owner");
    if (owner == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login+to+edit+hotel");
        return;
    }

    String idStr = request.getParameter("id");
    int id = 0;
    Hotel hotel = null;
    try {
        if (idStr != null && !idStr.trim().isEmpty()) {
            id = Integer.parseInt(idStr);
            hotel = new HotelDAO().getHotelById(id);
            if (hotel == null) {
                request.setAttribute("error", "Hotel not found for ID: " + id);
            } else if (hotel.getOwnerId() != owner.getId()) {
                request.setAttribute("error", "You are not authorized to edit this hotel");
                hotel = null;
            }
        } else {
            request.setAttribute("error", "No hotel ID provided");
        }
    } catch (NumberFormatException e) {
        request.setAttribute("error", "Invalid hotel ID");
    } catch (Exception e) {
        request.setAttribute("error", "Error retrieving hotel: " + e.getMessage());
    }
%>
<html>
<head>
    <title>Edit Hotel - HotelSync</title>
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

        h3 {
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
            box-shadow: 0 0 5px rgba(49, 130, 206, 0.5);
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

        .btn-secondary {
            background-color: #6c757d;
            border: none;
            border-radius: 25px;
            padding: 8px 16px;
            transition: all 0.3s ease;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
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
        <a class="navbar-brand" href="../index.jsp">HotelSync</a>
    </div>
</nav>

<div class="container">
    <div class="welcome-message">
        <h3>Edit Hotel</h3>
        <p>Update details for your hotel</p>
    </div>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <% if (hotel != null) { %>
    <form action="<%= request.getContextPath() %>/EditHotelServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= hotel.getId() %>">
        <input type="hidden" name="existingImage" value="<%= hotel.getImagePath() != null ? hotel.getImagePath() : "" %>">
        <div class="form-group">
            <label class="form-label">Name</label>
            <input type="text" name="name" class="form-control" value="<%= hotel.getName() != null ? hotel.getName() : "" %>" required>
        </div>
        <div class="form-group">
            <label class="form-label">Location</label>
            <input type="text" name="location" class="form-control" value="<%= hotel.getLocation() != null ? hotel.getLocation() : "" %>" required>
        </div>
        <div class="form-group">
            <label class="form-label">Description</label>
            <textarea name="description" class="form-control" rows="4"><%= hotel.getDescription() != null ? hotel.getDescription() : "" %></textarea>
        </div>
        <div class="form-group">
            <label class="form-label">Amenities</label>
            <input type="text" name="amenities" class="form-control" value="<%= hotel.getAmenities() != null ? hotel.getAmenities() : "" %>">
        </div>
        <div class="form-group">
            <label class="form-label">Image (Current: <%= hotel.getImagePath() != null ? hotel.getImagePath() : "None" %>)</label>
            <input type="file" name="image" class="form-control" accept="image/*">
        </div>
        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Update</button>
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary"><i class="fas fa-times"></i> Cancel</a>
        </div>
    </form>
    <% } else { %>
    <div class="alert alert-danger">
        Unable to load hotel details. <a href="<%= request.getContextPath() %>/index.jsp" class="alert-link">Return to Dashboard</a>.
    </div>
    <% } %>
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