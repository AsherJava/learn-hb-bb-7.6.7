/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import java.util.HashSet;
import java.util.Set;

public class FCConditionDeployParam {
    private String formulaScheme;
    private Set<String> runKeys;
    private Set<String> designKeys;
    private Set<String> refreshSchemeKeys;
    private Set<DesignFormulaConditionLink> designLinks;
    private Set<FormulaConditionLink> runLinks;

    public FCConditionDeployParam() {
        this(null);
    }

    public FCConditionDeployParam(String formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    public Set<String> getRunKeys() {
        if (this.runKeys == null) {
            this.runKeys = new HashSet<String>();
        }
        return this.runKeys;
    }

    public void setRunKeys(Set<String> runKeys) {
        this.runKeys = runKeys;
    }

    public Set<String> getDesignKeys() {
        if (this.designKeys == null) {
            this.designKeys = new HashSet<String>();
        }
        return this.designKeys;
    }

    public void setDesignKeys(Set<String> designKeys) {
        this.designKeys = designKeys;
    }

    public Set<String> getRefreshSchemeKeys() {
        if (this.refreshSchemeKeys == null) {
            this.refreshSchemeKeys = new HashSet<String>();
        }
        return this.refreshSchemeKeys;
    }

    public void setRefreshSchemeKeys(Set<String> refreshSchemeKeys) {
        this.refreshSchemeKeys = refreshSchemeKeys;
    }

    public String getFormulaScheme() {
        return this.formulaScheme;
    }

    public void setFormulaScheme(String formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    public Set<DesignFormulaConditionLink> getDesignLinks() {
        if (this.designLinks == null) {
            this.designLinks = new HashSet<DesignFormulaConditionLink>();
        }
        return this.designLinks;
    }

    public void setDesignLinks(Set<DesignFormulaConditionLink> designLinks) {
        this.designLinks = designLinks;
    }

    public Set<FormulaConditionLink> getRunLinks() {
        if (this.runLinks == null) {
            this.runLinks = new HashSet<FormulaConditionLink>();
        }
        return this.runLinks;
    }

    public void setRunLinks(Set<FormulaConditionLink> runLinks) {
        this.runLinks = runLinks;
    }
}

