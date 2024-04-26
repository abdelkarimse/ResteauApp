<?php
include("init.php");

// Function to execute and display products
function ExecutdisplayProd() {
    global $conn;

    $productList = array();
    $query = "SELECT * FROM products";
    $statement = $conn->prepare($query);
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

        // Create product object
        $product = array(
            "id" => $id,
            "Name" => $name,
            "Price" => $price,
            "Remise" => $remise,
            "description" => $description,
            "category" => $category,
            "Image" => $image
        );
        $productList[] = $product;
    }

    // Output the product list as JSON
    echo json_encode($productList);

    // Close connection (if needed)
    $statement->close();
    $conn->close();
}

// Call the function to execute and display products
ExecutdisplayProd();
?>
