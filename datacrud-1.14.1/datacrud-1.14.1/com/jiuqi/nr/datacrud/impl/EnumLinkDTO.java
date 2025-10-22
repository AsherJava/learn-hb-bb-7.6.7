/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

import java.util.ArrayList;
import java.util.List;

public class EnumLinkDTO {
    private String link;
    private String level;
    private List<String> preLinks = new ArrayList<String>();
    private List<String> nextLinks = new ArrayList<String>();
    private String type;
    private String formula;

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getPreLinks() {
        return this.preLinks;
    }

    public List<String> getNextLinks() {
        return this.nextLinks;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}

