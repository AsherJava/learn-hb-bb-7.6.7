/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RunTableStrategy
implements RunTimeSchemeLoaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(RunTableStrategy.class);
    @Autowired
    private IRuntimeDataSchemeService service;
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();
    private static final int RES_DEFAULT_INTEREST_TYPE = NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();

    RunTableStrategy() {
    }

    public boolean matching(int nodeType) {
        return (nodeType & TABLE) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
        DataTable dataTable = this.service.getDataTable(root.getKey());
        Object other = visitor.visitRootIsTableNode(dataTable);
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        List fields;
        if (interestType == null) {
            fields = this.service.getDataFieldByTable(next.getKey());
        } else {
            int kind = BaseLevelLoader.nodeType2FieldKind(interestType);
            if (kind == 0) {
                this.logger.debug("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u8868\uff0c\u7528\u6237\u4e0d\u5173\u5fc3\u6307\u6807\u8df3\u8fc7");
                return null;
            }
            fields = this.service.getDataFieldByTableKeyAndKind(next.getKey(), DataFieldKind.interestType((int)kind));
        }
        this.logger.debug("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u8868\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u6307\u6807", (Object)fields.size());
        visitor.visitTableNode(next, fields);
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        SchemeNode preNode;
        if (interestType == null) {
            interestType = RES_DEFAULT_INTEREST_TYPE;
        }
        DataTable dataTable = this.service.getDataTable(next.getKey());
        String parentKey = dataTable.getDataGroupKey();
        String dataSchemeKey = dataTable.getDataSchemeKey();
        this.logger.debug("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848\u4e0b\u6570\u636e\u8868");
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
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)groups.size(), (Object)tables.size());
        Object other = reverse.visitGoTaNode(next, groups, tables, dims);
        preNode.setOther(other);
        return preNode;
    }
}

