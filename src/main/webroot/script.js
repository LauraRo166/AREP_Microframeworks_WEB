function sendGet() {
    let nameVar = document.getElementById("nameGet").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        document.getElementById("responseGet").innerText = this.responseText;
    };
    xhttp.open("GET", "/hello?name=" + encodeURIComponent(nameVar));
    xhttp.send();
}

function sendPost() {
    let nameVar = document.getElementById("namePost").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        document.getElementById("responsePost").innerHTML = this.responseText;
    };
    xhttp.open("POST", "/echo");
    xhttp.setRequestHeader("Content-Type", "text/plain"); 
    xhttp.send(nameVar);
}
