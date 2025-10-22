/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.nr.bpm.dataflow.serviceImpl.CustomActionState;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.io.Serializable;

public class ActionStateBean
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String titile;
    private String icon;
    private String color;
    private boolean forceUpload;
    private String taskKey;
    private String taskCode;
    private String preNode;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitile() {
        return this.titile;
    }

    public String getTitile(String formSchemeKey) {
        CustomActionState bean = (CustomActionState)BeanUtil.getBean(CustomActionState.class);
        String name = bean.getCustomStateDes(formSchemeKey, this.code, this.taskCode);
        if (name != null) {
            this.titile = name;
        }
        return this.titile;
    }

    public void setTitile(String formSchemeKey, String title) {
        String tempTitle = title;
        CustomActionState bean = (CustomActionState)BeanUtil.getBean(CustomActionState.class);
        String name = bean.getCustomStateDes(formSchemeKey, this.code, this.taskCode);
        if (name != null) {
            tempTitle = name;
        }
        this.titile = tempTitle;
    }

    public void setTitile(String formSchemeKey, String code, String title) {
        String tempTitle = title;
        CustomActionState bean = (CustomActionState)BeanUtil.getBean(CustomActionState.class);
        String name = bean.getCustomStateDes(formSchemeKey, code, this.taskCode);
        if (name != null) {
            tempTitle = name;
        }
        this.titile = tempTitle;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getIcon() {
        CustomActionState bean = (CustomActionState)BeanUtil.getBean(CustomActionState.class);
        String icon2 = bean.getIcon(this.code);
        if (icon2 != null) {
            this.icon = icon2;
        }
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        CustomActionState bean = (CustomActionState)BeanUtil.getBean(CustomActionState.class);
        String color2 = bean.getColor(this.code);
        if (color2 != null) {
            this.color = color2;
        }
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isForceUpload() {
        return this.forceUpload;
    }

    public void setForceUpload(boolean forceUpload) {
        this.forceUpload = forceUpload;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPreNode() {
        return this.preNode;
    }

    public void setPreNode(String preNode) {
        this.preNode = preNode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}

