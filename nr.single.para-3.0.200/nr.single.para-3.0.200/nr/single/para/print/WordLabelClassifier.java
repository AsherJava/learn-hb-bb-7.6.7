/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nr.single.para.print.IWordLabelClassifier;

public class WordLabelClassifier
implements IWordLabelClassifier {
    private Map<Integer, List<WordLabelDefine>> container = new TreeMap<Integer, List<WordLabelDefine>>();
    private List<WordLabelDefine> leftLabels;
    private List<WordLabelDefine> centerLabels;
    private List<WordLabelDefine> rightLabels;

    public WordLabelClassifier(WordLabelDefine[] originLabels, int element, int vPos) {
        this.process(originLabels, element, vPos);
    }

    private void process(WordLabelDefine[] labels, int element, int vPos) {
        this.leftLabels = new ArrayList<WordLabelDefine>();
        this.centerLabels = new ArrayList<WordLabelDefine>();
        this.rightLabels = new ArrayList<WordLabelDefine>();
        for (WordLabelDefine label : labels) {
            int verticalPos = label.getVerticalPos();
            int horizontalPos = label.getHorizontalPos();
            if (label.getElement() != element || verticalPos != vPos) continue;
            if (horizontalPos == 0) {
                this.leftLabels.add(label);
                continue;
            }
            if (horizontalPos == 1) {
                this.centerLabels.add(label);
                continue;
            }
            if (horizontalPos != 2) continue;
            this.rightLabels.add(label);
        }
        this.container.put(0, this.leftLabels);
        this.container.put(1, this.centerLabels);
        this.container.put(2, this.rightLabels);
    }

    @Override
    public List<WordLabelDefine> getLabelsByHorizontalPos(int hPos) {
        return this.container.get(hPos);
    }

    @Override
    public int getMaxVertiaclLabelNum() {
        int leftNum = this.leftLabels.size();
        int centerNum = this.centerLabels.size();
        int rightNum = this.rightLabels.size();
        return Math.max(Math.max(leftNum, centerNum), rightNum);
    }

    @Override
    public double getMaxVertiaclHeight(double labelHeight, double labelVerticalSpacing) {
        return (double)this.getMaxVertiaclLabelNum() * (labelHeight + labelVerticalSpacing);
    }
}

