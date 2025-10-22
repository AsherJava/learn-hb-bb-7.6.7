/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treecommon.i18n.uselector;

public enum UnitSelectorI18nKeys {
    CHECK_ALL("check_all", "\u5168\u9009"),
    CHECK_DIRECT_PARENT("check_direct_parent", "\u9009\u62e9\u76f4\u63a5\u4e0a\u7ea7"),
    CHECK_ALL_CHILDREN("check_all_children", "\u52fe\u9009\u6240\u6709\u4e0b\u7ea7"),
    CHECK_ALL_CHILDREN_WITH_CTRL("check_all_children_with_ctrl", "Ctrl\u52fe\u9009\u6240\u6709\u4e0b\u7ea7"),
    CHECK_ALL_LEAVES("check_all_leaves", "\u6240\u6709\u53f6\u5b50\u8282\u70b9"),
    CHECK_ALL_NON_LEAVES("check_all_non_leaves", "\u6240\u6709\u975e\u53f6\u5b50\u8282\u70b9"),
    CHECK_ALL_PARENTS("check_all_parents", "\u9009\u62e9\u6240\u6709\u4e0a\u7ea7"),
    CHECK_DIRECT_CHILDREN("check_direct_children", "\u52fe\u9009\u76f4\u63a5\u4e0b\u7ea7"),
    CHECK_DIRECT_CHILDREN_WITH_SHIFT("check_direct_children_with_shift", "Shift\u52fe\u9009\u76f4\u63a5\u4e0b\u7ea7"),
    CHECK_LOWER_LEAVES("check_lower_leaves", "\u9009\u62e9\u6240\u6709\u4e0b\u7ea7\u53f6\u5b50\u8282\u70b9"),
    CHECK_LOWER_NON_LEAVES("check_lower_non_leaves", "\u9009\u62e9\u6240\u6709\u4e0b\u7ea7\u975e\u53f6\u5b50\u8282\u70b9"),
    CHECK_NODES("check_nodes", "\u52fe\u9009\u8282\u70b9"),
    CHECK_WITH_BBLX("check_with_bblx", "\u62a5\u8868\u7c7b\u578b"),
    CHECK_WITH_EDIT_TABLE("check_with_edit_table", "\u5217\u8868\u7b5b\u9009"),
    CHECK_WITH_EXPRESSION("check_with_expression", "\u8868\u8fbe\u5f0f\u7b5b\u9009"),
    CHECK_WITH_FILTER_SCHEME("check_with_filter_scheme", "\u9ad8\u7ea7\u7b5b\u9009"),
    CHECK_WITH_FORMULA("check_with_formula", "\u516c\u5f0f\u7b5b\u9009"),
    CHECK_WITH_NODE_TAGS("check_with_node_tags", "\u5e38\u7528\u5355\u4f4d"),
    CHECK_WITH_TREE_LEVEL("check_with_tree_level", "\u7ea7\u6b21\u9009\u62e9"),
    CHECK_WITH_WORKFLOW_STATUS("check_with_workflow_status", "\u4e0a\u62a5\u72b6\u6001"),
    DELETE_ALL("delete_all", "\u5168\u5220"),
    DELETE_ALL_CHILDREN("delete_all_children", "\u53d6\u6d88\u52fe\u9009\u6240\u6709\u4e0b\u7ea7"),
    DELETE_ALL_CHILDREN_WITH_CTRL("delete_all_children_with_ctrl", "Ctrl\u53d6\u6d88\u52fe\u9009\u6240\u6709\u4e0b\u7ea7"),
    DELETE_DIRECT_CHILDREN("delete_direct_children", "\u53d6\u6d88\u52fe\u9009\u76f4\u63a5\u4e0b\u7ea7"),
    DELETE_DIRECT_CHILDREN_WITH_SHIFT("delete_direct_children_with_shift", "Shift\u53d6\u6d88\u52fe\u9009\u76f4\u63a5\u4e0b\u7ea7"),
    DELETE_NODES("delete_nodes", "\u53d6\u6d88\u52fe\u9009\u8282\u70b9");

    public String i18nKey;
    public String title;

    private UnitSelectorI18nKeys(String i18nKey, String title) {
        this.i18nKey = i18nKey;
        this.title = title;
    }

    public static String getI18nKey(String name) {
        return UnitSelectorI18nKeys.valueOf((String)name).i18nKey;
    }

    public static String getTitle(String name) {
        return UnitSelectorI18nKeys.valueOf((String)name).title;
    }
}

