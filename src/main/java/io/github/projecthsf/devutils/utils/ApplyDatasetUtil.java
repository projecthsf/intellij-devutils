package io.github.projecthsf.devutils.utils;

import com.opencsv.CSVReader;
import io.github.projecthsf.devutils.enums.NameCaseEnum;

import javax.swing.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ApplyDatasetUtil {
    public static String getPreviewString(JTextArea dataList, JTextArea template) {
        List<String> previewData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new StringReader(dataList.getText()))) {
            String[] columns;
            while ((columns = reader.readNext()) != null) {
                previewData.add(getAppliedString(template.getText(), columns));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return String.join("\n", previewData);
    }

    private static String getAppliedString(String codeTemplate, String[] values) {
        int i = 0;
        for (String value: values) {
            for (NameCaseEnum nameCase: NameCaseEnum.values()) {
                codeTemplate = codeTemplate.replace(String.format("${%s.%s}", i, nameCase.getCode()), NameCaseUtil.toNameCase(nameCase, value));
            }
            codeTemplate = codeTemplate.replace(String.format("${%s}", i), value);
            codeTemplate = codeTemplate.replace(String.format("$%s", i), value);
            i++;
        }

        return codeTemplate;
    }
}
