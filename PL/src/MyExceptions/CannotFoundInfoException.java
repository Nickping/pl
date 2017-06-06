package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class CannotFoundInfoException extends Exception {
    //검색 회원 또는 주문정보가 없는 경우
    public CannotFoundInfoException(String message) {
        super(message);
    }
}
