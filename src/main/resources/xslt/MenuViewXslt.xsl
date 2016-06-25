<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/dailymenu">
        <xsl:apply-templates select="monday|tuesday|wednesday|thursday|friday"/>

        <p class="text-center validity"><strong>Valid from </strong>
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
        <xsl:variable name="j" select="position() - 1"/>
        <h3 class="firstUppercase"><xsl:value-of select ="local-name()"/></h3>
        <form name="{@date}" id="{local-name()}" onsubmit="openModal('{@date}'); return false;">
            <xsl:apply-templates select="*"/>
            <div class="preorder-btn-wrapper">
                <span id="finalPrice{$j}"></span>
                <button type="button" class="btn btn-default clear-btn" onclick="clearForm('{@date}');">Clear</button>
                <input type="submit" value="Pre-order" class="btn preorder-btn"/>
            </div>
        </form>
    </xsl:template>

    <xsl:template match="appetizer|soup|mainDish|dessert">
        <h4 class="firstUppercase"><xsl:value-of select="local-name()"/></h4>
        <fieldset>
            <ul class="foodVariants">
                <xsl:choose>
                    <xsl:when test="variant">
                        <xsl:apply-templates select="variant"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <li>
                            <label>
                                <input type="radio" name="{local-name()}" value="1"/>
                                <strong><xsl:value-of select="name"/></strong> (allergens: <xsl:value-of select="allergens"/>) <span><span class="priceValue"><xsl:value-of select="sellPrice"/></span> Kč</span>
                            </label>
                        </li>
                    </xsl:otherwise>
                </xsl:choose>
            </ul>
        </fieldset>
    </xsl:template>

    <xsl:template match="variant">
        <xsl:variable name="i" select="position()" />
        <li>
            <label>
                <input type="radio" name="{local-name(./parent::*)}" value="{$i}"/>
                <strong><xsl:value-of select="name"/></strong> (allergens: <xsl:value-of select="allergens"/>) <span><span class="priceValue"><xsl:value-of select="sellPrice"/></span> Kč</span>
            </label>
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