# Birthday Reminder - AI Powered

![App Icon](https://raw.githubusercontent.com/thezayin/AI-Birthday-Reminder/main/core/values/src/main/res/drawable/ic_main.png)

[![Kotlin](https://img.shields.io/badge/Kotlin-Compose-purple)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Android-blue)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%7C%20MVI-blue)](https://developer.android.com/topic/libraries/architecture/guide)
[![DI](https://img.shields.io/badge/DI-Koin-blue)](https://insert-koin.io/)
[![Networking](https://img.shields.io/badge/Networking-Ktor-orange)](https://ktor.io/)

A smart and intuitive Android app designed to help users remember birthdays, calculate age, and suggest thoughtful gift ideas with the help of **Gemnie AI**. Built with **Clean Architecture**, **MVI (Model-View-Intent)**, and a **multi-modular setup**, the app leverages **Koin** for dependency injection and **Ktor** for network requests. Firebase analytics, crash reporting, and remote configuration ensure a seamless and robust experience.

---

## ✨ Features

This application offers a comprehensive suite of functionalities to make remembering birthdays effortless and meaningful:

*   **Comprehensive Birthday Management:**
    *   **Add & Organize Birthdays:** Easily add new birthdays with name, date, and assign to specific groups (work, friend, family, or other).
    *   **Edit & Delete:** Simple options to update or remove birthday entries.
*   **Intelligent Notifications & Alarms:**
    *   **Customizable Reminders:** Receive notifications at a customizable time (default 12:00 AM) on the selected date.
    *   **Birthday Alarm:** An optional built-in birthday song plays as an alarm to ensure no celebration is missed.
*   **Intuitive Home Screen Dashboard:**
    *   **Upcoming Birthdays:** Displays a clear list of upcoming birthdays with a handy countdown of days remaining.
*   **Advanced Age Calculator:**
    *   **Precise Age Calculation:** Input a birthdate to calculate exact age in years, months, and days.
    *   **Historical Age Query:** Input any past or future date to determine the age at that specific time.
*   **AI-Powered Gift Idea Generator (Powered by Gemnie AI):**
    *   **Personalized Suggestions:** Input details about the recipient (relationship, interests, dislikes, budget) to receive tailored gift suggestions.
    *   **Intelligent Recommendations:** Leverages AI capabilities to provide creative and relevant gift ideas.
*   **Organized Saved Birthdays:**
    *   Easily access and browse a dedicated list of all saved birthday entries.
*   **Flexible Settings:**
    *   **Notification Control:** Customize notification settings, including reminder time and sound preferences.
    *   Adjust other general app preferences.

---

## 📱 UI Flow Overview

The app's user interface is designed for intuitive navigation and ease of use:

1.  **Splash Screen**: A welcoming screen initiating app launch and background setup.
2.  **Onboarding**: Guides new users through the app's core functionalities.
3.  **Home Screen**: The central hub displaying upcoming birthdays, and offering quick access to add birthdays, calculate age, and generate gift ideas.
4.  **Age Calculator**: A dedicated screen for performing age calculations.
5.  **Saved Birthdays**: A list view where users can manage all their stored birthday contacts.
6.  **Gift Idea Generation**: An interactive interface where users input preferences and receive AI-generated gift suggestions.
7.  **Birthday Reminder Notifications**: System-level notifications designed to alert users at their chosen reminder times, often integrated with a distinctive alarm.

---

## 🏗️ Architecture

The Birthday Reminder app is engineered following **Clean Architecture** principles, ensuring a robust, scalable, and maintainable codebase. The UI leverages the **Model-View-Intent (MVI)** pattern for predictable state management.

### Layered Structure

1.  **Domain Layer:**
    *   **Purpose:** Encapsulates the core business logic, application-specific rules, and common models. It's platform-independent.
    *   **Components:** Data Models (e.g., `Birthday`, `GiftIdea`), Repository Interfaces (e.g., `BirthdayRepository`, `GiftRepository`), and Use Cases (e.g., `AddBirthdayUseCase`, `GenerateGiftIdeaUseCase`).

2.  **Data Layer:**
    *   **Purpose:** Implements the repository interfaces defined in the Domain layer, handling data retrieval and storage. It interacts with various data sources.
    *   **Components:** Repository Implementations, Data Sources (Local: **Room Database** DAOs; Remote: **Ktor API services** for AI, **Firebase Remote Config** for dynamic data). Mapper functions handle conversions between data source-specific entities and domain models.

3.  **Presentation Layer:**
    *   **Purpose:** Manages the user interface and user interactions.
    *   **Components:**
        *   **View Models (using `StateFlow`):** Hold the UI state and process user `Intent`s, orchestrating calls to the Domain Layer. State updates are exposed via `StateFlow` for reactive UIs.
        *   **Composables (Jetpack Compose):** Render the UI elements based on the state observed from View Models, and emit user `Intent`s (actions) back to the View Model.

### MVI Pattern

The **MVI (Model-View-Intent)** pattern is explicitly adopted within the Presentation layer to achieve:

*   **Unidirectional Data Flow:** Strict flow of data (User Intent -> View Model -> State -> UI).
*   **Predictable State:** The UI is a pure function of state, making it easy to understand and debug.
*   **Clear Actions:** User interactions are modeled as explicit `Intent` objects.

### Multi-Modular Setup

The project is structured into multiple Gradle modules for better organization, separation of concerns, and build performance:

*   **`core:data`**: Contains data source implementations and repository implementations.
*   **`core:domain`**: Houses core business logic, models, and use cases.
*   **`core:values`**: Shared resources like strings, drawables, fonts.
*   **`feature:add_birthday`**: Manages the logic and UI for adding and editing birthday entries.
*   **`feature:age_calculator`**: Handles age calculation logic and UI.
*   **`feature:gift_idea`**: Encapsulates AI-powered gift idea generation.
*   **`feature:home`**: The main application dashboard.
*   **`feature:settings`**: Manages user preferences and app settings.
*   **`feature:splash`**: Handles app startup, onboarding, and splash screen.
*   **`app`**: The main application module, integrating all feature modules and configuring global settings.

---

## 🛠️ Key Technologies & Libraries

This application is built with a modern Android tech stack, leveraging popular and robust libraries:

*   **Platform:** Android Native
*   **Language:** **Kotlin**
*   **UI Framework:** **Jetpack Compose** (Declarative UI)
*   **Dependency Injection:** **Koin** (Lightweight DI framework)
*   **Persistence:** **Room Database** (for local data persistence like birthdays)
*   **Networking:** **Ktor Client** (Asynchronous HTTP requests, used for Gemnie AI API)
*   **AI Integration:** **Gemnie AI (via Ktor)** for personalized gift suggestions. *(While TensorFlow is mentioned, if the direct AI calls are via Ktor to Gemnie, it's the primary AI interaction. TensorFlow might be for on-device aspects if any).*
*   **Firebase Ecosystem:**
    *   **Firebase Analytics:** For tracking user engagement and events.
    *   **Firebase Crashlytics:** For real-time crash reporting.
    *   **Firebase Remote Config:** For dynamic management of app configurations (e.g., ad settings).
*   **Notification Management:** **Android Notification API** with **AlarmManager** for birthday reminders.
*   **Image Loading:** **Coil Compose** (Asynchronous image loading)
*   **Animations:** **Lottie Compose** (Animated UI elements)
*   **Asynchronous Operations:** **Kotlin Coroutines** & **Flows**
*   **Build System:** **Gradle** (Kotlin DSL)

---

## 🚀 Getting Started

To get a local copy of the project up and running:

### Prerequisites

*   **Android Studio** (latest stable version recommended)
*   **Java Development Kit (JDK) 11** or higher
*   An **Android device or emulator** running API 21 (Android 5.0 Lollipop) or higher.

### Installation & Build

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/thezayin/AI-Birthday-Reminder.git
    cd AI-Birthday-Reminder
    ```

2.  **Open in Android Studio:**
    *   Launch Android Studio and select `File > Open`.
    *   Navigate to the cloned `AI-Birthday-Reminder` directory and click `Open`.

3.  **Sync Gradle Project:**
    *   Android Studio will automatically try to sync the Gradle project. If it doesn't, click the `Sync Project with Gradle Files` button (🐘 icon) in the toolbar.

4.  **Firebase & Gemnie AI Setup:**
    *   **`google-services.json`:** For Firebase Analytics, Crashlytics, and Remote Config, place your `google-services.json` file in the `app/` directory. Follow the official [Firebase documentation](https://firebase.google.com/docs/android/setup) for detailed setup.
    *   **Gemnie AI API Key:** If a Gemnie AI API key is required and not dynamically fetched via Firebase Remote Config, ensure it's configured as a `buildConfigField` in your `build.gradle.kts` files or through secure means.

5.  **Run the application:**
    *   Select the `app` module in the Gradle pane.
    *   Choose your desired device/emulator from the run configurations dropdown.
    *   Click the `Run 'app'` button (▶️) to build and install the application.

### Customization Tips

*   **Notification Settings:** Modify the notification logic within the relevant modules (`feature:add_birthday` or `feature:settings`) to adjust default times or reminder types.
*   **AI Model Parameters:** If your AI integration allows, adjust input parameters for the Gemnie AI calls (e.g., in `feature:gift_idea`) to fine-tune gift suggestions.
*   **Gift Generation Fields:** Extend the gift idea input fields to collect more specific user data for even more personalized recommendations.

---

## 🤝 Contributing

Contributions are welcome! If you find a bug, have a feature request, or would like to contribute code, please feel free to:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature`).
3.  Make your changes and commit them (`git commit -m 'feat: Add your feature'`).
4.  Push to the branch (`git push origin feature/your-feature`).
5.  Open a Pull Request.

---

## 📧 Contact

For any inquiries or feedback, please reach out to:

**Zain Shahid**  
[zainshahidbuttt@gmail.com](mailto:zainshahidbuttt@gmail.com)  
**WhatsApp**: [+923033009802](https://wa.me/923033009802)

---

## 📄 License

This project is licensed under the MIT License.
_(Please create a `LICENSE` file in the root of your repo with the MIT License text if it doesn't exist yet.)_

---
