/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.dao.BillRuleOptionDao;
import com.jiuqi.va.bill.dao.BillRuleOptionItemDao;
import com.jiuqi.va.bill.domain.BillRuleOptionConsts;
import com.jiuqi.va.bill.domain.option.BillRuleOptionDO;
import com.jiuqi.va.bill.domain.option.BillRuleOptionItemDO;
import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.bill.service.BillRuleOptionService;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class BillRuleOptionServiceImpl
implements BillRuleOptionService {
    private static final Logger log = LoggerFactory.getLogger(BillRuleOptionServiceImpl.class);
    @Autowired
    private BillRuleOptionDao billRuleOptionDao;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private BillRuleOptionItemDao billRuleOptionItemDao;

    @Override
    public List<BillRuleOptionVO> list(OptionItemDTO param) {
        ArrayList<BillRuleOptionVO> endList = new ArrayList<BillRuleOptionVO>();
        String unitCode = StringUtils.hasText(param.getUnitcode()) ? param.getUnitcode() : ShiroUtil.getUser().getLoginUnit();
        String tenantName = Env.getTenantName();
        PageVO orgs = new PageVO();
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setCode(unitCode);
        orgs = this.orgDataClient.list(orgDTO);
        if (orgs.getTotal() == 0) {
            return endList;
        }
        HashMap<String, BillRuleOptionDO> vals = new HashMap<String, BillRuleOptionDO>();
        BillRuleOptionDO query = new BillRuleOptionDO();
        query.setUnitcode(unitCode);
        List list = this.billRuleOptionDao.select((Object)query);
        if (list != null && list.size() > 0) {
            for (BillRuleOptionDO optionDO : list) {
                if (!StringUtils.hasText(optionDO.getVal())) continue;
                vals.put(optionDO.getName(), optionDO);
            }
        }
        LinkedHashMap<String, BillRuleOptionVO> infos = BillRuleOptionConsts.optionFoMap(param.getGroupName());
        for (BillRuleOptionVO optionItemVO : infos.values()) {
            if (StringUtils.hasText(param.getSearchKey()) && !optionItemVO.getName().contains(param.getSearchKey()) && !optionItemVO.getTitle().contains(param.getSearchKey()) || StringUtils.hasText(param.getName()) && !optionItemVO.getName().equals(param.getName())) continue;
            if (vals.containsKey(optionItemVO.getName())) {
                BillRuleOptionDO hadDo = (BillRuleOptionDO)((Object)vals.get(optionItemVO.getName()));
                optionItemVO.setVal(hadDo.getVal());
                optionItemVO.setModifyuser(hadDo.getModifyuser());
                optionItemVO.setModifytime(hadDo.getModifytime());
                optionItemVO.setContronflag(hadDo.getContronflag());
                if (hadDo.getContronflag() == null) {
                    optionItemVO.setContronflag(0);
                }
                BillRuleOptionItemDO billRuleOptionItemDO = new BillRuleOptionItemDO();
                billRuleOptionItemDO.setParentid(hadDo.getId());
                List select = this.billRuleOptionItemDao.select((Object)billRuleOptionItemDO);
                ArrayList<String> storageValue = new ArrayList<String>();
                for (BillRuleOptionItemDO ruleOptionItemDO : select) {
                    storageValue.add(ruleOptionItemDO.getVal());
                }
                optionItemVO.setStorageValue(((Object)storageValue).toString());
            } else {
                optionItemVO.setVal(optionItemVO.getDefauleVal());
                optionItemVO.setContronflag(0);
            }
            endList.add(optionItemVO);
        }
        boolean isCtrlModel = param.getControlModel() != null && param.getControlModel() != false;
        List<String> parentNames = null;
        if (isCtrlModel) {
            parentNames = this.getOrgParents(unitCode, tenantName);
        }
        boolean hasFlag = false;
        String name = null;
        BillRuleOptionDO currData = null;
        BillRuleOptionDO record = new BillRuleOptionDO();
        for (BillRuleOptionVO optionFoVO : endList) {
            name = optionFoVO.getName();
            record.setName(name);
            if (isCtrlModel) {
                for (int i = 0; i < parentNames.size(); ++i) {
                    hasFlag = false;
                    record.setUnitcode(parentNames.get(i));
                    currData = (BillRuleOptionDO)((Object)this.billRuleOptionDao.selectOne((Object)record));
                    if (currData == null || currData.getContronflag() == null || currData.getContronflag() != 1) continue;
                    hasFlag = true;
                    break;
                }
            }
            if (!hasFlag) {
                if (isCtrlModel) {
                    record.setUnitcode(unitCode);
                } else {
                    record.setUnitcode(null);
                }
                currData = (BillRuleOptionDO)((Object)this.billRuleOptionDao.selectOne((Object)record));
            }
            if (currData == null) continue;
            if (unitCode.equals(currData.getUnitcode())) {
                optionFoVO.setEditable(true);
            } else {
                BillRuleOptionItemDO billRuleOptionItemDO = new BillRuleOptionItemDO();
                billRuleOptionItemDO.setParentid(currData.getId());
                List select = this.billRuleOptionItemDao.select((Object)billRuleOptionItemDO);
                ArrayList<String> storageValue = new ArrayList<String>();
                for (BillRuleOptionItemDO ruleOptionItemDO : select) {
                    storageValue.add(ruleOptionItemDO.getVal());
                }
                optionFoVO.setStorageValue(((Object)storageValue).toString());
                optionFoVO.setEditable(false);
            }
            optionFoVO.setVal(currData.getVal());
            optionFoVO.setModifytime(currData.getModifytime());
            optionFoVO.setModifyuser(currData.getModifyuser());
        }
        return endList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public R update(BillRuleOptionVO billRuleOptionVO) {
        BillRuleOptionDO option = new BillRuleOptionDO();
        try {
            BeanUtils.copyProperties((Object)billRuleOptionVO, (Object)option);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
        option.setModifytime(new Date());
        option.setModifyuser(ShiroUtil.getUser().getUsername());
        BillRuleOptionDO param = new BillRuleOptionDO();
        param.setName(option.getName());
        if (option.getContronflag() != null) {
            param.setUnitcode(option.getUnitcode());
        }
        BillRuleOptionDO result = (BillRuleOptionDO)((Object)this.billRuleOptionDao.selectOne((Object)param));
        int insert = 0;
        if (result != null) {
            option.setId(result.getId());
            insert = this.billRuleOptionDao.updateByPrimaryKey((Object)option);
        } else {
            option.setId(UUID.randomUUID());
            insert = this.billRuleOptionDao.insert((Object)option);
        }
        BillRuleOptionItemDO billRuleOptionItemDO = new BillRuleOptionItemDO();
        billRuleOptionItemDO.setParentid(option.getId());
        this.billRuleOptionItemDao.delete((Object)billRuleOptionItemDO);
        String storageValue = billRuleOptionVO.getStorageValue();
        if (storageValue != null) {
            List jsonNodes = JSONUtil.parseMapArray((String)storageValue);
            for (Map jsonNode : jsonNodes) {
                billRuleOptionItemDO.setId(UUID.randomUUID());
                String val = JSONUtil.toJSONString((Object)jsonNode);
                billRuleOptionItemDO.setVal(val);
                this.billRuleOptionItemDao.insert((Object)billRuleOptionItemDO);
            }
        }
        return insert == 1 ? R.ok() : R.error((String)"\u64cd\u4f5c\u5931\u8d25");
    }

    private List<String> getOrgParents(String unitcode, String tenantName) {
        ArrayList<String> parentNames = new ArrayList<String>();
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(unitcode);
        orgDTO.setTenantName(tenantName);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO pageVO = this.orgDataClient.listSuperior(orgDTO);
        if (pageVO != null && pageVO.getRs().getCode() == 0 && pageVO.getTotal() > 0) {
            parentNames.addAll(pageVO.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList()));
        }
        return parentNames;
    }
}

