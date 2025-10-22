/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.system.check.model.request;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.system.check.common.SCSelectType;
import com.jiuqi.nr.system.check.model.request.EntityObj;
import com.jiuqi.nr.system.check.model.request.FormObj;
import java.util.Map;

public class DeleteDataObj {
    private String task;
    private String scheme;
    private String period;
    private FormObj forms;
    private Map<String, EntityObj> entitys;
    private String conditions;

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public FormObj getForms() {
        return this.forms;
    }

    public void setForms(FormObj forms) {
        this.forms = forms;
    }

    public Map<String, EntityObj> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(Map<String, EntityObj> entitys) {
        this.entitys = entitys;
    }

    public boolean isNotEmpty() {
        boolean entityDataNotEmpty;
        boolean taskKeyNotEmpty = StringUtils.isNotEmpty((String)this.task);
        boolean formSchemeKeyNotEmpty = StringUtils.isNotEmpty((String)this.scheme);
        boolean periodValueNotEmpty = StringUtils.isNotEmpty((String)this.period);
        boolean formDataNotEmpty = this.forms != null && (SCSelectType.ALL.getName().equals(this.forms.getFormType()) || SCSelectType.CHOOSE.getName().equals(this.forms.getFormType()) && this.forms.getSelected() != null && this.forms.getSelected().size() > 0);
        boolean bl = entityDataNotEmpty = this.entitys != null && this.entitys.size() > 0;
        return taskKeyNotEmpty && formSchemeKeyNotEmpty && periodValueNotEmpty && formDataNotEmpty && entityDataNotEmpty;
    }

    public boolean isSelectAll() {
        if (this.forms != null && SCSelectType.ALL.getName().equals(this.forms.getFormType()) && this.entitys != null && this.entitys.size() > 0) {
            for (EntityObj entityObj : this.entitys.values()) {
                if (SCSelectType.ALL.getName().equals(entityObj.getEntityType())) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

