/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 */
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.domain.enumdata.EnumDataDO;
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
public class EnumDataTransUtil {
    private static Logger logger = LoggerFactory.getLogger(EnumDataTransUtil.class);
    @Autowired
    private VaI18nClient vaDataResourceClient;

    public void transDatss(List<EnumDataDO> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        try {
            ArrayList<String> keys = new ArrayList<String>();
            HashMap<String, EnumDataDO> enumMap = new HashMap<String, EnumDataDO>();
            for (EnumDataDO data : datas) {
                String key = "VA#enumdata#" + data.getBiztype() + "#" + data.getVal();
                keys.add(key);
                enumMap.put(key, data);
            }
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List results = this.vaDataResourceClient.queryList(dataResourceDTO);
            if (results == null || results.size() != keys.size()) {
                return;
            }
            for (int i = 0; i < keys.size(); ++i) {
                if (!StringUtils.hasText((String)results.get(i))) continue;
                ((EnumDataDO)enumMap.get(keys.get(i))).setTitle((String)results.get(i));
            }
        }
        catch (Exception e) {
            logger.info("\u679a\u4e3e\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }
}

