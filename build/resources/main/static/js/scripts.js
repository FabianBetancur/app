window.onload = function(){
    alert("The page has loaded.");
    console.log("message");
};

document.addEventListener("DOMContentLoaded",function(){
    var element = document.getElementById("myElement");
    element.addEventListener('click',function(){
        alert("You have clicked on the item!");
    });
});