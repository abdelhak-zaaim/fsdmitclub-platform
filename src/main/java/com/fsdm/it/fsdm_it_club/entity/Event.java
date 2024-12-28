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

package com.fsdm.it.fsdm_it_club.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String title;

    private String description;
    private String location;


    private LocalDate startDate;
    private LocalDate endDate;



    private LocalTime startTime;
    private LocalTime endTime;

    private String image;
    private String category;
    private String status;
    private String type;

    private boolean isFeatured;
    private boolean isPublished;
    private boolean isOnline;

    private String onlinePlatform;
    private String onlineLink;

    private int views;

    @ElementCollection
    private Collection<String> topics;

    @ElementCollection
    private Collection<String> keyWords;

    public boolean isPast() {
        if (this.endDate == null) {
            return this.startDate.isBefore(LocalDate.now()) || (this.startDate.isEqual(LocalDate.now()) && this.startTime.compareTo(LocalTime.now()) < 0);
        }
        return this.endDate.isBefore(LocalDate.now()) || (this.endDate.isEqual(LocalDate.now()) && this.endTime.compareTo(LocalTime.now()) < 0);
    }

}
