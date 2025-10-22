/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="dataentry_infoviews")
public class InfoViewsGatherImp
implements IListGathers<InfoViewItem> {
    @Override
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.INFOVIEW;
    }

    @Override
    public List<InfoViewItem> gather() {
        ArrayList<InfoViewItem> result = new ArrayList<InfoViewItem>();
        InfoViewItem checkResult = new InfoViewItem("check_result", "\u5ba1\u6838\u7ed3\u679c");
        InfoViewItem commitHistory = new InfoViewItem("commit_history", "\u4e0a\u62a5\u5386\u53f2");
        InfoViewItem search_zb = new InfoViewItem("search_zb", "\u6307\u6807\u641c\u7d22");
        InfoViewItem zb_info = new InfoViewItem("zb_info", "\u6307\u6807\u8be6\u60c5");
        InfoViewItem zb_select = new InfoViewItem("zb_select", "\u6307\u6807\u9009\u62e9\u5668");
        result.add(checkResult);
        result.add(commitHistory);
        result.add(search_zb);
        result.add(zb_info);
        result.add(zb_select);
        return result;
    }
}

