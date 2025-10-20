/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.txt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SeparatedValueTextParser {
    private char separator;
    private boolean isConvertEscapeCharactor;
    private boolean ignoreEmptyLine = false;
    private ArrayList<String> lineBuffer = null;
    private StringBuffer fieldBuffer = null;
    private int lineNumber = -1;

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setConvertEscapeCharactor(boolean isConvertEscapeCharactor) {
        this.isConvertEscapeCharactor = isConvertEscapeCharactor;
    }

    public void setIgnoreEmptyLine(boolean ignoreEmptyLine) {
        this.ignoreEmptyLine = ignoreEmptyLine;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void parse(InputStream is, String charset, ITextRecordProcessor processor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));){
            this.parse(reader, processor);
        }
    }

    private void init() {
        this.lineBuffer = new ArrayList();
        this.fieldBuffer = new StringBuffer(32);
        this.lineNumber = 0;
    }

    public void parse(BufferedReader reader, ITextRecordProcessor processor) throws IOException {
        int i;
        if (this.separator == '\u0000') {
            throw new IOException("\u672a\u8bbe\u7f6e\u5206\u9694\u7b26");
        }
        this.init();
        while ((i = reader.read()) != -1) {
            char c = (char)i;
            if (c == '\r') continue;
            if (c == '\n') {
                this.fillFieldToLine();
                boolean isContinue = this.toProcessor(processor);
                if (isContinue) continue;
                processor.complete(false);
                return;
            }
            if (c == this.separator) {
                this.fillFieldToLine();
                continue;
            }
            if (c == '\\') {
                if (this.isConvertEscapeCharactor) {
                    i = reader.read();
                    if (i == -1) break;
                    c = (char)i;
                    c = this.convertEscapeCharactor(c);
                    this.fillCharactorToField(c);
                    continue;
                }
                this.fillCharactorToField(c);
                continue;
            }
            this.fillCharactorToField(c);
        }
        this.fillFieldToLine();
        if (!this.lineBuffer.isEmpty()) {
            boolean b = this.toProcessor(processor);
            processor.complete(!b);
        } else {
            processor.complete(true);
        }
    }

    private char convertEscapeCharactor(char c) {
        switch (c) {
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
        }
        return c;
    }

    private void fillCharactorToField(char c) {
        this.fieldBuffer.append(c);
    }

    private void fillFieldToLine() {
        this.lineBuffer.add(this.fieldBuffer.toString());
        this.fieldBuffer.delete(0, this.fieldBuffer.length());
    }

    private boolean toProcessor(ITextRecordProcessor processor) {
        if (this.ignoreEmptyLine && (this.lineBuffer.isEmpty() || this.lineBuffer.size() == 1 && this.lineBuffer.get(0).isEmpty())) {
            return true;
        }
        String[] separatedValues = this.lineBuffer.toArray(new String[this.lineBuffer.size()]);
        this.lineBuffer.clear();
        return processor.process(this.lineNumber++, separatedValues);
    }

    public static interface ITextRecordProcessor {
        public boolean process(int var1, String[] var2);

        public void complete(boolean var1);
    }
}

