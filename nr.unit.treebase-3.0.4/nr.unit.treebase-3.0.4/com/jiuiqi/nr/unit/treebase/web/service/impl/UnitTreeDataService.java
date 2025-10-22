/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceItem
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.tag.management.bean.TagItemData
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.counter2.IUnitTreeNodeCounter;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeStaticSourceService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceItem;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.tag.management.bean.TagItemData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service(value="unit-tree-data-service")
public class UnitTreeDataService
implements IUnitTreeDataService {
    static final String KEY_OF_ENTITY_ID = "entityId";
    static final String KEY_OF_ICON_SOURCE = "iconSource";
    static final String KEY_OF_TAG_SOURCE = "tagSource";
    static final String KEY_OF_STATE_COLOR = "dataStatusSources";
    static final String KEY_USE_ASYNC_LOAD_ALL_CHILD_COUNT = "useAsyncLoadAllChildCount";
    @Resource
    private IconSourceHelper iconSourceHelper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUnitTreeDataSourceHelper treeSourceHelper;
    @Resource
    private ITagQueryTemplateHelper tagQueryTemplateHelper;
    @Resource
    private IUnitTreeStaticSourceService staticSourceService;

    @Override
    public Map<String, Object> getStaticResource(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        Map<String, Object> staticResource = dataSource.getStaticResource(context = this.contextBuilder.createTreeContext(context, dataSource));
        if (null == staticResource) {
            staticResource = new HashMap<String, Object>();
        }
        staticResource.put(KEY_OF_ENTITY_ID, context.getEntityDefine().getId());
        staticResource.put(KEY_OF_ICON_SOURCE, this.createIconSource(context));
        if (context.getFormScheme() != null && context.getFormScheme().getKey() != null && !context.getFormScheme().getKey().isEmpty()) {
            staticResource.put(KEY_OF_TAG_SOURCE, this.createTagResource(context));
        }
        staticResource.put(KEY_OF_STATE_COLOR, this.staticSourceService.createWorkflowStatusSource(context));
        staticResource.put(KEY_USE_ASYNC_LOAD_ALL_CHILD_COUNT, dataSource.getNodeCounter(context) != null);
        return staticResource;
    }

    private List<IconSourceItem> createIconSource(IUnitTreeContext context) {
        Map base64IconMap = context.getIconProvider().getBase64IconMap();
        ArrayList<IconSourceItem> iconSource = new ArrayList<IconSourceItem>();
        for (Map.Entry entry : base64IconMap.entrySet()) {
            IconSourceItem iconSourceItem = new IconSourceItem();
            iconSourceItem.setKey((String)entry.getKey());
            iconSourceItem.setIcon((String)entry.getValue());
            iconSource.add(iconSourceItem);
        }
        return iconSource;
    }

    private List<TagItemData> createTagResource(IUnitTreeContext context) {
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        tagQueryContextData.setFormScheme(context.getFormScheme().getKey());
        tagQueryContextData.setPeriod(context.getPeriod());
        tagQueryContextData.setEntityId(context.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(context.getDimValueSet());
        tagQueryContextData.setCustomVariable(context.getCustomVariable());
        List tags = this.tagQueryTemplateHelper.getQueryTemplate().getInfoTags((ITagQueryContext)tagQueryContextData);
        if (tags != null && !tags.isEmpty()) {
            return tags.stream().map(tagDefine -> {
                TagItemData tagItemData = new TagItemData();
                tagItemData.setKey(tagDefine.getKey());
                tagItemData.setTitle(tagDefine.getTitle());
                tagItemData.setIcon(tagDefine.getIcon());
                tagItemData.setCategory(tagDefine.getCategory());
                return tagItemData;
            }).collect(Collectors.toList());
        }
        return new ArrayList<TagItemData>();
    }

    @Override
    public List<ITree<IBaseNodeData>> getChildren(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        context = this.contextBuilder.createTreeContext(context, dataSource);
        ITreeNodeProvider treeNodeProvider = dataSource.getTreeNodeProvider(context);
        return treeNodeProvider.getChildren(context.getActionNode());
    }

    @Override
    public List<ITree<IBaseNodeData>> getTree(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        context = this.contextBuilder.createTreeContext(context, dataSource);
        ITreeNodeProvider treeNodeProvider = dataSource.getTreeNodeProvider(context);
        return treeNodeProvider.getTree();
    }

    @Override
    public Map<String, Integer> getNodeCountMap(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        context = this.contextBuilder.createTreeContext(context, dataSource);
        IUnitTreeNodeCounter nodeCounter = dataSource.getNodeCounter(context);
        return nodeCounter.getTreeNodeCountMap(context.getActionNode());
    }

    @Override
    public Map<String, Integer> getExpandNodeCountMap(UnitTreeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext(contextData);
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        context = this.contextBuilder.createTreeContext(context, dataSource);
        IUnitTreeNodeCounter nodeCounter = dataSource.getNodeCounter(context);
        return nodeCounter.getChildNodeCountMap(context.getActionNode());
    }
}

