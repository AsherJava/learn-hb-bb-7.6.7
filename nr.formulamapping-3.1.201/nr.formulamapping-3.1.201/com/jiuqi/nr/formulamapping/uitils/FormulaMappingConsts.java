/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulamapping.uitils;

public class FormulaMappingConsts {
    public static final String REST_PREFIX = "api/v1/formula/mapping/";
    public static final String REST_QUERY_TASKS = "\u67e5\u8be2\u8fd0\u884c\u671f\u7684\u5efa\u6a21\u4efb\u52a1";
    public static final String REST_QUERY_TASKS_URL = "query/taskDefines";
    public static final String REST_QUERY_FORMSCHEME_BY_TASK = "\u67e5\u8be2\u8fd0\u884c\u671f\u7684\u5efa\u6a21\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848";
    public static final String REST_QUERY_FORMSCHEME_BY_TASK_URL = "query/formSchemeByTask";
    public static final String REST_QUERY_FORMULASCHEME_BY_FORMSCHEME = "\u67e5\u8be2\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u65b9\u6848";
    public static final String REST_QUERY_FORMULASCHEME_BY_FORMSCHEME_URL = "query/formulaScheme";
    public static final String REST_QUERY_FORMSCHEME_AND_TASKKEYS = "\u67e5\u8be2\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u4efb\u52a1key\u548c\u62a5\u8868\u65b9\u6848key";
    public static final String REST_QUERY_FORMSCHEME_AND_TASKKEYS_URL = "query/taskAndFormSchemeKeys";
    public static final String REST_QUERY_FORMULASCHEME_MAPPING = "\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULASCHEME_MAPPING_URL = "query/formula_mapping_scheme";
    public static final String REST_ADD_FORMULASCHEME_MAPPING = "\u65b0\u589e\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e";
    public static final String REST_ADD_FORMULASCHEME_MAPPING_URL = "insert/formula_mapping_scheme";
    public static final String REST_UPDATE_FORMULASCHEME_MAPPING = "\u4fee\u6539\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e";
    public static final String REST_UPDATE_FORMULASCHEME_MAPPING_URL = "update/formula_mapping_scheme";
    public static final String REST_DELETE_FORMULASCHEME_MAPPING = "\u5220\u9664\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e";
    public static final String REST_DELETE_FORMULASCHEME_MAPPING_URL = "delete/formula_mapping_scheme";
    public static final String REST_INITTREE_FORMULA_MAPPING = "\u521d\u59cb\u5316\u6811";
    public static final String REST_INITTREE_FORMULA_MAPPING_URL = "init_tree/formula_mapping/{formulaSchemeKey}/{nodeKey}";
    public static final String REST_QUERY_FORMULAS = "\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULAS_URL = "query/formulas";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_FORMULASCHEME = "\u6839\u636e\u516c\u5f0f\u65b9\u6848\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_FORMULASCHEME_URL = "query/formula_mapping_by_formulaScheme";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_FORM = "\u6839\u636e\u6307\u5b9a\u62a5\u8868\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_FORM_URL = "query/formula_mapping_by_form";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_PAGE = "\u516c\u5f0f\u65b9\u6848/\u62a5\u8868\u5206\u7ec4/\u62a5\u8868\u5206\u9875\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULA_MAPPING_BY_PAGE_URL = "query/formula_mapping_by_page";
    public static final String REST_QUERY_FORMULA_MAPPING = "\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_QUERY_FORMULA_MAPPING_URL = "query/formula_mapping";
    public static final String REST_ADD_FORMULA_MAPPING = "\u65b0\u589e\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_ADD_FORMULA_MAPPING_URL = "insert/formula_mapping";
    public static final String REST_UPDATE_FORMULA_MAPPING = "\u4fee\u6539\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_UPDATE_FORMULA_MAPPING_URL = "update/formula_mapping";
    public static final String REST_CLEAR_FORMULA_MAPPINGS = "\u6279\u91cf\u4fee\u6539\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_CLEAR_FORMULA_MAPPINGS_URL = "clear/formula_mappings";
    public static final String REST_DELETE_FORMULA_MAPPING = "\u5220\u9664\u516c\u5f0f\u6620\u5c04\u6570\u636e";
    public static final String REST_DELETE_FORMULA_MAPPING_URL = "delete/formula_mapping";
    public static final String REST_AUTO_FORMULA_MAPPING = "\u81ea\u52a8\u516c\u5f0f\u6620\u5c04";
    public static final String REST_AUTO_FORMULA_MAPPING_URL = "auto/formula_mapping";
    public static final String REST_IMPORT_FORMULA_MAPPING = "\u5bfc\u5165\u516c\u5f0f\u6620\u5c04";
    public static final String REST_IMPORT_FORMULA_MAPPING_URL = "import/formula_mapping";
    public static final String REST_EXPORT_FORMULA_MAPPING = "\u5bfc\u51fa\u516c\u5f0f\u6620\u5c04";
    public static final String REST_EXPORT_FORMULA_MAPPING_URL = "export/formula_mapping";
}

