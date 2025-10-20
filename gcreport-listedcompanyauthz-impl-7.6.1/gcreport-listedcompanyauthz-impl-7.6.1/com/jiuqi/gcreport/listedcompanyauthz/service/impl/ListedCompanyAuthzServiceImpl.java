/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.context.EnvCenter
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserQueryReq
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserOrgDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.dao.VaOrgAuthDao
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  com.jiuqi.va.organization.domain.OrgAuthDTO
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 */
package com.jiuqi.gcreport.listedcompanyauthz.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.context.EnvCenter;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.listedcompanyauthz.base.BeanUtil;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyAuthzDao;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyDao;
import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyAuthzService;
import com.jiuqi.gcreport.listedcompanyauthz.service.impl.ListedCompanyCacheService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserQueryReq;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserOrgDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.dao.VaOrgAuthDao;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.service.OrgAuthService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ListedCompanyAuthzServiceImpl
implements FListedCompanyAuthzService {
    private Logger logger = LoggerFactory.getLogger(ListedCompanyAuthzServiceImpl.class);
    @Autowired
    private ListedCompanyCacheService cacheService;
    @Autowired
    private FListedCompanyDao listedCompanyDao;
    @Autowired
    private FListedCompanyAuthzDao companyAuthzDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private AuthUserClient userClient;
    @Autowired
    private OrgAuthService orgAuthService;
    @Autowired
    private VaOrgAuthDao orgAuthDao;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    OrgDataClient orgDataClient;

    @Override
    public List<ListedCompanyAuthzVO> query(ListedCompanyAuthzVO param) {
        param.setCurrLoginUser(EnvCenter.getCurrentUser().getName());
        List<ListedCompanyAuthzEO> query = this.companyAuthzDao.query(param);
        return query.stream().map(eo -> {
            ListedCompanyAuthzVO vo = BeanUtil.authzEO2VO(eo);
            vo.setOrgName((String)eo.getFieldValue("ORGNAME"));
            User username = this.userService.getByUsername(eo.getUserName());
            if (username != null) {
                vo.setUserTitle(username.getNickname());
                vo.setUserRole(this.getRolesByUser(vo.getUserId()));
                vo.setUserBelongOrg(this.getBelongOrgByUser(vo.getUserId()));
            } else {
                vo.setUserTitle("\u6ca1\u6709\u67e5\u627e\u5230\u5bf9\u5e94\u7528\u6237\u4fe1\u606f");
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ListedCompanyAuthzVO> queryUserByOrgs(UserQueryParam param) {
        if (StringUtils.isEmpty(param)) {
            return new ArrayList<ListedCompanyAuthzVO>();
        }
        User user = null;
        if (!StringUtils.isEmpty(param.getUsername())) {
            user = this.userService.getByUsername(param.getUsername());
        }
        List<Object> users = new ArrayList<User>();
        if (StringUtils.isEmpty(param.getOrgCodes())) {
            if (user != null) {
                users.add(user);
            }
        } else {
            String[] orgArray = param.getOrgCodes().split(",");
            ArrayList<Object> userids = new ArrayList<Object>();
            NvwaUserService nvwaUserService = (NvwaUserService)SpringContextUtils.getBean(NvwaUserService.class);
            UserQueryReq userQueryReq = new UserQueryReq();
            userQueryReq.setPage(Integer.valueOf(0));
            userQueryReq.setOrgType("MD_ORG");
            if (orgArray.length == 1) {
                Collection userid = this.orgIdentityService.getGrantedIdentity(orgArray[0]);
                userids.addAll(userid);
                userQueryReq.setOrgCode(orgArray[0]);
                List userDTORes = null;
                try {
                    userDTORes = nvwaUserService.queryUser(userQueryReq, false).stream().map(UserDTO::getId).collect(Collectors.toList());
                }
                catch (JQException e) {
                    e.printStackTrace();
                }
                userids.addAll(userDTORes);
            } else if (orgArray.length > 1) {
                ArrayList useridsInUser = new ArrayList();
                for (int i = 0; i < orgArray.length; ++i) {
                    if (StringUtils.isEmpty(orgArray[i])) continue;
                    Collection userid = this.orgIdentityService.getGrantedIdentity(orgArray[i]);
                    if (i == 0) {
                        userids.addAll(userid);
                    } else {
                        userids.retainAll(userid);
                    }
                    userQueryReq.setOrgCode(orgArray[i]);
                    try {
                        List userDTORes = nvwaUserService.queryUser(userQueryReq, false).stream().map(UserDTO::getId).collect(Collectors.toList());
                        if (i == 0) {
                            useridsInUser.addAll(userDTORes);
                            continue;
                        }
                        useridsInUser.retainAll(userDTORes);
                        continue;
                    }
                    catch (JQException e) {
                        e.printStackTrace();
                    }
                }
                userids.addAll(useridsInUser);
            } else {
                return new ArrayList<ListedCompanyAuthzVO>();
            }
            if (user != null) {
                if (userids.contains(user.getId())) {
                    users.add(user);
                }
            } else {
                users = this.userService.get(userids.toArray(new String[userids.size()]));
            }
        }
        return users.stream().map(u -> {
            ListedCompanyAuthzVO vo = new ListedCompanyAuthzVO();
            vo.setUserId(u.getId());
            vo.setUserName(u.getName());
            vo.setUserTitle(u.getNickname());
            vo.setUserRole(this.getRolesByUser(u.getId()));
            vo.setUserBelongOrg(this.getBelongOrgByUser(u.getId()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public ListedCompanyAuthzEO get(String id) {
        return this.companyAuthzDao.get(id);
    }

    @Override
    public boolean save(List<ListedCompanyAuthzVO> param) {
        if (CollectionUtils.isEmpty(param)) {
            param.clear();
            ListedCompanyAuthzVO listedCompanyAuthzVO = new ListedCompanyAuthzVO();
            listedCompanyAuthzVO.setErrorMsg("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u4fdd\u5b58\u6570\u636e\u4e3a\u7a7a");
            param.add(listedCompanyAuthzVO);
            return false;
        }
        if (StringUtils.isEmpty(param.get(0).getOrgCode())) {
            param.clear();
            ListedCompanyAuthzVO listedCompanyAuthzVO = new ListedCompanyAuthzVO();
            listedCompanyAuthzVO.setErrorMsg("\u5f53\u524d\u7528\u6237\u65e0\u6743\u64cd\u4f5c\uff0c\u8bf7\u7528\u4e0a\u5e02\u516c\u53f8\u7ba1\u7406\u5458\u64cd\u4f5c");
            param.add(listedCompanyAuthzVO);
            return false;
        }
        ListedCompanySaveParam<ListedCompanyAuthzEO> datas = new ListedCompanySaveParam<ListedCompanyAuthzEO>();
        ArrayList userNames = new ArrayList();
        param.forEach(vo -> {
            ListedCompanyAuthzEO eo = BeanUtil.authzVO2EO(vo);
            try {
                this.checkFormatEO(datas, eo);
            }
            catch (Exception e) {
                vo.setErrorMsg(e.getMessage());
            }
            userNames.add(vo.getUserName());
        });
        int totalSize = datas.getInsertDatas().size() + datas.getUpdateDatas().size() + datas.getDeleteDatas().size();
        if (totalSize == param.size()) {
            this.companyAuthzDao.saveAll(datas);
            this.cacheService.publishOrgCacheEvent(userNames.toArray(new String[0]));
            return true;
        }
        return false;
    }

    @Override
    public int delete(ListedCompanyAuthzEO param) {
        int i = this.companyAuthzDao.delete(param);
        this.cacheService.publishOrgCacheEvent(param.getUserName());
        return i;
    }

    @Override
    public boolean checkPenetratePermission(String orgCode, String orgType) {
        boolean businessManager = this.systemIdentityService.isBusinessManager();
        boolean admin = this.systemIdentityService.isAdmin();
        if (businessManager || admin) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u8be5\u7a7f\u900f\u7528\u6237\u662f\u7ba1\u7406\u5458\u7528\u6237\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        String value = GcSystermOptionTool.getOptionValue((String)"BEGIN_LISTEDCOMPANY_AUTHZ");
        if ("0".equalsIgnoreCase(value)) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u7cfb\u7edf\u914d\u7f6e\u672a\u5f00\u542f\u4e0a\u5e02\u516c\u53f8\u63a7\u5236\u6743\u9650\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        ListedCompanyVO p = new ListedCompanyVO();
        p.setOrgCode(orgCode);
        List<ListedCompanyEO> coms = this.listedCompanyDao.query(p);
        if (coms == null || coms.isEmpty()) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u5f53\u524d\u7a7f\u900f\u5355\u4f4d\u4e0d\u662f\u4e0a\u5e02\u516c\u53f8\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        String userName = EnvCenter.getCurrentUser().getName();
        User user = this.userService.getByUsername(userName);
        List roles = this.roleService.getByIdentity(user.getId());
        List<Object> roleNames = CollectionUtils.newArrayList();
        if (roles != null && !roles.isEmpty()) {
            roleNames = roles.stream().map(role -> "EVERYONE".equals(role.getName()) ? "-" : role.getName()).collect(Collectors.toList());
        }
        OrgAuthDTO orgAllAuthDTO = new OrgAuthDTO();
        orgAllAuthDTO.setAuthtype(Integer.valueOf(0));
        orgAllAuthDTO.setBiztype(Integer.valueOf(0));
        orgAllAuthDTO.setOrgname("rule_all");
        if (Boolean.TRUE.equals(this.isHaveOrgAuth((List<String>)roleNames, orgAllAuthDTO))) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u7ec4\u7ec7\u673a\u6784\u89c4\u5219\u914d\u7f6e\u6240\u6709\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        if (orgCode.equals(user.getOrgCode())) {
            OrgAuthDTO orgAuthDTO = new OrgAuthDTO();
            orgAuthDTO.setAuthtype(Integer.valueOf(0));
            orgAuthDTO.setBiztype(Integer.valueOf(0));
            orgAuthDTO.setOrgname("rule_belong");
            if (Boolean.TRUE.equals(this.isHaveOrgAuth((List<String>)roleNames, orgAuthDTO))) {
                this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u7a7f\u900f\u5355\u4f4d\u662f\u7a7f\u900f\u7528\u6237\u7684\u6240\u5c5e\u5355\u4f4d\u5e76\u6709\u673a\u6784\u89c4\u5219\u6240\u5c5e\u5355\u4f4d\u914d\u7f6e\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
                return true;
            }
        }
        List bothOrgs = CollectionUtils.newArrayList();
        UserOrgDTO uoDTO = new UserOrgDTO();
        uoDTO.setUsername(userName);
        uoDTO = this.userClient.listBothOrg(uoDTO);
        if (uoDTO != null) {
            bothOrgs = uoDTO.getOrgCodes();
        }
        if (bothOrgs != null && !bothOrgs.isEmpty() && bothOrgs.contains(orgCode)) {
            OrgAuthDTO orgAuthDTO = new OrgAuthDTO();
            orgAuthDTO.setAuthtype(Integer.valueOf(0));
            orgAuthDTO.setBiztype(Integer.valueOf(0));
            orgAuthDTO.setOrgname("rule_both");
            if (Boolean.TRUE.equals(this.isHaveOrgAuth((List<String>)roleNames, orgAuthDTO))) {
                this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u7a7f\u900f\u5355\u4f4d\u662f\u7a7f\u900f\u7528\u6237\u7684\u517c\u7ba1\u5355\u4f4d\u5e76\u6709\u673a\u6784\u89c4\u5219\u517c\u7ba1\u5355\u4f4d\u914d\u7f6e\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
                return true;
            }
        }
        OrgAuthDTO userDTO = new OrgAuthDTO();
        userDTO.setAuthtype(Integer.valueOf(1));
        userDTO.setBiztype(Integer.valueOf(1));
        userDTO.setBizname(userName);
        userDTO.setOrgname(orgCode);
        userDTO.setOrgcategory(orgType);
        OrgAuthDO userAuthDO = this.orgAuthService.get(userDTO);
        if (userAuthDO != null && userAuthDO.getAtaccess() == 1) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u7528\u6237\u660e\u7ec6\u6743\u9650\u914d\u7f6e\u4e86\u5f53\u524d\u5355\u4f4d\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        OrgAuthDTO roleDTO = new OrgAuthDTO();
        roleDTO.setAuthtype(Integer.valueOf(1));
        roleDTO.setBiztype(Integer.valueOf(0));
        roleDTO.setOrgname(orgCode);
        roleDTO.setOrgcategory(orgType);
        List list = this.orgAuthDao.select(roleDTO);
        if (list != null && !list.isEmpty()) {
            for (OrgAuthDO orgAuthDO : list) {
                if (!roleNames.contains(orgAuthDO.getBizname()) || orgAuthDO.getAtaccess() != 1) continue;
                this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u89d2\u8272\u660e\u7ec6\u6743\u9650\u914d\u7f6e\u4e86\u5f53\u524d\u5355\u4f4d\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
                return true;
            }
        }
        ListedCompanyAuthzVO vo = new ListedCompanyAuthzVO();
        vo.setUserName(userName);
        vo.setOrgCode(orgCode);
        List<ListedCompanyAuthzEO> authzVOList = this.companyAuthzDao.query(vo);
        if (authzVOList != null && !authzVOList.isEmpty() && Boolean.TRUE.equals(authzVOList.get(0).getIsPenetrate())) {
            this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u52fe\u9009\u4e86\u5f53\u524d\u5355\u4f4d\u7a7f\u900f\u6743\u9650\uff0c\u4e0d\u62e6\u622a", (Object)orgCode, (Object)orgType);
            return true;
        }
        this.logger.info("\u4e0a\u5e02\u516c\u53f8\u6388\u6743\u7ba1\u7406\u3010{}\u3011\u3010{}\u3011-\u5f53\u524d\u5355\u4f4d\u65e0\u7a7f\u900f\u6743\u9650\uff0c\u62e6\u622a", (Object)orgCode, (Object)orgType);
        return false;
    }

    private boolean isHaveOrgAuth(List<String> roleNames, OrgAuthDTO orgAuthDTO) {
        List list = this.orgAuthDao.select(orgAuthDTO);
        if (list != null && !list.isEmpty()) {
            List bizNames = list.stream().map(OrgAuthDO::getBizname).collect(Collectors.toList());
            Set intersection = roleNames.stream().filter(bizNames::contains).collect(Collectors.toSet());
            if (!intersection.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private ListedCompanyAuthzEO checkFormatEO(ListedCompanySaveParam<ListedCompanyAuthzEO> datas, ListedCompanyAuthzEO param) {
        User user;
        if (StringUtils.isEmpty(param.getUserName()) && StringUtils.isEmpty(param.getUserId())) {
            throw new NullPointerException("\u5355\u4f4d\u6388\u6743ID/\u767b\u5f55\u540d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (!StringUtils.isEmpty(param.getUserName())) {
            user = this.userService.getByUsername(param.getUserName());
            if (user == null) {
                throw new NullPointerException("\u7528\u6237\u3010" + param.getUserName() + "\u3011\u4e0d\u5b58\u5728\u6216\u8005\u662f\u7cfb\u7edf\u7528\u6237\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u7528\u6237\u3002");
            }
            param.setUserId(user.getId());
            param.setCreateUser(EnvCenter.getCurrentUser().getName());
        }
        if (!StringUtils.isEmpty(param.getUserId())) {
            user = this.userService.get(param.getUserId());
            if (user == null) {
                throw new NullPointerException("\u7528\u6237\u3010" + param.getUserId() + "\u3011\u4e0d\u5b58\u5728\u6216\u8005\u662f\u7cfb\u7edf\u7528\u6237\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u7528\u6237\u3002");
            }
            param.setUserName(user.getName());
            ListedCompanyVO p = new ListedCompanyVO();
            p.setOrgCode(param.getOrgCode());
            List<ListedCompanyEO> coms = this.listedCompanyDao.query(p);
            if (coms == null || coms.size() < 1) {
                throw new NullPointerException("\u5355\u4f4d\u3010" + param.getOrgCode() + "\u3011\u4e0d\u662f\u4e0a\u5e02\u516c\u53f8,\u65e0\u6cd5\u4fdd\u5b58\u7528\u6237\u6743\u9650\u8bb0\u5f55\u3002");
            }
        }
        ListedCompanyAuthzVO p = new ListedCompanyAuthzVO();
        p.setId(param.getId());
        p.setOrgCode(param.getOrgCode());
        p.setUserName(param.getUserName());
        List<ListedCompanyAuthzEO> db = this.companyAuthzDao.query(p);
        if (db != null && db.size() > 0) {
            throw new NullPointerException("\u5355\u4f4d\u3010" + param.getOrgCode() + "\u3011\u4e0b\u6743\u9650\u7528\u6237\u3010" + param.getUserName() + "\u3011\u5df2\u5b58\u5728\u3002");
        }
        if (!StringUtils.isEmpty(param.getId())) {
            ListedCompanyAuthzEO old = this.companyAuthzDao.get(param.getId());
            if (old != null) {
                param.setCreateTime(old.getCreateTime());
                datas.addUpdate(param);
            }
        } else {
            param.setCreateTime(new Date());
            datas.addInsert(param);
        }
        return param;
    }

    private String getBelongOrgByUser(String userid) {
        Collection orgs = this.orgIdentityService.getGrantedOrg(userid);
        StringBuilder orgSb = new StringBuilder();
        if (orgs != null && orgs.size() > 0) {
            OrgDTO param = new OrgDTO();
            param.setCategoryname("MD_ORG");
            param.setOrgCodes(new ArrayList(orgs));
            param.setAuthType(OrgDataOption.AuthType.NONE);
            List orgdatas = this.orgDataCacheService.listBasicCacheData(param);
            orgdatas.forEach(o -> orgSb.append(o.getName()).append(","));
        }
        return orgSb.toString();
    }

    private String getRolesByUser(String userid) {
        List roles = this.roleService.getByIdentity(userid);
        StringBuilder roleSb = new StringBuilder();
        roleSb.append("\u6240\u6709\u4eba");
        if (roles != null && roles.size() > 0) {
            roles.forEach(role -> roleSb.append(",").append(role.getTitle()));
        }
        return roleSb.toString();
    }
}

