/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 *  com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider
 *  javax.annotation.Resource
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSEntityRowQuery;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSSchemeInfo;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSTreeDataProvider;
import com.jiuqi.nr.batch.summary.service.ext.unittree.FSUnitTreeSource;
import com.jiuqi.nr.batch.summary.service.ext.unittree.MSUnitTreeSource;
import com.jiuqi.nr.batch.summary.service.ext.unittree.SSUnitTreeSource;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import com.jiuqi.nr.itreebase.source.search.ISearchNodeProvider;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class BSUnitTreeSource
implements IUnitTreeDataSource {
    public static final String SOURCE_ID = "nr.batch.summary.unit.tree.source.id";
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private BSEntityRowQuery entityRowQuery;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    public IconSourceScheme[] getNodeIconSource(IUnitTreeContext ctx) {
        return new IconSourceScheme[]{this.contextWrapper.getBBLXIConSourceScheme(ctx.getEntityDefine())};
    }

    public String getSourceId() {
        return SOURCE_ID;
    }

    public ITreeNodeProvider getTreeNodeProvider(IUnitTreeContext ctx) {
        JSONObject customVariable = ctx.getCustomVariable();
        if (this.isBatchShowScheme(customVariable)) {
            List<String> showSchemeKeys = this.getShowSchemeKeys(customVariable);
            Map<String, IUnitTreeDataSource> dataSourceMap = this.getDataSourceMap(ctx, showSchemeKeys);
            LinkedHashMap<String, IUnitTreeNodeBuilder> nodeBuilderMap = new LinkedHashMap<String, IUnitTreeNodeBuilder>();
            LinkedHashMap<String, ITreeNodeProvider> nodeProviderMap = new LinkedHashMap<String, ITreeNodeProvider>();
            LinkedHashMap<String, IUnitTreeEntityRowProvider> rowProviderMap = new LinkedHashMap<String, IUnitTreeEntityRowProvider>();
            for (Map.Entry<String, IUnitTreeDataSource> entry : dataSourceMap.entrySet()) {
                IUnitTreeEntityRowProvider rowProvider = entry.getValue().getUnitTreeEntityRowProvider(ctx);
                nodeBuilderMap.put(entry.getKey(), entry.getValue().getNodeBuilder(ctx, rowProvider));
                nodeProviderMap.put(entry.getKey(), entry.getValue().getTreeNodeProvider(ctx));
                rowProviderMap.put(entry.getKey(), entry.getValue().getUnitTreeEntityRowProvider(ctx));
            }
            return new BSTreeDataProvider(nodeBuilderMap, nodeProviderMap, rowProviderMap, ctx.getActionNode(), showSchemeKeys.get(0));
        }
        return this.madeSummarySchemeUnitTreeSource(customVariable).getTreeNodeProvider(ctx);
    }

    public ISearchNodeProvider getSearchDataProvider(IUnitTreeContext ctx) {
        return null;
    }

    public IUnitTreeEntityRowProvider getUnitTreeEntityRowProvider(IUnitTreeContext ctx) {
        LinkedHashMap<String, IUnitTreeEntityRowProvider> entityRowProviderMap = new LinkedHashMap<String, IUnitTreeEntityRowProvider>();
        JSONObject customVariable = ctx.getCustomVariable();
        if (this.isBatchShowScheme(customVariable)) {
            List<String> showSchemeKeys = this.getShowSchemeKeys(customVariable);
            Map<String, IUnitTreeDataSource> dataSourceMap = this.getDataSourceMap(ctx, showSchemeKeys);
            for (Map.Entry<String, IUnitTreeDataSource> entry : dataSourceMap.entrySet()) {
                entityRowProviderMap.put(entry.getKey(), entry.getValue().getUnitTreeEntityRowProvider(ctx));
            }
            return new BSEntityRowProvider(entityRowProviderMap);
        }
        return this.madeSummarySchemeUnitTreeSource(customVariable).getUnitTreeEntityRowProvider(ctx);
    }

    private Map<String, IUnitTreeDataSource> getDataSourceMap(IUnitTreeContext context, List<String> schemeKeys) {
        LinkedHashMap<String, IUnitTreeDataSource> sourceMap = new LinkedHashMap<String, IUnitTreeDataSource>();
        FormSchemeDefine formSchemeDefine = context.getFormScheme();
        sourceMap.put(formSchemeDefine.getKey(), new FSUnitTreeSource(formSchemeDefine, this.entityRowQuery));
        for (String schemeKey : schemeKeys) {
            SummaryScheme scheme = this.schemeService.findScheme(schemeKey);
            sourceMap.put(scheme.getKey(), new MSUnitTreeSource(scheme, this.entityRowQuery));
        }
        return sourceMap;
    }

    private List<String> getShowSchemeKeys(JSONObject customVariable) {
        ArrayList<BSSchemeInfo> schemes = new ArrayList<BSSchemeInfo>();
        JSONArray jsonArray = customVariable.optJSONArray("batchShowSchemeCodes");
        for (int i = 0; i < jsonArray.length(); ++i) {
            BSSchemeInfo schemeInfo = (BSSchemeInfo)BatchSummaryUtils.toJavaBean((String)jsonArray.getJSONObject(i).toString(), BSSchemeInfo.class);
            if (schemeInfo == null) continue;
            schemes.add(schemeInfo);
        }
        return schemes.stream().map(BSSchemeInfo::getKey).collect(Collectors.toList());
    }

    private boolean isBatchShowScheme(JSONObject customVariable) {
        return customVariable.has("batchShowSchemeCodes") && !customVariable.isNull("batchShowSchemeCodes") && !customVariable.optJSONArray("batchShowSchemeCodes").isEmpty();
    }

    private IUnitTreeDataSource madeSummarySchemeUnitTreeSource(JSONObject customVariable) {
        String schemeKey = customVariable.getString("batchGatherSchemeCode");
        SummaryScheme scheme = this.schemeService.findScheme(schemeKey);
        return new SSUnitTreeSource(scheme, this.entityRowQuery);
    }
}

