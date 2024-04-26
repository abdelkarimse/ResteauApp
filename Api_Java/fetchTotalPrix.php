<?php
include("init.php"); // Assuming init.php contains your database connection code

// Check if the connection is successful
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Fetch total price
$sumQuery = "SELECT SUM(prix) AS totalPrix FROM casier";
$sumResultSet = $conn->query($sumQuery);
$totalPrix = 0;

if ($sumResultSet) {
    if ($sumResultSet->num_rows > 0) {
        while ($row = $sumResultSet->fetch_assoc()) {
            $totalPrix = $row["totalPrix"];
        }
    }
    $sumResultSet->close();
} else {
    echo "Error executing query: " . $conn->error;
}

$conn->close();
echo $totalPrix;
?>
