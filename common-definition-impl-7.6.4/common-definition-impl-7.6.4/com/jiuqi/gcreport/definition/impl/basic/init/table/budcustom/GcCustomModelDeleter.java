/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.service.IModelDeleter
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom;

import com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.service.IModelDeleter;
import org.springframework.stereotype.Component;

@Component
public class GcCustomModelDeleter
implements IModelDeleter {
    public int getOrder() {
        return 0;
    }

    public boolean isMatch(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return DataSchemeType.OTHER.getHexCode().equals(hyperDataSchemeDO.getDataSchemeType().getHexCode());
    }

    public void doDelete(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
    }
}

