<?php 
include("init.php");

// API for inserting delivery information
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_GET['delivery']) && isset($_POST["name"]) && isset($_POST["address"]) && isset($_POST["phone"]) && isset($_POST["price"]) && isset($_POST["idList"])) {
    $price = $_POST["price"];
    $name = $_POST["name"];
    $address = $_POST["address"];
    $phone = $_POST["phone"];
    $idList = explode(",", $_POST["idList"]); // Split the comma-separated ID list into an array

    // Insert data into the database
    $sql = "INSERT INTO commandL (nom, adresse, telephone, prix) VALUES (?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssd", $name, $address, $phone, $price);
    
    if ($stmt->execute()) {
        echo "Delivery data received and saved successfully!";

        $lastDeliveryId = $conn->insert_id;

        // Insert data into the notification table
        $sql = "INSERT INTO notification (id, aff, op) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $aff = 1;
        $op = 0;
        $stmt->bind_param("iii", $lastDeliveryId, $aff, $op);
        if (!$stmt->execute()) {
            echo "Error: " . $stmt->error;
        }

        // Iterate over the ID list and insert each ID into the command table with the associated delivery ID
        foreach ($idList as $id) {
            $commandSql = "INSERT INTO  (idP,id) VALUES (?, ?)";
            $commandStmt = $conn->prepare($commandSql);
            $commandStmt->bind_param("ii", $id,$lastDeliveryId);
            if (!$commandStmt->execute()) {
                echo "Error: Unable to save command data to the database.";
                break;
            }
        }
    } else {
        echo "Error: Unable to save delivery data to the database.";
    }
}

// API for inserting payment information
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_GET['place']) && isset($_POST["numerot"]) && isset($_POST["price"]) && isset($_POST["tables"]) && isset($_POST["idList"])) {
    $numerot = $_POST["numerot"];
    $price = $_POST["price"];
    $tables = $_POST["tables"];
    $idList = explode(",", $_POST["idList"]); // Split the comma-separated ID list into an array

    // Prepare the SQL statement for inserting payment data
    $paymentSql = "INSERT INTO commandP (numT, prix, tables) VALUES (?, ?, ?)";
    $paymentStmt = $conn->prepare($paymentSql);
    $paymentStmt->bind_param("sdd", $numerot, $price, $tables);

    // Execute the payment SQL statement
    if ($paymentStmt->execute()) {
        echo "Payment data received and saved successfully!";

        // Get the last inserted payment ID
        $lastPaymentId = $conn->insert_id;

        // Insert data into the notification table
        $sql = "INSERT INTO notification (id, aff, op) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $aff = 1;
        $op = 1;
        $stmt->bind_param("iii", $lastPaymentId, $aff, $op);
        if (!$stmt->execute()) {
            echo "Error: " . $stmt->error;
        }

        // Iterate over the ID list and insert each ID into the command table with the associated payment ID
        foreach ($idList as $id) {
            $commandSql = "INSERT INTO command (idP,id) VALUES (?, ?)";
            $commandStmt = $conn->prepare($commandSql);
            $commandStmt->bind_param("ii", $id, $lastPaymentId);
            if (!$commandStmt->execute()) {
                echo "Error: Unable to save command data to the database.";
                break;
            }
        }
    } else {
        echo "Error: Unable to save payment data to the database.";
    }
}

$conn->close();
?>
