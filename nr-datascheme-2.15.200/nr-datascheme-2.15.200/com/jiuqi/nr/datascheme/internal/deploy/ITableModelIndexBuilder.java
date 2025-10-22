/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import java.util.List;

public interface ITableModelIndexBuilder {
    public DataTableType[] getDoForTableTypes();

    public void doBuild(DataTableDeployObj var1, List<TableModelDeployObj> var2) throws ModelValidateException, JQException;

    public int doCheck(RuntimeDataTableDTO var1) throws JQException;

    public void doRebuild(RuntimeDataTableDTO var1, int var2) throws JQException;
}

