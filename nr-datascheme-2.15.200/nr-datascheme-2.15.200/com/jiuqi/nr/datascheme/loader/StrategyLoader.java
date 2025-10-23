/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.LevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 */
package com.jiuqi.nr.datascheme.loader;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.des.LevelLoader;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
class StrategyLoader
implements LevelLoader {
    private final Logger logger = LoggerFactory.getLogger(StrategyLoader.class);
    @Autowired(required=false)
    private List<DataSchemeLoaderStrategy> strategies;
    private final Map<Integer, DataSchemeLoaderStrategy> strategyMap = new ConcurrentHashMap<Integer, DataSchemeLoaderStrategy>(NodeType.values().length);

    private StrategyLoader() {
    }

    public <E> void walkDataSchemeTree(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        this.walkDataSchemeTree(root, schemeNodeVisitor, null);
    }

    public <E> void walkDataSchemeTree(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        Assert.notNull(root, "root must not be null");
        Assert.notNull(schemeNodeVisitor, "schemeNodeVisitor must not be null");
        this.logger.debug("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9{}", (Object)root);
        int type = root.getType();
        VisitorResult visitorResult = schemeNodeVisitor.preVisitNode(root);
        if (visitorResult == VisitorResult.TERMINATE || visitorResult == VisitorResult.CONTINUE) {
            this.logger.trace("\u8df3\u8fc7\u6839\u8282\u70b9\uff0c\u76f4\u63a5\u7ed3\u675f");
            return;
        }
        DataSchemeLoaderStrategy strategy = this.getDataSchemeLoaderStrategy(type);
        strategy.visitRoot(root, schemeNodeVisitor);
        this.logger.debug("\u6839\u8282\u70b9\u8bbf\u95ee\u5b8c\u6210");
        this.sequence(root, schemeNodeVisitor, interestType);
    }

    private <E> void sequence(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        SchemeNode next;
        LinkedList<SchemeNode<E>> queue = new LinkedList<SchemeNode<E>>();
        queue.add(root);
        while ((next = (SchemeNode)queue.poll()) != null) {
            DataSchemeLoaderStrategy strategy;
            List list;
            if (!next.equals(root)) {
                this.logger.trace("\u9884\u68c0\u67e5\u8282\u70b9 {} ", (Object)next);
                VisitorResult visitorResult = schemeNodeVisitor.preVisitNode(next);
                if (visitorResult == VisitorResult.CONTINUE) {
                    this.logger.trace("\u8df3\u8fc7\u5f53\u524d\u8282\u70b9");
                    continue;
                }
                if (visitorResult == VisitorResult.TERMINATE) {
                    this.logger.trace("\u7ed3\u675f\u904d\u5386");
                    return;
                }
            }
            if (CollectionUtils.isEmpty(list = (strategy = this.getDataSchemeLoaderStrategy(next.getType())).visitNode(next, schemeNodeVisitor, interestType))) continue;
            queue.addAll(list);
        }
    }

    public <E> void walkDataSchemeTree(DesignDataScheme root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(schemeNodeVisitor, "schemeNodeVisitor must not be null");
        SchemeNode schemeNode = new SchemeNode(root.getKey(), NodeType.SCHEME.getValue());
        this.walkDataSchemeTree(schemeNode, schemeNodeVisitor);
    }

    public <E> void walkDataSchemeTree(DesignDataGroup root, SchemeNodeVisitor<E> schemeNodeVisitor) {
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(schemeNodeVisitor, "schemeNodeVisitor must not be null");
        SchemeNode objectSchemeNode = new SchemeNode(root.getKey(), NodeType.GROUP.getValue());
        this.walkDataSchemeTree(objectSchemeNode, schemeNodeVisitor);
    }

    public <E> E reverseDataSchemeTree(SchemeNode<E> leaf, ReverseSchemeNodeVisitor<E> reverse) {
        Assert.notNull(leaf, "root must not be null");
        Assert.notNull(reverse, "reverse must not be null");
        return this.reverseDataSchemeTree(leaf, reverse, null);
    }

    public <E> E reverseDataSchemeTree(SchemeNode<E> leaf, ReverseSchemeNodeVisitor<E> reverse, Integer interestType) {
        Assert.notNull(leaf, "root must not be null");
        Assert.notNull(reverse, "reverse must not be null");
        SchemeNode next = leaf;
        while (next != null) {
            this.logger.trace("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9 {} ", (Object)next);
            VisitorResult preResult = reverse.preVisitNode(next);
            if (preResult == VisitorResult.CONTINUE || preResult == VisitorResult.TERMINATE) {
                this.logger.trace("\u7ed3\u675f\u904d\u5386");
                return (E)next.getOther();
            }
            DataSchemeLoaderStrategy strategy = this.getDataSchemeLoaderStrategy(next.getType());
            next = strategy.visitNode(next, reverse, interestType);
        }
        return null;
    }

    private DataSchemeLoaderStrategy getDataSchemeLoaderStrategy(int type) {
        if (this.strategies == null) {
            throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
        }
        DataSchemeLoaderStrategy strategyByType = this.strategyMap.get(type);
        if (strategyByType != null) {
            return strategyByType;
        }
        for (DataSchemeLoaderStrategy strategy : this.strategies) {
            boolean matching = strategy.matching(type);
            if (!matching) continue;
            this.strategyMap.put(type, strategy);
            return strategy;
        }
        throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
    }
}

