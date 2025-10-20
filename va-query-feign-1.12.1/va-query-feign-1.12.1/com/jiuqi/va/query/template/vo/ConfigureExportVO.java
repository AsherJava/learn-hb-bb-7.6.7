/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.template.vo.TemplateContentVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;

public class ConfigureExportVO {
    private List<MenuTreeVO> menuTree;
    private List<TemplateContentVO> templateContents;
    private String importStrategy;

    public List<MenuTreeVO> getMenuTree() {
        return this.menuTree;
    }

    public void setMenuTree(List<MenuTreeVO> menuTree) {
        this.menuTree = menuTree;
    }

    public List<TemplateContentVO> getTemplateContents() {
        return this.templateContents;
    }

    public void setTemplateContents(List<TemplateContentVO> templateContents) {
        this.templateContents = templateContents;
    }

    public String getImportStrategy() {
        return this.importStrategy;
    }

    public void setImportStrategy(String importStrategy) {
        this.importStrategy = importStrategy;
    }

    public String toString() {
        return "ConfigureExportVO{menuTree=" + this.menuTree + ", templateContents=" + this.templateContents + ", importStrategy='" + this.importStrategy + '\'' + '}';
    }
}

