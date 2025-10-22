/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

public enum MultCheckEnum {
    GSSH("GSSH", "\u516c\u5f0f\u5ba1\u6838"),
    ENUM_CHECK("enumCheck", "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5"),
    INTEGRITY_FORM("integrityForm", "\u8868\u5b8c\u6574\u6027\u68c0\u67e5"),
    NODE_CHECK("nodeCheck", "\u8282\u70b9\u68c0\u67e5"),
    ENTITY_CHECK("entityCheck", "\u6237\u6570\u6838\u5bf9"),
    ERROR_DESC_CHECK("errorDescCheck", "\u51fa\u9519\u8bf4\u660e\u68c0\u67e5"),
    ATTACHMENT_CHECK("attachmentCheck", "\u9644\u4ef6\u6587\u6863\u68c0\u67e5"),
    QUERY_TEMPLATE("queryTemplate", "\u67e5\u8be2\u6a21\u677f"),
    ENTITY_TREE_CHECK("entityTreeCheck", "\u6811\u5f62\u7ed3\u6784\u68c0\u67e5"),
    DATA_ANALYSIS("dataAnalysis", "\u6570\u636e\u5206\u6790"),
    ZB_QUERY_TEMPLATE("zbQueryTemplate", "\u6307\u6807\u7efc\u5408\u67e5\u8be2");

    private String key;
    private String desc;

    private MultCheckEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static MultCheckEnum findByKey(String key) {
        MultCheckEnum dict = null;
        switch (key) {
            case "GSSH": {
                dict = GSSH;
                break;
            }
            case "enumCheck": {
                dict = ENUM_CHECK;
                break;
            }
            case "integrityForm": {
                dict = INTEGRITY_FORM;
                break;
            }
            case "nodeCheck": {
                dict = NODE_CHECK;
                break;
            }
            case "entityCheck": {
                dict = ENTITY_CHECK;
                break;
            }
            case "errorDescCheck": {
                dict = ERROR_DESC_CHECK;
                break;
            }
            case "entityTreeCheck": {
                dict = ENTITY_TREE_CHECK;
                break;
            }
            case "attachmentCheck": {
                dict = ATTACHMENT_CHECK;
                break;
            }
            case "queryTemplate": {
                dict = QUERY_TEMPLATE;
                break;
            }
            case "zbQueryTemplate": {
                dict = ZB_QUERY_TEMPLATE;
                break;
            }
            case "dataAnalysis": {
                dict = DATA_ANALYSIS;
            }
        }
        return dict;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getKey() {
        return this.key;
    }
}

