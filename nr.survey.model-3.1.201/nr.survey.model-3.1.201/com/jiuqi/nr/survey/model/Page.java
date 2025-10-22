/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import java.util.List;

public class Page {
    private String name;
    private List<Element> elements;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}

