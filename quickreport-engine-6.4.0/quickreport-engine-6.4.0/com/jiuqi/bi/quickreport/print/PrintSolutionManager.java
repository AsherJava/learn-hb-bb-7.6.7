/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.print.IPrintSolution;
import com.jiuqi.bi.quickreport.print.IPrintSolutionProvider;
import com.jiuqi.bi.quickreport.print.PrintException;
import java.util.ArrayList;
import java.util.List;

public class PrintSolutionManager {
    private IPrintSolutionProvider provider;
    private static final PrintSolutionManager instance = new PrintSolutionManager();

    private PrintSolutionManager() {
    }

    public static PrintSolutionManager getInstance() {
        return instance;
    }

    public void setProvider(IPrintSolutionProvider provider) {
        this.provider = provider;
    }

    public IPrintSolutionProvider getProvider() {
        return this.provider;
    }

    public List<IPrintSolution> getPrintSolutions(String id) throws PrintException {
        if (this.provider == null) {
            return new ArrayList<IPrintSolution>();
        }
        return this.provider.getPrintSolutions(id);
    }

    public void print(String qrGuid, String psGuid, GridData gridData) throws PrintException {
        this.print(qrGuid, psGuid, gridData, null);
    }

    public void print(String qrGuid, String psGuid, GridData gridData, Object parentComposite) throws PrintException {
        if (this.provider == null) {
            return;
        }
        this.provider.print(qrGuid, psGuid, gridData, parentComposite);
    }

    public String print(String taskTitle, GridData gridData, byte[] templete) throws PrintException {
        if (this.provider == null) {
            return null;
        }
        return this.provider.print(taskTitle, gridData, templete);
    }
}

