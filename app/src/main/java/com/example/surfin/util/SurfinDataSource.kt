package com.example.surfin.util

import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.User
import com.example.surfin.data.localsource.SurfinDatabaseDao

class SurfinDataSource(private val dao: SurfinDatabaseDao) : SurfinRepository {

    override suspend fun getCwaTide() {
    }


    override suspend fun insert(user: User) {
        return dao.insert(user)
    }

}