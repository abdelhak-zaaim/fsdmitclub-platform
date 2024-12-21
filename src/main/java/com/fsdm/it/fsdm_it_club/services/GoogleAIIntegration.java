/*
 * Copyright (c) 2024 FSDM IT Club.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fsdm.it.fsdm_it_club.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class GoogleAIIntegration {
    @Value("${google.api.key}")
    private static String API_KEY;

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/tunedModels/csclubchat-p367tijt4mfj:generateContent?key=";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // Request Payload
        Map<String, Object> requestPayload = Map.of(
                "contents", new Object[]{
                        Map.of("role", "user", "parts", new Object[]{
                                Map.of("text", "Generate audio diarization, including transcriptions and speaker information...")
                        })
                }
        );

        // Convert Payload to JSON String
        String requestBody = objectMapper.writeValueAsString(requestPayload);

        String url = API_URL + API_KEY;
        // Create HTTP Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Create HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Send Request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print Response
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }
}
