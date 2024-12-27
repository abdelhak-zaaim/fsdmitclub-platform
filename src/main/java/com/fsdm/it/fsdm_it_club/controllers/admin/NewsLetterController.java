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


import com.fsdm.it.fsdm_it_club.dto.request.DeleteNewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.dto.request.NewsLetterEmailsRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailsResponseDto;
import com.fsdm.it.fsdm_it_club.entity.NewsletterEmail;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;

@Controller
@Validated
public class NewsLetterController {
    private final NewsletterEmailService newsletterEmailService;

    public NewsLetterController(NewsletterEmailService newsletterEmailService) {
        this.newsletterEmailService = newsletterEmailService;
    }

    @PostMapping("/admin/newsletter/delete")
    public ResponseEntity<MessageResponseDto> deleteNewsLetter(@RequestBody DeleteNewsLetterEmailDto deleteNewsLetterEmailDto) {
        newsletterEmailService.deleteById(deleteNewsLetterEmailDto.id());
        return ResponseEntity.ok(MessageResponseDto.builder().message("Email deleted successfully").success(true).build());
    }

    @PostMapping("/admin/newsletter")
    public ResponseEntity<?> getNewsletterEmails(
            @RequestBody NewsLetterEmailsRequestDto requestDto) {

        int page = requestDto.page();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, requestDto.length());
        Page<NewsletterEmail> emailsPage = newsletterEmailService.searchByEmails(requestDto.search(), pageable);


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


        return ResponseEntity.ok(response.toJson());
    }

    @GetMapping("/admin/newsletter")
    public String newsletter(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        Page<NewsletterEmail> emailsPage = newsletterEmailService.getEmails(pageable);

        // convert to dto
        Page<NewsLetterEmailDto> emailsPageDto = emailsPage.map(email ->
                new NewsLetterEmailDto(email.getId(),
                        email.getEmail(),
                        email.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                        email.isSubscribed()
                )
        );

        model.addAttribute("emailsPage", emailsPageDto);

        return "admin/newsletter";
    }
}
