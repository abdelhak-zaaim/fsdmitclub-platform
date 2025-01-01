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

package com.fsdm.it.fsdm_it_club.services;


import com.fsdm.it.fsdm_it_club.entity.NewsletterEmail;
import com.fsdm.it.fsdm_it_club.model.TableSortOrder;
import com.fsdm.it.fsdm_it_club.model.enums.SortOrder;
import com.fsdm.it.fsdm_it_club.repository.NewsletterEmailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsletterEmailService {
    private final NewsletterEmailRepository newsletterEmailRepository;

    public NewsletterEmailService(NewsletterEmailRepository newsletterEmailRepository) {
        this.newsletterEmailRepository = newsletterEmailRepository;
    }

    public NewsletterEmail saveEmail(String email_string) {
        if (newsletterEmailRepository.existsByEmail(email_string)) {
            throw new IllegalArgumentException("Email already subscribed");
        }
        NewsletterEmail email = new NewsletterEmail();

        email.setEmail(email_string);
        email.setSubscribed(true);

        return newsletterEmailRepository.save(email);
    }

    public void deleteEmail(Long id) {
        newsletterEmailRepository.deleteById(id);
    }

    public NewsletterEmail loadEmailById(Long id) {
        return newsletterEmailRepository.findById(id).orElse(null);
    }

    public Page<NewsletterEmail> getEmails(Pageable pageable) {
        return newsletterEmailRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        newsletterEmailRepository.deleteById(id);
    }

    public Page<NewsletterEmail> searchByEmails(String search, List<TableSortOrder> orderList, Pageable pageable) {
        TableSortOrder order = orderList.get(0);
        if (order.column() == 0) {
            if (order.dir() == SortOrder.ASC) {
                return newsletterEmailRepository.findByEmailContainingOrderByIdAsc(search, pageable);
            } else {
                return newsletterEmailRepository.findByEmailContainingOrderByIdDesc(search, pageable);
            }
        } else if (order.column() == 1) {
            if (order.dir() == SortOrder.ASC) {
                return newsletterEmailRepository.findByEmailContainingOrderByEmailAsc(search, pageable);
            } else {
                return newsletterEmailRepository.findByEmailContainingOrderByEmailDesc(search, pageable);
            }

        } else if (order.column() == 2) {
            if (order.dir() == SortOrder.ASC) {
                return newsletterEmailRepository.findByEmailContainingOrderByCreatedAtAsc(search, pageable);
            } else {
                return newsletterEmailRepository.findByEmailContainingOrderByCreatedAtDesc(search, pageable);
            }

        }
        return newsletterEmailRepository.findByEmailContaining(search, pageable);
    }

    public void unsubscribeById(Long id) {
        NewsletterEmail email = newsletterEmailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Email not found"));
        email.setSubscribed(false);
        newsletterEmailRepository.save(email);
    }

    public void subscribeById(Long id) {
        NewsletterEmail email = newsletterEmailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Email not found"));
        email.setSubscribed(true);
        newsletterEmailRepository.save(email);
    }
}
