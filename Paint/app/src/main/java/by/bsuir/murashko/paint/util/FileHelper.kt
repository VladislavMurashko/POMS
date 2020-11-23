package by.bsuir.murashko.paint.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import by.bsuir.murashko.paint.R

class FileHelper {
    companion object {
        fun pickFile(activity: Activity) {
            val intent = Intent()
                .addCategory(Intent.CATEGORY_OPENABLE)
                .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            activity.startActivityForResult(
                Intent.createChooser(intent, R.string.select_file_title.toString()),
                0
            )
        }

        fun getImagePath(context: Context, data: Intent?): String? {
            return data?.data.let { URIPathHelper.getPath(context, it!!) }
        }
    }
}
