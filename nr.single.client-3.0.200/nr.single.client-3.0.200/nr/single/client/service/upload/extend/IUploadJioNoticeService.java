/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.service.upload.extend;

import nr.single.client.bean.UploadJioNoticeParam;
import nr.single.client.bean.UploadJioNoticeResult;

public interface IUploadJioNoticeService {
    public int getNotitykind();

    public String getNoticeName();

    public UploadJioNoticeResult beforeImportNotice(UploadJioNoticeParam var1);

    public UploadJioNoticeResult importNotice(UploadJioNoticeParam var1);

    public UploadJioNoticeResult aftterImportNotice(UploadJioNoticeParam var1);
}

