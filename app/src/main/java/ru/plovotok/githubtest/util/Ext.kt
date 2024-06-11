package ru.plovotok.githubtest.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.mailTo(addresses: Array<String>, title: String, subject: String? = null, body: String? = null) {
    val uri = Uri.parse("mailto:")
    val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
    emailIntent.apply {
        putExtra(Intent.EXTRA_EMAIL, addresses)
        subject?.let {
            putExtra(Intent.EXTRA_SUBJECT, it)
        }
        body?.let {
            putExtra(Intent.EXTRA_TEXT, it)
        }
    }
    startActivity(Intent.createChooser(emailIntent, title))
}

fun Context.openUrl(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}