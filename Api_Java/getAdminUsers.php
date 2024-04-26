<?php
include("init.php");
  $adminUsers = array();

    // Fetch admin users from database
    $query = "SELECT username, password, role, id FROM users WHERE role = 'admin'";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        // Output data of each row
        while ($row = $result->fetch_assoc()) {
            $adminUsers[] = $row;
        }
    }

    // Return admin users as JSON
    echo json_encode($adminUsers);



?>