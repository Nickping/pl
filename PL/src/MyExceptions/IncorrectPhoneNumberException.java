package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class IncorrectPhoneNumberException extends Exception {
    //전화번호 입력시 숫자와/ 이외의 문자 및 특수문자 입력시
    public IncorrectPhoneNumberException(String message) {
        super(message);
    }
}
