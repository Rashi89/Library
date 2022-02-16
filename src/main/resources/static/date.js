function today_date(){
var data = new Date();
var dzien = data.getDate();
var miesiac = data.getMonth()+1;
var rok = data.getFullYear();

if(dzien<10) dzien="0"+dzien;
if(miesiac<10) miesiac="0"+miesiac;

document.getElementById("today_date").innerHTML=dzien+"-"+miesiac+"-"+rok;
setTimeout("today_date()",1000);
}
window.onload=today_date;