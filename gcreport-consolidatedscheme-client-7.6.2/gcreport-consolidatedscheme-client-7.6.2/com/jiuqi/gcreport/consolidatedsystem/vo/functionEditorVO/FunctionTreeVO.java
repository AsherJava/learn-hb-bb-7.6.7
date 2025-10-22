/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO;

import com.jiuqi.bi.syntax.function.IFunction;
import java.util.List;

public class FunctionTreeVO {
    private String title;
    private String code;
    private String defCode;
    private String description;
    private IFunction function;
    private List<FunctionTreeVO> children;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IFunction getFunction() {
        return this.function;
    }

    public void setFunction(IFunction function) {
        this.function = function;
    }

    public List<FunctionTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FunctionTreeVO> children) {
        this.children = children;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefCode() {
        return this.defCode;
    }

    public void setDefCode(String defCode) {
        this.defCode = defCode;
    }
}

