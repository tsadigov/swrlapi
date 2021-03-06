package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.exceptions.LiteralException;

import java.net.URI;

/**
 * Wraps an OWLAPI literal to provide additional convenience methods used by the SWRLAPI.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public interface Literal
{
  boolean isNumeric();

  boolean isByte();

  boolean isShort();

  boolean isInt();

  boolean isLong();

  boolean isFloat();

  boolean isDouble();

  boolean isString();

  boolean isBoolean();

  boolean isAnyURI();

  boolean isTime();

  boolean isDate();

  boolean isDateTime();

  boolean isDuration();

  byte getByte() throws LiteralException;

  short getShort() throws LiteralException;

  int getInt() throws LiteralException;

  long getLong() throws LiteralException;

  float getFloat() throws LiteralException;

  double getDouble() throws LiteralException;

  @NonNull String getString() throws LiteralException;

  boolean getBoolean() throws LiteralException;

  @NonNull URI getAnyURI() throws LiteralException;

  @NonNull XSDTime getTime() throws LiteralException;

  @NonNull XSDDate getDate() throws LiteralException;

  @NonNull XSDDateTime getDateTime() throws LiteralException;

  @NonNull XSDDuration getDuration() throws LiteralException;

  @NonNull String getValue();

  boolean isComparable();

  boolean isQuotableType();

  @NonNull @Override
  String toString();

  @NonNull String toQuotedString();

  @NonNull OWLLiteral getOWLLiteral();

  @NonNull OWLDatatype getOWLDatatype();
}
