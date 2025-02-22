package com.plutecoder.itworld.views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.plutecoder.itworld.R
import com.plutecoder.itworld.models.Uses

fun Context.showUsesDialog(uses: Uses) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_uses_info, null)

    // Find views
    val tvTitle = view.findViewById<TextView>(R.id.title)
    val tvDescription = view.findViewById<TextView>(R.id.description)
    val btnClose = view.findViewById<ImageView>(R.id.close_image)

    // Set data
    tvTitle.text = uses.title
    tvDescription.text = uses.description

    btnClose.setOnClickListener {
        dialog.dismiss()
    }

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

//    dialog.window?.setLayout(
//        (resources.displayMetrics.widthPixels * 0.85).toInt(),
//        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
//    )

    // Set custom view
    dialog.setContentView(view)
    dialog.setCancelable(true)
    dialog.show()

}