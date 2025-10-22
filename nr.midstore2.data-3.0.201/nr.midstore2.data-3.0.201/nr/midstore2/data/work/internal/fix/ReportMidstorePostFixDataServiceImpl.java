/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter
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
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService
 *  nr.midstore2.core.dataset.IMidstoreBatchImportDataService
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.dataset.IMidstoreRegionDataSetReader
 *  nr.midstore2.core.dataset.MidsotreTableContext
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore2.data.work.internal.fix;

import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter;
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
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreFieldDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreFieldService;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreResultService;
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
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import nr.midstore2.data.work.fix.IReportMidstorePostFixDataService;
import nr.midstore2.data.work.internal.fix.reader.MidstoreFixRegionDataSetReaderImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportMidstorePostFixDataServiceImpl
implements IReportMidstorePostFixDataService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstorePostFixDataServiceImpl.class);
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
    private static final String TEMP_TABLE = "TempAssistantTable";
    private static final String READ_ERROR = "\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a";
    @Value(value="${jiuqi.nvwa.midstore.postByReader:true}")
    private boolean midstorePostByReader;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeFixFieldDataToMidstore(ReportMidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        Map dimNameValueList = (Map)context.getExcuteParams().get("DIMNAMEVALUELIST");
        Map unitDimsList = null;
        if (context.getExcuteParams().containsKey("UNITDIMSLIST")) {
            unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
        }
        Map tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        ArrayList<String> deleteOrgs = null;
        boolean deleteByOrgCode = false;
        if (context.getExcuteParams().containsKey("OrgData")) {
            deleteOrgs = new ArrayList<String>();
            HashSet<String> orgDic = new HashSet<String>();
            String orgCodes = (String)context.getExcuteParams().get("OrgData");
            String[] orgCodes2 = orgCodes.split(",");
            String[] stringArray = orgCodes2;
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String string;
                String deOrgCode = string = stringArray[i];
                if (context.getMappingCache().getUnitMappingInfos().containsKey(string)) {
                    deOrgCode = ((UnitMappingInfo)context.getMappingCache().getUnitMappingInfos().get(string)).getUnitMapingCode();
                } else if (context.isUseOrgCode() && context.getEntityCache().getEntityCodeList().containsKey(string)) {
                    deOrgCode = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityCodeList().get(string)).getOrgCode();
                }
                if (orgDic.contains(deOrgCode)) continue;
                orgDic.add(deOrgCode);
                deleteOrgs.add(deOrgCode);
            }
        }
        if (context.getExcuteParams().containsKey("DeleteByOrgCode")) {
            String flag = (String)context.getExcuteParams().get("DeleteByOrgCode");
            deleteByOrgCode = "TRUE".equalsIgnoreCase(flag);
        }
        context.getExcuteParams().put("DIMNAMEVALUELIST", dimNameValueList);
        Map<String, DimensionValue> dimSetMap2 = this.dimensionService.getDimSetMap(context);
        HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
        for (String dimName : dimSetMap2.keySet()) {
            DimensionValue value = dimSetMap2.get(dimName);
            if (StringUtils.isNotEmpty((CharSequence)value.getValue())) {
                dimSetMap.put(dimName, value);
                continue;
            }
            context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u60c5\u666f\u4e3a\u7a7a\uff0c" + dimName);
        }
        if (dimNameValueList != null) {
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
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setType(0);
                dimensionValue.setName(dimName);
                dimensionValue.setValue(sp.toString());
                dimSetMap.put(dimName, dimensionValue);
            }
        }
        String nrPeriodCode = context.getExcuteNrPeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDlIST");
        if (StringUtils.isNotEmpty((CharSequence)dataTime)) {
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
        context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        MidstoreFieldDTO midstoreFieldDTO = new MidstoreFieldDTO();
        midstoreFieldDTO.setSchemeKey(context.getExeContext().getMidstoreScheme().getKey());
        midstoreFieldDTO.setSourceType(context.getExeContext().getSourceTypeId());
        List allMidFields = this.fieldService.list(midstoreFieldDTO);
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
        ArrayList<String> writeDeFieldNames = new ArrayList<String>();
        HashMap<String, String> writeFieldMapTable = new HashMap<String, String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
        List deZbInfos = tableModel.getRefZBs();
        Map<String, DEZBInfo> deFieldMap = deZbInfos.stream().collect(Collectors.toMap(DEZBInfo::getName, dEZBInfo -> dEZBInfo));
        for (Object zbInfo : deZbInfos) {
            DataTable checkTable;
            String deFieldName = zbInfo.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            ZBMappingInfo zbMaping = null;
            String nrTableCode = null;
            if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                zbMaping = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName);
                nrTableCode = zbMaping.getTable();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                zbMaping = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(mapCode);
                nrTableCode = zbMaping.getTable();
            } else if (allMidFieldMap.containsKey(deFieldName)) {
                List midFields = (List)allMidFieldMap.get(deFieldName);
                String nrTableCode2 = null;
                for (MidstoreFieldDTO midField : midFields) {
                    DataTable dataTable = this.dataSchemeSevice.getDataTable(midField.getSrcTableKey());
                    if (dataTable.getDataTableType() == DataTableType.TABLE) {
                        nrTableCode = dataTable.getCode();
                        continue;
                    }
                    if (dataTable.getDataTableType() != DataTableType.MD_INFO) continue;
                    nrTableCode2 = dataTable.getCode();
                }
                if (StringUtils.isEmpty((CharSequence)nrTableCode)) {
                    nrTableCode = nrTableCode2;
                }
            }
            if (StringUtils.isEmpty((CharSequence)nrTableCode) || tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode) || (checkTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode)) == null || checkTable.getDataTableType() != DataTableType.TABLE && checkTable.getDataTableType() != DataTableType.MD_INFO) continue;
            if (!writeFieldMapTable.containsKey(deFieldName)) {
                writeFieldMapTable.put(deFieldName, nrTableCode);
                writeDeFieldNames.add(deFieldName);
            } else {
                context.info(logger, "\u56fa\u5b9a\u6307\u6807\u91cd\u590d\uff1a" + deFieldName + ",\u6765\u81eaNR" + nrTableCode + ",\u91cd\u590d\u8868\uff1a" + (String)writeFieldMapTable.get(deFieldName));
            }
            nrFixTableCodes.add(nrTableCode);
        }
        ArrayList<String> dimFeilds = new ArrayList<String>();
        if (context.getDataSourceDTO().isUseDimensionField() && tableModel.getTableInfo().getType() == TableType.MDZB) {
            for (DEFieldInfo dimField : tableModel.getFields()) {
                String dimName = dimField.getName();
                dimFeilds.add(dimName);
            }
        }
        try {
            if (deleteByOrgCode && deleteOrgs != null) {
                if (nrFixTableCodes.isEmpty()) {
                    context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u65e0 \u56fa\u5b9a\u6307\u6807" + deTableCode);
                    return;
                }
                if (tableModel.getTableInfo().getType() == TableType.MDZB) {
                    logger.debug("\u5220\u9664\u8868\uff1a" + deTableCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode + ",\u5355\u4f4d\u6570\uff1a" + deleteOrgs.size() + ",\u6307\u6807\u6570\uff1a" + writeDeFieldNames.size());
                    dataExchangeTask.deleteFixedZBTableData(deTableCode, dePeriodCode, deleteOrgs, writeDeFieldNames);
                } else {
                    logger.debug("\u5220\u9664\u8868\uff1a" + deTableCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode + ",\u5355\u4f4d\u6570\uff1a" + deleteOrgs.size() + ",\u6307\u6807\u6570\uff1a" + writeDeFieldNames.size());
                    dataExchangeTask.deleteFixedZBTableData(deTableCode, dePeriodCode, deleteOrgs, writeDeFieldNames);
                }
            } else {
                dataExchangeTask.deleteData(deTableCode, "DATATIME='" + dePeriodCode + "'");
            }
            IZBWriter zbWriter = dataExchangeTask.createZBWriter(deTableCode);
            try {
                HashMap<String, HashMap<String, DataField>> nrDataTableFields = new HashMap<String, HashMap<String, DataField>>();
                HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
                for (String nrTableCode : nrFixTableCodes) {
                    block74: {
                        HashMap<String, Object> repeatFeildValues = new HashMap<String, Object>();
                        DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
                        if (dataTable == null) {
                            context.info(logger, "\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                            continue;
                        }
                        Set tableUnitSet = null;
                        if (tableUnitList != null && tableUnitList.size() > 0) {
                            if (!tableUnitList.containsKey(nrTableCode)) continue;
                            tableUnitSet = (Set)tableUnitList.get(nrTableCode);
                        }
                        List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
                        HashMap<String, DataField> fieldCodeList = new HashMap<String, DataField>();
                        for (DataField field : dataFieldList) {
                            fieldCodeList.put(field.getCode(), field);
                            if (!StringUtils.isNotEmpty((CharSequence)field.getRefDataEntityKey())) continue;
                            String baseDataCode = field.getRefDataEntityKey();
                            baseDataCode = EntityUtils.getId((String)baseDataCode);
                            nrFieldMapBaseDatas.put(field.getCode(), baseDataCode);
                        }
                        nrDataTableFields.put(nrTableCode, fieldCodeList);
                        MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getProgressID());
                        tableContext.setFormSchemeKey(context.getFormSchemeKey());
                        if (context.getIntfObjects().containsKey(TEMP_TABLE)) {
                            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get(TEMP_TABLE);
                            tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
                        }
                        tableContext.setFloatImpOpt(2);
                        MidstoreWorkResultTableData tableResult = this.recordTableResult(context, dataTable);
                        IMidstoreDataSet bathDataSet = this.batchImportService.getBatchExportRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey());
                        try {
                            int corpKeyFieldId = -1;
                            int corpCodeFieldId = -1;
                            ArrayList<ExportFieldDefine> netFieldList = new ArrayList<ExportFieldDefine>();
                            if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u53f0\u5361\u8868," + dataTable.getCode());
                            } else {
                                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u6307\u6807\u8868\u6570\u636e," + dataTable.getCode());
                            }
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
                            for (ExportFieldDefine netfield : bathDataSet.getFieldDataList()) {
                                String fieldCode = netfield.getCode();
                                int idPos = fieldCode.indexOf(46);
                                if (idPos >= 0) {
                                    fieldCode = fieldCode.substring(idPos + 1, fieldCode.length());
                                }
                                netFieldList.add(netfield);
                                if (corpKeyFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_KEY.getValue()) {
                                    corpKeyFieldId = netFieldList.size() - 1;
                                } else if (corpCodeFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE.getValue()) {
                                    corpCodeFieldId = netFieldList.size() - 1;
                                } else if (corpKeyFieldId < 0 && dwField != null && StringUtils.isNotEmpty((CharSequence)fieldCode) && fieldCode.equalsIgnoreCase(dwField.getCode())) {
                                    corpKeyFieldId = netFieldList.size() - 1;
                                } else if (corpKeyFieldId < 0 && dwField2 != null && StringUtils.isNotEmpty((CharSequence)fieldCode) && fieldCode.equalsIgnoreCase(dwField2.getCode())) {
                                    corpKeyFieldId = netFieldList.size() - 1;
                                } else if ("MDCODE".equalsIgnoreCase(fieldCode)) {
                                    corpKeyFieldId = netFieldList.size() - 1;
                                } else if ("MDCODE".equalsIgnoreCase(fieldCode) && corpCodeFieldId < 0) {
                                    corpCodeFieldId = netFieldList.size() - 1;
                                }
                                nrFieldMap.put(fieldCode, netFieldList.size() - 1);
                            }
                            List nrExportFiels = bathDataSet.getFieldDataList();
                            MidstoreFixRegionDataSetReaderImpl regionReader = new MidstoreFixRegionDataSetReaderImpl(context, tableModel, dataTable, dePeriodCode, netFieldList, nrFieldMap, nrFieldMapBaseDatas, tableUnitSet, dimFeilds, nrExportFiels, dimSetMap, deFieldMap, repeatFeildValues, fieldCodeList, tableResult, corpKeyFieldId, zbWriter);
                            if (this.midstorePostByReader) {
                                bathDataSet.queryToDataRowReader((IMidstoreRegionDataSetReader)regionReader);
                                break block74;
                            }
                            while (bathDataSet.hasNext()) {
                                List floatDatas2 = (List)bathDataSet.next();
                                if (floatDatas2.size() == 0) {
                                    context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1aNR\u6307\u6807\u8868" + nrTableCode + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                                    break;
                                }
                                regionReader.readRowData(floatDatas2);
                            }
                        }
                        finally {
                            this.recordUpdateTableResult(tableResult);
                            if (bathDataSet != null) {
                                bathDataSet.close();
                            }
                            context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1aNR\u6307\u6807\u8868," + nrTableCode + ",\u5b8c\u6210");
                        }
                    }
                    if (context.getAsyncMonitor() == null || !context.getAsyncMonitor().isCancel()) continue;
                    break;
                }
            }
            catch (Exception e) {
                context.error(logger, e.getMessage(), e);
                this.resultService.addUnitErrorInfo((MidstoreContext)context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
                throw new MidstoreException(READ_ERROR + e.getMessage(), (Throwable)e);
            }
            finally {
                logger.debug("\u6570\u636e\u63d0\u4f9b\uff1a\u51c6\u5907\u5173\u95ed\u4e2d\u95f4\u5e93\u56fa\u5b9a\u6307\u6807\u8868\uff0c" + deTableCode);
                zbWriter.close();
                context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u5b8c\u6210\u5173\u95ed\u4e2d\u95f4\u5e93\u56fa\u5b9a\u6307\u6807\u8868\uff0c" + deTableCode);
            }
        }
        catch (Exception e) {
            context.error(logger, e.getMessage(), e);
            throw new MidstoreException("\u6570\u636e\u63d0\u4f9b\uff1a\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
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

