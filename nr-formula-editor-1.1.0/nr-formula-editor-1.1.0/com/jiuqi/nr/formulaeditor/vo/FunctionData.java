/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.function.func.doc.FuncParamDoc
 *  com.jiuqi.nr.function.func.doc.IFuncDoc
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.formulaeditor.vo.ParameterData;
import com.jiuqi.nr.function.func.doc.FuncParamDoc;
import com.jiuqi.nr.function.func.doc.IFuncDoc;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class FunctionData {
    private String name;
    private String title;
    private String group;
    private String description;
    private int parameterCount;
    private List<ParameterData> parameterList;
    private String resultType;
    private String application;
    private String example;
    private String function;
    private String parameter;

    public FunctionData() {
    }

    public FunctionData(String name, String title, String group, String desp, int paramCount) {
        this.name = name;
        this.title = title;
        this.group = group;
        this.description = desp;
        this.parameterCount = paramCount;
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

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParameterCount() {
        return this.parameterCount;
    }

    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    public List<ParameterData> getParameterList() {
        return this.parameterList;
    }

    public void setParameterList(List<ParameterData> parameterList) {
        this.parameterList = parameterList;
    }

    public String getResultType() {
        return this.resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getExample() {
        return this.example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public static FunctionData getInstance(IFuncDoc doc) {
        FunctionData data = new FunctionData();
        data.setName(doc.name());
        data.setTitle(doc.sortName());
        data.setGroup(doc.category());
        data.setDescription(doc.desc());
        StringBuilder function = new StringBuilder(doc.name());
        if (!CollectionUtils.isEmpty(doc.params())) {
            ArrayList<ParameterData> params = new ArrayList<ParameterData>(doc.params().size());
            data.setParameterCount(doc.params().size());
            function.append("(");
            for (FuncParamDoc funcParam : doc.params()) {
                function.append(funcParam.getName()).append(",");
                ParameterData paramData = new ParameterData();
                paramData.setName(funcParam.getName());
                paramData.setTitle(funcParam.getSortName());
                paramData.setDataType(funcParam.getType());
                params.add(paramData);
            }
            function.deleteCharAt(function.length() - 1);
            function.append(")");
            data.setParameterList(params);
        }
        data.setResultType(doc.result());
        data.setExample(doc.example());
        data.setFunction(function.toString());
        return data;
    }
}

