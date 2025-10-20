/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.gcreport.onekeymerge.dao.OnekeyMergeResultDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeResultService;
import com.jiuqi.gcreport.onekeymerge.service.GcTaskResultService;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcOnekeyMergeResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOnekeyMergeResultServiceImpl
implements GcOnekeyMergeResultService {
    @Autowired
    private OnekeyMergeResultDao onekeyMergeResultDao;
    @Autowired
    private GcTaskResultService taskResultService;

    @Override
    public void saveResult(GcOnekeyMergeResultEO eo) {
        this.onekeyMergeResultDao.save(eo);
    }

    @Override
    public List<GcOnekeyMergeResultVO> getCurrentThreeResult(GcActionParamsVO paramsVO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        List<GcOnekeyMergeResultEO> ret = this.onekeyMergeResultDao.findPastThreeResult(paramsVO);
        return ret.stream().map(gcOnekeyMergeResultEO -> {
            GcOnekeyMergeResultVO vo = new GcOnekeyMergeResultVO();
            BeanUtils.copyProperties(gcOnekeyMergeResultEO, vo);
            vo.setTaskTime(sdf.format(gcOnekeyMergeResultEO.getTaskTime()));
            Map<String, GcTaskResultVO> resultByGroupId = this.taskResultService.getResultByGroupId(vo.getId());
            vo.setTaskResult(resultByGroupId);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public GcOnekeyMergeResultVO getCurrentResultById(String taskLogId) {
        GcOnekeyMergeResultEO gcOnekeyMergeResultEO = (GcOnekeyMergeResultEO)this.onekeyMergeResultDao.get((Serializable)((Object)taskLogId));
        GcOnekeyMergeResultVO vo = new GcOnekeyMergeResultVO();
        BeanUtils.copyProperties((Object)gcOnekeyMergeResultEO, vo);
        return vo;
    }
}

