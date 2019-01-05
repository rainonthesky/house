package com.kiyozawa.houses.utils;
/**
 * 校验密码是否有效
 */

public class HashUtils1 {
    public  static final int SALT_SIZE=3;
    public  static final int HASH_ITERATIONS=1024;

    public boolean vaildatePsd(String plainPsd,String encryptPsd) {
        //将密文逆转，截取salt
        byte[] salt = EncryptUtil.decodeHex(encryptPsd.substring(0, SALT_SIZE * 2));
        //重新平凑 盐+密码 进行加密
        byte[] hashPassword = EncryptUtil.sha1(plainPsd.getBytes(),salt, HASH_ITERATIONS);
        String newEncrypPsd = EncryptUtil.encodeHex(salt) + EncryptUtil.encodeHex(hashPassword);
        boolean flag = false;
        flag = newEncrypPsd.equals(encryptPsd);
        return flag;
    }
    public   String encryPassword(String password ){
        //生成随机数，所谓的盐
        byte[] salt =EncryptUtil.generateSalt(SALT_SIZE);
        //盐+密码   进行sha1的加密
        byte[] hashPass = EncryptUtil.sha1(password.getBytes(), salt, HASH_ITERATIONS);
        //盐可逆加密+(盐+密码 sha1加密后)可逆加密
        String hashCode=EncryptUtil.encodeHex(salt) + EncryptUtil.encodeHex(hashPass);
        return hashCode.toString();

    }


}
