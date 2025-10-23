/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 */
package com.jiuqi.nr.datascheme.loader.strategy.des;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class GroupStrategy
implements DataSchemeLoaderStrategy {
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    private final Logger logger = LoggerFactory.getLogger(GroupStrategy.class);
    private static final int DEFAULT_INTEREST_TYPE = NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.DIM.getValue() | NodeType.MD_INFO.getValue();
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();

    GroupStrategy() {
    }

    public boolean matching(int nodeType) {
        return (NodeType.GROUP.getValue() & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        DesignDataGroup group = this.dataGroupDao.get(root.getKey());
        Object other = schemeNodeVisitor.visitRootIsGroupNode(group);
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List<DesignDataGroupDO> groups = Collections.emptyList();
        if ((interestType & NodeType.GROUP.getValue()) != 0) {
            groups = this.dataGroupDao.getByParent(next.getKey());
        }
        List<DesignDataTableDO> dataTables = Collections.emptyList();
        if ((interestType & TABLE) != 0) {
            dataTables = this.dataTableDao.getByGroup(next.getKey());
            dataTables = dataTables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
        }
        List<DesignDataGroupDO> copy = BaseLevelLoader.copyGroup(groups);
        List<DesignDataTableDO> tables = BaseLevelLoader.copyTable(dataTables);
        this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u5206\u7ec4\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)groups.size(), (Object)dataTables.size());
        Map otherPar = schemeNodeVisitor.visitGroupNode(next, copy, tables);
        ArrayList list = new ArrayList();
        list.addAll(BaseLevelLoader.group2SchemeNode(groups, otherPar));
        list.addAll(BaseLevelLoader.table2SchemeNode(dataTables, otherPar));
        return list;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, ReverseSchemeNodeVisitor<E> reverse, Integer interestType) {
        SchemeNode preNode;
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        DesignDataGroupDO dataGroupDO = this.dataGroupDao.get(next.getKey());
        String parentKey = dataGroupDO.getParentKey();
        String dataSchemeKey = dataGroupDO.getDataSchemeKey();
        this.logger.trace("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848\u4e0b\u5206\u7ec4");
        List<Object> groups = Collections.emptyList();
        List<Object> tables = Collections.emptyList();
        List<Object> dims = Collections.emptyList();
        if (parentKey == null) {
            if ((interestType & NodeType.GROUP.getValue()) != 0) {
                groups = this.dataGroupDao.getByCondition(dataSchemeKey, null);
            }
            if ((interestType & TABLE) != 0) {
                tables = this.dataTableDao.getByCondition(dataSchemeKey, null);
                tables = tables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            }
            if ((interestType & NodeType.DIM.getValue()) != 0) {
                dims = this.dataDimDao.getByDataScheme(dataSchemeKey);
            }
            preNode = new SchemeNode(dataSchemeKey, NodeType.SCHEME.getValue());
        } else {
            if ((interestType & NodeType.GROUP.getValue()) != 0) {
                groups = this.dataGroupDao.getByParent(parentKey);
            }
            if ((interestType & TABLE) != 0) {
                tables = this.dataTableDao.getByGroup(parentKey);
                tables = tables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            }
            preNode = new SchemeNode(parentKey, NodeType.GROUP.getValue());
        }
        Object other = reverse.visitGoTaNode(next, groups, tables, dims);
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)groups.size(), (Object)tables.size());
        preNode.setOther(other);
        return preNode;
    }
}

