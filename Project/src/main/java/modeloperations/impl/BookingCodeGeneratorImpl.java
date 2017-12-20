package modeloperations.impl;

import modeloperations.BookingCodeGenerator;
import java.util.UUID;

public class BookingCodeGeneratorImpl implements BookingCodeGenerator {
    public String generateCode() {
        //TODO добавить проверку в базе данных
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString();
        return code.substring(0,code.indexOf("-"));
    }


}


