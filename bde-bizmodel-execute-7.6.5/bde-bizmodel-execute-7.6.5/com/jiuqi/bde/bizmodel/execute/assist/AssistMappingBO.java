/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.bizmodel.execute.assist;

import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;

public class AssistMappingBO<AccountAssist extends IAcctAssist> {
    private DimMappingVO assistMapping;
    private DimensionVO dimension;
    private AccountAssist accountAssist;
    private Dimension executeDim;

    public AssistMappingBO(DimMappingVO assistMapping, DimensionVO dimension, AccountAssist accountAssist, Dimension executeDim) {
        this.assistMapping = assistMapping;
        this.dimension = dimension;
        this.accountAssist = accountAssist;
        this.executeDim = executeDim;
    }

    public String getAssistCode() {
        return this.assistMapping.getCode();
    }

    public String getAssistName() {
        return this.dimension.getTitle();
    }

    public String getAssistSql() {
        return this.assistMapping.getAdvancedSql();
    }

    public String getAccountAssistCode() {
        return StringUtils.isEmpty((String)this.assistMapping.getOdsFieldName()) ? this.accountAssist.getCode() : this.assistMapping.getOdsFieldName();
    }

    public String getAccountAssistName() {
        return StringUtils.isEmpty((String)this.assistMapping.getOdsFieldTitle()) ? this.accountAssist.getName() : this.assistMapping.getOdsFieldTitle();
    }

    public AccountAssist getAccountAssist() {
        return this.accountAssist;
    }

    public Dimension getExecuteDim() {
        return this.executeDim;
    }

    public String toString() {
        return "AssistMappingBO [assistMapping=" + this.assistMapping + ", dimension=" + this.dimension + ", acctAssist=" + this.accountAssist + "]";
    }
}

