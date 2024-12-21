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
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
