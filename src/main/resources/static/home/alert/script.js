function startLoading() {
    const loaderContainer = document.getElementById('loader_container');
    if (loaderContainer) {
        loaderContainer.style.display = 'flex'; // Display loader container
    }
}

function endLoading() {
    const loaderContainer = document.getElementById('loader_container');
    if (loaderContainer) {
        loaderContainer.style.display = 'none'; // Hide loader container
    }
}