# Birthday Reminder - AI Powered

![App Icon]([https://raw.githubusercontent.com/thezayin/AI-Birthday-Reminder/main/core/values/src/main/res/drawable/ic_main.png](https://github.com/thezayin/AI-Birthday-Reminder/blob/main/core/values/src/main/res/drawable/ic_main.png))

A smart and intuitive Android app designed to help users remember birthdays, calculate age, and suggest thoughtful gift ideas with the help of AI. Built with Clean Architecture, MVI (Model-View-Intent), and multi-modular setup, the app leverages Koin for dependency injection and Ktor for network requests. Firebase analytics, crash reporting, and remote configuration for ads ensure a seamless and robust experience.

## Features

- **Add, Update, and Delete Birthdays**: Users can add a birthday with the name, date, and group (work, friend, family, or other). Notifications and alarms are set up based on the user’s preferences.
- **Customizable Notifications**: By default, users will get a notification at 12:00 AM on the selected date, but they can choose any time.
- **Birthday Alarm**: A built-in birthday song plays as an alarm.
- **Upcoming Birthdays**: The home screen displays upcoming birthdays with a countdown of days remaining.
- **Age Calculator**: Users can enter a birthdate and calculate their age in years, months, and days, or input any date to calculate age at that time.
- **Gift Idea Generator**: Users can input details about the recipient (relationship, interests, dislikes, budget) and get personalized gift suggestions from Gemnie AI.
- **Saved Birthdays**: Easy access to a list of saved birthdays, and the option to edit or delete them.
- **Settings**: Customize notification settings, including time and sound, and adjust other preferences.

## UI Flow

1. **Splash Screen**: Welcome screen introducing the app.
2. **Onboarding**: Briefly guides the user through the key features.
3. **Home Screen**: Central hub where users can add birthdays, calculate age, and explore gift ideas.
4. **Age Calculator**: Calculates age based on inputted birthdate and current date.
5. **Saved Birthdays**: List of all birthdays with the option to manage them.
6. **Gift Idea Generation**: Users can generate personalized gift ideas based on their inputs.
7. **Birthday Reminder Notifications**: Custom notifications that alert users at the desired time.

## Architecture

The app follows **Clean Architecture** principles, ensuring separation of concerns for better scalability and maintenance. The app structure is as follows:

- **Model**: Manages the data, including birthday entries, user preferences, and external API requests (Gemnie for gift ideas).
- **View**: Represents the UI, built with Jetpack Compose for declarative UI elements.
- **Intent**: Handles user actions such as adding/editing birthdays, calculating age, and generating gift ideas.

**State Management** is handled with **StateFlow** to ensure UI consistency, and **MVI** architecture ensures clarity in user interactions.

## Dependencies

- **Jetpack Compose**: For building modern and responsive UIs.
- **Koin**: For efficient dependency injection.
- **Ktor**: For network requests (sending gift idea requests to Gemnie).
- **Firebase Analytics & Crashlytics**: For tracking app performance and user engagement.
- **Remote Config**: For managing ad settings and other app configurations dynamically.
- **TensorFlow**: For AI-powered features in the app.

## How to Use

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android device.
4. Add birthdays, set notification times, and generate personalized gift ideas.
5. Enjoy a smooth, interactive experience with AI-based features.

## Customization

- You can modify the **notification time** and **sound** settings based on user preferences.
- Adjust the **AI model parameters** for generating more personalized gift suggestions.
- Modify the **gift generation fields** to include additional user-specific data for more accurate suggestions.

## Getting Started

- Clone the repository and set up the project in Android Studio.
- Make sure to configure Firebase services (Analytics, Crashlytics, etc.).
- Add your personal app icon, as shown above, with rounded corners to match the app’s aesthetic.

## Contact

- **Email**: [zainshahidbuttt@gmail.com](mailto:zainshahidbuttt@gmail.com)
- **WhatsApp**: [+923033009802](https://wa.me/923033009802)

## License

This project is licensed under the MIT License.
