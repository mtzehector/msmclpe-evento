package mx.gob.imss.dpes.evento.exception;

import mx.gob.imss.dpes.common.exception.BusinessException;

import java.util.List;

public class BitacoraInterfazTopicException extends BusinessException {

    public final static String KEY = "msg30000";

    public BitacoraInterfazTopicException() {
        super(KEY);
    }
    public BitacoraInterfazTopicException(List parameters){
        super(KEY);
        super.addParameters(parameters);
    }
    public BitacoraInterfazTopicException(String causa) {
        super(causa);
    }
}
