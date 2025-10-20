/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.oss.service.IObjectService
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.gcreport.aidocaudit.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleDao;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleItemDao;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditRuleParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.MQRuleItemResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.MQRuleResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditRuleService;
import com.jiuqi.gcreport.aidocaudit.util.ApiKeyCache;
import com.jiuqi.gcreport.aidocaudit.util.BuildTreeUtil;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.oss.service.IObjectService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class AidocauditRuleServiceImpl
implements IAidocauditRuleService {
    private final Logger logger = LoggerFactory.getLogger(AidocauditRuleServiceImpl.class);
    public static final String MSG_REQUEST_FAILED_EMPTY_RESPONSE = "\u8bf7\u6c42\u5931\u8d25\uff0c\u54cd\u5e94\u4e3a\u7a7a";
    public static final String PREFIX_HTTP = "http://";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    @Autowired
    private IAidocauditRuleDao ruleDao;
    @Autowired
    private IAidocauditRuleItemDao ruleItemDao;
    @Autowired
    private ApiKeyCache apiKeyCache;
    @Autowired
    private IObjectService objectService;
    @Autowired
    private AidocauditRuleServiceImpl self;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Value(value="${jiuqi.gcreport.aidocaudit.scoresystem.url:10.2.45.188:8880}")
    private String ipAndPort;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BusinessResponseEntity<List<AidocauditRuleEO>> list() {
        List<AidocauditRuleEO> list = this.ruleDao.list(null);
        return BusinessResponseEntity.ok(list);
    }

    @Override
    public BusinessResponseEntity<Boolean> add(AidocauditRuleParamDTO rule) {
        AidocauditRuleEO ruleEO = new AidocauditRuleEO();
        BeanUtils.copyProperties(rule, (Object)ruleEO);
        ruleEO.setId(UUIDUtils.newUUIDStr());
        String fileNameWithoutExtension = rule.getRuleAttachmentName().substring(0, rule.getRuleAttachmentName().lastIndexOf(46));
        ruleEO.setRuleAttachmentName(fileNameWithoutExtension);
        ruleEO.setRuleStatus(0);
        ruleEO.setCreateTime(new Date());
        ruleEO.setCreateUser(NpContextHolder.getContext().getUserId());
        int affectedRow = this.ruleDao.add((BaseEntity)ruleEO);
        return BusinessResponseEntity.ok((Object)(affectedRow > 0 ? 1 : 0));
    }

    @Override
    public BusinessResponseEntity<Boolean> generate(String ruleId) {
        String apiKey = this.apiKeyCache.getToken();
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)ruleId));
        Map<String, Object> body = this.constructBodyData(aidocauditRuleEO);
        String url = StringUtils.hasText(aidocauditRuleEO.getScoreTmplId()) ? PREFIX_HTTP + this.ipAndPort + "/api/doc-score/jq/temp/rebuild" : PREFIX_HTTP + this.ipAndPort + "/api/doc-score/jq/temp/add";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_AUTHORIZATION, apiKey);
        HttpEntity requestEntity = new HttpEntity(body, (MultiValueMap)headers);
        ResponseEntity responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class, new Object[0]);
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            this.apiKeyCache.invalidateToken();
            apiKey = this.apiKeyCache.getToken();
            headers.set(HEADER_AUTHORIZATION, apiKey);
            requestEntity = new HttpEntity(body, (MultiValueMap)headers);
            responseEntity = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class, new Object[0]);
        }
        Map response = (Map)responseEntity.getBody();
        String msg = "";
        if (response != null) {
            int code = (Integer)response.get("code");
            msg = (String)response.get("msg");
            if (code == 1) {
                aidocauditRuleEO.setRuleStatus(1);
                aidocauditRuleEO.setUpdateTime(new Date());
                aidocauditRuleEO.setUpdateUser(NpContextHolder.getContext().getUserId());
                this.ruleDao.update((BaseEntity)aidocauditRuleEO);
                this.logger.info("\u5f00\u59cb\u751f\u6210\u8bc4\u5206\u6a21\u677f\uff0c\u6a21\u677f\u540d\u79f0:{},\u9644\u4ef6ID:{}", (Object)aidocauditRuleEO.getRuleName(), (Object)aidocauditRuleEO.getRuleAttachmentId());
                return BusinessResponseEntity.ok((Object)true);
            }
            this.logger.error("\u751f\u6210\u8bc4\u5206\u6a21\u677f\uff0c\u8bf7\u6c42\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)msg);
        } else {
            this.logger.error(MSG_REQUEST_FAILED_EMPTY_RESPONSE);
            msg = MSG_REQUEST_FAILED_EMPTY_RESPONSE;
        }
        return BusinessResponseEntity.error((String)msg);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Boolean> delete(String ruleId, Boolean deleteRuleFalg) {
        String apiKey = this.apiKeyCache.getToken();
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)ruleId));
        if (aidocauditRuleEO.getRuleStatus().equals(0) || aidocauditRuleEO.getRuleStatus().equals(3)) {
            if (Boolean.TRUE.equals(deleteRuleFalg)) {
                this.ruleDao.delete((BaseEntity)aidocauditRuleEO);
            }
            List<AidocauditRuleItemEO> ruleItemList = this.ruleItemDao.getByRuleId(aidocauditRuleEO.getId());
            this.ruleItemDao.deleteBatch(ruleItemList);
            this.logger.info("\u5220\u9664\u8bc4\u5206\u6a21\u677f\uff0c\u6a21\u677f\u540d\u79f0:{},tempId:{}", (Object)aidocauditRuleEO.getRuleName(), (Object)aidocauditRuleEO.getScoreTmplId());
            return BusinessResponseEntity.ok((Object)true);
        }
        String url = PREFIX_HTTP + this.ipAndPort + "/api/doc-score/jq/temp/delete";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_AUTHORIZATION, apiKey);
        HashMap body = new HashMap();
        ArrayList<String> tempIds = new ArrayList<String>();
        tempIds.add(aidocauditRuleEO.getScoreTmplId());
        body.put("temp_ids", tempIds);
        HttpEntity requestEntity = new HttpEntity(body, (MultiValueMap)headers);
        ResponseEntity responseEntity = this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Map.class, new Object[0]);
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            this.apiKeyCache.invalidateToken();
            apiKey = this.apiKeyCache.getToken();
            headers.set(HEADER_AUTHORIZATION, apiKey);
            requestEntity = new HttpEntity(body, (MultiValueMap)headers);
            responseEntity = this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Map.class, new Object[0]);
        }
        Map response = (Map)responseEntity.getBody();
        String msg = "";
        if (response != null) {
            int code = (Integer)response.get("code");
            msg = (String)response.get("msg");
            if (code == 1) {
                if (Boolean.TRUE.equals(deleteRuleFalg)) {
                    this.ruleDao.delete((BaseEntity)aidocauditRuleEO);
                }
                List<AidocauditRuleItemEO> ruleItemList = this.ruleItemDao.getByRuleId(aidocauditRuleEO.getId());
                this.ruleItemDao.deleteBatch(ruleItemList);
                this.logger.info("\u5220\u9664\u8bc4\u5206\u6a21\u677f\uff0c\u6a21\u677f\u540d\u79f0:{},tempId:{}", (Object)aidocauditRuleEO.getRuleName(), (Object)aidocauditRuleEO.getScoreTmplId());
                return BusinessResponseEntity.ok((Object)true);
            }
            this.logger.error("\u5220\u9664\u8bc4\u5206\u6a21\u677f\uff0c\u8bf7\u6c42\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)msg);
        } else {
            this.logger.error(MSG_REQUEST_FAILED_EMPTY_RESPONSE);
            msg = MSG_REQUEST_FAILED_EMPTY_RESPONSE;
        }
        return BusinessResponseEntity.error((String)msg);
    }

    @Override
    public BusinessResponseEntity<Boolean> updateRuleItem(AidocauditRuleParamDTO rule) {
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)rule.getId()));
        ResponseEntity<Map> responseEntity = this.sendUpdateRequest(rule, aidocauditRuleEO);
        Map response = (Map)responseEntity.getBody();
        String msg = "";
        if (response != null) {
            int code = (Integer)response.get("code");
            msg = (String)response.get("msg");
            if (code == 1) {
                this.processRuleItems(rule);
                this.logger.info("\u66f4\u65b0\u8bc4\u5206\u6a21\u677f\uff0c\u6a21\u677f\u540d\u79f0:{},tempId:{}", (Object)aidocauditRuleEO.getRuleName(), (Object)aidocauditRuleEO.getScoreTmplId());
                return BusinessResponseEntity.ok((Object)true);
            }
            this.logger.error("\u66f4\u65b0\u8bc4\u5206\u6a21\u677f\uff0c\u8bf7\u6c42\u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f: {}", (Object)msg);
        } else {
            this.logger.error(MSG_REQUEST_FAILED_EMPTY_RESPONSE);
            msg = MSG_REQUEST_FAILED_EMPTY_RESPONSE;
        }
        return BusinessResponseEntity.error((String)msg);
    }

    private ResponseEntity<Map> sendUpdateRequest(AidocauditRuleParamDTO rule, AidocauditRuleEO aidocauditRuleEO) {
        String url = PREFIX_HTTP + this.ipAndPort + "/api/doc-score/jq/temp/update";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_AUTHORIZATION, this.apiKeyCache.getToken());
        Map<String, Object> body = this.constructUpdateBodyData(aidocauditRuleEO, rule.getRuleItemList());
        HttpEntity requestEntity = new HttpEntity(body, (MultiValueMap)headers);
        ResponseEntity responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class, new Object[0]);
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            this.apiKeyCache.invalidateToken();
            String apiKey = this.apiKeyCache.getToken();
            headers.set(HEADER_AUTHORIZATION, apiKey);
            requestEntity = new HttpEntity(body, (MultiValueMap)headers);
            responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class, new Object[0]);
        }
        return responseEntity;
    }

    private void processRuleItems(AidocauditRuleParamDTO rule) {
        List<RuleItemTreeDTO> ruleItemList = rule.getRuleItemList();
        ArrayList<AidocauditRuleItemEO> ruleItemUpdateEOList = new ArrayList<AidocauditRuleItemEO>();
        ArrayList<AidocauditRuleItemEO> ruleItemInsertEOList = new ArrayList<AidocauditRuleItemEO>();
        int ruleCount = 0;
        for (int i = 0; i < ruleItemList.size(); ++i) {
            RuleItemTreeDTO ruleItem = ruleItemList.get(i);
            AidocauditRuleItemEO ruleItemEO = new AidocauditRuleItemEO();
            BeanUtils.copyProperties(ruleItem, (Object)ruleItemEO);
            if (ruleItem.getId() == null) {
                ruleItemEO.setRuleId(rule.getId());
                ruleItemInsertEOList.add(ruleItemEO);
            } else {
                ruleItemUpdateEOList.add(ruleItemEO);
            }
            String ordinal1 = String.format("%03d", i + 1);
            ruleItemEO.setOrdinal(ordinal1);
            ruleItemEO.setUpdateTime(new Date());
            ruleItemEO.setUpdateUser(NpContextHolder.getContext().getUserId());
            String scoreItemId = ruleItemEO.getScoreItemId();
            if (!StringUtils.hasText(scoreItemId)) {
                scoreItemId = UUIDUtils.newUUIDStr();
            }
            List<RuleItemTreeDTO> children = ruleItem.getChildren();
            for (int j = 0; j < children.size(); ++j) {
                ++ruleCount;
                RuleItemTreeDTO child = children.get(j);
                AidocauditRuleItemEO childEO = new AidocauditRuleItemEO();
                BeanUtils.copyProperties(child, (Object)childEO);
                String ordinal2 = String.format("%03d%03d", i + 1, j + 1);
                childEO.setOrdinal(ordinal2);
                if (childEO.getId() == null) {
                    childEO.setId(UUIDUtils.newUUIDStr());
                    childEO.setRuleId(rule.getId());
                    ruleItemInsertEOList.add(childEO);
                } else {
                    ruleItemUpdateEOList.add(childEO);
                }
                childEO.setParentScoreItemId(scoreItemId);
                if (!StringUtils.hasText(childEO.getScoreItemId())) {
                    childEO.setScoreItemId(UUIDUtils.newUUIDStr());
                }
                childEO.setUpdateTime(new Date());
                childEO.setUpdateUser(NpContextHolder.getContext().getUserId());
            }
        }
        List<AidocauditRuleItemEO> oldRuleList = this.ruleItemDao.getByRuleId(rule.getId());
        List newIds = ruleItemUpdateEOList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        List deleteList = oldRuleList.stream().filter(old -> !newIds.contains(old.getId())).collect(Collectors.toList());
        this.ruleItemDao.deleteBatch(deleteList);
        this.ruleItemDao.updateBatch(ruleItemUpdateEOList);
        this.ruleItemDao.addBatch(ruleItemInsertEOList);
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)rule.getId()));
        aidocauditRuleEO.setRuleCount(ruleCount);
        this.ruleDao.update((BaseEntity)aidocauditRuleEO);
    }

    @Override
    public BusinessResponseEntity<List<AidocauditRuleEO>> queryReportType(Integer type) {
        return BusinessResponseEntity.ok(this.ruleDao.list(type));
    }

    @Override
    public BusinessResponseEntity<AidocauditRuleEO> query(String ruleId) {
        return BusinessResponseEntity.ok((Object)this.ruleDao.get((Serializable)((Object)ruleId)));
    }

    @Override
    public BusinessResponseEntity<Boolean> updateRule(AidocauditRuleParamDTO rule) {
        String id = rule.getId();
        this.self.delete(id, false);
        AidocauditRuleEO oldRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)id));
        AidocauditRuleEO ruleEO = new AidocauditRuleEO();
        BeanUtils.copyProperties(rule, (Object)ruleEO);
        ruleEO.setId(id);
        String fileNameWithoutExtension = rule.getRuleAttachmentName().substring(0, rule.getRuleAttachmentName().lastIndexOf(46));
        ruleEO.setRuleAttachmentName(fileNameWithoutExtension);
        ruleEO.setRuleStatus(0);
        ruleEO.setUpdateUser(NpContextHolder.getContext().getUserId());
        ruleEO.setUpdateTime(new Date());
        ruleEO.setCreateUser(oldRuleEO.getCreateUser());
        ruleEO.setCreateTime(oldRuleEO.getCreateTime());
        int affectedRow = this.ruleDao.update((BaseEntity)ruleEO);
        return BusinessResponseEntity.ok((Object)(affectedRow > 0 ? 1 : 0));
    }

    private Map<String, Object> constructUpdateBodyData(AidocauditRuleEO aidocauditRuleEO, List<RuleItemTreeDTO> ruleItemList) {
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("temp_id", aidocauditRuleEO.getScoreTmplId());
        body.put("temp_name", aidocauditRuleEO.getRuleName());
        body.put("biz_dim", "UNIT");
        body.put("score_res_id", "");
        List<Map<String, Object>> scoreItems = this.convertRuleItemsToMap(ruleItemList);
        body.put("score_items", scoreItems);
        return body;
    }

    private List<Map<String, Object>> convertRuleItemsToMap(List<RuleItemTreeDTO> ruleItemList) {
        ArrayList<Map<String, Object>> scoreItems = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(ruleItemList)) {
            return scoreItems;
        }
        for (RuleItemTreeDTO ruleItem : ruleItemList) {
            Map<String, Object> scoreItem = this.convertRuleItemToMap(ruleItem);
            scoreItems.add(scoreItem);
        }
        return scoreItems;
    }

    private Map<String, Object> convertRuleItemToMap(RuleItemTreeDTO ruleItem) {
        HashMap<String, Object> scoreItem = new HashMap<String, Object>();
        scoreItem.put("score_item_id", ruleItem.getId());
        scoreItem.put("score_item_name", ruleItem.getScoreItemName());
        scoreItem.put("full_score", ruleItem.getFullScore());
        scoreItem.put("parent_id", ruleItem.getParentScoreItemId());
        scoreItem.put("prompt", ruleItem.getPrompt());
        List<RuleItemTreeDTO> childrenDTO = ruleItem.getChildren();
        if (childrenDTO != null && !childrenDTO.isEmpty()) {
            List<Map<String, Object>> children = this.convertRuleItemsToMap(childrenDTO);
            scoreItem.put("children", children);
        }
        return scoreItem;
    }

    @Override
    public void handerErrorMessage(MQRuleResultDTO resultDTO) {
        String ruleAttachId = resultDTO.getFileAttachId();
        List<AidocauditRuleEO> ruleEOList = this.ruleDao.queryByRuleAttachIdAndStatus(ruleAttachId);
        AidocauditRuleEO rule = ruleEOList.get(0);
        rule.setRuleStatus(3);
        this.ruleDao.update((BaseEntity)rule);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void handerMessage(MQRuleResultDTO resultDTO) {
        String ruleAttachId = resultDTO.getFileAttachId();
        List<AidocauditRuleEO> ruleEOList = this.ruleDao.queryByRuleAttachIdAndStatus(ruleAttachId);
        AidocauditRuleEO rule = ruleEOList.get(0);
        rule.setRuleStatus(2);
        rule.setScoreTmplId(resultDTO.getTempId());
        rule.setTotalScore(resultDTO.getTotalScore());
        List<MQRuleItemResultDTO> scoreItems = resultDTO.getScoreItems();
        int ruleCount = 0;
        Date createTime = new Date();
        ArrayList<AidocauditRuleItemEO> ruleItemEOList = new ArrayList<AidocauditRuleItemEO>();
        for (int i = 0; i < scoreItems.size(); ++i) {
            MQRuleItemResultDTO item = scoreItems.get(i);
            AidocauditRuleItemEO ruleItemEO = this.structureRuleItem(item, rule.getId(), createTime);
            String ordinal1 = String.format("%03d", i + 1);
            ruleItemEO.setOrdinal(ordinal1);
            ruleItemEOList.add(ruleItemEO);
            List<MQRuleItemResultDTO> children = item.getChildren();
            ruleCount += children.size();
            for (int j = 0; j < children.size(); ++j) {
                MQRuleItemResultDTO ruleItemChild = children.get(j);
                AidocauditRuleItemEO ruleItemChildEO = this.structureRuleItem(ruleItemChild, rule.getId(), createTime);
                String ordinal2 = String.format("%03d%03d", i + 1, j + 1);
                ruleItemChildEO.setOrdinal(ordinal2);
                ruleItemChildEO.setParentScoreItemId(item.getScoreItemId());
                ruleItemEOList.add(ruleItemChildEO);
            }
        }
        rule.setRuleCount(ruleCount);
        List<AidocauditRuleItemEO> oldRuleItemList = this.ruleItemDao.getByRuleId(rule.getId());
        if (!CollectionUtils.isEmpty(oldRuleItemList)) {
            this.ruleItemDao.deleteBatch(oldRuleItemList);
        }
        this.ruleDao.update((BaseEntity)rule);
        this.ruleItemDao.addBatch(ruleItemEOList);
    }

    @Override
    public RuleDetailDTO queryRuleItem(String ruleId) {
        RuleDetailDTO ruleDetailDTO = new RuleDetailDTO();
        AidocauditRuleEO aidocauditRuleEO = (AidocauditRuleEO)this.ruleDao.get((Serializable)((Object)ruleId));
        ruleDetailDTO.setRuleId(ruleId);
        ruleDetailDTO.setTotalScore(aidocauditRuleEO.getTotalScore());
        List<AidocauditRuleItemEO> ruleItemEOList = this.ruleItemDao.getByRuleId(ruleId);
        long gourpCount = ruleItemEOList.stream().filter(ruleItem -> ruleItem.getParentScoreItemId() == null || ruleItem.getParentScoreItemId().isEmpty()).count();
        ruleDetailDTO.setGroupCount((int)gourpCount);
        ruleDetailDTO.setItemCount(aidocauditRuleEO.getRuleCount());
        List<RuleItemTreeDTO> ruleItemTreeDTOList = BuildTreeUtil.buildRuleItemTree(ruleItemEOList);
        ruleDetailDTO.setRuleItemList(ruleItemTreeDTOList);
        return ruleDetailDTO;
    }

    private AidocauditRuleItemEO structureRuleItem(MQRuleItemResultDTO item, String ruleId, Date createTime) {
        AidocauditRuleItemEO ruleItemEO = new AidocauditRuleItemEO();
        ruleItemEO.setId(UUIDUtils.newUUIDStr());
        ruleItemEO.setRuleId(ruleId);
        ruleItemEO.setScoreItemId(item.getScoreItemId());
        ruleItemEO.setScoreItemName(item.getScoreItemName());
        ruleItemEO.setFullScore(item.getFullScore());
        ruleItemEO.setPrompt(item.getPrompt());
        ruleItemEO.setParentScoreItemId(item.getParentId());
        ruleItemEO.setCreateTime(createTime);
        return ruleItemEO;
    }

    private Map<String, Object> constructBodyData(AidocauditRuleEO rule) {
        HashMap<String, Object> bodyDate = new HashMap<String, Object>();
        if (rule.getScoreTmplId() != null) {
            bodyDate.put("temp_id", rule.getScoreTmplId());
        }
        bodyDate.put("temp_name", rule.getRuleName());
        bodyDate.put("file_attach_id", rule.getRuleAttachmentId());
        bodyDate.put("file_name", rule.getRuleAttachmentName());
        ObjectInfo objectInfo = new ObjectInfo();
        try {
            objectInfo = this.objectService.getObjectInfo("JTABLEAREA", rule.getRuleAttachmentId());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6587\u4ef6\u62a5\u9519", (Throwable)e);
        }
        String[] splitStr = objectInfo.getName().split("\\.");
        String fileType = splitStr[splitStr.length - 1];
        bodyDate.put("file_type", fileType);
        bodyDate.put("file_size", objectInfo.getSize());
        bodyDate.put("file_path", "/template/" + rule.getRuleAttachmentName());
        bodyDate.put("biz_dim", "UNIT");
        return bodyDate;
    }
}

