function calculateBMI() {
    var w = parseInt(document.getElementById("form:weight").value);
    var h = parseInt( document.getElementById("form:height").value);

    document.getElementById("form:trBmi").value=document.getElementById("form:bmi").value=w/(h*h);
}

function numbersOnly(input) {
    var regex=/[^0-9]/gi;
    input.value=input.value.replace(regex,'');
}