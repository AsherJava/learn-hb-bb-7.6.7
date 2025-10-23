/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 */
package com.jiuqi.nr.fmdm.system;

import com.jiuqi.nr.fmdm.system.IFMDMAuditOptions;
import com.jiuqi.nr.fmdm.system.dto.FMDMOptionsDTO;
import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractCheckBoxOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FMDMOptions
extends BaseNormalOptionDeclare {
    public static final String OPTION_ID = "fmdm_group_id";
    public static final String OPTION_TITLE = "\u5c01\u9762\u4ee3\u7801";
    @Autowired
    private IFMDMAuditOptions auditOptions;
    public static final String OPTION_SAVE_CHECK = "OPTION_SAVE_CHECK";

    public String getId() {
        return OPTION_ID;
    }

    public String getTitle() {
        return OPTION_TITLE;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)new AbstractCheckBoxOption(){

            public String getId() {
                return FMDMOptions.OPTION_SAVE_CHECK;
            }

            public String getTitle() {
                return "\u4fdd\u5b58\u524d\u5ba1\u6838\u7684\u516c\u5f0f\u7c7b\u578b";
            }

            public String getDefaultValue() {
                return "[]";
            }

            public List<ISystemOptionalValue> getOptionalValues() {
                List<FMDMOptionsDTO> allAuditOptions = FMDMOptions.this.auditOptions.getAllAuditOptions();
                ArrayList<ISystemOptionalValue> optionItems = new ArrayList<ISystemOptionalValue>(allAuditOptions.size());
                for (final FMDMOptionsDTO auditOption : allAuditOptions) {
                    optionItems.add(new ISystemOptionalValue(){

                        public String getTitle() {
                            return auditOption.getTitle();
                        }

                        public String getValue() {
                            return String.valueOf(auditOption.getKey());
                        }
                    });
                }
                return optionItems;
            }
        });
        return optionItems;
    }
}

