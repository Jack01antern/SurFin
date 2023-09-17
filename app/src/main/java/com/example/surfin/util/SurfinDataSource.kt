package com.example.surfin.util

import androidx.lifecycle.LiveData
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.localsource.SurfinDatabaseDao

class SurfinDataSource(private val dao: SurfinDatabaseDao) : SurfinRepository {

    //remote
    override suspend fun getCwaTide(): CwaTideResult {
        return super.getCwaTide()
    }

    override suspend fun getCwaTemp(): CwaTempResult {
        return super.getCwaTemp()
    }

    override suspend fun getCwaWdsd(): CwaTempResult {
        return super.getCwaWdsd()
    }

    override suspend fun getCwaUvi(): CwaUviResult {
        return super.getCwaUvi()
    }

    //local
    override suspend fun insert(user: UserActivityHistory) {
        return dao.insert(user)
    }

    override suspend fun clear() {
        return dao.clear()
    }

    override fun getAllHistory(): LiveData<List<UserActivityHistory>> {
        return dao.getAllHistory()
    }
}