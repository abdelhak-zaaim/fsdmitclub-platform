
// FUNCTIONS:
function startLoading() {

    const loadingy = document.createElement("p");
   // loadingy.innerText = text;
    loadingy.style.setProperty(
        "--yTransform",
        `-20px`
    );

    loadingy.className = "loadingy";
    loadingy.setAttribute("role", "status");

    // Create a new div for the progress indicator
    const progress = document.createElement("div");
    progress.className = "progress";
    // Append the progress indicator to the loadingy element
    loadingy.appendChild(progress);

    document.body.insertAdjacentElement("beforeend", loadingy);
}



function closeLoading() {

    document.querySelectorAll(".loadingy").forEach((el) => {
        el.remove();
    });


}
