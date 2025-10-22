/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataRelationService;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class DefaultBaseDataRelationServiceImpl
implements IBaseDataRelationService {
    @Autowired
    protected BaseDataClient baseDataClient;

    @Override
    public Set<String> getUserIds(BaseDataDTO baseDataDO) {
        HashSet<String> codes = new HashSet<String>();
        Object o = baseDataDO.get((Object)"informant_user");
        if (o == null) {
            return codes;
        }
        codes.add(String.valueOf(o));
        return codes;
    }

    @Override
    public Set<String> getObjectCodesByUser(BaseDataAuthFindDTO findDTO) {
        HashSet<String> codes = new HashSet<String>();
        UserDO user = findDTO.getUser();
        BaseDataDTO param = findDTO.getParam();
        if (user == null || user.getId() == null || param == null || param.getTableName() == null) {
            return codes;
        }
        String userId = user.getId();
        BaseDataDTO baseDataDTO = this.copyParam(findDTO.getParam());
        PageVO page = this.baseDataClient.list(baseDataDTO);
        if (page != null && page.getRows() != null) {
            for (BaseDataDO row : page.getRows()) {
                Object o = row.get((Object)"informant_user");
                if (!userId.equals(o)) continue;
                codes.add(row.getObjectcode());
            }
        }
        return codes;
    }

    @Override
    public Set<String> getObjectCodesByCodes(BaseDataDTO param, Set<String> codes) {
        HashSet<String> baseCodes = new HashSet<String>();
        if (param == null || param.getTableName() == null || CollectionUtils.isEmpty(codes)) {
            return baseCodes;
        }
        BaseDataDTO baseDataDTO = this.copyParam(param);
        PageVO page = this.baseDataClient.list(baseDataDTO);
        if (page != null && page.getRows() != null) {
            for (BaseDataDO row : page.getRows()) {
                Object o = row.get((Object)"masterid");
                if (o == null || !codes.contains(String.valueOf(o))) continue;
                baseCodes.add(row.getObjectcode());
            }
        }
        return baseCodes;
    }

    private BaseDataDTO copyParam(BaseDataDTO param) {
        ContextUser user;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTenantName(param.getTenantName());
        basedataDTO.setTableName(param.getTableName());
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setVersionDate(param.getVersionDate());
        basedataDTO.setOrderBy(Collections.emptyList());
        basedataDTO.setStopflag(param.getStopflag());
        Integer shareType = (Integer)param.get((Object)"defineSharetype");
        if (shareType != null && shareType != 0 && (user = NpContextHolder.getContext().getUser()) != null) {
            basedataDTO.setUnitcode(user.getOrgCode());
        }
        return basedataDTO;
    }
}

