package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class IncorrectDayException extends Exception{
    //날짜 입력 잘못할경우 숫자와 - 이외의 문자 및 특수문자 입력시

    public IncorrectDayException(String message) {
        super(message);
    }
}
