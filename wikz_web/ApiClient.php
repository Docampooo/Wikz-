<?php

class ApiClient
{
    private string $baseUrl = "http://localhost:8080/api/wikz/operaciones";

    private function request(string $method, string $endpoint, array $data = null)
    {
        $url = $this->baseUrl . $endpoint;

        if ($method === "GET" && $data) {
            $url .= "?" . http_build_query($data);
        }

        $ch = curl_init($url);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $method);

        if ($method === "POST") {
            curl_setopt($ch, CURLOPT_HTTPHEADER, [
                "Content-Type: application/json"
            ]);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        }

        $response = curl_exec($ch);

        if ($response === false) {
            throw new Exception(curl_error($ch));
        }

        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        curl_close($ch);

        return [
            "code" => $httpCode,
            "data" => json_decode($response, true)
        ];
    }

    public function get(string $endpoint, array $params = [])
    {
        return $this->request("GET", $endpoint, $params);
    }

    public function post(string $endpoint, array $data)
    {
        return $this->request("POST", $endpoint, $data);
    }
}
