/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.event.BaseDataEvent
 */
package com.jiuqi.gcreport.rate.impl.listen;

import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.event.BaseDataEvent;
import java.util.ArrayList;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RateTypeChangeListener
implements ApplicationListener<BaseDataEvent> {
    @Override
    public void onApplicationEvent(BaseDataEvent baseDataEvent) {
        BaseDataDTO baseDataDTO = baseDataEvent.getBaseDataDTO();
        if (!baseDataDTO.getTableName().equals("MD_RATETYPE") || baseDataEvent.getEventType() != BaseDataOption.EventType.ADD) {
            return;
        }
        this.addRateInfoTableFields(baseDataEvent);
    }

    private void addRateInfoTableFields(BaseDataEvent baseDataEvent) {
        BaseDataDTO baseDataDTO = baseDataEvent.getBaseDataDTO();
        String code = baseDataDTO.getCode();
        DataModelColumn column = new DataModelColumn();
        column.setColumnName("RATETYPE_" + code);
        column.setColumnTitle(baseDataDTO.getName());
        column.setColumnType(DataModelType.ColumnType.NUMERIC);
        Integer[] lengths = new Integer[]{19, 6};
        column.setLengths(lengths);
        column.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
        ArrayList<DataModelColumn> addColumList = new ArrayList<DataModelColumn>();
        addColumList.add(column);
        CommonRateUtils.updateDefine("MD_ENT_RATE", addColumList);
    }
}

