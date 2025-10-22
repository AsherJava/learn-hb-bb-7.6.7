/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 */
package nr.single.para.parain.internal.controller;

import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import nr.single.para.parain.controller.ISingleParaImportController;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.service.ITaskFileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleParaImportController
implements ISingleParaImportController {
    @Autowired
    private ITaskFileImportService taskFileImportService;

    @Override
    public String ImportSingeTask(String file) throws Exception {
        return this.taskFileImportService.ImportSingleToTask(null, file);
    }

    @Override
    public String ImportSingleToTask(String taskKey, String file) throws Exception {
        return this.taskFileImportService.ImportSingleToTask(taskKey, file);
    }

    @Override
    public String ImportSingleToFormScheme(String taskKey, String schemeKey, String file, SingleParaImportOption option) throws Exception {
        return this.taskFileImportService.ImportSingleToFormScheme(taskKey, schemeKey, file, option);
    }

    @Override
    public SingleFile GetSingleFile(String file) throws Exception {
        SingleFileImpl singleFile = new SingleFileImpl();
        singleFile.infoLoad(file);
        return singleFile;
    }
}

