<?xml version="1.0" encoding="UTF-8"?>
<!--

Renders the navigation for the current testPart

-->
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:qti="http://www.imsglobal.org/xsd/imsqti_v2p1"
  xmlns:m="http://www.w3.org/1998/Math/MathML"
  xmlns:qw="http://www.ph.ed.ac.uk/qtiworks"
  xmlns="http://www.w3.org/1999/xhtml"
  xpath-default-namespace="http://www.w3.org/1999/xhtml"
  exclude-result-prefixes="xs qti qw m">

  <!-- ************************************************************ -->

  <xsl:import href="qti-fallback.xsl"/>
  <xsl:import href="test-common.xsl"/>
  <xsl:import href="serialize.xsl"/>
  <xsl:import href="utils.xsl"/>

  <!-- Action permissions -->
  <xsl:param name="endTestPartAllowed" as="xs:boolean" required="yes"/>

  <!-- ************************************************************ -->

  <xsl:template match="/">
    <xsl:variable name="unserialized-output" as="element()">
      <xsl:apply-templates select="qw:to-qti21(/)/*"/>
    </xsl:variable>
    <xsl:apply-templates select="$unserialized-output" mode="serialize"/>
  </xsl:template>

  <!-- ************************************************************ -->

  <xsl:template match="qti:assessmentTest" as="element(html)">
    <html>
      <xsl:if test="@lang">
        <xsl:copy-of select="@lang"/>
        <xsl:attribute name="xml:lang" select="@lang"/>
      </xsl:if>
      <head>
        <title><xsl:value-of select="@title"/></title>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"/>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.1/jquery-ui.min.js"/>
        <script src="{$webappContextPath}/rendering/javascript/QtiWorksRendering.js?{$qtiWorksVersion}"/>
        <xsl:if test="$authorMode">
          <script src="{$webappContextPath}/rendering/javascript/AuthorMode.js?{$qtiWorksVersion}"/>
        </xsl:if>

        <!-- Styling for JQuery -->
        <link rel="stylesheet" type="text/css" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.1/themes/redmond/jquery-ui.css"/>

        <!-- QTIWorks styling -->
        <link rel="stylesheet" href="{$webappContextPath}/rendering/css/assessment.css?{$qtiWorksVersion}" type="text/css" media="screen"/>
      </head>
      <body class="qtiworks assessmentTest testPartNavigation">
        <h2>Test Question Menu</h2>
        <xsl:apply-templates select="$currentTestPartNode" mode="testPart-navigation"/>

        <!-- Test session control -->
        <xsl:call-template name="qw:test-controls"/>
       </body>
    </html>
  </xsl:template>

  <xsl:template name="qw:test-controls">
    <div class="sessionControl">
      <xsl:if test="$authorMode">
        <div class="authorMode">
          The candidate currently has the following "test session control" options. (These
          currently depend on the navigation &amp; submission mode of the test only.)
        </div>
      </xsl:if>
      <ul class="controls test">
        <li>
          <form action="{$webappContextPath}{$endTestPartUrl}" method="post"
            onsubmit="return confirm('Are you sure?')">
            <input type="submit" value="End {$testOrTestPart}">
              <xsl:if test="not($endTestPartAllowed)">
                <xsl:attribute name="disabled" select="'disabled'"/>
              </xsl:if>
            </input>
          </form>
        </li>
      </ul>
    </div>
  </xsl:template>

  <xsl:template match="qw:node" mode="testPart-navigation">
    <ul class="testPartNavigation">
      <xsl:apply-templates mode="testPart-navigation"/>
    </ul>
  </xsl:template>

  <xsl:template match="qw:node[@type='ASSESSMENT_SECTION']" mode="testPart-navigation">
    <li class="assessmentSection">
      <header>
        <!-- Section title -->
        <h2><xsl:value-of select="@sectionPartTitle"/></h2>
        <!-- Handle rubrics -->
        <xsl:variable name="sectionIdentifier" select="qw:extract-identifier(.)" as="xs:string"/>
        <xsl:variable name="assessmentSection" select="$assessmentTest//qti:assessmentSection[@identifier=$sectionIdentifier]" as="element(qti:assessmentSection)*"/>
        <xsl:apply-templates select="$assessmentSection/qti:rubricBlock"/>
      </header>
      <!-- Descend -->
      <ul class="testPartNavigationInner">
        <xsl:apply-templates mode="testPart-navigation"/>
      </ul>
    </li>
  </xsl:template>

  <xsl:template match="qw:node[@type='ASSESSMENT_ITEM_REF']" mode="testPart-navigation">
    <xsl:variable name="itemSessionState" select="$testSessionState/qw:item[@key=current()/@key]/qw:itemSessionState" as="element(qw:itemSessionState)"/>
    <li class="assessmentItem">
      <form action="{$webappContextPath}{$selectTestItemUrl}/{@key}" method="post">
        <button type="submit">
          <span class="questionTitle"><xsl:value-of select="@sectionPartTitle"/></span>
          <xsl:apply-templates select="$itemSessionState" mode="item-status"/>
        </button>
      </form>
    </li>
  </xsl:template>

</xsl:stylesheet>

