package com.platform.service.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.platform.service.core.store.FileType;

public abstract class IOUtil {
    
    public static final String CHARSET_NAME_UTF8 = "UTF-8";
    
    public static final Charset CHARSET_UTF8 = Charset.forName(CHARSET_NAME_UTF8);
    
    public static final byte[] EMPTY_BYTES = new byte[0];
    
    private static final int DEFAULT_SIZE = 4096;
    
    public static void close(Closeable... closeableAry) {
        for (Closeable closeable : closeableAry) {
            close(closeable);
        }
    }
    
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static byte[] base64StringToBytes(String base64String) {
        if (StringUtils.isNotEmpty(base64String)) {
            return Base64.decodeBase64(base64String);
        } else {
            return EMPTY_BYTES;
        }
    }
    
    public static byte[] fileToBytes(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return streamToBytes(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
        return null;
    }
    
    public static byte[] streamToBytes(InputStream is) {
        return streamToBytes(is, false);
    }
    
    public static byte[] streamToBytes(InputStream is, boolean closeInputStream) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen = -1;
            while (-1 != (readedLen = is.read(buff))) {
                baos.write(buff, 0, readedLen);
            }
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(baos);
            if (closeInputStream) {
                close(is);
            }
        }
        return null;
    }
    
    public static String bytesToBase64String(byte[] bytes) {
        if (null != bytes) {
            return Base64.encodeBase64String(bytes);
        } else {
            return StringUtils.EMPTY;
        }
    }
    
    public static String fileToBase64String(File file) {
        byte[] bytes = fileToBytes(file);
        return bytesToBase64String(bytes);
    }
    
    public static String getFileHeader(File file) {
        if (null == file || 0 == file.length() || !file.exists()) {
            return "";
        }
        InputStream ips = null;
        try {
            ips = new FileInputStream(file);
            return getFileHeader(ips);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } finally {
            close(ips);
        }
    }
    
    public static String getFileHeader(InputStream ips) {
        try {
            byte[] b = new byte[10];
            ips.read(b, 0, b.length);
            return bytesToHexString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    public static String getFileType(File file) {
        try {
            return getFileType(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
    
    public static String getFileType(InputStream ips) {
        String fileHeader = getFileHeader(ips);
        if (null == fileHeader) {
            return StringUtils.EMPTY;
        }
        return FileType.getByHeader(fileHeader);
    }
    
    public static String streamToBase64String(InputStream is) {
        return streamToBase64String(is, false);
    }
    
    public static String streamToBase64String(InputStream is, boolean closeInputStream) {
        byte[] bytes = streamToBytes(is, closeInputStream);
        if (null != bytes) {
            return Base64.encodeBase64String(bytes);
        } else {
            return StringUtils.EMPTY;
        }
    }
    
    public static boolean bytesToFile(byte[] bytes, File file) {
        if (null != bytes && null != file) {
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                return streamToFile(bais, file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(bais);
            }
        }
        return false;
    }
    
    public static boolean streamToFile(InputStream is, File file) {
        return streamToFile(is, file, false);
    }
    
    public static boolean streamToFile(InputStream is, File file, boolean closeInputStream) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen = -1;
            while (-1 != (readedLen = is.read(buff))) {
                fos.write(buff, 0, readedLen);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fos);
            if (closeInputStream) {
                close(is);
            }
        }
        return false;
    }
    
    public static boolean base64StringToFile(String base64String, File file) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return bytesToFile(bytes, file);
        }
        return false;
    }
    
    public static InputStream bytesToStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
    
    public static InputStream base64StringToStream(String base64String) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return new ByteArrayInputStream(bytes);
        }
        return null;
    }
    
    public static InputStream fileToStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
    
    public static String fileToString(File file, String charset) {
        return fileToString(file, Charset.forName(charset));
    }
    
    public static String fileToString(File file, Charset charset) {
        byte[] bytes = fileToBytes(file);
        if (null != bytes) {
            return new String(bytes, charset);
        }
        return StringUtils.EMPTY;
    }
    
    public static void delete(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
    
    public static void copy(InputStream ips, OutputStream fos, boolean flushFlag) {
        try {
            IOUtils.copy(ips, fos);
            if (flushFlag) {
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Integer streamLength(InputStream ips) {
        if (null == ips)
            return 0;
        return streamToBytes(ips).length;
    }
    
    public static File[] listFile(File pathFile, FileFilter fileFilter) {
        return pathFile.listFiles(fileFilter);
    }
    
    public static File findFile(File pathFile, FileFilter fileFilter) {
        File[] fileAry = listFile(pathFile, fileFilter);
        if (fileAry.length == 0) {
            return null;
        } else if (fileAry.length == 1) {
            return fileAry[0];
        }
        return fileAry[0];
    }
    
    public static void flush(Flushable flush) {
        if (null != flush) {
            try {
                flush.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void flush(Flushable... flushAry) {
        if (null != flushAry && 0 == flushAry.length) {
            for (Flushable flushable : flushAry) {
                flush(flushable);
            }
        }
    }
    
    public static InputStream reset(InputStream ips) {
        try {
            ips.reset();
            mark(ips);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ips;
    }
    
    public static InputStream mark(InputStream ips) {
        return mark(ips, Integer.MAX_VALUE);
    }
    
    public static InputStream mark(InputStream ips, Integer readlimit) {
        ips = new BufferedInputStream(ips);
        ips.mark(readlimit);
        return ips;
    }
    
    public static BufferedImage bytesToBufferedImage(byte[] b) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            return ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
