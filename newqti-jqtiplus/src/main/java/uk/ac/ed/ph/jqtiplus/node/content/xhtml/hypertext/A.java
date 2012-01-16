/* Copyright (c) 2012, University of Edinburgh.
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
 * This software is derived from (and contains code from) QTItools and MathAssessEngine.
 * QTItools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package uk.ac.ed.ph.jqtiplus.node.content.xhtml.hypertext;

import uk.ac.ed.ph.jqtiplus.attribute.value.StringAttribute;
import uk.ac.ed.ph.jqtiplus.attribute.value.UriAttribute;
import uk.ac.ed.ph.jqtiplus.node.XmlNode;
import uk.ac.ed.ph.jqtiplus.node.content.basic.AbstractSimpleInline;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.validation.ValidationError;

import java.net.URI;

/**
 * Although a inherits from simpleInline it must not contain, either directly or indirectly, another a.
 * Attribute : href [1]: uri
 * Attribute : type [0..1]: mimeType
 * 
 * @author Jonathon Hare
 */
public class A extends AbstractSimpleInline {

    private static final long serialVersionUID = -838798085866010380L;

    /** Name of this class in xml schema. */
    public static String CLASS_TAG = "a";

    /** Name of href attribute in xml schema. */
    public static final String ATTR_HREF_NAME = "href";

    /** Name of type attribute in xml schema. */
    public static final String ATTR_TYPE_NAME = "type";

    /**
     * Constructs object.
     * 
     * @param parent parent of constructed object
     */
    public A(XmlNode parent) {
        super(parent);

        getAttributes().add(new UriAttribute(this, ATTR_HREF_NAME));
        getAttributes().add(new StringAttribute(this, ATTR_TYPE_NAME, null, null, false));
    }

    @Override
    public String getClassTag() {
        return CLASS_TAG;
    }

    @Override
    public void validate(ValidationContext context) {
        super.validate(context);

        //Although a inherits from simpleInline it must not contain, either directly or indirectly, another a. 
        if (search(A.class).size() > 0) {
            context.getValidationResult().add(new ValidationError(this, "The " + CLASS_TAG + " class cannot contain " + CLASS_TAG + " children"));
        }
    }

    /**
     * Gets value of href attribute.
     * 
     * @return value of href attribute
     * @see #setHref
     */
    public URI getHref() {
        return getAttributes().getUriAttribute(ATTR_HREF_NAME).getValue();
    }

    /**
     * Sets new value of href attribute.
     * 
     * @param href new value of href attribute
     * @see #getHref
     */
    public void setHref(URI href) {
        getAttributes().getUriAttribute(ATTR_HREF_NAME).setValue(href);
    }

    /**
     * Gets value of type attribute.
     * 
     * @return value of type attribute
     * @see #setType
     */
    public String getType() {
        return getAttributes().getStringAttribute(ATTR_TYPE_NAME).getValue();
    }

    /**
     * Sets new value of type attribute.
     * 
     * @param type new value of type attribute
     * @see #getType
     */
    public void setType(String type) {
        getAttributes().getStringAttribute(ATTR_TYPE_NAME).setValue(type);
    }
}
