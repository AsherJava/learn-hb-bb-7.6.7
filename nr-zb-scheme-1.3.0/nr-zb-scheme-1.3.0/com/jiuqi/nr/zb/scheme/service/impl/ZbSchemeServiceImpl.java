/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.zb.scheme.common.Consts;
import com.jiuqi.nr.zb.scheme.common.Move;
import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.MetaGroup;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.PropLink;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.dto.SyncNode;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeCheckException;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.dao.IPropInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IPropLinkDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeVersionDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeVersionDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeVersionDO;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeSyncProvider;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeValidator;
import com.jiuqi.nr.zb.scheme.utils.FieldNameUtils;
import com.jiuqi.nr.zb.scheme.utils.VersionUtils;
import com.jiuqi.nr.zb.scheme.utils.ZbSchemeConvert;
import com.jiuqi.nr.zb.scheme.web.vo.MoveParam;
import com.jiuqi.nr.zb.scheme.web.vo.PageVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbInfoVO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class ZbSchemeServiceImpl
implements IZbSchemeService {
    @Autowired
    private IZbSchemeDao zbSchemeDao;
    @Autowired
    private IZbSchemeGroupDao zbSchemeGroupDao;
    @Autowired
    private IZbSchemeVersionDao zbSchemeVersionDao;
    @Autowired
    private IPropInfoDao propInfoDao;
    @Autowired
    private IZbInfoDao zbInfoDao;
    @Autowired
    private IZbGroupDao zbGroupDao;
    @Autowired
    private IPropLinkDao propLinkDao;
    @Autowired
    private IZbSchemeValidator validator;
    @Autowired
    private IZbSchemeSyncProvider syncProvider;
    private static final Logger logger = LoggerFactory.getLogger(ZbSchemeServiceImpl.class);

    @Override
    public boolean enableAnalyseZb() {
        return false;
    }

    @Override
    public boolean isExistData(NodeType type, String key, String version) {
        if (NodeType.ZB_SCHEME_GROUP.equals((Object)type)) {
            return this.listZbSchemeByParent(key).isEmpty();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public synchronized void insertPropInfo(PropInfo prop) {
        this.validator.checkPropInfo(prop);
        try {
            this.propInfoDao.insert(ZbSchemeConvert.cdo(prop));
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public synchronized void insertPropInfo(List<PropInfo> props) {
        if (CollectionUtils.isEmpty(props)) {
            return;
        }
        List<PropInfo> propInfos = this.listAllPropInfo();
        Set<String> codes = propInfos.stream().map(PropInfo::getCode).collect(Collectors.toSet());
        FieldNameUtils fieldNameUtils = new FieldNameUtils(codes);
        ArrayList<PropInfoDO> list = new ArrayList<PropInfoDO>(props.size());
        for (PropInfo prop : props) {
            this.validator.checkPropInfo(prop);
            prop.setFieldName(fieldNameUtils.next());
            list.add(ZbSchemeConvert.cdo(prop));
        }
        try {
            this.propInfoDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void updatePropInfo(PropInfo prop) {
        this.validator.checkPropInfo(prop);
        try {
            this.propInfoDao.update(ZbSchemeConvert.cdo(prop));
        }
        catch (Exception e) {
            throw new ZbSchemeException("\u66f4\u65b0\u6269\u5c55\u5c5e\u6027\u5931\u8d25", e);
        }
    }

    @Override
    public void updatePropInfo(List<PropInfo> props) {
        if (CollectionUtils.isEmpty(props)) {
            return;
        }
        ArrayList<PropInfoDO> list = new ArrayList<PropInfoDO>(props.size());
        for (PropInfo prop : props) {
            this.validator.checkPropInfo(prop);
            list.add(ZbSchemeConvert.cdo(prop));
        }
        try {
            this.propInfoDao.update(list);
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void deletePropInfo(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u5c5e\u6027key\u4e0d\u80fd\u4e3a\u7a7a");
        this.deletePropInfo(Collections.singletonList(key));
    }

    @Override
    public void deletePropInfo(List<String> keys) {
        Assert.notEmpty(keys, "\u6307\u6807\u5c5e\u6027key\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<PropInfoDO> delete = new ArrayList<PropInfoDO>(keys.size());
        for (String key : keys) {
            PropInfoDO propInfoDO = this.propInfoDao.getByKey(key);
            int count = this.zbInfoDao.getNonEmptyFieldCount(propInfoDO.getFieldName());
            if (count > 0) {
                throw new ZbSchemeException("\u5df2\u6709\u6570\u636e\uff0c\u7981\u6b62\u5220\u9664\uff01");
            }
            delete.add(propInfoDO);
        }
        try {
            this.propInfoDao.delete(delete);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u5c5e\u6027\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public PropInfo getPropInfo(String key) {
        return ZbSchemeConvert.cto(this.propInfoDao.getByKey(key));
    }

    @Override
    public List<PropInfo> listAllPropInfo() {
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listAll();
        ArrayList<PropInfo> list = new ArrayList<PropInfo>(propInfoDOS.size());
        for (PropInfoDO propInfoDO : propInfoDOS) {
            list.add(ZbSchemeConvert.cto(propInfoDO));
        }
        return list;
    }

    @Override
    public List<PropInfo> listPropInfoLinkedByScheme(String schemeKey) {
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(schemeKey);
        ArrayList<PropInfo> list = new ArrayList<PropInfo>(propInfoDOS.size());
        for (PropInfoDO propInfoDO : propInfoDOS) {
            list.add(ZbSchemeConvert.cto(propInfoDO));
        }
        return list;
    }

    @Override
    public PropInfo getLinkedPropInfo(String prop) {
        PropInfoDO infoDO = this.propInfoDao.getByKeyFromPropLink(prop);
        return ZbSchemeConvert.cto(infoDO);
    }

    @Override
    public String insertZbSchemeGroup(ZbSchemeGroup zbGroupDTO) {
        this.validator.checkZbSchemeGroup(zbGroupDTO);
        try {
            this.zbSchemeGroupDao.insert(ZbSchemeConvert.cdo(zbGroupDTO));
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
        return zbGroupDTO.getKey();
    }

    @Override
    public void insertZbSchemeGroup(List<ZbSchemeGroup> groups) {
        if (CollectionUtils.isEmpty(groups)) {
            return;
        }
        ArrayList<ZbSchemeGroupDO> list = new ArrayList<ZbSchemeGroupDO>(groups.size());
        for (ZbSchemeGroup group : groups) {
            this.validator.checkZbSchemeGroup(group);
            list.add(ZbSchemeConvert.cdo(group));
        }
        try {
            this.zbSchemeGroupDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void updateZbSchemeGroup(ZbSchemeGroup group) {
        this.updateZbSchemeGroup(Collections.singletonList(group));
    }

    @Override
    public void updateZbSchemeGroup(List<ZbSchemeGroup> groups) {
        if (CollectionUtils.isEmpty(groups)) {
            return;
        }
        ArrayList<ZbSchemeGroupDO> list = new ArrayList<ZbSchemeGroupDO>(groups.size());
        for (ZbSchemeGroup groupDTO : groups) {
            this.validator.checkZbSchemeGroup(groupDTO);
            list.add(ZbSchemeConvert.cdo(groupDTO));
        }
        try {
            this.zbSchemeGroupDao.update(list);
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void deleteZbSchemeGroup(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u4f53\u7cfb\u5206\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<String> delete = new ArrayList<String>();
        this.findDeleteKeys(key, delete);
        try {
            this.zbSchemeGroupDao.deleteByKeys(delete);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    private void findDeleteKeys(String key, List<String> delete) {
        List<ZbSchemeDO> schemeDOS = this.zbSchemeDao.listByParent(key);
        if (!schemeDOS.isEmpty()) {
            throw new ZbSchemeCheckException("\u8be5\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u4e0b\u5b58\u5728\u6307\u6807\u4f53\u7cfb\uff0c\u7981\u6b62\u5220\u9664");
        }
        delete.add(key);
        List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listByParent(key);
        if (!groupDOS.isEmpty()) {
            for (ZbSchemeGroupDO groupDO : groupDOS) {
                this.findDeleteKeys(groupDO.getKey(), delete);
            }
        }
    }

    @Override
    public ZbSchemeGroup getZbSchemeGroup(String key) {
        ZbSchemeGroupDO groupDO = this.zbSchemeGroupDao.getByKey(key);
        return ZbSchemeConvert.cto(groupDO);
    }

    @Override
    public List<ZbSchemeGroup> listZbSchemeGroupByParent(String parent) {
        List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listByParent(parent);
        ArrayList<ZbSchemeGroup> list = new ArrayList<ZbSchemeGroup>(groupDOS.size());
        for (ZbSchemeGroupDO groupDO : groupDOS) {
            list.add(ZbSchemeConvert.cto(groupDO));
        }
        return list;
    }

    @Override
    public void insertZbScheme(ZbSchemeDTO zbScheme) {
        this.insertZbScheme(zbScheme, false);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String insertZbSchemeAndVersion(ZbSchemeDTO zbScheme) {
        return this.insertZbScheme(zbScheme, true);
    }

    private String insertZbScheme(ZbSchemeDTO zbScheme, boolean withVersion) {
        this.validator.checkZbScheme(zbScheme);
        try {
            this.zbSchemeDao.insert(Collections.singletonList(ZbSchemeConvert.cdo(zbScheme)));
            if (withVersion) {
                ZbSchemeVersionDO versionDO = this.getDefaultVersion();
                versionDO.setSchemeKey(zbScheme.getKey());
                this.zbSchemeVersionDao.insert(Collections.singletonList(versionDO));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return zbScheme.getKey();
    }

    private ZbSchemeVersionDO getDefaultVersion() {
        ZbSchemeVersionDO version = new ZbSchemeVersionDO();
        version.setKey(UUID.randomUUID().toString());
        version.setStartPeriod("1970N0001");
        version.setEndPeriod("9999N0001");
        version.setUpdateTime(Instant.now());
        version.setCode(VersionUtils.getVersionCodeByPeriod("1970N0001"));
        version.setStatus(VersionStatus.UNPUBLISHED);
        version.setOrder(OrderGenerator.newOrder());
        version.setTitle("\u9ed8\u8ba4\u7248\u672c");
        return version;
    }

    @Override
    public void insertZbScheme(List<ZbSchemeDTO> schemes) {
        if (CollectionUtils.isEmpty(schemes)) {
            return;
        }
        ArrayList<ZbSchemeDO> list = new ArrayList<ZbSchemeDO>(schemes.size());
        for (ZbSchemeDTO scheme : schemes) {
            this.validator.checkZbScheme(scheme);
            list.add(ZbSchemeConvert.cdo(scheme));
        }
        try {
            this.zbSchemeDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void updateZbScheme(ZbSchemeDTO zbSchemeDTO) {
        this.validator.checkZbScheme(zbSchemeDTO);
        ZbSchemeDO cdo = ZbSchemeConvert.cdo(zbSchemeDTO);
        try {
            this.zbSchemeDao.update(Collections.singletonList(cdo));
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u4f53\u7cfb\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public ZbScheme getZbScheme(String zbSchemeKey) {
        ZbSchemeDO schemeDO = this.zbSchemeDao.getByKey(zbSchemeKey);
        return ZbSchemeConvert.cto(schemeDO);
    }

    @Override
    public List<ZbScheme> listZbSchemeByParent(String parent) {
        List<ZbSchemeDO> zbSchemeDOS = this.zbSchemeDao.listByParent(parent);
        ArrayList<ZbScheme> list = new ArrayList<ZbScheme>(zbSchemeDOS.size());
        for (ZbSchemeDO zbSchemeDO : zbSchemeDOS) {
            list.add(ZbSchemeConvert.cto(zbSchemeDO));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void insertZbSchemeVersion(ZbSchemeVersionDTO version) {
        this.validator.checkZbSchemeVersion(version);
        List<ZbSchemeVersionDO> versionDOS = this.zbSchemeVersionDao.listByScheme(version.getSchemeKey());
        boolean match = versionDOS.stream().anyMatch(t -> t.getStartPeriod().compareToIgnoreCase(version.getStartPeriod()) >= 0);
        if (match) {
            throw new ZbSchemeException("\u65b0\u5efa\u7248\u672c\u65f6\u95f4\u8981\u5728\u5df2\u53d1\u5e03\u6700\u65b0\u7248\u672c\u65f6\u95f4\u4e4b\u540e");
        }
        ZbSchemeVersionDO last = versionDOS.get(versionDOS.size() - 1);
        if (!VersionStatus.PUBLISHED.equals((Object)last.getStatus())) {
            throw new ZbSchemeException("\u5f53\u524d\u6700\u65b0\u7248\u672c\u672a\u53d1\u5e03\uff0c\u7981\u6b62\u65b0\u589e\u7248\u672c");
        }
        last.setEndPeriod(version.getStartPeriod());
        version.setEndPeriod("9999N0001");
        version.setCode(Consts.ZB_SCHEME_VERSION_CODE_FORMAT.apply(version.getStartPeriod()));
        try {
            this.zbSchemeVersionDao.insert(Collections.singletonList(ZbSchemeConvert.cdo(version)));
            this.zbSchemeVersionDao.update(Collections.singletonList(last));
            if (version.isCopyLast()) {
                HashMap<String, String> keyMap = new HashMap<String, String>();
                this.copyZbGroup(version.getSchemeKey(), last.getKey(), version.getKey(), keyMap);
                this.copyZbInfo(version.getSchemeKey(), last.getKey(), version.getKey(), keyMap);
            }
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4f53\u7cfb\u7248\u672c\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    private void copyZbInfo(String schemeKey, String startKey, String endKey, Map<String, String> keyMap) throws Exception {
        List<ZbInfo> zbInfos = this.listZbInfoBySchemeAndVersion(schemeKey, startKey);
        ArrayList<ZbInfoDO> inserts = new ArrayList<ZbInfoDO>(zbInfos.size());
        for (ZbInfo info : zbInfos) {
            info.setKey(UUID.randomUUID().toString());
            info.setVersionKey(endKey);
            if (keyMap.containsKey(info.getParentKey())) {
                info.setParentKey(keyMap.get(info.getParentKey()));
            }
            inserts.add(ZbSchemeConvert.cdo(info));
        }
        this.zbInfoDao.insert(inserts);
    }

    private void copyZbGroup(String schemeKey, String last, String start, Map<String, String> keyMap) throws Exception {
        List<ZbGroupDO> zbGroups = this.zbGroupDao.listBySchemeAndVersion(schemeKey, last);
        for (ZbGroupDO zbGroup : zbGroups) {
            String key = UUID.randomUUID().toString();
            keyMap.put(zbGroup.getKey(), key);
            zbGroup.setKey(key);
            zbGroup.setVersionKey(start);
        }
        for (ZbGroupDO zbGroup : zbGroups) {
            if (!keyMap.containsKey(zbGroup.getParentKey())) continue;
            zbGroup.setParentKey(keyMap.get(zbGroup.getParentKey()));
        }
        this.zbGroupDao.insert(zbGroups);
    }

    @Override
    public void updateZbSchemeVersion(ZbSchemeVersion version) {
        Assert.notNull((Object)version, "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        this.validator.checkZbSchemeVersion(version);
        try {
            this.zbSchemeVersionDao.update(Collections.singletonList(ZbSchemeConvert.cdo(version)));
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u4f53\u7cfb\u7248\u672c\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void deleteZbSchemeVersion(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u4f53\u7cfb\u7248\u672ckey\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getByKey(key);
        if (versionDO == null) {
            return;
        }
        if ("1970N0001".equals(versionDO.getStartPeriod())) {
            throw new ZbSchemeException("\u9ed8\u8ba4\u7248\u672c\u7981\u6b62\u5220\u9664");
        }
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u7248\u672c\u5904\u4e8e\u53d1\u5e03\u72b6\u6001\uff0c\u7981\u6b62\u5220\u9664");
        }
        if (this.hasReferVersion(key)) {
            throw new ZbSchemeException("\u7248\u672c\u88ab\u5f15\u7528\uff0c\u65e0\u6cd5\u5220\u9664");
        }
        try {
            this.zbSchemeVersionDao.deleteByKey(key);
            this.zbGroupDao.deleteByVersion(versionDO.getKey());
            this.zbInfoDao.deleteByVersion(versionDO.getKey());
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u4f53\u7cfb\u7248\u672c\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public ZbSchemeVersion getZbSchemeVersion(String key) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getByKey(key);
        return ZbSchemeConvert.cto(versionDO);
    }

    @Override
    public List<ZbSchemeVersion> listZbSchemeVersionByScheme(String zbSchemeKey) {
        List<ZbSchemeVersionDO> zbSchemeVersionDOS = this.zbSchemeVersionDao.listByScheme(zbSchemeKey);
        ArrayList<ZbSchemeVersion> list = new ArrayList<ZbSchemeVersion>(zbSchemeVersionDOS.size());
        for (ZbSchemeVersionDO versionDO : zbSchemeVersionDOS) {
            list.add(ZbSchemeConvert.cto(versionDO));
        }
        list.sort(Comparator.reverseOrder());
        return list;
    }

    @Override
    public ZbSchemeVersion getZbSchemeVersion(String zbSchemeKey, String period) {
        ZbSchemeVersionDO schemeAndPeriod = this.zbSchemeVersionDao.getBySchemeAndPeriod(zbSchemeKey, period);
        return ZbSchemeConvert.cto(schemeAndPeriod);
    }

    @Override
    public ZbSchemeVersion getZbSchemeVersionByKey(String versionKey) {
        ZbSchemeVersionDO schemeAndPeriod = this.zbSchemeVersionDao.getByKey(versionKey);
        return ZbSchemeConvert.cto(schemeAndPeriod);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteZbScheme(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u4f53\u7cfbkey\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<SyncNode> nodes = new ArrayList<SyncNode>();
        nodes.addAll(this.syncProvider.listSyncNode(key));
        if (!nodes.isEmpty()) {
            throw new ZbSchemeCheckException("\u6307\u6807\u4f53\u7cfb\u88ab\u5f15\u7528\uff0c\u65e0\u6cd5\u5220\u9664\uff01");
        }
        try {
            this.zbSchemeDao.deleteByKey(key);
            this.zbSchemeVersionDao.deleteByScheme(key);
            this.zbInfoDao.deleteByScheme(key);
            this.zbGroupDao.deleteByScheme(key);
            this.propLinkDao.deleteByScheme(key);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u4f53\u7cfb\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public ZbInfo initZbInfo() {
        ZbInfoDTO zbInfo = new ZbInfoDTO();
        zbInfo.setKey(UUID.randomUUID().toString());
        zbInfo.setOrder(OrderGenerator.newOrder());
        zbInfo.setUpdateTime(Instant.now());
        return zbInfo;
    }

    @Override
    public ZbInfo getZbInfo(String key) {
        ZbInfoDO infoDO = this.zbInfoDao.getByKey(key);
        List<PropInfoDO> infoDOS = this.propInfoDao.listPropInfoInScheme(infoDO.getSchemeKey());
        ZbInfoDO zbInfoDO = this.zbInfoDao.getByKey(key, new ArrayList<PropInfo>(infoDOS));
        return ZbSchemeConvert.cto(zbInfoDO);
    }

    @Override
    public ZbInfo getZbInfoByVersionAndCode(String version, String code) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getByKey(version);
        if (versionDO == null) {
            return null;
        }
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(versionDO.getSchemeKey());
        ZbInfoDO zbInfoDO = this.zbInfoDao.getByVersionAndCode(version, code, new ArrayList<PropInfo>(propInfoDOS));
        return ZbSchemeConvert.cto(zbInfoDO);
    }

    @Override
    public List<ZbInfo> listZbInfoBySchemeAndVersion(String schemeKey, String versionKey) {
        Assert.notNull((Object)schemeKey, "\u6307\u6807\u4f53\u7cfbkey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)versionKey, "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(schemeKey);
        List<ZbInfoDO> infos = this.zbInfoDao.listByVersion(versionKey, new ArrayList<PropInfo>(propInfoDOS));
        return infos.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbInfo> listZbInfoByParent(String parentKey) {
        Assert.notNull((Object)parentKey, "\u6307\u6807\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        ZbGroupDO group = this.zbGroupDao.getByKey(parentKey);
        if (group == null) {
            return Collections.emptyList();
        }
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(group.getSchemeKey());
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listByParent(parentKey, new ArrayList<PropInfo>(propInfoDOS));
        return infoDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbInfo> listZbInfoByKeys(List<String> keys) {
        Assert.notEmpty(keys, "\u6307\u6807key\u4e0d\u80fd\u4e3a\u7a7a");
        ZbInfoDO infoDO = this.zbInfoDao.getByKey(keys.get(0));
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(infoDO.getSchemeKey());
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listByKeys(keys, new ArrayList<PropInfo>(propInfoDOS));
        return infoDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbInfo> listZbInfoByVersion(String versionKey) {
        if (versionKey == null) {
            return Collections.emptyList();
        }
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getByKey(versionKey);
        if (versionDO == null) {
            return Collections.emptyList();
        }
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(versionDO.getSchemeKey());
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listByVersion(versionKey, new ArrayList<PropInfo>(propInfoDOS));
        return infoDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbInfo> listZbInfoByVersionAndCode(String versionKey, List<String> codes) {
        List<ZbInfo> zbInfos = this.listZbInfoByVersion(versionKey);
        return zbInfos.stream().filter(info -> codes.contains(info.getCode())).collect(Collectors.toList());
    }

    @Override
    public List<ZbInfo> listZbInfoBySchemeAndCode(String schemeKey, String code) {
        if (schemeKey == null || code == null) {
            return Collections.emptyList();
        }
        List<PropInfoDO> propInfoDOS = this.propInfoDao.listPropInfoInScheme(schemeKey);
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listBySchemeAndCode(schemeKey, code, new ArrayList<PropInfo>(propInfoDOS));
        if (infoDOS.isEmpty()) {
            return Collections.emptyList();
        }
        return infoDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public Integer insertZbInfo(ZbInfo zbInfo) {
        this.validator.checkZbInfo(zbInfo);
        try {
            ZbInfoDO cdo = ZbSchemeConvert.cdo(zbInfo);
            this.zbInfoDao.insert(Collections.singletonList(cdo));
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4fe1\u606f\u5931\u8d25", e);
            throw new ZbSchemeException("\u63d2\u5165\u6307\u6807\u4fe1\u606f\u5931\u8d25");
        }
        String parentKey = zbInfo.getParentKey();
        return this.zbInfoDao.listByParent(parentKey).size();
    }

    @Override
    public void insertZbInfo(List<ZbInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return;
        }
        ArrayList<ZbInfoDO> list = new ArrayList<ZbInfoDO>(infos.size());
        for (ZbInfo info : infos) {
            this.validator.checkZbInfo(info);
            list.add(ZbSchemeConvert.cdo(info));
        }
        try {
            this.zbInfoDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u63d2\u5165\u6307\u6807\u4fe1\u606f\u5931\u8d25", e);
            throw new ZbSchemeException("\u63d2\u5165\u6307\u6807\u4fe1\u606f\u5931\u8d25");
        }
    }

    @Override
    public String insertZbInfo(ZbInfoDTO zbInfoDTO, boolean useOld) {
        String key;
        if (useOld) {
            String code = zbInfoDTO.getCode();
            String schemeKey = zbInfoDTO.getSchemeKey();
            Assert.notNull((Object)code, "\u6307\u6807\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
            Assert.notNull((Object)schemeKey, "\u6307\u6807\u4f53\u7cfbkey\u4e0d\u80fd\u4e3a\u7a7a");
            List<ZbInfo> zbInfos = this.listZbInfoBySchemeAndCode(zbInfoDTO.getSchemeKey(), code);
            ZbInfo zbInfo = zbInfos.get(zbInfos.size() - 1);
            key = UUID.randomUUID().toString();
            zbInfo.setKey(key);
            zbInfo.setParentKey(zbInfoDTO.getParentKey());
            zbInfo.setUpdateTime(Instant.now());
            zbInfo.setVersionKey(zbInfoDTO.getVersionKey());
            try {
                this.zbInfoDao.insert(ZbSchemeConvert.cdo(zbInfo));
            }
            catch (Exception e) {
                logger.error("\u542f\u7528\u5386\u53f2\u6307\u6807\u4fe1\u606f\u5931\u8d25", e);
                throw new RuntimeException(e);
            }
        } else {
            this.insertZbInfo(zbInfoDTO);
            key = zbInfoDTO.getKey();
        }
        return key;
    }

    @Override
    public ZbGroup getZbGroup(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u5206\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        ZbGroupDO byKey = this.zbGroupDao.getByKey(key);
        return ZbSchemeConvert.cto(byKey);
    }

    @Override
    public void deleteZbGroup(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u5206\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        ZbGroupDO group = this.zbGroupDao.getByKey(key);
        if (group == null) {
            return;
        }
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getByKey(group.getVersionKey());
        if (versionDO == null || VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeCheckException("\u7248\u672c\u5df2\u7ecf\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539!");
        }
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<String> fieldKeys = new ArrayList<String>();
        HashSet<String> codes = new HashSet<String>();
        List<String> syncCode = this.syncProvider.listSyncZbCode(group.getSchemeKey(), group.getVersionKey());
        if (syncCode != null) {
            codes.addAll(syncCode);
        }
        this.findDeleteZbGroupKeys(group, codes, groupKeys, fieldKeys);
        try {
            this.zbGroupDao.deleteByKeys(groupKeys);
            this.zbInfoDao.deleteByKeys(fieldKeys);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    private void findDeleteZbGroupKeys(ZbGroupDO group, Set<String> codes, List<String> groupKeys, List<String> fieldKeys) {
        groupKeys.add(group.getKey());
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listByParent(group.getKey());
        for (ZbInfoDO infoDO : infoDOS) {
            fieldKeys.add(infoDO.getKey());
            if (!codes.contains(infoDO.getCode())) continue;
            throw new ZbSchemeException("\u76ee\u5f55\u4e0b\u5b58\u5728\u88ab\u5f15\u7528\u7684\u6307\u6807\uff0c\u7981\u6b62\u5220\u9664\uff01");
        }
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersionAndParent(group.getVersionKey(), group.getKey());
        for (ZbGroupDO groupDO : zbGroupDOS) {
            this.findDeleteZbGroupKeys(groupDO, codes, groupKeys, fieldKeys);
        }
    }

    @Override
    public void deleteZbGroup(List<String> keys) {
        Assert.notEmpty(keys, "\u6307\u6807\u5206\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByKeys(keys);
        for (ZbGroupDO groupDO : zbGroupDOS) {
            this.validator.checkZbGroupBeforeDelete(groupDO);
        }
        try {
            this.zbGroupDao.deleteByKeys(keys);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void deleteZbGroupByVersion(String versionKey) {
    }

    @Override
    public void deleteZbInfo(String key) {
        this.deleteZbInfo(Collections.singletonList(key));
    }

    @Override
    @Transactional(rollbackFor={ZbSchemeException.class}, noRollbackFor={ZbSchemeCheckException.class})
    public Integer deleteZbInfo(List<String> keys) {
        Assert.notEmpty(keys, "\u6307\u6807\u4fe1\u606fkey\u4e0d\u80fd\u4e3a\u7a7a");
        List<ZbInfoDO> infos = this.zbInfoDao.listByKeys(keys);
        ArrayList<ZbInfoDO> invalidInfos = new ArrayList<ZbInfoDO>();
        for (ZbInfoDO zbInfo : infos) {
            this.validator.checkZbInfoBeforeDelete(zbInfo);
            if (!this.syncProvider.checkZbRefer(zbInfo.getSchemeKey(), zbInfo.getCode())) continue;
            invalidInfos.add(zbInfo);
            keys.remove(zbInfo.getKey());
        }
        try {
            this.zbInfoDao.deleteByKeys(keys);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u6307\u6807\u4fe1\u606f\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
        if (!invalidInfos.isEmpty()) {
            throw new ZbSchemeCheckException("\u6307\u6807\u88ab\u5f15\u7528\uff0c\u65e0\u6cd5\u5220\u9664");
        }
        String parentKey = infos.get(0).getParentKey();
        return this.zbInfoDao.listByParent(parentKey).size();
    }

    @Override
    public void deleteZbInfoByVersion(String versionKey) {
    }

    @Override
    public List<ZbSchemeGroup> listAllZbSchemeGroup() {
        List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listAll();
        return groupDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbScheme> listAllZbScheme() {
        List<ZbSchemeDO> zbSchemeDOS = this.zbSchemeDao.listAll();
        return zbSchemeDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public void moveZbProp(Move move, String key) {
        Assert.notNull((Object)move, "\u79fb\u52a8\u65b9\u5411\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)key, "\u6307\u6807\u5c5e\u6027key\u4e0d\u80fd\u4e3a\u7a7a");
        List<PropInfoDO> list = this.propInfoDao.listAll();
        ArrayList<PropInfoDO> update = new ArrayList<PropInfoDO>();
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).getKey().equals(key)) continue;
            index = i;
            break;
        }
        if (index == -1) {
            return;
        }
        if (move == Move.UP && index < 1) {
            throw new ZbSchemeException("\u7981\u6b62\u4e0a\u79fb\u7b2c\u4e00\u4e2a\u6570\u636e\u9879");
        }
        if (move == Move.DOWN && index >= list.size() - 1) {
            throw new ZbSchemeException("\u7981\u6b62\u4e0b\u79fb\u6700\u540e\u4e00\u4e2a\u6570\u636e\u9879");
        }
        PropInfoDO prop = list.get(index);
        PropInfoDO tempProp = list.get(index + (move == Move.UP ? -1 : 1));
        update.add(prop);
        update.add(tempProp);
        String tempOrder = prop.getOrder();
        prop.setOrder(tempProp.getOrder());
        tempProp.setOrder(tempOrder);
        try {
            this.propInfoDao.update(update, false);
        }
        catch (Exception e) {
            logger.error("\u79fb\u52a8\u6307\u6807\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public List<ZbGroup> listZbGroupBySchemeAndPeriod(String schemeKey, String period) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getBySchemeAndPeriod(schemeKey, period);
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listBySchemeAndVersion(schemeKey, versionDO.getKey());
        return zbGroupDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbGroup> listZbGroupByVersion(String versionKey) {
        Assert.notNull((Object)versionKey, "\u7248\u672ckey\u4e0d\u80fd\u4e3a\u7a7a");
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersion(versionKey);
        return zbGroupDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbGroup> listZbGroupByParent(String parent) {
        Assert.notNull((Object)parent, "\u7236\u7ea7key\u4e0d\u80fd\u4e3a\u7a7a");
        if ("00000000-0000-0000-0000-000000000000".equals(parent)) {
            throw new ZbSchemeException("\u7236\u8282\u70b9\u4e0d\u80fd\u4e3a00000000-0000-0000-0000-000000000000");
        }
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByParent(parent);
        return zbGroupDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
    }

    @Override
    public List<ZbGroup> listZbGroupByVersionAndParent(String version, String parent) {
        Assert.notNull((Object)version, "\u7248\u672ckey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)parent, "\u7236\u7ea7key\u4e0d\u80fd\u4e3a\u7a7a");
        if ("00000000-0000-0000-0000-000000000000".equals(parent)) {
            List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersionAndParent(version, parent);
            return zbGroupDOS.stream().map(ZbSchemeConvert::cto).collect(Collectors.toList());
        }
        return this.listZbGroupByParent(parent);
    }

    @Override
    public ZbGroup initZbGroup() {
        ZbGroupDTO groupDTO = new ZbGroupDTO();
        groupDTO.setKey(UUID.randomUUID().toString());
        groupDTO.setOrder(OrderGenerator.newOrder());
        groupDTO.setUpdateTime(Instant.now());
        return groupDTO;
    }

    @Override
    public void insertZbGroup(ZbGroup zbGroup) {
        Assert.notNull((Object)zbGroup, "\u6307\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        this.insertZbGroup(Collections.singletonList(zbGroup));
    }

    @Override
    public void updateZbGroup(ZbGroup zbGroup) {
        Assert.notNull((Object)zbGroup, "\u6307\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        this.updateZbGroup(Collections.singletonList(zbGroup));
    }

    @Override
    public void insertZbGroup(List<ZbGroup> zbGroups) {
        if (CollectionUtils.isEmpty(zbGroups)) {
            return;
        }
        ArrayList<ZbGroupDO> list = new ArrayList<ZbGroupDO>(zbGroups.size());
        for (ZbGroup zbGroup : zbGroups) {
            this.validator.checkZbGroup(zbGroup);
            list.add(ZbSchemeConvert.cdo(zbGroup));
        }
        try {
            this.zbGroupDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u65b0\u589e\u6307\u6807\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void updateZbGroup(List<ZbGroup> zbGroups) {
        if (CollectionUtils.isEmpty(zbGroups)) {
            return;
        }
        ArrayList<ZbGroupDO> list = new ArrayList<ZbGroupDO>(zbGroups.size());
        for (ZbGroup zbGroup : zbGroups) {
            this.validator.checkZbGroup(zbGroup);
            list.add(ZbSchemeConvert.cdo(zbGroup));
        }
        try {
            this.zbGroupDao.update(list);
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u5206\u7ec4\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void updateZbInfo(ZbInfo zbInfo) {
        Assert.notNull((Object)zbInfo, "\u6307\u6807\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        this.updateZbInfo(Collections.singletonList(zbInfo));
    }

    @Override
    public void updateZbInfo(List<ZbInfo> zbInfos) {
        if (CollectionUtils.isEmpty(zbInfos)) {
            return;
        }
        ArrayList<ZbInfoDO> list = new ArrayList<ZbInfoDO>(zbInfos.size());
        for (ZbInfo zbInfo : zbInfos) {
            this.validator.checkZbInfoBeforeUpdate(zbInfo);
            list.add(ZbSchemeConvert.cdo(zbInfo));
        }
        try {
            this.zbInfoDao.update(list);
        }
        catch (Exception e) {
            logger.error("\u66f4\u65b0\u6307\u6807\u4fe1\u606f\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void saveZbSchemeProp(String schemeKey, List<PropLink> links) {
        ArrayList<PropLinkDO> list = new ArrayList<PropLinkDO>(links.size());
        for (PropLink link : links) {
            list.add(ZbSchemeConvert.cdo(link));
        }
        try {
            this.propLinkDao.deleteByScheme(schemeKey);
            this.propLinkDao.insert(list);
        }
        catch (Exception e) {
            logger.error("\u4fdd\u5b58\u6307\u6807\u6269\u5c55\u5c5e\u6027\u5931\u8d25", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public int countZbByVersion(String version) {
        Assert.notNull((Object)version, "\u7248\u672ckey\u4e0d\u80fd\u4e3a\u7a7a");
        return this.zbInfoDao.countZbByVersion(version);
    }

    @Override
    public int countZbByScheme(String schemeKey) {
        Assert.notNull((Object)schemeKey, "\u6307\u6807\u4f53\u7cfbkey\u4e0d\u80fd\u4e3a\u7a7a");
        return this.zbInfoDao.countZbByScheme(schemeKey);
    }

    @Override
    public void publishVersion(String versionKey, String period, VersionStatus changeStatus) {
        Assert.notNull((Object)versionKey, "The versionKey must not be null");
        ZbSchemeVersionDO byKey = this.zbSchemeVersionDao.getByKey(versionKey);
        if (byKey == null) {
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u5b58\u5728");
        }
        switch (byKey.getStatus()) {
            case UNPUBLISHED: 
            case CANCELED: {
                if (VersionStatus.PUBLISHED.equals((Object)changeStatus)) {
                    byKey.setStatus(changeStatus);
                    break;
                }
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u5df2\u7ecf\u53d1\u5e03");
            }
            case PUBLISHED: {
                if (VersionStatus.CANCELED.equals((Object)changeStatus)) {
                    byKey.setStatus(changeStatus);
                    break;
                }
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u53d1\u5e03\u53c2\u6570\u65e0\u6548");
            }
        }
        try {
            this.zbSchemeVersionDao.update(Collections.singletonList(byKey));
        }
        catch (Exception e) {
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public int getReferVersionNum(String versionKey) {
        return this.zbSchemeVersionDao.getReferNum(versionKey);
    }

    @Override
    public PageVO<ZbInfoVO> moveZbInfo(MoveParam moveParam) {
        PageVO<ZbInfoVO> res;
        Move move = moveParam.getMove();
        List<ZbInfo> zbInfos = this.listZbInfoByParent(moveParam.getParentKey());
        int currentPage = moveParam.getCurrentPage();
        int pageSize = moveParam.getPageSize();
        if (CollectionUtils.isEmpty(moveParam.getSelectedKeys())) {
            throw new ZbSchemeException("\u672a\u9009\u4e2d\u6307\u6807");
        }
        HashSet<String> selectedKeys = new HashSet<String>(moveParam.getSelectedKeys());
        int skip = (currentPage - 1) * pageSize;
        List pageInfo = zbInfos.stream().skip(skip).limit(pageSize).collect(Collectors.toList());
        int start = -1;
        int end = -1;
        HashMap<String, ZbInfo> update = new HashMap<String, ZbInfo>();
        if (Move.DOWN.equals((Object)move)) {
            pageInfo.sort(Comparator.reverseOrder());
        }
        for (int i = 0; i < pageInfo.size(); ++i) {
            ZbInfo tempZbInfo;
            ZbInfo infoDTO = (ZbInfo)pageInfo.get(i);
            if (!selectedKeys.remove(infoDTO.getKey())) continue;
            if (start == -1) {
                start = end = i;
            } else {
                if (end != i - 1) {
                    throw new ZbSchemeException("\u6307\u6807\u4e0d\u8fde\u7eed");
                }
                end = i;
            }
            if (i == 0) {
                if (Move.UP.equals((Object)move)) {
                    ZbInfo zbInfo = tempZbInfo = skip - 1 > 0 ? zbInfos.get(skip - 1) : null;
                    if (tempZbInfo == null) {
                        throw new ZbSchemeException("\u6307\u6807\u5df2\u7ecf\u5728\u6700\u9876\u90e8");
                    }
                } else {
                    ZbInfo zbInfo = tempZbInfo = skip + pageSize < zbInfos.size() ? zbInfos.get(skip + pageSize) : null;
                    if (tempZbInfo == null) {
                        throw new ZbSchemeException("\u6307\u6807\u5df2\u7ecf\u5728\u6700\u5e95\u90e8");
                    }
                }
            } else {
                tempZbInfo = (ZbInfo)pageInfo.get(i - 1);
                pageInfo.set(i - 1, infoDTO);
            }
            pageInfo.set(i, tempZbInfo);
            String tempOrder = tempZbInfo.getOrder();
            tempZbInfo.setOrder(infoDTO.getOrder());
            infoDTO.setOrder(tempOrder);
            if (!update.containsKey(tempZbInfo.getKey())) {
                update.put(tempZbInfo.getKey(), tempZbInfo);
            }
            if (update.containsKey(infoDTO.getKey())) continue;
            update.put(infoDTO.getKey(), infoDTO);
        }
        if (start <= 0) {
            if (end != start) {
                throw new ZbSchemeException("\u7981\u6b62\u591a\u9009\u8de8\u9875\u79fb\u52a8");
            }
            res = new PageVO(currentPage + (Move.UP.equals((Object)move) ? -1 : 1), pageSize, zbInfos.size());
        } else {
            res = new PageVO<ZbInfoVO>(currentPage, pageSize, zbInfos.size());
        }
        int index = res.getSkip() + 1;
        List pageData = zbInfos.stream().sorted().skip(res.getSkip().intValue()).limit(res.getLimit().intValue()).map(ZbSchemeConvert::cvo).collect(Collectors.toList());
        for (ZbInfoVO pageDatum : pageData) {
            pageDatum.setIndex(index++);
        }
        res.setData(pageData);
        this.updateZbInfo(new ArrayList<ZbInfo>(update.values()));
        return res;
    }

    @Override
    public Boolean isInOldVersion(String schemeKey, String period, String code) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDao.getBySchemeAndPeriod(schemeKey, period);
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listBySchemeAndCode(schemeKey, code);
        for (ZbInfoDO infoDO : infoDOS) {
            if (!infoDO.getVersionKey().equals(versionDO.getKey())) continue;
            throw new ZbSchemeException("\u6307\u6807\u6807\u8bc6\u91cd\u590d");
        }
        return !CollectionUtils.isEmpty(infoDOS);
    }

    @Override
    public void moveZbScheme(String key, Move move) {
        Assert.notNull((Object)key, "key must not be null.");
        Assert.notNull((Object)move, "move must not be null.");
        List<ZbSchemeDO> zbSchemeDOS = this.zbSchemeDao.listByParent("00000000-0000-0000-0000-000000000000");
        ArrayList<ZbSchemeDO> list = new ArrayList<ZbSchemeDO>(2);
        for (int i = 0; i < zbSchemeDOS.size(); ++i) {
            ZbSchemeDO temp;
            if (!key.equals(zbSchemeDOS.get(i).getKey())) continue;
            ZbSchemeDO zbSchemeDO = zbSchemeDOS.get(i);
            list.add(zbSchemeDO);
            if (move.equals((Object)Move.UP)) {
                if (i == 0) {
                    throw new ZbSchemeException("\u5df2\u7ecf\u5728\u9876\u90e8");
                }
                temp = zbSchemeDOS.get(i - 1);
                this.swap(zbSchemeDO, temp);
                list.add(temp);
                continue;
            }
            if (i == zbSchemeDOS.size() - 1) {
                throw new ZbSchemeException("\u5df2\u7ecf\u5728\u5e95\u90e8");
            }
            temp = zbSchemeDOS.get(i + 1);
            this.swap(zbSchemeDO, temp);
            list.add(temp);
        }
        if (list.size() == 2) {
            try {
                this.zbSchemeDao.update(list);
                logger.debug("move zbScheme success");
            }
            catch (Exception e) {
                logger.error("move zbScheme error", e);
                throw new ZbSchemeException(e);
            }
        }
    }

    private void swap(ZbSchemeDO zbScheme, ZbSchemeDO temp) {
        String tempOrder = zbScheme.getOrder();
        zbScheme.setOrder(temp.getOrder());
        temp.setOrder(tempOrder);
    }

    @Override
    public void moveZbGroup(String parentKey, String key, Move move) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        Assert.notNull((Object)key, "key must not be null.");
        Assert.notNull((Object)move, "move must not be null.");
        ZbGroupDO zbGroupDO = this.zbGroupDao.getByKey(key);
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersionAndParent(zbGroupDO.getVersionKey(), parentKey);
        ArrayList<ZbGroupDO> list = new ArrayList<ZbGroupDO>(2);
        for (int i = 0; i < zbGroupDOS.size(); ++i) {
            ZbGroupDO temp;
            if (!key.equals(zbGroupDOS.get(i).getKey())) continue;
            ZbGroupDO groupDO = zbGroupDOS.get(i);
            list.add(groupDO);
            if (move.equals((Object)Move.UP)) {
                if (i == 0) {
                    throw new ZbSchemeException("\u5df2\u7ecf\u5728\u9876\u90e8");
                }
                temp = zbGroupDOS.get(i - 1);
                this.swap(groupDO, temp);
                list.add(temp);
                continue;
            }
            if (i == zbGroupDOS.size() - 1) {
                throw new ZbSchemeException("\u5df2\u7ecf\u5728\u5e95\u90e8");
            }
            temp = zbGroupDOS.get(i + 1);
            this.swap(groupDO, temp);
            list.add(temp);
        }
        if (list.size() == 2) {
            try {
                this.zbGroupDao.update(list);
                logger.debug("move zbGroup success");
            }
            catch (Exception e) {
                logger.error("move zbGroup error", e);
                throw new ZbSchemeException(e);
            }
        }
    }

    private void swap(ZbGroupDO groupDO, ZbGroupDO temp) {
        String tempOrder = groupDO.getOrder();
        groupDO.setOrder(temp.getOrder());
        temp.setOrder(tempOrder);
    }

    @Override
    public void moveZbToGroup(List<String> selectedKeys, String parentKey) {
        Assert.notEmpty(selectedKeys, "selectedKeys must not be empty.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<ZbInfoDO> infoDOS = this.zbInfoDao.listByKeys(selectedKeys);
        for (ZbInfoDO infoDO : infoDOS) {
            infoDO.setParentKey(parentKey);
        }
        try {
            this.zbInfoDao.update(infoDOS);
            logger.debug("move zbToGroup success");
        }
        catch (Exception e) {
            logger.error("move zbToGroup error", e);
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void clearEmptyGroup(String groupKey) {
        Assert.notNull((Object)groupKey, "groupKey must not be null.");
        ZbGroup zbGroup = this.getZbGroup(groupKey);
        if (zbGroup == null) {
            return;
        }
        List<ZbGroup> zbGroups = this.listZbGroupByVersion(zbGroup.getVersionKey());
        ArrayList<ZbGroup> groups = new ArrayList<ZbGroup>();
        groups.add(zbGroup);
        Map<String, List<ZbGroup>> groupMap = zbGroups.stream().collect(Collectors.groupingBy(MetaGroup::getParentKey));
        if (!groupMap.containsKey(groupKey)) {
            return;
        }
        HashSet<String> deletes = new HashSet<String>();
        List<ZbInfo> zbInfos = this.listZbInfoByVersion(zbGroup.getVersionKey());
        Set<String> zbParentKeys = zbInfos.stream().map(ZbInfo::getParentKey).collect(Collectors.toSet());
        this.findEmptyGroup(groupMap, groupKey, deletes, zbParentKeys);
        this.deleteZbGroup(new ArrayList<String>(deletes));
        logger.debug("\u5220\u9664\u7a7a\u76ee\u5f55\uff1a{}", (Object)deletes.size());
    }

    private void findEmptyGroup(Map<String, List<ZbGroup>> groupMap, String groupKey, Set<String> deletes, Set<String> zbParentKeys) {
        if (groupMap.containsKey(groupKey)) {
            List<ZbGroup> zbGroups = groupMap.get(groupKey);
            for (int i = 0; i < zbGroups.size(); ++i) {
                ZbGroup group = zbGroups.get(i);
                String key = group.getKey();
                this.findEmptyGroup(groupMap, key, deletes, zbParentKeys);
                if (!deletes.contains(key)) continue;
                zbGroups.set(i, null);
            }
            if (zbGroups.stream().allMatch(Objects::isNull)) {
                deletes.add(groupKey);
            }
        } else if (!zbParentKeys.contains(groupKey)) {
            deletes.add(groupKey);
        }
    }
}

