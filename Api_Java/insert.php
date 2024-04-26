<?php
// Retrieve the JSON data sent from the Java client
$json = file_get_contents('php://input');
$data = json_decode($json);
include("init.php");

// Prepare and bind the INSERT statement
$stmt = $conn->prepare("INSERT INTO casier (date, prix) VALUES (CURRENT_DATE(), ?)");
$stmt->bind_param("f", $total); // 'f' indicates a float parameter

// Set parameters and execute
$total = $data->total;
$stmt->execute();

// Check if the insertion was successful
if ($stmt->affected_rows > 0) {
    echo json_encode(["success" => true]);
} else {
    echo json_encode(["success" => false, "error" => "Failed to insert data"]);
}

// Close statement and connection
$stmt->close();
$conn->close();
?>
