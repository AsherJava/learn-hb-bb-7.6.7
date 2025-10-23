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
package com.jiuqi.nr.mapping.view;

import com.jiuqi.nr.mapping.view.MSResourceDataProvider;
import com.jiuqi.nr.mapping.view.search.MSResourceSearcher;
import com.jiuqi.nr.mapping.view.table.MSTableActionProvider;
import com.jiuqi.nr.mapping.view.table.MSTableColumnProvider;
import com.jiuqi.nr.mapping.view.toolbar.MSToolbarActionProvider;
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
public class MappingSchemeView
implements IResourceCategoryView {
    @Autowired
    private MSResourceDataProvider dataProvider;
    @Autowired
    private MSToolbarActionProvider toolbarActionProvider;
    @Autowired
    private MSResourceSearcher searcher;
    @Autowired
    private MSTableColumnProvider tableColumnProvider;

    public IResourceDataProvider getResourceDataProvider() {
        return this.dataProvider;
    }

    public IToolbarActionProvider getToolbarActionProvider() {
        return this.toolbarActionProvider;
    }

    public IResourceSearcher getResourceSearcher() {
        return this.searcher;
    }

    public ITableColumnProvider getTableColumnProvider() {
        return this.tableColumnProvider;
    }

    public List<IResourceTypeProvider> getResourceTypeProviders() {
        ArrayList<IResourceTypeProvider> datas = new ArrayList<IResourceTypeProvider>();
        IResourceTypeProvider rtProvider = new IResourceTypeProvider(){

            public ResourceType getResourceType() {
                return new ResourceType("com.jiuqi.nr.mappingScheme", "\u6620\u5c04\u65b9\u6848");
            }

            public ITableActionProvider getTableActionProvider() {
                return new MSTableActionProvider();
            }

            public String getResourceClickAction() {
                return "mapping_table_mapping";
            }
        };
        datas.add(rtProvider);
        return datas;
    }

    public String getId() {
        return "com.jiuqi.nr.mappingSchemeView";
    }

    public String getTitle() {
        return "\u5168\u90e8\u4efb\u52a1";
    }

    public String getParent() {
        return "com.jiuqi.nr.mappingScheme";
    }

    public String getIcon() {
        return "#icon16_DH_A_NW_gongnengfenzushouqi";
    }

    public double getOrder() {
        return 0.0;
    }
}

