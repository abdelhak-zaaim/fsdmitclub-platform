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

package com.fsdm.it.fsdm_it_club.controllers.admin;


import com.fsdm.it.fsdm_it_club.dto.request.PaginateTableRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailsResponseDto;
import com.fsdm.it.fsdm_it_club.entity.NewsletterEmail;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@Controller
@Validated
public class NewsLetterController {
    private final NewsletterEmailService newsletterEmailService;

    public NewsLetterController(NewsletterEmailService newsletterEmailService) {
        this.newsletterEmailService = newsletterEmailService;
    }

    @PostMapping("/admin/newsletter")
    public ResponseEntity<?> getNewsletterEmails(
            @RequestBody PaginateTableRequestDto requestDto) {

        int page = requestDto.page();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, requestDto.length());
        Page<NewsletterEmail> emailsPage = newsletterEmailService.searchByEmails(requestDto.search(), requestDto.order(), pageable);


        Page<NewsLetterEmailDto> emailsPageDto = emailsPage.map(email ->
                new NewsLetterEmailDto(email.getId(),
                        email.getEmail(),
                        email.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                        email.isSubscribed()
                )
        );

        NewsLetterEmailsResponseDto response = NewsLetterEmailsResponseDto.builder()
                .draw(requestDto.draw())
                .recordsTotal(emailsPage.getTotalElements())
                .recordsFiltered(emailsPage.getTotalElements())
                .data(emailsPageDto.getContent())
                .build();


        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/newsletter")
    public String newsletter() {
        return "admin/newsletter";
    }

    @DeleteMapping("/admin/newsletter/delete/{id}")
    public ResponseEntity<MessageResponseDto> deleteNewsLetter(@PathVariable Long id) {
        newsletterEmailService.deleteById(id);
        return ResponseEntity.ok(MessageResponseDto.builder().message("Email deleted successfully").success(true).build());
    }

    @PutMapping("/admin/newsletter/unsubscribe/{id}")
    public ResponseEntity<MessageResponseDto> unsubscribeNewsLetter(@PathVariable Long id) {
        newsletterEmailService.unsubscribeById(id);
        return ResponseEntity.ok(MessageResponseDto.builder().message("Email unsubscribed successfully").success(true).build());
    }

    @PutMapping("/admin/newsletter/subscribe/{id}")
    public ResponseEntity<MessageResponseDto> subscribeNewsLetter(@PathVariable Long id) {
        newsletterEmailService.subscribeById(id);
        return ResponseEntity.ok(MessageResponseDto.builder().message("Email subscribed successfully").success(true).build());
    }
}
