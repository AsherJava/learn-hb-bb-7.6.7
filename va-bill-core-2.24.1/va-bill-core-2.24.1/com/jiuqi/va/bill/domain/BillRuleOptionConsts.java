/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemType
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.domain.option.OptionItemType;
import java.util.LinkedHashMap;

public class BillRuleOptionConsts {
    public static final String BR1001 = "BR1001";
    public static final String BR1001_TITLE = "\u5de5\u4f5c\u6d41\u8282\u70b9\u9a73\u56de\u540e\uff0c\u63d0\u4ea4\u6216\u5ba1\u6279\u65f6\u9700\u8981\u586b\u5199\u4fee\u6539\u8bf4\u660e";
    public static final String BR1002 = "BR1002";
    public static final String BR1002_TITLE = "\u5de5\u4f5c\u6d41\u8282\u70b9\u663e\u793a\u5168\u90e8\u8282\u70b9\u7684\u9a73\u56de\u610f\u89c1";

    public static LinkedHashMap<String, BillRuleOptionVO> optionFoMap(String groupName) {
        LinkedHashMap<String, BillRuleOptionVO> infos = new LinkedHashMap<String, BillRuleOptionVO>();
        BillRuleOptionVO br1001 = new BillRuleOptionVO();
        br1001.setName(BR1001);
        br1001.setTitle(BR1001_TITLE);
        br1001.setRemark("\u9ed8\u8ba4\u4e0d\u52fe\u9009\u586b\u5199\u4fee\u6539\u8bf4\u660e\uff0c\u9002\u7528\u5355\u636e\u4e3a\u7a7a\u65f6\uff0c\u6240\u6709\u5355\u636e\u90fd\u4e0d\u54cd\u5e94");
        br1001.setItemtype(OptionItemType.LIST);
        infos.put(BR1001, br1001);
        BillRuleOptionVO br1002 = new BillRuleOptionVO();
        br1002.setName(BR1002);
        br1002.setTitle(BR1002_TITLE);
        br1002.setRemark("\u9ed8\u8ba4\u4e0d\u663e\u793a\u9a73\u56de\u539f\u56e0\uff0c\u9002\u7528\u5355\u636e\u4e3a\u7a7a\u65f6\uff0c\u6240\u6709\u5355\u636e\u90fd\u4e0d\u54cd\u5e94");
        br1002.setItemtype(OptionItemType.LIST);
        infos.put(BR1002, br1002);
        return infos;
    }
}

