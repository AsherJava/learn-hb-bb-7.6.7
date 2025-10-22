/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.datascheme.adjustment.service.impl;

import com.jiuqi.nr.datascheme.adjustment.dao.AdjustPeriodDao;
import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.adjustment.exception.AdjustPeriodException;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class AdjustPeriodDesignServiceImpl
implements AdjustPeriodDesignService {
    @Autowired
    private AdjustPeriodDao<DesignAdjustPeriodDO> adjustPeriodDao;

    @Override
    public void add(List<DesignAdjustPeriodDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<DesignAdjustPeriodDTO> query = this.query(list.get(0).getDataSchemeKey());
        Set codes = query.stream().map(AdjustPeriod::getCode).collect(Collectors.toSet());
        for (DesignAdjustPeriodDTO dto : list) {
            if (!codes.contains(dto.getCode())) continue;
            throw new AdjustPeriodException(String.format("\u6dfb\u52a0\u8c03\u6574\u671f\u6570\u636e\u5f02\u5e38\uff0c\u6807\u8bc6\u91cd\u590d[%s]", dto.getCode()));
        }
        List add = list.stream().map(DesignAdjustPeriodDO::convert).collect(Collectors.toList());
        this.adjustPeriodDao.insert(add);
    }

    @Override
    public List<DesignAdjustPeriodDTO> query(String schemeKey, String period) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null!");
        List<DesignAdjustPeriodDO> list = this.adjustPeriodDao.list(schemeKey, period);
        list.add(AdjustUtils.createDesignNotAdjust(schemeKey, period));
        return this.convertDTO(list);
    }

    private List<DesignAdjustPeriodDTO> convertDTO(List<DesignAdjustPeriodDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(DesignAdjustPeriodDTO::convert).collect(Collectors.toList());
    }

    @Override
    public List<DesignAdjustPeriodDTO> query(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null!");
        List<DesignAdjustPeriodDO> list = this.adjustPeriodDao.list(schemeKey);
        Set collect = list.stream().map(AdjustPeriodDO::getPeriod).collect(Collectors.toSet());
        for (String s : collect) {
            list.add(AdjustUtils.createDesignNotAdjust(schemeKey, s));
        }
        return this.convertDTO(list);
    }

    @Override
    public void deleteByDataScheme(String key) {
        if (StringUtils.hasLength(key)) {
            this.adjustPeriodDao.delete(key);
        }
    }

    @Override
    public void updateByPeriod(String schemeKey, String period, List<DesignAdjustPeriodDTO> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return;
        }
        List addDo = dtos.stream().map(DesignAdjustPeriodDO::convert).collect(Collectors.toList());
        this.adjustPeriodDao.delete(schemeKey, period);
        this.adjustPeriodDao.insert(addDo);
    }

    @Override
    public void updateAdjust(List<DesignAdjustPeriodDTO> adjustPeriodDTOList) {
        if (CollectionUtils.isEmpty(adjustPeriodDTOList)) {
            return;
        }
        String dataSchemeKey = adjustPeriodDTOList.get(0).getDataSchemeKey();
        this.adjustPeriodDao.delete(dataSchemeKey);
        List collect = adjustPeriodDTOList.stream().filter(Objects::nonNull).map(DesignAdjustPeriodDO::convert).collect(Collectors.toList());
        this.adjustPeriodDao.insert(collect);
    }
}

