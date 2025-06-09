
# Hangman Game

This is a desktop-based Hangman game implemented in Java using Swing for the GUI and MySQL for database connectivity. The game randomly selects a word from predefined categories stored in a local file, and the user has to guess the word by selecting letters.

## Features

- Interactive GUI with category-based word guessing  
- Words loaded from external file (`data.txt`)  
- Hangman image progression for incorrect guesses  
- Score tracking and leaderboard system with MySQL  
- Custom fonts, themes, and visual feedback using colored buttons


## Screenshots

### 1. Name Input Dialog
![Image](https://github.com/user-attachments/assets/d390d5f8-3f7a-4465-8eae-a7f280f229ee)

### 2. Gameplay UI
![Image](https://github.com/user-attachments/assets/7c4edd5b-96ce-4588-a23c-a649f9e72a73)

### 3. High Score Dialog
![Image](https://github.com/user-attachments/assets/884e00a9-b7b6-4e71-a95d-5f0732d5d3a7)


## How It Works

1. On launch, the game prompts the player to enter their name.
2. A random word from a category (e.g., Countries) is chosen.
3. The player guesses letters via button clicks:
   - The word is hidden using `*`.
   - Correct guesses reveal the letters.
   - Incorrect guesses change the hangman image.
4. After either winning or losing:
   - The score is updated in the MySQL database.
   - A dialog appears for restarting or exiting.
5. Players can view the **Top 10 Scores** from the "High Scores" button.


## Requirements

- Java 8 or higher  
- MySQL Server  
- JDBC Driver


##  Setup Instructions

1. **Clone this Repository By: ```git clone https://github.com/ishita3075/Hangman_Game && cd Hangman_Game```**

2. **Compile and Run**

   ```
   javac -d bin -sourcepath src src/App.java
   java -cp bin App
   ```

### 3. Configure MySQL
Make sure you have a `scores` table in a `Hangman` database with the following schema:
```sql
CREATE DATABASE IF NOT EXISTS Hangman;
USE Hangman;

CREATE TABLE IF NOT EXISTS scores (
    player_name VARCHAR(50) PRIMARY KEY,
    score INT DEFAULT 0
);
```
