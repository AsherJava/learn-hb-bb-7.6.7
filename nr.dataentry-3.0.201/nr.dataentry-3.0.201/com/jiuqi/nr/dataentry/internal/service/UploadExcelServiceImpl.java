/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.logger.DataIoLoggerFactory
 *  com.jiuqi.nr.data.excel.param.UploadParam
 *  com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService
 *  com.jiuqi.nr.data.excel.service.impl.UploadExcelServiceImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.logger.DataIoLoggerFactory;
import com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.options.DataentryOptionsUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.OrderAfterImport;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IUploadTypeExcelService;
import com.jiuqi.nr.dataentry.service.IUploadTypeService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.dataentry.util.ExcelImportUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service(value="upload_type_excel")
public class UploadExcelServiceImpl
implements IUploadTypeService {
    private static final Logger logger = LoggerFactory.getLogger(UploadExcelServiceImpl.class);
    private static final String MODULEEXCEL = "EXCEL\u5bfc\u5165";
    @Autowired
    private IUploadTypeExcelService uploadTypeExcelService;
    @Resource
    private IRunTimeViewController controller;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired
    private DataentryOptionsUtil dataentryOptionsUtil;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private com.jiuqi.nr.data.excel.service.impl.UploadExcelServiceImpl uploadExcelServiceImpl;
    @Autowired
    private UploadExcelBaseService uploadExcelBaseService;
    @Autowired
    private DataIoLoggerFactory dataIoLoggerFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ImportResultObject upload(File fileTemp, UploadParam param, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            PathUtils.validatePathManipulation((String)fileTemp.getPath());
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("EXCEL\u5bfc\u5165\u670d\u52a1", OperLevel.USER_OPER);
        ImportResultObject res = new ImportResultObject();
        FileInputStream input = null;
        Workbook workbook = null;
        FormSchemeDefine formScheme = this.controller.getFormScheme(param.getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = new LogDimensionCollection();
        logDimension.setDw(formScheme.getDw(), new String[]{param.getDimensionSet().get(queryEntity.getDimensionName()).getValue()});
        logDimension.setPeriod(formScheme.getDateTime(), param.getDimensionSet().get("DATATIME").getValue());
        logHelper.info(formScheme.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f00\u59cb", "Excel\u5bfc\u5165\u5355\u4e2a\u6587\u4ef6\u6587\u4ef6:" + fileTemp.getName());
        try {
            List relationDimensions;
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(param.getTaskKey());
            jtableContext.setFormSchemeKey(param.getFormSchemeKey());
            jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
            jtableContext.setDimensionSet(param.getDimensionSet());
            jtableContext.setVariableMap(param.getVariableMap());
            ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, null, Consts.FormAccessLevel.FORM_DATA_WRITE);
            ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
            readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
            String fileName = fileTemp.getName();
            workbook = ExcelImportUtil.create(fileTemp);
            com.jiuqi.nr.data.excel.param.UploadParam pramsss = new com.jiuqi.nr.data.excel.param.UploadParam();
            pramsss.setAppending(false);
            pramsss.setFilePath(param.getFilePath());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(param.getDimensionSet());
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionValueSet);
            pramsss.setDimensionSet(builder.getCombination());
            pramsss.setFormSchemeKey(param.getFormSchemeKey());
            IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(param.getTaskKey(), param.getFormSchemeKey());
            CommonMessage message = new CommonMessage();
            DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger("EXCEL\u5bfc\u5165\u529f\u80fd", pramsss.getFormSchemeKey(), pramsss.getDimensionSet());
            ImportResultExcelFileObject tempres = this.uploadExcelBaseService.upload(workbook, fileName, pramsss, asyncTaskMonitor, 0.01, 0.8, dataAccessService, message, dataImportLogger);
            tempres.setLocation(fileTemp.getPath());
            res.setFmdmed(tempres.isFmdmed());
            if (!tempres.isSuccessIs().booleanValue()) {
                try (Workbook workbookError = null;){
                    res.setSuccess(false);
                    workbookError = ExcelErrorUtil.exportExcel(res, tempres, ExcelImportUtil.create(fileTemp));
                    String uid = UUID.randomUUID().toString();
                    String filePath = fileTemp.getPath();
                    String filePathNew = filePath.replace(param.getFileLocation(), param.getFileLocation() + uid);
                    String fileLocation = param.getFileLocation() + uid + BatchExportConsts.SEPARATOR + fileName;
                    File errorFile = new File(filePathNew);
                    try (FileOutputStream out = new FileOutputStream(errorFile);){
                        errorFile.getParentFile().mkdirs();
                        errorFile.createNewFile();
                        workbookError.write(out);
                        out.flush();
                        res.setLocation(fileLocation);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        logHelper.error(formScheme.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage());
                    }
                }
            } else {
                res.setSuccess(true);
            }
            if ((("".equals(param.getAutoCacl()) || Consts.ASYNC_PARAM_AUTOCACL_NULL == param.getAutoCacl()) && this.dataentryOptionsUtil.autoCaclUpload() || "1".equals(param.getAutoCacl())) && !(relationDimensions = tempres.getRelationDimensions()).isEmpty()) {
                Map mergeDimension = DimensionValueSetUtil.mergeDimension((List)relationDimensions);
                String childrenTaskId = UUID.randomUUID().toString();
                SimpleAsyncProgressMonitor childrenAsyncTaskMonitor = new SimpleAsyncProgressMonitor(childrenTaskId, this.cacheObjectResourceRemote, asyncTaskMonitor);
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(mergeDimension);
                batchCalculateInfo.setFormSchemeKey(param.getFormSchemeKey());
                batchCalculateInfo.setFormulaSchemeKey(param.getFormulaSchemeKey());
                batchCalculateInfo.setTaskKey(param.getTaskKey());
                batchCalculateInfo.setVariableMap(param.getVariableMap() != null ? param.getVariableMap() : new HashMap<String, Object>());
                batchCalculateInfo.setContext(jtableContext);
                this.batchCalculateService.batchCalculateForm(batchCalculateInfo, childrenAsyncTaskMonitor);
                this.jLoggerAspect.log(batchCalculateInfo.getContext(), "\u6570\u636e\u6267\u884c\u81ea\u52a8\u8fd0\u7b97");
                String calculate = "calculate_success_info";
                asyncTaskMonitor.progressAndMessage(0.9, calculate);
            }
            String childrenTaskId = UUID.randomUUID().toString();
            OrderAfterImport orderAfterImport = new OrderAfterImport();
            orderAfterImport.setFormSchemeKey(param.getFormSchemeKey());
            orderAfterImport.setFormulaSchemeKey(param.getFormulaSchemeKey());
            orderAfterImport.setTaskKey(param.getTaskKey());
            orderAfterImport.setContext(jtableContext);
            orderAfterImport.setImportSuccess(tempres.isSuccessIs());
            this.publisher.publishEvent((Object)orderAfterImport);
            String orderInfo = "order_success_info";
            asyncTaskMonitor.progressAndMessage(0.98, orderInfo);
        }
        catch (Exception e1) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e1.getMessage(), e1);
            logHelper.error(formScheme.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u51fa\u9519\u539f\u56e0\uff1a" + e1.getMessage());
        }
        finally {
            if (null != workbook) {
                try {
                    workbook.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    logHelper.error(formScheme.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5f02\u5e38", "\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage());
                }
            }
            if (null != input) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        try {
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            logHelper.info(param.getTaskKey(), logDimension, "EXCEL\u5bfc\u5165\u5b8c\u6210", "Excel\u5bfc\u5165\u5355\u4e2a\u6587\u4ef6\u6587\u4ef6:" + fileTemp.getName() + "\u7ed3\u675f\n" + gson.toJson((Object)res));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;
    }
}

