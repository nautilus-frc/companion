package org.nautilusapp.nautilus.android.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.nautilusapp.nautilus.android.cardColor
import org.nautilusapp.nautilus.userauth.TokenUser

@Composable
fun ScreenScaffold(
    navController: NavController,
    user: TokenUser?,
    snack: SnackbarHostState,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val perms = user?.permissions
    Scaffold(
        bottomBar = { NavBar(navController, user) },
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(snack) {
                Snackbar(
                    snackbarData = it,
                    containerColor = cardColor(),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            content()
        }
    }
}

@Composable
fun NestedScaffold(
    snack: SnackbarHostState,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        snackbarHost = {
            SnackbarHost(snack) {
                Snackbar(
                    snackbarData = it,
                    containerColor = cardColor(),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            content()
        }
    }
}