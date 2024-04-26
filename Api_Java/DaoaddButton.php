<?php
// Include the init.php file to establish the database connection
include("init.php");

// Prepare SQL statement
$sql = "INSERT INTO products (Name, Price, Remise, Image, description, category) VALUES (?, ?, ?, ?, ?, ?)";
$statement = $conn->prepare($sql);

// Bind parameters
$statement->bind_param("sddsss", $name, $price, $discount, $imagePath, $description, $category);

// Set parameters
$name = $_POST['name'];
$price = $_POST['price'];
$discount = $_POST['discount'];
$imagePath = $_POST['imagePath'];
$description = $_POST['description'];
$category = $_POST['category'];

// Execute statement and return true or false
if ($statement->execute()) {
    $statement->close(); // Close statement
    $conn->close(); // Close connection
    return true; // Return true upon successful insertion
} else {
    $statement->close(); // Close statement
    $conn->close(); // Close connection
    return false; // Return false upon insertion failure
}
?>
