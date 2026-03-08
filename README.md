## Description de l'application
MTG Custom Collection Connector (abrégé M3C) est une application dont le but est d'intégrer
les données des cartes [Magic](https://magic.wizards.com/fr) de votre collection afin de 
pouvoir automatiser certaines actions rébarbatives.  
Pour le moment, l'application n'est pas assez avancée pour délivrer ses automatisations, mais
la première étapes serait d'importer et de comparer deux collections Manabox entre elles pour
proposer des échanges de cartes en double.  
Peut-être que d'autres fonctionnalités seront ajoutées à l'avenir.  

## Comment ça marche ?
Voici, ci-dessous, les étapes pour tester l'application en local sur votre pc.  
A noter qu'il est conseillé d'avoir de l'expérience en développement logiciel pour 
suivre les étapes.

### Génération du Manabox
Scanner vos cartes sur l'application Manabox.  
Ensuite exporter votre collection complète (Collection -> 3 points en haut à droite -> 
Exporter -> Exporter) dans un fichier csv, stocké sur votre pc.  

### Cloner le projet et l'ouvrir dans IntelliJ
Télécharger [IntelliJ](https://www.jetbrains.com/fr-fr/idea/)  
Cloner le projet en local et l'ouvrir dans IntelliJ  
Dans le terminal, faire un
```bash
mvn clean install
```
(Il est aussi possible de le faire directement depuis IntelliJ dans l'onglet Maven) 

### Configurer Bruno
Télécharger [Bruno](https://www.usebruno.com/)  
Open Collection -> Choisir le dossier M3C-Collection, dans le dossier bruno à la racine 
du projet  
Adapter les requêtes et les variables d'environnements à votre usage  

### Configurer postgresql
Il faut setter 3 variables d'environnement système
```bash
export POSTGRES_USER="..."
export POSTGRES_PASSWORD="..."
export POSTGRES_EMAIL="..."
```
Si vous êtes sur linux ou macos, les mettre directement dans le .bashrc ou .zshrc, 
en haut du fichier et redémarrer votre terminal  
Redémarrer IntelliJ pour qu'il intègre ces changements  

### Lancer l'application dans IntelliJ
Lancer l'application dans IntelliJ  
`src/main/java/com/julian/guigon/mtg/custom/collection/connector/MtgCustomCollectionConnectorApplication.java`  
Click-droit -> Run MtgCustomCollectionConnectorApplication.main()  
Si tout est OK, vous devriez voir apparaître, dans la console d'exécution, un log qui 
ressemble à celui-ci :  
```
2026-03-01T15:34:46.480+01:00  INFO 7429 --- [mtg-custom-collection-connector] [           main] .MtgCustomCollectionConnectorApplication : Started MtgCustomCollectionConnectorApplication in 25.879 seconds (process running for 26.482)
```

### Lancer l'import sur Bruno
Lancer la requête `Import manabox csv` en adaptant les paramètres  
Il faut que le `path` corresponde au chemin vers le fichier csv de votre collection manabox

---

### Pour les contributeurs
Il y a un second fichier README (README-DEV.md) à l'intention des contributeurs