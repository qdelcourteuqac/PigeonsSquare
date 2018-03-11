# Pigeons Square

Devoir 2 pour 8INF957 (POO) à l'UQAC.

## Auteurs :

- Ramzi AMEUR
- Quentin DELCOURTE

## Simulation de pigeons

Profiter de cette simulation pour jouer avec des pigeons avec deux actions possibles:

- Leur donner de la nourriture (cycle de vie de 5 sec, au-delà la nourriture n'est plus fraiche et disparait)
- Leur faire peur en leur lançant une pierre (cycle de vie de 6 sec, au-delà les pigeons ne seront plus éffrayé)

Et voyez leur réaction !

### Spécifications

- Chaque pigeon devra s'éxecuter dans un Thread.
- Utiliser JavaFx pour l'application.
- Gérer le multi-threading entre chaque cellule vivante et l'affichage JavaFx.
- Ajouter des éléments (ex: nourriture, pierre) sur le plateau de jeu en pleine simulation.

NB: Les pigeons ont tous le même comportement. Pour différencier les comportements des différents pigeons
il vous suffit de surcharger les méthodes de décision. 