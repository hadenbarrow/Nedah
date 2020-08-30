# Nedah
Nedah is an AI that plays a game called Congress Chess that was originally made for CSc 180.

# Download and Compile for Windows

1. Download the zip file. 

![download](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/download.png)

2. Open a command prompt instance.

3. In your command prompt instance, navigate to "..Nedah-master/Nedah/src/main" 

![navigate](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/navigateToMain.png)

4. Type "javac \*.java" to compile all the .java files. 

![compile](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/compile%20.java%20files.png)

5. Navagate back to "Nedah-master/Nedah/src".

6. Type "java main.CongressChess" to run an instance of Nedah. 

![run](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/runTheProgram.png)

7. Congrats! You're ready to play Congress Chess against Nedah.

# Rules & How to play

Congress Chess is very similar to regular chess, with a few distinct differences. 

This is a board-game with chess-like pieces, in which each player has TWO kings.
Victory is achieved by capturing both kings.  Other pieces are pawns, bishops,
and horses. The board is divided in half (vertically), and all of the horse/bishop
pieces are horses on the left side of the board, and bishops on the right side.
That means that the type of piece changes if it crosses the dividing line.
Also, the board is divided in half horizontally, and pieces in the forward half
are allowed to capture backwards, whereas pieces in the back half are not.

- Pawns can move forward by 1 if not blocked by another peice. They capture diagonally only.

- Bishops move exactly as they do in regular chess except: If they are on the back half of the board (the half closest to the player they belong to) they are NOT allowed to capture backwards. If they are on the forward half of the board (the half closest to the player they do not belong to) they are allowed to capture backwards.

- Knights move exactly as they do in regular chess and have the same restriction as above.

- Kings are only allowed to move sideways by 1. The left king can only move left and the right king can only move right.

Perhaps the most intersting aspect of Congress Chess is that Bishops and Knights change roles depending on the vertical half of the board they are on. Knights are on the left and Bishops are on the right. If a Bishop crosses to the left side, it becomes a Knight, and vice versa.

The initial position is:

      H H - K K - B B (COMPUTER)
      - P P P P P P -
      - - - - - - - -
      - - - - - - - -
      - p p p p p p -
      h h - k k - b b  (HUMAN)
      
     
When you run Nedah you will be prompted as follows:
![run](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/runTheProgram.png)

When Nedah makes a move you will see a board displayed.

![nedahsmove](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/nedahsMove.png)

A "ply" is equivalent to "looking one move ahead" for each piece Nedah could possibly move. In this case, Nedah checked every possible move, 7 moves deep. As the number of possible moves dwindles and the game progresses, Nedah can search more and more moves ahead. 

Nedah is set to only search for 5 seconds. Allowing it more time would allow it to search deeper.

When it's the players turn to move, a list of valid moves will be displayed for you to choose from. 

![playermoves](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/playersMove.png)

This is what it looks like when you lose to Nedah (you will lose):

![youlost](https://github.com/hadenbarrow/Nedah/blob/master/readmeImages/nedahWins.png)

Thanks for reading! This was by far my favorite project during my entire time in university. Huge thanks to Scott Gordon who teaches CSc 180 (among other courses) at Sac State.

If you want to see a list of all the different wacky versions of chess he creates for this course (he creates a new one every semester) you can check it out here: https://athena.ecs.csus.edu/~gordonvs/

You'll also notice that he keeps track of the top performing programs each semester. You can see my name there listed as "Nedah" (Barrow)

