/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;

public enum MonitorSceneGroupEnum {
    GROUP_REPORT_FILL_IN("reportFillIn", "\u5355\u6237\u62a5\u8868\u586b\u62a5", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_REPORT_PICK("reportPick", "\u5185\u90e8\u62a5\u8868\u636e\u91c7\u96c6", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_CONNECTED_TRANSACTION("connectedTransaction", "\u5173\u8054\u4ea4\u6613\u5e73\u53f0", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_INTERNAL_TRANSACTION("internalTransaction", "\u5185\u90e8\u4ea4\u6613\u5bf9\u8d26", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_INTERNAL_INVESTMENT_ACCOUNT("internalInvestmentAccount", "\u5185\u90e8\u6295\u8d44\u53f0\u8d26", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_INTERNAL_FIXED_ASSETS_ACCOUNT("internalFixedAssetsAccount", "\u5185\u90e8\u56fa\u5b9a\u8d44\u4ea7\u53f0\u8d26", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_CONSOLIDATED_JOURNAL_ADJUSTMENT("consolidatedJournalAdjustment", "\u5408\u5e76\u65e5\u8bb0\u8d26\u8c03\u6574", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_COMBINED_CALC("combinedCalc", "\u5408\u5e76\u8ba1\u7b97", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_OFFSET_ENTRY_MANAGEMENT("offsetEntryManagement", "\u62b5\u9500\u5206\u5f55\u7ba1\u7406", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    GROUP_REPORT_MERGE("reportMerge", "\u5b8c\u6210\u62a5\u8868\u5408\u5e76", MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS);

    private String code;
    private String name;
    private MonitorStateEnum[] monitors;

    private MonitorSceneGroupEnum(String code, String name, MonitorStateEnum ... monitors) {
        this.code = code;
        this.name = name;
        this.monitors = monitors;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public MonitorStateEnum[] getMonitorStates() {
        return this.monitors;
    }

    public static MonitorSceneGroupEnum getInstance(String code) {
        MonitorSceneGroupEnum[] scenes;
        if (code == null || code.trim().length() == 0) {
            return null;
        }
        for (MonitorSceneGroupEnum tempScene : scenes = MonitorSceneGroupEnum.values()) {
            if (!tempScene.getCode().equals(code.trim())) continue;
            return tempScene;
        }
        return null;
    }
}

