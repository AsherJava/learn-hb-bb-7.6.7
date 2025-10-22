/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlExtractLogDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlExtractLogEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTaskStateEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SameCtrlExtractLogServiceImpl
implements SameCtrlExtractLogService {
    @Autowired
    private SameCtrlExtractLogDao sameCtrlExtractLogDao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public SameCtrlExtractLogVO insertSameCtrlExtractLog(SameCtrlExtractLogVO sameCtrlExtractLogVO) {
        SameCtrlExtractLogEO sameCtrlExtractLog = new SameCtrlExtractLogEO();
        BeanUtils.copyProperties(sameCtrlExtractLogVO, (Object)sameCtrlExtractLog);
        sameCtrlExtractLog.setId(UUIDUtils.newUUIDStr());
        sameCtrlExtractLog.setOperate(sameCtrlExtractLogVO.getOperate().getCode());
        sameCtrlExtractLog.setTaskState(sameCtrlExtractLogVO.getTaskState().getCode());
        sameCtrlExtractLogVO.setId(sameCtrlExtractLog.getId());
        this.sameCtrlExtractLogDao.save(sameCtrlExtractLog);
        this.sameCtrlExtractLogDao.updateLogToUnLatestStateReportInfo(sameCtrlExtractLog);
        return sameCtrlExtractLogVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateSamrCtrlLogById(SameCtrlExtractLogVO sameCtrlExtractLogVO) {
        SameCtrlExtractLogEO sameCtrlExtractLog = new SameCtrlExtractLogEO();
        BeanUtils.copyProperties(sameCtrlExtractLogVO, (Object)sameCtrlExtractLog);
        sameCtrlExtractLog.setOperate(sameCtrlExtractLogVO.getOperate().getCode());
        sameCtrlExtractLog.setTaskState(sameCtrlExtractLogVO.getTaskState().getCode());
        this.sameCtrlExtractLogDao.updateSamrCtrlLogById(sameCtrlExtractLog);
    }

    @Override
    public SameCtrlExtractLogVO querySameCtrlExtractLog(SameCtrlExtractLogVO sameCtrlExtractLogVO) {
        SameCtrlExtractLogEO sameCtrlExtractLog = new SameCtrlExtractLogEO();
        BeanUtils.copyProperties(sameCtrlExtractLogVO, (Object)sameCtrlExtractLog);
        List<SameCtrlExtractLogEO> sameCtrlExtractLogList = this.sameCtrlExtractLogDao.querySameCtrlExtractLog(sameCtrlExtractLog);
        SameCtrlExtractLogVO sameCtrlExtractLogResult = new SameCtrlExtractLogVO();
        HashMap<String, String> mapOperate = new HashMap<String, String>(16);
        sameCtrlExtractLogResult.setTaskState(SameCtrlExtractTaskStateEnum.NONE);
        boolean errorState = false;
        if (!CollectionUtils.isEmpty(sameCtrlExtractLogList)) {
            for (SameCtrlExtractLogEO sameCtrlExtractLogEO : sameCtrlExtractLogList) {
                if (SameCtrlExtractTaskStateEnum.ERROR.getCode().equals(sameCtrlExtractLogEO.getTaskState())) {
                    errorState = true;
                }
                sameCtrlExtractLogResult.setTaskState(SameCtrlExtractTaskStateEnum.valueOf((String)sameCtrlExtractLogEO.getTaskState()));
                if (StringUtils.isEmpty((String)sameCtrlExtractLogEO.getOperate()) || StringUtils.isEmpty((String)sameCtrlExtractLogEO.getInfo())) continue;
                if (mapOperate.containsKey(sameCtrlExtractLogEO.getOperate())) {
                    String info = (String)mapOperate.get(sameCtrlExtractLogEO.getOperate()) + sameCtrlExtractLogEO.getInfo() + "\n";
                    mapOperate.put(sameCtrlExtractLogEO.getOperate(), info);
                    continue;
                }
                mapOperate.put(sameCtrlExtractLogEO.getOperate(), sameCtrlExtractLogEO.getInfo() + "\n");
            }
        }
        StringBuffer logInfo = new StringBuffer(StringUtils.isEmpty((String)((String)mapOperate.get(SameCtrlExtractOperateEnum.DATAENTRY_EXTRACT.getCode()))) ? "" : (String)mapOperate.get(SameCtrlExtractOperateEnum.DATAENTRY_EXTRACT.getCode())).append(StringUtils.isEmpty((String)((String)mapOperate.get(SameCtrlExtractOperateEnum.CHANGEDPARENT_EXTRACT.getCode()))) ? "" : (String)mapOperate.get(SameCtrlExtractOperateEnum.CHANGEDPARENT_EXTRACT.getCode())).append(StringUtils.isEmpty((String)((String)mapOperate.get(SameCtrlExtractOperateEnum.VIRTUALPARENT_EXTRACT.getCode()))) ? "" : (String)mapOperate.get(SameCtrlExtractOperateEnum.VIRTUALPARENT_EXTRACT.getCode()));
        if (errorState) {
            sameCtrlExtractLogResult.setTaskState(SameCtrlExtractTaskStateEnum.ERROR);
        }
        sameCtrlExtractLogResult.setInfo(logInfo.toString());
        return sameCtrlExtractLogResult;
    }
}

