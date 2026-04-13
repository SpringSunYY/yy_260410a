package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum HealthAppointmentStatusEnum {

    HEALTH_APPOINTMENT_STATUS_1("1", "待确认"),
    HEALTH_APPOINTMENT_STATUS_2("2", "已确定"),
    HEALTH_APPOINTMENT_STATUS_3("3", "已完成"),
    HEALTH_APPOINTMENT_STATUS_4("4", "已取消");

    private final String value;
    private final String label;

    HealthAppointmentStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, HealthAppointmentStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (HealthAppointmentStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<HealthAppointmentStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}