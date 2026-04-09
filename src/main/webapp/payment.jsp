<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Complete Payment - HotelSync</title>
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

    .form-control {
      border-radius: 10px;
      border: 1px solid #e2e8f0;
      transition: border-color 0.3s ease;
    }

    .form-control:focus {
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

    .room-info {
      background: #f8fafc;
      border-radius: 10px;
      padding: 15px;
      margin-bottom: 20px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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
    <a class="navbar-brand" href="touristDashboard.jsp">HotelSync</a>
  </div>
</nav>

<div class="container">
  <div class="welcome-message">
    <h2>Complete Payment</h2>
    <p>Securely pay for your booking</p>
  </div>

  <div class="room-info">
    <p><strong>Room Number:</strong> ${room.roomNumber}</p>
    <p><strong>Price:</strong> $${room.price}</p>
  </div>

  <form action="${pageContext.request.contextPath}/PaymentServlet" method="post" id="paymentForm" novalidate>
    <input type="hidden" name="roomId" value="${room.id}">
    <input type="hidden" name="amount" value="${room.price}">
    <div class="form-group">
      <label class="form-label" for="cardNumber">Card Number</label>
      <input type="text" id="cardNumber" name="cardNumber" class="form-control" placeholder="1111 2222 3333 4444" required>
      <div class="invalid-feedback">Card number must be exactly 16 digits.</div>
    </div>
    <div class="form-group">
      <label class="form-label" for="expiry">Expiry Date</label>
      <input type="text" id="expiry" name="expiry" class="form-control" placeholder="MM/YY" required>
      <div class="invalid-feedback">Expiry date must be in MM/YY format, with month 01-12 and year up to 2030.</div>
    </div>
    <div class="form-group">
      <label class="form-label" for="cvv">CVV</label>
      <input type="password" id="cvv" name="cvv" class="form-control" required>
      <div class="invalid-feedback">CVV must be exactly 3 digits.</div>
    </div>
    <button type="submit" class="btn btn-primary"><i class="fas fa-credit-card"></i> Pay</button>
  </form>
</div>

<footer class="footer">
  <p>&copy; 2025 HotelSync. All rights reserved. |
    <a href="privacy.jsp">Privacy Policy</a> |
    <a href="contact.jsp">Contact Us</a>
  </p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  document.getElementById('paymentForm').addEventListener('submit', function(event) {
    event.preventDefault();
    let isValid = true;

    // Card Number validation (exactly 16 digits, with or without spaces)
    const cardNumber = document.getElementById('cardNumber');
    const cardNumberValue = cardNumber.value.replace(/\s/g, ''); // Remove spaces
    const cardNumberRegex = /^\d{16}$/;
    if (!cardNumberRegex.test(cardNumberValue)) {
      cardNumber.classList.add('is-invalid');
      isValid = false;
    } else {
      cardNumber.classList.remove('is-invalid');
    }

    // Expiry Date validation (MM/YY, month 01-12, year <= 2030)
    const expiry = document.getElementById('expiry');
    const expiryRegex = /^(0[1-9]|1[0-2])\/([0-2][0-9]|30)$/;
    const currentYear = new Date().getFullYear() % 100; // Last two digits of current year
    if (!expiryRegex.test(expiry.value) || parseInt(expiry.value.split('/')[1]) > 30) {
      expiry.classList.add('is-invalid');
      isValid = false;
    } else {
      expiry.classList.remove('is-invalid');
    }

    // CVV validation (exactly 3 digits)
    const cvv = document.getElementById('cvv');
    const cvvRegex = /^\d{3}$/;
    if (!cvvRegex.test(cvv.value)) {
      cvv.classList.add('is-invalid');
      isValid = false;
    } else {
      cvv.classList.remove('is-invalid');
    }

    if (isValid) {
      this.submit();
    }
  });
</script>
</body>
</html>