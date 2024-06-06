package rs.ac.metropolitan.cs330_dz12

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class AppViewModel : ViewModel(){
    lateinit var navController: NavHostController
    var granted = mutableStateOf(false)

    fun navigateToTransactionDetails(id:String)
    {
        navController.navigate(rs.ac.metropolitan.cs330_dz12.ui.navigation.NavigationRoutes.TransactionDetailPage.route)
    }

    fun navigateToNewTransaction() {
        navController.navigate(rs.ac.metropolitan.cs330_dz12.ui.navigation.NavigationRoutes.NewTransaction.route)
    }

    fun goBack() {
        navController.popBackStack()
    }
}