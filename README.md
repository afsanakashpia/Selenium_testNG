# DailyFinance Automation Suite

This is an end-to-end automation testing project for [DailyFinance](https://dailyfinance.roadtocareer.net) built using **Java**, **Selenium WebDriver**, and **Gradle**. It covers key user flows including registration, password reset (both positive and negative cases), login, item addition, profile updates (email change), and login verification with old and new credentials.

## ✨ Features Tested

- ✅ User Registration (with email verification)
- ✅ Invalid Password Reset (negative test)
- ✅ Valid Password Reset (positive test)
- ✅ Login with updated password
- ✅ Item Addition to User Profile
- ✅ Profile Email Update
- ✅ Login with new email, and validation that old email no longer works

## 📁 Project Structure

```bash

src/
└── test/
    └── java/
        ├── config/
        │   ├── RegisterDataset
        │   ├── SetUp
        │   └── UserModel
        ├── Pages/
        │   ├── userPage
        │   └── UserController
        ├── TestRunner/
        │   ├── userPageRunner
        │   └── AdminPageRunner
        └── utils/
            └── utils

resources/
├── config.properties
├── users.json
└── registerdata.csv

```

## 🧪 Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Gradle
- Google OAuth 2.0 for Gmail access
- XPath / CSS Selectors for interaction

## 🚀 Getting Started

### Prerequisites

- jdk 17
- Gradle dependencies imported
- Google Chrome + ChromeDriver (match your browser version)

### 🚀 Steps it follows

 - Registers a new user using a dynamic Gmail.

 - Reads the verification email using Gmail API with OAuth.

 - Handles both failed and successful password reset attempts.

 - Logs in, adds an item, and updates the profile email.

 - Verifies login behavior with the old and new email addresses.
   
 - Admin login by passing Admin credential throug terminal.

 - Register users from a csv file

- Search for user by admin

### Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/afsanakashpia/Selenium_TestNG.git
   cd Selenium_testNG

### How to Run   
2.Run it using the following command on terminal and pass Admin credentials through command
 
  ``` gradle clean test -Pemail="Admin_username" -Ppassword="password" ```

### Automation Video
https://drive.google.com/file/d/1Tl1aDd9nD3IQQGG3v27upRUhXh6MhRYM/view?usp=sharing


### Test Case
https://docs.google.com/spreadsheets/d/1pofSkcEdcfKGoSCQtsLcyER0q1iroB1wihvSOD_bkzo/edit?usp=sharing

### Allure Report
<img width="935" alt="Allure" src="https://github.com/user-attachments/assets/6bbc94bb-daab-45b7-a3c9-0cf94669711a" />




  
   

   


      


