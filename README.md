# Pshepishnick - Your Recipe Companion

Pshepishnick is a mobile app for Android that serves as your ultimate recipe companion. Search for, discover, and save recipes for various kinds of food. Registered users can contribute by adding their own recipes, follow other users, and engage by liking and commenting on recipes.

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Recipe Search:** Explore a vast collection of recipes for different types of food.
- **User Registration:** Create an account to access personalized features.
- **Recipe Contribution:** Add your own recipes to share with the community.
- **User Interaction:** Follow other users, like, and comment on their recipes.
- **Bookmarking:** Save your favorite recipes for quick access.

## Screenshots

![Recipe Screen](https://i.imgur.com/5rN2fJP.png)
![Recipe Screen](https://i.imgur.com/UnUnHud.png)
![Recipe Screen](https://i.imgur.com/ogYP7jz.png)

## Installation

To use Pshepishnick on your Android device, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/Pshepishnick.git
    ```

2. Open the project in Android Studio.

3. Build and run the app on your Android device or emulator.

## Firebase Setup

Pshepishnick uses Firebase Firestore for storing recipe data and Firebase Storage for handling recipe images. Follow these steps to set up Firebase for your Pshepishnick app:

### Step 1: Create a Firebase Project

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Click on "Add Project" and follow the setup instructions.

### Step 2: Set up Firebase Firestore

1. In the Firebase Console, navigate to your project.
2. Click on "Firestore Database" in the left-hand menu.
3. Click "Create Database" and choose "Start in test mode" (for now, you can adjust security rules later).
4. Select the appropriate region for your database.

## Firebase Firestore Schema

Pshepishnick uses Firebase Firestore to store recipe data. Follow these steps to set up the Firestore schema for the `Recipe` class:

### Step 1: Define the Recipe Class

In your Android Studio project, you have a `Recipe` class representing the structure of a recipe. Make sure this class has the necessary fields such as `title`, `description`, `preparationDuration`, `difficulty`, and `photoUrl`. This class corresponds to the data you want to store for each recipe.

Here's a simplified example of the `Recipe` class:

```java
public class Recipe {
    private String title;
    private String description;
    private int preparationDuration;
    private int difficulty;
    private String photoUrl;

    // Constructors, getters, and setters
}
```
### Step 3: Set up Firebase Storage

1. In the Firebase Console, navigate to your project.
2. Click on "Storage" in the left-hand menu.
3. Click "Get Started" and follow the setup instructions.
4. Set the default security rules to allow read and write access (you can adjust them based on your app's security needs).

### Step 4: Obtain Firebase Configurations

1. In the Firebase Console, navigate to your project.
2. Click on the gear icon (Project settings) in the top-left corner.
3. Under the "General" tab, scroll down to the "Your apps" section.
4. Click on the Firebase SDK snippet and select "Config."
5. Copy the configurations (apiKey, authDomain, projectId, storageBucket, messagingSenderId, appId).

### Step 5: Integrate Firebase Configurations in Your App

1. Open your Pshepishnick Android Studio project.
2. Navigate to the `app` directory and create a new file named `google-services.json`.
3. Paste the copied Firebase configurations into this file.

Now, your Pshepishnick app is connected to Firebase Firestore and Storage. You can start using these services to store and retrieve recipe data along with associated images.

**Note:** Ensure that you handle sensitive information, such as API keys and configurations, securely and avoid exposing them publicly.

For more details on integrating Firebase with Android, refer to the [Firebase documentation](https://firebase.google.com/docs/android/setup).


## Usage

1. Launch the Pshepishnick app on your Android device.

2. Sign up or log in to your account.

3. Explore recipes, search for your favorite dishes, and discover new ones.

4. Contribute by adding your own recipes.

5. Connect with other users, follow them, like their recipes, and leave comments.

## Contributing

If you'd like to contribute to Pshepishnick, follow these steps:

1. Fork the project.

2. Create a new branch for your feature or bug fix.

3. Make your changes.

4. Submit a pull request.

## License

This project is licensed under the [Apache License 2.0](LICENSE) - see the [LICENSE](LICENSE) file for details.



