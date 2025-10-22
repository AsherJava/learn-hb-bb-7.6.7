/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.entity.component.currency.impl;

import com.jiuqi.nr.entity.component.currency.OrgCurrencyService;
import com.jiuqi.nr.entity.component.currency.dto.CurrencyCheckDTO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class OrgCurrencyServiceImpl
implements OrgCurrencyService {
    public static final Map<String, String> currencyNameMap = new HashMap<String, String>(2);
    @Autowired
    private OrgCategoryClient orgCategoryClient;

    @Override
    public CurrencyCheckDTO existCurrencyAttribute(String orgCategory) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(orgCategory);
        PageVO list = this.orgCategoryClient.list(orgCategoryDO);
        if (list.getTotal() == 0) {
            throw new IllegalArgumentException(String.format("\u67e5\u8be2\u4e0d\u5230\u7ec4\u7ec7\u673a\u6784\uff1a%s", orgCategory));
        }
        OrgCategoryDO categoryDO = (OrgCategoryDO)list.getRows().get(0);
        List zbs = categoryDO.getZbs();
        CurrencyCheckDTO currencyCheckDTO = new CurrencyCheckDTO();
        Set<String> keySet = currencyNameMap.keySet();
        for (String attribute : keySet) {
            Optional<ZB> findZb = this.findFiled(zbs, attribute);
            if (findZb.isPresent()) {
                ZB zb = findZb.get();
                if (this.relateCurrency(zb)) {
                    if (this.relateMultiple(zb)) continue;
                    currencyCheckDTO.addFix(zb.getTitle());
                    continue;
                }
                currencyCheckDTO.addFix(zb.getTitle());
                continue;
            }
            currencyCheckDTO.addGenerator(currencyNameMap.get(attribute));
        }
        return currencyCheckDTO;
    }

    private boolean relateMultiple(ZB zb) {
        return this.getMultiple(zb.getName()) == (zb.getMultiple() == null ? 0 : zb.getMultiple());
    }

    private int getMultiple(String zb) {
        return "CURRENCYID".equals(zb) ? 0 : 1;
    }

    private boolean relateCurrency(ZB zb) {
        return zb.getRelatetype() != null && 1 == zb.getRelatetype() && "MD_CURRENCY".equals(zb.getReltablename());
    }

    @Override
    public void generatorCurrency(String orgCategory) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(orgCategory);
        PageVO categoryPage = this.orgCategoryClient.list(orgCategoryDO);
        if (categoryPage.getTotal() == 0) {
            throw new IllegalArgumentException(String.format("\u67e5\u8be2\u4e0d\u5230\u7ec4\u7ec7\u673a\u6784\uff1a%s", orgCategory));
        }
        OrgCategoryDO oldCategory = (OrgCategoryDO)categoryPage.getRows().get(0);
        List zbs = oldCategory.getZbs();
        for (String attribute : currencyNameMap.keySet()) {
            Optional<ZB> findZb = this.findFiled(zbs, attribute);
            ZB zbDTO = null;
            if (!findZb.isPresent()) {
                zbDTO = new ZB();
                zbDTO.setId(UUID.randomUUID());
                zbDTO.setName(attribute);
                zbDTO.setTitle(currencyNameMap.get(attribute));
                zbDTO.setDatatype(Integer.valueOf(2));
                zbDTO.setPrecision(Integer.valueOf(200));
                zbDTO.setRelatetype(Integer.valueOf(1));
                zbDTO.setReltablename("MD_CURRENCY");
                zbDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
                zbDTO.setSolidityflag(Integer.valueOf(0));
                zbDTO.setMultiple(Integer.valueOf(this.getMultiple(attribute)));
            } else if (!this.relateCurrency(findZb.get()) || !this.relateMultiple(findZb.get())) {
                zbDTO = findZb.get();
                zbDTO.setRelatetype(Integer.valueOf(1));
                zbDTO.setReltablename("MD_CURRENCY");
                zbDTO.setMultiple(Integer.valueOf(this.getMultiple(attribute)));
            }
            if (zbDTO == null) continue;
            oldCategory.syncZb(zbDTO);
        }
        R rs = this.orgCategoryClient.update(oldCategory);
        if (rs.getCode() != 0) {
            throw new IllegalArgumentException(String.format("\u4e3a\u7ec4\u7ec7\u673a\u6784:%s\u65b0\u589e\u5e01\u79cd\u5b57\u6bb5\u5931\u8d25", orgCategory));
        }
    }

    private Optional<ZB> findFiled(List<ZB> zbs, String field) {
        return zbs.stream().filter(e -> field.equals(e.getName())).findAny();
    }

    static {
        currencyNameMap.put("CURRENCYID", "\u672c\u4f4d\u5e01");
        currencyNameMap.put("CURRENCYIDS", "\u62a5\u8868\u5e01\u79cd");
    }
}

