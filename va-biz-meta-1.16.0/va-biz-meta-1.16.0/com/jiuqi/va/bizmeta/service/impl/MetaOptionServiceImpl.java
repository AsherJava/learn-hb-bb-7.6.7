/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaOptionConsts;
import com.jiuqi.va.bizmeta.dao.IMetaDataOptionDao;
import com.jiuqi.va.bizmeta.domain.metaoption.MetaOptionDO;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MetaOptionServiceImpl
implements IMetaOptionService {
    private static final Logger log = LoggerFactory.getLogger(MetaOptionServiceImpl.class);
    @Autowired
    private IMetaDataOptionDao metaDataOptionDao;

    @Override
    public List<OptionItemVO> list(OptionItemDTO param) {
        HashMap<String, MetaOptionDO> vals = new HashMap<String, MetaOptionDO>();
        List list = this.metaDataOptionDao.select((Object)new MetaOptionDO());
        if (list != null && list.size() > 0) {
            for (MetaOptionDO optionDO : list) {
                if (!StringUtils.hasText(optionDO.getVal())) continue;
                vals.put(optionDO.getName(), optionDO);
            }
        }
        ArrayList<OptionItemVO> endList = new ArrayList<OptionItemVO>();
        LinkedHashMap<String, OptionItemVO> infos = MetaOptionConsts.optionFoMap(param.getGroupName());
        for (OptionItemVO optionItemVO : infos.values()) {
            if (StringUtils.hasText(param.getSearchKey()) && !optionItemVO.getName().contains(param.getSearchKey()) && !optionItemVO.getTitle().contains(param.getSearchKey()) || StringUtils.hasText(param.getName()) && !optionItemVO.getName().equals(param.getName())) continue;
            if (vals.containsKey(optionItemVO.getName())) {
                MetaOptionDO hadDo = (MetaOptionDO)((Object)vals.get(optionItemVO.getName()));
                optionItemVO.setVal(hadDo.getVal());
                optionItemVO.setModifyuser(hadDo.getModifyuser());
                optionItemVO.setModifytime(hadDo.getModifytime());
            } else {
                optionItemVO.setVal(optionItemVO.getDefauleVal());
            }
            endList.add(optionItemVO);
        }
        return endList;
    }

    @Override
    public R update(MetaOptionDO option) {
        MetaOptionDO param = new MetaOptionDO();
        param.setName(option.getName());
        MetaOptionDO old = (MetaOptionDO)((Object)this.metaDataOptionDao.selectOne((Object)param));
        if (old != null) {
            option.setId(old.getId());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.metaDataOptionDao.updateByPrimaryKey((Object)option);
        } else {
            option.setId(UUID.randomUUID());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.metaDataOptionDao.insert((Object)option);
        }
        return R.ok();
    }
}

