/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package nr.single.para.parain.internal.service3.entity;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nr.single.para.basedata.IBaseDataDefineService;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IEntityDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="EntityDefineImportBaseDataService")
public class EntityDefineImportBaseDataService
implements IEntityDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(EntityDefineImportBaseDataService.class);
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IBaseDataDefineService baseDefineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmdFieldService;

    @Override
    public String importCorpEntity(TaskImportContext importContext) throws Exception {
        String tableCode;
        BaseDataDefineDO table;
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        DesignDataScheme dataScheme = importContext.getDataScheme();
        BaseDataGroupDO group = this.CreateDataGroup(importContext);
        String fileFlag = "";
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = "_" + dataScheme.getPrefix().toUpperCase();
        }
        if ((table = this.baseDefineService.queryBaseDatadefine(tableCode = String.format("MD%s_%s", fileFlag, "FMDM"))) == null) {
            table = new BaseDataDefineDTO();
            table.setName(tableCode);
            if ("\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848".equalsIgnoreCase(dataScheme.getTitle())) {
                table.setTitle(dataScheme.getTitle() + "\u5355\u4f4d");
            } else {
                table.setTitle(dataScheme.getTitle() + "\u5355\u4f4d");
            }
            table.setGroupname(group.getName());
            table.setId(UUID.randomUUID());
            table.setTenantName(tenantName);
            table.setSharetype(Integer.valueOf(0));
            table.setStructtype(Integer.valueOf(2));
            table.setDimensionflag(Integer.valueOf(1));
            table.setDefaultShowColumns(this.getShowColumns(importContext, tableCode));
            this.baseDefineService.insertBaseDataDefine((BaseDataDefineDTO)table);
        } else {
            BaseDataDefineDTO newTableDefine = new BaseDataDefineDTO();
            newTableDefine.setName(tableCode);
            newTableDefine.setTitle(table.getTitle());
            newTableDefine.setGroupname(group.getName());
            newTableDefine.setId(table.getId());
            newTableDefine.setTenantName(tenantName);
            newTableDefine.setSharetype(Integer.valueOf(0));
            newTableDefine.setModifytime(new Date());
            newTableDefine.setStructtype(table.getStructtype());
            newTableDefine.setShowtype(table.getShowtype());
            newTableDefine.setDimensionflag(Integer.valueOf(1));
            newTableDefine.setDefaultShowColumns(this.getShowColumns(importContext, tableCode));
            this.baseDefineService.update(newTableDefine);
        }
        DataModelDTO param = new DataModelDTO();
        param.setName(tableCode);
        param.setTenantName(tenantName);
        DataModelDO baseDataModel = this.dataModelClient.get(param);
        if (baseDataModel == null) {
            baseDataModel = new DataModelDO();
            baseDataModel.setTenantName(tenantName);
            baseDataModel.setName(tableCode);
            baseDataModel.setId(UUID.randomUUID());
            baseDataModel.setTitle(table.getTitle());
        }
        importContext.setEntityTable(new TableInfoDefine(table));
        this.UpdateSingleFields(importContext, table.getId().toString(), baseDataModel);
        R r = this.dataModelClient.push(baseDataModel);
        log.info(r.getMsg());
        baseDataModel.getColumns();
        String entityId = EntityUtils.getEntityId((String)table.getName(), (String)"BASE");
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        importContext.getSchemeInfoCache().getTableCache().put(table.getName(), new TableInfoDefine(table));
        String parentField = "PARENTCODE";
        String codeFieldid = "CODE";
        this.paraImportService.setEnityCodeField(importContext.getParaInfo(), codeFieldid);
        this.paraImportService.setEnityParentField(importContext.getParaInfo(), parentField);
        importContext.getImportOption().setCorpEntityId(entityId);
        return entityDefine.getId();
    }

    @Override
    public String updateCorpEntity(TaskImportContext importContext) throws Exception {
        IEntityDefine entityDefine;
        DesignTaskDefine task = importContext.getTaskDefine();
        ParaInfo para = importContext.getParaInfo();
        String corpEntityKey = null;
        if (task != null && StringUtils.isNotEmpty((String)task.getDw())) {
            corpEntityKey = task.getDw();
        } else {
            DesignDataScheme dataScheme = importContext.getDataScheme();
            List dimesions = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey());
            for (DesignDataDimension dim : dimesions) {
                if (dim.getDimensionType() != DimensionType.UNIT) continue;
                corpEntityKey = dim.getDimKey();
                break;
            }
        }
        if (StringUtils.isNotEmpty((String)corpEntityKey) && (entityDefine = this.entityMetaService.queryEntity(corpEntityKey)) != null) {
            BaseDataDefineDO table = this.baseDefineService.queryBaseDatadefine(EntityUtils.getId((String)corpEntityKey));
            if (table != null) {
                importContext.setEntityTable(new TableInfoDefine(table));
                String tenantName = null;
                if (NpContextHolder.getContext() != null) {
                    tenantName = NpContextHolder.getContext().getTenant();
                }
                DataModelDTO param = new DataModelDTO();
                param.setName(EntityUtils.getId((String)corpEntityKey));
                param.setTenantName(tenantName);
                DataModelDO baseDataModel = this.dataModelClient.get(param);
                this.UpdateSingleFields(importContext, table.getId().toString(), baseDataModel);
                this.dataModelClient.push(baseDataModel);
            } else {
                importContext.setEntityTable(new TableInfoDefine(entityDefine));
            }
        }
        String parentField = "PARENTCODE";
        String codeFieldid = "CODE";
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

    private BaseDataGroupDO CreateDataGroup(TaskImportContext importContext) {
        DesignDataScheme dataScheme = importContext.getDataScheme();
        BaseDataGroupDO groupr = this.baseDefineService.getAndInsertBaseDataGroup("NR_GROUP", "\u62a5\u8868", "");
        BaseDataGroupDO group0 = this.baseDefineService.getAndInsertBaseDataGroup("NR_DT_" + dataScheme.getCode(), dataScheme.getTitle(), groupr.getName());
        BaseDataGroupDO group = this.baseDefineService.getAndInsertBaseDataGroup("NR_ENTITY_" + dataScheme.getCode(), "\u5355\u4f4d", group0.getName());
        return group;
    }

    private void UpdateSingleFields(TaskImportContext importContext, String baseDataCode, DataModelDO baseDataModel) throws Exception {
        FMRepInfo repInfo;
        if (baseDataModel == null) {
            return;
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
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldDic = new HashMap<String, CompareDataFMDMFieldDTO>();
        if (importContext.getCompareInfo() != null) {
            CompareDataFMDMFieldDTO fmdmQueryParam = new CompareDataFMDMFieldDTO();
            fmdmQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            fmdmQueryParam.setDataType(CompareDataType.DATA_FMDMFIELD);
            List<CompareDataFMDMFieldDTO> oldFMDMFieldList = this.fmdmdFieldService.list(fmdmQueryParam);
            for (CompareDataFMDMFieldDTO oldData : oldFMDMFieldList) {
                oldFMDMFieldDic.put(oldData.getSingleCode(), oldData);
            }
        }
        if ((repInfo = importContext.getParaInfo().getFmRepInfo()) == null) {
            return;
        }
        for (ZBInfo zb : repInfo.getDefs().getZbsNoZDM()) {
            String fieldName = zb.getFieldName();
            if ("PARENTCODE".equalsIgnoreCase(fieldName) || "CODE".equalsIgnoreCase(fieldName)) continue;
            CompareDataFMDMFieldDTO fieldData = null;
            if (oldFMDMFieldDic.containsKey(fieldName) && ((fieldData = (CompareDataFMDMFieldDTO)oldFMDMFieldDic.get(fieldName)).getOwnerTableType() == CompareTableType.TABLE_FIX || fieldData.getOwnerTableType() == CompareTableType.TABLE_MDINFO) || StringUtils.isNotEmpty((String)(fieldName = this.replaceSysChar(baseDataModel.getName(), fieldName))) && fieldName.equalsIgnoreCase(repInfo.getDWMCFieldName())) continue;
            DataModelColumn column = null;
            if (!oldColumnDic.containsKey(fieldName)) {
                column = this.AddAndModifyField4(importContext, baseDataModel, baseDataCode, baseDataModel.getName(), zb, oldColumns);
                column.setColumnName(fieldName);
                oldColumnDic.put(fieldName, column);
                continue;
            }
            column = (DataModelColumn)oldColumnDic.get(fieldName);
        }
    }

    private DataModelColumn AddAndModifyField4(TaskImportContext importContext, DataModelDO baseDataMode, String tableKey, String tableName, ZBInfo zb, List<DataModelColumn> oldColumns) throws Exception {
        int len;
        String fieldName = this.replaceSysChar(baseDataMode.getName(), zb.getFieldName());
        DataModelColumn column = baseDataMode.addColumn(fieldName);
        column.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        column.setColumnTitle(zb.getZbTitle());
        column.setColumnType(this.paraImportService.getColumnType(zb.getDataType()));
        if (zb.getEnumInfo() != null) {
            column.setMappingType(Integer.valueOf(1));
            String fileFlag = "";
            if (StringUtils.isNotEmpty((String)importContext.getDataScheme().getPrefix())) {
                fileFlag = "_" + importContext.getDataScheme().getPrefix().toUpperCase();
            } else if (importContext.getSchemeInfoCache().getFormScheme() != null && StringUtils.isNotEmpty((String)importContext.getSchemeInfoCache().getFormScheme().getFilePrefix())) {
                fileFlag = "_" + importContext.getSchemeInfoCache().getFormScheme().getFilePrefix().toUpperCase();
            }
            String enumName = String.format("MD%s_%s", fileFlag, zb.getEnumInfo().getEnumIdenty().toUpperCase());
            boolean enumIsBBLX = "BBLX".equalsIgnoreCase(zb.getEnumInfo().getEnumIdenty());
            if (enumIsBBLX) {
                enumName = String.format("MD_%s%s", zb.getEnumInfo().getEnumIdenty().toUpperCase(), fileFlag);
            }
            column.setMapping(enumName + ".OBJECTCODE");
        }
        if ((len = zb.getLength()) == 0) {
            len = 20;
        }
        byte dec = zb.getDecimal();
        column.setLengths(new Integer[]{len, dec});
        column.setNullable(Boolean.valueOf(true));
        if (StringUtils.isNotEmpty((String)zb.getDefaultValue())) {
            String dValue = zb.getDefaultValue().replaceAll("\"", "");
            column.setDefaultVal(dValue);
        }
        return column;
    }

    private List<Map<String, Object>> getShowColumns(TaskImportContext importContext, String tableName) {
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
        FMRepInfo repInfo = importContext.getParaInfo().getFmRepInfo();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)"\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)"\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"SHORTNAME", (String)"\u7b80\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        if (repInfo == null) {
            return fields;
        }
        HashMap<String, CompareDataFMDMFieldDTO> oldFMDMFieldDic = new HashMap<String, CompareDataFMDMFieldDTO>();
        if (importContext.getCompareInfo() != null) {
            CompareDataFMDMFieldDTO fmdmQueryParam = new CompareDataFMDMFieldDTO();
            fmdmQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            fmdmQueryParam.setDataType(CompareDataType.DATA_FMDMFIELD);
            List<CompareDataFMDMFieldDTO> oldFMDMFieldList = this.fmdmdFieldService.list(fmdmQueryParam);
            for (CompareDataFMDMFieldDTO oldData : oldFMDMFieldList) {
                oldFMDMFieldDic.put(oldData.getSingleCode(), oldData);
            }
        }
        for (ZBInfo zb : repInfo.getDefs().getZbsNoZDM()) {
            String fieldName = zb.getFieldName();
            if ("PARENTCODE".equalsIgnoreCase(fieldName) || "CODE".equalsIgnoreCase(fieldName)) continue;
            CompareDataFMDMFieldDTO fieldData = null;
            if (oldFMDMFieldDic.containsKey(fieldName) && ((fieldData = (CompareDataFMDMFieldDTO)oldFMDMFieldDic.get(fieldName)).getOwnerTableType() == CompareTableType.TABLE_FIX || fieldData.getOwnerTableType() == CompareTableType.TABLE_MDINFO) || StringUtils.isNotEmpty((String)(fieldName = this.replaceSysChar(tableName, fieldName))) && fieldName.equalsIgnoreCase(repInfo.getDWMCFieldName())) continue;
            byte dec = zb.getDecimal();
            Integer[] lens = new Integer[]{zb.getLength(), dec};
            if (zb.getEnumInfo() != null) {
                String fileFlag = "";
                if (StringUtils.isNotEmpty((String)importContext.getDataScheme().getPrefix())) {
                    fileFlag = "_" + importContext.getDataScheme().getPrefix().toUpperCase();
                } else if (StringUtils.isNotEmpty((String)importContext.getSchemeInfoCache().getFormScheme().getFilePrefix())) {
                    fileFlag = "_" + importContext.getSchemeInfoCache().getFormScheme().getFilePrefix().toUpperCase();
                }
                String enumName = String.format("MD%s_%s", fileFlag, zb.getEnumInfo().getEnumIdenty().toUpperCase());
                boolean enumIsBBLX = "BBLX".equalsIgnoreCase(zb.getEnumInfo().getEnumIdenty());
                if (enumIsBBLX) {
                    enumName = String.format("MD_%s%s", zb.getEnumInfo().getEnumIdenty().toUpperCase(), fileFlag);
                }
                fields.add(BaseDataStorageUtil.getColumnMap((String)fieldName, (String)zb.getZbTitle(), (String)this.paraImportService.getColumnType(zb.getDataType()).name(), (String)(enumName + ".CODE"), (Integer)1, (Integer[])lens, null, null, (Boolean)true, null, null));
                continue;
            }
            fields.add(BaseDataStorageUtil.getColumnMap((String)fieldName, (String)zb.getZbTitle(), (String)this.paraImportService.getColumnType(zb.getDataType()).name(), null, null, (Integer[])lens, null, null, (Boolean)true, null, null));
        }
        return fields;
    }

    private String replaceSysChar(String tableName, String fieldName) {
        String code = fieldName;
        if ("LEVEL".equalsIgnoreCase(fieldName)) {
            code = tableName + "_" + fieldName;
        }
        return code;
    }

    private static List<Map<String, Object>> getDefaultShowColumns() {
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)"\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)"\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"SHORTNAME", (String)"\u7b80\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"BANKTYPECODE", (String)"\u94f6\u884c\u5927\u7c7b", (String)DataModelType.ColumnType.NVARCHAR.name(), (String)"MD_BANKTYPE.CODE", (Integer)1, (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CNAPSCODE", (String)"\u4eba\u884c\u8054\u884c\u53f7", (String)DataModelType.ColumnType.NVARCHAR.name(), (String)"MD_CNAPS.CODE", (Integer)1));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CNAPSNAME", (String)"\u4eba\u884c\u8054\u884c\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"BRANCHCODE", (String)"\u673a\u6784\u53f7", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"AREACODE", (String)"\u5730\u533a\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"PROVINCECODE", (String)"\u7701\u4efd", (String)DataModelType.ColumnType.NVARCHAR.name(), (String)"MD_PROVINCE.CODE", (Integer)1));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CITYCODE", (String)"\u57ce\u5e02", (String)DataModelType.ColumnType.NVARCHAR.name(), (String)"MD_CITY.CODE", (Integer)1));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"PAYMENTCODE", (String)"\u73b0\u4ee3\u5316\u4ed8\u6b3e\u7cfb\u7edf\u884c\u53f7", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"SWIFT", (String)"SWIFT\u7801", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"ADDRESS", (String)"\u5730\u5740", (String)DataModelType.ColumnType.NVARCHAR.name()));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"TRANSSYSTEMCODE", (String)"\u4ea4\u6613\u7cfb\u7edf", (String)DataModelType.ColumnType.NVARCHAR.name(), (String)"MD_TRANSSYSTEM.CODE", (Integer)1));
        return fields;
    }

    private DataModelColumn AddAndModifyField3(String tableKey, String tableName, Consts.EntityField field, List<DataModelColumn> oldColumns) throws Exception {
        DataModelColumn column = new DataModelColumn();
        column.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        column.setColumnName(field.fieldKey);
        column.setColumnTitle(field.fieldTitle);
        column.setColumnType(DataModelType.ColumnType.NVARCHAR);
        column.setLengths(new Integer[]{field.size});
        oldColumns.add(column);
        return column;
    }

    @Override
    public String getType() {
        return "BASE";
    }
}

