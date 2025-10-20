/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.paramsync.transfer.VaParamTransferFactory
 */
package com.jiuqi.gcreport.formulaschemeconfig.transfermodule;

import com.jiuqi.va.paramsync.transfer.VaParamTransferFactory;
import java.util.ArrayList;
import java.util.List;

public class FormulaSchemeConfigTransferFactory
extends VaParamTransferFactory {
    public List<String> getDependenceFactoryIds() {
        ArrayList<String> dependenceFactoryIds = new ArrayList<String>();
        dependenceFactoryIds.add("DEFINITION_TRANSFER_FACTORY_ID");
        return dependenceFactoryIds;
    }
}

