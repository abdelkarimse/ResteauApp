<?php
include("init.php");

if(isset($_GET['action'])) {
    $action = $_GET['action'];

    switch($action) {
        case 'getNumerotab':
            $numerotab = getNumerotab();
            echo $numerotab;
            break;
        case 'verifyTab':
            if(isset($_GET['tableId'])) {
                $tableId = $_GET['tableId'];
                $tableExists = verifyTable($tableId);
                echo $tableExists ? 'true' : 'false';
            } else {
                echo 'Missing tableId parameter';
            }
            break;
        default:
            echo 'Invalid action';
            break;
    }
} else {
    echo 'No action specified';
}

function getNumerotab() {
    global $conn;
    $query = "SELECT numerotab FROM tables WHERE id = 1";
    $result = $conn->query($query);

    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        return $row["numerotab"];
    } else {
        return 0;
    }
}

function verifyTable($tableId) {
    global $conn;
    $query = "SELECT * FROM commandP WHERE tables = ?";
    $statement = $conn->prepare($query);
    $statement->bind_param("i", $tableId);
    $statement->execute();
    $result = $statement->get_result();

    $tableExists = $result && $result->num_rows > 0;

    $statement->close();
    return $tableExists;
}
?>
