package hiof.mobilg11.quizapplication.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.viewmodels.BottomNavBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    navController: NavController,
    viewModel: BottomNavBarViewModel = hiltViewModel()
) {
    val gameNotifications = viewModel.gameNotifications.collectAsState()

    val items = listOf(
        BottomNavBarItem.Home,
        BottomNavBarItem.Play,
        BottomNavBarItem.Profile
    )
    if (gameNotifications.value > 0) {
        items[1].notifications = gameNotifications.value
    }
    val selectedItem = remember { mutableStateOf(items[0]) }

    NavigationBar {
        for (item in items) {
            val isSelected = selectedItem.value == item

            NavigationBarItem(
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Box {
                        if (item.notifications != null) {
                            Badge(
                                content = {
                                    Text(text = item.notifications.toString())
                                },
                                modifier = Modifier.offset(x = 12.dp, y = (-8).dp)
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = item.selectedIcon,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = item.unselectedIcon,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                selected = isSelected,
                onClick = {
                    selectedItem.value = item
                    navController.navigate(item.route)
                }
            )
        }
    }
}

sealed class BottomNavBarItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var notifications: Int? = null
) {
    object Home : BottomNavBarItem(
        route = Screen.Home.route,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object Play : BottomNavBarItem(
        route = Screen.SinglePlayer.route,
        title = "Play",
        selectedIcon = Icons.Filled.PlayArrow,
        unselectedIcon = Icons.Outlined.PlayArrow,
    )

    object Profile : BottomNavBarItem(
        route = Screen.Profile.route,
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}