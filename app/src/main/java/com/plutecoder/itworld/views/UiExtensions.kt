package com.plutecoder.itworld.views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

    if (isDarkModeEnabled(this)) {
        view.findViewById<soup.neumorphism.NeumorphCardView>(R.id.dialog_uses_card).setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
        view.findViewById<soup.neumorphism.NeumorphCardView>(R.id.dialog_uses_card).setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
    }

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    // Set custom view
    dialog.setContentView(view)
    dialog.setCancelable(true)
    dialog.show()

}

fun isDarkModeEnabled(context: Context): Boolean {
    return context.resources.configuration.uiMode==33
}