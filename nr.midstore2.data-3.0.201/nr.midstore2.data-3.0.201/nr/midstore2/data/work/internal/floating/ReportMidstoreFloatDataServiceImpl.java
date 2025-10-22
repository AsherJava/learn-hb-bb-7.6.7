/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumItemMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.definition.bean.MidstoreFileInfo
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 */
package nr.midstore2.data.work.internal.floating;

import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumItemMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.definition.bean.MidstoreFileInfo;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import nr.midstore2.data.work.floating.IReportMidstoreFloatDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ReportMidstoreFloatDataServiceImpl
implements IReportMidstoreFloatDataService {
    private static final Logger logger = LoggerFactory.getLogger(ReportMidstoreFloatDataServiceImpl.class);
    @Autowired
    private IMidstoreAttachmentService attachmentService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void saveFloatDataToNR(ReportMidstoreContext context, IMidstoreDataSet bathDataSet, String nrPeriodCode, String nrTableCode, String deTableCode, List<String> nrFieldsArr, Map<String, DEFieldInfo> nrFieldMapDes, Map<String, Map<String, DataField>> nrDataTableFields, Map<String, DimensionValue> dimSetMap, Map<String, String> nrFieldMapBaseDatas, List<String> dimFields, MemoryDataSet memoryDataSet) throws Exception {
        int importRowCount = 0;
        Map tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        Map tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        Metadata metaData = memoryDataSet.getMetadata();
        HashMap<String, Integer> deDataCoumnMap = new HashMap<String, Integer>();
        for (int i = 0; i < metaData.getColumnCount(); ++i) {
            Column col = metaData.getColumn(i);
            deDataCoumnMap.put(col.getName(), i);
        }
        for (DataRow row : memoryDataSet) {
            DimensionValueSet dimSet;
            Set unitList;
            String nrOrgCode = null;
            if (deDataCoumnMap.containsKey("MDCODE")) {
                int columIndex = (Integer)deDataCoumnMap.get("MDCODE");
                String orgDataCode2 = row.getString(columIndex);
                if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
                    UnitMappingInfo unitInfo = (UnitMappingInfo)context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
                    orgDataCode2 = unitInfo.getUnitCode();
                } else if (context.isUseOrgCode() && context.getEntityCache().getEntityOrgCodeList().containsKey(orgDataCode2)) {
                    orgDataCode2 = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityOrgCodeList().get(orgDataCode2)).getCode();
                }
                nrOrgCode = orgDataCode2;
            }
            Object[] rowData = new Object[nrFieldsArr.size()];
            ArrayList<Object> listRow = new ArrayList<Object>();
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                String nrTable;
                DataField field;
                String defieldName;
                Column col = metaData.getColumn(i);
                Object fieldObject = row.getValue(i);
                String fieldValue = row.getString(i);
                String nrZBCode = defieldName = col.getName();
                String zbFindCode = deTableCode + "[" + defieldName + "]";
                String zbFindCode1 = nrTableCode + "[" + defieldName + "]";
                if (context.getMappingCache().getSrcZbMapingInfos().containsKey(zbFindCode)) {
                    ZBMappingInfo zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(zbFindCode);
                    field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                    nrTable = zbMapingInfo.getTable();
                    nrZBCode = field.getCode();
                } else if (context.getMappingCache().getSrcZbMapingInfos().containsKey(zbFindCode1)) {
                    ZBMappingInfo zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfos().get(zbFindCode1);
                    field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                    nrTable = zbMapingInfo.getTable();
                    nrZBCode = field.getCode();
                } else if (context.getMappingCache().getSrcZbMapingInfosOld().containsKey(defieldName)) {
                    ZBMappingInfo zbMapingInfo = (ZBMappingInfo)context.getMappingCache().getSrcZbMapingInfosOld().get(defieldName);
                    field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                    nrTable = zbMapingInfo.getTable();
                    nrZBCode = field.getCode();
                }
                int rowIndex = nrFieldsArr.indexOf(nrZBCode);
                if ("DATATIME".equalsIgnoreCase(nrZBCode) || "TIMEKEY".equalsIgnoreCase(nrZBCode)) {
                    rowIndex = nrFieldsArr.indexOf("DATATIME");
                    rowData[rowIndex] = nrPeriodCode;
                    continue;
                }
                if ("MDCODE".equalsIgnoreCase(nrZBCode)) {
                    rowIndex = nrFieldsArr.indexOf("MD_ORG");
                    String deOrgCode = fieldValue;
                    nrOrgCode = fieldValue;
                    if (context.getMappingCache().getSrcUnitMappingInfos().containsKey(deOrgCode)) {
                        UnitMappingInfo unitInfo = (UnitMappingInfo)context.getMappingCache().getSrcUnitMappingInfos().get(deOrgCode);
                        nrOrgCode = unitInfo.getUnitCode();
                    } else if (context.isUseOrgCode() && context.getEntityCache().getEntityOrgCodeList().containsKey(deOrgCode)) {
                        nrOrgCode = ((MidstoreOrgDataDTO)context.getEntityCache().getEntityOrgCodeList().get(deOrgCode)).getCode();
                    }
                    rowData[rowIndex] = nrOrgCode;
                    continue;
                }
                if (rowIndex < 0) continue;
                DEFieldInfo deField = nrFieldMapDes.get(defieldName);
                if (deField != null) {
                    if (deField.getDataType() != DEDataType.INTEGER && deField.getDataType() != DEDataType.FLOAT) {
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
                                context.info(logger, "\u6587\u4ef6\u578b\u5b57\u6bb5\u5b58\u5728\u95ee\u9898\uff1a" + defieldName);
                            }
                            if (attachMent != null && attachMent.getData() != null) {
                                DataField dataField = null;
                                if (nrDataTableFields.containsKey(nrTableCode)) {
                                    Map<String, DataField> fieldCodeList = nrDataTableFields.get(nrTableCode);
                                    if (fieldCodeList.containsKey(nrZBCode)) {
                                        dataField = fieldCodeList.get(nrZBCode);
                                    }
                                } else {
                                    dataField = this.dataSchemeSevice.getDataFieldByTableKeyAndCode(nrTableCode, nrZBCode);
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
                            } else {
                                fieldValue = null;
                            }
                        } else if (nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                            EnumMappingInfo enumMapping;
                            String baseDataCode = nrFieldMapBaseDatas.get(nrZBCode);
                            if (context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = (EnumMappingInfo)context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                                fieldValue = ((EnumItemMappingInfo)enumMapping.getSrcItemMappings().get(fieldValue)).getItemCode();
                            }
                        }
                    }
                    if (deField.getIsEncrypted()) {
                        fieldValue = this.encryptedFieldService.decrypt(context.getMidstoreScheme(), fieldValue);
                    }
                }
                rowData[rowIndex] = fieldValue;
            }
            for (String dimName : dimFields) {
                int rowIndex;
                DimensionValue dim = dimSetMap.get(dimName);
                if (dim == null || (rowIndex = nrFieldsArr.indexOf(dim.getName())) >= 0) continue;
                listRow.add(dim.getValue());
            }
            for (Object obj : rowData) {
                listRow.add(obj);
            }
            if (StringUtils.isEmpty((String)nrOrgCode)) {
                context.info(logger, "\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
                continue;
            }
            context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
            if (context.getExchangeEnityCodes().size() > 0 && !context.getExchangeEnityCodes().contains(nrOrgCode) || tableUnitList != null && tableUnitList.size() > 0 && (!tableUnitList.containsKey(nrTableCode) || !(unitList = (Set)tableUnitList.get(nrTableCode)).contains(nrOrgCode)) || (dimSet = bathDataSet.importDatas(listRow)) == null || dimSet.size() <= 0) continue;
            ++importRowCount;
        }
        if (importRowCount > 0) {
            context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + deTableCode + ",\u884c\u6570\uff1a" + String.valueOf(importRowCount));
            bathDataSet.commit();
        }
    }
}

