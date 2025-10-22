/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDeployFieldExtendService
 *  com.jiuqi.nr.datascheme.api.service.IDeployFieldNameProvider
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDeployFieldExtendService;
import com.jiuqi.nr.datascheme.api.service.IDeployFieldNameProvider;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache.DataFieldDefaultValueCache;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Lazy(value=false)
public class DataSchemeDeployHelper {
    private static DesignDataModelService designDataModelService;
    private static CatalogModelService catalogModelService;
    private static IDeployFieldNameProvider iFieldNameProvider;
    private static IDeployFieldExtendService iDeployFieldExtendService;
    private static IDesignDataSchemeService designDataSchemeService;
    private static IEntityMetaService entityMetaService;
    private static IPeriodEntityAdapter periodEntityAdapter;
    private static int maxFieldCount;
    private static int maxByteCount;
    private static boolean useAlisa;
    private static DataFieldDefaultValueCache dataFieldDefaultValueCache;
    public static final Comparator<DataField> DATAFIELD_COMPARATOR;
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ORDER_LENGTH = 8;

    @Value(value="${jiuqi.nr.jio.useAlisa:false}")
    public void setUseAlisa(boolean useAlisa) {
        DataSchemeDeployHelper.useAlisa = useAlisa;
    }

    @Value(value="${jiuqi.nr.jio.splitcount:950}")
    public void setMaxFieldCount(int maxFieldCount) {
        DataSchemeDeployHelper.maxFieldCount = maxFieldCount;
    }

    @Value(value="${jiuqi.nr.jio.splittablesize:6553500}")
    public void setMaxByteCount(int maxByteCount) {
        DataSchemeDeployHelper.maxByteCount = maxByteCount;
    }

    @Autowired
    public void setPeriodEntityAdapter(PeriodEngineService periodEngineService) {
        periodEntityAdapter = periodEngineService.getPeriodAdapter();
    }

    @Autowired
    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        DataSchemeDeployHelper.entityMetaService = entityMetaService;
    }

    @Autowired
    public void setDesignDataModelService(DesignDataModelService designDataModelService) {
        DataSchemeDeployHelper.designDataModelService = designDataModelService;
    }

    @Autowired
    public void setCatalogModelService(CatalogModelService catalogModelService) {
        DataSchemeDeployHelper.catalogModelService = catalogModelService;
    }

    @Autowired
    public void setDesignDataSchemeService(IDesignDataSchemeService designDataSchemeService) {
        DataSchemeDeployHelper.designDataSchemeService = designDataSchemeService;
    }

    @Autowired(required=false)
    public void setIFieldNameProvider(IDeployFieldNameProvider iFieldNameProvider) {
        DataSchemeDeployHelper.iFieldNameProvider = iFieldNameProvider;
    }

    @Autowired(required=false)
    public void setIDeployFieldExtendService(IDeployFieldExtendService iDeployFieldExtendService) {
        DataSchemeDeployHelper.iDeployFieldExtendService = iDeployFieldExtendService;
    }

    @Autowired
    public void setDataFieldDefaultValueCache(DataFieldDefaultValueCache dataFieldDefaultValueCache) {
        DataSchemeDeployHelper.dataFieldDefaultValueCache = dataFieldDefaultValueCache;
    }

    public static DesignDataModelService getDesignDataModelService() {
        return designDataModelService;
    }

    public static IDesignDataSchemeService getDesignDataSchemeService() {
        return designDataSchemeService;
    }

    public static boolean splitTable(Collection<DesignColumnModelDefine> allColumns) {
        if (allColumns.size() >= maxFieldCount) {
            return true;
        }
        int byteCount = 0;
        for (DesignColumnModelDefine column : allColumns) {
            byteCount += DataSchemeDeployHelper.getFieldBytes(column.getColumnType(), column.getPrecision());
        }
        return byteCount >= maxByteCount;
    }

    private static int getFieldBytes(ColumnModelType type, Integer size) {
        if (null == size) {
            size = 0;
        }
        switch (type) {
            case DOUBLE: {
                return 8;
            }
            case INTEGER: {
                return 8;
            }
            case BIGDECIMAL: {
                return size + 2;
            }
            case STRING: {
                return 4 * size;
            }
            case CLOB: {
                return 2000;
            }
            case DATETIME: {
                return 8;
            }
            case BOOLEAN: {
                return 1;
            }
            case UUID: {
                return 200;
            }
            case ATTACHMENT: {
                return 200;
            }
            case BLOB: {
                return 200;
            }
        }
        return 8;
    }

    public static Set<String> union(Set<String> set1, Set<String> set2) {
        HashSet<String> result = new HashSet<String>(set1);
        result.retainAll(set2);
        return result;
    }

    public static Set<String> diff(Set<String> set1, Set<String> set2) {
        HashSet<String> result = new HashSet<String>(set1);
        result.removeAll(set2);
        return result;
    }

    public static String getParentCatalogKey(String dataSchemeKey, String parentGroupKey) {
        if (!StringUtils.hasLength(parentGroupKey) || "00000000-0000-0000-0000-000000000000".equals(parentGroupKey)) {
            return dataSchemeKey;
        }
        return parentGroupKey;
    }

    public static DesignTableModel createTableModel(DataTable dataTable, String defaultCode) {
        DesignTableModel tableModelDefine = designDataModelService.createTableModel();
        DataSchemeDeployHelper.updateTableModel(tableModelDefine, dataTable);
        if (StringUtils.hasLength(defaultCode)) {
            tableModelDefine.setCode(defaultCode);
            tableModelDefine.setName(defaultCode.toUpperCase());
        }
        return tableModelDefine;
    }

    public static DesignColumnModelDefine createColumnModel(DeployContext context, Collection<String> columnNames, DataField dataField) throws JQException {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        DataSchemeDeployHelper.updateColumnModel(context, columnModelDefine, dataField, false, () -> DataSchemeDeployHelper.getColumnName(columnNames, dataField));
        return columnModelDefine;
    }

    public static DesignCatalogModelDefine createCatalogModel(DataScheme dataScheme) {
        DesignCatalogModelDefine catalogModelDefine = catalogModelService.createCatalogModelDefine();
        catalogModelDefine.setID(dataScheme.getKey());
        catalogModelDefine.setTitle(dataScheme.getTitle());
        catalogModelDefine.setParentID("10000000-1100-1110-1111-000000000000");
        return catalogModelDefine;
    }

    public static DesignCatalogModelDefine createCatalogModel(DataGroup dataGroup) {
        DesignCatalogModelDefine catalogModelDefine = catalogModelService.createCatalogModelDefine();
        catalogModelDefine.setID(dataGroup.getKey());
        catalogModelDefine.setTitle(dataGroup.getTitle());
        catalogModelDefine.setParentID(DataSchemeDeployHelper.getParentCatalogKey(dataGroup.getDataSchemeKey(), dataGroup.getParentKey()));
        return catalogModelDefine;
    }

    public static List<DesignCatalogModelDefine> createCatalogModel(Collection<? extends DataGroup> dataGroups) {
        if (null == dataGroups) {
            return new ArrayList<DesignCatalogModelDefine>();
        }
        return dataGroups.stream().map(DataSchemeDeployHelper::createCatalogModel).collect(Collectors.toList());
    }

    public static List<DesignCatalogModelDefine> createCatalogModel(DataScheme dataScheme, Collection<? extends DataGroup> dataGroups) {
        ArrayList<DesignCatalogModelDefine> result = new ArrayList<DesignCatalogModelDefine>();
        result.add(DataSchemeDeployHelper.createCatalogModel(dataScheme));
        if (null != dataGroups) {
            for (DataGroup dataGroup : dataGroups) {
                result.add(DataSchemeDeployHelper.createCatalogModel(dataGroup));
            }
        }
        return result;
    }

    public static void updateTableModel(DesignTableModel tableModelDefine, DataTable dataTable) {
        tableModelDefine.setCode(dataTable.getCode());
        if (!StringUtils.hasLength(tableModelDefine.getName())) {
            tableModelDefine.setName(dataTable.getCode().toUpperCase());
        }
        tableModelDefine.setTitle(dataTable.getTitle());
        tableModelDefine.setDesc(dataTable.getDesc());
        tableModelDefine.setCatalogID(DataSchemeDeployHelper.getParentCatalogKey(dataTable.getDataSchemeKey(), dataTable.getDataGroupKey()));
        tableModelDefine.setCreateTime(new Date());
        tableModelDefine.setType(TableModelType.DATA);
        tableModelDefine.setKind(TableModelKind.DEFAULT);
        tableModelDefine.setOwner("NR");
        tableModelDefine.setUpdateTime(new Date());
    }

    public static String getColumnName(Collection<String> columnNames, DataField dataField) {
        String code = dataField.getCode().toUpperCase();
        if (DataFieldKind.BUILT_IN_FIELD == dataField.getDataFieldKind()) {
            return code;
        }
        if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind() && ("MDCODE".equalsIgnoreCase(dataField.getCode()) || "DATATIME".equalsIgnoreCase(dataField.getCode()))) {
            return code;
        }
        if (code.length() >= 20) {
            code = code.substring(0, 20);
            if (null != columnNames && !columnNames.isEmpty()) {
                code = DataSchemeDeployHelper.getUniqueCode(code, c -> !columnNames.contains(c));
            }
        }
        if (null != iFieldNameProvider) {
            String fieldName = iFieldNameProvider.getFieldName(dataField, code);
            if (StringUtils.hasText(fieldName)) {
                return fieldName;
            }
        } else if (useAlisa && StringUtils.hasText(dataField.getAlias())) {
            return dataField.getAlias();
        }
        return code;
    }

    public static String getUniqueCode(String code, Predicate<String> uniqueChecker) {
        int subfix = 0;
        String newcode = code;
        while (!uniqueChecker.test(newcode)) {
            newcode = code + "_" + ++subfix;
        }
        return newcode;
    }

    public static void updateColumnModel(DeployContext context, DesignColumnModelDefine columnModelDefine, DataField dataField, boolean existData, Supplier<String> nameSupplier) throws JQException {
        int decimal;
        columnModelDefine.setCode(dataField.getCode());
        if (null != nameSupplier) {
            columnModelDefine.setName(nameSupplier.get());
        }
        columnModelDefine.setTitle(dataField.getTitle());
        columnModelDefine.setDesc(dataField.getDesc());
        columnModelDefine.setColumnType(dataField.getDataFieldType().toColumnModelType());
        columnModelDefine.setAggrType(null == dataField.getDataFieldGatherType() ? AggrType.NONE : AggrType.forValue((int)dataField.getDataFieldGatherType().getValue()));
        columnModelDefine.setApplyType(null == dataField.getDataFieldApplyType() ? ApplyType.NONE : dataField.getDataFieldApplyType().getApplyType());
        if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind() && "ADJUST".equals(dataField.getCode())) {
            columnModelDefine.setDefaultValue("0");
        } else {
            columnModelDefine.setDefaultValue(dataFieldDefaultValueCache.getDefaultValue(dataField.getDataSchemeKey(), dataField.getKey()));
        }
        if ("SBID".equals(columnModelDefine.getCode())) {
            columnModelDefine.setNullAble(dataField.getNullable().booleanValue());
        } else {
            columnModelDefine.setNullAble(DataFieldKind.PUBLIC_FIELD_DIM != dataField.getDataFieldKind() && DataFieldKind.TABLE_FIELD_DIM != dataField.getDataFieldKind());
        }
        int precision = null == dataField.getPrecision() ? 0 : dataField.getPrecision();
        int n = decimal = null == dataField.getDecimal() ? 0 : dataField.getDecimal();
        if (existData) {
            if (dataField.isEncrypted()) {
                precision = context.getSymmetricEncryptor().getCiphertextMaxLength(precision);
            }
            if (columnModelDefine.getPrecision() < precision) {
                columnModelDefine.setPrecision(precision);
            }
            if (columnModelDefine.getDecimal() < decimal) {
                columnModelDefine.setDecimal(decimal);
            }
        } else {
            columnModelDefine.setPrecision(dataField.isEncrypted() ? context.getSymmetricEncryptor().getCiphertextMaxLength(precision) : precision);
            columnModelDefine.setDecimal(decimal);
        }
        if (dataField.isEncrypted()) {
            columnModelDefine.setSceneId(context.getDataScheme().getEncryptScene());
        } else {
            columnModelDefine.setSceneId(null);
        }
        columnModelDefine.setMultival(dataField.isAllowMultipleSelect());
        if (null != dataField.getFormatProperties()) {
            columnModelDefine.setShowFormat(dataField.getFormatProperties().getPattern());
        } else {
            columnModelDefine.setShowFormat(null);
        }
        columnModelDefine.setMeasureUnit(dataField.getMeasureUnit());
        columnModelDefine.setKind(ColumnModelKind.DEFAULT);
        if (StringUtils.hasLength(dataField.getRefDataEntityKey()) && !"ADJUST".equals(dataField.getCode())) {
            TableModelDefine tableModel = periodEntityAdapter.isPeriodEntity(dataField.getRefDataEntityKey()) ? periodEntityAdapter.getPeriodEntityTableModel(dataField.getRefDataEntityKey()) : entityMetaService.getTableModel(dataField.getRefDataEntityKey());
            if (null == tableModel) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY_1, String.format("\u672a\u627e\u5230%s[%s]\u5173\u8054\u679a\u4e3e\u7684\u5b58\u50a8\u6a21\u578b\u4fe1\u606f\uff01", dataField.getTitle(), dataField.getCode()));
            }
            columnModelDefine.setReferTableID(tableModel.getID());
            columnModelDefine.setReferColumnID(tableModel.getBizKeys());
        } else {
            columnModelDefine.setReferTableID(null);
            columnModelDefine.setReferColumnID(null);
        }
        columnModelDefine.setOrder(DataSchemeDeployHelper.orderToNum(dataField.getOrder()));
        if (null != iDeployFieldExtendService) {
            iDeployFieldExtendService.doExtend(dataField, columnModelDefine);
        }
    }

    public static DataFieldDeployInfoDO createDeployInfo(DesignTableModelDefine tableModel, DesignColumnModelDefine columnModel, DataField dataField) {
        DataFieldDeployInfoDO deployInfo = new DataFieldDeployInfoDO();
        deployInfo.setDataSchemeKey(dataField.getDataSchemeKey());
        deployInfo.setDataTableKey(dataField.getDataTableKey());
        deployInfo.setSourceTableKey(dataField.getDataTableKey());
        deployInfo.setDataFieldKey(dataField.getKey());
        deployInfo.setTableModelKey(columnModel.getTableID());
        deployInfo.setTableName(tableModel.getName());
        deployInfo.setColumnModelKey(columnModel.getID());
        deployInfo.setFieldName(columnModel.getName());
        deployInfo.setUpdateTime(Instant.now());
        return deployInfo;
    }

    public static boolean isRequiredField(DataField dataField) {
        return DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind() || DataFieldKind.TABLE_FIELD_DIM == dataField.getDataFieldKind() || DataFieldKind.BUILT_IN_FIELD == dataField.getDataFieldKind();
    }

    public static boolean compareBaseObj(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (null == obj1 || null == obj2) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static boolean compareDataScheme(DataScheme source, DataScheme target) {
        return source.getUpdateTime().equals(target.getUpdateTime());
    }

    public static boolean compareDataDimension(DataDimension source, DataDimension target) {
        if (!DataSchemeDeployHelper.compareBaseObj(source.getOrder(), target.getOrder())) {
            return false;
        }
        if (source.getDimensionType() != target.getDimensionType()) {
            return false;
        }
        if (source.getPeriodType() != target.getPeriodType()) {
            return false;
        }
        if (source.getPeriodPattern() != target.getPeriodPattern()) {
            return false;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataSchemeKey(), target.getDataSchemeKey())) {
            return false;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDimKey(), target.getDimKey())) {
            return false;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getVersion(), target.getVersion())) {
            return false;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getLevel(), target.getLevel())) {
            return false;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getReportDim(), target.getReportDim())) {
            return false;
        }
        return DataSchemeDeployHelper.compareBaseObj(source.getDimAttribute(), target.getDimAttribute());
    }

    public static boolean compareDataGroup(DataGroup source, DataGroup target) {
        return source.getUpdateTime().equals(target.getUpdateTime());
    }

    public static DeployType compareDataTable(DataTable source, DataTable target) {
        if (source.getUpdateTime().equals(target.getUpdateTime())) {
            return DeployType.NONE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getCode(), target.getCode())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getTitle(), target.getTitle())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(Arrays.toString(source.getBizKeys()), Arrays.toString(target.getBizKeys()))) {
            return DeployType.UPDATE;
        }
        if (source.getDataTableGatherType() != target.getDataTableGatherType()) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataGroupKey(), target.getDataGroupKey())) {
            return DeployType.UPDATE;
        }
        if (source.isRepeatCode() != target.isRepeatCode()) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDesc(), target.getDesc())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(Arrays.toString(source.getGatherFieldKeys()), Arrays.toString(target.getGatherFieldKeys()))) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getVersion(), target.getVersion())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getLevel(), target.getLevel())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getOrder(), target.getOrder())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getTrackHistory(), target.getTrackHistory())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getOwner(), target.getOwner())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getSyncError(), target.getSyncError())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getAlias(), target.getAlias())) {
            return DeployType.UPDATE_NODPLOY;
        }
        return DeployType.NONE;
    }

    public static DeployType compareDataField(DesignDataField source, DataField target) {
        if (source.getUpdateTime().equals(target.getUpdateTime())) {
            return DeployType.NONE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getCode(), target.getCode())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getTitle(), target.getTitle())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDecimal(), target.getDecimal())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getPrecision(), target.getPrecision())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getMeasureUnit(), target.getMeasureUnit())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getFormatProperties(), target.getFormatProperties())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataFieldType(), target.getDataFieldType())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataFieldApplyType(), target.getDataFieldApplyType())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataFieldGatherType(), target.getDataFieldGatherType())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getRefDataEntityKey(), target.getRefDataEntityKey())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getRefDataFieldKey(), target.getRefDataFieldKey())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getAllowMultipleSelect(), target.getAllowMultipleSelect())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDesc(), target.getDesc())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataFieldKind(), target.getDataFieldKind())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getNullable(), target.getNullable())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getOrder(), target.getOrder())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getEncrypted(), target.getEncrypted())) {
            return DeployType.UPDATE;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDefaultValue(), target.getDefaultValue())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataTableKey(), target.getDataTableKey())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getValidationRules(), target.getValidationRules())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getUseAuthority(), target.getUseAuthority())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getAlias(), target.getAlias())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getVersion(), target.getVersion())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getSecretLevel(), target.getSecretLevel())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getLevel(), target.getLevel())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getAllowUndefinedCode(), target.getAllowUndefinedCode())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getChangeWithPeriod(), target.getChangeWithPeriod())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getGenerateVersion(), target.getGenerateVersion())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getAllowTreeSum(), target.getAllowTreeSum())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getFormula(), target.getFormula())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getFormulaDesc(), target.getFormulaDesc())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getZbType(), target.getZbType())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getZbSchemeVersion(), target.getZbSchemeVersion())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getDataMaskCode(), target.getDataMaskCode())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getRestrictType(), target.getRestrictType())) {
            return DeployType.UPDATE_NODPLOY;
        }
        if (!DataSchemeDeployHelper.compareBaseObj(source.getRefParameter(), target.getRefParameter())) {
            return DeployType.UPDATE_NODPLOY;
        }
        return DeployType.NONE;
    }

    public static double orderToNum(String order) {
        double num = 0.0;
        if (StringUtils.hasText(order)) {
            if (order.length() > 8) {
                throw new RuntimeException("Order \u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc78\u4f4d");
            }
            char[] charArray = order.toCharArray();
            for (int i = 0; i < charArray.length; ++i) {
                int indexOf = CHARS.indexOf(charArray[charArray.length - i - 1]);
                num += (double)indexOf * Math.pow(36.0, i);
            }
        }
        return num;
    }

    public static String orderToString(long order) {
        StringBuilder rt = new StringBuilder(8);
        while (order > 0L) {
            rt.insert(0, CHARS.charAt((int)(order % 36L)));
            order /= 36L;
        }
        return rt.toString();
    }

    static {
        DATAFIELD_COMPARATOR = (o1, o2) -> {
            if ("MDCODE".equals(o1.getCode())) {
                return -1;
            }
            if ("MDCODE".equals(o2.getCode())) {
                return 1;
            }
            if ("DATATIME".equals(o1.getCode())) {
                return -1;
            }
            if ("DATATIME".equals(o2.getCode())) {
                return 1;
            }
            if (o1.getDataFieldKind() == o2.getDataFieldKind()) {
                return o1.compareTo(o2);
            }
            if (DataFieldKind.PUBLIC_FIELD_DIM == o1.getDataFieldKind()) {
                return -1;
            }
            if (DataFieldKind.PUBLIC_FIELD_DIM == o2.getDataFieldKind()) {
                return 1;
            }
            if (DataFieldKind.TABLE_FIELD_DIM == o1.getDataFieldKind()) {
                return -1;
            }
            if (DataFieldKind.TABLE_FIELD_DIM == o2.getDataFieldKind()) {
                return 1;
            }
            if ("BIZKEYORDER".equals(o1.getCode())) {
                return -1;
            }
            if ("BIZKEYORDER".equals(o2.getCode())) {
                return 1;
            }
            if ("FLOATORDER".equals(o1.getCode())) {
                return -1;
            }
            if ("FLOATORDER".equals(o2.getCode())) {
                return 1;
            }
            if ("SBID".equals(o1.getCode())) {
                return -1;
            }
            if ("SBID".equals(o2.getCode())) {
                return 1;
            }
            return o1.compareTo(o2);
        };
    }
}

