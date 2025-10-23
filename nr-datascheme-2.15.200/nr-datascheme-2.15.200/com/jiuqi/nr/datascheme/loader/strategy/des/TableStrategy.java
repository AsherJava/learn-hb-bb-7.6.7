/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 */
package com.jiuqi.nr.datascheme.loader.strategy.des;

import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
class TableStrategy
implements DataSchemeLoaderStrategy {
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    private final Logger logger = LoggerFactory.getLogger(TableStrategy.class);
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();
    private static final int RES_DEFAULT_INTEREST_TYPE = NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();

    TableStrategy() {
    }

    public boolean matching(int nodeType) {
        return (nodeType & TABLE) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        DesignDataTableDO dataTableDO = this.dataTableDao.get(root.getKey());
        Object other = schemeNodeVisitor.visitRootIsTableNode((DesignDataTable)dataTableDO);
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        List<DesignDataFieldDO> fields = null;
        if (interestType == null) {
            fields = this.dataFieldDao.getByTable(next.getKey());
        } else {
            int kind = BaseLevelLoader.nodeType2FieldKind(interestType);
            if (kind != 0) {
                fields = this.dataFieldDao.getByTableAndKind(next.getKey(), kind);
            } else {
                this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u8868\uff0c\u7528\u6237\u4e0d\u5173\u5fc3\u6307\u6807\u8df3\u8fc7");
            }
        }
        if (!CollectionUtils.isEmpty(fields)) {
            this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u8868\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u6307\u6807", (Object)fields.size());
            List<DesignDataFieldDO> copy = BaseLevelLoader.copyField(fields);
            schemeNodeVisitor.visitTableNode(next, copy);
        } else {
            schemeNodeVisitor.visitTableNode(next, Collections.emptyList());
        }
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, ReverseSchemeNodeVisitor<E> reverse, Integer interestType) {
        SchemeNode preNode;
        if (interestType == null) {
            interestType = RES_DEFAULT_INTEREST_TYPE;
        }
        DesignDataTableDO dataTableDO = this.dataTableDao.get(next.getKey());
        String parentKey = dataTableDO.getDataGroupKey();
        String dataSchemeKey = dataTableDO.getDataSchemeKey();
        this.logger.trace("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848\u4e0b\u6570\u636e\u8868");
        List<Object> groups = Collections.emptyList();
        List<Object> tables = Collections.emptyList();
        List<Object> dims = Collections.emptyList();
        if (parentKey == null) {
            if ((interestType & NodeType.GROUP.getValue()) != 0) {
                groups = this.dataGroupDao.getByCondition(dataSchemeKey, null);
            }
            if ((interestType & TABLE) != 0) {
                tables = this.dataTableDao.getByCondition(dataSchemeKey, null);
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
            }
            preNode = new SchemeNode(parentKey, NodeType.GROUP.getValue());
        }
        tables = tables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u8868\uff0c{}\u4e2a\u7ef4\u5ea6", groups.size(), tables.size(), dims.size());
        Object other = reverse.visitGoTaNode(next, groups, tables, dims);
        preNode.setOther(other);
        return preNode;
    }
}

