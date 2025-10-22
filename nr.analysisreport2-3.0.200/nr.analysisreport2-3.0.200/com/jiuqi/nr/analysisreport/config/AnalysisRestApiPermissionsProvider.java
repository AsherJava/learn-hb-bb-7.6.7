/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.analysisreport.config;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalysisRestApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_analysisreport";
    }

    public String getTitle() {
        return "\u5206\u6790\u62a5\u544a";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> items = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
        item.setCode("nr:analysisreport:template");
        item.setTitle("\u62a5\u544a\u6a21\u677f\u7ba1\u7406");
        items.add(item);
        PermissionsResourceItemDTO group = new PermissionsResourceItemDTO();
        group.setCode("nr:analysisreport:group");
        group.setTitle("\u62a5\u544a\u6a21\u677f\u5206\u7ec4\u7ba1\u7406");
        items.add(group);
        PermissionsResourceItemDTO chapter = new PermissionsResourceItemDTO();
        chapter.setCode("nr:analysisreport:chapter");
        chapter.setTitle("\u6a21\u677f\u7ae0\u8282\u7ba1\u7406");
        items.add(chapter);
        PermissionsResourceItemDTO generator = new PermissionsResourceItemDTO();
        generator.setCode("nr:analysisreport:generator");
        generator.setTitle("\u62a5\u544a\u67e5\u770b");
        items.add(generator);
        PermissionsResourceItemDTO export = new PermissionsResourceItemDTO();
        export.setCode("nr:analysisreport:export");
        export.setTitle("\u62a5\u544a\u5bfc\u51fa");
        items.add(export);
        PermissionsResourceItemDTO batchExport = new PermissionsResourceItemDTO();
        batchExport.setCode("nr:analysisreport:batchexport");
        batchExport.setTitle("\u62a5\u544a\u6279\u91cf\u5bfc\u51fa");
        items.add(batchExport);
        PermissionsResourceItemDTO exportModel = new PermissionsResourceItemDTO();
        exportModel.setCode("nr:analysisreport:exportmodel");
        exportModel.setTitle("\u5bfc\u51fa\u6a21\u677f");
        items.add(exportModel);
        PermissionsResourceItemDTO importModel = new PermissionsResourceItemDTO();
        importModel.setCode("nr:analysisreport:importmodel");
        importModel.setTitle("\u5bfc\u5165\u6a21\u677f");
        items.add(importModel);
        return items;
    }
}

