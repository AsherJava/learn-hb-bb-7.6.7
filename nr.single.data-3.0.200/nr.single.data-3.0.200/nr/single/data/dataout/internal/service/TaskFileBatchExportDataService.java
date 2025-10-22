/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.IRegionDataSetReader
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 *  nr.single.map.data.internal.service.TaskDataServiceNewImpl
 *  nr.single.map.data.service.SingleDimissionServcie
 */
package nr.single.data.dataout.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.datain.internal.service.TaskFileBatchImportDataService;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import nr.single.data.dataout.internal.service.reader.RegionDataSetReaderImpl;
import nr.single.data.dataout.service.ITaskFileBatchExportDataService;
import nr.single.data.dataout.service.ITaskFileBatchExportFMDMService;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import nr.single.map.data.internal.service.TaskDataServiceNewImpl;
import nr.single.map.data.service.SingleDimissionServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskFileBatchExportDataService
implements ITaskFileBatchExportDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileBatchExportDataService.class);
    @Autowired
    private ITaskFileExportDataService exportDataSevice;
    @Autowired
    private ITaskFileBatchExportFMDMService fmdmDataService;
    @Autowired
    private SingleDimissionServcie singleDimService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Value(value="${jiuqi.nr.jio.exportByReader:true}")
    private boolean jioExportByReader;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void batchOperFormData(TaskDataContext context, String filePath, String netFormKey, Map<String, DimensionValue> dimMapNoUnit, List<String> corpList) throws Exception {
        boolean isFMDM;
        ITaskFileBatchImportDataService batchImportService = (ITaskFileBatchImportDataService)context.getIntfObjects().get("batchImportService");
        Map formSeeUnits = (Map)context.getIntfObjects().get("formSeeUnits");
        if (StringUtils.isEmpty((String)netFormKey)) {
            return;
        }
        FormDefine formDefine = batchImportService.getViewController().queryFormById(netFormKey);
        String netFormCode = formDefine.getFormCode();
        SingleFileTableInfo table = TaskFileDataOperateUtil.getSingleTableInfo(context, netFormKey, netFormCode);
        boolean bl = isFMDM = formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM;
        if (!context.getMapingCache().getNetFieldMap().containsKey(netFormCode)) {
            if (!isFMDM) {
                context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u672a\u505a\u6620\u5c04\u4e0d\u5bfc\u51fa\uff1a");
                return;
            }
            if (table == null) {
                table = TaskFileDataOperateUtil.getSingleFMDMTable(context);
            }
            context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u672a\u505a\u6620\u5c04.");
        } else if (isFMDM && !"FMDM".equalsIgnoreCase(table.getSingleTableCode())) {
            context.info(logger, "\u662f\u5c01\u9762\u8868\u5355\uff0c\u4fee\u6b63\u4e3a\u5355\u673a\u5c01\u9762\u4ee3\u7801\u8868");
            table = TaskFileDataOperateUtil.getSingleFMDMTable(context);
        }
        List formSeeUnitList = null;
        if (!isFMDM && (formSeeUnitList = (List)formSeeUnits.get(netFormKey)) == null) {
            context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u65e0\u6743\u9650\u8bfb\u53d6\u4e0d\u5bfc\u51fa\uff1a");
            return;
        }
        StringBuilder sb = new StringBuilder();
        String unitKeys = "";
        for (String unitKey : corpList) {
            if (!isFMDM && formSeeUnitList != null && !formSeeUnitList.contains(unitKey)) {
                context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u5355\u4f4d\uff1a" + unitKey + "\u65e0\u6743\u9650\u8bfb\u53d6\u4e0d\u5bfc\u51fa\uff1a");
                continue;
            }
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(unitKey);
        }
        unitKeys = sb.toString();
        if (StringUtils.isEmpty((String)unitKeys)) {
            return;
        }
        Map<String, DimensionValue> dimensionValueMap = batchImportService.getNewDimensionSet(dimMapNoUnit);
        DimensionValue newDim = new DimensionValue();
        newDim.setName(context.getEntityCompanyType());
        newDim.setValue(unitKeys);
        dimensionValueMap.put(context.getEntityCompanyType(), newDim);
        if (context.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
            this.singleDimService.setDimensionByUnitSingleDim(context, dimensionValueMap);
        }
        List<RegionData> dataRegions = batchImportService.getFormRegions(netFormKey);
        RegionData fixRegion = null;
        ArrayList<RegionData> FloatReigonDataList = new ArrayList<RegionData>();
        for (RegionData aRegion : dataRegions) {
            if (aRegion.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                fixRegion = aRegion;
                continue;
            }
            FloatReigonDataList.add(aRegion);
        }
        if (FloatReigonDataList.size() > 1) {
            Collections.sort(FloatReigonDataList, new Comparator<RegionData>(){

                @Override
                public int compare(RegionData o1, RegionData o2) {
                    int comValue = 0;
                    if (o1.getRegionTop() > o2.getRegionTop()) {
                        comValue = 1;
                    } else if (o1.getRegionTop() < o2.getRegionTop()) {
                        comValue = -1;
                    }
                    return comValue;
                }
            });
        }
        SingleFileRegionInfo singleRegion = null;
        if (null != table && null != table.getRegion()) {
            singleRegion = table.getRegion();
        }
        if (isFMDM) {
            this.fmdmDataService.batchOperRegionData(context, filePath, netFormKey, netFormCode, dimensionValueMap, fixRegion, table, singleRegion);
        } else {
            if (table != null && "FMDM".equalsIgnoreCase(table.getSingleTableCode())) {
                this.fmdmDataService.batchOperRegionData(context, filePath, netFormKey, netFormCode, dimensionValueMap, fixRegion, table, singleRegion);
            }
            this.batchOperRegionData(context, filePath, netFormKey, netFormCode, dimensionValueMap, fixRegion, table, singleRegion);
        }
        int k = 0;
        while (k < FloatReigonDataList.size()) {
            RegionData region = (RegionData)FloatReigonDataList.get(k);
            singleRegion = null;
            if (null != table && null != table.getRegion() && null != table.getRegion().getSubRegions()) {
                if (k < table.getRegion().getSubRegions().size()) {
                    singleRegion = (SingleFileRegionInfo)table.getRegion().getSubRegions().get(k);
                } else {
                    singleRegion = table.getRegion();
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u662f\u6d6e\u52a8\u884c\u8f6c\u56fa\u5b9a\u8868\uff0c\u8bf7\u68c0\u67e5");
                }
            }
            this.batchOperRegionData(context, filePath, netFormKey, netFormCode, dimensionValueMap, region, table, singleRegion);
            ++k;
        }
        return;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchOperRegionData(TaskDataContext context, String filePath, String netformKey, String netFormCode, Map<String, DimensionValue> dimMap, RegionData regionData, SingleFileTableInfo table, SingleFileRegionInfo singleRegion) throws Exception {
        ReportRegionDataSetList olddataSetList;
        TaskFileBatchImportDataService batchImportService = (TaskFileBatchImportDataService)context.getIntfObjects().get("batchImportService");
        ArrayList<FieldData> netFieldList = new ArrayList<FieldData>();
        LinkedHashMap<String, Integer> netFieldIds = new LinkedHashMap<String, Integer>();
        SingleFileFmdmInfo fmTable = null;
        FormDefine form = batchImportService.getViewController().queryFormById(netformKey);
        boolean isFMDM = form.getFormType() == FormType.FORM_TYPE_NEWFMDM;
        int corpKeyFieldId = -1;
        int corpCodeFieldId = -1;
        int periodFieldId = -1;
        int bizOrderField = -1;
        TableContext tableContext = batchImportService.getTableContex(dimMap, context.getTaskKey(), context.getFormSchemeKey(), netformKey, context.getSyncTaskID());
        tableContext.setExportBizkeyorder(true);
        tableContext.setExpEntryFields(ExpViewFields.KEY);
        if (isFMDM) {
            ArrayList<String> sortFields = new ArrayList<String>();
            sortFields.add(Consts.EntityField.ENTITY_FIELD_ORDINAL.fieldKey);
            tableContext.setSortFields(sortFields);
        }
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            tableContext.setTempAssistantTable(context.getEntityCompanyType(), tempTable);
        }
        if (StringUtils.isNotEmpty((String)context.getMeasureCode())) {
            tableContext.setMeasure(context.getMeasureCode());
            if (StringUtils.isNotEmpty((String)context.getMeasureDecimal())) {
                tableContext.setDecimal(context.getMeasureDecimal());
            }
        }
        int totalRowCount = 0;
        IRegionDataSet bathDataSet = batchImportService.getBatchExportRegionDataSet(tableContext, regionData);
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
        for (ExportFieldDefine netfield : bathDataSet.getFieldDataList()) {
            FieldData netFieldData = new FieldData();
            Object fieldCode = netfield.getCode();
            String tableName = netFormCode;
            int idPos = ((String)fieldCode).indexOf(".");
            if (idPos >= 0) {
                tableName = ((String)fieldCode).substring(0, idPos);
                fieldCode = ((String)fieldCode).substring(idPos + 1, ((String)fieldCode).length());
            }
            netFieldData.setFieldCode((String)fieldCode);
            netFieldData.setFieldName((String)fieldCode);
            netFieldData.setTableName(netfield.getTableCode());
            netFieldData.setFieldType(netfield.getType());
            netFieldData.setFieldValueType(netfield.getValueType());
            netFieldData.setRegionKey(regionData.getKey());
            netFieldList.add(netFieldData);
            netFieldIds.put((String)fieldCode, netFieldList.size() - 1);
            if (corpKeyFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_KEY.getValue()) {
                corpKeyFieldId = netFieldList.size() - 1;
                continue;
            }
            if (corpCodeFieldId < 0 && netfield.getValueType() == FieldValueType.FIELD_VALUE_UNIT_CODE.getValue()) {
                corpCodeFieldId = netFieldList.size() - 1;
                continue;
            }
            if (netfield.getValueType() == FieldValueType.FIELD_VALUE_PERIOD_VALUE.getValue()) {
                periodFieldId = netFieldList.size() - 1;
                continue;
            }
            if ("DATATIME".equalsIgnoreCase((String)fieldCode)) {
                periodFieldId = netFieldList.size() - 1;
                continue;
            }
            if (netfield.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER.getValue()) {
                bizOrderField = netFieldList.size() - 1;
                continue;
            }
            if ("BIZKEYORDER".equalsIgnoreCase((String)fieldCode)) {
                bizOrderField = netFieldList.size() - 1;
                continue;
            }
            if (corpKeyFieldId < 0 && dwField != null && StringUtils.isNotEmpty((String)fieldCode) && ((String)fieldCode).equalsIgnoreCase(dwField.getCode())) {
                corpKeyFieldId = netFieldList.size() - 1;
                continue;
            }
            if (corpKeyFieldId < 0 && dwField2 != null && StringUtils.isNotEmpty((String)fieldCode) && ((String)fieldCode).equalsIgnoreCase(dwField2.getCode())) {
                corpKeyFieldId = netFieldList.size() - 1;
                continue;
            }
            if ("MDCODE".equalsIgnoreCase((String)fieldCode)) {
                corpKeyFieldId = netFieldList.size() - 1;
                continue;
            }
            if (!"MDCODE".equalsIgnoreCase((String)fieldCode) || corpCodeFieldId >= 0) continue;
            corpCodeFieldId = netFieldList.size() - 1;
        }
        HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
        int floatingRow = regionData.getRegionTop();
        if (regionData.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            floatingRow = -1;
        }
        if (null != singleRegion) {
            for (SingleFileFieldInfo field : singleRegion.getFields()) {
                mapNetFieldList.put(field.getNetTableCode() + "." + field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getTableCode() + "." + field.getFieldCode(), field);
            }
        }
        int downCorpCount = 0;
        downCorpCount = context.getDownloadEntityKeys() != null ? context.getDownloadEntityKeys().size() : context.getEntityCache().getEntityList().size();
        ReportRegionDataSetList dataSetList = olddataSetList = TaskFileDataOperateUtil.getRegionDataSetList(context, filePath, netFormCode, netFieldList, floatingRow, netFieldList.size() * downCorpCount > 1000000);
        if (olddataSetList.isVirtualFloat()) {
            dataSetList = olddataSetList.getVirtualDatasets();
            mapNetFieldList.clear();
            mapSingleFieldList.clear();
            for (Iterator<Object> obj : dataSetList.getFieldMap().values()) {
                SingleFileFieldInfo field = (SingleFileFieldInfo)obj;
                mapNetFieldList.put(field.getNetTableCode() + "." + field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getTableCode() + "." + field.getFieldCode(), field);
            }
        }
        try {
            boolean hasData;
            RegionDataSetReaderImpl regionReader2;
            HashMap<String, String> fieldValueMap = new HashMap<String, String>();
            if (isFMDM) {
                fmTable = (SingleFileFmdmInfo)table;
                for (String code : fmTable.getZdmFieldCodes()) {
                    fieldValueMap.put(code, "");
                }
                if (mapSingleFieldList.containsKey(fmTable.getPeriodField()) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                    SingleFileFieldInfo field = (SingleFileFieldInfo)mapSingleFieldList.get(fmTable.getPeriodField());
                    String fieldValue = context.getNetPeriodCode();
                    TaskDataServiceNewImpl taskService2 = new TaskDataServiceNewImpl();
                    String newFieldValue = taskService2.getSinglePeriodCode(context, context.getNetPeriodCode(), field.getFieldSize());
                    fieldValue = StringUtils.isEmpty((String)newFieldValue) ? fieldValue.substring(fieldValue.length() - field.getFieldSize(), fieldValue.length()) : newFieldValue;
                    fieldValueMap.put(field.getFieldCode(), fieldValue);
                    context.setMapCurrentPeriod(fieldValue);
                }
            } else {
                fmTable = null;
                if (context.getMapingCache().isMapConfig()) {
                    fmTable = context.getMapingCache().getFmdmInfo2();
                }
            }
            boolean hasMapFields = true;
            if (dataSetList == null || dataSetList.getDataSetList().size() <= 0) {
                hasMapFields = false;
                if (isFMDM) {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u65e0\u6620\u5c04\u7684\u5355\u673a\u7248\u6307\u6807");
                    dataSetList = TaskFileDataOperateUtil.getRegionDataSetListByTable(context, filePath, "FMDM", -1, dataSetList);
                } else {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6570\u636e\u533a\u57df" + regionData.getRegionTop() + "\u65e0\u6620\u5c04\u7684\u5355\u673a\u7248\u6307\u6807,\u4e0d\u5bfc\u51fa");
                    return;
                }
            }
            if (!hasMapFields) {
                context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u65e0\u6620\u5c04\u5355\u673a\u7248\u6307\u6807\uff0c\u8bf7\u68c0\u67e5");
                return;
            }
            StringBuilder noMapFields = new StringBuilder();
            for (int i = 0; i < netFieldList.size(); ++i) {
                String fieldName = ((FieldData)netFieldList.get(i)).getFieldName();
                String fieldFlag = ((FieldData)netFieldList.get(i)).getTableName() + "." + fieldName;
                if (dataSetList.getFieldDataSetMap().containsKey(fieldFlag) || Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equalsIgnoreCase(fieldName) || context.getEntityCompanyType().equalsIgnoreCase(fieldName)) continue;
                noMapFields.append(fieldFlag).append(",");
            }
            if (noMapFields.length() > 0) {
                context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6570\u636e\u533a\u57df" + regionData.getRegionTop() + "\u6307\u6807\u65e0\u6620\u5c04\uff1a" + noMapFields.toString());
            }
            LinkedHashMap<String, String> floatKeyCodes = new LinkedHashMap<String, String>();
            LinkedHashMap<Map<String, String>, String> floatKeyCodeMap = new LinkedHashMap<Map<String, String>, String>();
            ArrayList<String> hasDataZdms = new ArrayList<String>();
            ArrayList<DataEntityInfo> writeEntityList = new ArrayList<DataEntityInfo>();
            RegionDataSetReaderImpl regionReader = new RegionDataSetReaderImpl(context, netFormCode, bathDataSet, dataSetList, mapNetFieldList, netFieldList, netFieldIds, floatKeyCodes, floatKeyCodeMap, hasDataZdms, isFMDM, fmTable, writeEntityList, fieldValueMap);
            if (this.jioExportByReader) {
                bathDataSet.queryToDataRowReader((IRegionDataSetReader)regionReader);
                regionReader2 = regionReader;
                totalRowCount = regionReader2.getTotalRowCount();
            } else {
                while (bathDataSet.hasNext()) {
                    List floatDatas2 = (List)bathDataSet.next();
                    if (floatDatas2.size() == 0) {
                        context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                        break;
                    }
                    regionReader.readRowData(floatDatas2);
                }
                if ((totalRowCount = bathDataSet.getTotalCount()) == 0) {
                    regionReader2 = regionReader;
                    totalRowCount = regionReader2.getTotalRowCount();
                }
            }
            boolean bl = hasData = !hasDataZdms.isEmpty();
            if (isFMDM) {
                context.setSuccessCorpCount(0);
                if (context.getDownloadEntityKeyZdmMap().size() != writeEntityList.size()) {
                    HashMap writeEntityCodes = new HashMap();
                    HashMap<String, DataEntityInfo> writeEntityKeys = new HashMap<String, DataEntityInfo>();
                    for (DataEntityInfo entity : writeEntityList) {
                        writeEntityCodes.put(entity.getEntityCode(), entity);
                        writeEntityKeys.put(entity.getEntityKey(), entity);
                    }
                    for (String zdmKey : context.getDownloadEntityKeyZdmMap().keySet()) {
                        if (writeEntityCodes.containsKey(zdmKey) || writeEntityKeys.containsKey(zdmKey)) continue;
                        DataEntityInfo entity = context.getEntityCache().findEntityByKey(zdmKey);
                        if (entity == null) {
                            entity = context.getEntityCache().findEntityByCode(zdmKey);
                        }
                        if (entity == null) continue;
                        String zdm = null;
                        if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                            zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                        } else if (null != entity) {
                            zdm = entity.getSingleZdm();
                        }
                        if (StringUtils.isEmpty(zdm)) continue;
                        dataSetList.locateDataRowByZdm(zdm);
                        DataRow dataRow = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getCurDataRow();
                        Map unitFieldMap = null;
                        unitFieldMap = context.getMapingCache().getUnitFieldMap().containsKey(zdmKey) ? (Map)context.getMapingCache().getUnitFieldMap().get(zdmKey) : new HashMap();
                        for (String fieldName : unitFieldMap.keySet()) {
                            dataRow.setValue(fieldName, unitFieldMap.get(fieldName));
                        }
                        for (String fieldName : fieldValueMap.keySet()) {
                            Object value = dataRow.getValue(fieldName);
                            if (null != value) {
                                String fieldValue = value.toString();
                                if (context.getMapingCache().isMapConfig() && fieldName.equals(fmTable.getPeriodField()) && StringUtils.isEmpty((String)fieldValue) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                                    fieldValue = context.getMapCurrentPeriod();
                                }
                                fieldValueMap.put(fieldName, fieldValue);
                                continue;
                            }
                            fieldValueMap.put(fieldName, "");
                        }
                        context.setCurrentEntintyKey(zdmKey);
                        context.setCurrentZDM(zdm);
                        if (dataRow.getValue("SYS_FJD") == null || StringUtils.isEmpty((String)dataRow.getValue("SYS_FJD").toString())) {
                            String fjdKey = null;
                            if (context.getEntityKeyParentMap().containsKey(zdmKey)) {
                                fjdKey = (String)context.getEntityKeyParentMap().get(zdmKey);
                            } else if (entity != null) {
                                fjdKey = entity.getEntityParentKey();
                            }
                            if (StringUtils.isNotEmpty(fjdKey)) {
                                String fjdCode;
                                DataEntityInfo entity2 = context.getEntityCache().findEntityByCode(fjdKey);
                                if (context.getEntityKeyZdmMap().containsKey(fjdKey)) {
                                    fjdCode = (String)context.getEntityKeyZdmMap().get(fjdKey);
                                    dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                } else if (null != entity2) {
                                    fjdCode = entity2.getSingleZdm();
                                    dataRow.setValue("SYS_FJD", (Object)fjdCode);
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty((String)fmTable.getPeriodField())) {
                            dataRow.setValue(fmTable.getPeriodField(), (Object)context.getMapCurrentPeriod());
                        }
                        if (StringUtils.isNotEmpty((String)fmTable.getDWMCField()) && (dataRow.getValue(fmTable.getDWMCField()) == null || StringUtils.isEmpty((String)dataRow.getValue(fmTable.getDWMCField()).toString())) && null != entity) {
                            dataRow.setValue(fmTable.getDWMCField(), (Object)entity.getEntityTitle());
                        }
                        if (StringUtils.isNotEmpty((String)zdm)) {
                            Map mapFieldvalues = context.getEntityCache().getFieldValueByZdm(zdm);
                            for (String mapfieldCode : mapFieldvalues.keySet()) {
                                String mapFieldValue = (String)mapFieldvalues.get(mapfieldCode);
                                if (!StringUtils.isNotEmpty((String)mapFieldValue) || !StringUtils.isEmpty((String)dataRow.getValueString(mapfieldCode))) continue;
                                dataRow.setValue(mapfieldCode, (Object)mapFieldValue);
                            }
                        }
                        dataRow.setValue("SYS_ZDM", (Object)zdm);
                        dataSetList.setAllFieldValue("SYS_ZDM", zdm);
                        dataSetList.saveRowData();
                        hasData = true;
                    }
                }
            }
            if (hasData) {
                if (olddataSetList.isVirtualFloat()) {
                    dataSetList.saveData();
                    TaskFileDataOperateUtil.ConvertDataSetListFromvirtual(olddataSetList);
                    dataSetList = olddataSetList;
                }
                dataSetList.saveData();
                if (olddataSetList.isVirtualFloat()) {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u865a\u62df\u6d6e\u52a8\u884c");
                } else if (regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                    for (ReportRegionDataSet dataSet : dataSetList.getDataSetList()) {
                        ReportRegionDataSet parentDataSet = dataSet.getParentDataSet();
                        if (null == parentDataSet) continue;
                        boolean hasNewData = false;
                        for (String aZdm : hasDataZdms) {
                            parentDataSet.locateDataRowByZdm(aZdm);
                            boolean isNewRow = parentDataSet.getIsNewRow();
                            if (isNewRow) {
                                parentDataSet.saveRowData();
                                hasNewData = true;
                                continue;
                            }
                            if (parentDataSet.getDataSet() == null || parentDataSet.getDataSet().isHasLoadAllRec()) continue;
                            parentDataSet.saveRowData();
                        }
                        if (!hasNewData) continue;
                        parentDataSet.saveData();
                    }
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + ",\u6d6e\u52a8\u533a\u57df\uff1a" + regionData.getRegionTop() + "\u5bfc\u51fa\u6570\u636e\u884c\u6570\uff1a" + totalRowCount);
                    this.exportDataSevice.updateFormCheckInfo(context, netFormCode, floatKeyCodes, floatKeyCodeMap);
                } else if (isFMDM) {
                    int downLoadCount = 0;
                    if (dataSetList.getDataSetList().size() > 0) {
                        downLoadCount = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getDataSet().getDataRowCount();
                    }
                    context.setSuccessCorpCount(downLoadCount);
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u6570\u636e\u96c6\u8bb0\u5f55\u6570" + totalRowCount + "\uff0c\u5bfc\u51fa\u5230JIO\u6587\u4ef6\u4e2d\u7684\u5355\u4f4d\u4e2a\u6570" + downLoadCount);
                } else {
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u5bfc\u51fa\u6570\u636e\u884c\u6570\uff1a" + totalRowCount);
                }
            } else if (isFMDM) {
                context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u5bf9\u5e94\u5c01\u9762\u4ee3\u7801\u6570\u636e\u96c6\u65e0\u6570\u636e\uff0c\u8bf7\u68c0\u67e5");
            }
        }
        finally {
            if (dataSetList != null) {
                dataSetList.close();
                dataSetList = null;
                olddataSetList = null;
            }
        }
    }
}

