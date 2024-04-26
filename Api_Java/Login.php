<?php
// Database connection
include("init.php");

// Get username and password from GET parameters
$username = $_GET['username'] ?? '';
$password = $_GET['password'] ?? '';

// Prepare and bind parameters
$stmt = $conn->prepare("SELECT role FROM users WHERE username = ? AND password = ?");
$stmt->bind_param("ss", $username, $password);

// Execute query
$stmt->execute();

// Get result
$result = $stmt->get_result();

// Fetch row
$row = $result->fetch_assoc();

// Check if row exists
if ($row) {
    echo $row["role"]; // Return user role
} else {
    echo "null"; // No matching user found
}

// Close statement and connection
$stmt->close();
$conn->close();
?>
