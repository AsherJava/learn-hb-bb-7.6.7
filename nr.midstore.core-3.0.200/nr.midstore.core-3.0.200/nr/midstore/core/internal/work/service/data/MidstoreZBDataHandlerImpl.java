/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package nr.midstore.core.internal.work.service.data;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
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
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreFileInfo;
import nr.midstore.core.definition.bean.mapping.EnumMappingInfo;
import nr.midstore.core.definition.bean.mapping.UnitMappingInfo;
import nr.midstore.core.util.IMidstoreAttachmentService;
import nr.midstore.core.util.IMidstoreEncryptedFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreZBDataHandlerImpl
implements ITableDataHandler {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreZBDataHandlerImpl.class);
    private Map<String, Integer> deDataCoumnMap;
    private MidstoreContext context;
    private IMidstoreDataSet bathDataSet;
    private String nrPeriodCode;
    private String nrTableCode;
    private String deTableCode;
    private List<String> nrFieldsArr;
    private Map<String, DEZBInfo> nrFieldMapDes;
    private Map<String, Map<String, DataField>> nrDataTableFields;
    private Map<String, DimensionValue> dimSetMap;
    private Map<String, String> nrFieldMapBaseDatas;
    private List<String> dimFields;
    private Map<String, String> nrFieldMapDables;
    private Map<String, String> deFieldMapNrs;
    private Map<String, DataField> fieldCodeList;
    private IRuntimeDataSchemeService dataSchemeSevice;
    private IMidstoreAttachmentService attachmentService;
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private Map<String, Set<String>> tableFieldList;
    private Map<String, Set<String>> tableUnitList;
    private Map<String, String> tableFormList;
    private Map<String, List<DimensionValueSet>> unitDimsList;
    private Metadata<Column> metaData;
    private int importRowCount = 0;
    private Set<String> orgHasDatas;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public MidstoreZBDataHandlerImpl() {
    }

    public MidstoreZBDataHandlerImpl(MidstoreContext context, IMidstoreDataSet bathDataSet, String nrPeriodCode, String nrTableCode, String deTableCode, List<String> nrFieldsArr, Map<String, DEZBInfo> nrFieldMapDes, Map<String, Map<String, DataField>> nrDataTableFields, Map<String, DimensionValue> dimSetMap, Map<String, String> nrFieldMapBaseDatas, List<String> dimFields, Map<String, String> nrFieldMapDables, Map<String, String> deFieldMapNrs, IRuntimeDataSchemeService dataSchemeSevice, IMidstoreAttachmentService attachmentService) {
        this.context = context;
        this.bathDataSet = bathDataSet;
        this.nrPeriodCode = nrPeriodCode;
        this.nrTableCode = nrTableCode;
        this.deTableCode = deTableCode;
        this.nrFieldsArr = nrFieldsArr;
        this.nrFieldMapDes = nrFieldMapDes;
        this.nrDataTableFields = nrDataTableFields;
        this.dimSetMap = dimSetMap;
        this.nrFieldMapBaseDatas = nrFieldMapBaseDatas;
        this.dimFields = dimFields;
        this.nrFieldMapDables = nrFieldMapDables;
        this.deFieldMapNrs = deFieldMapNrs;
        this.dataSchemeSevice = dataSchemeSevice;
        this.attachmentService = attachmentService;
        this.encryptedFieldService = (IMidstoreEncryptedFieldService)ApplicationContextRegister.getBean(IMidstoreEncryptedFieldService.class);
        this.fieldCodeList = nrDataTableFields.get(nrTableCode);
        this.tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDLIST");
        this.tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        this.tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        this.unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
        this.orgHasDatas = new HashSet<String>();
    }

    public void start(Metadata<Column> arg0) throws DataExchangeException {
        this.importRowCount = 0;
        this.metaData = arg0;
        this.deDataCoumnMap = new HashMap<String, Integer>();
        for (int i = 0; i < this.metaData.getColumnCount(); ++i) {
            Column col = this.metaData.getColumn(i);
            this.deDataCoumnMap.put(col.getName(), i);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void handle(DataRow arg0) throws DataExchangeException {
        List<DimensionValueSet> unitDims;
        DataRow row = arg0;
        String nrOrgCode = null;
        if (!this.deDataCoumnMap.containsKey("MDCODE")) return;
        int columIndex = this.deDataCoumnMap.get("MDCODE");
        String orgDataCode2 = row.getString(columIndex);
        if (this.context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
            UnitMappingInfo unitInfo = this.context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
            orgDataCode2 = unitInfo.getUnitCode();
        }
        if (StringUtils.isEmpty((String)(nrOrgCode = orgDataCode2))) {
            logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
            return;
        }
        this.context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
        if (this.context.getExchangeEnityCodes().size() > 0 && !this.context.getExchangeEnityCodes().contains(nrOrgCode)) {
            return;
        }
        if (this.tableUnitList != null && this.tableUnitList.size() > 0) {
            if (!this.tableUnitList.containsKey(this.nrTableCode)) return;
            Set<String> unitList = this.tableUnitList.get(this.nrTableCode);
            if (!unitList.contains(nrOrgCode)) {
                return;
            }
        }
        if (StringUtils.isEmpty((String)nrOrgCode)) {
            logger.info("\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
            return;
        }
        Object[] rowData = new Object[this.nrFieldsArr.size()];
        for (int i = 0; i < this.metaData.getColumnCount(); ++i) {
            int rowIndex;
            String defieldName;
            Column col = this.metaData.getColumn(i);
            Iterator<String> fieldObject = row.getValue(i);
            String fieldValue = row.getString(i);
            String nrZBCode = defieldName = col.getName();
            if ("MDCODE".equalsIgnoreCase(defieldName) || "DATATIME".equalsIgnoreCase(defieldName) || !this.deFieldMapNrs.containsKey(defieldName) || !this.nrFieldMapDables.containsKey(nrZBCode = this.deFieldMapNrs.get(defieldName)) || (rowIndex = this.nrFieldsArr.indexOf(nrZBCode)) < 0) continue;
            DEZBInfo deField = this.nrFieldMapDes.get(defieldName);
            if (deField != null) {
                if (deField.getDataType() != DEDataType.INTEGER && deField.getDataType() != DEDataType.FLOAT) {
                    if (deField.getDataType() == DEDataType.DATE) {
                        if (fieldObject instanceof Date) {
                            Date date = (Date)((Object)fieldObject);
                            fieldValue = this.dateFormatter.format(date);
                        } else if (fieldObject instanceof GregorianCalendar) {
                            GregorianCalendar calendar = (GregorianCalendar)((Object)fieldObject);
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
                                if (this.fieldCodeList.containsKey(nrZBCode)) {
                                    dataField = this.fieldCodeList.get(nrZBCode);
                                }
                                MidstoreFileInfo fieldFileInfo = new MidstoreFileInfo();
                                fieldFileInfo.setDataSchemeKey(this.context.getTaskDefine().getDataScheme());
                                if (dataField != null) {
                                    fieldFileInfo.setFieldKey(dataField.getKey());
                                }
                                if (this.tableFormList.containsKey(this.nrTableCode)) {
                                    fieldFileInfo.setFormKey(this.tableFormList.get(this.nrTableCode));
                                }
                                fieldFileInfo.setFormSchemeKey(this.context.getFormSchemeKey());
                                Map<String, DimensionValue> fieldDimSetMap = this.dimSetMap;
                                fieldDimSetMap.get(this.context.getEntityTypeName()).setValue(nrOrgCode);
                                fieldFileInfo.setDimensionSet(fieldDimSetMap);
                                fieldFileInfo.setTaskKey(this.context.getTaskDefine().getKey());
                                fieldValue = this.attachmentService.saveFileFieldDataToNR(attachMent.getData(), fieldFileInfo);
                            }
                            catch (Exception e) {
                                logger.error("\u6587\u4ef6\u578b\u5b57\u6bb5" + defieldName + "\u51fa\u9519\uff1a" + e.getMessage(), e);
                                fieldValue = null;
                            }
                        } else {
                            fieldValue = null;
                        }
                    } else if (this.nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                        EnumMappingInfo enumMapping;
                        String baseDataCode = this.nrFieldMapBaseDatas.get(nrZBCode);
                        if (this.context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = this.context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                            fieldValue = enumMapping.getSrcItemMappings().get(fieldValue).getItemCode();
                        }
                    }
                }
                if (deField.getIsEncrypted()) {
                    fieldValue = this.encryptedFieldService.decrypt(this.context.getMidstoreScheme(), fieldValue);
                }
            }
            rowData[rowIndex] = fieldValue;
        }
        DimensionValueSet unitDim = null;
        if (this.unitDimsList != null && this.unitDimsList.containsKey(nrOrgCode) && (unitDims = this.unitDimsList.get(nrOrgCode)) != null && !unitDims.isEmpty()) {
            unitDim = unitDims.get(0);
        }
        ArrayList<Object> listRow = new ArrayList<Object>();
        for (String dimName : this.dimFields) {
            DimensionValue dim = this.dimSetMap.get(dimName);
            if (dim == null) continue;
            if ("DATATIME".equalsIgnoreCase(dimName)) {
                listRow.add(this.nrPeriodCode);
                continue;
            }
            if ("MD_ORG".equalsIgnoreCase(dimName)) {
                listRow.add(nrOrgCode);
                continue;
            }
            Integer columIndex2 = this.deDataCoumnMap.get(dimName);
            if (columIndex2 != null && columIndex2 > 0) {
                listRow.add(row.getString(columIndex2.intValue()));
                continue;
            }
            String unitDimValue = null;
            if (unitDim != null && unitDim.getValue(dimName) != null) {
                unitDimValue = unitDim.getValue(dimName).toString();
            }
            if (StringUtils.isNotEmpty(unitDimValue)) {
                listRow.add(unitDimValue);
                continue;
            }
            listRow.add(dim.getValue());
        }
        for (int j = this.dimFields.size(); j < rowData.length; ++j) {
            Object obj = rowData[j];
            listRow.add(obj);
        }
        DimensionValueSet dimSet = null;
        try {
            this.orgHasDatas.add(nrOrgCode);
            dimSet = this.bathDataSet.importDatas(listRow);
        }
        catch (Exception e) {
            dimSet = null;
            logger.error(e.getMessage(), e);
        }
        if (dimSet == null || dimSet.size() <= 0) return;
        ++this.importRowCount;
    }

    public void finish() throws DataExchangeException {
        if (this.context.isDeleteEmptyData() && this.context.getExchangeEnityCodes().size() > 0) {
            for (String nrOrgCode : this.context.getExchangeEnityCodes()) {
                List<DimensionValueSet> unitDims;
                if (this.orgHasDatas.contains(nrOrgCode)) continue;
                boolean needDelete = true;
                if (this.tableUnitList != null && this.tableUnitList.size() > 0) {
                    if (this.tableUnitList.containsKey(this.nrTableCode)) {
                        Set<String> unitList = this.tableUnitList.get(this.nrTableCode);
                        if (!unitList.contains(nrOrgCode)) {
                            needDelete = false;
                        }
                    } else {
                        needDelete = false;
                    }
                }
                if (!needDelete) continue;
                Object[] rowData = new Object[this.nrFieldsArr.size()];
                for (int k = 0; k < rowData.length; ++k) {
                    rowData[k] = "";
                }
                DimensionValueSet unitDim = null;
                if (this.unitDimsList != null && this.unitDimsList.containsKey(nrOrgCode) && (unitDims = this.unitDimsList.get(nrOrgCode)) != null && !unitDims.isEmpty()) {
                    unitDim = unitDims.get(0);
                }
                ArrayList<Object> listRow = new ArrayList<Object>();
                for (String dimName : this.dimFields) {
                    DimensionValue dim = this.dimSetMap.get(dimName);
                    if (dim == null) continue;
                    if ("DATATIME".equalsIgnoreCase(dimName)) {
                        listRow.add(this.nrPeriodCode);
                        continue;
                    }
                    if ("MD_ORG".equalsIgnoreCase(dimName)) {
                        listRow.add(nrOrgCode);
                        continue;
                    }
                    if (unitDim != null) {
                        listRow.add(unitDim.getValue(dimName));
                        continue;
                    }
                    listRow.add(dim.getValue());
                }
                for (int j = this.dimFields.size(); j < rowData.length; ++j) {
                    Object obj = rowData[j];
                    listRow.add(obj);
                }
                DimensionValueSet dimSet = null;
                try {
                    dimSet = this.bathDataSet.importDatas(listRow);
                }
                catch (Exception e) {
                    dimSet = null;
                    logger.error(e.getMessage(), e);
                }
                if (dimSet == null || dimSet.size() <= 0) continue;
                ++this.importRowCount;
            }
        }
        if (this.importRowCount > 0) {
            logger.info("\u6570\u636e\u63a5\u6536\uff1a" + this.deTableCode + ",\u884c\u6570\uff1a" + String.valueOf(this.importRowCount));
            try {
                this.bathDataSet.commit();
            }
            catch (Exception e) {
                throw new DataExchangeException(e.getMessage(), (Throwable)e);
            }
        }
    }
}

