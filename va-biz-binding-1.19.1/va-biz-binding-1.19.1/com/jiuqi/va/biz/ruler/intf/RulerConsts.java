/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.ruler.common.consts.FieldPropertyTypeEnum;

public class RulerConsts {
    public static final String RULER_TYPE_FORMULA = "Formula";
    public static final String RULER_TYPE_FIXED = "Fixed";
    public static final String FORMULA_OBJECT_TYPE_TABLE = "table";
    public static final String FORMULA_OBJECT_TYPE_FIELD = "field";
    public static final String FORMULA_OBJECT_TYPE_CONTROL = "control";
    public static final String FORMULA_OBJECT_TYPE_ACTION = "action";
    public static final String FORMULA_OBJECT_TYPE_BACK_WRITE = "backWrite";
    public static final String FORMULA_OBJECT_TYPE_EVENT = "event";
    public static final String FORMULA_OBJECT_TYPE_WIZARD = "wizard";
    public static final String FORMULA_OBJECT_PROP_TABLE_READONLY = "readonly";
    public static final String FORMULA_OBJECT_PROP_TABLE_REQUIRED = "required";
    public static final String FORMULA_OBJECT_PROP_TABLE_HIDE = "hide";
    public static final String FORMULA_OBJECT_PROP_TABLE_SINGLE = "single";
    public static final String FORMULA_OBJECT_PROP_TABLE_FIXED = "fixed";
    public static final String FORMULA_OBJECT_PROP_TABLE_ADDROW = "addRow";
    public static final String FORMULA_OBJECT_PROP_TABLE_DELROW = "delRow";
    public static final String FORMULA_OBJECT_PROP_WIZARD_JUMP = "jump";
    public static final String FORMULA_OBJECT_PROP_TABLE_CONDITION = "condition";
    public static final String FORMULA_OBJECT_PROP_TABLE_INITROWS = "initRows";
    public static final String FORMULA_OBJECT_PROP_TABLE_ENABLEFILTER = "enableFilter";
    public static final String FORMULA_OBJECT_PROP_TABLE_FILTERCONTIDION = "filterCondition";
    public static final String FORMULA_OBJECT_PROP_EVENT_AFTER_SET_VALUE = "AfterSetValue";
    public static final String FORMULA_OBJECT_PROP_EVENT_AFTER_DEL_ROW = "AfterDelRow";
    public static final String FORMULA_OBJECT_PROP_EVENT_AFTER_ADD_ROW = "AfterAddRow";
    public static final String FORMULA_OBJECT_PROP_EVENT_BEFORE_SAVE = "BeforeSave";
    public static final String FORMULA_OBJECT_PROP_EVENT_BEFORE_DELETE = "BeforeDelete";
    public static final String FORMULA_OBJECT_PROP_FIELD_READONLY = FieldPropertyTypeEnum.readonly.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_REQUIRED = FieldPropertyTypeEnum.required.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_HIDDEN = FieldPropertyTypeEnum.hidden.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_FILTER = FieldPropertyTypeEnum.filter.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_GROUPFILTER = FieldPropertyTypeEnum.groupFilter.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_VALIDATE = FieldPropertyTypeEnum.inputCheck.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_VALIDATE2 = FieldPropertyTypeEnum.range.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_INPUT = FieldPropertyTypeEnum.charSet.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_VALUE = FieldPropertyTypeEnum.calculation.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_MASK = FieldPropertyTypeEnum.mask.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_BACKGROUNDCOLOR = FieldPropertyTypeEnum.backgroundColor.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_SHOWBACKGROUNDCOLORONVIEW = FieldPropertyTypeEnum.showBackgroundColorOnView.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_ORGFILTER = FieldPropertyTypeEnum.orgFilter.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_SSOPARAM = FieldPropertyTypeEnum.ssoParam.name();
    public static final String FORMULA_OBJECT_PROP_FIELD_ISDISZERO = FieldPropertyTypeEnum.isDisZero.name();
    public static final String FORMULA_OBJECT_PROP_CONTROL_HIDDEN = "hidden";
    public static final String FORMULA_OBJECT_PROP_CONTROL_ENABLE = "enable";
    public static final String FORMULA_OBJECT_PROP_CONTROL_ACTION = "action";
    public static final String FORMULA_OBJECT_PROP_ACTION_BEFORE = "before";
    public static final String[] FRONT_TABLE_PROPS = new String[]{"readonly", "required", "hide", "single", "fixed", "addRow", "delRow", "condition"};
    public static final String[] FRONT_FIELD_PROPS = new String[]{FORMULA_OBJECT_PROP_FIELD_READONLY, FORMULA_OBJECT_PROP_FIELD_REQUIRED, FORMULA_OBJECT_PROP_FIELD_HIDDEN, FORMULA_OBJECT_PROP_FIELD_FILTER, FORMULA_OBJECT_PROP_FIELD_GROUPFILTER, FORMULA_OBJECT_PROP_FIELD_VALIDATE, FORMULA_OBJECT_PROP_FIELD_VALIDATE2, FORMULA_OBJECT_PROP_FIELD_INPUT, FORMULA_OBJECT_PROP_FIELD_MASK, FORMULA_OBJECT_PROP_FIELD_BACKGROUNDCOLOR, FORMULA_OBJECT_PROP_FIELD_SHOWBACKGROUNDCOLORONVIEW, FORMULA_OBJECT_PROP_FIELD_ORGFILTER, FORMULA_OBJECT_PROP_FIELD_SSOPARAM, FORMULA_OBJECT_PROP_FIELD_ISDISZERO};
    public static final String[] FRONT_CONTROL_PROPS = new String[]{"hidden", "enable"};
    public static final String[] FRONT_WIZRAD_PROPS = new String[]{"jump"};
    public static final String[] FRONT_ATTACHMENT_PROPS = new String[]{"hidden", "required", "delete", "upload", "canDownload", "canPreview"};
}

