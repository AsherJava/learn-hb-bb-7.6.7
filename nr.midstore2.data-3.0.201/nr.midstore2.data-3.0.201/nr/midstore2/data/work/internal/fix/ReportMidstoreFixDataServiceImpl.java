/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.core.midstore.dataexchange.services.impl.ZbDataQueryFilter
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService
 *  nr.midstore2.core.dataset.IMidstoreBatchImportDataService
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.dataset.MidsotreTableContext
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 */
package nr.midstore2.data.work.internal.fix;

import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.core.midstore.dataexchange.services.impl.ZbDataQueryFilter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import nr.midstore2.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.MidsotreTableContext;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.util.IReportMidstoreConditonService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.work.fix.IReportMidstoreFixDataService;
import nr.midstore2.data.work.internal.fix.ReportMidstoreZBDataHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstoreFixDataServiceImpl
implements IReportMidstoreFixDataService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreFixDataServiceImpl.class);
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IReportMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IReportMidstoreConditonService conditionService;
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void readFixFieldDataFromMidstore(ReportMidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map<String, DimensionValue> dimSetMap2 = this.dimensionService.getDimSetMap(context);
        HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
        for (String dimName : dimSetMap2.keySet()) {
            DimensionValue value = dimSetMap2.get(dimName);
            if (StringUtils.isNotEmpty((String)value.getValue())) {
                dimSetMap.put(dimName, value);
                continue;
            }
            dimSetMap.put(dimName, value);
            context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a\u60c5\u666f\u4e3a\u7a7a\uff0c" + dimName);
        }
        String nrPeriodCode = context.getExcuteNrPeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDlIST");
        if (StringUtils.isNotEmpty((String)dataTime)) {
            nrPeriodCode = dataTime;
        }
        String dePeriodCode = this.dimensionService.getDePeriodFromNr(context.getTaskDefine().getDateTime(), nrPeriodCode);
        if (context.getMappingCache().getPeriodMappingInfos().containsKey(nrPeriodCode)) {
            dePeriodCode = ((PeriodMapingInfo)context.getMappingCache().getPeriodMappingInfos().get(nrPeriodCode)).getPeriodMapCode();
        }
        if (context.getWorkResult() != null) {
            context.getWorkResult().setPeriodCode(nrPeriodCode);
            context.getWorkResult().setPeriodTitle(this.dimensionService.getPeriodTitle(context.getTaskDefine().getDateTime(), nrPeriodCode));
            context.getWorkResult().setMidstorePeriodCode(dePeriodCode);
        }
        context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
        MidstoreFieldDTO param = new MidstoreFieldDTO();
        param.setSchemeKey(context.getSchemeKey());
        param.setSourceType(context.getExeContext().getSourceTypeId());
        List allMidFields = this.fieldService.list(param);
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
        HashMap<String, DEZBInfo> nrFieldMapDes = new HashMap<String, DEZBInfo>();
        HashMap<String, String> deFieldMapNrs = new HashMap<String, String>();
        HashMap<String, String> nrFieldMapDables = new HashMap<String, String>();
        HashMap nrTableMapFields = new HashMap();
        HashMap nrTableMapDeFields = new HashMap();
        ArrayList<String> dimFields = new ArrayList<String>();
        ArrayList<String> deDimFields = new ArrayList<String>();
        for (DimensionValue dim : dimSetMap.values()) {
            dimFields.add(dim.getName());
            String dimName = dim.getName();
            if (context.getDimEntityCache().getEntitySingleDims().contains(dimName) || "MDCODE".equalsIgnoreCase(dimName) || "MD_ORG".equalsIgnoreCase(dimName) || "DATATIME".equalsIgnoreCase(dimName)) continue;
            deDimFields.add(dimName);
        }
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        List fieldInfos = tableModel.getRefZBs();
        for (DEZBInfo deField : fieldInfos) {
            List fieldList;
            DataField field;
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            String nrTableCode2 = null;
            if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                field = this.dataSchemeSevice.getDataField(((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName)).getFieldKey());
                nrFieldName = field.getCode();
                nrTableCode2 = ((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName)).getTable();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                field = this.dataSchemeSevice.getDataField(((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode)).getFieldKey());
                nrFieldName = field.getCode();
                nrTableCode2 = ((ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode)).getTable();
            } else if (allMidFieldMap.containsKey(nrFieldName) && (fieldList = (List)allMidFieldMap.get(nrFieldName)).size() > 0) {
                MidstoreFieldDTO findField = (MidstoreFieldDTO)fieldList.get(0);
                DataTable dataTable = this.dataSchemeSevice.getDataTable(findField.getSrcTableKey());
                nrTableCode2 = dataTable.getCode();
            }
            nrFieldMapDes.put(nrFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
            if (StringUtils.isEmpty((String)nrTableCode2)) {
                context.info(logger, "\u6307\u6807\u6709\u95ee\u9898\uff1a" + deFieldName + "," + nrFieldName);
                continue;
            }
            nrFieldMapDables.put(nrFieldName, nrTableCode2);
            if (nrTableMapFields.containsKey(nrTableCode2)) {
                ((List)nrTableMapFields.get(nrTableCode2)).add(nrFieldName);
            } else {
                ArrayList<String> nrFields = new ArrayList<String>();
                for (DimensionValue dimensionValue : dimSetMap.values()) {
                    nrFields.add(dimensionValue.getName());
                }
                if (!context.getDimEntityCache().getEntitySingleDims().isEmpty()) {
                    for (String string : context.getDimEntityCache().getEntitySingleDims()) {
                        if (nrFields.contains(string)) continue;
                        nrFields.add(string);
                    }
                }
                nrFields.add(nrFieldName);
                nrTableMapFields.put(nrTableCode2, nrFields);
            }
            if (nrTableMapDeFields.containsKey(nrTableCode2)) {
                ((List)nrTableMapDeFields.get(nrTableCode2)).add(deFieldName);
            } else {
                ArrayList<String> deFields = new ArrayList<String>();
                deFields.add(deFieldName);
                nrTableMapDeFields.put(nrTableCode2, deFields);
            }
            if (tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode2)) continue;
            nrFixTableCodes.add(nrTableCode2);
        }
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        HashMap<String, Map<String, DataField>> nrDataTableFields = new HashMap<String, Map<String, DataField>>();
        for (String nrTableCode : nrFixTableCodes) {
            Map map;
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
            if (dataTable == null) {
                context.info(logger, "\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                continue;
            }
            context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + nrTableCode);
            MistoreWorkResultObject workResult = context.getExeContext().getMidstoreContext().getWorkResult();
            workResult.getPeriodResult().getTableResults();
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
            if (context.getExcuteParams().containsKey("DIMNAMEVALUELIST") && (map = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST")) != null) {
                Object field;
                field = map.keySet().iterator();
                while (field.hasNext()) {
                    String dimName = (String)field.next();
                    if (StringUtils.isEmpty((String)dimName) || dimName.equalsIgnoreCase(context.getEntityTypeName()) || dimName.equalsIgnoreCase(context.getDateTypeName())) continue;
                    Set dimValues = (Set)map.get(dimName);
                    StringBuilder sp = new StringBuilder();
                    Iterator iterator = dimValues.iterator();
                    while (iterator.hasNext()) {
                        String dimValue = (String)iterator.next();
                        sp.append(dimValue).append(',');
                    }
                    if (sp.length() == 0) continue;
                    sp.delete(sp.length() - 1, sp.length());
                    if (dimSetMap.containsKey(dimName)) {
                        ((DimensionValue)dimSetMap.get(dimName)).setValue(sp.toString());
                        continue;
                    }
                    DimensionValue otherDim = new DimensionValue();
                    otherDim.setType(0);
                    otherDim.setName(dimName);
                    otherDim.setValue(sp.toString());
                    dimSetMap.put(dimName, otherDim);
                    dimFields.add(dimName);
                }
            }
            MidsotreTableContext midsotreTableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getProgressID());
            midsotreTableContext.setFormSchemeKey(context.getFormSchemeKey());
            if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
                midsotreTableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
            }
            midsotreTableContext.setFloatImpOpt(2);
            ArrayList<String> nrFieldsArr = (ArrayList<String>)nrTableMapFields.get(nrTableCode);
            if (!context.getDimEntityCache().getEntitySingleDims().isEmpty()) {
                ArrayList<String> nrFieldsArr2 = nrFieldsArr;
                nrFieldsArr = new ArrayList<String>();
                nrFieldsArr.addAll(nrFieldsArr2);
                for (String dimName : context.getDimEntityCache().getEntitySingleDims()) {
                    if (nrFieldsArr.contains(dimName)) continue;
                    nrFieldsArr.add(dimName);
                }
            }
            IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(midsotreTableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);
            MidstoreWorkResultTableData tableResult = this.recordTableResult(context, dataTable);
            try {
                List deFieldsArr = (List)nrTableMapDeFields.get(nrTableCode);
                String contition = String.format("DATATIME = '%s'", dePeriodCode);
                ZbDataQueryFilter filter = new ZbDataQueryFilter();
                filter.setFilterSql(contition);
                filter.setOrgCodes(this.conditionService.getDeEntityCodes(context));
                filter.setQueryZbCodes(deFieldsArr);
                ReportMidstoreZBDataHandlerImpl zbHandler = new ReportMidstoreZBDataHandlerImpl(context, bathDataSet, nrPeriodCode, nrTableCode, deTableCode, nrFieldsArr, nrFieldMapDes, nrDataTableFields, dimSetMap, nrFieldMapBaseDatas, dimFields, nrFieldMapDables, deFieldMapNrs, this.dataSchemeSevice, this.attachmentService);
                dataExchangeTask.readZBData(deTableCode, (ITableDataHandler)zbHandler, filter);
            }
            catch (Exception e) {
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
                context.error(logger, e.getMessage(), e);
                this.resultService.addUnitErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
                context.getWorkResult().getPeriodResult().getErrorWorkTableKeys().add(dataTable.getKey());
                throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
            }
            finally {
                this.recordUpdateTableResult(tableResult);
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
            }
            if (context.getAsyncMonitor() == null || !context.getAsyncMonitor().isCancel()) continue;
            break;
        }
    }

    private MidstoreWorkResultTableData recordTableResult(ReportMidstoreContext context, DataTable dataTable) {
        MidstoreWorkResultTableData tableResult = context.getWorkResult().getPeriodResult().getTableResult(dataTable.getKey());
        if (tableResult == null) {
            tableResult = new MidstoreWorkResultTableData();
            tableResult.getTableDTO().setKey(UUID.randomUUID().toString());
            tableResult.getTableDTO().setSourceTableKey(dataTable.getKey());
            tableResult.getTableDTO().setSourceTableCode(dataTable.getCode());
            tableResult.getTableDTO().setResultKey(context.getWorkResult().getPeriodResult().getWorkResultDTO().getKey());
            tableResult.getTableDTO().setSourceType(context.getExeContext().getSourceTypeId());
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
            tableResult.getTableDTO().setObjectErrorCount(0);
            tableResult.getTableDTO().setObjectItemCount(0);
            tableResult.getTableDTO().setTotalRecordSize(0);
            tableResult.getTableDTO().setErrorRecordSize(0);
            context.getWorkResult().getPeriodResult().addTableResult(tableResult);
        }
        return tableResult;
    }

    private void recordUpdateTableResult(MidstoreWorkResultTableData tableResult) {
        tableResult.getTableDTO().setObjectErrorCount(tableResult.getErrorObjectCodes().size());
        tableResult.getTableDTO().setObjectItemCount(tableResult.getObjectResults().size());
        if (tableResult.getTableDTO().getObjectErrorCount() > 0 || tableResult.getTableDTO().getErrorRecordSize() > 0) {
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_ERROR);
        } else {
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
        }
        for (MidstoreWorkResultObjectData objectResult : tableResult.getObjectResults()) {
            if (objectResult.getObjectDTO().getErrorRecordSize() > 0) {
                objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_ERROR);
                continue;
            }
            objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
        }
    }
}

