# PsykoComVCLogin

Le projet PsykoComVCLogin est un projet que j'ai débuté en 2014 et a été un de mes premiers projets.

Le projet consiste à créer un logiciel similaire à Skype qui permet la communication entre plusieurs personnes en ligne,
composé d'un système de serveur hébergeable sur machine personnelle et des clients (fenêtres) qui se connectent directement à celui-ci.

Cela permettait à un groupe du nom de PsykoCom d'héberger des sessions (conférences ou autres dans le futur) privées entre contacts.
La partie de sécurité qui encrypte les données n'a pas été gérée et le projet a été mis en pause depuis 2015.
Pour cause, il est instable et non fonctionnel.

Le projet contient principalement les fichiers partie client et une partie côté serveur.
Un système d'inscription sur base de données locale est possible par le biais de sqlite, permettant l'utilisation de comptes entre utilisateurs.

Le programme est basé sur l'utilisation des bilbiothèques java.awt et javax.swing pour permettre l'interface graphique.

Le logiciel constitue un ensemble de sous logiciels :


La fenêtre de login ou de connexion au serveur :

![alt text](https://github.com/zentsugo/PsykoComVCLogin/blob/main/psykocom-login.PNG?raw=true)


Le client qui comporte des sections différentes pour discuter, voir les membres connectés sur le serveur, etc :

![alt text](https://github.com/zentsugo/PsykoComVCLogin/blob/main/psykocom-client.PNG?raw=true)


Le serveur qui consiste plus en un ensemble de connexions et échanges avec plusieurs flux qui composent les clients qu'en une fenêtre en soi :

![alt text](https://github.com/zentsugo/PsykoComVCLogin/blob/main/psykocom-server.PNG?raw=true)


La fenêtre pour s'inscrire dans la base de données locale de l'hébergeur du serveur :

![alt text](https://github.com/zentsugo/PsykoComVCLogin/blob/main/psykocom-register.PNG?raw=true)
