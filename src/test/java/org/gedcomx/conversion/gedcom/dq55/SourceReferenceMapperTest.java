/**
 * Copyright 2012 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gedcomx.conversion.gedcom.dq55;

import java.io.File;
import java.net.URL;

import org.folg.gedcom.model.Family;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.parser.ModelParser;
import org.gedcomx.conclusion.Fact;
import org.gedcomx.conclusion.Person;
import org.gedcomx.conclusion.Relationship;
import org.gedcomx.source.SourceDescription;
import org.gedcomx.source.SourceReference;
import org.gedcomx.types.RelationshipType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;


public class SourceReferenceMapperTest {
  private MappingConfig mappingConfig = new MappingConfig("intputFile.ged", true);
  private Gedcom gedcom;

  @BeforeClass
  public void setUp() throws Exception {
    SequentialIdentifierGenerator.reset();
    URL gedcomUrl = this.getClass().getClassLoader().getResource("Case007-SourceCitations.ged");
    File gedcomFile = new File(gedcomUrl.toURI());
    ModelParser modelParser = new ModelParser();

    gedcom = modelParser.parseGedcom(gedcomFile);
    assertNotNull(gedcom);
    assertNotNull(gedcom.getPeople());
    assertEquals(gedcom.getPeople().size(), 5);
    assertNotNull(gedcom.getFamilies());
    assertEquals(gedcom.getFamilies().size(), 2);
    assertNotNull(gedcom.getSources());
    assertEquals(gedcom.getSources().size(), 1);
    assertNotNull(gedcom.getRepositories());
    assertEquals(gedcom.getRepositories().size(), 1);
  }

  @Test
  public void testToSourcesAndSourceReferences1() throws Exception {
    Family dqFamily = gedcom.getFamilies().get(0);
    TestConversionResult result = new TestConversionResult();

    FamilyMapper mapper = new FamilyMapper(mappingConfig);

    String generatedId = null;

    mapper.toRelationship(dqFamily, null, result);
    assertNotNull(result.getRelationships());
    assertEquals(result.getRelationships().size(), 3);
    for (Relationship gedxRelationship : result.getRelationships()) {
      if (gedxRelationship.getKnownType() == RelationshipType.Couple) {
        assertNotNull(gedxRelationship.getFacts());
        assertEquals(gedxRelationship.getFacts().size(), 1);
        Fact gedxFact = gedxRelationship.getFacts().get(0);
        assertNotNull(gedxFact);
        assertNotNull(gedxFact.getSources());
        assertEquals(gedxFact.getSources().size(), 1);
        SourceReference gedxSourceReference = gedxFact.getSources().get(0);
        assertNotNull(gedxSourceReference);
        assertNotNull(gedxSourceReference.getDescriptionRef());
        assertTrue(gedxSourceReference.getDescriptionRef().toString().startsWith("#"));
        generatedId = gedxSourceReference.getDescriptionRef().toString().substring(1);
        assertNotNull(generatedId, "1");
        assertNull(gedxSourceReference.getAttribution());
        assertNull(gedxSourceReference.getExtensionElements());
      }
    }
    assertNotNull(result.getSourceDescriptions());
    assertEquals(result.getSourceDescriptions().size(), 1);
    SourceDescription gedxSourceDescription = result.getSourceDescriptions().get(0);
    assertNotNull(gedxSourceDescription);
    assertEquals(gedxSourceDescription.getId(), generatedId);
    assertNotNull(gedxSourceDescription.getCitation());
    assertEquals(gedxSourceDescription.getCitation().getValue(), "31 Jan 1820, FHL INTL Film 1226427, Geboorten, 1820, No. 143");
    assertEquals(gedxSourceDescription.getCitation().getCitationTemplate().getResource().toURI().toString(), "gedcom5:citation-template");
    assertNotNull(gedxSourceDescription.getCitation().getFields());
    assertEquals(gedxSourceDescription.getCitation().getFields().size(), 2);
    assertEquals(gedxSourceDescription.getCitation().getFields().get(0).getName().toURI().toString(), "gedcom5:citation-template/date");
    assertEquals(gedxSourceDescription.getCitation().getFields().get(0).getValue(), "31 Jan 1820");
    assertEquals(gedxSourceDescription.getCitation().getFields().get(1).getName().toURI().toString(), "gedcom5:citation-template/page");
    assertEquals(gedxSourceDescription.getCitation().getFields().get(1).getValue(), "FHL INTL Film 1226427, Geboorten, 1820, No. 143");
    assertNull(gedxSourceDescription.getAbout());
    assertNull(gedxSourceDescription.getMediator());
    assertNull(gedxSourceDescription.getSources());
    assertEquals(gedxSourceDescription.getComponentOf().getDescriptionRef().toURI().toString(), "#SOUR1");
    assertNull(gedxSourceDescription.getTitle());
    assertNull(gedxSourceDescription.getAttribution());
    assertNull(gedxSourceDescription.getNotes());
    assertNull(gedxSourceDescription.getExtensionElements());
  }

  @Test
  public void testToSourcesAndSourceReferences2() throws Exception {
    Family dqFamily = gedcom.getFamilies().get(1);
    TestConversionResult result = new TestConversionResult();

    FamilyMapper mapper = new FamilyMapper(mappingConfig);

    String generatedId = null;

    mapper.toRelationship(dqFamily, null, result);
    assertNotNull(result.getRelationships());
    assertEquals(result.getRelationships().size(), 3);
    for (Relationship gedxRelationship : result.getRelationships()) {
      if (gedxRelationship.getKnownType() == RelationshipType.Couple) {
        assertNotNull(gedxRelationship.getFacts());
        assertEquals(gedxRelationship.getFacts().size(), 1);
        Fact gedxFact = gedxRelationship.getFacts().get(0);
        assertNotNull(gedxFact);
        assertNotNull(gedxFact.getSources());
        assertEquals(gedxFact.getSources().size(), 1);
        SourceReference gedxSourceReference = gedxFact.getSources().get(0);
        assertNotNull(gedxSourceReference);
        assertNotNull(gedxSourceReference.getDescriptionRef());
        assertTrue(gedxSourceReference.getDescriptionRef().toString().startsWith("#"));
        generatedId = gedxSourceReference.getDescriptionRef().toString().substring(1);
        assertEquals(generatedId, "SOUR-2");
        assertNull(gedxSourceReference.getAttribution());
      }
    }
    assertNotNull(result.getSourceDescriptions());
    assertEquals(result.getSourceDescriptions().size(), 1);
    SourceDescription gedxSourceDescription = result.getSourceDescriptions().get(0);
    assertNotNull(gedxSourceDescription);
    assertEquals(gedxSourceDescription.getId(), generatedId);
    assertNotNull(gedxSourceDescription.getCitation());
    assertEquals(gedxSourceDescription.getCitation().getValue(), "\"Germany, Births and Baptisms, 1558-1898,\" index, FamilySearch (https://familysearch.org/pal:/MM9.1.1/VHQB-CHW :accessed 22 May 2012), Joannes Baptista Louwart.");
    assertNull(gedxSourceDescription.getCitation().getCitationTemplate());
    assertNotNull(gedxSourceDescription.getCitation().getFields());
    assertEquals(gedxSourceDescription.getCitation().getFields().size(), 0);
    assertNull(gedxSourceDescription.getAbout());
    assertNull(gedxSourceDescription.getMediator());
    assertNull(gedxSourceDescription.getSources());
    assertNull(gedxSourceDescription.getComponentOf());
    assertNull(gedxSourceDescription.getTitles());
    assertNull(gedxSourceDescription.getAttribution());
    assertNull(gedxSourceDescription.getNotes());
    assertNull(gedxSourceDescription.getExtensionElements());
  }

  @Test
  public void testToSourcesAndSourceReferences3() throws Exception {
    org.folg.gedcom.model.Person dqPerson = gedcom.getPeople().get(0);
    TestConversionResult result = new TestConversionResult();

    PersonMapper mapper = new PersonMapper(mappingConfig);

    String generatedId;

    mapper.toPerson(dqPerson, result);
    assertNotNull(result.getPersons());
    assertEquals(result.getPersons().size(), 1);

    Person gedxPerson = result.getPersons().get(0);
    assertNotNull(gedxPerson.getFacts());
    assertEquals(gedxPerson.getFacts().size(), 1);
    Fact gedxFact = gedxPerson.getFacts().get(0);
    assertNotNull(gedxFact);
    assertNotNull(gedxFact.getSources());
    assertEquals(gedxFact.getSources().size(), 1);
    SourceReference gedxSourceReference = gedxFact.getSources().get(0);
    assertNotNull(gedxSourceReference);
    assertNotNull(gedxSourceReference.getDescriptionRef());
    assertTrue(gedxSourceReference.getDescriptionRef().toString().startsWith("#"));
    generatedId = gedxSourceReference.getDescriptionRef().toString().substring(1);
    assertNotNull(generatedId, "3");
    assertNull(gedxSourceReference.getAttribution());
    assertNull(gedxSourceReference.getExtensionElements());

    assertNotNull(result.getSourceDescriptions());
    assertEquals(result.getSourceDescriptions().size(), 1);
    SourceDescription gedxSourceDescription = result.getSourceDescriptions().get(0);
    assertNotNull(gedxSourceDescription);
    assertEquals(gedxSourceDescription.getId(), generatedId);
    assertNotNull(gedxSourceDescription.getCitation());
    assertEquals(gedxSourceDescription.getCitation().getValue(), "__SOUR_JoannesBaptistaLouwaert__");
    assertNull(gedxSourceDescription.getCitation().getCitationTemplate());
    assertNotNull(gedxSourceDescription.getCitation().getFields());
    assertEquals(gedxSourceDescription.getCitation().getFields().size(), 0);
    assertNull(gedxSourceDescription.getAbout());
    assertNull(gedxSourceDescription.getMediator());
    assertNull(gedxSourceDescription.getSources());
    assertNull(gedxSourceDescription.getComponentOf());
    assertNull(gedxSourceDescription.getTitles());
    assertNull(gedxSourceDescription.getAttribution());
    assertNull(gedxSourceDescription.getNotes());
    assertNull(gedxSourceDescription.getExtensionElements());
  }

  @Test
  public void testToSourcesAndSourceReferences4() throws Exception {
    org.folg.gedcom.model.Person dqPerson = gedcom.getPeople().get(1);
    TestConversionResult result = new TestConversionResult();

    PersonMapper mapper = new PersonMapper(mappingConfig);

    String generatedId;

    mapper.toPerson(dqPerson, result);
    assertNotNull(result.getPersons());
    assertEquals(result.getPersons().size(), 1);

    Person gedxPerson = result.getPersons().get(0);
    assertNotNull(gedxPerson.getFacts());
    assertEquals(gedxPerson.getFacts().size(), 1);
    Fact gedxFact = gedxPerson.getFacts().get(0);
    assertNotNull(gedxFact);
    assertNotNull(gedxFact.getSources());
    assertEquals(gedxFact.getSources().size(), 1);
    SourceReference gedxSourceReference = gedxFact.getSources().get(0);
    assertNotNull(gedxSourceReference);
    assertNotNull(gedxSourceReference.getDescriptionRef());
    assertTrue(gedxSourceReference.getDescriptionRef().toString().startsWith("#"));
    generatedId = gedxSourceReference.getDescriptionRef().toString().substring(1);
    assertNotNull(generatedId, "4");
    assertNull(gedxSourceReference.getAttribution());
    assertNull(gedxSourceReference.getExtensionElements());

    assertNotNull(result.getSourceDescriptions());
    assertEquals(result.getSourceDescriptions().size(), 0);
  }

  @Test
  public void testToSourcesAndSourceReferences5() throws Exception {
    org.folg.gedcom.model.Person dqPerson = gedcom.getPeople().get(2);
    TestConversionResult result = new TestConversionResult();

    PersonMapper mapper = new PersonMapper(mappingConfig);

    mapper.toPerson(dqPerson, result);
    assertNotNull(result.getPersons());
    assertEquals(result.getPersons().size(), 1);

    Person gedxPerson = result.getPersons().get(0);
    assertNotNull(gedxPerson.getFacts());
    assertEquals(gedxPerson.getFacts().size(), 1);
    Fact gedxFact = gedxPerson.getFacts().get(0);
    assertNotNull(gedxFact);
    assertNull(gedxFact.getSources());
// TODO: will probably be needed when the TEXT tag starts being processed
//    assertEquals(gedxFact.getSources().size(), 1);
//    SourceReference gedxSourceReference = gedxFact.getSources().get(0);
//    assertNotNull(gedxSourceReference);
//    assertNotNull(gedxSourceReference.getSourceDescription());
//    assertTrue(gedxSourceReference.getSourceDescription().getResource().toString().startsWith("#"));
//    generatedId = gedxSourceReference.getSourceDescription().getResource().toString().substring(1);
//    assertNotNull(generatedId, "5");
//    assertNull(gedxSourceReference.getAttribution());
//    assertNull(gedxSourceReference.getExtensionElements());

    assertNotNull(result.getSourceDescriptions());
    assertEquals(result.getSourceDescriptions().size(), 0);
  }
}
