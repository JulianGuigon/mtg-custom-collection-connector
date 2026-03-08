### Lancer les tests de mutation
Vous pouvez lancer la commmande Maven suivante
```bash
mvn clean install org.pitest:pitest-maven:mutationCoverage
```
Attention à ne pas avoir activé -DSkipTests dans l'onglet Maven d'IntelliJ  
Si tout est ok, ouvrez target/pit-reports/index.html dans un navigateur

### Consulter les données sur pg_admin
Le docker-compose.yml met a disposition une instance de pg_admin, accessible à l'adresse :   
`http://0.0.0.0:51024/browser/`
A la première connexion, il faut ajouter le serveur postgresql lancé en local (Servers -> 
Click droit -> Register -> Server) :
```
Name: M3C Postgresql local
Host name/address: postgres
Port: 5432
Maintenance database : m3c
Username : ${correspond à la variable d'env POSTGRES_USER}
Password : ${correspond à la variable d'env POSTGRES_PASSWORD}
Save Password ? : coché
```

Click droit sur m3c -> Query Tool  
Executer la requête que vous voulez. Exemples : 
```
select * from m3c.mana_box_card;
select * from m3c.mana_box_collection;
```

### Changer le mot de passe de postgresql
La Docker Integration gère les volumes donc si l'on change les mots de passes après la
première exécution, il faut faire un `docker compose down -v` pour qu'il reparte de zéro

Sinon, pour ne pas perdre les données, ChatGPT suggère :

###### 1️⃣ Entrer dans le conteneur
`docker exec -it mtg-custom-collection-connector-postgres-1 psql -U postgres`
(si admin est ton user principal, adapte)

###### 2️⃣ Changer le mot de passe
Dans psql : `ALTER USER admin WITH PASSWORD 'myPassword';`
Puis : `\q`

###### 3️⃣ Redémarrer l’application