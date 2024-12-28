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

package com.fsdm.it.fsdm_it_club.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {

    USER(new SimpleGrantedAuthority("USER")),
    ADMIN(new SimpleGrantedAuthority("ADMIN"));

    public static final String USER_ROLE_NAME = "ROLE";

    public static final String USER_ROLE = "CUSTOMER";

    public static final String ADMIN_ROLE = "ADMIN";

    private final GrantedAuthority grantedAuthority;

    Role(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }


}
