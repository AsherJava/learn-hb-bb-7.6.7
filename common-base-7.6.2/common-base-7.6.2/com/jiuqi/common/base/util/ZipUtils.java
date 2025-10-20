/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.fileupload.util.LimitedInputStream
 *  org.apache.commons.io.FileUtils
 *  org.apache.poi.util.IOUtils
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.util.UUIDUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.fileupload.util.LimitedInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public class ZipUtils {
    public static final String TEMP_ROOT_LOCATION = System.getProperty("java.io.tmpdir");
    public static final String TEMP_ZIP_FILE_LOCATION = TEMP_ROOT_LOCATION + File.separator + "commonbase" + File.separator + "tempData" + File.separator + "zip";

    public static void addFile(String zipFilePathStr, String appendFilePathStr) throws IOException {
        Path zipPath = Paths.get(zipFilePathStr, new String[0]);
        Path appendFilePath = Paths.get(appendFilePathStr, new String[0]);
        URI uri = URI.create("jar:file:" + zipPath.toUri().getPath());
        try (final FileSystem zipFileSystem = FileSystems.newFileSystem(uri, new HashMap());){
            final Path root = zipFileSystem.getPath("/", new String[0]);
            Path dest = zipFileSystem.getPath(root.toString(), appendFilePath.getFileName().toString());
            if (!Files.isDirectory(appendFilePath, new LinkOption[0])) {
                Files.copy(appendFilePath, dest, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.walkFileTree(appendFilePath, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){
                    private String dirRoot = null;

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path dest = this.dirRoot != null ? zipFileSystem.getPath(root.toString(), this.dirRoot, File.separator, ZipUtils.subAfter(file.toString(), this.dirRoot, false)) : zipFileSystem.getPath(root.toString(), file.toString());
                        Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path dirToCreate;
                        if (this.dirRoot == null) {
                            dirToCreate = zipFileSystem.getPath(root.toString(), dir.getFileName().toString());
                            this.dirRoot = dir.getFileName().toString();
                        } else {
                            dirToCreate = zipFileSystem.getPath(root.toString(), this.dirRoot, File.separator, ZipUtils.subAfter(dir.toString(), this.dirRoot, false));
                        }
                        if (Files.notExists(dirToCreate, new LinkOption[0])) {
                            Files.createDirectories(dirToCreate, new FileAttribute[0]);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }
        catch (FileAlreadyExistsException fileAlreadyExistsException) {
            // empty catch block
        }
    }

    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        int pos;
        if (ObjectUtils.isEmpty(string)) {
            return null == string ? null : "";
        }
        if (separator == null) {
            return "";
        }
        String str = string.toString();
        String sep = separator.toString();
        int n = pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (-1 == pos || string.length() - 1 == pos) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    public static FileSystem createZip(String path, Charset charset) throws IOException {
        if (null == charset) {
            charset = StandardCharsets.UTF_8;
        }
        HashMap<String, String> env = new HashMap<String, String>();
        env.put("create", "true");
        env.put("encoding", charset.name());
        FileSystem fileSystem = FileSystems.newFileSystem(URI.create("jar:" + Paths.get(path, new String[0]).toUri()), env);
        return fileSystem;
    }

    public static byte[] unzipFileBytesAndDeleteTempFileOnExit(MultipartFile importFile, String name) throws IOException {
        String unzipTempFilePath = TEMP_ZIP_FILE_LOCATION + File.separator + UUIDUtils.newUUIDStr();
        File file = new File(unzipTempFilePath);
        FileUtils.copyInputStreamToFile((InputStream)importFile.getInputStream(), (File)file);
        InputStream inputStream = ZipUtils.get(new ZipFile(file), name);
        byte[] bytes = IOUtils.toByteArray((InputStream)inputStream);
        file.deleteOnExit();
        return bytes;
    }

    public static byte[] unzipFileBytes(ZipFile zipFile, String name) throws IOException {
        InputStream inputStream = ZipUtils.get(zipFile, name);
        byte[] bytes = IOUtils.toByteArray((InputStream)inputStream);
        return bytes;
    }

    public static InputStream get(ZipFile zipFile, String path) throws IOException {
        if (zipFile == null) {
            return null;
        }
        ZipEntry zipEntry = zipFile.getEntry(path);
        if (zipEntry == null) {
            return null;
        }
        LimitedInputStream limitedInputStream = new LimitedInputStream(zipFile.getInputStream(zipEntry), zipEntry.getSize()){

            protected void raiseError(long pSizeMax, long pCount) throws IOException {
            }
        };
        return limitedInputStream;
    }
}

