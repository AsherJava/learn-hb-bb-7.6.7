/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject
 *  com.jiuqi.nr.single.core.print.IDrawResult
 *  com.jiuqi.xg.process.ITemplateElement
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.nr.single.core.print.IDrawResult;
import com.jiuqi.xg.process.ITemplateElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawResult
implements IDrawResult {
    private List<WordLabelTemplateObject> elements = new ArrayList<WordLabelTemplateObject>();

    public Collection<? extends ITemplateElement<?>> getElements() {
        return this.elements;
    }

    public void addAll(Collection<WordLabelTemplateObject> elements) {
        this.elements.addAll(elements);
    }
}

