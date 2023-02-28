package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Thrown when a necessary parameters are not found. <br> 
 * 
 * Code: 422
 * 
 * @author Mohibur Rashid
 *
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ParameterNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParameterNotFoundException(String msg) {
        super(msg);
    }

}
