/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.IAccessResult
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.data.access.param.IAccessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFieldWriteAccessResultInfo {
    private static final Logger logger = LoggerFactory.getLogger(DataFieldWriteAccessResultInfo.class);
    private IAccessResult accessResult;

    public DataFieldWriteAccessResultInfo(IAccessResult accessResult) {
        this.accessResult = accessResult;
    }

    public boolean haveAccess() {
        try {
            return this.accessResult.haveAccess();
        }
        catch (Exception e) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u6743\u9650\u67e5\u8be2\u5f02\u5e38", e);
            return false;
        }
    }

    public String getMessage() {
        try {
            return this.accessResult.getMessage();
        }
        catch (Exception e) {
            logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u6743\u9650\u67e5\u8be2\u5f02\u5e38", e);
            return "\u672a\u77e5\u5f02\u5e38";
        }
    }
}

