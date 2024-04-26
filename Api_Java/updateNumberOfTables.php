<?php
include("init.php");
$newNumberOfTables = $_GET['newNumberOfTables'];

// Update number of tables
$updateQuery = "UPDATE tables SET numerotab = ? WHERE idTabel = 1";
$updateStatement = $conn->prepare($updateQuery);
$updateStatement->bind_param("i", $newNumberOfTables);
$updateStatement->execute();
$rowsAffected = $updateStatement->affected_rows;

$conn->close();
echo $rowsAffected;
?>
