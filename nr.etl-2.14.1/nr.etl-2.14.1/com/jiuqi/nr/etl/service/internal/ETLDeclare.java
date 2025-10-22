/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ETLDeclare
extends BaseNormalOptionDeclare {
    @Deprecated
    public static final String ETLURL = "ETLUrl";
    public static final String ETL_INFO = "etl-info";
    @Deprecated
    public static final String ETLUSERNAME = "ETLUserName";
    @Deprecated
    public static final String ETLPASSWORD = "ETLPassword";
    @Deprecated
    public static final String ID = "etl-group";
    public static final String N_ID = "etl";

    public ISystemOptionOperator getSystemOptionOperator() {
        return super.getSystemOptionOperator();
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "etl-option-plugin";
    }

    public String getId() {
        return N_ID;
    }

    public String getTitle() {
        return "ETL\u670d\u52a1";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)new AbstractInputOption(){

            public String getId() {
                return ETLDeclare.ETL_INFO;
            }

            public String getTitle() {
                return "ETL\u670d\u52a1\u4fe1\u606f";
            }
        });
        return optionItems;
    }

    public int getOrdinal() {
        return 9;
    }
}

