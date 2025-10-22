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
package com.jiuqi.nr.datacheck.entitytree;

import com.jiuqi.nr.datacheck.entitytree.service.ITreeCheckService;
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
public class EntityTreeProvider
implements IMultcheckItemProvider {
    @Autowired
    private ITreeCheckService treeCheckService;
    @Autowired
    private MultCheckResDao multCheckResDao;

    public String getType() {
        return "TREE_STRUCTURE";
    }

    public String getTitle() {
        return "\u6811\u5f62\u7ed3\u6784\u68c0\u67e5";
    }

    public double getOrder() {
        return 8.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-tree-inspection-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        return null;
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        dto.setConfig(sourceItem.getConfig());
        dto.setTitle(sourceItem.getTitle());
        dto.setType(sourceItem.getType());
        return dto;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return null;
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return null;
    }

    public boolean canChangeConfig() {
        return false;
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        return this.treeCheckService.runCheck(param);
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-tree-inspection-plugin", "Result");
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        return this.treeCheckService.getDefaultCheckItem(formSchemeKey);
    }

    public void cleanCheckItemTables(Date date) {
        this.multCheckResDao.deleteByCreatedAt(date);
    }

    public String getEntryView() {
        return "entityTreeCheckInfoView2";
    }

    public boolean supportDimension() {
        return false;
    }
}

