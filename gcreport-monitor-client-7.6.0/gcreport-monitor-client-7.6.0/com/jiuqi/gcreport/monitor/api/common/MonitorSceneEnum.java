/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

import com.jiuqi.gcreport.monitor.api.common.MonitorOrgTypeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;

public enum MonitorSceneEnum {
    NODE_FETCH_ACCOUNTING_DATA("fetchAccountingData", "\u63d0\u53d6\u6838\u7b97\u6570\u636e", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.FETCH_IS, MonitorStateEnum.FETCH_NOT_IS),
    NODE_ACCOUNT_DATA_CONSISTENCY("accountDataConsistency", "\u8d26\u8868\u6570\u636e\u4e00\u81f4\u6027", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.PASS_IS, MonitorStateEnum.PASS_NOT_IS),
    NODE_DATAINPUT("dataInput", "\u6570\u636e\u5f55\u5165", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.INPUT_IS, MonitorStateEnum.INPUT_NOT_IS),
    NODE_JOURNAL_ADJUSTMENT("journalAdjustment", "\u65e5\u8bb0\u8d26\u8c03\u6574", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.ADJUST_IS_CONFIRM_IS, MonitorStateEnum.ADJUST_NOT_IS_CONFIRM_IS, MonitorStateEnum.ADJUST_IS_CONFIRM_NOT_IS, MonitorStateEnum.ADJUST_NOT_IS_CONFIRM_NOT_IS),
    NODE_CONVERSION("conversion", "\u5916\u5e01\u6298\u7b97", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.CONVERSION_IS, MonitorStateEnum.CONVERSION_NOT_IS),
    NODE_AUDIT("audit", "\u5ba1\u6838", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.AUDIT_IS, MonitorStateEnum.AUDIT_NOT, MonitorStateEnum.AUDIT_NOT_IS),
    NODE_UPLOAD("upload", "\u4e0a\u62a5", 3, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.UPLOAD_IS, MonitorStateEnum.UPLOAD_NOT, MonitorStateEnum.REJECT_NOT_IS),
    NODE_SUBMITTED("submitted", "\u9001\u5ba1", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.SUBMITTED_IS, MonitorStateEnum.SUBMITTED_NOT, MonitorStateEnum.RETURNED_NOT_IS, MonitorStateEnum.REJECT_NOT_IS),
    NODE_INTERNAL_INVESTMENT("internalInvestment", "\u5185\u90e8\u6295\u8d44", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_TRANSACTION, MonitorStateEnum.PICK_IS, MonitorStateEnum.PICK_NOT_IS),
    NODE_INTERNAL_INTERCOURSE("internalIntercourse", "\u5185\u90e8\u5f80\u6765", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.PICK_IS, MonitorStateEnum.PICK_NOT_IS),
    NODE_INTERNAL_PURCHASE_AND_SALE("internalPurchaseAndSale", "\u5185\u90e8\u8d2d\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_REPORT_FILL_IN, MonitorStateEnum.PICK_IS, MonitorStateEnum.PICK_NOT_IS),
    NODE_INTERNAL_CASH("internalCash", "\u5185\u90e8\u73b0\u91d1", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_TRANSACTION, MonitorStateEnum.PICK_IS, MonitorStateEnum.PICK_NOT_IS),
    NODE_DATA_INIT("dataInit", "\u6570\u636e\u521d\u59cb", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_CONNECTED_TRANSACTION, MonitorStateEnum.INITIAL_IS, MonitorStateEnum.INITIAL_NOT_IS),
    NODE_DATA_PICK("dataPick", "\u6570\u636e\u91c7\u96c6", 0, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_CONNECTED_TRANSACTION, MonitorStateEnum.PICK_IS, MonitorStateEnum.PICK_NOT_IS),
    NODE_DATA_QUERY("dataQuery", "\u6570\u636e\u67e5\u8be2", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_CONNECTED_TRANSACTION, new MonitorStateEnum[0]),
    NODE_AUTO_CHECK("autoCheck", "\u81ea\u52a8\u5bf9\u8d26", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_TRANSACTION, MonitorStateEnum.CHECK_IS, MonitorStateEnum.CHECK_NOT_IS),
    NODE_MANUAL_CHECK("manualCheck", "\u624b\u5de5\u5bf9\u8d26", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_TRANSACTION, new MonitorStateEnum[0]),
    NODE_INVESTMENT_INIT("investmentInit", "\u6295\u8d44\u521d\u59cb", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_INVESTMENT_ACCOUNT, MonitorStateEnum.INITIAL_IS, MonitorStateEnum.INITIAL_NOT_IS),
    NODE_INVESTMENT_NEW("investmentNew", "\u6295\u8d44\u65b0\u589e", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_INVESTMENT_ACCOUNT, MonitorStateEnum.FILLIN_IS, MonitorStateEnum.FILLIN_NOT_IS),
    NODE_ASSETS_INIT("assetsInit", "\u8d44\u4ea7\u521d\u59cb", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_FIXED_ASSETS_ACCOUNT, MonitorStateEnum.INITIAL_IS, MonitorStateEnum.INITIAL_NOT_IS),
    NODE_ASSETS_NEW("assetsNew", "\u8d44\u4ea7\u65b0\u589e", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_INTERNAL_FIXED_ASSETS_ACCOUNT, MonitorStateEnum.FILLIN_IS, MonitorStateEnum.FILLIN_NOT_IS),
    NODE_ADJUSTING_ENTRIES("adjustingEntries", "\u5f55\u5165\u8c03\u6574\u5206\u5f55", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_CONSOLIDATED_JOURNAL_ADJUSTMENT, MonitorStateEnum.ADJUST_IS, MonitorStateEnum.ADJUST_NOT_IS),
    NODE_INTERCOURSE_OFFSET("intercourseOffset", "\u5f80\u6765\u62b5\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_COMBINED_CALC, MonitorStateEnum.OFFSET_IS, MonitorStateEnum.OFFSET_NOT_IS),
    NODE_TRANSACTION_OFFSET("transactionOffset", "\u4ea4\u6613\u62b5\u6d88", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_COMBINED_CALC, MonitorStateEnum.OFFSET_IS, MonitorStateEnum.OFFSET_NOT_IS),
    NODE_INVESTMENT_OFFSET("investmentOffset", "\u6295\u8d44\u62b5\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_COMBINED_CALC, MonitorStateEnum.CREATE_IS, MonitorStateEnum.CREATE_NOT_IS),
    NODE_ASSETS_OFFSET("assetsOffset", "\u8d44\u4ea7\u62b5\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_COMBINED_CALC, MonitorStateEnum.CREATE_IS, MonitorStateEnum.CREATE_NOT_IS),
    NODE_CALC("calc", "\u5408\u5e76\u8ba1\u7b97", 1, MonitorOrgTypeEnum.PARENT_ORG, MonitorSceneGroupEnum.GROUP_COMBINED_CALC, MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS),
    NODE_MANUAL_OFFSET("manualOffset", "\u624b\u5de5\u5e72\u9884\u62b5\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_OFFSET_ENTRY_MANAGEMENT, new MonitorStateEnum[0]),
    NODE_ADJUST_OFFSET("adjustOffset", "\u8f93\u5165\u8c03\u6574\u62b5\u9500", 99, MonitorOrgTypeEnum.ALL_ORG, MonitorSceneGroupEnum.GROUP_OFFSET_ENTRY_MANAGEMENT, new MonitorStateEnum[0]),
    NODE_FINISH_MERGE("finishMerge", "\u5b8c\u6210\u5408\u5e76", 2, MonitorOrgTypeEnum.PARENT_ORG, MonitorSceneGroupEnum.GROUP_REPORT_MERGE, MonitorStateEnum.FINISH_IS, MonitorStateEnum.FINISH_NOT_IS);

    private MonitorStateEnum[] monitorStates;
    private String code;
    private String name;
    private Integer order;
    private MonitorOrgTypeEnum nodeOrgType;
    private MonitorSceneGroupEnum sceneGroup;

    private MonitorSceneEnum(String code, String name, Integer order, MonitorOrgTypeEnum nodeOrgType, MonitorSceneGroupEnum sceneGroup, MonitorStateEnum ... monitorStates) {
        this.code = code;
        this.name = name;
        this.order = order;
        this.nodeOrgType = nodeOrgType;
        this.sceneGroup = sceneGroup;
        this.monitorStates = monitorStates;
    }

    public MonitorStateEnum[] getMonitorStates() {
        return this.monitorStates;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public MonitorSceneGroupEnum getSceneGroup() {
        return this.sceneGroup;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setMonitorStates(MonitorStateEnum[] monitorStates) {
        this.monitorStates = monitorStates;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonitorOrgTypeEnum getNodeOrgType() {
        return this.nodeOrgType;
    }

    public void setNodeOrgType(MonitorOrgTypeEnum nodeOrgType) {
        this.nodeOrgType = nodeOrgType;
    }

    public void setSceneGroup(MonitorSceneGroupEnum sceneGroup) {
        this.sceneGroup = sceneGroup;
    }

    public static MonitorSceneEnum getInstance(String code) {
        MonitorSceneEnum[] scenes;
        if (code == null || code.trim().length() == 0) {
            return null;
        }
        for (MonitorSceneEnum tempScene : scenes = MonitorSceneEnum.values()) {
            if (!tempScene.getCode().equals(code.trim())) continue;
            return tempScene;
        }
        return null;
    }
}

