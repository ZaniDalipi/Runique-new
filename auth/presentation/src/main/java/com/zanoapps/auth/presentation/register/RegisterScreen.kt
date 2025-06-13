package com.zanoapps.auth.presentation.register


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zanoapps.core.presentation.designsystem.CheckIcon
import com.zanoapps.core.presentation.designsystem.CrossIcon
import com.zanoapps.core.presentation.designsystem.EmailIcon
import com.zanoapps.core.presentation.designsystem.Poppins
import com.zanoapps.core.presentation.designsystem.R
import com.zanoapps.core.presentation.designsystem.RuniqueDarkRed
import com.zanoapps.core.presentation.designsystem.RuniqueGreen
import com.zanoapps.core.presentation.designsystem.RuniqueTheme
import com.zanoapps.core.presentation.designsystem.components.GradientBackground
import com.zanoapps.core.presentation.designsystem.components.RuniqueActionButton
import com.zanoapps.core.presentation.designsystem.components.RuniquePasswordTextField
import com.zanoapps.core.presentation.designsystem.components.RuniqueTextField
import com.zanoapps.core.presentation.ui.ObserveAsEvents
import com.zanoappsa.auth.domain.PasswordValidationState
import com.zanoappsa.auth.domain.UserDataValidator
import org.koin.androidx.compose.koinViewModel

@Composable

fun RegisterScreenRoot(
    onLoginClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()

) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { registerEvent ->
        when (registerEvent) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    registerEvent.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()

            }

            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()

                onSuccessfulRegistration()
            }


        }

    }

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> onLoginClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable

private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit

) {

    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 32.dp)
        ) {

            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )

            BasicText(
                text = buildAnnotatedString {
                    append(stringResource(R.string.already_have_an_account) + " ")
                    val link =
                        LinkAnnotation.Clickable(
                            tag = "on_login_clickable_text",
                            styles = TextLinkStyles(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = Poppins
                                )
                            ),
                            linkInteractionListener = {
                                onAction(RegisterAction.OnLoginClick)
                            }

                        )
                    withLink(link) { append(stringResource(R.string.login)) }
                },
                style = TextStyle(
                    fontFamily = Poppins,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )



            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityCheck)
                },
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 1
            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASS_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))


            // 2
            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))


            // 3
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_lowercase_character,
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))


            // 4
            PasswordRequirement(
                text = stringResource(
                    R.string.contains_uppercase_character,
                    UserDataValidator.MIN_PASS_LENGTH
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))

            RuniqueActionButton(
                text = stringResource(R.string.register),
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canRegister,
            ) {
                onAction(RegisterAction.OnRegisterClick)
            }
        }
    }

}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if (isValid)
                RuniqueGreen
            else
                RuniqueDarkRed
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }

}

@Preview

@Composable

private fun RegisterScreenPreview() {

    RuniqueTheme {

        RegisterScreen(

            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasMinLength = false,
                    hasNumber = true,
                    hasLowerCaseCharacter = false,
                    hasUpperCaseCharacter = true
                ),
            ),
            onAction = {}
        )

    }

}