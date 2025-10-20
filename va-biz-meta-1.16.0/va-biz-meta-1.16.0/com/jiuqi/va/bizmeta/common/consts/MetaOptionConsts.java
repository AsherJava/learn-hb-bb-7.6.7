/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemLabel
 *  com.jiuqi.va.domain.option.OptionItemType
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bizmeta.common.consts;

import com.jiuqi.va.domain.option.OptionItemLabel;
import com.jiuqi.va.domain.option.OptionItemType;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.LinkedHashMap;

public class MetaOptionConsts {
    public static final String META_001 = "META001";
    public static final String META001_TITLE = "\u662f\u5426\u5f00\u542f\u5de5\u4f5c\u6d41\u5b9a\u4e49\u6743\u9650";

    public static LinkedHashMap<String, OptionItemVO> optionFoMap(String groupName) {
        LinkedHashMap<String, OptionItemVO> infos = new LinkedHashMap<String, OptionItemVO>();
        OptionItemVO meta001 = new OptionItemVO();
        meta001.setName(META_001);
        meta001.setTitle(META001_TITLE);
        meta001.setRemark("\u9ed8\u8ba4\u4e3a\u5426\uff0c\u53c2\u6570\u503c\u4e3a\u201c\u5426\u201d\u65f6\uff0c\u4e0d\u5f00\u542f\u5de5\u4f5c\u6d41\u5b9a\u4e49\u6743\u9650\uff1b\u53c2\u6570\u503c\u4e3a\u201c\u662f\u201d\u65f6\uff0c\u5f00\u542f\u5de5\u4f5c\u6d41\u5b9a\u4e49\u6743\u9650");
        meta001.setDefauleVal("0");
        meta001.setItemtype(OptionItemType.LIST);
        meta001.addLabel(new OptionItemLabel("\u662f", "1"));
        meta001.addLabel(new OptionItemLabel("\u5426", "0"));
        infos.put(META_001, meta001);
        return infos;
    }
}

