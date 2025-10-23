/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.task.form.formstyle.service;

import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nvwa.cellbook.model.CellBook;

public interface IFormStyleService {
    public void insertDefaultFormStyle(String var1);

    public void insertDefaultFMDMFormStyle(String var1);

    public void saveFormStyle(String var1, FormStyleDTO var2);

    public CellBook getFormStyle(String var1, int var2);
}

