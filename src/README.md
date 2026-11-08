# Connect-x

A customizable Connect-4 game that allows players to change the board size, win, conditions, and ability to have 3 or more players.

## The Problem

Connect-4 by itself is too rigid to have much fun with as a party game. Without a way to change up the game, it gets boring.

## How It Works

### Initial Setup
When the program starts or players want to restart the game, it asks the user to input the desired size, win count and number of players they want in the game. If any of the values are invalid, the question will be asked again. When setup is complete, the game starts. 

### Game Loop
For each player, the program will ask for an input, which would be either "exit", "restart", or a column. If something else is provided or the column chosen is invalid or full, it will ask again. When a valid command is given, the program will carry it out(i.e exiting or restarting the program or dropping a token in the column). Upon dropping a token, it will display the current board after adding the new token. The program will then check if a win or draw occurred. If not, the next player gets their turn.

### Winning and Drawing
After each drop, the program will then check for either a win or a draw. If neither are found, the game will continue, moving on to the next player. If a game is won (horizontal, vertical, or diagonal) or comes to a draw (board is filled), the program will stop the game and display the outcome. Finally the program asks if the players want to restart the game. If yes, it will return to the setup phase.


## Tech Stack

- **Language:** Java

## Setup

```bash
git clone https://github.com/ejiang1223/Connect-x
cd Connect-x
cd src
javac ConnectX.java
```

## Usage

```bash
java ConnectX
```

## Roadmap

- [ ] Multiple Boards in the same game
- [ ] Java Swing to add GUI and replace terminal
- [ ] Java awt for interactability
- [ ] A move history
- [ ] Feature to choose how many moves a player gets per turn(for high win con/board count games)
