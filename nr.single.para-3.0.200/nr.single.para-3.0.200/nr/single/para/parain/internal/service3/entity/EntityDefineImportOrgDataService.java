/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.TableTypeType
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.CannotGetJdbcConnectionException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package nr.single.para.parain.internal.service3.entity;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.TableTypeType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import nr.single.para.basedata.IOrgDataDefineService;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IEntityDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component(value="EntityDefineImportOrgDataService")
public class EntityDefineImportOrgDataService
implements IEntityDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(EntityDefineImportOrgDataService.class);
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IOrgDataDefineService orgDataDefineSevice;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmdFieldService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String importCorpEntity(TaskImportContext importContext) throws Exception {
        String tableCode;
        OrgCategoryDO table;
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        DesignDataScheme dataScheme = importContext.getDataScheme();
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = "_" + dataScheme.getPrefix().toUpperCase();
        }
        if ((table = this.orgDataDefineSevice.queryOrgDatadefine(tableCode = String.format("MD_ORG%s", fileFlag))) == null) {
            table = new OrgCategoryDO();
            table.setName(tableCode);
            if ("\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848".equalsIgnoreCase(dataScheme.getTitle())) {
                table.setTitle(dataScheme.getTitle() + "\u5355\u4f4d");
            } else {
                table.setTitle(dataScheme.getTitle() + "\u5355\u4f4d");
            }
            table.setId(UUID.randomUUID());
            table.setTenantName(tenantName);
            table.setVersionflag(Integer.valueOf(1));
            this.orgDataDefineSevice.InsertOrgDataDefine(table);
        } else {
            OrgCategoryDO newTableDefine = table;
            newTableDefine.setName(tableCode);
            newTableDefine.setTitle(table.getTitle());
            newTableDefine.setId(table.getId());
            newTableDefine.setTenantName(tenantName);
            this.orgDataDefineSevice.update(newTableDefine);
        }
        DataModelDTO param = new DataModelDTO();
        param.setName(tableCode);
        param.setTenantName(tenantName);
        DataModelDO dataModel = this.dataModelClient.get(param);
        if (dataModel == null) {
            dataModel = new DataModelDO();
            dataModel.setTenantName(tenantName);
            dataModel.setName(tableCode);
            dataModel.setId(UUID.randomUUID());
            dataModel.setTitle(table.getTitle());
        }
        importContext.setEntityTable(new TableInfoDefine(table));
        List<ZB> orgFieldList = this.UpdateSingleFields(importContext, table.getId().toString(), dataModel);
        if (orgFieldList.size() > 0) {
            for (ZB orgField : orgFieldList) {
                table.syncZb(orgField);
            }
            this.orgDataDefineSevice.update(table);
        }
        String entityId = EntityUtils.getEntityId((String)table.getName(), (String)"ORG");
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        importContext.getSchemeInfoCache().getTableCache().put(table.getName(), new TableInfoDefine(table));
        String parentField = "PARENTCODE";
        String codeFieldid = "ORGCODE";
        this.paraImportService.setEnityCodeField(importContext.getParaInfo(), codeFieldid);
        this.paraImportService.setEnityParentField(importContext.getParaInfo(), parentField);
        importContext.getImportOption().setCorpEntityId(entityId);
        return entityId;
    }

    @Override
    public String updateCorpEntity(TaskImportContext importContext) throws Exception {
        IEntityDefine entityDefine;
        DesignTaskDefine task = importContext.getTaskDefine();
        ParaInfo para = importContext.getParaInfo();
        String corpEntityKey = null;
        if (task != null && StringUtils.isNotEmpty((String)task.getDw())) {
            corpEntityKey = task.getDw();
        } else if (importContext.getImportOption() != null && StringUtils.isNotEmpty((String)importContext.getImportOption().getCorpEntityId())) {
            corpEntityKey = importContext.getImportOption().getCorpEntityId();
        } else {
            DesignDataScheme dataScheme = importContext.getDataScheme();
            List dimesions2 = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT_SCOPE);
            List dimesions3 = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT);
            if (dimesions2 != null && dimesions2.size() > 0) {
                corpEntityKey = ((DesignDataDimension)dimesions2.get(0)).getDimKey();
            } else if (dimesions3 != null && dimesions3.size() > 0) {
                corpEntityKey = ((DesignDataDimension)dimesions3.get(0)).getDimKey();
            } else {
                List dimesions = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT);
                for (DesignDataDimension dim : dimesions) {
                    if (dim.getDimensionType() != DimensionType.UNIT) continue;
                    corpEntityKey = dim.getDimKey();
                    break;
                }
            }
        }
        if (StringUtils.isNotEmpty((String)corpEntityKey) && (entityDefine = this.entityMetaService.queryEntity(corpEntityKey)) != null) {
            SingleParaImportOption option = importContext.getImportOption();
            OrgCategoryDO table = this.orgDataDefineSevice.queryOrgDatadefine(EntityUtils.getId((String)corpEntityKey));
            if (table != null) {
                importContext.setEntityTable(new TableInfoDefine(table));
                boolean isNeedUpdateOrgField = true;
                if (option != null && !option.isUploadTask()) {
                    isNeedUpdateOrgField = false;
                }
                if (isNeedUpdateOrgField) {
                    ParaImportInfoResult fmdmLog = null;
                    if (importContext.getCompareInfo() != null && importContext.getImportResult() != null) {
                        fmdmLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FMDMFIELD, "FMDM", "\u5c01\u9762\u4ee3\u7801");
                    }
                    String tenantName = null;
                    if (NpContextHolder.getContext() != null) {
                        tenantName = NpContextHolder.getContext().getTenant();
                    }
                    BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
                    DataModelDTO param = new DataModelDTO();
                    param.setName(EntityUtils.getId((String)corpEntityKey));
                    param.setTenantName(tenantName);
                    DataModelDO baseDataModel = this.dataModelClient.get(param);
                    boolean isFieldHasData = this.checkTableExistData(table.getName());
                    List<ZB> orgFieldList = this.UpdateSingleFields(importContext, table.getId().toString(), baseDataModel);
                    if (orgFieldList.size() > 0) {
                        for (ZB orgField : orgFieldList) {
                            ZB oldField = table.getZbByName(orgField.getName());
                            if (oldField != null) {
                                orgField.setId(oldField.getId());
                            }
                            if (!isFieldHasData || oldField == null) {
                                if (StringUtils.isNotEmpty((String)orgField.getReltablename())) {
                                    String relTableName = orgField.getReltablename();
                                    BaseDataDefineDTO baseDataQueryDTO = new BaseDataDefineDTO();
                                    baseDataQueryDTO.setTenantName(tenantName);
                                    baseDataQueryDTO.setName(orgField.getReltablename());
                                    BaseDataDefineDO refEnumDefine = client.get(baseDataQueryDTO);
                                    if (refEnumDefine == null) {
                                        ParaImportInfoResult fieldLog = null;
                                        if (fmdmLog != null && fmdmLog.getCodeFinder().containsKey(orgField.getName())) {
                                            fieldLog = fmdmLog.getCodeFinder().get(orgField.getName());
                                        }
                                        if (oldField == null || StringUtils.isNotEmpty((String)oldField.getReltablename())) {
                                            // empty if block
                                        }
                                    }
                                }
                                table.syncZb(orgField);
                                continue;
                            }
                            orgField.setDatatype(oldField.getDatatype());
                            orgField.setReltablename(oldField.getReltablename());
                            if (oldField.getDatatype() == 4) {
                                if (oldField.getDecimal() != null && orgField.getDecimal() != null) {
                                    if (orgField.getDecimal() < oldField.getDecimal()) {
                                        orgField.setDecimal(oldField.getDecimal());
                                    }
                                    if (orgField.getPrecision() < oldField.getPrecision() + orgField.getDecimal() - oldField.getDecimal()) {
                                        orgField.setPrecision(Integer.valueOf(oldField.getPrecision() + orgField.getDecimal() - oldField.getDecimal()));
                                    }
                                    if (orgField.getPrecision() < oldField.getPrecision()) {
                                        orgField.setPrecision(oldField.getPrecision());
                                    }
                                } else {
                                    orgField.setPrecision(oldField.getPrecision());
                                    orgField.setDecimal(oldField.getDecimal());
                                }
                            } else {
                                if (orgField.getPrecision() != null && oldField.getPrecision() != null && orgField.getPrecision() < oldField.getPrecision()) {
                                    orgField.setPrecision(oldField.getPrecision());
                                }
                                if (orgField.getDecimal() != null && oldField.getDecimal() != null && orgField.getDecimal() < oldField.getDecimal()) {
                                    orgField.setDecimal(oldField.getDecimal());
                                }
                            }
                            table.syncZb(orgField);
                        }
                        R r = this.orgDataDefineSevice.update(table);
                        if (r.getCode() != 0) {
                            log.info(table.getName() + "\u7ec4\u7ec7\u673a\u6784\u66f4\u65b0\u5931\u8d25\uff1a" + r.getMsg());
                        }
                    }
                }
            } else {
                importContext.setEntityTable(new TableInfoDefine(entityDefine));
            }
        }
        String parentField = "PARENTCODE";
        String codeFieldid = "ORGCODE";
        this.paraImportService.setEnityCodeField(importContext.getParaInfo(), codeFieldid);
        this.paraImportService.setEnityParentField(importContext.getParaInfo(), parentField);
        return corpEntityKey;
    }

    @Override
    public void CheckAddVersionEntity(DesignTaskDefine task) throws Exception {
        DesignDataScheme dataScheme = this.dataSchemeSevice.getDataScheme(task.getDataScheme());
        if (dataScheme == null) {
            return;
        }
        List dims = this.dataSchemeSevice.getDataSchemeDimension(task.getDataScheme());
        String newMasterkey = "";
        String DWViewKey = null;
        String DimViewKeys = "";
        String DateTimeViewKey = null;
        boolean hasVersionEntity = false;
        for (DesignDataDimension dim : dims) {
            if (dim.getDimensionType() == DimensionType.PERIOD) {
                DateTimeViewKey = dim.getDimKey();
                continue;
            }
            if (dim.getDimensionType() == DimensionType.UNIT) {
                DWViewKey = dim.getDimKey();
                continue;
            }
            if (dim.getDimensionType() != DimensionType.DIMENSION) continue;
            hasVersionEntity = true;
            if (StringUtils.isNotEmpty((String)DimViewKeys)) {
                DimViewKeys = DimViewKeys + ";" + dim.getDimKey();
                continue;
            }
            DimViewKeys = dim.getDimKey();
        }
        if (hasVersionEntity || StringUtils.isNotEmpty(DWViewKey)) {
            // empty if block
        }
    }

    private List<ZB> UpdateSingleFields(TaskImportContext importContext, String orgDataCode, DataModelDO baseDataModel) throws Exception {
        ArrayList<ZB> orgFieldList = new ArrayList<ZB>();
        if (baseDataModel == null) {
            return orgFieldList;
        }
        HashMap<String, DataModelColumn> oldColumnDic = new HashMap<String, DataModelColumn>();
        ArrayList<DataModelColumn> oldColumns = baseDataModel.getColumns();
        if (oldColumns != null) {
            for (DataModelColumn column : oldColumns) {
                oldColumnDic.put(column.getColumnName(), column);
            }
        } else {
            oldColumns = new ArrayList<DataModelColumn>();
            baseDataModel.setColumns(oldColumns);
        }
        ParaImportInfoResult fmdmLog = null;
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldDic = new HashMap<String, CompareDataFMDMFieldDTO>();
        if (importContext.getCompareInfo() != null) {
            if (importContext.getImportResult() != null) {
                fmdmLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FMDMFIELD, "FMDM", "\u5c01\u9762\u4ee3\u7801");
            }
            CompareDataFMDMFieldDTO fmdmQueryParam = new CompareDataFMDMFieldDTO();
            fmdmQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            fmdmQueryParam.setDataType(CompareDataType.DATA_FMDMFIELD);
            List<CompareDataFMDMFieldDTO> oldFMDMFieldList = this.fmdmdFieldService.list(fmdmQueryParam);
            for (CompareDataFMDMFieldDTO oldData : oldFMDMFieldList) {
                oldFMDMFieldDic.put(oldData.getSingleCode(), oldData);
                if (fmdmLog == null) continue;
                ParaImportInfoResult fieldLog = new ParaImportInfoResult();
                fieldLog.copyForm(oldData);
                fieldLog.setSuccess(true);
                fmdmLog.addItem(fieldLog);
            }
        }
        Map<String, String> enumMapDic = this.paraImportService.getEnumMapNets(importContext);
        FMRepInfo repInfo = importContext.getParaInfo().getFmRepInfo();
        if (repInfo == null) {
            return orgFieldList;
        }
        for (ZBInfo zb : repInfo.getDefs().getZbsNoZDM()) {
            String fieldName = zb.getFieldName();
            String fieldTitle = zb.getZbTitle();
            if ("PARENTCODE".equalsIgnoreCase(fieldName) || "CODE".equalsIgnoreCase(fieldName) || "ORGCODE".equalsIgnoreCase(fieldName)) continue;
            CompareDataFMDMFieldDTO fieldCompare = null;
            if (oldFMDMFieldDic.containsKey(fieldName)) {
                fieldCompare = (CompareDataFMDMFieldDTO)oldFMDMFieldDic.get(fieldName);
                if (fieldCompare.getOwnerTableType() == CompareTableType.TABLE_FIX || fieldCompare.getOwnerTableType() == CompareTableType.TABLE_MDINFO) continue;
                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATA_USENET) {
                    log.info("\u6307\u6807\uff1a" + fieldName + ",\u4ee5\u7f51\u62a5\u4e3a\u51c6\uff0c\u4e0d\u66f4\u65b0");
                    continue;
                }
                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                    log.info("\u6307\u6807\uff1a" + fieldName + ",\u4e0d\u8986\u76d6\u5bfc\u5165");
                    continue;
                }
                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                    log.info("\u6307\u6807\uff1a" + fieldName + ",\u5ffd\u7565\u5bfc\u5165");
                    continue;
                }
                if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_RECODE) {
                    log.info("\u6307\u6807\uff1a" + fieldName + ",\u91cd\u65b0\u7f16\u7801\u5bfc\u5165");
                    fieldName = fieldCompare.getNetCode();
                    fieldTitle = fieldCompare.getNetTitle();
                    if (StringUtils.isEmpty((String)fieldName)) {
                        log.info("\u6307\u6807\uff1a" + fieldName + ",\u91cd\u65b0\u7f16\u7801\u5bfc\u5165,\u6807\u8bc6\u4e3a\u7a7a\uff0c\u4e0d\u5bfc\u5165");
                    }
                } else if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATA_USESINGLE) {
                    if (fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_NEW) {
                        fieldName = fieldCompare.getNetCode();
                        fieldTitle = fieldCompare.getNetTitle();
                    } else if (fieldCompare.getUpdateType() != CompareUpdateType.UPDATE_OVER && fieldCompare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                        if (StringUtils.isNotEmpty((String)fieldCompare.getNetCode()) && oldColumnDic.containsKey(fieldCompare.getNetCode())) {
                            fieldName = fieldCompare.getNetCode();
                            fieldTitle = fieldCompare.getNetTitle();
                        } else {
                            log.info("\u6307\u6807\uff1a" + fieldName + ",\u6307\u5b9a\u7684\u6307\u6807\u4e0d\u5b58\u5728\uff1a" + fieldCompare.getNetCode());
                            continue;
                        }
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)(fieldName = this.replaceSysChar(baseDataModel.getName(), fieldName))) && fieldName.equalsIgnoreCase(repInfo.getDWMCFieldName()) || "NAME".equalsIgnoreCase(fieldName)) continue;
            DataModelColumn column = null;
            if (!oldColumnDic.containsKey(fieldName)) {
                column = this.AddAndModifyField4(importContext, baseDataModel, orgDataCode, baseDataModel.getName(), zb, oldColumns, enumMapDic);
                column.setColumnName(fieldName);
                column.setColumnTitle(fieldTitle);
                oldColumnDic.put(fieldName, column);
            } else {
                column = (DataModelColumn)oldColumnDic.get(fieldName);
            }
            ZB orgField = new ZB();
            this.setOrgField(importContext, baseDataModel.getName(), orgField, zb, enumMapDic);
            orgField.setName(fieldName);
            orgField.setTitle(fieldTitle);
            orgFieldList.add(orgField);
        }
        return orgFieldList;
    }

    private void setOrgField(TaskImportContext importContext, String tableName, ZB orgField, ZBInfo zb, Map<String, String> enumMapDic) {
        String fieldName = this.replaceSysChar(tableName, zb.getFieldName());
        orgField.setId(UUID.randomUUID());
        orgField.setName(fieldName);
        orgField.setTitle(zb.getZbTitle());
        orgField.setDatatype(Integer.valueOf(this.paraImportService.getDataModelType(zb.getDataType())));
        int len = zb.getLength();
        if (len == 0 && orgField.getDatatype() != 5) {
            len = 20;
        }
        byte dec = zb.getDecimal();
        orgField.setPrecision(Integer.valueOf(len));
        orgField.setDecimal(Integer.valueOf(dec));
        String dValue = null;
        if (StringUtils.isNotEmpty((String)zb.getDefaultValue()) && StringUtils.isNotEmpty((String)(dValue = zb.getDefaultValue().replaceAll("\"", "")))) {
            orgField.setDefaultVal(dValue);
        }
        if (zb.getEnumInfo() != null) {
            String enumName1;
            orgField.setRelatetype(Integer.valueOf(1));
            String fileFlag = "";
            if (importContext.getDataScheme() != null && StringUtils.isNotEmpty((String)importContext.getDataScheme().getPrefix())) {
                fileFlag = "_" + importContext.getDataScheme().getPrefix().toUpperCase();
            } else if (importContext.getSchemeInfoCache() != null && importContext.getSchemeInfoCache().getFormScheme() != null && StringUtils.isNotEmpty((String)importContext.getSchemeInfoCache().getFormScheme().getFilePrefix())) {
                fileFlag = "_" + importContext.getSchemeInfoCache().getFormScheme().getFilePrefix().toUpperCase();
            }
            String enumName = String.format("MD%s%s", fileFlag, "_" + zb.getEnumInfo().getEnumIdenty().toUpperCase());
            boolean enumIsBBLX = "BBLX".equalsIgnoreCase(zb.getEnumInfo().getEnumIdenty());
            if (enumIsBBLX) {
                enumName = String.format("MD%s%s", "_" + zb.getEnumInfo().getEnumIdenty().toUpperCase(), fileFlag);
            }
            if (enumMapDic.containsKey(zb.getEnumInfo().getEnumIdenty().toUpperCase())) {
                enumName1 = enumMapDic.get(zb.getEnumInfo().getEnumIdenty().toUpperCase());
                if (StringUtils.isNotEmpty((String)enumName1)) {
                    enumName = enumName1;
                }
            } else if (enumMapDic.containsKey(zb.getEnumInfo().getEnumIdenty()) && StringUtils.isNotEmpty((String)(enumName1 = enumMapDic.get(zb.getEnumInfo().getEnumIdenty())))) {
                enumName = enumName1;
            }
            orgField.setReltablename(enumName);
            EnumsItemModel enumModel = (EnumsItemModel)importContext.getParaInfo().getEnunList().get(zb.getEnumInfo().getEnumIdenty());
            if (enumModel != null && enumModel.getMultiSelect()) {
                orgField.setMultiple(Integer.valueOf(1));
            }
            if (enumModel != null && enumIsBBLX && StringUtils.isEmpty((String)dValue)) {
                if (enumModel.getEnumItemList().containsKey("0")) {
                    orgField.setDefaultVal("0");
                } else {
                    FMRepInfo fmRep = importContext.getParaInfo().getFmRepInfo();
                    if (fmRep != null) {
                        String jcfh = fmRep.returnTableTypeCode(TableTypeType.TTT_JCFH);
                        String jchb = fmRep.returnTableTypeCode(TableTypeType.TTT_JCHB);
                        if (StringUtils.isNotEmpty((String)jcfh)) {
                            orgField.setDefaultVal(jcfh);
                        } else if (StringUtils.isNotEmpty((String)jchb)) {
                            orgField.setDefaultVal(jchb);
                        }
                    }
                }
            }
        }
        orgField.setRequiredflag(Integer.valueOf(0));
        orgField.setUniqueflag(Integer.valueOf(0));
        orgField.setSolidityflag(Integer.valueOf(0));
    }

    private DataModelColumn AddAndModifyField4(TaskImportContext importContext, DataModelDO dataMode, String tableKey, String tableName, ZBInfo zb, List<DataModelColumn> oldColumns, Map<String, String> enumMapDic) throws Exception {
        int len;
        String fieldName = this.replaceSysChar(dataMode.getName(), zb.getFieldName());
        DataModelColumn column = dataMode.addColumn(fieldName);
        column.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        column.setColumnTitle(zb.getZbTitle());
        column.setColumnType(this.paraImportService.getColumnType(zb.getDataType()));
        if (zb.getEnumInfo() != null) {
            String enumName1;
            column.setMappingType(Integer.valueOf(1));
            String fileFlag = "";
            if (importContext.getDataScheme() != null && StringUtils.isNotEmpty((String)importContext.getDataScheme().getPrefix())) {
                fileFlag = "_" + importContext.getDataScheme().getPrefix().toUpperCase();
            } else if (importContext.getSchemeInfoCache() != null && importContext.getSchemeInfoCache().getFormScheme() != null && StringUtils.isNotEmpty((String)importContext.getSchemeInfoCache().getFormScheme().getFilePrefix())) {
                fileFlag = "_" + importContext.getSchemeInfoCache().getFormScheme().getFilePrefix().toUpperCase();
            }
            String enumName = String.format("MD%s%s", fileFlag, "_" + zb.getEnumInfo().getEnumIdenty().toUpperCase());
            boolean enumIsBBLX = "BBLX".equalsIgnoreCase(zb.getEnumInfo().getEnumIdenty());
            if (enumIsBBLX) {
                enumName = String.format("MD%s%s", "_" + zb.getEnumInfo().getEnumIdenty().toUpperCase(), fileFlag);
            }
            if (enumMapDic.containsKey(zb.getEnumInfo().getEnumIdenty().toUpperCase()) && StringUtils.isNotEmpty((String)(enumName1 = enumMapDic.get(zb.getEnumInfo().getEnumIdenty().toUpperCase())))) {
                enumName = enumName1;
            }
            column.setMapping(enumName + ".OBJECTCODE");
        }
        if ((len = zb.getLength()) == 0 && column.getColumnType() != DataModelType.ColumnType.DATE) {
            len = 20;
        }
        byte dec = zb.getDecimal();
        column.setLengths(new Integer[]{len, dec});
        column.setNullable(Boolean.valueOf(true));
        if (StringUtils.isNotEmpty((String)zb.getDefaultValue())) {
            String dValue = zb.getDefaultValue().replace("\"", "").replace("\"", "");
            column.setDefaultVal(dValue);
        }
        return column;
    }

    private String replaceSysChar(String tableName, String fieldName) {
        String code = fieldName;
        if ("LEVEL".equalsIgnoreCase(fieldName) && StringUtils.isNotEmpty((String)tableName)) {
            code = tableName + "_" + fieldName;
        }
        return code;
    }

    @Override
    public String getType() {
        return "ORG";
    }

    private boolean checkTableExistData(String tableName) throws CannotGetJdbcConnectionException, SQLException {
        try (Connection conn = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata sqlMetadata = database.createMetadata(conn);
            boolean bl = sqlMetadata.existsData(tableName);
            return bl;
        }
    }
}

