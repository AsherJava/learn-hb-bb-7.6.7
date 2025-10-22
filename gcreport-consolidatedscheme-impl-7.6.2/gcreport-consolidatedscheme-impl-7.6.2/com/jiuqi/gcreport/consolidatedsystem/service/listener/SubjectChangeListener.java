/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.common.subject.impl.subject.event.SubjectChangeEvent
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.listener;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.event.SubjectChangeEvent;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SubjectChangeListener {
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedSubjectUIService subjectUIService;
    @Autowired
    private ConsolidatedSystemService systemService;

    @EventListener
    @Transactional(rollbackFor={Exception.class})
    @Async
    public void handleEvent(SubjectChangeEvent event) {
        String code;
        String optionValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
        boolean relateGlobalSubject = "1".equals(optionValue);
        if (!relateGlobalSubject) {
            return;
        }
        Boolean isStop = false;
        if (event.getCode() != null) {
            code = event.getCode();
        } else {
            if (event.getId() == null) {
                return;
            }
            BaseDataService baseDataService = (BaseDataService)SpringContextUtils.getBean(BaseDataService.class);
            BaseDataDTO param = new BaseDataDTO();
            param.setTableName("MD_ACCTSUBJECT");
            param.setId(event.getId());
            List data = baseDataService.list(param).getRows();
            if (data.size() <= 0) {
                return;
            }
            code = ((BaseDataDO)data.get(0)).getCode();
            isStop = true;
        }
        List<ConsolidatedSystemEO> systemEOS = this.systemService.getConsolidatedSystemEOS();
        ArrayList<ConsolidatedSubjectEO> consolidatedSubjectEOS = new ArrayList<ConsolidatedSubjectEO>();
        for (int i = 0; i < systemEOS.size(); ++i) {
            ConsolidatedSubjectEO eo = this.subjectService.getSubjectByCode(systemEOS.get(i).getId(), code);
            if (eo == null) continue;
            consolidatedSubjectEOS.add(eo);
            if (!isStop.booleanValue() || Boolean.TRUE != event.getStopChildItem()) continue;
            List<ConsolidatedSubjectEO> childrenList = this.subjectService.listDirectChildrensByCode(systemEOS.get(i).getId(), eo.getCode());
            consolidatedSubjectEOS.addAll(childrenList);
        }
        for (ConsolidatedSubjectEO subjectEO : consolidatedSubjectEOS) {
            if (isStop.booleanValue()) {
                subjectEO.setConsolidationFlag(event.getStopFlag() != 1);
            } else {
                subjectEO.setAsstype(event.getAsstype());
                String generalType = event.getGeneralType();
                subjectEO.setAttri(this.getSubjectAttri(generalType));
                subjectEO.setOrient(event.getOrient());
                subjectEO.setAsstype(event.getAsstype());
                subjectEO.setParentCode(event.getParentid());
                subjectEO.setTitle(event.getName());
            }
            this.subjectUIService.saveSubject(SubjectConvertUtil.convertEO2VO(subjectEO));
        }
    }

    private Integer getSubjectAttri(String generalType) {
        Integer attri = SubjectAttributeEnum.OTHER.getValue();
        if (SubjectClassEnum.ASSET.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.ASSET.getValue();
        } else if (SubjectClassEnum.COST.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.PROFITLOSS.getValue();
        } else if (SubjectClassEnum.EQUITY.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.RIGHT.getValue();
        } else if (SubjectClassEnum.GAIN_LOSS.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.PROFITLOSS.getValue();
        } else if (SubjectClassEnum.LIABILITY.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.DEBT.getValue();
        } else if (SubjectClassEnum.CASH.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.CASH.getValue();
        }
        return attri;
    }
}

