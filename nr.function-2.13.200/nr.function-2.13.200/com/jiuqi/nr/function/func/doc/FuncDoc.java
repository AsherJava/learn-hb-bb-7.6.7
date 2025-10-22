/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.doc;

import com.jiuqi.nr.function.func.doc.FuncParamDoc;
import com.jiuqi.nr.function.func.doc.IFuncDoc;
import java.util.ArrayList;
import java.util.List;

public class FuncDoc
implements IFuncDoc {
    private String name;
    private String sortName;
    private String category;
    private String desc;
    private List<FuncParamDoc> params;
    private String result;
    private String example;
    private boolean deprecated;

    public void setName(String name) {
        this.name = name;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setParams(List<FuncParamDoc> params) {
        this.params = params;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String sortName() {
        return this.sortName;
    }

    @Override
    public String category() {
        return this.category;
    }

    @Override
    public String desc() {
        return this.desc;
    }

    @Override
    public List<FuncParamDoc> params() {
        return this.params;
    }

    @Override
    public String result() {
        return this.result;
    }

    @Override
    public String example() {
        return this.example;
    }

    @Override
    public boolean deprecated() {
        return this.deprecated;
    }

    public void addParam(FuncParamDoc paramDoc) {
        if (this.params == null) {
            this.params = new ArrayList<FuncParamDoc>();
        }
        this.params.add(paramDoc);
    }
}

