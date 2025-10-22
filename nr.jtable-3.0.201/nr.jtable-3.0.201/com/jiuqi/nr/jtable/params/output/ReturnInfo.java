/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.jtable.params.output.EntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel(value="ReturnInfo", description="\u8fd4\u56de\u6570\u636e")
public class ReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u4e3b\u4f53\u6570\u636e\u679a\u4e3e\u6761\u76ee", name="entity")
    private EntityData entity;
    @ApiModelProperty(value="\u4fdd\u5b58\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u9519\u8bef\u4fe1\u606f", name="commitError")
    private String commitError;
    private List<FormDefine> formDefine;
    private List<FormGroupDefine> formGroupDefine;
    private List<String> taskKeyList;
    private Map<String, String> formulaMeansMap;
    private List<String> stateMessage;

    public ReturnInfo() {
    }

    public ReturnInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EntityData getEntity() {
        return this.entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }

    public String getCommitError() {
        return this.commitError;
    }

    public void setCommitError(String commitError) {
        this.commitError = commitError;
    }

    public List<FormDefine> getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(List<FormDefine> formDefine) {
        this.formDefine = formDefine;
    }

    public List<FormGroupDefine> getFormGroupDefine() {
        return this.formGroupDefine;
    }

    public void setFormGroupDefine(List<FormGroupDefine> formGroupDefine) {
        this.formGroupDefine = formGroupDefine;
    }

    public List<String> getTaskKeyList() {
        return this.taskKeyList;
    }

    public void setTaskKeyList(List<String> taskKeyList) {
        this.taskKeyList = taskKeyList;
    }

    public Map<String, String> getFormulaMeansMap() {
        return this.formulaMeansMap;
    }

    public void setFormulaMeansMap(Map<String, String> formulaMeansMap) {
        this.formulaMeansMap = formulaMeansMap;
    }

    public List<String> getStateMessage() {
        return this.stateMessage;
    }

    public void setStateMessage(List<String> stateMessage) {
        this.stateMessage = stateMessage;
    }
}

