/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.constants;

public final class WorkflowBusinessRelationConst {
    public static final String MD_ORG = "MD_ORG";
    public static final String SLASHES = "/";
    public static final String SEP = "_";
    public static final String META_TYPE = "metaType";
    public static final String KEY_BIZ_NAME = "bizName";
    public static final String KEY_WORKFLOW_NAME = "workflowName";
    public static final String KEY_ADD = "add";
    public static final String KEY_DELETE = "delete";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADAPT_CONDITION = "adaptcondition";
    public static final String KEY_CONDITION_VIEW = "conditionView";
    public static final String DISTRIBUTE_MODE_GROUP = "group";
    public static final String DISTRIBUTE_MODE_LINE = "line";
    public static final String DISTRIBUTE_TYPE_ADD = "add";
    public static final String DISTRIBUTE_TYPE_DELETE = "delete";
    public static final String KEY_WORKFLOW_VARIABLES = "workflowvariables";
    public static final String KEY_ACTION = "action";
    public static final String KEY_EDITABLE_FIELD = "editablefield";
    public static final String KEY_EDITABLE_TABLE = "editabletable";
    public static final String KEY_MULTI_SCHEMES = "multiSchemes";
    public static final String KEY_PARAM_NAME = "paramName";
    public static final String KEY_VALUE_FORMULA = "valueformula";

    public static enum MetaTypeEnum {
        BILL("bill", "\u5355\u636e", "B"),
        BILL_LIST("billlist", "\u5355\u636e\u5217\u8868", "L"),
        WORKFLOW("workflow", "\u5de5\u4f5c\u6d41", "W");

        private final String name;
        private final String title;
        private final String code;

        private MetaTypeEnum(String name, String title, String code) {
            this.name = name;
            this.title = title;
            this.code = code;
        }

        public String getName() {
            return this.name;
        }

        public String getTitle() {
            return this.title;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static enum DistributeStatus {
        SUCCESS(0),
        SUCCESS_WITH_SKIP(1),
        FAILED(2);

        private final int value;

        private DistributeStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

