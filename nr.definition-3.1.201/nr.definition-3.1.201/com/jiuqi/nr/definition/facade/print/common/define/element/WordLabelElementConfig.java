/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.Guid
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.print.viewer.AbstractElementConfiger
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.definition.facade.print.common.define.element;

import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.util.Guid;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.print.viewer.AbstractElementConfiger;
import com.jiuqi.xg.process.IGraphicalElement;

public class WordLabelElementConfig
extends AbstractElementConfiger {
    private int count = 0;

    public WordLabelElementConfig(String nature, String name, String title) {
        super(nature, name, title);
    }

    public WordLabelElementConfig(String nature) {
        this(nature, "element_wordLabel", "\u6587\u5b57\u6807\u7b7e");
    }

    public IGraphicalElement initElement(IGraphicalElement element) {
        WordLabelTemplateObject word = (WordLabelTemplateObject)element;
        word.setID("label_" + Guid.newGuid());
        if (word.getWidth() == 0.0 && word.getHeight() == 0.0) {
            word.setWidth(30.0);
            word.setHeight(10.0);
        }
        word.setContent("\u6587\u5b57\u6807\u7b7e" + this.count++);
        word.setFont(new Font());
        return word;
    }
}

