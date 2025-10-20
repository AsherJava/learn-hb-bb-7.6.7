/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.vo.GroupVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 */
package com.jiuqi.va.query.tree.service;

import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.vo.GroupVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface MenuTreeService {
    public List<MenuTreeVO> getTree();

    public MenuTreeVO getMenuByParam(List<MenuTreeVO> var1, List<MenuTreeVO> var2, Predicate<MenuTreeVO> var3);

    public MenuTreeVO getMenuVO(String var1);

    public List<MenuTreeVO> treeInit();

    public List<TemplateInfoVO> listInit();

    public String groupSave(GroupVO var1);

    public void groupUpdate(GroupVO var1);

    public void groupDelete(String var1);

    public void templateDelete(String var1);

    public void saveMenuTree(List<MenuTreeVO> var1, Map<String, TemplateInfoVO> var2, List<String> var3, Map<String, String> var4);

    public List<MenuTreeVO> treeInit(List<String> var1);

    public void querySave(TemplateInfoVO var1);
}

