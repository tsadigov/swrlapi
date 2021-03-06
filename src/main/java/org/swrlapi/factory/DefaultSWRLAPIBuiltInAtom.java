package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.exceptions.SWRLAPIException;
import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class DefaultSWRLAPIBuiltInAtom extends SWRLBuiltInAtomImpl implements SWRLAPIBuiltInAtom
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String ruleName;
  @NonNull private final IRI builtInIRI;
  @NonNull private final String builtInPrefixedName;
  @NonNull private List<SWRLBuiltInArgument> arguments;
  @NonNull private Set<String> pathVariablePrefixedNames = new HashSet<>();
  private boolean sqwrlCollectionResultsUsed = false;
  private int builtInIndex = -1; // Index of this built-in atom in rule body; left-to-right, first built-in index is 0,
  // second in 1, and so on

  public DefaultSWRLAPIBuiltInAtom(@NonNull String ruleName, @NonNull IRI builtInIRI,
    @NonNull String builtInPrefixedName, @NonNull List<SWRLBuiltInArgument> arguments)
  {
    super(builtInIRI, new ArrayList<>(arguments));
    this.ruleName = ruleName;
    this.builtInIRI = builtInIRI;
    this.builtInPrefixedName = builtInPrefixedName;
    this.arguments = arguments;
  }

  @NonNull @Override public String getRuleName()
  {
    return this.ruleName;
  }

  @Override public void setBuiltInArguments(@NonNull List<SWRLBuiltInArgument> arguments)
  {
    this.arguments = arguments;
  }

  @NonNull @Override public String getBuiltInPrefixedName()
  {
    return this.builtInPrefixedName;
  }

  @NonNull @Override public IRI getBuiltInIRI()
  {
    return this.builtInIRI;
  }

  @NonNull @Override public List<SWRLBuiltInArgument> getBuiltInArguments()
  {
    return Collections.unmodifiableList(this.arguments);
  }

  @Override public int getNumberOfArguments()
  {
    return this.arguments.size();
  }

  @Override public int getBuiltInIndex()
  {
    return this.builtInIndex;
  }

  @Override public void setBuiltInIndex(int builtInIndex)
  {
    this.builtInIndex = builtInIndex;
  }

  @NonNull @Override public Set<String> getPathVariablePrefixedNames()
  {
    return Collections.unmodifiableSet(this.pathVariablePrefixedNames);
  }

  @Override public boolean hasPathVariables()
  {
    return !this.pathVariablePrefixedNames.isEmpty();
  }

  @Override public boolean usesAtLeastOneVariableOf(@NonNull Set<String> variablePrefixedNames)
  {
    Set<String> s = new HashSet<>(variablePrefixedNames);

    s.retainAll(getArgumentsVariablePrefixedNames());

    return !s.isEmpty();
  }

  @Override public boolean isArgumentAVariable(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    return this.arguments.get(argumentNumber) instanceof SWRLVariableBuiltInArgument;
  }

  @Override public boolean isArgumentUnbound(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    return this.arguments.get(argumentNumber).isVariable() && this.arguments.get(argumentNumber).asVariable()
      .isUnbound();
  }

  @Override public boolean hasUnboundArguments()
  {
    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable() && argument.asVariable().isUnbound())
        return true;
    return false;
  }

  @Override public boolean hasVariableArguments()
  {
    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable())
        return true;
    return false;
  }

  @NonNull @Override public Set<String> getUnboundArgumentVariablePrefixedNames()
  {
    Set<String> result = this.arguments.stream()
      .filter(argument -> argument.isVariable() && argument.asVariable().isUnbound())
      .map(argument -> argument.asVariable().getVariablePrefixedName()).collect(Collectors.toSet());

    return Collections.unmodifiableSet(result);
  }

  @NonNull @Override public String getArgumentVariablePrefixedName(int argumentNumber)
  {
    checkArgumentNumber(argumentNumber);

    if (!this.arguments.get(argumentNumber).isVariable())
      throw new SWRLAPIException("expecting a variable for (0-offset) argument #" + argumentNumber);

    return this.arguments.get(argumentNumber).asVariable().getVariablePrefixedName();
  }

  @NonNull @Override public List<String> getArgumentsVariablePrefixedNames()
  {
    List<String> result = this.arguments.stream().filter(SWRLBuiltInArgument::isVariable)
      .map(argument -> argument.asVariable().getVariablePrefixedName()).collect(Collectors.toList());

    return Collections.unmodifiableList(result);
  }

  @NonNull @Override public List<String> getArgumentsVariableNamesExceptFirst()
  {
    List<String> result = new ArrayList<>();
    int argumentCount = 0;

    for (SWRLBuiltInArgument argument : this.arguments)
      if (argument.isVariable() && argumentCount++ != 0)
        result.add(argument.asVariable().getVariableName());

    return Collections.unmodifiableList(result);
  }

  @Override public void addArguments(@NonNull List<SWRLBuiltInArgument> additionalArguments)
  {
    this.arguments.addAll(additionalArguments);
  }

  @Override public void setPathVariablePrefixedNames(@NonNull Set<String> variablePrefixedNames)
  {
    this.pathVariablePrefixedNames = new HashSet<>(variablePrefixedNames);
  }

  @Override public void setUsesSQWRLCollectionResults()
  {
    this.sqwrlCollectionResultsUsed = true;
  }

  @Override public boolean usesSQWRLCollectionResults()
  {
    return this.sqwrlCollectionResultsUsed;
  }

  private void checkArgumentNumber(int argumentNumber)
  {
    if (argumentNumber < 0 || argumentNumber > this.arguments.size())
      throw new SWRLAPIException("invalid (0-offset) argument #" + argumentNumber);
  }
}
