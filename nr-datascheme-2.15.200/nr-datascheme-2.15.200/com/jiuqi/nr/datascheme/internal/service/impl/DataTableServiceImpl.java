/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.BizKeyOrder;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableDesignService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Exception.class})
@Service
public class DataTableServiceImpl
implements DataTableDesignService {
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DesignDataModelService dataModelService;
    private final Logger logger = LoggerFactory.getLogger(DataTableServiceImpl.class);

    @Override
    public <E extends DesignDataTable> String[] insertDataTables(List<E> dataTable) {
        if (dataTable == null) {
            return null;
        }
        if (dataTable.isEmpty()) {
            return new String[0];
        }
        ArrayList<DesignDataTableDO> insert = new ArrayList<DesignDataTableDO>(dataTable.size());
        List<DesignDataDimDO> dims = this.dataDimDao.getByDataScheme(((DesignDataTable)dataTable.get(0)).getDataSchemeKey());
        for (DesignDataTable e : dataTable) {
            DataTableType dataTableType;
            if (e.getKey() == null) {
                e.setKey(UUID.randomUUID().toString());
            }
            if (null == (dataTableType = e.getDataTableType())) continue;
            ArrayList<DesignDataFieldDO> bizKeys = new ArrayList<DesignDataFieldDO>(5);
            String dataSchemeKey = e.getDataSchemeKey();
            if (!dims.isEmpty()) {
                if (DataTableType.MD_INFO == dataTableType) {
                    e.setKey(DataSchemeUtils.getUUID(e.getDataSchemeKey() + "MD_INFO"));
                    bizKeys.addAll(this.createDimFieldsForMdInfo(e, dims));
                } else {
                    bizKeys.addAll(this.createDimFields(e, dims));
                }
                if (DataTableType.DETAIL == dataTableType || DataTableType.ACCOUNT == dataTableType) {
                    DesignDataFieldDO bizKeyField = this.createBizKeyField(e.getKey(), dataSchemeKey);
                    this.createFloatOrderField(e.getKey(), dataSchemeKey);
                    Boolean repeatCode = e.getRepeatCode();
                    if (repeatCode != null && repeatCode.booleanValue()) {
                        bizKeys.add(bizKeyField);
                    }
                }
                if (DataTableType.ACCOUNT == dataTableType) {
                    this.createAccountIdField(e.getKey(), dataSchemeKey);
                }
            }
            e.setBizKeys(BizKeyOrder.order(bizKeys));
            insert.add(Convert.iDt2Do(e));
        }
        return this.dataTableDao.batchInsert(insert);
    }

    @NotNull
    private <E extends DesignDataTable> List<DesignDataFieldDO> createDimFieldsForMdInfo(E e, List<DesignDataDimDO> dims) {
        List<DesignDataDimDO> publicDims = dims.stream().filter(d -> DimensionType.UNIT == d.getDimensionType() || DimensionType.PERIOD == d.getDimensionType()).collect(Collectors.toList());
        List<DesignDataFieldDO> dimField = this.buildFieldByDim(publicDims, e);
        for (DesignDataFieldDO designDataFieldDO : dimField) {
            if ("MDCODE".equals(designDataFieldDO.getCode())) {
                designDataFieldDO.setKey(DataSchemeUtils.getUUID(designDataFieldDO.getDataTableKey() + "MDCODE"));
                continue;
            }
            if ("DATATIME".equals(designDataFieldDO.getCode())) {
                designDataFieldDO.setKey(DataSchemeUtils.getUUID(designDataFieldDO.getDataTableKey() + "DATATIME"));
                continue;
            }
            designDataFieldDO.setKey(DataSchemeUtils.getUUID(designDataFieldDO.getDataTableKey() + designDataFieldDO.getCode()));
        }
        this.dataFieldDao.batchInsert(dimField);
        this.logger.debug("\u521b\u5efa\u6570\u636e\u8868\u4e0b\u7684\u7ef4\u5ea6\u6307\u6807/\u5b57\u6bb5 {}", (Object)dimField);
        return dimField;
    }

    @NotNull
    private <E extends DesignDataTable> List<DesignDataFieldDO> createDimFields(E e, List<DesignDataDimDO> dims) {
        List<DesignDataFieldDO> dimField = this.buildFieldByDim(dims, e);
        this.dataFieldDao.batchInsert(dimField);
        this.logger.debug("\u521b\u5efa\u6570\u636e\u8868\u4e0b\u7684\u7ef4\u5ea6\u6307\u6807/\u5b57\u6bb5 {}", (Object)dimField);
        return dimField;
    }

    @Override
    public <E extends DesignDataTable> void updateDataTables(List<E> dataTable) {
        if (dataTable == null) {
            return;
        }
        if (dataTable.isEmpty()) {
            return;
        }
        ArrayList<DesignDataTableDO> update = new ArrayList<DesignDataTableDO>(dataTable.size());
        for (DesignDataTable e : dataTable) {
            DataTableType dataTableType = e.getDataTableType();
            if (null == dataTableType) continue;
            if (DataTableType.DETAIL == dataTableType) {
                this.solutionBizKeys(e);
            }
            if (DataTableType.ACCOUNT == dataTableType) {
                this.solutionBizKeys(e);
                if (!e.isTrackHistory()) {
                    this.solutionGenerateVersionInField(e);
                }
            }
            update.add(Convert.iDt2Do(e));
        }
        this.dataTableDao.batchUpdate(update);
    }

    private <E extends DesignDataTable> void solutionBizKeys(E e) {
        LinkedHashSet<String> bizKeys = new LinkedHashSet<String>(5);
        Boolean repeatCode = e.getRepeatCode();
        String[] keys = e.getBizKeys();
        if (keys != null) {
            bizKeys.addAll(Arrays.asList(keys));
        }
        DesignDataFieldDO field = this.dataFieldDao.getByTableAndCode(e.getKey(), "BIZKEYORDER");
        if (repeatCode == null || !repeatCode.booleanValue()) {
            if (field != null) {
                bizKeys.remove(field.getKey());
            }
        } else {
            bizKeys.add(field.getKey());
        }
        e.setBizKeys(bizKeys.toArray(new String[0]));
    }

    private <E extends DesignDataTable> void solutionGenerateVersionInField(E e) {
        List<DesignDataFieldDO> accountFields;
        DesignDataTableDO oldTable = this.dataTableDao.get(e.getKey());
        if (!oldTable.isTrackHistory() && (accountFields = this.dataFieldDao.getByTableAndKind(e.getKey(), DataFieldKind.FIELD.getValue())) != null) {
            List needUpdateFields = accountFields.stream().filter(DataFieldDO::getChangeWithPeriod).peek(f -> f.setGenerateVersion(true)).collect(Collectors.toList());
            this.dataFieldDao.batchUpdate(needUpdateFields);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<DesignDataTable> searchBy(String scheme, String keyword, int type) {
        List<DesignDataTableDO> r = this.dataTableDao.searchBy(scheme, keyword, type);
        return new ArrayList<DesignDataTable>(r);
    }

    @Override
    @Transactional(readOnly=true)
    public List<DesignDataTable> searchBy(List<String> schemes, String keyword, int type) {
        List<DesignDataTableDO> r = this.dataTableDao.searchBy(schemes, keyword, type);
        return new ArrayList<DesignDataTable>(r);
    }

    private List<DesignDataFieldDO> buildFieldByDim(List<DesignDataDimDO> dims, DesignDataTable table) {
        ArrayList<DesignDataFieldDO> fields = new ArrayList<DesignDataFieldDO>();
        try {
            HashSet<String> codes = new HashSet<String>();
            for (DesignDataDimDO dim : dims) {
                IEntityAttribute bizKeyField;
                TableModelDefine tableModel;
                String title;
                String code;
                String bizKeys;
                IEntityModel entityModel;
                String dimKey = dim.getDimKey();
                Integer precision = null;
                if (DimensionType.UNIT.getValue() == dim.getDimensionType().getValue()) {
                    IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                    entityModel = this.entityMetaService.getEntityModel(iEntityDefine.getId());
                    IEntityAttribute bizKeyField2 = entityModel.getBizKeyField();
                    TableModelDefine tableModel2 = this.entityMetaService.getTableModel(dimKey);
                    bizKeys = tableModel2.getBizKeys();
                    if (bizKeyField2 != null) {
                        precision = bizKeyField2.getPrecision();
                    }
                    code = "MDCODE";
                    title = iEntityDefine.getTitle();
                } else if (DimensionType.DIMENSION.getValue() == dim.getDimensionType().getValue()) {
                    if ("ADJUST".equals(dim.getDimKey())) {
                        code = "ADJUST";
                        title = "\u8c03\u6574\u671f";
                        precision = 100;
                        bizKeys = null;
                    } else {
                        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                        entityModel = this.entityMetaService.getEntityModel(iEntityDefine.getId());
                        tableModel = this.entityMetaService.getTableModel(dimKey);
                        bizKeys = tableModel.getBizKeys();
                        bizKeyField = entityModel.getBizKeyField();
                        if (bizKeyField != null) {
                            precision = bizKeyField.getPrecision();
                        }
                        code = iEntityDefine.getCode();
                        title = iEntityDefine.getTitle();
                    }
                } else {
                    if (DimensionType.PERIOD.getValue() != dim.getDimensionType().getValue()) continue;
                    IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                    IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                    tableModel = periodAdapter.getPeriodEntityTableModel(dimKey);
                    bizKeys = tableModel.getBizKeys();
                    code = "DATATIME";
                    title = periodEntity.getTitle();
                    bizKeyField = this.dataModelService.getColumnModelDefine(bizKeys);
                    if (null != bizKeyField) {
                        precision = bizKeyField.getPrecision();
                    }
                }
                int step = 1;
                while (codes.contains(code)) {
                    code = code + step;
                }
                fields.add(Convert.dimFieldBuild(table, code, title, bizKeys, dimKey, DataFieldKind.PUBLIC_FIELD_DIM, precision));
                codes.add(code);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new SchemeDataException(DataSchemeEnum.DATA_SCHEME_DF_1_1.getMessage());
        }
        return fields;
    }

    private DesignDataFieldDO createBizKeyField(String ownerTableKey, String scheme) {
        DesignDataFieldDO field = new DesignDataFieldDO();
        field.setKey(UUID.randomUUID().toString());
        field.setCode("BIZKEYORDER");
        field.setTitle("BIZKEYORDER");
        field.setDataFieldType(DataFieldType.STRING);
        field.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
        field.setNullable(false);
        field.setUseAuthority(false);
        field.setPrecision(40);
        field.setDataTableKey(ownerTableKey);
        field.setDataSchemeKey(scheme);
        field.setOrder(OrderGenerator.newOrder());
        this.dataFieldDao.insert(field);
        this.logger.debug("\u521b\u5efa\u6570\u636e\u8868\u4e0b\u7684{}\u5b57\u6bb5 {}", (Object)"BIZKEYORDER", (Object)field);
        return field;
    }

    public DesignDataFieldDO createFloatOrderField(String ownerTableKey, String scheme) {
        DesignDataFieldDO field = new DesignDataFieldDO();
        field.setKey(UUID.randomUUID().toString());
        field.setDataTableKey(ownerTableKey);
        field.setDataSchemeKey(scheme);
        field.setCode("FLOATORDER");
        field.setTitle("FLOATORDER");
        field.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
        field.setDataFieldType(DataFieldType.BIGDECIMAL);
        field.setPrecision(16);
        field.setDecimal(5);
        field.setNullable(true);
        field.setUseAuthority(false);
        field.setAllowMultipleSelect(false);
        field.setOrder(OrderGenerator.newOrder());
        this.dataFieldDao.insert(field);
        this.logger.debug("\u521b\u5efa\u6570\u636e\u8868\u4e0b\u7684{}\u5b57\u6bb5 {}", (Object)"FLOATORDER", (Object)field);
        return field;
    }

    private DesignDataFieldDO createAccountIdField(String ownerTableKey, String scheme) {
        DesignDataFieldDO field = new DesignDataFieldDO();
        field.setKey(UUID.randomUUID().toString());
        field.setDataTableKey(ownerTableKey);
        field.setDataSchemeKey(scheme);
        field.setCode("SBID");
        field.setTitle("SBID");
        field.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
        field.setDataFieldType(DataFieldType.STRING);
        field.setPrecision(50);
        field.setNullable(false);
        field.setUseAuthority(false);
        field.setOrder(OrderGenerator.newOrder());
        this.dataFieldDao.insert(field);
        this.logger.debug("\u521b\u5efa\u6570\u636e\u8868\u4e0b\u7684{}\u5b57\u6bb5 {}", (Object)"SBID", (Object)field);
        return field;
    }

    @Override
    @Deprecated
    public <DM extends DesignDataDimension> Map<DesignDataDimension, String> initCoverTable(DesignDataScheme dataScheme, Collection<DM> dims) {
        return null;
    }
}

