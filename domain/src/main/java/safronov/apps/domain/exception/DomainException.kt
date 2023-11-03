package safronov.apps.domain.exception

import java.lang.RuntimeException

class DomainException(msg: String? = null, cuz: Throwable? = null): RuntimeException(msg, cuz)