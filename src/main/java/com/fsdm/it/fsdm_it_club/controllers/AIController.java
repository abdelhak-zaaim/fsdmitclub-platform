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

package com.fsdm.it.fsdm_it_club.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fsdm.it.fsdm_it_club.services.GoogleAIService;

import java.util.Map;

@Controller
public class AIController {

    private final GoogleAIService aiService;

    public AIController(GoogleAIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/test/ai")
    public ResponseEntity<String> generateContent(@RequestParam String payload) {
        String response = aiService.callGoogleAI(payload);
        return ResponseEntity.ok(response);
    }
}
