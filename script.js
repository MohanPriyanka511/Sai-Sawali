let menuopen = document.querySelector(".open");
let menuclose = document.querySelector(".close");

// nav .menu
let menubar = document.querySelector(".menu");
menuopen.addEventListener("click",()=>{
    menubar.style.transform = "translate(-100%,0%)";

})

menuclose.addEventListener("click",()=>{
    menubar.style.transform = "translate(0%,0%)";

})