/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import nr.single.para.upload.domain.UploadFileDTO;

public class CreateParamDTO
extends UploadFileDTO {
    private Boolean createDataScheme;
    private String dataSchemeKey;
    private String mainDimension;
    private String period;
    private String dimensions;
    private String code;
    private String title;
    private String prefix;
    private String formSchemeTitle;
    private String taskCode;
    private String taskTitle;
    private String fromPeriod;
    private String toPeriod;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getCreateDataScheme() {
        return this.createDataScheme;
    }

    public void setCreateDataScheme(Boolean createDataScheme) {
        this.createDataScheme = createDataScheme;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getMainDimension() {
        return this.mainDimension;
    }

    public void setMainDimension(String mainDimension) {
        this.mainDimension = mainDimension;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    @Override
    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    @Override
    public String toString() {
        return "CreateParamDTO{createDataScheme=" + this.createDataScheme + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", mainDimension='" + this.mainDimension + '\'' + ", period='" + this.period + '\'' + ", dimensions=" + this.dimensions + '}';
    }
}

