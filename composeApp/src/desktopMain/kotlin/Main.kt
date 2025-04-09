import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import org.mathieu.cleanrmapi.koin.desktopModule
import org.mathieu.cleanrmapi.ui.App

/**
 * The main entry point for the desktop application.
 *
 * This function initializes the Koin dependency injection framework,
 * sets up the application window, and starts the Compose UI.
 *
 * It performs the following actions:
 * 1. **Starts Koin:** Initializes the Koin dependency injection framework with the `desktopModule`.
 *    This allows components within the application to access dependencies defined in the module.
 * 2. **Creates a Window:** Sets up the main application window using the `Window` composable.
 *    - `onCloseRequest`: Specifies the action to perform when the window is closed, which is to exit the application.
 *    - `title`: Sets the title of the application window to "Clean RmApi UDF".
 * 3. **Starts the App:**  Calls the `App()` composable, which is the root of the application's UI.
 *
 */
fun main() = application {
    startKoin {
        modules(desktopModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Clean RmApi UDF",
    ) {
        App()
    }
}