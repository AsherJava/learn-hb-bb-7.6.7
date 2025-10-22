/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.resourceview.table.ITableActionProvider
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.BatchGatherSchemeTableActionGZWProvider;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.resourceview.table.ITableActionProvider;

public class BatchGatherSchemeResourceTypeGZWProvider
implements IResourceTypeProvider {
    public static final String TYPE_ID = "com.jiuqi.nr.batch.gather.gzw.web.app.scheme.resource.type";
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;
    private BeforeViewPageDataGZWHandler pageHandler;
    private BSShareService bsShareService;

    public BatchGatherSchemeResourceTypeGZWProvider(BatchGatherGZWContextData contextData, BSSchemeService schemeService, BeforeViewPageDataGZWHandler pageHandler, BSShareService bsShareService) {
        this.contextData = contextData;
        this.pageHandler = pageHandler;
        this.schemeService = schemeService;
        this.bsShareService = bsShareService;
    }

    public ResourceType getResourceType() {
        return new ResourceType(TYPE_ID, "\u6c47\u603b\u65b9\u6848\u8d44\u6e90\u7c7b\u578b");
    }

    public ITableActionProvider getTableActionProvider() {
        return new BatchGatherSchemeTableActionGZWProvider(this.contextData, this.schemeService, this.pageHandler, this.bsShareService);
    }

    public String getResourceClickAction() {
        return "com.jiuqi.nr.batch.gather.gzw.web.app.action.show.scheme.data";
    }
}

