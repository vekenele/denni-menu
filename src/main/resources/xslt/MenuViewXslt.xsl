<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/dailymenu">
        <xsl:apply-templates select="monday|tuesday|wednesday|thursday|friday"/>

        <p class="text-center"><strong>Valid from </strong>
            <xsl:call-template name="formatDate">
                <xsl:with-param name="date" select="validFrom" />
            </xsl:call-template>
            <strong> to </strong>
            <xsl:call-template name="formatDate">
                <xsl:with-param name="date" select="validTo" />
            </xsl:call-template>
        </p>
    </xsl:template>

    <xsl:template match="monday|tuesday|wednesday|thursday|friday">
        <h3 class="firstUppercase"><xsl:value-of select ="local-name()"/></h3>
        <xsl:apply-templates select="*"/>
    </xsl:template>

    <xsl:template match="appetizer|soup|mainDish|dessert">
        <p>some food type</p>
    </xsl:template>

    <xsl:template name="formatDate">
        <xsl:param name="date" />
        <xsl:variable name="year" select="substring-before($date, '-')" />
        <xsl:variable name="month" select="substring-before(substring-after($date, '-'), '-')" />
        <xsl:variable name="day" select="substring-after(substring-after($date, '-'), '-')" />
        <xsl:value-of select="concat($day, '/', $month, '/', $year)" />
    </xsl:template>

</xsl:stylesheet>