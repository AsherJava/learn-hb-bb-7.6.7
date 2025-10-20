/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.biz.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.dao.BizCommonRuleDao;
import com.jiuqi.va.biz.dao.BizCommonRuleInfoDao;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDTO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleInfoDO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleVO;
import com.jiuqi.va.biz.domain.commrule.BizRuleDTO;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.service.BizCommonRuleService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class BizCommonRuleServiceImpl
implements BizCommonRuleService {
    private static final Logger logger = LoggerFactory.getLogger(BizCommonRuleServiceImpl.class);
    @Autowired
    private BizCommonRuleDao bizCommonRuleDao;
    @Autowired
    private AuthUserClient userClient;
    @Autowired
    private BizCommonRuleInfoDao bizCommonRuleInfoDao;

    @Override
    public List<Integer> checkName(BizCommonRuleVO bizCommonRuleVO) {
        String id = ShiroUtil.getUser().getId();
        BizCommonRuleDO bizCommonRuleDO = new BizCommonRuleDO();
        bizCommonRuleDO.setCreateuser(id);
        List select = this.bizCommonRuleDao.select((Object)bizCommonRuleDO);
        List<String> rulenames = bizCommonRuleVO.getRulenames();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < rulenames.size(); ++i) {
            String s = rulenames.get(i);
            if (!select.stream().anyMatch(e -> e.getRulename().equals(s))) continue;
            result.add(i);
        }
        return result;
    }

    @Override
    public String saveRule(BizCommonRuleVO bizCommonRuleVO) {
        List<BizRuleDTO> rules = bizCommonRuleVO.getRules();
        ModelDefine modelDefine = bizCommonRuleVO.getModelDefine();
        DataDefineImpl dataDefine = (DataDefineImpl)modelDefine.getPlugins().get("data");
        String masterTable = ((DataTableDefineImpl)((DataTableNodeContainerImpl)dataDefine.getTables()).getMasterTable()).getTableName();
        ModelDataContext context = new ModelDataContext(modelDefine);
        String userId = ShiroUtil.getUser().getId();
        List<Integer> updateindex = bizCommonRuleVO.getUpdateindex();
        StringBuilder errorMSG = new StringBuilder();
        for (int index = 0; index < rules.size(); ++index) {
            IExpression iExpression;
            DataTableDefineImpl dataTableDefine;
            int i;
            UUID objectId;
            HashMap<String, Object> ruleInfo = new HashMap<String, Object>();
            BizRuleDTO rule = rules.get(index);
            String objectType = rule.getObjectType();
            if ("field".equals(objectType)) {
                objectId = rule.getObjectId();
                block3: for (i = 0; i < ((ListContainerImpl)((Object)dataDefine.getTables())).size() && !ruleInfo.containsKey("tableName"); ++i) {
                    dataTableDefine = (DataTableDefineImpl)((ListContainerImpl)((Object)dataDefine.getTables())).get(i);
                    for (int i1 = 0; i1 < ((NamedContainerImpl)dataTableDefine.getFields()).size(); ++i1) {
                        DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)((NamedContainerImpl)dataTableDefine.getFields()).get(i1);
                        if (!dataFieldDefine.getId().equals(objectId)) continue;
                        ruleInfo.put("tableName", dataTableDefine.getTableName());
                        ruleInfo.put("fieldName", dataFieldDefine.getFieldName());
                        continue block3;
                    }
                }
            }
            if ("table".equals(objectType)) {
                objectId = rule.getObjectId();
                for (i = 0; i < ((ListContainerImpl)((Object)dataDefine.getTables())).size(); ++i) {
                    dataTableDefine = (DataTableDefineImpl)((ListContainerImpl)((Object)dataDefine.getTables())).get(i);
                    if (!dataTableDefine.getId().equals(objectId)) continue;
                    ruleInfo.put("tableName", dataTableDefine.getTableName());
                    break;
                }
            }
            if ((iExpression = this.parseExpression(context, rule)) == null) continue;
            IASTNode child = iExpression.getChild(0);
            ArrayList<Integer> masterIndex = new ArrayList<Integer>();
            ruleInfo.put("masterLength", masterTable.length());
            ruleInfo.put("masterIndex", masterIndex);
            ruleInfo.put("expression", rule.getExpression());
            ruleInfo.put("ruleName", rule.getTitle());
            ruleInfo.put("checkMessage", rule.getCheckMessage());
            ruleInfo.put("propertyType", rule.getPropertyType());
            ruleInfo.put("objectType", rule.getObjectType());
            ruleInfo.put("triggerType", rule.getTriggerType());
            ruleInfo.put("formulaType", (Object)rule.getFormulaType());
            this.handleMasterTable(child, masterTable, masterIndex);
            try {
                int insert1;
                BizCommonRuleInfoDO bizCommonRuleInfoDO;
                int insert;
                UUID uuid = null;
                if (!CollectionUtils.isEmpty(updateindex) && updateindex.contains(index)) {
                    BizCommonRuleDO query = new BizCommonRuleDO();
                    query.setCreateuser(userId);
                    query.setRulename(rule.getTitle());
                    List select = this.bizCommonRuleDao.select((Object)query);
                    if (!CollectionUtils.isEmpty(select)) {
                        BizCommonRuleDO bizCommonRuleDO = (BizCommonRuleDO)((Object)select.get(0));
                        uuid = bizCommonRuleDO.getId();
                    }
                }
                BizCommonRuleDO bizCommonRuleDO = new BizCommonRuleDO();
                bizCommonRuleDO.setBiztype(bizCommonRuleVO.getBiztype());
                bizCommonRuleDO.setCreatetime(new Date());
                bizCommonRuleDO.setDefinecode(bizCommonRuleVO.getDefinecode());
                bizCommonRuleDO.setDefinetitle(bizCommonRuleVO.getDefinetitle());
                bizCommonRuleDO.setObjecttype(rule.getObjectType());
                bizCommonRuleDO.setPropertytype(rule.getPropertyType());
                bizCommonRuleDO.setRemark(rule.getRemark());
                bizCommonRuleDO.setVer(System.currentTimeMillis());
                bizCommonRuleDO.setCreateuser(userId);
                bizCommonRuleDO.setRulename(rule.getTitle());
                if (uuid == null) {
                    uuid = UUID.randomUUID();
                    bizCommonRuleDO.setId(uuid);
                    insert = this.bizCommonRuleDao.insert((Object)bizCommonRuleDO);
                    if (insert == 0) {
                        logger.error("\u5e38\u7528\u516c\u5f0f\u4fdd\u5b58\u5931\u8d25" + rule.getTitle());
                        errorMSG.append(rule.getTitle()).append(",");
                        continue;
                    }
                    bizCommonRuleInfoDO = new BizCommonRuleInfoDO();
                    bizCommonRuleInfoDO.setId(uuid);
                    bizCommonRuleInfoDO.setRuleinfo(JSONUtil.toJSONString(ruleInfo));
                    insert1 = this.bizCommonRuleInfoDao.insert((Object)bizCommonRuleInfoDO);
                    if (insert1 != 0) continue;
                    logger.error("\u5e38\u7528\u516c\u5f0f\u4fe1\u606f\u4fdd\u5b58\u5931\u8d25" + rule.getTitle());
                    errorMSG.append(rule.getTitle()).append(",");
                    continue;
                }
                bizCommonRuleDO.setId(uuid);
                insert = this.bizCommonRuleDao.updateByPrimaryKey((Object)bizCommonRuleDO);
                if (insert == 0) {
                    logger.error("\u5e38\u7528\u516c\u5f0f\u66f4\u65b0\u5931\u8d25" + rule.getTitle());
                    errorMSG.append(rule.getTitle()).append(",");
                    continue;
                }
                bizCommonRuleInfoDO = new BizCommonRuleInfoDO();
                bizCommonRuleInfoDO.setId(uuid);
                bizCommonRuleInfoDO.setRuleinfo(JSONUtil.toJSONString(ruleInfo));
                insert1 = this.bizCommonRuleInfoDao.updateByPrimaryKey((Object)bizCommonRuleInfoDO);
                if (insert1 != 0) continue;
                logger.error("\u5e38\u7528\u516c\u5f0f\u4fe1\u606f\u66f4\u65b0\u5931\u8d25" + rule.getTitle());
                errorMSG.append(rule.getTitle()).append(",");
                continue;
            }
            catch (Exception e) {
                logger.error("\u5e38\u7528\u516c\u5f0f\u4fdd\u5b58\u5931\u8d25" + rule.getTitle() + e.getMessage(), e);
                errorMSG.append(rule.getTitle()).append(",");
            }
        }
        if (errorMSG.length() != 0) {
            return errorMSG + "\u4fdd\u5b58\u5931\u8d25";
        }
        return null;
    }

    @Override
    public List<BizCommonRuleDTO> list(BizCommonRuleVO bizCommonRuleVO) {
        boolean flag;
        BizCommonRuleDTO commonRuleDTO;
        Iterator<BizCommonRuleDTO> iterator;
        Map<String, Object> queryParam = bizCommonRuleVO.getQueryParam();
        BizCommonRuleVO query = new BizCommonRuleVO();
        if (bizCommonRuleVO.isCurUser()) {
            query.setCreateuser(ShiroUtil.getUser().getId());
            query.setCurUser(true);
        }
        query.setBiztype(bizCommonRuleVO.getBiztype());
        List<BizCommonRuleDTO> bizCommonRuleDTO = this.bizCommonRuleDao.listByParam(query);
        this.handleUser(bizCommonRuleDTO);
        if (CollectionUtils.isEmpty(queryParam)) {
            return bizCommonRuleDTO;
        }
        if (queryParam.containsKey("objecttype")) {
            List objecttype = (List)queryParam.get("objecttype");
            iterator = bizCommonRuleDTO.iterator();
            while (iterator.hasNext()) {
                commonRuleDTO = iterator.next();
                flag = this.handleObjectType(commonRuleDTO, objecttype);
                if (flag) continue;
                iterator.remove();
            }
        }
        if (queryParam.containsKey("propertytype")) {
            List propertytype = (List)queryParam.get("propertytype");
            iterator = bizCommonRuleDTO.iterator();
            while (iterator.hasNext()) {
                commonRuleDTO = iterator.next();
                flag = this.handlePropertyType(commonRuleDTO, propertytype);
                if (flag) continue;
                iterator.remove();
            }
        }
        if (queryParam.containsKey("triggerType")) {
            List triggerType = (List)queryParam.get("triggerType");
            iterator = bizCommonRuleDTO.iterator();
            while (iterator.hasNext()) {
                commonRuleDTO = iterator.next();
                flag = this.handleTriggerType(commonRuleDTO, triggerType);
                if (flag) continue;
                iterator.remove();
            }
        }
        if (queryParam.containsKey("tableFields")) {
            List tableFields = (List)queryParam.get("tableFields");
            iterator = bizCommonRuleDTO.iterator();
            while (iterator.hasNext()) {
                commonRuleDTO = iterator.next();
                flag = this.handleCurItem(commonRuleDTO, tableFields);
                if (flag) continue;
                iterator.remove();
            }
        }
        if (queryParam.containsKey("specialParam")) {
            List specialParam = (List)queryParam.get("specialParam");
            iterator = bizCommonRuleDTO.iterator();
            while (iterator.hasNext()) {
                commonRuleDTO = iterator.next();
                flag = this.handleSpecialParam(commonRuleDTO, specialParam);
                if (flag) continue;
                iterator.remove();
            }
        }
        return bizCommonRuleDTO;
    }

    private boolean handleTriggerType(BizCommonRuleDTO commonRuleDTO, List<String> triggerType) {
        Object triggerType1;
        String ruleinfo = commonRuleDTO.getRuleinfo();
        Map stringObjectMap = JSONUtil.parseMap((String)ruleinfo);
        return stringObjectMap.containsKey("triggerType") && triggerType.contains(triggerType1 = stringObjectMap.get("triggerType"));
    }

    private boolean handleSpecialParam(BizCommonRuleDTO commonRuleDTO, List<String> spacialParam) {
        if (!"action".equals(commonRuleDTO.getObjecttype())) {
            return true;
        }
        String ruleinfo = commonRuleDTO.getRuleinfo();
        Map stringObjectMap = JSONUtil.parseMap((String)ruleinfo);
        if (stringObjectMap.containsKey("triggerType")) {
            Object triggerType = stringObjectMap.get("triggerType");
            return spacialParam.contains(triggerType);
        }
        return false;
    }

    private boolean handlePropertyType(BizCommonRuleDTO commonRuleDTO, List<String> propertytype) {
        return propertytype.contains(commonRuleDTO.getPropertytype());
    }

    private boolean handleObjectType(BizCommonRuleDTO commonRuleDTO, List<String> objecttype) {
        return objecttype.contains(commonRuleDTO.getObjecttype());
    }

    private boolean handleCurItem(BizCommonRuleDTO commonRuleDTO, List<Map<String, Object>> tableFields) {
        Map stringObjectMap = JSONUtil.parseMap((String)commonRuleDTO.getRuleinfo());
        if (!stringObjectMap.containsKey("tableName")) {
            return true;
        }
        String tableName = (String)stringObjectMap.get("tableName");
        for (Map<String, Object> tableField : tableFields) {
            if (!tableField.get("name").equals(tableName)) continue;
            if (!stringObjectMap.containsKey("fieldName")) {
                return true;
            }
            List children = (List)tableField.get("children");
            for (Map child : children) {
                if (!child.get("name").equals(stringObjectMap.get("fieldName"))) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private void handleUser(List<BizCommonRuleDTO> bizCommonRuleDTO) {
        HashMap<String, UserDO> userMap = new HashMap<String, UserDO>();
        String id = ShiroUtil.getUser().getId();
        for (BizCommonRuleDTO item : bizCommonRuleDTO) {
            item.setCanDel(item.getCreateuser().equals(id));
            if (userMap.containsKey(item.getCreateuser())) {
                item.setCreateuser(((UserDO)userMap.get(item.getCreateuser())).getName());
                continue;
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setId(item.getCreateuser());
            UserDO userDO = this.userClient.get(userDTO);
            if (userDO == null) continue;
            item.setCreateuser(userDO.getName());
            userMap.put(item.getCreateuser(), userDO);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String delete(BizCommonRuleVO vo) {
        if (vo.getId() == null) {
            return "id\u4e0d\u80fd\u4e3a\u7a7a";
        }
        BizCommonRuleDO bizCommonRuleDO = new BizCommonRuleDO();
        bizCommonRuleDO.setId(vo.getId());
        int delete = this.bizCommonRuleDao.delete((Object)bizCommonRuleDO);
        if (delete == 0) {
            return "\u5e38\u7528\u89c4\u5219\u5220\u9664\u5931\u8d25";
        }
        BizCommonRuleInfoDO bizCommonRuleInfoDO = new BizCommonRuleInfoDO();
        bizCommonRuleInfoDO.setId(vo.getId());
        int delete1 = this.bizCommonRuleInfoDao.delete((Object)bizCommonRuleInfoDO);
        if (delete1 == 0) {
            return "\u5e38\u7528\u89c4\u5219\u4fe1\u606f\u5220\u9664\u5931\u8d25";
        }
        return null;
    }

    private void handleMasterTable(IASTNode curNode, String masterTable, List<Integer> masterIndex) {
        ModelNode modelNode;
        String tableName;
        if (curNode instanceof ModelNode && masterTable.equals(tableName = (modelNode = (ModelNode)curNode).getTableName())) {
            Token token = modelNode.getToken();
            int index = token.index();
            masterIndex.add(index - masterTable.length());
        }
        for (int i = 0; i < curNode.childrenSize(); ++i) {
            this.handleMasterTable(curNode.getChild(i), masterTable, masterIndex);
        }
    }

    private IExpression parseExpression(ModelDataContext context, BizRuleDTO ruleDTO) {
        IExpression iExpression;
        try {
            iExpression = ModelFormulaHandle.getInstance().parse(context, ruleDTO.getExpression(), ruleDTO.getFormulaType());
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return iExpression;
    }
}

