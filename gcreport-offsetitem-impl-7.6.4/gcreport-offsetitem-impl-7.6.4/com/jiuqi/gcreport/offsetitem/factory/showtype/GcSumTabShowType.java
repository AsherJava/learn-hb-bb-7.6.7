/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemInputDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemSumPageImpl
 */
package com.jiuqi.gcreport.offsetitem.factory.showtype;

import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.button.AutoOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.ColumnSelectButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.ExportButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.FilterButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.GcAdjustButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.InputAdjustButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.LossGainButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.ManualBatchOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.RefreshButton;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemInputDataSource;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemSumPageImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcSumTabShowType
implements GcOffsetItemShowType {
    @Autowired
    private AutoOffsetButton autoOffsetButton;
    @Autowired
    private ManualBatchOffsetButton manualBatchOffsetButton;
    @Autowired
    private LossGainButton lossGainButton;
    @Autowired
    private InputAdjustButton inputAdjustButton;
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
        return GcOffsetItemSumPageImpl.newInstance();
    }

    public GcOffsetItemDataSource getDataSource() {
        return GcOffsetItemInputDataSource.newInstance();
    }

    public String getCode() {
        return FilterMethodEnum.AMT.getCode();
    }

    public String getTitle() {
        return FilterMethodEnum.AMT.getTitle();
    }

    public List<Object> getSelectedData(Object object) {
        return null;
    }

    public List<GcOffSetItemAction> actions() {
        ArrayList<GcOffSetItemAction> list = new ArrayList<GcOffSetItemAction>();
        list.add((GcOffSetItemAction)this.autoOffsetButton);
        list.add((GcOffSetItemAction)this.manualBatchOffsetButton);
        list.add((GcOffSetItemAction)this.lossGainButton);
        list.add((GcOffSetItemAction)this.inputAdjustButton);
        list.add((GcOffSetItemAction)this.filterButton);
        list.add((GcOffSetItemAction)this.columnSelectButton);
        list.add((GcOffSetItemAction)this.exportButton);
        list.add((GcOffSetItemAction)this.refreshButton);
        list.add((GcOffSetItemAction)this.gcAdjustButton);
        return list;
    }

    public boolean isEnableMemorySort() {
        return false;
    }
}

