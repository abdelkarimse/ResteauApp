<?php
include("init.php");

// Fetch data from database
$sql = "SELECT produit, quantite, quantitekg, prix FROM stock";
$result = $conn->query($sql);

// Prepare response data
$data = array();
if ($result->num_rows > 0) {
    // Output data of each row
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }
} else {
    $data["message"] = "No results found";
}

// Return JSON response
header('Content-Type: application/json');
echo json_encode($data);

// Close connection
$conn->close();
?>
