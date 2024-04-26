<?php
include("init.php");

// API endpoint to check if database connection is successful
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['check_connection'])) {
    echo "Database connection successful!";
    exit;
}

// API endpoint to fetch Plat data
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['get_plat_data'])) {
    $sql = "SELECT * FROM products WHERE category = 'Plat'";
    $result = $conn->query($sql);

    $platList = array();
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $platList[] = $row;
        }
    }

    echo json_encode($platList);
    exit;
}

// API endpoint to fetch Fast Food data
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['get_fast_food_data'])) {
    $sql = "SELECT * FROM products WHERE category = 'Fast Food'";
    $result = $conn->query($sql);

    $fastFoodList = array();
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $fastFoodList[] = $row;
        }
    }

    echo json_encode($fastFoodList);
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['get_cafe_data'])) {
    $sql = "SELECT * FROM products WHERE category = 'Cafe'";
    $result = $conn->query($sql);

    $cafeList = array();
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $cafeList[] = $row;
        }
    }

    echo json_encode($cafeList);
    exit;
}


// Close the database connection
$conn->close();

?>
