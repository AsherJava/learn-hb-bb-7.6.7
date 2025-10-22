/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class I18nReadWriteAccess
implements I18NResource {
    private static final long serialVersionUID = 1L;
    public static final String ANALYSIS_REPORT_NO_EDIT = "ANALYSIS_REPORT_NO_EDIT";
    public static final String VERSION_DATA_CANNOT_MODIFIED = "VERSION_DATA_CANNOT_MODIFIED";
    public static final String DECIMAL_DIGITS_CANNOT_WRITTEN = "DECIMAL_DIGITS_CANNOT_WRITTEN";
    public static final String UNIT_NULL_DATA_CANNOT_READ = "UNIT_NULL_DATA_CANNOT_READ";
    public static final String NULL_DIMENSION_CANNOT_READ = "NULL_DIMENSION_CANNOT_READ";
    public static final String UNIT_TREE_NO_ADMIN = "UNIT_TREE_NO_ADMIN";
    public static final String NODE_NO_ADMIN_FMDM_NO_EDIT = "NODE_NO_ADMIN_FMDM_NO_EDIT";
    public static final String NO_DEFAULT_MEASURE_NO_WRITTEN = "NO_DEFAULT_MEASURE_NO_WRITTEN";
    public static final String SUM_UNIT_CANNOT_WRITTEN = "SUM_UNIT_CANNOT_WRITTEN";
    public static final String SUM_DATA_CANNOT_WRITTEN = "SUM_DATA_CANNOT_WRITTEN";
    public static final String TASK_DEFEND_CANNOT_WRITTEN = "TASK_DEFEND_CANNOT_WRITTEN";

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u8bfb\u5199\u6743\u9650";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            resourceObjects.add(new I18NResourceItem(ANALYSIS_REPORT_NO_EDIT, "\u5206\u6790\u62a5\u544a\u8868\u5355\u4e0d\u53ef\u7f16\u8f91"));
            resourceObjects.add(new I18NResourceItem(VERSION_DATA_CANNOT_MODIFIED, "\u5feb\u7167\u6570\u636e\uff0c\u65e0\u6cd5\u4fee\u6539"));
            resourceObjects.add(new I18NResourceItem(DECIMAL_DIGITS_CANNOT_WRITTEN, "\u5f53\u524d\u5c0f\u6570\u4f4d\u6570\u4e0d\u662f\u9ed8\u8ba4\u503c\uff0c\u4e0d\u53ef\u5199"));
            resourceObjects.add(new I18NResourceItem(UNIT_NULL_DATA_CANNOT_READ, "\u5355\u4f4d\u6570\u636e\u4e3a\u7a7a\u4e0d\u53ef\u67e5\u770b"));
            resourceObjects.add(new I18NResourceItem(NULL_DIMENSION_CANNOT_READ, "\u7ef4\u5ea6\u4e3a\u7a7a\u4e0d\u53ef\u67e5\u770b"));
            resourceObjects.add(new I18NResourceItem(UNIT_TREE_NO_ADMIN, "\u5355\u4f4d\u6811\u6ca1\u6709\u7ba1\u7406\u6743\u9650"));
            resourceObjects.add(new I18NResourceItem(NODE_NO_ADMIN_FMDM_NO_EDIT, "\u8be5\u8282\u70b9\u6ca1\u6709\u7ba1\u7406\u6743\u9650\uff0c\u5c01\u9762\u4ee3\u7801\u4e0d\u53ef\u7f16\u8f91"));
            resourceObjects.add(new I18NResourceItem(NO_DEFAULT_MEASURE_NO_WRITTEN, "\u5f53\u524d\u91cf\u7eb2\u4e0d\u662f\u9ed8\u8ba4\uff0c\u4e0d\u53ef\u5199"));
            resourceObjects.add(new I18NResourceItem(SUM_UNIT_CANNOT_WRITTEN, "\u6c47\u603b\u5355\u4f4d\u4e0d\u53ef\u5199"));
            resourceObjects.add(new I18NResourceItem(SUM_DATA_CANNOT_WRITTEN, "\u6c47\u603b\u6570\u636e\uff0c\u4e0d\u53ef\u5199"));
            resourceObjects.add(new I18NResourceItem(TASK_DEFEND_CANNOT_WRITTEN, "\u4efb\u52a1\u7ef4\u62a4\u4e2d\uff0c\u4e0d\u53ef\u5199"));
        }
        return resourceObjects;
    }
}

