package com.example.demo.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BedCountConverter implements AttributeConverter<BedCount, String> {

    @Override
    public String convertToDatabaseColumn(BedCount bedCount) {
        return bedCount != null ? bedCount.getCount() : null;
    }

    @Override
    public BedCount convertToEntityAttribute(String count) {
        return count != null ? BedCount.fromCount(count) : null;
    }
}
