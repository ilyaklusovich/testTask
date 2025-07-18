package com.test.postservice.letterservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockitoExtension.class)
class BadRequestExceptionTest {

    String CHECK_STR = "test";
    String MESSAGE = "test message";

    @Test
    void BadRequestExceptionTestThrowException() {
        BadRequestException actual = assertThrowsExactly(BadRequestException.class, () -> throwException(CHECK_STR));
        assertEquals(MESSAGE, actual.getMessage());
    }

    private void throwException(String str) throws BadRequestException {
        if (str.equals(CHECK_STR)) {
            throw new BadRequestException(MESSAGE);
        }
    }
}