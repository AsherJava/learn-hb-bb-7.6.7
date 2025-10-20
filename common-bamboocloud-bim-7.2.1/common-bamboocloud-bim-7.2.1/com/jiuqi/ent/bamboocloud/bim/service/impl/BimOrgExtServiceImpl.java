/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.ent.bamboocloud.bim.service.impl;

import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.org.OrgExtFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.service.BimOrgExtService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class BimOrgExtServiceImpl
implements BimOrgExtService {
    private static final Logger logger = LoggerFactory.getLogger(BimOrgExtServiceImpl.class);
    private static final String QYDM = "qydm";
    private static final String CURRENCYID = "currencyid";
    private static final String UPDATETIME = "updatetime";
    @Autowired
    private BimProperties bimProperties;
    @Lazy
    @Autowired
    private SystemUserService systemUserService;
    @Lazy
    @Autowired
    private OrgDataClient orgDataClient;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void createBaseOrg(OrgExtFieldDTO vo) {
        OrgDTO param = new OrgDTO();
        param.setCode(vo.getCode());
        OrgDO orgDO = this.orgDataClient.get(param);
        Assert.isNull((Object)orgDO, "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5df2\u5b58\u5728\uff0c\u8bf7\u8003\u8651\u8c03\u7528\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u63a5\u53e3!");
        OrgDTO org = new OrgDTO();
        org.setCode(vo.getCode());
        org.setName(vo.getName());
        org.setShortname(vo.getShortName());
        if (StringUtils.isNotBlank((CharSequence)this.bimProperties.getDefaultOrgParentCode())) {
            org.setParentcode(this.bimProperties.getDefaultOrgParentCode());
        } else {
            org.setParentcode(vo.getParentCode());
        }
        org.setStopflag(Integer.valueOf(0));
        org.setVersionDate(new Date());
        org.setCreatetime(new Date());
        org.put(CURRENCYID, (Object)vo.getCurrencyCode());
        org.put(QYDM, (Object)vo.getCreditCode());
        org.put(UPDATETIME, (Object)new Date());
        this.setExtFieldValue(vo, (Map<String, Object>)org);
        R result = this.orgDataClient.add(org);
        if (result.getCode() != 0) {
            throw new RuntimeException("\u6dfb\u52a0\u7ec4\u7ec7\u62a5\u9519:" + result.getMsg());
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateBaseOrg(OrgExtFieldDTO vo) {
        OrgDTO param = new OrgDTO();
        param.setCode(vo.getCode());
        param.setStopflag(Integer.valueOf(-1));
        OrgDO orgDO = this.orgDataClient.get(param);
        Assert.notNull((Object)orgDO, "\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u4e0d\u5b58\u5728\u65e0\u6cd5\u4fee\u6539\uff0c\u8bf7\u8003\u8651\u8c03\u7528\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u63a5\u53e3!");
        OrgDTO curOrg = new OrgDTO((Map)orgDO);
        if (StringUtils.isNotBlank((CharSequence)vo.getName())) {
            curOrg.setName(vo.getName());
        }
        if (StringUtils.isNotBlank((CharSequence)vo.getShortName())) {
            curOrg.setShortname(vo.getShortName());
        }
        if (StringUtils.isNotBlank((CharSequence)vo.getCurrencyCode())) {
            curOrg.put(CURRENCYID, (Object)vo.getCurrencyCode());
        }
        if (StringUtils.isNotBlank((CharSequence)vo.getCreditCode())) {
            curOrg.put(QYDM, (Object)vo.getCreditCode());
        }
        curOrg.setVersionDate(new Date());
        curOrg.put(UPDATETIME, (Object)new Date());
        this.setExtFieldValue(vo, (Map<String, Object>)curOrg);
        R result = this.orgDataClient.update(curOrg);
        if (result.getCode() != 0) {
            throw new RuntimeException("\u4fee\u6539\u7ec4\u7ec7\u62a5\u9519:" + result.getMsg());
        }
        if (StringUtils.isBlank((CharSequence)this.bimProperties.getDefaultOrgParentCode()) && this.bimProperties.isAllowOrgUpdateParentcode() && StringUtils.isNotBlank((CharSequence)vo.getParentCode()) && !orgDO.getParentcode().equals(vo.getParentCode())) {
            logger.info("\u673a\u6784[{}]\u7236\u7ea7\u6709\u53d8\u52a8\uff0c{}->{}", orgDO.getCode(), orgDO.getParentcode(), vo.getParentCode());
            curOrg.setParentcode(vo.getParentCode());
            R move = this.orgDataClient.move(curOrg);
            if (move.getCode() != 0) {
                logger.warn("\u673a\u6784\u7236\u7ea7\u79fb\u52a8\u5931\u8d25\u3002\u9519\u8bef\u4fe1\u606f:{}", (Object)move.getMsg());
                throw new IllegalArgumentException("\u673a\u6784\u7236\u7ea7\u53d8\u52a8\u65f6\u5f02\u5e38: " + move.getMsg());
            }
            logger.info("\u673a\u6784\u7236\u7ea7\u79fb\u52a8\u6210\u529f\u3002");
        }
    }

    private void setExtFieldValue(OrgExtFieldDTO org, Map<String, Object> extFields) {
        HashSet<String> keySet = new HashSet<String>();
        keySet.addAll(org.getDatas().keySet());
        keySet.retainAll(this.bimProperties.getExtOrgFieldNames());
        for (String key : keySet) {
            extFields.put(key.toLowerCase(), org.getDatas().get(key));
        }
    }

    @Override
    public boolean isOrgExists(String code) {
        OrgDTO param = new OrgDTO();
        param.setCode(code);
        param.setStopflag(Integer.valueOf(-1));
        OrgDO orgDO = this.orgDataClient.get(param);
        return !Objects.isNull(orgDO);
    }

    @Override
    public List<String> queryAllOrgIds() {
        List<String> orgIds = new LinkedList<String>();
        OrgDTO param = new OrgDTO();
        param.setCategoryname("MD_ORG");
        param.setCode("-");
        param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        param.setStopflag(Integer.valueOf(-1));
        PageVO list = this.orgDataClient.list(param);
        if (list != null && list.getTotal() > 0) {
            List orgList = list.getRows();
            orgIds = orgList.stream().map(OrgDO::getCode).collect(Collectors.toList());
        }
        return orgIds;
    }

    @Override
    public Map<String, Object> queryOrgById(String code) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCode(code);
        queryParam.setStopflag(Integer.valueOf(-1));
        OrgDO orgDo = this.orgDataClient.get(queryParam);
        map.put("CODE", orgDo.getCode());
        map.put("NAME", orgDo.getName());
        map.put("SHORT_NAME", orgDo.getShortname());
        map.put("PARENT_CODE", orgDo.getParentcode());
        map.put("CURRENCY_CODE", this.getCurrencyCode(orgDo));
        map.put("CREDIT_CODE", this.getCreditCode(orgDo));
        map.put("CREATETIME", this.getCreateTime(orgDo));
        if (!CollectionUtils.isEmpty(this.bimProperties.getExtOrgFieldNames())) {
            for (String extFieldName : this.bimProperties.getExtOrgFieldNames()) {
                String lowerCaseExtField = extFieldName.toLowerCase();
                if (orgDo.containsKey((Object)lowerCaseExtField)) {
                    map.put(extFieldName, orgDo.get((Object)lowerCaseExtField));
                    continue;
                }
                map.put(extFieldName, "");
            }
        }
        return map;
    }

    private String getCreateTime(OrgDO orgDo) {
        Date createTime = orgDo.getCreatetime();
        if (Objects.isNull(createTime)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(createTime);
    }

    private String getCreditCode(OrgDO orgDo) {
        String creditCode = (String)orgDo.get((Object)QYDM);
        if (StringUtils.isEmpty((CharSequence)creditCode)) {
            return "";
        }
        return creditCode;
    }

    private String getCurrencyCode(OrgDO orgDo) {
        String currencyCode = (String)orgDo.get((Object)CURRENCYID);
        if (StringUtils.isEmpty((CharSequence)currencyCode)) {
            return "";
        }
        return currencyCode;
    }

    @Override
    public void bindNpUserContext() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = new NpContextUser();
        Optional systemUser = this.systemUserService.getUsers().stream().findFirst();
        SystemUser user = (SystemUser)systemUser.get();
        contextUser.setId(user.getId());
        contextUser.setName(user.getName());
        contextUser.setNickname(user.getNickname());
        contextUser.setDescription(user.getDescription());
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        npContext.setIdentity((ContextIdentity)identity);
        NpContextHolder.setContext((NpContext)npContext);
    }

    @Override
    public void unbindNpUserContext() {
        NpContextHolder.clearContext();
    }

    @Override
    public void deleteBaseOrg(String code) {
        OrgDTO orgDTO;
        R result;
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCode(code);
        queryParam.setStopflag(Integer.valueOf(-1));
        OrgDO orgDO = this.orgDataClient.get(queryParam);
        if (orgDO != null && (result = this.orgDataClient.remove(orgDTO = new OrgDTO((Map)orgDO))).getCode() != 0) {
            throw new RuntimeException("\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u62a5\u9519:" + result.getMsg());
        }
    }
}

