package zjtech.smf.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
class EmailAlreadyInUseException : RuntimeException()