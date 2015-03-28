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
( 3 configs à lancer )

#le client
program arguments-> 
"localhost" "1111" "Chaine" "Paris" "localhost" "2222" "Annuaire"

#le serveur annuaire
program arguments-> 
"Annuaire" "DataStore/Annuaire.xml" "2222"

#le serveur chaine1
program arguments-> 
"Chaine" "DataStore/Hotels1.xml" "1111"

VM arguments -> -Djava.security.policy=.policy # fichier à la racine du projet
( idem pour les 3 configs )
```

Pour les params du client , on peut ajouter N chaines d'hotels , ou N annuaires.
Les annuaires doivent logiquement être positionés APRES les descriptions des chaines, vu qu'on interroge
le/les annuaire(s) suivant une liste d'hotels construites suivant les matchs avec la localisation passée en param.

rmq : on peut donc interroger des chaines avec différents params de localisation.