/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.PublishFlagEnum
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.dc.base.common.enums.PublishFlagEnum;
import com.jiuqi.dc.mappingscheme.impl.util.InnerAssistDimProvider;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DcInnerAssistDimProvider
implements InnerAssistDimProvider {
    private List<DimensionVO> innerAssistList = new ArrayList<DimensionVO>();
    private Set<String> assistSet;

    public DcInnerAssistDimProvider() {
        DimensionVO orgDim = new DimensionVO();
        orgDim.setCode("MD_ORG");
        orgDim.setTitle("\u7ec4\u7ec7\u673a\u6784");
        orgDim.setFieldType(Integer.valueOf(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue()));
        orgDim.setDictTableName("MD_ORG");
        orgDim.setPublishedFlag(PublishFlagEnum.PUBLISHED.getCode());
        DimensionVO subjectDim = new DimensionVO();
        subjectDim.setCode("MD_ACCTSUBJECT");
        subjectDim.setTitle("\u79d1\u76ee");
        subjectDim.setFieldType(Integer.valueOf(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue()));
        subjectDim.setDictTableName("MD_ACCTSUBJECT");
        subjectDim.setPublishedFlag(PublishFlagEnum.PUBLISHED.getCode());
        DimensionVO currencyDim = new DimensionVO();
        currencyDim.setCode("MD_CURRENCY");
        currencyDim.setTitle("\u5e01\u522b");
        currencyDim.setFieldType(Integer.valueOf(FieldTypeUtils.FieldType.FIELD_TYPE_STRING.getNrValue()));
        currencyDim.setDictTableName("MD_CURRENCY");
        currencyDim.setPublishedFlag(PublishFlagEnum.PUBLISHED.getCode());
        this.innerAssistList.add(orgDim);
        this.innerAssistList.add(subjectDim);
        this.innerAssistList.add(currencyDim);
        this.assistSet = this.innerAssistList.stream().map(DimensionVO::getCode).collect(Collectors.toSet());
    }

    @Override
    public List<DimensionVO> getInnerAssistList() {
        return Collections.unmodifiableList(this.innerAssistList);
    }

    @Override
    public boolean isInnerAssist(String assistCode) {
        return this.assistSet.contains(assistCode);
    }
}

