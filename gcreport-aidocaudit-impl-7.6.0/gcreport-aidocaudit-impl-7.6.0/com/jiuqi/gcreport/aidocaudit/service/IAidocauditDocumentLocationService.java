/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 */
package com.jiuqi.gcreport.aidocaudit.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AnnotationFileDTO;
import com.jiuqi.gcreport.aidocaudit.dto.LineTextPosition;

public interface IAidocauditDocumentLocationService {
    public AnnotationFileDTO downloadAnnotationFile(String var1);

    public BusinessResponseEntity<LineTextPosition> targeting(String var1, String var2, String var3);
}

