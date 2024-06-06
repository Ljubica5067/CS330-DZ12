package rs.ac.metropolitan.cs330_dz12.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionItem(@PrimaryKey val id: String,
                           val currency: String,
                           val date: Long,
                           val category: String,
                           val amount: Double,
                           val comment: String)
