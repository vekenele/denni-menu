<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/dailymenu">
        <xsl:apply-templates select="monday|tuesday|wednesday|thursday|friday"/>

        <div class="restaurantInfo">
            <p class="address">Restaurace VEKENELE<br/>
            Galerie Vaňkovka<br/>
            Masarykova 18/2205<br/>
            Brno - Střed</p>

            <p>Phone: +420 605 332 445, Email: info [@] vekenele.com</p>

            <p>Monday - Friday: 10 AM - 23 PM, Saturday - Sunday: 17 PM - 1 AM</p>
        </div>
    </xsl:template>

    <xsl:template match="monday|tuesday|wednesday|thursday|friday">
        <xsl:variable name="i" select="position()" />
        <h2 class="firstUppercase"><xsl:value-of select ="local-name()"/> (<xsl:call-template name="formatDate"><xsl:with-param name="date" select="@date" /></xsl:call-template>)</h2>
        <div class="day">
            <xsl:apply-templates select="*"/>
        </div>
        <xsl:if test="$i=3"><div class="page-break"></div></xsl:if>
    </xsl:template>

    <xsl:template match="appetizer|soup|mainDish|dessert">
        
        <h3 class="firstUppercase"><xsl:value-of select="local-name()"/></h3>
        <ul class="foodVariants">
            <xsl:choose>
                <xsl:when test="variant">
                    <xsl:apply-templates select="variant"/>
                </xsl:when>
                <xsl:otherwise>
                    <li>
                        <strong><xsl:value-of select="name"/></strong> (allergens: <xsl:value-of select="allergens"/>) <span><span class="priceValue"><xsl:value-of select="sellPrice"/></span> Kč</span>
                    </li>
                </xsl:otherwise>
            </xsl:choose>
        </ul>
    </xsl:template>

    <xsl:template match="variant">
        <li>
            <strong><xsl:value-of select="name"/></strong> (allergens: <xsl:value-of select="allergens"/>) <span><span class="priceValue"><xsl:value-of select="sellPrice"/></span> Kč</span>
        </li>
    </xsl:template>

    <xsl:template name="formatDate">
        <xsl:param name="date" />
        <xsl:variable name="year" select="substring-before($date, '-')" />
        <xsl:variable name="month" select="substring-before(substring-after($date, '-'), '-')" />
        <xsl:variable name="day" select="substring-after(substring-after($date, '-'), '-')" />
        <xsl:value-of select="concat($day, '/', $month, '/', $year)" />
    </xsl:template>

</xsl:stylesheet>