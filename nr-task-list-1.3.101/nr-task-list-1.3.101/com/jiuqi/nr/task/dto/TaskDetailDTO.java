/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.TaskGatherType
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.task.dto.AutoCloseFill;
import com.jiuqi.nr.task.dto.AutoStartFill;
import com.jiuqi.nr.task.dto.TaskDTO;
import com.jiuqi.nr.task.dto.TaskDimension;
import com.jiuqi.nr.task.web.vo.TaskOrgListVO;
import java.util.List;
import org.springframework.util.StringUtils;

public class TaskDetailDTO
extends TaskDTO {
    private String dataScheme;
    private String dataSchemeTitle;
    private TaskDimension dw;
    private List<TaskDimension> dims;
    private PeriodType periodType;
    private Integer periodOffset;
    private String starterPeriod;
    private String endPeriod;
    private String dateTime;
    private AutoCloseFill autoCloseFill;
    private AutoStartFill autoStartFill;
    private String measureUnit;
    private TaskGatherType gatherType;
    private FormulaSyntaxStyle formulaSyntaxStyle;
    private Boolean enableEFDC;
    private String entityViewsInEFDC;
    private List<TaskOrgListVO> taskOrgList;

    private TaskDetailDTO(Builder builder) {
        this.setKey(builder.key);
        this.setCode(builder.code);
        this.setOrder(builder.order);
        this.setGroup(builder.group);
        this.setTitle(builder.title);
        this.setUpdateUser(builder.updateUser);
        this.setUpdateTime(builder.updateTime);
        this.dataScheme = builder.dataScheme;
        this.dataSchemeTitle = builder.dataSchemeTitle;
        this.dw = builder.dw;
        this.dims = builder.dims;
        this.periodType = builder.periodType;
        this.periodOffset = builder.periodOffset;
        this.starterPeriod = builder.starterPeriod;
        this.endPeriod = builder.endPeriod;
        this.dateTime = builder.dateTime;
        this.autoCloseFill = builder.autoCloseFill;
        this.autoStartFill = builder.autoStartFill;
        this.measureUnit = builder.measureUnit;
        this.gatherType = builder.gatherType;
        this.formulaSyntaxStyle = builder.formulaSyntaxStyle;
        this.enableEFDC = builder.enableEFDC;
        this.entityViewsInEFDC = builder.entityViewsInEFDC;
        this.taskOrgList = builder.taskOrgList;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public TaskDimension getDw() {
        return this.dw;
    }

    public List<TaskDimension> getDims() {
        return this.dims;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public String getStarterPeriod() {
        return this.starterPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public AutoCloseFill getAutoCloseFill() {
        return this.autoCloseFill;
    }

    public AutoStartFill getAutoStartFill() {
        return this.autoStartFill;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public TaskGatherType getGatherType() {
        return this.gatherType;
    }

    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle;
    }

    public Boolean getEnableEFDC() {
        return this.enableEFDC;
    }

    public String getEntityViewsInEFDC() {
        return this.entityViewsInEFDC;
    }

    public List<TaskOrgListVO> getTaskOrgList() {
        return this.taskOrgList;
    }

    public static final class Builder {
        private String key;
        private String code;
        private String title;
        private String order;
        private String group;
        private String updateTime;
        private String updateUser;
        private String dataScheme;
        private String dataSchemeTitle;
        private TaskDimension dw;
        private List<TaskDimension> dims;
        private PeriodType periodType;
        private Integer periodOffset;
        private String starterPeriod;
        private String endPeriod;
        private String dateTime;
        private AutoCloseFill autoCloseFill;
        private AutoStartFill autoStartFill;
        private String measureUnit;
        private TaskGatherType gatherType;
        private FormulaSyntaxStyle formulaSyntaxStyle;
        private Boolean enableEFDC;
        private String entityViewsInEFDC;
        private List<TaskOrgListVO> taskOrgList;

        public Builder(String dataScheme) {
            this.dataScheme = dataScheme;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setOrder(String order) {
            this.order = order;
            return this;
        }

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public Builder setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
            return this;
        }

        public Builder setDataSchemeTitle(String dataSchemeTitle) {
            this.dataSchemeTitle = dataSchemeTitle;
            return this;
        }

        public Builder setDw(TaskDimension dw) {
            this.dw = dw;
            return this;
        }

        public Builder setDims(List<TaskDimension> dims) {
            this.dims = dims;
            return this;
        }

        public Builder setPeriodType(PeriodType periodType) {
            this.periodType = periodType;
            return this;
        }

        public Builder setPeriodOffset(Integer periodOffset) {
            this.periodOffset = periodOffset;
            return this;
        }

        public Builder setStarterPeriod(String starterPeriod) {
            this.starterPeriod = starterPeriod;
            return this;
        }

        public Builder setEndPeriod(String endPeriod) {
            this.endPeriod = endPeriod;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setAutoCloseFill(AutoCloseFill autoCloseFill) {
            if (autoCloseFill == null) {
                this.setAutoCloseFill(new AutoCloseFill());
            } else {
                this.autoCloseFill = autoCloseFill;
            }
            return this;
        }

        public Builder setAutoStartFill(AutoStartFill autoStartFill) {
            if (autoStartFill == null) {
                this.setAutoStartFill(new AutoStartFill());
            } else {
                this.autoStartFill = autoStartFill;
            }
            return this;
        }

        public Builder setMeasureUnit(String measureUnit) {
            if (!StringUtils.hasLength(measureUnit)) {
                this.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;YUAN");
            } else {
                this.measureUnit = measureUnit;
            }
            return this;
        }

        public Builder setGatherType(TaskGatherType gatherType) {
            if (gatherType == null) {
                this.setGatherType(TaskGatherType.TASK_GATHER_MANUAL);
            } else {
                this.gatherType = gatherType;
            }
            return this;
        }

        public Builder setFormulaSyntaxStyle(FormulaSyntaxStyle formulaSyntaxStyle) {
            if (formulaSyntaxStyle == null) {
                this.setFormulaSyntaxStyle(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION);
            } else {
                this.formulaSyntaxStyle = formulaSyntaxStyle;
            }
            return this;
        }

        public Builder setEnableEFDC(Boolean enableEFDC) {
            if (enableEFDC == null) {
                this.setEnableEFDC(false);
            } else {
                this.enableEFDC = enableEFDC;
            }
            return this;
        }

        public Builder setEntityViewsInEFDC(String entityViewsInEFDC) {
            this.entityViewsInEFDC = entityViewsInEFDC;
            return this;
        }

        public Builder setTaskOrgList(List<TaskOrgListVO> orgList) {
            this.taskOrgList = orgList;
            return this;
        }

        public TaskDetailDTO build() {
            return new TaskDetailDTO(this);
        }
    }
}

