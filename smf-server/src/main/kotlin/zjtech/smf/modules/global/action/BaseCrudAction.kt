package zjtech.smf.modules.global.action

import mu.KLogging
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.slf4j.Logger
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import zjtech.smf.common.constants.INVALID_ID
import zjtech.smf.common.constants.SmfValueConstants
import zjtech.smf.common.constants.UPDATE_CONFLICT
import zjtech.smf.common.result.ISmfResult
import zjtech.smf.common.result.SmfResult
import zjtech.smf.modules.global.domain.IEntity
import zjtech.smf.modules.global.service.ICrudService
import java.io.Serializable

open class BaseCrudAction<E, ID> : BaseAction() where E : IEntity, ID : Serializable {
    companion object : KLogging()

    protected open fun getLogger(): Logger {
        return logger
    }

    /**
     * 获取当前用户对象关联的Subject
     */
    protected open fun getSubject(): Subject {
        return SecurityUtils.getSubject()
    }

    /**
     * 获取分页查询结果
     *
     * @return SmfResult<List></List><E>>
     */
    open fun list(currentPage: Int, pageSize: Int, service: ICrudService<E, ID>): ResponseEntity<ISmfResult<Page<E>>> {
        var result = SmfResult<Page<E>>()
        var pageable = PageRequest(currentPage, pageSize)
        var pageInfo = service.findAll(pageable)
        result.attachedObj = pageInfo
        return ResponseEntity(result, HttpStatus.OK)
    }


    /**
     *  创建或一个新的Server
     *  @return ResponseEntity
     */
    fun save(dto: E, service: ICrudService<E, ID>): ResponseEntity<ISmfResult<E>> {
        service.save(dto)
        return ResponseEntity(null, HttpStatus.CREATED)
    }

    /**
     * 创建或更新一个新的Server

     * @return ResponseEntity
     */
    fun update(dto: E, service: ICrudService<E, ID>): ResponseEntity<ISmfResult<E>> {
        var result: ISmfResult<E> = SmfResult()
        var status = HttpStatus.NO_CONTENT

        try {
            service.update(dto)
        } catch (e: OptimisticLockingFailureException) {
            result.addGlobalError("global", UPDATE_CONFLICT)
            result.ok = false
            status = HttpStatus.BAD_REQUEST
            getLogger().warn("Failed to update the existing entity this is due to the record has been changed by other user.")
        }
        return ResponseEntity(result, status)
    }


    /**
     * 获取对象

     * @param id 对象ID
     * *
     * @return SmfResult
     */
    operator fun get(id: ID, service: ICrudService<E, ID>): ResponseEntity<ISmfResult<E>> {
        var smfResult: ISmfResult<E> = SmfResult()
        var status: HttpStatus

        var idLength = id.toString().length
        if (idLength <= 0 || idLength > SmfValueConstants.MAX_LENGTH_OF_ID) {
            smfResult.ok = false
            smfResult.addFieldError("id", INVALID_ID)
            return ResponseEntity(smfResult, HttpStatus.BAD_REQUEST)
        }

        var attachment: E? = service.findById(id);
        smfResult.attachedObj = attachment
        status = HttpStatus.OK
        return ResponseEntity(smfResult, status)
    }

    /**
     * 获取一个存在的对象

     * @param id 对象ID
     * *
     * @return SmfResult
     */
    fun delete(id: ID, service: ICrudService<E, ID>): ResponseEntity<ISmfResult<E>> {
        var smfResult: ISmfResult<E> = SmfResult()
        var status = HttpStatus.OK

        service.delete(id)
        return ResponseEntity(smfResult, status)
    }

}
