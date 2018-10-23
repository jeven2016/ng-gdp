package zjtech.smf.common.constants

/**
 * 值常量定义类
 */
interface SmfValueConstants {
    companion object {
        /**
         * 验证码长度
         */
        val MAX_NUMBER_OF_VERIFY_CODE = 4

        /**
         * 名称允许输入的最多字符数
         */
        val MAX_LENGTH_OF_NAME = 20

        /**
         * 名称允许输入的最少字符数
         */
        val MIN_LENGTH_OF_NAME = 1

        /**
         * 描述允许输入的最多字符数
         */
        val MAX_LENGTH_OF_DESC = 250

        /**
         * 描述允许输入的最少字符数
         */
        val MIN_LENGTH_OF_DESC = 250

        /**
         * 每页最多显示的数据条数
         */
        val MAX_NUMBER_OF_ELEMENTS = 50

        /**
         * 缺省每页显示的数据条数
         */
        val DEFAULT_NUMBER_OF_ELEMENTS = 10

        /**
         * ID 最大长度
         */
        val MAX_LENGTH_OF_ID = 25
    }

}
