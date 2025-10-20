/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.CheckResult
 *  com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.service.IModelChecker
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom;

import com.jiuqi.budget.param.hypermodel.domain.CheckResult;
import com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.service.IModelChecker;
import org.springframework.stereotype.Component;

@Component
public class GcCustomModelChecker
implements IModelChecker {
    public int getOrder() {
        return 0;
    }

    public boolean isMatch(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return DataSchemeType.OTHER.getHexCode().equals(hyperDataSchemeDO.getDataSchemeType().getHexCode());
    }

    public CheckResult doCheckCreate(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return CheckResult.success();
    }

    public CheckResult doCheckUpdate(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return CheckResult.success();
    }

    public CheckResult doCheckDelete(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return CheckResult.fail((String)"\u56fa\u5316\u6a21\u578b\u4e0d\u5141\u8bb8\u88ab\u5220\u9664");
    }
}

