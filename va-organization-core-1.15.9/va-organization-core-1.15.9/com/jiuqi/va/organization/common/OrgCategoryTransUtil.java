/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgCategoryTransUtil {
    private static Logger logger = LoggerFactory.getLogger(OrgCategoryTransUtil.class);
    @Autowired
    private VaI18nClient vaDataResourceClient;

    public List<String> transResource(VaI18nResourceDTO vaDataResourceDTO) {
        return this.vaDataResourceClient.queryList(vaDataResourceDTO);
    }

    public void transCategorys(List<OrgCategoryDO> categorys) {
        if (categorys == null || categorys.isEmpty()) {
            return;
        }
        try {
            for (OrgCategoryDO category : categorys) {
                this.transCategory(category);
            }
        }
        catch (Exception e) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }

    public void transCategory(OrgCategoryDO category) {
        if (category == null) {
            return;
        }
        try {
            if (StringUtils.hasText(category.getTitle())) {
                VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
                ArrayList<String> keys = new ArrayList<String>();
                keys.add("VA#orgdata#categorys#" + category.getName() + "#categoryTitle#" + category.getName());
                dataResourceDTO.setKey(keys);
                List results = this.vaDataResourceClient.queryList(dataResourceDTO);
                if (results != null && !results.isEmpty() && StringUtils.hasText((String)results.get(0))) {
                    category.setTitle((String)results.get(0));
                }
            }
            this.transCategoryShowFields(category.getName(), category.getAllZbs());
        }
        catch (Exception e) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }

    public void transCategoryShowFields(String categoryName, List<? extends ZB> zbs) {
        if (zbs == null || zbs.isEmpty()) {
            return;
        }
        try {
            ArrayList<String> keys = new ArrayList<String>();
            HashMap<String, ZB> zbMap = new HashMap<String, ZB>();
            for (ZB zB : zbs) {
                String key = "VA#orgdata#categorys#" + categoryName + "#showcol#" + zB.getName();
                keys.add(key);
                zbMap.put(key, zB);
            }
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List list = this.vaDataResourceClient.queryList(dataResourceDTO);
            if (list == null || list.size() != keys.size()) {
                return;
            }
            for (int i = 0; i < keys.size(); ++i) {
                if (!StringUtils.hasText((String)list.get(i))) continue;
                ((ZB)zbMap.get(keys.get(i))).setTitle((String)list.get(i));
            }
        }
        catch (Exception e) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u6307\u6807\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }
}

