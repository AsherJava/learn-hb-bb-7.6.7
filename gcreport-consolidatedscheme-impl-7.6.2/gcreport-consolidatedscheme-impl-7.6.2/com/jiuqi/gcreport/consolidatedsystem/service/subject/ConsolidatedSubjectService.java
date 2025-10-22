/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.service.subject;

import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

public interface ConsolidatedSubjectService {
    public ConsolidatedSubjectEO getSubjectById(String var1);

    public ConsolidatedSubjectEO getSubjectByCode(String var1, String var2);

    public String getTitleByCode(@NotNull String var1, @NotNull String var2);

    public FieldDefine getFieldDefineBySubject(ConsolidatedSubjectEO var1);

    public FieldDefine getFieldDefineByCode(String var1, String var2);

    public boolean isLeafByCode(String var1, String var2);

    public List<ConsolidatedSubjectEO> listAllSubjectsBySystemId(String var1);

    public List<ConsolidatedSubjectEO> listSubjectsBySystemIdWithSortOrder(String var1);

    public List<ConsolidatedSubjectEO> listAllChildrenSubjects(String var1, String var2);

    public Set<String> listAllChildrenCodes(String var1, String var2);

    public List<ConsolidatedSubjectEO> listDirectChildrensByCode(String var1, String var2);

    public List<FieldDefine> listAllFieldDefines(String var1);

    public Set<String> listAllChildrenCodesContainsSelf(List<String> var1, String var2);

    public Set<String> listAllCodesByAttr(String var1, SubjectAttributeEnum var2);

    public Set<String> filterByExcludeChild(String var1, Collection<String> var2);

    public Set<String> filterByExcludeChild(List<ConsolidatedSubjectEO> var1, Collection<String> var2);

    public List<SubjectTreeVO> treeBaseData(String var1);

    public List<TreeNode> getFieldDefinesByTable(String var1);
}

