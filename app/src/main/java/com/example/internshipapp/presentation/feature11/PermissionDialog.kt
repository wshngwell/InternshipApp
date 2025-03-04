import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionName: String = "",
    onDismiss: () -> Unit = {},
    onOpenSettings: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Требуется разрешение") },
        text = { Text("Нужно разрешение на $permissionName") },
        confirmButton = {
            Button(
                modifier = modifier,
                onClick = {
                    onOpenSettings()
                    onDismiss()
                }) {
                Text("Открыть настройки приложения")
            }
        },
        dismissButton = {
            Button(
                modifier = modifier,
                onClick = onDismiss
            ) {
                Text("Закрыть")
            }
        }
    )
}