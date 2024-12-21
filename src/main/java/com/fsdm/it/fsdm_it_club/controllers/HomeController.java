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

import com.fsdm.it.fsdm_it_club.dto.request.NewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller()
@Validated
public class HomeController {

    private final Validator validator;

    private final NewsletterEmailService newsletterEmailService;

    public HomeController(Validator validator, NewsletterEmailService newsletterEmailService) {
        this.validator = validator;
        this.newsletterEmailService = newsletterEmailService;
    }

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/event-listing")
    public String eventListing() {
        return "home/event-listing";
    }

    @GetMapping("/event-details")
    public String eventDetails() {
        return "home/event-detail";
    }

    @PostMapping("/public/newsletter")
    public ResponseEntity<MessageResponseDto> newsletter(@RequestBody NewsLetterEmailDto newsLetterEmailDto) {
        try {
            newsletterEmailService.saveEmail(newsLetterEmailDto.email());
            return ResponseEntity.ok(MessageResponseDto.builder().message("Email added to newsletter").success(true).build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message(e.getMessage()).success(false).build());
        }
    }
}