package com.algaworks.osworks.domain.exception;

import java.io.Serial;

public class EntidadeNaoEncontradaException extends NegocioException{

    @Serial
    private static final long serialVersionUID = -7231262891603666165L;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
