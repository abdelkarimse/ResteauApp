<?php
include("init.php");

$action = $_POST['action'];

switch ($action) {
    case 'updateProduct':
        updateProduct($_POST);
        break;
    case 'removeProduct':
        removeProduct($_POST['id']);
        break;
    default:
        echo "Invalid action";
}

function updateProduct($postData) {
    global $conn;

    $id = $postData['id'];
    $name = $postData['Name'];
    $price = $postData['Price'];
    $remise = $postData['Remise'];
    $category = $postData['Category'];
    $description = $postData['Description'];

    // Prepare and execute SQL update statement
    $sql = "UPDATE products SET Name = ?, Price = ?, Remise = ?, Category = ?, Description = ? WHERE id = ?";
    $statement = $conn->prepare($sql);
    $statement->bind_param("sddssi", $name, $price, $remise, $category, $description, $id);
    $result = $statement->execute();

    if ($result) {
        echo "Product updated successfully";
    } else {
        echo "Error updating product: " . $conn->error;
    }
}
function removeProduct($id) {
    global $conn;

    // Prepare and execute SQL delete statement
    $sql = "DELETE FROM products WHERE id = ?";
    $statement = $conn->prepare($sql);
    $statement->bind_param("i", $id);
    $result = $statement->execute();

    // Process result if needed
    if ($result) {
        echo "Product removed successfully";
    } else {
        echo "Error removing product: " . $conn->error;
    }
}
?>
