/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.textprocessor;

public class TextSegment {
    private String text;
    private String result;
    private String errMsg;
    private boolean isFormula;
    static final String FORMULA_BEGIN_TOKEN = "${";
    static final String FORMULA_END_TOKEN = "}";

    public TextSegment(String text) {
        this.text = text;
        this.isFormula = text.startsWith(FORMULA_BEGIN_TOKEN) && text.endsWith(FORMULA_END_TOKEN);
    }

    public TextSegment(String text, boolean isFormula) {
        this.text = text;
        this.isFormula = isFormula;
    }

    public boolean isFormula() {
        return this.isFormula;
    }

    public boolean isErrorBlock() {
        return this.errMsg != null;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getText() {
        return this.text;
    }

    public String getFormula() {
        if (this.isFormula) {
            return this.text.substring(FORMULA_BEGIN_TOKEN.length(), this.text.length() - FORMULA_END_TOKEN.length());
        }
        return null;
    }

    public String getResult() {
        return this.result;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}

