package fatec.v2.unlockway.domain.exception;

public class ResourceNotFoundException extends Exception{
  public ResourceNotFoundException(String statement) {
    super(statement);
  }
}
