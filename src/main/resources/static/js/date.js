function getDate() {
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();
alert("sfhssss====================");
  if(dd<10) {
      dd = '0'+dd
  } 

  if(mm<10) {
      mm = '0'+mm
  } 

  today = yyyy + '/' + mm + '/' + dd;
  console.log(today);
  document.getElementById("date").value = today;
}


window.onload = function() {
  getDate();
};