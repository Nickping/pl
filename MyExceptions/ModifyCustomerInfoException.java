package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class ModifyCustomerInfoException extends Exception {
    //고객 정보 변경시

    public ModifyCustomerInfoException(String message) {
        super(message);
    }
}
