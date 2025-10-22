/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  nr.midstore.core.definition.bean.MidstoreResultObject
 *  nr.midstore.core.definition.common.NrMidstoreErrorEnum
 *  nr.midstore.core.definition.db.MidstoreException
 *  nr.midstore.core.definition.dto.MidstorePlanTaskDTO
 *  nr.midstore.core.param.service.IMidstoreDocumentService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.midstore.design.web;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.NrMidstoreErrorEnum;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.core.param.service.IMidstoreDocumentService;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.domain.ExchangeSchemeDTO;
import nr.midstore.design.domain.SchemeBaseDTO;
import nr.midstore.design.domain.SchemeBaseDataDTO;
import nr.midstore.design.domain.SchemeBaseDataFieldDTO;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.domain.SchemeGroupDTO;
import nr.midstore.design.domain.SchemeInfoDTO;
import nr.midstore.design.domain.SchemeOrgDataFieldDTO;
import nr.midstore.design.domain.SchemeOrgDataItemDTO;
import nr.midstore.design.service.IFileExecuteService;
import nr.midstore.design.service.IFileReadService;
import nr.midstore.design.service.IFileUpdateService;
import nr.midstore.design.vo.ExchangeSchemeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/mistore/desgin"})
@Api(tags={"\u4e2d\u95f4\u5e93\u4ea4\u6362\u65b9\u6848"})
public class MidstoreDesginController {
    private static final Logger log = LoggerFactory.getLogger(MidstoreDesginController.class);
    @Autowired
    private IFileExecuteService fileExecuteService;
    @Autowired
    private IFileReadService fileReadService;
    @Autowired
    private IFileUpdateService fileUpdateService;
    @Autowired
    private IMidstoreDocumentService documentService;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private static final String EXPORTOCUMENTTASK = "MIDSTORE_EXPORT_DOCUMENT_TASK";

    @PostMapping(value={"/doPublish/{schemeKey}"})
    @ApiOperation(value="\u53d1\u5e03\u4ea4\u6362\u65b9\u6848")
    public AsyncTaskInfo doPublish(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.executePublish(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_000, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/doExportDocument/{schemeKey}"})
    @ApiOperation(value="\u5bfc\u51fa\u6587\u6863")
    public AsyncTaskInfo doExportDocument(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.doExportDocument(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_000, e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/exportDocumentByScheme/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u6587\u4ef6KEY\u4e0b\u8f7d\u89c4\u8303\u6587\u6863")
    public void exportDocumentByScheme(HttpServletResponse response, @PathVariable(value="schemeKey") String schemeKey) {
        ServletOutputStream outputStream = null;
        try {
            String fileName = "\u5bfc\u51fa\u89c4\u8303\u6587\u6863.docx";
            outputStream = response.getOutputStream();
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            fileName = "attachment;filename=" + fileName;
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", fileName);
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId, EXPORTOCUMENTTASK);
            log.info("\u5bfc\u51fa\u6587\u6863\u5f00\u59cb\uff1a" + schemeKey);
            this.documentService.exportDocument(schemeKey, (OutputStream)outputStream, (AsyncTaskMonitor)monitor);
            outputStream.flush();
            log.info("\u5bfc\u51fa\u6587\u6863\u5b8c\u6210:" + schemeKey);
        }
        catch (Exception e) {
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    @PostMapping(value={"/doLinkBaseDataFromFields/{schemeKey}"})
    @ApiOperation(value="\u6dfb\u52a0\u5173\u8054\u57fa\u7840\u6570\u636e")
    public AsyncTaskInfo doLinkBaseDataFromFields(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.doLinkBaseDataFromFields(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_009, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/doCheckParams/{schemeKey}"})
    @ApiOperation(value="\u68c0\u67e5\u4ea4\u6362\u65b9\u6848\u7684\u53c2\u6570")
    public AsyncTaskInfo doCheckParams(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.doCheckParams(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_010, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/doGetDataFromMidstore/{schemeKey}"})
    @ApiOperation(value="\u62c9\u53d6\u6570\u636e")
    public AsyncTaskInfo doGetDataFromMidstore(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.doGetDataFromMidstore(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_011, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/doPostDataToMidstore/{schemeKey}"})
    @ApiOperation(value="\u63a8\u9001\u6570\u636e")
    public AsyncTaskInfo doPostDataToMidstore(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileExecuteService.doPostDataToMidstore(schemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_011, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getAllSchemeGroups"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u4ea4\u6362\u65b9\u6848\u5206\u7ec4")
    public List<SchemeGroupDTO> getAllSchemeGroups() throws JQException {
        List<SchemeGroupDTO> list = this.fileReadService.listAllSchemeGroups();
        if (list == null || list.size() == 0) {
            SchemeGroupDTO groupDTO = new SchemeGroupDTO();
            try {
                groupDTO.setTitle("\u5168\u90e8\u4ea4\u6362\u65b9\u6848");
                MidstoreResultObject result = this.fileUpdateService.saveSchemeGroup(groupDTO);
                if (StringUtils.isNotEmpty((String)result.getResultKey())) {
                    groupDTO.setKey(result.getResultKey());
                } else {
                    list = this.fileReadService.listAllSchemeGroups();
                }
            }
            catch (MidstoreException e) {
                log.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_001, e.getMessage(), (Throwable)e);
            }
        }
        return list;
    }

    @GetMapping(value={"/getRootSchemeGroups"})
    @ApiOperation(value="\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u6839\u5206\u7ec4")
    public List<SchemeGroupDTO> getRootSchemeGroups() {
        return this.fileReadService.listRootSchemeGroups();
    }

    @GetMapping(value={"/getSchemeGroupsByParent/{groupKey}"})
    @ApiOperation(value="\u6839\u636e\u4e0a\u7ea7\u5206\u7ec4\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u5206\u7ec4")
    public List<SchemeGroupDTO> getSchemeGroupsByParent(@PathVariable(value="groupKey") String groupKey) {
        return this.fileReadService.listSchemeGroupsByParent(groupKey);
    }

    @PostMapping(value={"/updateSchemeGroup"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u5206\u7ec4")
    public void updateSchemeGroup(@RequestBody SchemeGroupDTO groupDTO) throws JQException {
        try {
            this.fileUpdateService.saveSchemeGroup(groupDTO);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_001, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteSchemeGroup/{groupKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u5206\u7ec4")
    public void deleteSchemeGroup(@PathVariable(value="groupKey") String groupKey) throws JQException {
        try {
            this.fileUpdateService.deleteMidstoreSchemeGroup(groupKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_001, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeBasesByGroup/{groupKey}"})
    @ApiOperation(value="\u6839\u636e\u4e0a\u7ea7\u5206\u7ec4\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u7684\u7b80\u5355\u4fe1\u606f")
    public List<SchemeBaseDTO> getSchemesByGroup(@PathVariable(value="groupKey") String groupKey) {
        return this.fileReadService.listMidstoreSchemesInGroup(groupKey);
    }

    @GetMapping(value={"/getSchemByGroup/{groupKey}"})
    @ApiOperation(value="\u6839\u636e\u4e0a\u7ea7\u5206\u7ec4\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u672c\u4fe1\u606f")
    public List<ExchangeSchemeDTO> getSchemByGroup(@PathVariable(value="groupKey") String groupKey) {
        return this.fileReadService.listSchemesInGroup(groupKey);
    }

    @GetMapping(value={"/listSchemeVOsInGroup/{groupKey}"})
    @ApiOperation(value="\u6839\u636e\u4e0a\u7ea7\u5206\u7ec4\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u7684\u4fe1\u606f")
    public List<ExchangeSchemeVO> listSchemeVOsInGroup(@PathVariable(value="groupKey") String groupKey) {
        return this.fileReadService.listSchemeVOsInGroup(groupKey);
    }

    @GetMapping(value={"/getExchangeScheme/{schemeKey}"})
    @ApiOperation(value="\u6839\u636eKEY\u83b7\u53d6\u4ea4\u6362\u65b9\u6848")
    public ExchangeSchemeDTO getExchangeScheme(@PathVariable(value="schemeKey") String schemeKey) {
        return this.fileReadService.listMidstoreSchemeByKey(schemeKey);
    }

    @GetMapping(value={"/getSchemByTablePrefix/{tablePrefix}"})
    @ApiOperation(value="\u6839\u636e\u4e2d\u95f4\u5e93\u524d\u7f00\u83b7\u53d6\u4ea4\u6362\u65b9\u6848\u7684\u4fe1\u606f")
    public List<ExchangeSchemeDTO> getSchemByTablePrefix(@PathVariable(value="tablePrefix") String tablePrefix) {
        return this.fileReadService.listMidstoreSchemeBytablePrefix(tablePrefix);
    }

    @PostMapping(value={"/updateExchangeSchemeBase"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7684\u7b80\u5355\u4fe1\u606f(\u6807\u8bc6\u3001\u6807\u9898\u3001\u5206\u7ec4\uff09")
    public MidstoreResultObject updateExchangeSchemeBase(@RequestBody SchemeBaseDTO schemeBaseDTO) throws JQException {
        try {
            return this.fileUpdateService.saveMidstoreScheme(schemeBaseDTO);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_002, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/updateExchangeScheme"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u672c\u4fe1\u606f")
    public MidstoreResultObject updateExchangeScheme(@RequestBody ExchangeSchemeDTO schemeDTO) throws JQException {
        try {
            return this.fileUpdateService.saveMidstoreScheme(schemeDTO);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_002, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/DeleteExchangeScheme/{schemeKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848")
    public MidstoreResultObject deleteExchangeScheme(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileUpdateService.deleteMidstoreScheme(schemeKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_002, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeInfo/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u65b9\u6848\u4fe1\u606f")
    public SchemeInfoDTO getSchemeInfo(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        SchemeInfoDTO info = this.fileReadService.getMidstoreSchemeInfo(schemeKey);
        if (info == null) {
            try {
                this.fileUpdateService.addDefaultSchemeInfo(schemeKey);
            }
            catch (MidstoreException e) {
                log.error(e.getMessage());
                throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_003, e.getMessage(), (Throwable)e);
            }
            info = this.fileReadService.getMidstoreSchemeInfo(schemeKey);
        }
        return info;
    }

    @PostMapping(value={"/updateSchemeInfo"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7684\u6269\u5c55\u4fe1\u606f")
    public MidstoreResultObject updateSchemeInfo(@RequestBody SchemeInfoDTO schemeInfoDTO) throws JQException {
        try {
            return this.fileUpdateService.saveSchemeInfo(schemeInfoDTO);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_003, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/updateSchemeUsePlanTask/{schemeKey}/{usePlanTask}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u662f\u5426\u542f\u7528\u8ba1\u5212\u4efb\u52a1")
    public MidstoreResultObject updateSchemeUsePlanTask(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="usePlanTask") boolean usePlanTask) throws JQException {
        try {
            return this.fileUpdateService.updateSchemeUsePlanTask(schemeKey, usePlanTask);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_003, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeOrgDataItems/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u6570\u636e")
    public List<SchemeOrgDataItemDTO> getSchemeOrgDataItems(@PathVariable(value="schemeKey") String schemeKey) {
        return this.fileReadService.listMidstoreOrgDataByScheme(schemeKey);
    }

    @PostMapping(value={"/updateSchemeOrgDataItems/{schemeKey}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4fe1\u606f")
    public MidstoreResultObject updateSchemeOrgDataItems(@PathVariable String schemeKey, @RequestBody List<SchemeOrgDataItemDTO> orgDataItems) throws JQException {
        try {
            return this.fileUpdateService.saveSchemeOrgDataItems(schemeKey, orgDataItems);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_004, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteOrgDataItemsByScheme/{schemeKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u7684\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4fe1\u606f")
    public MidstoreResultObject deleteOrgDataItemsByScheme(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileUpdateService.deleteOrgDataItemsByScheme(schemeKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_004, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteOrgDataItemsByKeys"})
    @ApiOperation(value="\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4fe1\u606f")
    public MidstoreResultObject deleteOrgDataItemsByKeys(@RequestBody List<String> orgDataItemKeys) throws JQException {
        try {
            return this.fileUpdateService.deleteOrgDataItemsByKeys(orgDataItemKeys);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_004, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeOrgDataFields/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5")
    public List<SchemeOrgDataFieldDTO> getSchemeOrgDataFields(@PathVariable(value="schemeKey") String schemeKey) {
        return this.fileReadService.listMidstoreOrgDataFieldByScheme(schemeKey);
    }

    @PostMapping(value={"/updateSchemeOrgDataFields/{schemeKey}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u4fe1\u606f")
    public MidstoreResultObject updateSchemeOrgDataFields(@PathVariable(value="schemeKey") String schemeKey, @RequestBody List<SchemeOrgDataFieldDTO> orgDataFields) throws JQException {
        try {
            return this.fileUpdateService.saveSchemeOrgDataFields(schemeKey, orgDataFields);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_005, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteOrgDataFieldsByScheme/{schemeKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u7684\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u4fe1\u606f")
    public MidstoreResultObject deleteOrgDataFieldsByScheme(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileUpdateService.deleteOrgDataFieldsByScheme(schemeKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_005, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteOrgDataFieldsByKeys"})
    @ApiOperation(value="\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u4fe1\u606f")
    public MidstoreResultObject deleteOrgDataFieldsByKeys(@RequestBody List<String> orgDataFieldKeys) throws JQException {
        try {
            return this.fileUpdateService.deleteOrgDataFieldsByKeys(orgDataFieldKeys);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_005, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeBaseDatas/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u57fa\u7840\u6570\u636e")
    public List<SchemeBaseDataDTO> getSchemeBaseDatas(@PathVariable(value="schemeKey") String schemeKey) {
        return this.fileReadService.listMidstoreBaseDataByScheme(schemeKey);
    }

    @PostMapping(value={"/updateSchemBaseDatas/{schemeKey}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u57fa\u7840\u6570\u636e\u4fe1\u606f")
    public MidstoreResultObject updateSchemBaseDatas(@PathVariable(value="schemeKey") String schemeKey, @RequestBody List<SchemeBaseDataDTO> baseDatas) throws JQException {
        try {
            return this.fileUpdateService.saveSchemeBaseDatas(schemeKey, baseDatas);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_006, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteBaseDatasByScheme/{schemeKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e")
    public MidstoreResultObject deleteBaseDatasByScheme(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileUpdateService.deleteBaseDatasByScheme(schemeKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_006, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteBaseDatasByKeys"})
    @ApiOperation(value="\u5220\u9664\u57fa\u7840\u6570\u636e")
    public MidstoreResultObject deleteBaseDatasByKeys(@RequestBody List<String> baseDataKeys) throws JQException {
        try {
            return this.fileUpdateService.deleteBaseDatasByKeys(baseDataKeys);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_006, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeBaseDataFields/{schemeKey}", "/getSchemeBaseDataFields/{schemeKey}/{baseDataKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u57fa\u7840\u6570\u636e\u5b57\u6bb5")
    public List<SchemeBaseDataFieldDTO> getSchemeBaseDataFields(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="baseDataKey", required=false) String baseDataKey) {
        if (StringUtils.isNotEmpty((String)baseDataKey)) {
            return this.fileReadService.listMidstoreBaseDataFieldBySchemeAndBaseData(schemeKey, baseDataKey);
        }
        return this.fileReadService.listMidstoreBaseDataFieldByScheme(schemeKey);
    }

    @PostMapping(value={"/updateSchemBaseDataFields/{schemeKey}/{baseDataKey}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u4fe1\u606f")
    public MidstoreResultObject updateSchemBaseDataFields(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="baseDataKey") String srcBaseDataKey, @RequestBody List<SchemeBaseDataFieldDTO> baseDataFields) throws JQException {
        try {
            return this.fileUpdateService.saveSchemeBaseDataFields(schemeKey, srcBaseDataKey, baseDataFields);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_007, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteBaseDataFieldsBySchemeAndBaseData/{schemeKey}/{baseDataKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e\u5b57\u6bb5")
    public MidstoreResultObject deleteBaseDataFieldsBySchemeAndBaseData(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="baseDataKey") String baseDataKey) throws JQException {
        try {
            return this.fileUpdateService.deleteBaseDataFieldsBySchemeAndBaseData(schemeKey, baseDataKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_007, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteBaseDataFieldsByKeys"})
    @ApiOperation(value="\u5220\u9664\u57fa\u7840\u6570\u636e\u5b57\u6bb5")
    public MidstoreResultObject deleteBaseDataFieldsByKeys(@RequestBody List<String> baseDataFieldKeys) throws JQException {
        try {
            return this.fileUpdateService.deleteBaseDataFieldsByKeys(baseDataFieldKeys);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_007, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getSchemeDataTables/{schemeKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u6307\u6807\u6240\u5728\u5b58\u50a8\u8868\u4fe1\u606f")
    public List<CommonParamDTO> getSchemeDataTables(@PathVariable(value="schemeKey") String schemeKey) {
        return this.fileReadService.listMidstoreDataTableBySheme(schemeKey);
    }

    @GetMapping(value={"/getSchemeFields/{schemeKey}", "/getSchemeFields/{schemeKey}/{srcTableKey}"})
    @ApiOperation(value="\u6839\u636e\u65b9\u6848KEY\u83b7\u53d6\u6307\u6807\u4fe1\u606f")
    public List<SchemeFieldDTO> getSchemeFields(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="srcTableKey", required=false) String srcTableKey) {
        if (StringUtils.isNotEmpty((String)srcTableKey)) {
            return this.fileReadService.listMidstoreFieldByShemeAndTable(schemeKey, srcTableKey);
        }
        return this.fileReadService.listMidstoreFieldBySheme(schemeKey);
    }

    @PostMapping(value={"/updateSchemFields/{schemeKey}", "/updateSchemFields/{schemeKey}/{srcTableKey}"})
    @ApiOperation(value="\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u6307\u6807\u4fe1\u606f")
    public MidstoreResultObject updateSchemeFields(@PathVariable(value="schemeKey") String schemeKey, @PathVariable(value="srcTableKey", required=false) String srcTableKey, @RequestBody List<SchemeFieldDTO> fields) throws JQException {
        try {
            if (StringUtils.isNotEmpty((String)srcTableKey)) {
                return this.fileUpdateService.saveSchemFields(schemeKey, srcTableKey, fields);
            }
            return this.fileUpdateService.saveSchemFields(schemeKey, fields);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_008, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteFieldsByScheme/{schemeKey}"})
    @ApiOperation(value="\u5220\u9664\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807")
    public MidstoreResultObject deleteFieldsByScheme(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            return this.fileUpdateService.deleteFieldsByScheme(schemeKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_008, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteFieldsByKeys"})
    @ApiOperation(value="\u5220\u9664\u6307\u6807")
    public MidstoreResultObject deleteFieldsByKeys(@RequestBody List<String> fieldKeys) throws JQException {
        try {
            return this.fileUpdateService.deleteFieldsByKeys(fieldKeys);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_008, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getMidstorePlanTaskGroupKey"})
    @ApiOperation(value="\u83b7\u53d6\u4e2d\u95f4\u5e93\u8ba1\u5212\u4efb\u52a1\u7684\u5206\u7ec4KEY")
    public String getMidstorePlanTaskGroupKey() {
        return this.fileReadService.getMidstorePlanTaskGroup();
    }

    @GetMapping(value={"/getMidstorePlanTask/{planTaskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4e2d\u95f4\u5e93\u8ba1\u5212\u4efb\u52a1")
    public MidstorePlanTaskDTO getMidstorePlanTask(@PathVariable(value="planTaskKey") String planTaskKey) throws JQException {
        try {
            return this.fileReadService.getMidstorePlanTask(planTaskKey);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_013, e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/updateMidstorePlanTask}"})
    @ApiOperation(value="\u66f4\u65b0\u4e2d\u95f4\u5e93\u8ba1\u5212\u4efb\u52a1")
    public MidstoreResultObject updateMidstorePlanTask(@RequestBody MidstorePlanTaskDTO planTask) throws JQException {
        try {
            return this.fileUpdateService.savePlanTask(planTask);
        }
        catch (MidstoreException e) {
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_006, e.getMessage(), (Throwable)e);
        }
    }

    @GetMapping(value={"/getMidstorePlanTaskLogDetail/{planTaskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4e2d\u95f4\u5e93\u8ba1\u5212\u4efb\u52a1\u7684\u65e5\u5fd7")
    public MidstoreResultObject getMidstorePlanTaskLogDetail(@PathVariable(value="planTaskKey") String planTaskKey) throws JQException {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        try {
            result.setMessage(this.fileReadService.getPlanTaskLogDetail(planTaskKey));
            result.setSuccess(true);
            return result;
        }
        catch (MidstoreException e) {
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
            throw new JQException((ErrorEnum)NrMidstoreErrorEnum.NRDESINGER_EXCEPTION_013, e.getMessage(), (Throwable)e);
        }
    }
}

