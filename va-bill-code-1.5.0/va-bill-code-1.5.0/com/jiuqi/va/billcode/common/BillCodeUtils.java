/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 */
package com.jiuqi.va.billcode.common;

import com.jiuqi.va.billcode.common.NumberRange;
import com.jiuqi.va.billcode.service.IBillCodeFlowService;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.util.StringUtils;

public class BillCodeUtils {
    private static final ConcurrentHashMap<String, AtomicInteger> WAITING_THREADS = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String, NumberRange> ACQUIRED_SERIAL_NUMBERS = new ConcurrentHashMap();

    public static final String createDefaultBillCode(BillCodeDTO billCodeDTO, IBillCodeFlowService billCodeFlowService) {
        String[] codes = billCodeDTO.getDefineCode().split("_");
        String constant = codes[codes.length - 1];
        if (constant.length() > 4) {
            constant = constant.substring(0, 4);
        }
        BillCodeRuleDTO codeRuleDTO = new BillCodeRuleDTO();
        codeRuleDTO.setConstant(constant);
        return BillCodeUtils.createBillCode(codeRuleDTO, billCodeDTO, billCodeFlowService);
    }

    public static final String createBillCode(BillCodeRuleDTO codeRuleDTO, BillCodeDTO billCodeDTO, IBillCodeFlowService billCodeFlowService) {
        String unitCode = billCodeDTO.getUnitCode();
        Date createTime = billCodeDTO.getCreateTime();
        StringBuffer billCode = new StringBuffer(codeRuleDTO.getConstant());
        String defautSdf = "yyMM";
        String ruleData = codeRuleDTO.getRuleData();
        Map ruleValue = null;
        String dateType = null;
        int flow = 1;
        String fillStr = null;
        int left = 0;
        int totalLen = 0;
        int fillLen = 0;
        if (StringUtils.hasText(ruleData)) {
            ruleValue = JSONUtil.parseMap((String)ruleData);
            left = ruleValue.get("leftSub") == null ? 0 : (Integer)ruleValue.get("leftSub");
            totalLen = ruleValue.get("totalLength") == null ? 0 : (Integer)ruleValue.get("totalLength");
            fillStr = (String)ruleValue.get("fillStr");
            String temp = (String)ruleValue.get("dateType");
            if (StringUtils.hasText(temp)) {
                dateType = temp;
            }
            int n = flow = ruleValue.get("flow") == null ? 0 : (Integer)ruleValue.get("flow");
        }
        if (StringUtils.hasText(unitCode) && totalLen > 0) {
            int uLen = unitCode.length();
            if (left > uLen) {
                throw new RuntimeException("\u89c4\u5219\u914d\u7f6e\u5de6\u8d77\u957f\u5ea6\u5927\u4e8e\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u957f\u5ea6");
            }
            fillLen = uLen - left - totalLen;
            if (left > 0 && totalLen <= 0 && uLen > left || fillLen < 0 && !StringUtils.hasText(fillStr)) {
                billCode.append(unitCode.substring(left));
            } else if (fillLen >= 0) {
                billCode.append(unitCode.substring(left, totalLen + left));
            } else if (fillLen < 0 && StringUtils.hasText(fillStr) && uLen > left) {
                billCode.append(unitCode.substring(left) + BillCodeUtils.getFullStr(fillLen, fillStr));
            }
        }
        billCode.append(billCodeDTO.getDimFormulaValue());
        if (!StringUtils.hasText((String)billCodeDTO.getExtInfo("datedimformula"))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(StringUtils.hasText(dateType) ? dateType : "yyMM");
            billCode.append(dateFormat.format(createTime));
        } else {
            billCode.append(billCodeDTO.getExtInfo("datedimformula"));
        }
        String dimensions = billCode.toString();
        String flowNumber = BillCodeUtils.fillFlow(BillCodeUtils.getSerialNumber(dimensions, billCodeFlowService), flow);
        billCode.append(flowNumber);
        return billCode.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getSerialNumber(String dim, IBillCodeFlowService billCodeFlowService) {
        AtomicInteger atomic = WAITING_THREADS.computeIfAbsent(dim, k -> new AtomicInteger(0));
        atomic.incrementAndGet();
        AtomicInteger atomicInteger = atomic;
        synchronized (atomicInteger) {
            long end;
            NumberRange range = ACQUIRED_SERIAL_NUMBERS.getOrDefault(dim, null);
            if (range != null && range.getCount() > 0) {
                NumberRange decrementRange = range.decrement();
                ACQUIRED_SERIAL_NUMBERS.put(dim, decrementRange);
                return decrementRange.getStart();
            }
            int n = atomic.getAndSet(0);
            try {
                end = billCodeFlowService.updateFlowNumberByDimensions(dim, n);
            }
            catch (Exception e) {
                end = billCodeFlowService.reUpdateFlowNumberByDimensions(dim, n);
            }
            if (n == 1) {
                return end;
            }
            long start = end - (long)n + 1L;
            NumberRange decrementRange = new NumberRange(start, n - 1);
            ACQUIRED_SERIAL_NUMBERS.put(dim, decrementRange);
            return start;
        }
    }

    private static String getFullStr(int fillLen, String fillStr) {
        fillLen = Math.abs(fillLen);
        String fullStr = "";
        for (int i = 0; i < fillLen; ++i) {
            fullStr = fullStr + fillStr;
        }
        return fullStr;
    }

    private static String fillFlow(Long flowNumber, int flow) {
        StringBuffer flowNumberStr = new StringBuffer(String.valueOf(flowNumber));
        int length = flowNumberStr.length();
        if (flow == 0) {
            flow = 6;
        }
        if (length < flow) {
            for (int i = flow; i > length; --i) {
                flowNumberStr.insert(0, "0");
            }
        }
        return flowNumberStr.toString();
    }

    public static List<MetaTreeInfoDTO> getChrildrens(List<MetaTreeInfoDTO> groups, List<MetaTreeInfoDTO> metas, BillCodeRuleDTO parentNode) {
        BillCodeUtils utils = new BillCodeUtils();
        switch (parentNode.getType()) {
            case METATYPE: {
                return metas;
            }
            case MODULE: {
                return utils.getChrildrensByModule(metas, parentNode.getGroupName());
            }
            case GROUP: {
                MetaTreeInfoDTO infoDTO = utils.getGroup(groups, parentNode);
                ArrayList<MetaTreeInfoDTO> chrildrenGroup = new ArrayList<MetaTreeInfoDTO>();
                chrildrenGroup.add(infoDTO);
                utils.getAllChrildrenGroup(groups, infoDTO, chrildrenGroup);
                return utils.getChrildrensByPNode(chrildrenGroup, metas);
            }
        }
        return null;
    }

    private void getAllChrildrenGroup(List<MetaTreeInfoDTO> groups, MetaTreeInfoDTO infoDTO, List<MetaTreeInfoDTO> chrildrenGroup) {
        for (MetaTreeInfoDTO metaTreeInfoDTO : groups) {
            if (!metaTreeInfoDTO.getType().equals((Object)MetaType.GROUP) || !metaTreeInfoDTO.getModuleName().equals(infoDTO.getModuleName()) || metaTreeInfoDTO.getParentName() == null || !metaTreeInfoDTO.getParentName().equals(infoDTO.getName())) continue;
            chrildrenGroup.add(metaTreeInfoDTO);
            this.getAllChrildrenGroup(groups, metaTreeInfoDTO, chrildrenGroup);
        }
    }

    private MetaTreeInfoDTO getGroup(List<MetaTreeInfoDTO> allNode, BillCodeRuleDTO parentNode) {
        for (MetaTreeInfoDTO metaTreeInfoDTO : allNode) {
            if (!metaTreeInfoDTO.getType().equals((Object)MetaType.GROUP) || !metaTreeInfoDTO.getModuleName().equals(parentNode.getModuleName()) || !metaTreeInfoDTO.getName().equals(parentNode.getGroupName())) continue;
            return metaTreeInfoDTO;
        }
        return null;
    }

    private List<MetaTreeInfoDTO> getChrildrensByPNode(List<MetaTreeInfoDTO> groups, List<MetaTreeInfoDTO> metas) {
        ArrayList<MetaTreeInfoDTO> dtos = new ArrayList<MetaTreeInfoDTO>();
        for (MetaTreeInfoDTO groupDTO : groups) {
            for (MetaTreeInfoDTO metaDTO : metas) {
                if (!groupDTO.getType().equals((Object)MetaType.GROUP) || !groupDTO.getModuleName().equals(metaDTO.getModuleName()) || !groupDTO.getName().equals(metaDTO.getGroupName())) continue;
                dtos.add(metaDTO);
            }
        }
        return dtos;
    }

    private List<MetaTreeInfoDTO> getChrildrensByModule(List<MetaTreeInfoDTO> allNode, String moduleName) {
        ArrayList<MetaTreeInfoDTO> treeInfoDTOs = new ArrayList<MetaTreeInfoDTO>();
        for (MetaTreeInfoDTO metaTreeInfoDTO : allNode) {
            if (!moduleName.equals(metaTreeInfoDTO.getModuleName())) continue;
            treeInfoDTOs.add(metaTreeInfoDTO);
        }
        return treeInfoDTOs;
    }

    public static String getStrategy(String ruleData) {
        Map ruleValue = null;
        String dateType = "yyMM";
        int flow = 6;
        if (StringUtils.hasText(ruleData)) {
            ruleValue = JSONUtil.parseMap((String)ruleData);
            String temp = (String)ruleValue.get("dateType");
            if (StringUtils.hasText(temp)) {
                dateType = temp;
            }
            int n = flow = ruleValue.get("flow") == null ? 0 : (Integer)ruleValue.get("flow");
        }
        if (dateType.length() == 2) {
            dateType = "\u5e74\uff08yy\uff09";
        } else if (dateType.length() == 4) {
            dateType = "\u5e74\u6708\uff08yyMM\uff09";
        } else if (dateType.length() == 6) {
            dateType = "\u5e74\u6708\u65e5\uff08yyMMdd\uff09";
        }
        return "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801+" + dateType + "+" + flow + "\u4f4d\u6d41\u6c34";
    }

    public static String getGroupTitle(List<MetaTreeInfoDTO> groups, MetaTreeInfoDTO treeInfoDTO) {
        StringBuffer groupPath = new StringBuffer();
        MetaTreeInfoDTO groupNode = null;
        HashMap<String, String> moduleMap = new HashMap<String, String>();
        for (MetaTreeInfoDTO metaTreeInfoDTO : groups) {
            if (metaTreeInfoDTO.getType().equals((Object)MetaType.GROUP) && metaTreeInfoDTO.getModuleName().equals(treeInfoDTO.getModuleName()) && metaTreeInfoDTO.getName().equals(treeInfoDTO.getGroupName())) {
                groupNode = metaTreeInfoDTO;
                break;
            }
            if (!metaTreeInfoDTO.getType().equals((Object)MetaType.MODULE)) continue;
            moduleMap.put(metaTreeInfoDTO.getName(), metaTreeInfoDTO.getTitle());
        }
        groupPath.append(groupNode.getTitle());
        new BillCodeUtils().gatherGroupPath(groups, groupNode, groupPath);
        return String.format("\u5355\u636e/%s/%s", moduleMap.get(treeInfoDTO.getModuleName()), groupPath.toString());
    }

    private void gatherGroupPath(List<MetaTreeInfoDTO> groups, MetaTreeInfoDTO groupNode, StringBuffer groupPath) {
        for (MetaTreeInfoDTO metaTreeInfoDTO : groups) {
            if (!metaTreeInfoDTO.getType().equals((Object)MetaType.GROUP) || !metaTreeInfoDTO.getModuleName().equals(groupNode.getModuleName()) || !metaTreeInfoDTO.getName().equals(groupNode.getParentName())) continue;
            groupPath.insert(0, "/").insert(0, metaTreeInfoDTO.getTitle());
            break;
        }
    }
}

