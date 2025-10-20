/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.factory.action.button.GcAdjustButton
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemInputDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetParentPageImpl
 */
package com.jiuqi.gcreport.inputdata.offsetitem.showtype.unoffsetparent;

import com.jiuqi.gcreport.inputdata.offsetitem.action.button.AutoOffsetButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ColumnSelectButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ExportButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.FilterButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.RefreshButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffsetparent.UnitParentQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.UnoffsetParentTabFixedExportTask.UnOffsetParentTabUnitExportTask;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.button.GcAdjustButton;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemInputDataSource;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetParentPageImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcUnitUnOffsetParent
implements GcOffsetItemShowType {
    @Autowired
    private UnitParentQueryAction unitParentQueryAction;
    @Autowired
    private UnOffsetParentTabUnitExportTask unOffsetParentTabUnitExportTask;
    @Autowired
    private AutoOffsetButton autoOffsetButton;
    @Autowired
    private FilterButton filterButton;
    @Autowired
    private ColumnSelectButton columnSelectButton;
    @Autowired
    private ExportButton exportButton;
    @Autowired
    private RefreshButton refreshButton;
    @Autowired
    private GcAdjustButton gcAdjustButton;

    public GcOffsetItemPage getPage() {
        return GcOffsetItemNotOffsetParentPageImpl.newInstance();
    }

    public GcOffsetItemDataSource getDataSource() {
        return GcOffsetItemInputDataSource.newInstance();
    }

    public String getCode() {
        return FilterMethodEnum.UNIT.getCode();
    }

    public String getTitle() {
        return FilterMethodEnum.UNIT.getTitle();
    }

    public List<Object> getSelectedData(Object object) {
        return null;
    }

    public List<GcOffSetItemAction> actions() {
        ArrayList<GcOffSetItemAction> list = new ArrayList<GcOffSetItemAction>();
        list.add(this.unitParentQueryAction);
        list.add(this.unOffsetParentTabUnitExportTask);
        list.add((GcOffSetItemAction)this.autoOffsetButton);
        list.add((GcOffSetItemAction)this.filterButton);
        list.add((GcOffSetItemAction)this.columnSelectButton);
        list.add((GcOffSetItemAction)this.exportButton);
        list.add((GcOffSetItemAction)this.refreshButton);
        list.add((GcOffSetItemAction)this.gcAdjustButton);
        return list;
    }

    public boolean isEnableMemorySort() {
        return true;
    }
}

