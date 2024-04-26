<?php
include("init.php");

if(isset($_GET['notificationnum'])) {
    $notificationId = $_GET['notificationnum'];

    // Update aff field to 0 for the notification with the specified IdN
    $updateQuery = "UPDATE notification SET aff = 0 WHERE IdN = ?";
    $updateStatement = $conn->prepare($updateQuery);
    $updateStatement->bind_param("i", $notificationId);
    $updateStatement->execute();
    $rowsAffected = $updateStatement->affected_rows;

    // Delete records with aff=0
    $deleteQuery = "DELETE FROM notification WHERE aff = 0";
    $deleteStatement = $conn->prepare($deleteQuery);
    $deleteStatement->execute();
    $deleteStatement->close();

    // Close the update statement
    $updateStatement->close();

    // Close the database connection
    $conn->close();

    // Return the number of affected rows
    echo $rowsAffected;
} else {
    // If notificationnum parameter is not defined, return an error
    http_response_code(400);
    echo "Missing parameter 'notificationnum'";
}
?>
