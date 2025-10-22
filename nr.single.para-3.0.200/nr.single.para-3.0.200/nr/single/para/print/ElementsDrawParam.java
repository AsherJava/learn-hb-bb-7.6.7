/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil
 *  com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig
 *  com.jiuqi.nr.single.core.print.TemplatePageStyleConfigFactory
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil;
import com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig;
import com.jiuqi.nr.single.core.print.TemplatePageStyleConfigFactory;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.obj.PageTemplateObject;

public class ElementsDrawParam {
    private Paper paper;
    private double[] margins;
    private ITemplatePageStyleConfig config;
    private double startY;
    private PageTemplateObject page;
    private WordLabelDefine[] allWordLabels;
    private DesignFormDefine curReport;
    private int printSolutionType = 0;

    public DesignFormDefine getReport() {
        return this.curReport;
    }

    public ElementsDrawParam(PageTemplateObject page, DesignFormDefine curReport, WordLabelDefine[] allWordLabels, int printSolutionType) {
        this.page = page;
        this.paper = page.getPaper();
        this.margins = page.getMargins();
        this.config = TemplatePageStyleConfigFactory.getConfig();
        this.allWordLabels = allWordLabels;
        this.curReport = curReport;
        this.printSolutionType = printSolutionType;
    }

    public ElementsDrawParam(PageTemplateObject page, DesignFormDefine curReport, WordLabelDefine[] allWordLabels, int printSolutionType, ITemplatePageStyleConfig config) {
        this.page = page;
        this.paper = page.getPaper();
        this.margins = page.getMargins();
        this.config = config;
        this.allWordLabels = allWordLabels;
        this.curReport = curReport;
        this.printSolutionType = printSolutionType;
    }

    public ITemplateObjectFactory getFactory() {
        return DocumentBuildUtil.getTemplateObjectFactory();
    }

    public Paper getPaper() {
        return this.paper;
    }

    public PageTemplateObject getPage() {
        return this.page;
    }

    public int getPrintSolutionType() {
        return this.printSolutionType;
    }

    public double[] getMargins() {
        return this.margins;
    }

    public double getLeftX() {
        return this.margins[2];
    }

    public double getMiddleX() {
        double rEdge = this.getRightEdgeX();
        double lEdge = this.getLeftX();
        double midLine = (rEdge - lEdge) / 2.0;
        double labelWidth = this.getLabelWidth();
        return midLine - labelWidth / 2.0 + lEdge;
    }

    private double getRightEdgeX() {
        if (this.page.getOrientation() == 256) {
            return this.paper.getHeight() - this.margins[3];
        }
        return this.paper.getWidth() - this.margins[3];
    }

    public double getRightX() {
        return this.getRightEdgeX() - this.getLabelWidth();
    }

    public double getLabelWidth() {
        return this.config.getWordLabelWidth();
    }

    public double getLabelHeight() {
        return this.config.getWordLabelHeight();
    }

    public double getStartY() {
        return this.startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public WordLabelDefine[] getAllWordLabels() {
        return this.allWordLabels;
    }

    public double getLabelVerticalSpacing() {
        return this.config.getVerticalSpacing();
    }

    public ITemplatePageStyleConfig getConfig() {
        return this.config;
    }
}

