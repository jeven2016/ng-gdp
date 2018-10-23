/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package zjtech.smf.modules.global.action

import mu.KLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import zjtech.smf.aop.action.HandleActionException
import zjtech.smf.common.constants.SmfValueConstants
import zjtech.smf.common.result.ISmfResult
import zjtech.smf.modules.global.domain.IEntity
import zjtech.smf.modules.global.service.ICrudService
import java.io.Serializable
import javax.validation.Valid

abstract class CrudMappingAction<E, ID> : BaseCrudAction<E, ID>()  where E : IEntity, ID : Serializable {

    companion object : KLogging()

    /* @Autowired
   @Qualifier(IdentifierConstants.BMF_SEmvn RVICE_MAP)*/
    //这里如果使用以上spring 原生注解来注入map，会出现无法找到对应资源注入的异常。 故这里使用@Resource
    //  In other words, by saying @Autowired @Qualifier("myList") List<String>,
    //  you're actually asking for "give me the list of all beans of type java.lang.String that have the qualifier "myList".
    //  The solution is mentioned in 3.11.3. Fine-tuning annotation-based autowiring with qualifiers:
    /*  @Resource(name = IdentifierConstants.BMF_SERVICE_MAP)
  private Map<String, String> serviceMap;*/

    /**
     * Get the specific service instance that associated with this resource

     * @return ICrudService<ID></ID>, DTO>
     */
    abstract protected fun associatedService(): ICrudService<E, ID>


    override fun getLogger(): Logger {
        return logger
    }


    /**
     * 获取分页查询结果
     * @return SmfResult<List></List><E>>
     */
    @GetMapping
    @HandleActionException(errorMessage = "Failed to query a list of objects")
    open fun list(@RequestParam(value = "currentPage", required = false) currentPage: Int?,
                  @RequestParam(value = "pageSize", required = false) pageSize: Int?): ResponseEntity<ISmfResult<Page<E>>> {
        //设置分页参数
        var page = ensureValidValue(currentPage, 0, Integer.MAX_VALUE)
        var size = ensureValidValue(pageSize, SmfValueConstants.DEFAULT_NUMBER_OF_ELEMENTS, SmfValueConstants.MAX_NUMBER_OF_ELEMENTS)
        return super.list(page, size, associatedService())
    }

    /**
     * 创建或更新一个新的Server
     * /memcached/server -PUT  = create new
     * @return SmfResult
     */
    @PostMapping
    @HandleActionException(errorMessage = "Failed to save")
    fun save(@Valid @RequestBody entity: E,
             result: BindingResult): ResponseEntity<ISmfResult<E>> {
        if (result.hasErrors()) {
            // return createResult<Any>(result)
        }
        return super.save(entity, associatedService())
    }

    /**
     * 创建或更新一个新的Server
     * /memcached/server -PUT  = create new
     * @return SmfResult
     */
    @PutMapping("/{id}")
    @HandleActionException(errorMessage = "Failed to update a object")
    fun update(@Valid @RequestBody dto: E,
               @PathVariable("id") id: ID,
               result: BindingResult): ResponseEntity<ISmfResult<E>> {
        if (result.hasErrors()) {
            // return createResult<Any>(result)
        }
        return super.update(dto, associatedService())
    }


    /**
     * 获取对象
     * @param id 对象ID
     * *
     * @return SmfResult
     */
    @GetMapping("/{id}")
    @HandleActionException(errorMessage = "Failed to find a object")
    operator fun get(@PathVariable("id") id: ID): ResponseEntity<ISmfResult<E>> {
        return super.get(id, associatedService())
    }

    /**
     * 获取一个存在的对象
     * @param id 对象ID
     * *
     * @return SmfResult
     */
    @DeleteMapping("/{id}")
    @HandleActionException(errorMessage = "Failed to delete a object")
    fun delete(@PathVariable("id") id: ID): ResponseEntity<ISmfResult<E>> {
        return super.delete(id, associatedService())
    }

}
