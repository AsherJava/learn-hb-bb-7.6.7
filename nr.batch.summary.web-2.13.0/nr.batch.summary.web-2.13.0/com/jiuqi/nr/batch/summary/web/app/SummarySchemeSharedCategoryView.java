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
 *  com.jiuqi.nvwa.resourceview.table.ITableColumnProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.web.app.SummarySchemeResourceSearcher;
import com.jiuqi.nr.batch.summary.web.app.SummarySchemeSharedResourceDataProvider;
import com.jiuqi.nr.batch.summary.web.app.SummarySchemeSharedResourceTypeProvider;
import com.jiuqi.nr.batch.summary.web.app.SummarySchemeSharedTableColumnProvider;
import com.jiuqi.nr.batch.summary.web.app.SummarySchemeSharedToolbarActionProvider;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextHelper;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryView;
import com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.table.ITableColumnProvider;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class SummarySchemeSharedCategoryView
implements IResourceCategoryView {
    public static final String VIEW_ID = "shared-summary-scheme-resource-view-id";
    @Resource
    private BSGroupService groupService;
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private BatchSummaryContextHelper contextHelper;
    @Resource
    private BeforeViewPageDataHandler pageHandler;
    @Resource
    private BSShareService bsShareService;

    public String getId() {
        return VIEW_ID;
    }

    public String getTitle() {
        return "\u4ed6\u4eba\u5171\u4eab";
    }

    public String getParent() {
        return "batch-summary";
    }

    public String getIcon() {
        return "#icon16_SHU_A_NW_yingyongfuwu";
    }

    public double getOrder() {
        return 2.0;
    }

    public List<IResourceTypeProvider> getResourceTypeProviders() {
        ArrayList<IResourceTypeProvider> provider = new ArrayList<IResourceTypeProvider>();
        BatchSummaryContextData contextData = this.contextHelper.getContextData();
        provider.add(new SummarySchemeSharedResourceTypeProvider(contextData, this.schemeService, this.pageHandler));
        return provider;
    }

    public IResourceDataProvider getResourceDataProvider() {
        BatchSummaryContextData contextData = this.contextHelper.getContextData();
        return new SummarySchemeSharedResourceDataProvider(contextData.getTaskId(), this.bsShareService);
    }

    public IResourceSearcher getResourceSearcher() {
        BatchSummaryContextData contextData = this.contextHelper.getContextData();
        return new SummarySchemeResourceSearcher(contextData, this.groupService, this.schemeService);
    }

    public ITableColumnProvider getTableColumnProvider() {
        return new SummarySchemeSharedTableColumnProvider();
    }

    public IToolbarActionProvider getToolbarActionProvider() {
        BatchSummaryContextData contextData = this.contextHelper.getContextData();
        return new SummarySchemeSharedToolbarActionProvider(contextData, this.groupService, this.schemeService, this.pageHandler);
    }
}

