/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.DataScheme;
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
class RunSchemeStrategy
implements RunTimeSchemeLoaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(RunSchemeStrategy.class);
    @Autowired
    private IRuntimeDataSchemeService service;
    private static final int DEFAULT_INTEREST_TYPE = NodeType.DIM.getValue() | NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.FMDM_TABLE.getValue() | NodeType.MD_INFO.getValue();
    private static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();
    protected static final int RES_INTEREST_TYPE = NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue();

    RunSchemeStrategy() {
    }

    public boolean matching(int nodeType) {
        return NodeType.SCHEME.getValue() == nodeType;
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
        DataScheme dataScheme = this.service.getDataScheme(root.getKey());
        Object other = visitor.visitRootIsSchemeNode(dataScheme);
        this.logger.trace("\u8ddf\u8282\u70b9\u8bbf\u95ee\u5b8c\u6210");
        if (other != null) {
            root.setOther(other);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        boolean care = false;
        List dataGroups = Collections.emptyList();
        if ((interestType & NodeType.GROUP.getValue()) != 0) {
            dataGroups = this.service.getDataGroupByScheme(next.getKey());
            care = true;
        }
        List dataTables = Collections.emptyList();
        if ((interestType & TABLE) != 0) {
            dataTables = this.service.getDataTableByScheme(next.getKey());
            dataTables = dataTables.stream().filter(BaseLevelLoader.getPredicate(interestType)).collect(Collectors.toList());
            care = true;
        }
        List dims = Collections.emptyList();
        if ((interestType & NodeType.DIM.getValue()) != 0) {
            dims = this.service.getDataSchemeDimension(next.getKey());
            care = true;
            if (dims == null) {
                dims = Collections.emptyList();
            }
        }
        if (!care) {
            this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u65b9\u6848\uff0c\u7528\u6237\u4e0d\u5173\u5fc3\u65b9\u6848\u4e0b\u4efb\u4f55\u6570\u636e\uff0c\u8df3\u8fc7");
            return null;
        }
        this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u65b9\u6848\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u5206\u7ec4\uff0c{}\u4e2a\u8868", (Object)dataGroups.size(), (Object)dataTables.size());
        Map otherPar = visitor.visitSchemeNode(next, dataGroups, dataTables, dims);
        ArrayList list = new ArrayList();
        list.addAll(BaseLevelLoader.group2SchemeNode(dataGroups, otherPar));
        list.addAll(BaseLevelLoader.table2SchemeNode(dataTables, otherPar));
        return list;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        DataScheme dataScheme = this.service.getDataScheme(next.getKey());
        String groupKey = dataScheme.getDataGroupKey();
        this.logger.debug("\u5f53\u524d\u8282\u70b9\u662f\u65b9\u6848");
        if (interestType == null) {
            interestType = RES_INTEREST_TYPE;
        }
        List groups = Collections.emptyList();
        if ((interestType & NodeType.SCHEME_GROUP.getValue()) != 0) {
            groups = this.service.getDataGroupByParent(groupKey);
        }
        List schemes = Collections.emptyList();
        if ((interestType & NodeType.SCHEME.getValue()) != 0) {
            schemes = this.service.getDataSchemeByParent(groupKey);
        }
        this.logger.debug("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u65b9\u6848", (Object)groups.size(), (Object)schemes.size());
        Object e = reverse.visitGoScNode(next, groups, schemes);
        SchemeNode preNode = new SchemeNode(groupKey, NodeType.SCHEME_GROUP.getValue());
        preNode.setOther(e);
        return preNode;
    }
}

