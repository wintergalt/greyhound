package com.vgs.greyhound.model.exception;

@SuppressWarnings("serial")
public class ModelException extends Exception {

    public ModelException() {
    }

    public ModelException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ModelException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ModelException(String string) {
        super(string);
    }

}
