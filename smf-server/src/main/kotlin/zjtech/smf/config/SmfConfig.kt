package zjtech.smf.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class SmfConfig {

    @Value("\${app.name}")
    lateinit var name: String

}