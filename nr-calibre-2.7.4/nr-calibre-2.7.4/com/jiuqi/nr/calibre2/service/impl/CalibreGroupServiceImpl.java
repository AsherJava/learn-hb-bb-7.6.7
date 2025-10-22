/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.ICalibreGroupService;
import com.jiuqi.nr.calibre2.common.CalibreGroupOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.exception.CalibreGroupServiceException;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreGroupDao;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CalibreGroupServiceImpl
implements ICalibreGroupService {
    public static final String CALIBRE_GROUP_ROOT_NAME = "\u5168\u90e8\u53e3\u5f84";
    public static final String CALIBRE_GROUP_ROOT_KEY = "00000000-0000-0000-0000-000000000000";
    @Autowired
    private ICalibreGroupDao iCalibreGroupDao;
    @Autowired
    ICalibreDefineService calibreDefineService;
    private static final Logger log = LoggerFactory.getLogger(CalibreGroupServiceImpl.class);

    @Override
    public Result<List<CalibreGroupDTO>> list(CalibreGroupDTO groupDTO) {
        CalibreGroupOption.GroupTreeType groupTreeType = groupDTO.getGroupTreeType();
        if (groupTreeType != null) {
            if (groupTreeType == CalibreGroupOption.GroupTreeType.ROOT) {
                List<CalibreGroupDO> rootCalibreGroupDOS = this.iCalibreGroupDao.queryByParent(CALIBRE_GROUP_ROOT_KEY);
                rootCalibreGroupDOS.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
                return Result.success(CalibreGroupDTO.DOToDTO(rootCalibreGroupDOS), "\u83b7\u5f97\u771f\u5b9e\u6839\u8282\u70b9");
            }
            Assert.notNull((Object)groupDTO.getKey(), "\u67e5\u8be2\u8282\u70b9\u4e0b\u7ea7\u65f6\u5206\u7ec4\u4fe1\u606f\u4e3anull");
            List<CalibreGroupDO> rootCalibreGroupDOS = this.iCalibreGroupDao.queryByParent(groupDTO.getKey());
            rootCalibreGroupDOS.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
            return Result.success(CalibreGroupDTO.DOToDTO(rootCalibreGroupDOS), "\u83b7\u5f97\u8be5\u8282\u70b9\u7684\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9");
        }
        if (StringUtils.hasText(groupDTO.getKeyWords())) {
            List<CalibreGroupDO> calibreGroupDOS = this.iCalibreGroupDao.queryByName(groupDTO.getKeyWords());
            calibreGroupDOS.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
            return Result.success(CalibreGroupDTO.DOToDTO(calibreGroupDOS), "\u83b7\u5f97\u6a21\u7cca\u5339\u914d\u5206\u7ec4");
        }
        if (StringUtils.hasText(groupDTO.getKey())) {
            CalibreGroupDO calibreGroupDO = this.iCalibreGroupDao.get(groupDTO.getKey());
            ArrayList<CalibreGroupDO> calibreGroupDOList = new ArrayList<CalibreGroupDO>();
            calibreGroupDOList.add(calibreGroupDO);
            return Result.success(CalibreGroupDTO.DOToDTO(calibreGroupDOList), "\u83b7\u5f97\u8be5\u5206\u7ec4");
        }
        return Result.success(CalibreGroupDTO.DOToDTO(this.iCalibreGroupDao.query()), "\u83b7\u5f97\u5168\u90e8\u5206\u7ec4");
    }

    @Override
    public Result<CalibreGroupDTO> get(String key) {
        Assert.notNull((Object)key, "\u83b7\u53d6\u5206\u7ec4\u65f6\u5019\u5206\u7ec4\u5173\u952e\u5b57\u4e3anull");
        if (key.equals(CALIBRE_GROUP_ROOT_KEY)) {
            return Result.success(this.getRootCalibreGroup(), "\u67e5\u8be2\u7684\u662f\u201c\u5168\u90e8\u53e3\u5f84\u201d ");
        }
        CalibreGroupDO calibreGroupDO = this.iCalibreGroupDao.get(key);
        CalibreGroupDTO calibreGroupDTO = new CalibreGroupDTO();
        if (calibreGroupDO == null) {
            return Result.success(calibreGroupDTO, "\u5206\u7ec4\u672a\u67e5\u8be2\u5230");
        }
        calibreGroupDTO.setKey(calibreGroupDO.getKey());
        calibreGroupDTO.setName(calibreGroupDO.getName());
        calibreGroupDTO.setParent(calibreGroupDO.getParent());
        calibreGroupDTO.setOrder(calibreGroupDO.getOrder());
        return Result.success(calibreGroupDTO, "\u67e5\u8be2\u5206\u7ec4\u6210\u529f");
    }

    @Override
    public Result<UpdateResult> add(CalibreGroupDTO groupDTO) throws CalibreGroupServiceException {
        groupDTO.setKey(UUIDUtils.getKey());
        groupDTO.setOrder(OrderGenerator.newOrder());
        if (!StringUtils.hasText(groupDTO.getName())) {
            throw new CalibreGroupServiceException("\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u5168\u7a7a\u683c");
        }
        groupDTO.setName(groupDTO.getName().trim());
        UpdateResult updateResult = new UpdateResult();
        updateResult.setKey(groupDTO.getKey());
        updateResult.setCode(groupDTO.getName());
        int result = this.iCalibreGroupDao.insert(groupDTO);
        if (result == 1) {
            return Result.success(updateResult, "\u6dfb\u52a0\u5206\u7ec4\u6210\u529f");
        }
        throw new CalibreGroupServiceException("\u6dfb\u52a0\u5206\u7ec4\u5931\u8d25");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> delete(CalibreGroupDTO groupDTO) throws CalibreGroupServiceException {
        Assert.notNull((Object)groupDTO.getKey(), "\u5220\u9664\u5206\u7ec4\u65f6\u5019\u5206\u7ec4\u5173\u952e\u5b57\u4e3anull");
        if (CALIBRE_GROUP_ROOT_KEY.equals(groupDTO.getKey())) {
            throw new CalibreGroupServiceException("\u6839\u5206\u7ec4\u201c\u5168\u90e8\u53e3\u5f84\u201d\u65e0\u6cd5\u5220\u9664\uff01");
        }
        CalibreGroupDO srcCalibreGroupDTO = this.iCalibreGroupDao.get(groupDTO.getKey());
        String parentId = srcCalibreGroupDTO.getParent();
        List<CalibreGroupDO> calibreGroupDOS = this.iCalibreGroupDao.queryByParent(groupDTO.getKey());
        if (!CollectionUtils.isEmpty(calibreGroupDOS)) {
            for (CalibreGroupDO calibreGroupDO : calibreGroupDOS) {
                calibreGroupDO.setParent(parentId);
                this.iCalibreGroupDao.update(calibreGroupDO);
            }
        }
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setGroup(groupDTO.getKey());
        List<CalibreDefineDTO> calibreList = this.calibreDefineService.list(calibreDefineDTO).getData();
        this.handleCalibreDefineForGroup(calibreList, groupDTO);
        int result = this.iCalibreGroupDao.delete(groupDTO.getKey());
        if (result != 1) {
            throw new CalibreGroupServiceException("\u5220\u9664\u5206\u7ec4\u5931\u8d25");
        }
        UpdateResult updateResult = new UpdateResult();
        updateResult.setKey(groupDTO.getKey());
        return Result.success(updateResult, "\u5220\u9664\u5206\u7ec4\u6210\u529f");
    }

    @Override
    public Result<UpdateResult> update(CalibreGroupDTO groupDTO) throws CalibreGroupServiceException {
        if (!StringUtils.hasText(groupDTO.getName())) {
            throw new CalibreGroupServiceException("\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u5168\u7a7a\u683c");
        }
        if (groupDTO.getKey().equals(groupDTO.getParent())) {
            throw new CalibreGroupServiceException("\u5206\u7ec4\u4e0a\u7ea7\u4e0d\u80fd\u8bbe\u7f6e\u4e3a\u81ea\u5df1");
        }
        groupDTO.setName(groupDTO.getName().trim());
        int result = this.iCalibreGroupDao.update(groupDTO);
        if (result != 1) {
            throw new CalibreGroupServiceException("\u66f4\u65b0\u5206\u7ec4\u5931\u8d25");
        }
        UpdateResult updateResult = new UpdateResult();
        updateResult.setKey(groupDTO.getKey());
        updateResult.setCode(groupDTO.getName());
        return Result.success(updateResult, "\u5220\u9664\u5206\u7ec4\u6210\u529f");
    }

    private void handleCalibreDefineForGroup(List<CalibreDefineDTO> calibreList, CalibreGroupDTO groupDTO) throws CalibreGroupServiceException {
        if (!CollectionUtils.isEmpty(calibreList)) {
            try {
                for (CalibreDefineDTO calibreGroupDTO : calibreList) {
                    calibreGroupDTO.setGroup(CALIBRE_GROUP_ROOT_KEY);
                    this.calibreDefineService.update(calibreGroupDTO);
                }
            }
            catch (Exception e) {
                throw new CalibreGroupServiceException(e.getMessage(), e);
            }
        }
    }

    public CalibreGroupDTO getRootCalibreGroup() {
        CalibreGroupDTO rootCalibreGroupDTO = new CalibreGroupDTO();
        rootCalibreGroupDTO.setName(CALIBRE_GROUP_ROOT_NAME);
        rootCalibreGroupDTO.setKey(CALIBRE_GROUP_ROOT_KEY);
        rootCalibreGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        rootCalibreGroupDTO.setParent(null);
        rootCalibreGroupDTO.setOrder(null);
        return rootCalibreGroupDTO;
    }
}

