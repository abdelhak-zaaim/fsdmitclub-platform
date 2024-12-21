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
let news_letter = document.getElementById("news_letter_form");


document.querySelector('.submit-email').addEventListener('mousedown', (e) => {
    e.preventDefault();
    if (isSubscribed) return;
// check if the email is valid
    if (!news_letter.checkValidity()) {
        document.querySelector('.subscription').classList.add('error');
        return;
    }
    isSubscribed = true;
    let token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    let header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    let formData = new FormData(news_letter);

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

