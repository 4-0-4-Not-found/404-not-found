 window.onload= function (){
    let macarte =L.map("carte").setView([48.852969,2.349903],
        13)
     L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
         attribution:'donneés open streetmap france',
         minZoom:1,
         maxZoom:20
     }).addTo(macarte)
 }