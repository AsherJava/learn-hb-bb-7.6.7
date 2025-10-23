/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.formulaeditor.internal.param;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.formulaeditor.common.EditorUtils;
import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;
import com.jiuqi.nr.formulaeditor.internal.param.core.DefaultFormulaEditor;
import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.FormData;
import com.jiuqi.nr.formulaeditor.vo.ITree;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nr.formulaeditor.vo.TaskData;
import com.jiuqi.nr.formulaeditor.vo.TaskParam;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.List;

public class OnlyFormEditor
extends DefaultFormulaEditor {
    @Override
    public String getId() {
        return "NO_TASK";
    }

    @Override
    public TaskParam queryTaskData(EditObject editObject) {
        TaskParam taskParam = new TaskParam();
        taskParam.setTasks(new ArrayList<TaskData>());
        return taskParam;
    }

    @Override
    public List<SchemeData> querySchemeData(EditObject editObject, ParamObj paramObj) {
        return new ArrayList<SchemeData>();
    }

    @Override
    public List<ITree<EditorNodeData>> queryFormData(EditObject editObject, ParamObj paramObj) {
        DesignFormDefine form = this.iDesignTimeViewController.getForm("a97324c5-8dbb-4442-9397-389693bd6101");
        DesignFormDefine form2 = this.iDesignTimeViewController.getForm("17ac986a-7624-4af2-81d0-c5be90b01869");
        ArrayList<ITree<EditorNodeData>> list = new ArrayList<ITree<EditorNodeData>>();
        list.add(EditorUtils.createFormITree((FormDefine)form, "", ""));
        list.add(EditorUtils.createFormITree((FormDefine)form2, "", ""));
        return list;
    }

    @Override
    public FormData queryStyleData(EditObject editObject, ParamObj paramObj) {
        if (null != editObject.getParams()) {
            String apo = (String)editObject.getParams().get("apo");
            FormData formData = new FormData();
            formData.setKey(apo);
            Grid2Data formStyle = this.iDesignTimeViewController.getFormStyle(apo);
            CellBook cellBook = new CellBook();
            CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)formStyle, (CellBook)cellBook, (String)(paramObj.getFormKey() + "-" + paramObj.getViewType()));
            formData.setCellBook(cellBook);
            return formData;
        }
        return super.queryStyleData(editObject, paramObj);
    }
}

