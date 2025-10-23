/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.param.transfer.Utils;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaDTO {
    private String formulaSchemeKey;
    private String formKey;
    private String code;
    private String expression;
    private String dataItems;
    private String adjustItems;
    private String description;
    private boolean autoExecute;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;
    private int checkType;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String balanceZBExp;
    private int syntax;
    private String ordinal;
    private String languageInfo;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDataItems() {
        return this.dataItems;
    }

    public void setDataItems(String dataItems) {
        this.dataItems = dataItems;
    }

    public String getAdjustItems() {
        return this.adjustItems;
    }

    public void setAdjustItems(String adjustItems) {
        this.adjustItems = adjustItems;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAutoExecute() {
        return this.autoExecute;
    }

    public void setAutoExecute(boolean autoExecute) {
        this.autoExecute = autoExecute;
    }

    public boolean isUseCalculate() {
        return this.useCalculate;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public boolean isUseCheck() {
        return this.useCheck;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public boolean isUseBalance() {
        return this.useBalance;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public int getSyntax() {
        return this.syntax;
    }

    public void setSyntax(int syntax) {
        this.syntax = syntax;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getLanguageInfo() {
        return this.languageInfo;
    }

    public void setLanguageInfo(String languageInfo) {
        this.languageInfo = languageInfo;
    }

    public void value2Define(DesignFormulaDefine formulaDefine) {
        formulaDefine.setKey(this.getKey());
        formulaDefine.setOrder(this.getOrder());
        formulaDefine.setVersion(this.getVersion());
        formulaDefine.setTitle(this.getTitle());
        formulaDefine.setUpdateTime(this.getUpdateTime());
        formulaDefine.setFormulaSchemeKey(this.getFormulaSchemeKey());
        formulaDefine.setFormKey(this.getFormKey());
        formulaDefine.setCode(this.getCode());
        formulaDefine.setExpression(this.getExpression());
        formulaDefine.setDataItems(this.getDataItems());
        formulaDefine.setAdjustItems(this.getAdjustItems());
        formulaDefine.setDescription(this.getDescription());
        formulaDefine.setIsAutoExecute(this.isAutoExecute());
        formulaDefine.setUseCalculate(this.isUseCalculate());
        formulaDefine.setUseCheck(this.isUseCheck());
        formulaDefine.setUseBalance(this.isUseBalance());
        formulaDefine.setCheckType(this.getCheckType());
        formulaDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        formulaDefine.setBalanceZBExp(this.getBalanceZBExp());
        formulaDefine.setSyntax(this.getSyntax() != 0 ? this.getSyntax() : FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
        if (StringUtils.hasLength(this.getOrdinal())) {
            formulaDefine.setOrdinal(new BigDecimal(this.getOrdinal()));
        } else if (StringUtils.hasLength(this.getOrder())) {
            formulaDefine.setOrdinal(new BigDecimal(Utils.stringToOrder(this.getOrder())));
        }
    }

    public static FormulaDTO valueOf(FormulaDefine formulaDefine) {
        if (formulaDefine == null) {
            return null;
        }
        FormulaDTO formulaDTO = new FormulaDTO();
        formulaDTO.setKey(formulaDefine.getKey());
        formulaDTO.setOrder(formulaDefine.getOrder());
        formulaDTO.setVersion(formulaDefine.getVersion());
        formulaDTO.setTitle(formulaDefine.getTitle());
        formulaDTO.setUpdateTime(formulaDefine.getUpdateTime());
        formulaDTO.setFormulaSchemeKey(formulaDefine.getFormulaSchemeKey());
        formulaDTO.setFormKey(formulaDefine.getFormKey());
        formulaDTO.setCode(formulaDefine.getCode());
        formulaDTO.setExpression(formulaDefine.getExpression());
        formulaDTO.setDataItems(formulaDefine.getDataItems());
        formulaDTO.setAdjustItems(formulaDefine.getAdjustItems());
        formulaDTO.setDescription(formulaDefine.getDescription());
        formulaDTO.setAutoExecute(formulaDefine.getIsAutoExecute());
        formulaDTO.setUseCalculate(formulaDefine.getUseCalculate());
        formulaDTO.setUseCheck(formulaDefine.getUseCheck());
        formulaDTO.setUseBalance(formulaDefine.getUseBalance());
        formulaDTO.setCheckType(formulaDefine.getCheckType());
        formulaDTO.setOwnerLevelAndId(formulaDefine.getOwnerLevelAndId());
        formulaDTO.setBalanceZBExp(formulaDefine.getBalanceZBExp());
        formulaDTO.setSyntax(formulaDefine.getSyntax());
        if (formulaDefine.getOrdinal() != null) {
            formulaDTO.setOrdinal(formulaDefine.getOrdinal().toString());
        }
        return formulaDTO;
    }
}

