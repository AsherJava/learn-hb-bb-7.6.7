/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 *  org.apache.commons.collections4.CollectionUtils
 */
package com.jiuqi.budget.systemoption;

import com.jiuqi.budget.autoconfigure.BudProductNameComponent;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import com.jiuqi.budget.dataperiod.format.DataPeriodFormatCollector;
import com.jiuqi.budget.dataperiod.format.DataPeriodFormatVO;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="budDataPeriodOption")
public class BudDataPeriodOption
implements ISystemOptionDeclare {
    public static final String ID = "budDataPeriodOption";
    @Autowired
    private DataPeriodFormatCollector dataPeriodFormatCollector;
    @Autowired
    private ISystemOptionOperator systemOptionOperator;
    @Autowired
    private BudProductNameComponent productNameComponent;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u65f6\u671f\u9ed8\u8ba4\u5c55\u793a\u683c\u5f0f";
    }

    public String getNameSpace() {
        return this.productNameComponent.getProductName();
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        Map<IDataPeriodType, List<DataPeriodFormatVO>> dataPeriodType2FormatMap = this.dataPeriodFormatCollector.getMap();
        Map<IDataPeriodType, BaseDataPeriodFormat> defaultDataPeriodType2FormatMap = this.dataPeriodFormatCollector.getDefaultMap();
        Arrays.stream(DataPeriodType.values()).forEach(dataPeriodType -> optionItems.add((ISystemOptionItem)BudDataPeriodOption.buildSystemOptionItem(dataPeriodType.name(), dataPeriodType.getTitle(), ((BaseDataPeriodFormat)defaultDataPeriodType2FormatMap.get(dataPeriodType)).getName(), "\u8bf7\u9009\u62e9" + dataPeriodType.getTitle(), BudDataPeriodOption.buildSystemOptionalValueList((List)dataPeriodType2FormatMap.get(dataPeriodType)))));
        return optionItems;
    }

    private static SystemOptionItem buildSystemOptionItem(String id, String title, String defaultValue, String placeholder, List<ISystemOptionalValue> optionalValues) {
        SystemOptionItem item = new SystemOptionItem();
        item.setEditMode(SystemOptionConst.EditMode.DROP_DOWN_SINGLE);
        item.setOptionalValues(optionalValues);
        item.setId(id);
        item.setTitle(title);
        item.setDefaultValue(defaultValue);
        item.setPlaceholder(placeholder);
        return item;
    }

    private static List<ISystemOptionalValue> buildSystemOptionalValueList(List<DataPeriodFormatVO> dataPeriodFormatList) {
        if (CollectionUtils.isEmpty(dataPeriodFormatList)) {
            return Collections.emptyList();
        }
        return dataPeriodFormatList.stream().map(formatVO -> new ISystemOptionalValue((DataPeriodFormatVO)formatVO){
            final /* synthetic */ DataPeriodFormatVO val$formatVO;
            {
                this.val$formatVO = dataPeriodFormatVO;
            }

            public String getTitle() {
                return this.val$formatVO.getName();
            }

            public String getValue() {
                return this.val$formatVO.getCode();
            }
        }).collect(Collectors.toList());
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public Map<IDataPeriodType, BaseDataPeriodFormat> getDataPeriodType2FormatMap() {
        HashMap<IDataPeriodType, BaseDataPeriodFormat> resultMap = new HashMap<IDataPeriodType, BaseDataPeriodFormat>();
        Map<String, BaseDataPeriodFormat> formatBeanMap = this.dataPeriodFormatCollector.getFormatBeanMap();
        Arrays.stream(DataPeriodType.values()).forEach(dataPeriodType -> {
            BaseDataPeriodFormat format = (BaseDataPeriodFormat)formatBeanMap.get(this.nvwaSystemOptionService.get(ID, dataPeriodType.getName()));
            if (Objects.nonNull(format)) {
                resultMap.put(format.adaptType(), format);
            }
        });
        this.dataPeriodFormatCollector.getDefaultMap().forEach(resultMap::putIfAbsent);
        return resultMap;
    }
}

