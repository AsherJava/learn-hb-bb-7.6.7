/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.executor.impl;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaEntityIdentityDTO;
import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaEntityIdentityOrignDTO;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class NvwaEntityIdentityDataSyncExecutor
implements CommonDataSyncExecutor {
    private final Logger LOGGER = LoggerFactory.getLogger(NvwaEntityIdentityDataSyncExecutor.class);
    @Autowired
    private EntityIdentityService entityIdentityService;
    @Autowired
    private CommonDataSyncService dataSyncService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String SQL_getTableName2KeyMaps = "select TD_CODE, TD_KEY from sys_tabledefine";
    public static final String SQL_getOrignEntityIdentityDTOs = "select identity1.ENTITY_TABLE_KEY_ as ENTITY_TABLE_KEY_, identity1.ENTITY_DATA_KEY_ as ENTITY_DATA_KEY_, identity1.IDENTITY_ID_ as IDENTITY_ID_ from NP_AUTHZ_ENTITY_IDENTITY identity1";

    @Override
    public String title() {
        return "\u5973\u5a32\u5e73\u53f0\u4e3b\u4f53\u8eab\u4efd\u6743\u9650\u6570\u636e\u540c\u6b65";
    }

    @Override
    public String type() {
        return "NvwaEntityIdentity";
    }

    @Override
    public String description() {
        return "<p>\u63a5\u53e3\u53c2\u6570\u793a\u4f8b\u63cf\u8ff0: \u4e00\u672c\u8d26\u670d\u52a1(\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-servcie)\u62bd\u53d6\u5408\u5e76\u62a5\u8868\u670d\u52a1\uff08\u88ab\u8c03\u7528\u65b9\u670d\u52a1url\uff1ahttp://10.2.33.35:8188\uff09\u7684\u7528\u6237\u6240\u5c5e\u4e3b\u4f53\u8eab\u4efd\u4fe1\u606f</p><p>\u8c03\u7528\u65b9\u670d\u52a1\u540d\uff1adatacenter-service</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u5730\u5740\uff1ahttp://10.2.33.35:8188</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u7528\u6237\uff1aadmin</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u8ba4\u8bc1\u5bc6\u7801\uff1agcP@ssw0rd</p><p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\uff1a<p>\u88ab\u8c03\u7528\u65b9\u670d\u52a1\u63a5\u53e3\u53c2\u6570\u63cf\u8ff0\uff1a\u672c\u793a\u4f8b\u4e3a\u4e00\u672c\u8d26\u670d\u52a1\u62bd\u53d6\u5408\u5e76\u670d\u52a1\u7684\u7528\u6237\u6240\u5c5e\u4e3b\u4f53\u8eab\u4efd\u4fe1\u606f\u3002</p>";
    }

    @Override
    public void execute(CommonDataSyncExecutorContext context) {
        CommonDataSyncSettingItemDTO itemDTO = context.getDataSyncSettingItemDTO();
        this.dataSync(itemDTO);
    }

    public void dataSync(CommonDataSyncSettingItemDTO itemDTO) {
        List<DataSyncNvwaEntityIdentityDTO> nvwaEntityIdentityDTOS = this.fetchDataSyncDatas(itemDTO);
        if (CollectionUtils.isEmpty(nvwaEntityIdentityDTOS)) {
            this.LOGGER.info("\u672a\u62bd\u53d6\u5230\u7528\u6237\u5173\u8054\u4e3b\u4f53\u3002");
            return;
        }
        this.saveDataSyncDatas(nvwaEntityIdentityDTOS);
    }

    public List<DataSyncNvwaEntityIdentityDTO> fetchDataSyncDatas(CommonDataSyncSettingItemDTO itemDTO) {
        if (ObjectUtils.isEmpty(itemDTO.getUrl())) {
            return null;
        }
        List<DataSyncNvwaEntityIdentityDTO> collection = new ArrayList<DataSyncNvwaEntityIdentityDTO>();
        try {
            NvwaLoginUserDTO userDTO = this.dataSyncService.initNvwaFeignClientTokenEnv(itemDTO.getUrl(), itemDTO.getUsername(), itemDTO.getPassword());
            BusinessResponseEntity<List<DataSyncNvwaEntityIdentityDTO>> authIdentitys = this.dataSyncService.getNvwaFeignClient().getNvwaEntityIdentityDTOs(new URI(itemDTO.getUrl()), userDTO);
            collection = (List)authIdentitys.getData();
        }
        catch (Exception e) {
            this.LOGGER.error(e.getMessage(), e);
        }
        return collection;
    }

    @Transactional(rollbackFor={Exception.class})
    public void saveDataSyncDatas(List<DataSyncNvwaEntityIdentityDTO> nvwaEntityIdentityDTOS) {
        this.LOGGER.info("\u62bd\u53d6\u5230" + nvwaEntityIdentityDTOS.size() + "\u4e2a\u7528\u6237\u5173\u8054\u4e3b\u4f53");
        if (CollectionUtils.isEmpty(nvwaEntityIdentityDTOS)) {
            return;
        }
        Map<String, String> entitytTableName2KeyMap = this.getTableName2KeyMaps();
        Map<String, String> userName2IdMap = this.userService.getUsers().stream().collect(Collectors.toMap(User::getName, User::getId));
        ArrayList<DataSyncNvwaEntityIdentityOrignDTO> convertEntityIdentityDTOS = new ArrayList<DataSyncNvwaEntityIdentityOrignDTO>();
        for (DataSyncNvwaEntityIdentityDTO nvwaEntityIdentityDTO : nvwaEntityIdentityDTOS) {
            String entityTableName = nvwaEntityIdentityDTO.getEntityTableName();
            String entityDataCode = nvwaEntityIdentityDTO.getEntityDataCode();
            String username = nvwaEntityIdentityDTO.getUsername();
            String entityTableKey = entitytTableName2KeyMap.get(entityTableName);
            if (ObjectUtils.isEmpty(entityTableKey)) {
                this.LOGGER.warn("\u5f53\u524d\u7cfb\u7edf\u672a\u627e\u5230\u4e3b\u4f53Name\u4e3a\u2019".concat(entityTableName).concat("\u2018\u7684\u4e3b\u4f53\u8868\u6620\u5c04\uff0c \u8df3\u8fc7\u8be5\u6761\u6240\u5c5e\u4e3b\u4f53\u6570\u636e\u540c\u6b65\u3002"), (Object)JsonUtils.writeValueAsString((Object)nvwaEntityIdentityDTO));
                continue;
            }
            String userId = userName2IdMap.get(username);
            if (ObjectUtils.isEmpty(userId)) {
                this.LOGGER.warn("\u5f53\u524d\u7cfb\u7edf\u672a\u627e\u5230\u7528\u6237Name\u4e3a\u2019".concat(username).concat("\u2018\u7684\u7528\u6237\u4fe1\u606f\uff0c \u8df3\u8fc7\u8be5\u6761\u6240\u5c5e\u4e3b\u4f53\u6570\u636e\u540c\u6b65\u3002"), (Object)JsonUtils.writeValueAsString((Object)nvwaEntityIdentityDTO));
                continue;
            }
            convertEntityIdentityDTOS.add(new DataSyncNvwaEntityIdentityOrignDTO(entityTableKey, entityDataCode, userId));
        }
        if (CollectionUtils.isEmpty(convertEntityIdentityDTOS)) {
            this.LOGGER.info("\u6ca1\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u4e3b\u4f53\u8eab\u4efd\u6570\u636e\u9700\u8981\u4fdd\u5b58\u3002");
            return;
        }
        HashMap convertEntityIdentityDTOsMap = new HashMap();
        HashSet convertEntityId2TableKeys = new HashSet();
        convertEntityIdentityDTOS.stream().forEach(entityIdentityDTO -> {
            String entityTableKey = entityIdentityDTO.getEntityTableKey();
            String entityDataKey = entityIdentityDTO.getEntityDataKey();
            String entityId = entityIdentityDTO.getEntityId();
            convertEntityIdentityDTOsMap.put(entityId + "_" + entityTableKey + "_" + entityDataKey, entityIdentityDTO);
            convertEntityId2TableKeys.add(entityId + "_" + entityTableKey + "_");
        });
        ArrayList deleteItems = new ArrayList();
        List<DataSyncNvwaEntityIdentityOrignDTO> orignEntityIdentityDTOs = this.getOrignEntityIdentityDTOs();
        orignEntityIdentityDTOs.stream().forEach(orignEntityIdentityDTO -> {
            String entityTableKey = orignEntityIdentityDTO.getEntityTableKey();
            String entityDataKey = orignEntityIdentityDTO.getEntityDataKey();
            String entityId = orignEntityIdentityDTO.getEntityId();
            if (!convertEntityId2TableKeys.contains(entityId + "_" + entityTableKey + "_")) {
                return;
            }
            String key = entityId + "_" + entityTableKey + "_" + entityDataKey;
            if (convertEntityIdentityDTOsMap.get(key) == null) {
                deleteItems.add(orignEntityIdentityDTO);
            }
        });
        NvwaEntityIdentityDataSyncExecutor dataSyncExecutor = (NvwaEntityIdentityDataSyncExecutor)SpringContextUtils.getBean(NvwaEntityIdentityDataSyncExecutor.class);
        if (!CollectionUtils.isEmpty(convertEntityIdentityDTOS)) {
            convertEntityIdentityDTOS.stream().forEach(addOrUpdateItem -> {
                String entityId;
                String entityDataKey;
                String entityTableKey = addOrUpdateItem.getEntityTableKey();
                Boolean granted = this.entityIdentityService.isGranted(entityTableKey, entityDataKey = addOrUpdateItem.getEntityDataKey(), entityId = addOrUpdateItem.getEntityId());
                if (Boolean.FALSE.equals(granted)) {
                    try {
                        dataSyncExecutor.insertOrUpdate(entityTableKey, entityDataKey, entityId);
                    }
                    catch (Exception e) {
                        this.LOGGER.error(e.getMessage(), e);
                    }
                }
            });
        }
        if (!CollectionUtils.isEmpty(deleteItems)) {
            deleteItems.stream().forEach(deleteItem -> {
                String entityId;
                String entityDataKey;
                String entityTableKey = deleteItem.getEntityTableKey();
                Boolean granted = this.entityIdentityService.isGranted(entityTableKey, entityDataKey = deleteItem.getEntityDataKey(), entityId = deleteItem.getEntityId());
                if (Boolean.TRUE.equals(granted)) {
                    try {
                        dataSyncExecutor.delete(entityTableKey, entityDataKey, entityId);
                    }
                    catch (Exception e) {
                        this.LOGGER.error(e.getMessage(), e);
                    }
                }
            });
        }
        this.LOGGER.info("\u672c\u6b21\u65b0\u589e/\u66f4\u65b0" + convertEntityIdentityDTOS.size() + ", \u5220\u9664" + deleteItems.size() + "\u6761\u4e3b\u4f53\u8eab\u4efd\u5173\u8054\u6570\u636e\u8bb0\u5f55\u3002");
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertOrUpdate(String entityTableKey, String entityDataCode, String userId) {
        this.entityIdentityService.grant(entityTableKey, entityDataCode, userId);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void delete(String entityTableKey, String entityDataCode, String userId) {
        this.entityIdentityService.revoke(entityTableKey, entityDataCode, userId);
    }

    public Map<String, String> getTableName2KeyMaps() {
        Map tableName2KeyMap = (Map)this.jdbcTemplate.query(SQL_getTableName2KeyMaps, rs -> {
            HashMap<String, String> map = new HashMap<String, String>();
            while (rs.next()) {
                String tableName = rs.getString(1);
                String tableKey = rs.getString(2);
                map.put(tableName, tableKey);
            }
            return map;
        });
        return tableName2KeyMap;
    }

    public List<DataSyncNvwaEntityIdentityOrignDTO> getOrignEntityIdentityDTOs() {
        List daAuthEntityIdentityDTOS = (List)this.jdbcTemplate.query(SQL_getOrignEntityIdentityDTOs, rs -> {
            ArrayList<DataSyncNvwaEntityIdentityOrignDTO> authEntityIdentityDTOS = new ArrayList<DataSyncNvwaEntityIdentityOrignDTO>();
            while (rs.next()) {
                String entityTableKey = rs.getString(1);
                String entityDataKey = rs.getString(2);
                String entityId = rs.getString(3);
                DataSyncNvwaEntityIdentityOrignDTO nvwaEntityIdentityDTO = new DataSyncNvwaEntityIdentityOrignDTO(entityTableKey, entityDataKey, entityId);
                authEntityIdentityDTOS.add(nvwaEntityIdentityDTO);
            }
            return authEntityIdentityDTOS;
        });
        if (daAuthEntityIdentityDTOS == null) {
            return new ArrayList<DataSyncNvwaEntityIdentityOrignDTO>(0);
        }
        return daAuthEntityIdentityDTOS;
    }
}

