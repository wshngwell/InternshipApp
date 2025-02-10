import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.internshipapp.ui.theme.defaultTextSp
import com.example.internshipapp.ui.theme.postColor

@Composable
@Preview
fun CustomTextField(
    value: String = "текст для теста",
    onValueChange: (String) -> Unit = {},
    onFilterText: () -> Unit = {},
    @SuppressLint(
        "ModifierParameter"
    ) modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = 30,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.White, backgroundColor = postColor
    )
    Box(
        modifier = modifier
            .background(Color.Black)
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(value = value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .border(
                        width = 5.dp, color = postColor, shape = RoundedCornerShape(12.dp)
                    ),
                onValueChange = {
                    if (it.length <= maxLength) {
                        onValueChange(it)
                    }
                },
                cursorBrush = Brush.linearGradient(colors = listOf(Color.Magenta, Color.Cyan)),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType, imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { onFilterText() }),
                textStyle = TextStyle(
                    fontSize = defaultTextSp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .width(150.dp)
                            .border(
                                width = 5.dp, color = postColor, shape = RoundedCornerShape(12.dp)
                            )
                            .padding(5.dp)
                            .background(Color.LightGray)
                            .padding(11.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) { innerTextField() }
                        Spacer(
                            Modifier
                                .width(
                                    16.dp
                                )
                                .weight(1f)
                        )
                        Icon(
                            modifier =
                            Modifier.clickable { onFilterText() },
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                })
        }
    }
}

@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        CustomTextField()
        CustomTextField(modifier = Modifier.fillMaxWidth())
    }
}