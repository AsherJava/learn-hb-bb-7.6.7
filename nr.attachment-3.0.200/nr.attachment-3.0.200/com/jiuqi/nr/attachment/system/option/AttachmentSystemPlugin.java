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
package com.jiuqi.nr.attachment.system.option;

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
public class AttachmentSystemPlugin
implements ISystemOptionDeclare {
    public static final String PLUGINNAME = "attachment-config-plugin";
    @Autowired
    private SystemOptionOperator systemOptionOperator;
    public static final String ID = "attachmentManagement";
    public static final String ATTACHMENT_OPEN_FILEPOOL = "OPEN_FILEPOOL";
    public static final String ATTACHMENT_OPEN_CATEGORY = "ATTACHMENT_OPEN_CATEGORY";
    public static final String IS_PREVIEW_OPTIONS = "IS_PREVIEW_OPTIONS";
    public static final String MAX_PREVIEW_FILE = "MAX_PREVIEW_FILE";
    public static final String MAX_QUEUING_PREVIEW = "MAX_QUEUING_PREVIEW";
    public static final String QUEU_PREVIEW_TIP = "QUEU_PREVIEW_TIP";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u9644\u4ef6\u7ba1\u7406";
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
                return AttachmentSystemPlugin.ATTACHMENT_OPEN_FILEPOOL;
            }

            public String getTitle() {
                return "\u5f00\u542f\u9644\u4ef6\u6c60";
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
                return AttachmentSystemPlugin.ATTACHMENT_OPEN_CATEGORY;
            }

            public String getTitle() {
                return "\u5f00\u542f\u9644\u4ef6\u7c7b\u522b";
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
                return AttachmentSystemPlugin.IS_PREVIEW_OPTIONS;
            }

            public String getTitle() {
                return "\u542f\u7528\u9644\u4ef6\u5728\u7ebf\u9884\u89c8";
            }

            public String getDefaultValue() {
                return "1";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AttachmentSystemPlugin.MAX_PREVIEW_FILE;
            }

            public String getTitle() {
                return "\u751f\u6210\u9884\u89c8\u6587\u4ef6\u6700\u5927\u5e76\u53d1\u6570\uff08\u91cd\u542f\u540e\u751f\u6548)";
            }

            public String getDefaultValue() {
                return "2";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AttachmentSystemPlugin.MAX_QUEUING_PREVIEW;
            }

            public String getTitle() {
                return "\u751f\u6210\u9884\u89c8\u6587\u4ef6\u6700\u5927\u6392\u961f\u6570";
            }

            public String getDefaultValue() {
                return "3";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }
        });
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return AttachmentSystemPlugin.QUEU_PREVIEW_TIP;
            }

            public String getTitle() {
                return "\u9644\u4ef6\u9884\u89c8\u961f\u5217\u6392\u6ee1\u540e\u7684\u63d0\u793a\u4fe1\u606f";
            }

            public String getDefaultValue() {
                return "\u7cfb\u7edf\u7e41\u5fd9,\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

