package zjtech.smf.common.utils

import com.esotericsoftware.kryo.pool.KryoPool
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.pool.KryoFactory


object KryUtlis {

    /**
     * 获取一个KryPool
     *
     * 使用方式：
     * var pool = KryUtils.pool
     * Kryo kryo = pool.borrow();
     *  do s.th. with kryo here, and afterwards release it
     * pool.release(kryo);
     */
    lateinit var pool: KryoPool
        private set

    init {
        init()
    }

    private fun init() {
        val factory = KryoFactory {
            val kryo = Kryo()
            kryo.asmEnabled = true
            kryo.setCopyReferences(false)
            kryo
        }
        // Build pool with SoftReferences enabled (optional)
        this.pool = KryoPool.Builder(factory).softReferences().build()
    }

}