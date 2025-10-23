/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public interface BlobContainer {
    public static final String CONTAINER_NAME = "NpBlobStorage";

    public boolean exists(String var1);

    public boolean exists(String var1, String var2);

    public void uploadFromStream(String var1, InputStream var2) throws IOException;

    public String uploadFromStreamExten(String var1, InputStream var2) throws IOException;

    public void uploadFromStream(String var1, String var2, InputStream var3) throws IOException;

    public void uploadText(String var1, String var2) throws IOException;

    public void uploadText(String var1, String var2, String var3) throws IOException;

    public void downloadToStream(String var1, OutputStream var2) throws IOException;

    public void downloadToStream(String var1, String var2, OutputStream var3) throws IOException;

    public String downloadText(String var1) throws IOException;

    public String downloadText(String var1, String var2) throws IOException;

    public byte[] downloadBytes(String var1) throws IOException;

    public byte[] downloadBytes(String var1, String var2) throws IOException;

    public void deleteIfExists(String var1);

    public void deleteAllBlobs();

    public void deleteBlobs(String var1);

    public URI getURI() throws URISyntaxException;

    public URI getURI(String var1) throws URISyntaxException;

    public URI getURI(String var1, String var2) throws URISyntaxException;

    public void startCopyFromBlob(String var1, String var2) throws IOException;
}

