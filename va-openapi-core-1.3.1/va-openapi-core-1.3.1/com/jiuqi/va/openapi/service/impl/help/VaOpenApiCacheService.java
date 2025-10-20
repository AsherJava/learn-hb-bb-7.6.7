/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.openapi.service.impl.help;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.openapi.config.VaOpenApiCoreConfig;
import com.jiuqi.va.openapi.dao.VaOpenApiAuthDao;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import com.jiuqi.va.openapi.domain.OpenApiSyncCacheDTO;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaOpenApiCacheService {
    @Autowired
    private VaOpenApiAuthDao authDao;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, OpenApiAuthDO>> cache = new ConcurrentHashMap();

    public OpenApiAuthDO get(OpenApiAuthDTO param) {
        ConcurrentHashMap<String, OpenApiAuthDO> refs = this.getRefs(param);
        OpenApiAuthDO oado = null;
        String clientId = param.getClientid();
        if (refs.containsKey(clientId)) {
            oado = refs.get(clientId);
        }
        if (oado == null && (oado = (OpenApiAuthDO)((Object)this.authDao.selectOne((Object)param))) != null) {
            refs.put(clientId, oado);
        }
        if (oado != null) {
            if (param.getRandomcode() != null && !param.getRandomcode().equals(oado.getRandomcode())) {
                return null;
            }
            if (param.getOpenid() != null && !param.getOpenid().equals(oado.getOpenid())) {
                return null;
            }
            return oado;
        }
        return null;
    }

    public void update(OpenApiAuthDO param) {
        ConcurrentHashMap<String, OpenApiAuthDO> refs = this.getRefs(param);
        refs.put(param.getClientid(), param);
    }

    public void remove(OpenApiAuthDO param) {
        ConcurrentHashMap<String, OpenApiAuthDO> refs = this.getRefs(param);
        refs.remove(param.getClientid());
    }

    public void stop(OpenApiAuthDO param) {
        ConcurrentHashMap<String, OpenApiAuthDO> refs = this.getRefs(param);
        OpenApiAuthDO oaad = refs.get(param.getClientid());
        if (oaad != null) {
            oaad.setStopflag(param.getStopflag());
        }
    }

    public void publishMsg(OpenApiAuthDO param, boolean isUpdate, boolean isRemove, boolean isStop) {
        if (!EnvConfig.getRedisEnable()) {
            return;
        }
        OpenApiSyncCacheDTO oascd = new OpenApiSyncCacheDTO();
        oascd.setCurrtNodeId(EnvConfig.getCurrNodeId());
        oascd.setOpenApiAuthDO(param);
        oascd.setUpdate(isUpdate);
        oascd.setRemove(isRemove);
        oascd.setStop(isStop);
        EnvConfig.sendRedisMsg((String)VaOpenApiCoreConfig.getOpenApiSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)oascd)));
    }

    private ConcurrentHashMap<String, OpenApiAuthDO> getRefs(OpenApiAuthDO param) {
        String tenantName = param.getTenantName();
        if (!cache.containsKey(tenantName)) {
            cache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        return cache.get(tenantName);
    }
}

