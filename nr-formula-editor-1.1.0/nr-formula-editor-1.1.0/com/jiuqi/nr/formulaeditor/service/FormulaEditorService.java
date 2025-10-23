/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.service;

import com.jiuqi.nr.formulaeditor.common.FormulaEditorException;
import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.FormulaObj;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;
import com.jiuqi.nr.formulaeditor.internal.IFormulaEditor;
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
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormulaEditorService {
    @Autowired(required=false)
    private List<IFormulaEditor> formulaEditors;

    public TaskParam queryTaskData(EditObject editObject) {
        return this.getFormulaEditor(editObject).queryTaskData(editObject);
    }

    public List<SchemeData> querySchemeData(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).querySchemeData(editObject, paramObj);
    }

    public List<ITree<EditorNodeData>> queryFormData(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).queryFormData(editObject, paramObj);
    }

    public List<ITree<EditorNodeData>> searchFormData(EditObject editObject, ParamObj paramObj) {
        if (!StringUtils.hasText(paramObj.getKeyWords())) {
            return Collections.emptyList();
        }
        return this.getFormulaEditor(editObject).searchFormData(editObject, paramObj);
    }

    public FormData queryStyleData(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).queryStyleData(editObject, paramObj);
    }

    public List<ITree<EditorNodeData>> initTreeData(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).initTreeData(editObject, paramObj);
    }

    public List<ITree<EditorNodeData>> queryTreeGroupData(EditObject editObject, TreeObj treeObj) {
        return this.getFormulaEditor(editObject).queryTreeGroupData(editObject, treeObj);
    }

    public List<FieldData> queryFieldData(EditObject editObject, TreeObj treeObj) {
        return this.getFormulaEditor(editObject).queryFieldData(editObject, treeObj);
    }

    public List<FormulaCheckData> formulaCheck(EditObject editObject, FormulaObj formulaObj) {
        return this.getFormulaEditor(editObject).formulaCheck(editObject, formulaObj);
    }

    public List<FunctionList> queryFunctionData(EditObject editObject) {
        return this.getFormulaEditor(editObject).queryFunctionData(editObject);
    }

    public List<EntityData> queryEnumDefine(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).queryEnumDefine(editObject, paramObj);
    }

    public List<FormulaVariableData> queryFormulaValiData(EditObject editObject, ParamObj paramObj) {
        return this.getFormulaEditor(editObject).queryFormulaValiData(editObject, paramObj);
    }

    private IFormulaEditor getFormulaEditor(EditObject editObject) {
        if (null != this.formulaEditors) {
            String editKey = "DEFAULT_EDITOR_ID";
            if (StringUtils.hasLength(editObject.getEditKey())) {
                editKey = editObject.getEditKey();
            }
            for (IFormulaEditor formulaEditor : this.formulaEditors) {
                if (!editKey.equals(formulaEditor.getId())) continue;
                return formulaEditor;
            }
        }
        throw new RuntimeException(FormulaEditorException.EDITOR_EXCEPTION_001.getMessage());
    }
}

