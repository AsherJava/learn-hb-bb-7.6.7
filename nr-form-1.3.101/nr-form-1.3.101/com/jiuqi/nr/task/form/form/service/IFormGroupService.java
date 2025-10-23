/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.form.service;

import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormGroupDTO;
import java.util.List;

public interface IFormGroupService {
    public String insertGroup(FormGroupDTO var1);

    public String insertDefaultGroup(String var1);

    public boolean checkHasGroup(FormGroupDTO var1);

    public void insertGroupLink(FormDTO var1);

    public void updateGroup(FormGroupDTO var1);

    public void deleteGroup(String var1);

    public void moveGroup(ItemOrderMoveDTO var1);

    public List<FormGroupDTO> initGroupTree(String var1);

    public FormGroupDTO query(String var1);

    public List<FormGroupDTO> loadGroupChildren(String var1, String var2);

    public List<FormGroupDTO> listFormGroupByScheme(String var1);

    public FormGroupDTO getFormGroupByFrom(String var1);

    public boolean existFormInGroup(String var1);

    public List<FormGroupDTO> queryRoot(String var1);
}

