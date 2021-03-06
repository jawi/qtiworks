/* $Id:SAXErrorHandler.java 2824 2008-08-01 15:46:17Z davemckain $
 *
 * Copyright (c) 2012-2013, The University of Edinburgh.
 * All Rights Reserved
 */
package uk.ac.ed.ph.qtiworks.examples;

import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import uk.ac.ed.ph.jqtiplus.utils.contentpackaging.QtiContentPackageExtractor;
import uk.ac.ed.ph.jqtiplus.utils.contentpackaging.QtiContentPackageSummary;

import java.io.File;

/**
 * Content Package extraction dev/test
 *
 * @author David McKain
 */
public final class ContentPackageExample {
    
    public static void main(String[] args) throws Exception {
        File packageBaseDirectory = new File("src/main/resources/Aardvark-cannon");
        QtiContentPackageExtractor extractor = new QtiContentPackageExtractor(packageBaseDirectory);
        QtiContentPackageSummary result = extractor.parse();
        System.out.println(ObjectDumper.dumpObject(result, DumpMode.DEEP));
    }
}
