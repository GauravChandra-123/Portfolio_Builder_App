🔥 Portfolio Builder App
Build your personal portfolio on Android

A simple and elegant Android application that lets users create a personal portfolio, add projects, upload images, and finally export everything as a PDF.
The app is designed with a clean UI using Jetpack Compose and follows the MVVM architectural pattern.

🚀 Features
✔ Create a professional profile

Add name, job title, email, and bio

Upload a profile picture from gallery

✔ Manage project list

Add unlimited projects

Each project includes title, description, link, and optional image

Remove or edit projects anytime

✔ Auto-save using DataStore

The user’s profile and projects are saved automatically

Data persists even after the app is closed

✔ Beautiful portfolio preview

Clean layout showcasing profile and projects

Thumbnail images expand to full-screen view

✔ Export to PDF

Generates a high-quality PDF

Includes profile info, project description, and project images

PDF can be shared through WhatsApp, email, etc.

🧱 Tech Stack
📱 Android + Kotlin

The entire app is built natively using Kotlin.

🎨 Jetpack Compose

Modern UI toolkit for building declarative UIs

No XML layouts

Screens: Profile, Projects, Portfolio Preview

🧩 MVVM Architecture

Clean separation of logic & UI:

Model → UserProfile, PortfolioItem

ViewModel → ProfileViewModel, ProjectsViewModel, PortfolioViewModel

View → Composable UI screens

Repository → Handles saving/loading data

DataStore → Key-value persistent storage

💾 DataStore (Preferences)

Used to store:

User profile (as JSON)

Project list (as JSON)

Provides:

Persistence

Safety

Coroutines support

No need for SharedPreferences

🖼 Image Loading – Coil

Used for:

Loading profile image

Loading project images

Displaying full screen previews

📄 PDF Generation (Android PdfDocument)

A custom PDF is generated:

Draws text

Loads images

Uses automatic line wrapping

Multi-page support

🧩 App Structure

📦 com.example.bizzcardapp
│
├─ 📂 data.datastore
│   └─ 📄 PortfolioDataStore.kt
│
├─ 📂 model
│   ├─ 📄 PortfolioItem.kt
│   └─ 📄 UserProfile.kt
│
├─ 📂 repository
│   └─ 📄 PortfolioRepository.kt
│
├─ 📂 ui
│   ├─ 📂 profile
│   │   ├─ 📄 ProfileScreen.kt
│   │   └─ 📄 ProfileViewModel.kt
│   │
│   ├─ 📂 projects
│   │   ├─ 📄 ProjectsScreen.kt
│   │   └─ 📄 ProjectsViewModel.kt
│   │
│   ├─ 📂 portfolio
│   │   ├─ 📄 PortfolioScreen.kt
│   │   └─ 📄 PortfolioViewModel.kt
│   │
│   ├─ 📂 components
│   │   └─ 📄 ImagePreview.kt
│   │
│   └─ 📂 navigation
│       └─ 📄 Screen.kt
│
├─ 📂 theme
│   ├─ 📄 Color.kt
│   ├─ 📄 Theme.kt
│   └─ 📄 Typography.kt
│
└─ 📄 MainActivity.kt



🔄 How the App Works
1️⃣ Profile Creation

User enters:

Full name

Title

Email

Bio

Profile photo

When saved:

Data is stored in DataStore

UI navigates to the Projects screen

2️⃣ Adding Projects

Each project includes:

Title

Description

Optional link

Image (gallery picker)

The project list updates automatically through:

**MutableStateFlow<List<PortfolioItem>>**


Everything is saved through:

PortfolioRepository.saveProjects()

3️⃣ Portfolio Preview

User can see:

Profile photo

Bio

Projects with images

Full-screen image preview

Includes buttons:

Edit

Export as PDF

4️⃣ PDF Generation

A custom PDF is built using:

PdfDocument()
Canvas.drawText()
Canvas.drawBitmap()


Supports:

Multi-page documents

Scaled images

Wrapped text

Share via apps

🧭 Navigation Flow

Simple Compose Navigation:

Profile → Projects → Portfolio Preview


Using:

sealed class Screen(val route: String)

⚙️ How to Build & Run
1. Clone the repository
git clone https://github.com/GauravChandra-123/portfolio-builder-app.git

2. Open in Android Studio
3. Enable:

Jetpack Compose

Kotlin

4. Run on:

Android Emulator

Physical device


🔮 Future Enhancements

If you want to improve the app later, here are ideas:

Add animations

Add ability to reorder projects

Add shareable portfolio webpage

Add cloud sync

Add dark mode

🤝 Contributing

Feel free to fork the project and open pull requests.

📄 License

This project is licensed under the MIT License — free to use, modify, and distribute.
