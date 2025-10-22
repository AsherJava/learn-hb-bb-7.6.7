/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.single.core.dbf.DBFCreator
 *  com.jiuqi.nr.single.core.dbf.DbfException
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.idx.IndexFieldDef
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.SingleSecurityUtils
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  nr.single.map.data.DbfUtil
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 *  nr.single.map.data.internal.SingleFileFieldInfoImpl
 *  nr.single.map.data.internal.service.dataSet.ReportRegionDataSetImpl
 *  nr.single.map.data.internal.service.dataSet.ReportRegionDataSetListImpl
 */
package nr.single.data.util;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.idx.IndexFieldDef;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nr.single.map.data.DbfUtil;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.service.dataSet.ReportRegionDataSetImpl;
import nr.single.map.data.internal.service.dataSet.ReportRegionDataSetListImpl;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskFileDataOperateUtil {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileDataOperateUtil.class);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SingleFileTableInfo getSingleTableInfo(TaskDataContext context, String netFormKey, String netFormCode) {
        SingleFileTableInfo table = null;
        if (context.getMapingCache().getSingleTableMap().containsKey(netFormCode)) {
            table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get(netFormCode);
        }
        boolean isFMDM = netFormKey.equalsIgnoreCase(context.getFmdmFormKey());
        if (!context.getMapingCache().getNetFieldMap().containsKey(netFormCode)) {
            if (!isFMDM) return null;
            return TaskFileDataOperateUtil.getSingleFMDMTable(context);
        }
        if (table == null) {
            if (isFMDM) {
                return TaskFileDataOperateUtil.getSingleFMDMTable(context);
            }
            Map singleMapFields = (Map)context.getMapingCache().getNetFieldMap().get(netFormCode);
            SingleFileTableInfo firstTable = null;
            for (SingleFileFieldInfo field : singleMapFields.values()) {
                if (firstTable == null && context.getMapingCache().getMapTables().containsKey(field.getTableCode())) {
                    firstTable = (SingleFileTableInfo)context.getMapingCache().getMapTables().get(field.getTableCode());
                }
                if (!"FMDM".equalsIgnoreCase(field.getTableCode())) continue;
                table = TaskFileDataOperateUtil.getSingleFMDMTable(context);
                break;
            }
            if (table != null) return table;
            return firstTable;
        }
        if (!isFMDM) return table;
        if ("FMDM".equalsIgnoreCase(table.getSingleTableCode())) return table;
        logger.info("\u4fee\u6b63\u4e3a\u5c01\u9762\u4ee3\u7801");
        return TaskFileDataOperateUtil.getSingleFMDMTable(context);
    }

    public static SingleFileTableInfo getSingleFMDMTable(TaskDataContext context) {
        SingleFileTableInfo table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get("FMDM");
        if ((table = TaskFileDataOperateUtil.jugeIsSingleFMDM(table, context.getMapingCache().getSingleTableMap())) == null) {
            table = (SingleFileTableInfo)context.getMapingCache().getMapTables().get("FMDM");
            table = TaskFileDataOperateUtil.jugeIsSingleFMDM(table, context.getMapingCache().getMapTables());
        }
        return table;
    }

    private static SingleFileTableInfo jugeIsSingleFMDM(SingleFileTableInfo table, Map<String, SingleFileTableInfo> singleTableMap) {
        SingleFileTableInfo newTable = table;
        boolean isChagne = false;
        if (table != null && !(table instanceof SingleFileFmdmInfo)) {
            for (SingleFileTableInfo aTable : singleTableMap.values()) {
                if (!(aTable instanceof SingleFileFmdmInfo)) continue;
                newTable = aTable;
                isChagne = true;
                logger.info("\u4fee\u6b63\u4e3a\u5c01\u9762\u4ee3\u7801," + aTable.getSingleTableCode());
                break;
            }
            if (!isChagne) {
                logger.info("\u6620\u5c04\u5173\u7cfb\u4e2d\uff0c\u672a\u627e\u5230\u5355\u673a\u7248\u5c01\u9762\u4ee3\u7801\u8868");
            }
        }
        return newTable;
    }

    public static void convertFixtAndFloatDataSets(ReportRegionDataSetList fixDataSets, ReportRegionDataSetList floatDataSets, boolean isFixToFloat, int floatEnumType) throws Exception {
        List zdmList;
        String floatEnumCode;
        String lastFloatEnumCode = null;
        HashMap floatRowFieldMap = new HashMap();
        HashMap floatRowEnumFieldMap = new HashMap();
        HashMap floatRowEnumKeyFields = new HashMap();
        for (String fieldFlag : fixDataSets.getFieldMap().keySet()) {
            SingleFileFieldInfo fieldMapItem = (SingleFileFieldInfo)fixDataSets.getFieldMap().get(fieldFlag);
            Integer floatEnumOrder = fieldMapItem.getFloatEnumOrder();
            floatEnumCode = fieldMapItem.getFloatEnumCode();
            boolean isEnumField = StringUtils.isEmpty((String)floatEnumCode);
            if (floatEnumType == 1 && isEnumField) {
                floatEnumCode = fieldMapItem.getFieldCode();
            } else if (floatEnumType == 2 && isEnumField) {
                floatEnumCode = fieldMapItem.getNetFieldCode();
            }
            Object rowFields = null;
            if (floatRowFieldMap.containsKey(floatEnumOrder)) {
                rowFields = (List)floatRowFieldMap.get(floatEnumOrder);
            } else {
                rowFields = new ArrayList();
                floatRowFieldMap.put(floatEnumOrder, rowFields);
            }
            rowFields.add(fieldMapItem);
            List<SingleFileFieldInfo> rowEnumFields = null;
            if (floatRowEnumFieldMap.containsKey(floatEnumCode)) {
                rowEnumFields = (List)floatRowEnumFieldMap.get(floatEnumCode);
            } else {
                rowEnumFields = new ArrayList();
                floatRowEnumFieldMap.put(floatEnumCode, rowEnumFields);
            }
            rowEnumFields.add(fieldMapItem);
            if (!isEnumField) continue;
            List<SingleFileFieldInfo> enumKeyFields = null;
            if (floatRowEnumKeyFields.containsKey(floatEnumCode)) {
                enumKeyFields = (List)floatRowEnumKeyFields.get(floatEnumCode);
            } else {
                enumKeyFields = new ArrayList();
                floatRowEnumKeyFields.put(floatEnumCode, enumKeyFields);
            }
            enumKeyFields.add(fieldMapItem);
            lastFloatEnumCode = floatEnumCode;
        }
        if (isFixToFloat) {
            zdmList = fixDataSets.getZdmList();
            for (String zdm : zdmList) {
                fixDataSets.locateDataRowByZdm(zdm);
                for (Integer rowIndex : floatRowFieldMap.keySet()) {
                    List rowFields = (List)floatRowFieldMap.get(rowIndex);
                    if (rowFields.size() == 1 && StringUtils.isEmpty((String)((SingleFileFieldInfo)rowFields.get(0)).getFloatEnumCode())) continue;
                    floatDataSets.AppendDataRowByZdm(zdm);
                    for (SingleFileFieldInfo fieldMapItem : rowFields) {
                        ReportRegionDataSet dataset;
                        String floatEnumCode2 = fieldMapItem.getFloatEnumCode();
                        boolean isEnumField = StringUtils.isEmpty((String)floatEnumCode2);
                        String sourceFieldName = "";
                        String destFieldName = "";
                        if (floatEnumType == 1 && isEnumField) {
                            floatEnumCode2 = fieldMapItem.getFieldCode();
                        } else if (floatEnumType == 2 && isEnumField) {
                            floatEnumCode2 = fieldMapItem.getNetFieldCode();
                        }
                        if (floatEnumType == 1) {
                            sourceFieldName = fieldMapItem.getTableCode() + "." + fieldMapItem.getFieldCode();
                            destFieldName = fieldMapItem.getNetTableCode() + "." + fieldMapItem.getNetFieldCode();
                        } else if (floatEnumType == 2) {
                            destFieldName = sourceFieldName = fieldMapItem.getTableCode() + "." + fieldMapItem.getFieldCode();
                        }
                        String fieldValue = floatEnumCode2;
                        if (!isEnumField) {
                            fieldValue = fixDataSets.getFieldValue(sourceFieldName);
                        }
                        if (!StringUtils.isNotEmpty((String)destFieldName) || !StringUtils.isNotEmpty((String)fieldValue) || (dataset = floatDataSets.getDataSetbyFieldName(destFieldName)) == null || !dataset.getFieldMap().containsKey(destFieldName)) continue;
                        dataset.getCurDataRow().setValue(fieldMapItem.getNetFieldCode(), (Object)fieldValue);
                    }
                    floatDataSets.saveRowData();
                }
            }
        } else {
            zdmList = floatDataSets.getZdmList();
            for (String zdm : zdmList) {
                floatDataSets.locateDataRowByZdm(zdm);
                for (int i = 0; i < floatDataSets.getFloatRowsCount(); ++i) {
                    List rowFields;
                    floatDataSets.locateDataRowByFloatIndex(i);
                    fixDataSets.locateDataRowByZdm(zdm);
                    floatEnumCode = "";
                    List enumKeyFields = (List)floatRowEnumKeyFields.get(lastFloatEnumCode);
                    HashMap<String, SingleFileFieldInfo> valueMapFields = new HashMap<String, SingleFileFieldInfo>();
                    if (enumKeyFields != null) {
                        for (SingleFileFieldInfo enumField : enumKeyFields) {
                            String fieldFlag = enumField.getNetTableCode() + "." + enumField.getNetFieldCode();
                            String enumValue = floatDataSets.getFieldValue(fieldFlag);
                            if (StringUtils.isEmpty((String)enumValue)) {
                                if (floatEnumType == 1) {
                                    enumValue = enumField.getFieldCode();
                                } else if (floatEnumType == 2) {
                                    enumValue = enumField.getNetFieldCode();
                                }
                            }
                            if (valueMapFields.containsKey(enumValue)) continue;
                            valueMapFields.put(enumValue, enumField);
                            if (StringUtils.isNotEmpty((String)floatEnumCode)) {
                                floatEnumCode = floatEnumCode + ";";
                            }
                            floatEnumCode = floatEnumCode + enumValue;
                        }
                    }
                    if ((rowFields = (List)floatRowEnumFieldMap.get(floatEnumCode)) != null) {
                        for (SingleFileFieldInfo fieldMapItem : rowFields) {
                            ReportRegionDataSet dataset;
                            boolean isEnumField = StringUtils.isEmpty((String)floatEnumCode);
                            if (floatEnumType == 1 && isEnumField) {
                                floatEnumCode = fieldMapItem.getFieldCode();
                            } else if (floatEnumType == 2 && isEnumField) {
                                floatEnumCode = fieldMapItem.getNetFieldCode();
                            }
                            String sourceFieldName = "";
                            String destFieldName = "";
                            if (floatEnumType == 1) {
                                destFieldName = sourceFieldName = fieldMapItem.getNetTableCode() + "." + fieldMapItem.getNetFieldCode();
                            } else if (floatEnumType == 2) {
                                destFieldName = sourceFieldName = fieldMapItem.getTableCode() + "." + fieldMapItem.getFieldCode();
                            }
                            String fieldValue = floatEnumCode;
                            if (!isEnumField) {
                                fieldValue = floatDataSets.getFieldValue(sourceFieldName);
                            }
                            if (!StringUtils.isNotEmpty((String)destFieldName) || !StringUtils.isNotEmpty((String)fieldValue) || (dataset = fixDataSets.getDataSetbyFieldName(destFieldName)) == null || !dataset.getFieldMap().containsKey(destFieldName)) continue;
                            dataset.getCurDataRow().setValue(fieldMapItem.getFieldCode(), (Object)fieldValue);
                        }
                    }
                    fixDataSets.saveRowData();
                }
            }
        }
    }

    public static void ConvertDataSetListTovirtual(ReportRegionDataSetList dataSets) throws Exception {
        ReportRegionDataSetList virtualDatasets = dataSets.getVirtualDatasets();
        int floatEnumType = dataSets.getFloatEnumType();
        if (virtualDatasets != null && floatEnumType > 0) {
            if (floatEnumType == 1) {
                TaskFileDataOperateUtil.convertFixtAndFloatDataSets(dataSets, virtualDatasets, true, floatEnumType);
            } else if (floatEnumType == 2) {
                TaskFileDataOperateUtil.convertFixtAndFloatDataSets(virtualDatasets, dataSets, true, floatEnumType);
            }
        }
    }

    public static void ConvertDataSetListFromvirtual(ReportRegionDataSetList dataSets) throws Exception {
        ReportRegionDataSetList virtualDatasets = dataSets.getVirtualDatasets();
        int floatEnumType = dataSets.getFloatEnumType();
        if (virtualDatasets != null && floatEnumType > 0) {
            if (floatEnumType == 1) {
                TaskFileDataOperateUtil.convertFixtAndFloatDataSets(dataSets, virtualDatasets, false, floatEnumType);
            } else if (floatEnumType == 2) {
                TaskFileDataOperateUtil.convertFixtAndFloatDataSets(virtualDatasets, dataSets, false, floatEnumType);
            }
        }
    }

    public static ReportRegionDataSetList getRegionDataSetList(TaskDataContext context, String filePath, String netFormCode, List<FieldData> netFieldDefines, int floatingRow) throws Exception {
        return TaskFileDataOperateUtil.getRegionDataSetList(context, filePath, netFormCode, netFieldDefines, floatingRow, false);
    }

    public static ReportRegionDataSetList getRegionDataSetList(TaskDataContext context, String filePath, String netFormCode, List<FieldData> netFieldDefines, int floatingRow, boolean batchMode) throws Exception {
        ReportRegionDataSetListImpl dataSets = new ReportRegionDataSetListImpl();
        Map fieldDataSetMap = dataSets.getFieldDataSetMap();
        Map allFieldMap = dataSets.getFieldMap();
        dataSets.setFilePath(filePath);
        dataSets.setFloatEnumType(0);
        int regionFloatEnumType = 0;
        Map netFormFieldList = (Map)context.getMapingCache().getNetFieldListMap().get(netFormCode);
        for (FieldData netField : netFieldDefines) {
            String fieldFlag = netField.getTableName() + "." + netField.getFieldCode();
            if (null == netFormFieldList || !netFormFieldList.containsKey(fieldFlag)) continue;
            List fieldMapItems = (List)netFormFieldList.get(fieldFlag);
            ArrayList<SingleFileFieldInfo> fieldMapItems2 = new ArrayList<SingleFileFieldInfo>();
            boolean hasRegionMaping = false;
            if (fieldMapItems.size() > 0 && StringUtils.isNotEmpty((String)netField.getRegionKey())) {
                for (SingleFileFieldInfo fieldMapItem : fieldMapItems) {
                    if (netField.getRegionKey().equalsIgnoreCase(fieldMapItem.getRegionKey())) {
                        fieldMapItems2.add(fieldMapItem);
                        break;
                    }
                    if (!StringUtils.isNotEmpty((String)fieldMapItem.getRegionKey())) continue;
                    hasRegionMaping = true;
                }
            }
            if (fieldMapItems2.size() == 0 && !hasRegionMaping) {
                fieldMapItems2.addAll(fieldMapItems);
            }
            for (SingleFileFieldInfo fieldMapItem : fieldMapItems2) {
                boolean isUseFloatEnum = fieldMapItem.getFloatEnumType() > 0;
                String singleFormCode = fieldMapItem.getFormCode();
                String singleFieldCode = fieldMapItem.getFieldCode();
                if (StringUtils.isEmpty((String)singleFormCode)) {
                    singleFormCode = fieldMapItem.getTableCode();
                }
                ReportRegionDataSet dataSet = null;
                if (StringUtils.isNotEmpty((String)singleFormCode) && context.getMapingCache().getSingleTableMap().containsKey(singleFormCode)) {
                    SingleFileRegionInfo singleRegion = TaskFileDataOperateUtil.getSingleRegionInfo(context, singleFormCode, singleFieldCode, floatingRow);
                    int singleFloatingRow = floatingRow;
                    if (singleRegion != null) {
                        singleFloatingRow = singleRegion.getFloatingIndex();
                    }
                    if ((dataSet = TaskFileDataOperateUtil.getSingleRegionDataSet(context, filePath, singleFormCode, null, singleFloatingRow, (ReportRegionDataSetList)dataSets, batchMode)) != null) {
                        fieldDataSetMap.put(fieldFlag, dataSet);
                        allFieldMap.put(fieldFlag, fieldMapItem);
                        dataSet.getFieldMap().put(fieldFlag, fieldMapItem);
                    }
                }
                if (!isUseFloatEnum) continue;
                int floatEnumType = fieldMapItem.getFloatEnumType();
                if (regionFloatEnumType == 0) {
                    regionFloatEnumType = floatEnumType;
                    dataSets.setFloatEnumType(regionFloatEnumType);
                } else if (regionFloatEnumType != floatEnumType) {
                    logger.error("\u56fa\u5b9a\u4e0e\u6d6e\u52a8\u7684\u8f6c\u6362\u6620\u5c04\u5173\u7cfb\u5b58\u5728\u95ee\u9898\u8bf7\u68c0\u67e5");
                }
                ReportRegionDataSetList virtualDatasets = dataSets.getVirtualDatasets();
                if (virtualDatasets == null) {
                    virtualDatasets = new ReportRegionDataSetListImpl();
                    virtualDatasets.setFilePath(filePath);
                    dataSets.setFloatEnumType(regionFloatEnumType);
                    dataSets.setVirtualDatasets(virtualDatasets);
                }
                ReportRegionDataSet virtualDataset = null;
                String tempDBFName = fieldMapItem.getNetFormCode() + "_FloatEnumTemp";
                if (floatingRow > 0) {
                    tempDBFName = tempDBFName + "_" + String.valueOf(floatingRow);
                }
                if (virtualDatasets.getFormDataSetMap().containsKey(tempDBFName)) {
                    virtualDataset = (ReportRegionDataSet)virtualDatasets.getFormDataSetMap().get(tempDBFName);
                } else {
                    virtualDataset = TaskFileDataOperateUtil.getSingleRegionDataSet(context, filePath, tempDBFName, netFieldDefines, floatingRow, virtualDatasets, batchMode);
                    for (String code : virtualDataset.getFieldMap().keySet()) {
                        SingleFileFieldInfo fieldMapItem1 = (SingleFileFieldInfo)virtualDataset.getFieldMap().get(code);
                        virtualDatasets.getFieldDataSetMap().put(code, virtualDataset);
                        virtualDatasets.getFieldMap().put(code, fieldMapItem1);
                    }
                }
                if (virtualDataset != null && !virtualDatasets.getFieldDataSetMap().containsKey(fieldFlag)) {
                    virtualDatasets.getFieldDataSetMap().put(fieldFlag, virtualDataset);
                    virtualDatasets.getFieldMap().put(fieldFlag, fieldMapItem);
                    virtualDataset.getFieldMap().put(fieldFlag, fieldMapItem);
                }
                String fieldFlag2 = fieldMapItem.getTableCode() + "." + fieldMapItem.getFieldCode();
                if (floatEnumType == 2) {
                    fieldFlag2 = fieldMapItem.getNetTableCode() + "." + fieldMapItem.getNetFieldCode();
                }
                if (dataSet == null) continue;
                fieldDataSetMap.put(fieldFlag2, dataSet);
                allFieldMap.put(fieldFlag2, fieldMapItem);
                dataSet.getFieldMap().put(fieldFlag2, fieldMapItem);
            }
        }
        return dataSets;
    }

    public static ReportRegionDataSetList getRegionDataSetListByTable(TaskDataContext context, String filePath, String singleFormCode, int floatingRow, ReportRegionDataSetList dataSets) throws Exception {
        return TaskFileDataOperateUtil.getRegionDataSetListByTable(context, filePath, singleFormCode, floatingRow, dataSets, false);
    }

    public static ReportRegionDataSetList getRegionDataSetListByTable(TaskDataContext context, String filePath, String singleFormCode, int floatingRow, ReportRegionDataSetList dataSets, boolean batchMode) throws Exception {
        ReportRegionDataSet dataSet;
        Map fieldDataSetMap = dataSets.getFieldDataSetMap();
        Map allFieldMap = dataSets.getFieldMap();
        if (context.getMapingCache().getSingleTableMap().containsKey(singleFormCode) && (dataSet = TaskFileDataOperateUtil.getSingleRegionDataSet(context, filePath, singleFormCode, null, floatingRow, dataSets, batchMode)) != null) {
            SingleFileTableInfo table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get(singleFormCode);
            SingleFileRegionInfo singleRegion = (SingleFileRegionInfo)dataSet.getRegionInfo();
            for (SingleFileFieldInfo field : singleRegion.getFields()) {
                String fieldFlag = singleFormCode + "." + field.getFieldCode();
                SingleFileFieldInfo fieldMapItem = field;
                fieldDataSetMap.put(fieldFlag, dataSet);
                allFieldMap.put(fieldFlag, fieldMapItem);
                dataSet.getFieldMap().put(fieldFlag, fieldMapItem);
            }
            SingleFileRegionInfo fixRegion = table.getRegion();
            if (floatingRow > 0 && singleRegion != fixRegion) {
                for (SingleFileFieldInfo field : fixRegion.getFields()) {
                    String fieldFlag = singleFormCode + "." + field.getFieldCode();
                    SingleFileFieldInfo fieldMapItem = field;
                    fieldDataSetMap.put(fieldFlag, dataSet);
                    allFieldMap.put(fieldFlag, fieldMapItem);
                    dataSet.getFieldMap().put(fieldFlag, fieldMapItem);
                }
            }
        }
        return dataSets;
    }

    public static ReportRegionDataSet getSingleRegionDataSet(TaskDataContext context, String filePath, String singleFormCode, List<FieldData> netFieldDefines, int floatingRow, ReportRegionDataSetList dataSets) throws Exception {
        return TaskFileDataOperateUtil.getSingleRegionDataSet(context, filePath, singleFormCode, netFieldDefines, floatingRow, dataSets, false);
    }

    public static ReportRegionDataSet getSingleRegionDataSet(TaskDataContext context, String filePath, String singleFormCode, List<FieldData> netFieldDefines, int floatingRow, ReportRegionDataSetList dataSets, boolean batchMode) throws Exception {
        ReportRegionDataSet dataSet = null;
        List dataSetList = dataSets.getDataSetList();
        Map formDataSetMap = dataSets.getFormDataSetMap();
        String singleDataSetCode = singleFormCode;
        SingleFileTableInfo table = null;
        SingleFileRegionInfo singleRegion = null;
        String fileFlag = context.getFileFlag();
        String fileName = filePath + fileFlag + singleDataSetCode + ".DBF";
        if (context.getMapingCache().getSingleTableMap().containsKey(singleFormCode)) {
            table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get(singleFormCode);
            singleRegion = table.getRegion();
        }
        if (floatingRow > 0 && null != table && null != table.getRegion() && null != table.getRegion().getSubRegions()) {
            for (SingleFileRegionInfo sRegion : table.getRegion().getSubRegions()) {
                if (sRegion.getFloatingIndex() != floatingRow) continue;
                singleDataSetCode = singleFormCode + "_F" + sRegion.getFloatingIndex();
                fileName = filePath + fileFlag + singleDataSetCode + ".DBF";
                singleRegion = sRegion;
                break;
            }
        }
        if (!formDataSetMap.containsKey(singleDataSetCode)) {
            dataSet = TaskFileDataOperateUtil.getRegionDataSet(context, fileName, singleFormCode, netFieldDefines, floatingRow, singleRegion, batchMode);
            if (dataSet != null) {
                dataSet.setTableInfo((Object)table);
            }
            formDataSetMap.put(singleDataSetCode, dataSet);
            dataSetList.add(dataSet);
            if (floatingRow > 0 && singleRegion != null) {
                String fxiFileName = filePath + fileFlag + singleFormCode + ".DBF";
                ReportRegionDataSet fixDataSet = TaskFileDataOperateUtil.getRegionDataSet(context, fxiFileName, singleFormCode, null, -1, table.getRegion(), batchMode);
                dataSet.setParentDataSet(fixDataSet);
                if (fixDataSet != null) {
                    fixDataSet.setTableInfo((Object)table);
                }
            }
        } else {
            dataSet = (ReportRegionDataSet)formDataSetMap.get(singleDataSetCode);
        }
        return dataSet;
    }

    public static ReportRegionDataSet getRegionDataSet(TaskDataContext context, String fileName, String singleFormCode, List<FieldData> netFieldDefines, int floatingRow, SingleFileRegionInfo singleRegion) throws Exception {
        return TaskFileDataOperateUtil.getRegionDataSet(context, fileName, singleFormCode, netFieldDefines, floatingRow, singleRegion, false);
    }

    public static ReportRegionDataSet getRegionDataSet(TaskDataContext context, String fileName, String singleFormCode, List<FieldData> netFieldDefines, int floatingRow, SingleFileRegionInfo singleRegion, boolean batchMode) throws Exception {
        ReportRegionDataSetImpl dataSet = null;
        HashMap<String, Object> mapSingleFieldList = new HashMap<String, Object>();
        boolean isFMDM = singleFormCode.equalsIgnoreCase("FMDM");
        boolean hasOrderField = context.getMapingCache().getHasOrderField();
        if (!PathUtil.getFileExists((String)fileName)) {
            DbfUtil.createDbfFileBySingleRegion((TaskDataContext)context, (SingleFileRegionInfo)singleRegion, netFieldDefines, (String)fileName, (boolean)true, (int)context.getMapingCache().getZdmLength(), (boolean)isFMDM, (hasOrderField && floatingRow > 0 ? 1 : 0) != 0);
        } else {
            String info = "\u6709\u6570\u636e";
            info = info + "\u8c03\u8bd5";
        }
        IDbfTable dbf = DbfUtil.getDbfTable((String)fileName, (boolean)batchMode);
        dataSet = new ReportRegionDataSetImpl();
        dataSet.setFileName(fileName);
        dataSet.setDataSet(dbf);
        dataSet.setFloatingIndex(floatingRow);
        dataSet.setFormCode(singleFormCode);
        dataSet.setIsDataModified(false);
        dataSet.setIsNewRow(false);
        dataSet.setRegionInfo((Object)singleRegion);
        HashMap<String, SingleFileFieldInfoImpl> fieldMap = new HashMap<String, SingleFileFieldInfoImpl>();
        dataSet.setFieldMap(fieldMap);
        dataSet.setMapFieldList(mapSingleFieldList);
        dataSet.buildRecordCache();
        List indexFields = DbfUtil.getZdmIndexFields((int)context.getMapingCache().getZdmLength());
        if (singleRegion != null) {
            dataSet.setFloatCodeFields(singleRegion.getFloatCodes());
            for (SingleFileFieldInfo singleField : singleRegion.getFields()) {
                mapSingleFieldList.put(singleField.getFieldCode(), singleField);
            }
            for (String floatCode : singleRegion.getFloatCodes()) {
                if (!mapSingleFieldList.containsKey(floatCode)) continue;
                SingleFileFieldInfo mapField = (SingleFileFieldInfo)mapSingleFieldList.get(floatCode);
                IndexFieldDef field = new IndexFieldDef();
                field.setFieldName(floatCode);
                field.setDataType('C');
                field.setFieldLen((short)mapField.getFieldSize());
                indexFields.add(field);
            }
        } else if (netFieldDefines != null) {
            for (int i = 0; i < netFieldDefines.size(); ++i) {
                FieldData field = netFieldDefines.get(i);
                FieldType atype = FieldType.forValue((int)field.getFieldType());
                int fieldLen = field.getFieldSize();
                if (fieldLen > 255) {
                    fieldLen = 255;
                } else if (FieldType.FIELD_TYPE_UUID == atype) {
                    fieldLen = 40;
                }
                SingleFileFieldInfoImpl singleField = new SingleFileFieldInfoImpl();
                singleField.setFieldCode(field.getFieldCode());
                singleField.setTableCode(field.getTableName());
                singleField.setFormCode(field.getTableName());
                singleField.setNetTableCode(field.getTableName());
                singleField.setNetFieldCode(field.getFieldCode());
                singleField.setRegionKey(field.getRegionKey());
                singleField.setFieldType(atype);
                singleField.setFieldSize(fieldLen);
                singleField.setFieldDecimal(field.getFractionDigits());
                singleField.setNetFieldKey(field.getFieldKey());
                String fieldFlag = singleField.getFormCode() + "." + singleField.getFieldCode();
                fieldMap.put(fieldFlag, singleField);
                mapSingleFieldList.put(singleField.getFieldCode(), singleField);
            }
        }
        dataSet.setIndexFields(indexFields);
        return dataSet;
    }

    public static SingleFileRegionInfo getSingleRegionInfo(TaskDataContext context, String singleFormCode, String singleFieldCode, int netFloatingRow) {
        SingleFileRegionInfo singleRegion = null;
        if (context.getMapingCache().getSingleTableMap().containsKey(singleFormCode)) {
            SingleFileTableInfo table = (SingleFileTableInfo)context.getMapingCache().getSingleTableMap().get(singleFormCode);
            if (StringUtils.isNotEmpty((String)singleFieldCode)) {
                List fieldRegions;
                HashMap<String, ArrayList<SingleFileRegionInfo>> formFieldRegionMap = (HashMap<String, ArrayList<SingleFileRegionInfo>>)context.getMapingCache().getSingleFieldRegionMap().get(singleFormCode);
                if (formFieldRegionMap == null) {
                    formFieldRegionMap = new HashMap<String, ArrayList<SingleFileRegionInfo>>();
                    context.getMapingCache().getSingleFieldRegionMap().put(singleFormCode, formFieldRegionMap);
                    for (SingleFileFieldInfo field : table.getRegion().getFields()) {
                        ArrayList<SingleFileRegionInfo> fieldRegions2 = (ArrayList<SingleFileRegionInfo>)formFieldRegionMap.get(field.getFieldCode());
                        if (fieldRegions2 == null) {
                            fieldRegions2 = new ArrayList<SingleFileRegionInfo>();
                            formFieldRegionMap.put(field.getFieldCode(), fieldRegions2);
                        }
                        fieldRegions2.add(table.getRegion());
                    }
                    if (null != table.getRegion().getSubRegions()) {
                        for (SingleFileRegionInfo sRegion : table.getRegion().getSubRegions()) {
                            for (SingleFileFieldInfo field : sRegion.getFields()) {
                                ArrayList<SingleFileRegionInfo> fieldRegions3 = (ArrayList<SingleFileRegionInfo>)formFieldRegionMap.get(field.getFieldCode());
                                if (fieldRegions3 == null) {
                                    fieldRegions3 = new ArrayList<SingleFileRegionInfo>();
                                    formFieldRegionMap.put(field.getFieldCode(), fieldRegions3);
                                }
                                fieldRegions3.add(sRegion);
                            }
                        }
                    }
                }
                if ((fieldRegions = (List)formFieldRegionMap.get(singleFieldCode)) == null) {
                    singleRegion = null;
                } else if (fieldRegions.size() == 0) {
                    singleRegion = null;
                } else if (fieldRegions.size() == 1) {
                    singleRegion = (SingleFileRegionInfo)fieldRegions.get(0);
                } else if (fieldRegions.size() > 1) {
                    singleRegion = null;
                }
            }
            if (singleRegion == null) {
                if (netFloatingRow > 0) {
                    if (null != table.getRegion().getSubRegions()) {
                        for (SingleFileRegionInfo sRegion : table.getRegion().getSubRegions()) {
                            if (netFloatingRow != sRegion.getFloatingIndex()) continue;
                            singleRegion = sRegion;
                            break;
                        }
                    }
                } else {
                    singleRegion = table.getRegion();
                }
            }
        }
        return singleRegion;
    }

    public static void maintainSingleDbfs(TaskDataContext context, String filePath, Map<String, String> uploadEntityZdmKeyMap) throws SingleFileException, DbfException {
        TaskFileDataOperateUtil.loadDocDirs(context, filePath);
        if (context.getMapingCache().getMapConfig() == null) {
            return;
        }
        List tables = context.getMapingCache().getMapConfig().getTableInfos();
        if (tables == null) {
            return;
        }
        for (SingleFileTableInfo singleTable : tables) {
            if (singleTable.getSingleTableType() != 4) continue;
            String fileFlag = context.getFileFlag();
            String fileName = filePath + fileFlag + singleTable.getNetFormCode() + ".DBF";
            if (PathUtil.getFileExists((String)fileName)) {
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u7ef4\u62a4\u9644\u4ef6\u8868\uff1a" + singleTable.getNetFormCode() + "," + fileName);
                TaskFileDataOperateUtil.mainTainDocDbf(context, filePath, fileName, singleTable.getNetFormCode(), uploadEntityZdmKeyMap);
                continue;
            }
            logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u9644\u4ef6\u8868\u65e0DBF\u6587\u4ef6\uff1a" + singleTable.getNetFormCode() + "," + fileName);
            TaskFileDataOperateUtil.mainTainCreateDocDbf(context, filePath, fileName, singleTable.getNetFormCode(), uploadEntityZdmKeyMap);
        }
    }

    private static void loadDocDirs(TaskDataContext context, String filePath) throws SingleFileException {
        String docDirName = context.getTaskDocPath();
        if (StringUtils.isEmpty((String)docDirName)) {
            docDirName = PathUtil.getNewPath((String)filePath, (String)"SYS_DOC");
        }
        Map dirs = SinglePathUtil.getSubDirList((String)docDirName);
        context.getFjZdmDirs().putAll(dirs);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void mainTainDocDbf(TaskDataContext context, String filePath, String dbfFileName, String singleTableFlag, Map<String, String> uploadEntityZdmKeyMap) throws SingleFileException, DbfException {
        String docDirName = context.getTaskDocPath();
        if (StringUtils.isEmpty((String)docDirName)) {
            docDirName = PathUtil.getNewPath((String)filePath, (String)"SYS_DOC");
        }
        try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
            CaseInsensitiveMap<String, String> hasZdms = new CaseInsensitiveMap<String, String>();
            HashSet<String> newZdms = new HashSet<String>();
            for (int i = 0; i < dbf.getRecordCount(); ++i) {
                DataRow dbfRow = dbf.getRecordByIndex(i);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRowByIndexs(dbfRow, null);
                }
                String zdm = dbfRow.getValueString(0);
                hasZdms.put(zdm, zdm);
                if (dbf.isHasLoadAllRec()) continue;
                dbf.clearDataRow(dbfRow);
            }
            for (String zdm : uploadEntityZdmKeyMap.keySet()) {
                if (hasZdms.containsKey(zdm) || context.getFjUploadMode() == 1) continue;
                String zdmDocDir = SinglePathUtil.getNewFilePath((String)docDirName, (String)zdm);
                if (context.getFjZdmDirs().containsKey(zdm)) {
                    zdmDocDir = (String)context.getFjZdmDirs().get(zdm);
                    zdmDocDir = SinglePathUtil.getPath((String)zdmDocDir);
                }
                String tableDocFile = SinglePathUtil.getNewFilePath((String)zdmDocDir, (String)(singleTableFlag + ".ZIP"));
                String tableDocFile1 = SinglePathUtil.getNewFilePath((String)zdmDocDir, (String)(singleTableFlag + ".zip"));
                if (!SinglePathUtil.getFileExists((String)tableDocFile) && !SinglePathUtil.getFileExists((String)tableDocFile1)) continue;
                DataRow dbfRow = dbf.getTable().newRow();
                dbfRow.setValue(0, (Object)zdm);
                dbf.getTable().getRows().add((Object)dbfRow);
                newZdms.add(zdm);
            }
            if (!newZdms.isEmpty()) {
                dbf.saveData();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void mainTainCreateDocDbf(TaskDataContext context, String filePath, String dbfFileName, String singleTableFlag, Map<String, String> uploadEntityZdmKeyMap) throws SingleFileException, DbfException {
        String docDirName = context.getTaskDocPath();
        if (StringUtils.isEmpty((String)docDirName)) {
            docDirName = PathUtil.getNewPath((String)filePath, (String)"SYS_DOC");
        }
        String docDirName2 = "DATA/SYS_DOC";
        HashSet<String> newZdms = new HashSet<String>();
        for (String zdm : uploadEntityZdmKeyMap.keySet()) {
            String tableDocFile1;
            String tableDocFile;
            String zdmDocDir;
            if (context.getFjUploadMode() == 1) {
                zdmDocDir = docDirName2 + "/" + zdm;
                tableDocFile = zdmDocDir + "/" + singleTableFlag + ".ZIP";
                tableDocFile1 = zdmDocDir + "/" + singleTableFlag + ".zip";
                if (!context.getZipParam().getFilterFileNames().contains(tableDocFile) && !context.getZipParam().getFilterFileNames().contains(tableDocFile1)) continue;
                newZdms.add(zdm);
                continue;
            }
            zdmDocDir = SinglePathUtil.getNewFilePath((String)docDirName, (String)zdm);
            if (context.getFjZdmDirs().containsKey(zdm)) {
                zdmDocDir = (String)context.getFjZdmDirs().get(zdm);
                zdmDocDir = SinglePathUtil.getPath((String)zdmDocDir);
            }
            tableDocFile = SinglePathUtil.getNewFilePath((String)zdmDocDir, (String)(singleTableFlag + ".ZIP"));
            tableDocFile1 = SinglePathUtil.getNewFilePath((String)zdmDocDir, (String)(singleTableFlag + ".zip"));
            if (!SinglePathUtil.getFileExists((String)tableDocFile) && !SinglePathUtil.getFileExists((String)tableDocFile1)) continue;
            newZdms.add(zdm);
        }
        if (!newZdms.isEmpty()) {
            DBFCreator creator = new DBFCreator();
            creator.addField("SYS_ZDM", 'C', context.getMapingCache().getZdmLength());
            creator.createTable(dbfFileName);
            try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                for (String zdm : newZdms) {
                    DataRow dbfRow = dbf.getTable().newRow();
                    dbfRow.setValue(0, (Object)zdm);
                    dbf.getTable().getRows().add((Object)dbfRow);
                }
                dbf.saveData();
            }
        }
    }

    public static List<FormDefine> getExistSingleDataForms(TaskDataContext context, String filePath, List<FormDefine> forms) throws SingleFileException {
        ArrayList<FormDefine> dataForms = new ArrayList<FormDefine>();
        Map netFieldMaps = context.getMapingCache().getNetFieldMap();
        block0: for (FormDefine form : forms) {
            String netFormCode = form.getFormCode();
            if (!netFieldMaps.containsKey(netFormCode)) continue;
            if (context.isDeleteEmptyData()) {
                dataForms.add(form);
                continue;
            }
            Map netFormFields = (Map)netFieldMaps.get(netFormCode);
            for (SingleFileFieldInfo fieldMapItem : netFormFields.values()) {
                String singleFormCode;
                String singleDataSetCode = singleFormCode = fieldMapItem.getFormCode();
                if (StringUtils.isEmpty((String)singleFormCode)) {
                    singleFormCode = fieldMapItem.getTableCode();
                }
                if (!context.getMapingCache().getSingleTableMap().containsKey(singleFormCode)) continue;
                String fileFlag = context.getFileFlag();
                String fileName = filePath + fileFlag + singleDataSetCode + ".DBF";
                if (!PathUtil.getFileExists((String)fileName)) continue;
                dataForms.add(form);
                continue block0;
            }
        }
        return dataForms;
    }

    public static List<String> getSingleFormsByMapNetForm(TaskDataContext context, FormDefine form) {
        String netFormCode;
        ArrayList<String> singleFormCodes = new ArrayList<String>();
        Map netFieldMaps = context.getMapingCache().getNetFieldMap();
        if (!netFieldMaps.containsKey(netFormCode = form.getFormCode())) {
            return singleFormCodes;
        }
        Map netFormFields = (Map)netFieldMaps.get(netFormCode);
        for (SingleFileFieldInfo fieldMapItem : netFormFields.values()) {
            String singleFormCode = fieldMapItem.getFormCode();
            if (StringUtils.isEmpty((String)singleFormCode)) {
                singleFormCode = fieldMapItem.getTableCode();
            }
            if (!context.getMapingCache().getSingleTableMap().containsKey(singleFormCode)) continue;
            singleFormCodes.add(singleFormCode);
        }
        return singleFormCodes;
    }

    public static Map<String, List<String>> getSingleFormsByMapNetForms(TaskDataContext context, List<FormDefine> forms) {
        HashMap<String, List<String>> list = new HashMap<String, List<String>>();
        for (FormDefine form : forms) {
            List<String> singleForms = TaskFileDataOperateUtil.getSingleFormsByMapNetForm(context, form);
            list.put(form.getKey(), singleForms);
        }
        return list;
    }

    public static List<FormDefine> getNetFormsExistMap(TaskDataContext context, List<FormDefine> forms) {
        ArrayList<FormDefine> list = new ArrayList<FormDefine>();
        for (FormDefine form : forms) {
            List<String> singleForms = TaskFileDataOperateUtil.getSingleFormsByMapNetForm(context, form);
            if (singleForms == null) continue;
            list.add(form);
        }
        return list;
    }

    public static List<SingleFieldFileInfo> getSingleFileInfoFormFileByZdm(TaskDataContext context, String filePath, String singleFormCode, String zdm) throws SingleDataException {
        String taskDocDir = context.getTaskDocPath();
        if (StringUtils.isEmpty((String)taskDocDir)) {
            taskDocDir = filePath + "SYS_DOC" + File.separator;
        }
        String zdmDocDir = taskDocDir + zdm + File.separator;
        if (context.getFjZdmDirs().containsKey(zdm)) {
            zdmDocDir = (String)context.getFjZdmDirs().get(zdm);
            zdmDocDir = SinglePathUtil.getPath((String)zdmDocDir);
        }
        String docZipFile = zdmDocDir + singleFormCode + ".ZIP";
        try {
            if (!PathUtil.getFileExists((String)docZipFile)) {
                docZipFile = zdmDocDir + singleFormCode + ".zip";
            }
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException((Throwable)e);
        }
        String docFilePath = zdmDocDir + singleFormCode + File.separator;
        return TaskFileDataOperateUtil.getSingleFileInfosFormZipFile(docZipFile, docFilePath, true);
    }

    public static List<String> getSingleFormFileByZdm(TaskDataContext context, String filePath, String singleFormCode, String zdm) throws SingleDataException {
        List<SingleFieldFileInfo> fileInfos = TaskFileDataOperateUtil.getSingleFileInfoFormFileByZdm(context, filePath, singleFormCode, zdm);
        ArrayList<String> list = new ArrayList<String>();
        for (SingleFieldFileInfo fileInfo : fileInfos) {
            list.add(fileInfo.getFilePath());
        }
        return list;
    }

    public static List<String> getSingleFormZipFile(String docZipFile, String docFilePath) throws SingleDataException {
        List<SingleFieldFileInfo> fileInfos = TaskFileDataOperateUtil.getSingleFileInfosFormZipFile(docZipFile, docFilePath, false);
        ArrayList<String> list = new ArrayList<String>();
        for (SingleFieldFileInfo fileInfo : fileInfos) {
            list.add(fileInfo.getFilePath());
        }
        return list;
    }

    public static List<SingleFieldFileInfo> getSingleFileInfosFormZipFile(String docZipFile, String docFilePath, boolean readInfo) throws SingleDataException {
        ArrayList<SingleFieldFileInfo> list;
        block14: {
            list = new ArrayList<SingleFieldFileInfo>();
            try {
                String docZipFile1 = SinglePathUtil.normalize((String)docZipFile);
                if (!PathUtil.getFileExists((String)docZipFile1)) break block14;
                String docFilePath1 = FilenameUtils.normalize(docFilePath);
                SingleSecurityUtils.validatePathManipulation((String)docFilePath1);
                try {
                    ZipUtil.unzipFile((String)docFilePath1, (String)docZipFile1, (String)"GBK");
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                }
                File file2 = new File(docFilePath1);
                if (file2.exists()) {
                    String[] subFiles = file2.list();
                    HashMap<String, SingleFieldFileInfo> fileMap = new HashMap<String, SingleFieldFileInfo>();
                    for (String subFile : subFiles) {
                        SingleFieldFileInfo fjInfo = new SingleFieldFileInfo();
                        fjInfo.setFileName(subFile);
                        fjInfo.setFilePath(docFilePath1 + subFile);
                        fileMap.put(subFile, fjInfo);
                        list.add(fjInfo);
                    }
                    if (readInfo) {
                        File zipFile = new File(docZipFile1);
                        String zipFileName = zipFile.getName();
                        String zipFileNoExt = SinglePathUtil.getFileNoExtensionName((String)zipFileName);
                        String iniFileName = SinglePathUtil.getNewFilePath((String)zipFile.getParent(), (String)(zipFileNoExt + ".Ini"));
                        if (!PathUtil.getFileExists((String)iniFileName) && !PathUtil.getFileExists((String)(iniFileName = SinglePathUtil.getNewFilePath((String)zipFile.getParent(), (String)(zipFileNoExt + ".ini"))))) {
                            iniFileName = SinglePathUtil.getNewFilePath((String)zipFile.getParent(), (String)(zipFileNoExt + ".INI"));
                        }
                        if (SinglePathUtil.getFileExists((String)iniFileName)) {
                            Ini ini = new Ini();
                            ini.loadIniFile(iniFileName);
                            String countCode = ini.readString(zipFileNoExt, "Count", "0");
                            int count = Integer.parseInt(countCode);
                            for (int i = 0; i < count; ++i) {
                                String fileNoExt = ini.readString(zipFileNoExt, i + "_Name", "");
                                String fileExt = ini.readString(zipFileNoExt, i + "_Type", "");
                                String fileKey = ini.readString(zipFileNoExt, i + "_FileKey", "");
                                String category = ini.readString(zipFileNoExt, i + "_Category", "");
                                String fileSecret = ini.readString(zipFileNoExt, i + "_SecretLevel", "");
                                String fjFileName = fileNoExt;
                                if (StringUtils.isNotEmpty((String)fileExt)) {
                                    fjFileName = fileExt.startsWith(".") ? fileNoExt + fileExt : fileNoExt + "." + fileExt;
                                }
                                if (!fileMap.containsKey(fjFileName)) continue;
                                SingleFieldFileInfo fjInfo = (SingleFieldFileInfo)fileMap.get(fjFileName);
                                if (StringUtils.isNotEmpty((String)fileKey)) {
                                    fjInfo.setFileKey(fileKey);
                                }
                                if (StringUtils.isNotEmpty((String)category)) {
                                    fjInfo.setCategory(category);
                                }
                                if (!StringUtils.isNotEmpty((String)fileSecret)) continue;
                                fjInfo.setFileSecret(fileSecret);
                            }
                        }
                    }
                }
            }
            catch (SingleFileException e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException((Throwable)e);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new SingleDataException((Throwable)e);
            }
        }
        return list;
    }

    public static String getSingleFieldTextByZdm(TaskDataContext context, String filePath, String singleFileName, String zdm) throws IOException, SingleFileException {
        String textFilePath;
        String result = null;
        String textFileDir = context.getTaskTxtPath();
        if (StringUtils.isEmpty((String)textFileDir)) {
            textFileDir = filePath + "SYS_TXT" + File.separator;
        }
        if (PathUtil.getFileExists((String)(textFilePath = textFileDir + singleFileName))) {
            String textFilePath1 = FilenameUtils.normalize(textFilePath);
            SingleSecurityUtils.validatePathManipulation((String)textFilePath1);
            try {
                MemStream stream = new MemStream();
                stream.loadFromFile(textFilePath1);
                stream.seek(0L, 0);
                long aSize = stream.getSize();
                if (stream.getSize() == 0L) {
                    return result;
                }
                int count = stream.readInt();
                if ((long)count > aSize - 4L) {
                    count = (int)aSize - 4;
                }
                byte[] data = new byte[count];
                stream.readBuffer(data, 0, count);
                result = new String(data, "GB2312");
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return result;
    }

    public static String getSingleFieldImgByZdm(TaskDataContext context, String filePath, String singleFileName, String zdm) throws IOException, SingleFileException {
        String textFilePath;
        String result = null;
        String imgFileDir = context.getTaskImgPath();
        if (StringUtils.isEmpty((String)imgFileDir)) {
            imgFileDir = filePath + "SYS_IMG" + File.separator;
        }
        result = textFilePath = imgFileDir + singleFileName;
        return result;
    }

    public static void saveSingleFieldTextByZdm(TaskDataContext context, String filePath, String singleFileName, String zdm, String fieldValue) {
        String textFilePath = filePath + singleFileName;
        try {
            if (PathUtil.getFileExists((String)textFilePath)) {
                PathUtil.deleteFile((String)textFilePath);
            }
        }
        catch (SingleFileException e) {
            logger.info("\u5220\u9664\u6587\u4ef6\u5931\u8d25\uff1a" + textFilePath);
        }
        try {
            if (StringUtils.isEmpty((String)fieldValue)) {
                return;
            }
            String textFilePath1 = FilenameUtils.normalize(textFilePath);
            SingleSecurityUtils.validatePathManipulation((String)textFilePath1);
            MemStream stream = new MemStream();
            stream.seek(0L, 0);
            byte[] data = fieldValue.getBytes("GB2312");
            int aSize = data.length;
            stream.writeInt(aSize);
            stream.writeBuffer(data, 0, aSize);
            stream.saveToFile(textFilePath1);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void SaveFileToSingleByZdm(TaskDataContext context, List<String> files, String srcFilePath, String tofilePath, String singleFormCode, String zdm) throws IOException, SingleFileException {
        String docZipFile = tofilePath + singleFormCode + ".ZIP";
        if (PathUtil.getFileExists((String)srcFilePath) && files.size() > 0) {
            String docZipFile1 = FilenameUtils.normalize(docZipFile);
            SingleSecurityUtils.validatePathManipulation((String)docZipFile1);
            try (FileOutputStream outStream = new FileOutputStream(docZipFile1);){
                ZipUtil.zipDirectory((String)srcFilePath, (OutputStream)outStream, null, (String)"GBK");
            }
        }
    }
}

