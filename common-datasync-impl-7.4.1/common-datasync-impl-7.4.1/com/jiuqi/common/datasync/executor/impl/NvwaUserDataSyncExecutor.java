/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.BatchUserAttributeDTO
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaPasswordClient
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserManageOrgReq
 *  com.jiuqi.nvwa.glue.client.user.MergeDataUtil
 *  com.jiuqi.nvwa.glue.common.GlueSyncEnv
 *  com.jiuqi.nvwa.glue.data.Attribute
 *  com.jiuqi.nvwa.glue.data.GlueUser
 *  com.jiuqi.nvwa.glue.data.impl.GlueUserDTO
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.executor.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.converter.NvwaUserConverter;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaUserDTO;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.BatchUserAttributeDTO;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserManageOrgReq;
import com.jiuqi.nvwa.glue.client.user.MergeDataUtil;
import com.jiuqi.nvwa.glue.common.GlueSyncEnv;
import com.jiuqi.nvwa.glue.data.Attribute;
import com.jiuqi.nvwa.glue.data.GlueUser;
import com.jiuqi.nvwa.glue.data.impl.GlueUserDTO;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class NvwaUserDataSyncExecutor
implements CommonDataSyncExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(NvwaUserDataSyncExecutor.class);
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private NvwaPasswordClient nvwaPasswordClient;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommonDataSyncService dataSyncService;
    @Autowired
    private NvwaUserService nvwaUserService;

    @Override
    public String title() {
        return "\u5973\u5a32\u5e73\u53f0\u7528\u6237\u6570\u636e\u540c\u6b65";
    }

    @Override
    public String type() {
        return "NvwaUser";
    }

    @Override
    public String description() {
        return "<p>\u63a5\u53e3\u53c2\u6570\u793a\u4f8b\u63cf\u8ff0: \u4e00\u672c\u8d26\u670d\u52a1(\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-servcie)\u62bd\u53d6\u5408\u5e76\u62a5\u8868\u670d\u52a1\uff08\u88ab\u8c03\u7528\u65b9\u670d\u52a1url\uff1ahttp://10.2.33.35:8188\uff09\u7684\u7528\u6237\u6570\u636e</p><p>\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-service</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u5730\u5740\uff1ahttp://10.2.33.35:8188</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u7528\u6237\uff1aadmin</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u5bc6\u7801\uff1agcP@ssw0rd</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\uff1a<p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\u63cf\u8ff0\uff1a\u672c\u793a\u4f8b\u4e3a\u4e00\u672c\u8d26\u670d\u52a1\u62bd\u53d6\u5408\u5e76\u670d\u52a1\u7684\u7528\u6237\u6570\u636e\u3002</p>";
    }

    @Override
    public void execute(CommonDataSyncExecutorContext context) {
        CommonDataSyncSettingItemDTO itemDTO = context.getDataSyncSettingItemDTO();
        this.saveDataSyncDatas(itemDTO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveDataSyncDatas(CommonDataSyncSettingItemDTO itemDTO) {
        List<DataSyncNvwaUserDTO> fetchDataSyncDatas = this.fetchDataSyncDatas(itemDTO);
        this.saveDataSyncDatas(fetchDataSyncDatas);
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveDataSyncDatas(List<DataSyncNvwaUserDTO> nvwaUserDTOS) {
        if (CollectionUtils.isEmpty(nvwaUserDTOS)) {
            this.LOGGER.info("\u672a\u62bd\u53d6\u5230\u7528\u6237\u3002");
            return;
        }
        this.LOGGER.info("\u62bd\u53d6\u5230" + nvwaUserDTOS.size() + "\u4e2a\u7528\u6237\u6570\u636e");
        AtomicInteger addCount = new AtomicInteger(0);
        int currentIndex = 0;
        NvwaUserDataSyncExecutor dataSyncExecutor = (NvwaUserDataSyncExecutor)SpringContextUtils.getBean(NvwaUserDataSyncExecutor.class);
        for (DataSyncNvwaUserDTO nvwaUserDTO : nvwaUserDTOS) {
            ++currentIndex;
            try {
                dataSyncExecutor.insertOrUpdateUser(nvwaUserDTO, addCount, currentIndex);
            }
            catch (Exception e) {
                this.LOGGER.error(e.getMessage(), e);
            }
        }
        this.LOGGER.info("\u65b0\u589e" + addCount.get() + "\u4e2a\u7528\u6237,\u66f4\u65b0" + (nvwaUserDTOS.size() - addCount.get()) + "\u4e2a\u7528\u6237");
    }

    public List<DataSyncNvwaUserDTO> fetchDataSyncDatas(CommonDataSyncSettingItemDTO itemDTO) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return Collections.emptyList();
        }
        List nvwaUserDTOS = Collections.emptyList();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaUserDTO>> nvwaUserDTOsResponseEntity = this.dataSyncService.getNvwaFeignClient().getNvwaUserDTOs(new URI(itemDTO.getUrl()), userDTO);
            if (nvwaUserDTOsResponseEntity.getData() != null) {
                nvwaUserDTOS = (List)nvwaUserDTOsResponseEntity.getData();
            }
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        return nvwaUserDTOS;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdateUser(DataSyncNvwaUserDTO nvwaUserDTO, AtomicInteger addCount, int currentIndex) {
        GlueSyncEnv glueEnv = new GlueSyncEnv();
        GlueUserDTO glueUserDTO = NvwaUserConverter.convertGlueDTO(nvwaUserDTO);
        UserDTO exsitsUser = this.nvwaUserClient.findByUsername(glueUserDTO.getUsername());
        if (exsitsUser == null) {
            this.LOGGER.info("\u62bd\u53d6\u7b2c" + currentIndex + "\u4e2a\u7528\u6237\u505a\u65b0\u5efa,name:" + glueUserDTO.getUsername());
            String id = this.addUser(glueUserDTO, glueEnv);
            this.addUserAttribute(id, glueUserDTO.getAttribute());
            this.manageOrg(nvwaUserDTO.getGrantedOrgS(), id);
            addCount.getAndIncrement();
        } else {
            this.LOGGER.info("\u62bd\u53d6\u7b2c" + currentIndex + "\u4e2a\u7528\u6237\u505a\u66f4\u65b0,name:" + glueUserDTO.getUsername());
            UserDTO userEntity = MergeDataUtil.mergeUser((GlueUser)glueUserDTO, (User)exsitsUser, (GlueSyncEnv)glueEnv);
            this.nvwaUserClient.update(userEntity);
            List attribute = glueUserDTO.getAttribute();
            this.addUserAttribute(glueUserDTO.getId(), attribute);
            this.manageOrg(nvwaUserDTO.getGrantedOrgS(), userEntity.getId());
        }
    }

    private String addUser(GlueUserDTO glueUserDTO, GlueSyncEnv glueEnv) {
        String id = glueUserDTO.getId();
        UserDTO userEntity = MergeDataUtil.mergeUser((GlueUser)glueUserDTO, null, (GlueSyncEnv)glueEnv);
        if (!this.roleService.isGranted("ffffffff-ffff-ffff-bbbb-ffffffffffff", id)) {
            this.roleService.grant("ffffffff-ffff-ffff-bbbb-ffffffffffff", id);
        }
        this.nvwaUserClient.create(userEntity);
        this.nvwaPasswordClient.delete(id);
        this.nvwaPasswordClient.create(id, glueUserDTO.getUsername());
        return id;
    }

    private void addUserAttribute(String id, List<Attribute> attribute) {
        this.nvwaUserClient.deleteExtendedAttribute(id, null);
        BatchUserAttributeDTO attributes = MergeDataUtil.mergeAttribute((String)id, attribute);
        if (attributes == null) {
            return;
        }
        this.nvwaUserClient.batchAddAttribute(attributes);
    }

    private void manageOrg(List<String> grantOrgS, String userId) {
        UserManageOrgReq userManageOrgReq = new UserManageOrgReq();
        userManageOrgReq.setManageOrgCodes(grantOrgS);
        userManageOrgReq.setId(userId);
        this.nvwaUserService.manageOrg(userManageOrgReq);
    }
}

