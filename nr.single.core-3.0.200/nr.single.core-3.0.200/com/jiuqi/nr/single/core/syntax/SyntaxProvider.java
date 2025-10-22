/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax;

import com.jiuqi.nr.single.core.syntax.bean.BZZDataType;
import com.jiuqi.nr.single.core.syntax.bean.CodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.ExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastCodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.FastExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.FastTableCellType;
import com.jiuqi.nr.single.core.syntax.bean.MonthDataType;
import com.jiuqi.nr.single.core.syntax.bean.StringDataType;
import com.jiuqi.nr.single.core.syntax.bean.TableCellType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import java.util.Map;

public interface SyntaxProvider {
    public SyntaxCode syntaxGetTableCell(TableCellType var1);

    public SyntaxCode syntaxSetTableCell(TableCellType var1);

    public SyntaxCode syntaxGetCodeCell(CodeCellType var1);

    public SyntaxCode syntaxGetCodeMean(CodeCellType var1);

    public SyntaxCode syntaxExistData(ExistDataType var1);

    public SyntaxCode syntaxGetBZZData(BZZDataType var1);

    public SyntaxCode syntaxGetMonth(MonthDataType var1);

    public SyntaxCode syntaxGetStrData(StringDataType var1);

    public void niporlanGetTableCell(FastTableCellType var1);

    public void niporlanSetTableCell(FastTableCellType var1);

    public void niporlanGetCodeCell(FastCodeCellType var1);

    public void niporlanExistData(FastExistDataType var1);

    public void setVariableMap(Map<String, Object> var1);

    public Map<String, Object> getVariableMap();
}

