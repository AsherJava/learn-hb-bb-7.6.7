/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  javax.annotation.Resource
 *  nr.single.map.common.ImportConsts
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.RuleKind
 *  nr.single.map.configurations.bean.RuleMap
 *  nr.single.map.configurations.bean.SkipUnit
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.configurations.bean.UpdateWay
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.service.TaskDataService
 */
package nr.single.data.datain.internal.service.org;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import nr.single.data.datain.service.ITaskFileImportEntityService;
import nr.single.map.common.ImportConsts;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.service.TaskDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="TaskFileImportEntityTableServiceImpl")
public class TaskFileImportEntityTableServiceImpl
implements ITaskFileImportEntityService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileImportEntityTableServiceImpl.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private TaskDataService taskDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void importSingleEnityData(TaskDataContext importContext, String path, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        try {
            String dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            String dbfFileName = dataPath + importContext.getSingleFileFlag() + "FMDM.DBF";
            try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                List fieldDefines = this.dataRuntimeController.getAllFieldsInTable(importContext.getDwEntityId());
                SingleFileTableInfo table = null;
                if (importContext.getMapingCache().isMapConfig()) {
                    table = importContext.getMapingCache().getFmdmInfo();
                }
                this.ImportEntityDatasToNet(importContext, importContext.getDwEntityId(), fieldDefines, dbf, table, asyncTaskMonitor);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void ImportEntityDatasToNet(TaskDataContext importContext, String entityId, List<FieldDefine> fieldDefines, IDbfTable dbf, SingleFileTableInfo singeleTable, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        if (dbf.getRecordCount() > 0) {
            DataRow dbfRow = dbf.getRecordByIndex(0);
            String firstZDM = dbfRow.getValueString(0);
            importContext.setFirstZDM(firstZDM);
        }
        this.taskDataService.MapSingleEnityData(importContext);
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u52a0\u8f7d\u53c2\u6570");
        if (StringUtils.isEmpty((String)importContext.getCurrentPeriod()) && StringUtils.isNotEmpty((String)importContext.getFirstZDM()) && StringUtils.isEmpty((String)importContext.getMapNetPeriodCode())) {
            String zdm = importContext.getFirstZDM();
            String singlePeriod = importContext.getEntityCache().getSinglePeriodByZdm(zdm);
            importContext.setCurrentPeriod(singlePeriod);
            String singlePeriod1 = importContext.getSingleTaskYear() + "@" + singlePeriod;
            String mapNetPeriod = this.taskDataService.getNetPeriodCode(importContext, singlePeriod1);
            importContext.setMapNetPeriodCode(mapNetPeriod);
        }
        HashMap<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();
        HashMap<String, FieldDefine> singleFieldMap = new HashMap<String, FieldDefine>();
        HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, String> fieldValueMap = new HashMap<String, String>();
        HashMap<String, FieldDefine> zdmFieldMap = new HashMap<String, FieldDefine>();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        ArrayList<String> singleZdmWithoutPeriodCodes = new ArrayList<String>();
        LinkedHashMap<String, Integer> SingleZdmCodeAndFieldIdMap = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, String> SingleZdmCodeAndFieldCodeMap = new LinkedHashMap<String, String>();
        ArrayList<FieldDefine> zdmFieldList = new ArrayList<FieldDefine>();
        ArrayList<SingleFileFieldInfo> singeZdmFieldList = new ArrayList<SingleFileFieldInfo>();
        ArrayList<SingleFileFieldInfo> singeOtherFieldList = new ArrayList<SingleFileFieldInfo>();
        int periodIndex = 0;
        int peirodLength = 0;
        int zmdLength = 0;
        SingleFileFmdmInfo fMTable = null;
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        boolean isZdmMapExpression = false;
        for (FieldDefine fieldDefine : fieldDefines) {
            dataQuery.addColumn(fieldDefine);
            fieldMap.put(fieldDefine.getCode(), fieldDefine);
        }
        if (null != importContext.getMapingCache().getMapConfig()) {
            TableDefine tableDefine = this.dataRuntimeController.queryTableDefine(importContext.getDwEntityId());
            ISingleMappingConfig mapConfig = importContext.getMapingCache().getMapConfig();
            List rules = mapConfig.getMapRule(RuleKind.UNIT_MAP_IMPORT);
            for (RuleMap rule : rules) {
                String fieldCode;
                if (!StringUtils.isNotEmpty((String)rule.getSingleCode()) || StringUtils.isEmpty((String)(fieldCode = rule.getNetCode()))) continue;
                if (fieldCode.indexOf("[") < 0) {
                    fieldCode = tableDefine.getCode() + "[" + fieldCode + "]";
                }
                Integer a = dataQuery.addExpressionColumn(fieldCode);
                SingleZdmCodeAndFieldIdMap.put(rule.getSingleCode(), a);
                SingleZdmCodeAndFieldCodeMap.put(rule.getSingleCode(), rule.getNetCode());
                isZdmMapExpression = true;
            }
        }
        FieldDefine periodField = null;
        String periodCode = "";
        if (null != singeleTable) {
            for (SingleFileFieldInfo field : singeleTable.getRegion().getFields()) {
                mapNetFieldList.put(field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getFieldCode(), field);
                String netCode = field.getNetFieldCode();
                if (!fieldMap.containsKey(netCode)) continue;
                singleFieldMap.put(field.getFieldCode(), (FieldDefine)fieldMap.get(netCode));
            }
            fMTable = (SingleFileFmdmInfo)singeleTable;
            for (String code : fMTable.getZdmFieldCodes()) {
                String netCode1;
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) {
                    singleZdmWithoutPeriodCodes.add(code);
                }
                if (!mapSingleFieldList.containsKey(code)) continue;
                SingleFileFieldInfo singleField = (SingleFileFieldInfo)mapSingleFieldList.get(code);
                singeZdmFieldList.add(singleField);
                zmdLength += singleField.getFieldSize();
                if (!code.equals(fMTable.getPeriodField())) {
                    singeOtherFieldList.add(singleField);
                    periodIndex += singleField.getFieldSize();
                } else {
                    peirodLength = singleField.getFieldSize();
                }
                String netCode = singleField.getNetFieldCode();
                if (SingleZdmCodeAndFieldCodeMap.containsKey(code) && fieldMap.containsKey(netCode1 = (String)SingleZdmCodeAndFieldCodeMap.get(code))) {
                    netCode = netCode1;
                }
                FieldDefine netField = null;
                if (fieldMap.containsKey(netCode)) {
                    netField = (FieldDefine)fieldMap.get(netCode);
                    zdmFieldMap.put(code, netField);
                    zdmFieldList.add(netField);
                } else {
                    zdmFieldMap.put(code, null);
                }
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) continue;
                periodField = netField;
                periodCode = this.taskDataService.getSinglePeriodCode(importContext, importContext.getNetPeriodCode(), singleField.getFieldSize());
                fieldValueMap.put(singleField.getFieldCode(), periodCode);
                importContext.setMapCurrentPeriod(periodCode);
            }
        }
        if (importContext.getEntityCache().getEntityList().size() > 3000 || dbf.getDataRowCount() > 3000) {
            this.upateEntityDataNew(importContext, dbf, dataQuery, fMTable, fieldMap, singleFieldMap, SingleZdmCodeAndFieldIdMap, singleZdmWithoutPeriodCodes, peirodLength, periodIndex, zmdLength, asyncTaskMonitor);
        } else {
            this.upateEntityData(importContext, dbf, dataQuery, fMTable, fieldMap, singleFieldMap, SingleZdmCodeAndFieldIdMap, singleZdmWithoutPeriodCodes, peirodLength, periodIndex, zmdLength, asyncTaskMonitor);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void upateEntityDataNew(TaskDataContext importContext, IDbfTable dbf, IDataQuery dataQuery, SingleFileFmdmInfo fMTable, Map<String, FieldDefine> fieldMap, Map<String, FieldDefine> singleFieldMap, Map<String, Integer> SingleZdmCodeAndFieldIdMap, List<String> singleZdmWithoutPeriodCodes, int peirodLength, int periodIndex, int zmdLength, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        ExecutorContext context = new ExecutorContext(this.dataRuntimeController);
        DimensionValueSet rowKeys = new DimensionValueSet();
        if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
            rowKeys.setValue("DATATIME", (Object)importContext.getMapNetPeriodCode());
        } else {
            rowKeys.setValue("DATATIME", (Object)importContext.getNetPeriodCode());
        }
        FieldDefine fjdField = null;
        FieldDefine codeField = null;
        FieldDefine titleField = null;
        FieldDefine autoAppendField = null;
        boolean isIncrement = true;
        boolean isUpdate = true;
        HashMap<String, String> zdmWithoutPeriodTempMap = new HashMap<String, String>();
        HashMap<String, String> zdmTempMap = new HashMap<String, String>();
        HashMap<String, String> tempFJDRows = new HashMap<String, String>();
        HashMap<String, String> tempFJDZdms = new HashMap<String, String>();
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        HashMap<String, DataRow> dbfZdmRowMap = new HashMap<String, DataRow>();
        HashMap<String, String> dbfZdmFjdMap = new HashMap<String, String>();
        uploadEntityZdmKeyMap.clear();
        insertEntityZdmKeyMap.clear();
        AutoAppendCode autoCodeCoinfig = null;
        ArrayList<String> skipUnitKeys = new ArrayList<String>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            SkipUnit skipUnit;
            if (null != importContext.getMapingCache().getMapConfig().getConfig()) {
                UpdateWay way = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay();
                isIncrement = way.isIncrement();
                isUpdate = way.isUpdate();
                skipUnit = importContext.getMapingCache().getMapConfig().getConfig().getSkipUnit();
                if (null != skipUnit) {
                    List skipUnitKeys3;
                    String skipFormula;
                    List skipUnitKeys2 = skipUnit.getUnitKey();
                    if (null != skipUnitKeys2) {
                        skipUnitKeys.addAll(skipUnitKeys2);
                    }
                    if (StringUtils.isNotEmpty((String)(skipFormula = skipUnit.getFormula())) && null != (skipUnitKeys3 = this.taskDataService.queryFilterUnits(importContext, skipFormula))) {
                        skipUnitKeys.addAll(skipUnitKeys3);
                    }
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getMapping()) {
                List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                skipUnit = list.iterator();
                while (skipUnit.hasNext()) {
                    UnitCustomMapping unitMap = (UnitCustomMapping)skipUnit.next();
                    if (!StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) || !StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) continue;
                    skipUnitKeys.add(unitMap.getNetUnitKey());
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
                autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
            }
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            fjdField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey);
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
            codeField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey);
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            titleField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey);
        }
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb()) && fieldMap.containsKey(autoCodeCoinfig.getAppendCodeZb())) {
            autoAppendField = fieldMap.get(autoCodeCoinfig.getAppendCodeZb());
        }
        for (FieldDefine field : fieldMap.values()) {
            if (field.getValueType() == FieldValueType.FIELD_VALUE_UNIT_NAME) {
                if (titleField != null) continue;
                titleField = field;
                continue;
            }
            if (field.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE) {
                if (codeField != null) continue;
                codeField = field;
                continue;
            }
            if (field.getValueType() != FieldValueType.FIELD_VALUE_PARENT_UNIT || fjdField != null) continue;
            fjdField = field;
        }
        ArrayList dbfZdmPages = new ArrayList();
        ArrayList<String> dbfZdmCurPage = new ArrayList<String>();
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            dbfZdmRowMap.put(zdm, dbfRow);
            String fjd = dbfRow.getValueString("SYS_FJD");
            dbfZdmFjdMap.put(zdm, fjd);
            if (dbfZdmCurPage.size() < 1000) {
                dbfZdmCurPage.add(zdm);
            } else {
                dbfZdmPages.add(dbfZdmCurPage);
                dbfZdmCurPage = new ArrayList();
                dbfZdmCurPage.add(zdm);
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
        if (dbfZdmCurPage.size() > 0) {
            dbfZdmPages.add(dbfZdmCurPage);
        }
        boolean showConsole = dbf.getRecordCount() <= 10;
        double addProgress = 0.0;
        if (dbf.getRecordCount() > 0) {
            addProgress = importContext.getNextProgressLen() / (double)dbf.getRecordCount();
        }
        DataEntityInfo entityInfo = null;
        HashMap<String, String> unitKeyZdms = new HashMap<String, String>();
        HashMap<String, String> zdmMapExCodes = new HashMap<String, String>();
        HashMap<String, String> zdmMapWithOutPeriod = new HashMap<String, String>();
        HashMap<String, String> zdmMapentityJcm = new HashMap<String, String>();
        for (int k = 0; k < dbfZdmPages.size(); ++k) {
            String entityAutoExCode;
            List dbfCurPage = (List)dbfZdmPages.get(k);
            ArrayList<String> arrayList = new ArrayList<String>();
            Iterator iterator = dbfCurPage.iterator();
            while (iterator.hasNext()) {
                String zdm = (String)iterator.next();
                DataRow dbfRow = (DataRow)dbfZdmRowMap.get(zdm);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRow(dbfRow);
                }
                String zdmWithOutPeriod = "";
                if (SingleZdmCodeAndFieldIdMap.size() > 0) {
                    for (String code : SingleZdmCodeAndFieldIdMap.keySet()) {
                        String fieldValue = dbfRow.getValueString(code);
                        if (StringUtils.isEmpty((String)fieldValue)) {
                            zdmWithOutPeriod = "";
                            break;
                        }
                        zdmWithOutPeriod = zdmWithOutPeriod + fieldValue;
                    }
                } else {
                    for (String code : singleZdmWithoutPeriodCodes) {
                        zdmWithOutPeriod = zdmWithOutPeriod + dbfRow.getValueString(code);
                    }
                }
                if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                    zdmWithOutPeriod = zdm;
                    if (peirodLength > 0 && StringUtils.isNotEmpty((String)zdm) && zdm.length() > periodIndex) {
                        zdmWithOutPeriod = zdm.substring(0, periodIndex - 1) + zdm.substring(periodIndex + peirodLength - 1, zdm.length());
                    }
                }
                String entityJcm = "";
                if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isEmpty((String)(entityJcm = this.getJcmbyZdm(importContext, zdm, zdmWithOutPeriod, dbfZdmFjdMap, autoCodeCoinfig)))) {
                    String zdmCode = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                    }
                    SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165", "notJcm", "", zdmWithOutPeriod, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                entityAutoExCode = zdmWithOutPeriod + entityJcm;
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(entityAutoExCode);
                if (null != entityInfo) {
                    if (!unitKeyZdms.containsKey(entityInfo.getEntityKey())) {
                        arrayList.add(entityInfo.getEntityKey());
                        unitKeyZdms.put(entityInfo.getEntityKey(), zdm);
                    } else {
                        logger.info(entityAutoExCode + "\u5b58\u5728\u91cd\u7801");
                    }
                }
                zdmMapExCodes.put(zdm, entityAutoExCode);
                zdmMapWithOutPeriod.put(zdm, zdmWithOutPeriod);
                zdmMapentityJcm.put(zdm, entityJcm);
            }
            rowKeys.setValue(importContext.getEntityCompanyType(), arrayList);
            dataQuery.setMasterKeys(rowKeys);
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u67e5\u8be2\u6570\u636e");
            IDataTable dataTable = dataQuery.executeQuery(context);
            HashMap<String, IDataRow> entityAutoExCodeAndRowMap = new HashMap<String, IDataRow>();
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6784\u9020\u4e3b\u4ee3\u7801\u4e0e\u6570\u636e\u884c\u5173\u7cfb");
            entityInfo = null;
            for (int i = 0; i < dataTable.getCount(); ++i) {
                IDataRow entityRow = dataTable.getItem(i);
                String zdmKey = entityRow.getRecKey();
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                if (entityInfo == null) continue;
                entityAutoExCodeAndRowMap.put(entityInfo.getEntityAutoExCode(), entityRow);
            }
            Iterator iterator2 = dbfCurPage.iterator();
            while (iterator2.hasNext()) {
                String zdm = (String)iterator2.next();
                if (null != asyncTaskMonitor) {
                    importContext.addProgress(addProgress);
                    asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "");
                }
                DataRow dbfRow = (DataRow)dbfZdmRowMap.get(zdm);
                entityAutoExCode = (String)zdmMapExCodes.get(zdm);
                String zdmWithOutPeriod = (String)zdmMapWithOutPeriod.get(zdm);
                String entityJcm = (String)zdmMapentityJcm.get(zdm);
                entityInfo = null;
                boolean isNewEntity = false;
                IDataRow entityRow = null;
                if (entityAutoExCodeAndRowMap.containsKey(entityAutoExCode)) {
                    SingleDataError errorItem;
                    entityRow = (IDataRow)entityAutoExCodeAndRowMap.get(entityAutoExCode);
                    String zdmKey = entityRow.getRecKey();
                    String zdmTitle = "";
                    String zdmCode = "";
                    entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                    if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                        zdmTitle = entityInfo.getEntityTitle();
                        zdmCode = entityInfo.getEntityCode();
                    }
                    if (skipUnitKeys.size() > 0 && skipUnitKeys.contains(zdmKey)) {
                        errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "notMap", zdmKey, zdmTitle, zdmCode);
                        importContext.recordLog("FMDM", errorItem);
                        if (dbf.isHasLoadAllRec()) continue;
                        dbf.loadDataRow(dbfRow);
                        continue;
                    }
                    uploadEntityZdmKeyMap.put(zdm, zdmKey);
                    if (!isUpdate) {
                        errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f", "notUpdate", zdmKey, zdmTitle, zdmCode);
                        importContext.recordLog("FMDM", errorItem);
                        if (dbf.isHasLoadAllRec()) continue;
                        dbf.loadDataRow(dbfRow);
                        continue;
                    }
                    if (showConsole) {
                        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u66f4\u65b0" + entityRow.getAsString(0) + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                    }
                } else {
                    isNewEntity = true;
                    if (!isIncrement) {
                        String zdmCode = zdm;
                        if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                            zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                        }
                        SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", "", zdm, zdmCode);
                        importContext.recordLog("FMDM", errorItem);
                        if (dbf.isHasLoadAllRec()) continue;
                        dbf.loadDataRow(dbfRow);
                        continue;
                    }
                    ArrayList<String> keys = new ArrayList<String>();
                    String newZdmKeyID = null;
                    if (zdmWithoutPeriodTempMap.containsKey(zdmWithOutPeriod)) {
                        newZdmKeyID = (String)zdmWithoutPeriodTempMap.get(zdmWithOutPeriod);
                        tempFJDZdms.put(newZdmKeyID, zdm);
                    } else if (zdmTempMap.containsKey(zdm)) {
                        newZdmKeyID = (String)zdmTempMap.get(zdm);
                        tempFJDZdms.put(newZdmKeyID, zdm);
                    } else {
                        newZdmKeyID = UUID.randomUUID().toString();
                    }
                    if (showConsole) {
                        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65b0\u589e" + newZdmKeyID.toString() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                    }
                    String newZdmKey = newZdmKeyID.toString();
                    keys.add(newZdmKey);
                    String dimName = importContext.getEntityCompanyType();
                    DimensionValueSet rowKeys1 = new DimensionValueSet();
                    rowKeys1.setValue(dimName, (Object)newZdmKey);
                    entityRow = dataTable.appendRow(rowKeys1);
                    entityInfo = new DataEntityInfo();
                    entityCodeKeyMap.put(zdmWithOutPeriod, newZdmKey);
                    entityKeyCodeMap.put(newZdmKey, zdmWithOutPeriod);
                    entityKeyZdmMap.put(newZdmKey, zdm);
                    entityZdmAndKeyMap.put(zdm, newZdmKey);
                    insertEntityZdmKeyMap.put(zdm, newZdmKey);
                    uploadEntityZdmKeyMap.put(zdm, newZdmKey);
                    entityAutoExCodeAndRowMap.put(entityAutoExCode, entityRow);
                    if (null != codeField) {
                        String dwdmField = null;
                        if (null != fMTable) {
                            dwdmField = fMTable.getDWDMField();
                        }
                        if (StringUtils.isNotEmpty(dwdmField)) {
                            entityRow.setValue(codeField, (Object)dbfRow.getValueString(dwdmField));
                        } else if (StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                            entityRow.setValue(codeField, (Object)zdmWithOutPeriod);
                        } else {
                            entityRow.setValue(codeField, (Object)OrderGenerator.newOrderID());
                        }
                    }
                    if (null != titleField) {
                        String dwmcField = null;
                        if (null != fMTable) {
                            dwmcField = fMTable.getDWMCField();
                        }
                        if (StringUtils.isNotEmpty(dwmcField)) {
                            entityRow.setValue(titleField, (Object)dbfRow.getValueString(dwmcField));
                        } else {
                            entityRow.setValue(titleField, (Object)OrderGenerator.newOrderID());
                        }
                    }
                    String netPeriodCode = importContext.getNetPeriodCode();
                    if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
                        netPeriodCode = importContext.getMapNetPeriodCode();
                    }
                    PeriodWrapper periodWrapper = new PeriodWrapper(netPeriodCode);
                    Date beginDate = null;
                    String[] periodList = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper);
                    if (periodList != null && periodList.length == 2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        beginDate = simpleDateFormat.parse(periodList[0]);
                    }
                    entityRow.setValue(fieldMap.get(ImportConsts.ENTITY_SORTORDER), (Object)OrderGenerator.newOrderID());
                    if (null != beginDate) {
                        entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey), (Object)beginDate);
                    } else {
                        entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey), (Object)Consts.DATE_VERSION_MIN_VALUE);
                    }
                    entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_INVALIDTIME.fieldKey), (Object)Consts.DATE_VERSION_MAX_VALUE);
                    if (autoAppendField != null) {
                        entityRow.setValue(autoAppendField, (Object)entityJcm);
                    }
                    entityInfo = new DataEntityInfo();
                    entityInfo.setEntityKey(newZdmKey);
                    entityInfo.setEntityExCode(zdmWithOutPeriod);
                    entityInfo.setSingleZdm(zdm);
                }
                for (int j = 1; j < dbf.geDbfFields().length; ++j) {
                    String fjdZdm;
                    String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
                    String dbFieldValue = dbfRow.getValueString(j);
                    if (singleFieldMap.containsKey(dbfFieldName)) {
                        FieldDefine field = singleFieldMap.get(dbfFieldName);
                        entityRow.setAsString(field, dbFieldValue);
                        continue;
                    }
                    if (!dbfFieldName.equalsIgnoreCase("SYS_FJD") || !StringUtils.isNotEmpty((String)(fjdZdm = dbFieldValue))) continue;
                    String fjdZdmWithoutPeriod = fjdZdm = fjdZdm.toUpperCase();
                    if (zdmMapWithOutPeriod.containsKey(fjdZdm)) {
                        fjdZdmWithoutPeriod = (String)zdmMapWithOutPeriod.get(fjdZdm);
                    } else if (peirodLength > 0 && fjdZdm.length() == zmdLength) {
                        fjdZdmWithoutPeriod = fjdZdm.substring(0, periodIndex - 1) + fjdZdm.substring(periodIndex + peirodLength - 1, fjdZdm.length());
                    }
                    String fjdEntityAutoExCode = (String)zdmMapExCodes.get(fjdZdm);
                    String fjdEntityJcm = (String)zdmMapentityJcm.get(fjdZdm);
                    if (StringUtils.isEmpty((String)fjdEntityAutoExCode) && StringUtils.isNotEmpty((String)fjdEntityJcm)) {
                        fjdEntityAutoExCode = fjdZdmWithoutPeriod + fjdEntityJcm;
                    }
                    String fjdKey = null;
                    entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(fjdEntityAutoExCode);
                    if (StringUtils.isNotEmpty((String)fjdEntityAutoExCode) && entityInfo != null) {
                        fjdKey = entityInfo.getEntityKey();
                    } else if (entityCodeKeyMap.containsKey(fjdZdmWithoutPeriod)) {
                        fjdKey = (String)entityCodeKeyMap.get(fjdZdmWithoutPeriod);
                    } else if (entityZdmAndKeyMap.containsKey(fjdZdm)) {
                        fjdKey = (String)entityZdmAndKeyMap.get(fjdZdm);
                    } else if (zdmWithoutPeriodTempMap.containsKey(fjdZdmWithoutPeriod)) {
                        fjdKey = (String)zdmWithoutPeriodTempMap.get(fjdZdmWithoutPeriod);
                        tempFJDRows.put(fjdKey, entityRow.getRecKey());
                    } else if (zdmTempMap.containsKey(fjdZdm)) {
                        fjdKey = (String)zdmTempMap.get(fjdZdm);
                        tempFJDRows.put(fjdKey, entityRow.getRecKey());
                    }
                    if (StringUtils.isEmpty((String)fjdKey) && dbfZdmRowMap.containsKey(fjdZdm)) {
                        fjdKey = UUID.randomUUID().toString();
                        zdmWithoutPeriodTempMap.put(fjdZdmWithoutPeriod, fjdKey);
                        zdmTempMap.put(fjdZdm, fjdKey);
                        tempFJDRows.put(fjdKey, entityRow.getRecKey());
                    }
                    if (null == fjdField || !StringUtils.isNotEmpty((String)fjdKey)) continue;
                    entityRow.setAsString(fjdField, fjdKey);
                }
                if (isNewEntity) {
                    String entityCode = "";
                    if (codeField != null) {
                        entityCode = entityRow.getAsString(codeField);
                    }
                    String entityTitle = "";
                    if (titleField != null) {
                        entityTitle = entityRow.getAsString(titleField);
                    }
                    String entityRowCaption = "";
                    String entityParentKey = "";
                    if (fjdField != null) {
                        entityParentKey = entityRow.getAsString(fjdField);
                    }
                    if (null == entityInfo) {
                        entityInfo = new DataEntityInfo();
                    }
                    entityInfo.setEntityTitle(entityTitle);
                    entityInfo.setEntityCode(entityCode);
                    entityInfo.setEntityParentKey(entityParentKey);
                    entityInfo.setEntityRowCaption(entityRowCaption);
                    entityInfo.setIsUnitMap(false);
                    entityInfo.setExpEntityExCode(zdmWithOutPeriod);
                    entityInfo.setExpSingleZdm(zdm);
                    entityInfo.setEntityAppendCode(entityJcm);
                    importContext.getEntityCache().addEntity(entityInfo);
                }
                if (dbf.isHasLoadAllRec()) continue;
                dbf.clearDataRow(dbfRow);
            }
            if (!importContext.getDataOption().isUploadEntityData()) continue;
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u63d0\u4ea4\u4fdd\u5b58\u6570\u636e,\u5206\u9875" + String.valueOf(k));
            dataTable.commitChanges(true);
        }
        if (null != fjdField) {
            String zdmKey;
            ArrayList<String> unitkeys2 = new ArrayList<String>();
            for (Map.Entry entry : tempFJDRows.entrySet()) {
                String fjdzdmKey = (String)entry.getKey();
                zdmKey = (String)entry.getValue();
                if (tempFJDZdms.containsKey(fjdzdmKey) || !StringUtils.isNotEmpty((String)zdmKey)) continue;
                unitkeys2.add(zdmKey);
            }
            if (unitkeys2.size() > 0) {
                void var47_54;
                rowKeys.setValue(importContext.getEntityCompanyType(), unitkeys2);
                dataQuery.setMasterKeys(rowKeys);
                IDataTable dataTable = dataQuery.executeQuery(context);
                boolean bl = false;
                while (var47_54 < dataTable.getCount()) {
                    IDataRow row = dataTable.getItem((int)var47_54);
                    row.setAsString(fjdField, "");
                    zdmKey = row.getRecKey();
                    if (importContext.getEntityCache().getEntityKeyFinder().containsKey(zdmKey)) {
                        entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                        entityInfo.setEntityParentKey("");
                    }
                    ++var47_54;
                }
                if (importContext.getDataOption().isUploadEntityData()) {
                    logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u66f4\u6539\u4e0a\u7ea7\u5355\u4f4d");
                    dataTable.commitChanges(true);
                }
            }
        }
    }

    private void upateEntityData(TaskDataContext importContext, IDbfTable dbf, IDataQuery dataQuery, SingleFileFmdmInfo fMTable, Map<String, FieldDefine> fieldMap, Map<String, FieldDefine> singleFieldMap, Map<String, Integer> SingleZdmCodeAndFieldIdMap, List<String> singleZdmWithoutPeriodCodes, int peirodLength, int periodIndex, int zmdLength, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        ExecutorContext context = new ExecutorContext(this.dataRuntimeController);
        DimensionValueSet rowKeys = new DimensionValueSet();
        if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
            rowKeys.setValue("DATATIME", (Object)importContext.getMapNetPeriodCode());
        } else {
            rowKeys.setValue("DATATIME", (Object)importContext.getNetPeriodCode());
        }
        dataQuery.setMasterKeys(rowKeys);
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u67e5\u8be2\u6570\u636e");
        IDataTable dataTable = dataQuery.executeQuery(context);
        HashMap<String, IDataRow> entityAutoExCodeAndRowMap = new HashMap<String, IDataRow>();
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6784\u9020\u4e3b\u4ee3\u7801\u4e0e\u6570\u636e\u884c\u5173\u7cfb,\u884c\u6578:" + dataTable.getCount());
        DataEntityInfo entityInfo = null;
        for (int i = 0; i < dataTable.getCount(); ++i) {
            IDataRow entityRow = dataTable.getItem(i);
            String zdmKey = entityRow.getRecKey();
            entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
            if (entityInfo == null) continue;
            entityAutoExCodeAndRowMap.put(entityInfo.getEntityAutoExCode(), entityRow);
        }
        FieldDefine fjdField = null;
        FieldDefine codeField = null;
        FieldDefine UnitCodeField = null;
        FieldDefine titleField = null;
        FieldDefine autoAppendField = null;
        boolean isIncrement = true;
        boolean isUpdate = true;
        HashMap<String, String> zdmWithoutPeriodTempMap = new HashMap<String, String>();
        HashMap<String, String> zdmTempMap = new HashMap<String, String>();
        HashMap<String, IDataRow> tempFJDRows = new HashMap<String, IDataRow>();
        HashMap<String, String> tempFJDZdms = new HashMap<String, String>();
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        HashMap<String, DataRow> dbfZdmRowMap = new HashMap<String, DataRow>();
        HashMap<String, String> dbfZdmFjdMap = new HashMap<String, String>();
        uploadEntityZdmKeyMap.clear();
        insertEntityZdmKeyMap.clear();
        AutoAppendCode autoCodeCoinfig = null;
        ArrayList<String> skipUnitKeys = new ArrayList<String>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            SkipUnit skipUnit;
            if (null != importContext.getMapingCache().getMapConfig().getConfig()) {
                UpdateWay way = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay();
                isIncrement = way.isIncrement();
                isUpdate = way.isUpdate();
                skipUnit = importContext.getMapingCache().getMapConfig().getConfig().getSkipUnit();
                if (null != skipUnit) {
                    List skipUnitKeys3;
                    String skipFormula;
                    List skipUnitKeys2 = skipUnit.getUnitKey();
                    if (null != skipUnitKeys2) {
                        skipUnitKeys.addAll(skipUnitKeys2);
                    }
                    if (StringUtils.isNotEmpty((String)(skipFormula = skipUnit.getFormula())) && null != (skipUnitKeys3 = this.taskDataService.queryFilterUnits(importContext, skipFormula))) {
                        skipUnitKeys.addAll(skipUnitKeys3);
                    }
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getMapping()) {
                List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                skipUnit = list.iterator();
                while (skipUnit.hasNext()) {
                    UnitCustomMapping unitMap = (UnitCustomMapping)skipUnit.next();
                    if (!StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) || !StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) continue;
                    skipUnitKeys.add(unitMap.getNetUnitKey());
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
                autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
            }
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            fjdField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey);
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
            codeField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey);
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            titleField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey);
        }
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb()) && fieldMap.containsKey(autoCodeCoinfig.getAppendCodeZb())) {
            autoAppendField = fieldMap.get(autoCodeCoinfig.getAppendCodeZb());
        }
        for (FieldDefine field : fieldMap.values()) {
            if (field.getValueType() == FieldValueType.FIELD_VALUE_UNIT_NAME) {
                if (titleField != null) continue;
                titleField = field;
                continue;
            }
            if (field.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE) {
                if (codeField != null) continue;
                codeField = field;
                continue;
            }
            if (field.getValueType() != FieldValueType.FIELD_VALUE_PARENT_UNIT || fjdField != null) continue;
            fjdField = field;
        }
        if (codeField == null || StringUtils.isNotEmpty((String)importContext.getDwEntityId())) {
            // empty if block
        }
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            dbfZdmRowMap.put(zdm, dbfRow);
            String fjd = dbfRow.getValueString("SYS_FJD");
            dbfZdmFjdMap.put(zdm, fjd);
        }
        boolean showConsole = dbf.getRecordCount() <= 10;
        double addProgress = 0.0;
        if (dbf.getRecordCount() > 0) {
            addProgress = importContext.getNextProgressLen() / (double)dbf.getRecordCount();
        }
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            if (null != asyncTaskMonitor) {
                importContext.addProgress(addProgress);
                if (i % 10 == 0) {
                    asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "");
                }
            }
            DataRow dbfRow = dbf.getRecordByIndex(i);
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            IDataRow entityRow = null;
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            String zdmWithOutPeriod = "";
            if (SingleZdmCodeAndFieldIdMap.size() > 0) {
                for (String code : SingleZdmCodeAndFieldIdMap.keySet()) {
                    String fieldValue = dbfRow.getValueString(code);
                    if (StringUtils.isEmpty((String)fieldValue)) {
                        zdmWithOutPeriod = "";
                        break;
                    }
                    zdmWithOutPeriod = zdmWithOutPeriod + fieldValue;
                }
            } else {
                for (String code : singleZdmWithoutPeriodCodes) {
                    zdmWithOutPeriod = zdmWithOutPeriod + dbfRow.getValueString(code);
                }
            }
            if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                zdmWithOutPeriod = zdm;
                if (peirodLength > 0 && StringUtils.isNotEmpty((String)zdm) && zdm.length() > periodIndex) {
                    zdmWithOutPeriod = zdm.substring(0, periodIndex - 1) + zdm.substring(periodIndex + peirodLength - 1, zdm.length());
                }
            }
            String entityJcm = "";
            if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isEmpty((String)(entityJcm = this.getJcmbyZdm(importContext, zdm, zdmWithOutPeriod, dbfZdmFjdMap, autoCodeCoinfig)))) {
                String zdmCode = zdm;
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                }
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165", "notJcm", "", zdmWithOutPeriod, zdmCode);
                importContext.recordLog("FMDM", errorItem);
                continue;
            }
            String entityAutoExCode = zdmWithOutPeriod + entityJcm;
            entityInfo = null;
            boolean isNewEntity = false;
            if (entityAutoExCodeAndRowMap.containsKey(entityAutoExCode)) {
                SingleDataError errorItem;
                entityRow = (IDataRow)entityAutoExCodeAndRowMap.get(entityAutoExCode);
                String zdmKey = entityRow.getRecKey();
                String zdmTitle = "";
                String zdmCode = "";
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                    zdmTitle = entityInfo.getEntityTitle();
                    zdmCode = entityInfo.getEntityCode();
                }
                if (skipUnitKeys.size() > 0 && skipUnitKeys.contains(zdmKey)) {
                    errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "notMap", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                uploadEntityZdmKeyMap.put(zdm, zdmKey);
                if (!isUpdate) {
                    errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f", "notUpdate", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                if (showConsole) {
                    logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u66f4\u65b0" + entityRow.getAsString(0) + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
            } else {
                isNewEntity = true;
                if (!isIncrement) {
                    String zdmCode = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                    }
                    SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", "", zdm, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                ArrayList<String> keys = new ArrayList<String>();
                String newZdmKeyID = null;
                if (zdmWithoutPeriodTempMap.containsKey(zdmWithOutPeriod)) {
                    newZdmKeyID = (String)zdmWithoutPeriodTempMap.get(zdmWithOutPeriod);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else if (zdmTempMap.containsKey(zdm)) {
                    newZdmKeyID = (String)zdmTempMap.get(zdm);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else {
                    newZdmKeyID = UUID.randomUUID().toString();
                }
                if (showConsole) {
                    logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65b0\u589e" + newZdmKeyID.toString() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
                String newZdmKey = newZdmKeyID.toString();
                keys.add(newZdmKey);
                String dimName = importContext.getEntityCompanyType();
                DimensionValueSet rowKeys1 = new DimensionValueSet();
                rowKeys1.setValue(dimName, (Object)newZdmKey);
                entityRow = dataTable.appendRow(rowKeys1);
                entityInfo = new DataEntityInfo();
                entityCodeKeyMap.put(zdmWithOutPeriod, newZdmKey);
                entityKeyCodeMap.put(newZdmKey, zdmWithOutPeriod);
                entityKeyZdmMap.put(newZdmKey, zdm);
                entityZdmAndKeyMap.put(zdm, newZdmKey);
                insertEntityZdmKeyMap.put(zdm, newZdmKey);
                uploadEntityZdmKeyMap.put(zdm, newZdmKey);
                entityAutoExCodeAndRowMap.put(entityAutoExCode, entityRow);
                if (null != codeField) {
                    String dwdmField = null;
                    if (null != fMTable) {
                        dwdmField = fMTable.getDWDMField();
                    }
                    if (StringUtils.isNotEmpty(dwdmField)) {
                        entityRow.setValue(codeField, (Object)dbfRow.getValueString(dwdmField));
                    } else if (StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                        entityRow.setValue(codeField, (Object)zdmWithOutPeriod);
                    } else {
                        entityRow.setValue(codeField, (Object)OrderGenerator.newOrderID());
                    }
                    if (UnitCodeField != null && StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                        entityRow.setValue(UnitCodeField, (Object)zdmWithOutPeriod);
                    }
                }
                if (null != titleField) {
                    String dwmcField = null;
                    if (null != fMTable) {
                        dwmcField = fMTable.getDWMCField();
                    }
                    if (StringUtils.isNotEmpty(dwmcField)) {
                        entityRow.setValue(titleField, (Object)dbfRow.getValueString(dwmcField));
                    } else {
                        entityRow.setValue(titleField, (Object)OrderGenerator.newOrderID());
                    }
                }
                String netPeriodCode = importContext.getNetPeriodCode();
                if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
                    netPeriodCode = importContext.getMapNetPeriodCode();
                }
                PeriodWrapper periodWrapper = new PeriodWrapper(netPeriodCode);
                Date beginDate = null;
                String[] periodList = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper);
                if (periodList != null && periodList.length == 2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    beginDate = simpleDateFormat.parse(periodList[0]);
                }
                entityRow.setValue(fieldMap.get(ImportConsts.ENTITY_SORTORDER), (Object)OrderGenerator.newOrderID());
                if (null != beginDate) {
                    entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey), (Object)beginDate);
                } else {
                    entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey), (Object)Consts.DATE_VERSION_MIN_VALUE);
                }
                entityRow.setValue(fieldMap.get(Consts.EntityField.ENTITY_FIELD_INVALIDTIME.fieldKey), (Object)Consts.DATE_VERSION_MAX_VALUE);
                if (autoAppendField != null) {
                    entityRow.setValue(autoAppendField, (Object)entityJcm);
                }
                entityInfo = new DataEntityInfo();
                entityInfo.setEntityKey(newZdmKey);
                entityInfo.setEntityExCode(zdmWithOutPeriod);
                entityInfo.setSingleZdm(zdm);
            }
            for (int j = 1; j < dbf.geDbfFields().length; ++j) {
                String fjdZdm;
                String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
                String dbFieldValue = dbfRow.getValueString(j);
                if (singleFieldMap.containsKey(dbfFieldName)) {
                    FieldDefine field = singleFieldMap.get(dbfFieldName);
                    entityRow.setAsString(field, dbFieldValue);
                    continue;
                }
                if (!dbfFieldName.equalsIgnoreCase("SYS_FJD") || !StringUtils.isNotEmpty((String)(fjdZdm = dbFieldValue))) continue;
                String fjdZdmWithoutPeriod = fjdZdm = fjdZdm.toUpperCase();
                if (peirodLength > 0 && fjdZdm.length() == zmdLength) {
                    fjdZdmWithoutPeriod = fjdZdm.substring(0, periodIndex - 1) + fjdZdm.substring(periodIndex + peirodLength - 1, fjdZdm.length());
                }
                String jfdEntityJcm = this.getJcmbyZdm(importContext, fjdZdm, fjdZdmWithoutPeriod, dbfZdmFjdMap, autoCodeCoinfig);
                String fjdKey = null;
                String fjdEntityAutoExCode = fjdZdmWithoutPeriod + jfdEntityJcm;
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(fjdEntityAutoExCode);
                if (StringUtils.isNotEmpty((String)fjdEntityAutoExCode) && entityInfo != null) {
                    fjdKey = entityInfo.getEntityKey();
                } else if (entityCodeKeyMap.containsKey(fjdZdmWithoutPeriod)) {
                    fjdKey = (String)entityCodeKeyMap.get(fjdZdmWithoutPeriod);
                } else if (entityZdmAndKeyMap.containsKey(fjdZdm)) {
                    fjdKey = (String)entityZdmAndKeyMap.get(fjdZdm);
                } else if (zdmWithoutPeriodTempMap.containsKey(fjdZdmWithoutPeriod)) {
                    fjdKey = (String)zdmWithoutPeriodTempMap.get(fjdZdmWithoutPeriod);
                    tempFJDRows.put(fjdKey, entityRow);
                } else if (zdmTempMap.containsKey(fjdZdm)) {
                    fjdKey = (String)zdmTempMap.get(fjdZdm);
                    tempFJDRows.put(fjdKey, entityRow);
                }
                if (StringUtils.isEmpty((String)fjdKey) && dbfZdmRowMap.containsKey(fjdZdm)) {
                    String fjdKeyId = UUID.randomUUID().toString();
                    fjdKey = fjdKeyId.toString();
                    zdmWithoutPeriodTempMap.put(fjdZdmWithoutPeriod, fjdKeyId);
                    zdmTempMap.put(fjdZdm, fjdKeyId);
                    tempFJDRows.put(fjdKeyId, entityRow);
                }
                if (null == fjdField || !StringUtils.isNotEmpty((String)fjdKey)) continue;
                entityRow.setAsString(fjdField, fjdKey);
            }
            if (isNewEntity) {
                String entityCode = "";
                if (codeField != null) {
                    entityCode = entityRow.getAsString(codeField);
                }
                String entityTitle = "";
                if (titleField != null) {
                    entityTitle = entityRow.getAsString(titleField);
                }
                String entityRowCaption = "";
                String entityParentKey = "";
                if (fjdField != null) {
                    entityParentKey = entityRow.getAsString(fjdField);
                }
                if (null == entityInfo) {
                    entityInfo = new DataEntityInfo();
                }
                entityInfo.setEntityTitle(entityTitle);
                entityInfo.setEntityCode(entityCode);
                entityInfo.setEntityParentKey(entityParentKey);
                entityInfo.setEntityRowCaption(entityRowCaption);
                entityInfo.setIsUnitMap(false);
                entityInfo.setExpEntityExCode(zdmWithOutPeriod);
                entityInfo.setExpSingleZdm(zdm);
                entityInfo.setEntityAppendCode(entityJcm);
                importContext.getEntityCache().addEntity(entityInfo);
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
        if (null != fjdField) {
            for (Map.Entry entry : tempFJDRows.entrySet()) {
                String zdmKey = (String)entry.getKey();
                if (tempFJDZdms.containsKey(zdmKey)) continue;
                ((IDataRow)entry.getValue()).setAsString(fjdField, "");
                if (!importContext.getEntityCache().getEntityKeyFinder().containsKey(zdmKey)) continue;
                DataEntityInfo entityInfo2 = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                entityInfo2.setEntityParentKey("");
            }
        }
        if (importContext.getDataOption().isUploadEntityData()) {
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u63d0\u4ea4\u4fdd\u5b58\u6570\u636e");
            dataTable.commitChanges(true);
        }
    }

    private String getJcmbyZdm(TaskDataContext importContext, String zdm, String zdmWithOutPeriod, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String entityJcm = "";
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
            if (autoCodeCoinfig.getCodeMapping().containsKey(zdmWithOutPeriod)) {
                entityJcm = (String)autoCodeCoinfig.getCodeMapping().get(zdmWithOutPeriod);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    entityJcm = this.getJcm(importContext, zdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
            }
        }
        return entityJcm;
    }

    private String getJcm(TaskDataContext importContext, String firstzdm, String zdm, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String jcm = "";
        if (!firstzdm.equalsIgnoreCase(zdm)) {
            String zmdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(zdm);
            if (autoCodeCoinfig.getCodeMapping().containsKey(zmdOutPeriodCode)) {
                jcm = (String)autoCodeCoinfig.getCodeMapping().get(zmdOutPeriodCode);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    String fjdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(fjdZdm);
                    jcm = autoCodeCoinfig.getCodeMapping().containsKey(fjdOutPeriodCode) ? (String)autoCodeCoinfig.getCodeMapping().get(fjdOutPeriodCode) : this.getJcm(importContext, firstzdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
            }
        }
        return jcm;
    }

    @Override
    public String getType() {
        return "NRE";
    }
}

