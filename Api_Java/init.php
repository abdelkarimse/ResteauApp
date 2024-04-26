<?php
// Database connection parameters
$servername = "localhost";
$username = "Your_username";
$password = "Yourpassword"; 
$database = "Yourdatabase";  

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


?>
