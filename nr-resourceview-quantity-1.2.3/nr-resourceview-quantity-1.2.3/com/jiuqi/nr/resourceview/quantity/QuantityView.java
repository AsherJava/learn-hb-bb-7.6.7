/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryObjectView
 *  com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.table.TableColumnDefine
 *  com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider
 */
package com.jiuqi.nr.resourceview.quantity;

import com.jiuqi.nr.resourceview.quantity.provider.QuantityProvider;
import com.jiuqi.nr.resourceview.quantity.provider.QuantitySearcher;
import com.jiuqi.nr.resourceview.quantity.provider.QuantityToolbarActionProvider;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryObjectView;
import com.jiuqi.nvwa.resourceview.category.IResourceTypeProvider;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.table.TableColumnDefine;
import com.jiuqi.nvwa.resourceview.toolbar.IToolbarActionProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityView
implements IResourceCategoryObjectView {
    @Autowired
    private QuantityProvider quantityProvider;
    @Autowired
    private QuantityToolbarActionProvider toolbarActionProvider;
    @Autowired
    private QuantitySearcher quantitySearcher;

    public List<IResourceTypeProvider> getResourceTypeProviders() {
        return null;
    }

    public IResourceDataProvider getResourceDataProvider() {
        return this.quantityProvider;
    }

    public IResourceSearcher getResourceSearcher() {
        return this.quantitySearcher;
    }

    public IToolbarActionProvider getToolbarActionProvider() {
        return this.toolbarActionProvider;
    }

    public String getId() {
        return "quantityView";
    }

    public String getTitle() {
        return "\u6240\u6709\u91cf\u7eb2";
    }

    public String getParent() {
        return "nr-quantity-manage";
    }

    public String getIcon() {
        return "#icon16_DH_A_NW_gongnengfenzushouqi";
    }

    public double getOrder() {
        return 0.0;
    }

    public List<TableColumnDefine> getTableColumns(String objectId) {
        ArrayList<TableColumnDefine> columnDefines = new ArrayList<TableColumnDefine>();
        if (!"00000000000000000000000000000000".equals(objectId)) {
            if (objectId.startsWith("QI")) {
                columnDefines.add(new TableColumnDefine("code", "\u91cf\u7eb2\u5206\u7c7b\u6807\u8bc6"));
                columnDefines.add(new TableColumnDefine("title", "\u91cf\u7eb2\u5206\u7c7b\u6807\u9898"));
                columnDefines.add(new TableColumnDefine("qc_base", "\u662f\u5426\u57fa\u51c6\u5206\u7c7b"));
                columnDefines.add(new TableColumnDefine("qc_rate", "\u5206\u7c7b\u6bd4\u7387"));
            } else if (objectId.startsWith("QC")) {
                columnDefines.add(new TableColumnDefine("code", "\u91cf\u7eb2\u5355\u4f4d\u6807\u8bc6"));
                columnDefines.add(new TableColumnDefine("title", "\u91cf\u7eb2\u5355\u4f4d\u6807\u9898"));
                columnDefines.add(new TableColumnDefine("qu_base", "\u662f\u5426\u57fa\u51c6\u5355\u4f4d"));
                columnDefines.add(new TableColumnDefine("qu_rate", "\u5355\u4f4d\u6bd4\u7387"));
            } else if (objectId.startsWith("QU")) {
                // empty if block
            }
        } else {
            TableColumnDefine qiCode = new TableColumnDefine("code", "\u91cf\u7eb2\u6807\u8bc6");
            qiCode.setWidth(200);
            TableColumnDefine qiTitle = new TableColumnDefine("title", "\u91cf\u7eb2\u6807\u9898");
            qiTitle.setWidth(200);
            columnDefines.add(qiCode);
            columnDefines.add(qiTitle);
        }
        return columnDefines;
    }
}

