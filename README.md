# AndrewHood v1.1

This is a project for my Programming, Algorithms and Data Structures (PAOO) class at the university. 

## Project Overview

The project is a game developed in Java. It features various game states such as PLAYING, MENU, PAUSE, OPTIONS, QUIT, COMPLETED, and SETTINGS. The game includes different levels, each with its own set of enemies and coins. The player's progress and current level are saved and loaded from a database.

## Key Features

- Multiple game states for a comprehensive gaming experience.
- Different levels with unique enemies and coins.
- Player's progress and current level are saved and loaded from a database.
- Interactive menu with buttons for different game states.

## Code Structure

The code is organized into different packages for easy navigation and maintenance. Here are some of the key packages and classes:

- `Game.gamestates`: Contains the different game states and their implementations.
- `Game.Levels`: Contains the `Level` class which represents a level in the game.
- `Game.Database`: Contains the `DatabaseHandler` class for saving and loading game state.
- `Game.UI`: Contains classes for different UI elements like `MenuButton`.
- `Game.entities`: Contains classes for different game entities like `Ghoul`, `Skeleton`, and `Coin`.

## How to Run

To run the game, compile and run the `Main` class in the `Game.Main` package.

## Future Work

Future updates will include more levels, enemies, and power-ups. Stay tuned!

## License

This project is licensed under the MIT License.

## Acknowledgements

I would like to thank my PAOO class professor and classmates for their support and feedback during the development of this project.
