/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.dimension.internal.service.DimensionManageService
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.common.subject.impl.subject.expimp;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.subject.impl.subject.data.SubjectUtil;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.BooleanValEnum;
import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectFieldDefineHolder;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.gcreport.dimension.internal.service.DimensionManageService;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectContentExporter {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private DimensionManageService dimensionManageService;

    public List<List<Object>> doExport(SubjectFieldDefineHolder defineHolder) {
        List<SubjectDTO> subjects = this.subjectService.list();
        if (subjects.isEmpty()) {
            return CollectionUtils.newArrayList();
        }
        Map<String, BaseDataDO> currencyMap = SubjectUtil.getCurrencyMap();
        List assistDimList = this.dimensionManageService.listDimensions();
        ArrayList<List<Object>> rowList = new ArrayList<List<Object>>(subjects.size());
        for (SubjectDTO subject : subjects) {
            rowList.add(this.convertRow(defineHolder, assistDimList, currencyMap, subject));
        }
        return rowList;
    }

    private List<Object> convertRow(SubjectFieldDefineHolder defineHolder, List<DimensionQueryVO> assistDimList, Map<String, BaseDataDO> currencyMap, SubjectDTO subject) {
        ArrayList<Object> row = new ArrayList<Object>(defineHolder.getDefineList().size());
        row.add(subject.getCode());
        row.add(subject.getName());
        row.add(subject.getShortname());
        row.add("-".equals(subject.getParentcode()) ? "" : subject.getParentcode());
        row.add(subject.get("orientVo"));
        row.add(subject.get("generaltypeVo"));
        row.add(subject.getRemark());
        Map<String, Integer> assTypeMap = subject.getAssTypeMap();
        if (assTypeMap.isEmpty()) {
            row.add("");
            row.add("");
        } else {
            StringBuilder requiredAssType = new StringBuilder();
            StringBuilder nonRequiredAssType = new StringBuilder();
            for (DimensionQueryVO assistDim : assistDimList) {
                if (!assTypeMap.containsKey(assistDim.getCode())) continue;
                if (BooleanValEnum.YES.getCode().compareTo(assTypeMap.get(assistDim.getCode())) == 0) {
                    requiredAssType.append(assistDim.getTitle()).append(",");
                    continue;
                }
                nonRequiredAssType.append(assistDim.getTitle()).append(",");
            }
            if (requiredAssType.length() > 0) {
                requiredAssType.delete(requiredAssType.length() - 1, requiredAssType.length()).toString();
            }
            if (nonRequiredAssType.length() > 0) {
                nonRequiredAssType.delete(nonRequiredAssType.length() - 1, nonRequiredAssType.length()).toString();
            }
            row.add(requiredAssType.toString());
            row.add(nonRequiredAssType.toString());
        }
        for (ISubjectExpImpFieldDefine extFieldDefine : defineHolder.getExternalDefineList()) {
            row.add(subject.get(extFieldDefine.getCode()));
        }
        return row;
    }
}

