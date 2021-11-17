$(document).ready(function(){
    $('select').material_select();
 });

 function countChars(obj){
    var maxLength = 255;
    var strLength = obj.value.length;
    var charRemain = (maxLength - strLength);
    
    
    if(charRemain <= 0){
        
        document.getElementById("charNum").innerHTML = '<span style="color: red;">Ah excedido el límite de '+maxLength+' carácteres</span>';
        document.getElementById("boton").style.cssText = 'display:none;';
        
    }else{
        document.getElementById("charNum").innerHTML = charRemain+' carácteres disponibles';
        document.getElementById("boton").style.cssText = 'display:block;';
    }

    absorverValor(charRemain);
}