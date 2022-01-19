# Group Project - *Macro_Counter*

Macro_Counter will help users track their daily calories of protein, carbs, fats and other micro nutrients

## User Stories

The followingfunctionality is completed:

- [x] Finish Firebase Search Fragment. (Search Bar with Firebase)
- [x] UI/UX fixes.
- [x] Add Calorie Counter to Profile Fragment. (User info)
- [x] Integrate builds (all of current activities and fragments)

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src="https://github.com/Codepath-Group-7/Macro_Counter/blob/master/Demo.gif" width=250><br>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview

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
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts
          ```swift
          private void queryPosts() {
              ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
              query.include(Post.KEY_USER);
              query.setLimit(20); // if we want to set limit
              query.addDescendingOrder(KEY_CREATED_KEY);
              query.findInBackground(new FindCallback<Post>() {
                  @Override
                  public void done(List<Post> posts, ParseException e) {
                      if (e != null) {
                          Log.e(TAG, "Issue with getting posts", e);
                          return;
                      }
                      for (Post post : posts) {
                          Log.i(TAG, "POST: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                      }
                      allPosts.addAll(posts);
                      adapter.notifyDataSetChanged();
                  }
              });
          }
          ```
   - Login Screen
      - (Read/Validate) Query user objects with user input id/pass to check is valid
          ```swift
          private void loginUser(String username, String password) {
              Log.i(TAG, "Attempting to login user " + username);
              ParseUser.logInInBackground(username, password, new LogInCallback() {
                  @Override
                  public void done(ParseUser user, ParseException e) {
                      if (e != null){
                          Log.e(TAG, "Issue with login", e);
                          return;
                      }
                      goMainActivity();
                      Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                  }
              });
          }
          ```
      - (Create/POST) Create a new account
          ```swift
          private void signUpUser(String username, String password) {
              Log.i(TAG, "Attempting to login user " + username);

              // Create the ParseUser
              ParseUser user = new ParseUser();
              // Set core properties
              user.setUsername(username);
              user.setPassword(password);

              user.signUpInBackground(new SignUpCallback() {
                  public void done(ParseException e) {
                      if (e == null) {
                          // Hooray! Let them use the app now.
                          goMainActivity();
                          Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                      } else {
                          // Sign up didn't succeed. Look at the ParseException
                          // to figure out what went wrong
                          Log.e(TAG, "Issue with login", e);
                          return;
                      }
                  }
              });
          }
          ```
   - Detail Screen
      - (Read/GET) Query selected object’s nutritional value
          ```swift
          private void queryInfo() {
              ParseQuery<Info> query = ParseQuery.getQuery(Info.class);
              query.include(Info.KEY_NAME);
              query.addDescendingOrder(KEY_ID_KEY);
              query.findInBackground(new FindCallback<Info>() {
                  @Override
                  public void done(List<Info> infos, ParseException e) {
                      if (e != null) {
                          Log.e(TAG, "Issue with getting information", e);
                          return;
                      }
                      for (Info info : infos) {
                          Log.i(TAG, "INFO: " + infos.getDescription() + ", Calories: " + info.getInfo().getCalories());
                      }
                      allInfos.addAll(infos);
                      adapter.notifyDataSetChanged();
                  }
              });
          }
          ```
   - Search Screen
      - (Read/Get) Query all food items
          ```swift
          protected void queryFoods() {
              ParseQuery<Food> query = ParseQuery.getQuery(Food.class);
              query.include(Food.KEY_foodName);
              query.setLimit(20);
              query.addDescendingOrder(Food.KEY_CREATED_KEY);
              query.findInBackground(new FindCallback<Food>() {
                  @Override
                  public void done(List<Food> foods, ParseException e) {
                      if (e != null) {
                          Log.e(TAG, "Issue with getting foods", e);
                          return;
                      }
                      for (Food food : foods) {
                          Log.i(TAG, info.getInfo().getCalories());
                      }
                      allFoods.addAll(foods);
                      adapter.notifyDataSetChanged();
                  }
          ```
   - Profile Screen
      - (Read/Get) Query get user's last 20 posts
          ```swift
          protected void queryPosts() {
                  ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                  query.include(Post.KEY_USER);
                  query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
                  query.setLimit(20);
                  query.addDescendingOrder(Post.KEY_CREATED_KEY);
                  query.findInBackground(new FindCallback<Post>() {
                      @Override
                      public void done(List<Post> posts, ParseException e) {
                          if (e != null) {
                              Log.e(TAG, "Issue with getting posts", e);
                              return;
                          }
                          for (Post post : posts) {
                              Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                          }
                          allPosts.addAll(posts);
                          adapter.notifyDataSetChanged();
                      }
          ```



- Using Food Database API: https://developer.edamam.com/food-database-api
