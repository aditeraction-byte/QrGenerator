# QR-Generator

Android app to generate QR codes dynamically and manage shortlinks.

---

## Features

- Generate dynamic QR codes pointing to shortlinks.
- Update the URL dynamically without changing the QR code.
- Scan the QR code with any QR scanner to test redirection.
- Responsive UI for all screens (Home, QR Generator, QR Stats, QR Details, Login).
- Reusable UI components (`AppButton`, `AppCard`, `AppTextField`) for consistent design.
- MVVM architecture with Compose UI and clean code principles.
- Firebase Firestore support for shortlink storage and dynamic URL updates.
- Login and registration flow with form validation.

---

## Screens

Below are screenshots of the main screens of the app (scaled for readability):

### Home Screen
<img src="https://github.com/user-attachments/assets/55a8fb3c-2f77-477b-b0fc-6f91f7db63d8" width="200"/>

### QR Generator Screen
<img src="https://github.com/user-attachments/assets/dcc7cf7f-3000-458b-a780-c81ee43b1c97" width="200"/>

### QR Details / Edit Screen
<img src="https://github.com/user-attachments/assets/eb82da20-b3b9-4da7-8f28-826d6733015f" width="200"/>

### QR Stats Screen
<img src="https://github.com/user-attachments/assets/cd3f2379-4d50-4559-bb92-58a7f0018c12" width="200"/>

### Login Screen
<img src="https://github.com/user-attachments/assets/6154fe7e-76c0-472d-ac7f-d136a7fde55d" width="200"/>

### Redirection
<img src="https://github.com/user-attachments/assets/effd6ef4-b14f-49ad-a02c-54ac21fdc4c2" width="200"/>

## How to Use

1. Open the app on your Android device or emulator.
2. Enter the URL you want the QR code to redirect to in the TextField.
3. Generate the QR code by tapping the "Generate" button.
4. Scan the QR code with any QR scanner to test it.
5. Update the URL dynamically, and the shortlink will redirect to the new destination.
6. Use the QR Stats and QR Details screens to view scan statistics and edit QR info.
7. Login/Register to save your QR codes and track them in Firebase.

---

## Tools & Technologies

- **Language:** Kotlin  
- **IDE:** Android Studio  
- **UI:** Jetpack Compose, Material3  
- **Dependency Injection:** Hilt  
- **Libraries:** ZXing (QR generation), Firebase Firestore  
- **Architecture:** MVVM, LiveData/ViewModel  
- **Version Control:** Git & GitHub  

---

## Notes

- All screens are **responsive for mobile devices**, with scroll added for small screens.
- UI components are **reusable** for consistency across the app.
- QR codes are generated dynamically and can redirect to different URLs via shortlinks.
- Follow Clean Code principles for easy maintenance and scalability.

