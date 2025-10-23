/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaConditionLinkDTO;
import java.util.Date;

public class NrdxFormulaFormDTO {
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
    private String languageInfo;
    private FormulaConditionLinkDTO formulaConditionLinkDTO;

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
    }

    public static NrdxFormulaFormDTO valueOf(FormulaDefine formulaDefine) {
        if (formulaDefine == null) {
            return null;
        }
        NrdxFormulaFormDTO nrdxFormulaFormDTO = new NrdxFormulaFormDTO();
        nrdxFormulaFormDTO.setKey(formulaDefine.getKey());
        nrdxFormulaFormDTO.setOrder(formulaDefine.getOrder());
        nrdxFormulaFormDTO.setVersion(formulaDefine.getVersion());
        nrdxFormulaFormDTO.setTitle(formulaDefine.getTitle());
        nrdxFormulaFormDTO.setUpdateTime(formulaDefine.getUpdateTime());
        nrdxFormulaFormDTO.setFormulaSchemeKey(formulaDefine.getFormulaSchemeKey());
        nrdxFormulaFormDTO.setFormKey(formulaDefine.getFormKey());
        nrdxFormulaFormDTO.setCode(formulaDefine.getCode());
        nrdxFormulaFormDTO.setExpression(formulaDefine.getExpression());
        nrdxFormulaFormDTO.setDataItems(formulaDefine.getDataItems());
        nrdxFormulaFormDTO.setAdjustItems(formulaDefine.getAdjustItems());
        nrdxFormulaFormDTO.setDescription(formulaDefine.getDescription());
        nrdxFormulaFormDTO.setAutoExecute(formulaDefine.getIsAutoExecute());
        nrdxFormulaFormDTO.setUseCalculate(formulaDefine.getUseCalculate());
        nrdxFormulaFormDTO.setUseCheck(formulaDefine.getUseCheck());
        nrdxFormulaFormDTO.setUseBalance(formulaDefine.getUseBalance());
        nrdxFormulaFormDTO.setCheckType(formulaDefine.getCheckType());
        nrdxFormulaFormDTO.setOwnerLevelAndId(formulaDefine.getOwnerLevelAndId());
        nrdxFormulaFormDTO.setBalanceZBExp(formulaDefine.getBalanceZBExp());
        return nrdxFormulaFormDTO;
    }

    public FormulaConditionLinkDTO getFormulaConditionLinkDTO() {
        return this.formulaConditionLinkDTO;
    }

    public void setFormulaConditionLinkDTO(FormulaConditionLinkDTO formulaConditionLinkDTO) {
        this.formulaConditionLinkDTO = formulaConditionLinkDTO;
    }
}

