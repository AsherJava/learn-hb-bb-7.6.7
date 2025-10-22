/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.single.para.compare.definition;

import com.jiuqi.np.period.PeriodType;
import nr.single.para.compare.definition.ICompareData;

public interface ICompareDataTaskLink
extends ICompareData {
    public String getSingleLinkAlias();

    public String getSingleTaskYear();

    public PeriodType getSingleTaskType();

    public String getSingleCurrentExp();

    public String getSingleLinkExp();

    public String getNetLinkAlias();

    public String getNetTaskKey();

    public String getNetFormSchemeKey();

    public String getNetCurrentExp();

    public String getNetLinkExp();
}

