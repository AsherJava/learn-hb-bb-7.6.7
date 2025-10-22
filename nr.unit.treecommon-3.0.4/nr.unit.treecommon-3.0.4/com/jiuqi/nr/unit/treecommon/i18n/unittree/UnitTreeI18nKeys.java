/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treecommon.i18n.unittree;

public enum UnitTreeI18nKeys {
    ADD("add-main-dimension", "\u65b0\u589e"),
    DELETE("delete-main-dimension", "\u5220\u9664"),
    TAG_NODE("tag-node", "\u6807\u8bb0"),
    SHOW_NODE_TAGS("show-node-tags", "\u663e\u793a\u6807\u8bb0"),
    SHOW_TERMINAL_STATE("terminal-state", "\u7ec8\u6b62\u586b\u62a5"),
    SHOW_SUMMARY_PAGE("showSummaryPage", "\u81ea\u5b9a\u4e49\u6c47\u603b"),
    EXPORT_LEVEL_TREE("export-level-tree", "\u5bfc\u51fa\u7ea7\u6b21\u6811"),
    EXPORT_ALL_LEVEL_TREES("export-all-level-trees", "\u5bfc\u51fa\u5168\u90e8\u7ea7\u6b21\u6811"),
    URGE_TO_REPORT("urge-to-report", "\u50ac\u62a5"),
    SUBMISSION_TIME_SETTING("submission-time-setting", "\u586b\u62a5\u65f6\u95f4\u8bbe\u7f6e"),
    SUBMISSION_TIME_SHOW("submission-time-show", "\u586b\u62a5\u65f6\u95f4\u67e5\u770b"),
    SHOW_CODE("show-code", "\u663e\u793a\u4ee3\u7801"),
    SHOW_TITLE("show-title", "\u663e\u793a\u540d\u79f0"),
    UPLOAD_STATE("uploadState", "\u4e0a\u62a5\u72b6\u6001"),
    BBLX_STATE("bblxState", "\u5355\u4f4d\u7c7b\u578b"),
    TAG_STATE("tagState", "\u6807\u8bb0\u7b5b\u9009"),
    TRANSFER_STATE("filter-unitselector", "\u5355\u4f4d\u7b5b\u9009"),
    SHOW_TAG_MANAGER("show-tag-manager", "\u7ba1\u7406\u6807\u8bb0"),
    NAME_OF_CHILD_COUNT("nameOfChildCount", "\u6240\u6709\u4e0b\u7ea7"),
    TAG_OF_SELECT_UNIT("tagOfSelectUnit", "\u9009\u4e2d\u5355\u4f4d"),
    TAG_OF_SELECTED_TEXT("tagOfSelectedText", "\u9009\u4e2d"),
    TAG_OF_SELECT_UNIT_COUNT("tagOfSelectUnitCount", "\u5bb6\u5355\u4f4d"),
    LOCATE_UNIT_TREE_NODE("locateUnitTreeNode", "\u5f53\u524d\u5355\u4f4d"),
    FIELD_SORT("field-sort", "\u6392\u5e8f"),
    DISPLAY_FIELDS_SETTING("display-fields-setting", "\u663e\u793a\u5b57\u6bb5\u8bbe\u7f6e"),
    ALL_CHILDREN_COUNT("all-children-count", "\u6240\u6709\u4e0b\u7ea7"),
    TREE_EXPAND_ALL_LEVEL("tree-expand-all-level", "\u5c55\u5f00\u6240\u6709\u5c42\u7ea7");

    public String key;
    public String title;

    private UnitTreeI18nKeys(String key, String title) {
        this.key = key;
        this.title = title;
    }
}

