/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.designer.web.facade.ParameterObj
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.facade.ParameterObj;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class AdaptFormulaFunctionVO {
    @ApiModelProperty(value="\u6807\u8bc6", notes="\u6807\u8bc6")
    @JsonProperty(value="Name")
    private String name;
    @ApiModelProperty(value="\u6807\u9898")
    @JsonProperty(value="Title")
    private String title;
    @ApiModelProperty(value="\u5206\u7ec4")
    @JsonProperty(value="Group")
    private String group;
    @ApiModelProperty(value="\u63cf\u8ff0")
    @JsonProperty(value="Description")
    private String description;
    @ApiModelProperty(value="\u53c2\u6570\u4e2a\u6570")
    @JsonProperty(value="ParameterCount")
    private int parameterCount;
    @ApiModelProperty(value="\u53c2\u6570\u5217\u8868")
    @JsonProperty(value="ParameterList")
    private List<ParameterObj> parameterList;
    @ApiModelProperty(value="\u591a\u91cd\u5faa\u73af\u53c2\u6570")
    @JsonProperty(value="IsInfiniteParameter")
    private boolean infiniteParameter;
    @ApiModelProperty(value="\u591a\u91cd\u5faa\u73af\u53c2\u6570\u4e2a\u6570")
    @JsonProperty(value="InfiniteParameterCount")
    private int infiniteParameterCount;
    @ApiModelProperty(value="\u8fd4\u56de\u503c\u7c7b\u578b")
    @JsonProperty(value="ResultType")
    private int resultType;
    @ApiModelProperty(value="\u5e94\u7528")
    @JsonProperty(value="Application")
    private String application;
    @ApiModelProperty(value="\u793a\u4f8b")
    @JsonProperty(value="Example")
    private String Example;
    @ApiModelProperty(value="\u51fd\u6570")
    @JsonProperty(value="Function")
    private String Function;
    @ApiModelProperty(value="\u53c2\u6570")
    @JsonProperty(value="Parameter")
    private String Parameter;

    @JsonIgnore
    public AdaptFormulaFunctionVO(String name, String title, String group, String desp, int paramCount) {
        this.name = name;
        this.title = title;
        this.group = group;
        this.description = desp;
        this.parameterCount = paramCount;
    }

    @JsonIgnore
    public AdaptFormulaFunctionVO(String name, String title, String group, List<ParameterObj> parameterList, String application, String example, String function, String parameter, String desp) {
        this.name = name;
        this.title = title;
        this.group = group;
        this.parameterList = parameterList;
        this.application = application;
        this.Example = example;
        this.Function = function;
        this.Parameter = parameter;
        this.description = desp;
    }

    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getExample() {
        return this.Example;
    }

    public void setExample(String example) {
        this.Example = example;
    }

    public String getFunction() {
        return this.Function;
    }

    public void setFunction(String function) {
        this.Function = function;
    }

    public String getParameter() {
        return this.Parameter;
    }

    public void setParameter(String parameter) {
        this.Parameter = parameter;
    }

    @JsonIgnore
    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getGroup() {
        return this.group;
    }

    @JsonIgnore
    public void setGroup(String group) {
        this.group = group;
    }

    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public int getParameterCount() {
        return this.parameterCount;
    }

    @JsonIgnore
    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    @JsonIgnore
    public List<ParameterObj> getParameterList() {
        return this.parameterList;
    }

    @JsonIgnore
    public void setParameterList(List<ParameterObj> parameterList) {
        this.parameterList = parameterList;
    }

    @JsonIgnore
    public boolean isIsInfiniteParameter() {
        return this.infiniteParameter;
    }

    @JsonIgnore
    public void setIsInfiniteParameter(boolean isInfiniteParameter) {
        this.infiniteParameter = isInfiniteParameter;
    }

    @JsonIgnore
    public int getInfiniteParameterCount() {
        return this.infiniteParameterCount;
    }

    @JsonIgnore
    public void setInfiniteParameterCount(int infiniteParameterCount) {
        this.infiniteParameterCount = infiniteParameterCount;
    }

    @JsonIgnore
    public int getResultType() {
        return this.resultType;
    }

    @JsonIgnore
    public void setResultType(int resultType) {
        this.resultType = resultType;
    }
}

