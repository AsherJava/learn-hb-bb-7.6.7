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
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 */
package com.jiuqi.gcreport.inputdata.offsetitem.showtype.unoffset;

import com.jiuqi.gcreport.inputdata.offsetitem.action.button.AutoOffsetButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ColumnSelectButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ExportButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.FilterButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ManualBatchOffsetButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.ManualOffsetButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.OffsetCheckButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.button.RefreshButton;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.RuleQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.factory.UnOffsetTabFixedExportTask.UnOffsetTabRuleExportTask;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.button.GcAdjustButton;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemInputDataSource;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcRuleQueryUnOffset
implements GcOffsetItemShowType {
    @Autowired
    private RuleQueryAction ruleQueryAction;
    @Autowired
    private UnOffsetTabRuleExportTask unOffsetTabRuleExportTask;
    @Autowired
    private AutoOffsetButton autoOffsetButton;
    @Autowired
    private OffsetCheckButton offsetCheckButton;
    @Autowired
    private ManualOffsetButton manualOffsetButton;
    @Autowired
    private ManualBatchOffsetButton manualBatchOffsetButton;
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
        return GcOffsetItemNotOffsetPageImpl.newInstance();
    }

    public GcOffsetItemDataSource getDataSource() {
        return GcOffsetItemInputDataSource.newInstance();
    }

    public String getCode() {
        return FilterMethodEnum.RULE.getCode();
    }

    public String getTitle() {
        return FilterMethodEnum.RULE.getTitle();
    }

    public List<Object> getSelectedData(Object object) {
        return null;
    }

    public List<GcOffSetItemAction> actions() {
        ArrayList<GcOffSetItemAction> list = new ArrayList<GcOffSetItemAction>();
        list.add(this.ruleQueryAction);
        list.add(this.unOffsetTabRuleExportTask);
        list.add((GcOffSetItemAction)this.autoOffsetButton);
        list.add((GcOffSetItemAction)this.offsetCheckButton);
        list.add((GcOffSetItemAction)this.manualOffsetButton);
        list.add((GcOffSetItemAction)this.manualBatchOffsetButton);
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

