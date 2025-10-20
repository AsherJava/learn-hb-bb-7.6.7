/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemOffsetPageImpl
 */
package com.jiuqi.gcreport.offsetitem.factory.showtype.offset;

import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.button.ColumnSelectButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.DelOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.DisableOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.EnableOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.ExportButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.FilterButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.InputAdjustButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.LossGainButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.RefreshButton;
import com.jiuqi.gcreport.offsetitem.factory.action.button.SortCancelOffsetButton;
import com.jiuqi.gcreport.offsetitem.factory.action.query.offset.AccountDetailsQueryAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemOffsetPageImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcAccountDetailsShowType
implements GcOffsetItemShowType {
    @Autowired
    private AccountDetailsQueryAction accountDetailsQueryAction;
    @Autowired
    private DelOffsetButton delOffsetButton;
    @Autowired
    private SortCancelOffsetButton sortCancelOffsetButton;
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
    private DisableOffsetButton disableOffsetButton;
    @Autowired
    private EnableOffsetButton enableOffsetButton;

    public GcOffsetItemPage getPage() {
        return GcOffsetItemOffsetPageImpl.newInstance();
    }

    public GcOffsetItemDataSource getDataSource() {
        return null;
    }

    public String getCode() {
        return FilterMethodEnum.ACCOUNTDETAILS.getCode();
    }

    public String getTitle() {
        return FilterMethodEnum.ACCOUNTDETAILS.getTitle();
    }

    public List<Object> getSelectedData(Object object) {
        return null;
    }

    public List<GcOffSetItemAction> actions() {
        ArrayList<GcOffSetItemAction> list = new ArrayList<GcOffSetItemAction>();
        list.add(this.accountDetailsQueryAction);
        list.add((GcOffSetItemAction)this.delOffsetButton);
        list.add((GcOffSetItemAction)this.sortCancelOffsetButton);
        list.add((GcOffSetItemAction)this.lossGainButton);
        list.add((GcOffSetItemAction)this.inputAdjustButton);
        list.add((GcOffSetItemAction)this.filterButton);
        list.add((GcOffSetItemAction)this.columnSelectButton);
        list.add((GcOffSetItemAction)this.exportButton);
        list.add((GcOffSetItemAction)this.refreshButton);
        list.add((GcOffSetItemAction)this.disableOffsetButton);
        list.add((GcOffSetItemAction)this.enableOffsetButton);
        return list;
    }
}

