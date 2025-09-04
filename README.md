# QR-Generator

### Android App to Generate QR Codes Dynamically
---

## How to Use the App

1. **Open the app** on your Android device or emulator.  
2. **Enter the URL** you want the QR code to redirect to in the TextField.  
3. **Generate the QR code** by tapping the "Generate" button.  
4. The QR code will always point to the same shortlink. Updating the URL dynamically changes the redirection destination.  
5. **Scan the QR code** with any QR scanner to test it.

---

## Tools & Technologies Used

- **Language:** Kotlin – modern, type-safe language for Android development.  
- **IDE:** Android Studio – official IDE for Android development with full Gradle support.  
- **Libraries & SDKs:**  
  - **ZXing:** used for generating QR codes dynamically.  
  - **Firebase Firestore / Realtime Database:** for storing shortlink mappings and dynamic URL updates (if implemented).  
  - **GitHub Pages:** used to host shortlinks and handle redirection for QR codes.  
  - **Android Jetpack Components:** LiveData, ViewModel for managing UI state and data flow.  
  - **Dagger / Hilt (Dependency Injection):** for modular and testable architecture.  
- **Platform:** Android SDK – supports a wide range of devices and versions.  
- **Version Control:** Git & GitHub – for code management, versioning, and collaboration.  
- **Other Tools / Practices:**  
  - **Gradle:** for dependency management and build automation.  
  - **MVVM Architecture:** separates UI from business logic.  
  - **Clean Code Principles:** meaningful variable names, modularization, and readability. 

