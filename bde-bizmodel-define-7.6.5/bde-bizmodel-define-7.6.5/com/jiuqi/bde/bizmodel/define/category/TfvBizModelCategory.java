/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory
 */
package com.jiuqi.bde.bizmodel.define.category;

import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory;
import org.springframework.stereotype.Component;

@Component
public class TfvBizModelCategory
implements IBizModelCategory {
    public String getCode() {
        return BizModelCategoryEnum.BIZMODEL_TFV.getCode();
    }

    public String getName() {
        return BizModelCategoryEnum.BIZMODEL_TFV.getName();
    }

    public String getProdLine() {
        return "@bde";
    }

    public String getAppName() {
        return "bde-bizmodel";
    }

    public Integer getOrder() {
        return 4;
    }
}

