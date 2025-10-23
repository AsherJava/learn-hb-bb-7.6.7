/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import com.jiuqi.nr.datascheme.loader.strategy.BaseSchemeGroupStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class RunSchemeGroupStrategy
extends BaseSchemeGroupStrategy
implements RunTimeSchemeLoaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(RunSchemeGroupStrategy.class);

    RunSchemeGroupStrategy() {
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
        DesignDataGroup dataGroupDO = super.getDataGroupDO(root);
        Object e = visitor.visitRootIsGroupNode((DataGroup)dataGroupDO);
        if (e != null) {
            root.setOther(e);
        }
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        List<DesignDataGroupDO> groups = super.getGroups(next, interestType);
        List<DataSchemeDO> runSchemes = super.getRunSchemes(next, interestType);
        List<DesignDataGroupDO> copy = BaseLevelLoader.copyGroup(groups);
        List<DataSchemeDO> copyScheme = BaseLevelLoader.copyRunScheme(runSchemes);
        this.logger.trace("\u5904\u7406\u8282\u70b9\u662f\u6570\u636e\u5206\u7ec4\uff0c\u67e5\u8be2\u5230\u4e0b\u7ea7{}\u4e2a\u5206\u7ec4\uff0c{}\u4e2a\u65b9\u6848", (Object)groups.size(), (Object)runSchemes.size());
        Map other = visitor.visitSchemeGroupNode(next, copy, copyScheme);
        ArrayList list = new ArrayList();
        list.addAll(BaseLevelLoader.group2SchemeNode(groups, other));
        list.addAll(BaseLevelLoader.scheme2SchemeNode(runSchemes, other));
        return list;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        String key = next.getKey();
        if ("00000000-0000-0000-0000-000000000000".equals(key) || "00000000-0000-0000-0000-111111111111".equals(key)) {
            reverse.visitRootNode(next);
            return null;
        }
        DesignDataGroupDO dataGroupDO = (DesignDataGroupDO)this.dataGroupDao.get(key);
        String parentKey = dataGroupDO.getParentKey();
        List<Object> groups = Collections.emptyList();
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        if ((interestType & NodeType.SCHEME_GROUP.getValue()) != 0) {
            groups = this.dataGroupDao.getByParent(parentKey);
        }
        List<Object> schemes = Collections.emptyList();
        if ((interestType & NodeType.SCHEME.getValue()) != 0) {
            schemes = this.runDataSchemeDao.getByParent(parentKey);
        }
        this.logger.trace("\u8bbf\u95ee{}\u4e2a\u65b9\u6848\u4e0b\u5206\u7ec4\uff0c{}\u4e2a\u65b9\u6848", (Object)groups.size(), (Object)schemes.size());
        Object e = reverse.visitGoScNode(next, groups, schemes);
        SchemeNode preNode = new SchemeNode(parentKey, NodeType.SCHEME_GROUP.getValue());
        preNode.setOther(e);
        return preNode;
    }
}

