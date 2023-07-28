1$numberOfFiles = Read-Host "Introduceti numarul de fisiere pe care doriti sa le creati:"
$numberOfFolders = Read-Host "Introduceti numarul de directoare pe care doriti sa le creati:"
$path = Read-Host "Introduceti calea catre directorul unde doriti sa creati fisierele si directoarele:"

for ($i=1; $i -le $numberOfFiles; $i++) {
    $fileName = Read-Host "Introduceti numele fisierului $i"
    New-Item -ItemType File -Path "$path\$fileName"
}

for ($i=1; $i -le $numberOfFolders; $i++) {
    $folderName = Read-Host "Introduceti numele directorului $i"
    New-Item -ItemType Directory -Path "$path\$folderName"
}

$copy = Read-Host "Doriti sa copiati directoarele? (da/nu)"
if ($copy -eq "da") {
    $copySource = Read-Host "Introduceti calea sursa a directoarelor:"
    $copyDestination = Read-Host "Introduceti calea destinatie a directoarelor:"
    Copy-Item -Path $copySource -Destination $copyDestination 
}

$write = Read-Host "Doriti sa scrieti in fisiere? (da/nu)"
if ($write -eq "da") {
    $fileToWrite = Read-Host "Introduceti numele fisierului in care doriti sa scrieti:"
    $textToWrite = Read-Host "Introduceti textul pe care doriti sa il scrieti:"
    Add-Content -Path "$path\$fileToWrite" -Value $textToWrite
}

$move = Read-Host "Doriti sa mutati fisierele si directoarele? (da/nu)"
if ($move -eq "da") {
    $moveSource = Read-Host "Introduceti calea sursa a fisierelor si directoarelor:"
    $moveDestination = Read-Host "Introduceti calea destinatie a fisierelor si directoarelor:"
    Move-Item -Path $moveSource -Destination $moveDestination
}

$delete = Read-Host "Doriti sa stergeti fisierele si directoarele? (da/nu)"
if ($delete -eq "da") {
    $deletePath = Read-Host "Introduceti calea fisierelor si directoarelor pe care doriti sa le stergeti:"
    Remove-Item -Path $deletePath 
}
