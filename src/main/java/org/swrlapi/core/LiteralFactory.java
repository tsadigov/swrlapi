package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;

import java.net.URI;

/**
 * Factory for constructing SWRLAPI-based literals, which wrap {@link org.semanticweb.owlapi.model.OWLLiteral}s to provide
 * additional convenience methods used by the SWRLAPI.
 *
 * @see Literal
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface LiteralFactory
{
  @NonNull Literal getLiteral(byte b);

  @NonNull Literal getLiteral(short s);

  @NonNull Literal getLiteral(int value);

  @NonNull Literal getLiteral(float value);

  @NonNull Literal getLiteral(double value);

  @NonNull Literal getLiteral(@NonNull String value);

  @NonNull Literal getLiteral(boolean value);

  @NonNull Literal getLiteral(@NonNull URI uri);

  @NonNull Literal getLiteral(@NonNull XSDDate date);

  @NonNull Literal getLiteral(@NonNull XSDTime time);

  @NonNull Literal getLiteral(@NonNull XSDDateTime datetime);

  @NonNull Literal getLiteral(@NonNull XSDDuration duration);

  @NonNull Literal getLiteral(@NonNull OWLLiteral literal);
}
