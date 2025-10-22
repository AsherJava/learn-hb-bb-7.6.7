/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.sensitive.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.sensitive.bean.ExportData;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.bean.viewObject.SensitiveWordViewObject;
import com.jiuqi.nr.sensitive.common.CheckResultObject;
import com.jiuqi.nr.sensitive.common.ResponseResult;
import com.jiuqi.nr.sensitive.common.ResultObject;
import com.jiuqi.nr.sensitive.dao.SensitiveWordDao;
import com.jiuqi.nr.sensitive.service.BatchCheckBillDataService;
import com.jiuqi.nr.sensitive.service.CheckSensitiveWordService;
import com.jiuqi.nr.sensitive.service.SensitiveWordService;
import com.jiuqi.nr.sensitive.util.ExcelUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SensitiveWordServiceImpl
implements SensitiveWordService {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordServiceImpl.class);
    private static final String INDEX = "\u5e8f\u53f7";
    private static final String TYPE = "\u89c4\u5219\u7c7b\u578b";
    private static final String INFO = "\u89c4\u5219\u5185\u5bb9";
    private static final String DESCRIPTION = "\u89c4\u5219\u63cf\u8ff0";
    private static final String EFFECTIVE = "\u542f\u7528";
    private static final String WORD_TYPE_1 = "\u5b57\u7b26\u4e32\u5305\u542b\u7c7b";
    private static final String WORD_TYPE_2 = "\u8868\u8fbe\u5f0f\u7c7b";
    private static final String SENSITIVE_TYPE_1 = "\u654f\u611f\u8bcd\u4fe1\u606f";
    private static final String SENSITIVE_TYPE_2 = "\u767d\u540d\u5355";
    private static final Integer INDEX_NUM = 0;
    private static final Integer TYPE_NUM = 1;
    private static final Integer INFO_NUM = 2;
    private static final Integer DESCRIPTION_NUM = 3;
    private static final Integer EFFECTIVE_NUM = 4;
    public static final String SEPARATOR = File.separator;
    public static final String ROOT_LOCATION = System.getProperty("java.io.tmpdir");
    public static final String EXPORTDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "export";
    private static final Integer MAX_LENGTH_OF_INFO = 100001;
    @Autowired
    private SensitiveWordDao sensitiveWordDao;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private CheckSensitiveWordService checkSensitiveWordService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired(required=false)
    private BatchCheckBillDataService batchCheckBillDataService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public List<SensitiveWordViewObject> queryAllSensitiveWord(Integer pageNum, Integer pageRow, Integer sensitiveWordType) {
        List<SensitiveWordViewObject> sensitiveWordViewObjectList = this.returnSensitiveWordViewList(this.sensitiveWordDao.queryAllSensitiveWordWithType(pageNum, pageRow, sensitiveWordType));
        return sensitiveWordViewObjectList;
    }

    @Override
    public List<SensitiveWordViewObject> getSensitiveWordWithType(String sensitiveWordInfo, Integer sensitiveWordType, Integer pageNum, Integer pageRow) {
        List<SensitiveWordViewObject> sensitiveWordViewObjectList = this.returnSensitiveWordViewList(this.sensitiveWordDao.getSensitiveWordWithType(sensitiveWordInfo, sensitiveWordType, pageNum, pageRow));
        return sensitiveWordViewObjectList;
    }

    @Override
    public ResponseResult<Boolean> insertSensitiveWord(SensitiveWordViewObject sensitiveWordViewObject) {
        SensitiveWordDaoObject sensitiveWordDaoObject = new SensitiveWordDaoObject(sensitiveWordViewObject);
        SensitiveWordDaoObject sensitiveWordDaoObjectInfo = this.sensitiveWordDao.getSensitiveWordBySensitiveInfo(sensitiveWordDaoObject.getSensitiveInfo(), sensitiveWordDaoObject.getSensitiveType());
        if (sensitiveWordDaoObjectInfo != null) {
            return ResponseResult.error("\u654f\u611f\u8bcd\u5185\u5bb9\u5df2\u5b58\u5728");
        }
        if (sensitiveWordDaoObject.getSensitiveType() == 1) {
            try {
                Pattern.compile(sensitiveWordDaoObject.getSensitiveInfo());
            }
            catch (PatternSyntaxException e) {
                return ResponseResult.error("\u8868\u8fbe\u5f0f\u683c\u5f0f\u9519\u8bef");
            }
        }
        sensitiveWordDaoObject.setSensitiveWordKey(UUID.randomUUID().toString());
        sensitiveWordDaoObject.setModifyUser(NpContextHolder.getContext().getUserName());
        sensitiveWordDaoObject.setModifyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int result = this.sensitiveWordDao.insertSensitiveWord(sensitiveWordDaoObject);
        if (result > 0) {
            this.publisher.publishEvent(sensitiveWordDaoObject);
            return ResponseResult.success("\u654f\u611f\u8bcd\u6dfb\u52a0\u6210\u529f", true);
        }
        return ResponseResult.error("\u654f\u611f\u8bcd\u6dfb\u52a0\u5931\u8d25");
    }

    @Override
    public ResponseResult<Boolean> updateSensitiveWord(SensitiveWordViewObject sensitiveWordViewObject) {
        SensitiveWordDaoObject sensitiveWordDaoObject = new SensitiveWordDaoObject(sensitiveWordViewObject);
        sensitiveWordDaoObject.setSensitiveWordKey(sensitiveWordViewObject.getSensitiveWordKey());
        sensitiveWordDaoObject.setModifyUser(NpContextHolder.getContext().getUserName());
        SensitiveWordDaoObject sensitiveWordDaoObjectInfo = this.sensitiveWordDao.getSensitiveWordBySensitiveInfo(sensitiveWordDaoObject.getSensitiveInfo(), sensitiveWordDaoObject.getSensitiveType());
        if (sensitiveWordDaoObjectInfo != null && !sensitiveWordDaoObjectInfo.getSensitiveWordKey().equals(sensitiveWordDaoObject.getSensitiveWordKey())) {
            return ResponseResult.error("\u654f\u611f\u8bcd\u5185\u5bb9\u5df2\u5b58\u5728");
        }
        int result = this.sensitiveWordDao.updateSensitiveWord(sensitiveWordDaoObject);
        this.publisher.publishEvent(sensitiveWordDaoObject);
        return ResponseResult.success("\u654f\u611f\u8bcd\u4fee\u6539\u6210\u529f", true);
    }

    @Override
    public Boolean deleteSensitiveWord(List<String> sensitiveWordKeyList) {
        boolean allIsDelete = true;
        for (String sensitiveWordKey : sensitiveWordKeyList) {
            allIsDelete = this.sensitiveWordDao.deleteSensitiveWord(sensitiveWordKey);
        }
        SensitiveWordDaoObject sensitiveWordDaoObject = new SensitiveWordDaoObject();
        this.publisher.publishEvent(sensitiveWordDaoObject);
        return allIsDelete;
    }

    private List<SensitiveWordViewObject> returnSensitiveWordViewList(List<SensitiveWordDaoObject> sensitiveWordDaoObjectList) {
        ArrayList<SensitiveWordViewObject> sensitiveWordViewObjectList = new ArrayList<SensitiveWordViewObject>();
        if (sensitiveWordDaoObjectList != null && sensitiveWordDaoObjectList.size() > 0) {
            for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordDaoObjectList) {
                SensitiveWordViewObject sensitiveWordViewObject = new SensitiveWordViewObject(sensitiveWordDaoObject);
                sensitiveWordViewObjectList.add(sensitiveWordViewObject);
            }
        }
        return sensitiveWordViewObjectList;
    }

    @Override
    public ExportData exportAllSensitiveWord() {
        XSSFWorkbook workbook = null;
        workbook = this.createAllSensitiveWordReturnInfo(this.sensitiveWordDao.queryAllSensitiveWordByWordType(0), this.sensitiveWordDao.queryAllSensitiveWordByWordType(1));
        if (null != workbook) {
            ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
            try {
                workbook.write(os);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            byte[] byteArray = os.toByteArray();
            long now = Instant.now().toEpochMilli();
            Date date = new Date(now);
            SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHH");
            String formatDateFolder = dateFormatForFolder.format(date);
            String fileName = "\u654f\u611f\u8bcd\u4fe1\u606f_" + formatDateFolder + ".xlsx";
            ExportData formulaExportData = new ExportData(fileName, byteArray);
            return formulaExportData;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ResultObject importAllSensitiveWord(MultipartFile file) {
        ResultObject resultObject;
        block47: {
            resultObject = new ResultObject();
            try (InputStream ins = file.getInputStream();
                 Workbook excel = ExcelUtils.create(ins);){
                if (excel == null) break block47;
                Sheet sensitiveSheet = excel.getSheet(SENSITIVE_TYPE_1);
                boolean isCanImport = this.checkImportSheet(sensitiveSheet);
                List<Object> checkResultObjectList = new ArrayList<CheckResultObject>();
                if (isCanImport) {
                    try {
                        checkResultObjectList = this.checkImportDataInfo(sensitiveSheet);
                        if (checkResultObjectList.size() > 0) {
                            resultObject.setData(checkResultObjectList);
                            resultObject.setMessage("\u5bfc\u5165\u5931\u8d25\uff01");
                            resultObject.setState(false);
                            ResultObject sensitiveWordList = resultObject;
                            return sensitiveWordList;
                        }
                        List<SensitiveWordDaoObject> sensitiveWordList = this.changeToDaoObject(sensitiveSheet);
                        this.sensitiveWordDao.deleteSensitiveWordByType(0);
                        this.insertSensitiveWordOfSheet(sensitiveWordList);
                        break block47;
                    }
                    catch (Exception e) {
                        checkResultObjectList.add(new CheckResultObject(e.getMessage()));
                        resultObject.setData(checkResultObjectList);
                        resultObject.setMessage(e.getMessage());
                        resultObject.setState(false);
                        ResultObject resultObject2 = resultObject;
                        return resultObject2;
                    }
                }
                resultObject.setMessage("\u5bfc\u5165\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u8bf7\u5728\u6a21\u677f\u57fa\u7840\u4e0a\u8fdb\u884c\u4fee\u6539\uff01\uff01\uff01");
                resultObject.setState(false);
                ResultObject resultObject3 = resultObject;
                return resultObject3;
            }
            catch (IOException e) {
                ArrayList<CheckResultObject> checkResultObjectList = new ArrayList<CheckResultObject>();
                checkResultObjectList.add(new CheckResultObject(e.getMessage()));
                resultObject.setData(checkResultObjectList);
                resultObject.setMessage(e.getMessage());
                resultObject.setState(false);
                return resultObject;
            }
        }
        resultObject.setMessage("\u5bfc\u5165\u5b8c\u6210\uff01");
        resultObject.setState(true);
        return resultObject;
    }

    @Override
    public Integer batchCheckSensitiveWord(String downLoadKey, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        List taskDefines = this.iRunTimeViewController.getAllTaskDefines();
        ArrayList<List<String>> batchCheckSensitiveResultList = new ArrayList<List<String>>();
        HashSet tableNameSet = new HashSet();
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        asyncTaskMonitor.progressAndMessage(0.0, "\u68c0\u6d4b\u5f00\u59cb");
        if (taskDefines != null && taskDefines.size() > 0) {
            block0: for (int j = 0; j < taskDefines.size(); ++j) {
                asyncTaskMonitor.progressAndMessage(0.9 * (((double)j + 1.0) / (double)taskDefines.size()), "\u68c0\u6d4b\u4e2d");
                List formSchemeDefineList = this.iRunTimeViewController.queryFormSchemeByTask(((TaskDefine)taskDefines.get(j)).getKey());
                if (formSchemeDefineList.size() <= 0 || formSchemeDefineList == null || batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) break;
                for (FormSchemeDefine formSchemeDefine : formSchemeDefineList) {
                    if (batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) continue block0;
                    this.handleFormSchemeDefineOfSensitive(formSchemeDefine, asyncTaskMonitor, (TaskDefine)taskDefines.get(j), batchCheckSensitiveResultList);
                }
            }
        }
        List<String> titleStringList = this.buildTitleList();
        ArrayList<String> titleStringListOfBill = new ArrayList();
        ArrayList<List<String>> batchCheckSensitiveResultListOfBill = new ArrayList();
        if (this.batchCheckBillDataService != null) {
            titleStringListOfBill = this.batchCheckBillDataService.batchCheckSensitiveSheetTitleInfoOfBillData();
            batchCheckSensitiveResultListOfBill = this.batchCheckBillDataService.batchCheckSensitiveDataInfoOfBillData();
        }
        this.exportExcelOfSensitiveWordInfo(downLoadKey, batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO ? batchCheckSensitiveResultList.subList(0, MAX_LENGTH_OF_INFO - 1) : batchCheckSensitiveResultList, titleStringList, batchCheckSensitiveResultListOfBill, titleStringListOfBill);
        CheckResultObject checkResultObject = new CheckResultObject();
        if (batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) {
            checkResultObject.setMaxLength(true);
            checkResultObject.setErrorNum(MAX_LENGTH_OF_INFO - 1);
        } else {
            checkResultObject.setMaxLength(false);
            checkResultObject.setErrorNum(batchCheckSensitiveResultList.size());
        }
        asyncTaskMonitor.finish("\u68c0\u6d4b\u5b8c\u6210", (Object)checkResultObject);
        return batchCheckSensitiveResultList.size();
    }

    private void handleFormSchemeDefineOfSensitive(FormSchemeDefine formSchemeDefine, AsyncTaskMonitor asyncTaskMonitor, TaskDefine taskDefine, List<List<String>> batchCheckSensitiveResultList) throws JQException {
        String[] entityKeys = formSchemeDefine.getMasterEntitiesKey().split(";");
        String entityKey = formSchemeDefine.getMasterEntitiesKey().split(";")[0];
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityKey);
        IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(entityViewDefine.getEntityId());
        IEntityTable entityTables = null;
        try {
            IEntityQuery iEntityQuery = this.iEntityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            entityTables = iEntityQuery.executeReader((IContext)new ExecutorContext(this.iDataDefinitionRuntimeController));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            asyncTaskMonitor.error("\u67e5\u8be2\u4e3b\u4f53\u4fe1\u606f\u5f02\u5e38", (Throwable)e);
        }
        List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
        if (formDefineList != null && formDefineList.size() > 0 && batchCheckSensitiveResultList.size() < MAX_LENGTH_OF_INFO) {
            for (FormDefine formDefine : formDefineList) {
                if (batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) break;
                this.handleFormDefineOfSensitive(formDefine, entityTables, formSchemeDefine, asyncTaskMonitor, taskDefine, batchCheckSensitiveResultList, entityKeys, iEntityDefine);
            }
        }
    }

    private void handleFormDefineOfSensitive(FormDefine formDefine, IEntityTable entityTables, FormSchemeDefine formSchemeDefine, AsyncTaskMonitor asyncTaskMonitor, TaskDefine taskDefine, List<List<String>> batchCheckSensitiveResultList, String[] entityKeys, IEntityDefine iEntityDefine) {
        List formGroupDefines = this.iRunTimeViewController.getFormGroupsByFormKey(formDefine.getKey());
        FormGroupDefine formGroupDefine = (FormGroupDefine)formGroupDefines.get(0);
        List dataRegionDefines = this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey());
        if (dataRegionDefines != null && dataRegionDefines.size() > 0 && batchCheckSensitiveResultList.size() < MAX_LENGTH_OF_INFO) {
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                if (batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) break;
                this.handleDataRegionDefineOfSensitive(entityTables, formSchemeDefine, asyncTaskMonitor, taskDefine, batchCheckSensitiveResultList, entityKeys, iEntityDefine, formGroupDefine, formDefine, dataRegionDefine);
            }
        }
    }

    private void handleDataRegionDefineOfSensitive(IEntityTable entityTables, FormSchemeDefine formSchemeDefine, AsyncTaskMonitor asyncTaskMonitor, TaskDefine taskDefine, List<List<String>> batchCheckSensitiveResultList, String[] entityKeys, IEntityDefine iEntityDefine, FormGroupDefine formGroupDefine, FormDefine formDefine, DataRegionDefine dataRegionDefine) {
        List dataLinkDefineList = this.iRunTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
        if (dataLinkDefineList != null) {
            ArrayList<String> fieldKeys = new ArrayList<String>();
            for (DataLinkDefine dataLinkDefine : dataLinkDefineList) {
                fieldKeys.add(dataLinkDefine.getLinkExpression());
            }
            if (fieldKeys != null && fieldKeys.size() > 0) {
                try {
                    List fieldDefineList = this.iDataDefinitionRuntimeController.queryFieldDefines(fieldKeys);
                    IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
                    for (FieldDefine field : fieldDefineList) {
                        dataQuery.addColumn(field);
                    }
                    dataQuery.setMasterKeys(new DimensionValueSet());
                    ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
                    IReadonlyTable dataTable = dataQuery.executeReader(context);
                    if (dataTable.getCount() > 0) {
                        block4: for (int i = 0; i < dataTable.getCount() && batchCheckSensitiveResultList.size() < MAX_LENGTH_OF_INFO; ++i) {
                            IDataRow iDataRow = dataTable.getItem(i);
                            for (FieldDefine fieldDefine : fieldDefineList) {
                                if (batchCheckSensitiveResultList.size() >= MAX_LENGTH_OF_INFO) continue block4;
                                this.handleFieldDefineOfSensitive(entityTables, taskDefine, formSchemeDefine, batchCheckSensitiveResultList, entityKeys, iEntityDefine, formGroupDefine, formDefine, dataRegionDefine, iDataRow, fieldDefine);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    asyncTaskMonitor.error("\u68c0\u6d4b\u8fc7\u7a0b\u5f02\u5e38", (Throwable)e);
                }
            }
        }
    }

    private void handleFieldDefineOfSensitive(IEntityTable entityTables, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, List<List<String>> batchCheckSensitiveResultList, String[] entityKeys, IEntityDefine iEntityDefine, FormGroupDefine formGroupDefine, FormDefine formDefine, DataRegionDefine dataRegionDefine, IDataRow iDataRow, FieldDefine fieldDefine) {
        String fieldValue = iDataRow.getAsString(fieldDefine);
        List<SensitiveWordDaoObject> sensitiveWordList = this.checkSensitiveWordService.thisWordIsSensitiveWord(fieldValue);
        if (sensitiveWordList != null && sensitiveWordList.size() > 0) {
            StringBuilder sensitiveWordInfo = new StringBuilder();
            StringBuilder sensitiveWordDesc = new StringBuilder();
            for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordList) {
                sensitiveWordInfo.append(sensitiveWordDaoObject.getSensitiveInfo());
                sensitiveWordInfo.append(";");
                sensitiveWordDesc.append(sensitiveWordDaoObject.getSensitiveDescription() == null ? "" : sensitiveWordDaoObject.getSensitiveDescription());
                if (sensitiveWordDaoObject.getSensitiveDescription() == null) continue;
                sensitiveWordDesc.append(";");
            }
            DimensionValueSet dimensionValueSet = iDataRow.getRowKeys();
            IEntityRow iEntityRow = entityTables.findByCode((String)dimensionValueSet.getValue(iEntityDefine.getDimensionName()));
            ArrayList<String> entityInfoList = new ArrayList<String>();
            StringBuilder entityInfoString = new StringBuilder();
            for (int entityIndex = 0; entityIndex < entityKeys.length; ++entityIndex) {
                if (!entityKeys[entityIndex].equals(dimensionValueSet.getValue("DATATIME")) || !entityKeys[entityIndex].equals(dimensionValueSet.getValue(iEntityDefine.getDimensionName()))) continue;
                entityInfoString.append(entityKeys[entityIndex]);
                entityInfoString.append(";");
                entityInfoList.add(entityKeys[entityIndex]);
            }
            String entityKeyInfo = dimensionValueSet.getValue(iEntityDefine.getDimensionName()).toString();
            String periodInfo = dimensionValueSet.getValue("DATATIME") == null ? "" : dimensionValueSet.getValue("DATATIME").toString();
            DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
            StringBuilder infoString = new StringBuilder("");
            for (int index = 0; index < dimensionSet.size(); ++index) {
                String dimString = dimensionSet.get(index);
                if (dimString.equals("DATATIME") || dimString.equals(iEntityDefine.getDimensionName()) || dimensionValueSet.getValue(dimString) == null || entityInfoList.contains(dimensionValueSet.getValue(dimString))) continue;
                infoString.append(dimensionValueSet.getValue(dimString));
                infoString.append(";");
            }
            List<String> batchCheckSensitiveResult = this.buildBatchCheckResult(taskDefine.getTitle(), formSchemeDefine.getTitle(), formGroupDefine.getTitle(), formDefine.getTitle(), fieldDefine, iEntityRow.getTitle(), iEntityRow.getCode(), periodInfo, entityInfoString.toString(), infoString.toString(), fieldValue, sensitiveWordInfo.toString(), sensitiveWordDesc.toString());
            batchCheckSensitiveResultList.add(batchCheckSensitiveResult);
        }
    }

    private List<String> buildBatchCheckResult(String taskTitle, String formSchemeTitle, String formGroupTitle, String formTitle, FieldDefine fieldDefine, String iEntityTitle, String iEntityCode, String periodInfo, String infoString, String entityString, String fieldValue, String sensitiveWordInfo, String sensitiveWordDesc) {
        ArrayList<String> batchCheckSensitiveResult = new ArrayList<String>();
        batchCheckSensitiveResult.add(taskTitle);
        batchCheckSensitiveResult.add(formSchemeTitle);
        batchCheckSensitiveResult.add(formGroupTitle);
        batchCheckSensitiveResult.add(formTitle);
        batchCheckSensitiveResult.add(fieldDefine.getTitle());
        batchCheckSensitiveResult.add(fieldDefine.getCode());
        List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(fieldDefine.getOwnerTableKey());
        if (deployInfoByDataTableKey != null && !deployInfoByDataTableKey.isEmpty()) {
            batchCheckSensitiveResult.add(((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName());
        }
        batchCheckSensitiveResult.add(fieldDefine.getCode());
        batchCheckSensitiveResult.add(iEntityTitle);
        batchCheckSensitiveResult.add(iEntityCode);
        batchCheckSensitiveResult.add(periodInfo);
        batchCheckSensitiveResult.add(infoString);
        batchCheckSensitiveResult.add(entityString);
        batchCheckSensitiveResult.add(fieldValue);
        batchCheckSensitiveResult.add(sensitiveWordInfo);
        batchCheckSensitiveResult.add(sensitiveWordDesc);
        return batchCheckSensitiveResult;
    }

    private List<String> buildTitleList() {
        ArrayList<String> titleStringList = new ArrayList<String>();
        titleStringList.add("\u62a5\u8868\u4efb\u52a1");
        titleStringList.add("\u62a5\u8868\u65b9\u6848");
        titleStringList.add("\u62a5\u8868\u5206\u7ec4");
        titleStringList.add("\u62a5\u8868\u540d\u79f0");
        titleStringList.add("\u6307\u6807\u6807\u9898");
        titleStringList.add("\u6307\u6807\u6807\u8bc6");
        titleStringList.add("\u7269\u7406\u8868\u6807\u8bc6");
        titleStringList.add("\u7269\u7406\u8868\u5b57\u6bb5\u6807\u8bc6");
        titleStringList.add("\u5355\u4f4d\u6807\u9898");
        titleStringList.add("\u5355\u4f4d\u4ee3\u7801");
        titleStringList.add("\u65f6\u671f\u4fe1\u606f");
        titleStringList.add("\u60c5\u666f");
        titleStringList.add("\u4e1a\u52a1\u4e3b\u952e\u6807\u9898");
        titleStringList.add("\u6570\u636e\u5185\u5bb9");
        titleStringList.add("\u654f\u611f\u4fe1\u606f\u5185\u5bb9");
        titleStringList.add("\u654f\u611f\u4fe1\u606f\u63cf\u8ff0");
        return titleStringList;
    }

    private void exportExcelOfSensitiveWordInfo(String downLoadKey, List<List<String>> batchCheckSensitiveResultList, List<String> titleStringList, List<List<String>> batchCheckSensitiveResultListOfBill, List<String> titleStringListOfBill) {
        if (downLoadKey.contains("\\.\\./")) {
            throw new RuntimeException("\u5b58\u5728\u4e0d\u5b89\u5168\u7684\u8def\u5f84\u5185\u5bb9");
        }
        String location = EXPORTDIR;
        NpContext context = NpContextHolder.getContext();
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String dirUU = UUID.randomUUID().toString();
        String fileName = "\u6279\u91cf\u68c0\u6d4b\u7ed3\u679c.xlsx";
        String resultLocation = location + SEPARATOR + context.getUser().getName() + SEPARATOR + formatDate + SEPARATOR + dirUU + SEPARATOR;
        String fileLocation = resultLocation + fileName;
        if (resultLocation.contains("\\.\\./")) {
            throw new RuntimeException("\u5b58\u5728\u4e0d\u5b89\u5168\u7684\u8def\u5f84\u5185\u5bb9");
        }
        File file = new File(FilenameUtils.normalize(resultLocation));
        if (!file.exists()) {
            file.mkdirs();
        }
        try (FileOutputStream output = new FileOutputStream(FilenameUtils.normalize(fileLocation));
             XSSFWorkbook xssfWorkbook = this.createHistoryCheckInfo(batchCheckSensitiveResultList, titleStringList, batchCheckSensitiveResultListOfBill, titleStringListOfBill);){
            xssfWorkbook.write(output);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.cacheObjectResourceRemote.create((Object)downLoadKey, (Object)fileLocation);
    }

    private XSSFWorkbook createHistoryCheckInfo(List<List<String>> batchCheckSensitiveResultList, List<String> titleStringList, List<List<String>> batchCheckSensitiveResultListOfBill, List<String> titleStringListOfBill) {
        return this.export2007OfBatchCheckInfo(batchCheckSensitiveResultList.size() > 0 ? batchCheckSensitiveResultList : null, this.createTitle(titleStringList), batchCheckSensitiveResultListOfBill.size() > 0 ? batchCheckSensitiveResultListOfBill : null, this.createTitle(titleStringListOfBill));
    }

    private String[] createTitle(List<String> titleStringList) {
        String[] titles = new String[titleStringList.size()];
        titleStringList.toArray(titles);
        return titles;
    }

    private void insertSensitiveWordOfSheet(List<SensitiveWordDaoObject> sensitiveWordDaoObjectList) {
        ArrayList<String> sensitiveWordInfoList = new ArrayList<String>();
        ArrayList<SensitiveWordDaoObject> sensitiveWordDaoObjectListAfterFormat = new ArrayList<SensitiveWordDaoObject>();
        for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordDaoObjectList) {
            if ((sensitiveWordInfoList.size() <= 0 || sensitiveWordInfoList.contains(sensitiveWordDaoObject.getSensitiveInfo())) && sensitiveWordInfoList.size() > 0) continue;
            sensitiveWordInfoList.add(sensitiveWordDaoObject.getSensitiveInfo());
            sensitiveWordDaoObjectListAfterFormat.add(sensitiveWordDaoObject);
        }
        this.sensitiveWordDao.batchInsertSensitiveWord(sensitiveWordDaoObjectListAfterFormat);
        this.publisher.publishEvent(sensitiveWordDaoObjectList.get(0));
    }

    private List<CheckResultObject> checkImportDataInfo(Sheet sensitiveSheet) {
        ArrayList<CheckResultObject> checkInfoResultList = new ArrayList<CheckResultObject>();
        if (sensitiveSheet.getSheetName().equals(SENSITIVE_TYPE_1) || sensitiveSheet.getSheetName().equals(SENSITIVE_TYPE_2)) {
            for (Row row : sensitiveSheet) {
                if (row.getRowNum() == 0) continue;
                try {
                    CheckResultObject checkResultObject = new CheckResultObject();
                    StringBuilder errorInfo = new StringBuilder();
                    if (!row.getCell(TYPE_NUM).toString().equals(WORD_TYPE_2) && !row.getCell(TYPE_NUM).toString().equals(WORD_TYPE_1) || row.getCell(TYPE_NUM) == null) {
                        errorInfo.append("\u654f\u611f\u8bcd\u7c7b\u578b\u53ea\u80fd\u4e3a'\u5b57\u7b26\u4e32\u5305\u542b\u7c7b'\u6216'\u8868\u8fbe\u5f0f\u7c7b';");
                    }
                    if (row.getCell(INFO_NUM) == null || row.getCell(INFO_NUM).toString().length() > 200 || row.getCell(INFO_NUM).toString().length() <= 0) {
                        errorInfo.append("\u654f\u611f\u8bcd\u5185\u5bb9\u4e0d\u80fd\u4e3a\u7a7a\u4e14\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e200\u5b57\u7b26;");
                    }
                    if (row.getCell(DESCRIPTION_NUM) != null && row.getCell(DESCRIPTION_NUM).toString().length() > 200) {
                        errorInfo.append("\u654f\u611f\u8bcd\u63cf\u8ff0\u4fe1\u606f\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e200\u5b57\u7b26;");
                    }
                    if (row.getCell(EFFECTIVE_NUM) == null || !row.getCell(EFFECTIVE_NUM).toString().equals("\u662f") && !row.getCell(EFFECTIVE_NUM).toString().equals("\u5426")) {
                        errorInfo.append("\u654f\u611f\u8bcd\u662f\u5426\u542f\u7528\u53ea\u80fd\u4e3a'\u662f'\u6216'\u5426';");
                    }
                    if (errorInfo.toString().length() <= 0) continue;
                    checkResultObject.setErrorNum(row.getRowNum());
                    checkResultObject.setErrorInfo(errorInfo.toString());
                    checkInfoResultList.add(checkResultObject);
                }
                catch (Exception e) {
                    CheckResultObject checkResultObject = new CheckResultObject();
                    checkResultObject.setErrorNum(row.getRowNum());
                    checkResultObject.setErrorInfo(e.getMessage());
                    checkInfoResultList.add(checkResultObject);
                    return checkInfoResultList;
                }
            }
        }
        return checkInfoResultList;
    }

    private List<SensitiveWordDaoObject> changeToDaoObject(Sheet sensitiveSheet) {
        ArrayList<SensitiveWordDaoObject> sensitiveWordDaoObjectList = new ArrayList<SensitiveWordDaoObject>();
        if (sensitiveSheet.getSheetName().equals(SENSITIVE_TYPE_1) || sensitiveSheet.getSheetName().equals(SENSITIVE_TYPE_2)) {
            for (Row row : sensitiveSheet) {
                if (row.getRowNum() == 0) continue;
                SensitiveWordDaoObject sensitiveWordDaoObject = new SensitiveWordDaoObject();
                sensitiveWordDaoObject.setSensitiveWordKey(UUID.randomUUID().toString());
                sensitiveWordDaoObject.setSensitiveType(row.getCell(TYPE_NUM).toString().equals(WORD_TYPE_2) ? 1 : 0);
                sensitiveWordDaoObject.setModifyUser(NpContextHolder.getContext().getUserName());
                sensitiveWordDaoObject.setModifyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                if (row.getCell(DESCRIPTION_NUM) != null) {
                    sensitiveWordDaoObject.setSensitiveDescription(row.getCell(DESCRIPTION_NUM).toString());
                }
                sensitiveWordDaoObject.setSensitiveInfo(row.getCell(INFO_NUM).toString());
                sensitiveWordDaoObject.setIsEffective(row.getCell(EFFECTIVE_NUM).toString().equals("\u662f") ? 0 : 1);
                sensitiveWordDaoObject.setSensitiveWordType(sensitiveSheet.getSheetName().equals(SENSITIVE_TYPE_2) ? 1 : 0);
                sensitiveWordDaoObjectList.add(sensitiveWordDaoObject);
            }
        }
        return sensitiveWordDaoObjectList;
    }

    private boolean checkImportSheet(Sheet sensitiveSheet) {
        boolean isCanImport = true;
        if (sensitiveSheet == null) {
            isCanImport = false;
        } else {
            Row headRow = sensitiveSheet.getRow(0);
            boolean isIndex = headRow.getCell(INDEX_NUM).getStringCellValue().equals(INDEX);
            boolean isType = headRow.getCell(TYPE_NUM).getStringCellValue().equals(TYPE);
            boolean isInfo = headRow.getCell(INFO_NUM).getStringCellValue().equals(INFO);
            boolean isDesc = headRow.getCell(DESCRIPTION_NUM).getStringCellValue().equals(DESCRIPTION);
            boolean isEff = headRow.getCell(EFFECTIVE_NUM).getStringCellValue().equals(EFFECTIVE);
            if (!(isIndex && isType && isInfo && isDesc && isEff)) {
                isCanImport = false;
            }
        }
        return isCanImport;
    }

    private String[] createTitle() {
        String[] titles = new String[]{INDEX, TYPE, INFO, DESCRIPTION, EFFECTIVE};
        return titles;
    }

    private XSSFWorkbook createAllSensitiveWordReturnInfo(List<SensitiveWordDaoObject> sensitiveWordDaoObjectList, List<SensitiveWordDaoObject> whiteWordDaoObjectList) {
        List<List<String>> sensitiveWordStringList = this.formatList(sensitiveWordDaoObjectList);
        return this.export2007(sensitiveWordStringList.size() > 0 ? sensitiveWordStringList : null, null, this.createTitle());
    }

    private List<List<String>> formatListOfBatchCheckResult(List<List<String>> batchCheckSensitiveResultList) {
        ArrayList<List<String>> batchCheckResultInfoList = new ArrayList<List<String>>();
        for (List<String> batchCheckSensitiveResult : batchCheckSensitiveResultList) {
            ArrayList<String> oneList = new ArrayList<String>();
            for (String str : batchCheckSensitiveResult) {
                oneList.add(str);
            }
            batchCheckResultInfoList.add(oneList);
        }
        return batchCheckResultInfoList;
    }

    private List<List<String>> formatList(List<SensitiveWordDaoObject> sensitiveWordDaoObjectList) {
        ArrayList<List<String>> sensitiveWordStringList = new ArrayList<List<String>>();
        int x = 0;
        for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordDaoObjectList) {
            ArrayList<String> oneList = new ArrayList<String>();
            oneList.add(++x + "");
            oneList.add(sensitiveWordDaoObject.getSensitiveType() == 0 ? WORD_TYPE_1 : WORD_TYPE_2);
            oneList.add(sensitiveWordDaoObject.getSensitiveInfo());
            oneList.add(sensitiveWordDaoObject.getSensitiveDescription());
            oneList.add(sensitiveWordDaoObject.getIsEffective() == 0 ? "\u662f" : "\u5426");
            sensitiveWordStringList.add(oneList);
        }
        return sensitiveWordStringList;
    }

    private XSSFWorkbook export2007OfBatchCheckInfo(List<List<String>> batchCheckResultList, String[] title, List<List<String>> batchCheckResultListOfBill, String[] titleOfBill) {
        XSSFWorkbook wb = new XSSFWorkbook();
        this.createSheetOfBatchCheck(wb, "\u62a5\u8868\u6570\u636e", batchCheckResultList, title);
        if (titleOfBill.length > 0) {
            this.createSheetOfBatchCheck(wb, "\u5355\u636e\u6570\u636e", batchCheckResultListOfBill, titleOfBill);
        }
        return wb;
    }

    private XSSFWorkbook export2007(List<List<String>> sensitiveWordDaoObjectList, List<List<String>> whiteWordDaoObjectList, String[] title) {
        XSSFWorkbook wb = new XSSFWorkbook();
        this.createSheet(wb, SENSITIVE_TYPE_1, sensitiveWordDaoObjectList, title);
        return wb;
    }

    private void createSheetOfBatchCheck(XSSFWorkbook wb, String sheetName, List<List<String>> list, String[] title) {
        XSSFSheet sheet = wb.createSheet(sheetName);
        for (int i = 0; i < title.length; ++i) {
            sheet.setColumnWidth(i, 6400);
        }
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = wb.createCellStyle();
        style = this.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        XSSFCell cell = null;
        for (int i = 0; i < title.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                row = sheet.createRow(i + 1);
                List<String> clist = list.get(i);
                for (int n = 0; n < clist.size(); ++n) {
                    String value = clist.get(n);
                    XSSFCell cellData = row.createCell(n);
                    cellData.setCellValue(value);
                    if (n == 0) {
                        cellData.setCellStyle(style2);
                        continue;
                    }
                    cellData.setCellStyle(style);
                }
            }
        }
    }

    private void createSheet(XSSFWorkbook wb, String sheetName, List<List<String>> list, String[] title) {
        XSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(0, 2048);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 6400);
        sheet.setColumnWidth(3, 6400);
        sheet.setColumnWidth(4, 6400);
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = wb.createCellStyle();
        style = this.createCellStyle(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        XSSFCell cell = null;
        for (int i = 0; i < title.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                row = sheet.createRow(i + 1);
                List<String> clist = list.get(i);
                for (int n = 0; n < clist.size(); ++n) {
                    String value = clist.get(n);
                    XSSFCell cellData = row.createCell(n);
                    cellData.setCellValue(value);
                    if (n == 0) {
                        cellData.setCellStyle(style2);
                        continue;
                    }
                    cellData.setCellStyle(style);
                }
            }
        }
        String[] actions = new String[]{WORD_TYPE_1, WORD_TYPE_2};
        String[] actions2 = new String[]{"\u662f", "\u5426"};
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(actions);
        XSSFDataValidationConstraint dvConstraint2 = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(actions2);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, 1, 1);
        CellRangeAddressList addressList2 = new CellRangeAddressList(1, 65535, 4, 4);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        DataValidation validation2 = dvHelper.createValidation(dvConstraint2, addressList2);
        validation2.setSuppressDropDownArrow(true);
        validation2.setShowErrorBox(true);
        sheet.addValidationData(validation);
        sheet.addValidationData(validation2);
    }

    private XSSFCellStyle createCellStyle(XSSFCellStyle style) {
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        return style;
    }
}

