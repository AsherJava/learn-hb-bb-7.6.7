/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
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
 *  nr.midstore2.core.dataset.IMidstoreRegionDataSetReader
 *  nr.midstore2.core.util.IMidstoreAttachmentService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore2.data.work.internal.floating.reader;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataWriter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultObjectData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class MidstoreFloatTableDataSetReaderImpl
implements IMidstoreRegionDataSetReader {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreFloatTableDataSetReaderImpl.class);
    private ReportMidstoreContext context;
    private DETableModel tableModel;
    private List<ExportFieldDefine> netFieldList;
    private DataTable dataTable;
    private String nrTableCode;
    private String dePeriodCode;
    private int corpKeyFieldId;
    private Map<String, List<DimensionValueSet>> unitDimsList;
    private Map<String, Integer> nrFieldMap;
    private Map<String, String> nrFieldMapBaseDatas;
    private List<ExportFieldDefine> nrExportFiels;
    private Map<String, DimensionValue> dimSetMap;
    private Map<String, DEFieldInfo> deFieldMap;
    private Map<String, DataField> fieldCodeList;
    private MidstoreWorkResultTableData tableResult;
    private IDataWriter dataWriter;
    private List<DEFieldInfo> fieldInfos;
    private Map<String, Integer> nr2DeFieldIndex;
    private List<String> dimFields;
    private IMidstoreAttachmentService attachmentService;
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TEMP_TABLE = "TempAssistantTable";
    private static final String READ_ERROR = "\u4e2d\u95f4\u5e93\u8bfb\u53d6\u6570\u636e\u6709\u5f02\u5e38\uff1a";
    private static final boolean NULL_VALUE_POST = true;
    @Value(value="${jiuqi.nvwa.midstore.postByReader:true}")
    private boolean midstorePostByReader;
    private MidstoreWorkResultObjectData lastObjectResult = null;

    public MidstoreFloatTableDataSetReaderImpl(ReportMidstoreContext context, DETableModel tableModel, DataTable dataTable, String dePeriodCode, List<ExportFieldDefine> netFieldList, Map<String, Integer> nrFieldMap, Map<String, String> nrFieldMapBaseDatas, List<ExportFieldDefine> nrExportFiels, Map<String, DimensionValue> dimSetMap, Map<String, DEFieldInfo> deFieldMap, Map<String, DataField> fieldCodeList, MidstoreWorkResultTableData tableResult, int corpKeyFieldId, IDataWriter dataWriter, List<DEFieldInfo> fieldInfos, Map<String, Integer> nr2DeFieldIndex, List<String> dimFields) {
        this.context = context;
        this.tableModel = tableModel;
        this.netFieldList = netFieldList;
        this.dataTable = dataTable;
        this.nrTableCode = dataTable.getCode();
        this.dePeriodCode = dePeriodCode;
        this.nrFieldMap = nrFieldMap;
        this.nrFieldMapBaseDatas = nrFieldMapBaseDatas;
        this.nrExportFiels = nrExportFiels;
        this.dimSetMap = dimSetMap;
        this.fieldCodeList = fieldCodeList;
        this.deFieldMap = deFieldMap;
        this.tableResult = tableResult;
        this.dataWriter = dataWriter;
        this.fieldInfos = fieldInfos;
        this.nr2DeFieldIndex = nr2DeFieldIndex;
        this.dimFields = dimFields;
        this.corpKeyFieldId = corpKeyFieldId;
        this.unitDimsList = null;
        if (context.getExcuteParams().containsKey("UNITDIMSLIST")) {
            this.unitDimsList = (Map)context.getExcuteParams().get("UNITDIMSLIST");
        }
        this.attachmentService = (IMidstoreAttachmentService)ApplicationContextRegister.getBean(IMidstoreAttachmentService.class);
        this.encryptedFieldService = (IMidstoreEncryptedFieldService)ApplicationContextRegister.getBean(IMidstoreEncryptedFieldService.class);
    }

    public boolean needRowKey() {
        return true;
    }

    public void start(List<FieldDefine> fieldDefines) {
    }

    public void readRowData(List<Object> dataRow) {
        List<Object> floatDatas2 = dataRow;
        if (floatDatas2.isEmpty()) {
            this.context.info(logger, "\u6570\u636e\u63d0\u4f9b\uff1a\u6307\u6807\u8868" + this.dataTable.getCode() + "\u6570\u636e\u96c6\u65e0\u6570\u636e\u5b57\u6bb5\uff1a");
            return;
        }
        Object[] floatDatas = floatDatas2.toArray();
        if (floatDatas.length != this.netFieldList.size()) {
            this.context.info(logger, "\u6570\u636e\u96c6\u51fa\u9519\uff1a\u6307\u6807\u5b57\u6bb5\u548c\u6570\u636e\u96c6\u5217\u6570\u4e0d\u4e00\u81f4");
        }
        String nrOrgCode = "";
        if (this.corpKeyFieldId >= 0) {
            nrOrgCode = (String)floatDatas[this.corpKeyFieldId];
        }
        if (StringUtils.isEmpty((CharSequence)nrOrgCode)) {
            return;
        }
        if (!this.context.getExchangeEnityCodes().contains(nrOrgCode)) {
            return;
        }
        if (this.lastObjectResult != null && nrOrgCode.equalsIgnoreCase(this.lastObjectResult.getObjectDTO().getObjectCode())) {
            this.lastObjectResult.getObjectDTO().setTotalRecordSize(this.lastObjectResult.getObjectDTO().getTotalRecordSize() + 1);
        } else {
            this.lastObjectResult = this.recordObjectResult(this.context, nrOrgCode, this.tableResult);
        }
        this.tableResult.getTableDTO().setTotalRecordSize(this.tableResult.getTableDTO().getTotalRecordSize() + 1);
        String deOrgCode = nrOrgCode;
        if (this.context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode)) {
            deOrgCode = ((UnitMappingInfo)this.context.getMappingCache().getUnitMappingInfos().get(nrOrgCode)).getUnitMapingCode();
        } else if (this.context.isUseOrgCode() && this.context.getEntityCache().getEntityCodeList().containsKey(nrOrgCode)) {
            deOrgCode = ((MidstoreOrgDataDTO)this.context.getEntityCache().getEntityCodeList().get(nrOrgCode)).getOrgCode();
        }
        this.context.getWorkResult().getMidstoreTableUnits().add(nrOrgCode);
        if (this.unitDimsList != null && this.unitDimsList.containsKey(nrOrgCode)) {
            List<DimensionValueSet> unitDims = this.unitDimsList.get(nrOrgCode);
            boolean dimIsSame = false;
            for (DimensionValueSet unitDim : unitDims) {
                boolean oneDimIsSame = true;
                for (int k = 0; k < unitDim.size(); ++k) {
                    String dimName = unitDim.getName(k);
                    String dimValue = (String)unitDim.getValue(k);
                    if (!this.nrFieldMap.containsKey(dimName)) continue;
                    int fieldId = this.nrFieldMap.get(dimName);
                    String fieldValue = (String)floatDatas[fieldId];
                    if (!StringUtils.isNotEmpty((CharSequence)dimValue) || dimValue.equalsIgnoreCase(fieldValue)) continue;
                    oneDimIsSame = false;
                    break;
                }
                if (!oneDimIsSame) continue;
                dimIsSame = true;
                break;
            }
            if (!dimIsSame) {
                return;
            }
        }
        StringBuilder fieldSb = new StringBuilder();
        Object[] deRowData = new Object[this.fieldInfos.size()];
        for (int i = 0; i < this.nrExportFiels.size(); ++i) {
            DEFieldInfo deField;
            Object fieldObject;
            String nrFieldCode;
            block77: {
                ExportFieldDefine exportField = this.nrExportFiels.get(i);
                nrFieldCode = exportField.getCode();
                String nrTableCode1 = exportField.getTableCode();
                int id1 = nrFieldCode.indexOf(46);
                if (id1 > 0) {
                    nrFieldCode = nrFieldCode.substring(id1 + 1, nrFieldCode.length());
                }
                String mapCode = nrTableCode1 + "[" + nrFieldCode + "]";
                String deFieldName = nrFieldCode;
                if (this.context.getMappingCache().getZbMapingInfos().containsKey(mapCode)) {
                    deFieldName = ((ZBMappingInfo)this.context.getMappingCache().getZbMapingInfos().get(mapCode)).getMappingzb();
                } else if (this.context.getMappingCache().getZbMapingInfosOld().containsKey(nrFieldCode)) {
                    ZBMappingInfo mapInfo = (ZBMappingInfo)this.context.getMappingCache().getZbMapingInfosOld().get(nrFieldCode);
                    if (this.dataTable.getCode().equalsIgnoreCase(mapInfo.getTable())) {
                        deFieldName = mapInfo.getMappingzb();
                    }
                }
                if ("TIMEKEY".equalsIgnoreCase(nrFieldCode) || "MDCODE".equalsIgnoreCase(nrFieldCode) || "DATATIME".equalsIgnoreCase(nrFieldCode) || this.dimSetMap.containsKey(nrFieldCode) || !this.deFieldMap.containsKey(deFieldName)) continue;
                fieldObject = floatDatas[i];
                String fieldValue = (String)fieldObject;
                deField = this.deFieldMap.get(deFieldName);
                if (deField.getDataType() == DEDataType.INTEGER) {
                    fieldObject = StringUtils.isEmpty((CharSequence)fieldValue) ? null : Integer.valueOf(Integer.parseInt(fieldValue));
                } else if (deField.getDataType() == DEDataType.FLOAT) {
                    fieldObject = StringUtils.isEmpty((CharSequence)fieldValue) ? null : Double.valueOf(Double.parseDouble(fieldValue));
                } else if (deField.getDataType() == DEDataType.DATE) {
                    DataField nrDataField;
                    boolean isTime = false;
                    if (this.fieldCodeList.containsKey(nrFieldCode) && (nrDataField = this.fieldCodeList.get(nrFieldCode)).getDataFieldType() == DataFieldType.DATE_TIME) {
                        isTime = true;
                    }
                    if (StringUtils.isEmpty((CharSequence)fieldValue)) {
                        fieldObject = null;
                    } else {
                        try {
                            if (isTime) {
                                fieldObject = this.dateTimeFormatter.parse(fieldValue);
                                break block77;
                            }
                            fieldObject = this.dateFormatter.parse(fieldValue);
                        }
                        catch (ParseException e) {
                            this.context.error(logger, "\u6307\u6807" + nrFieldCode + "\u503c," + fieldValue + ",\u4e0d\u662f\u65f6\u671f\u683c\u5f0f\uff1a" + e.getMessage(), e);
                        }
                    }
                } else if (deField.getDataType() == DEDataType.FILE) {
                    String groupFileKey = fieldValue;
                    if (StringUtils.isNotEmpty((CharSequence)fieldValue)) {
                        try {
                            String fieldKey = null;
                            if (this.fieldCodeList.containsKey(nrFieldCode)) {
                                DataField nrDataField = this.fieldCodeList.get(nrFieldCode);
                                fieldKey = nrDataField.getKey();
                            }
                            byte[] fileData = this.attachmentService.getFieldDataFromNR(groupFileKey, fieldKey, this.context.getTaskDefine().getDataScheme(), this.context.getTaskDefine().getKey());
                            DEAttachMent attachMent = new DEAttachMent(deOrgCode + "_" + this.tableModel.getTableInfo().getName() + "_" + deField.getName() + ".zip", fileData);
                            fieldObject = attachMent;
                        }
                        catch (Exception e) {
                            this.context.error(logger, "\u6307\u6807" + nrFieldCode + "\u503c," + fieldValue + ",\u65e0\u6cd5\u4e0b\u8f7d\u9644\u4ef6\uff1a" + e.getMessage(), e);
                        }
                    } else {
                        fieldObject = null;
                    }
                } else if (deField.getDataType() == DEDataType.BOOLEAN) {
                    if ("1".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.TRUE;
                    } else if ("0".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.FALSE;
                    } else if ("TRUE".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.TRUE;
                    } else if ("FALSE".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.FALSE;
                    } else if ("\u662f".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.TRUE;
                    } else if ("\u5426".equalsIgnoreCase(fieldValue)) {
                        fieldObject = Boolean.FALSE;
                    } else if (StringUtils.isEmpty((CharSequence)fieldValue)) {
                        fieldObject = null;
                    } else {
                        if (logger.isDebugEnabled()) {
                            this.context.info(logger, "\u4e0d\u8bc6\u522b\u7684\u5e03\u5c14\u503c\uff1a" + fieldValue + "," + deFieldName);
                        }
                        fieldObject = null;
                    }
                } else if (this.nrFieldMapBaseDatas.containsKey(nrFieldCode) && StringUtils.isNotEmpty((CharSequence)fieldValue)) {
                    EnumMappingInfo enumMapping;
                    String baseDataCode = this.nrFieldMapBaseDatas.get(nrFieldCode);
                    if (this.context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = (EnumMappingInfo)this.context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getItemMappings().containsKey(fieldValue)) {
                        fieldObject = ((EnumItemMappingInfo)enumMapping.getItemMappings().get(fieldValue)).getMappingCode();
                    }
                }
            }
            if (deField.getIsEncrypted() && fieldObject != null) {
                fieldObject = this.encryptedFieldService.encrypt(this.context.getMidstoreScheme(), (String)fieldObject);
            }
            if (!this.nr2DeFieldIndex.containsKey(nrFieldCode)) continue;
            int id = this.nr2DeFieldIndex.get(nrFieldCode);
            deRowData[id] = fieldObject;
            if (fieldObject == null) {
                fieldSb.append(nrFieldCode).append("=").append("null").append(",");
                continue;
            }
            fieldSb.append(nrFieldCode).append("=").append(fieldObject.toString()).append(",");
        }
        for (String nrFieldCode : this.dimFields) {
            int id;
            String fieldObject = null;
            if (this.nr2DeFieldIndex.containsKey(nrFieldCode)) {
                id = this.nr2DeFieldIndex.get(nrFieldCode);
                if ("MDCODE".equalsIgnoreCase(nrFieldCode)) {
                    fieldObject = deOrgCode;
                } else if ("MD_ORG".equalsIgnoreCase(nrFieldCode)) {
                    fieldObject = deOrgCode;
                } else if ("DATATIME".equalsIgnoreCase(nrFieldCode)) {
                    fieldObject = this.dePeriodCode;
                } else if (this.nrFieldMap.containsKey(nrFieldCode)) {
                    int fieldId = this.nrFieldMap.get(nrFieldCode);
                    fieldObject = (String)floatDatas[fieldId];
                } else if (this.dimSetMap.containsKey(nrFieldCode)) {
                    fieldObject = this.dimSetMap.get(nrFieldCode).getValue();
                }
                deRowData[id] = fieldObject;
            } else if ("MD_ORG".equalsIgnoreCase(nrFieldCode)) {
                id = this.nr2DeFieldIndex.get("MDCODE");
                deRowData[id] = deOrgCode;
                fieldObject = deOrgCode;
            }
            if (fieldObject == null) {
                fieldSb.append(nrFieldCode).append("=").append("null").append(",");
                continue;
            }
            fieldSb.append(nrFieldCode).append("=").append(fieldObject.toString()).append(",");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("insert:" + fieldSb.toString());
        }
        try {
            this.dataWriter.insert(deRowData);
        }
        catch (DataExchangeException e) {
            this.context.error(logger, "\u63d2\u5165\u5230\u4e2d\u95f4\u5e93\u6570\u636e\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    public void finish() {
    }

    private MidstoreWorkResultObjectData recordObjectResult(ReportMidstoreContext context, String nrOrgCode, MidstoreWorkResultTableData tableResult) {
        MidstoreWorkResultObjectData objectResult = tableResult.getObjectResult(nrOrgCode);
        if (objectResult == null) {
            objectResult = new MidstoreWorkResultObjectData();
            objectResult.getObjectDTO().setKey(UUID.randomUUID().toString());
            objectResult.getObjectDTO().setResultKey(tableResult.getTableDTO().getResultKey());
            objectResult.getObjectDTO().setObjectCode(nrOrgCode);
            objectResult.getObjectDTO().setObjectTitle(nrOrgCode);
            objectResult.getObjectDTO().setSourceTableCode(tableResult.getTableDTO().getSourceTableCode());
            objectResult.getObjectDTO().setSourceTableKey(tableResult.getTableDTO().getSourceTableKey());
            objectResult.getObjectDTO().setSourceType(tableResult.getTableDTO().getSourceType());
            objectResult.getObjectDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
            objectResult.getObjectDTO().setErrorRecordSize(0);
            objectResult.getObjectDTO().setTotalRecordSize(1);
            tableResult.addObjectResult(objectResult);
            context.getWorkResult().getPeriodResult().getObjectCodes().add(nrOrgCode);
            context.getSourceResult().getObjectCodes().add(nrOrgCode);
            context.getSourceResult().getWorkTableKeys().add(tableResult.getTableDTO().getSourceTableKey());
        } else {
            objectResult.getObjectDTO().setTotalRecordSize(objectResult.getObjectDTO().getTotalRecordSize() + 1);
        }
        return objectResult;
    }

    private void recordErrorData(ReportMidstoreContext context, String nrOrgCode, String message, String errorFieldCode, MidstoreWorkResultTableData tableResult) {
        MidstoreResultErrorDTO errorResult = new MidstoreResultErrorDTO();
        errorResult.setKey(UUID.randomUUID().toString());
        errorResult.setResultKey(tableResult.getTableDTO().getResultKey());
        errorResult.setErrorDetail(message);
        errorResult.setErrorDimCode(null);
        errorResult.setErrorFieldCode(errorFieldCode);
        errorResult.setErrorLevelType(MidstoreErrorLevelType.LEVEL_FIELD);
        errorResult.setErrorObjectCode(nrOrgCode);
        errorResult.setErrorTableCode(tableResult.getTableDTO().getSourceTableCode());
        errorResult.setSourceTableKey(tableResult.getTableDTO().getSourceTableKey());
        errorResult.setMessage(message);
        errorResult.setResultKey(tableResult.getTableDTO().getResultKey());
        context.getWorkResult().getPeriodResult().getErrorList().add(errorResult);
        context.getSourceResult().getErrorTableKeys().add(tableResult.getTableDTO().getSourceTableKey());
    }
}

