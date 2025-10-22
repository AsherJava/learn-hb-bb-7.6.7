/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 */
package com.jiuqi.bi.publicparam.datasource.caliberdim;

import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceProvider;
import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimParameterRenderer;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrCaliberDimDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ICalibreDefineService calibreDefineService;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NrCaliberDimDataSourceProvider((NrCaliberDimDataSourceModel)model, this.calibreDataService, this.dataModelService, this.calibreDefineService);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.caliber";
    }

    public String title() {
        return "\u62a5\u8868\u53e3\u5f84\u7ef4\u5ea6";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new NrCaliberDimDataSourceModel();
    }

    public boolean isPrivate() {
        return true;
    }

    public IParameterRenderer createParameterRenderer() {
        return new NrCaliberDimParameterRenderer();
    }
}

