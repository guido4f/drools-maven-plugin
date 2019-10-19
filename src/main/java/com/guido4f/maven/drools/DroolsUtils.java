package com.guido4f.maven.drools;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.decisiontable.parser.DefaultRuleSheetListener;

import java.io.InputStream;

public class DroolsUtils {
    private DroolsUtils() {
        //DO NOT INSTANTIATE
    }

    static String getDRL(InputStream stream) {
        return getDRL(stream,
                      null);
    }

    static String getDRL(InputStream stream, String worksheet) {

        SpreadsheetCompiler comp = new SpreadsheetCompiler();

        final DefaultRuleSheetListener defaultRuleSheetListener = new DefaultRuleSheetListener(false,
                                                                                               false);
        if (null != worksheet) {
            defaultRuleSheetListener.setWorksheetName(worksheet);
        }
        String drl = comp.compile(stream,
                                  InputType.XLS,
                                  defaultRuleSheetListener);


        return drl;
    }
}
