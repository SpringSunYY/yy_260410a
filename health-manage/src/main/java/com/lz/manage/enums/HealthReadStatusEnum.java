package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum HealthReadStatusEnum {

    HEALTH_READ_STATUS_0("0", "未读"),
    HEALTH_READ_STATUS_1("1", "已读");

    private final String value;
    private final String label;

    HealthReadStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, HealthReadStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (HealthReadStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<HealthReadStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}