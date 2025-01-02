

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