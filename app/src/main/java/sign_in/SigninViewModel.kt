package sign_in

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class SigninViewModel @Inject constructor(
    private val accountServiceImpl: AccountServiceImpl
) : NotesAppViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")


    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountServiceImpl.signIn(email.value, password.value)
         //   openAndPopUp(NOTES_LIST_SCREEN, SIGN_IN_SCREEN)
        }
    }




}