/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.dataset.IRegionDataSetReader
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.SingleOrderUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 */
package nr.single.data.dataout.internal.service.reader;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.SingleOrderUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionDataSetReaderImpl
implements IRegionDataSetReader {
    private static final Logger logger = LoggerFactory.getLogger(RegionDataSetReaderImpl.class);
    private TaskDataContext context;
    private String netFormCode;
    private IRegionDataSet bathDataSet;
    private ReportRegionDataSetList dataSetList;
    private Map<String, SingleFileFieldInfo> mapNetFieldList;
    private List<FieldData> netFieldList;
    private Map<String, Integer> netFieldIds;
    private Map<String, String> floatKeyCodes;
    private Map<Map<String, String>, String> floatKeyCodeMap;
    private List<String> hasDataZdms;
    private boolean isFMDM;
    private SingleFileFmdmInfo fmTable;
    private List<DataEntityInfo> writeEntityList;
    private Map<String, String> fieldValueMap;
    private ITaskFileExportDataService exportDataSevice;
    private IRuntimeDataSchemeService dataSchemeSevice;
    private IEntityMetaService entityMetaService;
    private int corpKeyFieldId;
    private int corpCodeFieldId;
    private int bizOrderField;
    private int totalRowCount = 0;

    public RegionDataSetReaderImpl(TaskDataContext context, String netFormCode, IRegionDataSet bathDataSet, ReportRegionDataSetList dataSetList, Map<String, SingleFileFieldInfo> mapNetFieldList, List<FieldData> netFieldList, Map<String, Integer> netFieldIds, Map<String, String> floatKeyCodes, Map<Map<String, String>, String> floatKeyCodeMap, List<String> hasDataZdms, boolean isFMDM, SingleFileFmdmInfo fmTable, List<DataEntityInfo> writeEntityList, Map<String, String> fieldValueMap) {
        this.context = context;
        this.netFormCode = netFormCode;
        this.bathDataSet = bathDataSet;
        this.dataSetList = dataSetList;
        this.mapNetFieldList = mapNetFieldList;
        this.netFieldList = netFieldList;
        this.netFieldIds = netFieldIds;
        this.floatKeyCodes = floatKeyCodes;
        this.floatKeyCodeMap = floatKeyCodeMap;
        this.hasDataZdms = hasDataZdms;
        this.isFMDM = isFMDM;
        this.fmTable = fmTable;
        this.writeEntityList = writeEntityList;
        this.fieldValueMap = fieldValueMap;
        this.corpKeyFieldId = -1;
        if (netFieldIds.containsKey("MDCODE")) {
            this.corpKeyFieldId = netFieldIds.get("MDCODE");
        }
        this.corpCodeFieldId = this.corpKeyFieldId;
        this.bizOrderField = -1;
        if (netFieldIds.containsKey("BIZKEYORDER")) {
            this.bizOrderField = netFieldIds.get("BIZKEYORDER");
        }
        this.exportDataSevice = (ITaskFileExportDataService)ApplicationContextRegister.getBean(ITaskFileExportDataService.class);
        this.dataSchemeSevice = (IRuntimeDataSchemeService)ApplicationContextRegister.getBean(IRuntimeDataSchemeService.class);
        this.entityMetaService = (IEntityMetaService)ApplicationContextRegister.getBean(IEntityMetaService.class);
        this.totalRowCount = 0;
    }

    public boolean needRowKey() {
        return true;
    }

    public void start(List<FieldDefine> fieldDefines) {
    }

    /*
     * WARNING - void declaration
     */
    public void readRowData(List<Object> dataRow) {
        try {
            String fieldValue;
            List<FieldData> fields;
            Object[] floatDatas;
            List<Object> floatDatas2 = dataRow;
            if (floatDatas2 == null || floatDatas2.isEmpty()) {
                this.context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + this.netFormCode + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
                return;
            }
            if (!this.bathDataSet.isFloatRegion()) {
                boolean rowIsEmpty = true;
                int fromFieldIndex = 0;
                if (this.bathDataSet.getBizFieldDefList() != null) {
                    fromFieldIndex = this.bathDataSet.getBizFieldDefList().size();
                }
                for (int i = fromFieldIndex; i < floatDatas2.size(); ++i) {
                    String fieldValue2 = "";
                    if (i < floatDatas2.size() && floatDatas2.get(i) != null) {
                        fieldValue2 = floatDatas2.get(i).toString();
                    }
                    if (!StringUtils.isNotEmpty((String)fieldValue2)) continue;
                    rowIsEmpty = false;
                    break;
                }
                if (rowIsEmpty) {
                    return;
                }
            }
            if ((floatDatas = floatDatas2.toArray()).length != (fields = this.netFieldList).size()) {
                this.context.info(logger, "\u6570\u636e\u96c6\u51fa\u9519\uff1a\u6307\u6807\u5b57\u6bb5\u548c\u6570\u636e\u96c6\u5217\u6570\u4e0d\u4e00\u81f4");
            }
            String zdm = null;
            String zdmKey = null;
            String entityKey = null;
            String entityCode = null;
            if (this.corpKeyFieldId >= 0 || this.corpCodeFieldId >= 0) {
                if (this.corpKeyFieldId >= 0) {
                    zdmKey = floatDatas[this.corpKeyFieldId].toString();
                } else if (this.corpCodeFieldId >= 0) {
                    zdmKey = floatDatas[this.corpCodeFieldId].toString();
                }
                DataEntityInfo entity = this.context.getEntityCache().findEntityByCode(zdmKey);
                DataEntityInfo entity2 = this.context.getEntityCache().findEntityByKey(zdmKey);
                if (this.context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                    zdm = (String)this.context.getEntityKeyZdmMap().get(zdmKey);
                } else if (null != entity) {
                    zdm = entity.getSingleZdm();
                } else if (null != entity2) {
                    zdm = entity2.getSingleZdm();
                }
                this.context.setCurrentEntintyKey(zdmKey);
                this.context.setCurrentZDM(zdm);
                if (this.isFMDM) {
                    if (null != entity) {
                        entityKey = entity.getEntityKey();
                        entityCode = entity.getEntityCode();
                        this.writeEntityList.add(entity);
                    } else if (null != entity2) {
                        entityKey = entity2.getEntityKey();
                        entityCode = entity2.getEntityCode();
                        this.writeEntityList.add(entity2);
                    } else {
                        entityKey = zdmKey;
                    }
                } else {
                    entityKey = zdmKey;
                    entityCode = zdmKey;
                }
            }
            if (StringUtils.isNotEmpty(entityCode) && this.context.getDimEntityCache().getEntitySingleDims().size() > 0 && this.context.getDimEntityCache().getEntitySingleDimList().containsKey(entityCode)) {
                Map unitSingleDims = (Map)this.context.getDimEntityCache().getEntitySingleDimList().get(entityCode);
                boolean needFilter = false;
                for (Iterator<String> dimCode : unitSingleDims.keySet()) {
                    String string = ((DimensionValue)unitSingleDims.get(dimCode)).getValue();
                    if (!this.netFieldIds.containsKey(dimCode)) continue;
                    int fieldId = this.netFieldIds.get(dimCode);
                    fieldValue = floatDatas[fieldId].toString();
                    if (!StringUtils.isNotEmpty((String)string) || string.equalsIgnoreCase(fieldValue)) continue;
                    needFilter = true;
                    break;
                }
                if (needFilter) {
                    return;
                }
            }
            if (!this.bathDataSet.isFloatRegion()) {
                this.dataSetList.locateDataRowByZdm(zdm);
            } else {
                this.dataSetList.AppendDataRowByZdm(zdm);
            }
            DataRow singleDataRow = null;
            this.exportDataSevice.setRowValueByFields(this.context, fields, singleDataRow, floatDatas, this.mapNetFieldList, this.dataSetList);
            this.exportDataSevice.setDataAfterExport(this.context, singleDataRow, this.dataSetList, zdm);
            String floatOrder = SingleOrderUtil.newOrder6();
            ((ReportRegionDataSet)this.dataSetList.getDataSetList().get(0)).setFloatOrder(floatOrder);
            if (this.isFMDM) {
                singleDataRow = ((ReportRegionDataSet)this.dataSetList.getDataSetList().get(0)).getCurDataRow();
                Map unitFieldMap = null;
                unitFieldMap = this.context.getMapingCache().getUnitFieldMap().containsKey(zdmKey) ? (Map)this.context.getMapingCache().getUnitFieldMap().get(zdmKey) : new HashMap();
                for (String string : unitFieldMap.keySet()) {
                    singleDataRow.setValue(string, unitFieldMap.get(string));
                }
                for (String string : this.fieldValueMap.keySet()) {
                    Object value = singleDataRow.getValue(string);
                    if (null != value) {
                        fieldValue = value.toString();
                        if (this.context.getMapingCache().isMapConfig() && this.context.getMapingCache().getFmdmInfo2() != null && string.equals(this.context.getMapingCache().getFmdmInfo2().getPeriodField()) && StringUtils.isEmpty((String)fieldValue) && StringUtils.isNotEmpty((String)this.context.getNetPeriodCode())) {
                            fieldValue = this.context.getMapCurrentPeriod();
                        }
                        this.fieldValueMap.put(string, fieldValue);
                        continue;
                    }
                    this.fieldValueMap.put(string, "");
                }
                DataEntityInfo entity = this.context.getEntityCache().findEntityByCode(zdmKey);
                if (null == entity) {
                    entity = this.context.getEntityCache().findEntityByKey(zdmKey);
                }
                if (this.context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                    zdm = (String)this.context.getEntityKeyZdmMap().get(zdmKey);
                } else if (null != entity) {
                    zdm = entity.getSingleZdm();
                } else if (this.fmTable != null) {
                    for (String code : this.fmTable.getZdmFieldCodes()) {
                        fieldValue = this.fieldValueMap.get(code);
                        zdm = zdm + fieldValue;
                    }
                    if (StringUtils.isNotEmpty((String)zdm) && zdm.length() > this.fmTable.getZdmLength()) {
                        zdm = zdm.substring(0, this.fmTable.getZdmLength());
                    }
                    if (!this.context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                        this.context.getEntityKeyZdmMap().put(zdmKey, zdm);
                    }
                    if (!this.context.getEntityZdmKeyMap().containsKey(zdm)) {
                        this.context.getEntityZdmKeyMap().put(zdm, zdmKey);
                    }
                }
                this.context.setCurrentZDM(zdm);
                if (singleDataRow.getValue("SYS_FJD") == null || StringUtils.isEmpty((String)singleDataRow.getValue("SYS_FJD").toString())) {
                    void var13_28;
                    Object var13_25 = null;
                    if (this.context.getEntityKeyParentMap().containsKey(zdmKey)) {
                        String string = (String)this.context.getEntityKeyParentMap().get(zdmKey);
                    } else if (entity != null) {
                        String string = entity.getEntityParentKey();
                    }
                    if (StringUtils.isNotEmpty((String)var13_28)) {
                        String fjdCode;
                        DataEntityInfo entity2 = this.context.getEntityCache().findEntityByCode((String)var13_28);
                        if (this.context.getEntityKeyZdmMap().containsKey(var13_28)) {
                            fjdCode = (String)this.context.getEntityKeyZdmMap().get(var13_28);
                            singleDataRow.setValue("SYS_FJD", (Object)fjdCode);
                        } else if (null != entity2) {
                            fjdCode = entity2.getSingleZdm();
                            singleDataRow.setValue("SYS_FJD", (Object)fjdCode);
                        }
                    }
                }
                if (StringUtils.isNotEmpty((String)this.fmTable.getDWMCField()) && (singleDataRow.getValue(this.fmTable.getDWMCField()) == null || StringUtils.isEmpty((String)singleDataRow.getValue(this.fmTable.getDWMCField()).toString())) && null != entity) {
                    singleDataRow.setValue(this.fmTable.getDWMCField(), (Object)entity.getEntityTitle());
                }
                if (StringUtils.isNotEmpty((String)this.fmTable.getPeriodField())) {
                    singleDataRow.setValue(this.fmTable.getPeriodField(), (Object)this.context.getMapCurrentPeriod());
                }
                if (StringUtils.isNotEmpty((String)zdm)) {
                    Map map = this.context.getEntityCache().getFieldValueByZdm(zdm);
                    for (String mapfieldCode : map.keySet()) {
                        String mapFieldValue = (String)map.get(mapfieldCode);
                        if (!StringUtils.isNotEmpty((String)mapFieldValue) || !StringUtils.isEmpty((String)singleDataRow.getValueString(mapfieldCode))) continue;
                        singleDataRow.setValue(mapfieldCode, (Object)mapFieldValue);
                    }
                }
                singleDataRow.setValue("SYS_ZDM", (Object)zdm);
            } else if (this.bathDataSet.isFloatRegion()) {
                ReportRegionDataSet singleRegionSet = (ReportRegionDataSet)this.dataSetList.getDataSetList().get(0);
                String floatCode = singleRegionSet.getFloatCodeValues();
                String string = floatCode + "," + floatOrder;
                String rowkey = "";
                rowkey = this.bizOrderField >= 0 ? floatDatas[this.bizOrderField].toString() : floatCode;
                this.floatKeyCodes.put(rowkey, string);
                Map floatCodeMap = singleRegionSet.getFloatCodeValueMap();
                HashMap floatCodeMap2 = new HashMap();
                Iterator iterator = floatCodeMap.keySet().iterator();
                while (iterator.hasNext()) {
                    IEntityDefine entityDefine;
                    DataField dataField;
                    SingleFileFieldInfo field;
                    String fieldCode;
                    String dimName = fieldCode = (String)iterator.next();
                    if (singleRegionSet.getMapFieldList().containsKey(fieldCode) && singleRegionSet.getMapFieldList() != null && singleRegionSet.getMapFieldList().containsKey(fieldCode) && StringUtils.isNotEmpty((String)(field = (SingleFileFieldInfo)singleRegionSet.getMapFieldList().get(fieldCode)).getNetFieldKey()) && (dataField = this.dataSchemeSevice.getDataField(field.getNetFieldKey())) != null && StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey()) && (entityDefine = this.entityMetaService.queryEntity(dataField.getRefDataEntityKey())) != null) {
                        dimName = entityDefine.getDimensionName();
                    }
                    floatCodeMap2.put(dimName, floatCodeMap.get(fieldCode));
                }
                this.floatKeyCodeMap.put(floatCodeMap2, string);
            }
            this.dataSetList.setAllFieldValue("SYS_ZDM", zdm);
            this.dataSetList.saveRowData();
            this.hasDataZdms.add(zdm);
            ++this.totalRowCount;
        }
        catch (Exception e) {
            this.context.error(logger, e.getMessage(), (Throwable)e);
        }
    }

    public void finish() {
    }

    public int getTotalRowCount() {
        return this.totalRowCount;
    }
}

