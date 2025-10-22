/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.stat.Avg
 *  com.jiuqi.bi.syntax.function.stat.Count
 *  com.jiuqi.bi.syntax.function.stat.Max
 *  com.jiuqi.bi.syntax.function.stat.Min
 *  com.jiuqi.bi.syntax.function.stat.Sum
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.function.provider;

import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.stat.Avg;
import com.jiuqi.bi.syntax.function.stat.Count;
import com.jiuqi.bi.syntax.function.stat.Max;
import com.jiuqi.bi.syntax.function.stat.Min;
import com.jiuqi.bi.syntax.function.stat.Sum;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.function.func.BGHZCheck;
import com.jiuqi.nr.function.func.CharRange;
import com.jiuqi.nr.function.func.ChineseDigitUnit;
import com.jiuqi.nr.function.func.ChineseStr;
import com.jiuqi.nr.function.func.DateDiff;
import com.jiuqi.nr.function.func.DateToPeriod;
import com.jiuqi.nr.function.func.EDate;
import com.jiuqi.nr.function.func.Evaluate;
import com.jiuqi.nr.function.func.Exist;
import com.jiuqi.nr.function.func.ExistChinese;
import com.jiuqi.nr.function.func.FALSE;
import com.jiuqi.nr.function.func.FetchOtherUnitDatas;
import com.jiuqi.nr.function.func.FloatCopy;
import com.jiuqi.nr.function.func.GetBDParent;
import com.jiuqi.nr.function.func.GetMasterData;
import com.jiuqi.nr.function.func.GetRowNum;
import com.jiuqi.nr.function.func.GetSameDataCount;
import com.jiuqi.nr.function.func.GetUpperLevelCode;
import com.jiuqi.nr.function.func.Hour;
import com.jiuqi.nr.function.func.IDC;
import com.jiuqi.nr.function.func.IDCL;
import com.jiuqi.nr.function.func.IDCard18;
import com.jiuqi.nr.function.func.IDCardDate;
import com.jiuqi.nr.function.func.InList;
import com.jiuqi.nr.function.func.IsDigit;
import com.jiuqi.nr.function.func.IsIDCard;
import com.jiuqi.nr.function.func.IsMobilePhone;
import com.jiuqi.nr.function.func.IsTelephone;
import com.jiuqi.nr.function.func.LDollar;
import com.jiuqi.nr.function.func.Len;
import com.jiuqi.nr.function.func.Length;
import com.jiuqi.nr.function.func.MDollar;
import com.jiuqi.nr.function.func.MatchRegex;
import com.jiuqi.nr.function.func.Minute;
import com.jiuqi.nr.function.func.MonthDay;
import com.jiuqi.nr.function.func.OrgRegulatory;
import com.jiuqi.nr.function.func.PeriodToDate;
import com.jiuqi.nr.function.func.Pos;
import com.jiuqi.nr.function.func.Position;
import com.jiuqi.nr.function.func.PushFixData;
import com.jiuqi.nr.function.func.PushFloatData;
import com.jiuqi.nr.function.func.QuickReportWriteBack;
import com.jiuqi.nr.function.func.RDollar;
import com.jiuqi.nr.function.func.Rpt_code;
import com.jiuqi.nr.function.func.Rpt_title;
import com.jiuqi.nr.function.func.Second;
import com.jiuqi.nr.function.func.Split;
import com.jiuqi.nr.function.func.Stdev;
import com.jiuqi.nr.function.func.Stdevp;
import com.jiuqi.nr.function.func.Substr;
import com.jiuqi.nr.function.func.TRUE;
import com.jiuqi.nr.function.func.TreeStat;
import com.jiuqi.nr.function.func.WeekDay;
import com.jiuqi.nr.function.func.YearDay;
import com.jiuqi.nr.function.func.deprecated.Childlevel;
import com.jiuqi.nr.function.func.deprecated.DistributeFloatCopy;
import com.jiuqi.nr.function.func.deprecated.Eval;
import com.jiuqi.nr.function.func.deprecated.GetMasterDataByObjectID;
import com.jiuqi.nr.function.func.deprecated.GetOrgTable;
import com.jiuqi.nr.function.func.deprecated.InDollar;
import com.jiuqi.nr.function.func.deprecated.PrevLineData;
import com.jiuqi.nr.function.func.deprecated.ToGuid;
import com.jiuqi.nr.function.func.deprecated.TreeStatEx;
import com.jiuqi.nr.function.func.deprecated.UnitCount;
import com.jiuqi.nr.function.func.deprecated.UnitExist;
import com.jiuqi.nr.function.func.deprecated.UnitLevel;
import com.jiuqi.nr.function.func.deprecated.Uuid;
import com.jiuqi.nr.function.func.deprecated.parentZBValue;
import com.jiuqi.nr.function.func.extract.CopyFromDataSet;
import com.jiuqi.nr.function.func.extract.GetFromDataSet;
import com.jiuqi.nr.function.func.floats.FloatPrevData;
import com.jiuqi.nr.function.func.getBDColumn;
import com.jiuqi.nr.function.func.getMeaning;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NrFunctionProvider
implements IFunctionProvider {
    private Map<String, IFunction> functions = new HashMap<String, IFunction>();

    public NrFunctionProvider() {
        this.addfunction((IFunction)new Sum());
        this.addfunction((IFunction)new Max());
        this.addfunction((IFunction)new Min());
        this.addfunction((IFunction)new Avg());
        this.addfunction((IFunction)new Count());
        this.addfunction((IFunction)new Exist());
        this.addfunction((IFunction)new InList());
        this.addfunction((IFunction)new FloatCopy());
        this.addfunction((IFunction)new TreeStat());
        this.addfunction((IFunction)new GetUpperLevelCode());
        this.addfunction((IFunction)new GetRowNum());
        this.addfunction((IFunction)new GetMasterData());
        this.addfunction((IFunction)new getBDColumn());
        this.addfunction((IFunction)new GetBDParent());
        this.addfunction((IFunction)new getMeaning());
        this.addfunction((IFunction)new MatchRegex());
        this.addfunction((IFunction)new CharRange());
        this.addfunction((IFunction)new ChineseDigitUnit());
        this.addfunction((IFunction)new ChineseStr());
        this.addfunction((IFunction)new EDate());
        this.addfunction((IFunction)new ExistChinese());
        this.addfunction((IFunction)new FALSE());
        this.addfunction((IFunction)new Hour());
        this.addfunction((IFunction)new IDC());
        this.addfunction((IFunction)new IDCard18());
        this.addfunction((IFunction)new IDCardDate());
        this.addfunction((IFunction)new IDCL());
        this.addfunction((IFunction)new IsDigit());
        this.addfunction((IFunction)new IsIDCard());
        this.addfunction((IFunction)new LDollar());
        this.addfunction((IFunction)new Len());
        this.addfunction((IFunction)new Length());
        this.addfunction((IFunction)new MDollar());
        this.addfunction((IFunction)new Minute());
        this.addfunction((IFunction)new MonthDay());
        this.addfunction((IFunction)new Pos());
        this.addfunction((IFunction)new Position());
        this.addfunction((IFunction)new RDollar());
        this.addfunction((IFunction)new Second());
        this.addfunction((IFunction)new Substr());
        this.addfunction((IFunction)new TRUE());
        this.addfunction((IFunction)new WeekDay());
        this.addfunction((IFunction)new YearDay());
        this.addfunction((IFunction)new IsTelephone());
        this.addfunction((IFunction)new IsMobilePhone());
        this.addfunction((IFunction)new Split());
        this.addfunction((IFunction)new GetFromDataSet());
        this.addfunction((IFunction)new CopyFromDataSet());
        this.addfunction((IFunction)new Rpt_code());
        this.addfunction((IFunction)new Rpt_title());
        this.addfunction((IFunction)new DateToPeriod());
        this.addfunction((IFunction)new FetchOtherUnitDatas());
        this.addfunction((IFunction)new OrgRegulatory());
        this.addfunction((IFunction)new PushFixData());
        this.addfunction((IFunction)new PushFloatData());
        this.addfunction((IFunction)new GetSameDataCount());
        this.addfunction((IFunction)new PeriodToDate());
        this.addfunction((IFunction)new DateDiff());
        this.addfunction((IFunction)new QuickReportWriteBack());
        this.addfunction((IFunction)new BGHZCheck());
        this.addfunction((IFunction)new FloatPrevData());
        this.addfunction((IFunction)new Stdev());
        this.addfunction((IFunction)new Stdevp());
        this.addfunction((IFunction)new ToGuid());
        this.addfunction((IFunction)new PrevLineData());
        this.addfunction((IFunction)new UnitCount());
        this.addfunction((IFunction)new UnitExist());
        this.addfunction((IFunction)new UnitLevel());
        this.addfunction((IFunction)new Childlevel());
        this.addfunction((IFunction)new Eval());
        this.addfunction((IFunction)new DistributeFloatCopy());
        this.addfunction((IFunction)new GetMasterDataByObjectID());
        this.addfunction((IFunction)new TreeStatEx());
        this.addfunction((IFunction)new GetOrgTable());
        this.addfunction((IFunction)new InDollar());
        this.addfunction((IFunction)new parentZBValue());
        this.addfunction((IFunction)new Evaluate());
        this.addfunction((IFunction)new Uuid());
    }

    private void addfunction(IFunction func) {
        this.functions.put(func.name().toUpperCase(), func);
        if (func.aliases() != null) {
            for (String alias : func.aliases()) {
                this.functions.put(alias.toUpperCase(), func);
            }
        }
    }

    public Iterator<IFunction> iterator() {
        return this.functions.values().iterator();
    }

    public IFunction find(IContext context, String funcName) throws FunctionException {
        return this.functions.get(funcName.toUpperCase());
    }
}

