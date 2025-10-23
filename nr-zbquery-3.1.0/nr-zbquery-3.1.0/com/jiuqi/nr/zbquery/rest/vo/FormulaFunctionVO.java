/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.nr.zbquery.rest.vo.FormulaParameterVO;
import java.util.ArrayList;
import java.util.List;

public class FormulaFunctionVO {
    private String name;
    private String title;
    private int dataType;
    private List<FormulaParameterVO> parameters;
    private boolean isInfiniteParameter;
    private String category;
    private String desc;

    public FormulaFunctionVO(IFunction func) {
        this.name = func.name();
        this.title = func.title();
        try {
            this.dataType = func.getResultType(null, null);
        }
        catch (SyntaxException e) {
            this.dataType = 0;
        }
        this.isInfiniteParameter = func.isInfiniteParameter();
        this.category = func.category();
        this.desc = func.toDescription();
        if (this.parameters == null) {
            this.parameters = new ArrayList<FormulaParameterVO>();
        }
        for (IParameter parameter : func.parameters()) {
            this.parameters.add(new FormulaParameterVO(parameter));
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List<FormulaParameterVO> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<FormulaParameterVO> parameters) {
        this.parameters = parameters;
    }

    public boolean isInfiniteParameter() {
        return this.isInfiniteParameter;
    }

    public void setInfiniteParameter(boolean infiniteParameter) {
        this.isInfiniteParameter = infiniteParameter;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

