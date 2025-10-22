/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.carryover.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverConfigDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.carryover.utils.CarryOverConfigUtil;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigVO;
import com.jiuqi.gcreport.carryover.vo.CarryOverTypeVO;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcCarryOverConfigServiceImpl
implements GcCarryOverConfigService {
    private static final Logger logger = LoggerFactory.getLogger(GcCarryOverConfigServiceImpl.class);
    @Autowired
    private CarryOverConfigDao carryOverConfigDao;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveConfig(CarryOverConfigVO configVO) {
        if (!StringUtils.isEmpty((String)configVO.getId())) {
            CarryOverConfigEO updateEO = (CarryOverConfigEO)this.carryOverConfigDao.get((Serializable)((Object)configVO.getId()));
            updateEO.setTitle(configVO.getTitle());
            this.carryOverConfigDao.updateSelective((BaseEntity)updateEO);
            return configVO.getId();
        }
        CarryOverConfigEO carryOverConfigEO = new CarryOverConfigEO();
        carryOverConfigEO.setTypeCode(configVO.getTypeCode());
        List configEOS = this.carryOverConfigDao.selectList((BaseEntity)carryOverConfigEO);
        CarryOverTypeEnum carryOverType = CarryOverTypeEnum.getEnumByCode((String)configVO.getTypeCode());
        if (!configEOS.isEmpty()) {
            if (!CarryOverTypeEnum.OFFSET.equals((Object)carryOverType)) {
                throw new BusinessRuntimeException("\u8be5\u7c7b\u578b\u7684\u5e74\u7ed3\u65b9\u6848\u5df2\u5b58\u5728\uff0c\u65e0\u6cd5\u4fdd\u5b58\uff01");
            }
            List boundSystemIds = configEOS.stream().map(eo -> {
                CarryOverConfigOptionBaseVO optionVO = CarryOverConfigUtil.convertConfigEO2OptionVO(eo);
                return optionVO.getBoundSystemId();
            }).collect(Collectors.toList());
            if (boundSystemIds.contains(configVO.getBoundSystemId())) {
                throw new BusinessRuntimeException("\u8be5\u5408\u5e76\u4f53\u7cfb\u4e0b\u7684\u62b5\u9500\u5206\u5f55\u5e74\u7ed3\u65b9\u6848\u5df2\u5b58\u5728\uff0c\u65e0\u6cd5\u4fdd\u5b58\uff01");
            }
        }
        CarryOverConfigUtil.initConfigVO(configVO);
        CarryOverConfigEO configEO = CarryOverConfigUtil.convertConfigV02EO(configVO);
        CarryOverConfigOptionBaseVO optionVO = new CarryOverConfigOptionBaseVO();
        if (!StringUtils.isEmpty((String)configVO.getBoundSystemId())) {
            optionVO.setBoundSystemId(configVO.getBoundSystemId());
        }
        optionVO.setTitle(configVO.getTitle());
        optionVO.setTypeCode(configVO.getTypeCode());
        configEO.setOptionData(JsonUtils.writeValueAsString((Object)optionVO));
        return ConverterUtils.getAsString((Object)this.carryOverConfigDao.save(configEO));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteConfigById(String id) {
        CarryOverConfigEO carryOverConfigEO = new CarryOverConfigEO();
        carryOverConfigEO.setId(id);
        int num = this.carryOverConfigDao.delete((BaseEntity)carryOverConfigEO);
        if (num > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String updateConfig(CarryOverConfigVO configVO) {
        CarryOverConfigEO eo = new CarryOverConfigEO();
        eo.setId(configVO.getId());
        CarryOverConfigEO configEO = (CarryOverConfigEO)this.carryOverConfigDao.selectByEntity((BaseEntity)eo);
        if (ObjectUtils.isEmpty((Object)configEO)) {
            throw new BusinessRuntimeException("\u8be5\u5e74\u7ed3\u65b9\u6848\u4e0d\u5b58\u5728\u3002");
        }
        configEO.setTitle(configVO.getTitle());
        configEO.setUpdateTime(new Date());
        configEO.setOptionData(configVO.getOptionData());
        this.carryOverConfigDao.updateSelective((BaseEntity)configEO);
        return configEO.getId();
    }

    @Override
    public List<CarryOverConfigVO> listCarryOverConfig() {
        List<CarryOverConfigEO> listAllConfigEO = this.carryOverConfigDao.listAllConfigEO();
        ArrayList<CarryOverConfigVO> configVOS = new ArrayList<CarryOverConfigVO>();
        for (CarryOverConfigEO configEO : listAllConfigEO) {
            CarryOverConfigVO optionVO = CarryOverConfigUtil.convertConfigEO2VO(configEO);
            if (!StringUtils.isEmpty((String)optionVO.getBoundSystemId())) {
                ConsolidatedSystemEO systemEO = this.consolidatedSystemService.getConsolidatedSystemEO(optionVO.getBoundSystemId());
                optionVO.setBoundSystemTitle(systemEO.getSystemName());
            }
            configVOS.add(optionVO);
        }
        return configVOS;
    }

    @Override
    public List<CarryOverConfigEO> listAll() {
        return this.carryOverConfigDao.loadAll();
    }

    @Override
    public List<CarryOverTypeVO> listCarryOverType() {
        ArrayList<CarryOverTypeVO> list = new ArrayList<CarryOverTypeVO>();
        for (CarryOverTypeEnum value : CarryOverTypeEnum.values()) {
            CarryOverTypeVO vo = new CarryOverTypeVO();
            vo.setCode(value.getCode());
            vo.setTitle(value.getTitle());
            list.add(vo);
        }
        return list;
    }

    @Override
    public String getConfigOptionById(String id) {
        CarryOverConfigEO eo = new CarryOverConfigEO();
        eo.setId(id);
        CarryOverConfigEO configEO = (CarryOverConfigEO)this.carryOverConfigDao.selectByEntity((BaseEntity)eo);
        if (ObjectUtils.isEmpty((Object)configEO)) {
            throw new BusinessRuntimeException("\u8be5\u5e74\u7ed3\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
        }
        return configEO.getOptionData();
    }

    @Override
    public CarryOverConfigEO getCarryOverConfigById(String id) {
        CarryOverConfigEO eo = new CarryOverConfigEO();
        eo.setId(id);
        CarryOverConfigEO configEO = (CarryOverConfigEO)this.carryOverConfigDao.selectByEntity((BaseEntity)eo);
        if (ObjectUtils.isEmpty((Object)configEO)) {
            throw new BusinessRuntimeException("\u8be5\u5e74\u7ed3\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
        }
        return configEO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean exchangeSortConfig(String currId, String exchangeId) {
        if (currId.equals(exchangeId)) {
            return Boolean.TRUE;
        }
        CarryOverConfigEO currConfigEO = (CarryOverConfigEO)this.carryOverConfigDao.get((Serializable)((Object)currId));
        CarryOverConfigEO exchangeConfigEO = (CarryOverConfigEO)this.carryOverConfigDao.get((Serializable)((Object)exchangeId));
        if (ObjectUtils.isEmpty((Object)currConfigEO) || ObjectUtils.isEmpty((Object)exchangeConfigEO)) {
            return Boolean.TRUE;
        }
        Double currOrdinal = currConfigEO.getOrdinal();
        Double exchangeOrdinal = exchangeConfigEO.getOrdinal();
        currConfigEO.setOrdinal(exchangeOrdinal);
        exchangeConfigEO.setOrdinal(currOrdinal);
        this.carryOverConfigDao.update((BaseEntity)currConfigEO);
        this.carryOverConfigDao.update((BaseEntity)exchangeConfigEO);
        return Boolean.TRUE;
    }

    @Override
    public void importConfigByJson(boolean isOverwrite, MultipartFile multipartFile) {
        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u65e0\u6cd5\u89e3\u6790\u5bfc\u5165\u6587\u4ef6\u3002", (Throwable)e);
        }
        if (Objects.isNull(bytes) || bytes.length == 0) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u53ef\u4ee5\u5bfc\u5165\u7684\u6570\u636e\u3002");
        }
        List importJsonList = null;
        try {
            importJsonList = (List)JsonUtils.readValue((byte[])bytes, (TypeReference)new TypeReference<List<CarryOverConfigEO>>(){});
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4ec5\u652f\u6301json\u683c\u5f0f");
        }
        if (CollectionUtils.isEmpty((Collection)importJsonList)) {
            throw new BusinessRuntimeException("\u89e3\u6790\u6587\u4ef6\u5931\u8d25\uff0c\u672a\u627e\u5230\u5e74\u7ed3\u8bbe\u7f6e\u65b9\u6848");
        }
        List deleteConfigEOS = this.carryOverConfigDao.loadAll();
        this.carryOverConfigDao.deleteBatch(deleteConfigEOS);
        logger.info("\u5e74\u7ed3\u8bbe\u7f6e\u5bfc\u5165-\u5220\u9664\u5e74\u7ed3\u8bbe\u7f6e\u65b9\u6848{}\u6761", (Object)deleteConfigEOS.size());
        this.carryOverConfigDao.addBatch(importJsonList);
        logger.info("\u5e74\u7ed3\u8bbe\u7f6e\u5bfc\u5165-\u63d2\u5165\u5e74\u7ed3\u8bbe\u7f6e\u65b9\u6848{}\u6761", (Object)importJsonList.size());
    }
}

