<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <h1>Seznam restaurací</h1>
        <xsl:for-each select="restaurant">
            <h2><xsl:value-of select="name"/></h2>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>