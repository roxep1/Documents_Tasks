package com.bashkir.documentstasks.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun getBytesDocument(uri: Uri?, context: Context): ByteArray? =
    uri?.let {
        context.contentResolver.openFileDescriptor(uri, "r").use {
            FileInputStream(it?.fileDescriptor).use { stream ->
                stream.readBytes()
            }
        }
    }

fun getMetadata(uri: Uri?, context: Context, onResult: (String?, Long?) -> Unit) =
    uri?.let {
        context.contentResolver.query(
            uri, null, null, null, null, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val displayName = if (index >= 0) cursor.getString(index) else null
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                val size = if (sizeIndex >= 0) cursor.getLong(sizeIndex) else null
                onResult(displayName, size)
            }
        }
    }

fun getAllFromUri(uri: Uri?, context: Context, onResult: (String?, Long?, ByteArray?) -> Unit) =
    getBytesDocument(uri, context).let { bytes ->
        getMetadata(uri, context) { name, size ->
            onResult(name, size, bytes)
        }
    }

fun writeDocument(file: File, uri: Uri?, context: Context) {
    uri?.let {
        context.contentResolver.openFileDescriptor(uri, "w")?.use {
            FileOutputStream(it.fileDescriptor).use { stream ->
                stream.write(file.file)
            }
        }
    }
}

fun Long.toMB(): Float = "${div(1000000)}.${
    (this % 1000000)}".toFloat()