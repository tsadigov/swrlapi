package org.swrlapi;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.test.SWRLAPIIntegrationTestBase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class SWRLParserTestCase extends SWRLAPIIntegrationTestBase
{
  @Before
  public void setUp() throws OWLOntologyCreationException
  {
    createOWLOntology();
  }

  @Test
  public void TestClassAtomInConsequentWithShortNamedIndividual() throws SWRLParseException
  {
    declareOWLClass("Male");
    declareOWLNamedIndividual("p1");

    SWRLAPIRule rule = createSWRLRule("r1", "-> Male(p1)");

    assertEquals(rule.getBodyAtoms().size(), 0);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test
  public void TestClassAtomInConsequentWithPrefixedNamedIndividual() throws SWRLParseException
  {
    declareOWLClass("Male");
    declareOWLNamedIndividual("p1");

    SWRLAPIRule rule = createSWRLRule("r1", "-> Male(:p1)");

    assertEquals(rule.getBodyAtoms().size(), 0);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test
  public void TestClassAtomInAntecedentWithVariable() throws SWRLParseException
  {
    declareOWLClass("Male");

    SWRLAPIRule rule = createSWRLRule("r1", "Male(?m) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test
  public void TestClassAtomInAntecedentWithPrefixedVariable() throws SWRLParseException
  {
    declareOWLClass("Male");

    SWRLAPIRule rule = createSWRLRule("r1", "Male(?m) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  @Test
  public void TestClassAtomInAntecedentWithName() throws SWRLParseException
  {
    declareOWLClass("Male");
    declareOWLNamedIndividual("p1");

    SWRLAPIRule rule = createSWRLRule("r1", "Male(p1) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
  }

  // TODO owl:Thing should be accessible in rules
  // @Test
  // public void TestOWLThingClass() throws SWRLParseException
  // {
  // SWRLAPIRule rule = createSWRLRule("r1", "owl:Thing(?x) -> ");
  //
  // assertEquals(rule.getBodyAtoms().size(), 1);
  // assertEquals(rule.getHeadAtoms().size(), 0);
  // assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLClassAtom.class));
  // }

  @Test
  public void TestStringLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasName");

    SWRLAPIRule rule = createSWRLRule("r1", "hasName(?p, \"Fred\") ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test
  public void TestRawBooleanTrueLowerCaseLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("isFrench");

    SWRLAPIRule rule = createSWRLRule("r1", "isFrench(?f, true) ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test
  public void TestRawBooleanFalseLowerCaseLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("isFrench");

    SWRLAPIRule rule = createSWRLRule("r1", "isFrench(?f, false) ->");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test
  public void TestRawBooleanTrueUpperCaseLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("isFrench");

    createSWRLRule("r1", "isFrench(?f, True) ->");
  }

  @Test
  public void TestRawBooleanFalseUpperCaseLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("isFrench");

    createSWRLRule("r1", "isFrench(?f, False) ->");
  }

  @Test
  public void TestBooleanTrueQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("isFrench");

    createSWRLRule("r1", "isFrench(?f, \"true\"^^xsd:boolean) ->");
  }

  @Test
  public void TestByteLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:byte) ->");
  }

  @Test
  public void TestNegativeByteLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("yearsToBirth");

    createSWRLRule("r1", "yearsToBirth(?p, \"-34\"^^xsd:byte) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidByteLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:byte) ->");
  }

  @Test
  public void TestShortLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:short) ->");
  }

  @Test
  public void TestNegativeShortLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:short) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidShortLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:short) ->");
  }

  @Test
  public void TestRawIntLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, 34) ->");
  }

  @Test
  public void TestRawNegativeIntLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, -34) ->");
  }

  @Test
  public void TestIntQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:int) ->");
  }

  @Test
  public void TestNegativeIntQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:int) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidIntLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:int) ->");
  }

  @Test
  public void TestLongLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"34\"^^xsd:long) ->");
  }

  @Test
  public void TestNegativeLongLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"-34\"^^xsd:long) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidLongLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"b34\"^^xsd:long) ->");
  }

  @Test
  public void TestFloatRawLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, 34.5) ->");
  }

  @Test
  public void TestNegativeFloatRawLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, -34.5) ->");
  }

  @Test
  public void TestFloatQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^xsd:float) ->");
  }

  @Test
  public void TestNegativeFloatQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"-34.0\"^^xsd:float) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidFloatLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"x34.0\"^^xsd:float) ->");
  }

  @Test
  public void TestDoubleQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"34.0\"^^xsd:double) ->");
  }

  @Test
  public void TestNegativeDoubleQualifiedLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"-34.0\"^^xsd:double) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidDoubleLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHeight");

    createSWRLRule("r1", "hasHeight(?p, \"x34.0\"^^xsd:double) ->");
  }

  @Test
  public void TestURILiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHomePage");

    createSWRLRule("r1", "hasHomePage(?p, \"http://stanford.edu/~fred\"^^xsd:anyURI) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidURILiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasHomePage");

    createSWRLRule("r1", "hasHomePage(?p, \":stanford.edu/~fred\"^^xsd:anyURI) ->");
  }

  @Test
  public void TestDateLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasDOB");

    createSWRLRule("r1", "hasDOB(?p, \"1999-11-22\"^^xsd:date) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidDateLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasDOB");

    createSWRLRule("r1", "hasDOB(?p, \"x199-11-22\"^^xsd:date) ->");
  }

  @Test
  public void TestTimeLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasTOB");

    createSWRLRule("r1", "hasTOB(?p, \"10:10:10.23\"^^xsd:time) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidTimeLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasTOB");

    createSWRLRule("r1", "hasTOB(?p, \"10:0:10.23\"^^xsd:time) ->");
  }

  @Test
  public void TestDateTimeLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasTOB");

    createSWRLRule("r1", "hasTOB(?p, \"1999-11-22T10:10:10.23\"^^xsd:dateTime) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidDateTimeLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasTOB");

    createSWRLRule("r1", "hasTOB(?p, \"x1999-11-22T10:10:10.23\"^^xsd:dateTime) ->");
  }

  @Test
  public void TestDurationLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"P43Y\"^^xsd:duration) ->");
  }

  @Test(expected = SWRLParseException.class)
  public void TestInvalidDurationLiteral() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");

    createSWRLRule("r1", "hasAge(?p, \"43Y\"^^xsd:duration) ->");
  }

  @Test
  public void TestAddAndEqualsTemporalBuiltIns() throws SWRLParseException
  {
    createSWRLRule("r1", "temporal:add(?x, \"1999-11-01T10:00:00\"^^xsd:dateTime, 4, \"Years\") ^ "
        + "temporal:equals(?x, \"2003-11-01T10:00:00.0\"^^xsd:dateTime) " + "-> sqwrl:select(\"Yes!\")");
  }

  @Test
  public void TestObjectPropertyInAntecedentWithVariables() throws SWRLParseException
  {
    declareOWLObjectProperty("hasUncle");

    SWRLAPIRule rule = createSWRLRule("r1", "hasUncle(?p, ?u) -> ");

    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLObjectPropertyAtom.class));
  }

  @Test
  public void TestObjectPropertyInAntecedentWithNamedIndividuals() throws SWRLParseException
  {
    declareOWLObjectProperty("hasUncle");
    declareOWLNamedIndividual("p1");
    declareOWLNamedIndividual("p2");

    SWRLAPIRule rule = createSWRLRule("r1", "hasUncle(p1, p2) -> ");
    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 0);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLObjectPropertyAtom.class));
  }

  @Test
  public void TestObjectPropertyInConsequentWithNamedIndividals() throws SWRLParseException
  {
    declareOWLObjectProperty("hasUncle");
    declareOWLNamedIndividual("p1");
    declareOWLNamedIndividual("p2");

    createSWRLRule("r1", "-> hasUncle(p1, p2)");
  }

  @Test
  public void TestDataPropertyInAntecedent() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");
    declareOWLNamedIndividual("p3");

    createSWRLRule("r1", "hasAge(p3, ?a) -> ");
  }

  @Test
  public void TestDataPropertyInConsequent() throws SWRLParseException
  {
    declareOWLDataProperty("hasAge");
    declareOWLNamedIndividual("p4");

    createSWRLRule("r1", " -> hasAge(p4, 34)");
  }

  @Test
  public void TestClassAtomInAntecedentWithNamedIndividual() throws SWRLParseException
  {
    declareOWLClass("Male");
    declareOWLNamedIndividual("m1");

    createSWRLRule("r1", "Male(m1) ->");
  }

  @Test
  public void TestClassAtomInConsequent() throws SWRLParseException
  {
    declareOWLClass("Male");
    declareOWLNamedIndividual("m1");

    createSWRLRule("r1", "-> Male(m1)");
  }

  @Test
  public void TestBuiltInWithLiteralsAndVariables() throws SWRLParseException
  {
    declareOWLNamedIndividual("p13");
    declareOWLDataProperty("hasLastAccessTime");

    SWRLAPIRule rule = createSWRLRule("r1",
        "swrlb:addDayTimeDurationToDateTime(?dt, \"1999-01-01T12:12:12\", \"P1Y\") -> hasLastAccessTime(p13, ?dt)");

    assertEquals(rule.getBodyAtoms().size(), 1);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLBuiltInAtom.class));
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLDataPropertyAtom.class));
  }

  @Test
  public void TestSameAsInConsequentWithNamedIndividualAndVariable() throws SWRLParseException
  {
    declareOWLClass("Person");
    declareOWLDataProperty("hasID");
    declareOWLNamedIndividual("s12");

    SWRLAPIRule rule = createSWRLRule("r1", "Person(?i2) ^ hasID(?i2, \"s13ID\") -> sameAs(s12, ?i2)");

    assertEquals(rule.getBodyAtoms().size(), 2);
    assertEquals(rule.getHeadAtoms().size(), 1);
    assertThat(rule.getBodyAtoms().get(0), instanceOf(SWRLClassAtom.class));
    assertThat(rule.getBodyAtoms().get(1), instanceOf(SWRLDataPropertyAtom.class));
    assertThat(rule.getHeadAtoms().get(0), instanceOf(SWRLSameIndividualAtom.class));
  }

  @Test
  public void TestSameAsInAntecedentWithVariables() throws SWRLParseException
  {
    createSWRLRule("r1", "sameAs(?i1, ?i2) ->");
  }

  @Test
  public void TestSameAsInAntecedentWithNamedIndividual() throws SWRLParseException
  {
    declareOWLNamedIndividual("i1");
    declareOWLNamedIndividual("i2");

    createSWRLRule("r1", "sameAs(i1, i2) ->");
  }

  @Test
  public void TestSameAsInConsequentWithNamedIndividual() throws SWRLParseException
  {
    declareOWLNamedIndividual("i1");
    declareOWLNamedIndividual("i2");

    createSWRLRule("r1", "-> sameAs(i1, i2)");
  }

  @Test
  public void TestDifferentFromInAntecedentWithVariables() throws SWRLParseException
  {
    createSWRLRule("r1", "differentFrom(?i1, ?i2) ->");
  }

  @Test
  public void TestDifferentFromInAntecedentWithNamedIndividual() throws SWRLParseException
  {
    declareOWLNamedIndividual("i1");
    declareOWLNamedIndividual("i2");

    createSWRLRule("r1", "differentFrom(i1, i2) ->");
  }

  @Test
  public void TestDifferentFromInConsequentWithNamedIndividual() throws SWRLParseException
  {
    declareOWLNamedIndividual("i1");
    declareOWLNamedIndividual("i2");

    createSWRLRule("r1", "-> differentFrom(i1, i2)");
  }

  @Test
  public void TestClassAndDataPropertyAtom() throws SWRLParseException
  {
    declareOWLClass("Person");
    declareOWLDataProperty("hasID");
    declareOWLDataProperty("hasFirstName");
    declareOWLNamedIndividual("s12");

    createSWRLRule("r1", "Person(?p) ^ hasID(?p, \"p7ID\") -> hasFirstName(?p, \"Angela\")");
  }
}
