package zjtech.smf.modules.global.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import zjtech.smf.modules.global.domain.IEntity
import java.io.Serializable

abstract class CrudServiceImp<E : IEntity, ID : Serializable> : ICrudService<E, ID> {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    abstract fun getRepository(): MongoRepository<E, ID>

    abstract fun getEntityClass(): Class<E>


    override fun count(): Long = getRepository().count()

    override fun save(entity: E) {
        getRepository().insert(entity)
    }

    override fun update(entity: E) {
        getRepository().save(entity)
    }

    override fun delete(id: ID) {
        getRepository().delete(id)
    }

    override fun delete(entity: E) = getRepository().delete(entity)

    override fun delete(ids: Array<ID>) {
        ids.forEach {
            getRepository().delete(it)
        }
    }

    override fun exists(id: ID): Boolean = getRepository().exists(id)

    override fun findAll(pageParam: Pageable): Page<E> = getRepository().findAll(pageParam)

    override fun findById(id: ID): E? = getRepository().findOne(id)

    override fun findByName(name: String): E? {
        var query = Query(Criteria.where("name").`is`(name))
        return mongoTemplate.findOne(query, getEntityClass())
    }
}
