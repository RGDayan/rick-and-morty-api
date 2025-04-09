# CleanRMAPI – Architecture Overview

**CleanRMAPI** is a Kotlin Multiplatform (KMP) project targeting both **Android** and **Desktop**, 
built around Clean Architecture principles. 
The structure is modular, with clear separation of concerns and a shared logic core across platforms.

---

## 🔧 Architecture Breakdown

### 🧠 **Domain Layer**
- Shared across all platforms (`commonMain`)
- Contains business rules, models, and repository interfaces
- Platform-agnostic, pure Kotlin — no external dependencies

### 🧰 **Application Layer**
- Also in `commonMain`
- Hosts shared `ViewModel`s and application logic
- Manages UI state and user actions using sealed classes
- Follows a unidirectional data flow architecture

### 🌐 **Data Layer**
- Mixed shared & platform-specific
- **Networking:** Uses [Ktor](https://ktor.io) in `commonMain` to call APIs
- **Caching:** Android uses Room (in `androidMain`); Desktop can use file or memory-based storage
- Implements repository interfaces using DI bindings

### 🖼️ **Presentation Layer**
- UI logic is platform-specific
- Android uses **Jetpack Compose**
- Desktop uses **Compose for Desktop**
- Both platforms consume the shared ViewModels for consistent behavior

### 💉 **Dependency Injection**
- Uses **Koin** across shared and platform code
- Shared bindings (like repositories, use cases) in `commonMain`
- Platform-specific modules for context-aware components like `SoundPlayer`

---

## 🔊 Platform Services

To support features like sound playback:
- A shared `SoundPlayer` interface is declared in `commonMain`
- Android uses `MediaPlayer`, Desktop uses `AudioSystem` via `Clip`
- Implementations are injected via platform-specific Koin modules

---

## 🗃️ Project Structure Highlights

```bash
src/
├── commonMain/       # Shared logic (domain, app, ViewModels)
├── androidMain/      # Android-specific (UI, Room, MediaPlayer)
├── desktopMain/      # Desktop-specific (Compose, AudioSystem)
```

---

This structure allows the app to reuse a majority of its codebase while staying adaptable 
to platform-specific needs like multimedia, local storage, and UI frameworks.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.