package com.algonquin.cst8288.assignment1.persistence;

import java.io.IOException;
import java.lang.reflect.Field;

import com.algonquin.cst8288.assignment1.employee.Employee;

public class TextFormatter implements Formatter {

    @Override
    public String format(Employee employee) throws IOException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        StringBuilder formattedText = new StringBuilder();
        formattedText.append("(");

        Field[] fields = Employee.class.getDeclaredFields();

        boolean first = true;
        for (Field field : fields) {
            if (!first) {
                formattedText.append(", ");
            }

            field.setAccessible(true);

            try {
                Object value = field.get(employee);
                formattedText.append(field.getName()).append("=").append(value);
            } catch (IllegalAccessException e) {
                throw new IOException("Error accessing field: " + field.getName(), e);
            }

            first = false;
        }

        formattedText.append(")");

        return formattedText.toString();
    }
}
