/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.rate.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateDao;
import com.jiuqi.gcreport.rate.impl.dao.CommonRateSchemeDao;
import com.jiuqi.gcreport.rate.impl.entity.CommonRateSchemeEO;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonRateSchemeServiceImpl
implements CommonRateSchemeService {
    @Autowired
    private CommonRateSchemeDao commonRateSchemeDao;
    @Autowired
    private CommonRateDao commonRateDao;

    @Override
    public List<CommonRateSchemeVO> listAllRateScheme() {
        List<CommonRateSchemeEO> eos = this.commonRateSchemeDao.listAllRateScheme();
        ArrayList<CommonRateSchemeVO> vos = new ArrayList<CommonRateSchemeVO>();
        if (CollectionUtils.isEmpty(eos)) {
            return vos;
        }
        eos.forEach(eo -> {
            CommonRateSchemeVO vo = new CommonRateSchemeVO();
            BeanUtils.copyProperties(eo, vo);
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public CommonRateSchemeVO queryRateScheme(String code) {
        CommonRateSchemeEO eo;
        CommonRateSchemeVO vo = new CommonRateSchemeVO();
        if (StringUtils.isEmpty((String)code)) {
            code = "DEFAULT";
        }
        if ((eo = this.commonRateSchemeDao.getRateSchemeByCode(code)) == null) {
            return null;
        }
        BeanUtils.copyProperties(eo, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean saveRateScheme(CommonRateSchemeVO rateSchemeVO) {
        CommonRateSchemeEO eo = new CommonRateSchemeEO();
        BeanUtils.copyProperties(rateSchemeVO, eo);
        CommonRateSchemeVO schemeVO = this.getRateSchemeByTitle(eo.getName());
        if (schemeVO != null && !schemeVO.getId().equals(eo.getId())) {
            throw new RuntimeException("\u5f53\u524d\u6c47\u7387\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
        }
        return this.commonRateSchemeDao.saveRateScheme(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteRateScheme(String rateSchemeCode) {
        this.commonRateDao.deleteRateInfoBySchemeCode(rateSchemeCode);
        this.commonRateSchemeDao.deleteRateScheme(rateSchemeCode);
        return true;
    }

    @Override
    public CommonRateSchemeVO getRateSchemeByTitle(String title) {
        CommonRateSchemeVO vo = new CommonRateSchemeVO();
        CommonRateSchemeEO eo = this.commonRateSchemeDao.getRateSchemeByTitle(title);
        if (eo == null) {
            return null;
        }
        BeanUtils.copyProperties(eo, vo);
        return vo;
    }
}

