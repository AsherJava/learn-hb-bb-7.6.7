/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.floatmodel.impl.service.impl;

import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.bde.floatmodel.impl.gather.FloatRegionHandlerGather;
import com.jiuqi.bde.floatmodel.impl.service.FloatRegionHandlerService;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FloatRegionHandlerServiceImpl
implements FloatRegionHandlerService {
    @Autowired
    private FloatRegionHandlerGather floatRegionHandlerGather;

    @Override
    public List<FloatRegionHandlerVO> listAllFloatRegionHandlerAppInfo() {
        List<FloatConfigHandler> floatRegionHandlerList = this.floatRegionHandlerGather.getFloatRegionHandlerList();
        if (CollectionUtils.isEmpty(floatRegionHandlerList)) {
            return Collections.emptyList();
        }
        ArrayList<FloatRegionHandlerVO> result = new ArrayList<FloatRegionHandlerVO>();
        HashMap prodLineToAppNameMap = new HashMap(8);
        for (FloatConfigHandler handler : floatRegionHandlerList) {
            if (prodLineToAppNameMap.containsKey(handler.getProdLine()) && ((Set)prodLineToAppNameMap.get(handler.getProdLine())).contains(handler.getAppName())) continue;
            FloatRegionHandlerVO vo = new FloatRegionHandlerVO();
            vo.setProdLine(handler.getProdLine());
            vo.setAppName(handler.getAppName());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<FloatRegionHandlerVO> getAllFloatRegionHandler() {
        List<FloatConfigHandler> floatRegionHandlerList = this.floatRegionHandlerGather.getFloatRegionHandlerList();
        if (CollectionUtils.isEmpty(floatRegionHandlerList)) {
            return Collections.emptyList();
        }
        return floatRegionHandlerList.stream().map(item -> {
            FloatRegionHandlerVO vo = new FloatRegionHandlerVO();
            vo.setCode(item.getCode());
            vo.setTitle(item.getTitle());
            return vo;
        }).collect(Collectors.toList());
    }
}

