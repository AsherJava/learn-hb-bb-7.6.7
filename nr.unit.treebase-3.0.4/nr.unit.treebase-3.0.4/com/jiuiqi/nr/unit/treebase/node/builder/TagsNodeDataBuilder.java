/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagsNodeDataBuilder
implements IUnitTreeNodeBuilder {
    private final ITagQueryTemplateHelper tagQueryTemplateHelper;
    private static final String KEY_OF_TAGS = "tags";
    private int showTagsPloy = 0;
    private final List<String> showTagKeys;
    private Map<String, List<String>> nodeTagsMap;
    private final IUnitTreeNodeBuilder baseNodeBuilder;
    private IUnitTreeContext context;

    public TagsNodeDataBuilder(IUnitTreeContext context, IUnitTreeNodeBuilder baseNodeBuilder, UnitTreeShowTagsOptions showTagsOptions) {
        this.baseNodeBuilder = baseNodeBuilder;
        this.showTagsPloy = showTagsOptions.getShowTagsPloy();
        this.showTagKeys = showTagsOptions.getShowTagKeys();
        this.context = context;
        this.tagQueryTemplateHelper = (ITagQueryTemplateHelper)BeanUtil.getBean(ITagQueryTemplateHelper.class);
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.baseNodeBuilder.beforeCreateITreeNode(rows);
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        tagQueryContextData.setFormScheme(this.context.getFormScheme().getKey());
        tagQueryContextData.setPeriod(this.context.getPeriod());
        tagQueryContextData.setEntityId(this.context.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(this.context.getDimValueSet());
        tagQueryContextData.setCustomVariable(this.context.getCustomVariable());
        this.nodeTagsMap = this.tagQueryTemplateHelper.getQueryTemplate().unitCountTags((ITagQueryContext)tagQueryContextData, rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.baseNodeBuilder.buildTreeNode(row);
        if (this.nodeTagsMap.containsKey(node.getKey())) {
            ((IBaseNodeData)node.getData()).put(KEY_OF_TAGS, this.getNodeTagKeys(node.getKey()));
        }
        return node;
    }

    private List<String> getNodeTagKeys(String nodeKey) {
        List<String> nodeTagKeys = this.nodeTagsMap.get(nodeKey);
        if (this.showTagsPloy == 0) {
            return this.retainAll(nodeTagKeys, this.showTagKeys);
        }
        if (this.showTagsPloy == 1) {
            ArrayList tagKeys = this.retainAll(nodeTagKeys, this.showTagKeys);
            return tagKeys.size() == this.showTagKeys.size() ? tagKeys : new ArrayList(0);
        }
        return nodeTagKeys;
    }

    private List<String> retainAll(List<String> a1, List<String> a2) {
        a1.removeIf(s -> !a2.contains(s));
        return a1;
    }
}

