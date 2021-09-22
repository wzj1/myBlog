package com.wzj.blog.myblog.util.key;

import com.wzj.blog.myblog.config.Constant;
import kotlin.text.Charsets;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * @author: 程龙 on 2019/3/13.
 * bolg:  https://me.csdn.net/qq_25749749
 * RSA算法，实现数据的加密解密。
 */
public class RSAUtil1 {

    /**
     *  使用私钥解密
     * @param content
     * @param private_key
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String private_key) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(base64decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     *  使用私钥解密
     * @param content
     * @return
     * @throws Exception
     */
    public static String decrypt(String content) throws Exception {
//        PrivateKey prikey = getPrivateKey(Constant.privateKey);
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.DECRYPT_MODE, prikey);

        return decrypt(content, Constant.privateKey);
    }

    /**
     * 获得私钥
     *
     * @param key
     *            私钥
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = base64decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    /**
     * 得到公钥
     *
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String bysKey){
        PublicKey publicKey = null ;
        try {
            byte[] decodedKey = base64decode(bysKey);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }


    /**
     *  base64加密
     * @param bstr
     * @return
     */
    public static String base64encode(byte[] bstr) {
        String str =  new BASE64Encoder().encode(bstr);
        str = str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        return str;
    }

    /**
     * base64解密
     * @param str
     * @return byte[]
     */
    public static byte[] base64decode(String str) {
        byte[] bt = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }

    /**
     * 使用公钥加密
     *
     * @param content 密文
     * @param pub_key 公钥
     * @return
     */
    public static String encryptByPublic(String content, String pub_key) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(pub_key);
            return  encryptByPublicKey(content,pubkey);

        } catch (Exception e) {
            return null;
        }
    }

    private static String  encryptByPublicKey(String content , PublicKey publicKey) {
        int MAX_ENCRYPT_BLOCK = 117;
        ByteArrayOutputStream out = null;
        byte[] entdate = null;
        try {
            //  对数据加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = content.getBytes(StandardCharsets.UTF_8);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;

            //  对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            entdate = out.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64encode(entdate);
    }

    /**
     * 使用公钥加密
     *
     * @param content 密文
     * @return
     */
    public static String encryptByPublic(String content) {
        PublicKey pubkey = getPublicKeyFromX509(Constant.publicKey);
        return  encryptByPublicKey(content,pubkey);
    }




}



