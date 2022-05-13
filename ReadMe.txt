//READ ME FILE

Joanes De Miguel, Asier Mayoz, Lukas Arana and Alexander Diez; Team Lagu


To do this third iteration of the bet&ruin project, we've done 4 tasks, which are Remove bet, publish results, forgot password and remove event as well as creating an installer In the process, we also updated some of the domain classes. Then, as an extra we implemented the automatic creation
executable files for Linux and Windows via Github actions. (we didn't have any Mac where we could test our executable).

We encountered various problems while working on this iteration.
Making the executable was quite a pain. We got the base files from the rklaim project and we configure them for our project. To do that, we needed
to refactor our code and create different packages. Then, cofigured the pom.xml, config file and build file for each OS. All these changes require we pushed
each time, that caused us to run out of github actions. That is why you could find the executable files in https://github.com/LukasArana/BetAndRuin2 .
Which is the exactly same project in another github.
We also had some problems when changing the data structure of the money movements; the amount of money wasn't stored. In the end it was a persistance error and what we needed to do was to put a @OneToMany label on the movement list.

NOTE: Before executing the program the user must copy the config folder into their $HOME directory.
NOTE: We found out that in some cases windows defender identifies this program as a trojan. Trust us, we don't know how to make a trojan.

Lost password use case was easier than expected, once discovered how to send an email via smtp the rest was straight forward.

(To do this second iteration of the bet&ruin project, we've created 3 new use cases: Deposit money, place a bet and view money movements,
as well as changing everything from the first sprint to JavaFX and SceneBuilder. In the process, we also updated some of the domain
classes when needed.
One of the problems we encountered was how to store the money movements of a user. In the end, we decided that we could keep track
of all the information of a movement using lists. Also, at first we didn't know how to localize the labels that change their text
during execution time, but we managed to do that using the getString method of the ResourceBundle class. Apart from that, we had some
problems with setting the language and ended up changing the xml file directly and having to restart the program to get the desired
result. Finally, we also solved all the problems mentioned in the review of the first sprint including not being able to commit to the
repository.)

(To do this first iteration of the bet&ruin project, we've done 6 tasks, which are create questions, browse questions, Sign-up, Log-in,
create a new event and set a fee. 
We tried to do as simply as possible version of this 1st iteration. We've changed the version Juananpe, as we used a similar main menu 
aspect, just adding more options. 
Our main problem it's been that in the "Event" class the event number didn't work so we have used a 
random number creator. In the other hand, we had some problems with github, principally we had errors with the conflicts and we were not
able to solve the problems with github. Finally, setFee option was so much harder than we thought. In this option which question and which
event were selected it's been a problem for us. It also was a problem to add the fee to the database. But we finally solved those problems
by creating new atributes and saving index's of which question and which event was selected.)
