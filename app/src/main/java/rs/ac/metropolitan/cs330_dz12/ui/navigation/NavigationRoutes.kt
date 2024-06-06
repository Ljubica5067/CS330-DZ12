package rs.ac.metropolitan.cs330_dz12.ui.navigation

sealed class NavigationRoutes(val route:String){
    object Home:NavigationRoutes(route="home")
    object NewTransaction:NavigationRoutes(route = "new")
    object TransactionDetailPage:NavigationRoutes(route = "detail/{elementId}")
    {
        fun createRoute(elementId:String)="detail/$elementId"
    }
}