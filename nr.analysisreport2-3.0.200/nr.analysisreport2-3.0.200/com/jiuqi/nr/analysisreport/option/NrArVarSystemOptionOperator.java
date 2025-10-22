/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.nr.analysisreport.option;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NrArVarSystemOptionOperator
implements ISystemOptionOperator {
    @Lazy
    @Autowired
    private SystemOptionOperator systemOptionOperator;
    private Map<String, AbstractExprParser> abstractExprParserMap = null;

    public Map<String, AbstractExprParser> getAbstractExprParserMap() {
        if (CollectionUtils.isEmpty(this.abstractExprParserMap)) {
            this.abstractExprParserMap = new HashMap<String, AbstractExprParser>();
            Map<String, AbstractExprParser> abstractExprParser = SpringBeanUtils.getApplicationContext().getBeansOfType(AbstractExprParser.class);
            for (AbstractExprParser exprParser : abstractExprParser.values()) {
                this.abstractExprParserMap.put(AnaUtils.getVarMaxThreadOptionItem(exprParser.getName()), exprParser);
            }
        }
        return this.abstractExprParserMap;
    }

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        for (ISystemOptionItemValue itemValue : itemValues) {
            AbstractExprParser abstractExprParser = this.getAbstractExprParserMap().get(itemValue.getKey());
            if (abstractExprParser == null) continue;
            Integer threadNum = Integer.valueOf(itemValue.getValue());
            ThreadPoolTaskExecutor threadPoolTaskExecutor = abstractExprParser.getThreadPoolTaskExecutor();
            if (threadPoolTaskExecutor.getCorePoolSize() == threadNum.intValue()) continue;
            threadPoolTaskExecutor.setMaxPoolSize(threadNum);
            threadPoolTaskExecutor.setCorePoolSize(threadNum);
        }
        return this.systemOptionOperator.save(itemValues);
    }

    public String query(String optionItemKey) {
        return this.systemOptionOperator.query(optionItemKey);
    }

    public List<String> query(List<String> optionItemKeys) {
        return this.systemOptionOperator.query(optionItemKeys);
    }
}

