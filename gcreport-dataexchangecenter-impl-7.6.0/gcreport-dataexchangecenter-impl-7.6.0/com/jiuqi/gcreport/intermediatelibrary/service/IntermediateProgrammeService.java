/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity
 */
package com.jiuqi.gcreport.intermediatelibrary.service;

import com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity;
import java.util.List;

public interface IntermediateProgrammeService {
    public List<ILVO> getAllProgramme();

    public ILVO getProgrammeForId(ILCondition var1);

    public String addProgramme(ILCondition var1);

    public ILEntity getProgrammeOfName(ILCondition var1);

    public void updateProgramme(ILCondition var1);

    public void deleteProgramme(ILCondition var1);

    public void addProgrammeOfField(ILCondition var1, boolean var2);

    public void synchroProgramme(ILExtractCondition var1) throws Exception;

    public void clearProgramme(ILClearCondition var1) throws Exception;

    public ILFieldPageVO getFieldOfProgrammeId(ILFieldCondition var1);

    public void deleteProgrammeOfField(ILCondition var1);

    public void deleteAllProgrammeOfField(ILCondition var1);

    public List<ILTreeVO> getFieldGroupMessage(ILFieldCondition var1);

    public ILFieldPageVO getFieldForGroup(ILFieldCondition var1);

    public List<ZbPickerEntity> getZbPickerList(ILFieldCondition var1);
}

