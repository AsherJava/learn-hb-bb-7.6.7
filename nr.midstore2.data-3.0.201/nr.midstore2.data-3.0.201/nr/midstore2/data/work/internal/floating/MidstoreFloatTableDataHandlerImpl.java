/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.midstore.audit.bean.MidstoreASTable
 *  com.jiuqi.nvwa.midstore.audit.result.bean.dto.MidstoreAuditResultObjectDTO
 *  com.jiuqi.nvwa.midstore.audit.result.common.AuditStatusType
 *  com.jiuqi.nvwa.midstore.audit.result.service.IMidstoreAuditResultObjectService
 *  com.jiuqi.nvwa.midstore.audit.service.IMidstoreASTableService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.extend.MidstoreSchemeInfoExtendDTO
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumItemMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.result.bean.dto.MidstoreResultErrorDTO
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreErrorLevelType
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 *  com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 *  nr.midstore2.core.dataset.clear.IMidstoreDataSetClearService
 *  nr.midstore2.core.definition.bean.MidstoreFileInfo
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 */
package nr.midstore2.data.work.internal.floating;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.ITableDataHandler;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.midstore.audit.bean.MidstoreASTable;
import com.jiuqi.nvwa.midstore.audit.result.bean.dto.MidstoreAuditResultObjectDTO;
import com.jiuqi.nvwa.midstore.audit.result.common.AuditStatusType;
import com.jiuqi.nvwa.midstore.audit.result.service.IMidstoreAuditResultObjectService;
import com.jiuqi.nvwa.midstore.audit.service.IMidstoreASTableService;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
import com.jiuqi.nvwa.midstore.core.definition.bean.extend.MidstoreSchemeInfoExtendDTO;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumItemMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.EnumMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.UnitMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.mapping.ZBMappingInfo;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.result.bean.dto.MidstoreResultErrorDTO;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreErrorLevelType;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import com.jiuqi.nvwa.midstore.work.util.IMidstoreEncryptedFieldService;
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
import java.util.UUID;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.core.dataset.clear.IMidstoreDataSetClearService;
import nr.midstore2.core.definition.bean.MidstoreFileInfo;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreFloatTableDataHandlerImpl
implements ITableDataHandler {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreFloatTableDataHandlerImpl.class);
    private Map<String, Integer> deDataCoumnMap;
    private ReportMidstoreContext context;
    private IMidstoreDataSet bathDataSet;
    private String nrPeriodCode;
    private String nrTableCode;
    private String deTableCode;
    private List<String> nrFieldsArr;
    private Map<String, DEFieldInfo> nrFieldMapDes;
    private Map<String, Map<String, DataField>> nrDataTableFields;
    private Map<String, DimensionValue> dimSetMap;
    private Map<String, String> nrFieldMapBaseDatas;
    private List<String> dimFields;
    private Map<String, String> nrFieldMapDables;
    private Map<String, String> deFieldMapNrs;
    private IRuntimeDataSchemeService dataSchemeSevice;
    private IMidstoreAttachmentService attachmentService;
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private IMidstoreDataSetClearService floatDataClearService;
    private IMidstoreAuditResultObjectService auditResultObjectService;
    private IMidstoreASTableService auditTableService;
    private Map<String, Set<String>> tableFieldList;
    private Map<String, Set<String>> tableUnitList;
    private Map<String, String> tableFormList;
    private Map<String, String> tableRegionList;
    private Map<String, List<DimensionValueSet>> unitDimsList;
    private Map<DimensionValueSet, List<String>> unitFormKeys;
    private Map<String, Set<String>> formKeyTablesList;
    private Metadata<Column> metaData;
    private int importRowCount = 0;
    private Set<String> orgHasDatas;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private MidstoreWorkResultTableData tableResult;
    private DataTable dataTable;
    private MidstoreSchemeInfoExtendDTO schemeExtDto;
    private Map<String, MidstoreAuditResultObjectDTO> auditObjectMap;
    private MidstoreWorkResultObjectData lastObjectResult = null;

    public MidstoreFloatTableDataHandlerImpl() {
    }

    public MidstoreFloatTableDataHandlerImpl(ReportMidstoreContext context, IMidstoreDataSet bathDataSet, String nrPeriodCode, String nrTableCode, String deTableCode, List<String> nrFieldsArr, Map<String, DEFieldInfo> nrFieldMapDes, Map<String, Map<String, DataField>> nrDataTableFields, Map<String, DimensionValue> dimSetMap, Map<String, String> nrFieldMapBaseDatas, List<String> dimFields, Map<String, String> nrFieldMapDables, Map<String, String> deFieldMapNrs, IRuntimeDataSchemeService dataSchemeSevice, IMidstoreAttachmentService attachmentService) {
        MidstoreASTable auditTable;
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
        this.floatDataClearService = (IMidstoreDataSetClearService)BeanUtil.getBean(IMidstoreDataSetClearService.class);
        this.tableFieldList = (Map)context.getExcuteParams().get("TABLEFIELDlIST");
        this.tableUnitList = (Map)context.getExcuteParams().get("TABLEUNITLIST");
        this.tableFormList = (Map)context.getExcuteParams().get("TABLEFORMLIST");
        this.tableRegionList = (Map)context.getExcuteParams().get("TABLEREGIONLIST");
        this.unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
        this.unitFormKeys = (Map)context.getExcuteParams().get("UNITFORMKEYS");
        this.formKeyTablesList = (Map)context.getExcuteParams().get("FORMTABLESLIST");
        this.orgHasDatas = new HashSet<String>();
        this.encryptedFieldService = (IMidstoreEncryptedFieldService)ApplicationContextRegister.getBean(IMidstoreEncryptedFieldService.class);
        this.auditResultObjectService = (IMidstoreAuditResultObjectService)ApplicationContextRegister.getBean(IMidstoreAuditResultObjectService.class);
        this.auditTableService = (IMidstoreASTableService)ApplicationContextRegister.getBean(IMidstoreASTableService.class);
        this.dataTable = dataSchemeSevice.getDataTableByCode(nrTableCode);
        this.tableResult = context.getWorkResult().getPeriodResult().getTableResult(this.dataTable.getKey());
        this.schemeExtDto = context.getExeContext().getMidstoreContext().getSchemeInfo().getExtendDTO();
        this.auditObjectMap = new HashMap<String, MidstoreAuditResultObjectDTO>();
        if (this.schemeExtDto != null && this.schemeExtDto.isUseAudit() && context.getWorkResult().getAuditResult().getStatus() == AuditStatusType.STATUS_ERROR && (auditTable = this.auditTableService.getAuditTableByASAndSourceTable(context.getWorkResult().getAuditResult().getAuditSchemeKey(), context.getExeContext().getSourceTypeId(), this.dataTable.getKey())) != null) {
            List auditObjectList = this.auditResultObjectService.listByTable(context.getWorkResult().getAuditResult().getKey(), auditTable.getKey());
            for (MidstoreAuditResultObjectDTO auditObject : auditObjectList) {
                this.auditObjectMap.put(auditObject.getCode(), auditObject);
            }
        }
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
        MidstoreAuditResultObjectDTO objectAuditResult;
        List<DimensionValueSet> unitDims;
        DataRow row = arg0;
        String nrOrgCode = null;
        if (!this.deDataCoumnMap.containsKey("MDCODE")) return;
        int columIndex = this.deDataCoumnMap.get("MDCODE");
        String orgDataCode2 = row.getString(columIndex);
        if (this.context.getMappingCache().getSrcUnitMappingInfos().containsKey(orgDataCode2)) {
            UnitMappingInfo unitInfo = (UnitMappingInfo)this.context.getMappingCache().getSrcUnitMappingInfos().get(orgDataCode2);
            orgDataCode2 = unitInfo.getUnitCode();
        } else if (this.context.isUseOrgCode() && this.context.getEntityCache().getEntityOrgCodeList().containsKey(orgDataCode2)) {
            orgDataCode2 = ((MidstoreOrgDataDTO)this.context.getEntityCache().getEntityOrgCodeList().get(orgDataCode2)).getCode();
        }
        nrOrgCode = orgDataCode2;
        if (StringUtils.isEmpty((String)nrOrgCode)) {
            this.context.info(logger, "\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u4e3a\u7a7a\uff0c\u8bc6\u522b\u6709\u95ee\u9898");
            return;
        }
        this.orgHasDatas.add(nrOrgCode);
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
        DimensionValueSet unitDim = null;
        if (this.unitDimsList != null && this.unitDimsList.containsKey(nrOrgCode) && (unitDims = this.unitDimsList.get(nrOrgCode)) != null && !unitDims.isEmpty()) {
            boolean isDimSame = false;
            for (int u = 0; u < unitDims.size(); ++u) {
                DimensionValueSet unitDim2 = unitDims.get(u);
                boolean isCurDimSame = true;
                if (unitDim2 != null) {
                    for (int k = 0; k < unitDim2.size(); ++k) {
                        String dimName = unitDim2.getName(k);
                        if ("DATATIME".equalsIgnoreCase(dimName) || "MDCODE".equalsIgnoreCase(dimName)) continue;
                        String dimValue = (String)unitDim2.getValue(k);
                        Integer rowIndex = this.deDataCoumnMap.get(dimName);
                        if (rowIndex == null || rowIndex < 0) continue;
                        String fieldValue = row.getString(rowIndex.intValue());
                        if (!StringUtils.isNotEmpty((String)dimValue) || dimValue.equalsIgnoreCase(fieldValue)) continue;
                        isCurDimSame = false;
                        break;
                    }
                }
                if (!isCurDimSame) continue;
                isDimSame = true;
                unitDim = unitDim2;
                break;
            }
            if (!isDimSame) {
                return;
            }
        }
        if (this.auditObjectMap.containsKey(nrOrgCode) && (objectAuditResult = this.auditObjectMap.get(nrOrgCode)).getStatus() == AuditStatusType.STATUS_ERROR) {
            this.context.info(logger, "\u6570\u636e\u63d0\u53d6\uff1a\u5355\u4f4d\u4ee3\u7801\u7a3d\u6838\u4e0d\u901a\u8fc7\uff0c\u4e0d\u63d0\u53d6:" + nrOrgCode + "," + this.dataTable.getCode());
            return;
        }
        if (this.lastObjectResult != null && nrOrgCode.equalsIgnoreCase(this.lastObjectResult.getObjectDTO().getObjectCode())) {
            this.lastObjectResult.getObjectDTO().setTotalRecordSize(this.lastObjectResult.getObjectDTO().getTotalRecordSize() + 1);
        } else {
            this.lastObjectResult = this.recordObjectResult(nrOrgCode);
        }
        this.tableResult.getTableDTO().setTotalRecordSize(this.tableResult.getTableDTO().getTotalRecordSize() + 1);
        Object[] rowData = new Object[this.nrFieldsArr.size()];
        ArrayList<Object> listRow = new ArrayList<Object>();
        for (int i = 0; i < this.metaData.getColumnCount(); ++i) {
            DataField field;
            String defieldName;
            Column col = this.metaData.getColumn(i);
            Object fieldObject = row.getValue(i);
            String fieldValue = row.getString(i);
            String nrZBCode = defieldName = col.getName();
            String zbFindCode = this.deTableCode + "[" + defieldName + "]";
            String zbFindCode1 = this.nrTableCode + "[" + defieldName + "]";
            if (this.context.getMappingCache().getSrcZbMapingInfos().containsKey(zbFindCode)) {
                ZBMappingInfo zbMapingInfo = (ZBMappingInfo)this.context.getMappingCache().getSrcZbMapingInfos().get(zbFindCode);
                field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                nrZBCode = field.getCode();
            } else if (this.context.getMappingCache().getSrcZbMapingInfos().containsKey(zbFindCode1)) {
                ZBMappingInfo zbMapingInfo = (ZBMappingInfo)this.context.getMappingCache().getSrcZbMapingInfos().get(zbFindCode1);
                field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                nrZBCode = field.getCode();
            } else if (this.context.getMappingCache().getSrcZbMapingInfosOld().containsKey(defieldName)) {
                ZBMappingInfo zbMapingInfo = (ZBMappingInfo)this.context.getMappingCache().getSrcZbMapingInfosOld().get(defieldName);
                field = this.dataSchemeSevice.getDataField(zbMapingInfo.getFieldKey());
                nrZBCode = field.getCode();
            }
            int rowIndex = this.nrFieldsArr.indexOf(nrZBCode);
            if ("DATATIME".equalsIgnoreCase(nrZBCode) || "TIMEKEY".equalsIgnoreCase(nrZBCode)) {
                rowIndex = this.nrFieldsArr.indexOf("DATATIME");
                rowData[rowIndex] = this.nrPeriodCode;
                continue;
            }
            if ("MDCODE".equalsIgnoreCase(nrZBCode)) {
                rowIndex = this.nrFieldsArr.indexOf("MD_ORG");
                String deOrgCode = fieldValue;
                nrOrgCode = fieldValue;
                if (this.context.getMappingCache().getSrcUnitMappingInfos().containsKey(deOrgCode)) {
                    UnitMappingInfo unitInfo = (UnitMappingInfo)this.context.getMappingCache().getSrcUnitMappingInfos().get(deOrgCode);
                    nrOrgCode = unitInfo.getUnitCode();
                } else if (this.context.isUseOrgCode() && this.context.getEntityCache().getEntityOrgCodeList().containsKey(deOrgCode)) {
                    nrOrgCode = ((MidstoreOrgDataDTO)this.context.getEntityCache().getEntityOrgCodeList().get(deOrgCode)).getCode();
                }
                rowData[rowIndex] = nrOrgCode;
                continue;
            }
            if (rowIndex < 0) continue;
            DEFieldInfo deField = this.nrFieldMapDes.get(defieldName);
            if (deField != null) {
                DataField dataField = null;
                if (this.nrDataTableFields.containsKey(this.nrTableCode)) {
                    Map<String, DataField> fieldCodeList = this.nrDataTableFields.get(this.nrTableCode);
                    if (fieldCodeList.containsKey(nrZBCode)) {
                        dataField = fieldCodeList.get(nrZBCode);
                    }
                } else {
                    dataField = this.dataSchemeSevice.getDataFieldByTableKeyAndCode(this.nrTableCode, nrZBCode);
                }
                if (deField.getDataType() != DEDataType.INTEGER && deField.getDataType() != DEDataType.FLOAT) {
                    if (deField.getDataType() == DEDataType.DATE) {
                        boolean isTime = false;
                        if (dataField != null && dataField.getDataFieldType() == DataFieldType.DATE_TIME) {
                            isTime = true;
                        }
                        if (fieldObject instanceof Date) {
                            Date date = (Date)fieldObject;
                            fieldValue = isTime ? this.dateTimeFormatter.format(date) : this.dateFormatter.format(date);
                        } else if (fieldObject instanceof GregorianCalendar) {
                            GregorianCalendar calendar = (GregorianCalendar)fieldObject;
                            Date date = calendar.getTime();
                            fieldValue = isTime ? this.dateTimeFormatter.format(date) : this.dateFormatter.format(date);
                        } else {
                            fieldValue = null;
                        }
                    } else if (deField.getDataType() == DEDataType.FILE) {
                        DEAttachMent attachMent = null;
                        if (fieldObject instanceof DEAttachMent) {
                            attachMent = (DEAttachMent)fieldObject;
                        } else if (fieldObject != null) {
                            this.context.info(logger, "\u6587\u4ef6\u578b\u5b57\u6bb5\u5b58\u5728\u95ee\u9898\uff1a" + defieldName);
                        }
                        if (attachMent != null && attachMent.getData() != null) {
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
                            try {
                                fieldValue = this.attachmentService.saveFileFieldDataToNR(attachMent.getData(), fieldFileInfo);
                            }
                            catch (Exception e) {
                                this.context.error(logger, e.getMessage(), e);
                                fieldValue = null;
                            }
                        } else {
                            fieldValue = null;
                        }
                    } else if (deField.getDataType() == DEDataType.BOOLEAN) {
                        if ("1".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.TRUE;
                            fieldValue = "\u662f";
                        } else if ("0".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.FALSE;
                            fieldValue = "\u5426";
                        } else if ("TRUE".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.TRUE;
                            fieldValue = "\u662f";
                        } else if ("FALSE".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.FALSE;
                            fieldValue = "\u5426";
                        } else if ("\u662f".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.TRUE;
                        } else if ("\u5426".equalsIgnoreCase(fieldValue)) {
                            fieldObject = Boolean.FALSE;
                        } else if (StringUtils.isEmpty((String)fieldValue)) {
                            fieldObject = null;
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("\u4e0d\u8bc6\u522b\u7684\u5e03\u5c14\u503c\uff1a" + fieldValue + "," + defieldName);
                            }
                            fieldObject = null;
                        }
                    } else if (this.nrFieldMapBaseDatas.containsKey(nrZBCode) && StringUtils.isNotEmpty((String)fieldValue)) {
                        EnumMappingInfo enumMapping;
                        String baseDataCode = this.nrFieldMapBaseDatas.get(nrZBCode);
                        if (this.context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = (EnumMappingInfo)this.context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getSrcItemMappings().containsKey(fieldValue)) {
                            fieldValue = ((EnumItemMappingInfo)enumMapping.getSrcItemMappings().get(fieldValue)).getItemCode();
                        }
                    }
                }
                if (deField.getIsEncrypted()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(deField.getName() + "=" + fieldValue);
                    }
                    fieldValue = this.encryptedFieldService.decrypt(this.context.getMidstoreScheme(), fieldValue);
                    if (logger.isDebugEnabled()) {
                        logger.debug(deField.getName() + "=" + fieldValue);
                    }
                }
            }
            rowData[rowIndex] = fieldValue;
        }
        DimensionValueSet rowDim = null;
        rowDim = unitDim != null ? new DimensionValueSet(unitDim) : new DimensionValueSet();
        for (String dimName : this.dimFields) {
            String unitDimValue;
            DimensionValue dim = this.dimSetMap.get(dimName);
            if (dim == null) continue;
            Integer rowIndex = this.nrFieldsArr.indexOf(dim.getName());
            if (rowIndex != null && rowIndex >= 0) {
                if (!this.context.getDataSourceDTO().isUseDimensionField()) {
                    if (!this.context.getDimEntityCache().getEntityDimAndEntityIds().containsKey(dimName)) continue;
                    unitDimValue = null;
                    if (unitDim != null && unitDim.getValue(dimName) != null) {
                        unitDimValue = unitDim.getValue(dimName).toString();
                    }
                    if (!StringUtils.isNotEmpty((String)unitDimValue)) continue;
                    rowData[rowIndex.intValue()] = unitDimValue;
                    rowDim.setValue(dimName, (Object)unitDimValue);
                    continue;
                }
                if (!this.context.getDimEntityCache().getEntityDimAndEntityIds().containsKey(dimName)) continue;
                unitDimValue = null;
                if (unitDim != null && unitDim.getValue(dimName) != null) {
                    unitDimValue = unitDim.getValue(dimName).toString();
                }
                if (!StringUtils.isNotEmpty((String)unitDimValue)) continue;
                rowData[rowIndex.intValue()] = unitDimValue;
                rowDim.setValue(dimName, (Object)unitDimValue);
                continue;
            }
            unitDimValue = null;
            if (unitDim != null && unitDim.getValue(dimName) != null) {
                unitDimValue = unitDim.getValue(dimName).toString();
            }
            if (StringUtils.isEmpty(unitDimValue)) {
                unitDimValue = dim.getValue();
            }
            listRow.add(unitDimValue);
            rowDim.setValue(dimName, (Object)unitDimValue);
        }
        if (this.unitFormKeys != null && this.formKeyTablesList != null && this.unitFormKeys.containsKey(rowDim)) {
            List<String> formKeys = this.unitFormKeys.get(rowDim);
            HashSet<String> tableCodes = new HashSet<String>();
            if (formKeys != null) {
                Iterator iterator = formKeys.iterator();
                while (iterator.hasNext()) {
                    String formKey = (String)iterator.next();
                    Set<String> tables = this.formKeyTablesList.get(formKey);
                    if (tables == null || tables.isEmpty()) continue;
                    tableCodes.addAll(tables);
                }
            } else {
                this.context.info(logger, "\u6570\u636e\u8bfb\u53d6\u51fa\u9519\uff1a\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\uff1a" + nrOrgCode + "," + rowDim.toString());
            }
            if (!tableCodes.isEmpty() && !tableCodes.contains(this.nrTableCode)) {
                return;
            }
            if (!this.formKeyTablesList.isEmpty() && tableCodes.isEmpty()) {
                return;
            }
        }
        for (Object obj : rowData) {
            listRow.add(obj);
        }
        DimensionValueSet dimSet = null;
        try {
            dimSet = this.bathDataSet.importDatas(listRow);
        }
        catch (Exception e) {
            dimSet = null;
            this.context.error(logger, e.getMessage(), e);
            this.lastObjectResult.getObjectDTO().setErrorRecordSize(this.lastObjectResult.getObjectDTO().getErrorRecordSize() + 1);
            this.tableResult.getTableDTO().setErrorRecordSize(this.tableResult.getTableDTO().getErrorRecordSize() + 1);
            this.recordErrorData(nrOrgCode, e.getMessage(), null);
            this.tableResult.getErrorObjectCodes().add(nrOrgCode);
            this.context.getWorkResult().getPeriodResult().getErrorWorkTableKeys().add(this.dataTable.getKey());
            this.context.getWorkResult().getPeriodResult().getErrorObjectCodes().add(nrOrgCode);
            this.context.getSourceResult().getErrorObjectCodes().add(nrOrgCode);
            this.context.getSourceResult().getErrorTableKeys().add(this.dataTable.getKey());
        }
        if (dimSet == null || dimSet.size() <= 0) return;
        ++this.importRowCount;
    }

    public void finish() throws DataExchangeException {
        if (this.importRowCount > 0) {
            this.context.info(logger, "\u6570\u636e\u63a5\u6536\uff1a" + this.deTableCode + ",\u884c\u6570\uff1a" + String.valueOf(this.importRowCount));
            try {
                this.bathDataSet.commit();
            }
            catch (Exception e) {
                throw new DataExchangeException(e.getMessage(), (Throwable)e);
            }
        }
        if (this.context.isDeleteEmptyData() && this.context.getExchangeEnityCodes().size() > 0) {
            DataTable dataTable = this.dataSchemeSevice.getDataTableByCode(this.nrTableCode);
            ArrayList<Object> deleteOrgCodes = new ArrayList<Object>();
            ArrayList<Object> unDeleteOrgCodes = new ArrayList<Object>();
            for (Object nrOrgCode : this.context.getExchangeEnityCodes()) {
                if (this.orgHasDatas.contains(nrOrgCode)) continue;
                if (this.tableUnitList != null && this.tableUnitList.size() > 0) {
                    if (this.tableUnitList.containsKey(this.nrTableCode)) {
                        Set<String> unitList = this.tableUnitList.get(this.nrTableCode);
                        if (!unitList.contains(nrOrgCode)) {
                            unDeleteOrgCodes.add(nrOrgCode);
                            continue;
                        }
                    } else {
                        unDeleteOrgCodes.add(nrOrgCode);
                        continue;
                    }
                }
                this.context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
                deleteOrgCodes.add(nrOrgCode);
            }
            if (!unDeleteOrgCodes.isEmpty()) {
                if (unDeleteOrgCodes.size() > 100) {
                    logger.info("\u4e2d\u95f4\u5e93\u6570\u636e\u5b8c\u6574\u8986\u76d6,\u7a7a\u8868\u5220\u9664,\u65e0\u6743\u9650\u5220\u9664\u8868" + this.nrTableCode + "\u6570\u636e\uff1a\u5355\u4f4d\u4e2a\u6570" + unDeleteOrgCodes.size());
                } else {
                    logger.info("\u4e2d\u95f4\u5e93\u6570\u636e\u5b8c\u6574\u8986\u76d6,\u7a7a\u8868\u5220\u9664,\u65e0\u6743\u9650\u5220\u9664\u8868" + this.nrTableCode + "\u6570\u636e\uff1a\u5355\u4f4d\u4e2a\u6570" + unDeleteOrgCodes.size() + "," + ((Object)unDeleteOrgCodes).toString());
                }
            }
            if (deleteOrgCodes.size() > 0) {
                ArrayList<DimensionValueSet> deleteDims = new ArrayList<DimensionValueSet>();
                if (this.unitDimsList != null && !this.unitDimsList.isEmpty()) {
                    for (Object orgCode : deleteOrgCodes) {
                        if (!this.unitDimsList.containsKey(orgCode)) continue;
                        deleteDims.add(this.unitDimsList.get(orgCode).get(0));
                    }
                } else {
                    DimensionValueSet dims = new DimensionValueSet();
                    for (String dimName : this.dimSetMap.keySet()) {
                        DimensionValue dimValue = this.dimSetMap.get(dimName);
                        if (this.context.getEntityTypeName().equalsIgnoreCase(dimName)) {
                            dims.setValue(dimName, deleteOrgCodes);
                            continue;
                        }
                        dims.setValue(dimName, (Object)dimValue.getValue());
                    }
                    deleteDims.add(dims);
                }
                try {
                    if (deleteOrgCodes.size() > 100) {
                        logger.info("\u4e2d\u95f4\u5e93\u6570\u636e\u5b8c\u6574\u8986\u76d6,\u7a7a\u8868\u5220\u9664,\u5220\u9664\u8868" + this.nrTableCode + "\u6570\u636e\uff1a\u5355\u4f4d\u4e2a\u6570" + deleteOrgCodes.size());
                    } else {
                        logger.info("\u4e2d\u95f4\u5e93\u6570\u636e\u5b8c\u6574\u8986\u76d6,\u7a7a\u8868\u5220\u9664,\u5220\u9664\u8868" + this.nrTableCode + "\u6570\u636e\uff1a\u5355\u4f4d\u4e2a\u6570" + deleteOrgCodes.size() + "," + ((Object)deleteOrgCodes).toString());
                    }
                    for (DimensionValueSet dims : deleteDims) {
                        if (dataTable == null) continue;
                        if (this.tableRegionList != null && this.tableRegionList.containsKey(this.nrTableCode)) {
                            String regionkey = this.tableRegionList.get(this.nrTableCode);
                            this.floatDataClearService.clearRegionData(this.context.getFormSchemeKey(), regionkey, dims);
                            continue;
                        }
                        this.floatDataClearService.clearTableData(this.context.getFormSchemeKey(), dataTable.getKey(), dims);
                    }
                }
                catch (Exception e) {
                    this.context.error(logger, e.getMessage(), e);
                    this.context.getWorkResult().getPeriodResult().getErrorWorkTableKeys().add(dataTable.getKey());
                    this.recordErrorData(null, e.getMessage(), null);
                    throw new DataExchangeException((Throwable)e);
                }
            }
        }
    }

    private MidstoreWorkResultObjectData recordObjectResult(String nrOrgCode) {
        MidstoreWorkResultObjectData objectResult = this.tableResult.getObjectResult(nrOrgCode);
        if (objectResult == null) {
            objectResult = new MidstoreWorkResultObjectData();
            objectResult.getObjectDTO().setKey(UUID.randomUUID().toString());
            objectResult.getObjectDTO().setResultKey(this.tableResult.getTableDTO().getResultKey());
            objectResult.getObjectDTO().setObjectCode(nrOrgCode);
            objectResult.getObjectDTO().setObjectTitle(nrOrgCode);
            objectResult.getObjectDTO().setSourceTableCode(this.tableResult.getTableDTO().getSourceTableCode());
            objectResult.getObjectDTO().setSourceTableKey(this.tableResult.getTableDTO().getSourceTableKey());
            objectResult.getObjectDTO().setSourceType(this.tableResult.getTableDTO().getSourceType());
            objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
            objectResult.getObjectDTO().setErrorRecordSize(0);
            objectResult.getObjectDTO().setTotalRecordSize(1);
            this.tableResult.addObjectResult(objectResult);
            this.context.getWorkResult().getPeriodResult().getObjectCodes().add(nrOrgCode);
            this.context.getSourceResult().getObjectCodes().add(nrOrgCode);
            this.context.getSourceResult().getWorkTableKeys().add(this.tableResult.getTableDTO().getSourceTableKey());
        } else {
            objectResult.getObjectDTO().setTotalRecordSize(objectResult.getObjectDTO().getTotalRecordSize() + 1);
        }
        return objectResult;
    }

    private void recordErrorData(String nrOrgCode, String message, String errorFieldCode) {
        MidstoreResultErrorDTO errorResult = new MidstoreResultErrorDTO();
        errorResult.setKey(UUID.randomUUID().toString());
        errorResult.setResultKey(this.tableResult.getTableDTO().getResultKey());
        errorResult.setErrorDetail(message);
        errorResult.setErrorDimCode(null);
        errorResult.setErrorFieldCode(errorFieldCode);
        errorResult.setErrorLevelType(MidstoreErrorLevelType.LEVEL_FIELD);
        errorResult.setErrorObjectCode(nrOrgCode);
        errorResult.setErrorTableCode(this.tableResult.getTableDTO().getSourceTableCode());
        errorResult.setSourceTableKey(this.tableResult.getTableDTO().getSourceTableKey());
        errorResult.setMessage(message);
        errorResult.setResultKey(this.tableResult.getTableDTO().getResultKey());
        this.context.getWorkResult().getPeriodResult().getErrorList().add(errorResult);
        this.context.getSourceResult().getErrorTableKeys().add(this.dataTable.getKey());
    }
}

