package kg.sunrise.dasslerpro.utils.network

import androidx.fragment.app.Fragment
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.utils.constants.*
import kg.sunrise.dasslerpro.utils.extensions.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

enum class ErrorCode {
    NOINTERNET,
}

fun Fragment.parseErrorCode(
    state: State.ErrorState,
    displayErrorLayout: (Boolean, ErrorCode?) -> Unit,
    showErrorAsDialog: (String) -> Unit,
    handleCustomError: (String) -> Unit
) {
    when (state.errorCode) {
        DEFAULT_INTEGER -> requireContext().toast(getString(R.string.error_network))
        DEFAULT_NO_INTERNER_INTEGER, 503 -> {
            if (state.errorCode == DEFAULT_NO_INTERNER_INTEGER) {
                displayErrorLayout(true, ErrorCode.NOINTERNET)
            }
        }
        DEFAULT_ERROR_INT -> showErrorAsDialog(state.message)
        401 -> CoroutineScope(Dispatchers.IO).launch {
//                FCMTokenUtils.getFCMToken()
//                SharedPrefModule(this@BaseActivity).TokenModule().deleteToken()
//                withContext(Dispatchers.Main) {
//                    startActivity(
//                        Intent(this@BaseActivity, LoginActivity::class.java).setFlags(
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        )
//                    )
//                }
        }
        500 -> showErrorAsDialog(getString(R.string.error_server))
        404 -> showErrorAsDialog(getString(R.string.error_not_found))
        413 -> showErrorAsDialog(getString(R.string.error_big_file))
        else -> {

            try {
                val jsonObject = JSONObject(state.message)

                when {
                    jsonObject.has(DETAIL) -> try {
                        val message = jsonObject.getString(DETAIL)
                        showErrorAsDialog(message)
                    } catch (e: Exception) {
                        val jsonArray = jsonObject.getJSONArray(DETAIL)
                        if (jsonArray.length() > 0) {
                            showErrorAsDialog(jsonArray.getString(1))
                        }
                    }
                    jsonObject.has(MESSAGE) -> try {
                        val message = jsonObject.getString(MESSAGE)
                        showErrorAsDialog(message)
                    } catch (e: Exception) {
                        val jsonArray = jsonObject.getJSONArray(MESSAGE)
                        if (jsonArray.length() > 0) {
                            showErrorAsDialog(jsonArray.getString(1))
                        }
                    }
                    else -> handleCustomError(state.message)
                }
            } catch (e: Exception) {
                handleCustomError(state.message)
            }
        }
    }
}