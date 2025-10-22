/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.annotation.system.option;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnnotationSystemPlugin
implements ISystemOptionDeclare {
    public static final String PLUGINNAME = "annotation-config-plugin";
    @Autowired
    private SystemOptionOperator systemOptionOperator;
    public static final String ID = "annotationManagement";
    public static final String OPEN_ANNOTATION_TYPE = "OPEN_ANNOTATION_TYPE";
    public static final String ANNOTATION_ONLY_LEAF_NODE = "ANNOTATION_ONLY_LEAF_NODE";
    public static final String BASE_DATA_KEY = "BASE_DATA_KEY";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u6279\u6ce8\u7ba1\u7406";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return PLUGINNAME;
    }

    public boolean disableReset() {
        return false;
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AnnotationSystemPlugin.OPEN_ANNOTATION_TYPE;
            }

            public String getTitle() {
                return "\u5f00\u542f\u6279\u6ce8\u7c7b\u578b";
            }

            public String getDefaultValue() {
                return "0";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AnnotationSystemPlugin.ANNOTATION_ONLY_LEAF_NODE;
            }

            public String getTitle() {
                return "\u53ea\u5141\u8bb8\u9009\u62e9\u53f6\u5b50\u8282\u70b9";
            }

            public String getDefaultValue() {
                return "0";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AnnotationSystemPlugin.BASE_DATA_KEY;
            }

            public String getTitle() {
                return "";
            }

            public String getDefaultValue() {
                return "";
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

