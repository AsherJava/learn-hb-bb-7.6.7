/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.service;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class VaParamSyncTableService {
    @Autowired(required=false)
    private List<VaParamTableSyncExtend> taskList;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public List<VaParamTableSyncDTO> getTableParams() {
        ArrayList<VaParamTableSyncDTO> list;
        block4: {
            block3: {
                list = new ArrayList<VaParamTableSyncDTO>();
                if (!EnvConfig.getRedisEnable()) break block3;
                Map taskMap = this.stringRedisTemplate.opsForHash().entries((Object)"VA_PARAM_SYNC_TABLE");
                if (taskMap.isEmpty()) break block4;
                for (Object val : taskMap.values()) {
                    list.add((VaParamTableSyncDTO)JSONUtil.parseObject((String)val.toString(), VaParamTableSyncDTO.class));
                }
                break block4;
            }
            if (this.taskList != null && !this.taskList.isEmpty()) {
                for (VaParamTableSyncExtend vaParamTableSyncExtend : this.taskList) {
                    list.addAll(vaParamTableSyncExtend.getParams());
                }
            }
        }
        return list;
    }
}

