/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 */
package com.jiuqi.nr.dataresource.loader.impl;

import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.loader.DataResourceLevelLoader;
import com.jiuqi.nr.dataresource.loader.DataResourceLoaderStrategy;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DefaultDataResourceLevelLoader
implements DataResourceLevelLoader {
    private final Logger logger = LoggerFactory.getLogger(DefaultDataResourceLevelLoader.class);
    @Autowired(required=false)
    private List<DataResourceLoaderStrategy> strategies;
    private final Map<Integer, DataResourceLoaderStrategy> strategyMap = new ConcurrentHashMap<Integer, DataResourceLoaderStrategy>();

    private DefaultDataResourceLevelLoader() {
    }

    @Override
    public <E> void walkDataResourceTree(ResourceNode<E> root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        Assert.notNull(root, "root must not be null");
        Assert.notNull(resourceNodeVisitor, "resourceNodeVisitor must not be null");
        this.logger.debug("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9{}", (Object)root);
        int type = root.getType();
        VisitorResult visitorResult = resourceNodeVisitor.preVisitNode(root);
        if (visitorResult == VisitorResult.TERMINATE || visitorResult == VisitorResult.CONTINUE) {
            this.logger.debug("\u8df3\u8fc7\u6839\u8282\u70b9\uff0c\u76f4\u63a5\u7ed3\u675f");
            return;
        }
        DataResourceLoaderStrategy strategy = this.getDataResourceLoaderStrategy(type);
        strategy.visitRoot(root, resourceNodeVisitor);
        this.logger.debug("\u6839\u8282\u70b9\u8bbf\u95ee\u5b8c\u6210");
        this.sequence(root, resourceNodeVisitor);
    }

    private <E> void sequence(ResourceNode<E> root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        ResourceNode next;
        LinkedList<ResourceNode<E>> queue = new LinkedList<ResourceNode<E>>();
        queue.add(root);
        while ((next = (ResourceNode)queue.poll()) != null) {
            if (!next.equals(root)) {
                this.logger.debug("\u9884\u68c0\u67e5\u8282\u70b9 {} ", (Object)next);
                VisitorResult visitorResult = resourceNodeVisitor.preVisitNode(next);
                if (visitorResult != null) {
                    this.logger.debug("\u8df3\u8fc7\u5f53\u524d\u8282\u70b9");
                    continue;
                }
            }
            DataResourceLoaderStrategy strategy = this.getDataResourceLoaderStrategy(next.getType());
            List<ResourceNode<E>> schemeNodes = strategy.visitNode(next, resourceNodeVisitor);
            queue.addAll(schemeNodes);
        }
    }

    private DataResourceLoaderStrategy getDataResourceLoaderStrategy(int type) {
        if (this.strategies == null) {
            throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
        }
        DataResourceLoaderStrategy strategyByType = this.strategyMap.get(type);
        if (strategyByType != null) {
            return strategyByType;
        }
        for (DataResourceLoaderStrategy strategy : this.strategies) {
            boolean matching = strategy.matching(type);
            if (!matching) continue;
            this.strategyMap.put(type, strategy);
            return strategy;
        }
        throw new UnsupportedOperationException("\u8282\u70b9\u7c7b\u578b\u4e0d\u652f\u6301");
    }

    public <E> void walkDataResourceTree(DataResourceDefine root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(resourceNodeVisitor, "resourceNodeVisitor must not be null");
        ResourceNode r = new ResourceNode(root.getKey(), NodeType.TREE.getValue());
        this.walkDataResourceTree(r, resourceNodeVisitor);
    }

    public <E> void walkDataResourceTree(DataResourceDefineGroup root, ResourceNodeVisitor<E> resourceNodeVisitor) {
        Assert.notNull((Object)root, "root must not be null");
        Assert.notNull(resourceNodeVisitor, "resourceNodeVisitor must not be null");
        ResourceNode r = new ResourceNode(root.getKey(), NodeType.TREE_GROUP.getValue());
        this.walkDataResourceTree(r, resourceNodeVisitor);
    }

    @Override
    public <E> E reverseDataResourceTree(ResourceNode<E> leaf, ReverseDataResourceNodeVisitor<E> reverse) {
        ResourceNode<E> next = leaf;
        while (next != null) {
            this.logger.trace("\u9884\u68c0\u67e5\u8f93\u5165\u8282\u70b9 {} ", (Object)next);
            int type = next.getType();
            DataResourceLoaderStrategy dataResourceLoaderStrategy = this.getDataResourceLoaderStrategy(type);
            VisitorResult preResult = reverse.preVisitNode(next);
            if (preResult == VisitorResult.CONTINUE || preResult == VisitorResult.TERMINATE) {
                this.logger.trace("\u7ed3\u675f\u904d\u5386");
                return next.getOther();
            }
            next = dataResourceLoaderStrategy.visitNode(next, reverse);
        }
        return null;
    }
}

