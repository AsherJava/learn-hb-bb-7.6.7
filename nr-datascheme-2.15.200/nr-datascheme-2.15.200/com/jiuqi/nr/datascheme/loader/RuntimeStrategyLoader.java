/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.loader;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
class RuntimeStrategyLoader
implements RuntimeLevelLoader {
    private final Logger logger = LoggerFactory.getLogger(RuntimeStrategyLoader.class);
    @Autowired(required=false)
    private List<RunTimeSchemeLoaderStrategy> strategies;
    private final ConcurrentHashMap<Integer, RunTimeSchemeLoaderStrategy> strategyMap = new ConcurrentHashMap(NodeType.values().length);

    RuntimeStrategyLoader() {
    }

    private RunTimeSchemeLoaderStrategy getDataSchemeLoaderStrategy(int type) {
        if (this.strategies == null) {
            throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
        }
        RunTimeSchemeLoaderStrategy strategyByType = this.strategyMap.get(type);
        if (strategyByType != null) {
            return strategyByType;
        }
        for (RunTimeSchemeLoaderStrategy strategy : this.strategies) {
            boolean matching = strategy.matching(type);
            if (!matching) continue;
            this.strategyMap.put(type, strategy);
            return strategy;
        }
        throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
    }

    public <E> void walkDataSchemeTree(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
        Assert.notNull(root, "root must not be null");
        Assert.notNull(visitor, "visitor must not be null");
        this.walkDataSchemeTree(root, visitor, null);
    }

    public <E> void walkDataSchemeTree(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        Assert.notNull(root, "root must not be null");
        Assert.notNull(visitor, "visitor must not be null");
        int type = root.getType();
        this.logger.debug("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9 {} ", (Object)root);
        VisitorResult visitorResult = visitor.preVisitNode(root);
        if (visitorResult == VisitorResult.TERMINATE || visitorResult == VisitorResult.CONTINUE) {
            this.logger.debug("\u8df3\u8fc7\u6839\u8282\u70b9\uff0c\u76f4\u63a5\u7ed3\u675f");
            return;
        }
        RunTimeSchemeLoaderStrategy strategy = this.getDataSchemeLoaderStrategy(type);
        strategy.visitRoot(root, visitor);
        this.logger.debug("\u8ddf\u8282\u70b9\u8bbf\u95ee\u5b8c\u6210");
        this.sequence(root, visitor, interestType);
    }

    private <E> void sequence(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        SchemeNode next;
        LinkedList<SchemeNode<E>> queue = new LinkedList<SchemeNode<E>>();
        queue.add(root);
        while ((next = (SchemeNode)queue.poll()) != null) {
            RunTimeSchemeLoaderStrategy strategy;
            List list;
            if (!next.equals(root)) {
                this.logger.debug("\u9884\u68c0\u67e5\u8282\u70b9 {} ", (Object)next);
                VisitorResult visitorResult = visitor.preVisitNode(next);
                if (visitorResult == VisitorResult.TERMINATE) {
                    this.logger.debug("\u7ed3\u675f\u904d\u5386");
                    return;
                }
                if (visitorResult == VisitorResult.CONTINUE) {
                    this.logger.debug("\u8df3\u8fc7\u8282\u70b9");
                    continue;
                }
            }
            if ((list = (strategy = this.getDataSchemeLoaderStrategy(next.getType())).visitNode(next, visitor, interestType)) != null) {
                queue.addAll(list);
            }
            this.logger.debug("\u8282\u70b9\u904d\u5386\u5b8c\u6210");
        }
    }

    public <E> void walkDataSchemeTree(DataScheme root, RuntimeSchemeVisitor<E> visitor) {
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(visitor, "visitor must not be null");
        this.walkDataSchemeTree(new SchemeNode(root.getKey(), NodeType.SCHEME.getValue()), visitor, null);
    }

    public <E> void walkDataSchemeTree(DataGroup root, RuntimeSchemeVisitor<E> visitor) {
        int type;
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(visitor, "visitor must not be null");
        DataGroupKind kind = root.getDataGroupKind();
        Assert.notNull((Object)kind, "kind must not be null");
        if (kind == DataGroupKind.SCHEME_GROUP) {
            type = NodeType.SCHEME_GROUP.getValue();
        } else if (kind == DataGroupKind.TABLE_GROUP) {
            type = NodeType.GROUP.getValue();
        } else {
            throw new SchemeDataException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b");
        }
        this.walkDataSchemeTree(new SchemeNode(root.getKey(), type), visitor, null);
    }

    public <E> E reverseDataSchemeTree(SchemeNode<E> leaf, RuntimeReverseSchemeVisitor<E> reverse) {
        Assert.notNull(leaf, "leaf must not be null");
        Assert.notNull(reverse, "reverse must not be null");
        return this.reverseDataSchemeTree(leaf, reverse, null);
    }

    public <E> E reverseDataSchemeTree(SchemeNode<E> leaf, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        Assert.notNull(leaf, "leaf must not be null");
        Assert.notNull(reverse, "reverse must not be null");
        SchemeNode next = leaf;
        Object other = null;
        while (next != null) {
            this.logger.debug("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9 {} ", (Object)next);
            VisitorResult preResult = reverse.preVisitNode(next);
            if (preResult == VisitorResult.CONTINUE || preResult == VisitorResult.TERMINATE) {
                this.logger.debug("\u7ed3\u675f\u904d\u5386");
                return (E)next.getOther();
            }
            RunTimeSchemeLoaderStrategy strategy = this.getDataSchemeLoaderStrategy(next.getType());
            if ((next = strategy.visitNode(next, reverse, interestType)) != null) {
                other = next.getOther();
            }
            this.logger.debug("\u8282\u70b9\u904d\u5386\u5b8c\u6210");
        }
        return (E)other;
    }
}

