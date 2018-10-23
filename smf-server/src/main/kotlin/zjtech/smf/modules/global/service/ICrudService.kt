package zjtech.smf.modules.global.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import zjtech.smf.common.exceptions.SmfException
import zjtech.smf.modules.global.domain.IEntity
import java.io.Serializable

interface ICrudService<E, ID : Serializable> : IBaseService {
    /**
     * 创建单个实体

     * @param entity 实体
     * *
     * @return 返回保存的实体
     */
    fun save(entity: E): Unit = throw SmfException("Method not implemented")

    /**
     * 更新单个实体

     * @param entity 实体
     * *
     * @return 返回更新的实体
     */
    fun update(entity: E): Unit = throw SmfException("Method not implemented")

    /**
     * 根据主键删除相应实体

     * @param id 主键
     */
    fun delete(id: ID): Unit = throw SmfException("Method not implemented")

    /**
     * 删除实体

     * @param entity 实体
     */
    fun delete(entity: E): Unit = throw SmfException("Method not implemented")

    /**
     * 根据主键删除相应实体

     * @param ids 实体
     */
    fun delete(ids: Array<ID>): Unit = throw SmfException("Method not implemented")

    /**
     * 按照名称查询

     * @param name 你改成
     * *
     * @return 返回id对应的实体
     */
    fun findByName(name: String): E? = throw SmfException("Method not implemented")


    /**
     * 按照主键查询

     * @param id 主键
     * *
     * @return 返回id对应的实体
     */
    fun findById(id: ID): E? = throw SmfException("Method not implemented")

    /**
     * 实体是否存在

     * @param id 主键
     * *
     * @return 存在 返回true，否则false
     */
    fun exists(id: ID): Boolean = throw SmfException("Method not implemented")

    /**
     * 统计实体总数

     * @return 实体总数
     */
    fun count(): Long = throw SmfException("Method not implemented")

    /**
     * 查询所有实体

     * @return
     */
    fun findAll(pageParam: Pageable): Page<E> = throw SmfException("Method not implemented")
}
