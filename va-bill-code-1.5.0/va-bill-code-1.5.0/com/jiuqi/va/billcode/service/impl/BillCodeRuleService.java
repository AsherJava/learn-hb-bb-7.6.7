/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.billcode.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.billcode.dao.IBillCodeCheckExistDao;
import com.jiuqi.va.billcode.dao.IBillCodeRuleDao;
import com.jiuqi.va.billcode.domain.BillCodeCheckExistDO;
import com.jiuqi.va.billcode.service.IBillCodeRuleService;
import com.jiuqi.va.domain.billcode.BillCodeRuleDO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillCodeRuleService
implements IBillCodeRuleService {
    @Autowired
    private IBillCodeRuleDao billCodeRuleDao;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private IBillCodeCheckExistDao iBillCodeCheckExistDao;

    @Override
    public BillCodeRuleDTO getRuleByUniqueCode(String uniqueCode) throws Exception {
        return this.getRuleByUniqueCodeUnCheck(uniqueCode, true);
    }

    @Override
    public BillCodeRuleDTO getRuleByUniqueCodeUnCheck(String uniqueCode, boolean chechExist) throws Exception {
        BillCodeRuleDO codeRuleDO = new BillCodeRuleDO();
        codeRuleDO.setUniqueCode(uniqueCode);
        codeRuleDO = (BillCodeRuleDO)this.billCodeRuleDao.selectOne(codeRuleDO);
        if (codeRuleDO == null) {
            return null;
        }
        BillCodeRuleDTO codeRuleDTO = new BillCodeRuleDTO();
        codeRuleDTO.setConstant(codeRuleDO.getConstant());
        codeRuleDTO.setId(codeRuleDO.getId());
        codeRuleDTO.setRuleData(codeRuleDO.getRuleData());
        codeRuleDTO.setRuleValue(codeRuleDO.getRuleData());
        codeRuleDTO.setUniqueCode(codeRuleDO.getUniqueCode());
        codeRuleDTO.setGenerateOpt(codeRuleDO.getGenerateOpt());
        if (chechExist) {
            codeRuleDTO.setBillCodeExist(this.checkBillCodeExist(uniqueCode));
        }
        return codeRuleDTO;
    }

    private boolean checkBillCodeExist(String uniqueCode) throws JsonMappingException, JsonProcessingException {
        MetaDesignDTO mdDTO = new MetaDesignDTO();
        mdDTO.setDefineCode(uniqueCode);
        R r = this.metaDataClient.getMetaDesign(mdDTO);
        String tableName = null;
        if ((Integer)r.get((Object)"code") == 0) {
            ObjectMapper mapper = new ObjectMapper();
            String data = String.valueOf(r.get((Object)"data"));
            MetaDesignDTO metaDesignDTO = (MetaDesignDTO)mapper.readValue(data, MetaDesignDTO.class);
            String datas = metaDesignDTO.getDatas();
            if (!StringUtils.hasText(datas)) {
                return false;
            }
            List jsonArray = (List)JSONUtil.parseMap((String)datas).get("plugins");
            if (jsonArray != null) {
                for (Map json : jsonArray) {
                    if (json.get("tables") == null) continue;
                    List tableJsonArray = (List)json.get("tables");
                    Iterator tableIterator = tableJsonArray.iterator();
                    if (!tableIterator.hasNext()) break;
                    Map jsonTable = (Map)tableIterator.next();
                    tableName = (String)jsonTable.get("name");
                    break;
                }
            }
        }
        BillCodeCheckExistDO bceDO = new BillCodeCheckExistDO();
        bceDO.setTableName(tableName);
        bceDO.setDefineCode(uniqueCode);
        bceDO.setTenantName(ShiroUtil.getTenantName());
        int count = this.iBillCodeCheckExistDao.selectExistCount(bceDO);
        return count > 0;
    }

    @Override
    public BillCodeRuleDTO addRule(BillCodeRuleDTO billCodeRuleDTO) throws Exception {
        billCodeRuleDTO.setId(UUID.randomUUID());
        billCodeRuleDTO.setRuleData(billCodeRuleDTO.getRuleValue());
        if (this.billCodeRuleDao.insert(billCodeRuleDTO) > 0) {
            return billCodeRuleDTO;
        }
        return null;
    }

    @Override
    public BillCodeRuleDTO editRule(BillCodeRuleDTO billCodeRuleDTO) throws Exception {
        billCodeRuleDTO.setRuleData(billCodeRuleDTO.getRuleValue());
        if (this.billCodeRuleDao.updateByPrimaryKey(billCodeRuleDTO) > 0) {
            return billCodeRuleDTO;
        }
        return null;
    }

    @Override
    public PageVO<MetaTreeInfoDTO> gatherMetaTree(TenantDO tenantDO) {
        return this.metaDataClient.getAllMetas(tenantDO);
    }

    @Override
    public boolean hasRuleByConstant(String constant) {
        BillCodeRuleDO codeRuleDO = new BillCodeRuleDO();
        codeRuleDO.setConstant(constant);
        return this.billCodeRuleDao.selectCount(codeRuleDO) > 0;
    }

    @Override
    public List<BillCodeRuleDTO> getRuleAll() throws Exception {
        List codeRuleDOs = this.billCodeRuleDao.select(new BillCodeRuleDO());
        ArrayList<BillCodeRuleDTO> codeRuleDTOs = new ArrayList<BillCodeRuleDTO>();
        for (BillCodeRuleDO codeRuleDO : codeRuleDOs) {
            BillCodeRuleDTO codeRuleDTO = new BillCodeRuleDTO();
            codeRuleDTO.setConstant(codeRuleDO.getConstant());
            codeRuleDTO.setId(codeRuleDO.getId());
            codeRuleDTO.setRuleValue(codeRuleDO.getRuleData());
            codeRuleDTO.setUniqueCode(codeRuleDO.getUniqueCode());
            codeRuleDTO.setGenerateOpt(codeRuleDO.getGenerateOpt());
            codeRuleDTOs.add(codeRuleDTO);
        }
        return codeRuleDTOs;
    }

    @Override
    public String getUniqueCodeByBillCode(String billCode) throws Exception {
        String uniqueCode = "";
        List<BillCodeRuleDTO> codeRuleDTOs = this.getRuleAll();
        if (codeRuleDTOs.size() == 0) {
            return uniqueCode;
        }
        int maxLength = 0;
        for (BillCodeRuleDTO billCodeRuleDTO : codeRuleDTOs) {
            if (!billCode.startsWith(billCodeRuleDTO.getConstant()) || billCodeRuleDTO.getConstant().length() <= maxLength) continue;
            maxLength = billCodeRuleDTO.getConstant().length();
            uniqueCode = billCodeRuleDTO.getUniqueCode();
        }
        return uniqueCode;
    }
}

