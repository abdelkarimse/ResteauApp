<?php
include("init.php");

// Function to execute and display products
function ExecutdisplayProd() {
    global $conn;

    $productList = array();
    $query = "SELECT * FROM notification WHERE aff=1";
    $statement = $conn->prepare($query);
    $statement->execute();
    $result = $statement->get_result();

    while ($row = $result->fetch_assoc()) {
        $idN = $row["IdN"];
        $id = $row["id"];
        $aff = $row["aff"];
        $op = $row["op"];
       
        // Create product object
        $product = array(
            "idN" => $idN,
            "id" => $id,
            "aff" => $aff,
            "op" => $op,
        );
        $productList[] = $product;
    }

    // Output the product list as JSON
    echo json_encode($productList);

    // Close statement
    $statement->close();
    // Close connection
    $conn->close();
}

// Call the function to execute and display products
ExecutdisplayProd();
?>
