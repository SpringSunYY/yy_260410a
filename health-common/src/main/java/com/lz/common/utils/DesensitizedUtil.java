package com.lz.common.utils;

/**
 * 脱敏工具类
 *
 * @author YY
 */
public class DesensitizedUtil
{
    /**
     * 密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password)
    {
        if (StringUtils.isBlank(password))
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.repeat('*', password.length());
    }

    /**
     * 车牌中间用*代替，如果是错误的车牌，不处理
     *
     * @param carLicense 完整的车牌号
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense)
    {
        if (StringUtils.isBlank(carLicense))
        {
            return StringUtils.EMPTY;
        }
        // 普通车牌
        if (carLicense.length() == 7)
        {
            carLicense = StringUtils.hide(carLicense, 3, 6);
        }
        else if (carLicense.length() == 8)
        {
            // 新能源车牌
            carLicense = StringUtils.hide(carLicense, 3, 7);
        }
        return carLicense;
    }

    /**
     * 身份证号脱敏
     * 显示前3位和后4位，中间用*代替
     *
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    public static String idCard(String idCard)
    {
        if (StringUtils.isBlank(idCard))
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.hide(idCard, 3, idCard.length() - 4);
    }

    /**
     * 手机号脱敏
     * 显示前3位和后4位，中间用*代替
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String mobilePhone(String phone)
    {
        if (StringUtils.isBlank(phone))
        {
            return StringUtils.EMPTY;
        }
        return StringUtils.hide(phone, 3, 7);
    }

    /**
     * 自定义脱敏规则
     * 如果长度小于等于4，直接全部脱敏
     * 如果长度大于4，脱敏二分之一（向下取整）
     *
     * @param content 原始内容
     * @return 脱敏后的内容
     */
    public static String customDesensitize(String content)
    {
        if (StringUtils.isBlank(content))
        {
            return StringUtils.EMPTY;
        }
        int length = content.length();
        if (length <= 4)
        {
            return StringUtils.repeat('*', length);
        }
        int hideCount = length / 2;
        int start = length / 4;
        int end = start + hideCount;
        return StringUtils.hide(content, start, end);
    }
}
