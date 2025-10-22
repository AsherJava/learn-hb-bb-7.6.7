/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.org.impl.tablepaste.common;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.impl.tablepaste.common.ColumnValueParser;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.LevelType;
import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColumnBaseDataParser
implements ColumnValueParser {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public Object parse(PasteDataVO source) {
        PageVO list;
        if (StringUtils.isEmpty((String)source.getColumnValue())) {
            return new HashMap();
        }
        String code = source.getColumnValue().split("\\|")[0].trim();
        String refTableName = source.getColumnDefine().getRefTableName();
        LevelType levelType = source.getColumnDefine().getLevelType();
        Map<String, Object> extParams = source.getExtParams();
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(refTableName);
        baseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
        baseDataDTO.setCode(code);
        if (levelType.equals((Object)LevelType.LEAF) || levelType.equals((Object)LevelType.MERGE)) {
            baseDataDTO.setLeafFlag(Boolean.valueOf(true));
        }
        if (refTableName.equalsIgnoreCase("MD_GCSUBJECT")) {
            baseDataDTO.put("systemid", extParams.get("systemId"));
        }
        if (CollectionUtils.isEmpty((Collection)(list = ((BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class)).list(baseDataDTO)).getRows())) {
            baseDataDTO.remove((Object)"code");
            baseDataDTO.setName(code);
            list = ((BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class)).list(baseDataDTO);
            if (CollectionUtils.isEmpty((Collection)list.getRows())) {
                return new HashMap();
            }
            if (list.getRows().size() > 1) {
                return new HashMap();
            }
        }
        BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
        if (levelType.equals((Object)LevelType.LEAF) && !((Boolean)baseDataDO.get((Object)"isLeaf")).booleanValue()) {
            return new HashMap();
        }
        if (levelType.equals((Object)LevelType.MERGE) && ((Boolean)baseDataDO.get((Object)"isLeaf")).booleanValue()) {
            return new HashMap();
        }
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.putAll((Map<String, Object>)baseDataDO);
        if (refTableName.equalsIgnoreCase("MD_GCSUBJECT")) {
            ret.put("id", baseDataDO.getId());
            ret.put("code", baseDataDO.getCode());
            ret.put("parentId", baseDataDO.getParentcode());
            ret.put("parents", baseDataDO.getParents() == null ? "" : baseDataDO.getParents().split("/"));
        }
        ret.put("title", baseDataDO.getLocalizedName());
        return ret;
    }
}

