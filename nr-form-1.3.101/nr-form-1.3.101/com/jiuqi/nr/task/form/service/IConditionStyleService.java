/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import java.util.List;

public interface IConditionStyleService {
    public void save(String var1, List<ConditionStyleDTO> var2);

    public void insert(List<ConditionStyleDTO> var1);

    public void update(List<ConditionStyleDTO> var1);

    public void delete(List<ConditionStyleDTO> var1);

    public List<ConditionStyleDTO> getByPos(String var1, int var2, int var3);

    public List<ConditionStyleDTO> getByForm(String var1);

    public List<ConditionStyleDTO> getByRegion(String var1, String var2, String var3);

    public boolean checkDifferent(String var1, String var2, String var3);
}

