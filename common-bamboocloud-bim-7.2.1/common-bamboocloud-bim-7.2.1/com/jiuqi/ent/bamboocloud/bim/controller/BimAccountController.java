/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.CertificateType
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.entity.UserEntity
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.np.user.service.UserService
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.ent.bamboocloud.bim.controller;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import com.jiuqi.ent.bamboocloud.bim.FunctionNotSupportException;
import com.jiuqi.ent.bamboocloud.bim.dto.GeneralDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.account.AccountCreateDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.account.AccountDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.account.AccountIdListDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.account.AccountQueryDTO;
import com.jiuqi.ent.bamboocloud.bim.util.BimHelper;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.CertificateType;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.entity.UserEntity;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.service.UserService;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/bamboocloud"})
public class BimAccountController {
    private static final Logger logger = LoggerFactory.getLogger(BimAccountController.class);
    @Autowired
    private BimHelper bimHelper;
    @Autowired
    private BimProperties bimProperties;
    @Lazy
    @Autowired
    private NvwaPasswordClient passwordClient;
    @Lazy
    @Autowired
    private UserService<User> userService;
    @Lazy
    @Autowired
    private RoleService roleService;

    @PostMapping(value={"/UserCreateService"})
    public String createUser(HttpServletRequest request) {
        String userId;
        String bimRequestId = "";
        try {
            if ("org".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            String password = this.getPassword(params);
            params.put("password", "****");
            logger.info("UserCreateService\u5165\u53c2--:[{}]", (Object)params);
            UserEntity createUser = this.getCreateUserDO(params);
            userId = this.userService.create((User)createUser);
            String title = String.format("\u65b0\u589e-\u7528\u6237 %s \u6210\u529f", createUser.getName());
            LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u65b0\u589e\u7528\u6237\u8bf7\u6c42\u5bf9\u8c61:\n" + createUser));
            if (StringUtils.isNotBlank((CharSequence)userId)) {
                if (StringUtils.isNotBlank((CharSequence)password)) {
                    this.passwordClient.changePassword(userId, password);
                } else {
                    String randomPassword = UUID.randomUUID().toString().replace("-", "@").substring(0, 9);
                    this.passwordClient.changePassword(userId, randomPassword);
                }
                this.roleService.grant("ffffffff-ffff-ffff-bbbb-ffffffffffff", userId);
            }
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u7528\u6237UserCreateService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        AccountCreateDTO response = new AccountCreateDTO(bimRequestId, "0", "success", userId);
        String json = JsonUtils.writeValueAsString((Object)response);
        logger.info("\u65b0\u589e\u7528\u6237UserCreateService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    private UserEntity getCreateUserDO(Map<String, Object> params) {
        Assert.hasText(this.getName(params), "\u65b0\u589e\u7528\u6237\u5fc5\u586bNAME");
        UserEntity userEntity = new UserEntity();
        userEntity.setName(this.getName(params));
        userEntity.setTelephone(this.getPhone(params));
        userEntity.setNickname(this.getNickName(params) != null ? this.getNickName(params) : this.getName(params));
        if (StringUtils.isNotBlank((CharSequence)this.bimProperties.getDefaultUserOrgCode())) {
            userEntity.setOrgCode(this.bimProperties.getDefaultUserOrgCode());
        } else {
            userEntity.setOrgCode(this.getOrgCode(params));
        }
        String idNumber = this.getIdNumber(params);
        boolean idNumberBlank = StringUtils.isNotBlank((CharSequence)idNumber);
        userEntity.setCertificateNumber(idNumber);
        userEntity.setCertificateType((CertificateType)(idNumberBlank ? CertificateType.IDENTITY_CARD_2ND_GENERATION : null));
        userEntity.setEmail(this.getEmail(params));
        Instant now = Instant.now();
        userEntity.setCreateTime(now);
        userEntity.setModifyTime(now);
        userEntity.setEnabled(Boolean.valueOf(this.isUserEnable(params) != null ? this.isUserEnable(params) : true));
        userEntity.setSecurityLevel(this.getSecurityLevel(params));
        userEntity.setCreator("sys_user_admin");
        return userEntity;
    }

    private String getSecurityLevel(Map<String, Object> params) {
        return (String)params.get("SECURITY_LEVEL");
    }

    private String getBimUid(Map<String, Object> params) {
        return (String)params.get("bimUid");
    }

    private String getEmail(Map<String, Object> params) {
        return (String)params.get("EMAIL");
    }

    private String getEmail(UserEntity user) {
        return user.getEmail() == null ? "" : user.getEmail();
    }

    private String getIdNumber(Map<String, Object> params) {
        return (String)params.get("ID_NUMBER");
    }

    private String getIdNumber(UserEntity user) {
        return user.getCertificateNumber() == null ? "" : user.getCertificateNumber();
    }

    private String getName(Map<String, Object> params) {
        return (String)params.get("NAME");
    }

    private String getNickName(Map<String, Object> params) {
        return (String)params.get("NICK_NAME");
    }

    private String getNickName(UserEntity user) {
        return user.getNickname() == null ? "" : user.getNickname();
    }

    private String getOrgCode(Map<String, Object> params) {
        return (String)params.get("ORGCODE");
    }

    private String getOrgCode(UserEntity user) {
        return user.getOrgCode() == null ? "" : user.getOrgCode();
    }

    private String getPassword(Map<String, Object> params) {
        return (String)params.get("password");
    }

    private String getPhone(Map<String, Object> params) {
        return (String)params.get("PHONE");
    }

    private String getPhone(UserEntity user) {
        return user.getTelephone() == null ? "" : user.getTelephone();
    }

    private String getUpdateTime(UserEntity user) {
        return user.getModifyTime() == null ? "" : this.instantToString(user.getModifyTime());
    }

    private String instantToString(Instant instant) {
        Date from = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(from);
    }

    private Boolean isUserEnable(Map<String, Object> params) {
        Object bimEnableObj = params.get("__ENABLE__");
        Object userEnableObj = params.get("ENABLE");
        if (bimEnableObj == null && userEnableObj == null) {
            return true;
        }
        if (bimEnableObj != null) {
            return this.determinBoolean(bimEnableObj);
        }
        return this.determinBoolean(userEnableObj);
    }

    private Boolean determinBoolean(Object o) {
        if (o instanceof Boolean) {
            return (Boolean)o;
        }
        if (o instanceof String) {
            if ("true".equals(o) || "false".equals(o)) {
                return Boolean.valueOf((String)o);
            }
            if ("1".equals(o)) {
                return true;
            }
            if ("0".equals(o)) {
                return false;
            }
        }
        logger.warn("\u4f20\u5165\u7684\u7528\u6237\u542f\u7528\u5b57\u6bb5\u4e0d\u5408\u6cd5\uff0c\u9ed8\u8ba4\u542f\u7528");
        return true;
    }

    @PostMapping(value={"/QueryAllUserIdsService"})
    public String queryAllUserIdsService(HttpServletRequest request) {
        String bimRequestId = "";
        List<String> idList = Collections.emptyList();
        try {
            if ("org".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            List users = this.userService.getUsers();
            if (!CollectionUtils.isEmpty(users)) {
                idList = users.stream().map(User::getId).collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u6240\u6709\u7528\u6237id QueryAllUserIdsService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        AccountIdListDTO response = new AccountIdListDTO(bimRequestId, "0", "success", idList);
        String json = JsonUtils.writeValueAsString((Object)response);
        logger.info("QueryAllUserIdsService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    @PostMapping(value={"/QueryUserByIdService"})
    public String queryUserByIdService(HttpServletRequest request) {
        String bimRequestId = "";
        AccountDTO account = null;
        try {
            if ("org".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            Assert.notNull((Object)this.getBimUid(params), "bimUid\u4e0d\u80fd\u4e3a\u7a7a");
            Optional optional = this.userService.find(this.getBimUid(params));
            if (!optional.isPresent()) {
                throw new RuntimeException("bimUid=" + this.getBimUid(params) + "\u5bf9\u5e94\u7684\u7528\u6237\u4e0d\u5b58\u5728");
            }
            UserEntity user = (UserEntity)optional.get();
            account = this.user2AccountVO(user);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7528\u6237QueryUserByIdService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        AccountQueryDTO response = new AccountQueryDTO(bimRequestId, "0", "success", account);
        String json = JsonUtils.writeValueAsString((Object)response);
        logger.info("QueryUserByIdService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    @PostMapping(value={"/UserUpdateService"})
    public String updateUser(HttpServletRequest request) {
        String bimRequestId = "";
        try {
            if ("org".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            String password = this.getPassword(params);
            if (StringUtils.isNotBlank((CharSequence)password)) {
                params.put("password", "****");
            }
            logger.info("UserUpdateService\u5165\u53c2--:[{}]", (Object)params);
            UserEntity updateUser = this.getUpdateUserDO(params);
            logger.info("UserUpdateService\u5f85\u4fdd\u5b58\u7684UserEntity--:[{}]", (Object)updateUser);
            this.userService.update((User)updateUser);
            Optional optional = this.userService.find(this.getBimUid(params));
            if (optional.isPresent()) {
                String title = String.format("\u4fee\u6539-\u7528\u6237 %s \u6210\u529f", ((User)optional.get()).getName());
                LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u4fee\u6539\u7528\u6237\u8bf7\u6c42\u5bf9\u8c61:\n" + updateUser));
            }
            if (StringUtils.isNotBlank((CharSequence)password)) {
                logger.info("\u5bc6\u7801\u4e0d\u4e3a\u7a7a\uff0c\u66f4\u65b0\u5bc6\u7801");
                this.passwordClient.changePassword(updateUser.getId(), password);
            }
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u7528\u6237UserUpdateService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        GeneralDTO response = new GeneralDTO(bimRequestId, "0", "success");
        String json = JsonUtils.writeValueAsString((Object)response);
        logger.info("UserUpdateService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    private UserEntity getUpdateUserDO(Map<String, Object> params) {
        UserEntity updateUser;
        Assert.hasText(this.getBimUid(params), "\u66f4\u65b0\u7528\u6237\u5fc5\u586bbimUid");
        Optional optional = this.userService.find(this.getBimUid(params));
        if (optional.isPresent()) {
            updateUser = (UserEntity)optional.get();
            if (StringUtils.isNotEmpty((CharSequence)this.getNickName(params))) {
                updateUser.setNickname(this.getNickName(params));
            }
            if (StringUtils.isNotEmpty((CharSequence)this.getPhone(params))) {
                updateUser.setTelephone(this.getPhone(params));
            }
            if (StringUtils.isBlank((CharSequence)this.bimProperties.getDefaultUserOrgCode()) && this.bimProperties.isAllowUserUpdateOrgcode() && StringUtils.isNotEmpty((CharSequence)this.getOrgCode(params))) {
                updateUser.setOrgCode(this.getOrgCode(params));
            }
            if (StringUtils.isNotEmpty((CharSequence)this.getIdNumber(params))) {
                updateUser.setCertificateNumber(this.getIdNumber(params));
            }
            if (StringUtils.isNotEmpty((CharSequence)this.getEmail(params))) {
                updateUser.setEmail(this.getEmail(params));
            }
            if (Objects.nonNull(this.isUserEnable(params))) {
                updateUser.setEnabled(this.isUserEnable(params));
            }
            if (StringUtils.isNotBlank((CharSequence)this.getSecurityLevel(params))) {
                updateUser.setSecurityLevel(this.getSecurityLevel(params));
            }
        } else {
            throw new RuntimeException("\u88ab\u66f4\u65b0\u7684\u7528\u6237[" + this.getBimUid(params) + "]\u4e0d\u5b58\u5728");
        }
        updateUser.setModifyTime(Instant.now());
        return updateUser;
    }

    @PostMapping(value={"/UserDeleteService"})
    public String deleteUser(HttpServletRequest request) {
        String bimRequestId = "";
        try {
            if ("org".equals(this.bimProperties.getOpenFunc())) {
                throw new FunctionNotSupportException("\u6682\u672a\u5f00\u653e\u7528\u6237\u540c\u6b65\u529f\u80fd\uff0c\u5982\u9700\u5f00\u653e\u8bf7\u8054\u7cfb\u8fd0\u7ef4\u4eba\u5458\u3002");
            }
            Map<String, Object> params = this.bimHelper.validate(request);
            bimRequestId = this.bimHelper.getBimRequestId(params);
            logger.info("UserDeleteService\u5165\u53c2--:[{}]", (Object)params);
            String userId = this.getBimUid(params);
            Assert.hasText(userId, "\u5220\u9664\u7528\u6237\u65f6\u5fc5\u4f20bimUid");
            Optional optional = this.userService.find(userId);
            if (optional.isPresent()) {
                this.userService.delete(userId);
                String title = String.format("\u5220\u9664-\u7528\u6237 %s \u6210\u529f", ((User)optional.get()).getName());
                LogHelper.info((String)"\u5408\u5e76-\u7af9\u4e91\u540c\u6b65", (String)title, (String)("\u5220\u9664\u7528\u6237\u8bf7\u6c42\u5bf9\u8c61:\n" + JsonUtils.writeValueAsString(params)));
            } else {
                logger.info("id\u4e3a{}\u7684\u7528\u6237\u4e0d\u5b58\u5728\uff0c\u4e0d\u4f5c\u5220\u9664\u64cd\u4f5c", (Object)userId);
            }
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7528\u6237UserDeleteService\u51fa\u73b0\u5f02\u5e38", e);
            String errorJson = this.bimHelper.errorJson(bimRequestId, e);
            return this.bimHelper.encrypt(errorJson);
        }
        GeneralDTO response = new GeneralDTO(bimRequestId, "0", "success");
        String json = JsonUtils.writeValueAsString((Object)response);
        logger.info("UserDeleteService\u7ed3\u679cjson --:{}", (Object)json);
        return this.bimHelper.encrypt(json);
    }

    private AccountDTO user2AccountVO(UserEntity user) {
        AccountDTO vo = new AccountDTO();
        vo.setCode(user.getId());
        vo.setName(user.getName());
        vo.setNickName(this.getNickName(user));
        vo.setPhone(this.getPhone(user));
        vo.setEmail(this.getEmail(user));
        vo.setEnable(user.getEnabled());
        vo.setCreateTime(this.instantToString(user.getCreateTime()));
        vo.setUpdateTime(this.getUpdateTime(user));
        vo.setOrgCode(this.getOrgCode(user));
        vo.setIdNumber(this.getIdNumber(user));
        vo.setSecurityLevel(this.getSecurityLevel(user));
        return vo;
    }

    private String getSecurityLevel(UserEntity user) {
        return user.getSecurityLevel() != null ? user.getSecurityLevel() : "";
    }
}

