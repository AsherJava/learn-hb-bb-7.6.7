/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gcreport.calculate.api.GcCalcClient
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO
 *  com.jiuqi.gcreport.calculate.vo.GcCalcLogVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.calculate.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gcreport.calculate.api.GcCalcClient;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.module.GcCalBusinessLogModule;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO;
import com.jiuqi.gcreport.calculate.vo.GcCalcLogVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Primary
@RestController
public class GcCalcController
implements GcCalcClient {
    @Autowired
    private GcCalcService calcService;
    @Autowired
    private ProgressService<GcCalcEnvContext, List<String>> progressService;

    public BusinessResponseEntity<Object> start(@Valid @RequestBody GcCalcArgmentsVO calcArgmentsVO) {
        NpContext context = NpContextHolder.getContext();
        GcOrgTypeUtils.setContextEntityId((String)calcArgmentsVO.getOrgType());
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                BusinessLogUtils.operate((Enum)GcCalBusinessLogModule.GcCalBusinessModuleOperateEnum.RUN_CALC, (String)(GcI18nUtil.getMessage((String)"gc.calculate.calc.web.log") + calcArgmentsVO.getSn()));
                GcCalcArgmentsDTO calcArgments = this.calcService.convertVO2DTO(calcArgmentsVO);
                this.calcService.calc(calcArgments);
            }
            finally {
                NpContextHolder.clearContext();
            }
        });
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<GcCalcLogVO> findCalcLogInfo(@RequestBody GcCalcArgmentsVO calcArgmentsVO) {
        GcCalcLogVO calcLogVO = this.calcService.findCalcLogVo(calcArgmentsVO);
        return BusinessResponseEntity.ok((Object)calcLogVO);
    }

    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(@PathVariable(value="sn") String sn) {
        ProgressData progressData = this.progressService.queryProgressData(sn, false);
        return BusinessResponseEntity.ok((Object)progressData);
    }

    public BusinessResponseEntity<Object> deleteSnStartProgress(@PathVariable(value="sn") String sn) {
        this.progressService.removeProgressData(sn);
        return BusinessResponseEntity.ok();
    }

    public List<Map<String, Object>> ruleTreeExpandLevel(String sn) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allLevel = new HashMap<String, Object>();
        String title = "title";
        allLevel.put(title, "\u5168\u90e8\u5c55\u5f00");
        String key = "key";
        allLevel.put(key, 0);
        result.add(allLevel);
        HashMap<String, Object> oneLevel = new HashMap<String, Object>();
        oneLevel.put(title, "\u5c55\u5f00\u4e00\u7ea7");
        oneLevel.put(key, 1);
        result.add(oneLevel);
        HashMap<String, Object> towLevel = new HashMap<String, Object>();
        towLevel.put(title, "\u5c55\u5f00\u4e8c\u7ea7");
        towLevel.put(key, 2);
        return result;
    }
}

