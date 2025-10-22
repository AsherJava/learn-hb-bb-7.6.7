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
package com.jiuqi.nr.datacheck.attachment;

import com.jiuqi.nr.datacheck.attachment.AttachmentConfig;
import com.jiuqi.nr.datacheck.attachment.service.IAttachmentService;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentProvider
implements IMultcheckItemProvider {
    @Autowired
    private IAttachmentService attachDocumentService;
    @Autowired
    private MultCheckResDao multCheckResDao;

    public String getType() {
        return "ATTACHMENT";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u6587\u6863\u68c0\u67e5";
    }

    public double getOrder() {
        return 6.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-attached-document-check-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.attachDocumentService.getItemDescribe(formSchemeKey, item);
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
        multCheckItemDTO.setTitle(sourceScheme.getTitle());
        multCheckItemDTO.setType(String.valueOf(sourceScheme.getType()));
        multCheckItemDTO.setConfig(sourceItem.getConfig());
        return multCheckItemDTO;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.attachDocumentService.getRunItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-attached-document-check-plugin", "Selector");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        return this.attachDocumentService.runCheck(param);
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-attached-document-check-plugin", "Result");
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
        multCheckItemDTO.setTitle("\u9644\u4ef6\u6587\u6863\u68c0\u67e5");
        multCheckItemDTO.setType("ATTACHMENT");
        AttachmentConfig config = new AttachmentConfig();
        config.setAuditScope(Collections.emptyList());
        try {
            multCheckItemDTO.setConfig(SerializeUtil.serializeToJson(config));
        }
        catch (Exception e) {
            multCheckItemDTO.setConfig("");
        }
        return multCheckItemDTO;
    }

    public void cleanCheckItemTables(Date date) {
        this.multCheckResDao.deleteByCreatedAt(date);
    }
}

