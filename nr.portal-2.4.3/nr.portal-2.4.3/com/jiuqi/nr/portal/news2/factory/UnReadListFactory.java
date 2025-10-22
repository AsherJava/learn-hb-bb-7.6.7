/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.factory;

import com.jiuqi.nr.portal.news2.factory.FileUnReadList;
import com.jiuqi.nr.portal.news2.factory.IUnReadList;
import com.jiuqi.nr.portal.news2.factory.NewsUnReadList;
import com.jiuqi.nr.portal.news2.service.PortalBeanUtil;

public class UnReadListFactory {
    public static IUnReadList getUnReadList(String unReadType) {
        switch (unReadType) {
            case "file": {
                return PortalBeanUtil.getBean(FileUnReadList.class);
            }
            case "news": {
                return PortalBeanUtil.getBean(NewsUnReadList.class);
            }
        }
        throw new IllegalArgumentException("\u672a\u8bfb\u64cd\u4f5c\u7c7b\u578b\u65e0\u6548\uff0c\u8bf7\u68c0\u67e5\uff01");
    }
}

