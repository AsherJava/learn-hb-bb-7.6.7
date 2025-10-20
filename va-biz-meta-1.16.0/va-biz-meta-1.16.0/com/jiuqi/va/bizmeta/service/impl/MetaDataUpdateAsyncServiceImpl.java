/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.ruler.common.consts.FieldPropertyTypeEnum
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataUpdateState
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  org.apache.commons.beanutils.PropertyUtils
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.common.consts.FieldPropertyTypeEnum;
import com.jiuqi.va.bizmeta.cache.MetaDataUpdateCache;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataLockDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaLockDo;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateAsyncService;
import com.jiuqi.va.bizmeta.service.impl.MetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataUpdateState;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MetaDataUpdateAsyncServiceImpl
implements MetaDataUpdateAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(MetaDataUpdateAsyncServiceImpl.class);
    private static final Map<String, DataModelDO> dataTableMap = new HashMap<String, DataModelDO>();
    private static final Map<String, UserDO> userMap = new HashMap<String, UserDO>();
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private IMetaDataLockDao iMetaDataLockDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private MetaVersionService metaVersionService;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private IMetaDataInfoVersionDao metaInfoHistoryDao;
    @Autowired
    private IMetaDataInfoDao iMetaDataInfoDao;
    @Autowired
    private IMetaDataDesignDao iMetaDataDesignDao;
    @Autowired
    private IMetaDataInfoUserDao iMetaDataInfoUserDao;
    @Autowired
    private IMetaDataDesignUserDao iMetaDataDesignUserDao;

    @Override
    @Async
    public void asyncHandleUpdate(List<MetaInfoDO> allMeteInfo, List<MetaInfoEditionDO> allAppendMeteInfoUser, MetaDataBatchUpdateProgress progress) {
        this.execute(allMeteInfo, allAppendMeteInfoUser, progress);
    }

    @Override
    public void syncHandleUpdate(List<MetaInfoDO> allMeteInfo, List<MetaInfoEditionDO> allAppendMeteInfoUser, MetaDataBatchUpdateProgress progress) {
        this.execute(allMeteInfo, allAppendMeteInfoUser, progress);
    }

    private void execute(List<MetaInfoDO> allMeteInfo, List<MetaInfoEditionDO> allAppendMeteInfoUser, MetaDataBatchUpdateProgress progress) {
        R execute = RedisLockUtil.execute(() -> {
            try {
                this.handleAppendMetaInfo(allAppendMeteInfoUser, progress);
                this.handleDeployMetaInfo(allMeteInfo, progress);
            }
            finally {
                dataTableMap.clear();
            }
        }, (String)"META_DATA_UPDATE_CACHE_KEY", (long)30000L, (boolean)true);
        if (execute.getCode() != 0) {
            logger.error("\u66f4\u65b0\u5143\u6570\u636e\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)execute.getMsg());
            progress.setErrorMsg(execute.getMsg());
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
        }
    }

    private void handleDeployMetaInfo(List<MetaInfoDO> deployList, MetaDataBatchUpdateProgress progress) {
        for (MetaInfoDO metaInfoDO : deployList) {
            progress.addCurrent();
            Long versionNO = metaInfoDO.getVersionNO();
            List<MetaInfoEditionDO> allModifyMeteInfoUser = this.getAllModifyMeteInfoUser(metaInfoDO.getUniqueCode(), versionNO, progress);
            try {
                this.deployMetaInfo(metaInfoDO, progress);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                progress.addErrorMetaData(new MetaDataUpdateState(metaInfoDO.getName(), metaInfoDO.getTitle(), e.getMessage(), false, null, "\u5df2\u53d1\u5e03"));
                MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
                continue;
            }
            this.handleModifyMetaInfo(allModifyMeteInfoUser, metaInfoDO.getVersionNO(), progress);
        }
    }

    private void handleModifyMetaInfo(List<MetaInfoEditionDO> metaInfoEditionDOS, Long versionNO, MetaDataBatchUpdateProgress progress) {
        for (MetaInfoEditionDO metaInfoEditionDO : metaInfoEditionDOS) {
            metaInfoEditionDO.setVersionNO(versionNO);
            metaInfoEditionDO.setRowVersion(Long.valueOf(System.currentTimeMillis()));
            try {
                this.isLocked(metaInfoEditionDO.getUniqueCode());
                this.updateUserMetaInfo(metaInfoEditionDO, progress);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                progress.addErrorMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), e.getMessage(), false, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
                MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            }
        }
    }

    public void updateUserMetaInfo(MetaInfoEditionDO metaInfoEditionDO, MetaDataBatchUpdateProgress progress) {
        MetaDataEditionDO select = new MetaDataEditionDO();
        select.setId(metaInfoEditionDO.getId());
        MetaDataEditionDO metaDataEditionDO = (MetaDataEditionDO)((Object)this.iMetaDataDesignUserDao.selectOne((Object)select));
        if (metaDataEditionDO == null) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), "\u5143\u6570\u636e\u8868\u4fe1\u606f\u4e3a\u7a7a", true, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        String designData = metaDataEditionDO.getDesignData();
        if (!StringUtils.hasText(designData)) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), "\u5143\u6570\u636edesignData\u4e3a\u7a7a", true, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        Map newDesignData = JSONUtil.parseMap((String)designData);
        boolean change = this.addFixedColumn(newDesignData);
        if (!change) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), "\u65e0\u65b0\u589e\u56fa\u5316\u5b57\u6bb5", true, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        TransactionStatus transaction = this.platformTransactionManager.getTransaction((TransactionDefinition)new DefaultTransactionDefinition());
        try {
            long l = System.currentTimeMillis();
            boolean b = this.updateModifyMetaInfo(metaInfoEditionDO, JSONUtil.toJSONString((Object)newDesignData));
            if (!b) {
                throw new RuntimeException("\u66f4\u65b0\u5931\u8d25");
            }
            progress.addSuccessMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), "\u5347\u7ea7\u6210\u529f", true, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            logger.info("\u66f4\u65b0\u7528\u6237\u5143\u6570\u636e\u8017\u65f6\uff1a{}", (Object)(System.currentTimeMillis() - l));
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            this.platformTransactionManager.rollback(transaction);
            throw e;
        }
    }

    public void deployMetaInfo(MetaInfoDO metaInfoDO, MetaDataBatchUpdateProgress progress) {
        UUID id = metaInfoDO.getId();
        MetaDataDTO metaDataById = this.metaDataService.getMetaDataById(id);
        if (metaDataById == null) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoDO.getName(), metaInfoDO.getTitle(), "\u5143\u6570\u636e\u8868\u4fe1\u606f\u4e3a\u7a7a", true, null, "\u5df2\u53d1\u5e03"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        String designData = metaDataById.getDesignData();
        if (!StringUtils.hasText(designData)) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoDO.getName(), metaInfoDO.getTitle(), "\u5143\u6570\u636edesignData\u4e3a\u7a7a", true, null, "\u5df2\u53d1\u5e03"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        Map designDataMap = JSONUtil.parseMap((String)designData);
        boolean b = this.addFixedColumn(designDataMap);
        if (!b) {
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoDO.getName(), metaInfoDO.getTitle(), "\u65e0\u65b0\u589e\u56fa\u5316\u5b57\u6bb5", true, null, "\u5df2\u53d1\u5e03"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            return;
        }
        TransactionStatus transaction = this.platformTransactionManager.getTransaction((TransactionDefinition)new DefaultTransactionDefinition());
        try {
            long l = System.currentTimeMillis();
            String newDesignData = JSONUtil.toJSONString((Object)designDataMap);
            this.isLocked(metaInfoDO.getUniqueCode());
            long newVersionNO = System.currentTimeMillis();
            metaInfoDO.setVersionNO(Long.valueOf(newVersionNO));
            metaInfoDO.setRowVersion(Long.valueOf(newVersionNO));
            MetaInfoDO infoDO = new MetaInfoDO();
            infoDO.setId(id);
            this.iMetaDataInfoDao.delete(infoDO);
            this.metaDataService.deleteMetaDataVById(id);
            UUID newInfoId = UUID.randomUUID();
            metaInfoDO.setId(newInfoId);
            this.iMetaDataInfoDao.insert(metaInfoDO);
            MetaDataDO metaDataDO = new MetaDataDO();
            metaDataDO.setId(newInfoId);
            metaDataDO.setDesignData(newDesignData);
            this.iMetaDataDesignDao.insert((Object)metaDataDO);
            MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
            MetaDataDTO metaDataDTO = new MetaDataDTO();
            try {
                PropertyUtils.copyProperties((Object)metaInfoDTO, (Object)metaInfoDO);
                PropertyUtils.copyProperties((Object)metaDataDTO, (Object)((Object)metaDataDO));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            metaInfoDTO.setDesignData(newDesignData);
            this.metaSyncCacheService.updateCache(metaDataDTO, metaInfoDTO, MetaState.DEPLOYED.getValue());
            this.insertHistoryMetaData(metaInfoDTO, newInfoId);
            this.metaSyncCacheService.pushSyncMsg(metaInfoDO.getId(), MetaState.DEPLOYED.getValue());
            progress.addSuccessMetaData(new MetaDataUpdateState(metaInfoDO.getName(), metaInfoDO.getTitle(), "\u5347\u7ea7\u6210\u529f", true, null, "\u5df2\u53d1\u5e03"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
            logger.info("\u5143\u6570\u636e\u66f4\u65b0\u8017\u65f6\uff1a{}", (Object)(System.currentTimeMillis() - l));
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            this.platformTransactionManager.rollback(transaction);
            throw e;
        }
    }

    private void isLocked(String uniqueCode) {
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setUniqueCode(uniqueCode);
        MetaLockDo metaLock = (MetaLockDo)((Object)this.iMetaDataLockDao.selectOne((Object)metaLockDo));
        String userName = ShiroUtil.getUser().getId();
        if (metaLock != null && "super".equals(metaLock.getLockuser())) {
            String mgrFlag = ShiroUtil.getUser().getMgrFlag();
            if (StringUtils.hasText(mgrFlag) && !"super".equals(mgrFlag)) {
                throw new RuntimeException("\u5143\u6570\u636e\u5df2\u88abadmin\u9501\u5b9a\u3002");
            }
        } else if (metaLock != null && !userName.equals(metaLock.getLockuser())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(metaLock.getLockuser());
            String lockuser = this.authUserClient.get(userDTO).getName();
            throw new RuntimeException("\u5143\u6570\u636e\u5df2\u88ab" + lockuser + "\u9501\u5b9a\u3002");
        }
    }

    private void insertHistoryMetaData(MetaInfoDTO metaInfoDTO, UUID id) {
        MetaInfoHistoryDO historyDO = new MetaInfoHistoryDO();
        historyDO.setId(id);
        historyDO.setGroupName(metaInfoDTO.getGroupName());
        historyDO.setMetaType(metaInfoDTO.getMetaType());
        historyDO.setModelName(metaInfoDTO.getModelName());
        historyDO.setModuleName(metaInfoDTO.getModuleName());
        historyDO.setName(metaInfoDTO.getName());
        historyDO.setTitle(metaInfoDTO.getTitle());
        historyDO.setVersionNO(metaInfoDTO.getVersionNO());
        historyDO.setRowVersion(metaInfoDTO.getRowVersion());
        historyDO.setGroupName(metaInfoDTO.getGroupName());
        historyDO.setUniqueCode(metaInfoDTO.getUniqueCode());
        this.metaInfoHistoryDao.insert((Object)historyDO);
        MetaDataHistoryDO dataHistoryDO = new MetaDataHistoryDO();
        dataHistoryDO.setId(id);
        dataHistoryDO.setDesignData(metaInfoDTO.getDesignData());
        this.metaDataService.createMetaHData(dataHistoryDO);
        MetaDataVersionDTO dataVersionDO = new MetaDataVersionDTO();
        dataVersionDO.setCreateTime(new Date());
        dataVersionDO.setInfo(null);
        dataVersionDO.setUserName(ShiroUtil.getUser().getId());
        dataVersionDO.setVersionNo(metaInfoDTO.getVersionNO());
        this.metaVersionService.addMetaVersionInfo(dataVersionDO);
    }

    private boolean updateDeployedMetaInfo(MetaInfoDO metaInfoDO, String newDesignData) {
        if (this.iMetaDataInfoDao.updateByPrimaryKey(metaInfoDO) < 1) {
            return false;
        }
        MetaDataDO dataDO = new MetaDataDO();
        dataDO.setId(metaInfoDO.getId());
        dataDO.setDesignData(newDesignData);
        return this.iMetaDataDesignDao.updateByPrimaryKey((Object)dataDO) > 0;
    }

    private boolean updateModifyMetaInfo(MetaInfoEditionDO metaInfoEditionDO, String newDesignData) {
        if (this.iMetaDataInfoUserDao.updateByPrimaryKey(metaInfoEditionDO) < 1) {
            return false;
        }
        MetaDataEditionDO metaDataEditionDO = new MetaDataEditionDO();
        metaDataEditionDO.setId(metaInfoEditionDO.getId());
        metaDataEditionDO.setDesignData(newDesignData);
        return this.iMetaDataDesignUserDao.updateByPrimaryKey((Object)metaDataEditionDO) > 0;
    }

    private void handleAppendMetaInfo(List<MetaInfoEditionDO> appendList, MetaDataBatchUpdateProgress progress) {
        for (MetaInfoEditionDO metaInfoDTO : appendList) {
            Map designDataMap;
            UUID id;
            block5: {
                progress.addCurrent();
                id = metaInfoDTO.getId();
                MetaDataEditionDO metaDataUserById = this.metaDataService.getMetaDataUserById(id);
                String designData = metaDataUserById.getDesignData();
                if (!StringUtils.hasText(designData)) {
                    progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoDTO.getName(), metaInfoDTO.getTitle(), "\u5143\u6570\u636e\u8868\u4fe1\u606f\u4e3a\u7a7a", true, this.getUserName(metaInfoDTO.getUserName()), "\u65b0\u589e"));
                    MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
                    continue;
                }
                designDataMap = JSONUtil.parseMap((String)designData);
                try {
                    boolean b = this.addFixedColumn(designDataMap);
                    if (!b) {
                        progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoDTO.getName(), metaInfoDTO.getTitle(), "\u65e0\u65b0\u589e\u56fa\u5316\u5b57\u6bb5", true, this.getUserName(metaInfoDTO.getUserName()), "\u65b0\u589e"));
                        MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
                    }
                    break block5;
                }
                catch (Exception e) {
                    progress.addErrorMetaData(new MetaDataUpdateState(metaInfoDTO.getName(), metaInfoDTO.getTitle(), e.getMessage(), false, this.getUserName(metaInfoDTO.getUserName()), "\u65b0\u589e"));
                    MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
                    logger.error(e.getMessage(), e);
                }
                continue;
            }
            String newDesignData = JSONUtil.toJSONString((Object)designDataMap);
            MetaDataDTO metaDataDTO = new MetaDataDTO();
            metaDataDTO.setId(id);
            metaDataDTO.setDesignData(newDesignData);
            this.metaDataService.updateMetaData(metaDataDTO);
            progress.addSuccessMetaData(new MetaDataUpdateState(metaInfoDTO.getName(), metaInfoDTO.getTitle(), "\u5347\u7ea7\u6210\u529f", true, this.getUserName(metaInfoDTO.getUserName()), "\u65b0\u589e"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
        }
    }

    private List<MetaInfoEditionDO> getAllModifyMeteInfoUser(String uniqueCode, Long version, MetaDataBatchUpdateProgress progress) {
        MetaInfoEditionDO select = new MetaInfoEditionDO();
        select.setMetaType("bill");
        select.setUniqueCode(uniqueCode);
        select.setMetaState(Integer.valueOf(2));
        List metaInfoEditionDOS = this.iMetaDataInfoUserDao.select(select);
        ArrayList<MetaInfoEditionDO> result = new ArrayList<MetaInfoEditionDO>();
        for (MetaInfoEditionDO metaInfoEditionDO : metaInfoEditionDOS) {
            if (metaInfoEditionDO.getVersionNO() >= version) {
                result.add(metaInfoEditionDO);
                continue;
            }
            progress.addNoChangeMetaData(new MetaDataUpdateState(metaInfoEditionDO.getName(), metaInfoEditionDO.getTitle(), "\u4f4e\u4e8e\u57fa\u7ebf\u7248\u672c", true, this.getUserName(metaInfoEditionDO.getUserName()), "\u4fee\u6539"));
            MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
        }
        return result;
    }

    private boolean addFixedColumn(Map<String, Object> designData) {
        List plugins = (List)designData.get("plugins");
        if (CollectionUtils.isEmpty(plugins)) {
            return false;
        }
        List dataPluginTables = null;
        for (Object plugin : plugins) {
            String pluginName = (String)plugin.get("type");
            if (!"data".equals(pluginName)) continue;
            dataPluginTables = (List)plugin.get("tables");
            break;
        }
        if (CollectionUtils.isEmpty(dataPluginTables)) {
            return false;
        }
        List rulePluginFormulas = null;
        for (Map plugin : plugins) {
            String pluginName = (String)plugin.get("type");
            if (!"ruler".equals(pluginName)) continue;
            rulePluginFormulas = (List)plugin.get("formulas");
            break;
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(ShiroUtil.getTenantName());
        boolean isChange = false;
        for (Map tableMap : dataPluginTables) {
            DataModelDO dataModelDO;
            if (!DataTableType.DATA.name().equals(tableMap.get("tableType"))) continue;
            String tableName = String.valueOf(tableMap.get("tableName"));
            List fields = (List)tableMap.get("fields");
            List fieldNames = fields.stream().map(map -> String.valueOf(map.get("fieldName"))).collect(Collectors.toList());
            if (dataTableMap.containsKey(tableName)) {
                dataModelDO = dataTableMap.get(tableName);
            } else {
                dataModelDTO.setName(tableName);
                dataModelDO = this.dataModelClient.get(dataModelDTO);
                dataTableMap.put(tableName, dataModelDO);
            }
            if (dataModelDO == null) {
                throw new RuntimeException("\u6570\u636e\u5efa\u6a21\u4e0d\u5b58\u5728\uff1a" + tableName);
            }
            List columns = dataModelDO.getColumns();
            for (DataModelColumn column : columns) {
                int fieldIndex;
                if (!DataModelType.ColumnAttr.FIXED.equals((Object)column.getColumnAttr()) || (fieldIndex = fieldNames.indexOf(column.getColumnName())) != -1) continue;
                isChange = true;
                Map<String, Object> fieldMap = this.declareField(dataModelDO.getName(), column);
                fields.add(fieldMap);
                logger.info(String.format("\u6dfb\u52a0\u56fa\u5b9a\u5b57\u6bb5%s:%s[%s]", designData.get("name"), tableName, column.getColumnName()));
                if (rulePluginFormulas == null || !StringUtils.hasText(column.getDefaultVal())) continue;
                rulePluginFormulas.add(this.declareFormula(fieldMap, dataModelDO, column));
                logger.info(String.format("\u6dfb\u52a0\u56fa\u5b9a\u516c\u5f0f%s:%s[%s]\uff0c%s", designData.get("name"), tableName, column.getColumnName(), column.getDefaultVal()));
            }
        }
        return isChange;
    }

    private Map<String, Object> declareFormula(Map<String, Object> field, DataModelDO dataModelDO, DataModelColumn column) {
        LinkedHashMap<String, Object> formula = new LinkedHashMap<String, Object>();
        String formulaName = dataModelDO.getName() + "_" + field.get("fieldName") + "_" + FieldPropertyTypeEnum.calculation.name().toUpperCase();
        String formulaTitle = dataModelDO.getTitle() + "[" + field.get("title") + "]";
        String expression = "if IsNull(" + dataModelDO.getName() + "[" + field.get("fieldName") + "]) then " + column.getDefaultVal() + " else " + dataModelDO.getName() + "[" + field.get("fieldName") + "]";
        formula.put("id", UUID.randomUUID());
        formula.put("used", true);
        formula.put("name", formulaName);
        formula.put("title", formulaTitle);
        formula.put("formulaType", "EVALUATE");
        formula.put("expression", expression);
        formula.put("triggerType", "field-property");
        formula.put("checkMessage", "");
        formula.put("objectType", "field");
        formula.put("objectId", field.get("id"));
        formula.put("propertyType", "calculation");
        formula.put("checkState", "SUCCESS");
        return formula;
    }

    private Map<String, Object> declareField(String tableName, DataModelColumn dataModelColumn) {
        Integer mappingType;
        LinkedHashMap<String, Object> fieldDeclare = new LinkedHashMap<String, Object>();
        fieldDeclare.put("id", UUID.nameUUIDFromBytes((tableName + '.' + dataModelColumn.getColumnName()).getBytes(StandardCharsets.UTF_8)));
        fieldDeclare.put("name", dataModelColumn.getColumnName());
        fieldDeclare.put("title", dataModelColumn.getColumnTitle());
        fieldDeclare.put("fieldType", DataFieldType.DATA);
        fieldDeclare.put("fieldName", dataModelColumn.getColumnName());
        fieldDeclare.put("valueType", this.toValueType(dataModelColumn.getColumnType()));
        if (ValueType.DECIMAL.equals(fieldDeclare.get("valueType"))) {
            fieldDeclare.put("length", dataModelColumn.getLengths()[0]);
            fieldDeclare.put("digits", dataModelColumn.getLengths().length > 1 ? dataModelColumn.getLengths()[1] : 0);
            if (fieldDeclare.get("digits").equals(0)) {
                if (fieldDeclare.get("length").equals(19)) {
                    fieldDeclare.put("valueType", ValueType.LONG);
                } else if (fieldDeclare.get("length").equals(10)) {
                    fieldDeclare.put("valueType", ValueType.INTEGER);
                } else if (fieldDeclare.get("length").equals(1)) {
                    fieldDeclare.put("valueType", ValueType.BOOLEAN);
                }
            }
        }
        if (ValueType.STRING.equals(fieldDeclare.get("valueType"))) {
            fieldDeclare.put("length", dataModelColumn.getLengths()[0]);
        }
        if ((mappingType = dataModelColumn.getMappingType()) != null) {
            if (DataModelType.ColumnType.INTEGER.equals((Object)dataModelColumn.getColumnType()) && mappingType == 0) {
                fieldDeclare.put("valueType", ValueType.BOOLEAN);
            } else if (StringUtils.hasText(dataModelColumn.getMapping())) {
                fieldDeclare.put("refTableType", mappingType);
                String mapping = dataModelColumn.getMapping();
                String[] refs = mapping.split("\\.", -1);
                String refTableName = refs[refs.length - 2];
                fieldDeclare.put("refTableName", refTableName);
            }
        }
        if (!fieldDeclare.containsKey("length")) {
            fieldDeclare.put("length", 0);
        }
        if (!fieldDeclare.containsKey("digits")) {
            fieldDeclare.put("digits", 0);
        }
        if (!fieldDeclare.containsKey("refTableType")) {
            fieldDeclare.put("refTableType", 0);
        }
        if (!fieldDeclare.containsKey("refTableName")) {
            fieldDeclare.put("refTableName", null);
        }
        fieldDeclare.put("defaultVal", dataModelColumn.getDefaultVal());
        fieldDeclare.put("columnAttr", "FIXED");
        Map dataFieldValue = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)new DataFieldDefineImpl()));
        for (String s : dataFieldValue.keySet()) {
            if (fieldDeclare.containsKey(s)) continue;
            fieldDeclare.put(s, dataFieldValue.get(s));
        }
        return fieldDeclare;
    }

    private ValueType toValueType(DataModelType.ColumnType columnType) {
        switch (columnType) {
            case UUID: {
                return ValueType.IDENTIFY;
            }
            case CLOB: {
                return ValueType.TEXT;
            }
            case DATE: {
                return ValueType.DATE;
            }
            case INTEGER: {
                return ValueType.INTEGER;
            }
            case NUMERIC: {
                return ValueType.DECIMAL;
            }
            case NVARCHAR: {
                return ValueType.STRING;
            }
            case TIMESTAMP: {
                return ValueType.DATETIME;
            }
        }
        return ValueType.AUTO;
    }

    private String getUserName(String uuid) {
        if (!StringUtils.hasText(uuid)) {
            return null;
        }
        if (userMap.containsKey(uuid)) {
            UserDO userDO = userMap.get(uuid);
            if (userDO == null) {
                return null;
            }
            return userDO.getName();
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(uuid);
        UserDO userDO = this.authUserClient.get(userDTO);
        userMap.put(uuid, userDO);
        if (userDO == null) {
            return null;
        }
        return userDO.getName();
    }
}

