/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.dataTableIndexRepair;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DataTableIndexRepairAction
implements ICheckAction {
    public static final String DATA_TABLE_INDEX_REPAIR_APP_NAME = "resourceDataTableIndexRepair";
    public static final String DATA_TABLE_INDEX_REPAIR_TITLE = "\u6570\u636e\u8868\u7d22\u5f15\u4fee\u590d";

    public String getCheckResourceKey() {
        return "resource-data-table-index-repair";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(DATA_TABLE_INDEX_REPAIR_TITLE, "\u6570\u636e\u8868\u7d22\u5f15\u4fee\u590d\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(DATA_TABLE_INDEX_REPAIR_APP_NAME);
        popFrameVO.setTitle(DATA_TABLE_INDEX_REPAIR_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}

