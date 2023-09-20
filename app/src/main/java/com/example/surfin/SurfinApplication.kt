package com.example.surfin

import android.app.Application
import com.example.surfin.data.SurfinRepository
import com.example.surfin.util.ServiceLocator
import kotlin.properties.Delegates

class SurfinApplication:Application() {

    // Depends on the flavor,
    val surfinRepository: SurfinRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: SurfinApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}