/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.dataSnapshot.system.option;

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
public class SnapshotSystemPlugin
implements ISystemOptionDeclare {
    @Autowired
    private SystemOptionOperator systemOptionOperator;
    public static final String ID = "snapshotManagement";
    public static final String SNAPSHOT_SELECT_FIELD = "SNAPSHOT_SELECT_FIELD";
    public static final String SNAPSHOT_SET_RATE = "SNAPSHOT_SET_RATE";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5feb\u7167";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public int getOrdinal() {
        return 1;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return SnapshotSystemPlugin.SNAPSHOT_SELECT_FIELD;
            }

            public String getTitle() {
                return "\u5141\u8bb8\u9009\u62e9\u5bf9\u6bd4\u6307\u6807";
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
                return SnapshotSystemPlugin.SNAPSHOT_SET_RATE;
            }

            public String getTitle() {
                return "\u5141\u8bb8\u8bbe\u7f6e\u504f\u5dee\u7387";
            }

            public String getDefaultValue() {
                return "1";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.TRUE_FALSE;
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

