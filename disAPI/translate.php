<?php

//1.installed php-curl on linux
//2.uncommented curl-php from php.ini (and restarted)

$from=$_REQUEST['from'];
$to=$_REQUEST['to'];
$text = $_REQUEST['text'];
$CLIENT_ID = "FREE_TRIAL_ACCOUNT";
$CLIENT_SECRET = "PUBLIC_SECRET";
$postData = array(
  'fromLang' => $from,
  'toLang' => $to,
  'text' => $text
);
$headers = array(
  'Content-Type: application/json',
  'X-WM-CLIENT-ID: '.$CLIENT_ID,
  'X-WM-CLIENT-SECRET: '.$CLIENT_SECRET
);
$url = 'http://api.whatsmate.net/v1/translation/translate';
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($postData));
$response = curl_exec($ch);

echo $response;
curl_close($ch);
?>

