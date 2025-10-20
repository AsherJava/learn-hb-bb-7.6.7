/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.bde.penetrate.impl.i18n;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeBizModelTitleI18NResource
implements I18NResource {
    private static final long serialVersionUID = 5755783235021897L;

    public String name() {
        return "BDE/\u900f\u89c6\u6807\u9898";
    }

    public String getNameSpace() {
        return "BDE";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        for (ComputationModelEnum bizModelEnum : ComputationModelEnum.values()) {
            resourceObjects.add(new I18NResourceItem(BdeBizModelTitleI18NResource.getResourceKey(bizModelEnum.getCode(), PenetrateTypeEnum.BALANCE), BdeBizModelTitleI18NResource.getResourceName(bizModelEnum, PenetrateTypeEnum.BALANCE)));
            resourceObjects.add(new I18NResourceItem(BdeBizModelTitleI18NResource.getResourceKey(bizModelEnum.getCode(), PenetrateTypeEnum.DETAILLEDGER), BdeBizModelTitleI18NResource.getResourceName(bizModelEnum, PenetrateTypeEnum.DETAILLEDGER)));
            resourceObjects.add(new I18NResourceItem(BdeBizModelTitleI18NResource.getResourceKey(bizModelEnum.getCode(), PenetrateTypeEnum.VOUCHER), BdeBizModelTitleI18NResource.getResourceName(bizModelEnum, PenetrateTypeEnum.VOUCHER)));
        }
        return resourceObjects;
    }

    public static String getResourceKey(String bizModelCode, PenetrateTypeEnum penetrateType) {
        Assert.isNotEmpty((String)bizModelCode);
        Assert.isNotNull((Object)((Object)penetrateType));
        return String.format(String.format("%1$s-%2$s", new Object[]{bizModelCode, penetrateType}), new Object[0]);
    }

    public static String getResourceName(ComputationModelEnum bizModelEnum, PenetrateTypeEnum penetrateType) {
        Assert.isNotNull((Object)bizModelEnum);
        Assert.isNotNull((Object)((Object)penetrateType));
        if (penetrateType == PenetrateTypeEnum.VOUCHER) {
            return penetrateType.getName();
        }
        String bizModelName = null;
        bizModelName = bizModelEnum.getName().endsWith("\u4f59\u989d") ? bizModelEnum.getName().replace("\u4f59\u989d", "") : bizModelEnum.getName();
        return bizModelName + penetrateType.getName();
    }

    public static String getResourceName(String bizModelKey, PenetrateTypeEnum penetrateType) {
        Assert.isNotEmpty((String)bizModelKey);
        Assert.isNotNull((Object)((Object)penetrateType));
        ComputationModelEnum bizModel = ComputationModelEnum.findEnumByCode((String)bizModelKey);
        if (bizModel == null) {
            return penetrateType.getName();
        }
        return BdeBizModelTitleI18NResource.getResourceName(bizModel, penetrateType);
    }
}

