/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import com.jiuqi.nr.mapping.dao.BaseDataItemMappingDao;
import com.jiuqi.nr.mapping.dao.BaseDataMappingDao;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.service.BaseDataMappingService;
import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseDataMappingServiceImpl
implements BaseDataMappingService {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataMappingDao baseDataMappingDao;
    @Autowired
    private BaseDataItemMappingDao baseDataItemMappingDao;
    @Autowired
    private MappingSchemeDao msDao;

    @Override
    public List<BaseDataMapping> getBaseDataMapping(String msKey) {
        return this.baseDataMappingDao.findByMS(msKey);
    }

    @Override
    public List<BaseDataItemMapping> getBaseDataItemMapping(String msKey, String code) {
        return this.baseDataItemMappingDao.findByMSAndBaseDataCode(msKey, code);
    }

    @Override
    public List<BaseDataItemMapping> getBaseDataItemMappingByMSKey(String msKey) {
        return this.baseDataItemMappingDao.findByMS(msKey);
    }

    @Override
    public List<BaseDataVO> getBaseDataItem(String msKey, String tableName) {
        ArrayList<BaseDataVO> baseDataVOList = new ArrayList<BaseDataVO>();
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        List<BaseDataItemMapping> mappings = this.baseDataItemMappingDao.findByMSAndBaseDataCode(msKey, tableName);
        for (BaseDataDO baseDataDO : pageVO.getRows()) {
            BaseDataVO baseDataVO = new BaseDataVO();
            baseDataVO.setCode(baseDataDO.getCode());
            baseDataVO.setTitle(baseDataDO.getName());
            Optional<BaseDataItemMapping> mapping = mappings.stream().filter(e -> e.getBaseItemCode().equals(baseDataDO.getCode())).findFirst();
            if (mapping.isPresent()) {
                baseDataVO.setMpCode(mapping.get().getMappingCode());
                baseDataVO.setMpTitle(mapping.get().getMappingTitle());
            }
            baseDataVOList.add(baseDataVO);
        }
        return baseDataVOList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveBaseDataMapping(String msKey, List<BaseDataMapping> bds) {
        this.baseDataMappingDao.deleteByMS(msKey);
        this.baseDataMappingDao.batchAdd(bds);
        this.msDao.updateTime(msKey);
    }

    @Override
    public void clearByMS(String msKey) {
        this.baseDataMappingDao.deleteByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveBaseDataItemMapping(String msKey, String baseDataCode, List<BaseDataItemMapping> list) {
        this.baseDataItemMappingDao.deleteByMSBaseData(msKey, baseDataCode);
        this.baseDataItemMappingDao.batchAdd(list);
        this.msDao.updateTime(msKey);
    }

    @Override
    public void clearByMSAndBaseData(String msKey, String baseDataCode) {
        this.baseDataItemMappingDao.deleteByMSBaseData(msKey, baseDataCode);
    }
}

