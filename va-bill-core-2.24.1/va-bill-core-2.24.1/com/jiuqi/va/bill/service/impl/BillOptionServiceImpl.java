/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillOptionDao;
import com.jiuqi.va.bill.domain.BillOptionConsts;
import com.jiuqi.va.bill.domain.BillOptionDO;
import com.jiuqi.va.bill.service.BillOptionService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillOptionServiceImpl
implements BillOptionService {
    @Autowired
    private BillOptionDao billOptionDao;

    @Override
    public List<OptionItemVO> list(OptionItemDTO param) {
        HashMap<String, BillOptionDO> vals = new HashMap<String, BillOptionDO>();
        List list = this.billOptionDao.select((Object)new BillOptionDO());
        if (list != null && list.size() > 0) {
            for (BillOptionDO optionDO : list) {
                if (!StringUtils.hasText(optionDO.getVal())) continue;
                vals.put(optionDO.getName(), optionDO);
            }
        }
        ArrayList<OptionItemVO> endList = new ArrayList<OptionItemVO>();
        LinkedHashMap<String, OptionItemVO> infos = BillOptionConsts.optionFoMap(param.getGroupName());
        for (OptionItemVO optionItemVO : infos.values()) {
            if (StringUtils.hasText(param.getSearchKey()) && !optionItemVO.getName().contains(param.getSearchKey()) && !optionItemVO.getTitle().contains(param.getSearchKey()) || StringUtils.hasText(param.getName()) && !optionItemVO.getName().equals(param.getName())) continue;
            if (vals.containsKey(optionItemVO.getName())) {
                BillOptionDO hadDo = (BillOptionDO)((Object)vals.get(optionItemVO.getName()));
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
    public R update(BillOptionDO option) {
        BillOptionDO param = new BillOptionDO();
        param.setName(option.getName());
        BillOptionDO old = (BillOptionDO)((Object)this.billOptionDao.selectOne((Object)param));
        if (old != null) {
            option.setId(old.getId());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.billOptionDao.updateByPrimaryKey((Object)option);
        } else {
            option.setId(UUID.randomUUID());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.billOptionDao.insert((Object)option);
        }
        return R.ok();
    }
}

