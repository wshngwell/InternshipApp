package com.example.internshipapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.internshipapp.R
import com.example.internshipapp.navigation.Screen
import com.example.internshipapp.navigation.myNavigate
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.example.internshipapp.ui.theme.defaultTextSp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<LoginViewModel>()
    val event: Flow<LoginViewModel.Event> by remember { mutableStateOf(viewModel.event) }
    val intent: (LoginViewModel.Intent) -> Unit by remember { mutableStateOf(viewModel::sendIntent) }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.filterIsInstance<LoginViewModel.Event>().collect {
            when (it) {
                LoginViewModel.Event.Error -> {
                    Toast.makeText(
                        context,
                        R.string.noUserError,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LoginViewModel.Event.OnLoginSuccess -> {
                    navController.myNavigate(Screen.AfterAuthorizationScreen.route)
                }
            }

        }
    }
    UI(
        state = state,
        intent = intent
    )

}


@Composable
@Preview
fun UI(
    state: LoginViewModel.State = LoginViewModel.State(),
    intent: (LoginViewModel.Intent) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        contentAlignment = Alignment.Center


    ) {
        if (state.isloading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
        Column(
            modifier = Modifier,
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                value = state.user.login,
                onValueChange = {
                    intent(LoginViewModel.Intent.OnLoginTextChange(it))
                },
                label = {
                    Text(
                        text = "Login",
                    )
                },
                textStyle = TextStyle(
                    fontSize = defaultTextSp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .fillMaxWidth(),
                value = state.user.password,
                onValueChange = {
                    intent(LoginViewModel.Intent.OnPasswordTextChange(it))
                },
                label = {
                    Text(
                        text = "Password",
                    )
                },
                textStyle = TextStyle(
                    fontSize = defaultTextSp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
            )


            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                enabled = state.enabledEnterButton,
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
                onClick = {
                    intent(LoginViewModel.Intent.CheckPasswordAndLogin)
                }
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = stringResource(R.string.enter)
                )
            }
        }

    }
}