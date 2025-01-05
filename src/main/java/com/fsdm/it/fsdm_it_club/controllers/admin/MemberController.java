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
import com.fsdm.it.fsdm_it_club.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/members/add")
    public MessageResponseDto addMember(@RequestBody AddMemberDto addMemberDto) {
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
                .role(User.Role.valueOf(addMemberDto.role()))
                .passions(addMemberDto.passions())
                .password("jdhbcjhfgeyufgehfbnebfef")
                .build();

        userService.saveUser(user);
        return MessageResponseDto.builder().message("Member added successfully").success(true).build();

    }
}
