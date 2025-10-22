/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
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
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService
 *  nr.midstore2.core.dataset.IMidstoreBatchImportDataService
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.dataset.IMidstoreRegionDataSetReader
 *  nr.midstore2.core.dataset.MidsotreTableContext
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore2.data.work.internal.floating;

import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
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
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.PeriodMapingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nr.midstore2.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;
import nr.midstore2.core.dataset.MidsotreTableContext;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.work.floating.IReportMidstorePostFloatDataService;
import nr.midstore2.data.work.internal.floating.reader.MidstoreFloatTableDataSetReaderImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstorePostFloatDataServiceImpl
implements IReportMidstorePostFloatDataService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstorePostFloatDataServiceImpl.class);
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
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TEMP_TABLE = "TempAssistantTable";
    private static final String READ_ERROR = "\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a";
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;
    @Value(value="${jiuqi.nvwa.midstore.postByReader:true}")
    private boolean midstorePostByReader;

    /*
     * WARNING - void declaration
     */
    @Override
    public void writeFloatFieldDataToMidstore(ReportMidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        block69: {
            String deTableCode;
            Map dimNameValueList;
            Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDlIST");
            ArrayList<String> deleteOrgs = null;
            ArrayList deleteOrgs2 = null;
            boolean deleteByOrgCode = false;
            if (context.getExcuteParams().containsKey("OrgData")) {
                String[] stringArray;
                deleteOrgs = new ArrayList<String>();
                deleteOrgs2 = new ArrayList();
                ArrayList<String> deleteOrgs3 = new ArrayList<String>();
                HashSet<String> orgDic = new HashSet<String>();
                deleteOrgs2.add(deleteOrgs3);
                String orgCodes = (String)context.getExcuteParams().get("OrgData");
                for (String nrOrgCode : stringArray = orgCodes.split(",")) {
                    String deOrgCode = nrOrgCode;
                    if (context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode)) {
                        deOrgCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(nrOrgCode)).getUnitMapingCode();
                    } else if (context.isUseOrgCode() && context.getEntityCache().getEntityCodeList().containsKey(nrOrgCode)) {
                        deOrgCode = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityCodeList().get(nrOrgCode)).getOrgCode();
                    }
                    if (orgDic.contains(deOrgCode)) continue;
                    orgDic.add(deOrgCode);
                    deleteOrgs.add(deOrgCode);
                    if (deleteOrgs3.size() < 500) {
                        deleteOrgs3.add(deOrgCode);
                        continue;
                    }
                    deleteOrgs3 = new ArrayList();
                    deleteOrgs3.add(deOrgCode);
                    deleteOrgs2.add(deleteOrgs3);
                }
            }
            if (context.getExcuteParams().containsKey("DeleteByOrgCode")) {
                String flag = (String)context.getExcuteParams().get("DeleteByOrgCode");
                deleteByOrgCode = "TRUE".equalsIgnoreCase(flag);
            }
            Map<String, DimensionValue> dimSetMap2 = this.dimensionService.getDimSetMap(context);
            HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
            for (String string : dimSetMap2.keySet()) {
                DimensionValue value = dimSetMap2.get(string);
                if (StringUtils.isNotEmpty((CharSequence)value.getValue())) {
                    dimSetMap.put(string, value);
                    continue;
                }
                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u60c5\u666f\u4e3a\u7a7a\uff0c" + string);
            }
            if (context.getExcuteParams().containsKey("DIMNAMEVALUELIST") && (dimNameValueList = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST")) != null) {
                for (String dimName : dimNameValueList.keySet()) {
                    if (StringUtils.isEmpty((CharSequence)dimName) || dimName.equalsIgnoreCase(context.getEntityTypeName()) || dimName.equalsIgnoreCase(context.getDateTypeName())) continue;
                    Set dimValues = (Set)dimNameValueList.get(dimName);
                    StringBuilder sp = new StringBuilder();
                    for (String dimValue : dimValues) {
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
                }
            }
            String nrPeriodCode = context.getExcuteNrPeriod();
            String string = (String)context.getExcuteParams().get("DATATIME");
            if (StringUtils.isNotEmpty((CharSequence)string)) {
                nrPeriodCode = string;
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
            context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
            DETableInfo tableInfo = tableModel.getTableInfo();
            String nrTableCode = deTableCode = tableInfo.getName();
            String tablePrefix = context.getMidstoreScheme().getTablePrefix();
            if (StringUtils.isNotEmpty((CharSequence)tablePrefix) && nrTableCode.startsWith(tablePrefix + "_")) {
                nrTableCode = nrTableCode.substring(tablePrefix.length() + 1, nrTableCode.length());
            }
            if (tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode)) {
                return;
            }
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
            if (dataTable == null) {
                context.info(logger, "\u6307\u6807\u4e0d\u5b58\u5728\uff0c\u4e0d\u6570\u636e\u63d0\u4f9b\uff1a" + nrTableCode);
                return;
            }
            List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
            HashMap<String, DataField> fieldCodeList = new HashMap<String, DataField>();
            for (DataField dataField : dataFieldList) {
                fieldCodeList.put(dataField.getCode(), dataField);
                if (!StringUtils.isNotEmpty((CharSequence)dataField.getRefDataEntityKey())) continue;
                String baseDataCode = dataField.getRefDataEntityKey();
                baseDataCode = EntityUtils.getId((String)baseDataCode);
                nrFieldMapBaseDatas.put(dataField.getCode(), baseDataCode);
            }
            if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u53f0\u5361\u8868\uff0c" + nrTableCode);
            } else {
                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u6d6e\u52a8\u6307\u6807\u8868\uff0c" + nrTableCode);
            }
            Map unitDimsList = null;
            if (context.getExcuteParams().containsKey("UNITDIMSLIST")) {
                unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
            }
            MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getProgressID());
            tableContext.setFormSchemeKey(context.getFormSchemeKey());
            if (context.getIntfObjects().containsKey(TEMP_TABLE)) {
                TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get(TEMP_TABLE);
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
            List fieldInfos = tableModel.getFields();
            Map<String, DEFieldInfo> deFieldMap = fieldInfos.stream().collect(Collectors.toMap(DEFieldInfo::getName, dEFieldInfo -> dEFieldInfo));
            for (int i = 0; i < fieldInfos.size(); ++i) {
                ZBMappingInfo mapInfo;
                DEFieldInfo deField = (DEFieldInfo)fieldInfos.get(i);
                String deFieldName = deField.getName();
                String mapCode = deTableCode + "[" + deFieldName + "]";
                String mapCode1 = tablePrefix + "[" + deFieldName + "]";
                deFieldNames.add(deFieldName);
                String nrFieldName = deFieldName;
                if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                    mapInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode);
                    nrFieldName = mapInfo.getZbCode();
                } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode1)) {
                    mapInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode1);
                    nrFieldName = mapInfo.getZbCode();
                } else if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                    mapInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName);
                    if (dataTable.getCode().equalsIgnoreCase(mapInfo.getTable())) {
                        nrFieldName = mapInfo.getZbCode();
                    }
                } else if ("TIMEKEY".equalsIgnoreCase(deFieldName)) {
                    nrFieldName = "DATATIME";
                }
                nrFieldsArr.add(nrFieldName);
                nr2DeFieldIndex.put(nrFieldName, deFieldNames.size() - 1);
            }
            MidstoreWorkResultTableData tableResult = this.recordTableResult(context, dataTable);
            IMidstoreDataSet bathDataSet = this.batchImportService.getBatchExportRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey());
            try {
                int corpKeyFieldId = -1;
                int corpCodeFieldId = -1;
                ArrayList<ExportFieldDefine> netFieldList = new ArrayList<ExportFieldDefine>();
                FieldDefine dwField = bathDataSet.getUnitFieldDefine();
                FieldDefine dwField2 = null;
                if (bathDataSet.getBizFieldDefList().size() > 2) {
                    FieldDefine field1 = (FieldDefine)bathDataSet.getBizFieldDefList().get(0);
                    if (field1.getCode().contains("PERIOD")) {
                        dwField2 = field1 = (FieldDefine)bathDataSet.getBizFieldDefList().get(1);
                    }
                    dwField2 = field1;
                    if (dwField == null) {
                        dwField = dwField2;
                    }
                }
                HashMap<String, Integer> nrFieldMap = new HashMap<String, Integer>();
                for (Object netfield : bathDataSet.getFieldDataList()) {
                    void var39_52;
                    String string2 = netfield.getCode();
                    int idPos = string2.indexOf(46);
                    if (idPos >= 0) {
                        String string3 = string2.substring(idPos + 1, string2.length());
                    }
                    netFieldList.add((ExportFieldDefine)netfield);
                    if (corpKeyFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_KEY.getValue()) {
                        corpKeyFieldId = netFieldList.size() - 1;
                    } else if (corpCodeFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE.getValue()) {
                        corpCodeFieldId = netFieldList.size() - 1;
                    } else if (corpKeyFieldId < 0 && dwField != null && StringUtils.isNotEmpty((CharSequence)var39_52) && var39_52.equalsIgnoreCase(dwField.getCode())) {
                        corpKeyFieldId = netFieldList.size() - 1;
                    } else if (corpKeyFieldId < 0 && dwField2 != null && StringUtils.isNotEmpty((CharSequence)var39_52) && var39_52.equalsIgnoreCase(dwField2.getCode())) {
                        corpKeyFieldId = netFieldList.size() - 1;
                    } else if ("MDCODE".equalsIgnoreCase((String)var39_52)) {
                        corpKeyFieldId = netFieldList.size() - 1;
                    } else if ("MDCODE".equalsIgnoreCase((String)var39_52) && corpCodeFieldId < 0) {
                        corpCodeFieldId = netFieldList.size() - 1;
                    }
                    nrFieldMap.put((String)var39_52, netFieldList.size() - 1);
                }
                List nrExportFiels = bathDataSet.getFieldDataList();
                if (deleteByOrgCode && deleteOrgs != null && !deleteOrgs.isEmpty()) {
                    for (List list : deleteOrgs2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("DATATIME='" + dePeriodCode + "'").append(" AND ");
                        sb.append("MDCODE in (");
                        for (String orgCode : list) {
                            sb.append("'" + orgCode + "',");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append(")");
                        logger.debug("\u5220\u9664\u8868\u6570\u636e\uff1a" + deTableCode + "," + sb.toString());
                        dataExchangeTask.deleteData(deTableCode, sb.toString());
                    }
                } else {
                    dataExchangeTask.deleteData(deTableCode, "DATATIME='" + dePeriodCode + "'");
                }
                IDataWriter dataWriter = dataExchangeTask.createTableWriter(deTableCode, deFieldNames);
                try {
                    MidstoreFloatTableDataSetReaderImpl midstoreFloatTableDataSetReaderImpl = new MidstoreFloatTableDataSetReaderImpl(context, tableModel, dataTable, dePeriodCode, netFieldList, nrFieldMap, nrFieldMapBaseDatas, nrExportFiels, dimSetMap, deFieldMap, fieldCodeList, tableResult, corpKeyFieldId, dataWriter, fieldInfos, nr2DeFieldIndex, dimFields);
                    if (this.midstorePostByReader) {
                        bathDataSet.queryToDataRowReader((IMidstoreRegionDataSetReader)midstoreFloatTableDataSetReaderImpl);
                        break block69;
                    }
                    while (bathDataSet.hasNext()) {
                        List floatDatas2 = (List)bathDataSet.next();
                        if (floatDatas2.size() == 0) {
                            context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1aNR\u6307\u6807\u8868" + nrTableCode + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                            break;
                        }
                        midstoreFloatTableDataSetReaderImpl.readRowData(floatDatas2);
                    }
                }
                catch (Exception exception) {
                    context.error(logger, exception.getMessage(), exception);
                    this.resultService.addTableErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u5176\u4ed6", exception.getMessage(), nrTableCode, "");
                    throw new MidstoreException(READ_ERROR + exception.getMessage(), (Throwable)exception);
                }
                finally {
                    context.info(logger, "\u51c6\u5907\u5173\u95ed\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6307\u6807\u8868\u5199\u5165\uff0c" + deTableCode);
                    dataWriter.close();
                    context.info(logger, "\u5b8c\u6210\u5173\u95ed\u4e2d\u95f4\u5e93\u6d6e\u52a8\u6307\u6807\u8868\u5199\u5165\uff0c" + deTableCode);
                }
            }
            catch (Exception e) {
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
                context.error(logger, e.getMessage(), e);
                throw new MidstoreException(READ_ERROR + e.getMessage(), (Throwable)e);
            }
            finally {
                this.recordUpdateTableResult(tableResult);
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
            }
        }
    }

    private MidstoreWorkResultTableData recordTableResult(ReportMidstoreContext context, DataTable dataTable) {
        return context.recordTableResult(dataTable);
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

