/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 */
package nr.single.para.print;

import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import java.util.List;

public interface IWordLabelClassifier {
    public List<WordLabelDefine> getLabelsByHorizontalPos(int var1);

    public int getMaxVertiaclLabelNum();

    public double getMaxVertiaclHeight(double var1, double var3);
}

