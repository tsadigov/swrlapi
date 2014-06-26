package org.swrlapi.core;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;

/**
 * Wraps the OWLAPI's {@link OWLOntology} class with additional methods used by the SWRLAPI. Primarily the
 * {@link #getSWRLAPIRules()} method extracts {@link SWRLAPIRule} objects from an OWL ontology. This class, which
 * extends the standard OWLAPI {@link SWRLRule} class, provide the richer representation of a SWRL rule required by the
 * SWRLAPI. In particular, the SWRLAPI has a range of types extending the OWLAPI's {@link SWRLDArgument} interface to
 * define arguments to built-in atoms.
 * <p>
 * This extension point is defined by the {@link SWRLBuiltInArgument} interface. A {@link SWRLAPIOWLOntology} will
 * construct SWRLAPI rules from the SWRL rules in an OWLAPI-based ontology to contain these additional types. A
 * {@link SWRLAPIOWLDataFactory} can be used to create {@link SWRLAPIRule} objects from a text-based representation of a
 * SWRL rule or SQWRL query.
 * <p>
 * The {@link startBulkConversion}, {@link completeBulkConversion}, {@link hasOntologyChanged}, and
 * {@link resetOntologyChanged} methods can be used for optimization purposed. For example, in the Protege-OWL API the
 * {@link startBulkConversion} method turns off listener notification so that bulk transfer of OWL axioms can be
 * performed more efficiently. The {@link hasOntologyChanged} method can be used by rule engines to avoid unnecessary
 * regeneration of knowledge.
 * <p>
 * A SWRLAPI ontology does not directly deal with SQWRL queries. Instead, a {@link SWRLAPIOntologyProcessor} is used to
 * extract SQWRL queries - which are stored as SWRL rules - from a {@link SWRLAPIOWLOntology}.
 * 
 * @see SWRLAPIRule, SWRLBuiltInArgument, SWRLAPIOntologyProcessor, SWRLAPIOWLDataFactory
 */
public interface SWRLAPIOWLOntology
{
	OWLOntologyManager getOWLOntologyManager();

	OWLOntology getOWLOntology();

	OWLDataFactory getOWLDataFactory();

	Set<SWRLAPIRule> getSWRLAPIRules();

	SWRLAPIOWLDataFactory getSWRLAPIOWLDataFactory();

	SWRLAPIOntologyProcessor getSWRLAPIOntologyProcessor();

	DefaultPrefixManager getPrefixManager();

	OWLIRIResolver getOWLIRIResolver();

	void startBulkConversion(); // Can be used, for example, to switch off notification during bulk conversion.

	void completeBulkConversion();

	boolean hasOntologyChanged();

	void resetOntologyChanged();

	boolean isSWRLBuiltIn(IRI iri);

	OWLClass getInjectedOWLClass(); // Auto-generate an OWL class with a unique IRI

	OWLNamedIndividual getInjectedOWLNamedIndividual(); // Auto-generate an OWL individual with a unique IRI

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	boolean isOWLIndividualOfType(IRI individualIRI, IRI classIRI);

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	Set<OWLObjectPropertyAssertionAxiom> getOWLObjectPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);

	// TODO We don't want this method here. It is a convenience method and used only by the temporal built-in library.
	Set<OWLDataPropertyAssertionAxiom> getOWLDataPropertyAssertionAxioms(IRI individualIRI, IRI propertyIRI);
}