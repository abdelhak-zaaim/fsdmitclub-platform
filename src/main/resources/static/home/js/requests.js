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
var isSubscribed = false;
let requests = document.getElementById("news_letter_form");


document.querySelector('.submit-email').addEventListener('mousedown', (e) => {
    e.preventDefault();
    if (isSubscribed) return;
// check if the email is valid
    if (!requests.checkValidity()) {
        document.querySelector('.subscription').classList.add('error');
        return;
    }
    isSubscribed = true;
    let token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    let formData = new FormData(requests);

    // news_letter.reset();
    document.querySelector('.subscription').classList.add('done');
    fetch("/public/newsletter", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({
            email: formData.get("email"),
        })
    })
        .then(response => response.json())
        .then(data => {

            console.log("Success:", data);
        }).then(function () {

    })
        .catch((error) => {
            console.error("Error:", error);
        });

});

// on submit form that have id let membership_form = document.getElementById("membership_form");

let membership_form = document.getElementById("membership_form");
document.querySelector('#membership_form').addEventListener('submit', (e) => {
    e.preventDefault();
    if (!membership_form.checkValidity()) {
        document.querySelector('.subscription').classList.add('error');
        return;
    }
    let membership_form_token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let membership_form_header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    let membership_form_data = new FormData(membership_form);
    startLoading()
    fetch("/join-requests", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [membership_form_header]: membership_form_token
        },
        body: JSON.stringify({
            fName: membership_form_data.get("fName"),
            email: membership_form_data.get("email"),
            phone: membership_form_data.get("phone"),
            degreeAndMajor: membership_form_data.get("degreeAndMajor"),
            message: membership_form_data.get("message"),
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                SnapDialog().success('Success', data.message);
                membership_form.reset();
            } else {
                SnapDialog().error('Error', data.message);
            }
            endLoading()
        }).then(function () {

    })
        .catch((error) => {
            SnapDialog().error('Ops', "Something went wrong");
            endLoading()
            console.error("Error:", error);
        });

})


// on submit form that have id let contact_form = document.getElementById("contact_form");

let contact_form = document.getElementById("contact_form");
document.querySelector('#contact_form').addEventListener('submit', (e) => {
    e.preventDefault();
    if (!contact_form.checkValidity()) {
        document.querySelector('.subscription').classList.add('error');
        return;
    }
    let contact_form_token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let contact_form_header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    let contact_form_data = new FormData(contact_form);
    startLoading()
    fetch("/contact", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            [contact_form_header]: contact_form_token
        },
        body: JSON.stringify({
            fName: contact_form_data.get("fName"),
            email: contact_form_data.get("email"),
            message: contact_form_data.get("message"),
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                SnapDialog().success('Success', data.message);
                contact_form.reset();
            } else {
                SnapDialog().error('Error', data.message);
            }
            endLoading()
        }).then(function () {

    })
        .catch((error) => {
            SnapDialog().error('Ops', "Something went wrong");
            endLoading()
            console.error("Error:", error);
        });

})