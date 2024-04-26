<?php
include("init.php");
$dataList = array();
$affQuery = "SELECT prix, date FROM casier";
$affResultSet = $conn->query($affQuery);

if ($affResultSet->num_rows > 0) {
    // Output data of each row
    while ($row = $affResultSet->fetch_assoc()) {
        $prix = $row["prix"];
        $date = $row["date"];
        $dataList[] = $prix . "," . $date;
    }
}

$conn->close();

// Send data to Java as a response
echo implode(";", $dataList);
?>
