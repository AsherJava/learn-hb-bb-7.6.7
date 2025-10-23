/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.DeleteAttributes;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={Exception.class})
public class DeleteSchemeVisitor
implements SchemeNodeVisitor<DeleteAttributes> {
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;

    public VisitorResult preVisitNode(SchemeNode<DeleteAttributes> ele) {
        if (ele.getType() == NodeType.SCHEME.getValue()) {
            String scheme = ele.getKey();
            this.dataSchemeDao.delete(scheme);
            this.dataTableDao.deleteByDataScheme(scheme);
            this.dataGroupDao.deleteByDataScheme(scheme);
            this.dataDimDao.deleteByDataScheme(scheme);
            this.dataFieldDao.deleteByDataScheme(scheme);
            return VisitorResult.TERMINATE;
        }
        if (((NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue()) & ele.getType()) != 0) {
            return VisitorResult.CONTINUE;
        }
        return null;
    }

    public DeleteAttributes visitRootIsSchemeNode(DesignDataScheme scheme) {
        return null;
    }

    public DeleteAttributes visitRootIsGroupNode(DesignDataGroup group) {
        this.dataGroupDao.delete(group.getKey());
        return null;
    }

    public DeleteAttributes visitRootIsTableNode(DesignDataTable table) {
        return null;
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, DeleteAttributes> visitSchemeGroupNode(SchemeNode<DeleteAttributes> next, List<DG> groups, List<DS> schemes) {
        return null;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, DeleteAttributes> visitSchemeNode(SchemeNode<DeleteAttributes> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        return null;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<DeleteAttributes> ele, List<DA> attributes) {
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, DeleteAttributes> visitGroupNode(SchemeNode<DeleteAttributes> ele, List<DG> dataGroups, List<DT> dataTables) {
        List<String> tables;
        List<String> groups = dataGroups.stream().map(Grouped::getKey).collect(Collectors.toList());
        if (!groups.isEmpty()) {
            this.dataGroupDao.batchDelete(groups);
        }
        if (!(tables = dataTables.stream().map(Basic::getKey).collect(Collectors.toList())).isEmpty()) {
            for (String table : tables) {
                this.dataFieldDao.deleteByTable(table);
            }
            this.dataTableDao.batchDelete(tables);
        }
        return null;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<DeleteAttributes> ele, List<DF> dataFields) {
    }
}

