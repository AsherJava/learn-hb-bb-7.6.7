/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryView
 *  com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.table.ITableActionProvider
 *  com.jiuqi.nvwa.resourceview.table.ITableColumnProvider
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 */
package com.jiuqi.nr.multcheck2.view;

import com.jiuqi.nr.multcheck2.view.MCResourceDataProvider;
import com.jiuqi.nr.multcheck2.view.MCResourceSearcher;
import com.jiuqi.nr.multcheck2.view.table.MCTableActionProvider;
import com.jiuqi.nr.multcheck2.view.table.MCTableColumnProvider;
import com.jiuqi.nr.multcheck2.view.toolbar.MCToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryView;
import com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.table.ITableActionProvider;
import com.jiuqi.nvwa.resourceview.table.ITableColumnProvider;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultcheckView
implements IResourceCategoryView {
    @Autowired
    private MCResourceDataProvider dataProvider;
    @Autowired
    private MCToolbarActionProvider toolbarActionProvider;
    @Autowired
    private MCTableColumnProvider tableColumnProvider;
    @Autowired
    private MCResourceSearcher searcher;

    public List<IResourceTypeProvider> getResourceTypeProviders() {
        ArrayList<IResourceTypeProvider> datas = new ArrayList<IResourceTypeProvider>();
        IResourceTypeProvider rtProvider = new IResourceTypeProvider(){

            public ResourceType getResourceType() {
                return new ResourceType("com.jiuqi.nr.multcheck2", "\u5ba1\u6838\u65b9\u6848");
            }

            public ITableActionProvider getTableActionProvider() {
                return new MCTableActionProvider();
            }

            public String getResourceClickAction() {
                return "multcheck2_table_design";
            }
        };
        datas.add(rtProvider);
        return datas;
    }

    public IToolbarActionProvider getToolbarActionProvider() {
        return this.toolbarActionProvider;
    }

    public ITableColumnProvider getTableColumnProvider() {
        return this.tableColumnProvider;
    }

    public IResourceDataProvider getResourceDataProvider() {
        return this.dataProvider;
    }

    public IResourceSearcher getResourceSearcher() {
        return this.searcher;
    }

    public ITableActionProvider getGroupTableActionProvider() {
        return new MCTableActionProvider();
    }

    public String getId() {
        return "com.jiuqi.nr.multcheckView";
    }

    public String getTitle() {
        return "\u5168\u90e8\u65b9\u6848";
    }

    public String getParent() {
        return "com.jiuqi.nr.multcheck2";
    }

    public String getIcon() {
        return "#icon16_DH_A_NW_gongnengfenzushouqi";
    }

    public double getOrder() {
        return 0.0;
    }
}

