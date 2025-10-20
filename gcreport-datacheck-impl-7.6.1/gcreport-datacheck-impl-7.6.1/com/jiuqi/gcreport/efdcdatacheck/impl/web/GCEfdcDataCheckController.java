/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.pdf.ConstantProperties
 *  com.jiuqi.gcreport.common.pdf.DownloadZipDTO
 *  com.jiuqi.gcreport.common.pdf.WordUtil
 *  com.jiuqi.gcreport.efdcdatacheck.client.intf.GCEfdcDataCheckClient
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportPageVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormGroupFieldsInfoVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormInfoVO
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.pdf.ConstantProperties;
import com.jiuqi.gcreport.common.pdf.DownloadZipDTO;
import com.jiuqi.gcreport.common.pdf.WordUtil;
import com.jiuqi.gcreport.efdcdatacheck.client.intf.GCEfdcDataCheckClient;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportPageVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormGroupFieldsInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckReportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckExportImpl;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GCEfdcDataCheckController
implements GCEfdcDataCheckClient {
    private static final Logger logger = LoggerFactory.getLogger(GCEfdcDataCheckController.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private INvwaSystemOptionService systemOptionsService;
    @Autowired
    private EFDCDataCheckReportService efdcReportService;
    @Autowired
    private ConstantProperties pro;
    @Autowired
    private IFormulaRunTimeController formCtrl;
    @Autowired
    private SystemUserService systemUserService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BusinessResponseEntity<EfdcCheckResultGroupVO> processEfdcDataCheckResultGroup(@RequestBody GcFormOperationInfo formOperationInfo) {
        NpContext context = NpContextHolder.getContext();
        EfdcCheckResultGroupVO efdcCheckResultGroupVO = null;
        try {
            efdcCheckResultGroupVO = new EFDCDataCheckImpl().processEfdcDataCheckResultGroup(context, formOperationInfo);
        }
        finally {
            NpContextHolder.clearContext();
        }
        return BusinessResponseEntity.ok((Object)efdcCheckResultGroupVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void exportPdf(@RequestBody GcFormOperationInfo formOperationInfo, HttpServletResponse response) throws Exception {
        new EFDCDataCheckExportImpl().exportPdf(formOperationInfo, response);
    }

    public BusinessResponseEntity<AsyncTaskInfo> efdcBatchDataCheck(@RequestBody GcBatchEfdcCheckInfo batchCheckInfo) {
        String asynTaskID = this.asyncTaskManager.publishTask((Object)batchCheckInfo, GcAsyncTaskPoolType.ASYNCTASK_BATCHEFDCCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return BusinessResponseEntity.ok((Object)asyncTaskInfo);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Map<String, Object>> efdcBatchCheckUnits(@PathVariable(value="asynTaskID") String asynTaskID) {
        List<EfdcCheckResultUnitVO> checkResultUnitVO = new EFDCDataCheckImpl().efdcBatchCheckUnits(asynTaskID);
        HashMap<String, Object> value = new HashMap<String, Object>();
        value.put("efdcCheckResultVOs", checkResultUnitVO);
        String address = this.systemOptionsService.get("fext-settings-group", "EFDCURL");
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("address", address);
        options.put("userName", user.getName());
        value.put("options", options);
        return BusinessResponseEntity.ok(value);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<EfdcCheckResultVO>> efdcBatchCheckResult(@RequestBody GcBatchEfdcQueryParam efdcQueryParam) {
        List<EfdcCheckResultVO> efdcCheckResultVOs = new EFDCDataCheckImpl().efdcBatchCheckResult(efdcQueryParam);
        return BusinessResponseEntity.ok(efdcCheckResultVOs);
    }

    public void batchResultExport(@RequestBody GcBatchEfdcQueryParam efdcQueryParam, HttpServletResponse response) {
        new EFDCDataCheckImpl().batchResultExportExcel(efdcQueryParam, response);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<EfdcCheckConfigVO>> getEfdcDataCheckConfig(@PathVariable(value="taskId") String taskId) throws Exception {
        return new EFDCDataCheckImpl().getEfdcDataCheckConfig(taskId);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> saveEfdcDataCheckConfig(@PathVariable(value="taskId") String taskId, @RequestBody List<EfdcCheckConfigVO> efdcCheckConfigVOs) throws Exception {
        new EFDCDataCheckImpl().saveEfdcDataCheckConfig(taskId, efdcCheckConfigVOs);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskFormInfoVO>> reports(@PathVariable(value="taskId") String taskId, @PathVariable(value="schemeId") String schemeId) {
        List<TaskFormInfoVO> formInfoVOs = new EFDCDataCheckImpl().reports(taskId, schemeId);
        return BusinessResponseEntity.ok(formInfoVOs);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<FormulaSchemeDefine>> cwFormulaScheme(@PathVariable(value="schemeId") String schemeId) {
        return BusinessResponseEntity.ok((Object)this.formCtrl.getAllCWFormulaSchemeDefinesByFormScheme(schemeId));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskFormGroupFieldsInfoVO>> efdcCheckZbs(@PathVariable(value="formId") String formId, @PathVariable(value="cwFormulaSchemeId") String cwFormulaSchemeId) throws Exception {
        List<TaskFormGroupFieldsInfoVO> fieldsInfoVOs = new EFDCDataCheckImpl().efdcCheckZbs(formId, cwFormulaSchemeId);
        return BusinessResponseEntity.ok(fieldsInfoVOs);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<EfdcCheckReportPageVO> reportQuery(@RequestParam(value="pageSize") Integer pageSize, @RequestParam(value="pageNum") Integer pageNum, @RequestParam(value="showQueryCount") boolean showQueryCount, @RequestParam(value="queryConditions") @RequestBody String queryConditions) throws Exception {
        Map conditionMap = (Map)JsonUtils.readValue((String)queryConditions, HashMap.class);
        String userName = NpContextHolder.getContext().getUserName();
        List allUsers = this.systemUserService.getAllUsers();
        if (!conditionMap.containsKey("userName") && !((SystemUser)allUsers.get(0)).getName().equals(userName)) {
            conditionMap.put("userName", userName);
        }
        long count = this.efdcReportService.queryRecordsByCondition(conditionMap).size();
        List<EfdcCheckReportLogVO> vos = this.efdcReportService.queryRecordsByPageCondition(pageSize, pageNum - 1, conditionMap);
        EfdcCheckReportPageVO pageVO = new EfdcCheckReportPageVO(pageSize, pageNum, count, vos);
        return BusinessResponseEntity.ok((Object)pageVO);
    }

    @Transactional(rollbackFor={Exception.class})
    public byte[] pdfView(@PathVariable @NotNull String id) throws IOException {
        byte[] buffer;
        File file = this.efdcReportService.viewPdf(id);
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);){
            int n;
            byte[] b = new byte[1000];
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        }
        catch (IOException e) {
            logger.error("\u6570\u636e\u7a3d\u6838\u529f\u80fd-\u8bfb\u53d6\u6216\u5199\u5165PDF\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38", e);
            throw e;
        }
        finally {
            if (file != null && file.exists()) {
                file.delete();
            }
        }
        return buffer;
    }

    @Transactional(rollbackFor={Exception.class})
    public void batchDownload(@PathVariable(value="ids") @RequestBody Set<String> ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.notNull(ids, "Ids is required; it must not be null");
        String zipName = System.currentTimeMillis() + "";
        String zipPath = this.pro.getTempFilePath() + zipName;
        DownloadZipDTO zipto = this.efdcReportService.batchDownload(ids, zipName);
        if (zipto.getZipfilename() != null) {
            File file = new File(this.pro.getTempFilePath() + File.separator + zipto.getZipfilename());
            response.setContentType("application/octet-stream");
            String fileName = zipto.getZipfilename();
            try {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                logger.error("\u6587\u4ef6\u540d\u7f16\u7801\u5931\u8d25", e);
                throw new IOException("\u6587\u4ef6\u540d\u7f16\u7801\u5931\u8d25", e);
            }
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ServletOutputStream os = response.getOutputStream();){
                int i;
                byte[] buffer = new byte[1024];
                while ((i = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, i);
                }
            }
            catch (IOException e) {
                logger.error("\u8bfb\u53d6\u6216\u5199\u5165ZIP\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38", e);
                throw e;
            }
        }
        if (zipto.getIscomplete().booleanValue()) {
            try {
                WordUtil.delAllFile((String)zipPath);
                File temp = new File(zipPath + this.pro.getZipSuffix());
                if (temp.exists()) {
                    temp.delete();
                    logger.info("\u4e34\u65f6\u6587\u4ef6\u5df2\u5220\u9664: " + temp.getAbsolutePath());
                }
                if ((temp = new File(zipPath)).exists()) {
                    temp.delete();
                    logger.info("\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u5df2\u5220\u9664: " + temp.getAbsolutePath());
                }
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38", e);
            }
        }
    }

    public void downloadExcel(String id, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull((Object)id, "id is required; it must not be null");
        this.efdcReportService.downloadExcel(id, response);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> bacthDelete(@PathVariable(value="ids") @RequestBody Set<String> ids) {
        Assert.notNull(ids, "Ids is required; it must not be null");
        return BusinessResponseEntity.ok((Object)this.efdcReportService.batchDeleteByRecids(ids));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> clearMongoFiles(@RequestParam(value="dateStr") String dateStr) throws Exception {
        this.efdcReportService.clearMongoFiles(dateStr);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<FormTree> queryEfdcConfigReports(String schemeId, String dataTime) {
        FormTree formTree = new EFDCDataCheckImpl().queryDataCheckConfigForms(schemeId, dataTime);
        return BusinessResponseEntity.ok((Object)formTree);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<FormTree> queryEfdcConfigReportsByReport(String schemeId, Map<String, DimensionValue> dimensionSetMap) {
        FormTree formTree = new EFDCDataCheckImpl().queryDataCheckConfigFormsByReport(schemeId, dimensionSetMap);
        return BusinessResponseEntity.ok((Object)formTree);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> createEfdcDataCheckReport(EfdcCheckCreateReportVO efdcCheckCreateReportVO) {
        return this.efdcReportService.createEfdcDataCheckReport(efdcCheckCreateReportVO);
    }

    public BusinessResponseEntity<String> efdcCheckUser(EfdcCheckUserVo efdcCheckUserVo) {
        return BusinessResponseEntity.ok((Object)this.efdcReportService.efdcCheckUser(efdcCheckUserVo));
    }

    public BusinessResponseEntity<String> shareFile(GcEfdcShareFileVO gcEfdcShareFileVO) {
        return BusinessResponseEntity.ok((Object)this.efdcReportService.shareFile(gcEfdcShareFileVO));
    }
}

