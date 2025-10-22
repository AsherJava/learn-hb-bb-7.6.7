/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.print.AbstractWordLabelDrawer;
import nr.single.para.print.ElementsDrawParam;

public class ReverseWordLabelDrawer
extends AbstractWordLabelDrawer {
    @Override
    protected List<WordLabelTemplateObject> drawLabels(ElementsDrawParam param, List<WordLabelDefine> labelDefines, double startX, double startY) {
        int endIndex;
        ArrayList<WordLabelTemplateObject> result = new ArrayList<WordLabelTemplateObject>();
        if (labelDefines.size() <= 0) {
            return result;
        }
        for (int i = endIndex = labelDefines.size() - 1; i >= 0; --i) {
            WordLabelDefine labelDefine = labelDefines.get(i);
            double elementX = startX;
            double elementY = startY - (double)(endIndex - i) * (param.getLabelVerticalSpacing() + param.getLabelHeight());
            WordLabelTemplateObject labelElement = this.drawOneLabel(param, labelDefine, elementX, elementY);
            result.add(labelElement);
        }
        return result;
    }
}

