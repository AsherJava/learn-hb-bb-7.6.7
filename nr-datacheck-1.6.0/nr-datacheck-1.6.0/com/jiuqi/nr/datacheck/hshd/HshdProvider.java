/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 */
package com.jiuqi.nr.datacheck.hshd;

import com.jiuqi.nr.datacheck.hshd.service.IHshdService;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HshdProvider
implements IMultcheckItemProvider {
    @Autowired
    private IHshdService hshdService;
    @Autowired
    private MultCheckResDao multCheckResDao;

    public String getType() {
        return "HSHD";
    }

    public String getTitle() {
        return "\u6237\u6570\u6838\u5bf9";
    }

    public double getOrder() {
        return 4.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-household-number-check-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.hshdService.getItemDescribe(formSchemeKey, item);
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
        multCheckItemDTO.setTitle(sourceScheme.getTitle());
        multCheckItemDTO.setType(String.valueOf(sourceScheme.getType()));
        multCheckItemDTO.setConfig(sourceItem.getConfig());
        return multCheckItemDTO;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.hshdService.getRunItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-household-number-check-plugin", "Selector");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        return this.hshdService.runCheck(param);
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-household-number-check-plugin", "Result");
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        return this.hshdService.getDefaultCheckItem(formSchemeKey);
    }

    public void cleanCheckItemTables(Date date) {
        this.multCheckResDao.deleteByCreatedAt(date);
    }

    public boolean supportDimension() {
        return false;
    }
}

