package idea.pti.insaf.purchi.ui

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class ImageLoader {

    companion object {

        fun switchNameCase(name: String): String {
            val originalName = name
            return originalName.let {
                if (it.isNotEmpty()) {
                    val firstLetter = it[0].let {
                        if (it.isLowerCase()) it.uppercaseChar() else it.lowercaseChar()
                    }
                    "$firstLetter${it.substring(1)}"
                } else {
                    it
                }
            }
        }

        fun loadImageFromAssets(
            context: Context,
            imageName: String,
            imageView: ImageView,
            width: Int,
            height: Int,
            halqa: String,
        ) {
            if (imageName == ".jpg") {
                return
            }

            var imageUrl = "file:///android_asset/drawables/$imageName"

            if (!assetExists(context, "drawables/$imageName")) {
                val modifiedName = switchNameCase(imageName)
                imageUrl = "file:///android_asset/drawables/$modifiedName"
                if (!assetExists(context, "drawables/$modifiedName")) {
                    Log.d("ImageLoader", "halqa $halqa Image not found: $imageName")
                }
            } else {
                Log.d("ImageLoader", "halqa $halqa Image found: $imageName")
            }

            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .override(width, height)
                .into(imageView)
        }

        fun assetExists(context: Context, filename: String): Boolean {
            return try {
                // Attempt to open the file
                context.assets.open(filename).use { inputStream ->
                    // If successful, return true
                    true
                }
            } catch (e: IOException) {
                // If an exception is caught, the file does not exist
                false
            }
        }
    }
}
