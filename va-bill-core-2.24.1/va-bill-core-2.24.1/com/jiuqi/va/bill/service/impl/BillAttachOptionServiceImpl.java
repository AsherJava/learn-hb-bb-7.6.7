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

import com.jiuqi.va.bill.dao.BillAttachOptionDao;
import com.jiuqi.va.bill.domain.BillAttachOptionConsts;
import com.jiuqi.va.bill.domain.option.BillAttachOptionDO;
import com.jiuqi.va.bill.service.BillAttachOptionService;
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
public class BillAttachOptionServiceImpl
implements BillAttachOptionService {
    @Autowired
    private BillAttachOptionDao billAttachOptionDao;

    @Override
    public List<OptionItemVO> list(OptionItemDTO param) {
        HashMap<String, BillAttachOptionDO> vals = new HashMap<String, BillAttachOptionDO>();
        List list = this.billAttachOptionDao.select((Object)new BillAttachOptionDO());
        if (list != null && list.size() > 0) {
            for (BillAttachOptionDO optionDO : list) {
                if (!StringUtils.hasText(optionDO.getVal())) continue;
                vals.put(optionDO.getName(), optionDO);
            }
        }
        ArrayList<OptionItemVO> endList = new ArrayList<OptionItemVO>();
        LinkedHashMap<String, OptionItemVO> infos = BillAttachOptionConsts.optionFoMap(param.getGroupName());
        for (OptionItemVO optionItemVO : infos.values()) {
            if (StringUtils.hasText(param.getSearchKey()) && !optionItemVO.getName().contains(param.getSearchKey()) && !optionItemVO.getTitle().contains(param.getSearchKey()) || StringUtils.hasText(param.getName()) && !optionItemVO.getName().equals(param.getName())) continue;
            if (vals.containsKey(optionItemVO.getName())) {
                BillAttachOptionDO hadDo = (BillAttachOptionDO)((Object)vals.get(optionItemVO.getName()));
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
    public R update(BillAttachOptionDO option) {
        BillAttachOptionDO param = new BillAttachOptionDO();
        param.setName(option.getName());
        BillAttachOptionDO old = (BillAttachOptionDO)((Object)this.billAttachOptionDao.selectOne((Object)param));
        if (old != null) {
            option.setId(old.getId());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.billAttachOptionDao.updateByPrimaryKey((Object)option);
        } else {
            option.setId(UUID.randomUUID());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.billAttachOptionDao.insert((Object)option);
        }
        return R.ok();
    }
}

