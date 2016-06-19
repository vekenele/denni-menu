<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <h2>Choose a restaurant</h2>
        <xsl:apply-templates select="restaurants/restaurant"/>
    </xsl:template>

    <xsl:template match="restaurant">
        <h3 id="heading{@rid}">
            <a role="button" data-toggle="collapse" href="#collapseRestaurant{@rid}"
               aria-expanded="false" aria-controls="collapseRestaurant{@rid}">
                <xsl:value-of select="name"/>
            </a>
        </h3>
        <div id="collapseRestaurant{@rid}" class="collapse" aria-labelledby="heading{@rid}">
            Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VH
        </div>
    </xsl:template>
</xsl:stylesheet>