package com.savorgames.service.exception;

public class DeleteTopicException extends Exception{

  private static final long serialVersionUID = -2407325517672817437L;
  public DeleteTopicException() {
    super();
  }

  public DeleteTopicException(String message, Throwable cause) {
    super(message, cause);
  }

  public DeleteTopicException(String message) {
    super(message);
  }

  public DeleteTopicException(Throwable cause) {
    super(cause);
  }
}
