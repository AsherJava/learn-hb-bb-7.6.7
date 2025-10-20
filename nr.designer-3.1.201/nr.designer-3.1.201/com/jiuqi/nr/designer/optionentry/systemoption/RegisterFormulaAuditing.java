/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.designer.optionentry.systemoption;

import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.designer.optionentry.systemoption.FormulaAuditingOptionOperator;
import com.jiuqi.nr.designer.optionentry.systemoption.InnerAuditingType;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="audit-type")
public class RegisterFormulaAuditing
implements ISystemOptionDeclare {
    private static final Logger log = LoggerFactory.getLogger(RegisterFormulaAuditing.class);
    public static final String AUDITINGTYPE = "auditing-type-id";
    public static final String FORMULAAUDITING = "FORMULAAUDITING";
    @Autowired
    private FormulaAuditingOptionOperator formulaAuditingOptionOperator;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    public String getId() {
        return AUDITINGTYPE;
    }

    public String getTitle() {
        return "\u5ba1\u6838\u7c7b\u578b";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public String getPluginName() {
        return "auditingtype-plugin";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> items = new ArrayList<ISystemOptionItem>();
        items.add(new ISystemOptionItem(){

            public String getId() {
                return RegisterFormulaAuditing.FORMULAAUDITING;
            }

            public String getTitle() {
                return "\u8bbe\u7f6e\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b";
            }

            public String getDefaultValue() {
                List queryAllAuditType = null;
                try {
                    queryAllAuditType = RegisterFormulaAuditing.this.auditTypeDefineService.queryAllAuditType();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                InnerAuditingType auditType = new InnerAuditingType();
                auditType.setAddress("/auditing");
                auditType.setObject(queryAllAuditType);
                auditType.setSystemId(RegisterFormulaAuditing.AUDITINGTYPE);
                auditType.setOptionId(RegisterFormulaAuditing.FORMULAAUDITING);
                return JacksonUtils.objectToJson((Object)auditType);
            }
        });
        return items;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.formulaAuditingOptionOperator;
    }
}

