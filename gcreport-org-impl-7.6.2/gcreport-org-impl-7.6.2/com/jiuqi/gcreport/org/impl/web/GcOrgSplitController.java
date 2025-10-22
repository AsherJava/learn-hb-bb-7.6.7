/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.org.api.intf.GcOrgSplitClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedVO
 *  com.jiuqi.gcreport.org.api.vo.OrgSplitVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.org.api.intf.GcOrgSplitClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedVO;
import com.jiuqi.gcreport.org.api.vo.OrgSplitVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgSplitController
implements GcOrgSplitClient {
    GcOrgSplitController() {
    }

    private GcOrgMangerCenterTool getTool(String orgType, String org_ver) {
        return GcOrgMangerCenterTool.getInstance(orgType, org_ver);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgSplitVO>> getSplitedOgrList(GcOrgSplitedParam param) {
        GcOrgMangerCenterTool tool = this.getTool(param.getOrgType(), param.getOrgVerCode());
        List<OrgSplitVO> splitedOrgs = tool.getSplitedOrgs(param.getParentCode());
        return BusinessResponseEntity.ok(splitedOrgs);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgSplitVO>> saveSplitOrgs(GcOrgSplitedVO vo) {
        Assert.isNotEmpty((Collection)vo.getOrgs(), (String)"\u6ca1\u6709\u53ef\u4fdd\u5b58\u7684\u6570\u636e!", (Object[])new Object[0]);
        GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), vo.getOrgVerCode());
        try {
            tool.batchSaveSplitOrg(vo.getOrgs());
            return BusinessResponseEntity.ok((Object)vo.getOrgs());
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteOrgSplit(GcOrgSplitedVO vo) {
        GcOrgMangerCenterTool tool = this.getTool(vo.getOrgType(), vo.getOrgVerCode());
        try {
            tool.deleteOrgSplit(vo.getOrgCodes());
            return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
        }
        catch (Exception e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
    }
}

