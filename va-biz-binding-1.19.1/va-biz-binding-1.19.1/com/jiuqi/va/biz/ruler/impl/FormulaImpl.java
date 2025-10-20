/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.ruler.common.consts.CheckState;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.i18n.LocaleContextHolder;

public class FormulaImpl
implements Formula {
    private UUID id;
    private String objectType;
    private UUID objectId;
    private String propertyType;
    private String name;
    private String title;
    private String expression;
    private transient IExpression compiledExpression;
    private FormulaType formulaType;
    private String checkMessage;
    private transient Map<String, String> messageI18nMap = new HashMap<String, String>();
    private transient IExpression compiledCheckMessage;
    private boolean used;
    private String group;
    private CheckState checkState;
    private String triggerType;
    private boolean extend;
    private String propTable;

    public String getTriggerType() {
        return this.triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Transient
    public IExpression getCompiledExpression() {
        return this.compiledExpression;
    }

    @Override
    public FormulaType getFormulaType() {
        return this.formulaType;
    }

    @Override
    public String getCheckMessage() {
        String i18nMessage = this.messageI18nMap.get(LocaleContextHolder.getLocale().toLanguageTag());
        if (VaI18nParamUtils.getTranslationEnabled().booleanValue() && i18nMessage != null) {
            return i18nMessage;
        }
        return this.checkMessage;
    }

    public Map<String, String> getMessageI18nMap() {
        return this.messageI18nMap;
    }

    public void setMessageI18nMap(Map<String, String> messageI18nMap) {
        this.messageI18nMap = messageI18nMap;
    }

    @Transient
    public IExpression getCompiledCheckMessage() {
        return this.compiledCheckMessage;
    }

    @Override
    public boolean isUsed() {
        return this.used;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public CheckState getCheckState() {
        return this.checkState;
    }

    @Override
    public String getObjectType() {
        return this.objectType;
    }

    @Override
    public UUID getObjectId() {
        return this.objectId;
    }

    @Override
    public String getPropertyType() {
        return this.propertyType;
    }

    @Override
    public boolean isExtend() {
        return this.extend;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Transient
    public void setCompiledExpression(IExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }

    public void setFormulaType(FormulaType formulaType) {
        this.formulaType = formulaType;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage;
    }

    @Transient
    public void setCompiledCheckMessage(IExpression compiledCheckMessage) {
        this.compiledCheckMessage = compiledCheckMessage;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCheckState(CheckState checkState) {
        this.checkState = checkState;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setExtend(boolean extend) {
        this.extend = extend;
    }

    @Transient
    public String getPropTable() {
        return this.propTable;
    }

    @Transient
    public void setPropTable(String propTable) {
        this.propTable = propTable;
    }
}

