<?php
// Include init.php for database connection setup
include 'init.php';

// Retrieve POST data
$id = $_POST['id'];
$newName = $_POST['newName'];
$newPassword = $_POST['newPassword'];

// Prepare update statement
$sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ssi", $newName, $newPassword, $id);

// Execute update statement
if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}

$stmt->close();
$conn->close();
?>
