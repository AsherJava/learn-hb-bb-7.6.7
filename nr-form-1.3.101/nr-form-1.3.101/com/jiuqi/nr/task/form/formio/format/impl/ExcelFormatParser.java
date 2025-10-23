/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.format.impl;

import com.jiuqi.nr.task.form.formio.format.FormatDTO;
import com.jiuqi.nr.task.form.formio.format.IFormatParser;
import com.jiuqi.nr.task.form.formio.format.ParseConfig;
import com.jiuqi.nr.task.form.formio.format.impl.AccountFormatParser;
import com.jiuqi.nr.task.form.formio.format.impl.CurrencyFormatParser;
import com.jiuqi.nr.task.form.formio.format.impl.DateParser;
import com.jiuqi.nr.task.form.formio.format.impl.GeneralParser;
import com.jiuqi.nr.task.form.formio.format.impl.NumberParser;
import com.jiuqi.nr.task.form.formio.format.impl.PercentageParser;
import com.jiuqi.nr.task.form.formio.format.impl.TextParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFormatParser
implements IFormatParser {
    private final List<IFormatParser> formatParsers;
    private final IFormatParser defaultFormatParser;
    private final ParseConfig parseConfig;
    private final Map<String, FormatDTO> formatDTOMap;

    public ExcelFormatParser() {
        this(new ParseConfig());
    }

    public ExcelFormatParser(ParseConfig parseConfig) {
        this.parseConfig = parseConfig;
        this.formatDTOMap = new HashMap<String, FormatDTO>(35);
        this.formatParsers = new ArrayList<IFormatParser>(6);
        this.formatParsers.add(new GeneralParser());
        this.formatParsers.add(new TextParser());
        this.formatParsers.add(new PercentageParser());
        this.formatParsers.add(new NumberParser());
        this.formatParsers.add(new CurrencyFormatParser());
        this.formatParsers.add(new AccountFormatParser());
        this.formatParsers.add(new DateParser());
        this.defaultFormatParser = new GeneralParser();
    }

    @Override
    public boolean supportFormatParts() {
        return false;
    }

    @Override
    public void registerFormatParser(IFormatParser ... formatParsers) {
        if (formatParsers == null) {
            return;
        }
        Collections.addAll(this.formatParsers, formatParsers);
        this.formatParsers.sort(Comparator.comparing(IFormatParser::order));
    }

    private String[] parseFormatParts(String format) {
        String[] parts = format.split(";", -1);
        if (parts.length == 1) {
            return new String[]{parts[0], "-" + parts[0], parts[0], "@"};
        }
        if (parts.length == 2) {
            return new String[]{parts[0], parts[1], parts[0], "@"};
        }
        if (parts.length == 3) {
            return new String[]{parts[0], parts[1], parts[2], "@"};
        }
        return parts;
    }

    @Override
    public FormatDTO parse(String format) {
        FormatDTO formatDTO;
        if (this.parseConfig.isUseCache().booleanValue()) {
            if (this.formatDTOMap.containsKey(format)) {
                formatDTO = this.formatDTOMap.get(format);
            } else {
                formatDTO = this.doParse(format);
                this.formatDTOMap.put(format, formatDTO);
            }
        } else {
            formatDTO = this.doParse(format);
        }
        return formatDTO;
    }

    private FormatDTO doParse(String format) {
        String[] formatParts = this.parseFormatParts(format);
        FormatDTO formatDTO = null;
        for (IFormatParser formatParser : this.formatParsers) {
            if (!formatParser.supportFormatParts() || !formatParser.supports(formatParts)) continue;
            formatDTO = formatParser.parse(formatParts);
            break;
        }
        if (formatDTO == null) {
            formatDTO = this.defaultFormatParser.parse(formatParts);
        }
        return formatDTO;
    }

    public Map<String, FormatDTO> getFormatDTOMap() {
        return this.formatDTOMap;
    }

    @Override
    public boolean supports(String[] format) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FormatDTO parse(String[] formats) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

