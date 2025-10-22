/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType
 *  com.jiuqi.bi.core.midstore.dataexchange.enums.TableType
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter
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
package nr.midstore2.data.work.internal.fix.reader;

import com.jiuqi.bi.core.midstore.dataexchange.enums.DEDataType;
import com.jiuqi.bi.core.midstore.dataexchange.enums.TableType;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEAttachMent;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEZBInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IZBWriter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import nr.midstore2.core.dataset.IMidstoreRegionDataSetReader;
import nr.midstore2.core.util.IMidstoreAttachmentService;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreFixRegionDataSetReaderImpl
implements IMidstoreRegionDataSetReader {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreFixRegionDataSetReaderImpl.class);
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
    private Set<String> tableUnitSet;
    private List<String> dimFeilds;
    private List<ExportFieldDefine> nrExportFiels;
    private Map<String, DimensionValue> dimSetMap;
    private Map<String, DEZBInfo> deFieldMap;
    private Map<String, DataField> fieldCodeList;
    private Map<String, Object> repeatFeildValues;
    private MidstoreWorkResultTableData tableResult;
    private IZBWriter zbWriter;
    private IMidstoreAttachmentService attachmentService;
    private IMidstoreEncryptedFieldService encryptedFieldService;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MidstoreFixRegionDataSetReaderImpl(ReportMidstoreContext context, DETableModel tableModel, DataTable dataTable, String dePeriodCode, List<ExportFieldDefine> netFieldList, Map<String, Integer> nrFieldMap, Map<String, String> nrFieldMapBaseDatas, Set<String> tableUnitSet, List<String> dimFeilds, List<ExportFieldDefine> nrExportFiels, Map<String, DimensionValue> dimSetMap, Map<String, DEZBInfo> deFieldMap, Map<String, Object> repeatFeildValues, Map<String, DataField> fieldCodeList, MidstoreWorkResultTableData tableResult, int corpKeyFieldId, IZBWriter zbWriter) {
        this.context = context;
        this.tableModel = tableModel;
        this.netFieldList = netFieldList;
        this.dataTable = dataTable;
        this.nrTableCode = dataTable.getCode();
        this.dePeriodCode = dePeriodCode;
        this.nrFieldMap = nrFieldMap;
        this.nrFieldMapBaseDatas = nrFieldMapBaseDatas;
        this.tableUnitSet = tableUnitSet;
        this.dimFeilds = dimFeilds;
        this.nrExportFiels = nrExportFiels;
        this.dimSetMap = dimSetMap;
        this.fieldCodeList = fieldCodeList;
        this.deFieldMap = deFieldMap;
        this.repeatFeildValues = repeatFeildValues;
        this.tableResult = tableResult;
        this.zbWriter = zbWriter;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
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
        if (!this.context.getExchangeEnityCodes().contains(nrOrgCode)) {
            return;
        }
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
        MidstoreWorkResultObjectData objectResult = this.recordObjectResult(this.context, nrOrgCode, this.tableResult);
        this.tableResult.getTableDTO().setTotalRecordSize(this.tableResult.getTableDTO().getTotalRecordSize() + 1);
        String deOrgCode = nrOrgCode;
        if (this.context.getMappingCache().getUnitMappingInfos().containsKey(nrOrgCode)) {
            deOrgCode = ((UnitMappingInfo)this.context.getMappingCache().getUnitMappingInfos().get(nrOrgCode)).getUnitMapingCode();
        } else if (this.context.isUseOrgCode() && this.context.getEntityCache().getEntityCodeList().containsKey(nrOrgCode)) {
            deOrgCode = ((MidstoreOrgDataDTO)this.context.getEntityCache().getEntityCodeList().get(nrOrgCode)).getOrgCode();
        }
        if (this.tableUnitSet != null && !this.tableUnitSet.contains(nrOrgCode)) {
            return;
        }
        ArrayList<String> dimFieldValues = new ArrayList<String>();
        String dimFieldsCode = "";
        if (this.context.getDataSourceDTO().isUseDimensionField() && this.tableModel.getTableInfo().getType() == TableType.MDZB) {
            StringBuilder sb = new StringBuilder();
            for (String dimName : this.dimFeilds) {
                int fieldId = this.nrFieldMap.get(dimName);
                String fieldValue = (String)floatDatas[fieldId];
                dimFieldValues.add(fieldValue);
                sb.append(dimName).append("=").append(fieldValue).append(",");
            }
            dimFieldsCode = sb.toString();
            logger.debug("\u5bfc\u5165\u60c5\u666f\u503c\uff1a" + sb.toString());
        }
        int i = 0;
        while (true) {
            block45: {
                DEZBInfo deField;
                String fieldValue;
                Object fieldObject;
                String deFieldName;
                String nrFieldCode;
                block44: {
                    block62: {
                        EnumMappingInfo enumMapping;
                        block56: {
                            block61: {
                                block60: {
                                    block59: {
                                        block58: {
                                            block57: {
                                                block54: {
                                                    block55: {
                                                        block52: {
                                                            DataField nrDataField;
                                                            block53: {
                                                                block49: {
                                                                    block50: {
                                                                        block51: {
                                                                            block46: {
                                                                                block47: {
                                                                                    block48: {
                                                                                        if (i >= this.nrExportFiels.size()) {
                                                                                            return;
                                                                                        }
                                                                                        ExportFieldDefine exportField = this.nrExportFiels.get(i);
                                                                                        nrFieldCode = exportField.getCode();
                                                                                        String nrTableCode1 = exportField.getTableCode();
                                                                                        int id1 = nrFieldCode.indexOf(46);
                                                                                        if (id1 > 0) {
                                                                                            nrFieldCode = nrFieldCode.substring(id1 + 1, nrFieldCode.length());
                                                                                        }
                                                                                        String mapCode = nrTableCode1 + "[" + nrFieldCode + "]";
                                                                                        deFieldName = nrFieldCode;
                                                                                        if (this.context.getMappingCache().getZbMapingInfosOld().containsKey(nrFieldCode)) {
                                                                                            deFieldName = ((ZBMappingInfo)this.context.getMappingCache().getZbMapingInfosOld().get(nrFieldCode)).getMappingzb();
                                                                                        } else if (this.context.getMappingCache().getZbMapingInfos().containsKey(mapCode)) {
                                                                                            deFieldName = ((ZBMappingInfo)this.context.getMappingCache().getZbMapingInfos().get(mapCode)).getMappingzb();
                                                                                        }
                                                                                        if ("TIMEKEY".equalsIgnoreCase(nrFieldCode) || "MDCODE".equalsIgnoreCase(nrFieldCode) || "DATATIME".equalsIgnoreCase(nrFieldCode) || this.dimSetMap.containsKey(nrFieldCode) || !this.deFieldMap.containsKey(deFieldName)) break block45;
                                                                                        fieldObject = floatDatas[i];
                                                                                        fieldValue = (String)fieldObject;
                                                                                        deField = this.deFieldMap.get(deFieldName);
                                                                                        if (deField.getDataType() != DEDataType.INTEGER) break block46;
                                                                                        if (!StringUtils.isEmpty((CharSequence)fieldValue)) break block47;
                                                                                        if (!this.context.isPostNullValue()) break block48;
                                                                                        fieldObject = null;
                                                                                        break block44;
                                                                                    }
                                                                                    fieldObject = 0;
                                                                                    break block45;
                                                                                }
                                                                                int fieldIntValue = Integer.parseInt(fieldValue);
                                                                                fieldObject = fieldIntValue;
                                                                                if (fieldIntValue != 0 || this.context.isPostZeroValue()) break block44;
                                                                                break block45;
                                                                            }
                                                                            if (deField.getDataType() != DEDataType.FLOAT) break block49;
                                                                            if (!StringUtils.isEmpty((CharSequence)fieldValue)) break block50;
                                                                            if (!this.context.isPostNullValue()) break block51;
                                                                            fieldObject = null;
                                                                            break block44;
                                                                        }
                                                                        fieldObject = 0;
                                                                        break block45;
                                                                    }
                                                                    double fieldDoubleObject = Double.parseDouble(fieldValue);
                                                                    fieldObject = fieldDoubleObject;
                                                                    if (fieldDoubleObject != 0.0 || this.context.isPostZeroValue()) break block44;
                                                                    break block45;
                                                                }
                                                                if (deField.getDataType() != DEDataType.DATE) break block52;
                                                                if (!StringUtils.isEmpty((CharSequence)fieldValue)) break block53;
                                                                fieldObject = null;
                                                                if (this.context.isPostNullValue()) break block44;
                                                                break block45;
                                                            }
                                                            boolean isTime = false;
                                                            if (this.fieldCodeList.containsKey(nrFieldCode) && (nrDataField = this.fieldCodeList.get(nrFieldCode)).getDataFieldType() == DataFieldType.DATE_TIME) {
                                                                isTime = true;
                                                            }
                                                            try {
                                                                if (isTime) {
                                                                    fieldObject = this.dateTimeFormatter.parse(fieldValue);
                                                                    break block44;
                                                                }
                                                                fieldObject = this.dateFormatter.parse(fieldValue);
                                                            }
                                                            catch (ParseException e) {
                                                                this.context.error(logger, "\u6307\u6807" + nrFieldCode + "\u503c," + fieldValue + ",\u4e0d\u662f\u65f6\u671f\u683c\u5f0f\uff1a" + e.getMessage(), e);
                                                            }
                                                            break block44;
                                                        }
                                                        if (deField.getDataType() != DEDataType.FILE) break block54;
                                                        String groupFileKey = fieldValue;
                                                        if (!StringUtils.isNotEmpty((CharSequence)fieldValue)) break block55;
                                                        try {
                                                            String fieldKey = null;
                                                            if (this.fieldCodeList.containsKey(nrFieldCode)) {
                                                                DataField nrDataField = this.fieldCodeList.get(nrFieldCode);
                                                                fieldKey = nrDataField.getKey();
                                                            }
                                                            byte[] fileData = this.attachmentService.getFieldDataFromNR(groupFileKey, fieldKey, this.context.getTaskDefine().getDataScheme(), this.context.getTaskDefine().getKey());
                                                            DEAttachMent attachMent = new DEAttachMent(deOrgCode + "_" + this.tableModel.getTableInfo().getName() + "_" + deField.getName(), fileData);
                                                            fieldObject = attachMent;
                                                        }
                                                        catch (Exception e) {
                                                            this.context.error(logger, "\u6307\u6807" + nrFieldCode + "\u503c," + fieldValue + ",\u65e0\u6cd5\u4e0b\u8f7d\u9644\u4ef6\uff1a" + e.getMessage(), e);
                                                        }
                                                        break block44;
                                                    }
                                                    fieldObject = null;
                                                    if (this.context.isPostNullValue()) break block44;
                                                    break block45;
                                                }
                                                if (deField.getDataType() != DEDataType.BOOLEAN) break block56;
                                                if (!"1".equalsIgnoreCase(fieldValue)) break block57;
                                                fieldObject = Boolean.TRUE;
                                                break block44;
                                            }
                                            if (!"0".equalsIgnoreCase(fieldValue)) break block58;
                                            fieldObject = Boolean.FALSE;
                                            break block44;
                                        }
                                        if (!"TRUE".equalsIgnoreCase(fieldValue)) break block59;
                                        fieldObject = Boolean.TRUE;
                                        break block44;
                                    }
                                    if (!"FALSE".equalsIgnoreCase(fieldValue)) break block60;
                                    fieldObject = Boolean.FALSE;
                                    break block44;
                                }
                                if (!"\u662f".equalsIgnoreCase(fieldValue)) break block61;
                                fieldObject = Boolean.TRUE;
                                break block44;
                            }
                            if ("\u5426".equalsIgnoreCase(fieldValue)) {
                                fieldObject = Boolean.FALSE;
                                break block44;
                            } else if (StringUtils.isEmpty((CharSequence)fieldValue)) {
                                fieldObject = null;
                                break block44;
                            } else {
                                if (logger.isDebugEnabled()) {
                                    this.context.info(logger, "\u4e0d\u8bc6\u522b\u7684\u5e03\u5c14\u503c\uff1a" + fieldValue + "," + deFieldName);
                                }
                                fieldObject = null;
                            }
                            break block44;
                        }
                        if (!this.nrFieldMapBaseDatas.containsKey(nrFieldCode) || !StringUtils.isNotEmpty((CharSequence)fieldValue)) break block62;
                        String baseDataCode = this.nrFieldMapBaseDatas.get(nrFieldCode);
                        if (this.context.getMappingCache().getEnumMapingInfos().containsKey(baseDataCode) && (enumMapping = (EnumMappingInfo)this.context.getMappingCache().getEnumMapingInfos().get(baseDataCode)).getItemMappings().containsKey(fieldValue)) {
                            fieldObject = ((EnumItemMappingInfo)enumMapping.getItemMappings().get(fieldValue)).getMappingCode();
                        }
                        break block44;
                    }
                    if (!StringUtils.isEmpty((CharSequence)fieldValue)) break block44;
                    fieldObject = null;
                    if (!this.context.isPostNullValue()) break block45;
                }
                if (fieldObject != null || this.context.isPostNullValue()) {
                    try {
                        if (logger.isDebugEnabled()) {
                            logger.debug("insert:" + String.format("\u65f6\u671f\uff1a%s,\u5355\u4f4d\uff1a%s,\u5b57\u6bb5\uff1a%s,\u503c\uff1a%s", this.dePeriodCode, deOrgCode, deFieldName, fieldValue));
                            String repeatCode = this.dePeriodCode + deOrgCode + deFieldName + dimFieldsCode;
                            if (!this.repeatFeildValues.containsKey(repeatCode)) {
                                this.repeatFeildValues.put(repeatCode, fieldObject);
                            } else {
                                this.context.info(logger, "\u5b58\u5728\u91cd\u7801\uff1a" + repeatCode + "," + String.format("\u65f6\u671f\uff1a%s,\u5355\u4f4d\uff1a%s,\u5b57\u6bb5\uff1a%s,\u503c\uff1a%s", this.dePeriodCode, deOrgCode, deFieldName, fieldValue) + ",\u8868:" + this.nrTableCode);
                            }
                        }
                        if (deField.getIsEncrypted() && fieldObject != null) {
                            fieldObject = this.encryptedFieldService.encrypt(this.context.getMidstoreScheme(), (String)fieldObject);
                        }
                        if (this.context.getDataSourceDTO().isUseDimensionField() && this.tableModel.getTableInfo().getType() == TableType.MDZB) {
                            this.zbWriter.insert(this.dePeriodCode, deOrgCode, deFieldName, fieldObject, dimFieldValues);
                        } else {
                            this.zbWriter.insert(this.dePeriodCode, deOrgCode, deFieldName, fieldObject);
                        }
                    }
                    catch (Exception e) {
                        this.context.info(logger, String.format("\u65f6\u671f\uff1a%s,\u5355\u4f4d\uff1a%s,\u5b57\u6bb5\uff1a%s,\u503c\uff1a%s", this.dePeriodCode, deOrgCode, deFieldName, fieldValue));
                        this.context.error(logger, e.getMessage(), e);
                        String msg = e.getMessage();
                        this.recordErrorData(this.context, nrOrgCode, msg, nrFieldCode, this.tableResult);
                        this.tableResult.getErrorObjectCodes().add(nrOrgCode);
                        this.context.getWorkResult().getPeriodResult().getErrorWorkTableKeys().add(this.dataTable.getKey());
                        this.context.getWorkResult().getPeriodResult().getErrorObjectCodes().add(nrOrgCode);
                        this.context.getSourceResult().getErrorObjectCodes().add(nrOrgCode);
                        this.context.getSourceResult().getErrorTableKeys().add(this.dataTable.getKey());
                    }
                }
            }
            ++i;
        }
    }

    public void finish() {
    }

    private MidstoreWorkResultTableData recordTableResult(ReportMidstoreContext context, DataTable dataTable) {
        MidstoreWorkResultTableData tableResult = context.getWorkResult().getPeriodResult().getTableResult(dataTable.getKey());
        if (tableResult == null) {
            tableResult = new MidstoreWorkResultTableData();
            tableResult.getTableDTO().setKey(UUID.randomUUID().toString());
            tableResult.getTableDTO().setSourceTableKey(dataTable.getKey());
            tableResult.getTableDTO().setSourceTableCode(dataTable.getCode());
            tableResult.getTableDTO().setResultKey(context.getWorkResult().getPeriodResult().getWorkResultDTO().getKey());
            tableResult.getTableDTO().setSourceType(context.getExeContext().getSourceTypeId());
            tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
            tableResult.getTableDTO().setObjectErrorCount(0);
            tableResult.getTableDTO().setObjectItemCount(0);
            tableResult.getTableDTO().setTotalRecordSize(0);
            tableResult.getTableDTO().setErrorRecordSize(0);
            context.getWorkResult().getPeriodResult().addTableResult(tableResult);
        }
        return tableResult;
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

