/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 */
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BaseDataDefineTransUtil {
    private static Logger logger = LoggerFactory.getLogger(BaseDataDefineTransUtil.class);
    @Autowired
    private VaI18nClient vaDataResourceClient;

    public List<String> transResource(VaI18nResourceDTO vaDataResourceDTO) {
        return this.vaDataResourceClient.queryList(vaDataResourceDTO);
    }

    public void transDefines(List<BaseDataDefineDO> baseDataDefines) {
        if (baseDataDefines == null || baseDataDefines.isEmpty()) {
            return;
        }
        try {
            for (BaseDataDefineDO define : baseDataDefines) {
                this.transDefine(define);
            }
        }
        catch (Exception e) {
            logger.info("\u679a\u4e3e\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }

    public void transDefine(BaseDataDefineDO baseDataDefine) {
        if (baseDataDefine == null) {
            return;
        }
        try {
            if (StringUtils.hasText(baseDataDefine.getTitle())) {
                VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
                ArrayList<String> keys = new ArrayList<String>();
                keys.add("VA#basedata#defines#" + baseDataDefine.getName() + "#defineTitle#" + baseDataDefine.getName());
                dataResourceDTO.setKey(keys);
                List results = this.vaDataResourceClient.queryList(dataResourceDTO);
                if (results != null && !results.isEmpty() && StringUtils.hasText((String)results.get(0))) {
                    baseDataDefine.setTitle((String)results.get(0));
                }
            }
            this.transDefineShowFields(baseDataDefine);
        }
        catch (Exception e) {
            logger.info("\u679a\u4e3e\u6570\u636e\u8d44\u6e90\u8f6c\u6362\u5f02\u5e38", e);
        }
    }

    public void transDefineShowFields(BaseDataDefineDO baseDataDefine) {
        String define = baseDataDefine.getDefine();
        if (!StringUtils.hasText(define)) {
            return;
        }
        Map defineObj = JSONUtil.parseMap((String)define);
        if (defineObj == null || !defineObj.containsKey("showFields")) {
            return;
        }
        List showFields = (List)defineObj.get("showFields");
        if (showFields == null || showFields.isEmpty()) {
            return;
        }
        ArrayList<String> keys = new ArrayList<String>();
        HashMap<String, Map> showFieldMap = new HashMap<String, Map>();
        String parentKey = "VA#basedata#defines#" + baseDataDefine.getName() + "#showcol#";
        for (Object showField : showFields) {
            Map obj = (Map)showField;
            Object columnName = obj.get("columnName");
            if (columnName == null || !StringUtils.hasText(columnName.toString())) continue;
            String currKey = parentKey + columnName.toString();
            keys.add(currKey);
            showFieldMap.put(currKey, obj);
        }
        if (!keys.isEmpty()) {
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List results = this.vaDataResourceClient.queryList(dataResourceDTO);
            if (results == null || results.size() != keys.size()) {
                return;
            }
            for (int i = 0; i < keys.size(); ++i) {
                if (!StringUtils.hasText((String)results.get(i))) continue;
                ((Map)showFieldMap.get(keys.get(i))).put("columnTitle", results.get(i));
            }
        }
        baseDataDefine.setDefine(JSONUtil.toJSONString((Object)defineObj));
    }
}

