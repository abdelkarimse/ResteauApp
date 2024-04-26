<?php
include("init.php");

// Function to execute and display products based on command ID
function executeAndDisplayProducts($commandId) {
    global $conn;

    $productList = array();
    // Prepare the query to fetch products based on command ID
    $query = "SELECT p.id, p.Name, p.Price 
    FROM products p, command pc
    WHERE p.id = pc.idP
    AND pc.id= ?
";
    $statement = $conn->prepare($query);
    $statement->bind_param("i", $commandId);
    $statement->execute();
    $result = $statement->get_result();

    while ($row = $result->fetch_assoc()) {
        // Create product object
        $product = array(
            "id" => $row["id"],
            "Name" => $row["Name"],
            "Price" => $row["Price"]
        );
        $productList[] = $product;
    }

    echo json_encode($productList);

    // Close statement
    $statement->close();
    // Close connection
    $conn->close();
}

// Check if the command ID is passed in the URL parameter
if(isset($_GET['idcommand'])) {
    $commandId = $_GET['idcommand'];
    // Call the function to execute and display products based on command ID
    executeAndDisplayProducts($commandId);
} else {
    // If the command ID parameter is not provided, return an error message
    http_response_code(400);
    echo "Missing parameter 'commandId'";
}
?>
