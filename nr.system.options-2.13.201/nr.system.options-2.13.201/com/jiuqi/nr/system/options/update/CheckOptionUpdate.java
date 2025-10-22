/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckRuleVO
 *  com.jiuqi.bsp.contentcheckrules.beans.ContentRuleType
 *  com.jiuqi.bsp.contentcheckrules.beans.LenLimitInfo
 *  com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService
 *  com.jiuqi.bsp.contentcheckrules.service.CheckRulesService
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.nr.system.options.update;

import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO;
import com.jiuqi.bsp.contentcheckrules.beans.CheckRuleVO;
import com.jiuqi.bsp.contentcheckrules.beans.ContentRuleType;
import com.jiuqi.bsp.contentcheckrules.beans.LenLimitInfo;
import com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService;
import com.jiuqi.bsp.contentcheckrules.service.CheckRulesService;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import com.jiuqi.va.domain.common.JSONUtil;
import javax.sql.DataSource;
import org.springframework.util.StringUtils;

public class CheckOptionUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)BeanUtils.getBean(INvwaSystemOptionService.class);
        CheckGroupingService groupingService = (CheckGroupingService)BeanUtils.getBean(CheckGroupingService.class);
        CheckGroupingVO vo = new CheckGroupingVO();
        vo.setCode("NR/DEFAULT");
        vo.setTitle("\u62a5\u8868-\u7cfb\u7edf\u9009\u9879\u81ea\u52a8\u8fc1\u79fb");
        vo.setLeaf(true);
        String groupKey = groupingService.addCheckGrouping(vo);
        CheckRulesService checkRulesService = (CheckRulesService)BeanUtils.getBean(CheckRulesService.class);
        CheckRuleVO checkRuleVO = new CheckRuleVO();
        checkRuleVO.setCode("SYSTEMRULE");
        checkRuleVO.setTitle("\u7cfb\u7edf\u9009\u9879\u8fc1\u79fb\u89c4\u5219");
        checkRuleVO.setGroupKey(groupKey);
        StringBuilder desc = new StringBuilder();
        checkRuleVO.setRuleId(String.valueOf(ContentRuleType.LENLIMIT.getNumber()));
        checkRuleVO.setRuleType(String.valueOf(ContentRuleType.LENLIMIT.getNumber()));
        checkRuleVO.setCheckType(Integer.valueOf(1));
        LenLimitInfo lenLimitInfo = new LenLimitInfo();
        String containsChineseStr = systemOptionService.findValueById("ERROR_MSG_CONTAIN_CHINESE_CHAR");
        lenLimitInfo.setContainsChinese("1".equals(containsChineseStr));
        String minStr = systemOptionService.findValueById("CHAR_NUMBER_OF_ERROR_MSG");
        int min = 10;
        try {
            if (StringUtils.hasLength(minStr)) {
                min = Integer.parseInt(minStr);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        String maxStr = systemOptionService.findValueById("MAX_NUMBER_OF_ERROR_MSG");
        int max = 172;
        try {
            if (StringUtils.hasLength(maxStr)) {
                max = Integer.parseInt(maxStr);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        desc.append("\u5b57\u7b26\u4e2a\u6570\u9700\u5927\u4e8e").append(min).append(",\u5c0f\u4e8e").append(max);
        if (lenLimitInfo.getContainsChinese()) {
            desc.append(",\u4e14\u5305\u542b\u6c49\u5b57");
        }
        lenLimitInfo.setMinWords(min);
        lenLimitInfo.setMaxWords(max);
        String jsonString = JSONUtil.toJSONString((Object)lenLimitInfo);
        checkRuleVO.setContent(jsonString);
        checkRuleVO.setEnable(Boolean.valueOf(true));
        checkRuleVO.setDescription(desc.toString());
        checkRulesService.addCheckRule(checkRuleVO);
        SystemOptionSave save = new SystemOptionSave();
        save.setKey("nr-audit-group");
        SystemOptionItemValue itemValue = new SystemOptionItemValue();
        itemValue.setKey("@nr/check/explain-use-rule-group");
        itemValue.setValue(groupKey);
        save.getItemValues().add(itemValue);
        systemOptionService.save(save);
    }
}

