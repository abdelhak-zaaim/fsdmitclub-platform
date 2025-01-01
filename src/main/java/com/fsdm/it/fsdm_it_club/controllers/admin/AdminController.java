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

import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.entity.NewsletterEmail;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;

@Controller
public class AdminController {
    private final NewsletterEmailService newsletterEmailService;

    public AdminController(NewsletterEmailService newsletterEmailService) {
        this.newsletterEmailService = newsletterEmailService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/events")
    public String users() {
        return "admin/events";
    }

    @GetMapping("/admin/members")
    public String members() {
        return "admin/members";
    }

    @GetMapping("/admin/add-member")
    public String addMember() {
        return "admin/add-member";
    }

    @GetMapping("/admin/add-event")
    public String addEvent() {
        return "admin/add-event";
    }

}
