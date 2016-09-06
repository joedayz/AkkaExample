package tss.com.pe.mileage.util;

/**
 * Created by josediaz on 1/09/2016.
 */
public class MileageRuntimeException extends RuntimeException {

    public MileageRuntimeException(String s) {
        super(s);
    }

    public MileageRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
