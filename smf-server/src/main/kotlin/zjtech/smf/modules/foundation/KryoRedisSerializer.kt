package zjtech.smf.modules.foundation

import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import org.springframework.data.redis.serializer.RedisSerializer
import zjtech.smf.common.utils.KryUtlis

/**
 * 使用Kryo作为spring redis 的序列号工具
 */
class KryoRedisSerializer<E> : RedisSerializer<E> {

    override fun deserialize(data: ByteArray?): E {
        val pool = KryUtlis.pool
        val kryo = pool.borrow()

        val input = Input(data)
        try {
            val obj = kryo.readClassAndObject(input) as E
            return obj
        } finally {
            input.close()
            pool.release(kryo)
        }
    }

    override fun serialize(data: E): ByteArray {
        val pool = KryUtlis.pool
        val kryo = pool.borrow()

        val output = Output(2048, -1)
        try {
            kryo.writeClassAndObject(output, data)
            return output.toBytes()
        } finally {
            output.close()
            pool.release(kryo)
        }
    }
}