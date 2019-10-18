package com.guido4f.maven.drools;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

import java.io.InputStream;

public class DroolsUtils {
    private DroolsUtils() {
        //DO NOT INSTANTIATE
    }
    static String getDRL(InputStream stream) {
        SpreadsheetCompiler comp = new SpreadsheetCompiler();
        String drl = comp.compile(false,
                                  stream,
                                  InputType.XLS);
        return drl;
    }
}
