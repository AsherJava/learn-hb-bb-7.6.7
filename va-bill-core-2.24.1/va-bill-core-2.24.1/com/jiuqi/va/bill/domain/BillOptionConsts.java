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

public class BillOptionConsts {
    public static final String BILL1001 = "BILLQUERY1001";
    public static final String BILL1001_TITLE = "\u5355\u636e\u8054\u67e5\u6253\u5f00\u65b9\u5f0f";

    public static LinkedHashMap<String, OptionItemVO> optionFoMap(String groupName) {
        LinkedHashMap<String, OptionItemVO> infos = new LinkedHashMap<String, OptionItemVO>();
        OptionItemVO bill1001 = new OptionItemVO();
        bill1001.setName(BILL1001);
        bill1001.setTitle(BILL1001_TITLE);
        bill1001.setRemark("\u9ed8\u8ba4\u4e3a\u5426\uff0c\u53c2\u6570\u503c\u4e3a\u201c\u5426\u201d\u65f6\uff0c\u6253\u5f00modal\u7a97\u53e3\u65b9\u5f0f\uff1b\u53c2\u6570\u503c\u4e3a\u201c\u662f\u201d\u65f6\uff0c\u901a\u8fc7\u6253\u5f00\u65b0\u9875\u7b7e");
        bill1001.setDefauleVal("0");
        bill1001.setItemtype(OptionItemType.LIST);
        bill1001.addLabel(new OptionItemLabel("\u662f", "1"));
        bill1001.addLabel(new OptionItemLabel("\u5426", "0"));
        infos.put(BILL1001, bill1001);
        return infos;
    }
}

