/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.bde.plugin.common.utils.entity;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.List;

public class OrgDataHolder {
    private boolean dataExists;
    private OrgDO orgDO;
    private List<AssistExtendDimVO> orgAssistExtendDimensions;

    public OrgDataHolder() {
    }

    public OrgDataHolder(boolean dataExists, OrgDO orgDO, List<AssistExtendDimVO> orgAssistExtendDimensions) {
        this.dataExists = dataExists;
        this.orgDO = orgDO;
        this.orgAssistExtendDimensions = orgAssistExtendDimensions;
    }

    public boolean isDataExists() {
        return this.dataExists;
    }

    public void setDataExists(boolean dataExists) {
        this.dataExists = dataExists;
    }

    public OrgDO getOrgDO() {
        return this.orgDO;
    }

    public void setOrgDO(OrgDO orgDO) {
        this.orgDO = orgDO;
    }

    public List<AssistExtendDimVO> getOrgAssistExtendDimensions() {
        return this.orgAssistExtendDimensions;
    }

    public void setOrgAssistExtendDimensions(List<AssistExtendDimVO> orgAssistExtendDimensions) {
        this.orgAssistExtendDimensions = orgAssistExtendDimensions;
    }
}

