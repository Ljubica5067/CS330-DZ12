package rs.ac.metropolitan.cs330_dz12.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}