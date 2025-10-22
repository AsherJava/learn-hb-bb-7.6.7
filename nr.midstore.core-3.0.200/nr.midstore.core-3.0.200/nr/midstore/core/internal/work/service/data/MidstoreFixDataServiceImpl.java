/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.core.midstore.dataexchange.services.impl.ZbDataQueryFilter
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package nr.midstore.core.internal.work.service.data;

import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.core.midstore.dataexchange.services.impl.ZbDataQueryFilter;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore.core.dataset.IMidstoreBatchImportDataService;
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreFileInfo;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import nr.midstore.core.internal.work.service.data.MidstoreZBDataHandlerImpl;
import nr.midstore.core.util.IMidstoreAttachmentService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.core.util.IMidstoreResultService;
import nr.midstore.core.work.service.data.IMidstoreConditonService;
import nr.midstore.core.work.service.data.IMidstoreFixDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreFixDataServiceImpl
implements IMidstoreFixDataService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreFixDataServiceImpl.class);
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreBatchImportDataService batchImportService;
    @Autowired
    private IMidstoreDimensionService dimensionService;
    @Autowired
    private IMidstoreResultService resultService;
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IMidstoreConditonService conditionService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void readFixFieldDataFromMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
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
        logger.info("\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
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
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        HashMap<String, DEZBInfo> nrFieldMapDes = new HashMap<String, DEZBInfo>();
        HashMap<String, String> deFieldMapNrs = new HashMap<String, String>();
        HashMap<String, String> nrFieldMapDables = new HashMap<String, String>();
        HashMap nrTableMapFields = new HashMap();
        HashMap nrTableMapDeFields = new HashMap();
        ArrayList<String> dimFields = new ArrayList<String>();
        for (DimensionValue dim : dimSetMap.values()) {
            dimFields.add(dim.getName());
        }
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        List fieldInfos = tableModel.getRefZBs();
        for (DEZBInfo deField : fieldInfos) {
            List fieldList;
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            String nrTableCode2 = null;
            if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getTable();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getTable();
            } else if (allMidFieldMap.containsKey(nrFieldName) && (fieldList = (List)allMidFieldMap.get(nrFieldName)).size() > 0) {
                Iterator<DimensionValue> findField = (MidstoreFieldDTO)fieldList.get(0);
                DataTable dataTable = this.dataSchemeSevice.getDataTable(((MidstoreFieldDO)((Object)findField)).getSrcTableKey());
                nrTableCode2 = dataTable.getCode();
            }
            nrFieldMapDes.put(nrFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
            if (StringUtils.isEmpty((String)nrTableCode2)) {
                logger.info("\u6307\u6807\u6709\u95ee\u9898\uff1a" + deFieldName + "," + nrFieldName);
                continue;
            }
            nrFieldMapDables.put(nrFieldName, nrTableCode2);
            if (nrTableMapFields.containsKey(nrTableCode2)) {
                ((List)nrTableMapFields.get(nrTableCode2)).add(nrFieldName);
            } else {
                ArrayList<String> nrFields = new ArrayList<String>();
                for (DimensionValue dim : dimSetMap.values()) {
                    nrFields.add(dim.getName());
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
            Map dimNameValueList;
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
            if (dataTable == null) {
                logger.info("\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                continue;
            }
            logger.info("\u6570\u636e\u63a5\u6536\uff1a" + nrTableCode);
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
            MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
            if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
                tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
            }
            tableContext.setFloatImpOpt(2);
            List nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
            IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);
            try {
                List deFieldsArr = (List)nrTableMapDeFields.get(nrTableCode);
                String contition = String.format("DATATIME = '%s'", dePeriodCode);
                ZbDataQueryFilter filter = new ZbDataQueryFilter();
                filter.setFilterSql(contition);
                filter.setOrgCodes(this.conditionService.getDeEntityCodes(context));
                filter.setQueryZbCodes(deFieldsArr);
                MidstoreZBDataHandlerImpl zbHandler = new MidstoreZBDataHandlerImpl(context, bathDataSet, nrPeriodCode, nrTableCode, deTableCode, nrFieldsArr, nrFieldMapDes, nrDataTableFields, dimSetMap, nrFieldMapBaseDatas, dimFields, nrFieldMapDables, deFieldMapNrs, this.dataSchemeSevice, this.attachmentService);
                dataExchangeTask.readZBData(deTableCode, (ITableDataHandler)zbHandler, filter);
            }
            catch (Exception e) {
                if (bathDataSet != null) {
                    bathDataSet.close();
                }
                logger.error(e.getMessage(), e);
                this.resultService.addUnitErrorInfo(context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
                throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
            finally {
                if (bathDataSet == null) continue;
                bathDataSet.close();
            }
        }
    }

    private void saveFixDataToNR(MidstoreContext context, IMidstoreDataSet bathDataSet, String nrPeriodCode, String nrTableCode, String deTableCode, List<String> nrFieldsArr, Map<String, DEZBInfo> nrFieldMapDes, Map<String, Map<String, DataField>> nrDataTableFields, Map<String, DimensionValue> dimSetMap, Map<String, String> nrFieldMapBaseDatas, List<String> dimFields, MemoryDataSet memoryDataSet, Map<String, String> nrFieldMapDables, Map<String, String> deFieldMapNrs) throws Exception {
        DimensionValueSet dimSet;
        Object obj;
        DimensionValue dim;
        ArrayList<Object> listRow;
        int importRowCount = 0;
        HashSet<String> orgHasDatas = new HashSet<String>();
        Map<String, DataField> fieldCodeList = nrDataTableFields.get(nrTableCode);
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
        Map tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        Map tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        Metadata metaData = memoryDataSet.getMetadata();
        Iterator it = memoryDataSet.iterator();
        if (!it.hasNext()) {
            logger.info("\u56fa\u5b9a\u6307\u6807\u65e0\u6570\u636e");
        }
        HashMap<String, Integer> deDataCoumnMap = new HashMap<String, Integer>();
        for (int i = 0; i < metaData.getColumnCount(); ++i) {
            Column col = metaData.getColumn(i);
            deDataCoumnMap.put(col.getName(), i);
        }
        while (it.hasNext()) {
            Set unitList;
            DataRow row = (DataRow)it.next();
            String nrOrgCode = null;
            if (!deDataCoumnMap.containsKey("MDCODE")) continue;
            int columIndex = (Integer)deDataCoumnMap.get("MDCODE");
            String orgDataCode2 = row.getString(columIndex);
            if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                UnitMappingInfo unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                orgDataCode2 = unitInfo.getUnitCode();
            }
            nrOrgCode = orgDataCode2;
            context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
            if (context.getExchangeEnityCodes().size() > 0 && !context.getExchangeEnityCodes().contains(nrOrgCode) || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode))) continue;
            if (StringUtils.isEmpty((String)nrOrgCode)) {
                logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
                continue;
            }
            Object[] rowData = new Object[nrFieldsArr.size()];
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                int rowIndex;
                String defieldName;
                Column col = metaData.getColumn(i);
                Object fieldObject = row.getValue(i);
                String fieldValue = row.getString(i);
                String nrZBCode = defieldName = col.getName();
                if ("MDCODE".equalsIgnoreCase(defieldName) || "DATATIME".equalsIgnoreCase(defieldName) || !deFieldMapNrs.containsKey(defieldName) || !nrFieldMapDables.containsKey(nrZBCode = deFieldMapNrs.get(defieldName)) || (rowIndex = nrFieldsArr.indexOf(nrZBCode)) < 0) continue;
                DEZBInfo deField = nrFieldMapDes.get(defieldName);
                if (deField != null && deField.getDataType() != DEDataType.INTEGER && deField.getDataType() != DEDataType.FLOAT) {
                    if (deField.getDataType() == DEDataType.DATE) {
                        if (fieldObject instanceof Date) {
                            Date date = (Date)fieldObject;
                            fieldValue = this.dateFormatter.format(date);
                        } else if (fieldObject instanceof GregorianCalendar) {
                            GregorianCalendar calendar = (GregorianCalendar)fieldObject;
                            Date date = calendar.getTime();
                            fieldValue = this.dateFormatter.format(date);
                        } else {
                            fieldValue = null;
                        }
                    } else if (deField.getDataType() == DEDataType.FILE) {
                        DEAttachMent attachMent = null;
                        if (fieldObject instanceof DEAttachMent) {
                            attachMent = (DEAttachMent)fieldObject;
                        } else if (fieldObject != null) {
                            logger.info("\u6587\u4ef6\u578b\u5b57\u6bb5\u5b58\u5728\u95ee\u9898\uff1a" + defieldName);
                        }
                        if (attachMent != null && attachMent.getData() != null) {
                            try {
                                DataField dataField = null;
                                if (fieldCodeList.containsKey(nrZBCode)) {
                                    dataField = fieldCodeList.get(nrZBCode);
                                }
                                MidstoreFileInfo fieldFileInfo = new MidstoreFileInfo();
                                fieldFileInfo.setDataSchemeKey(context.getTaskDefine().getDataScheme());
                                if (dataField != null) {
                                    fieldFileInfo.setFieldKey(dataField.getKey());
                                }
                                if (tableFormList.containsKey(nrTableCode)) {
                                    fieldFileInfo.setFormKey((String)tableFormList.get(nrTableCode));
                                }
                                fieldFileInfo.setFormSchemeKey(context.getFormSchemeKey());
                                Map<String, DimensionValue> fieldDimSetMap = dimSetMap;
                                fieldDimSetMap.get(context.getEntityTypeName()).setValue(nrOrgCode);
                                fieldFileInfo.setDimensionSet(fieldDimSetMap);
                                fieldFileInfo.setTaskKey(context.getTaskDefine().getKey());
                                fieldValue = this.attachmentService.saveFileFieldDataToNR(attachMent.getData(), fieldFileInfo);
                            }
                            catch (Exception e) {
                                logger.error("\u6587\u4ef6\u578b\u5b57\u6bb5" + defieldName + "\u51fa\u9519\uff1a" + e.getMessage(), e);
                                fieldValue = null;
                            }
                        } else {
                            fieldValue = null;
                        }
                    } else if (nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                        EnumMappingInfo enumMapping;
                        String baseDataCode = nrFieldMapBaseDatas.get(nrZBCode);
                        if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                            fieldValue = enumMapping.getSrcItemMappings().get(fieldValue).getItemCode();
                        }
                    }
                }
                rowData[rowIndex] = fieldValue;
            }
            listRow = new ArrayList();
            for (String dimName : dimFields) {
                dim = dimSetMap.get(dimName);
                if (dim == null) continue;
                if ("DATATIME".equalsIgnoreCase(dimName)) {
                    listRow.add(nrPeriodCode);
                    continue;
                }
                if ("MD_ORG".equalsIgnoreCase(dimName)) {
                    listRow.add(nrOrgCode);
                    continue;
                }
                Integer columIndex2 = (Integer)deDataCoumnMap.get(dimName);
                if (columIndex2 != null && columIndex2 > 0) {
                    listRow.add(row.getString(columIndex2.intValue()));
                    continue;
                }
                listRow.add(dim.getValue());
            }
            for (int j = dimFields.size(); j < rowData.length; ++j) {
                obj = rowData[j];
                listRow.add(obj);
            }
            orgHasDatas.add(nrOrgCode);
            dimSet = bathDataSet.importDatas(listRow);
            if (dimSet == null || dimSet.size() <= 0) continue;
            ++importRowCount;
        }
        if (context.isDeleteEmptyData() && context.getExchangeEnityCodes().size() > 0) {
            for (String nrOrgCode : context.getExchangeEnityCodes()) {
                if (orgHasDatas.contains(nrOrgCode)) continue;
                Object[] rowData = new Object[nrFieldsArr.size()];
                for (int k = 0; k < rowData.length; ++k) {
                    rowData[k] = "";
                }
                listRow = new ArrayList<Object>();
                for (String dimName : dimFields) {
                    dim = dimSetMap.get(dimName);
                    if (dim == null) continue;
                    if ("DATATIME".equalsIgnoreCase(dimName)) {
                        listRow.add(nrPeriodCode);
                        continue;
                    }
                    if ("MD_ORG".equalsIgnoreCase(dimName)) {
                        listRow.add(nrOrgCode);
                        continue;
                    }
                    listRow.add(dim.getValue());
                }
                for (int j = dimFields.size(); j < rowData.length; ++j) {
                    obj = rowData[j];
                    listRow.add(obj);
                }
                dimSet = null;
                try {
                    dimSet = bathDataSet.importDatas(listRow);
                }
                catch (Exception e) {
                    dimSet = null;
                    logger.error(e.getMessage(), e);
                }
                if (dimSet == null || dimSet.size() <= 0) continue;
                ++importRowCount;
            }
        }
        if (importRowCount > 0) {
            logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u6307\u6807\u8868\u6570\u636e\u63d0\u4ea4\uff0c" + nrTableCode, (Object)("\uff0c\u884c\u6570\uff0c" + importRowCount));
            bathDataSet.commit();
        }
    }

    private void readFixFieldDataFromMidstoreOld(MidstoreContext context, IDataExchangeTask dataExchangeTask, DETableModel tableModel) throws MidstoreException {
        String nrPeriodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DATATIME");
        Map tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
        Map tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        Map tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
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
        logger.info("\u6570\u636e\u63a5\u6536\uff1a" + tableModel.getTableInfo().getName() + ",\u62a5\u8868\u65f6\u671f\uff1a" + nrPeriodCode + ",\u4e2d\u95f4\u5e93\u65f6\u671f\uff1a" + dePeriodCode);
        DETableInfo tableInfo = tableModel.getTableInfo();
        String deTableCode = tableInfo.getName();
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
        Map<String, DimensionValue> dimSetMap = this.dimensionService.getDimSetMap(context);
        HashMap<String, DEZBInfo> nrFieldMapDes = new HashMap<String, DEZBInfo>();
        HashMap<String, String> deFieldMapNrs = new HashMap<String, String>();
        HashMap<String, String> nrFieldMapDables = new HashMap<String, String>();
        HashMap nrTableMapFields = new HashMap();
        ArrayList<String> dimFields = new ArrayList<String>();
        for (DimensionValue dim : dimSetMap.values()) {
            dimFields.add(dim.getName());
        }
        boolean hasFile = false;
        ArrayList<String> deFieldNames = new ArrayList<String>();
        HashSet<String> nrFixTableCodes = new HashSet<String>();
        List fieldInfos = tableModel.getRefZBs();
        for (DEZBInfo deField : fieldInfos) {
            List fieldList;
            String deFieldName = deField.getName();
            String mapCode = deTableCode + "[" + deFieldName + "]";
            deFieldNames.add(deFieldName);
            String nrFieldName = deFieldName;
            String nrTableCode2 = null;
            if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(deFieldName)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfosOld().get(deFieldName).getZbMapping().getTable();
            } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(mapCode)) {
                nrFieldName = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getZbCode();
                nrTableCode2 = context.getMappingCache().getSrcZbMapingInfos().get(mapCode).getZbMapping().getTable();
            } else if (allMidFieldMap.containsKey(nrFieldName) && (fieldList = (List)allMidFieldMap.get(nrFieldName)).size() > 0) {
                MidstoreFieldDTO findField = (MidstoreFieldDTO)fieldList.get(0);
                DataTable dataTable = this.dataSchemeSevice.getDataTable(findField.getSrcTableKey());
                nrTableCode2 = dataTable.getCode();
            }
            nrFieldMapDes.put(nrFieldName, deField);
            deFieldMapNrs.put(deFieldName, nrFieldName);
            if (StringUtils.isEmpty((String)nrTableCode2)) {
                logger.info("\u6307\u6807\u6709\u95ee\u9898\uff1a" + deFieldName + "," + nrFieldName);
                continue;
            }
            nrFieldMapDables.put(nrFieldName, nrTableCode2);
            if (deField.getDataType() == DEDataType.FILE) {
                hasFile = true;
            }
            if (nrTableMapFields.containsKey(nrTableCode2)) {
                ((List)nrTableMapFields.get(nrTableCode2)).add(nrFieldName);
            } else {
                ArrayList<String> nrFields = new ArrayList<String>();
                for (DimensionValue dim : dimSetMap.values()) {
                    nrFields.add(dim.getName());
                }
                nrFields.add(nrFieldName);
                nrTableMapFields.put(nrTableCode2, nrFields);
            }
            if (tableFieldList != null && tableFieldList.size() > 0 && !tableFieldList.containsKey(nrTableCode2)) continue;
            nrFixTableCodes.add(nrTableCode2);
        }
        HashMap<String, IMidstoreDataSet> nrDataSets = new HashMap<String, IMidstoreDataSet>();
        HashMap<String, String> nrFieldMapBaseDatas = new HashMap<String, String>();
        HashMap nrDataTableFields = new HashMap();
        for (String nrTableCode : nrFixTableCodes) {
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
            if (dataTable == null) {
                logger.info("\u6307\u6807\u8868\u4e0d\u5b58\u5728\uff1a" + nrTableCode);
                continue;
            }
            List dataFieldList = this.dataSchemeSevice.getDataFieldByTable(dataTable.getKey());
            HashMap<String, DataField> fieldCodeList = new HashMap<String, DataField>();
            for (DataField field : dataFieldList) {
                fieldCodeList.put(field.getCode(), field);
                if (!StringUtils.isNotEmpty((String)field.getRefDataEntityKey())) continue;
                String baseDataCode = field.getRefDataEntityKey();
                baseDataCode = EntityUtils.getId((String)baseDataCode);
                nrFieldMapBaseDatas.put(field.getCode(), baseDataCode);
            }
            nrDataTableFields.put(nrTableCode, fieldCodeList);
            MidsotreTableContext tableContext = this.batchImportService.getTableContext(dimSetMap, context.getTaskDefine().getKey(), context.getTaskDefine().getDataScheme(), dataTable.getKey(), context.getAsyncMonitor().getTaskId());
            if (context.getIntfObjects().containsKey("TempAssistantTable")) {
                TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
                tableContext.setTempAssistantTable(context.getEntityTypeName(), tempTable);
            }
            tableContext.setFloatImpOpt(2);
            List nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
            IMidstoreDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, context.getTaskDefine().getKey(), dataTable.getKey(), nrFieldsArr);
            nrDataSets.put(nrTableCode, bathDataSet);
        }
        try {
            int importRowCount = 0;
            String contition = String.format("DATATIME = '%s'", dePeriodCode);
            MemoryDataSet memoryDataSet = dataExchangeTask.readZBData(deTableCode, contition);
            Metadata metaData = memoryDataSet.getMetadata();
            Iterator it = memoryDataSet.iterator();
            if (!it.hasNext()) {
                logger.info("\u56fa\u5b9a\u6307\u6807\u65e0\u6570\u636e");
            }
            HashMap<String, Integer> deDataCoumnMap = new HashMap<String, Integer>();
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                Column col = metaData.getColumn(i);
                deDataCoumnMap.put(col.getName(), i);
            }
            while (it.hasNext()) {
                DataRow row = (DataRow)it.next();
                String nrOrgCode = null;
                if (deDataCoumnMap.containsKey("MDCODE")) {
                    int columIndex = (Integer)deDataCoumnMap.get("MDCODE");
                    String orgDataCode2 = row.getString(columIndex);
                    if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                        UnitMappingInfo unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                        orgDataCode2 = unitInfo.getUnitCode();
                    }
                    nrOrgCode = orgDataCode2;
                    context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
                }
                HashMap<String, Object[]> nrTableMapRowDatas = new HashMap<String, Object[]>();
                for (int i = 0; i < metaData.getColumnCount(); ++i) {
                    String fieldValue;
                    block63: {
                        String nrZBCode;
                        block65: {
                            block66: {
                                DEZBInfo deField;
                                String nrTableCode;
                                String defieldName;
                                Object fieldObject;
                                block64: {
                                    Set unitList;
                                    int rowIndex;
                                    Column col = metaData.getColumn(i);
                                    fieldObject = row.getValue(i);
                                    fieldValue = row.getString(i);
                                    nrZBCode = defieldName = col.getName();
                                    Object[] rowData = null;
                                    List nrFieldsArr = null;
                                    if ("MDCODE".equalsIgnoreCase(defieldName)) {
                                        String deOrgCode = fieldValue;
                                        String nrOrgCode2 = fieldValue;
                                        if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(deOrgCode)) {
                                            UnitMappingInfo unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(deOrgCode);
                                            nrOrgCode2 = unitInfo.getUnitCode();
                                        }
                                        nrOrgCode = nrOrgCode2;
                                        continue;
                                    }
                                    if ("DATATIME".equalsIgnoreCase(defieldName) || !deFieldMapNrs.containsKey(defieldName) || !nrFieldMapDables.containsKey(nrZBCode = (String)deFieldMapNrs.get(defieldName))) continue;
                                    nrTableCode = (String)nrFieldMapDables.get(nrZBCode);
                                    nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
                                    rowData = (Object[])nrTableMapRowDatas.get(nrTableCode);
                                    if (rowData == null) {
                                        rowData = new Object[nrFieldsArr.size()];
                                        nrTableMapRowDatas.put(nrTableCode, rowData);
                                    }
                                    if ((rowIndex = nrFieldsArr.indexOf(nrZBCode)) < 0 || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode))) continue;
                                    deField = (DEZBInfo)nrFieldMapDes.get(defieldName);
                                    if (deField == null || deField.getDataType() == DEDataType.INTEGER || deField.getDataType() == DEDataType.FLOAT) break block63;
                                    if (deField.getDataType() != DEDataType.DATE) break block64;
                                    if (fieldObject instanceof Date) {
                                        Date date = (Date)fieldObject;
                                        fieldValue = this.dateFormatter.format(date);
                                    } else if (fieldObject instanceof GregorianCalendar) {
                                        GregorianCalendar calendar = (GregorianCalendar)fieldObject;
                                        Date date = calendar.getTime();
                                        fieldValue = this.dateFormatter.format(date);
                                    } else {
                                        fieldValue = null;
                                    }
                                    break block63;
                                }
                                if (deField.getDataType() != DEDataType.FILE) break block65;
                                DEAttachMent attachMent = null;
                                if (fieldObject instanceof DEAttachMent) {
                                    attachMent = (DEAttachMent)fieldObject;
                                } else if (fieldObject != null) {
                                    logger.info("\u6587\u4ef6\u578b\u5b57\u6bb5\u5b58\u5728\u95ee\u9898\uff1a" + defieldName);
                                }
                                if (attachMent == null || attachMent.getData() == null) break block66;
                                try {
                                    DataField dataField;
                                    block68: {
                                        block67: {
                                            dataField = null;
                                            if (!nrDataTableFields.containsKey(nrTableCode)) break block67;
                                            Map fieldCodeList = (Map)nrDataTableFields.get(nrTableCode);
                                            if (!fieldCodeList.containsKey(nrZBCode)) break block68;
                                            dataField = (DataField)fieldCodeList.get(nrZBCode);
                                            break block68;
                                        }
                                        DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(nrTableCode);
                                        if (dataTable != null && (dataField = this.dataSchemeSevice.getDataFieldByTableKeyAndCode(dataTable.getKey(), nrZBCode)) == null) {
                                            List findList = this.dataSchemeSevice.getDataFieldByTableCode(nrTableCode);
                                            for (DataField field : findList) {
                                                if (!nrZBCode.equalsIgnoreCase(field.getCode())) continue;
                                                dataField = field;
                                                break;
                                            }
                                        }
                                    }
                                    MidstoreFileInfo fieldFileInfo = new MidstoreFileInfo();
                                    fieldFileInfo.setDataSchemeKey(context.getTaskDefine().getDataScheme());
                                    if (dataField != null) {
                                        fieldFileInfo.setFieldKey(dataField.getKey());
                                    }
                                    if (tableFormList.containsKey(nrTableCode)) {
                                        fieldFileInfo.setFormKey((String)tableFormList.get(nrTableCode));
                                    }
                                    fieldFileInfo.setFormSchemeKey(context.getFormSchemeKey());
                                    Map<String, DimensionValue> fieldDimSetMap = dimSetMap;
                                    fieldDimSetMap.get(context.getEntityTypeName()).setValue(nrOrgCode);
                                    fieldFileInfo.setDimensionSet(fieldDimSetMap);
                                    fieldFileInfo.setTaskKey(context.getTaskDefine().getKey());
                                    fieldValue = this.attachmentService.saveFileFieldDataToNR(attachMent.getData(), fieldFileInfo);
                                }
                                catch (Exception e) {
                                    logger.error("\u6587\u4ef6\u578b\u5b57\u6bb5" + defieldName + "\u51fa\u9519\uff1a" + e.getMessage(), e);
                                    fieldValue = null;
                                }
                                break block63;
                            }
                            fieldValue = null;
                            break block63;
                        }
                        if (nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                            EnumMappingInfo enumMapping;
                            String baseDataCode = (String)nrFieldMapBaseDatas.get(nrZBCode);
                            if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                                fieldValue = enumMapping.getSrcItemMappings().get(fieldValue).getItemCode();
                            }
                        }
                    }
                    rowData[rowIndex] = fieldValue;
                }
                for (String nrTableCode : nrDataSets.keySet()) {
                    DimensionValueSet dimSet;
                    Set unitList;
                    IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                    List nrFieldsArr = (List)nrTableMapFields.get(nrTableCode);
                    Object[] rowData = (Object[])nrTableMapRowDatas.get(nrTableCode);
                    ArrayList<Object> listRow = new ArrayList<Object>();
                    for (String dimName : dimFields) {
                        int columIndex;
                        DimensionValue dim = dimSetMap.get(dimName);
                        if (dim == null) continue;
                        if ("DATATIME".equalsIgnoreCase(dimName)) {
                            listRow.add(nrPeriodCode);
                            continue;
                        }
                        if ("MD_ORG".equalsIgnoreCase(dimName)) {
                            columIndex = (Integer)deDataCoumnMap.get("MDCODE");
                            String orgDataCode2 = row.getString(columIndex);
                            if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                                UnitMappingInfo unitInfo = context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                                orgDataCode2 = unitInfo.getUnitCode();
                            }
                            listRow.add(orgDataCode2);
                            continue;
                        }
                        columIndex = (Integer)deDataCoumnMap.get(dimName);
                        listRow.add(row.getString(columIndex));
                    }
                    for (int j = dimFields.size(); j < rowData.length; ++j) {
                        Object obj = rowData[j];
                        listRow.add(obj);
                    }
                    if (StringUtils.isEmpty((String)nrOrgCode)) {
                        logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
                        continue;
                    }
                    if (context.getExchangeEnityCodes().size() > 0 && !context.getExchangeEnityCodes().contains(nrOrgCode) || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode)) || (dimSet = bathDataSet.importDatas(listRow)) == null || dimSet.size() <= 0) continue;
                    ++importRowCount;
                }
            }
            if (importRowCount > 0) {
                for (String nrTableCode : nrDataSets.keySet()) {
                    logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u6307\u6807\u8868\u6570\u636e\u63d0\u4ea4\uff0c" + nrTableCode);
                    IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                    bathDataSet.commit();
                }
            }
        }
        catch (Exception e) {
            for (String nrTableCode : nrDataSets.keySet()) {
                IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                if (bathDataSet == null) continue;
                bathDataSet.close();
            }
            logger.error(e.getMessage(), e);
            this.resultService.addUnitErrorInfo(context, context.getWorkResult(), "\u56fa\u5b9a\u6307\u6807\u5f02\u5e38", e.getMessage());
            throw new MidstoreException("\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            for (String nrTableCode : nrDataSets.keySet()) {
                IMidstoreDataSet bathDataSet = (IMidstoreDataSet)nrDataSets.get(nrTableCode);
                if (bathDataSet == null) continue;
                bathDataSet.close();
            }
        }
    }
}

