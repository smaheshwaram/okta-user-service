package org.naic.fs.okta.usersvc.invoker;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.FieldPosition;
import java.util.Date;

public class RFC3339DateFormat extends ISO8601DateFormat {

    private static final long serialVersionUID = 1L;

    // Same as ISO8601DateFormat but serializing milliseconds.
    @Override
    public StringBuffer format( final Date date, final StringBuffer toAppendTo, final FieldPosition fieldPosition ) {
        final String value = ISO8601Utils.format( date, true );
        toAppendTo.append( value );
        return toAppendTo;
    }
}
