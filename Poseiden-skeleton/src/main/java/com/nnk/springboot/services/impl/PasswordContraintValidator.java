package com.nnk.springboot.services.impl;

import com.nnk.springboot.services.ValidPassword;
import lombok.SneakyThrows;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PasswordContraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        //customizing validation messages
        Properties props = new Properties();
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("passay.properties");
        props.load(inputStream);

        MessageResolver resolver = new PropertiesMessageResolver(props);
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
            // length between 8 and 16 characters
            new LengthRule(8, Integer.MAX_VALUE)
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if(result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
