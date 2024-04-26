<?php
include("init.php")

  $username = $data['username'];
    $password = $data['password'];
    $role = $data['role'];

    // Insert new user into database
    $query = "INSERT INTO users (username, password, role) VALUES ('$username', '$password', '$role')";
    if ($conn->query($query) === TRUE) {
        echo "User added successfully";
    } else {
        echo "Error adding user: " . $conn->error;
    }

// Close connection
$conn->close();


?>