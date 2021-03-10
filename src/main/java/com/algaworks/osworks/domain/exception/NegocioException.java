package com.algaworks.osworks.domain.exception;

import java.io.Serial;

public class NegocioException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -809319978862266568L;

    public NegocioException(String message){
        super(message);
    }

}
