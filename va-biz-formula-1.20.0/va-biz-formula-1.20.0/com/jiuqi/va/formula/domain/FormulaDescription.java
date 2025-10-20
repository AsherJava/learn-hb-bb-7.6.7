/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.domain;

import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import java.util.List;

public class FormulaDescription {
    private String name;
    private String title;
    private String returnValue;
    private String description;
    private String[] aliases;
    private List<ParameterDescription> parameters;
    private List<FormulaExample> examples;
    private List<String> notes;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ParameterDescription> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<ParameterDescription> parameters) {
        this.parameters = parameters;
    }

    public List<FormulaExample> getExamples() {
        return this.examples;
    }

    public void setExamples(List<FormulaExample> examples) {
        this.examples = examples;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public List<String> getNotes() {
        return this.notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public FormulaDescription(String name, String title, String returnValue, String description, String[] aliases, List<ParameterDescription> parameters, List<FormulaExample> examples, List<String> notes) {
        this.name = name;
        this.title = title;
        this.returnValue = returnValue;
        this.description = description;
        this.aliases = aliases;
        this.parameters = parameters;
        this.examples = examples;
        this.notes = notes;
    }

    public FormulaDescription(String name, String title, String returnValue, String description, List<ParameterDescription> parameters, List<FormulaExample> examples, List<String> notes) {
        this.name = name;
        this.title = title;
        this.returnValue = returnValue;
        this.description = description;
        this.parameters = parameters;
        this.examples = examples;
        this.notes = notes;
    }

    public FormulaDescription() {
    }
}

