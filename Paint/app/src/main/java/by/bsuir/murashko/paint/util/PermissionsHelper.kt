package by.bsuir.murashko.paint.util

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import by.bsuir.murashko.paint.R

class PermissionsHelper {
    companion object {
        private const val REQUEST_CODE = 1
        private const val PERMISSION_GRANTED_CODE = PackageManager.PERMISSION_GRANTED

        fun askForPermissions(context: Context, activity: Activity): Boolean {
            if (!isPermissionsAllowed(context)) {
                if (shouldShowRequestPermissionRationale(activity, READ_EXTERNAL_STORAGE) ||
                    shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE)
                ) {
                    showPermissionsDeniedDialog(context)
                } else {
                    requestPermissions(
                        activity,
                        arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE
                    )
                }
                return false
            }
            return true
        }

        private fun isPermissionsAllowed(context: Context): Boolean {
            val readPermCode = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)
            val writePermCode = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE)

            return readPermCode == PERMISSION_GRANTED_CODE && writePermCode == PERMISSION_GRANTED_CODE
        }

        private fun showPermissionsDeniedDialog(context: Context) {
            AlertDialog.Builder(context)
                .setTitle(R.string.permission_denied_title)
                .setMessage(R.string.permission_denied_text)
                .setPositiveButton(R.string.app_settings_title) { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }
                .setNegativeButton(R.string.cancel_button_title, null)
                .show()
        }
    }
}