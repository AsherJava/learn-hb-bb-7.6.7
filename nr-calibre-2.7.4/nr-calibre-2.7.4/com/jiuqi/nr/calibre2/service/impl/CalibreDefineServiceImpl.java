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
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import com.jiuqi.nr.calibre2.exception.CalibreDefineServiceException;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreDefineDao;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreGroupDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CalibreDefineServiceImpl
implements ICalibreDefineService {
    private static final Logger log = LoggerFactory.getLogger(CalibreDefineServiceImpl.class);
    @Autowired
    private ICalibreDefineDao calibreDefineDao;
    @Autowired
    private ICalibreGroupDao iCalibreGroupDao;

    @Override
    public Result<List<CalibreDefineDTO>> list(CalibreDefineDTO defineDTO) {
        if (StringUtils.hasText(defineDTO.getKeyWords())) {
            List<CalibreDefineDO> calibreDefineDTOS = StringUtils.hasText(defineDTO.getGroup()) && !"00000000-0000-0000-0000-000000000000".equals(defineDTO.getGroup()) ? this.calibreDefineDao.searchByNameOrCode(defineDTO.getKeyWords(), defineDTO.getGroup()) : this.calibreDefineDao.searchByNameOrCode(defineDTO.getKeyWords());
            calibreDefineDTOS.sort(Comparator.comparing(CalibreDefineDO::getOrder));
            return Result.success(this.DOToDTOS(calibreDefineDTOS), "\u83b7\u5f97\u6a21\u7cca\u5339\u914d\u53e3\u5f84");
        }
        if (StringUtils.hasText(defineDTO.getGroup())) {
            List<CalibreDefineDO> calibreDefineDTOS = this.calibreDefineDao.queryByGroup(defineDTO.getGroup());
            calibreDefineDTOS.sort(Comparator.comparing(CalibreDefineDO::getOrder));
            return Result.success(this.DOToDTOS(calibreDefineDTOS), "\u83b7\u5f97\u8be5\u5206\u7ec4\u4e0b\u7684\u6240\u6709\u76f4\u63a5\u53e3\u5f84");
        }
        List<CalibreDefineDO> calibreDefineDTOS = this.calibreDefineDao.query();
        this.sortAllCalibre(calibreDefineDTOS);
        return Result.success(this.DOToDTOS(calibreDefineDTOS), "\u83b7\u5f97\u6240\u6709\u53e3\u5f84");
    }

    private void sortAllCalibre(List<CalibreDefineDO> allCalibreDefineDtoS) {
        List<CalibreGroupDO> groupDOList = this.iCalibreGroupDao.query();
        groupDOList.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
        List groupKeyByOrder = groupDOList.stream().map(CalibreGroupDO::getKey).collect(Collectors.toList());
        groupKeyByOrder.add("00000000-0000-0000-0000-000000000000");
        Collections.sort(allCalibreDefineDtoS, (o1, o2) -> {
            int io2;
            int io1 = groupKeyByOrder.indexOf(o1.getGroup());
            if (io1 == (io2 = groupKeyByOrder.indexOf(o2.getGroup()))) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
            return io1 - io2;
        });
    }

    @Override
    public Result<CalibreDefineDTO> get(CalibreDefineDTO defineDTO) {
        CalibreDefineDO CalibreDefineDO2 = null;
        if (StringUtils.hasText(defineDTO.getKey())) {
            CalibreDefineDO2 = this.calibreDefineDao.get(defineDTO.getKey());
        } else if (StringUtils.hasText(defineDTO.getCode())) {
            CalibreDefineDO2 = this.calibreDefineDao.getByCode(defineDTO.getCode());
        }
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        if (CalibreDefineDO2 == null) {
            return Result.success(calibreDefineDTO, "\u53e3\u5f84\u672a\u67e5\u8be2\u5230");
        }
        calibreDefineDTO = this.DOToDTO(CalibreDefineDO2);
        return Result.success(calibreDefineDTO, "\u67e5\u8be2\u53e3\u5f84\u6210\u529f");
    }

    @Override
    public Result<List<CalibreDefineDTO>> getByRefer(CalibreDefineDTO defineDTO) {
        Assert.notNull((Object)defineDTO.getEntityId(), "\u53e3\u5f84\u5173\u8054\u5b9e\u4f53\u975e\u6cd5");
        List<CalibreDefineDO> calibreDefineDOS = this.calibreDefineDao.queryByRefer(defineDTO.getEntityId());
        if (calibreDefineDOS.size() == 0) {
            return Result.success(Collections.emptyList(), "\u53e3\u5f84\u672a\u67e5\u8be2\u5230");
        }
        return Result.success(this.DOToDTOS(calibreDefineDOS), "\u6839\u636e\u5173\u8054\u5b9e\u4f53\u67e5\u8be2\u53e3\u5f84\u6210\u529f");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> add(CalibreDefineDTO defineDTO) throws CalibreDefineServiceException {
        defineDTO.setKey(UUIDUtils.getKey());
        defineDTO.setOrder(OrderGenerator.newOrder());
        defineDTO.setCode(defineDTO.getCode().trim());
        defineDTO.setName(defineDTO.getName().trim());
        UpdateResult updateResult = new UpdateResult();
        if (!this.checkCalibre(defineDTO).booleanValue()) {
            throw new CalibreDefineServiceException("\u53e3\u5f84\u5b58\u5728\u5c5e\u6027\u4e3anull");
        }
        CalibreDefineDO calibreDefineDO = this.calibreDefineDao.getByCode(defineDTO.getCode());
        if (calibreDefineDO != null) {
            throw new CalibreDefineServiceException("\u53e3\u5f84\u4ee3\u7801" + defineDTO.getCode() + "\u91cd\u590d");
        }
        updateResult.setKey(defineDTO.getKey());
        updateResult.setCode(defineDTO.getCode());
        int result = this.calibreDefineDao.insert(defineDTO);
        if (result == 0) {
            throw new CalibreDefineServiceException("\u6dfb\u52a0\u53e3\u5f84\u5931\u8d25");
        }
        return Result.success(updateResult, "\u6dfb\u52a0\u53e3\u5f84\u6210\u529f");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> delete(String key) throws CalibreDefineServiceException {
        Assert.notNull((Object)key, "\u53e3\u5f84key\u975e\u6cd5");
        CalibreDefineDO calibreDefineDO = this.calibreDefineDao.get(key);
        if (calibreDefineDO == null) {
            throw new CalibreDefineServiceException("\u8981\u5220\u9664\u7684\u53e3\u5f84\u4e0d\u5b58\u5728");
        }
        int result = this.calibreDefineDao.delete(key);
        if (result != 1) {
            throw new CalibreDefineServiceException("\u5220\u9664\u53e3\u5f84\u5931\u8d25");
        }
        UpdateResult updateResult = new UpdateResult();
        updateResult.setKey(key);
        return Result.success(updateResult, "\u5220\u9664\u53e3\u5f84\u6210\u529f");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> update(CalibreDefineDTO defineDTO) throws CalibreDefineServiceException {
        defineDTO.setCode(defineDTO.getCode().trim());
        defineDTO.setName(defineDTO.getName().trim());
        if (!this.checkCalibre(defineDTO).booleanValue()) {
            throw new CalibreDefineServiceException("\u53e3\u5f84\u5b58\u5728\u5c5e\u6027\u4e3anull");
        }
        UpdateResult updateResult = new UpdateResult();
        updateResult.setKey(defineDTO.getKey());
        updateResult.setCode(defineDTO.getCode());
        int result = this.calibreDefineDao.update(defineDTO);
        if (result != 1) {
            throw new CalibreDefineServiceException("\u66f4\u65b0\u53e3\u5f84\u5931\u8d25");
        }
        return Result.success(updateResult, "\u66f4\u65b0\u53e3\u5f84\u6210\u529f");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> batchDelete(List<String> keys) throws CalibreDefineServiceException {
        ArrayList<UpdateResult> updateResultList = new ArrayList<UpdateResult>();
        for (String key : keys) {
            Result<UpdateResult> result = this.delete(key);
            updateResultList.add(result.getData());
        }
        return Result.success(updateResultList, "\u5220\u9664\u53e3\u5f84\u6210\u529f");
    }

    public CalibreDefineDTO DOToDTO(CalibreDefineDO calibreDefineDO) {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setKey(calibreDefineDO.getKey());
        calibreDefineDTO.setCode(calibreDefineDO.getCode());
        calibreDefineDTO.setName(calibreDefineDO.getName());
        calibreDefineDTO.setGroup(calibreDefineDO.getGroup());
        calibreDefineDTO.setType(calibreDefineDO.getType());
        calibreDefineDTO.setStructType(calibreDefineDO.getStructType());
        calibreDefineDTO.setExpression_Values(calibreDefineDO.getExpression_Values());
        calibreDefineDTO.setEntityId(calibreDefineDO.getEntityId());
        calibreDefineDTO.setOrder(calibreDefineDO.getOrder());
        return calibreDefineDTO;
    }

    public List<CalibreDefineDTO> DOToDTOS(List<CalibreDefineDO> calibreDefineDOList) {
        ArrayList<CalibreDefineDTO> calibreDefineDTOList = new ArrayList<CalibreDefineDTO>();
        if (!CollectionUtils.isEmpty(calibreDefineDOList)) {
            for (CalibreDefineDO calibreDefineDO : calibreDefineDOList) {
                calibreDefineDTOList.add(this.DOToDTO(calibreDefineDO));
            }
        }
        return calibreDefineDTOList;
    }

    public Boolean checkCalibre(CalibreDefineDTO calibreDefineDTO) {
        if (calibreDefineDTO.getKey() == null || !StringUtils.hasText(calibreDefineDTO.getCode()) || !StringUtils.hasText(calibreDefineDTO.getName()) || calibreDefineDTO.getGroup() == null || calibreDefineDTO.getType() == null || calibreDefineDTO.getStructType() == null || calibreDefineDTO.getOrder() == null) {
            return false;
        }
        if (calibreDefineDTO.getType() == 1 || calibreDefineDTO.getType() == 2) {
            return calibreDefineDTO.getExpression_Values() != null;
        }
        return true;
    }
}

