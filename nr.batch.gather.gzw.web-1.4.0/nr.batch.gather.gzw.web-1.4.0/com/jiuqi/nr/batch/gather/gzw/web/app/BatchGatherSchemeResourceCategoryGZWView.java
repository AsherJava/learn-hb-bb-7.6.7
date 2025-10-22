/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryView
 *  com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.table.ITableActionProvider
 *  com.jiuqi.nvwa.resourceview.table.ITableColumnProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherGroupTableActionGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeResourceDataGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeResourceGZWSearcher;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeResourceTypeGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeTableColumnGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeToolbarActionGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextHelper;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryView;
import com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.table.ITableActionProvider;
import com.jiuqi.nvwa.resourceview.table.ITableColumnProvider;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BatchGatherSchemeResourceCategoryGZWView
implements IResourceCategoryView {
    public static final String VIEW_ID = "batch-gather-GZW-scheme-resource-view-id";
    @Resource
    private BSGroupService groupService;
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private BatchGatherGZWContextHelper contextHelper;
    @Resource
    private BeforeViewPageDataGZWHandler pageHandler;
    @Resource
    private BSShareService bsShareService;

    public String getId() {
        return VIEW_ID;
    }

    public String getTitle() {
        return "\u6211\u7684\u5206\u7c7b\u6c47\u603b\u65b9\u6848";
    }

    public String getParent() {
        return "batch-gather-GZW";
    }

    public String getIcon() {
        return "#icon16_SHU_A_NW_yingyongfuwu";
    }

    public double getOrder() {
        return 0.0;
    }

    public List<IResourceTypeProvider> getResourceTypeProviders() {
        ArrayList<IResourceTypeProvider> provider = new ArrayList<IResourceTypeProvider>();
        BatchGatherGZWContextData contextData = this.contextHelper.getContextData();
        provider.add(new BatchGatherSchemeResourceTypeGZWProvider(contextData, this.schemeService, this.pageHandler, this.bsShareService));
        return provider;
    }

    public IResourceDataProvider getResourceDataProvider() {
        BatchGatherGZWContextData contextData = this.contextHelper.getContextData();
        return new BatchGatherSchemeResourceDataGZWProvider(contextData.getTaskId(), this.groupService, this.schemeService);
    }

    public IResourceSearcher getResourceSearcher() {
        BatchGatherGZWContextData contextData = this.contextHelper.getContextData();
        return new BatchGatherSchemeResourceGZWSearcher(contextData, this.groupService, this.schemeService);
    }

    public ITableColumnProvider getTableColumnProvider() {
        return new BatchGatherSchemeTableColumnGZWProvider();
    }

    public ITableActionProvider getGroupTableActionProvider() {
        BatchGatherGZWContextData contextData = this.contextHelper.getContextData();
        return new BatchGatherGroupTableActionGZWProvider(contextData, this.groupService, this.schemeService);
    }

    public IToolbarActionProvider getToolbarActionProvider() {
        BatchGatherGZWContextData contextData = this.contextHelper.getContextData();
        return new BatchGatherSchemeToolbarActionGZWProvider(contextData, this.groupService, this.schemeService, this.pageHandler, this.bsShareService);
    }
}

