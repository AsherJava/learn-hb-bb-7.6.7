/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.log;

public enum LogModuleEnum {
    NRDESIGNER("NRDESIGNER", "\u62a5\u8868\u5efa\u6a21"),
    NRDEFINITION("NRDEFINITION", "\u62a5\u8868\u53c2\u6570\u5b9a\u4e49"),
    NRBASEDATA("NRBASEDATA", "\u679a\u4e3e\u5b57\u5178"),
    NRZB("NRZB", "\u6307\u6807\u7ba1\u7406"),
    NRLOG("NRLOG", "\u65e5\u5fd7\u67e5\u8be2"),
    NRPUBLICPARAM("NRPUBLICPARAM", "\u516c\u5171\u53c2\u6570\u7ba1\u7406"),
    NRQUERY("NRQUERY", "\u6570\u636e\u67e5\u8be2"),
    NRAUTH("NRAUTH", "\u6743\u9650\u7ba1\u7406"),
    NRUSER("NRUSER", "\u7528\u6237\u7ba1\u7406"),
    NRENTITY("NRENTITY", "\u4e3b\u4f53\u7ba1\u7406"),
    NREFDC("NREFDC", "EFDC"),
    NRPROCESS("NRPROCESS", "\u6d41\u7a0b\u7ba1\u7406"),
    NRWORKFLOW("NRWORKFLOW", "\u5de5\u4f5c\u6d41"),
    NRUSERLOGINSUC("\u767b\u5f55\u7cfb\u7edf", "\u7528\u6237\u6210\u529f\u767b\u9646\u7cfb\u7edf"),
    NRUSERLOGOUTSUC("\u767b\u51fa\u7cfb\u7edf", "\u7528\u6237\u6210\u529f\u767b\u51fa\u7cfb\u7edf");

    private String code;
    private String title;

    private LogModuleEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public static String getModuleTitle(String code) {
        for (LogModuleEnum module : LogModuleEnum.values()) {
            if (!module.getCode().equals(code)) continue;
            return module.getTitle();
        }
        return code;
    }
}

