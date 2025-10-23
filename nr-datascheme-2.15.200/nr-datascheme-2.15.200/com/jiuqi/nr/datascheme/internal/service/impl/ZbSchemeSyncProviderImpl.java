/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.zb.scheme.dto.SyncNode
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeSyncProvider
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.zb.scheme.dto.SyncNode;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeSyncProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ZbSchemeSyncProviderImpl
implements IZbSchemeSyncProvider {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    public String getType() {
        return "DATA_SCHEME";
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848";
    }

    public List<SyncNode> listSyncNode(String zbSchemeKey) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        ArrayList<SyncNode> nodes = new ArrayList<SyncNode>();
        for (DesignDataScheme scheme : allDataScheme) {
            if (!zbSchemeKey.equals(scheme.getZbSchemeKey())) continue;
            SyncNode node = new SyncNode();
            node.setKey(scheme.getKey());
            node.setTitle(scheme.getTitle());
            nodes.add(node);
        }
        return nodes;
    }

    public List<SyncNode> listSyncNode(String zbSchemeKey, String version) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        ArrayList<SyncNode> nodes = new ArrayList<SyncNode>();
        for (DesignDataScheme scheme : allDataScheme) {
            if (!zbSchemeKey.equals(scheme.getZbSchemeKey()) || !version.equals(scheme.getZbSchemeVersion())) continue;
            SyncNode node = new SyncNode();
            node.setKey(scheme.getKey());
            node.setTitle(scheme.getTitle());
            nodes.add(node);
        }
        return nodes;
    }

    public List<String> listSyncZbCode(String zbSchemeKey) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        ArrayList<String> codes = new ArrayList<String>();
        for (DesignDataScheme scheme : allDataScheme) {
            if (!zbSchemeKey.equals(scheme.getZbSchemeKey())) continue;
            List allDataField = this.designDataSchemeService.getAllDataFieldByKind(scheme.getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD});
            for (DesignDataField field : allDataField) {
                codes.add(field.getCode());
            }
        }
        return codes;
    }

    public List<String> listSyncZbCode(String zbSchemeKey, String version) {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        ArrayList<String> codes = new ArrayList<String>();
        for (DesignDataScheme scheme : allDataScheme) {
            if (!zbSchemeKey.equals(scheme.getZbSchemeKey()) || !version.equals(scheme.getZbSchemeVersion())) continue;
            List allDataField = this.designDataSchemeService.getAllDataFieldByKind(scheme.getKey(), new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD});
            for (DesignDataField field : allDataField) {
                codes.add(field.getCode());
            }
        }
        return codes;
    }

    public boolean checkZbRefer(String schemeKey, String code) {
        Assert.notNull((Object)schemeKey, "schemeKey can not be null");
        Assert.notNull((Object)code, "code can not be null");
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        List keys = allDataScheme.stream().filter(designDataScheme -> schemeKey.equals(designDataScheme.getZbSchemeKey())).map(Basic::getKey).collect(Collectors.toList());
        for (String key : keys) {
            List fields = this.designDataSchemeService.getAllDataField(key);
            for (DesignDataField field : fields) {
                if (!field.getCode().equals(code)) continue;
                return true;
            }
        }
        return false;
    }
}

