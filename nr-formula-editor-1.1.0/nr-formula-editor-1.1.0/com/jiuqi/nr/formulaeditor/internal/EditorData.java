/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.internal;

import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.FormulaObj;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;
import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.EntityData;
import com.jiuqi.nr.formulaeditor.vo.FieldData;
import com.jiuqi.nr.formulaeditor.vo.FormData;
import com.jiuqi.nr.formulaeditor.vo.FormulaCheckData;
import com.jiuqi.nr.formulaeditor.vo.FormulaVariableData;
import com.jiuqi.nr.formulaeditor.vo.FunctionList;
import com.jiuqi.nr.formulaeditor.vo.ITree;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nr.formulaeditor.vo.TaskParam;
import com.jiuqi.nr.formulaeditor.vo.TreeObj;
import java.util.List;

public interface EditorData {
    public TaskParam queryTaskData(EditObject var1);

    public List<SchemeData> querySchemeData(EditObject var1, ParamObj var2);

    public List<ITree<EditorNodeData>> queryFormData(EditObject var1, ParamObj var2);

    public List<ITree<EditorNodeData>> searchFormData(EditObject var1, ParamObj var2);

    public FormData queryStyleData(EditObject var1, ParamObj var2);

    public List<ITree<EditorNodeData>> initTreeData(EditObject var1, ParamObj var2);

    public List<ITree<EditorNodeData>> queryTreeGroupData(EditObject var1, TreeObj var2);

    public List<FieldData> queryFieldData(EditObject var1, TreeObj var2);

    public List<FormulaCheckData> formulaCheck(EditObject var1, FormulaObj var2);

    public List<FunctionList> queryFunctionData(EditObject var1);

    public List<EntityData> queryEnumDefine(EditObject var1, ParamObj var2);

    public List<FormulaVariableData> queryFormulaValiData(EditObject var1, ParamObj var2);
}

