/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParseException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.dataset.IRegionExportDataSet
 *  com.jiuqi.nr.jtable.dataset.IReportExportDataSet
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.DataChkInfo
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleOrderUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 *  nr.single.map.data.internal.service.TaskDataServiceNewImpl
 */
package nr.single.data.dataout.internal.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.data.util.TaskFileDataCommonService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.data.util.service.ISingleAttachmentService;
import nr.single.map.data.DataChkInfo;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleOrderUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import nr.single.map.data.internal.service.TaskDataServiceNewImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileExportDataService
implements ITaskFileExportDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileExportDataService.class);
    @Autowired
    private TaskFileDataCommonService commonImportService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    @Override
    public void dataOper(TaskDataContext context, IReportExportDataSet reportExportData, String filePath) throws Exception {
        List dataRegions = reportExportData.getDataRegionList();
        ArrayList<String> floatKeys = new ArrayList<String>();
        IRegionExportDataSet fixRegionDataSet = null;
        ArrayList<IRegionExportDataSet> floatRegionDataSet = new ArrayList<IRegionExportDataSet>();
        ArrayList<RegionData> FloatReigonDataList = new ArrayList<RegionData>();
        for (RegionData aRegion : dataRegions) {
            if (aRegion.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                fixRegionDataSet = reportExportData.getRegionExportDataSet(aRegion, true);
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
        for (RegionData region : FloatReigonDataList) {
            floatKeys.add(region.getKey().toString());
            floatRegionDataSet.add(reportExportData.getRegionExportDataSet(region, true, true));
        }
        SingleFileTableInfo table = null;
        if (null != reportExportData.getFormData()) {
            String netFormCode = reportExportData.getFormData().getCode();
            String netFormKey = reportExportData.getFormData().getKey();
            table = TaskFileDataOperateUtil.getSingleTableInfo(context, netFormKey, netFormCode);
            boolean isFMDM = netFormKey.equalsIgnoreCase(context.getFmdmFormKey());
            if (!context.getMapingCache().getNetFieldMap().containsKey(netFormCode)) {
                if (isFMDM) {
                    if (table == null && (table = (SingleFileTableInfo)context.getMapingCache().getMapTables().get("FMDM")) == null) {
                        table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get("FMDM");
                    }
                    context.info(logger, "\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u672a\u505a\u6620\u5c04.");
                } else {
                    context.info(logger, "\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u672a\u505a\u6620\u5c04\u4e0d\u5bfc\u51fa\uff1a");
                    return;
                }
            }
        }
        this.exportFixRegion(context, reportExportData, fixRegionDataSet, filePath, table);
        this.exportFloatRegion(context, reportExportData, floatRegionDataSet, filePath, table);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportFixRegion(TaskDataContext context, IReportExportDataSet reportExportData, IRegionExportDataSet fixRegionDataSet, String filePath, SingleFileTableInfo table) throws Exception {
        String newEntityId;
        if (null == fixRegionDataSet) {
            return;
        }
        List oldFieldDefines = fixRegionDataSet.getFieldDataList();
        List oldFieldLinks = fixRegionDataSet.getLinkDataList();
        List<FieldData> fieldDefines = this.commonImportService.copyFieldsFromLink(oldFieldDefines, oldFieldLinks);
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)(newEntityId = dsContext.getContextEntityId()))) {
            String oldEntityId = context.getFormScheme().getDw();
            IEntityDefine oldDefine = this.entityMetaService.queryEntity(oldEntityId);
            String oldEntityCode = oldDefine.getCode();
            IEntityDefine newDefine = this.entityMetaService.queryEntity(newEntityId);
            String newEntityCode = newDefine.getCode();
            for (FieldData field : fieldDefines) {
                if (!StringUtils.isNotEmpty((String)newEntityCode) || !newEntityCode.equalsIgnoreCase(field.getTableName())) continue;
                field.setTableName(oldEntityCode);
            }
        }
        ReportRegionDataSetList dataSetList = TaskFileDataOperateUtil.getRegionDataSetList(context, filePath, reportExportData.getFormData().getCode(), fieldDefines, -1);
        try {
            boolean isSingleFMDM;
            boolean bl = isSingleFMDM = table != null && "FMDM".equalsIgnoreCase(table.getSingleTableCode());
            if (dataSetList == null || dataSetList.getDataSetList().size() <= 0) {
                if (isSingleFMDM) {
                    dataSetList = TaskFileDataOperateUtil.getRegionDataSetListByTable(context, filePath, "FMDM", -1, dataSetList);
                } else {
                    context.info(logger, "\u8be5\u8868\u56fa\u5b9a\u533a\u57df\u6ca1\u6709\u6620\u5c04");
                    return;
                }
            }
            HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
            HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
            Map singleFieldMaps = context.getMapingCache().getSingleFieldMap();
            Map singleFieldMap = null;
            Map unitFieldMap = null;
            SingleFileFmdmInfo fmTable = null;
            if (null != table) {
                if (singleFieldMaps.containsKey(table.getSingleTableCode())) {
                    singleFieldMap = (Map)singleFieldMaps.get(table.getSingleTableCode());
                }
                for (SingleFileFieldInfo field : table.getRegion().getFields()) {
                    String fieldFlag = field.getTableCode() + "." + field.getFieldCode();
                    if (null == singleFieldMap || !singleFieldMap.containsKey(fieldFlag)) continue;
                    SingleFileFieldInfo field2 = (SingleFileFieldInfo)singleFieldMap.get(fieldFlag);
                    mapSingleFieldList.put(field2.getTableCode() + "." + field2.getFieldCode(), field2);
                    mapNetFieldList.put(field2.getNetTableCode() + "." + field2.getNetFieldCode(), field2);
                }
            }
            MemoryDataSet dataRowSet = null;
            Object[] fixDatas = null;
            while (fixRegionDataSet.hasNext()) {
                dataRowSet = (MemoryDataSet)fixRegionDataSet.next();
            }
            if (dataRowSet.size() > 0) {
                fixDatas = dataRowSet.getBuffer(0);
            }
            if (fixDatas != null) {
                boolean isFMDM = reportExportData.getFormData().getKey().equalsIgnoreCase(context.getFmdmFormKey());
                HashMap<String, String> fieldValueMap = new HashMap<String, String>();
                if (isFMDM || isSingleFMDM) {
                    fmTable = (SingleFileFmdmInfo)table;
                    for (String code : fmTable.getZdmFieldCodes()) {
                        fieldValueMap.put(code, "");
                    }
                    if (mapSingleFieldList.containsKey(fmTable.getPeriodField()) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                        SingleFileFieldInfo field = (SingleFileFieldInfo)mapSingleFieldList.get(fmTable.getPeriodField());
                        Object fieldValue = context.getNetPeriodCode();
                        TaskDataServiceNewImpl taskService2 = new TaskDataServiceNewImpl();
                        String newFieldValue = taskService2.getSinglePeriodCode(context, context.getNetPeriodCode(), field.getFieldSize());
                        fieldValue = StringUtils.isEmpty((String)newFieldValue) ? ((String)fieldValue).substring(((String)fieldValue).length() - field.getFieldSize(), ((String)fieldValue).length()) : newFieldValue;
                        fieldValueMap.put(field.getFieldCode(), (String)fieldValue);
                        context.setMapCurrentPeriod((String)fieldValue);
                    }
                    unitFieldMap = context.getMapingCache().getUnitFieldMap().containsKey(context.getCurrentEntintyKey()) ? (Map)context.getMapingCache().getUnitFieldMap().get(context.getCurrentEntintyKey()) : new HashMap();
                }
                String zdm = "";
                DataEntityInfo entityInfo = context.getEntityCache().findEntityByCode(context.getCurrentEntintyKey());
                if (context.getEntityKeyZdmMap().containsKey(context.getCurrentEntintyKey())) {
                    zdm = (String)context.getEntityKeyZdmMap().get(context.getCurrentEntintyKey());
                } else if (entityInfo != null) {
                    zdm = entityInfo.getSingleZdm();
                } else if (!isFMDM) {
                    zdm = context.getCurrentZDM();
                }
                dataSetList.locateDataRowByZdm(zdm);
                DataRow dataRow = null;
                this.setRowValueByFields(context, fieldDefines, dataRow, fixDatas, mapNetFieldList, dataSetList);
                this.setDataAfterExport(context, dataRow, dataSetList, zdm);
                dataSetList.saveRowData();
                if (isFMDM || isSingleFMDM) {
                    String fieldValue;
                    dataRow = ((ReportRegionDataSet)dataSetList.getDataSetList().get(0)).getCurDataRow();
                    for (Object fieldName : unitFieldMap.keySet()) {
                        if (!StringUtils.isNotEmpty((String)((String)unitFieldMap.get(fieldName))) || !StringUtils.isEmpty((String)dataRow.getValueString((String)fieldName))) continue;
                        dataRow.setValue((String)fieldName, unitFieldMap.get(fieldName));
                    }
                    for (Object fieldName : fieldValueMap.keySet()) {
                        Object value = dataRow.getValue((String)fieldName);
                        fieldValue = "";
                        if (null != value) {
                            fieldValue = value.toString();
                        }
                        if (fmTable != null && ((String)fieldName).equals(fmTable.getPeriodField()) && StringUtils.isEmpty((String)fieldValue) && StringUtils.isNotEmpty((String)context.getNetPeriodCode())) {
                            fieldValue = context.getMapCurrentPeriod();
                        }
                        fieldValueMap.put((String)fieldName, fieldValue);
                    }
                    DataEntityInfo entity = context.getEntityCache().findEntityByCode(context.getCurrentEntintyKey());
                    if (entity == null) {
                        entity = context.getEntityCache().findEntityByKey(context.getCurrentEntintyKey());
                    }
                    if (context.getEntityKeyZdmMap().containsKey(context.getCurrentEntintyKey())) {
                        zdm = (String)context.getEntityKeyZdmMap().get(context.getCurrentEntintyKey());
                    } else if (entity != null) {
                        zdm = entity.getSingleZdm();
                    } else if (fmTable != null) {
                        for (String code : fmTable.getZdmFieldCodes()) {
                            fieldValue = (String)fieldValueMap.get(code);
                            zdm = zdm + fieldValue;
                        }
                        if (StringUtils.isNotEmpty((String)zdm) && zdm.length() > fmTable.getZdmLength()) {
                            zdm = zdm.substring(0, fmTable.getZdmLength());
                        }
                        if (!context.getEntityKeyZdmMap().containsKey(context.getCurrentEntintyKey())) {
                            context.getEntityKeyZdmMap().put(context.getCurrentEntintyKey(), zdm);
                        }
                        if (!context.getEntityZdmKeyMap().containsKey(zdm)) {
                            context.getEntityZdmKeyMap().put(zdm, context.getCurrentEntintyKey());
                        }
                    }
                    context.setCurrentZDM(zdm);
                    if (dataRow.getValue("SYS_FJD") == null || StringUtils.isEmpty((String)dataRow.getValue("SYS_FJD").toString())) {
                        String fjdKey = null;
                        if (context.getEntityKeyParentMap().containsKey(context.getCurrentEntintyKey())) {
                            fjdKey = (String)context.getEntityKeyParentMap().get(context.getCurrentEntintyKey());
                        } else if (entity != null) {
                            fjdKey = entity.getEntityParentKey();
                        }
                        if (StringUtils.isNotEmpty(fjdKey)) {
                            String fjdCode;
                            DataEntityInfo entity2 = context.getEntityCache().findEntityByCode(fjdKey);
                            if (context.getEntityKeyZdmMap().containsKey(fjdKey)) {
                                fjdCode = (String)context.getEntityKeyZdmMap().get(fjdKey);
                                dataRow.setValue("SYS_FJD", (Object)fjdCode);
                            } else if (entity2 != null) {
                                fjdCode = entity2.getSingleZdm();
                                dataRow.setValue("SYS_FJD", (Object)fjdCode);
                            }
                        }
                    }
                    if (fmTable != null && entity != null && StringUtils.isNotEmpty((String)fmTable.getDWMCField()) && dataRow.getValue(fmTable.getDWMCField()) == null) {
                        dataRow.setValue(fmTable.getDWMCField(), (Object)entity.getEntityTitle());
                    }
                    if (StringUtils.isNotEmpty((String)fmTable.getPeriodField())) {
                        if (StringUtils.isNotEmpty((String)context.getMapCurrentPeriod())) {
                            dataRow.setValue(fmTable.getPeriodField(), (Object)context.getMapCurrentPeriod());
                        } else {
                            dataRow.setValue(fmTable.getPeriodField(), (Object)context.getCurrentPeriod());
                        }
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
                } else {
                    zdm = context.getCurrentZDM();
                }
                dataSetList.saveData();
            }
        }
        finally {
            if (dataSetList != null) {
                dataSetList.close();
                dataSetList = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportFloatRegion(TaskDataContext context, IReportExportDataSet reportExportData, List<IRegionExportDataSet> floatRegionDataSet, String filePath, SingleFileTableInfo table) throws Exception {
        if (null == floatRegionDataSet) {
            return;
        }
        if (floatRegionDataSet.size() < 1) {
            return;
        }
        String netFormCode = reportExportData.getFormData().getCode();
        ArrayList<List> floatFieldDefines = new ArrayList<List>();
        ArrayList<List> floatdataLinkDefines = new ArrayList<List>();
        for (int k = 0; k < floatRegionDataSet.size(); ++k) {
            ReportRegionDataSetList olddataSetList;
            IRegionExportDataSet floatData = floatRegionDataSet.get(k);
            SingleFileRegionInfo singleRegion = null;
            if (null != table && null != table.getRegion() && null != table.getRegion().getSubRegions()) {
                if (k < table.getRegion().getSubRegions().size()) {
                    singleRegion = (SingleFileRegionInfo)table.getRegion().getSubRegions().get(k);
                } else {
                    singleRegion = table.getRegion();
                    context.info(logger, "\u6279\u91cf\u5bfc\u51faJIO\uff1a\u8868\u5355" + netFormCode + "\u662f\u6d6e\u52a8\u884c\u8f6c\u56fa\u5b9a\u8868\uff0c\u8bf7\u68c0\u67e5");
                }
            }
            HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
            HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
            if (null != singleRegion) {
                for (SingleFileFieldInfo field : singleRegion.getFields()) {
                    mapNetFieldList.put(field.getNetTableCode() + "." + field.getNetFieldCode(), field);
                    mapSingleFieldList.put(field.getTableCode() + "." + field.getFieldCode(), field);
                }
            }
            List oldFloatFieldLists = floatData.getFieldDataList();
            List<FieldData> floatFieldLists = this.commonImportService.copyFieldsFromLink(oldFloatFieldLists, floatData.getLinkDataList());
            ReportRegionDataSetList dataSetList = olddataSetList = TaskFileDataOperateUtil.getRegionDataSetList(context, filePath, netFormCode, floatFieldLists, floatData.getRegionData().getRegionTop());
            if (olddataSetList.isVirtualFloat()) {
                dataSetList = olddataSetList.getVirtualDatasets();
                mapNetFieldList.clear();
                mapSingleFieldList.clear();
                for (Object obj : dataSetList.getFieldMap().values()) {
                    SingleFileFieldInfo field = (SingleFileFieldInfo)obj;
                    mapNetFieldList.put(field.getNetTableCode() + "." + field.getNetFieldCode(), field);
                    mapSingleFieldList.put(field.getTableCode() + "." + field.getFieldCode(), field);
                }
            }
            try {
                if (dataSetList.getDataSetList().size() <= 0) {
                    context.info(logger, "\u8be5\u8868\u6d6e\u52a8\u533a\u57df\u6ca1\u6709\u6620\u5c04\uff1a" + String.valueOf(k));
                    continue;
                }
                boolean hasData = false;
                LinkedHashMap<String, String> floatKeyCodes = new LinkedHashMap<String, String>();
                LinkedHashMap<Map<String, String>, String> floatKeyCodeMap = new LinkedHashMap<Map<String, String>, String>();
                floatFieldDefines.add(floatData.getFieldDataList());
                floatdataLinkDefines.add(floatData.getLinkDataList());
                while (floatData.hasNext()) {
                    MemoryDataSet floatDataSet = (MemoryDataSet)floatData.next();
                    hasData = floatDataSet.size() > 0;
                    if (!hasData) continue;
                    for (int r = 0; r < floatDataSet.size(); ++r) {
                        int posId;
                        Object[] floatDatas = floatDataSet.getBuffer(r);
                        dataSetList.AppendDataRowByZdm(context.getCurrentZDM());
                        DataRow dataRow = null;
                        this.setRowValueByFields(context, floatFieldLists, dataRow, floatDatas, mapNetFieldList, dataSetList);
                        this.setDataAfterExport(context, dataRow, dataSetList, context.getCurrentZDM());
                        ReportRegionDataSet singleRegionSet = (ReportRegionDataSet)dataSetList.getDataSetList().get(0);
                        String floatOrder = SingleOrderUtil.newOrder6();
                        singleRegionSet.setFloatOrder(floatOrder);
                        dataSetList.saveRowData();
                        String floatCode = singleRegionSet.getFloatCodeValues();
                        floatCode = floatCode + "," + floatOrder;
                        String rowkey = floatDatas[floatDatas.length - 1].toString();
                        if (StringUtils.isNotEmpty((String)rowkey) && (posId = rowkey.indexOf("#^$")) > 0) {
                            rowkey = rowkey.substring(posId + "#^$".length());
                        }
                        floatKeyCodes.put(rowkey, floatCode);
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
                        floatKeyCodeMap.put(floatCodeMap2, floatCode);
                    }
                }
                if (hasData) {
                    if (olddataSetList.isVirtualFloat()) {
                        dataSetList.saveData();
                        TaskFileDataOperateUtil.ConvertDataSetListFromvirtual(olddataSetList);
                        dataSetList = olddataSetList;
                    }
                    dataSetList.saveData();
                    for (ReportRegionDataSet dataSet : dataSetList.getDataSetList()) {
                        if (null == dataSet.getParentDataSet()) continue;
                        dataSet.getParentDataSet().locateDataRowByZdm(context.getCurrentZDM());
                        boolean isNewRow = dataSet.getParentDataSet().getIsNewRow();
                        if (!isNewRow) continue;
                        dataSet.getParentDataSet().saveRowData();
                        dataSet.getParentDataSet().saveData();
                    }
                }
                this.updateFormCheckInfo(context, netFormCode, floatKeyCodes, floatKeyCodeMap);
                continue;
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

    @Override
    public void setRowValueByFields(TaskDataContext context, List<FieldData> fields, DataRow dataRow, Object[] rowDatas, Map<String, SingleFileFieldInfo> mapNetFieldList, ReportRegionDataSetList dataSets) throws JsonParseException, JsonMappingException, IOException {
        Map netField2FieldMap = dataSets.getFieldMap();
        for (int i = 0; i < fields.size(); ++i) {
            String fieldName = fields.get(i).getFieldCode();
            String fieldFlag = fields.get(i).getTableName() + "." + fieldName;
            String fieldValue = "";
            if (i < rowDatas.length && rowDatas[i] != null) {
                fieldValue = rowDatas[i].toString();
            }
            String enumItemCode = "";
            boolean needSubCode = true;
            ReportRegionDataSet dataSet = null;
            if (!dataSets.getFieldDataSetMap().containsKey(fieldFlag)) {
                if (!Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equalsIgnoreCase(fieldName) && !context.getEntityCompanyType().equalsIgnoreCase(fieldName)) continue;
                String zdmKey = fieldValue;
                String zdm = "";
                Map[] entity = context.getEntityCache().findEntityByCode(zdmKey);
                if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                    zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                } else if (null != entity) {
                    zdm = entity.getSingleZdm();
                }
                if (context.getEntityFieldIsCode()) continue;
                context.setCurrentEntintyKey(zdmKey);
                context.setCurrentZDM(zdm);
                continue;
            }
            dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
            if (StringUtils.isNotEmpty((String)fieldValue) && fieldValue.startsWith("[") && fieldValue.contains("]")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Map[] resMaps = (Map[])mapper.readValue(fieldValue, Map[].class);
                    if (null != resMaps && resMaps.length > 0) {
                        fieldValue = "";
                        for (Map item : resMaps) {
                            enumItemCode = null != item.get("codevalue") ? item.get("codevalue").toString() : item.get("code").toString();
                            if (StringUtils.isNotEmpty((String)fieldValue)) {
                                fieldValue = fieldValue + ";";
                            }
                            fieldValue = fieldValue + enumItemCode;
                        }
                    }
                }
                catch (Exception e) {
                    context.info(logger, e.getMessage());
                }
            }
            int fieldSize = fields.get(i).getFieldSize();
            String enumFlag = "";
            if (mapNetFieldList.containsKey(fieldFlag) || netField2FieldMap.containsKey(fieldFlag)) {
                SingleFileFieldInfo field = null;
                field = netField2FieldMap.containsKey(fieldFlag) ? (SingleFileFieldInfo)netField2FieldMap.get(fieldFlag) : mapNetFieldList.get(fieldFlag);
                fieldName = field.getFieldCode();
                fieldSize = field.getFieldSize();
                enumFlag = field.getEnumCode();
                needSubCode = field.getFieldType() == FieldType.FIELD_TYPE_TEXT ? false : field.getFieldType() == FieldType.FIELD_TYPE_STRING;
                String newFieldValue = context.getMapingCache().getEnumItemCodeFromNetItem(enumFlag, fieldValue);
                if (StringUtils.isNotEmpty((String)newFieldValue)) {
                    fieldValue = newFieldValue;
                }
            } else if (Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey.equals(fieldName)) {
                String parentKey = null;
                if (!StringUtils.isEmpty((String)fieldValue)) {
                    parentKey = fieldValue;
                }
                context.setParentEntintyKey(parentKey);
                String fjd = "";
                DataEntityInfo entity = context.getEntityCache().findEntityByCode(parentKey);
                if (context.getEntityKeyZdmMap().containsKey(parentKey)) {
                    fjd = (String)context.getEntityKeyZdmMap().get(parentKey);
                } else if (null != entity) {
                    fjd = entity.getSingleZdm();
                }
                fieldValue = fjd;
            } else {
                if (!Consts.EntityField.ENTITY_FIELD_KEY.fieldKey.equals(fieldName)) continue;
                String zdmKey = null;
                if (!StringUtils.isEmpty((String)fieldValue)) {
                    zdmKey = fieldValue;
                }
                String zdm = "";
                DataEntityInfo entity = context.getEntityCache().findEntityByCode(zdmKey);
                if (context.getEntityKeyZdmMap().containsKey(zdmKey)) {
                    zdm = (String)context.getEntityKeyZdmMap().get(zdmKey);
                } else if (null != entity) {
                    zdm = entity.getSingleZdm();
                }
                context.setCurrentEntintyKey(zdmKey);
                context.setCurrentZDM(zdm);
                fieldValue = zdm;
                dataRow.setValue("SYS_ZDM", (Object)zdm);
            }
            if (needSubCode && StringUtils.isNotEmpty((String)fieldValue) && fieldValue.length() > fieldSize && fieldSize > 0) {
                fieldValue = fieldValue.substring(0, fieldSize);
            }
            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
        }
        for (ReportRegionDataSet dataSet : dataSets.getDataSetList()) {
            if (dataSet.getFloatCodeFields() == null || dataSet.getFloatCodeFields().size() <= 0) continue;
            for (String fieldName : dataSet.getFloatCodeFields()) {
                SingleFileFieldInfo field;
                if (dataSet.getMapFieldList() == null || !dataSet.getMapFieldList().containsKey(fieldName) || !StringUtils.isNotEmpty((String)(field = (SingleFileFieldInfo)dataSet.getMapFieldList().get(fieldName)).getDefaultValue()) || !StringUtils.isEmpty((String)dataSet.getCurDataRow().getValueString(fieldName))) continue;
                String fieldValue = field.getDefaultValue();
                fieldValue = fieldValue.replace("\"", "");
                dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setDataAfterExport(TaskDataContext context, DataRow dataRow, ReportRegionDataSetList dataSets, String zdm) {
        try {
            String zdmDocFilePath;
            String tempTaskFilePath;
            String picFilePath;
            String txtFilePath;
            ISingleAttachmentService singleAttachService = (ISingleAttachmentService)context.getIntfObjects().get("singleAttachService");
            String docFilePath = dataSets.getDocFilePath();
            if (StringUtils.isEmpty((String)docFilePath)) {
                docFilePath = PathUtil.createNewPath((String)dataSets.getFilePath(), (String)"SYS_DOC");
                dataSets.setDocFilePath(docFilePath);
            }
            if (StringUtils.isEmpty((String)(txtFilePath = dataSets.getTxtFilePath()))) {
                txtFilePath = PathUtil.createNewPath((String)dataSets.getFilePath(), (String)"SYS_TXT");
                dataSets.setTxtFilePath(txtFilePath);
            }
            if (StringUtils.isEmpty((String)(picFilePath = dataSets.getImgFilePath()))) {
                picFilePath = PathUtil.createNewPath((String)dataSets.getFilePath(), (String)"SYS_IMG");
                dataSets.setImgFilePath(picFilePath);
            }
            if (StringUtils.isEmpty((String)(tempTaskFilePath = dataSets.getTempFilePath()))) {
                tempTaskFilePath = PathUtil.createNewPath((String)context.getTaskDataPath(), (String)"Temp");
                dataSets.setTempFilePath(tempTaskFilePath);
            }
            if (!(zdmDocFilePath = PathUtil.getNewPath((String)docFilePath, (String)zdm)).equalsIgnoreCase(dataSets.getZdmDocFilePath())) {
                docFilePath = zdmDocFilePath = PathUtil.createNewPath((String)docFilePath, (String)zdm);
                dataSets.setZdmDocFilePath(zdmDocFilePath);
            } else {
                docFilePath = zdmDocFilePath;
            }
            for (String fieldFlag : dataSets.getFieldDataSetMap().keySet()) {
                String textFieldValue;
                String newFileNameAll;
                String fileNameAll;
                String fileName;
                FileInfo fileInfo;
                Object fileInfos;
                ArrayList<String> fileNames;
                String tempFilePath;
                String groupFileKey;
                String formCode;
                ReportRegionDataSet dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
                SingleFileFieldInfo field = (SingleFileFieldInfo)dataSet.getFieldMap().get(fieldFlag);
                String fieldValue = "";
                String fieldName = field.getFieldCode();
                SingleFileTableInfo singleTableInfo = (SingleFileTableInfo)dataSet.getTableInfo();
                if (singleTableInfo == null && (fieldName.equalsIgnoreCase("FJFIELD") || fieldName.equalsIgnoreCase("WZFIELD")) && StringUtils.isNotEmpty((String)(formCode = field.getTableCode())) && context.getMapingCache().getMapConfig() != null) {
                    for (SingleFileTableInfo v : context.getMapingCache().getMapConfig().getTableInfos()) {
                        if (!formCode.equalsIgnoreCase(v.getSingleTableCode())) continue;
                        singleTableInfo = v;
                        break;
                    }
                }
                if (fieldName.equalsIgnoreCase("FJFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 3)) {
                    if (!context.isExportEnclosure()) continue;
                    try {
                        groupFileKey = dataSet.getCurDataRow().getValueString(fieldName);
                        if (!StringUtils.isNotEmpty((String)groupFileKey)) continue;
                        tempFilePath = tempTaskFilePath;
                        tempFilePath = PathUtil.createNewPath((String)tempFilePath, (String)OrderGenerator.newOrder());
                        try {
                            fileNames = new ArrayList<String>();
                            fileInfos = this.downloadSingleFiles(singleAttachService, tempFilePath, context.getDataSchemeKey(), context.getTaskKey(), groupFileKey);
                            Iterator<FileInfo> iterator = fileInfos.iterator();
                            while (iterator.hasNext()) {
                                fileInfo = iterator.next();
                                fileName = tempFilePath + fileInfo.getName();
                                fileNames.add(fileName);
                            }
                            if (fileNames.size() > 0) {
                                fieldValue = field.getTableCode();
                                TaskFileDataOperateUtil.SaveFileToSingleByZdm(context, fileNames, tempFilePath, docFilePath, fieldValue, zdm);
                                this.saveFileInfoToIni(fieldValue, docFilePath, (List<FileInfo>)fileInfos);
                                context.updateAttachFileNumAsyn(fileNames.size());
                            }
                            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
                        }
                        finally {
                            PathUtil.deleteDir((String)tempFilePath);
                        }
                    }
                    catch (Exception ex) {
                        context.error(logger, "\u5bfc\u51fa\u4e3b\u4ee3\u7801\uff1a" + zdm + "," + ex.getMessage(), (Throwable)ex);
                    }
                    continue;
                }
                if (fieldName.equalsIgnoreCase("WZFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 2)) {
                    if (!context.isExportEnclosure()) continue;
                    try {
                        groupFileKey = dataSet.getCurDataRow().getValueString(fieldName);
                        fieldValue = null;
                        if (!StringUtils.isNotEmpty((String)groupFileKey)) continue;
                        tempFilePath = tempTaskFilePath;
                        tempFilePath = PathUtil.createNewPath((String)tempFilePath, (String)OrderGenerator.newOrder());
                        try {
                            List<FileInfo> fileInfos2 = this.downloadSingleFiles(singleAttachService, tempFilePath, context.getDataSchemeKey(), context.getTaskKey(), groupFileKey);
                            if (fileInfos2 != null && fileInfos2.size() > 0) {
                                for (FileInfo fileInfo2 : fileInfos2) {
                                    String extFile = fileInfo2.getExtension();
                                    if (!".RTF".equalsIgnoreCase(extFile)) continue;
                                    fieldValue = field.getTableCode();
                                    fileNameAll = tempFilePath + fileInfo2.getName();
                                    newFileNameAll = docFilePath + fieldValue + extFile;
                                    PathUtil.copyFile((String)fileNameAll, (String)newFileNameAll);
                                    break;
                                }
                            }
                            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
                        }
                        finally {
                            PathUtil.deleteDir((String)tempFilePath);
                        }
                    }
                    catch (Exception ex) {
                        context.error(logger, "\u5bfc\u51fa\u4e3b\u4ee3\u7801\uff1a" + zdm + "," + ex.getMessage(), (Throwable)ex);
                    }
                    continue;
                }
                if (field.getFieldType() == FieldType.FIELD_TYPE_FILE) {
                    if (!context.isExportEnclosure()) continue;
                    try {
                        groupFileKey = dataSet.getCurDataRow().getValueString(fieldName);
                        if (!StringUtils.isNotEmpty((String)groupFileKey)) continue;
                        tempFilePath = tempTaskFilePath;
                        tempFilePath = PathUtil.createNewPath((String)tempFilePath, (String)OrderGenerator.newOrder());
                        try {
                            FileInfo fileInfo2;
                            fileNames = new ArrayList();
                            fileInfos = this.downloadSingleFiles(singleAttachService, tempFilePath, context.getDataSchemeKey(), context.getTaskKey(), groupFileKey);
                            fileInfo2 = fileInfos.iterator();
                            while (fileInfo2.hasNext()) {
                                fileInfo = fileInfo2.next();
                                fileName = tempFilePath + fileInfo.getName();
                                fileNames.add(fileName);
                            }
                            if (fileNames.size() > 0) {
                                fieldValue = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                                TaskFileDataOperateUtil.SaveFileToSingleByZdm(context, fileNames, tempFilePath, docFilePath, fieldValue, zdm);
                                this.saveFileInfoToIni(fieldValue, docFilePath, (List<FileInfo>)fileInfos);
                                fieldValue = fieldValue + ";" + fileNames.size();
                                context.updateAttachFileNumAsyn(fileNames.size());
                            }
                            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
                        }
                        finally {
                            PathUtil.deleteDir((String)tempFilePath);
                        }
                    }
                    catch (Exception ex) {
                        context.error(logger, "\u5bfc\u51fa\u4e3b\u4ee3\u7801\uff1a" + zdm + "," + ex.getMessage(), (Throwable)ex);
                    }
                    continue;
                }
                if (field.getFieldType() == FieldType.FIELD_TYPE_PICTURE) {
                    if (!context.isExportEnclosure()) continue;
                    try {
                        groupFileKey = dataSet.getCurDataRow().getValueString(fieldName);
                        fieldValue = null;
                        if (!StringUtils.isNotEmpty((String)groupFileKey)) continue;
                        tempFilePath = tempTaskFilePath;
                        tempFilePath = PathUtil.createNewPath((String)tempFilePath, (String)OrderGenerator.newOrder());
                        try {
                            List<FileInfo> fileInfos3;
                            String fieldKey = null;
                            DataField dataField = this.dataSchemeSevice.getDataField(field.getNetFieldKey());
                            if (dataField != null) {
                                fieldKey = dataField.getKey();
                            }
                            if ((fileInfos3 = this.downloadSingleFiles(singleAttachService, tempFilePath, context.getDataSchemeKey(), context.getTaskKey(), groupFileKey, fieldKey)) != null && fileInfos3.size() > 0) {
                                fileInfo = fileInfos3.get(0);
                                fieldValue = UUID.randomUUID().toString().replace("-", "").toUpperCase() + fileInfo.getExtension();
                                fileNameAll = tempFilePath + fileInfo.getName();
                                newFileNameAll = picFilePath + fieldValue;
                                PathUtil.copyFile((String)fileNameAll, (String)newFileNameAll);
                            }
                            dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
                        }
                        finally {
                            PathUtil.deleteDir((String)tempFilePath);
                        }
                    }
                    catch (Exception ex) {
                        context.error(logger, "\u5bfc\u51fa\u4e3b\u4ee3\u7801\uff1a" + zdm + "," + ex.getMessage(), (Throwable)ex);
                    }
                    continue;
                }
                if (field.getFieldType() != FieldType.FIELD_TYPE_TEXT || !StringUtils.isNotEmpty((String)(textFieldValue = dataSet.getCurDataRow().getValueString(fieldName)))) continue;
                String textFileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + ".TXT";
                TaskFileDataOperateUtil.saveSingleFieldTextByZdm(context, txtFilePath, textFileName, zdm, textFieldValue);
                dataSet.getCurDataRow().setValue(fieldName, (Object)textFileName);
            }
        }
        catch (Exception ex2) {
            context.error(logger, "\u5bfc\u51fa\u4e3b\u4ee3\u7801\uff1a" + zdm + "," + ex2.getMessage(), (Throwable)ex2);
        }
    }

    private List<FileInfo> downloadSingleFiles(ISingleAttachmentService singleAttachService, String filePath, String dataSchemeKey, String taskKey, String groupKey) throws StreamException {
        return this.downloadSingleFiles(singleAttachService, filePath, dataSchemeKey, taskKey, groupKey, null);
    }

    private List<FileInfo> downloadSingleFiles(ISingleAttachmentService singleAttachService, String filePath, String dataSchemeKey, String taskKey, String groupKey, String fieldKey) throws StreamException {
        ArrayList<FileInfo> NewfileInfos = new ArrayList<FileInfo>();
        ArrayList<String> fileNames = new ArrayList<String>();
        List<FileInfo> fileInfos = singleAttachService.getFileInfoByGroup(groupKey, dataSchemeKey, taskKey, fieldKey);
        if (null != fileInfos) {
            for (FileInfo fileInfo : fileInfos) {
                String filename1 = fileInfo.getName();
                if (StringUtils.isEmpty((String)filename1)) continue;
                if (filename1.contains("\u2014")) {
                    filename1 = filename1.replace("\u2014", "");
                }
                if (filename1.contains("\u00a0")) {
                    filename1 = filename1.replace("\u00a0", "");
                }
                String fileName = filePath + filename1;
                try {
                    FileOutputStream stream = new FileOutputStream(new File(SinglePathUtil.normalize((String)fileName)));
                    Throwable throwable = null;
                    try {
                        singleAttachService.download(fileInfo.getKey(), dataSchemeKey, taskKey, stream);
                        stream.flush();
                        fileNames.add(fileName);
                        NewfileInfos.add(fileInfo);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (stream == null) continue;
                        if (throwable != null) {
                            try {
                                stream.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        stream.close();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return NewfileInfos;
    }

    private void saveFileInfoToIni(String fieldValue, String docFilePath, List<FileInfo> fileInfos) throws IOException, SingleFileException {
        Ini ini = new Ini();
        ini.writeString(fieldValue, "Count", String.valueOf(fileInfos.size()));
        int i = 0;
        for (FileInfo fileInfo : fileInfos) {
            String extFile = fileInfo.getExtension();
            String fileName = fileInfo.getName();
            if (StringUtils.isNotEmpty((String)extFile)) {
                fileName = fileName.replace(extFile, "");
                if ((extFile = extFile.replace(".", "")).startsWith(".")) {
                    extFile = extFile.substring(1, extFile.length());
                }
            }
            ini.writeString(fieldValue, String.valueOf(i) + "_Name", fileName);
            ini.writeString(fieldValue, String.valueOf(i) + "_Type", extFile);
            ini.writeString(fieldValue, String.valueOf(i) + "_FileSize", String.valueOf(fileInfo.getSize()));
            Date date = new Date();
            ini.writeString(fieldValue, String.valueOf(i) + "_UpDateTime", String.valueOf(date));
            ini.writeString(fieldValue, String.valueOf(i) + "_UpLoadTime", String.valueOf(date));
            if (StringUtils.isNotEmpty((String)fileInfo.getKey())) {
                ini.writeString(fieldValue, String.valueOf(i) + "_FileKey", fileInfo.getKey());
            }
            if (StringUtils.isNotEmpty((String)fileInfo.getCategory())) {
                ini.writeString(fieldValue, String.valueOf(i) + "_Category", fileInfo.getCategory());
            }
            if (StringUtils.isNotEmpty((String)fileInfo.getSecretlevel())) {
                ini.writeString(fieldValue, String.valueOf(i) + "_SecretLevel", fileInfo.getSecretlevel());
            }
            ++i;
        }
        ini.saveToFile(docFilePath + fieldValue + ".Ini");
    }

    @Override
    public void updateFormCheckInfo(TaskDataContext context, String formCode, Map<String, String> floatKeyCodes, Map<Map<String, String>, String> floatKeyCodeMap) {
        Map checkInfoCache = context.getCheckInfos();
        if (checkInfoCache.size() <= 0) {
            return;
        }
        HashMap<String, String> floatCodeKeys = new HashMap<String, String>();
        for (String aCode : floatKeyCodes.keySet()) {
            String floatCodes = floatKeyCodes.get(aCode);
            if (StringUtils.isEmpty((String)floatCodes)) continue;
            floatCodeKeys.put(floatCodes, aCode);
        }
        for (Map SchemeCache : checkInfoCache.values()) {
            List bjFormCheckInfoList;
            String floatCode;
            String findCode;
            if (SchemeCache.containsKey(formCode)) {
                List formCheckInfoList = (List)SchemeCache.get(formCode);
                for (DataChkInfo checkInfo : formCheckInfoList) {
                    findCode = "";
                    if (StringUtils.isNotEmpty((String)checkInfo.getFloatRecKey())) {
                        findCode = checkInfo.getFloatRecKey();
                    } else if (StringUtils.isNotEmpty((String)checkInfo.getFloatFlag())) {
                        findCode = checkInfo.getFloatFlag();
                    }
                    if (!StringUtils.isNotEmpty((String)findCode)) continue;
                    if (floatKeyCodes.containsKey(findCode)) {
                        floatCode = floatKeyCodes.get(findCode);
                        this.analFloatCode(floatCode, checkInfo);
                        continue;
                    }
                    if (checkInfo.getFloatFlagItems().size() <= 0 || floatKeyCodeMap == null || !floatKeyCodeMap.containsKey(checkInfo.getFloatFlagItems())) continue;
                    floatCode = floatKeyCodeMap.get(checkInfo.getFloatFlagItems());
                    this.analFloatCode(floatCode, checkInfo);
                }
            }
            if (!SchemeCache.containsKey("") || (bjFormCheckInfoList = (List)SchemeCache.get("")) == null || bjFormCheckInfoList.isEmpty()) continue;
            for (DataChkInfo checkInfo : bjFormCheckInfoList) {
                if (checkInfo.getOwnerTables() == null || checkInfo.getOwnerTables().isEmpty() || !checkInfo.getOwnerTables().contains(formCode)) continue;
                findCode = "";
                if (StringUtils.isNotEmpty((String)checkInfo.getFloatRecKey())) {
                    findCode = checkInfo.getFloatRecKey();
                } else if (StringUtils.isNotEmpty((String)checkInfo.getFloatFlag())) {
                    findCode = checkInfo.getFloatFlag();
                }
                if (!StringUtils.isNotEmpty((String)findCode)) continue;
                if (floatKeyCodes.containsKey(findCode)) {
                    floatCode = floatKeyCodes.get(findCode);
                    this.analFloatCode(floatCode, checkInfo);
                    continue;
                }
                if (checkInfo.getFloatFlagItems().size() <= 0 || floatKeyCodeMap == null || !floatKeyCodeMap.containsKey(checkInfo.getFloatFlagItems())) continue;
                floatCode = floatKeyCodeMap.get(checkInfo.getFloatFlagItems());
                this.analFloatCode(floatCode, checkInfo);
            }
        }
    }

    private void analFloatCode(String floatCode, DataChkInfo checkInfo) {
        String floatOrder = null;
        if (StringUtils.isNotEmpty((String)floatCode)) {
            String[] floatCodes = floatCode.split(",");
            if (floatCodes.length > 0) {
                floatCode = floatCodes[0];
            }
            if (floatCodes.length > 1) {
                floatOrder = floatCodes[1];
            }
        }
        checkInfo.setFloatFlag(floatCode);
        if (checkInfo.isLinkFormula()) {
            floatOrder = "";
        } else if (checkInfo.getOwnerTables() != null && checkInfo.getOwnerTables().size() > 1) {
            floatOrder = "";
        }
        checkInfo.setFloatOrder(floatOrder);
        checkInfo.setMaped(true);
        checkInfo.setFloatData(true);
    }
}

