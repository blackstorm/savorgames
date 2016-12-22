package com.savorgames.service.exception;

public class UpdateTopicException extends Exception{
  private static final long serialVersionUID = -3863187623038568047L;
  public UpdateTopicException() {
    super();
  }

  public UpdateTopicException(String message, Throwable cause) {
    super(message, cause);
  }

  public UpdateTopicException(String message) {
    super(message);
  }

  public UpdateTopicException(Throwable cause) {
    super(cause);
  }
}
