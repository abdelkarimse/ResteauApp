<?php
include("init.php");

// Function to execute and display products
function ExecutdisplayProd($pageSize) {
    global $conn;

    $productList = array();
    $query = "SELECT id, Name, Price, Image, Remise, description, category FROM products LIMIT ?";
    $statement = $conn->prepare($query);
    $statement->bind_param("i", $pageSize);
    $statement->execute();
    $result = $statement->get_result();

    while ($row = $result->fetch_assoc()) {
        $id = $row["id"];
        $name = $row["Name"];
        $price = $row["Price"];
        $remise = $row["Remise"];
        $image = $row["Image"];
        $description = $row["description"];
        $category = $row["category"];

        // Process image if needed
        // ...

        // Create product object
        $product = array(
            "id" => $id,
            "name" => $name,
            "price" => $price,
            "remise" => $remise,
            "description" => $description,
            "category" => $category,
            "image" => $image
        );

        // Add product to list
        $productList[] = $product;
    }

    return $productList;
}

// Example usage
$pageSize = 10; // Define your page size here
$productList = ExecutdisplayProd($pageSize);

// Output the product list as JSON
echo json_encode($productList);

// Close connection
$conn->close();
?>
