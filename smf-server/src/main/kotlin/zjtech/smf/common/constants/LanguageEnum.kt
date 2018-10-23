package zjtech.smf.common.constants

import java.util.*

/**
 * 语言类型枚举类
 */
enum class LanguageEnum {
    /**
     * 中文
     */
    Chinese {
        override val localeInfo: Locale
            get() = Locale.SIMPLIFIED_CHINESE
    },

    //英语
    English {
        override val localeInfo: Locale
            get() = Locale.US
    };

    /**
     * 获取国际化Locale信息

     * @return
     */
    open val localeInfo: Locale
        get() = LanguageEnum.Chinese.localeInfo

    companion object {

        fun acceptable(value: String): Boolean {
            val values = LanguageEnum.values()
            for (item in values) {
                if (item.name == value) {
                    return true
                }
            }
            return false
        }

        /**
         * 根据Locale获取语言枚举对象

         * @param locale Locale
         * *
         * @return LanguageEnum
         */
        fun getLanguageByLocale(locale: Locale): LanguageEnum? {
            val values = LanguageEnum.values()
            for (item in values) {
                if (item.localeInfo == locale) {
                    return item
                }
            }
            return null
        }
    }
}
