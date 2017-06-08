package MyExceptions;

/**
 * Created by admin on 2017-05-19.
 */
public class NotEnoughInfoException extends Exception {
    //입력된 내용 없이 주문 취소 고객등록 고객검색 고객삭제 메뉴 클릭할 경우
    public NotEnoughInfoException(String message) {
        super(message);
    }
}
