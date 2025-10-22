/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.PopFrameVO
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 */
package com.jiuqi.nr.system.check2.temporaryTablesSQL;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.PopFrameVO;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TemporaryTablesSQLAction
implements ICheckAction {
    public static final String TEMPORARY_TABLE_SQL_APP_NAME = "temporaryTablesSQL";
    public static final String TEMPORARY_TABLE_SQL_TITLE = "\u4e34\u65f6\u8868\u751f\u6210SQL";

    public String getCheckResourceKey() {
        return "resource-temporary-tables-sql";
    }

    public CheckOptionType getOptionType() {
        return CheckOptionType.MODAL;
    }

    public String getConfirmMessage() {
        return null;
    }

    public Map<String, Object> option() throws Exception {
        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(TEMPORARY_TABLE_SQL_TITLE, "\u4e34\u65f6\u8868\u751f\u6210SQL\u6210\u529f");
        return objectObjectHashMap;
    }

    public PopFrameVO getPageMessage() throws Exception {
        PopFrameVO popFrameVO = new PopFrameVO();
        popFrameVO.setAppName(TEMPORARY_TABLE_SQL_APP_NAME);
        popFrameVO.setTitle(TEMPORARY_TABLE_SQL_TITLE);
        popFrameVO.setEntry("refreshCkd");
        return popFrameVO;
    }
}

