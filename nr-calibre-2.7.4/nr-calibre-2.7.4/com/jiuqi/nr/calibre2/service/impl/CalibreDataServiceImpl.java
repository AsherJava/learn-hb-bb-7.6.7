/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.common.Utils;
import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.exception.CalibreDataUpdateException;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreDataDao;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreSubListDao;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
import com.jiuqi.nr.calibre2.internal.domain.BatchCalibreSubListDO;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
public class CalibreDataServiceImpl
implements ICalibreDataService {
    private static final Logger log = LoggerFactory.getLogger(CalibreDataServiceImpl.class);
    @Autowired
    private ICalibreSubListDao calibreSubListDao;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private ICalibreDataDao calibreDataDao;

    private boolean safeCheck(String keyWords) {
        if (Utils.sqlValidate(keyWords)) {
            throw new IllegalArgumentException("\u975e\u6cd5\u5b57\u7b26\uff1a" + keyWords);
        }
        return true;
    }

    @Override
    public Result<List<CalibreDataDTO>> list(CalibreDataDTO calibreDataDTO) {
        if (!StringUtils.hasText(calibreDataDTO.getCalibreCode()) && !StringUtils.hasText(calibreDataDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(calibreDataDTO.getCalibreCode())) {
            CalibreDefineDTO calibreDefine = this.getCalibreDefine(calibreDataDTO.getDefineKey());
            calibreDataDTO.setCalibreCode(calibreDefine.getCode());
        }
        List<Object> calibreDataDOS = new ArrayList();
        String keyWords = calibreDataDTO.getKeyWords();
        String key = calibreDataDTO.getKey();
        String code = calibreDataDTO.getCode();
        List<String> codes = calibreDataDTO.getCodes();
        boolean desc = calibreDataDTO.isDesc();
        CalibreDataOption.DataTreeType dataTreeType = calibreDataDTO.getDataTreeType();
        calibreDataDOS = StringUtils.hasText(keyWords) && this.safeCheck(keyWords) ? this.calibreDataDao.fuzzyQuery(calibreDataDTO.getCalibreCode(), keyWords) : (dataTreeType != null ? (StringUtils.hasText(code) ? this.calibreDataDao.query(calibreDataDTO.getCalibreCode(), CalibreDataMapper.FIELD_PARENT, code) : this.calibreDataDao.queryRoot(calibreDataDTO.getCalibreCode())) : (StringUtils.hasText(key) ? this.calibreDataDao.query(calibreDataDTO.getCalibreCode(), CalibreDataMapper.FIELD_KEY, key) : (StringUtils.hasText(code) ? this.calibreDataDao.query(calibreDataDTO.getCalibreCode(), CalibreDataMapper.FIELD_CODE, code) : (!CollectionUtils.isEmpty(codes) ? this.calibreDataDao.batchQuery(calibreDataDTO.getCalibreCode(), CalibreDataMapper.FIELD_CODE, codes) : this.calibreDataDao.queryByCalibreCode(calibreDataDTO.getCalibreCode())))));
        calibreDataDOS.sort(Comparator.comparingLong(CalibreDataDO::getOrder));
        List<Object> query = new ArrayList();
        query = CalibreDataDTO.CalibreDataDOsToDTOsWithDefineKey(calibreDataDOS, calibreDataDTO.getDefineKey());
        return Result.success(query);
    }

    @Override
    public Result<CalibreDataDTO> get(CalibreDataDTO calibreDataDTO) {
        if (!StringUtils.hasText(calibreDataDTO.getCalibreCode()) && !StringUtils.hasText(calibreDataDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Assert.notNull((Object)calibreDataDTO.getCode(), "\u53e3\u5f84\u9879\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        List<Object> query = new ArrayList();
        if (StringUtils.hasText(calibreDataDTO.getCalibreCode())) {
            query = this.calibreDataDao.query(calibreDataDTO.getCalibreCode(), CalibreDataMapper.FIELD_CODE, calibreDataDTO.getCode());
        } else {
            CalibreDefineDTO calibreDefine = this.getCalibreDefine(calibreDataDTO.getDefineKey());
            query = this.calibreDataDao.query(calibreDefine.getCode(), CalibreDataMapper.FIELD_CODE, calibreDataDTO.getCode());
            calibreDataDTO.setCalibreCode(calibreDefine.getCode());
        }
        if (CollectionUtils.isEmpty(query)) {
            return Result.fail();
        }
        CalibreDataDTO calibreDataDTO1 = CalibreDataDTO.getInstance((CalibreDataDO)query.get(0));
        calibreDataDTO1.setDefineKey(calibreDataDTO.getDefineKey());
        return Result.success(calibreDataDTO1);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> add(CalibreDataDTO calibreDataDTO) {
        CalibreDefineDTO calibreDefine;
        Assert.notNull((Object)calibreDataDTO.getCalibreCode(), "\u53e3\u5f84\u9879\u53e3\u5f84\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)calibreDataDTO.getCode(), "\u53e3\u5f84\u9879\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)calibreDataDTO.getName(), "\u53e3\u5f84\u9879\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        calibreDataDTO.setCode(calibreDataDTO.getCode().trim());
        calibreDataDTO.setName(calibreDataDTO.getName().trim());
        UpdateResult result = new UpdateResult();
        String itemKey = calibreDataDTO.getKey();
        if (!StringUtils.hasText(itemKey)) {
            calibreDataDTO.setKey(UUIDUtils.getKey());
        }
        if (calibreDataDTO.getOrder() == 0L) {
            calibreDataDTO.setOrder(OrderGenerator.newOrderID());
        }
        if ((calibreDefine = this.getCalibreDefineByCode(calibreDataDTO.getCalibreCode())).getStructType() == 0) {
            int add = this.calibreDataDao.addWithoutParent(calibreDataDTO);
        } else {
            int add = this.calibreDataDao.add(calibreDataDTO);
        }
        if (calibreDefine.getType() == 0) {
            BatchCalibreSubListDO batch = new BatchCalibreSubListDO();
            batch.setCalibreDefine(calibreDefine.getCode());
            List<CalibreSubListDO> listInstance = CalibreSubListDO.getInstance(calibreDataDTO);
            if (!CollectionUtils.isEmpty(listInstance)) {
                batch.setCalibreSubListDOList(listInstance);
                this.calibreSubListDao.batchAdd(batch);
            }
        }
        result.setKey(calibreDataDTO.getCode());
        return Result.success(result);
    }

    private CalibreDefineDTO getCalibreDefine(String key) throws CalibreDataUpdateException {
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        calibreDefine.setKey(key);
        Result<CalibreDefineDTO> result = this.calibreDefineService.get(calibreDefine);
        if (result.getCode() == 0) {
            throw new CalibreDataUpdateException("\u672a\u80fd\u627e\u5230\u53e3\u5f84\u5b9a\u4e49");
        }
        calibreDefine = result.getData();
        return calibreDefine;
    }

    private CalibreDefineDTO getCalibreDefineByCode(String code) throws CalibreDataUpdateException {
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        calibreDefine.setCode(code);
        Result<CalibreDefineDTO> result = this.calibreDefineService.get(calibreDefine);
        if (result.getCode() == 0) {
            throw new CalibreDataUpdateException("\u672a\u80fd\u627e\u5230\u53e3\u5f84\u5b9a\u4e49");
        }
        calibreDefine = result.getData();
        return calibreDefine;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> delete(CalibreDataDTO calibreDataDTO) {
        Assert.notNull((Object)calibreDataDTO.getCalibreCode(), "\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<String> deleteKeys = new ArrayList<String>();
        deleteKeys.add(calibreDataDTO.getCode());
        Result<List<UpdateResult>> listResult = this.executeDelete(calibreDataDTO.getCalibreCode(), deleteKeys, false);
        return Result.success(listResult.getData().get(0));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<UpdateResult> update(CalibreDataDTO calibreDataDTO) {
        if (!StringUtils.hasText(calibreDataDTO.getCalibreCode()) && !StringUtils.hasText(calibreDataDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Assert.notNull((Object)calibreDataDTO.getCode(), "\u53e3\u5f84\u9879\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        calibreDataDTO.setCode(calibreDataDTO.getCode().trim());
        calibreDataDTO.setName(calibreDataDTO.getName().trim());
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        if (StringUtils.hasText(calibreDataDTO.getDefineKey())) {
            calibreDefine = this.getCalibreDefine(calibreDataDTO.getDefineKey());
        } else {
            calibreDefine = this.getCalibreDefineByCode(calibreDataDTO.getCalibreCode());
            calibreDataDTO.setDefineKey(calibreDefine.getKey());
        }
        int update = this.calibreDataDao.update(calibreDataDTO);
        if (calibreDefine.getType() == 0) {
            CalibreSubListDO calibreSubListDO = new CalibreSubListDO();
            calibreSubListDO.setCalibreCode(calibreDataDTO.getCalibreCode());
            calibreSubListDO.setCode(calibreDataDTO.getCode());
            int delete = this.calibreSubListDao.delete(calibreSubListDO);
            BatchCalibreSubListDO batchCalibreSubListDO = new BatchCalibreSubListDO();
            batchCalibreSubListDO.setCalibreDefine(calibreDataDTO.getCalibreCode());
            List<CalibreSubListDO> listInstance = CalibreSubListDO.getInstance(calibreDataDTO);
            if (!CollectionUtils.isEmpty(listInstance)) {
                batchCalibreSubListDO.setCalibreSubListDOList(listInstance);
                this.calibreSubListDao.batchAdd(batchCalibreSubListDO);
            }
        }
        UpdateResult result = new UpdateResult();
        result.setKey(calibreDataDTO.getKey());
        return Result.success(result);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> batchAdd(BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO) throws CalibreDataUpdateException {
        CalibreDefineDTO calibreDefine;
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode()) && !StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode())) {
            calibreDefine = this.getCalibreDefine(batchCalibreDataOptionsDTO.getDefineKey());
            batchCalibreDataOptionsDTO.setDefineCode(calibreDefine.getCode());
        } else {
            calibreDefine = this.getCalibreDefineByCode(batchCalibreDataOptionsDTO.getDefineCode());
        }
        String calibreDefineCode = calibreDefine.getCode();
        ArrayList<UpdateResult> results = new ArrayList<UpdateResult>();
        List<CalibreDataDTO> calibreDataDTOS = batchCalibreDataOptionsDTO.getCalibreDataDTOS();
        for (CalibreDataDTO calibreDataDTO : calibreDataDTOS) {
            String key = UUIDUtils.getKey();
            calibreDataDTO.setKey(key);
            calibreDataDTO.setCode(calibreDataDTO.getCode().trim());
            calibreDataDTO.setName(calibreDataDTO.getName().trim());
            calibreDataDTO.setOrder(OrderGenerator.newOrderID());
            calibreDataDTO.setCalibreCode(calibreDefineCode);
            UpdateResult result = new UpdateResult();
            result.setKey(key);
            result.setCode(calibreDataDTO.getCode());
            results.add(result);
        }
        List<CalibreDataDO> calibreDataDOS = CalibreDataDTO.calibreDataDTOssToDOs(calibreDataDTOS);
        int[] ints = calibreDefine.getStructType() == 0 ? this.calibreDataDao.batchAddWithoutParent(calibreDataDOS) : this.calibreDataDao.batchAdd(calibreDataDOS);
        if (calibreDefine.getType() == 0) {
            BatchCalibreSubListDO batch = new BatchCalibreSubListDO();
            batch.setCalibreDefine(calibreDefine.getCode());
            ArrayList<CalibreSubListDO> calibreSubListDOS = new ArrayList<CalibreSubListDO>();
            int count = 0;
            for (CalibreDataDTO calibreDataDTO : batchCalibreDataOptionsDTO.getCalibreDataDTOS()) {
                List<CalibreSubListDO> listInstance = CalibreSubListDO.getInstance(calibreDataDTO);
                calibreSubListDOS.addAll(listInstance);
                count += listInstance.size();
            }
            if (count > 0) {
                batch.setCalibreSubListDOList(calibreSubListDOS);
                this.calibreSubListDao.batchAdd(batch);
            }
        }
        return Result.success(results);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> batchUpdate(BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO) {
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode()) && !StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode())) {
            calibreDefine = this.getCalibreDefine(batchCalibreDataOptionsDTO.getDefineKey());
            batchCalibreDataOptionsDTO.setDefineCode(calibreDefine.getCode());
        } else {
            calibreDefine = this.getCalibreDefineByCode(batchCalibreDataOptionsDTO.getDefineCode());
        }
        String calibreDefineCode = calibreDefine.getCode();
        List<CalibreDataDTO> calibreDataDTOS = batchCalibreDataOptionsDTO.getCalibreDataDTOS();
        calibreDataDTOS.stream().forEach(e -> e.setCalibreCode(calibreDefineCode));
        List<CalibreDataDO> calibreDataDOS = CalibreDataDTO.calibreDataDTOssToDOs(calibreDataDTOS);
        int[] ints = this.calibreDataDao.batchUpdate(calibreDataDOS);
        if (calibreDefine.getType() == 0) {
            BatchCalibreSubListDO batch = new BatchCalibreSubListDO();
            batch.setCalibreDefine(calibreDefine.getCode());
            ArrayList<CalibreSubListDO> calibreSubListDOS = new ArrayList<CalibreSubListDO>();
            for (CalibreDataDTO calibreDataDTO : batchCalibreDataOptionsDTO.getCalibreDataDTOS()) {
                List<CalibreSubListDO> listInstance = CalibreSubListDO.getInstance(calibreDataDTO);
                calibreSubListDOS.addAll(listInstance);
            }
            batch.setCalibreSubListDOList(calibreSubListDOS);
            this.calibreSubListDao.batchDelete(batch);
            this.calibreSubListDao.batchAdd(batch);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Result<List<UpdateResult>> batchDelete(BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO, Boolean isDeleteCalibreDefine) {
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode()) && !StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineKey())) {
            throw new IllegalArgumentException("\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        CalibreDefineDTO calibreDefine = new CalibreDefineDTO();
        if (!StringUtils.hasText(batchCalibreDataOptionsDTO.getDefineCode())) {
            calibreDefine = this.getCalibreDefine(batchCalibreDataOptionsDTO.getDefineKey());
            batchCalibreDataOptionsDTO.setDefineCode(calibreDefine.getCode());
        }
        List<String> deletecodes = batchCalibreDataOptionsDTO.getCalibreDataDTOS().stream().map(CalibreDataDO::getCode).collect(Collectors.toList());
        return this.executeDelete(batchCalibreDataOptionsDTO.getDefineCode(), deletecodes, isDeleteCalibreDefine);
    }

    @Override
    public Result<List<UpdateResult>> batchChangeOrder(BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO) {
        List<CalibreDataDTO> calibreDataDTOS = batchCalibreDataOptionsDTO.getCalibreDataDTOS();
        List<CalibreDataDO> calibreDataDOS = CalibreDataDTO.calibreDataDTOssToDOs(calibreDataDTOS);
        int[] ints = this.calibreDataDao.batchChangeOrder(calibreDataDOS, CalibreDataMapper.FIELD_ORDER);
        return Result.success();
    }

    @Transactional(rollbackFor={Exception.class})
    Result<List<UpdateResult>> executeDelete(String defineCode, List<String> codes, Boolean isDeleteCalibreDefine) throws CalibreDataUpdateException {
        if (CollectionUtils.isEmpty(codes)) {
            return Result.success();
        }
        CalibreDefineDTO calibreDefine = this.getCalibreDefineByCode(defineCode);
        if (calibreDefine.getStructType() == 1 && !isDeleteCalibreDefine.booleanValue()) {
            try {
                this.searchNeedChangeParentData(defineCode, codes);
            }
            catch (Exception e2) {
                log.error(e2.getMessage(), e2);
                throw new CalibreDataUpdateException("\u4fee\u6539\u5b50\u8282\u70b9\u4e0a\u7ea7\u9519\u8bef", e2);
            }
        }
        if (isDeleteCalibreDefine.booleanValue()) {
            int e2 = this.calibreDataDao.deleteAll(calibreDefine.getCode());
        } else {
            this.calibreDataDao.batchDelete(calibreDefine.getCode(), CalibreDataMapper.FIELD_CODE, codes);
        }
        if (calibreDefine.getType() == 0) {
            if (isDeleteCalibreDefine.booleanValue()) {
                CalibreSubListDO calibreSublistDTO = new CalibreSubListDO();
                calibreSublistDTO.setCalibreCode(calibreDefine.getCode());
                int n = this.calibreSubListDao.deleteAll(calibreSublistDTO);
            } else {
                BatchCalibreSubListDO batchCalibreSubListDO = new BatchCalibreSubListDO();
                batchCalibreSubListDO.setCalibreDefine(calibreDefine.getCode());
                for (String code : codes) {
                    CalibreSubListDO calibreSublistDTO = new CalibreSubListDO();
                    calibreSublistDTO.setCalibreCode(calibreDefine.getCode());
                    calibreSublistDTO.setCode(code);
                    batchCalibreSubListDO.addCalibreSubListDO(calibreSublistDTO);
                }
                this.calibreSubListDao.batchDelete(batchCalibreSubListDO);
            }
        }
        List result = codes.stream().map(e -> {
            UpdateResult updateResult = new UpdateResult();
            updateResult.setCode((String)e);
            return updateResult;
        }).collect(Collectors.toList());
        return Result.success(result);
    }

    private Result<List<UpdateResult>> searchNeedChangeParentData(String defineCode, List<String> deleteCodes) throws CalibreDataUpdateException {
        if (CollectionUtils.isEmpty(deleteCodes)) {
            return Result.success();
        }
        CalibreDataDTO calibreDataDto = new CalibreDataDTO();
        calibreDataDto.setCalibreCode(defineCode);
        Result<List<CalibreDataDTO>> allCalibreList = this.list(calibreDataDto);
        HashMap<String, String> codeToParentMap = new HashMap<String, String>();
        allCalibreList.getData().forEach(e -> codeToParentMap.put(e.getCode(), e.getParent()));
        ArrayList<CalibreDataDTO> childNeedChangeParents = new ArrayList<CalibreDataDTO>();
        for (String deleteCode : deleteCodes) {
            String parentCode = (String)codeToParentMap.get(deleteCode);
            while (deleteCodes.contains(parentCode) && StringUtils.hasText(parentCode)) {
                parentCode = (String)codeToParentMap.get(parentCode);
            }
            CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
            calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
            calibreDataDTO.setCode(deleteCode);
            calibreDataDTO.setCalibreCode(defineCode);
            List<CalibreDataDTO> childs = this.list(calibreDataDTO).getData();
            for (CalibreDataDTO child : childs) {
                String childCode = child.getCode();
                codeToParentMap.put(childCode, parentCode);
                if (deleteCodes.contains(childCode)) continue;
                child.setParent(parentCode);
                childNeedChangeParents.add(child);
            }
        }
        BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO = new BatchCalibreDataOptionsDTO();
        batchCalibreDataOptionsDTO.setDefineCode(defineCode);
        batchCalibreDataOptionsDTO.setCalibreDataDTOS(childNeedChangeParents);
        Result<List<UpdateResult>> listResult = this.batchChangeParent(batchCalibreDataOptionsDTO);
        return listResult;
    }

    private Result<List<UpdateResult>> batchChangeParent(BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO) throws CalibreDataUpdateException {
        List<CalibreDataDTO> calibreDataDTOS = batchCalibreDataOptionsDTO.getCalibreDataDTOS();
        List<CalibreDataDO> calibreDataDOS = CalibreDataDTO.calibreDataDTOssToDOs(calibreDataDTOS);
        int[] ints = this.calibreDataDao.batchChangeOrder(calibreDataDOS, CalibreDataMapper.FIELD_PARENT);
        return Result.success();
    }

    @Override
    public Result<List<CalibreDataDO>> listAll(String key) {
        Assert.notNull((Object)key, "\u53e3\u5f84\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        List<CalibreDataDO> calibreDataDOS = this.calibreDataDao.queryByCalibreCode(key);
        if (CollectionUtils.isEmpty(calibreDataDOS)) {
            CalibreDefineDTO calibreDefine = this.getCalibreDefine(key);
            calibreDataDOS = this.calibreDataDao.queryByCalibreCode(calibreDefine.getCode());
        }
        return Result.success(calibreDataDOS);
    }
}

