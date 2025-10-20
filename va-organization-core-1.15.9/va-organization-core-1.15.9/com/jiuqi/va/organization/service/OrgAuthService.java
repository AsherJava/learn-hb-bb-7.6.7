/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.domain.OrgAuthVO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrgAuthService {
    public OrgAuthDO get(OrgAuthDTO var1);

    public PageVO<OrgAuthVO> listDetail(OrgAuthDTO var1);

    public R updateDetail(List<OrgAuthDO> var1);

    public PageVO<OrgAuthVO> listRule(OrgAuthDTO var1);

    public R updateRule(List<OrgAuthDO> var1);

    public Set<String> listAuthOrg(UserDO var1, OrgDTO var2);

    public R existCategoryAuth(UserDO var1, OrgDTO var2);

    public R existDataAuth(UserDO var1, OrgDTO var2);

    public List<String> getAuthTypeExtendName();

    public List<Map<String, String>> getAuthType();

    public R findPath(OrgAuthDTO var1);

    default public R existCategoryAuth(OrgDTO param) {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user == null) {
            return R.error((String)"\u65e0\u6548\u7684\u7528\u6237");
        }
        if ("super".equalsIgnoreCase(user.getMgrFlag())) {
            R rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
            rs.put("exist", (Object)true);
            return rs;
        }
        return this.existCategoryAuth((UserDO)user, param);
    }

    default public R existDataAuth(OrgAuthFindDTO orgAuthFindDTO) {
        UserDO user = orgAuthFindDTO.getUserDO();
        if (user == null) {
            user = ShiroUtil.getUser();
        }
        if (user instanceof UserLoginDTO && "super".equals(((UserLoginDTO)user).getMgrFlag())) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])).put("exist", (Object)true);
        }
        return this.existDataAuth(user, orgAuthFindDTO.getOrgDTO());
    }
}

