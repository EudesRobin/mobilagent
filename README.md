# mobilagent
RICM4 - AR - Polytech' Grenoble

Initialisation :
--------

* Lien vers le dépot
```
git clone https://github.com/EudesRobin/mobilagent.git
```

* [Tutos git ; google is your friend... ;)](http://www.miximum.fr/enfin-comprendre-git.html)

* Point d'entrée : Starter.java , paramètres pour lancer une première simulation
```
#agent Hello
( 3 configs à lancer )
program arguments-> 
"Configurations/hello.client1.xml" "mobilagent://localhost:4200/"
program arguments-> 
"Configurations/hello.server1.xml" "server1"
program arguments-> 
"Configurations/hello.server2.xml" "server2"

VM arguments -> -Djava.security.policy=.policy # fichier à la racine du projet
( idem pour les 3 configs )
```

```
#agent LookForHotel
( 4 configs à lancer )
program arguments-> 
"Configurations/hostel.client1.xml" "mobilagent://localhost:1111/"
program arguments-> 
"Configurations/hostel.server1.xml" "server1"
program arguments-> 
"Configurations/hostel.server2.xml" "server2"
program arguments-> 
"Configurations/hostel.server3.xml" "server3"

VM arguments -> -Djava.security.policy=.policy # fichier à la racine du projet
( idem pour les 4 configs )
```

```
# LookForHotel version RMI
( 4 configs à lancer )

#le client
program arguments-> 
"localhost" "1111" "Chaine" "Paris" "localhost" "3333" "Chaine" "Paris" "localhost" "2222" "Annuaire"

#le serveur annuaire
program arguments-> 
"Annuaire" "DataStore/Annuaire.xml" "2222"

#le serveur chaine1
program arguments-> 
"Chaine" "DataStore/Hotels1.xml" "1111"

#le serveur chaine2
program arguments-> 
"Chaine" "DataStore/Hotels2.xml" "3333"

VM arguments -> -Djava.security.policy=.policy # fichier à la racine du projet
( idem pour les 4 configs )
```

Pour les params du client , on peut ajouter N chaines d'hotels , ou N annuaires.
Les annuaires doivent logiquement être positionés APRES les descriptions des chaines, vu qu'on interroge
le/les annuaire(s) suivant une liste d'hotels construites suivant les matchs avec la localisation passée en param.

rmq : on peut donc interroger des chaines avec différents params de localisation.


```
# Serveur RMI Courtage...
( 5 configs à lancer ou plus...)

#le serveur RMI "annuaire"
"Courtage" "5555"
( main class : jus.aor.courtage.kernel.RMIcourtage )

Pour la suite, on revient au starter :
( main class : jus.aor.courtage.kernel.Starter )
#le serveur annuaire
program arguments-> 
"Configurations/srv-annuaire3.xml" "mobilagent://localhost:4444/" "" "localhost" "5555"

#le serveur chaine1
program arguments-> 
"Configurations/srv-chaine1.xml" "mobilagent://localhost:2222/" "" "localhost" "5555"

#le serveur chaine2
program arguments-> 
"Configurations/srv-chaine2.xml" "mobilagent://localhost:3333/" "" "localhost" "5555"

#le client
program arguments-> 
"Configurations/courtier_client.xml" "mobilagent://localhost:1111/" "first" "localhost" "5555"

VM arguments -> -Djava.security.policy=.policy # fichier à la racine du projet
( idem pour les 5 configs )
```

On doit lancer impérativement le serveur RMI en premier, chaque serveur du bam s'enregistre ensuite dessus.
Le client à la fin. Deux choix pour la selection de service : "all" ou "first". Dans le cas de first, le client aura une seule et unique étape vers le premier serveur dispo ayant le service qui l'interesse. Avec all, on ajoutera une étape par serveur dispo pour le service en question.

ex avec first.
(ordre d'enregistrement et de dispo)
srv 1 -> service Hotels
srv 2 -> service Hotels
srv 3 -> service Telephones

Un client faisant des appels à get_hotels ( service hotel ) et get_nums (service Telephones ) ira voir uniquement
le srv 1 et srv 3. Avec une politique all, il serait allé voir srv1, srv 2 et srv 3.
