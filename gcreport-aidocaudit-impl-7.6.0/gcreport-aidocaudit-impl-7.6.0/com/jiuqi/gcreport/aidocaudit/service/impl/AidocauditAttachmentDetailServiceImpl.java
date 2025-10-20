/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nr.attachment.utils.FileOperationUtils
 *  com.jiuqi.nvwa.oss.service.IObjectService
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultItemDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleDao;
import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import com.jiuqi.gcreport.aidocaudit.enums.ResultTypeEnum;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditAttachmentDetailService;
import com.jiuqi.gcreport.aidocaudit.service.impl.AidocauditDocumentLocationServiceImpl;
import com.jiuqi.gcreport.aidocaudit.util.AidocauditScoreUtil;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nvwa.oss.service.IObjectService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class AidocauditAttachmentDetailServiceImpl
implements IAidocauditAttachmentDetailService {
    private Logger log = LoggerFactory.getLogger(AidocauditDocumentLocationServiceImpl.class);
    @Autowired
    private IObjectService objectService;
    @Autowired
    private IAidocauditResultItemDao resultItemDao;
    @Autowired
    private IAidocauditResultDao resultDao;
    @Autowired
    private IAidocauditRuleDao ruleDao;

    @Override
    public List<ResultitemOrderDTO> auditDetails(String resultId, int resultType) {
        List<ResultitemOrderDTO> resultItems = this.resultItemDao.queryDataByOrder(resultId);
        ArrayList<ResultitemOrderDTO> parents = new ArrayList<ResultitemOrderDTO>();
        ArrayList<ResultitemOrderDTO> lefts = new ArrayList<ResultitemOrderDTO>();
        for (ResultitemOrderDTO resultItem : resultItems) {
            if (StringUtils.hasText(resultItem.getParentScoreItemId())) {
                Boolean isFilter = this.filterByResultType(resultType, resultItem);
                if (!Boolean.TRUE.equals(isFilter)) continue;
                lefts.add(resultItem);
                continue;
            }
            parents.add(resultItem);
        }
        ArrayList<ResultitemOrderDTO> filterItemList = new ArrayList<ResultitemOrderDTO>();
        filterItemList.addAll(parents);
        filterItemList.addAll(lefts);
        return this.buildTree(filterItemList);
    }

    @Override
    public Map<String, Object> resultScore(String ruleId, String resultId) {
        if (!StringUtils.hasText(ruleId) || !StringUtils.hasText(resultId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u9519\u8bef");
        }
        HashMap<String, Object> scoreMap = new HashMap<String, Object>();
        AidocauditResultEO aidocauditResultEO = (AidocauditResultEO)this.resultDao.get((Serializable)((Object)resultId));
        if (ObjectUtils.isEmpty((Object)aidocauditResultEO)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5ba1\u6838\u7ed3\u679c");
        }
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)ruleId));
        if (ObjectUtils.isEmpty((Object)aidocauditRuleEO)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u89c4\u5219");
        }
        scoreMap.put("totalScore", aidocauditRuleEO.getTotalScore());
        scoreMap.put("score", aidocauditResultEO.getScore());
        scoreMap.put("scoreTime", aidocauditResultEO.getCreateTime());
        String fileId = aidocauditResultEO.getAttachmentId();
        ObjectStorageService objectStorageService = null;
        boolean b = false;
        try {
            objectStorageService = FileOperationUtils.objService((String)"JTABLEAREA");
            b = objectStorageService.existObject(fileId);
        }
        catch (ObjectStorageException e) {
            this.log.error("\u6587\u4ef6\u83b7\u53d6\u5931\u8d25", e);
        }
        if (!b) {
            this.log.error("\u672a\u627e\u5230\u6587\u4ef6");
        }
        try {
            ObjectInfo objectInfo = this.objectService.getObjectInfo("JTABLEAREA", fileId);
            scoreMap.put("fileName", objectInfo.getName());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u5b9a\u4f4d\u5931\u8d25", (Throwable)e);
        }
        return scoreMap;
    }

    private Boolean filterByResultType(int resultType, ResultitemOrderDTO resultItem) {
        String scoreBasis = resultItem.getScoreBasis();
        BigDecimal score = resultItem.getScore();
        BigDecimal fullScore = resultItem.getFullScore();
        ResultTypeEnum resultTypeEnum = ResultTypeEnum.getEnumByCode(resultType);
        switch (resultTypeEnum) {
            case ALL: {
                return true;
            }
            case MATCH: {
                return score.compareTo(fullScore) == 0;
            }
            case SUSPECTMATCH: {
                return AidocauditScoreUtil.isSuspectMatch(scoreBasis);
            }
            case UNMATCH: {
                return AidocauditScoreUtil.isSuspectMatch(scoreBasis) == false && score.compareTo(fullScore) < 0;
            }
        }
        return false;
    }

    public List<ResultitemOrderDTO> buildTree(List<ResultitemOrderDTO> resultitems) {
        HashMap<String, ResultitemOrderDTO> nodeMap = new HashMap<String, ResultitemOrderDTO>();
        ArrayList<ResultitemOrderDTO> rootNodes = new ArrayList<ResultitemOrderDTO>();
        for (ResultitemOrderDTO item : resultitems) {
            nodeMap.put(item.getOrdinal(), item);
        }
        for (ResultitemOrderDTO item : resultitems) {
            String ordinal = item.getOrdinal();
            if (ordinal.length() == 3) {
                rootNodes.add(item);
                continue;
            }
            String parentOrdinal = ordinal.substring(0, ordinal.length() - 3);
            ResultitemOrderDTO parentNode = (ResultitemOrderDTO)nodeMap.get(parentOrdinal);
            if (parentNode == null) continue;
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<ResultitemOrderDTO>());
            }
            parentNode.getChildren().add(item);
        }
        rootNodes.removeIf(node -> CollectionUtils.isEmpty(node.getChildren()));
        return rootNodes;
    }
}

