/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.cache.BillRuleMonitorCache;
import com.jiuqi.va.biz.domain.RuleAcceptDim;
import com.jiuqi.va.biz.impl.data.DataEventImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataListener;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.ComputedPropImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckItem;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RulerExecutor {
    private final RulerImpl ruler;
    private final ComputedPropImpl computedProp;
    private boolean enable;
    private boolean process;
    private LinkedList<RulerItem> activeQueue;
    private Map<RulerItem, Set<TriggerEvent>> activeMap;
    private Map<RulerItem, Map<TriggerEvent, Integer>> disableMap;
    private static final Logger logger = LoggerFactory.getLogger(RulerExecutor.class);
    private static final int LIMIT_MIN = 5000;
    private static final int LIMIT_MAX = 600000;
    private static int limit = 5000;
    private static boolean monitorEnabled = false;
    private static int monitorThreshold = 10;
    private List<CheckResult> checkMessages;
    private Data data;
    private DataListener listener = new DataListener(){

        @Override
        public void afterSetValue(DataTable table, DataRow row, DataField field) {
            RulerExecutor.this.afterSetValue(table, row, field);
        }

        @Override
        public void afterAddRow(DataTable table, DataRow row) {
            RulerExecutor.this.afterAddRow(table, row);
        }

        @Override
        public void afterDelRow(DataTable table, DataRow row) {
            RulerExecutor.this.afterDelRow(table, row);
        }

        @Override
        public void afterReload(DataTable table) {
            RulerExecutor.this.afterReload(table);
        }
    };

    public static void setLimit(int limit) {
        RulerExecutor.limit = limit < 5000 ? 5000 : Math.min(limit, 600000);
    }

    public static void setRuleMonitorEnabled(boolean enabled) {
        monitorEnabled = enabled;
    }

    public static void setRuleMonitorThreshold(int threshold) {
        if (threshold > 10) {
            monitorThreshold = threshold;
        }
    }

    public RulerExecutor(RulerImpl ruler, ComputedPropImpl computedProp) {
        this.ruler = ruler;
        this.computedProp = computedProp;
        this.bindData(ruler.getModel().getPlugins().get(DataImpl.class));
    }

    public void setEnable(boolean enable) {
        if (this.process) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerexecutor.cannotcall"));
        }
        this.enable = enable;
        this.data.setEnableRule(enable);
        if (enable) {
            this.activeQueue = new LinkedList();
            this.activeMap = new HashMap<RulerItem, Set<TriggerEvent>>();
            this.disableMap = new HashMap<RulerItem, Map<TriggerEvent, Integer>>();
        } else {
            this.activeQueue = null;
            this.activeMap = null;
            this.disableMap = null;
        }
    }

    private void processEvents() {
        long t0 = System.currentTimeMillis();
        ArrayList<Supplier<String>> infos = new ArrayList<Supplier<String>>();
        boolean debugEnabled = logger.isDebugEnabled();
        String defineName = this.ruler.getModel().getDefine().getName();
        while (this.activeQueue.size() > 0) {
            RulerItem rulerItem = this.activeQueue.removeFirst();
            Set<TriggerEvent> eventSet = this.activeMap.remove(rulerItem);
            Map disableEventMap = this.disableMap.computeIfAbsent(rulerItem, k -> new HashMap());
            for (TriggerEvent triggerEvent : eventSet) {
                Integer times = disableEventMap.getOrDefault(triggerEvent, 0);
                disableEventMap.put(triggerEvent, times + 1);
            }
            long t = System.currentTimeMillis();
            if (rulerItem instanceof CheckItem) {
                ((CheckItem)rulerItem).execute(this.ruler.getModel(), eventSet.stream(), this.checkMessages);
            } else {
                try {
                    if (System.currentTimeMillis() - t0 > (long)limit) {
                        List collect = infos.stream().map(Supplier::get).collect(Collectors.toList());
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerexecutor.executetimeout") + StringUtils.collectionToCommaDelimitedString(collect));
                    }
                    rulerItem.execute(this.ruler.getModel(), eventSet.stream());
                }
                catch (CheckException e) {
                    this.checkMessages.addAll(e.getCheckMessages());
                }
                catch (Exception e) {
                    LogUtil.add((String)"\u5355\u636e", (String)(rulerItem instanceof FormulaRulerItem ? "\u516c\u5f0f" : "\u89c4\u5219"), (String)this.ruler.getModel().getDefine().getName(), (String)rulerItem.getName(), (String)this.getErrorMessage(rulerItem, e));
                    throw e;
                }
            }
            long diff = System.currentTimeMillis() - t;
            Object info = this.toString(rulerItem);
            if (monitorEnabled && diff > (long)monitorThreshold) {
                String ruleName = rulerItem.getName();
                String ruleId = rulerItem instanceof FormulaRulerItem ? String.valueOf(((FormulaRulerItem)rulerItem).getFormula().getId()) : rulerItem.getName();
                BillRuleMonitorCache.updateRuleStatistics(defineName, ruleId, ruleName, diff);
            }
            if (debugEnabled) {
                logger.debug("\u6267\u884c\u89c4\u5219={}ms:{}", (Object)diff, info);
            }
            infos.add(() -> String.format("%s[%sms]", info, diff));
        }
        this.disableMap.clear();
    }

    private String getErrorMessage(RulerItem rulerItem, Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(rulerItem instanceof FormulaRulerItem ? "\u516c\u5f0f" : "\u89c4\u5219");
        sb.append("\u3010");
        sb.append(rulerItem.getTitle());
        sb.append("\u3011");
        sb.append("\u6267\u884c\u9519\u8bef");
        if (e.getMessage() != null) {
            sb.append(":");
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    private Object toString(RulerItem rulerItem) {
        return rulerItem instanceof FormulaRulerItem ? ((FormulaRulerItem)rulerItem).formula.getExpression() : rulerItem.getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void activeStepRulerItem(TriggerEvent event, List<FormulaType> excludeType, boolean exclude) {
        this.ruler.getDefine().getItems().forEach((i, rulerItem) -> {
            if ("BillCheckRequired".equals(rulerItem.getName())) {
                return;
            }
            if (rulerItem instanceof FormulaRulerItem) {
                FormulaRulerItem formulaRulerItem = (FormulaRulerItem)rulerItem;
                FormulaImpl formula = formulaRulerItem.getFormula();
                if (!"action".equals(formula.getObjectType())) {
                    return;
                }
                if (rulerItem.getTriggerTypes() == null || !rulerItem.getTriggerTypes().contains("before-save")) {
                    return;
                }
            }
            this.addEvents(event, excludeType, exclude, (RulerItem)rulerItem);
        });
        if (!this.process) {
            this.process = true;
            try {
                this.processEvents();
            }
            finally {
                this.process = false;
            }
        }
    }

    private void addEvents(TriggerEvent event, List<FormulaType> excludeType, boolean exclude, RulerItem rulerItem) {
        if (excludeType != null && excludeType.size() > 0) {
            if (rulerItem instanceof FormulaRulerItem) {
                if (!(exclude ^ excludeType.contains((Object)((FormulaRulerItem)rulerItem).getFormula().getFormulaType()))) {
                    return;
                }
            } else {
                if (!(exclude || rulerItem.getFormulaType() != null && excludeType.contains((Object)rulerItem.getFormulaType()))) {
                    return;
                }
                if (exclude && rulerItem.getFormulaType() != null && excludeType.contains((Object)rulerItem.getFormulaType())) {
                    return;
                }
            }
        }
        if (this.accept(rulerItem, this.ruler.getModel().getDefine(), event)) {
            Set<TriggerEvent> events = this.activeMap.get(rulerItem);
            if (events == null) {
                events = new LinkedHashSet<TriggerEvent>();
                this.activeMap.put(rulerItem, events);
                this.activeQueue.addLast(rulerItem);
            }
            events.add(event);
        }
    }

    private boolean accept(RulerItem rulerItem, ModelDefine modelDefine, TriggerEvent event) {
        Set<String> triggerTypes = rulerItem.getTriggerTypes();
        if (triggerTypes == null || triggerTypes.isEmpty()) {
            return false;
        }
        String triggerType = event.getTriggerType();
        if (!triggerTypes.contains(triggerType)) {
            return false;
        }
        if (rulerItem.enableCaching()) {
            RuleAcceptDim ruleAcceptDim = RuleAcceptDim.of(rulerItem.getName(), event.getTriggerType(), event.getTable() == null ? null : event.getTable().getName(), event.getField() == null ? null : event.getField().getName());
            return this.ruler.getDefine().getAcceptCache().computeIfAbsent(ruleAcceptDim, key -> rulerItem.accept(modelDefine, event));
        }
        return rulerItem.accept(modelDefine, event);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void activeRulerItem(TriggerEvent event, List<FormulaType> excludeType, boolean exclude) {
        this.ruler.getDefine().getItems().forEach((i, rulerItem) -> this.addEvents(event, excludeType, exclude, (RulerItem)rulerItem));
        if (!this.process) {
            this.process = true;
            try {
                this.processEvents();
            }
            finally {
                this.process = false;
            }
        }
    }

    private void activeRulerItem(TriggerEvent event) {
        this.ruler.getDefine().getItems().forEach((i, rulerItem) -> this.addEvents(event, (RulerItem)rulerItem, null));
        AtomicInteger index = this.activeQueue.isEmpty() ? new AtomicInteger(0) : new AtomicInteger(1);
        this.computedProp.getDefine().getItems().forEach((i, rulerItem) -> {
            boolean b = this.addEvents(event, (RulerItem)rulerItem, index.get());
            if (b) {
                index.getAndIncrement();
            }
        });
        if (!this.process) {
            this.process = true;
            try {
                this.processEvents();
            }
            finally {
                this.process = false;
            }
        }
    }

    private boolean addEvents(TriggerEvent event, RulerItem rulerItem, Integer index) {
        boolean result = false;
        if (this.accept(rulerItem, this.ruler.getModel().getDefine(), event)) {
            Map<TriggerEvent, Integer> disableEvents = this.disableMap.get(rulerItem);
            if (disableEvents != null && disableEvents.getOrDefault(event, 0) > 6) {
                if (rulerItem instanceof FormulaRulerItem) {
                    logger.info(String.format("\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u89c4\u5219%s", ((FormulaRulerItem)rulerItem).formula.getExpression()));
                } else {
                    logger.info(String.format("\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u89c4\u5219%s", rulerItem.getClass()));
                }
                return false;
            }
            Set<TriggerEvent> events = this.activeMap.get(rulerItem);
            if (events == null) {
                events = new LinkedHashSet<TriggerEvent>();
                this.activeMap.put(rulerItem, events);
                if (index != null) {
                    this.activeQueue.add(index, rulerItem);
                    result = true;
                } else {
                    this.activeQueue.addLast(rulerItem);
                }
            }
            events.add(event);
        }
        return result;
    }

    public List<CheckResult> exeucteTrigger(String triggerType) {
        if (!this.enable) {
            return Collections.emptyList();
        }
        this.checkMessages = new ArrayList<CheckResult>();
        try {
            this.activeRulerItem(new DataEventImpl(triggerType, null, null, null));
            List<CheckResult> list = this.checkMessages;
            return list;
        }
        finally {
            this.checkMessages = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<CheckResult> exeucteStepTrigger(String triggerType, List<FormulaType> excludeType, boolean exclude) {
        if (!this.enable) {
            return Collections.emptyList();
        }
        this.checkMessages = new ArrayList<CheckResult>();
        try {
            this.activeStepRulerItem(new DataEventImpl(triggerType, null, null, null), excludeType, exclude);
            List<CheckResult> list = this.checkMessages;
            return list;
        }
        finally {
            this.checkMessages = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<CheckResult> exeucteTrigger(String triggerType, List<FormulaType> excludeType, boolean exclude) {
        if (!this.enable) {
            return Collections.emptyList();
        }
        this.checkMessages = new ArrayList<CheckResult>();
        try {
            this.activeRulerItem(new DataEventImpl(triggerType, null, null, null), excludeType, exclude);
            List<CheckResult> list = this.checkMessages;
            return list;
        }
        finally {
            this.checkMessages = null;
        }
    }

    public List<CheckResult> beforeStepAction(List<FormulaType> excludeType, boolean exclude) {
        return this.exeucteStepTrigger("before-save", excludeType, exclude);
    }

    public List<CheckResult> beforeAction(String action, List<FormulaType> excludeType, boolean exclude) {
        return this.exeucteTrigger("before-" + action, excludeType, exclude);
    }

    public List<CheckResult> beforeAction(String action) {
        return this.exeucteTrigger("before-" + action);
    }

    private void bindData(Data data) {
        if (this.data != null) {
            this.data.removeListener(this.listener);
        }
        this.data = data;
        if (this.data != null) {
            this.data.addListener(this.listener);
        }
    }

    private void afterSetValue(DataTable table, DataRow row, DataField field) {
        if (!this.enable) {
            return;
        }
        this.activeRulerItem(new DataEventImpl("after-set-value", table, row, field));
    }

    private void afterAddRow(DataTable table, DataRow row) {
        if (!this.enable) {
            return;
        }
        this.activeRulerItem(new DataEventImpl("after-add-row", table, row, null));
    }

    private void afterDelRow(DataTable table, DataRow row) {
        if (!this.enable) {
            return;
        }
        this.activeRulerItem(new DataEventImpl("after-del-row", table, row, null));
    }

    private void afterReload(DataTable table) {
        if (!this.enable) {
            return;
        }
        if (!this.process) {
            this.process = true;
            try {
                this.doAfterReload(table);
            }
            finally {
                this.process = false;
            }
        } else {
            this.doAfterReload(table);
        }
    }

    private void doAfterReload(DataTable table) {
        table.getRows().stream().forEach(o -> {
            this.afterAddRow(table, (DataRow)o);
            table.getFields().stream().forEach(f -> this.afterSetValue(table, (DataRow)o, (DataField)f));
        });
    }

    public boolean isEnable() {
        return this.enable;
    }
}

