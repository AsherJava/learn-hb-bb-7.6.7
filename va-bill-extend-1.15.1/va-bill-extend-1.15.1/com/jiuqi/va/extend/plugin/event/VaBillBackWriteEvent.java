/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.utils.FormulaUtils
 */
package com.jiuqi.va.extend.plugin.event;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.extend.common.VaBackWriteTriggerTypeEnum;
import com.jiuqi.va.extend.intf.VaBillBackWriteTypeIntf;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class VaBillBackWriteEvent
implements DataPostEvent {
    private static final Logger log = LoggerFactory.getLogger("BackWriteFunction");
    @Autowired
    private List<VaBillBackWriteTypeIntf> backWriteType;

    public void beforeSave(DataImpl data) {
        BillModel billModel = (BillModel)data.getModel();
        this.executeByProcess(billModel, VaBackWriteTriggerTypeEnum.BEFORE_SAVE.getName());
    }

    public void afterSave(DataImpl data) {
        BillModel billModel = (BillModel)data.getModel();
        this.executeByProcess(billModel, VaBackWriteTriggerTypeEnum.AFTER_SAVE.getName());
    }

    public void afterDelete(DataImpl data) {
        BillModel billModel = (BillModel)data.getModel();
        this.executeByProcess(billModel, VaBackWriteTriggerTypeEnum.AFTER_DELETE.getName());
    }

    public void beforeDelete(DataImpl data) {
        BillModel billModel = (BillModel)data.getModel();
        this.executeByProcess(billModel, VaBackWriteTriggerTypeEnum.BEFORE_DELETE.getName());
    }

    private void executeByProcess(BillModel billModel, String triggerType) {
        String billcode = billModel.getMaster().getString("BILLCODE");
        DataField billstateDefine = (DataField)billModel.getMasterTable().getFields().find("BILLSTATE");
        if (billstateDefine == null) {
            return;
        }
        RulerDefineImpl rulerDefineImpl = (RulerDefineImpl)((Plugin)billModel.getPlugins().get("ruler")).getDefine();
        ListContainer formulas = rulerDefineImpl.getFormulas();
        if (formulas == null || formulas.size() == 0) {
            return;
        }
        Map<String, List<FormulaImpl>> mapFormulas = formulas.stream().filter(formula -> formula.isUsed() && !ObjectUtils.isEmpty(formula.getExpression()) && "backWrite".equals(formula.getObjectType())).collect(Collectors.groupingBy(FormulaImpl::getTriggerType));
        for (VaBillBackWriteTypeIntf type : this.backWriteType) {
            boolean assignableFrom;
            String backWriteType = type.getType();
            if (!mapFormulas.containsKey(backWriteType) || mapFormulas.get(backWriteType).size() == 0 || !type.getTriggerType().equals(triggerType) || !(assignableFrom = type.getDependModel().isAssignableFrom(billModel.getClass()))) continue;
            log.info("\u5355\u636e{}\u6821\u9a8c\u662f\u5426\u9700\u8981\u6267\u884c{} \u7c7b\u578b\u53cd\u5199\u516c\u5f0f\uff0c\u7c7b\u540d{}", billcode, type.getTitle(), type.getClass().getName());
            boolean needExecute = type.needExecute(billModel);
            if (!needExecute) {
                log.info("\u5355\u636e{}\u6821\u9a8c\u4e0d\u6ee1\u8db3\u6267\u884c{} \u7c7b\u578b\u53cd\u5199\u516c\u5f0f\u6761\u4ef6\uff0c\u7c7b\u540d{}", billcode, type.getTitle(), type.getClass().getName());
                continue;
            }
            List<FormulaImpl> list = mapFormulas.get(backWriteType);
            for (FormulaImpl formula2 : list) {
                log.info("\u5355\u636e{}\u5f00\u59cb\u6267\u884c{} \u7c7b\u578b\u53cd\u5199\u516c\u5f0f\uff0c\u516c\u5f0f\u5185\u5bb9{}", billcode, type.getTitle(), formula2.getExpression());
                try {
                    FormulaUtils.execute((FormulaImpl)formula2, (Model)billModel);
                }
                catch (Exception e) {
                    log.error("\u5355\u636e{}\u6267\u884c{} \u7c7b\u578b\u53cd\u5199\u516c\u5f0f\u53d1\u751f\u5f02\u5e38\uff0c\u516c\u5f0f\u5185\u5bb9{}", billcode, type.getTitle(), formula2.getExpression(), e);
                    throw new BillException(e.getMessage());
                }
                log.info("\u5355\u636e{}\u7ed3\u675f\u6267\u884c{} \u7c7b\u578b\u53cd\u5199\u516c\u5f0f\uff0c\u516c\u5f0f\u5185\u5bb9{}", billcode, type.getTitle(), formula2.getExpression());
            }
        }
    }
}

