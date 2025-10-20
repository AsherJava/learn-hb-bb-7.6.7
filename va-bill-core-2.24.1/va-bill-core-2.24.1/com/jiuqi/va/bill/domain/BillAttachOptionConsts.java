/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemLabel
 *  com.jiuqi.va.domain.option.OptionItemType
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.domain.option.OptionItemLabel;
import com.jiuqi.va.domain.option.OptionItemType;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.LinkedHashMap;

public class BillAttachOptionConsts {
    public static final String BILL1001 = "BILLATTACH1001";
    public static final String BILL1001_TITLE = "\u5141\u8bb8\u5220\u9664\u5176\u4ed6\u4eba\u4e0a\u4f20\u7684\u9644\u4ef6";
    public static final String BILL1002 = "BILLATTACH1002";
    public static final String BILL1002_TITLE = "\u5ffd\u7565\u5220\u9664\u9644\u4ef6\u65f6\u7684\u6821\u9a8c";

    public static LinkedHashMap<String, OptionItemVO> optionFoMap(String groupName) {
        LinkedHashMap<String, OptionItemVO> infos = new LinkedHashMap<String, OptionItemVO>();
        OptionItemVO bill1001 = new OptionItemVO();
        bill1001.setName(BILL1001);
        bill1001.setTitle(BILL1001_TITLE);
        bill1001.setRemark("\u9ed8\u8ba4\u4e3a\u5426\uff0c\u53c2\u6570\u503c\u4e3a\u201c\u5426\u201d\u65f6\uff0c\u53ea\u5141\u8bb8\u5220\u9664\u672c\u4eba\u4e0a\u4f20\u7684\u9644\u4ef6\uff1b\u53c2\u6570\u503c\u4e3a\u201c\u662f\u201d\u65f6\uff0c\u5141\u8bb8\u5220\u9664\u5176\u4ed6\u4eba\u4e0a\u4f20\u7684\u9644\u4ef6");
        bill1001.setDefauleVal("0");
        bill1001.setItemtype(OptionItemType.LIST);
        bill1001.addLabel(new OptionItemLabel("\u662f", "1"));
        bill1001.addLabel(new OptionItemLabel("\u5426", "0"));
        infos.put(BILL1001, bill1001);
        OptionItemVO bill1002 = new OptionItemVO();
        bill1002.setName(BILL1002);
        bill1002.setTitle(BILL1002_TITLE);
        bill1002.setRemark("\u9ed8\u8ba4\u4e3a\u5426\uff0c\u53c2\u6570\u503c\u4e3a\u201c\u5426\u201d\u65f6\uff0c\u4e0d\u5141\u8bb8\u5220\u9664\u5ba1\u6279\u786e\u8ba4\u8fc7\u7684\u9644\u4ef6\uff1b\u53c2\u6570\u503c\u4e3a\u201c\u662f\u201d\u65f6\uff0c\u5141\u8bb8\u5220\u9664\u5ba1\u6279\u786e\u8ba4\u8fc7\u7684\u9644\u4ef6");
        bill1002.setDefauleVal("0");
        bill1002.setItemtype(OptionItemType.LIST);
        bill1002.addLabel(new OptionItemLabel("\u662f", "1"));
        bill1002.addLabel(new OptionItemLabel("\u5426", "0"));
        infos.put(BILL1002, bill1002);
        return infos;
    }
}

