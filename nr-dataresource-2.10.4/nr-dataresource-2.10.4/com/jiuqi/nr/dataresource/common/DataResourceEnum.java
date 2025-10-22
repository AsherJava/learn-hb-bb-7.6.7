/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.dataresource.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DataResourceEnum implements ErrorEnum
{
    DATA_RESOURCE("DD", "\u5931\u8d25"),
    DATA_RESOURCE_DD_1_1("DD_01_1", "\u540d\u79f0\u91cd\u590d"),
    DATA_RESOURCE_DD_1_2("DD_01_2", "\u540d\u79f0\u5fc5\u586b"),
    DATA_RESOURCE_DG_1_1("DG_01_1", "\u5206\u7ec4\u540d\u79f0\u91cd\u590d\uff01"),
    DATA_RESOURCE_DG_2_1("DG_02_1", "\u5206\u7ec4\u4e0b\u6709\u6307\u6807\u89c6\u56fe,\u8bf7\u5220\u9664\u6307\u6807\u89c6\u56fe\u540e\u91cd\u8bd5\uff01"),
    DATA_RESOURCE_DG_3_1("DG_03_1", "\u5bf9\u7236\u7ea7\u5206\u7ec4\u6ca1\u6709\u5199\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u64cd\u4f5c\uff01"),
    DATA_RESOURCE_DG_3_2("DG_03_2", "\u5bf9\u5f53\u524d\u5206\u7ec4\u6ca1\u6709\u5199\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u64cd\u4f5c\uff01"),
    DATA_RESOURCE_DG_3_3("DG_03_3", "\u5bf9\u5f53\u524d\u6307\u6807\u89c6\u56fe\u6ca1\u6709\u5199\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u64cd\u4f5c\uff01"),
    DATA_RESOURCE_DG_3_4("DG_03_4", "\u5bf9\u6765\u6e90\u6307\u6807\u89c6\u56fe\u6ca1\u6709\u8bfb\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u590d\u5236\uff01"),
    DATA_RESOURCE_DG_3_5("DG_03_5", "\u53ea\u6709\u7ba1\u7406\u5458\u624d\u80fd\u64cd\u4f5c\u8ddf\u8282\u70b9\uff01"),
    DATA_RESOURCE_DR_1_1("DR_01_1", "\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a"),
    DATA_RESOURCE_DR_1_2("DR_01_2", "\u4e0d\u5141\u8bb8\u91cd\u590d\u6dfb\u52a0\uff01");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DataResourceEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

