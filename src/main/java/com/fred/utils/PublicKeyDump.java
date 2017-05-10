package com.fred.utils;


import android.content.Context;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.X509Certificate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import sun.security.pkcs.PKCS7;

public class PublicKeyDump {

    public static byte[] get(Context c, String path) {
        DirectByteArrayOutputStream data = getMetaFileData(c, path);
        if (data != null) {
            return getHelper(data);
        }
        return null;
    }

    private static byte[] getHelper(DirectByteArrayOutputStream data) {
        InputStream in;
        X509Certificate x509;
        try {
            in = new ByteArrayInputStream(data.getArray(), 0, data.getCount());
            PKCS7 pkcs7 = new PKCS7(in);
            x509 = pkcs7.getCertificates()[0];
        } catch (Exception e) {
            return null;
        }

        return x509.getPublicKey().getEncoded();
    }

    private static boolean isPublicKeyFile(String name) {
        String[] postfix = new String[]{".RSA", ".DSA", ".EC"};
        name = name.toUpperCase();
        for (String s : postfix) {
            if (name.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    private static void copyStream(InputStream source, OutputStream target) throws IOException {
        final int bsize = 4096;
        byte[] buffer = new byte[bsize];
        int length = 0;
        while ((length = source.read(buffer)) > 0) {
            target.write(buffer, 0, length);
        }
    }

    private static DirectByteArrayOutputStream getMetaFileData(Context c, String path) {
        ZipInputStream zin = null;
        DirectByteArrayOutputStream out = new DirectByteArrayOutputStream(1024);
        try {
            zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(path)));
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    String name = ze.getName();
                    if (isPublicKeyFile(name)) {
                        copyStream(zin, out);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (Exception e) {
                }
            }
        }
        return out;
    }
}
