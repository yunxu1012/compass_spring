package com.example.demo.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BathCountConverter implements AttributeConverter<BathCount, String> {

    @Override
    public String convertToDatabaseColumn(BathCount bathCount) {
        return bathCount != null ? bathCount.getCount() : null;
    }

    @Override
    public BathCount convertToEntityAttribute(String count) {
        return count != null ? BathCount.fromCount(count) : null;
    }
}
