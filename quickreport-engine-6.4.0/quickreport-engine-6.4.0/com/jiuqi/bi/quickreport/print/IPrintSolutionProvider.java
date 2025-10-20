/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.print.IPrintSolution;
import com.jiuqi.bi.quickreport.print.PrintException;
import java.util.List;

public interface IPrintSolutionProvider {
    public String getType();

    public List<IPrintSolution> getPrintSolutions(String var1) throws PrintException;

    public void print(String var1, String var2, GridData var3, Object var4) throws PrintException;

    public String print(String var1, GridData var2, byte[] var3) throws PrintException;
}

