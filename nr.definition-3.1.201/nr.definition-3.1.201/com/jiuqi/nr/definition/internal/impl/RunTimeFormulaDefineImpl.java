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
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.util.StringUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULA")
public class RunTimeFormulaDefineImpl
implements FormulaDefine {
    private static final long serialVersionUID = -9148441421409735812L;
    public static final String TABLE_NAME = "NR_PARAM_FORMULA";
    public static final String FIELD_NAME_KEY = "FL_KEY";
    public static final String FIELD_NAME_SCHEME_KEY = "FL_SCHEME_KEY";
    @DBAnno.DBField(dbField="FL_SCHEME_KEY", isPk=false)
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
    @DBAnno.DBField(dbField="FL_KEY", isPk=true)
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
    @DBAnno.DBField(dbField="FL_UNIT")
    private String unit = "-";
    @DBAnno.DBField(dbField="fl_syntax")
    private int syntax;

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

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
    public int getSyntax() {
        return this.syntax;
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

    @Override
    public int getCheckType() {
        return this.checkType;
    }

    public int getCheckTypeDB() {
        return this.checkType;
    }

    public void setCheckTypeDB(Integer type) {
        this.checkType = type;
    }

    @Override
    public String getBalanceZBExp() {
        return this.balanceZBExp;
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
    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setDataItems(String dataItems) {
        this.dataItems = dataItems;
    }

    public void setAdjustItems(String adjustItems) {
        this.adjustItems = adjustItems;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsAutoExecute(boolean isAutoExecute) {
        this.isAutoExecute = isAutoExecute;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public void setSyntax(int syntax) {
        this.syntax = syntax;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

