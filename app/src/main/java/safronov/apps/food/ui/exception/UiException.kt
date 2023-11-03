package safronov.apps.food.ui.exception

import java.lang.RuntimeException

class UiException(msg: String? = null, cuz: Throwable? = null): RuntimeException(msg, cuz)