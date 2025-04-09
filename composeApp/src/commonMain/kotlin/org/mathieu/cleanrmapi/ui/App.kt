package org.mathieu.cleanrmapi.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.composable
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsScreen
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersScreen
import org.mathieu.cleanrmapi.ui.screens.episodedetails.EpisodeDetailsScreen
import org.mathieu.cleanrmapi.ui.screens.locationdetails.LocationDetailsScreen


/**
 * The root composable of the application.
 *
 * Sets up navigation and Koin dependency injection before rendering the main UI.
 */
@Composable
fun App() {
    NavigationManager.navController = rememberNavController()
    KoinContext {
        MainContent()
    }

}

/**
 * `NavigationManager` provides global access to the [NavHostController].
 *
 * **Initialization:** Set `navController` with `rememberNavController()` in your `NavHost` setup
 * **Navigation:** Use `NavigationManager.navController.navigate("route")` from anywhere.
 *
 * **Important:** `navController` must be initialized before any navigation calls.
 * Otherwise, an exception will be thrown.
 * However, it should be initialized in the App composable.
 */
object NavigationManager {
    lateinit var navController: NavHostController
}

@Composable
private fun MainContent() {

    //https://developer.android.com/jetpack/compose/navigation?hl=fr
    NavHost(navController = NavigationManager.navController, startDestination = "characters") {

        composable(Destination.Characters) { CharactersScreen() }

        composable(
            destination = Destination.CharacterDetails()
        ) { backStackEntry ->

            CharacterDetailsScreen(
                id = backStackEntry.arguments?.getInt("characterId") ?: -1
            )

        }

        composable(
            destination = Destination.EpisodeDetails()
        ) { backStackEntry ->

            EpisodeDetailsScreen(
                id = backStackEntry.arguments?.getInt("episodeId") ?: -1
            )

        }

        composable(
            destination = Destination.LocationDetails()
        ) { backStackEntry ->

            LocationDetailsScreen(
                id = backStackEntry.arguments?.getInt("locationId") ?: -1
            )

        }

    }

}
