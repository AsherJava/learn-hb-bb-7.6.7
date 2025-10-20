/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.concurrent.ScheduleException
 *  com.jiuqi.bi.concurrent.Scheduler
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 */
package com.jiuqi.bi.dataset.textprocessor;

import com.jiuqi.bi.concurrent.ScheduleException;
import com.jiuqi.bi.concurrent.Scheduler;
import com.jiuqi.bi.dataset.textprocessor.IDataSourceProvider;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.TextSegment;
import com.jiuqi.bi.dataset.textprocessor.parse.TextParser;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Callable;

public class TextProcessor {
    private static final int MAX_CALC_THREAD_NUM = 5;
    private IDataSourceProvider dsProvider;
    private List<IFunctionProvider> funcProviders = new ArrayList<IFunctionProvider>();
    private List<IDynamicNodeProvider> dynamicProviders = new ArrayList<IDynamicNodeProvider>();

    public TextProcessor() {
    }

    public TextProcessor(IDataSourceProvider provider) {
        this.dsProvider = provider;
    }

    public List<TextSegment> parseText(String text, TextFormulaContext context) throws ParseException {
        List<TextSegment> segments = TextProcessor.parseText(text);
        this.parseText(segments, context);
        return segments;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void parseText(List<TextSegment> segments, TextFormulaContext context) throws ParseException {
        block13: {
            if (segments == null) {
                return;
            }
            context = this.checkContext(context);
            if (this.needTaskConcurrent(segments)) {
                Scheduler scheduler = new Scheduler(5, null);
                scheduler.start();
                try {
                    for (TextSegment segment : segments) {
                        if (!segment.isFormula()) continue;
                        String formula = segment.getText();
                        formula = formula.substring(2, formula.length() - 1);
                        TextParser parser = this.newParser(context);
                        TaskCaller taskCaller = new TaskCaller(parser, segment, formula, context);
                        try {
                            scheduler.addTask(Guid.newGuid(), (Callable)taskCaller);
                        }
                        catch (ScheduleException e1) {
                            throw new ParseException("\u542f\u52a8\u4efb\u52a1\u65f6\u51fa\u9519\uff0c" + e1.getMessage(), (Throwable)e1);
                        }
                    }
                    try {
                        scheduler.join();
                        break block13;
                    }
                    catch (InterruptedException e) {
                        throw new ParseException("\u6267\u884c\u8ba1\u7b97\u5931\u8d25\uff0c\u8ba1\u7b97\u7ebf\u7a0b\u88ab\u4e2d\u65ad");
                    }
                }
                finally {
                    scheduler.stop(0L);
                }
            }
            for (TextSegment segment : segments) {
                try {
                    this.parseText(segment, context);
                }
                catch (SyntaxException e) {
                    throw new ParseException(e.getMessage(), (Throwable)e);
                }
            }
        }
    }

    private boolean needTaskConcurrent(List<TextSegment> segments) {
        int counter = 0;
        for (TextSegment segment : segments) {
            if (!segment.isFormula()) continue;
            ++counter;
        }
        return counter >= 3;
    }

    public void parseText(TextSegment segment, TextFormulaContext context) throws SyntaxException {
        if (segment == null || !segment.isFormula()) {
            return;
        }
        context = this.checkContext(context);
        String formula = segment.getText();
        formula = formula.substring(2, formula.length() - 1);
        TextParser parser = this.newParser(context);
        IExpression expr = parser.parse(formula, context);
        String result = expr.evalAsString((IContext)context);
        segment.setResult(result);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TextSegment> parseFormula(List<String> formulas, TextFormulaContext context) throws ParseException {
        int threads = Math.min(5, formulas.size());
        ArrayList<TextSegment> segments = new ArrayList<TextSegment>(formulas.size());
        Scheduler scheduler = new Scheduler(threads, null);
        scheduler.start();
        try {
            context = this.checkContext(context);
            for (String formula : formulas) {
                TextSegment segment = new TextSegment(formula, true);
                segments.add(segment);
                TextParser parser = this.newParser(context);
                TaskCaller taskCaller = new TaskCaller(parser, segment, formula, context);
                try {
                    scheduler.addTask(Guid.newGuid(), (Callable)taskCaller);
                }
                catch (ScheduleException e1) {
                    throw new ParseException("\u542f\u52a8\u4efb\u52a1\u65f6\u51fa\u9519\uff0c" + e1.getMessage(), (Throwable)e1);
                }
            }
            try {
                scheduler.join();
            }
            catch (InterruptedException e) {
                throw new ParseException("\u6267\u884c\u8ba1\u7b97\u5931\u8d25\uff0c\u8ba1\u7b97\u7ebf\u7a0b\u88ab\u4e2d\u65ad");
            }
        }
        finally {
            scheduler.stop(0L);
        }
        return segments;
    }

    public IExpression parse(String formula, TextFormulaContext context) throws ParseException {
        if (StringUtils.isEmpty((String)formula)) {
            return null;
        }
        context = this.checkContext(context);
        TextParser parser = this.newParser(context);
        try {
            return parser.parse(formula, context);
        }
        catch (SyntaxException e) {
            throw new ParseException((Throwable)e);
        }
    }

    public Set<IFunction> allFunctions() {
        TextParser parser = this.newParser(null);
        return parser.allFunctions();
    }

    public void registerFunctionProvider(IFunctionProvider funcProvider) {
        this.funcProviders.add(funcProvider);
    }

    public void registerDynamicNodeProvider(IDynamicNodeProvider dynamicProvider) {
        this.dynamicProviders.add(dynamicProvider);
    }

    public static final String concat(List<TextSegment> segments) {
        return TextProcessor.concat(segments, 1);
    }

    public static final String concat(List<TextSegment> segments, int errorFragmentShowMode) {
        StringBuffer buf = new StringBuffer();
        for (TextSegment segment : segments) {
            if (segment.isFormula()) {
                if (segment.isErrorBlock()) {
                    if (errorFragmentShowMode == 1) {
                        buf.append(segment.getErrMsg());
                        continue;
                    }
                    if (errorFragmentShowMode != 2) continue;
                    buf.append(segment.getText());
                    continue;
                }
                buf.append(segment.getResult());
                continue;
            }
            buf.append(segment.getText());
        }
        return buf.toString();
    }

    private TextParser newParser(TextFormulaContext context) {
        TextParser parser = new TextParser();
        for (IFunctionProvider iFunctionProvider : this.funcProviders) {
            parser.registerFunctionProvider(iFunctionProvider);
        }
        for (IDynamicNodeProvider iDynamicNodeProvider : this.dynamicProviders) {
            parser.registerParamProvider(iDynamicNodeProvider);
        }
        if (context != null) {
            Iterator<IParameterEnv> itor = context.enhancedParamEnvItor();
            while (itor.hasNext()) {
                ParamNodeProvider paramNodeProvider = new ParamNodeProvider(itor.next());
                parser.registerParamProvider((IDynamicNodeProvider)paramNodeProvider);
            }
        }
        return parser;
    }

    private TextFormulaContext checkContext(TextFormulaContext context) {
        if (context == null) {
            context = new TextFormulaContext(null);
        }
        if (context.getDsProvider() == null) {
            context.setDsProvider(this.dsProvider);
        }
        return context;
    }

    public static final List<TextSegment> parseText(String txt) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int charIdx = 0; charIdx < txt.length(); ++charIdx) {
            char c1 = txt.charAt(charIdx);
            if (c1 == '$' && charIdx < txt.length() - 1 && txt.charAt(charIdx + 1) == '{') {
                stack.push(charIdx);
                ++charIdx;
                continue;
            }
            if (c1 == '{') {
                stack.push(charIdx);
                continue;
            }
            if (c1 != '}') continue;
            if (!stack.isEmpty() && txt.charAt((Integer)stack.peek()) == '{') {
                stack.pop();
                continue;
            }
            stack.push(charIdx);
        }
        int lastIdx = 0;
        ArrayList<TextSegment> fgs = new ArrayList<TextSegment>();
        for (Integer idx : stack) {
            if (idx <= lastIdx) continue;
            char c = txt.charAt(idx);
            String value = null;
            if (c == '$') {
                value = txt.substring(lastIdx, idx);
            } else if (c == '}') {
                value = txt.substring(lastIdx, idx + 1);
            }
            if (value == null) continue;
            TextSegment segment = new TextSegment(value);
            fgs.add(segment);
            if (c == '}') {
                lastIdx = idx + 1;
                continue;
            }
            lastIdx = idx;
        }
        if (lastIdx < txt.length()) {
            String value = txt.substring(lastIdx, txt.length());
            TextSegment segment = new TextSegment(value);
            fgs.add(segment);
        }
        return fgs;
    }

    private final class TaskCaller
    implements Callable<TextSegment> {
        private TextParser txtParser;
        private TextSegment segment;
        private String formula;
        private TextFormulaContext context;

        public TaskCaller(TextParser txtParser, TextSegment segment, String formula, TextFormulaContext context) {
            this.txtParser = txtParser;
            this.segment = segment;
            this.formula = formula;
            this.context = context.clone();
        }

        @Override
        public TextSegment call() throws Exception {
            if (StringUtils.isEmpty((String)this.formula)) {
                this.segment.setResult(null);
            } else {
                try {
                    IExpression expr = this.txtParser.parse(this.formula, this.context);
                    String result = expr.evalAsString((IContext)this.context);
                    this.segment.setResult(result);
                }
                catch (Exception e) {
                    this.segment.setErrMsg(e.getMessage());
                    e.printStackTrace();
                }
            }
            return this.segment;
        }
    }
}

