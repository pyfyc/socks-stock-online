package pro.sky.socksstockonline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidSocksQuantityException extends RuntimeException{
    public InvalidSocksQuantityException(String message) {
        super(message);
    }
}
