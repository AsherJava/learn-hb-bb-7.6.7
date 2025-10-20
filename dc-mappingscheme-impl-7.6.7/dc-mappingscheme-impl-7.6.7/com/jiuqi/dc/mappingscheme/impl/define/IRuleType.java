/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.common.RuleTypeClass
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.RuleTypeClass;
import java.util.List;

public interface IRuleType {
    public String getCode();

    public String getName();

    public RuleTypeClass getRuleTypeClass();

    public Boolean getItem2Item();

    default public List<SelectOptionVO> isolationDim() {
        return null;
    }
}

