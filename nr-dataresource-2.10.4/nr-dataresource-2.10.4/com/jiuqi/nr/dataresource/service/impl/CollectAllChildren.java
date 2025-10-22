/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeBeanUtils;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectAllChildren
implements RuntimeSchemeVisitor<Void> {
    private final Set<RuntimeDataSchemeNodeDTO> values = new HashSet<RuntimeDataSchemeNodeDTO>();
    private final IEntityMetaService entityMetaService;
    private final PeriodEngineService periodEngineService;
    private final IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private final IDataTableDao<DataTableDO> iDataTableDao;
    private final IDataFieldDao<DataFieldDO> iDataFieldDao;
    private final int unShow = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();
    private NodeFilter filter;

    public CollectAllChildren(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, IDataTableRelDao<DataTableRelDO> iDataTableRelDao, IDataTableDao<DataTableDO> iDataTableDao, IDataFieldDao<DataFieldDO> iDataFieldDao) {
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
        this.iDataTableRelDao = iDataTableRelDao;
        this.iDataTableDao = iDataTableDao;
        this.iDataFieldDao = iDataFieldDao;
    }

    public Set<RuntimeDataSchemeNodeDTO> getValues() {
        return this.values;
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.filter != null && !this.filter.test(ele)) {
            return VisitorResult.CONTINUE;
        }
        if (ele.getType() == NodeType.SCHEME_GROUP.getValue() && !DataSchemeBeanUtils.getDataSchemeAuthService().canReadGroup(ele.getKey()) || ele.getType() == NodeType.SCHEME.getValue() && !DataSchemeBeanUtils.getDataSchemeAuthService().canReadScheme(ele.getKey())) {
            return VisitorResult.CONTINUE;
        }
        return null;
    }

    public Void visitRootIsSchemeNode(DataScheme scheme) {
        return null;
    }

    public Void visitRootIsGroupNode(DataGroup group) {
        return null;
    }

    public Void visitRootIsTableNode(DataTable table) {
        return null;
    }

    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        this.extracted(dataGroups, dataTables);
        RuntimeDataSchemeNodeDTO unit = null;
        RuntimeDataSchemeNodeDTO period = null;
        ArrayList<RuntimeDataSchemeNodeDTO> scopes = new ArrayList<RuntimeDataSchemeNodeDTO>();
        ArrayList<RuntimeDataSchemeNodeDTO> dimsOther = new ArrayList<RuntimeDataSchemeNodeDTO>();
        dims.removeIf(x -> AdjustUtils.isAdjust((String)x.getDimKey()));
        for (DataDimension dim : dims) {
            String dimKey = dim.getDimKey();
            DimensionType dimensionType = dim.getDimensionType();
            try {
                if (dimensionType == DimensionType.PERIOD) {
                    IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                    IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                    period = new RuntimeDataSchemeNodeDTO(periodEntity, ele.getKey());
                    if (this.filter == null || this.filter.test((Object)period)) continue;
                    period = null;
                    continue;
                }
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                RuntimeDataSchemeNodeDTO node = new RuntimeDataSchemeNodeDTO(iEntityDefine, ele.getKey());
                if (this.filter != null && !this.filter.test((Object)node)) continue;
                if (dimensionType == DimensionType.UNIT_SCOPE) {
                    scopes.add(node);
                }
                if (dimensionType == DimensionType.UNIT) {
                    unit = node;
                }
                if (dimensionType != DimensionType.DIMENSION) continue;
                dimsOther.add(node);
            }
            catch (Exception e) {
                throw new DataResourceException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
            }
        }
        if (scopes.isEmpty()) {
            if (unit != null) {
                this.values.add(unit);
            }
        } else {
            this.values.addAll(scopes);
        }
        if (period != null) {
            this.values.add(period);
        }
        this.values.addAll(dimsOther);
        return null;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<Void> ele, List<DA> attributes) {
    }

    public <DG extends DataGroup, DT extends DataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        this.extracted(dataGroups, dataTables);
        return null;
    }

    private <DG extends DataGroup, DT extends DataTable> void extracted(List<DG> dataGroups, List<DT> dataTables) {
        RuntimeDataSchemeNodeDTO dto;
        for (DataGroup dataGroup : dataGroups) {
            dto = new RuntimeDataSchemeNodeDTO(dataGroup);
            if (this.values.contains(dto)) {
                throw new RuntimeException("\u6709\u73af\u5f62\u6570\u636e");
            }
            this.build(dto);
            this.values.add(dto);
        }
        for (DataTable dataTable : dataTables) {
            if (dataTable.getDataTableType() == null || dataTable.getDataTableType() == DataTableType.SUB_TABLE) continue;
            dto = new RuntimeDataSchemeNodeDTO(dataTable);
            if (this.values.contains(dto)) {
                throw new RuntimeException("\u6709\u73af\u5f62\u6570\u636e");
            }
            if (this.filter != null && !this.filter.test((Object)dto)) continue;
            this.build(dto);
            this.values.add(dto);
        }
    }

    public <DF extends DataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
        RuntimeDataSchemeNodeDTO dto;
        ArrayList<DF> fields = new ArrayList<DF>(dataFields);
        List desTable = this.iDataTableRelDao.getByDesTable(ele.getKey());
        if (!desTable.isEmpty()) {
            List subTables = this.iDataTableDao.batchGet(desTable.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
            for (DataTableDO subTable : subTables) {
                dto = new RuntimeDataSchemeNodeDTO((DataTable)subTable);
                dto.setParentKey(ele.getKey());
                if (this.filter != null && !this.filter.test((Object)dto)) continue;
                this.values.add(dto);
                fields.addAll(this.iDataFieldDao.getByTable(subTable.getKey()));
            }
        }
        for (DataField dataField : fields) {
            IEntityDefine entityDefine;
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if ((this.unShow & dataFieldKind.getValue()) != 0) continue;
            dto = new RuntimeDataSchemeNodeDTO(dataField);
            if (this.filter != null && !this.filter.test((Object)dto)) continue;
            String refDataEntityKey = dataField.getRefDataEntityKey();
            if (refDataEntityKey != null && (entityDefine = this.entityMetaService.queryEntity(refDataEntityKey)) != null && dataFieldKind == DataFieldKind.TABLE_FIELD_DIM) {
                dto.setTableDim(true);
                dto.setDataTableKey(dataField.getDataTableKey());
            }
            this.build(dto);
            this.values.add(dto);
        }
    }

    public <DG extends DataGroup, DS extends DataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        RuntimeDataSchemeNodeDTO dto;
        for (DataGroup group : groups) {
            dto = new RuntimeDataSchemeNodeDTO(group);
            if (this.values.contains(dto)) {
                throw new RuntimeException("\u6709\u73af\u5f62\u6570\u636e");
            }
            if (!DataSchemeBeanUtils.getDataSchemeAuthService().canReadGroup(dto.getKey()) || this.filter != null && !this.filter.test((Object)dto)) continue;
            this.build(dto);
            this.values.add(dto);
        }
        for (DataScheme scheme : schemes) {
            dto = new RuntimeDataSchemeNodeDTO(scheme);
            if (this.values.contains(dto)) {
                throw new RuntimeException("\u6709\u73af\u5f62\u6570\u636e");
            }
            if (!DataSchemeBeanUtils.getDataSchemeAuthService().canReadScheme(dto.getKey()) || this.filter != null && !this.filter.test((Object)dto)) continue;
            this.build(dto);
            this.values.add(dto);
        }
        return null;
    }

    public void build(RuntimeDataSchemeNodeDTO dto) {
        dto.setCode(null);
    }

    public NodeFilter getFilter() {
        return this.filter;
    }

    public void setFilter(NodeFilter filter) {
        this.filter = filter;
    }
}

