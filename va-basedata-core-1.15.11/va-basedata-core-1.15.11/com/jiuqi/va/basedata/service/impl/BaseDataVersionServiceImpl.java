/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.event.BaseDataVersionEvent
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.common.BaseDataAsyncTask;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.config.VaBasedataCoreConfig;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.dao.VaBaseDataVersionDao;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.domain.BaseDataVersionSyncCacheDTO;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.event.BaseDataVersionEvent;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaBaseDataVersionServiceImpl")
public class BaseDataVersionServiceImpl
implements BaseDataVersionService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataVersionServiceImpl.class);
    @Autowired
    private VaBaseDataVersionDao verDataDao;
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataAsyncTask baseDataAsyncTask;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, List<BaseDataVersionDO>>> dataMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();
    private BaseDataCacheService baseDataCacheService = null;

    private BaseDataCacheService getBaseDataCacheService() {
        if (this.baseDataCacheService == null) {
            this.baseDataCacheService = (BaseDataCacheService)ApplicationContextRegister.getBean(BaseDataCacheService.class);
        }
        return this.baseDataCacheService;
    }

    @Override
    public BaseDataVersionDO get(BaseDataVersionDTO param) {
        this.initCache((BaseDataVersionDO)param);
        String tableName = param.getTablename();
        UUID id = param.getId();
        String name = param.getName();
        Date validtime = param.getValidtime();
        Date invalidtime = param.getInvalidtime();
        Date versionDate = param.getVersionDate();
        List<BaseDataVersionDO> versionList = dataMap.get(param.getTenantName()).get(tableName);
        for (BaseDataVersionDO data : versionList) {
            if (!data.getTablename().equals(tableName) || id != null && !data.getId().equals(id) || name != null && !data.getName().equals(name) || validtime != null && !validtime.equals(data.getValidtime()) || invalidtime != null && !invalidtime.equals(data.getInvalidtime()) || versionDate != null && (versionDate.before(data.getValidtime()) || !versionDate.before(data.getInvalidtime()))) continue;
            return data;
        }
        return null;
    }

    @Override
    public List<BaseDataVersionDO> listCache(BaseDataVersionDTO param) {
        this.initCache((BaseDataVersionDO)param);
        return dataMap.get(param.getTenantName()).get(param.getTablename());
    }

    @Override
    public PageVO<BaseDataVersionDO> list(BaseDataVersionDTO param) {
        PageVO page = new PageVO();
        page.setRs(R.ok());
        List<BaseDataVersionDO> list = this.verDataDao.list(param);
        page.setRows(list);
        page.setTotal(list.size());
        return page;
    }

    @Override
    public R add(BaseDataVersionDO verDataDO) {
        int flag;
        if (!StringUtils.hasText(verDataDO.getTablename()) || !StringUtils.hasText(verDataDO.getName()) || verDataDO.getValidtime() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(verDataDO.getValidtime());
        if (ca.get(1) > 2500) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.effective.date", new Object[0]));
        }
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTablename(verDataDO.getTablename());
        param.setName(verDataDO.getName());
        BaseDataVersionDO oldData = (BaseDataVersionDO)this.verDataDao.selectOne(param);
        if (oldData != null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.name.existed", new Object[0]));
        }
        param.setName(null);
        List<BaseDataVersionDO> verList = this.verDataDao.list(param);
        if (verList != null && !verList.isEmpty()) {
            BaseDataVersionDO nearVersionDO = verList.get(0);
            if (!verDataDO.getValidtime().after(nearVersionDO.getValidtime())) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.effective.date.must.after", nearVersionDO.getName()));
            }
            BaseDataVersionDO newVersionDO = new BaseDataVersionDO();
            newVersionDO.setId(nearVersionDO.getId());
            newVersionDO.setInvalidtime(verDataDO.getValidtime());
            newVersionDO.setModifytime(new Date());
            this.verDataDao.updateByPrimaryKeySelective(newVersionDO);
            this.sendEvent(BaseDataOption.EventType.UPDATE, newVersionDO, nearVersionDO);
        }
        verDataDO.setName(verDataDO.getName().trim());
        verDataDO.setInvalidtime(BaseDataConsts.VERSION_MAX_DATE);
        verDataDO.setId(UUID.randomUUID());
        verDataDO.setModifytime(new Date());
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            verDataDO.setCreator(user.getUsername());
        }
        if (verDataDO.getActiveflag() == null) {
            verDataDO.setActiveflag(Integer.valueOf(1));
        }
        if ((flag = this.verDataDao.insert(verDataDO)) > 0) {
            this.syncCache(verDataDO);
            this.sendEvent(BaseDataOption.EventType.ADD, verDataDO, null);
        }
        return flag > 0 ? R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()) : R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @Override
    public R split(BaseDataVersionDO verDataDO) {
        int flag;
        if (verDataDO.getId() == null || !StringUtils.hasText(verDataDO.getTablename()) || !StringUtils.hasText(verDataDO.getName()) || verDataDO.getValidtime() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTablename(verDataDO.getTablename());
        param.setId(verDataDO.getId());
        BaseDataVersionDO old = this.verDataDao.get(param);
        if (old == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.split.not.exist", new Object[0]));
        }
        if (verDataDO.getValidtime().compareTo(old.getValidtime()) <= 0 || verDataDO.getValidtime().compareTo(old.getInvalidtime()) >= 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.split.effective.time.exceed", new Object[0]));
        }
        param.setId(null);
        param.setValidtime(verDataDO.getValidtime());
        if (this.verDataDao.get(param) != null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.split.effective.time.existed", new Object[0]));
        }
        Date invalidtime = old.getInvalidtime();
        BaseDataVersionDO newVersionDO = new BaseDataVersionDO();
        newVersionDO.setId(old.getId());
        newVersionDO.setModifytime(new Date());
        newVersionDO.setInvalidtime(verDataDO.getValidtime());
        this.verDataDao.updateByPrimaryKeySelective(newVersionDO);
        this.sendEvent(BaseDataOption.EventType.UPDATE, newVersionDO, old);
        verDataDO.setId(UUID.randomUUID());
        verDataDO.setInvalidtime(invalidtime);
        if (verDataDO.getActiveflag() == null) {
            verDataDO.setActiveflag(Integer.valueOf(1));
        }
        if ((flag = this.verDataDao.insertSelective(verDataDO)) > 0) {
            this.syncCache(verDataDO);
            this.sendEvent(BaseDataOption.EventType.ADD, verDataDO, null);
        }
        return flag > 0 ? R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()) : R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @Override
    public R update(BaseDataVersionDO verDataDO) {
        if (verDataDO.getId() == null || !StringUtils.hasText(verDataDO.getTablename()) || !StringUtils.hasText(verDataDO.getName())) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTablename(verDataDO.getTablename());
        param.setName(verDataDO.getName().trim());
        BaseDataVersionDO oldVerDO = (BaseDataVersionDO)this.verDataDao.selectOne(param);
        if (oldVerDO != null && !oldVerDO.getId().equals(verDataDO.getId())) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.name.existed", new Object[0]));
        }
        param.setName(null);
        param.setId(verDataDO.getId());
        oldVerDO = (BaseDataVersionDO)this.verDataDao.selectOne(param);
        if (oldVerDO == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.not.exist", new Object[0]));
        }
        if (oldVerDO.getValidtime().compareTo(BaseDataConsts.VERSION_MIN_DATE) == 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.modify.prohibit.default.version", new Object[0]));
        }
        if (verDataDO.getValidtime() != null && verDataDO.getValidtime().compareTo(oldVerDO.getValidtime()) != 0) {
            if (verDataDO.getValidtime().compareTo(oldVerDO.getInvalidtime()) >= 0) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.effective.date.check", new Object[0]));
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(verDataDO.getValidtime());
            if (ca.get(1) > 3000) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.effective.date", new Object[0]));
            }
            if (this.verDataDao.dataExist(oldVerDO) > 0) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.modfiy.effectiveDate.data.exist", new Object[0]));
            }
            param.setId(null);
            param.setInvalidtime(oldVerDO.getValidtime());
            BaseDataVersionDO lastVesionDO = this.verDataDao.get(param);
            if (lastVesionDO != null) {
                if (!verDataDO.getValidtime().after(lastVesionDO.getValidtime())) {
                    return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.effective.date.must.after", lastVesionDO.getName()));
                }
                BaseDataVersionDO newVersionDO = new BaseDataVersionDO();
                newVersionDO.setId(lastVesionDO.getId());
                newVersionDO.setModifytime(new Date());
                newVersionDO.setInvalidtime(verDataDO.getValidtime());
                this.verDataDao.updateByPrimaryKeySelective(newVersionDO);
                this.sendEvent(BaseDataOption.EventType.UPDATE, newVersionDO, lastVesionDO);
            } else {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.find.previous.version", new Object[0]));
            }
        }
        verDataDO.setInvalidtime(null);
        verDataDO.setModifytime(new Date());
        verDataDO.setName(verDataDO.getName().trim());
        verDataDO.setActiveflag(null);
        int flag = this.verDataDao.updateByPrimaryKeySelective(verDataDO);
        if (flag > 0) {
            this.syncCache(verDataDO);
            this.sendEvent(BaseDataOption.EventType.UPDATE, verDataDO, oldVerDO);
        }
        return flag > 0 ? R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg()) : R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @Override
    public R remove(BaseDataVersionDO verDataDO) {
        if (verDataDO.getId() == null) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setId(verDataDO.getId());
        BaseDataVersionDO oldData = (BaseDataVersionDO)this.verDataDao.selectOne(param);
        if (oldData == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.not.exist", new Object[0]));
        }
        if (oldData.getValidtime().compareTo(BaseDataConsts.VERSION_MIN_DATE) == 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.remove.prohibit.default.version", new Object[0]));
        }
        String tableName = oldData.getTablename();
        param.setId(null);
        param.setTablename(tableName);
        param.setInvalidtime(oldData.getValidtime());
        BaseDataVersionDO lastVesionDO = this.verDataDao.get(param);
        if (lastVesionDO == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.check.find.previous.version", new Object[0]));
        }
        if (verDataDO.getExtInfo("forceRemove") != null) {
            try {
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setTableName(tableName);
                baseDataDTO.setVersionDate(oldData.getValidtime());
                this.verDataDao.removeData(oldData);
                try {
                    this.baseDataDao.removeSub(baseDataDTO);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
                bdsc.setRemove(true);
                bdsc.setBaseDataDTO(baseDataDTO);
                this.getBaseDataCacheService().pushSyncMsg(bdsc);
                this.verDataDao.updateLastInvalidtime(oldData);
                this.verDataDao.updateNextValidtime(oldData);
                bdsc.setRemove(false);
                this.getBaseDataCacheService().pushSyncMsg(bdsc);
            }
            catch (Throwable e) {
                logger.error("\u6570\u636e\u53ef\u80fd\u5b58\u5728\u7248\u672c\u8303\u56f4\u91cd\u53e0", e);
                return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
            }
        }
        int checkValidtimeDate = this.verDataDao.validtimeExist(oldData);
        int checkInvalidtimeDate = this.verDataDao.invalidtimeExist(oldData);
        if (checkValidtimeDate > 0 || checkInvalidtimeDate > 0) {
            return R.error((int)203, (String)BaseDataCoreI18nUtil.getMessage("basedata.error.bdversion.remove.data.exist", "$date$", checkValidtimeDate, checkInvalidtimeDate));
        }
        BaseDataVersionDO newVersionDO = new BaseDataVersionDO();
        newVersionDO.setId(lastVesionDO.getId());
        newVersionDO.setModifytime(new Date());
        newVersionDO.setInvalidtime(oldData.getInvalidtime());
        this.verDataDao.updateByPrimaryKeySelective(newVersionDO);
        this.sendEvent(BaseDataOption.EventType.UPDATE, newVersionDO, lastVesionDO);
        param.setInvalidtime(null);
        param.setId(oldData.getId());
        int flag = this.verDataDao.delete(param);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u7248\u672c\u7ba1\u7406", (String)"\u5220\u9664", (String)tableName, (String)oldData.getName(), (String)(flag > 0 ? "\u5220\u9664\u6210\u529f" : "\u5220\u9664\u5931\u8d25"));
        if (flag > 0) {
            this.syncCache(oldData);
            this.sendEvent(BaseDataOption.EventType.DELETE, null, oldData);
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        return R.error((String)BaseDataCoreI18nUtil.getOptFailureMsg());
    }

    @Override
    public R changeStatus(List<BaseDataVersionDO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        String tenantName = ShiroUtil.getTenantName();
        HashSet<UUID> ids = new HashSet<UUID>();
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTenantName(tenantName);
        for (BaseDataVersionDO data : dataList) {
            if (data.getTablename() == null || data.getId() == null || data.getActiveflag() == null) {
                return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
            }
            param.setTablename(data.getTablename());
            param.setId(data.getId());
            param.setActiveflag(data.getActiveflag());
            if (this.verDataDao.updateByPrimaryKeySelective(param) <= 0 || data.getActiveflag() != 0) continue;
            ids.add(data.getId());
        }
        this.syncCache((BaseDataVersionDO)param);
        if (ids.isEmpty()) {
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        param.setId(null);
        param.setActiveflag(null);
        List<BaseDataVersionDO> versionList = this.verDataDao.list(param);
        for (BaseDataVersionDO verDO : versionList) {
            if (!ids.contains(verDO.getId())) continue;
            this.baseDataAsyncTask.execute(() -> {
                BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
                bdsc.setTenantName(tenantName);
                bdsc.setRemove(true);
                BaseDataDTO dataParam = new BaseDataDTO();
                dataParam.setTenantName(tenantName);
                dataParam.setTableName(param.getTablename());
                dataParam.setVersionDate(verDO.getValidtime());
                bdsc.setBaseDataDTO(dataParam);
                this.getBaseDataCacheService().pushSyncMsg(bdsc);
            });
        }
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initCache(BaseDataVersionDO verDataDO) {
        String tenantName = verDataDO.getTenantName();
        String tableName = verDataDO.getTablename();
        if (!dataMap.containsKey(tenantName)) {
            dataMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
        }
        if (!dataVerMap.containsKey(tenantName)) {
            dataVerMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
        }
        boolean forceUpdate = false;
        ConcurrentHashMap<String, List<BaseDataVersionDO>> cacheMap = dataMap.get(tenantName);
        if (!cacheMap.containsKey(tableName)) {
            forceUpdate = true;
        }
        ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
        BigDecimal startVer = tenantVer.get(tableName);
        if (!forceUpdate && startVer == null) {
            return;
        }
        String lockKey = tenantName + tableName;
        boolean locked = lockMap.putIfAbsent(lockKey, true) != null;
        try {
            if (locked) {
                while (lockMap.get(lockKey) != null) {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (InterruptedException e) {
                        logger.error("baseDataVersionCacheWaittingErro", e);
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
            BaseDataVersionDTO param = new BaseDataVersionDTO();
            param.setTenantName(tenantName);
            param.setTablename(tableName);
            List<BaseDataVersionDO> versionList = this.verDataDao.list(param);
            for (BaseDataVersionDO versionDO : versionList) {
                versionDO.setTenantName(tenantName);
                versionDO.setLocked(true);
            }
            if (startVer != null) {
                tenantVer.remove(tableName, startVer);
            }
            cacheMap.put(tableName, versionList);
        }
        catch (Throwable e) {
            logger.error(tableName + "\u57fa\u7840\u6570\u636e\u7248\u672c\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
        }
        finally {
            if (!locked) {
                lockMap.remove(lockKey);
            }
        }
    }

    private void sendEvent(BaseDataOption.EventType eventType, BaseDataVersionDO newData, BaseDataVersionDO oldData) {
        BaseDataVersionEvent event = new BaseDataVersionEvent((Object)this.getClass().getName());
        event.setEventType(eventType);
        event.setOldVerionDTO(oldData);
        event.setNewVersionDTO(newData);
        try {
            ApplicationContextRegister.publishEvent((ApplicationEvent)event);
        }
        catch (Throwable e) {
            logger.error("\u57fa\u7840\u6570\u636e\u7248\u672c\u53d8\u66f4\u540e\u7f6e\u4e8b\u4ef6\u5f02\u5e38", e);
        }
    }

    @Override
    public R syncCache(BaseDataVersionDO verDataDO) {
        if (!EnvConfig.getRedisEnable()) {
            return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
        }
        BaseDataVersionSyncCacheDTO bdvscd = new BaseDataVersionSyncCacheDTO();
        bdvscd.setTenantName(verDataDO.getTenantName());
        bdvscd.setBaseDataVersionDO(verDataDO);
        this.pushSyncMsg(bdvscd);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(BaseDataVersionSyncCacheDTO bdvscd) {
        String tenantName = bdvscd.getTenantName();
        BaseDataVersionDO versionDO = bdvscd.getBaseDataVersionDO();
        String tableName = versionDO.getTablename();
        try {
            dataMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
            if (!dataMap.get(tenantName).containsKey(tableName)) {
                return;
            }
            dataVerMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
            dataVerMap.get(tenantName).put(tableName, bdvscd.getVer());
        }
        catch (Throwable e) {
            logger.error(versionDO.getTablename() + "\u57fa\u7840\u6570\u636e\u7248\u672c\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(bdvscd.getCurrNodeId())) {
                checkSyncMap.put(bdvscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pushSyncMsg(BaseDataVersionSyncCacheDTO bdvscd) {
        BigDecimal ver = bdvscd.getVer();
        if (ver == null) {
            ver = OrderNumUtil.getOrderNumByCurrentTimeMillis();
            bdvscd.setVer(ver);
        }
        BaseDataVersionDO newData = bdvscd.getBaseDataVersionDO();
        BaseDataVersionDO msgData = new BaseDataVersionDO();
        msgData.setTenantName(bdvscd.getTenantName());
        msgData.setTablename(newData.getTablename());
        bdvscd.setBaseDataVersionDO(msgData);
        bdvscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        bdvscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(bdvscd);
                return;
            }
            if (!this.tryPushBroadcast(bdvscd)) {
                logger.error(msgData.getTablename() + "\u7248\u672c\u4fe1\u606f\u901a\u8fc7redis\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c\u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(bdvscd);
            }
        }
        catch (Throwable e) {
            logger.error(msgData.getTablename() + "\u7248\u672c\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(bdvscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(BaseDataVersionSyncCacheDTO bdvscd) {
        int cnt;
        if (bdvscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaBasedataCoreConfig.getBasedataVersionSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)bdvscd)));
        String checkKey = bdvscd.getCheckKey();
        for (cnt = 0; !checkSyncMap.containsKey(checkKey) && cnt < 300; ++cnt) {
            try {
                Thread.sleep(cnt < 10 ? 5L : (long)(cnt < 100 ? 50 : 500));
                continue;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (cnt == 300) {
            bdvscd.setRetry(bdvscd.getRetry() + 1);
            return this.tryPushBroadcast(bdvscd);
        }
        return true;
    }
}

