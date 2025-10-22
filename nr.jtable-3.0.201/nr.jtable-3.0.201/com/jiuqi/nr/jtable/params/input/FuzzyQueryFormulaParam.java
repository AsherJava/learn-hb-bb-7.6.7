/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FuzzyQueryFormulaParam", description="\u6a21\u7cca\u67e5\u8be2\u516c\u5f0f\u53c2\u6570")
public class FuzzyQueryFormulaParam
extends BatchSaveFormulaCheckDesInfo {
    private String keyWord;
    private List<Integer> uploadCheckTypes = new ArrayList<Integer>();
    private boolean checkDesNull = true;

    public List<Integer> getUploadCheckTypes() {
        return this.uploadCheckTypes;
    }

    public void setUploadCheckTypes(List<Integer> uploadCheckTypes) {
        this.uploadCheckTypes = uploadCheckTypes;
    }

    public boolean isCheckDesNull() {
        return this.checkDesNull;
    }

    public void setCheckDesNull(boolean checkDesNull) {
        this.checkDesNull = checkDesNull;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}

