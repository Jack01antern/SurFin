package com.example.surfin.emergency

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class EmergencyViewModel : ViewModel() {
    private val num118 = "118"
    private val num112 = "112"
    private val intent = Intent(Intent.ACTION_DIAL)

    fun dial118(): Intent {
        intent.setData(Uri.parse("tel:$num118"))
        return intent
    }

    fun dial112(): Intent {
        intent.setData(Uri.parse("tel:$num112"))
        return intent
    }


}