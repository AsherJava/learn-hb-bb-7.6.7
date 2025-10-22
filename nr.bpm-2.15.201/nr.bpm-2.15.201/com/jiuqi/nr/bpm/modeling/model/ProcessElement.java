/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.exception.ParameterUtils;
import com.jiuqi.nr.bpm.modeling.model.BPMNDocumentElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcessElement
extends BPMNDocumentElement {
    private final String tag;
    private List<ElementProperty> properties;
    private List<BPMNDocumentElement> children;

    protected ProcessElement(String tag) {
        this.tag = tag;
    }

    protected void setProperty(String name, String value) {
        Optional<ElementProperty> property;
        if (this.properties == null) {
            this.properties = new ArrayList<ElementProperty>();
        }
        if ((property = this.properties.stream().filter(o -> o.name.equals(name)).findFirst()).isPresent()) {
            property.get().value = value;
        } else {
            this.properties.add(new ElementProperty(name, value));
        }
    }

    protected void appendChild(BPMNDocumentElement child) {
        ParameterUtils.AssertNotNull("child", child);
        if (this.children == null) {
            this.children = new ArrayList<BPMNDocumentElement>();
        }
        this.children.add(child);
    }

    @Override
    public void buildBPMNDocument(StringBuilder builder) {
        builder.append("<").append(this.tag);
        if (this.properties != null) {
            for (ElementProperty property : this.properties) {
                builder.append(String.format(" %s=\"%s\"", property.name, property.value == null ? "" : property.value.replace("\"", "&quot;")));
            }
        }
        if (this.children == null) {
            builder.append("/>");
            return;
        }
        builder.append(">");
        for (BPMNDocumentElement child : this.children) {
            child.buildBPMNDocument(builder);
        }
        builder.append("</").append(this.tag).append(">");
    }

    public static class ElementProperty {
        String name;
        String value;

        public ElementProperty(String name, String value) {
            ParameterUtils.AssertNotNull("name", name);
            this.name = name;
            this.value = value;
        }
    }
}

