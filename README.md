# Hangman Game

This is a desktop-based Hangman game implemented in Java using Swing for the GUI and MySQL for database connectivity (initial testing only). The game randomly selects a word from predefined categories stored in a local file, and the user has to guess the word by selecting letters.

## Features

- GUI-based game using Java Swing  
- Custom font and themed UI with images  
- Word categories and words loaded from a local file (`data.txt`)  
- Letter-by-letter guessing with visual feedback (green/red buttons)  
- Hangman image progression with incorrect guesses  

## How It Works

- On launch, a word is randomly selected from a category.
- The word is hidden using `*`, and users guess by clicking letter buttons.
- Correct guesses reveal the letters; wrong guesses update the hangman image.
- The game ends in success if the user reveals the whole word or failure after 6 incorrect guesses.
- A result dialog allows the player to restart or quit the game.

## Requirements

- Java 8 or above  
- MySQL

## Setup Instructions

1. **Clone this Repository By: ```git clone https://github.com/ishita3075/Hangman_Game && cd Hangman_Game```**

2. **Compile and Run**

   ```
   javac -d bin -sourcepath src src/App.java
   java -cp bin App
   ```
