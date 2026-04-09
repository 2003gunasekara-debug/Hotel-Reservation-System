<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
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

    h1 {
      color: #2d3748;
      text-align: center;
      text-transform: uppercase;
      letter-spacing: 2px;
      margin-bottom: 30px;
    }

    h2 {
      color: #2d3748;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 2px;
    }

    table {
      width: 100%;
      margin: 20px 0;
      border-collapse: collapse;
      background: rgba(255, 255, 255, 0.9);
      border-radius: 10px;
      overflow: hidden;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 12px;
      text-align: left;
      color: #2d3748;
    }

    th {
      background-color: #3182ce;
      color: #fff;
      text-transform: uppercase;
      letter-spacing: 1px;
    }

    tr:nth-child(even) {
      background-color: #f7fafc;
    }

    tr:hover {
      background-color: #e2e8f0;
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

    .alert {
      border-radius: 10px;
      margin-bottom: 20px;
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
    <a class="navbar-brand" href="${pageContext.request.contextPath}/AdminDashboardServlet">Admin Dashboard</a>
    <div class="navbar-nav ms-auto">
      <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp" class="btn btn-danger"><i class="fas fa-sign-out-alt"></i> Logout</a>
    </div>
  </div>
</nav>

<div class="container">
  <div class="welcome-message">
    <h2>Welcome, Admin!</h2>
    <p>Manage all users and system settings</p>
  </div>

  <h2>Admins</h2>
  <table>
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Full Name</th>
      <th>Email</th>
      <th>Created Date</th>
    </tr>
    <c:forEach var="admin" items="${admins}">
      <tr>
        <td>${admin.id}</td>
        <td>${admin.username}</td>
        <td>${admin.fullName}</td>
        <td>${admin.email}</td>
        <td>${admin.createdDate}</td>
      </tr>
    </c:forEach>
  </table>

  <h2>Owners</h2>
  <table>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Phone</th>
    </tr>
    <c:forEach var="owner" items="${owners}">
      <tr>
        <td>${owner.id}</td>
        <td>${owner.name}</td>
        <td>${owner.email}</td>
        <td>${owner.phone}</td>
      </tr>
    </c:forEach>
  </table>

  <h2>Tourists</h2>
  <table>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
    </tr>
    <c:forEach var="tourist" items="${tourists}">
      <tr>
        <td>${tourist.id}</td>
        <td>${tourist.name}</td>
        <td>${tourist.email}</td>
      </tr>
    </c:forEach>
  </table>
</div>

<footer class="footer">
  <p>&copy; 2025 HotelSync. All rights reserved. |
    <a href="privacy.jsp">Privacy Policy</a> |
    <a href="contact.jsp">Contact Us</a>
  </p>
</footer>
</body>
</html>