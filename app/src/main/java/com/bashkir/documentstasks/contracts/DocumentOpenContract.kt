package com.bashkir.documentstasks.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class DocumentOpenContract : ActivityResultContract<Int, Uri?>() {
    override fun createIntent(context: Context, input: Int): Intent =
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
        when(resultCode){
            Activity.RESULT_OK -> intent?.data
            else -> null
        }
}