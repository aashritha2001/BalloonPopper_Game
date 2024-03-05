# Balloon Popper Game: README

## Introduction

Welcome to Balloon Popper, a fast-paced, engaging game designed to offer players a thrilling experience of popping balloons. With a time limit of 60 seconds, players are challenged to pop as many correct balloons as possible, earning points for accuracy and speed. This document outlines the gameplay mechanics, special features, and development details of Balloon Popper, providing a comprehensive guide for players and developers alike.

## Gameplay Mechanics

### Objective
- Players are tasked with popping balloons that match a specific shape and color indicated at the start of each 60-second round.

### Scoring System
- Correct Pop: Each correctly popped balloon awards the player 1 point.
- Incorrect Pop: Popping the wrong balloon deducts 1 point from the player's score.
- Missed Balloons: At the end of the game, 1 point is deducted for each valid balloon that was not popped.

## Special Features

### Score Board
- The game features a scoreboard that collects and displays the high scores of players, showcasing the top performers.

### Heat Sensor
- An innovative heat sensor feature increases the speed of the balloons when the device's temperature exceeds 33 degrees Celsius. Additionally, balloons change to a darker color to simulate being burned, adding an extra layer of challenge and excitement to the gameplay.

## Development Details

### Features
- **Drawable Class:** Utilized to create and render individual circle and square balloons on the screen.
- **onTouch Event:** Implemented to detect interactions with balloons, enabling the pop mechanic.
- **Multithreading:** Employed to efficiently draw and animate multiple balloons, facilitating their vertical movement across the screen.
- **Heat Sensor:** The game leverages the SensorEvent class to monitor temperature changes. If the device's sensor detects a temperature higher than 33 degrees Celsius, it triggers a change in the balloon's appearance to indicate overheating.
- **Custom View:** A Custom view that manages the game's visual aspects, occupying the bottom two-thirds of the game activity screen. It handles tasks such as recalculating balloon positions, counting down the timer, activating the heat sensor, and processing user interactions.
- **Score Board (AddActivity Class):** This activity allows users to input and submit their scores to the scoreboard. It features real-time validation of the player name, score, and date inputs. The submit button is dynamically enabled or disabled based on the validity of the inputs. Upon submission, a player object containing these details is created and transferred to the main screen for inclusion in the scoreboard. This class is designed without direct file operation capabilities.


## Sample Run Below
https://github.com/aashritha2001/BalloonPopper_Game/assets/75947163/a598b83a-c6c7-44b0-9d01-49eda6e7a531

<img width="532" alt="Screenshot 2024-03-04 at 6 25 31 PM" src="https://github.com/aashritha2001/BalloonPopper_Game/assets/75947163/421523cd-706c-46e4-8904-32ba9161e992">
