package org.swrlapi.core;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.visitors.SWRLAPIEntityVisitorEx;

/**
 * A renderer for {@link org.swrlapi.core.SWRLAPIRule} and {@link org.swrlapi.sqwrl.SQWRLQuery} objects.
 *
 * Can create using the {@link org.swrlapi.core.SWRLAPIOWLOntology}.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 * @see org.swrlapi.core.SWRLAPIOWLOntology
 */
public interface SWRLRuleRenderer extends SWRLAPIEntityVisitorEx<String>
{
  /**
   * @param rule A SWRL rule
   * @return A text rendering of the rule
   */
  @NonNull String renderSWRLRule(SWRLRule rule);
}
