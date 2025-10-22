/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.definition.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScriptParser {
    private static final String REPLACE_STR = "(\\[)\\s?(\\d+?)(\\s?),(\\s?)(\\d+?)\\s?(\\])";
    private static final String REPLACE_REG = "\u3010&REG&\u3011";
    private String formKey;
    private IRunTimeViewController controller;
    Pattern compile = Pattern.compile("(\\[)\\s?(\\d+?)(\\s?),(\\s?)(\\d+?)\\s?(\\])");

    public ScriptParser(String formKey) {
        this.formKey = formKey;
        this.controller = BeanUtil.getBean(IRunTimeViewController.class);
    }

    public String parser(String scriptStr) {
        if (StringUtils.isEmpty((String)scriptStr)) {
            return null;
        }
        String script = new String(scriptStr);
        Matcher matcher = this.compile.matcher(scriptStr);
        ArrayList<Integer[]> list = new ArrayList<Integer[]>();
        while (matcher.find()) {
            list.add(new Integer[]{Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(5))});
            script = script.replaceFirst(REPLACE_STR, REPLACE_REG);
        }
        if (list.size() == 0) {
            return script;
        }
        List parsedList = list.stream().map(cell -> {
            DataLinkDefine linkDefine = this.controller.queryDataLinkDefineByColRow(this.formKey, cell[1], cell[0]);
            if (linkDefine != null) {
                return linkDefine.getKey();
            }
            return "[" + cell[0] + "," + cell[1] + "]";
        }).collect(Collectors.toList());
        for (String parsedReg : parsedList) {
            script = script.replaceFirst(REPLACE_REG, parsedReg);
        }
        return script;
    }
}

