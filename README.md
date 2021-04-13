# Macro_Counter

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Macro Counter: Helps users track their daily calories of protein, carbs, fats and other micro nutrients

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Fitness
- **Mobile:** Android mobile application
- **Story:** Allows user to keep track of their daily macros/calorie in-takes
- **Market:** Anyone who's trying to become more fit. Ability to keep track of eaten foods throughout the day and/or week.
- **Habit:** Users can post with every meal, certain interval of hours, or by end of day.
- **Scope:** application should start as a calculator styled calorie tracker. Allowing for the subtracting from daily set limits for per meal/snack. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Login/Logout
* Forget and Reset Password
* Profile
* Home
* Seach
* some ingredient macro values

**Optional Nice-to-have Stories**

* Personal Recipe List
* Goal to plan or lose weight
* ability for users to add their own ingredients/values/recipes
* ability for users to track their previous days macros/history

### 2. Screen Archetypes

* Login Screen
   * Register to login
   * Forgot my password
* Home Screen
   * Daily Intake
* Profile Screen
   * Past macro information
   * Edit profile

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Daily Intake List
* Search
* Profile & Settings

**Flow Navigation** (Screen to Screen)

* Login
   * [list screen navigation here]
* [list second screen here]
   * [list screen navigation here]

## Wireframes
<img src="https://github.com/Codepath-Group-7/Macro_Counter/blob/master/Wireframe.png" width=600>
<img src="https://github.com/Codepath-Group-7/Macro_Counter/blob/master/wireframeLoginScreens.PNG" width=600>

## Schema 
### Models
**Model: Post**

| Property  | Type            | Description                                    |
|-----------|-----------------|------------------------------------------------|
| postId    | String          | unique id for the user post (default field)    |
| author    | Pointer to User | image author                                  |
| image     | File            | image that user posts                          |
| caption   | String          | caption by author                              |
| createdAt | DateTime        | date when post is created (default field)      |


**Model: Profile**
| property   | Type   | Description                |
|------------|--------|----------------------------|
| userId     | String | userId used for profileId  |
| image      | File   | Image for profile picture  |
| bio        | String | Used as a bio for the user |
| username   | String | User's username            |
| weight     | int    | User's weight              |
| height     | int    | User's height              |
| weightGoal | int    | User's weight goal         |

**Model: Food item** (NOTE: only calories and name will show up in search results)
| property      | Type | Description                 |
|---------------|------|-----------------------------|
| foodId        | int  | unique id for each food     |
| name          | String  | name of food             |
| calories      | int  | calories each food contains |
| proteins      | int  | proteins each food contains |
| carbohydrates | int  | carbs each food contains    |
| fats          | int  | fats each food contains     |


| property         | Type                | Description                        |
|------------------|---------------------|------------------------------------|
| Calorie Goal     | int                 | Calorie Goal for the User          |
| Calories Eaten   | int                 | Total Calories eaten today         |
| Food Items Eaten | Array of Food Items | List of each food item eaten today |
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- Using myfitnesspal API: https://myfitnesspalapi.com/
