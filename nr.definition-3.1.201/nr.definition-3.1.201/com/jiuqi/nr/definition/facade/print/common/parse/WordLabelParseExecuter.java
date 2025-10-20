/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.nr.definition.facade.print.common.parse.FormulaPatternParser;
import com.jiuqi.nr.definition.facade.print.common.parse.IPatternParser;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.facade.print.common.parse.SEMPatternParser;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordLabelParseExecuter {
    private List<IPatternParser> patternList;

    private WordLabelParseExecuter() {
        this.initPattern();
    }

    private void initPattern() {
        this.patternList = new ArrayList<IPatternParser>();
        this.patternList.add(new SEMPatternParser("{#\u65e5\u671f:", "}", new String[]{"yyyy", "mm", "dd", "hh", "nn"}));
        this.patternList.add(new SEMPatternParser("{#\u5e8f\u53f7:", "}", new String[]{"n", "m"}));
        this.patternList.add(new SEMPatternParser("{#", "}", new String[]{"ReportPageNum", "ReportPageCount", "PageNumber", "PageNum", "ReportOrder", "TotalCount", "RPTMAINTITLE", "RPTSUBTITLE", "REPORTNUM", "RPTMONEYUNIT"}));
        this.patternList.add(new FormulaPatternParser("{", "}"));
    }

    public static WordLabelParseExecuter getInstance() {
        return new WordLabelParseExecuter();
    }

    public void addPatternParser(IPatternParser patternParser) {
        this.patternList.add(3, patternParser);
    }

    public void addVariablePatternParser(List<String> varNames) {
        if (varNames == null) {
            return;
        }
        this.patternList.add(3, new SEMPatternParser("{@", "}", varNames.toArray(new String[varNames.size()])));
    }

    public String execute(ParseContext context, String source) {
        return this.execute(context, source, null);
    }

    public String execute(ParseContext context, String source, Map<String, String> patternAndValue) {
        if (StringUtils.isEmpty((String)source)) {
            return "";
        }
        StringBuffer resultBuffer = new StringBuffer();
        StringBuffer tmpBuffer = new StringBuffer();
        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            if (ch == '{') {
                if (tmpBuffer.length() > 0) {
                    resultBuffer.append(tmpBuffer.toString());
                }
                tmpBuffer.setLength(0);
                tmpBuffer.append(ch);
                continue;
            }
            if (ch == '}') {
                tmpBuffer.append(ch);
                String patternStr = tmpBuffer.toString().replace("\r\n", "");
                resultBuffer.append(this.parseSinglePattern(context, patternStr, patternAndValue));
                tmpBuffer.setLength(0);
                continue;
            }
            tmpBuffer.append(ch);
        }
        if (tmpBuffer.length() > 0) {
            resultBuffer.append(tmpBuffer.toString());
        }
        return resultBuffer.toString();
    }

    private String parseSinglePattern(ParseContext context, String patternStr, Map<String, String> patternAndValue) {
        String returnValue = "";
        for (IPatternParser parser : this.patternList) {
            if (!parser.canParse(patternStr)) continue;
            boolean reuser = parser.canReuser(patternStr);
            if (reuser && patternAndValue != null) {
                if (patternAndValue.containsKey(patternStr)) {
                    returnValue = returnValue.equals("") ? patternAndValue.get(patternStr) : returnValue;
                    break;
                }
                String parseStr = parser.parse(context, patternStr);
                returnValue = returnValue.equals("") ? parseStr : returnValue;
                patternAndValue.put(patternStr, returnValue);
                break;
            }
            String parseStr = parser.parse(context, patternStr);
            returnValue = returnValue.equals("") ? parseStr : returnValue;
            break;
        }
        return returnValue;
    }
}

