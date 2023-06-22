package com.sgbg.blockchain.common.exception;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(){
        super("wrong password");
    }
}
