import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

class PermissionLauncher(
    private val permission: String = "",
    private val onSuccess: () -> Unit = {},
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val context: Context
) {
    fun launch() {
        if (Build.VERSION.SDK_INT >= 29 && permission == WRITE_EXTERNAL_STORAGE) {
            onSuccess()
        } else {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                onSuccess()
            } else {
                launcher.launch(permission)
            }
        }
    }

    companion object {
        const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
        const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        @Composable
        fun build(
            permission: String,
            onSuccess: () -> Unit,
            onFailure: () -> Unit
        ): PermissionLauncher {
            val context = LocalContext.current
            val shouldShowDialog = remember { mutableStateOf(false) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted) {
                        onSuccess()
                    } else {
                        shouldShowDialog.value = true
                        onFailure()
                    }
                }

            )
            if (shouldShowDialog.value) {
                PermissionDialog(
                    modifier = Modifier.fillMaxWidth(),
                    permissionName = permission,
                    onDismiss = { shouldShowDialog.value = false },
                    onOpenSettings = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                )
            }
            return remember {
                PermissionLauncher(permission, onSuccess, launcher, context)
            }
        }
    }

}