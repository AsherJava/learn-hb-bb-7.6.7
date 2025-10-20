/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDeclare
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelTypeBase
 *  com.jiuqi.va.biz.intf.action.GlobalActionEventProcessor
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.data.DataTransEvent
 *  com.jiuqi.va.biz.intf.data.GlobalDataTransEvent
 *  com.jiuqi.va.biz.intf.model.DeclareHost
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginManager
 *  com.jiuqi.va.biz.intf.model.PluginType
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.ruler.impl.FormulaRulerItem
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.ruler.intf.CachedRulerItemDecorator
 *  com.jiuqi.va.biz.ruler.intf.CheckItem
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.gendata.config.CollectGenDataEvent;
import com.jiuqi.va.bill.gendata.intf.GenDataEvent;
import com.jiuqi.va.bill.impl.BillDeclare;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.impl.BillDefineMerge;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.impl.event.BillCodeGenerateEvent;
import com.jiuqi.va.bill.impl.event.DeleteRejectTodoEvent;
import com.jiuqi.va.bill.impl.event.VaAttachmentConfirmGlobalDataTransEvent;
import com.jiuqi.va.bill.intf.BillModelService;
import com.jiuqi.va.bill.plugin.event.DetailFilterSaveDataPosEvent;
import com.jiuqi.va.bill.ruler.AfterBillCodeChange;
import com.jiuqi.va.bill.ruler.BillCodeRefresh;
import com.jiuqi.va.bill.ruler.BillDateChangeListener;
import com.jiuqi.va.bill.ruler.CheckAttachmentRequired;
import com.jiuqi.va.bill.ruler.CheckFieldLengthBeforeSave;
import com.jiuqi.va.bill.ruler.CheckFieldLengthBeforeTempSave;
import com.jiuqi.va.bill.ruler.CheckRequired;
import com.jiuqi.va.bill.ruler.CheckTableRequired;
import com.jiuqi.va.bill.ruler.InitMasterTable;
import com.jiuqi.va.bill.ruler.InitSubTable;
import com.jiuqi.va.bill.ruler.UnitCodeChangeListener;
import com.jiuqi.va.bill.ruler.UnitCodeChangeListener2;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableDeclare;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.model.ModelTypeBase;
import com.jiuqi.va.biz.intf.action.GlobalActionEventProcessor;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.data.DataTransEvent;
import com.jiuqi.va.biz.intf.data.GlobalDataTransEvent;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.model.PluginType;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CachedRulerItemDecorator;
import com.jiuqi.va.biz.ruler.intf.CheckItem;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

public abstract class BillTypeBase
extends ModelTypeBase {
    private static final String BILL_PRINT_PLUGIN = "print";
    private static final String BILL_PLUGINTYPE_EVENTFORMULA = "eventFormula";
    private PluginManager pluginManager;
    private List<GlobalActionEventProcessor> globalActionEventProcessors;
    private List<GlobalDataTransEvent> globalDataTransEvents;
    @Autowired
    private BillModelService billModelService;
    @Autowired
    private CollectGenDataEvent collectGenDataEvent;
    @Autowired
    private VaAttachmentConfirmGlobalDataTransEvent vaAttachmentConfirmGlobalDataTransEvent;

    public final String getMetaType() {
        return "bill";
    }

    List<GlobalActionEventProcessor> getGlobalActionEventProcessors() {
        if (this.globalActionEventProcessors == null) {
            Map<String, GlobalActionEventProcessor> beansOfType = ApplicationContextRegister.getApplicationContext().getBeansOfType(GlobalActionEventProcessor.class);
            if (beansOfType == null || beansOfType.values().size() == 0) {
                return new ArrayList<GlobalActionEventProcessor>();
            }
            this.globalActionEventProcessors = new ArrayList<GlobalActionEventProcessor>(beansOfType.values());
        }
        return this.globalActionEventProcessors;
    }

    List<GlobalDataTransEvent> getGlobalDataTransEvents() {
        if (this.globalDataTransEvents == null) {
            Map<String, GlobalDataTransEvent> beansOfType = ApplicationContextRegister.getApplicationContext().getBeansOfType(GlobalDataTransEvent.class);
            if (beansOfType == null || beansOfType.values().size() == 0) {
                return new ArrayList<GlobalDataTransEvent>();
            }
            this.globalDataTransEvents = new ArrayList<GlobalDataTransEvent>(beansOfType.values());
        }
        return this.globalDataTransEvents;
    }

    private PluginManager getPluginManager() {
        if (this.pluginManager == null) {
            this.pluginManager = (PluginManager)ApplicationContextRegister.getBean(PluginManager.class);
        }
        return this.pluginManager;
    }

    public String[] getDependPlugins() {
        return new String[]{"data", "ruler", "view", BILL_PRINT_PLUGIN, "editableFields", "backWriteRule", "computedProp", "afterLoadEvent"};
    }

    public final Class<? extends BillDefineImpl> getModelDefineClass() {
        return BillDefineImpl.class;
    }

    public Class<? extends BillModelImpl> getModelClass() {
        return BillModelImpl.class;
    }

    public final void ensureModelDefine(ModelDefineImpl modelDefine) {
        BillDefineImpl billDefineImpl = (BillDefineImpl)modelDefine;
        ArrayList<RulerItem> itemList = new ArrayList<RulerItem>();
        this.ensureRulerList(billDefineImpl, itemList);
        if (!modelDefine.isInit()) {
            ListContainer sortInfo = billDefineImpl.getRuler().getSortInfo();
            LinkedHashMap map = new LinkedHashMap();
            itemList.forEach(o -> map.put(o.getClass(), o));
            ArrayList<RulerItem> allRulers = new ArrayList<RulerItem>(billDefineImpl.getRuler().getItemList());
            allRulers.addAll(map.values());
            List<RulerItem> formulas = this.sortRulers(modelDefine, (ListContainer<Map<String, Object>>)sortInfo, allRulers);
            billDefineImpl.getRuler().getItemList().clear();
            billDefineImpl.getRuler().getItemList().addAll(formulas);
        } else {
            billDefineImpl.getRuler().getItemList().addAll(itemList);
        }
    }

    protected final void declarePlugin(PluginType pluginType, ModelDefineImpl modelDefine) {
        if ("data".equals(pluginType.getName())) {
            pluginType.declare(() -> {
                final BillDefineImpl define = (BillDefineImpl)modelDefine;
                final ArrayList<1> billDeclareList = new ArrayList<1>();
                billDeclareList.add(new BillDeclare<DeclareHost<BillDefineImpl>>(declareImpl -> BillDefineMerge.merge(define, declareImpl)){

                    public DeclareHost<BillDefineImpl> endDeclare() {
                        BillDeclare billDeclare = (BillDeclare)billDeclareList.get(0);
                        BillTypeBase.this.declareFixed(billDeclare);
                        if (billDeclare.getData().getTables().size() == 0 && define.getData().getTables().size() > 0) {
                            DataDeclare declareData = billDeclare.declareData();
                            DataTableDeclare declareMasterTable = declareData.declareMasterTable().setName(((DataTableDefineImpl)define.getData().getTables().getMasterTable()).getName());
                            BillTypeBase.this.declareMasterTable(declareMasterTable).endDeclare();
                            declareData.endDeclare();
                        }
                        return super.endDeclare();
                    }
                });
                this.declare((BillDeclare)billDeclareList.get(0));
                ((BillDeclare)billDeclareList.get(0)).endDeclare();
            });
        } else {
            pluginType.declare(() -> {});
        }
    }

    private List<RulerItem> sortRulers(ModelDefineImpl modelDefine, ListContainer<Map<String, Object>> sortInfo, List<RulerItem> allRulers) {
        ArrayList<RulerItem> formulas = new ArrayList<RulerItem>();
        ArrayList<Object> fixeds = new ArrayList<Object>();
        for (RulerItem ruler : allRulers) {
            if (ruler.getRulerType().equals("Formula")) {
                formulas.add(ruler);
                continue;
            }
            if (ruler instanceof CheckItem) {
                fixeds.add(ruler);
                continue;
            }
            CachedRulerItemDecorator cachedRulerItemDecorator = new CachedRulerItemDecorator(ruler);
            cachedRulerItemDecorator.initCache((ModelDefine)modelDefine);
            fixeds.add(cachedRulerItemDecorator);
        }
        Map<String, Map> sortInfoMap = sortInfo.stream().collect(Collectors.toMap(o -> BillUtils.valueToString(o.get("id")), o -> o, (o1, o2) -> o2));
        Map<String, RulerItem> fixedMap = fixeds.stream().collect(Collectors.toMap(RulerItem::getName, o -> o, (o1, o2) -> o2));
        ArrayList<Map> newSorInfo = new ArrayList<Map>();
        for (Object fixed : fixeds) {
            Map jsonObject = sortInfoMap.get(fixed.getName());
            if (jsonObject != null) {
                newSorInfo.add(jsonObject);
                continue;
            }
            HashMap<String, String> newFixed = new HashMap<String, String>();
            newFixed.put("id", fixed.getName());
            newFixed.put("before", null);
            newSorInfo.add(newFixed);
        }
        int tmpIndex = -1;
        for (Map ruler : newSorInfo) {
            Object id = ruler.get("id");
            if (ObjectUtils.isEmpty(id)) continue;
            if (ruler.get("before") == null) {
                formulas.add(++tmpIndex, fixedMap.get(id));
                continue;
            }
            int index = -1;
            for (int i = 0; i < formulas.size(); ++i) {
                FormulaRulerItem item;
                if (!(formulas.get(i) instanceof FormulaRulerItem) || !(item = (FormulaRulerItem)formulas.get(i)).getFormula().getId().toString().equals(ruler.get("before"))) continue;
                index = i;
                break;
            }
            if (index != -1 && formulas.get(index) instanceof FormulaRulerItem && !fixedMap.get(id).getObjectType().equals(((FormulaRulerItem)formulas.get(index)).getFormula().getObjectType())) {
                formulas.add(++tmpIndex, fixedMap.get(id));
                continue;
            }
            tmpIndex = index + 1;
            formulas.add(tmpIndex, fixedMap.get(id));
        }
        for (int i = 0; i < formulas.size(); ++i) {
            RulerItem rulerItem = (RulerItem)formulas.get(i);
            boolean b = rulerItem instanceof CachedRulerItemDecorator;
            if (!b || !(((CachedRulerItemDecorator)rulerItem).getDelegate() instanceof InitMasterTable)) continue;
            RulerItem remove = (RulerItem)formulas.remove(i);
            formulas.add(0, remove);
            break;
        }
        int lastEventFixedIndex = -1;
        for (int i = 0; i < formulas.size(); ++i) {
            RulerItem rulerItem = (RulerItem)formulas.get(i);
            if (!"event".equals(rulerItem.getObjectType()) || rulerItem instanceof FormulaRulerItem) continue;
            lastEventFixedIndex = i;
        }
        if (lastEventFixedIndex == -1) {
            return formulas;
        }
        ArrayList<RulerItem> result = new ArrayList<RulerItem>();
        ArrayList<RulerItem> calc = new ArrayList<RulerItem>();
        for (int i = 0; i < formulas.size(); ++i) {
            RulerItem rulerItem = (RulerItem)formulas.get(i);
            if ("event".equals(rulerItem.getObjectType()) && rulerItem instanceof FormulaRulerItem && "calculation".equals(((FormulaRulerItem)rulerItem).getFormula().getPropertyType()) && i < lastEventFixedIndex) {
                calc.add(rulerItem);
                continue;
            }
            result.add(rulerItem);
        }
        if (calc.size() > 0) {
            result.addAll(lastEventFixedIndex - calc.size() + 1, calc);
        }
        return result;
    }

    protected void ensureRulerList(BillDefineImpl define, List<RulerItem> rulerList) {
        rulerList.add(new InitMasterTable());
        rulerList.add(new BillCodeRefresh());
        rulerList.add(new InitSubTable());
        rulerList.add((RulerItem)new CheckRequired());
        rulerList.add((RulerItem)new CheckTableRequired());
        rulerList.add(new UnitCodeChangeListener());
        rulerList.add(new UnitCodeChangeListener2());
        rulerList.add(new BillDateChangeListener());
        rulerList.add(new AfterBillCodeChange());
        rulerList.add((RulerItem)new CheckFieldLengthBeforeSave());
        rulerList.add((RulerItem)new CheckFieldLengthBeforeTempSave());
        rulerList.add(new CheckAttachmentRequired());
    }

    private void declareFixed(BillDeclare<?> billDeclare) {
        DataTableNodeContainerImpl tables = billDeclare.getData().getTables();
        if (tables.size() == 0) {
            return;
        }
        DataTableDefineImpl masterTable = (DataTableDefineImpl)billDeclare.getData().getTables().getMasterTable();
        DataDeclare<BillDeclare<?>> declareData = billDeclare.declareData();
        DataTableDeclare declareMasterTable = declareData.declareMasterTable().setName(masterTable.getName());
        this.declareMasterTable(declareMasterTable).endDeclare();
        declareData.getTables().stream().forEach(o -> {
            if (o.getParentId() == null || o.getTableType() != DataTableType.DATA && o.getTableType() == DataTableType.REFER) {
                return;
            }
            if (o.getParentId().equals(masterTable.getId())) {
                declareData.declareDetailTable().setName(o.getName()).endDeclare();
            } else {
                declareData.declareAssistTable().setName(o.getName()).endDeclare();
            }
        });
        declareData.endDeclare();
    }

    private DataTableDeclare<?> declareMasterTable(DataTableDeclare<?> declareMasterTable) {
        declareMasterTable.declareField().setName("BILLCODE").setTitle("\u5355\u636e\u7f16\u53f7").setFieldType(DataFieldType.DATA).setFieldName("BILLCODE").setValueType(ValueType.STRING).setLength(60).setRequired(false).setReadonly(true).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("BILLDATE").setTitle("\u5355\u636e\u65e5\u671f").setFieldType(DataFieldType.DATA).setFieldName("BILLDATE").setValueType(ValueType.DATE).setLength(60).setRequired(true).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("UNITCODE").setTitle("\u7ec4\u7ec7\u673a\u6784").setFieldType(DataFieldType.DATA).setFieldName("UNITCODE").setValueType(ValueType.STRING).setLength(60).setRequired(true).setReadonly(false).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("DEFINECODE").setTitle("\u5355\u636e\u5b9a\u4e49").setFieldType(DataFieldType.DATA).setFieldName("DEFINECODE").setValueType(ValueType.STRING).setLength(60).setReadonly(true).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("BILLSTATE").setTitle("\u5355\u636e\u72b6\u6001").setFieldType(DataFieldType.DATA).setFieldName("BILLSTATE").setValueType(ValueType.INTEGER).setLength(60).setReadonly(true).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("CREATEUSER").setTitle("\u521b\u5efa\u4eba").setFieldType(DataFieldType.DATA).setFieldName("CREATEUSER").setValueType(ValueType.STRING).setLength(60).setReadonly(true).setSolidified(true).endDeclare();
        declareMasterTable.declareField().setName("CREATETIME").setTitle("\u521b\u5efa\u65f6\u95f4").setFieldType(DataFieldType.DATA).setFieldName("CREATETIME").setValueType(ValueType.DATETIME).setLength(60).setReadonly(true).setSolidified(true).endDeclare();
        return declareMasterTable;
    }

    protected void declare(BillDeclare<?> billDeclare) {
    }

    public final void ensureModel(ModelImpl model) {
        BillModelImpl billModelImpl = (BillModelImpl)model;
        billModelImpl.setData((DataImpl)model.getPlugins().get(DataImpl.class));
        billModelImpl.setRuler((RulerImpl)model.getPlugins().get(RulerImpl.class));
        billModelImpl.setBillModelService(this.billModelService);
        billModelImpl.getData().registerDataPostEvent((DataPostEvent)new DeleteRejectTodoEvent());
        billModelImpl.getData().registerDataPostEvent((DataPostEvent)new BillCodeGenerateEvent(this.billModelService));
        billModelImpl.getData().registerDataPostEvent((DataPostEvent)new DetailFilterSaveDataPosEvent());
        billModelImpl.getData().registerDataTransEvent((DataTransEvent)this.vaAttachmentConfirmGlobalDataTransEvent);
        this.ensureBillModel(billModelImpl);
    }

    protected void ensureBillModel(BillModelImpl model) {
        if (this.collectGenDataEvent.size() > 0) {
            List<GenDataEvent> eventList = this.collectGenDataEvent.get();
            for (GenDataEvent event : eventList) {
                model.getData().registerDataPostEvent((DataPostEvent)event);
            }
        }
        if (this.getGlobalActionEventProcessors().size() != 0) {
            model.getDefine().initGlobalActionEventProcessor(this.globalActionEventProcessors);
        }
        if (this.getGlobalDataTransEvents().size() != 0) {
            model.getData().initGlobalDataTransEvents(this.globalDataTransEvents);
        }
    }
}

