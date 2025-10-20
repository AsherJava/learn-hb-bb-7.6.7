/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.navigation.service.impl;

import com.jiuqi.navigation.common.BusinessRuntimeException;
import com.jiuqi.navigation.common.UUIDUtils;
import com.jiuqi.navigation.dao.NavigationDao;
import com.jiuqi.navigation.entity.NavigationEO;
import com.jiuqi.navigation.service.NavigationService;
import com.jiuqi.navigation.vo.NavigationVO;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class NavigationServiceImpl
implements NavigationService {
    @Autowired
    private NavigationDao navigationDao;

    private NavigationVO convertEO2VO(NavigationEO eo) {
        NavigationVO vo = new NavigationVO();
        BeanUtils.copyProperties(eo, vo);
        return vo;
    }

    private NavigationEO convertVO2EO(NavigationVO vo) {
        NavigationEO eo = new NavigationEO();
        BeanUtils.copyProperties(vo, eo);
        return eo;
    }

    @Override
    public NavigationVO getNavigationConfigByCode(String code) {
        List<NavigationEO> NavigationVOS = this.navigationDao.loadAll();
        List eos = NavigationVOS.stream().map(this::convertEO2VO).filter(NavigationVO2 -> NavigationVO2.getCode().equalsIgnoreCase(code)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(eos)) {
            return null;
        }
        return (NavigationVO)eos.get(0);
    }

    @Override
    public NavigationVO saveConfig(NavigationVO vo) {
        if (ObjectUtils.isEmpty(vo.getCode())) {
            throw new RuntimeException("code can not be empty");
        }
        List<NavigationEO> byExample = this.navigationDao.findByCode(vo.getCode());
        if (!CollectionUtils.isEmpty(byExample)) {
            throw new BusinessRuntimeException("duplicate code");
        }
        vo.setId(UUIDUtils.newUUIDStr());
        if (ObjectUtils.isEmpty(vo.getConfigValue())) {
            vo.setConfigValue("[]");
        }
        if (ObjectUtils.isEmpty(vo.getBackImg())) {
            vo.setBackImg("");
        }
        if (ObjectUtils.isEmpty(vo.getRecver())) {
            vo.setRecver(String.valueOf(System.currentTimeMillis()));
        }
        this.navigationDao.save(this.convertVO2EO(vo));
        return vo;
    }

    @Override
    public List<NavigationVO> getAllPages() {
        List<NavigationVO> NavigationVOS = this.navigationDao.loadAll().stream().map(this::convertEO2VO).collect(Collectors.toList());
        return NavigationVOS;
    }

    @Override
    public NavigationVO updateConfig(NavigationVO vo) {
        NavigationVO voOld = this.getNavigationConfigByCode(vo.getCode());
        voOld.setBackImg(vo.getBackImg());
        voOld.setTitle(vo.getTitle());
        voOld.setConfigValue(vo.getConfigValue());
        this.navigationDao.update(this.convertVO2EO(voOld));
        return vo;
    }

    @Override
    public void deleteConfig(String id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new RuntimeException("id can not be empty");
        }
        this.navigationDao.deleteById(id);
    }
}

