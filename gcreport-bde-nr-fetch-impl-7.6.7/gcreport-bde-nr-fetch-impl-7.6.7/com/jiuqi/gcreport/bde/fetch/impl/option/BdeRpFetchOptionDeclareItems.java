/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.option.BdeOptionTypeEnum
 *  com.jiuqi.bde.common.option.IBdeOptionDeclareItems
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.gcreport.bde.fetch.impl.option;

import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.option.IBdeOptionDeclareItems;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.AutomationBatchFetchTimeoutTime;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BatchBdeFetchSplitTaskParallelDeclare;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BdeAutoFetchSetting;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BdeFetchSettingAdaptOptionItem;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BdeFetchSettingOutportZbCodeRuleOptionItem;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BdeFetchSettingStopUseFlagOptionItem;
import com.jiuqi.gcreport.bde.fetch.impl.option.item.BdeFetchTaskTimeoutTime;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeRpFetchOptionDeclareItems
implements IBdeOptionDeclareItems {
    public BdeOptionTypeEnum getOptionType() {
        return BdeOptionTypeEnum.SYSTEM;
    }

    public String getId() {
        return "bde-rp-fetch-option";
    }

    public String getTitle() {
        return "\u62a5\u8868\u53d6\u6570";
    }

    public List<ISystemOptionItem> getItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new BdeFetchTaskTimeoutTime());
        optionItems.add(new BatchBdeFetchSplitTaskParallelDeclare());
        optionItems.add(new BdeFetchSettingAdaptOptionItem());
        optionItems.add(new BdeFetchSettingOutportZbCodeRuleOptionItem());
        optionItems.add(new BdeFetchSettingStopUseFlagOptionItem());
        optionItems.add(new BdeAutoFetchSetting());
        optionItems.add(new AutomationBatchFetchTimeoutTime());
        return optionItems;
    }

    public int getOrder() {
        return 1;
    }
}

