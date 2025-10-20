/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.data.DataTargetType
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.DataTargetImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckItem
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.DataTargetImpl;
import com.jiuqi.va.biz.ruler.intf.CheckItem;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public class CheckFieldLength
implements CheckItem {
    public String getName() {
        return "BillCheckFieldLength";
    }

    public String getTitle() {
        return "\u6821\u9a8c\u5b57\u6bb5\u957f\u5ea6";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        throw new UnsupportedOperationException();
    }

    public void execute(Model model, Stream<TriggerEvent> events, List<CheckResult> checkMessages) {
        BillModelImpl billModel = (BillModelImpl)model;
        billModel.getData().getTables().stream().forEach(o -> {
            if (o.getName().endsWith("_M")) {
                return;
            }
            List dataFieldDefines = o.getDefine().getFields().stream().filter(f -> f.getFieldType().equals((Object)DataFieldType.DATA) && f.getRefTableType() == 0 && f.getValueType().equals((Object)ValueType.STRING)).collect(Collectors.toList());
            o.getRows().forEach((i, data) -> dataFieldDefines.forEach(field -> {
                String value = data.getString(field.getName());
                if (!StringUtils.hasText(value)) {
                    return;
                }
                if (value.length() <= field.getLength()) {
                    return;
                }
                CheckResultImpl result = new CheckResultImpl();
                result.setFormulaName("\u5b57\u6bb5\u957f\u5ea6\u6821\u9a8c");
                result.setCheckMessage(field.getTitle() + BillCoreI18nUtil.getMessage("va.billcore.checkfieldlength.fieldexceedmaxlength", new Object[]{field.getLength()}));
                ArrayList<DataTargetImpl> targetList = new ArrayList<DataTargetImpl>();
                DataTargetImpl target = new DataTargetImpl();
                target.setTargetType(DataTargetType.DATACELL);
                target.setTableName(o.getName());
                target.setFieldName(field.getName());
                target.setRowID((UUID)Convert.cast((Object)data.getId(), UUID.class));
                targetList.add(target);
                result.setTargetList(targetList);
                checkMessages.add((CheckResult)result);
            }));
        });
    }
}

