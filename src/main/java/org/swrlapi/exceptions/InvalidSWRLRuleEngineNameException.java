package org.swrlapi.exceptions;

import checkers.nullness.quals.NonNull;

public class InvalidSWRLRuleEngineNameException extends SWRLRuleEngineBridgeException
{
  private static final long serialVersionUID = 1L;

  public InvalidSWRLRuleEngineNameException(@NonNull String message)
  {
    super(message);
  }
}
