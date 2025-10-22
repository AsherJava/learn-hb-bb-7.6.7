/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.syntax.function.MOM
 *  com.jiuqi.bi.adhoc.engine.syntax.function.YOY
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.date.Date
 *  com.jiuqi.bi.syntax.function.date.DateAdd
 *  com.jiuqi.bi.syntax.function.date.DateDiff
 *  com.jiuqi.bi.syntax.function.date.DateValue
 *  com.jiuqi.bi.syntax.function.date.Day
 *  com.jiuqi.bi.syntax.function.date.DayOfYear
 *  com.jiuqi.bi.syntax.function.date.DaysInMonth
 *  com.jiuqi.bi.syntax.function.date.FormatDate
 *  com.jiuqi.bi.syntax.function.date.LastDay
 *  com.jiuqi.bi.syntax.function.date.Month
 *  com.jiuqi.bi.syntax.function.date.Now
 *  com.jiuqi.bi.syntax.function.date.Today
 *  com.jiuqi.bi.syntax.function.date.Weekday
 *  com.jiuqi.bi.syntax.function.date.Year
 *  com.jiuqi.bi.syntax.function.logic.IfThen
 *  com.jiuqi.bi.syntax.function.math.ABS
 *  com.jiuqi.bi.syntax.function.math.EXP
 *  com.jiuqi.bi.syntax.function.math.Int
 *  com.jiuqi.bi.syntax.function.math.Ln
 *  com.jiuqi.bi.syntax.function.math.Log
 *  com.jiuqi.bi.syntax.function.math.Log10
 *  com.jiuqi.bi.syntax.function.math.Mod
 *  com.jiuqi.bi.syntax.function.math.Power
 *  com.jiuqi.bi.syntax.function.math.Quotient
 *  com.jiuqi.bi.syntax.function.math.Rand
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.function.math.RoundDown
 *  com.jiuqi.bi.syntax.function.math.RoundUp
 *  com.jiuqi.bi.syntax.function.math.Sqrt
 *  com.jiuqi.bi.syntax.function.text.Concatenate
 *  com.jiuqi.bi.syntax.function.text.FormatNum
 *  com.jiuqi.bi.syntax.function.text.Left
 *  com.jiuqi.bi.syntax.function.text.LeftPad
 *  com.jiuqi.bi.syntax.function.text.LeftTrim
 *  com.jiuqi.bi.syntax.function.text.Len
 *  com.jiuqi.bi.syntax.function.text.Lower
 *  com.jiuqi.bi.syntax.function.text.Mid
 *  com.jiuqi.bi.syntax.function.text.Pos
 *  com.jiuqi.bi.syntax.function.text.Replace
 *  com.jiuqi.bi.syntax.function.text.ReplaceAll
 *  com.jiuqi.bi.syntax.function.text.Rept
 *  com.jiuqi.bi.syntax.function.text.Right
 *  com.jiuqi.bi.syntax.function.text.RightPad
 *  com.jiuqi.bi.syntax.function.text.RightTrim
 *  com.jiuqi.bi.syntax.function.text.Str
 *  com.jiuqi.bi.syntax.function.text.Text
 *  com.jiuqi.bi.syntax.function.text.TextJoin
 *  com.jiuqi.bi.syntax.function.text.Trim
 *  com.jiuqi.bi.syntax.function.text.Upper
 *  com.jiuqi.bi.syntax.function.text.Val
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.adhoc.engine.syntax.function.MOM;
import com.jiuqi.bi.adhoc.engine.syntax.function.YOY;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.date.Date;
import com.jiuqi.bi.syntax.function.date.DateAdd;
import com.jiuqi.bi.syntax.function.date.DateDiff;
import com.jiuqi.bi.syntax.function.date.DateValue;
import com.jiuqi.bi.syntax.function.date.Day;
import com.jiuqi.bi.syntax.function.date.DayOfYear;
import com.jiuqi.bi.syntax.function.date.DaysInMonth;
import com.jiuqi.bi.syntax.function.date.FormatDate;
import com.jiuqi.bi.syntax.function.date.LastDay;
import com.jiuqi.bi.syntax.function.date.Month;
import com.jiuqi.bi.syntax.function.date.Now;
import com.jiuqi.bi.syntax.function.date.Today;
import com.jiuqi.bi.syntax.function.date.Weekday;
import com.jiuqi.bi.syntax.function.date.Year;
import com.jiuqi.bi.syntax.function.logic.IfThen;
import com.jiuqi.bi.syntax.function.math.ABS;
import com.jiuqi.bi.syntax.function.math.EXP;
import com.jiuqi.bi.syntax.function.math.Int;
import com.jiuqi.bi.syntax.function.math.Ln;
import com.jiuqi.bi.syntax.function.math.Log;
import com.jiuqi.bi.syntax.function.math.Log10;
import com.jiuqi.bi.syntax.function.math.Mod;
import com.jiuqi.bi.syntax.function.math.Power;
import com.jiuqi.bi.syntax.function.math.Quotient;
import com.jiuqi.bi.syntax.function.math.Rand;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.function.math.RoundDown;
import com.jiuqi.bi.syntax.function.math.RoundUp;
import com.jiuqi.bi.syntax.function.math.Sqrt;
import com.jiuqi.bi.syntax.function.text.Concatenate;
import com.jiuqi.bi.syntax.function.text.FormatNum;
import com.jiuqi.bi.syntax.function.text.Left;
import com.jiuqi.bi.syntax.function.text.LeftPad;
import com.jiuqi.bi.syntax.function.text.LeftTrim;
import com.jiuqi.bi.syntax.function.text.Len;
import com.jiuqi.bi.syntax.function.text.Lower;
import com.jiuqi.bi.syntax.function.text.Mid;
import com.jiuqi.bi.syntax.function.text.Pos;
import com.jiuqi.bi.syntax.function.text.Replace;
import com.jiuqi.bi.syntax.function.text.ReplaceAll;
import com.jiuqi.bi.syntax.function.text.Rept;
import com.jiuqi.bi.syntax.function.text.Right;
import com.jiuqi.bi.syntax.function.text.RightPad;
import com.jiuqi.bi.syntax.function.text.RightTrim;
import com.jiuqi.bi.syntax.function.text.Str;
import com.jiuqi.bi.syntax.function.text.Text;
import com.jiuqi.bi.syntax.function.text.TextJoin;
import com.jiuqi.bi.syntax.function.text.Trim;
import com.jiuqi.bi.syntax.function.text.Upper;
import com.jiuqi.bi.syntax.function.text.Val;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BiAdaptFunctionProvider
implements IFunctionProvider {
    private Map<String, IFunction> functions = new HashMap<String, IFunction>();

    public BiAdaptFunctionProvider() {
        this.addfunction((IFunction)new Date());
        this.addfunction((IFunction)new DateAdd());
        this.addfunction((IFunction)new DateDiff());
        this.addfunction((IFunction)new DateValue());
        this.addfunction((IFunction)new Day());
        this.addfunction((IFunction)new DayOfYear());
        this.addfunction((IFunction)new FormatDate());
        this.addfunction((IFunction)new LastDay());
        this.addfunction((IFunction)new Month());
        this.addfunction((IFunction)new Now());
        this.addfunction((IFunction)new Today());
        this.addfunction((IFunction)new Weekday());
        this.addfunction((IFunction)new Year());
        this.addfunction((IFunction)new DaysInMonth());
        this.addfunction((IFunction)new ABS());
        this.addfunction((IFunction)new EXP());
        this.addfunction((IFunction)new Int());
        this.addfunction((IFunction)new Ln());
        this.addfunction((IFunction)new Log());
        this.addfunction((IFunction)new Log10());
        this.addfunction((IFunction)new Mod());
        this.addfunction((IFunction)new Power());
        this.addfunction((IFunction)new Quotient());
        this.addfunction((IFunction)new Rand());
        this.addfunction((IFunction)new Round());
        this.addfunction((IFunction)new RoundDown());
        this.addfunction((IFunction)new RoundUp());
        this.addfunction((IFunction)new Sqrt());
        this.addfunction((IFunction)new Concatenate());
        this.addfunction((IFunction)new FormatNum());
        this.addfunction((IFunction)new Left());
        this.addfunction((IFunction)new LeftPad());
        this.addfunction((IFunction)new LeftTrim());
        this.addfunction((IFunction)new Len());
        this.addfunction((IFunction)new Lower());
        this.addfunction((IFunction)new Mid());
        this.addfunction((IFunction)new Pos());
        this.addfunction((IFunction)new Replace());
        this.addfunction((IFunction)new ReplaceAll());
        this.addfunction((IFunction)new Rept());
        this.addfunction((IFunction)new Right());
        this.addfunction((IFunction)new RightPad());
        this.addfunction((IFunction)new RightTrim());
        this.addfunction((IFunction)new Str());
        this.addfunction((IFunction)new Text());
        this.addfunction((IFunction)new TextJoin());
        this.addfunction((IFunction)new Trim());
        this.addfunction((IFunction)new Upper());
        this.addfunction((IFunction)new Val());
        this.addfunction((IFunction)new IfThen());
        this.addfunction((IFunction)new YOY());
        this.addfunction((IFunction)new MOM());
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

