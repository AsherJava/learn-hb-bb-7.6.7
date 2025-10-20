/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.DateTimeUtil
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.DateTimeUtil;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.OrgAsyncTask;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.config.VaOrganizationCoreConfig;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.dao.VaOrgVersionDao;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.OrgVersionSyncCacheDTO;
import com.jiuqi.va.organization.service.OrgVersionService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaOrgVersionServiceImpl")
public class OrgVersionServiceImpl
implements OrgVersionService {
    private static Logger logger = LoggerFactory.getLogger(OrgVersionServiceImpl.class);
    @Autowired
    private VaOrgVersionDao orgVersionDao;
    @Autowired
    private VaOrgDataDao orgDataDao;
    @Autowired
    private OrgAsyncTask orgAsyncTask;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, List<OrgVersionDO>>> dataMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();
    private OrgDataCacheService orgDataCacheService = null;

    private OrgDataCacheService getOrgDataCacheService() {
        if (this.orgDataCacheService == null) {
            this.orgDataCacheService = (OrgDataCacheService)ApplicationContextRegister.getBean(OrgDataCacheService.class);
        }
        return this.orgDataCacheService;
    }

    @Override
    public OrgVersionDO get(OrgVersionDTO param) {
        this.initCache((OrgVersionDO)param);
        String categoryname = param.getCategoryname();
        UUID id = param.getId();
        String title = param.getTitle();
        Date validtime = param.getValidtime();
        Date invalidtime = param.getInvalidtime();
        Date versionDate = param.getVersionDate();
        List<OrgVersionDO> orgVersionList = dataMap.get(param.getTenantName()).get(categoryname);
        for (OrgVersionDO data : orgVersionList) {
            if (id != null && !data.getId().equals(id) || title != null && !data.getTitle().equals(title) || validtime != null && !validtime.equals(data.getValidtime()) || invalidtime != null && !invalidtime.equals(data.getInvalidtime()) || versionDate != null && (versionDate.before(data.getValidtime()) || !versionDate.before(data.getInvalidtime()))) continue;
            return data;
        }
        return null;
    }

    @Override
    public List<OrgVersionDO> listCache(OrgVersionDTO param) {
        this.initCache((OrgVersionDO)param);
        return dataMap.get(param.getTenantName()).get(param.getCategoryname());
    }

    @Override
    public PageVO<OrgVersionDO> list(OrgVersionDTO param) {
        PageVO page = new PageVO();
        page.setRs(R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])));
        List<OrgVersionDO> orgVersionList = this.orgVersionDao.list(param);
        page.setRows(orgVersionList);
        page.setTotal(orgVersionList.size());
        return page;
    }

    @Override
    public R add(OrgVersionDO orgVerDO) {
        String categoryname = orgVerDO.getCategoryname();
        if (!StringUtils.hasText(categoryname) || !StringUtils.hasText(orgVerDO.getTitle()) || orgVerDO.getValidtime() == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(orgVerDO.getValidtime());
        if (ca.get(1) > 2500) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.effectiveDate.bigger", new Object[0]));
        }
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        param.setTitle(orgVerDO.getTitle().trim());
        OrgVersionDO oldOrgVersionDO = this.orgVersionDao.find(param);
        if (oldOrgVersionDO != null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.title.existed", new Object[0]));
        }
        param.setTitle(null);
        List<OrgVersionDO> verList = this.orgVersionDao.list(param);
        if (verList != null && !verList.isEmpty()) {
            OrgVersionDO nearOrgVersionDO = verList.get(0);
            if (!orgVerDO.getValidtime().after(nearOrgVersionDO.getValidtime())) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.effectiveDate.not.after", nearOrgVersionDO.getTitle()));
            }
            nearOrgVersionDO.setInvalidtime(orgVerDO.getValidtime());
            this.orgVersionDao.updateByPrimaryKeySelective(nearOrgVersionDO);
        }
        orgVerDO.setTitle(orgVerDO.getTitle().trim());
        orgVerDO.setInvalidtime(new Date(DateTimeUtil.getMaxTime()));
        orgVerDO.setId(UUID.randomUUID());
        if (orgVerDO.getActiveflag() == null) {
            orgVerDO.setActiveflag(Integer.valueOf(1));
        }
        int flag = this.orgVersionDao.insert(orgVerDO);
        LogUtil.add((String)"\u673a\u6784\u7248\u672c\u7ba1\u7406", (String)"\u65b0\u589e", (String)categoryname, (String)orgVerDO.getTitle(), (String)(flag > 0 ? "\u65b0\u589e\u6210\u529f" : "\u65b0\u589e\u5931\u8d25"));
        if (flag > 0) {
            this.syncCache(orgVerDO);
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R split(OrgVersionDO orgVerDO) {
        String categoryname = orgVerDO.getCategoryname();
        UUID id = orgVerDO.getId();
        if (id == null || !StringUtils.hasText(categoryname) || !StringUtils.hasText(orgVerDO.getTitle()) || orgVerDO.getValidtime() == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        param.setId(id);
        OrgVersionDO old = this.orgVersionDao.find(param);
        if (old == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.spilt.not.exist", new Object[0]));
        }
        if (orgVerDO.getValidtime().compareTo(old.getValidtime()) <= 0 || orgVerDO.getValidtime().compareTo(old.getInvalidtime()) >= 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.spilt.effectiveDate.exceed", new Object[0]));
        }
        param.setId(null);
        param.setValidtime(orgVerDO.getValidtime());
        if (this.orgVersionDao.find(param) != null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.spilt.effectiveDate.existed", new Object[0]));
        }
        Date invalidtime = old.getInvalidtime();
        old.setInvalidtime(orgVerDO.getValidtime());
        this.orgVersionDao.updateByPrimaryKeySelective(old);
        orgVerDO.setId(UUID.randomUUID());
        orgVerDO.setInvalidtime(invalidtime);
        if (orgVerDO.getActiveflag() == null) {
            orgVerDO.setActiveflag(Integer.valueOf(1));
        }
        int flag = this.orgVersionDao.insertSelective(orgVerDO);
        LogUtil.add((String)"\u673a\u6784\u7248\u672c\u7ba1\u7406", (String)"\u62c6\u5206", (String)categoryname, (String)orgVerDO.getTitle(), (String)(flag > 0 ? "\u65b0\u589e\u6210\u529f" : "\u65b0\u589e\u5931\u8d25"));
        if (flag > 0) {
            this.syncCache(orgVerDO);
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R update(OrgVersionDO orgVerDO) {
        String categoryname = orgVerDO.getCategoryname();
        UUID id = orgVerDO.getId();
        if (id == null || !StringUtils.hasText(categoryname) || !StringUtils.hasText(orgVerDO.getTitle())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        param.setTitle(orgVerDO.getTitle().trim());
        OrgVersionDO oldOrgVersionDO = this.orgVersionDao.find(param);
        if (oldOrgVersionDO != null && !oldOrgVersionDO.getId().equals(orgVerDO.getId())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.title.existed", new Object[0]));
        }
        param.setTitle(null);
        param.setId(id);
        oldOrgVersionDO = this.orgVersionDao.find(param);
        if (oldOrgVersionDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.query.not.exist", new Object[0]));
        }
        if (oldOrgVersionDO.getValidtime().compareTo(new Date(DateTimeUtil.getMinTime())) == 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.modify.not.default", new Object[0]));
        }
        if (orgVerDO.getValidtime() != null && orgVerDO.getValidtime().compareTo(oldOrgVersionDO.getValidtime()) != 0) {
            if (orgVerDO.getValidtime().compareTo(oldOrgVersionDO.getInvalidtime()) >= 0) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.modify.effectiveDate.smaller", new Object[0]));
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(orgVerDO.getValidtime());
            if (ca.get(1) > 2500) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.effectiveDate.bigger", new Object[0]));
            }
            if (this.orgVersionDao.dataExist(oldOrgVersionDO) > 0) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.modify.data.existed", new Object[0]));
            }
            param.setId(null);
            param.setInvalidtime(oldOrgVersionDO.getValidtime());
            OrgVersionDO lastVesionDO = this.orgVersionDao.find(param);
            if (lastVesionDO != null) {
                if (!orgVerDO.getValidtime().after(lastVesionDO.getValidtime())) {
                    return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.effectiveDate.not.after", lastVesionDO.getTitle()));
                }
                lastVesionDO.setInvalidtime(orgVerDO.getValidtime());
                this.orgVersionDao.updateByPrimaryKeySelective(lastVesionDO);
            } else {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.last.not.existed", new Object[0]));
            }
        }
        orgVerDO.setInvalidtime(null);
        orgVerDO.setTitle(orgVerDO.getTitle().trim());
        orgVerDO.setActiveflag(null);
        int flag = this.orgVersionDao.updateByPrimaryKeySelective(orgVerDO);
        LogUtil.add((String)"\u673a\u6784\u7248\u672c\u7ba1\u7406", (String)"\u66f4\u65b0", (String)categoryname, (String)orgVerDO.getTitle(), (String)(flag > 0 ? "\u66f4\u65b0\u6210\u529f" : "\u66f4\u65b0\u5931\u8d25"));
        if (flag > 0) {
            this.syncCache(orgVerDO);
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R remove(OrgVersionDO orgVerDO) {
        String categoryname = orgVerDO.getCategoryname();
        UUID id = orgVerDO.getId();
        if (id == null || !StringUtils.hasText(categoryname)) {
            R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(categoryname);
        param.setId(id);
        OrgVersionDO oldOrgVersionDO = this.orgVersionDao.find(param);
        if (oldOrgVersionDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.query.not.exist", new Object[0]));
        }
        if (oldOrgVersionDO.getValidtime().compareTo(new Date(DateTimeUtil.getMinTime())) == 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.delete.not.default", new Object[0]));
        }
        param.setId(null);
        param.setInvalidtime(oldOrgVersionDO.getValidtime());
        OrgVersionDO lastVesionDO = this.orgVersionDao.find(param);
        if (lastVesionDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgversion.check.last.not.existed", new Object[0]));
        }
        if (orgVerDO.getExtInfo("forceRemove") != null) {
            try {
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCategoryname(categoryname);
                orgDTO.setVersionDate(oldOrgVersionDO.getValidtime());
                this.orgVersionDao.removeData(oldOrgVersionDO);
                try {
                    this.orgDataDao.removeSub(orgDTO);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
                bdsc.setRemove(true);
                bdsc.setOrgDTO(orgDTO);
                this.getOrgDataCacheService().pushSyncMsg(bdsc);
                this.orgVersionDao.updateLastInvalidtime(oldOrgVersionDO);
                this.orgVersionDao.updateNextValidtime(oldOrgVersionDO);
                bdsc.setRemove(false);
                this.getOrgDataCacheService().pushSyncMsg(bdsc);
            }
            catch (Throwable e) {
                logger.error("\u6570\u636e\u53ef\u80fd\u5b58\u5728\u7248\u672c\u8303\u56f4\u91cd\u53e0", e);
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
            }
        }
        int checkValidtimeDate = this.orgVersionDao.validtimeExist(oldOrgVersionDO);
        int checkInvalidtimeDate = this.orgVersionDao.invalidtimeExist(oldOrgVersionDO);
        if (checkValidtimeDate > 0 || checkInvalidtimeDate > 0) {
            return R.error((int)203, (String)OrgCoreI18nUtil.getMessage("org.error.orgversion.delete.data.existed", "$date$", checkValidtimeDate, checkInvalidtimeDate));
        }
        lastVesionDO.setInvalidtime(oldOrgVersionDO.getInvalidtime());
        this.orgVersionDao.updateByPrimaryKeySelective(lastVesionDO);
        param.setInvalidtime(null);
        param.setId(id);
        int flag = this.orgVersionDao.delete(param);
        LogUtil.add((String)"\u673a\u6784\u7248\u672c\u7ba1\u7406", (String)"\u5220\u9664", (String)categoryname, (String)oldOrgVersionDO.getTitle(), (String)(flag > 0 ? "\u5220\u9664\u6210\u529f" : "\u5220\u9664\u5931\u8d25"));
        if (flag > 0) {
            this.syncCache(oldOrgVersionDO);
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    @Override
    public R changeStatus(List<OrgVersionDO> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        String tenantName = ShiroUtil.getTenantName();
        HashSet<UUID> ids = new HashSet<UUID>();
        OrgVersionDTO param = new OrgVersionDTO();
        param.setTenantName(tenantName);
        for (OrgVersionDO data : dataList) {
            if (data.getCategoryname() == null || data.getId() == null || data.getActiveflag() == null) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
            }
            param.setCategoryname(data.getCategoryname());
            param.setId(data.getId());
            param.setActiveflag(data.getActiveflag());
            if (this.orgVersionDao.updateByPrimaryKeySelective(param) <= 0 || data.getActiveflag() != 0) continue;
            ids.add(data.getId());
        }
        this.syncCache((OrgVersionDO)param);
        if (ids.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        param.setId(null);
        param.setActiveflag(null);
        List<OrgVersionDO> orgVersionList = this.orgVersionDao.list(param);
        for (OrgVersionDO orgVerDO : orgVersionList) {
            if (!ids.contains(orgVerDO.getId())) continue;
            this.orgAsyncTask.execute(() -> {
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setTenantName(tenantName);
                orgDTO.setCategoryname(param.getCategoryname());
                orgDTO.setVersionDate(orgVerDO.getValidtime());
                OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
                bdsc.setTenantName(tenantName);
                bdsc.setRemove(true);
                bdsc.setOrgDTO(orgDTO);
                this.getOrgDataCacheService().pushSyncMsg(bdsc);
            });
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initCache(OrgVersionDO orgVerDO) {
        boolean locked;
        String tenantName = orgVerDO.getTenantName();
        String categoryname = orgVerDO.getCategoryname();
        if (!dataMap.containsKey(tenantName)) {
            dataMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
        }
        if (!dataVerMap.containsKey(tenantName)) {
            dataVerMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
        }
        boolean forceUpdate = false;
        ConcurrentHashMap<String, List<OrgVersionDO>> cacheMap = dataMap.get(tenantName);
        if (!cacheMap.containsKey(categoryname)) {
            forceUpdate = true;
        }
        ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
        BigDecimal startVer = tenantVer.get(categoryname);
        if (!forceUpdate && startVer == null) {
            return;
        }
        String lockKey = tenantName + categoryname;
        boolean bl = locked = lockMap.putIfAbsent(lockKey, true) != null;
        if (locked) {
            while (lockMap.get(lockKey) != null) {
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException e) {
                    logger.error("orgDataVersionCacheWaittingErro", e);
                    Thread.currentThread().interrupt();
                }
            }
            return;
        }
        try {
            OrgVersionDTO param = new OrgVersionDTO();
            param.setTenantName(tenantName);
            param.setCategoryname(categoryname);
            List<OrgVersionDO> orgVersionList = this.orgVersionDao.list(param);
            for (OrgVersionDO orgVersionDO : orgVersionList) {
                orgVersionDO.setTenantName(tenantName);
                orgVersionDO.setLocked(true);
            }
            if (startVer != null) {
                tenantVer.remove(categoryname, startVer);
            }
            cacheMap.put(categoryname, orgVersionList);
        }
        catch (Throwable e) {
            logger.error(categoryname + "\u673a\u6784\u7248\u672c\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
        }
        finally {
            if (!locked) {
                lockMap.remove(lockKey);
            }
        }
    }

    @Override
    public R syncCache(OrgVersionDO orgVerDO) {
        OrgVersionSyncCacheDTO ovscd = new OrgVersionSyncCacheDTO();
        ovscd.setTenantName(orgVerDO.getTenantName());
        ovscd.setOrgVersionDO(orgVerDO);
        this.pushSyncMsg(ovscd);
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(OrgVersionSyncCacheDTO ovscd) {
        String tenantName = ovscd.getTenantName();
        OrgVersionDO versionDO = ovscd.getOrgVersionDO();
        String categoryName = versionDO.getCategoryname();
        try {
            dataMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
            if (!dataMap.get(tenantName).containsKey(categoryName)) {
                return;
            }
            dataVerMap.computeIfAbsent(tenantName, key -> new ConcurrentHashMap());
            dataVerMap.get(tenantName).put(categoryName, ovscd.getVer());
        }
        catch (Throwable e) {
            logger.error(versionDO.getCategoryname() + "\u673a\u6784\u7248\u672c\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(ovscd.getCurrNodeId())) {
                checkSyncMap.put(ovscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pushSyncMsg(OrgVersionSyncCacheDTO ovscd) {
        BigDecimal ver = ovscd.getVer();
        if (ver == null) {
            ver = OrderNumUtil.getOrderNumByCurrentTimeMillis();
            ovscd.setVer(ver);
        }
        OrgVersionDO newData = ovscd.getOrgVersionDO();
        OrgVersionDO msgData = new OrgVersionDO();
        msgData.setTenantName(ovscd.getTenantName());
        msgData.setCategoryname(newData.getCategoryname());
        ovscd.setOrgVersionDO(msgData);
        ovscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        ovscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(ovscd);
                return;
            }
            if (!this.tryPushBroadcast(ovscd)) {
                logger.error(msgData.getCategoryname() + "\u7248\u672c\u4fe1\u606f\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c \u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(ovscd);
            }
        }
        catch (Throwable e) {
            logger.error(msgData.getCategoryname() + "\u7248\u672c\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(ovscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(OrgVersionSyncCacheDTO ovscd) {
        int cnt;
        if (ovscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaOrganizationCoreConfig.getOrgVersionSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)ovscd)));
        String checkKey = ovscd.getCheckKey();
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
            ovscd.setRetry(ovscd.getRetry() + 1);
            return this.tryPushBroadcast(ovscd);
        }
        return true;
    }
}

