/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemConnectDealDataSource
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.factory.showtype.common;

import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.factory.action.manual.QueryUnOffsetFinancialCheck;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemConnectDealDataSource;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCommonUnOffsetFinancialCheck
implements GcOffsetItemShowType {
    @Autowired
    private QueryUnOffsetFinancialCheck queryUnOffsetFinancialCheck;

    public GcOffsetItemPage getPage() {
        return GcOffsetItemNotOffsetPageImpl.newInstance();
    }

    public GcOffsetItemDataSource getDataSource() {
        return GcOffsetItemConnectDealDataSource.newInstance();
    }

    public String getCode() {
        return FilterMethodEnum.COMMON.getCode();
    }

    public String getTitle() {
        return FilterMethodEnum.COMMON.getTitle();
    }

    public List<Object> getSelectedData(Object object) {
        return null;
    }

    public List<GcOffSetItemAction> actions() {
        ArrayList<GcOffSetItemAction> list = new ArrayList<GcOffSetItemAction>();
        list.add(this.queryUnOffsetFinancialCheck);
        return list;
    }
}

