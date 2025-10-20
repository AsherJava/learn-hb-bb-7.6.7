/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.extend.DataModelBizType
 */
package com.jiuqi.va.datamodel.biztype;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelBizType;
import org.springframework.stereotype.Component;

@Component(value="vaDataModelOtherBizType")
public class OtherBizType
implements DataModelBizType {
    public String getName() {
        return DataModelType.BizType.OTHER.toString();
    }

    public String getTitle() {
        return DataModelCoreI18nUtil.getMessage("datamodel.attribute.biztype.other", new Object[0]);
    }

    public int getOrdinal() {
        return 90;
    }
}

