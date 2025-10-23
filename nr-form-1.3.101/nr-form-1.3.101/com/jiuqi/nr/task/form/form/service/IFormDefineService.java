/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.task.form.form.service;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.form.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import java.util.List;

public interface IFormDefineService {
    public List<FormItemDTO> queryFormByGroup(String var1);

    public FormDTO getForm(String var1);

    public List<DesignFormDefine> getFormFuzzy(String var1, String var2);

    public boolean updateForm(FormDTO var1);

    public void insertForm(FormDTO var1);

    public boolean deleteForm(String var1);

    public boolean moveForm(ItemOrderMoveDTO var1);

    public boolean changeFormGroup(ItemOrderMoveDTO var1);

    public List<FormItemDTO> queryFormByScheme(String var1);

    public boolean existFMDM(String var1);
}

