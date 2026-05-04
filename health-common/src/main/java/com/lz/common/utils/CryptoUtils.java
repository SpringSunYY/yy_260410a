package com.lz.common.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加密解密工具类
 * 使用AES对称加密算法
 *
 * @author YY
 */
public class CryptoUtils {

    private static final String SECRET_KEY = "health_secret_key_16byte";

    private static final SymmetricCrypto AES = new SymmetricCrypto(
            SymmetricAlgorithm.AES,
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
    );

    /**
     * 加密
     *
     * @param content 明文
     * @return 密文(Base64编码)
     */
    public static String encrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        byte[] encrypted = AES.encrypt(content);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 解密
     * 兼容旧数据：如果解密失败（说明是明文），直接返回原文
     *
     * @param encryptedContent 密文(Base64编码)
     * @return 明文
     */
    public static String decrypt(String encryptedContent) {
        if (StringUtils.isBlank(encryptedContent)) {
            return encryptedContent;
        }
        try {
            byte[] decrypted = Base64.getDecoder().decode(encryptedContent);
            return AES.decryptStr(decrypted);
        } catch (Exception e) {
            // 解密失败，说明是明文（兼容旧数据），直接返回
            return encryptedContent;
        }
    }
}
