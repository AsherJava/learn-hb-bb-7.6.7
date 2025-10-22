/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.i18n.CrudMessageSource;
import com.jiuqi.nr.datacrud.impl.check.FieldDynamicNodeProvider;
import com.jiuqi.nr.datacrud.impl.check.FieldValidationContext;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class BaseTypeParseStrategy
implements TypeParseStrategy {
    private static final Logger log = LoggerFactory.getLogger(BaseTypeParseStrategy.class);
    protected DataServiceLogger crudLogger;
    protected CrudMessageSource messageSource;
    private ReportFormulaParser validationRuleParser;

    public BaseTypeParseStrategy setCrudLogger(DataServiceLogger crudLogger) {
        this.crudLogger = crudLogger;
        return this;
    }

    public BaseTypeParseStrategy setMessageSource(CrudMessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    protected ReturnRes checkNonNull(DataLinkDefine link, DataField field, Object data) {
        ReturnRes build;
        if (link != null) {
            if (Boolean.FALSE.equals(link.getAllowNullAble())) {
                if (field.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                    return ReturnResInstance.OK_INSTANCE;
                }
                ReturnRes build2 = this.checkNonNull(field, data);
                if (build2 != null) {
                    return build2;
                }
            }
        } else if (field != null && !field.isNullable() && field.getDataFieldKind() != DataFieldKind.TABLE_FIELD_DIM && (build = this.checkNonNull(field, data)) != null) {
            return build;
        }
        return ReturnResInstance.OK_INSTANCE;
    }

    private ReturnRes checkNonNull(DataField field, Object data) {
        if (data == null || !StringUtils.hasLength(data.toString()) || "null".equals(data.toString())) {
            String message = this.messageSource.getMessage("data.not.null", (Object)field.getTitle());
            this.crudLogger.dataCheckFail(message);
            return ReturnRes.build(1201, message);
        }
        return null;
    }

    protected ReturnRes checkNonNull(DataLinkDefine link, IFMDMAttribute ifmdmAttribute, Object data) {
        if (link != null && Boolean.FALSE.equals(link.getAllowNullAble()) && (data == null || !StringUtils.hasLength(data.toString()) || "null".equals(data.toString()))) {
            String message = this.messageSource.getMessage("data.not.null", (Object)ifmdmAttribute.getTitle());
            this.crudLogger.dataCheckFail(message);
            return ReturnRes.build(1201, message);
        }
        if (!(ifmdmAttribute == null || ifmdmAttribute.isNullAble() || data != null && StringUtils.hasLength(data.toString()) && !"null".equals(data.toString()))) {
            String message = this.messageSource.getMessage("data.not.null", (Object)ifmdmAttribute.getTitle());
            this.crudLogger.dataCheckFail(message);
            return ReturnRes.build(1201, message);
        }
        return ReturnResInstance.OK_INSTANCE;
    }

    protected ReturnRes validationRule(DataField field, Object data) {
        if (field == null) {
            return ReturnResInstance.OK_INSTANCE;
        }
        List validationRules = field.getValidationRules();
        if (validationRules == null || validationRules.isEmpty()) {
            return ReturnResInstance.OK_INSTANCE;
        }
        ArrayList<String> messages = new ArrayList<String>();
        for (ValidationRule validationRule : validationRules) {
            if (validationRule.getCompareType() == CompareType.NOTNULL) continue;
            String verification = validationRule.getVerification();
            try {
                if (this.validationRuleParser == null) {
                    this.validationRuleParser = ReportFormulaParser.getInstance();
                    this.validationRuleParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new FieldDynamicNodeProvider());
                }
                FieldValidationContext validationContext = new FieldValidationContext();
                validationContext.setData(data);
                validationContext.setField(field);
                IExpression expression = this.validationRuleParser.parseCond(verification, (IContext)validationContext);
                boolean check = expression.judge((IContext)validationContext);
                if (check) continue;
                String message = validationRule.getMessage();
                messages.add(message);
            }
            catch (Exception e) {
                log.warn("\u6307\u6807\u6570\u636e\u6821\u9a8c\u5931\u8d25,\u6821\u9a8c\u4e0d\u751f\u6548 {}", (Object)verification, (Object)e);
            }
        }
        if (!messages.isEmpty()) {
            this.crudLogger.dataCheckFail((String)messages.get(0));
            ReturnRes build = ReturnRes.build(1204, (String)messages.get(0));
            build.setMessages(messages);
            return build;
        }
        return ReturnResInstance.OK_INSTANCE;
    }

    protected ParseReturnRes okValue(Object value) {
        ParseReturnRes build = ParseReturnRes.build(0);
        if (value instanceof AbstractData) {
            build.setAbstractData((AbstractData)value);
        } else {
            build.setAbstractData(AbstractData.valueOf((Object)value, (int)this.getDataType()));
        }
        return build;
    }

    protected ParseReturnRes errRes(Object data, String message) {
        ParseReturnRes res = new ParseReturnRes();
        res.setCode(1211);
        res.setMessage(message);
        res.setData(data.toString());
        return res;
    }

    protected abstract int getDataType();
}

