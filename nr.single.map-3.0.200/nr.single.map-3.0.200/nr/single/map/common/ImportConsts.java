/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.Consts$EnumField
 */
package nr.single.map.common;

import com.jiuqi.np.definition.common.Consts;
import java.util.List;

public class ImportConsts {
    public static final String ENUM_KEY_FIELD = Consts.EnumField.ENUM_FIELD_KEY.code;
    public static final String ENUM_CODE_FIELD = Consts.EnumField.ENUM_FIELD_CODE.code;
    public static final String ENUM_TITLE_FIELD = Consts.EnumField.ENUM_FIELD_TITLE.code;
    public static final String ENUM_PARENT_FIELD = Consts.EnumField.ENUM_FIELD_PARENT.code;
    public static final String ENUM_ORDER_FIELD = Consts.EnumField.ENUM_FIELD_ORDER.code;
    public static final String ENTITY_KEY_FIELD = Consts.EntityField.ENTITY_FIELD_KEY.fieldKey;
    public static final String ENTITY_CODE_FIELD = Consts.EntityField.ENTITY_FIELD_CODE.fieldKey;
    public static final String ENTITY_TITLE_FIELD = Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey;
    public static final String ENTITY_PARENT_FIELD = Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey;
    public static final String ENTITY_SORTORDER = Consts.EntityField.ENTITY_FIELD_ORDINAL.fieldKey;
    public static final String ENTITY_DESCRIPTION = "DESCRIPTION";
    public static final String ENTITY_PERIOD_NB_CODE = "NBCODE";
    public static final int IMPORT_BATCH_COUNT = 2000;
    public static final String DEFAULTFORM_SCHEME_NAME = "\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848";
    public static final String DEFAULTFORM_GROUP_NAME = "\u9ed8\u8ba4\u8868\u5355\u5206\u7ec4";
    public static final String OTHERFORM_GROUP_NAME = "\u5176\u4ed6";
    public static final String DEFAULTFORM_DEFINE_NAME = "\u5de5\u4f5c\u88681";
    public static final String ENTITY_PARENTCODE = "PARENTCODE";
    public static final String ENTITY_ORGCODE = "ORGCODE";
    public static final int PARAFILE_MAXSIZE = 10245760;

    public static final String getCodeStrings(List<String> list) {
        String result = "";
        for (int i = 0; i < list.size(); ++i) {
            result = i == 0 ? list.get(i).toString() : result + ";" + list.get(i).toString();
        }
        return result;
    }
}

