/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.mapping.bean.ZBMapping
 */
package nr.midstore.core.internal.work.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
import com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.mapping.bean.ZBMapping;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nr.midstore.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.ZBMappingInfo;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.FormAccessType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreMappingService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreAttachmentService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreEncryptedFieldService;
import nr.midstore.core.util.IMidstoreReadWriteService;
import nr.midstore.core.util.IMidstoreResultService;
import nr.midstore.core.util.auth.IMidstoreFormDataAccess;
import nr.midstore.core.work.service.IMidstoreExcutePostService;
import nr.midstore.core.work.service.extend.IMidstoreDataPostLaterProcessService;
import nr.midstore.core.work.service.org.IMidstoreOrgDataWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreExcutePostServiceImpl
implements IMidstoreExcutePostService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreExcutePostServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreCheckParamService checkParamService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IMidstoreOrgDataWorkService orgDataWorkService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreMappingService midstoreMappingService;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired(required=false)
    private IMidstoreFormDataAccess formAccessService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IMidstoreReadWriteService readWriteService;
    @Autowired(required=false)
    private IMidstoreDataPostLaterProcessService laterProcessService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private static final boolean NULL_VALUE_POST = true;

    @Override
    public MidstoreResultObject excutePostData(String midstoreSchemeId, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        return this.excutePosData2(context, monitor);
    }

    @Override
    public MidstoreResultObject excutePostData(MidstoreParam param, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.getContext(param.getMidstoreSchemeId(), monitor);
        context.getExcuteParams().putAll(param.getExcuteParams());
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        String userName = (String)context.getExcuteParams().get("EXCUTEUSER");
        if (StringUtils.isNotEmpty((String)userName)) {
            context.setExcuteUserName(userName);
        }
        if (!this.readWriteService.isAdmin()) {
            logger.info("\u666e\u901a\u7528\u6237");
        }
        if (this.formAccessService != null) {
            logger.info("\u666e\u901a\u7528\u6237\uff0c\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            context.setWorkResult(new MistoreWorkResultObject());
            Map<DimensionValueSet, List<String>> unitFormKeys = this.checkParamService.getUnitFormKeys(context, FormAccessType.FORMACCESS_READ);
            logger.info("\u8bfb\u53d6\u6743\u9650\u8bbe\u7f6e\u7684\u5355\u4f4d\u6570\uff1a" + unitFormKeys.size());
            this.checkParamService.tranUnitFormsToTables(context, unitFormKeys);
            return this.excutePosData2(context, monitor);
        }
        logger.info("\u666e\u901a\u7528\u6237\uff0c\u672a\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
        this.checkParamService.doLoadFormScheme(context, true);
        return this.excutePosData2(context, monitor);
    }

    @Override
    public MidstoreResultObject excutePostDataByCode(String midstoreSchemeCode, Map<DimensionValueSet, List<String>> unitFormKeys, AsyncTaskMonitor monitor) throws MidstoreException {
        if (StringUtils.isEmpty((String)midstoreSchemeCode)) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u6807\u8bc6\u4e3a\u7a7a");
        }
        MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByCode(midstoreSchemeCode);
        if (scheme == null) {
            return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff0c" + midstoreSchemeCode);
        }
        if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_POST) {
            return new MidstoreResultObject(false, "\u672a\u914d\u7f6e\u6570\u636e\u63d0\u4f9b\u65b9\u6848");
        }
        String midstoreSchemeId = scheme.getKey();
        MidstoreContext context = this.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        return this.excutePosData2(context, monitor);
    }

    @Override
    public MidstoreResultObject excutePostDataByUser(String midstoreSchemeId, String userName, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreContext context = this.checkParamService.getContext(midstoreSchemeId, monitor);
        MidstoreResultObject checkResult = this.checkParamService.doCheckParamsBeforePostData(context);
        if (checkResult == null) {
            return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38");
        }
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        if (StringUtils.isNotEmpty((String)userName)) {
            context.setExcuteUserName(userName);
        }
        if (this.formAccessService != null) {
            logger.info("\u666e\u901a\u7528\u6237\uff0c\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
            context.setWorkResult(new MistoreWorkResultObject());
            Map<DimensionValueSet, List<String>> unitFormKeys = this.checkParamService.getUnitFormKeys(context, FormAccessType.FORMACCESS_READ);
            logger.info("\u8bfb\u53d6\u6743\u9650\u8bbe\u7f6e\u7684\u5355\u4f4d\u6570\uff1a" + unitFormKeys.size());
            this.checkParamService.tranUnitFormsToTables(context, unitFormKeys);
            return this.excutePosData2(context, monitor);
        }
        logger.info("\u666e\u901a\u7528\u6237\uff0c\u672a\u542f\u7528\u8868\u5355\u6743\u9650\u5224\u65ad");
        this.checkParamService.doLoadFormScheme(context, true);
        return this.excutePosData2(context, monitor);
    }

    private MidstoreResultObject excutePosData2(MidstoreContext context, AsyncTaskMonitor monitor) throws MidstoreException {
        context.setWorkResult(new MistoreWorkResultObject());
        IDataExchangeTask exchangeTask = this.exchangeTaskService.getExchangeTask(context);
        logger.info(context.getMidstoreScheme().getTitle() + "\u6570\u636e\u63d0\u4f9b\u5f00\u59cb");
        this.writeOrgDataToMidstore(context, exchangeTask, monitor);
        this.writeFieldDataToMidstore(context, exchangeTask, monitor);
        logger.info(context.getMidstoreScheme().getTitle() + "\u6570\u6570\u63d0\u4f9b\u5b8c\u6210");
        this.resultService.reSetErrorInfo(context);
        MidstoreResultObject result = new MidstoreResultObject(true, "");
        result.getWorkResults().add(context.getWorkResult());
        if (this.laterProcessService != null) {
            logger.info("\u62a5\u8868\u6570\u636e\u63d0\u4f9b\u5b8c\u6210\uff0c\u8c03\u7528\u540e\u5904\u7406\u6269\u5c55\u63a5\u53e3");
            this.laterProcessService.laterProcessDataToMidstore(context, exchangeTask);
        }
        return result;
    }

    private void writeOrgDataToMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        logger.info(context.getMidstoreScheme().getTitle() + "\u6570\u636e\u63d0\u4f9b\u7ec4\u7ec7\u673a\u6784");
        this.orgDataWorkService.saveOrgDatas(context, dataExchangeTask, monitor);
    }

    private void writeFieldDataToMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        logger.info(context.getMidstoreScheme().getTitle() + "\u6570\u636e\u63d0\u4f9b\u6307\u6807\u6570\u636e");
        this.midstoreMappingService.initZbMapping(context);
        this.midstoreMappingService.initPeriodMapping(context);
        this.midstoreMappingService.initEnumMapping(context);
        MidstoreFieldDTO queryParam = new MidstoreFieldDTO();
        queryParam.setSchemeKey(context.getSchemeKey());
        if (context.getExchangeEnityCodes().size() > 499 && context.getExchangeEnityCodes().size() < 5000) {
            this.dimensionService.createTempTable(context);
        }
        try {
            DETableInfo tableInfo;
            List tableModes = dataExchangeTask.getAllTableModel();
            ArrayList<DETableModel> fixTableModes = new ArrayList<DETableModel>();
            ArrayList<DETableModel> floatTableModes = new ArrayList<DETableModel>();
            for (DETableModel tableModel : tableModes) {
                tableInfo = tableModel.getTableInfo();
                TableType tableType = tableInfo.getType();
                if (tableType == TableType.ZB) {
                    fixTableModes.add(tableModel);
                    continue;
                }
                if (tableType != TableType.BIZ) continue;
                floatTableModes.add(tableModel);
            }
            for (DETableModel tableModel : fixTableModes) {
                tableInfo = tableModel.getTableInfo();
                logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u8868\uff0c" + tableInfo.getName());
                this.writeFixFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
            for (DETableModel tableModel : floatTableModes) {
                tableInfo = tableModel.getTableInfo();
                logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u8868\uff0c" + tableInfo.getName());
                this.writeFloatFieldDataFromMidstore(context, dataExchangeTask, tableModel);
            }
        }
        catch (DataExchangeException e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException(e.getMessage(), e);
        }
        finally {
            this.dimensionService.closeTempTable(context);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeFixFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setNrPeriodCode(nrPeriodCode);
            context.getWorkResult().setNrPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        MidstoreFieldDTO param = new MidstoreFieldDTO();
        param.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> allMidFields = this.fieldService.list(param);
        HashMap allMidFieldMap = new HashMap();
        for (MidstoreFieldDTO field : allMidFields) {
            List<MidstoreFieldDTO> fieldList = null;
            if (allMidFieldMap.containsKey(field.getCode())) {
                fieldList = (List)allMidFieldMap.get(field.getCode());
            } else {
                fieldList = new ArrayList();
                allMidFieldMap.put(field.getCode(), fieldList);
            }
            fieldList.add(field);
        }
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
        List deZbInfos = tableModel.getRefZBs();
        Map<String, DEZBInfo> deFieldMap = deZbInfos.stream().collect(Collectors.toMap(DEZBInfo::getName, DEZBInfo2 -> DEZBInfo2));
        for (DEZBInfo zbInfo : deZbInfos) {
            String nrTableCode;
            String deFieldName = zbInfo.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            ZBMapping zbMaping = null;
            if (context.getMappingCache().getSrcZbMapingInfos().containsKey(deFieldName)) {
                zbMaping = context.getMappingCache().getSrcZbMapingInfos().get(deFieldName).getZbMapping();
                nrTableCode = zbMaping.getTable();
                nrFixTableCodes.add(nrTableCode);
                continue;
            }
            if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                zbMaping = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping();
                nrTableCode = zbMaping.getTable();
                nrFixTableCodes.add(nrTableCode);
                continue;
            }
            if (!allMidFieldMap.containsKey(deFieldName)) continue;
            List midFields = (List)allMidFieldMap.get(deFieldName);
            MidstoreFieldDTO midField = (MidstoreFieldDTO)midFields.get(0);
            DataTable dataTable = this.dataSchemeSevice.getDataTable(midField.getSrcTableKey());
            nrFixTableCodes.add(dataTable.getCode());
        }
        try {
            dataExchangeTask.deleteData(deTableCode, "DATATIME='" + dePeriodCode + "'");
            IZBWriter zbWriter = dataExchangeTask.createZBWriter(deTableCode);
            try {
                HashMap nrDataTableFields = new HashMap();
                HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
                block14: for (String nrTableCode : nrFixTableCodes) {
                    Map dimNameValueList;
                    DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
                    if (dataTable == null) {
                        logger.info("\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                        continue;
                    }
                    List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
                    HashMap<String, DataField> fieldCodeList = new HashMap<String, DataField>();
                    for (Object field : dataFieldList) {
                        fieldCodeList.put(field.getCode(), (DataField)field);
                        if (!StringUtils.isNotEmpty((String)field.getRefDataEntityKey())) continue;
                        String baseDataCode = field.getRefDataEntityKey();
                        baseDataCode = EntityUtils.getId((String)baseDataCode);
                        nrFieldMapBaseDatas.put(field.getCode(), baseDataCode);
                    }
                    nrDataTableFields.put(nrTableCode, fieldCodeList);
                    if (context.getExcuteParams().containsKey("DIMNAMEVALUELIST") && (dimNameValueList = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST")) != null) {
                        Object field;
                        field = dimNameValueList.keySet().iterator();
                        while (field.hasNext()) {
                            String dimName = (String)field.next();
                            if (StringUtils.isEmpty((String)dimName) || dimName.equalsIgnoreCase(context.getEntityTypeName()) || dimName.equalsIgnoreCase(context.getDateTypeName())) continue;
                            Set dimValues = (Set)dimNameValueList.get(dimName);
                            StringBuilder sp = new StringBuilder();
                            for (String dimValue : dimValues) {
                                sp.append(dimValue).append(',');
                            }
                            if (sp.length() == 0) continue;
                            sp.delete(sp.length() - 1, sp.length());
                            if (dimSetMap.containsKey(dimName)) {
                                dimSetMap.get(dimName).setValue(sp.toString());
                                continue;
                            }
                            DimensionValue otherDim = new DimensionValue();
                            otherDim.setType(0);
                            otherDim.setName(dimName);
                            otherDim.setValue(sp.toString());
                            dimSetMap.put(dimName, otherDim);
                        }
                    }
                    Map unitDimsList = null;
                    if (context.getExcuteParams().containsKey("UNITDIMSLIST")) {
                        unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
                    }
                    MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
                    if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                        TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
                        tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
                    }
                    tableContext.setFloatImpOpt(2);
                    IMidstoreDataSet bathDataSet = this.batchImportService.getBatchExportRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey());
                    try {
                        int corpKeyFieldId = -1;
                        int corpCodeFieldId = -1;
                        int periodFieldId = -1;
                        int bizOrderField = -1;
                        ArrayList<ExportFieldDefine> netFieldList = new ArrayList<ExportFieldDefine>();
                        if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                            logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u53f0\u5361\u8868," + dataTable.getCode());
                        } else {
                            logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u6307\u6807\u8868\u6570\u636e," + dataTable.getCode());
                        }
                        FieldDefine dwField = bathDataSet.getUnitFieldDefine();
                        FieldDefine dwField2 = null;
                        if (bathDataSet.getBizFieldDefList().size() > 2) {
                            FieldDefine field1 = bathDataSet.getBizFieldDefList().get(0);
                            if (field1.getCode().contains("PERIOD")) {
                                dwField2 = field1 = bathDataSet.getBizFieldDefList().get(1);
                            }
                            dwField2 = field1;
                            if (dwField == null) {
                                dwField = dwField2;
                            }
                        }
                        HashMap<String, Integer> nrFieldMap = new HashMap<String, Integer>();
                        for (ExportFieldDefine netfield : bathDataSet.getFieldDataList()) {
                            String fieldCode = netfield.getCode();
                            String tableName = netfield.getTableCode();
                            int idPos = fieldCode.indexOf(".");
                            if (idPos >= 0) {
                                tableName = fieldCode.substring(0, idPos);
                                fieldCode = fieldCode.substring(idPos + 1, fieldCode.length());
                            }
                            netFieldList.add(netfield);
                            if (corpKeyFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_KEY.getValue()) {
                                corpKeyFieldId = netFieldList.size() - 1;
                            } else if (corpCodeFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE.getValue()) {
                                corpCodeFieldId = netFieldList.size() - 1;
                            } else if (netfield.getValueType() == FieldValueType.FIELD_VALUE_PERIOD_VALUE.getValue()) {
                                periodFieldId = netFieldList.size() - 1;
                            } else if ("DATATIME".equalsIgnoreCase(fieldCode)) {
                                periodFieldId = netFieldList.size() - 1;
                            } else if (netfield.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                                bizOrderField = netFieldList.size() - 1;
                            } else if ("BIZKEYORDER".equalsIgnoreCase(fieldCode)) {
                                bizOrderField = netFieldList.size() - 1;
                            } else if (corpKeyFieldId < 0 && dwField != null && StringUtils.isNotEmpty((String)fieldCode) && fieldCode.equalsIgnoreCase(dwField.getCode())) {
                                corpKeyFieldId = netFieldList.size() - 1;
                            } else if (corpKeyFieldId < 0 && dwField2 != null && StringUtils.isNotEmpty((String)fieldCode) && fieldCode.equalsIgnoreCase(dwField2.getCode())) {
                                corpKeyFieldId = netFieldList.size() - 1;
                            } else if ("MDCODE".equalsIgnoreCase(fieldCode)) {
                                corpKeyFieldId = netFieldList.size() - 1;
                            } else if ("MDCODE".equalsIgnoreCase(fieldCode) && corpCodeFieldId < 0) {
                                corpCodeFieldId = netFieldList.size() - 1;
                            }
                            nrFieldMap.put(fieldCode, netFieldList.size() - 1);
                        }
                        List<ExportFieldDefine> nrExportFiels = bathDataSet.getFieldDataList();
                        HashSet<String> checkExcanageCodes = new HashSet<String>();
                        checkExcanageCodes.addAll(context.getExchangeEnityCodes());
                        while (bathDataSet.hasNext()) {
                            List floatDatas2 = (List)bathDataSet.next();
                            if (floatDatas2.size() == 0) {
                                logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u6307\u6807\u8868" + dataTable.getCode() + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                                continue block14;
                            }
                            Object[] floatDatas = floatDatas2.toArray();
                            if (floatDatas.length != netFieldList.size()) {
                                logger.info("\u6570\u636e\u96c6\u51fa\u9519\uff1a\u6307\u6807\u5b57\u6bb5\u548c\u6570\u636e\u96c6\u5217\u6570\u4e0d\u4e00\u81f4");
                            }
                            String nrOrgCode = "";
                            String nrPeriodCode1 = "";
                            if (corpKeyFieldId >= 0) {
                                nrOrgCode = (String)floatDatas[corpKeyFieldId];
                            }
                            if (periodFieldId >= 0) {
                                nrPeriodCode1 = (String)floatDatas[periodFieldId];
                            }
                            if (!checkExcanageCodes.contains(nrOrgCode)) continue;
                            String deOrgCode = nrOrgCode;
                            if (context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode)) {
                                deOrgCode = context.getMappingCache().getUnitMappingInfos().get(nrOrgCode).getUnitMapingCode();
                            }
                            if (unitDimsList != null && unitDimsList.containsKey(nrOrgCode)) {
                                List unitDims = (List)unitDimsList.get(nrOrgCode);
                                DimensionValueSet unitDim = (DimensionValueSet)unitDims.get(0);
                                boolean dimIsSame = true;
                                for (int k = 0; k < unitDim.size(); ++k) {
                                    String dimName = unitDim.getName(k);
                                    String dimValue = (String)unitDim.getValue(k);
                                    if (!nrFieldMap.containsKey(dimName)) continue;
                                    int fieldId = (Integer)nrFieldMap.get(dimName);
                                    String fieldValue = (String)floatDatas[fieldId];
                                    if (!StringUtils.isNotEmpty((String)dimValue) || dimValue.equalsIgnoreCase(fieldValue)) continue;
                                    dimIsSame = false;
                                    break;
                                }
                                if (!dimIsSame) continue;
                            }
                            for (int i = 0; i < nrExportFiels.size(); ++i) {
                                ExportFieldDefine exportField = nrExportFiels.get(i);
                                String nrFieldCode = exportField.getCode();
                                String nrTableCode1 = exportField.getTableCode();
                                int id1 = nrFieldCode.indexOf(".");
                                if (id1 > 0) {
                                    nrFieldCode = nrFieldCode.substring(id1 + 1, nrFieldCode.length());
                                }
                                String mapCode = nrTableCode1 + "[" + nrFieldCode + "]";
                                String deFieldName = nrFieldCode;
                                if (context.getMappingCache().getZbMapingInfosOld().containsKey(nrFieldCode)) {
                                    deFieldName = context.getMappingCache().getZbMapingInfosOld().get(nrFieldCode).getMappingzb();
                                } else if (context.getMappingCache().getZbMapingInfos().containsKey(mapCode)) {
                                    deFieldName = context.getMappingCache().getZbMapingInfos().get(mapCode).getMappingzb();
                                }
                                if ("TIMEKEY".equalsIgnoreCase(nrFieldCode) || "MDCODE".equalsIgnoreCase(nrFieldCode) || "DATATIME".equalsIgnoreCase(nrFieldCode) || dimSetMap.containsKey(nrFieldCode) || !deFieldMap.containsKey(deFieldName)) continue;
                                Object fieldObject = floatDatas[i];
                                String fieldValue = (String)fieldObject;
                                DEZBInfo deField = deFieldMap.get(deFieldName);
                                if (deField.getDataType() == DEDataType.INTEGER) {
                                    fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : Integer.valueOf(Integer.parseInt(fieldValue));
                                } else if (deField.getDataType() == DEDataType.FLOAT) {
                                    fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : Double.valueOf(Double.parseDouble(fieldValue));
                                } else if (deField.getDataType() == DEDataType.DATE) {
                                    fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : this.dateFormatter.parse(fieldValue);
                                } else if (deField.getDataType() == DEDataType.FILE) {
                                    String groupFileKey = fieldValue;
                                    if (StringUtils.isNotEmpty((String)fieldValue)) {
                                        byte[] fileData = this.attachmentService.getFieldDataFromNR(groupFileKey, context.getTaskDefine().getDataScheme(), context.getTaskDefine().getKey());
                                        DEAttachMent attachMent = new DEAttachMent(deOrgCode + "_" + tableModel.getTableInfo().getName() + "_" + deField.getName(), fileData);
                                        fieldObject = attachMent;
                                    } else {
                                        fieldObject = null;
                                    }
                                } else if (nrFieldMapBaseDatas.containsKey(nrFieldCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                                    EnumMappingInfo enumMapping;
                                    String baseDataCode = (String)nrFieldMapBaseDatas.get(nrFieldCode);
                                    if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getItemMappings().containsKey(fieldValue)) {
                                        fieldObject = enumMapping.getItemMappings().get(fieldValue).getMappingCode();
                                    }
                                }
                                try {
                                    if (deField.getIsEncrypted() && fieldObject != null) {
                                        fieldObject = this.encryptedFieldService.encrypt(context.getMidstoreScheme(), (String)fieldObject);
                                    }
                                    zbWriter.insert(dePeriodCode, deOrgCode, deFieldName, fieldObject);
                                    continue;
                                }
                                catch (Exception e) {
                                    logger.info(String.format("\u65f6\u671f\uff1a%s,\u5355\u4f4d\uff1a%s,\u5b57\u6bb5\uff1a%s,\u503c\uff1a%s", dePeriodCode, deOrgCode, deFieldName, fieldValue));
                                    logger.error(e.getMessage(), e);
                                }
                            }
                        }
                    }
                    finally {
                        if (bathDataSet != null) {
                            bathDataSet.close();
                        }
                        logger.info("\u6570\u636e\u63d0\u4f9b\uff1aNR\u6307\u6807\u8868," + nrTableCode + ",\u5b8c\u6210");
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                this.resultService.addUnitErrorInfo(context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
                throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            finally {
                logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u51c6\u5907\u5173\u95ed\u4e2d\u95f4\u5e93\u56fa\u5b9a\u6307\u6807\u8868\uff0c" + deTableCode);
                zbWriter.close();
                logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u5b8c\u6210\u5173\u95ed\u4e2d\u95f4\u5e93\u56fa\u5b9a\u6307\u6807\u8868\uff0c" + deTableCode);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MidstoreException("\u6570\u636e\u63d0\u4f9b\uff1a\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private void writeFloatFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map dimNameValueList;
        DataTable dataTable;
        String deTableCode;
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setNrPeriodCode(nrPeriodCode);
            context.getWorkResult().setNrPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        DETableInfo tableInfo = tableModel.getTableInfo();
        String nrTableCode = deTableCode = tableInfo.getName();
        String tablePrefix = context.getMidstoreScheme().getTablePrefix();
        if (StringUtils.isNotEmpty((String)tablePrefix) && nrTableCode.startsWith(tablePrefix + "_")) {
            nrTableCode = nrTableCode.substring(tablePrefix.length() + 1, nrTableCode.length());
        }
        if ((dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode)) == null) {
            logger.info("\u6307\u6807\u4e0d\u5b58\u5728\uff0c\u4e0d\u6570\u636e\u63d0\u4f9b\uff1a" + nrTableCode);
            return;
        }
        List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        for (DataField dataField : dataFieldList) {
            if (!StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) continue;
            Object baseDataCode = dataField.getRefDataEntityKey();
            baseDataCode = EntityUtils.getId((String)baseDataCode);
            nrFieldMapBaseDatas.put(dataField.getCode(), (String)baseDataCode);
        }
        if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
            logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u53f0\u5361\u8868\uff0c" + nrTableCode);
        } else {
            logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u6d6e\u52a8\u6307\u6807\u8868\uff0c" + nrTableCode);
        }
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        if (context.getExcuteParams().containsKey("DIMNAMEVALUELIST") && (dimNameValueList = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST")) != null) {
            for (String dimName : dimNameValueList.keySet()) {
                if (StringUtils.isEmpty((String)dimName) || dimName.equalsIgnoreCase(context.getEntityTypeName()) || dimName.equalsIgnoreCase(context.getDateTypeName())) continue;
                Set dimValues = (Set)dimNameValueList.get(dimName);
                StringBuilder sp = new StringBuilder();
                for (Object dimValue : dimValues) {
                    sp.append((String)dimValue).append(',');
                }
                if (sp.length() == 0) continue;
                sp.delete(sp.length() - 1, sp.length());
                if (dimSetMap.containsKey(dimName)) {
                    dimSetMap.get(dimName).setValue(sp.toString());
                    continue;
                }
                DimensionValue otherDim = new DimensionValue();
                otherDim.setType(0);
                otherDim.setName(dimName);
                otherDim.setValue(sp.toString());
                dimSetMap.put(dimName, otherDim);
            }
        }
        Map unitDimsList = null;
        if (context.getExcuteParams().containsKey("UNITDIMSLIST")) {
            unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
        }
        MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
        }
        tableContext.setFloatImpOpt(2);
        ArrayList<String> nrFieldsArr = new ArrayList<String>();
        ArrayList<String> dimFields = new ArrayList<String>();
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashMap<String, Integer> nr2DeFieldIndex = new HashMap<String, Integer>();
        for (DimensionValue dim : dimSetMap.values()) {
            nrFieldsArr.add(dim.getName());
            dimFields.add(dim.getName());
        }
        boolean hasFile = false;
        List fieldInfos = tableModel.getFields();
        Map<String, DEFieldInfo> deFieldMap = fieldInfos.stream().collect(Collectors.toMap(DEFieldInfo::getName, DEFieldInfo2 -> DEFieldInfo2));
        for (int i = 0; i < fieldInfos.size(); ++i) {
            ZBMappingInfo mapInfo;
            DEFieldInfo deField = (DEFieldInfo)fieldInfos.get(i);
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            String mapCode1 = tablePrefix + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                mapInfo = context.getMappingCache().getSrcZbMapingInfos().get(mapCode);
                nrFieldName = mapInfo.getZbMapping().getZbCode();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode1)) {
                mapInfo = context.getMappingCache().getSrcZbMapingInfos().get(mapCode1);
                nrFieldName = mapInfo.getZbMapping().getZbCode();
            } else if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                mapInfo = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName);
                if (dataTable.getCode().equalsIgnoreCase(mapInfo.getZbMapping().getTable())) {
                    nrFieldName = mapInfo.getZbMapping().getZbCode();
                }
            } else if ("TIMEKEY".equalsIgnoreCase(deFieldName)) {
                nrFieldName = "DATATIME";
            }
            nrFieldsArr.add(nrFieldName);
            nr2DeFieldIndex.put(nrFieldName, deFieldNames.size() - 1);
            if (deField.getDataType() != DEDataType.FILE) continue;
            hasFile = true;
        }
        try (IMidstoreDataSet bathDataSet = this.batchImportService.getBatchExportRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey());){
            int corpKeyFieldId = -1;
            int corpCodeFieldId = -1;
            int periodFieldId = -1;
            int bizOrderField = -1;
            ArrayList<ExportFieldDefine> netFieldList = new ArrayList<ExportFieldDefine>();
            FieldDefine dwField = bathDataSet.getUnitFieldDefine();
            FieldDefine dwField2 = null;
            if (bathDataSet.getBizFieldDefList().size() > 2) {
                FieldDefine field1 = bathDataSet.getBizFieldDefList().get(0);
                if (field1.getCode().contains("PERIOD")) {
                    dwField2 = field1 = bathDataSet.getBizFieldDefList().get(1);
                }
                dwField2 = field1;
                if (dwField == null) {
                    dwField = dwField2;
                }
            }
            HashMap<String, Integer> nrFieldMap = new HashMap<String, Integer>();
            for (ExportFieldDefine netfield : bathDataSet.getFieldDataList()) {
                String fieldCode = netfield.getCode();
                String tableName = netfield.getTableCode();
                int idPos = fieldCode.indexOf(".");
                if (idPos >= 0) {
                    tableName = fieldCode.substring(0, idPos);
                    fieldCode = fieldCode.substring(idPos + 1, fieldCode.length());
                }
                netFieldList.add(netfield);
                if (corpKeyFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_KEY.getValue()) {
                    corpKeyFieldId = netFieldList.size() - 1;
                } else if (corpCodeFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE.getValue()) {
                    corpCodeFieldId = netFieldList.size() - 1;
                } else if (netfield.getValueType() == FieldValueType.FIELD_VALUE_PERIOD_VALUE.getValue()) {
                    periodFieldId = netFieldList.size() - 1;
                } else if ("DATATIME".equalsIgnoreCase(fieldCode)) {
                    periodFieldId = netFieldList.size() - 1;
                } else if (netfield.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                    bizOrderField = netFieldList.size() - 1;
                } else if ("BIZKEYORDER".equalsIgnoreCase(fieldCode)) {
                    bizOrderField = netFieldList.size() - 1;
                } else if (corpKeyFieldId < 0 && dwField != null && StringUtils.isNotEmpty((String)fieldCode) && fieldCode.equalsIgnoreCase(dwField.getCode())) {
                    corpKeyFieldId = netFieldList.size() - 1;
                } else if (corpKeyFieldId < 0 && dwField2 != null && StringUtils.isNotEmpty((String)fieldCode) && fieldCode.equalsIgnoreCase(dwField2.getCode())) {
                    corpKeyFieldId = netFieldList.size() - 1;
                } else if ("MDCODE".equalsIgnoreCase(fieldCode)) {
                    corpKeyFieldId = netFieldList.size() - 1;
                } else if ("MDCODE".equalsIgnoreCase(fieldCode) && corpCodeFieldId < 0) {
                    corpCodeFieldId = netFieldList.size() - 1;
                }
                nrFieldMap.put(fieldCode, netFieldList.size() - 1);
            }
            HashSet<String> checkExcanageCodes = new HashSet<String>();
            checkExcanageCodes.addAll(context.getExchangeEnityCodes());
            List<ExportFieldDefine> nrExportFiels = bathDataSet.getFieldDataList();
            dataExchangeTask.deleteData(deTableCode, "DATATIME='" + dePeriodCode + "'");
            IDataWriter dataWriter = dataExchangeTask.createTableWriter(deTableCode, deFieldNames);
            try {
                while (bathDataSet.hasNext()) {
                    List floatDatas2 = (List)bathDataSet.next();
                    if (floatDatas2.size() == 0) {
                        logger.info("\u6570\u636e\u63d0\u4f9b\uff1a\u6307\u6807\u8868" + dataTable.getCode() + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                        break;
                    }
                    Object[] floatDatas = floatDatas2.toArray();
                    if (floatDatas.length != netFieldList.size()) {
                        logger.info("\u6570\u636e\u96c6\u51fa\u9519\uff1a\u6307\u6807\u5b57\u6bb5\u548c\u6570\u636e\u96c6\u5217\u6570\u4e0d\u4e00\u81f4");
                    }
                    String nrOrgCode = "";
                    String nrPeriodCode1 = "";
                    if (corpKeyFieldId >= 0) {
                        nrOrgCode = (String)floatDatas[corpKeyFieldId];
                    }
                    if (periodFieldId >= 0) {
                        nrPeriodCode1 = (String)floatDatas[periodFieldId];
                    }
                    if (!checkExcanageCodes.contains(nrOrgCode)) continue;
                    String deOrgCode = nrOrgCode;
                    if (context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode)) {
                        deOrgCode = context.getMappingCache().getUnitMappingInfos().get(nrOrgCode).getUnitMapingCode();
                    }
                    DimensionValueSet unitDim = null;
                    if (unitDimsList != null && unitDimsList.containsKey(nrOrgCode)) {
                        List unitDims = (List)unitDimsList.get(nrOrgCode);
                        unitDim = (DimensionValueSet)unitDims.get(0);
                        boolean dimIsSame = true;
                        for (int k = 0; k < unitDim.size(); ++k) {
                            String dimName = unitDim.getName(k);
                            String dimValue = (String)unitDim.getValue(k);
                            if (!nrFieldMap.containsKey(dimName)) continue;
                            int fieldId = (Integer)nrFieldMap.get(dimName);
                            String fieldValue = (String)floatDatas[fieldId];
                            if (!StringUtils.isNotEmpty((String)dimValue) || dimValue.equalsIgnoreCase(fieldValue)) continue;
                            dimIsSame = false;
                            break;
                        }
                        if (!dimIsSame) continue;
                    }
                    Object[] deRowData = new Object[fieldInfos.size()];
                    for (int i = 0; i < nrExportFiels.size(); ++i) {
                        ExportFieldDefine exportField = nrExportFiels.get(i);
                        String nrFieldCode = exportField.getCode();
                        String nrTableCode1 = exportField.getTableCode();
                        int id1 = nrFieldCode.indexOf(".");
                        if (id1 > 0) {
                            nrFieldCode = nrFieldCode.substring(id1 + 1, nrFieldCode.length());
                        }
                        String mapCode = nrTableCode1 + "[" + nrFieldCode + "]";
                        String deFieldName = nrFieldCode;
                        if (context.getMappingCache().getZbMapingInfos().containsKey(mapCode)) {
                            deFieldName = context.getMappingCache().getZbMapingInfos().get(mapCode).getMappingzb();
                        } else if (context.getMappingCache().getZbMapingInfosOld().containsKey(nrFieldCode)) {
                            ZBMappingInfo mapInfo = context.getMappingCache().getZbMapingInfosOld().get(nrFieldCode);
                            if (dataTable.getCode().equalsIgnoreCase(mapInfo.getZbMapping().getTable())) {
                                deFieldName = mapInfo.getZbMapping().getMapping();
                            }
                        }
                        if ("TIMEKEY".equalsIgnoreCase(nrFieldCode) || "MDCODE".equalsIgnoreCase(nrFieldCode) || "DATATIME".equalsIgnoreCase(nrFieldCode) || dimSetMap.containsKey(nrFieldCode) || !deFieldMap.containsKey(deFieldName)) continue;
                        Object fieldObject = floatDatas[i];
                        String fieldValue = (String)fieldObject;
                        DEFieldInfo deField = deFieldMap.get(deFieldName);
                        if (deField.getDataType() == DEDataType.INTEGER) {
                            fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : Integer.valueOf(Integer.parseInt(fieldValue));
                        } else if (deField.getDataType() == DEDataType.FLOAT) {
                            fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : Double.valueOf(Double.parseDouble(fieldValue));
                        } else if (deField.getDataType() == DEDataType.DATE) {
                            fieldObject = StringUtils.isEmpty((String)fieldValue) ? null : this.dateFormatter.parse(fieldValue);
                        } else if (deField.getDataType() == DEDataType.FILE) {
                            String groupFileKey = fieldValue;
                            if (StringUtils.isNotEmpty((String)fieldValue)) {
                                byte[] fileData = this.attachmentService.getFieldDataFromNR(groupFileKey, context.getTaskDefine().getDataScheme(), context.getTaskDefine().getKey());
                                DEAttachMent attachMent = new DEAttachMent(deOrgCode + "_" + tableModel.getTableInfo().getName() + "_" + deField.getName() + ".zip", fileData);
                                fieldObject = attachMent;
                            } else {
                                fieldObject = null;
                            }
                        } else if (nrFieldMapBaseDatas.containsKey(nrFieldCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                            EnumMappingInfo enumMapping;
                            String baseDataCode = (String)nrFieldMapBaseDatas.get(nrFieldCode);
                            if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getItemMappings().containsKey(fieldValue)) {
                                fieldObject = enumMapping.getItemMappings().get(fieldValue).getMappingCode();
                            }
                        }
                        if (deField.getIsEncrypted()) {
                            fieldObject = this.encryptedFieldService.encrypt(context.getMidstoreScheme(), (String)fieldObject);
                        }
                        if (!nr2DeFieldIndex.containsKey(nrFieldCode)) continue;
                        int id = (Integer)nr2DeFieldIndex.get(nrFieldCode);
                        deRowData[id] = fieldObject;
                    }
                    for (String nrFieldCode : dimFields) {
                        if (nr2DeFieldIndex.containsKey(nrFieldCode)) {
                            int id = (Integer)nr2DeFieldIndex.get(nrFieldCode);
                            Object FieldObject = null;
                            if ("MDCODE".equalsIgnoreCase(nrFieldCode)) {
                                FieldObject = deOrgCode;
                            } else if ("MD_ORG".equalsIgnoreCase(nrFieldCode)) {
                                FieldObject = deOrgCode;
                            } else if ("DATATIME".equalsIgnoreCase(nrFieldCode)) {
                                FieldObject = dePeriodCode;
                            } else if (dimSetMap.containsKey(nrFieldCode)) {
                                FieldObject = unitDim != null ? unitDim.getValue(nrFieldCode) : dimSetMap.get(nrFieldCode).getValue();
                            }
                            deRowData[id] = FieldObject;
                            continue;
                        }
                        if (!"MD_ORG".equalsIgnoreCase(nrFieldCode)) continue;
                        int id = (Integer)nr2DeFieldIndex.get("MDCODE");
                        deRowData[id] = deOrgCode;
                    }
                    dataWriter.insert(deRowData);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                this.resultService.addTableErrorInfo(context, context.getWorkResult(), "\u5176\u4ed6", e.getMessage(), nrTableCode, "");
                throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            finally {
                logger.info("\u51c6\u5907\u5173\u95ed\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6307\u6807\u8868\u5199\u5165\uff0c" + deTableCode);
                dataWriter.close();
                logger.info("\u5b8c\u6210\u5173\u95ed\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6307\u6807\u8868\u5199\u5165\uff0c" + deTableCode);
            }
        }
    }

    private MidstoreContext getContext(String midstoreSchemeId, AsyncTaskMonitor monitor) {
        MidstoreContext context = new MidstoreContext();
        context.setSchemeKey(midstoreSchemeId);
        context.setAsyncMonitor(monitor);
        context.setMidstoreScheme(this.midstoreSchemeSevice.getByKey(midstoreSchemeId));
        context.setSchemeInfo(this.schemeInfoSevice.getBySchemeKey(midstoreSchemeId));
        return context;
    }
}

