/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package nr.single.para.basedata.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import nr.single.para.basedata.IOrgDataDefineService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrgDataDefineServiceImpl
implements IOrgDataDefineService {
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataModelClient dataModelClient;

    public BaseDataGroupDO quertBaseDataGroup(String groupName) {
        return null;
    }

    public void InsertBaseDataGroup(BaseDataGroupDTO group) {
    }

    public BaseDataGroupDO GetAndInsertBaseDataGroup(String groupName, String groupTitle, String parentName) {
        return null;
    }

    @Override
    public OrgCategoryDO queryOrgDatadefine(String orgName) {
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        PageVO orgDefines = this.orgCategoryClient.list(param);
        OrgCategoryDO orgDefine = null;
        if (orgDefines != null && orgDefines.getRows().size() > 0) {
            orgDefine = (OrgCategoryDO)orgDefines.getRows().get(0);
        }
        return orgDefine;
    }

    @Override
    public R InsertOrgDataDefine(OrgCategoryDO orgDefine) {
        R r = this.orgCategoryClient.add(orgDefine);
        return r;
    }

    @Override
    public R update(OrgCategoryDO orgDefine) {
        return this.orgCategoryClient.update(orgDefine);
    }
}

