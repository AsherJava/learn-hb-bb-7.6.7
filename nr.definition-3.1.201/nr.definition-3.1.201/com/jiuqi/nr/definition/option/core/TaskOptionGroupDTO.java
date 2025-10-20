/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import java.util.List;

public class TaskOptionGroupDTO {
    private String title;
    private List<String> option;
    private boolean isCustom;
    private String pluginName;
    private String expose;
    private String newPluginName;
    private String newExpose;

    public TaskOptionGroupDTO() {
    }

    public TaskOptionGroupDTO(TaskOptionGroup group) {
        this.title = group.getTitle();
        this.isCustom = group.isCustom();
        this.pluginName = group.getPluginName();
        this.expose = group.getExpose();
        this.newPluginName = group.getNewPluginName();
        this.newExpose = group.getNewExpose();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean custom) {
        this.isCustom = custom;
    }

    public List<String> getOption() {
        return this.option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getExpose() {
        return this.expose;
    }

    public void setExpose(String expose) {
        this.expose = expose;
    }

    public String getNewPluginName() {
        return this.newPluginName;
    }

    public void setNewPluginName(String newPluginName) {
        this.newPluginName = newPluginName;
    }

    public String getNewExpose() {
        return this.newExpose;
    }

    public void setNewExpose(String newExpose) {
        this.newExpose = newExpose;
    }
}

