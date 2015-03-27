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
( idem pour les 3 configs )
```

