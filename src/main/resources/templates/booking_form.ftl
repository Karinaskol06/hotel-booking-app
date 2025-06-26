<!DOCTYPE html>
<html>
<head>
    <title>Booking Form</title>
</head>
<body>
<h1>Composite Booking Form</h1>
<form method="post" action="/composite-booking/submit">
    Check-in: <input type="date" name="checkIn" required><br>
    Check-out: <input type="date" name="checkOut" required><br>
    Apartment IDs (через кому): <input type="text" name="apartments" required><br>
    Package Type:
    <select name="type">
        <option value="family">Family</option>
        <option value="custom">Custom</option>
    </select><br>
    Package Name (для custom): <input type="text" name="packageName"><br>
    Discount (для custom): <input type="number" step="0.01" name="discount"><br>
    <button type="submit">Submit</button>
</form>
</body>
</html>
