/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions
 *  com.jiuiqi.nr.unit.treebase.entity.provider.AsyncUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.provider.FullBuildTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.node.state.TerminalStateManagement
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagFacade
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treeimpl.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.impl.RunTimeContextData;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.entity.provider.AsyncUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.FullBuildTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.node.state.TerminalStateManagement;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treeimpl.dataio.LevelTreeDataExport;
import com.jiuqi.nr.unit.treeimpl.web.request.LevelTreeExportParam;
import com.jiuqi.nr.unit.treeimpl.web.request.NodeModifyParam;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsCountInfo;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsCountObject;
import com.jiuqi.nr.unit.treeimpl.web.response.NodeTagsInfo;
import com.jiuqi.nr.unit.treeimpl.web.service.IDataEntryTreeNodeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DataEntryTreeNodeService
implements IDataEntryTreeNodeService {
    @Resource
    private ITagQueryTemplateHelper tagQueryTemplateHelper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUnitTreeDataSourceHelper treeSourceHelper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Resource
    private TerminalStateManagement terminalStateMgr;

    @Override
    public void exportLevelTree(LevelTreeExportParam levelParam, HttpServletResponse response) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext((UnitTreeContextData)levelParam);
        IEntityDefine entityDefine = context.getEntityDefine();
        IUnitTreeDataSource dataSource = this.treeSourceHelper.getBaseTreeDataSource(context.getDataSourceId());
        IUnitTreeEntityRowProvider entityRowProvider = dataSource.getUnitTreeEntityRowProvider(context);
        if (entityRowProvider instanceof AsyncUnitTreeEntityRowProvider) {
            entityRowProvider = new FullBuildTreeEntityRowProvider(context, (IUnitTreeEntityDataQuery)this.entityDataQuery);
        }
        LevelTreeDataExport exportWorker = new LevelTreeDataExport(entityRowProvider, (IBaseNodeData)levelParam.getRootLevelNode(), levelParam.isShowCode(), entityDefine.getTitle());
        String fileName = entityDefine.getTitle() + "[" + entityDefine.getCode() + "]";
        try {
            exportWorker.writeExcel(response, fileName);
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    @Override
    public NodeTagsInfo inqueryNodeTags(NodeModifyParam param) {
        NodeTagsInfo nodeTagsInfo = new NodeTagsInfo();
        String nodeKey = param.getTagNode().getKey();
        if (StringUtils.isNotEmpty((String)nodeKey)) {
            IUnitTreeContext context = this.contextBuilder.createTreeContext((UnitTreeContextData)param);
            TagQueryContextData tagQueryContextData = new TagQueryContextData();
            tagQueryContextData.setFormScheme(context.getFormScheme().getKey());
            tagQueryContextData.setPeriod(context.getPeriod());
            tagQueryContextData.setEntityId(context.getEntityDefine().getId());
            tagQueryContextData.setDimValueSet(context.getDimValueSet());
            tagQueryContextData.setCustomVariable(context.getCustomVariable());
            Map entity2Tags = this.tagQueryTemplateHelper.getQueryTemplate().unitCountTags((ITagQueryContext)tagQueryContextData, Collections.singletonList(nodeKey));
            List tagKeys = (List)entity2Tags.get(nodeKey);
            nodeTagsInfo.setNodeTagKeys(new ArrayList<String>());
            if (tagKeys != null) {
                nodeTagsInfo.setNodeTagKeys(new ArrayList<String>(tagKeys));
            }
            List tags = this.tagQueryTemplateHelper.getQueryTemplate().getInfoTags((ITagQueryContext)tagQueryContextData);
            nodeTagsInfo.setAllTags(tags);
        }
        return nodeTagsInfo;
    }

    @Override
    public Boolean saveTerminalState(NodeModifyParam param) {
        String nodeKey = param.getTagNode().getKey();
        IUnitTreeContext context = this.contextBuilder.createTreeContext((UnitTreeContextData)param);
        if (context.getFormScheme() == null) {
            return false;
        }
        return this.terminalStateMgr.saveOrUpdateData(context, nodeKey);
    }

    @Override
    public NodeTagsCountInfo countTagNodes(RunTimeContextData contextData) {
        IUnitTreeContext context = this.contextBuilder.createTreeContext((UnitTreeContextData)contextData);
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        tagQueryContextData.setFormScheme(context.getFormScheme().getKey());
        tagQueryContextData.setPeriod(context.getPeriod());
        tagQueryContextData.setEntityId(context.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(context.getDimValueSet());
        tagQueryContextData.setCustomVariable(context.getCustomVariable());
        UnitTreeShowTagsOptions showTagsOptions = UnitTreeShowTagsOptions.translate2ShowTagsOptions((JSONObject)context.getCustomVariable());
        List showTagKeys = showTagsOptions.getShowTagKeys();
        List tags = this.tagQueryTemplateHelper.getQueryTemplate().getInfoTags((ITagQueryContext)tagQueryContextData, showTagKeys);
        HashMap tagImplMap = new HashMap();
        tags.forEach(tag -> tagImplMap.put(tag.getKey(), tag));
        int totalCount = 0;
        Map tags2Entities = this.tagQueryTemplateHelper.getQueryTemplate().tagCountUnits((ITagQueryContext)tagQueryContextData, showTagKeys);
        ArrayList<NodeTagsCountObject> countObjects = new ArrayList<NodeTagsCountObject>();
        for (Map.Entry entry : tags2Entities.entrySet()) {
            ITagFacade impl = (ITagFacade)tagImplMap.get(entry.getKey());
            List entityDataKeys = (List)entry.getValue();
            int size = entityDataKeys.size();
            totalCount += size;
            NodeTagsCountObject countObject = new NodeTagsCountObject();
            countObject.setKey(impl.getKey());
            countObject.setTitle(impl.getTitle() + "\uff08" + size + "\uff09");
            countObject.setTotalCount(size);
            countObject.setCategory(impl.getCategory());
            countObjects.add(countObject);
        }
        NodeTagsCountInfo countInfo = new NodeTagsCountInfo();
        countInfo.setTotalCount(totalCount);
        countInfo.setCountObjects(countObjects);
        return countInfo;
    }
}

