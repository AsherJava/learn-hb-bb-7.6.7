/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 */
package com.jiuqi.nr.mapping2.dto;

import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import org.springframework.beans.BeanUtils;

public class NrMappingSchemeDTO
extends MappingScheme {
    private String task;
    private String formScheme;
    private String type;

    public NrMappingSchemeDTO(String code, String title, String group, String orgName, String task, String formScheme, String type) {
        this.setCode(code);
        this.setTitle(title);
        this.setGroup(group);
        this.setOrgName(orgName);
        this.setTask(task);
        this.setFormScheme(formScheme);
        this.setType(type);
    }

    public NrMappingSchemeDTO(MappingScheme scheme) {
        NrMappingParam nrMappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (nrMappingParam != null) {
            this.task = nrMappingParam.getTaskKey();
            this.formScheme = nrMappingParam.getFormSchemeKey();
            this.type = nrMappingParam.getType();
        }
        BeanUtils.copyProperties(scheme, (Object)this);
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

