package org.swrlapi.core.visitors;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLAPIBuiltInAtom;

public interface SWRLAPIBuiltInAtomVisitorEx<T>
{
  @NonNull T visit(SWRLAPIBuiltInAtom swrlapiBuiltInAtom);
}
