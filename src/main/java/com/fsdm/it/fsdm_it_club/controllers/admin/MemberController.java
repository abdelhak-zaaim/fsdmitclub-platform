/*
 * Copyright (c) 2025 FSDM IT Club.
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


import com.fsdm.it.fsdm_it_club.dto.request.AddMemberDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.model.enums.Degree;
import com.fsdm.it.fsdm_it_club.services.S3Service;
import com.fsdm.it.fsdm_it_club.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class MemberController {
    private final UserService userService;
    private final S3Service s3Service;

    public MemberController(UserService userService, S3Service s3Service) {
        this.userService = userService;
        this.s3Service = s3Service;
    }

    @PostMapping(value = "/admin/members/add", consumes = "multipart/form-data")
    @ResponseBody
    public MessageResponseDto addMember(@Valid @ModelAttribute AddMemberDto addMemberDto, BindingResult bindingResult , HttpServletResponse response ) throws IOException {
        // Process the file
        if(bindingResult.hasErrors()){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return MessageResponseDto.builder().success(false).message(bindingResult.getFieldError().getDefaultMessage()).build();
        }
        MultipartFile image = addMemberDto.image();
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(image); // Your upload logic
        }

        // Build and save the user
        User user = User.builder()
                .username(addMemberDto.username())
                .biography(addMemberDto.biography())
                .fName(addMemberDto.fName())
                .lName(addMemberDto.lName())
                .email(addMemberDto.email())
                .phone(addMemberDto.phone())
                .github(addMemberDto.github())
                .linkedin(addMemberDto.linkedin())
                .degree(Degree.valueOf(addMemberDto.degree().toUpperCase()))
                .major(addMemberDto.major())
                .role(User.Role.valueOf(addMemberDto.role().toUpperCase()))
                .image(imageUrl)
                .passions(addMemberDto.passions())
                .password("jdhbcjhfgeyufgehfbnebfef")
                .build();

        userService.saveUser(user);

        return MessageResponseDto.builder()
                .message("Member added successfully")
                .success(true)
                .build();
    }

    @PostMapping("/s3/upload")
    public ResponseEntity<String> uploadToS3(@RequestParam("image") MultipartFile image) {
        try {
            String s3Key = s3Service.uploadFile(image); // Assume S3Service handles upload logic
            return ResponseEntity.ok("File uploaded to S3 with key: " + s3Key);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file to S3.");
        }
    }
}
