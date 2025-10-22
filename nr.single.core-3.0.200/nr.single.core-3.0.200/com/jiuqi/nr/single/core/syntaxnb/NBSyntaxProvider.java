/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb;

import com.jiuqi.nr.single.core.syntax.bean.BoolDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.bean.IntegerDataType;
import com.jiuqi.nr.single.core.syntax.bean.StringDataType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import com.jiuqi.nr.single.core.syntaxnb.bean.EnumDictValueType;
import com.jiuqi.nr.single.core.syntaxnb.bean.FVParams;
import com.jiuqi.nr.single.core.syntaxnb.bean.FinanceDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.GetEnumMeaningType;
import com.jiuqi.nr.single.core.syntaxnb.bean.HTDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.HTSDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.NBMultiType;
import com.jiuqi.nr.single.core.syntaxnb.bean.NBTableCellType;
import com.jiuqi.nr.single.core.syntaxnb.bean.WildcardAreaType;
import java.util.Map;

public interface NBSyntaxProvider {
    public SyntaxCode syntaxGetTableCell(NBTableCellType var1);

    public SyntaxCode syntaxSetTableCell(NBTableCellType var1);

    public SyntaxCode syntaxGetTableCellName(NBTableCellType var1, StringDataType var2);

    public SyntaxCode syntaxGetFinanceData(FinanceDataType var1);

    public SyntaxCode syntaxGetWildcardArea(WildcardAreaType var1, int[] var2, int[] var3);

    public SyntaxCode syntaxGetEnumDictValue(EnumDictValueType var1);

    public SyntaxCode syntaxGetEnumMeaning(GetEnumMeaningType var1);

    public SyntaxCode syntaxHTDataEvent(HTDataType var1);

    public SyntaxCode syntaxHTSData(HTSDataType var1);

    public SyntaxCode syntaxAutoNum(String var1, int var2, StringDataType var3);

    public SyntaxCode syntaxExists(String var1, BoolDataType var2);

    public SyntaxCode syntaxFVFunction(FVParams var1);

    public SyntaxCode syntaxEvaluate(String var1, String var2, CommonDataType var3);

    public SyntaxCode syntaxUnitExist(String var1, BoolDataType var2);

    public SyntaxCode syntaxChildrenCount(String var1, int var2, boolean var3, IntegerDataType var4);

    public SyntaxCode syntaxChildLevel(String var1, IntegerDataType var2);

    public SyntaxCode syntaxStat(String var1, String var2, String var3, int var4, CommonDataType var5);

    public SyntaxCode syntaxFloatStat(String var1, String var2, int var3, CommonDataType var4, Object var5);

    public SyntaxCode syntaxTreeStat(String var1, String var2, int var3, String var4, int var5, CommonDataType var6);

    public SyntaxCode syntaxScript(String var1, NBMultiType var2, boolean var3);

    public boolean syntaxScriptJudge(String var1);

    public int syntaxLastSample(String var1);

    public int syntaxRegionSampleNum(int var1);

    public boolean syntaxCheckTripTime(String var1, int var2, String var3, String var4, String var5);

    public int syntaxBzzCopy(String var1, String var2, String var3, String var4, String var5, String var6);

    public String syntaxBzzCode(String var1);

    public Double syntaxBzzValue(String var1, String var2, String var3, String var4);

    public String syntaxBzzStr(String var1, String var2, String var3, String var4);

    public Double syntaxGBzz(long var1, long var3);

    public Double syntaxBZZClass(String var1, String var2, long var3);

    public Map<String, Object> getVariableMap();
}

