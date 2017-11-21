import Vue from 'vue'
import App from './App'
import router from './router'
import Buefy from 'buefy'

Vue.use(Buefy)

console.clear()

Vue.config.productionTip = false

const shared = {
  categories: [
    {
      name: 'ORDINATEURS PORTABLES',
      id: 1,
      products: [
        {
          id: 101,
          name: 'Dell XPS 13 9360 - 13.3" - Core i7 7500U - 8 Go RAM - 256 Go SSD - français',
          subcategory: 'Ordinateur ultra-portable',
          description: "Plus de surface d'affichage, moins de poids : la technologie pratiquement sans bordure InfinityEdge optimise la surface d'affichage en permettant de faire entrer un écran de 33 cm (13 pouces) dans un cadre de 28 cm (11 pouces). Avec une bordure de 5,2 mm seulement, un poids plume de 1,22 kg et une épaisseur comprise entre 9 et 15 petits millimètres, l'ordinateur portable XPS 13 est extrêmement fin et léger.Superbe résolution UltraSharp QHD+ (3 200 x 1 800) : une mise à niveau en option vous permet d'obtenir des détails stupéfiants avec 5,7 millions de pixels (276 ppp).Facilité de partage : bénéficiez d'images parfaitement nettes sous quasiment tous les angles avec l'écran IPS IGZO, qui offre un angle d'affichage atteignant 170°.Illuminez votre journée : la luminosité de 400 cd/m² est supérieure à celle d'un écran standard d'ordinateur portable, ce qui vous permet d'obtenir un affichage optimal, même à l'extérieur.Superbes couleurs : avec une gamme de couleurs de 72 % et un taux de contraste de 1 000:1, les couleurs sont plus vives et les noirs plus profonds.Technologie tactile : appuyez, balayez et pincez à loisir. L'écran tactile en option vous permet d'interagir naturellement avec vos technologies.Pincez, zoomez et cliquez avec précision : le pavé tactile de précision permet d'éviter les sauts et flottements du curseur, tandis que la fonction de prévention de l'activation accidentelle élimine les clics involontaires lorsque votre paume touche le pavé tactile.Tout devient clair : un clavier rétroéclairé standard vous permet de rester productif même dans les pièces sombres ou totalement plongées dans l'obscurité.Durabilité maximale : modèle taillé avec précision dans un seul bloc d'aluminium pour obtenir un boîtier robuste, durable et élégant. L'option d'écran tactile avec verre Corning Gorilla Glass NBT est jusqu'à 10 fois plus résistante aux rayures que le verre à la chaux sodée.Toujours frais, même sous pression : le repose-mains est composé de fibre de carbone. Aussi solide et fin que l'aluminium, ce matériau est plus léger et frais au toucher.Design élégant : choisissez le coloris or rose ou argent pour une touche plus personnalisée.Efficacité énergétique : l'ordinateur portable XPS 13 est certifié ENERGY STAR.Matériaux plus intelligents : cet ordinateur portable est exempt de cadmium, de plomb, de mercure et de certains phtalates. Il est également certifié EPEAT Gold12 et exempt de BFR/PVC.Écoresponsable : 90 % des pièces de l'ordinateur portable peuvent être facilement recyclées ou réutilisées, et les plateaux d'emballage en bambou sont quant à eux 100 % recyclables.",
          price: '1681€27'
        },
        {
          id: 102,
          name: 'Dell XPS 15 9560 - 15.6" - Core i7-7700HQ - 16 Go RAM - 512 Go SSD - français',
          subcategory: 'Ordinateur ultra-portable',
          description: 'Le plus petit ordinateur portable 15,6 pouces au monde est un concentré de performances. Il possède un superbe écran InfinityEdge, le tout dans notre ordinateur portable XPS le plus puissant.',
          price: '1681€27'
        }
      ]
    },
    {
      name: 'IMPRIMANTES ET SCANNERS',
      id: 2,
      products: [
        {
          id: 201,
          name: 'Imprimante HP Envy 4527 Multifonctions WiFi Noir',
          subcategory: 'Imprimante multifonctions',
          description: "Type de scanner : à plat, à 24 bits ; Plage d'agrandissement ou de redimensionnement de l'image : 25 à 400 % par incréments de 1 % ; Numérisation en couleur ; Formats de fichiers pour la numérisation : TIFF, JPEG, PNG, BMP, PDF ; Numérisation depuis panneau de commande, copie, logiciel de numérisation HP Scan ; Taille de numérisation (scanner à plat) maximale : 216 x 297 mm ; Capteur par contact ; Version Twain, Version 2.1 ; Résolution de numérisation optimisée : Jusqu'à 2 400 ppp",
          price: '49€99'
        },
        {
          id: 202,
          name: 'Imprimante HP Envy 5640 All-In-One',
          subcategory: 'Imprimante multifonctions',
          description: `Imprimante Multifonction HP ENVY 5640 e-All-in-One : Un tout-en-un fiable et rapide."
          Ce e-All-in-One produit au quotidien des  photos de qualité professionnelle et sans bordure. Imprimez à partir d'un smartphone, d'une tablette ou de tout autre périphérique et numériser directement dessus, au fil de votre créativité, pour un rangement et un partage facile. 
          Augmentez aussi votre productivité avec l'excellente vitesse d'impression de l'Envy 5640 allant jusqu'à 12 ppm en noir et 8 ppm en couleur.
          
          Avec ou sans PC, une machine facile à utiliser !
          Grâce à l' écran couleur intégré tactile de 6,74 cm, vous pouvez visionner, sélectionner et imprimer vos photos  sans même allumer votre ordinateur. Insérez votre carte mémoire et laissez vous guider par le logiciel intégré à la machine. Vous pouvez également facilement réaliser vos scans, copies et agrandissements  directement sur l'écran du HP Envy 5640.
          Mettez en valeur le meilleur de vos impressions et créez des  photos de qualité professionnelle sans bordure avec facilité.  De plus, l'impression est facilitée avec le tiroir spécial pour papier photo 10x15cm.
          Réalisez des  économies à 2 points de vue, au niveau du temps et du papier grâce à la  fonction recto-verso.
          Imprimez plus avec moins, grâce aux cartouches optionnelles XL haute capacité.
          
          Libérez-vous des câbles !
          Grâce à sa  connexion Wifi, vous pouvez placer la  HP Envy 5640 là où bon vous semble, sans contrainte de longueur de câble. En passant par un routeur tel qu'une box Internet wifi, vous pouvez même  partager l'imprimante entre plusieurs ordinateurs équipés du wifi ! Vous pouvez même imprimer et scanner vos documents par liaison sans fil  à partir de votre smartphone ou tablette Android ou iOS en utilisant HP ePrint et HP Printer Control.
          `,
          price: '94€17'
        }
      ]
    }
  ]
}

shared.install = function () {
  Object.defineProperty(Vue.prototype, '$ds', {
    get () { return shared }
  })
}
Vue.use(shared)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
