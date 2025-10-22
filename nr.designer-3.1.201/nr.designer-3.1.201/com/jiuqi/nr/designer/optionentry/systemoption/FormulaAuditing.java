/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.configuration.common.OptionItem
 *  com.jiuqi.nr.configuration.common.SystemOptionEditMode
 *  com.jiuqi.nr.configuration.common.SystemOptionType
 *  com.jiuqi.nr.configuration.facade.SystemOptionBase
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 */
package com.jiuqi.nr.designer.optionentry.systemoption;

import com.jiuqi.nr.configuration.common.OptionItem;
import com.jiuqi.nr.configuration.common.SystemOptionEditMode;
import com.jiuqi.nr.configuration.common.SystemOptionType;
import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="sys_formulaAuditing")
public class FormulaAuditing
implements SystemOptionBase {
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    public String getKey() {
        return "FORMULAAUDITING";
    }

    public String getGroup() {
        return null;
    }

    public String getTitle() {
        return "\u5ba1\u6838";
    }

    public Object getDefaultValue() {
        return "0";
    }

    public SystemOptionType getOptionType() {
        return SystemOptionType.SYSTEM_OPTION;
    }

    public SystemOptionEditMode getOptionEditMode() {
        return SystemOptionEditMode.OPTION_EDIT_MODE_SLIDE_MENU;
    }

    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> items = new ArrayList<OptionItem>();
        items.add(new OptionItem(){

            public String getTitle() {
                return "\u8bbe\u7f6e\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b";
            }

            public Object getValue() {
                List queryAllAuditType = null;
                try {
                    queryAllAuditType = FormulaAuditing.this.auditTypeDefineService.queryAllAuditType();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                InnerAuditingType auditType = new InnerAuditingType();
                auditType.setAddress("/auditing");
                auditType.setObject(queryAllAuditType);
                return auditType;
            }
        });
        return items;
    }

    public String getRegex() {
        return null;
    }

    class InnerAuditingType {
        private String address;
        private List<AuditType> object;

        InnerAuditingType() {
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<AuditType> getObject() {
            return this.object;
        }

        public void setObject(List<AuditType> object) {
            this.object = object;
        }
    }
}

