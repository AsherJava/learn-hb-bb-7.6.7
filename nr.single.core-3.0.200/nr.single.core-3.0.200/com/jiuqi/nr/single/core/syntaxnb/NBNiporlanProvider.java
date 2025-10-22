/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb;

import com.jiuqi.nr.single.core.syntax.bean.FastCodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.FastExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastTableCellType;
import java.util.Map;

public interface NBNiporlanProvider {
    public void niporlanGetTableCell(FastTableCellType var1);

    public void niporlanSetTableCell(FastTableCellType var1);

    public void niporlanGetCodeCell(FastCodeCellType var1);

    public void niporlanExistData(FastExistDataType var1);

    public void setVariableMap(Map<String, Object> var1);

    public Map<String, Object> getVariableMap();
}

