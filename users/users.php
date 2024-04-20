<?php
$jsonFilePath = '/Applications/XAMPP/xamppfiles/htdocs/users/users.json';

function readJsonFile($filePath) {
    if (file_exists($filePath)) {
        return json_decode(file_get_contents($filePath), true);
    } else {
        return [];
    }
}

function writeJsonFile($filePath, $data) {
    file_put_contents($filePath, json_encode($data, JSON_PRETTY_PRINT));
}

$method = $_SERVER['REQUEST_METHOD'];

if ($method === 'GET') {
    
    header('Content-Type: application/json');
    $data = readJsonFile($jsonFilePath);
    echo json_encode($data);
} elseif ($method === 'POST') {

    $inputData = file_get_contents('php://input');
    $newUser = json_decode($inputData, true);

    // Read the existing JSON data
    $existingData = readJsonFile($jsonFilePath);

    // Find the highest existing user ID
    $maxId = 0;
    foreach ($existingData as $user) {
        if ($user['id'] > $maxId) {
            $maxId = $user['id'];
        }
    }

    // Increment the max ID for the new user
    $newUserId = $maxId + 1;
    $newUser['id'] = $newUserId;

    // Add the new user to the existing data
    $existingData[] = $newUser;

    // Write the updated data back to the JSON file
    writeJsonFile($jsonFilePath, $existingData);
    
    header('Content-Type: application/json');
    echo json_encode(['success' => true, 'id' => $newUserId]);
} else {
    http_response_code(405);
    echo 'Method Not Allowed';
}
?>
