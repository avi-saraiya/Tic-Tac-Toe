# Tic Tac Toe (Java)
This is a simple command-line Tic Tac Toe game implemented in Java. 
The game allows for multiple modes of play, including Player vs Player, Player vs Computer, and Computer vs Computer. 
Players can choose the grid size and win length, providing a customizable gaming experience.
## Features
<b> Modes of Play</b><br>
* Player vs Player
* Player vs Computer
* Computer vs Computer

<b>Customizable Settings</b><br>
* Grid size (between 3 and 20)
* Win length (number of consecutive marks required to win)

<b>Random Player Selection</b><br>

* The game randomly selects which player goes first

## How to Run
1. <b>Clone the repository</b><br>

ADD IMAGE HERE<br>

2. <b>Compile the Java files:</b> Navigate to the directory containing the .java files and compile them using the javac command<br>

ADD IMAGE HERE<br>

3. <b>Run the Game:</b> After compiling, run the game using the following command<br>

ADD IMAGE HERE<br>

4. <b>Play the Game:</b> Follow the on-screen prompts to play the game. You will be prompted to select a grid size, win length, and game mode.
The game will then allow you to make moves as either a human or a computer.
## Classes Overview
### Board
* Checks if the board is full.
* Adds a move to the board.
* Checks for a winner based on the given win length.
### Player (Abstract Class)
* Represents a player (either human or computer).
* Defines the structure for making moves and handling counters (X or O).
### HumanPlayer
* A subclass of the Player class.
* Has the additional functionality of prompting the player to play.
### ComputerPlayer
* A subclass of the Player class.
* Generates a random move on the board upon the Computer's turn.
### Counter
* Represents the player's counter (X or O).
### Move
* Represents a move made by a player, including the counter and the position on the board.
### Game
* The main class that controls the flow of the game. It handles player selection, turn management, and win condition checking.
## Contributing
Feel free to fork this repository, open issues, or submit pull requests if you'd like to contribute improvements or fixes.
## License
This project is licensed under the MIT License
