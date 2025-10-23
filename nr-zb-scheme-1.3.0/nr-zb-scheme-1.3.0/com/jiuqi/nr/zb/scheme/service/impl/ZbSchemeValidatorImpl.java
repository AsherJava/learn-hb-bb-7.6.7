/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeCheckException;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.dao.IPropInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbInfoDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeVersionDao;
import com.jiuqi.nr.zb.scheme.internal.dto.PropInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbSchemeVersionDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.PropInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbInfoDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeGroupDO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeVersionDO;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeValidator;
import com.jiuqi.nr.zb.scheme.utils.FieldNameUtils;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ZbSchemeValidatorImpl
implements IZbSchemeValidator {
    @Autowired
    private IZbSchemeDao zbSchemeDAO;
    @Autowired
    private IZbInfoDao zbInfoDAO;
    @Autowired
    private IZbGroupDao zbGroupDao;
    @Autowired
    private IZbSchemeVersionDao zbSchemeVersionDAO;
    @Autowired
    private IPropInfoDao propInfoDao;
    @Autowired
    private IZbSchemeGroupDao zbSchemeGroupDao;

    @Override
    public void checkZbScheme(ZbSchemeDTO zbScheme) {
        Assert.notNull((Object)zbScheme, "\u6307\u6807\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbScheme.getCode(), "\u6307\u6807\u4f53\u7cfb\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbScheme.getTitle(), "\u6307\u6807\u4f53\u7cfb\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        if (zbScheme.getKey() == null) {
            zbScheme.setKey(UUID.randomUUID().toString());
            List<ZbSchemeDO> zbSchemeDOS = this.zbSchemeDAO.listAll();
            if (zbSchemeDOS.stream().anyMatch(scheme -> scheme.getCode().equals(zbScheme.getCode()))) {
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u6807\u8bc6\u91cd\u590d");
            }
            zbSchemeDOS = this.zbSchemeDAO.listByParent(zbScheme.getParentKey());
            for (ZbSchemeDO zbSchemeDO : zbSchemeDOS) {
                if (!zbScheme.getTitle().equals(zbSchemeDO.getTitle())) continue;
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u540d\u79f0\u91cd\u590d");
            }
        } else {
            List<ZbSchemeDO> zbSchemeDOS = this.zbSchemeDAO.listByParent(zbScheme.getParentKey());
            for (ZbSchemeDO zbSchemeDO : zbSchemeDOS) {
                if (zbScheme.getKey().equals(zbSchemeDO.getKey()) || !zbScheme.getTitle().equals(zbSchemeDO.getTitle())) continue;
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u540d\u79f0\u91cd\u590d");
            }
        }
        if (zbScheme.getOrder() == null) {
            zbScheme.setOrder(OrderGenerator.newOrder());
        }
        if (zbScheme.getUpdateTime() == null) {
            zbScheme.setUpdateTime(Instant.now());
        }
    }

    @Override
    public void checkZbSchemeGroup(ZbSchemeGroup group) {
        Assert.notNull((Object)group, "\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)group.getTitle(), "\u6307\u6807\u4f53\u7cfb\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        if (group.getKey() == null) {
            group.setKey(UUID.randomUUID().toString());
            List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listByParent(group.getParentKey());
            for (ZbSchemeGroupDO groupDO : groupDOS) {
                if (!group.getTitle().equals(groupDO.getTitle())) continue;
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u540d\u79f0\u91cd\u590d");
            }
        } else {
            List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listByParent(group.getParentKey());
            for (ZbSchemeGroupDO groupDO : groupDOS) {
                if (group.getKey().equals(groupDO.getKey()) || !group.getTitle().equals(groupDO.getTitle())) continue;
                throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u540d\u79f0\u91cd\u590d");
            }
        }
        if (group.getOrder() == null) {
            group.setOrder(OrderGenerator.newOrder());
        }
        if (group.getUpdateTime() == null) {
            group.setUpdateTime(Instant.now());
        }
    }

    @Override
    public void checkPropInfo(PropInfo prop) {
        Assert.notNull((Object)prop, "\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)prop.getTitle(), "\u6269\u5c55\u5c5e\u6027\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)prop.getDataType(), "\u6269\u5c55\u5c5e\u6027\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.isTrue(prop.getPrecision() != null && prop.getPrecision() > 0, "\u6269\u5c55\u5c5e\u6027\u957f\u5ea6\u5927\u4e8e0");
        if (prop.getKey() == null) {
            prop.setKey(UUID.randomUUID().toString());
            List<PropInfoDO> propInfos = this.propInfoDao.listAll();
            for (PropInfo propInfo : propInfos) {
                if (!propInfo.getTitle().equals(prop.getTitle())) continue;
                throw new ZbSchemeException("\u6269\u5c55\u5c5e\u6027\u540d\u79f0\u91cd\u590d");
            }
            Set<String> codes = propInfos.stream().map(PropInfo::getCode).collect(Collectors.toSet());
            FieldNameUtils fieldNameUtils = new FieldNameUtils(codes);
            prop.setFieldName(fieldNameUtils.next());
        } else {
            List<PropInfoDO> propInfos = this.propInfoDao.listAll();
            PropInfo oldProp = null;
            for (PropInfo propInfo : propInfos) {
                if (propInfo.getKey().equals(prop.getKey())) {
                    oldProp = propInfo;
                }
                if (prop.getKey().equals(propInfo.getKey()) || !propInfo.getTitle().equals(prop.getTitle())) continue;
                throw new ZbSchemeException("\u6269\u5c55\u5c5e\u6027\u540d\u79f0\u91cd\u590d");
            }
            if (oldProp == null) {
                throw new ZbSchemeException("\u6269\u5c55\u5c5e\u6027\u4e0d\u5b58\u5728");
            }
            int n = this.zbInfoDAO.getNonEmptyFieldCount(prop.getFieldName());
            if (n > 0) {
                if (prop.getDataType() != oldProp.getDataType()) {
                    throw new ZbSchemeException("\u6570\u636e\u7c7b\u578b\u4e0d\u80fd\u4fee\u6539");
                }
                Integer n2 = prop.getPrecision();
                Integer precision1 = oldProp.getPrecision();
                if (n2 != null && precision1 != null) {
                    if (n2 < precision1) {
                        throw new ZbSchemeException("\u957f\u5ea6/\u7cbe\u5ea6\u4e0d\u80fd\u53d8\u5c0f");
                    }
                    Integer decimal = prop.getDecimal();
                    Integer decimal1 = oldProp.getDecimal();
                    if (decimal != null && decimal1 != null && (n2 - decimal < precision1 - decimal1 || decimal < decimal1)) {
                        throw new ZbSchemeException("\u5c0f\u6570\u4f4d\u4e0d\u80fd\u53d8\u5c0f");
                    }
                }
            }
        }
        if (prop.getOrder() == null) {
            prop.setOrder(OrderGenerator.newOrder());
        }
        if (prop.getUpdateTime() == null) {
            prop.setUpdateTime(Instant.now());
        }
    }

    @Override
    public void checkZbGroup(ZbGroup zbGroup) {
        Assert.notNull((Object)zbGroup, "\u6307\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getTitle(), "\u6307\u6807\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getSchemeKey(), "\u6307\u6807\u5206\u7ec4schemeKey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getVersionKey(), "\u6307\u6807\u5206\u7ec4version\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getParentKey(), "\u6307\u6807\u5206\u7ec4parentKey\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO version = this.zbSchemeVersionDAO.getByKey(zbGroup.getVersionKey());
        Assert.notNull((Object)version, "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u5b58\u5728");
        Assert.isTrue(!version.getStatus().equals((Object)VersionStatus.PUBLISHED), "\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539\u6570\u636e!");
        if (zbGroup.getKey() == null) {
            zbGroup.setKey(UUID.randomUUID().toString());
        }
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersionAndParent(zbGroup.getVersionKey(), zbGroup.getParentKey());
        for (ZbGroupDO zbGroupDO : zbGroupDOS) {
            if (zbGroup.getKey().equals(zbGroupDO.getKey()) || !zbGroup.getTitle().equals(zbGroupDO.getTitle())) continue;
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u540d\u79f0\u91cd\u590d");
        }
        if (zbGroup.getOrder() == null) {
            zbGroup.setOrder(OrderGenerator.newOrder());
        }
        if (zbGroup.getUpdateTime() == null) {
            zbGroup.setUpdateTime(Instant.now());
        }
    }

    public void checkSchemeVersionBeforeInsert(ZbSchemeVersionDTO versionDTO, Consumer<String> oldPeriod) {
        Assert.notNull((Object)versionDTO, "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)versionDTO.getTitle(), "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        if (versionDTO.getKey() == null) {
            versionDTO.setKey(UUID.randomUUID().toString());
        }
        List<ZbSchemeVersionDO> versionByScheme = this.zbSchemeVersionDAO.listByScheme(versionDTO.getSchemeKey());
        ZbSchemeVersionDO oldVersion = null;
        for (ZbSchemeVersionDO versionDO : versionByScheme) {
            if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
                oldVersion = versionDO;
                continue;
            }
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u672a\u5168\u90e8\u53d1\u5e03");
        }
        if (oldVersion == null || oldVersion.getStartPeriod().compareTo(versionDTO.getStartPeriod()) >= 0) {
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u7684\u65f6\u95f4\u5e94\u5927\u4e8e\u4e4b\u524d\u7684\u7248\u672c");
        }
        versionDTO.setEndPeriod(oldVersion.getEndPeriod());
        oldVersion.setEndPeriod(versionDTO.getStartPeriod());
        oldPeriod.accept(oldVersion.getStartPeriod());
        try {
            this.zbSchemeVersionDAO.update(Collections.singletonList(oldVersion));
        }
        catch (Exception e) {
            throw new ZbSchemeException(e);
        }
    }

    @Override
    public void checkSchemeVersionBeforeDelete(String key) {
        Assert.notNull((Object)key, "\u7248\u672cKey\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDAO.getByKey(key);
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u7248\u672c\u5904\u4e8e\u53d1\u5e03\u72b6\u6001\uff0c\u7981\u6b62\u5220\u9664");
        }
    }

    @Override
    public void checkZbPropBeforeUpdate(PropInfoDTO prop) {
        Assert.notNull((Object)prop, "\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)prop.getTitle(), "\u6269\u5c55\u5c5e\u6027\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)prop.getDataType(), "\u6269\u5c55\u5c5e\u6027\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        if (prop.getKey() == null) {
            prop.setKey(UUID.randomUUID().toString());
        }
    }

    @Override
    public void checkSchemeVersionBeforeUpdate(ZbSchemeVersion version) {
        Assert.notNull((Object)version, "\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)version.getTitle(), "\u7248\u672c\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)version.getKey(), "\u7248\u672cKey\u4e0d\u80fd\u4e3a\u7a7a");
        if (VersionStatus.PUBLISHED.equals((Object)version.getStatus())) {
            throw new ZbSchemeException("\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539");
        }
    }

    @Override
    public void checkZbGroupBeforeDelete(ZbGroupDO zbGroup) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDAO.getByKey(zbGroup.getVersionKey());
        List<ZbInfoDO> zbInfoDOS = this.zbInfoDAO.listByParent(zbGroup.getKey());
        if (versionDO == null) {
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u6240\u5728\u7684\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (!zbInfoDOS.isEmpty()) {
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u4e0b\u5b58\u5728\u6570\u636e\uff0c\u7981\u6b62\u5220\u9664");
        }
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u6240\u5728\u7684\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u5220\u9664");
        }
    }

    @Override
    public void checkZbGroupBeforeUpdate(ZbGroup zbGroup) {
        Assert.notNull((Object)zbGroup, "\u6307\u6807\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getKey(), "\u6307\u6807\u5206\u7ec4Key\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getSchemeKey(), "\u6307\u6807\u5206\u7ec4\u7684\u4f53\u7cfbKey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getVersionKey(), "\u6307\u6807\u5206\u7ec4\u7684\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbGroup.getTitle(), "\u6307\u6807\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDAO.getByKey(zbGroup.getVersionKey());
        if (versionDO == null) {
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u6240\u5728\u7684\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u6240\u5728\u7684\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539");
        }
        List<ZbGroupDO> zbGroupDOS = this.zbGroupDao.listByVersionAndParent(zbGroup.getVersionKey(), zbGroup.getParentKey());
        for (ZbGroupDO zbGroupDO : zbGroupDOS) {
            if (zbGroup.getKey().equals(zbGroupDO.getKey()) || !zbGroup.getTitle().equals(zbGroupDO.getTitle())) continue;
            throw new ZbSchemeException("\u6307\u6807\u5206\u7ec4\u540d\u79f0\u91cd\u590d");
        }
    }

    @Override
    public void checkZbInfoBeforeUpdate(ZbInfo zbInfo) {
        Assert.notNull((Object)zbInfo, "\u6307\u6807\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getTitle(), "\u6307\u6807\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getCode(), "\u6307\u6807code\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getSchemeKey(), "\u6307\u6807\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getVersionKey(), "\u6307\u6807\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getKey(), "\u6307\u6807key\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDAO.getByKey(zbInfo.getVersionKey());
        if (versionDO == null) {
            throw new ZbSchemeException("\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539");
        }
        Map<String, String> codeKeyMap = this.zbInfoDAO.listBySchemeAndVersion(zbInfo.getSchemeKey(), zbInfo.getVersionKey()).stream().collect(Collectors.toMap(ZbInfoDO::getCode, ZbInfoDO::getKey));
        if (codeKeyMap.containsKey(zbInfo.getCode()) && !zbInfo.getKey().equals(codeKeyMap.get(zbInfo.getCode()))) {
            throw new ZbSchemeCheckException(String.format("\u6307\u6807\u6807\u8bc6[%s]\uff0c\u7248\u672c\u5185\u4e0d\u552f\u4e00", zbInfo.getCode()));
        }
    }

    @Override
    public void checkZbSchemeVersion(String versionKey) {
        Assert.notNull((Object)versionKey, "\u6307\u6807\u4f53\u7cfb\u7248\u672cKey\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO version = this.zbSchemeVersionDAO.getByKey(versionKey);
        if (VersionStatus.PUBLISHED.equals((Object)version.getStatus())) {
            throw new ZbSchemeException("\u6307\u6807\u4f53\u7cfb\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539");
        }
    }

    @Override
    public void checkZbPropBeforeDelete(PropInfoDTO propInfoDTO) {
        Assert.notNull((Object)propInfoDTO, "\u6269\u5c55\u5c5e\u6027\u4e0d\u80fd\u4e3a\u7a7a");
    }

    @Override
    public void checkZbSchemeGroupBeforeDelete(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u4f53\u7cfb\u5206\u7ec4key\u4e0d\u80fd\u4e3a\u7a7a");
        List<ZbSchemeDO> schemeDOS = this.zbSchemeDAO.listByParent(key);
        if (!schemeDOS.isEmpty()) {
            throw new ZbSchemeCheckException("\u8be5\u6307\u6807\u4f53\u7cfb\u5206\u7ec4\u4e0b\u5b58\u5728\u6307\u6807\u4f53\u7cfb\uff0c\u7981\u6b62\u5220\u9664");
        }
        List<ZbSchemeGroupDO> groupDOS = this.zbSchemeGroupDao.listByParent(key);
        if (!groupDOS.isEmpty()) {
            for (ZbSchemeGroupDO groupDO : groupDOS) {
                this.checkZbSchemeGroupBeforeDelete(groupDO.getKey());
            }
        }
    }

    @Override
    public void checkZbSchemeBeforeDelete(String key) {
        Assert.notNull((Object)key, "\u6307\u6807\u4f53\u7cfbkey\u4e0d\u80fd\u4e3a\u7a7a");
    }

    @Override
    public void checkZbInfo(ZbInfo zbInfo) {
        Assert.notNull((Object)zbInfo, "\u6307\u6807\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getTitle(), "\u6307\u6807\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getCode(), "\u6307\u6807code\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getSchemeKey(), "\u6307\u6807\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)zbInfo.getVersionKey(), "\u6307\u6807\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        ZbSchemeVersionDO version = this.zbSchemeVersionDAO.getByKey(zbInfo.getVersionKey());
        if (version == null) {
            throw new ZbSchemeException("\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (VersionStatus.PUBLISHED.equals((Object)version.getStatus())) {
            throw new ZbSchemeException("\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u4fee\u6539\u6570\u636e");
        }
        Set codes = this.zbInfoDAO.listBySchemeAndVersion(zbInfo.getSchemeKey(), zbInfo.getVersionKey()).stream().map(ZbInfoDO::getCode).collect(Collectors.toSet());
        if (codes.contains(zbInfo.getCode())) {
            throw new ZbSchemeCheckException(String.format("\u6307\u6807\u6807\u8bc6[%s]\uff0c\u7248\u672c\u5185\u4e0d\u552f\u4e00", zbInfo.getCode()));
        }
        if (zbInfo.getKey() == null) {
            zbInfo.setKey(UUID.randomUUID().toString());
        }
        if (zbInfo.getOrder() == null) {
            zbInfo.setOrder(OrderGenerator.newOrder());
        }
        if (zbInfo.getUpdateTime() == null) {
            zbInfo.setUpdateTime(Instant.now());
        }
    }

    @Override
    public void checkZbInfoBeforeDelete(ZbInfoDO zbInfo) {
        ZbSchemeVersionDO versionDO = this.zbSchemeVersionDAO.getByKey(zbInfo.getVersionKey());
        if (versionDO == null) {
            throw new ZbSchemeException("\u6307\u6807\u6240\u5728\u7684\u7248\u672c\u4e0d\u5b58\u5728");
        }
        if (VersionStatus.PUBLISHED.equals((Object)versionDO.getStatus())) {
            throw new ZbSchemeException("\u6307\u6807\u6240\u5728\u7684\u7248\u672c\u5df2\u53d1\u5e03\uff0c\u7981\u6b62\u5220\u9664");
        }
    }

    @Override
    public void checkZbSchemeVersion(ZbSchemeVersion version) {
        Assert.notNull((Object)version, "\u6307\u6807\u4f53\u7cfb\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)version.getSchemeKey(), "\u6307\u6807\u4f53\u7cfbKey\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)version.getStartPeriod(), "\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)version.getTitle(), "\u7248\u672c\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        List<ZbSchemeVersionDO> versionDOS = this.zbSchemeVersionDAO.listByScheme(version.getSchemeKey());
        ZbSchemeVersionDO last = versionDOS.get(versionDOS.size() - 1);
        if (version.getKey() == null) {
            version.setKey(UUID.randomUUID().toString());
            if (last.getStartPeriod().compareTo(version.getStartPeriod()) >= 0) {
                throw new ZbSchemeCheckException("\u7248\u672c\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u65e9\u4e8e\u6700\u540e\u4e00\u4e2a\u7248\u672c\u5f00\u59cb\u65f6\u95f4");
            }
            for (ZbSchemeVersionDO versionDO : versionDOS) {
                if (!versionDO.getTitle().equals(version.getTitle())) continue;
                throw new ZbSchemeCheckException("\u7248\u672c\u540d\u79f0\u91cd\u590d");
            }
        } else {
            if (versionDOS.size() > 1 && (last = versionDOS.get(versionDOS.size() - 2)).getStartPeriod().compareTo(version.getStartPeriod()) >= 0) {
                throw new ZbSchemeCheckException("\u7248\u672c\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u65e9\u4e8e\u6700\u540e\u4e00\u4e2a\u7248\u672c\u5f00\u59cb\u65f6\u95f4");
            }
            for (ZbSchemeVersionDO versionDO : versionDOS) {
                if (!versionDO.getTitle().equals(version.getTitle()) || versionDO.getKey().equals(version.getKey())) continue;
                throw new ZbSchemeCheckException("\u7248\u672c\u540d\u79f0\u91cd\u590d");
            }
        }
        if (version.getOrder() == null) {
            version.setOrder(OrderGenerator.newOrder());
        }
        if (version.getUpdateTime() == null) {
            version.setUpdateTime(Instant.now());
        }
    }
}

