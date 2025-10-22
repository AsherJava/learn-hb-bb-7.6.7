/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.journalsingle.api.JournalSingleClient
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailDelCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition
 *  com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum
 *  com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalPageVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.journalsingle.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.journalsingle.api.JournalSingleClient;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailDelCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSubjectDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum;
import com.jiuqi.gcreport.journalsingle.service.IJournalPostRuleService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSinglePostService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSchemeService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleService;
import com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalPageVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class JournalSingleController
implements JournalSingleClient {
    @Autowired
    private IJournalSingleService iService;
    @Autowired
    IJournalPostRuleService postRuleService;
    @Autowired
    private IJournalSingleSchemeService schemeService;
    @Autowired
    private IJournalSinglePostService singlePostService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private IJournalSubjectDao journalSubjectDao;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> addJournalDetailMerge(@RequestBody List<List<JournalSingleVO>> batchlist) {
        for (List<JournalSingleVO> list : batchlist) {
            for (JournalSingleVO oneRow : list) {
                Map unSysFields = oneRow.getUnSysFields();
                for (Map.Entry unSysField : unSysFields.entrySet()) {
                    if (unSysField.getValue() instanceof Map) {
                        unSysFields.put(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                        continue;
                    }
                    unSysFields.put(unSysField.getKey(), unSysField.getValue());
                }
            }
        }
        this.iService.addJournalDetail(batchlist, true);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> addJournalDetailSingle(@RequestBody List<List<JournalSingleVO>> batchlist, HttpServletRequest request) {
        for (List<JournalSingleVO> list : batchlist) {
            for (JournalSingleVO oneRow : list) {
                Map unSysFields = oneRow.getUnSysFields();
                for (Map.Entry unSysField : unSysFields.entrySet()) {
                    if (unSysField.getValue() instanceof Map) {
                        unSysFields.put(unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                        continue;
                    }
                    unSysFields.put(unSysField.getKey(), unSysField.getValue());
                }
            }
        }
        this.iService.addJournalDetail(batchlist, false);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<JournalPageVO> queryByPageCondi(@RequestBody JournalDetailCondition condi) {
        ArrayList<JournalSingleVO> detailList = new ArrayList<JournalSingleVO>();
        int count = this.iService.queryPageCountByCondition(condi);
        List<JournalSingleEO> list = this.iService.queryByPageCondition(condi);
        String relateSchemeId = this.schemeService.getRelateSchemeId(condi.getTaskId(), condi.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        List dimensionVOS = this.dimensionService.findDimFieldsByTableName("GC_JOURNAL_SINGLE");
        for (JournalSingleEO eo : list) {
            JournalSingleVO vo = this.iService.convertEO2VO(eo, relateSchemeId, dimensionVOS);
            detailList.add(vo);
        }
        Map journalVOGroupMap = detailList.stream().collect(Collectors.groupingBy(JournalSingleVO::getmRecid, LinkedHashMap::new, Collectors.toList()));
        if (condi.zbId != null) {
            List<String> subjectCodes = this.postRuleService.getSubjectCodesByZbId(condi);
            Iterator iterator = journalVOGroupMap.entrySet().iterator();
            while (iterator.hasNext()) {
                List journalVOs = (List)iterator.next().getValue();
                if (this.itemIsShow(journalVOs, subjectCodes)) continue;
                iterator.remove();
            }
        }
        detailList = new ArrayList();
        for (List journalVOList : journalVOGroupMap.values()) {
            for (JournalSingleVO journalVO : journalVOList) {
                detailList.add(journalVO);
            }
        }
        JournalPageVO pageVO = new JournalPageVO(count, detailList);
        return BusinessResponseEntity.ok((Object)pageVO);
    }

    public BusinessResponseEntity<List<JournalSingleVO>> queryByCondi(JournalDetailCondition condi) {
        ArrayList<Object> detailList = new ArrayList<Object>();
        JournalSubjectEO subjectEOByZbCode = this.journalSubjectDao.getSubjectEOByZbId(condi.zbId);
        if (subjectEOByZbCode == null) {
            return BusinessResponseEntity.ok(detailList);
        }
        List<JournalSubjectEO> journalSubjectEOS = this.journalSubjectDao.listAllChildSubjects(subjectEOByZbCode.getId(), -1, -1);
        Set subjectCodeSet = journalSubjectEOS.stream().map(JournalSubjectEO::getCode).collect(Collectors.toSet());
        List<JournalSingleEO> list = this.iService.queryByCondition(condi);
        String relateSchemeId = this.schemeService.getRelateSchemeId(condi.getTaskId(), condi.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        List dimensionVOS = this.dimensionService.findDimFieldsByTableName("GC_JOURNAL_SINGLE");
        for (JournalSingleEO eo : list) {
            JournalSingleVO vo = this.iService.convertEO2VO(eo, relateSchemeId, dimensionVOS);
            detailList.add(vo);
        }
        Map journalVOGroupMap = detailList.stream().collect(Collectors.groupingBy(JournalSingleVO::getmRecid, LinkedHashMap::new, Collectors.toList()));
        detailList = new ArrayList();
        for (List journalVOList : journalVOGroupMap.values()) {
            ArrayList<JournalSingleVO> tempDetailList = new ArrayList<JournalSingleVO>();
            boolean hasSubjectCode = false;
            for (JournalSingleVO journalVO : journalVOList) {
                tempDetailList.add(journalVO);
                if (!subjectCodeSet.contains(journalVO.getSubjectCode())) continue;
                hasSubjectCode = true;
            }
            if (!hasSubjectCode) continue;
            detailList.addAll(tempDetailList);
        }
        return BusinessResponseEntity.ok(detailList);
    }

    public BusinessResponseEntity<List<JournalSingleVO>> queryDetailByID(@RequestBody JournalDetailCondition condi) {
        List<JournalSingleEO> list = this.iService.queryDetailByID(condi);
        ArrayList<JournalSingleVO> result = new ArrayList<JournalSingleVO>();
        String relateSchemeId = this.schemeService.getRelateSchemeId(condi.getTaskId(), condi.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        List dimensionVOS = this.dimensionService.findDimFieldsByTableName("GC_JOURNAL_SINGLE");
        for (JournalSingleEO eo : list) {
            JournalSingleVO vo = this.iService.convertEO2VO(eo, relateSchemeId, dimensionVOS);
            vo.setEdit(true);
            result.add(vo);
        }
        return BusinessResponseEntity.ok(result);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> SingleDeleteByMrecid(@RequestBody JournalDetailDelCondition condi) {
        if (condi.srcids_year.size() <= 0 && condi.srcids_month.size() <= 0) {
            return BusinessResponseEntity.ok();
        }
        try {
            if (condi.srcids_year.size() > 0) {
                this.iService.SingleDeleteByMrecid(condi.srcids_year, condi.acctYear, 1, condi.acctPeriod);
            }
            if (condi.srcids_month.size() > 0) {
                this.iService.SingleDeleteByMrecid(condi.srcids_month, condi.acctYear, -1, condi.acctPeriod);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<JournalEnvContextVO>> queryJournalByDims(@RequestParam(value="orgid") String orgid, @RequestParam(value="periodValue") String periodValue, @RequestParam(value="gcorgtype") String gcorgtype, @RequestParam(value="taskid") String taskid, @RequestParam(value="schemeid") String schemeid, @RequestParam(value="adjust") String adjust) {
        List<JournalEnvContextVO> journalEnvContextVOs = this.iService.queryJournalByDims(orgid, periodValue, gcorgtype, taskid, schemeid, adjust);
        return BusinessResponseEntity.ok(journalEnvContextVOs);
    }

    private boolean itemIsShow(List<JournalSingleVO> journalVOs, List<String> subjectCodes) {
        for (JournalSingleVO journalVO : journalVOs) {
            for (String subjectCode : subjectCodes) {
                if (!journalVO.getSubjectCode().startsWith(subjectCode)) continue;
                return true;
            }
        }
        return false;
    }

    public BusinessResponseEntity<String> postData(JournalSinglePostCondition postCondition) {
        String msg = this.singlePostService.postData(postCondition);
        if (StringUtils.isEmpty(msg)) {
            return BusinessResponseEntity.ok((Object)"\u8fc7\u8d26\u6210\u529f");
        }
        return BusinessResponseEntity.error((String)msg);
    }

    public BusinessResponseEntity<List<JournalWorkPaperDataVO>> getJournalWorkPaperData(JournalDetailCondition condi) {
        return BusinessResponseEntity.ok(this.singlePostService.getJournalWorkPaperData(condi));
    }

    public BusinessResponseEntity<List<JournalSingleVO>> getPenerationData(JournalDetailCondition condi) {
        return BusinessResponseEntity.ok(this.singlePostService.getPenerationData(condi));
    }
}

