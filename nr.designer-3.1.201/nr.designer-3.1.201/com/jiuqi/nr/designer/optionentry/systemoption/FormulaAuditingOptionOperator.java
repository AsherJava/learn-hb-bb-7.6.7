/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.nr.designer.optionentry.systemoption;

import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.designer.optionentry.systemoption.InnerAuditingType;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaAuditingOptionOperator
extends SystemOptionOperator {
    private static final Logger logger = LoggerFactory.getLogger(FormulaAuditingOptionOperator.class);
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        if (null == itemValues || itemValues.size() == 0) {
            return super.save(itemValues);
        }
        ResultObject res = new ResultObject();
        for (ISystemOptionItemValue itemValue : itemValues) {
            String value = itemValue.getValue();
            try {
                List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                InnerAuditingType innerAuditingType = (InnerAuditingType)JacksonUtils.jsonToObject((String)value, InnerAuditingType.class);
                if (null == innerAuditingType) {
                    res.setSuccess(false);
                    res.setMessage("\u5ba1\u6838\u7c7b\u578b\u4e3a\u7a7a");
                    return res;
                }
                List<AuditType> object = innerAuditingType.getObject();
                if (null != object) {
                    ArrayList<AuditType> canUpdate = new ArrayList<AuditType>();
                    ArrayList<AuditType> canInsert = new ArrayList<AuditType>();
                    for (AuditType auditType : object) {
                        boolean isHav = false;
                        for (AuditType dbType : auditTypes) {
                            if (!dbType.getCode().equals(auditType.getCode())) continue;
                            isHav = true;
                        }
                        if (isHav) {
                            canUpdate.add(auditType);
                            continue;
                        }
                        canInsert.add(auditType);
                    }
                    for (AuditType auditType : canUpdate) {
                        this.auditTypeDefineService.updateAuditType(auditType);
                    }
                    for (AuditType auditType : canInsert) {
                        this.auditTypeDefineService.insertAuditType(auditType);
                    }
                }
                res.setSuccess(true);
                res.setMessage("\u5bfc\u5165\u6210\u529f");
            }
            catch (Exception e) {
                logger.error("\u5ba1\u6838\u7c7b\u578b\u5bfc\u5165\u5931\u8d25", e);
                res.setSuccess(false);
                res.setMessage("\u5bfc\u5165\u5931\u8d25");
            }
        }
        return res;
    }

    public String query(String optionItemKey) {
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            InnerAuditingType auditType = new InnerAuditingType();
            auditType.setAddress("/auditing");
            auditType.setObject(auditTypes);
            auditType.setSystemId("auditing-type-id");
            auditType.setOptionId("FORMULAAUDITING");
            return JacksonUtils.objectToJson((Object)auditType);
        }
        catch (Exception e) {
            logger.error("\u5ba1\u6838\u7c7b\u578b\u5bfc\u51fa\u5931\u8d25", e);
            return "";
        }
    }

    public List<String> query(List<String> optionItemKeys) {
        return new ArrayList<String>();
    }
}

