package mongo

import org.junit.Test

class test1 {

    @Test
    fun tt() {
        var ar = arrayOf("1", "2")
        ar.map { it + "hello" }.toList().forEach { println(it) }
    }


}
