/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.util.StringUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULA_DES")
public class DesignFormulaDefineImpl
implements DesignFormulaDefine {
    private static final int MAX_EXPRESSIONLENGTH = 1000;
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fl_scheme_key", isPk=false)
    private String formulaSchemeKey;
    @DBAnno.DBField(dbField="fl_form_key", isPk=false)
    private String formKey;
    @DBAnno.DBField(dbField="fl_code")
    private String code;
    @DBAnno.DBField(dbField="fl_expression")
    private String expression;
    @DBAnno.DBField(dbField="fl_data_Items")
    private String dataItems;
    @Deprecated
    @DBAnno.DBField(dbField="fl_adjust_Item")
    private String adjustItems;
    @DBAnno.DBField(dbField="fl_desc")
    private String description;
    @DBAnno.DBField(dbField="fl_ia_auto_execute", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isAutoExecute;
    @DBAnno.DBField(dbField="fl_use_calculate", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean useCalculate;
    @DBAnno.DBField(dbField="fl_use_check", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean useCheck;
    @DBAnno.DBField(dbField="fl_use_balance", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean useBalance;
    @DBAnno.DBField(dbField="fl_check_type")
    private int checkType;
    @DBAnno.DBField(dbField="fl_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fl_title")
    private String title;
    @DBAnno.DBField(dbField="fl_order")
    private String order = OrderGenerator.newOrder();
    @DBAnno.DBField(dbField="fl_ordinal", isOrder=true)
    private BigDecimal ordinal;
    @DBAnno.DBField(dbField="fl_version")
    private String version;
    @DBAnno.DBField(dbField="fl_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fl_large_expression", tranWith="transClob")
    private String largeExpression;
    @DBAnno.DBField(dbField="fl_balancefield_exp")
    private String balanceZBExp;
    @JsonIgnore
    @DBAnno.DBField(dbField="fl_private", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isPrivate;
    @JsonIgnore
    @DBAnno.DBField(dbField="fl_unit")
    private String unit = "-";
    @DBAnno.DBField(dbField="fl_syntax")
    private int syntax;

    @Override
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = StringUtils.isNotEmpty((String)unit) ? unit : "-";
    }

    @Override
    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getExpression() {
        return StringUtils.isEmpty((String)this.expression) ? this.largeExpression : this.expression;
    }

    @Override
    public String getDataItems() {
        return this.dataItems;
    }

    @Override
    public String getAdjustItems() {
        return this.adjustItems;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean getIsAutoExecute() {
        return this.isAutoExecute;
    }

    @Override
    public boolean getUseCalculate() {
        return this.useCalculate;
    }

    @Override
    public boolean getUseCheck() {
        return this.useCheck;
    }

    @Override
    public boolean getUseBalance() {
        return this.useBalance;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setExpression(String expression) {
        if (StringUtils.isEmpty((String)expression) || expression.length() < 1000) {
            this.expression = expression;
            this.largeExpression = "";
        } else {
            this.expression = "";
            this.largeExpression = expression;
        }
    }

    @Override
    public void setDataItems(String dataItems) {
        this.dataItems = dataItems;
    }

    @Override
    public void setAdjustItems(String adjustItems) {
        this.adjustItems = adjustItems;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setIsAutoExecute(boolean isAutoExecute) {
        this.isAutoExecute = isAutoExecute;
    }

    @Override
    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    @Override
    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    @Override
    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    @Override
    public int getCheckType() {
        return this.checkType;
    }

    @Override
    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    @Override
    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    @Override
    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    @Override
    public int getSyntax() {
        return this.syntax;
    }

    @Override
    public void setSyntax(int syntax) {
        this.syntax = syntax;
    }

    @Override
    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    @Override
    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

