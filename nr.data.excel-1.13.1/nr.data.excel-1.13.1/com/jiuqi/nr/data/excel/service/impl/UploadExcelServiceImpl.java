/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.common.CommonMessage
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.logger.DataIoLoggerFactory
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nr.data.common.param.ImportFileDataRange
 *  com.jiuqi.nr.data.common.service.dto.ImportCancledResult
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.common.CommonMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.logger.DataIoLoggerFactory;
import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.data.excel.consts.BatchExportConsts;
import com.jiuqi.nr.data.excel.extend.IExcelImpListener;
import com.jiuqi.nr.data.excel.param.CommonInitData;
import com.jiuqi.nr.data.excel.param.ImpEnv;
import com.jiuqi.nr.data.excel.param.UploadParam;
import com.jiuqi.nr.data.excel.param.bean.ImportResultObject;
import com.jiuqi.nr.data.excel.service.IUploadExcelService;
import com.jiuqi.nr.data.excel.service.impl.UploadExcelBaseService;
import com.jiuqi.nr.data.excel.service.impl.UploadMultiServiceImpl;
import com.jiuqi.nr.data.excel.utils.ExcelErrorUtil;
import com.jiuqi.nr.data.excel.utils.ExcelImportUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UploadExcelServiceImpl
implements IUploadExcelService {
    private static final Logger logger = LoggerFactory.getLogger(UploadExcelServiceImpl.class);
    public static final String SEPARATOR_ONE = " ";
    public static final String SEPARATOR_TWO = "_";
    public static final String SEPARATOR_THREE = "&";
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired
    private UploadExcelBaseService uploadExcelBaseService;
    @Autowired
    private UploadMultiServiceImpl uploadMultiServiceImpl;
    @Autowired
    private DataIoLoggerFactory dataIoLoggerFactory;
    @Autowired(required=false)
    private List<IExcelImpListener> excelImpListeners;
    static Map<String, String> FILETYPEMAP = new HashMap<String, String>();

    @Override
    public Message<CommonMessage> upload(UploadParam param, CommonParams commonParams) throws Exception {
        DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger("EXCEL\u5bfc\u5165\u529f\u80fd", param.getFormSchemeKey(), param.getDimensionSet());
        dataImportLogger.startImport();
        param.setRegionReadOnlyDataLinks(new HashMap<String, CommonInitData>());
        CommonMessage message = new CommonMessage();
        ImportCancledResult importCancledResult = new ImportCancledResult();
        importCancledResult.setImportFileDataRange(ImportFileDataRange.UNIT);
        message.setImportCancledResult(importCancledResult);
        AsyncTaskMonitor asyncTaskMonitor = null;
        if (commonParams != null && this.checkAsyncTaskCancling(asyncTaskMonitor = commonParams.getMonitor(), importCancledResult)) {
            dataImportLogger.cancelImport();
            return message;
        }
        File fileTemp = Paths.get(param.getFilePath(), new String[0]).normalize().toFile();
        if (fileTemp.isDirectory()) {
            this.searchFiles(param, message, asyncTaskMonitor, fileTemp, dataImportLogger);
        }
        if (fileTemp.isFile() && (fileTemp.getName().endsWith(".zip") || fileTemp.getName().endsWith(".ZIP")) || fileTemp.isDirectory()) {
            ImportResultObject resultObject = this.uploadMultiServiceImpl.upload(fileTemp, param, asyncTaskMonitor, message, dataImportLogger);
            message.setDetail((Object)resultObject);
        } else if (param.getFilePath().contains(".") && FILETYPEMAP.containsKey(param.getFilePath().substring(param.getFilePath().lastIndexOf(46) + 1))) {
            this.beforeImport(Paths.get(param.getFilePath(), new String[0]).normalize().toFile(), param);
            this.uploadWorkBook(param, fileTemp, message, asyncTaskMonitor, dataImportLogger);
            if (asyncTaskMonitor != null) {
                importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
                importCancledResult.getSuccessFiles().add(fileTemp.getName());
            }
        } else {
            message = null;
        }
        if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
            dataImportLogger.cancelImport();
            return message;
        }
        this.afterImp(param, message);
        dataImportLogger.finishImport();
        return message;
    }

    private void searchFiles(UploadParam param, CommonMessage message, AsyncTaskMonitor asyncTaskMonitor, File fileTemp, DataImportLogger dataImportLogger) throws IOException, InvalidFormatException {
        File[] list = fileTemp.listFiles();
        ImportCancledResult importCancledResult = message.getImportCancledResult();
        for (File file : list) {
            if (file.isFile()) {
                if (this.checkAsyncTaskCancling(asyncTaskMonitor, importCancledResult)) {
                    dataImportLogger.cancelImport();
                    return;
                }
                this.uploadWorkBook(param, fileTemp, message, asyncTaskMonitor, dataImportLogger);
                importCancledResult.getSuccessFiles().add(file.getName());
                continue;
            }
            this.searchFiles(param, message, asyncTaskMonitor, file, dataImportLogger);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void uploadWorkBook(UploadParam param, File fileTemp, CommonMessage message, AsyncTaskMonitor asyncTaskMonitor, DataImportLogger dataImportLogger) throws IOException, InvalidFormatException {
        block37: {
            ImportResultObject res = new ImportResultObject();
            Workbook workbook = null;
            try {
                String fileName = fileTemp.getName();
                workbook = ExcelImportUtil.create(fileTemp);
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
                IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), param.getFormSchemeKey());
                ImportResultExcelFileObject tempres = this.uploadExcelBaseService.upload(workbook, fileName, param, asyncTaskMonitor, 0.01, 0.8, dataAccessService, message, dataImportLogger);
                tempres.setLocation(fileTemp.getPath());
                res.setFmdmed(tempres.isFmdmed());
                res.setRelationDimensions(tempres.getRelationDimensions());
                message.setDetail((Object)res);
                if (tempres.getImportResultSheetObjectList().size() > 0) {
                    res.setSuccess(false);
                    try (Workbook workbookError = ExcelErrorUtil.exportExcel(res, tempres, workbook);){
                        String uid = UUID.randomUUID().toString();
                        File tempFile = new File(param.getFilePath());
                        String dir = tempFile.getAbsolutePath().replace(tempFile.getName(), "");
                        String filePathNew = dir + uid + BatchExportConsts.SEPARATOR + tempFile.getName();
                        File errorFile = Paths.get(filePathNew, new String[0]).normalize().toFile();
                        try {
                            errorFile.getParentFile().mkdirs();
                            errorFile.createNewFile();
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        try (FileOutputStream out = new FileOutputStream(errorFile);){
                            workbookError.write(out);
                            out.flush();
                            res.setLocation(filePathNew);
                            break block37;
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    break block37;
                }
                res.setSuccess(true);
            }
            finally {
                if (null != workbook) {
                    try {
                        workbook.close();
                    }
                    catch (IOException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void beforeImport(File file, UploadParam param) throws Exception {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx") || file.getName().endsWith(".et")) {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(param.getFormSchemeKey());
            if (formSchemeDefine == null) {
                throw new IllegalArgumentException("formSchemeDefine \u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a.");
            }
            EntityViewDefine iEntityDefine = this.entityViewRunTimeController.buildEntityView(formSchemeDefine.getDw());
            String dimensionName = this.entityMetaService.getDimensionName(iEntityDefine.getEntityId());
            if (param.getDimensionSet().getValue(dimensionName).equals("") || param.getDimensionSet().getValue(dimensionName) == null) {
                IEntityTable entityTable = null;
                IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                iEntityQuery.setEntityView(iEntityDefine);
                iEntityQuery.setAuthorityOperations(AuthorityType.None);
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                executorContext.setPeriodView(formSchemeDefine.getDateTime());
                entityTable = iEntityQuery.executeFullBuild((IContext)executorContext);
                List iEntityRows = entityTable.getAllRows();
                ArrayList<IEntityRow> entityList = new ArrayList<IEntityRow>();
                for (IEntityRow iEntityRow : iEntityRows) {
                    if (!fileName.equals(iEntityRow.getTitle()) && !fileName.equals(iEntityRow.getCode())) continue;
                    entityList.add(iEntityRow);
                }
                String sysSeparator = this.getSysSeparator();
                if (entityList.isEmpty() && fileName.contains(sysSeparator)) {
                    String[] fileNameArray;
                    for (String fileNameInfo : fileNameArray = fileName.split(sysSeparator)) {
                        for (IEntityRow iEntityRow : iEntityRows) {
                            if (!fileNameInfo.equals(iEntityRow.getTitle()) && !fileNameInfo.equals(iEntityRow.getCode())) continue;
                            entityList.add(iEntityRow);
                        }
                    }
                    if (entityList.size() > 1) {
                        entityList.clear();
                        for (String fileNameInfo : fileNameArray) {
                            for (IEntityRow iEntityRow : iEntityRows) {
                                if (!fileNameInfo.equals(iEntityRow.getCode())) continue;
                                entityList.add(iEntityRow);
                            }
                        }
                    }
                }
                if (entityList.size() == 1) {
                    param.getDimensionSet().toDimensionValueSet().setValue(dimensionName, (Object)((IEntityRow)entityList.get(0)).getEntityKeyData());
                } else {
                    String errorCode = ErrorCode.FILEERROR.getErrorCodeMsg();
                    if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILEERROR"))) {
                        errorCode = this.i18nHelper.getMessage("FILEERROR");
                    }
                    String message = "\u6587\u4ef6\u540d\u79f0\u672a\u5339\u914d\u5230\u5355\u4f4d\u6216\u5355\u4f4d\u4e0d\u552f\u4e00";
                    if (StringUtils.isNotEmpty((String)this.i18nHelper.getMessage("FILENAMEUNITCODEERROR"))) {
                        message = this.i18nHelper.getMessage("FILENAMEUNITCODEERROR");
                    }
                    throw new IllegalArgumentException(message + errorCode);
                }
            }
        }
    }

    private String getSysSeparator() {
        String separatorMessage = this.iNvwaSystemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        String separator = SEPARATOR_ONE;
        if (separatorMessage.equals("1")) {
            separator = SEPARATOR_TWO;
        } else if (separatorMessage.equals("2")) {
            separator = SEPARATOR_THREE;
        }
        return separator;
    }

    private void afterImp(UploadParam param, CommonMessage message) {
        if (message != null && message.getDetail() instanceof ImportResultObject) {
            ImportResultObject detail = (ImportResultObject)message.getDetail();
            if (CollectionUtils.isEmpty(detail.getRelationDimensions())) {
                return;
            }
            ImpEnv env = new ImpEnv();
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
            env.setTaskKey(formScheme.getTaskKey());
            env.setFormSchemeKey(param.getFormSchemeKey());
            ArrayList<DimensionCombination> dimensionCombinationList = new ArrayList<DimensionCombination>();
            for (Map<String, DimensionValue> relationDimension : detail.getRelationDimensions()) {
                dimensionCombinationList.add(new DimensionCombinationBuilder(DimensionValueSetUtil.getDimensionValueSet(relationDimension)).getCombination());
            }
            env.setDimensionCombinationList(dimensionCombinationList);
            if (!CollectionUtils.isEmpty(this.excelImpListeners)) {
                for (IExcelImpListener excelImpListener : this.excelImpListeners) {
                    excelImpListener.afterImpSuccess(env);
                }
            }
        }
    }

    private boolean checkAsyncTaskCancling(AsyncTaskMonitor asyncTaskMonitor, ImportCancledResult importCancledResult) {
        if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            importCancledResult.setProgress(asyncTaskMonitor.getLastProgress());
            return true;
        }
        return false;
    }

    static {
        FILETYPEMAP.put("xls", "upload_type_excel");
        FILETYPEMAP.put("zip", "upload_type_zip");
        FILETYPEMAP.put("xlsx", "upload_type_excel");
        FILETYPEMAP.put("et", "upload_type_excel");
    }
}

