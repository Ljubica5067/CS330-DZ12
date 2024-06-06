package rs.ac.metropolitan.cs330_dz12.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import rs.ac.metropolitan.cs330_dz12.AppViewModel

@Composable
fun NavSetup(){
    val navController=rememberNavController()
    val vm: AppViewModel =viewModel()
    val paddingValues = PaddingValues()
    vm.navController = navController
}