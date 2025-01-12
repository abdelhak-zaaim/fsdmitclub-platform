/*
 * Copyright (c) 2024, 2025 FSDM IT Club.
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

package com.fsdm.it.fsdm_it_club.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddMemberDto(
        @NotEmpty(message = "UserName is required")
        String username,
        String biography,
        MultipartFile image,
        String fName,
        String lName,
        @NotBlank(message = "Email is required") // Ensures the field is not empty
        @Email(message = "Email should be valid") // Validates the email format
        String email,
        String phone,
        String github,
        String linkedin,
        String degree,
        String major,
        String role,
        List<String> passions
) {
}