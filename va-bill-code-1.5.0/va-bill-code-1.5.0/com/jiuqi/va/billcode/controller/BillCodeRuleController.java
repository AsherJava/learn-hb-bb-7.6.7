/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleVO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.billcode.controller;

import com.jiuqi.va.billcode.common.BillCodeUtils;
import com.jiuqi.va.billcode.service.IBillCodeRuleService;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleVO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/billcode"})
public class BillCodeRuleController {
    private static final Logger logger = LoggerFactory.getLogger(BillCodeRuleController.class);
    @Autowired
    private IBillCodeRuleService billCodeRuleService;
    @Autowired
    private MetaDataClient metaDataClient;

    @GetMapping(value={"/metas"})
    public PageVO<MetaTreeInfoDTO> getAllBillMetas(TenantDO tenantDO) {
        return this.billCodeRuleService.gatherMetaTree(tenantDO);
    }

    @PostMapping(value={"/rule/get"})
    public PageVO<BillCodeRuleVO> getBillCodeRule(@RequestBody BillCodeRuleDTO param) {
        PageVO pageVO = new PageVO();
        ArrayList<BillCodeRuleVO> list = new ArrayList<BillCodeRuleVO>();
        pageVO.setRows(list);
        pageVO.setRs(R.ok());
        try {
            BillCodeRuleDTO codeRuleDTO = this.billCodeRuleService.getRuleByUniqueCode(param.getUniqueCode());
            if (codeRuleDTO != null) {
                BillCodeRuleVO billCodeRuleVO = new BillCodeRuleVO();
                billCodeRuleVO.setConstant(codeRuleDTO.getConstant());
                billCodeRuleVO.setRuleData(new String(codeRuleDTO.getRuleData()));
                billCodeRuleVO.setUniqueCode(codeRuleDTO.getUniqueCode());
                billCodeRuleVO.setGenerateOpt(codeRuleDTO.getGenerateOpt().intValue());
                billCodeRuleVO.setBillcodeExist(codeRuleDTO.isBillCodeExist());
                list.add(billCodeRuleVO);
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5b9a\u4e49\u5931\u8d25\uff1a", e);
            pageVO.setRs(R.error((String)("\u83b7\u53d6\u5b9a\u4e49\u5931\u8d25\uff1a" + e.getMessage())));
        }
        return pageVO;
    }

    @PostMapping(value={"rule/update"})
    public R updateBillCodeRule(@RequestBody BillCodeRuleDTO codeRuleDTO) {
        R r = null;
        try {
            BillCodeRuleDTO oldCodeRuleDTO = this.billCodeRuleService.getRuleByUniqueCode(codeRuleDTO.getUniqueCode());
            if (oldCodeRuleDTO == null) {
                return R.error((String)"\u4fee\u6539\u7684\u6570\u636e\u5df2\u88ab\u5220\u9664");
            }
            if (this.billCodeRuleService.hasRuleByConstant(codeRuleDTO.getConstant()) && !codeRuleDTO.getConstant().equals(oldCodeRuleDTO.getConstant())) {
                return R.error((String)"\u5e38\u91cf\u540d\u5df2\u7ecf\u5b58\u5728");
            }
            codeRuleDTO.setId(oldCodeRuleDTO.getId());
            this.billCodeRuleService.editRule(codeRuleDTO);
            r = R.ok();
            r.put("rule", (Object)codeRuleDTO);
        }
        catch (Exception e) {
            logger.error("\u4fee\u6539\u89c4\u5219\u5f02\u5e38\uff1a", e);
            r = R.error((String)("\u4fee\u6539\u89c4\u5219\u5f02\u5e38\uff1a" + e.getMessage()));
        }
        return r;
    }

    @PostMapping(value={"rule/add"})
    public R addBillCodeRule(@RequestBody BillCodeRuleDTO codeRuleDTO) {
        R r = null;
        try {
            if (this.billCodeRuleService.hasRuleByConstant(codeRuleDTO.getConstant())) {
                return R.error((String)"\u5e38\u91cf\u540d\u5df2\u7ecf\u5b58\u5728");
            }
            this.billCodeRuleService.addRule(codeRuleDTO);
            r = R.ok();
            r.put("rule", (Object)codeRuleDTO);
        }
        catch (Exception e) {
            r = R.error((String)("\u65b0\u589e\u89c4\u5219\u5f02\u5e38\uff1a" + e.getMessage()));
        }
        return r;
    }

    @PostMapping(value={"/rule/all"})
    public PageVO<BillCodeRuleVO> getBillCodeRuleAll(@RequestBody BillCodeRuleDTO codeRuleDTO) {
        PageVO pageVO = new PageVO();
        ArrayList<BillCodeRuleVO> ruleVOs = new ArrayList<BillCodeRuleVO>();
        pageVO.setRows(ruleVOs);
        pageVO.setRs(R.ok());
        try {
            PageVO<MetaTreeInfoDTO> metaTree = this.billCodeRuleService.gatherMetaTree((TenantDO)codeRuleDTO);
            if (metaTree.getRows() == null) {
                return pageVO;
            }
            HashMap<String, BillCodeRuleDTO> codeRuleMap = new HashMap<String, BillCodeRuleDTO>();
            List<BillCodeRuleDTO> codeRuleDTOs = this.billCodeRuleService.getRuleAll();
            if (codeRuleDTOs.isEmpty()) {
                return pageVO;
            }
            for (BillCodeRuleDTO billCodeRuleDTO : codeRuleDTOs) {
                codeRuleMap.put(billCodeRuleDTO.getUniqueCode(), billCodeRuleDTO);
            }
            ArrayList<MetaTreeInfoDTO> groups = new ArrayList<MetaTreeInfoDTO>();
            ArrayList<MetaTreeInfoDTO> metas = new ArrayList<MetaTreeInfoDTO>();
            for (MetaTreeInfoDTO metaTreeInfoDTO : metaTree.getRows()) {
                if (metaTreeInfoDTO.getType().equals((Object)MetaType.METADATA)) {
                    metas.add(metaTreeInfoDTO);
                    continue;
                }
                groups.add(metaTreeInfoDTO);
            }
            List<MetaTreeInfoDTO> treeInfoDTOs = BillCodeUtils.getChrildrens(groups, metas, codeRuleDTO);
            for (MetaTreeInfoDTO treeInfoDTO : treeInfoDTOs) {
                BillCodeRuleDTO billCodeRuleDTO = (BillCodeRuleDTO)codeRuleMap.get(treeInfoDTO.getUniqueCode());
                if (billCodeRuleDTO == null) continue;
                BillCodeRuleVO codeRuleVO = new BillCodeRuleVO();
                codeRuleVO.setBillTitle(treeInfoDTO.getTitle());
                codeRuleVO.setConstant(billCodeRuleDTO.getConstant());
                codeRuleVO.setStrategy(BillCodeUtils.getStrategy(billCodeRuleDTO.getRuleValue()));
                codeRuleVO.setUniqueCode(treeInfoDTO.getUniqueCode());
                codeRuleVO.setGroupTitle(BillCodeUtils.getGroupTitle(groups, treeInfoDTO));
                codeRuleVO.setGenerateOpt(billCodeRuleDTO.getGenerateOpt().intValue());
                ruleVOs.add(codeRuleVO);
            }
            ruleVOs.sort(new Comparator<BillCodeRuleVO>(){

                @Override
                public int compare(BillCodeRuleVO o1, BillCodeRuleVO o2) {
                    return o1.getGroupTitle().compareTo(o2.getGroupTitle());
                }
            });
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5355\u636e\u89c4\u5219\u5b9a\u4e49\u5f02\u5e38\uff1a", e);
            pageVO.setRs(R.error((String)("\u83b7\u53d6\u5355\u636e\u89c4\u5219\u5b9a\u4e49\u5f02\u5e38\uff1a" + e.getMessage())));
        }
        return pageVO;
    }

    @PostMapping(value={"/ruler/uniquecode"})
    public R getUniqueCodeByBillCode(@RequestBody TenantDO param) {
        R r = null;
        try {
            String billCode = (String)param.getExtInfo("billCode");
            if (!StringUtils.hasText(billCode)) {
                return R.error((String)"\u53c2\u6570\u975e\u6cd5\uff0cbillCode\u53c2\u6570\u503c\u4e0d\u5b58\u5728");
            }
            String uniqueCode = this.billCodeRuleService.getUniqueCodeByBillCode(billCode);
            if (!StringUtils.hasText(uniqueCode)) {
                r = R.error((String)"\u5355\u636e\u7f16\u53f7\u6ca1\u6709\u5bf9\u5e94\u7684\u5355\u636e\u7f16\u53f7\u914d\u7f6e\u89c4\u5219");
            } else {
                r = R.ok();
                r.put("value", (Object)uniqueCode);
            }
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u5355\u636e\u7f16\u53f7\u83b7\u53d6\u89c4\u5219\u5931\u8d25\uff1a", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/ruler/dimformula"})
    public R getDimFormulaByUniqueCode(@RequestBody BillCodeRuleDTO param) {
        R r = null;
        BillCodeRuleDTO ruleDTO = null;
        Map ruleValue = null;
        String uniqueCode = param.getUniqueCode();
        if (!StringUtils.hasText(uniqueCode)) {
            return R.error((String)"\u53c2\u6570\u975e\u6cd5\uff0cuniqueCode\u53c2\u6570\u503c\u4e0d\u5b58\u5728");
        }
        try {
            ruleDTO = this.billCodeRuleService.getRuleByUniqueCodeUnCheck(uniqueCode, false);
            String ruleData = ruleDTO.getRuleData();
            r = R.ok();
            if (StringUtils.hasText(ruleData)) {
                ruleValue = JSONUtil.parseMap((String)ruleData);
                r.put("dimformula", ruleValue.get("dimformula"));
                r.put("datedimformula", ruleValue.get("datedimformula"));
                r.put("generateopt", (Object)ruleDTO.getGenerateOpt());
            }
        }
        catch (Exception e) {
            logger.error("\u6839\u636e\u5355\u636e\u7f16\u53f7\u83b7\u53d6\u89c4\u5219\u5931\u8d25\uff1a", e);
            r = R.error((String)e.getMessage());
        }
        return r;
    }

    @PostMapping(value={"/rule/list"})
    public List<BillCodeRuleVO> getBillCodeRuleList(@RequestBody BillCodeRuleDTO codeRuleDTO) {
        ArrayList<BillCodeRuleVO> ruleVOs = new ArrayList<BillCodeRuleVO>();
        try {
            List<BillCodeRuleDTO> list = this.billCodeRuleService.getRuleAll();
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("metaType", (Object)"bill");
            tenant.addExtInfo("ignoreModelTitle", (Object)true);
            List metaInfos = this.metaDataClient.getAllMetaInfoByMetaType(tenant).getRows();
            HashMap<String, MetaInfoDim> metaInfoMap = new HashMap<String, MetaInfoDim>();
            for (MetaInfoDim metaInfo : metaInfos) {
                metaInfoMap.put(metaInfo.getUniqueCode(), metaInfo);
            }
            for (BillCodeRuleDTO rule : list) {
                MetaInfoDim metaInfo = (MetaInfoDim)metaInfoMap.get(rule.getUniqueCode());
                if (metaInfo == null) continue;
                BillCodeRuleVO ruleItem = new BillCodeRuleVO();
                ruleItem.setUniqueCode(rule.getUniqueCode());
                ruleItem.setBillTitle(metaInfo.getTitle());
                ruleVOs.add(ruleItem);
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5355\u636e\u89c4\u5219\u5b9a\u4e49\u5217\u8868\u5f02\u5e38\uff1a", e);
        }
        return ruleVOs;
    }
}

