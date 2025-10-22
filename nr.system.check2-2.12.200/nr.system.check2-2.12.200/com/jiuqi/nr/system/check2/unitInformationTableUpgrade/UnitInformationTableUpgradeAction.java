/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.unitInformationTableUpgrade;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UnitInformationTableUpgradeAction
implements ICheckAction {
    public static final String UNIT_INFORMATION_TABLE_UPGRADE_APP_NAME = "unitInformationTableUpgrade";
    public static final String UNIT_INFORMATION_TABLE_UPGRADE_TITLE = "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7";

    public String getCheckResourceKey() {
        return "resource-unit-information-table-upgrade";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(UNIT_INFORMATION_TABLE_UPGRADE_TITLE, "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(UNIT_INFORMATION_TABLE_UPGRADE_APP_NAME);
        popFrameVO.setTitle(UNIT_INFORMATION_TABLE_UPGRADE_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}

