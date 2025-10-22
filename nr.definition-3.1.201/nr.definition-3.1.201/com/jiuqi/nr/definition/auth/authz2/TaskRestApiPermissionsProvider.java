/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TaskRestApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_task";
    }

    public String getTitle() {
        return "\u4efb\u52a1\u8bbe\u8ba1";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> items = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
        item.setCode("nr:task_group:query");
        item.setTitle("\u5206\u7ec4\u67e5\u8be2");
        items.add(item);
        PermissionsResourceItemDTO itemGroupManage = new PermissionsResourceItemDTO();
        itemGroupManage.setCode("nr:task_group:manage");
        itemGroupManage.setTitle("\u5206\u7ec4\u7ba1\u7406");
        items.add(itemGroupManage);
        PermissionsResourceItemDTO itemTaskManage = new PermissionsResourceItemDTO();
        itemTaskManage.setCode("nr:task:manage");
        itemTaskManage.setTitle("\u4efb\u52a1\u7ba1\u7406");
        items.add(itemTaskManage);
        PermissionsResourceItemDTO itemReportManage = new PermissionsResourceItemDTO();
        itemReportManage.setCode("nr:task_report:manage");
        itemReportManage.setTitle("\u62a5\u544a\u7ba1\u7406");
        items.add(itemReportManage);
        PermissionsResourceItemDTO itemPrintManage = new PermissionsResourceItemDTO();
        itemPrintManage.setCode("nr:task_print:manage");
        itemPrintManage.setTitle("\u6253\u5370\u7ba1\u7406");
        items.add(itemPrintManage);
        PermissionsResourceItemDTO itemFormulaManage = new PermissionsResourceItemDTO();
        itemFormulaManage.setCode("nr:task_formula:manage");
        itemFormulaManage.setTitle("\u516c\u5f0f\u7ba1\u7406");
        items.add(itemFormulaManage);
        PermissionsResourceItemDTO itemFormulaEditorDesign = new PermissionsResourceItemDTO();
        itemFormulaEditorDesign.setCode("nr:task_formula_editor:manage");
        itemFormulaEditorDesign.setTitle("\u516c\u5f0f\u7f16\u8f91\u5668\u7ba1\u7406");
        items.add(itemFormulaEditorDesign);
        PermissionsResourceItemDTO itemFormDesign = new PermissionsResourceItemDTO();
        itemFormDesign.setCode("nr:task_form:design");
        itemFormDesign.setTitle("\u8868\u6837\u8bbe\u8ba1");
        items.add(itemFormDesign);
        return items;
    }
}

