/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package nr.midstore.design.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nvwa.grid2.Grid2Data;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MidstoreFormObj {
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="TaskId")
    private String taskId;
    @JsonProperty(value="FormType")
    private int formType;
    @JsonProperty(value="OwnGroupId")
    private String ownGroupId;
    @JsonProperty(value="FormStyle")
    private Grid2Data formStyle;
    @JsonProperty(value="ID")
    private String iD;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="FormScheme")
    private String formScheme;

    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public String getTaskId() {
        return this.taskId;
    }

    @JsonIgnore
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonIgnore
    public int getFormType() {
        return this.formType;
    }

    @JsonIgnore
    public void setFormType(int formType) {
        this.formType = formType;
    }

    @JsonIgnore
    public String getOwnGroupId() {
        return this.ownGroupId;
    }

    @JsonIgnore
    public void setOwnGroupId(String ownGroupId) {
        this.ownGroupId = ownGroupId;
    }

    @JsonIgnore
    public Grid2Data getFormStyle() {
        return this.formStyle;
    }

    @JsonIgnore
    public void setFormStyle(Grid2Data formStyle) {
        this.formStyle = formStyle;
    }

    @JsonIgnore
    public String getiD() {
        return this.iD;
    }

    @JsonIgnore
    public void setiD(String iD) {
        this.iD = iD;
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
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public String getFormScheme() {
        return this.formScheme;
    }

    @JsonIgnore
    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }
}

