/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.snapshotUpgrade;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SnapshotUpgradeAction
implements ICheckAction {
    public static final String SNAPSHOT_UPGRADE_APP_NAME = "snapshotUpgrade";
    public static final String SNAPSHOT_UPGRADE_TITLE = "\u5feb\u7167\u5347\u7ea7";

    public String getCheckResourceKey() {
        return "resource-snapshot-upgrade";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(SNAPSHOT_UPGRADE_TITLE, "\u5feb\u7167\u5347\u7ea7\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(SNAPSHOT_UPGRADE_APP_NAME);
        popFrameVO.setTitle(SNAPSHOT_UPGRADE_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}

