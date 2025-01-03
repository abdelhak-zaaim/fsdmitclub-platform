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

import com.fsdm.it.fsdm_it_club.entity.Contact;
import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import com.fsdm.it.fsdm_it_club.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

import static com.fsdm.it.fsdm_it_club.helper.Constants.MAIN_URL;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendJoinRequestNotification_sendsEmail_whenJoinRequestIsValid() throws MessagingException, UnsupportedEncodingException {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setFName("Mohammed");
        joinRequest.setEmail("abdo@example.com");
        joinRequest.setPhone("07654543");
        joinRequest.setDegree("master");
        joinRequest.setMajor("bdsi");
        joinRequest.setMessage("Interested in joining");

        User toUser = new User();
        toUser.setLastName("Zaaim");
        toUser.setEmail("admin@example.com");

        Context context = new Context();
        context.setVariable("toLastName", "Abdelhak");
        context.setVariable("fName", "abdo");
        context.setVariable("email", "abdelhak@example.com");
        context.setVariable("phone", "123456789");
        context.setVariable("degreeAndMajor", "master bdsi");
        context.setVariable("message", "Interested in joining");
        context.setVariable("adminDashboardUrl", "https://fsdmitclub.com/admin/login");

        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("Email Body");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendJoinRequestNotification(joinRequest, toUser);

        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void sendJoinRequestNotification_doesNotSendEmail_whenJoinRequestIsNull() {
        User toUser = new User();
        toUser.setEmail("admin@example.com");

        emailService.sendJoinRequestNotification(null, toUser);

        verify(emailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void sendContactNotification_sendsEmail_whenContactIsValid() throws MessagingException, UnsupportedEncodingException {
        Contact contact = new Contact();
        contact.setFName("mohammed");
        contact.setEmail("mohammed@example.com");
        contact.setMessage("Need more info");

        User toUser = new User();
        toUser.setLastName("abdo");
        toUser.setEmail("admin@example.com");

        Context context = new Context();
        context.setVariable("toLastName", "Smith");
        context.setVariable("fName", "Jane");
        context.setVariable("email", "jane@example.com");
        context.setVariable("message", "Need more info");
        context.setVariable("adminDashboardUrl", MAIN_URL + "/admin/login");

        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("Email Body");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendContactNotification(contact, toUser);

        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void sendContactNotification_doesNotSendEmail_whenContactIsNull() {
        User toUser = new User();
        toUser.setEmail("admin@fsdmitclub.com");

        emailService.sendContactNotification(null, toUser);

        verify(emailSender, never()).send(any(MimeMessage.class));
    }
}