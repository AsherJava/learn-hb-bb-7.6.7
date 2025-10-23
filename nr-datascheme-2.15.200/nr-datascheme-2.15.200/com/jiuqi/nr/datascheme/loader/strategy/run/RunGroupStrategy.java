/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
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
class RunGroupStrategy
implements RunTimeSchemeLoaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(RunGroupStrategy.class);
    private static final int DEFAULT_INTEREST_TYPE = NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.DIM.getValue() | NodeType.MD_INFO.getValue();
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();
    @Autowired
    private IRuntimeDataSchemeService service;

    RunGroupStrategy() {
    }

    public boolean matching(int nodeType) {
        return (NodeType.GROUP.getValue() & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
        DataGroup dataGroup = this.service.getDataGroup(root.getKey());
        Object other = visitor.visitRootIsGroupNode(dataGroup);
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List groups = Collections.emptyList();
        if ((interestType & NodeType.GROUP.getValue()) != 0) {
            groups = this.service.getDataGroupByParent(next.getKey());
        }
        List dataTables = Collections.emptyList();
        if ((interestType & TABLE) != 0) {
            dataTables = this.service.getDataTableByGroup(next.getKey());
            dataTables = dataTables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
        }
        this.logger.debug("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u5206\u7ec4\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)groups.size(), (Object)dataTables.size());
        Map otherPar = visitor.visitGroupNode(next, groups, dataTables);
        ArrayList list = new ArrayList();
        list.addAll(BaseLevelLoader.group2SchemeNode(groups, otherPar));
        list.addAll(BaseLevelLoader.table2SchemeNode(dataTables, otherPar));
        return list;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        SchemeNode preNode;
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        DataGroup dataGroup = this.service.getDataGroup(next.getKey());
        String parentKey = dataGroup.getParentKey();
        String dataSchemeKey = dataGroup.getDataSchemeKey();
        this.logger.debug("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848\u4e0b\u5206\u7ec4");
        List groups = Collections.emptyList();
        List tables = Collections.emptyList();
        List dims = Collections.emptyList();
        if (parentKey == null) {
            if ((interestType & NodeType.GROUP.getValue()) != 0) {
                groups = this.service.getDataGroupByScheme(dataSchemeKey);
            }
            if ((interestType & TABLE) != 0) {
                tables = this.service.getDataTableByScheme(dataSchemeKey);
                tables = tables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            }
            if ((interestType & NodeType.DIM.getValue()) != 0) {
                dims = this.service.getDataSchemeDimension(dataSchemeKey);
            }
            preNode = new SchemeNode(dataSchemeKey, NodeType.SCHEME.getValue());
        } else {
            if ((interestType & NodeType.GROUP.getValue()) != 0) {
                groups = this.service.getDataGroupByParent(parentKey);
            }
            if ((interestType & TABLE) != 0) {
                tables = this.service.getDataTableByGroup(parentKey);
                tables = tables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            }
            preNode = new SchemeNode(parentKey, NodeType.GROUP.getValue());
        }
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u8868\uff0c{}\u4e2a\u7ef4\u5ea6", groups.size(), tables.size(), dims.size());
        Object other = reverse.visitGoTaNode(next, groups, tables, dims);
        preNode.setOther(other);
        return preNode;
    }
}

