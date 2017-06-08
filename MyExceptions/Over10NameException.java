package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class Over10NameException extends  Exception{
    //이름이 10자 이상 넘아갈 경우

    public Over10NameException(String message) {
        super(message);
    }
}
