/* Copyright (c) 2012-2013, University of Edinburgh.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of the University of Edinburgh nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * This software is derived from (and contains code from) QTITools and MathAssessEngine.
 * QTITools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package uk.ac.ed.ph.jqtiplus.reading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import uk.ac.ed.ph.jqtiplus.node.content.xhtml.list.Dir;
import uk.ac.ed.ph.jqtiplus.node.content.xhtml.presentation.Center;
import uk.ac.ed.ph.jqtiplus.node.content.xhtml.text.Font;
import uk.ac.ed.ph.jqtiplus.node.item.AssessmentItem;
import uk.ac.ed.ph.jqtiplus.utils.QueryUtils;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlParseResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceNotFoundException;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;

import java.net.URI;
import java.util.List;

import org.junit.Test;

/**
 * FIXME: Document this type
 *
 * @author Jan Willem Janssen
 */
public class QtiObjectReaderTest {

    @Test
    public void testLoadUnknownTagOk() throws Exception {
        final String fileName = "unknowntag.xml";
        final QtiObjectReader reader = createObjectReader(false);

        final QtiObjectReadResult<AssessmentItem> rootNode = reader.lookupRootNode(makeSystemId(fileName), AssessmentItem.class);

        final XmlParseResult parseResult = rootNode.getXmlParseResult();
        assertEquals(makeSystemId(fileName), parseResult.getSystemId());
        assertTrue(parseResult.isParsed());
        assertFalse(parseResult.isValidated());
        assertFalse(parseResult.isSchemaValid());

        final AssessmentItem assessmentItem = rootNode.getRootNode();
        assertNotNull(assessmentItem);

        // There are two font tags...
        List<?> searchResult = QueryUtils.search(Font.class, assessmentItem);
        assertNotNull(searchResult);
        assertEquals(2, searchResult.size());

        // There is one center tag...
        searchResult = QueryUtils.search(Center.class, assessmentItem);
        assertNotNull(searchResult);
        assertEquals(1, searchResult.size());

        // There is one dir tag...
        searchResult = QueryUtils.search(Dir.class, assessmentItem);
        assertNotNull(searchResult);
        assertEquals(1, searchResult.size());
    }

    private QtiObjectReader createObjectReader(final boolean schemaValiadating) throws XmlResourceNotFoundException {
        final QtiXmlReader reader = new QtiXmlReader();
        final ResourceLocator inputResourceLocator = new ClassPathResourceLocator();
        return reader.createQtiObjectReader(inputResourceLocator, schemaValiadating);
    }

    private URI makeSystemId(final String testFileName) {
        return URI.create("classpath:/uk/ac/ed/ph/jqtiplus/reading/" + testFileName);
    }

}
