/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.core.FontConvertUtil
 *  com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject
 *  com.jiuqi.nr.single.core.print.IDrawResult
 */
package nr.single.para.print;

import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.FontConvertUtil;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.nr.single.core.print.IDrawResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nr.single.para.print.DrawResult;
import nr.single.para.print.ElementsDrawParam;
import nr.single.para.print.IWordLabelClassifier;
import nr.single.para.print.IWordLabelDrawer;

public abstract class AbstractWordLabelDrawer
implements IWordLabelDrawer {
    @Override
    public IDrawResult draw(ElementsDrawParam param, IWordLabelClassifier classifier) {
        DrawResult result = new DrawResult();
        double startY = param.getStartY();
        List<WordLabelDefine> leftLabels = classifier.getLabelsByHorizontalPos(0);
        result.addAll(this.drawLabels(param, leftLabels, param.getLeftX(), startY));
        List<WordLabelDefine> centerLabels = classifier.getLabelsByHorizontalPos(1);
        result.addAll(this.drawLabels(param, centerLabels, param.getMiddleX(), startY));
        List<WordLabelDefine> rightLabels = classifier.getLabelsByHorizontalPos(2);
        result.addAll(this.drawLabels(param, rightLabels, param.getRightX(), startY));
        return result;
    }

    protected List<WordLabelTemplateObject> drawLabels(ElementsDrawParam param, List<WordLabelDefine> labelDefines, double startX, double startY) {
        return new ArrayList<WordLabelTemplateObject>();
    }

    protected WordLabelTemplateObject drawOneLabel(ElementsDrawParam param, WordLabelDefine labelDefine, double x, double y) {
        WordLabelTemplateObject labelElement = (WordLabelTemplateObject)param.getFactory().create("element_wordLabel");
        String content = labelDefine.getText();
        labelElement.setX(x);
        labelElement.setY(y);
        labelElement.setID(content + "_" + UUID.randomUUID().toString());
        labelElement.setReportGuid(param.getReport().getKey());
        labelElement.setContent(content);
        labelElement.setHorizonAlignment(0x1000000);
        labelElement.setWidth(param.getLabelWidth());
        labelElement.setHeight(param.getLabelHeight());
        labelElement.setCharSet("UTF-16");
        labelElement.setFont(FontConvertUtil.getDraw2ndFont((Font)labelDefine.getFont()));
        labelElement.setAutoSize(true);
        labelElement.setAutoWrap(true);
        labelElement.setDrawScope(labelDefine.getScope());
        return labelElement;
    }
}

