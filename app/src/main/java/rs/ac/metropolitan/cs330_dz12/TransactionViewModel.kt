package rs.ac.metropolitan.cs330_dz12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.ac.metropolitan.cs330_dz12.data.TransactionDao
import rs.ac.metropolitan.cs330_dz12.data.TransactionItem
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel(){
    val transactions: LiveData<List<TransactionItem>> = transactionDao.getAllTransactions().asLiveData()

    fun insertTransaction(transaction: TransactionItem) {
        viewModelScope.launch {
            transactionDao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: TransactionItem) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(transaction)
        }
    }
}