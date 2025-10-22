/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.single.core.util.datatable.DataColumnCollection
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data.util;

import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.single.core.util.datatable.DataColumnCollection;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.List;

public interface SyntaxExcuteMapEntity {
    public boolean buildExpression(String var1, List<IFMDMAttribute> var2);

    public String getExpValue(IFMDMData var1);

    public boolean buildExpression(String var1, DataColumnCollection var2);

    public String getExpValue(DataRow var1);
}

