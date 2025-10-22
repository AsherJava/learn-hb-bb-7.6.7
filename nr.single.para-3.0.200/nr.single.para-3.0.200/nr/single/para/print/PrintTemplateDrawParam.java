/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import nr.single.para.print.ElementsDrawParam;

public class PrintTemplateDrawParam
extends ElementsDrawParam {
    private ITemplatePageStyleConfig config = new ITemplatePageStyleConfig(){

        public double getWordLabelWidth() {
            return 5.0;
        }

        public double getWordLabelHeight() {
            return 20.0;
        }

        public double getVerticalSpacing() {
            return 30.0;
        }

        public String getName() {
            return "";
        }
    };

    public PrintTemplateDrawParam(PageTemplateObject page, DesignFormDefine report, WordLabelDefine[] allWordLabels, int printSolutionType) {
        super(page, report, allWordLabels, printSolutionType);
    }
}

