/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 */
package com.jiuqi.nr.datascheme.loader.strategy.des;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
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
class SchemeStrategy
implements DataSchemeLoaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(SchemeStrategy.class);
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    private static final int DEFAULT_INTEREST_TYPE = NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.MD_INFO.getValue();
    protected static final int RES_INTEREST_TYPE = NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue();
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();

    SchemeStrategy() {
    }

    public boolean matching(int nodeType) {
        return NodeType.SCHEME.getValue() == nodeType;
    }

    public <E> void visitRoot(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        DesignDataSchemeDO designDataSchemeDO = this.dataSchemeDao.get(root.getKey());
        Object other = schemeNodeVisitor.visitRootIsSchemeNode((DesignDataScheme)designDataSchemeDO);
        this.logger.trace("\u8ddf\u8282\u70b9\u8bbf\u95ee\u5b8c\u6210");
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List<DesignDataGroupDO> dataGroups = Collections.emptyList();
        boolean care = false;
        if ((interestType & NodeType.GROUP.getValue()) != 0) {
            dataGroups = this.dataGroupDao.getByCondition(next.getKey(), null);
            care = true;
        }
        List<Object> dataTables = Collections.emptyList();
        if ((interestType & TABLE) != 0) {
            dataTables = this.dataTableDao.getByCondition(next.getKey(), null);
            dataTables = dataTables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            care = true;
        }
        List<Object> dims = Collections.emptyList();
        if ((interestType & NodeType.DIM.getValue()) != 0) {
            dims = this.dataDimDao.getByDataScheme(next.getKey());
            dims.removeIf(x -> x.getDimKey().equals("ADJUST"));
            care = true;
        }
        if (!care) {
            this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u65b9\u6848\uff0c\u7528\u6237\u4e0d\u5173\u5fc3\u65b9\u6848\u4e0b\u4efb\u4f55\u6570\u636e\uff0c\u8df3\u8fc7");
            return null;
        }
        List<DesignDataGroupDO> copy = BaseLevelLoader.copyGroup(dataGroups);
        List<DesignDataTableDO> copyTables = BaseLevelLoader.copyTable(dataTables);
        List<DesignDataDimDO> copyDims = BaseLevelLoader.copyDims(dims);
        this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u65b9\u6848\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)dataGroups.size(), (Object)dataTables.size());
        Map otherPar = schemeNodeVisitor.visitSchemeNode(next, copy, copyTables, copyDims);
        ArrayList list = new ArrayList();
        list.addAll(BaseLevelLoader.group2SchemeNode(dataGroups, otherPar));
        list.addAll(BaseLevelLoader.table2SchemeNode(dataTables, otherPar));
        return list;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, ReverseSchemeNodeVisitor<E> reverse, Integer interestType) {
        if (interestType == null) {
            interestType = RES_INTEREST_TYPE;
        }
        DesignDataSchemeDO designDataSchemeDO = this.dataSchemeDao.get(next.getKey());
        String parentKey = designDataSchemeDO.getDataGroupKey();
        this.logger.trace("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848");
        List<Object> groups = Collections.emptyList();
        if ((interestType & NodeType.SCHEME_GROUP.getValue()) != 0) {
            groups = this.dataGroupDao.getByParent(parentKey);
        }
        List<Object> schemes = Collections.emptyList();
        if ((interestType & NodeType.SCHEME.getValue()) != 0) {
            schemes = this.dataSchemeDao.getByParent(parentKey);
        }
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u65b9\u6848", (Object)groups.size(), (Object)schemes.size());
        Object e = reverse.visitGoScNode(next, groups, schemes);
        SchemeNode preNode = new SchemeNode(parentKey, NodeType.SCHEME_GROUP.getValue());
        preNode.setOther(e);
        return preNode;
    }
}

