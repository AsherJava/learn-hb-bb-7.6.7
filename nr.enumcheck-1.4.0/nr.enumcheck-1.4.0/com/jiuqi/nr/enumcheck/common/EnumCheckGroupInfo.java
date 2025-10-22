/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumCheckGroupItemInfo;
import java.util.List;

public class EnumCheckGroupInfo {
    private List<EnumCheckGroupItemInfo> enumCheckGroupInfoList;
    private int groupTotalCount;

    public List<EnumCheckGroupItemInfo> getEnumCheckGroupInfoList() {
        return this.enumCheckGroupInfoList;
    }

    public void setEnumCheckGroupInfoList(List<EnumCheckGroupItemInfo> enumCheckGroupInfoList) {
        this.enumCheckGroupInfoList = enumCheckGroupInfoList;
    }

    public int getGroupTotalCount() {
        return this.groupTotalCount;
    }

    public void setGroupTotalCount(int groupTotalCount) {
        this.groupTotalCount = groupTotalCount;
    }
}

