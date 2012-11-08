<?php


$uploaddir = './images/';
$uploadfile = $uploaddir . basename($_FILES['file']['name']);

echo '<p>';

if(is_uploadfile($_FILE['file']['tmp_name'])){
	if(move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile){
		print "success";
	}
}

echo '</p>';

?>