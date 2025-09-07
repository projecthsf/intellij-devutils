package io.github.projecthsf.devutils.utils;

import com.intellij.icons.AllIcons;
import com.intellij.ide.HelpTooltip;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.opencsv.*;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.toolWindow.controller.DatasetSnippetWindowController;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasetUtil {
    private static List<DatatsetDTO> records;
    public static class DatatsetDTO {
        private Map<Object, String> velocity = new HashMap<>();
        private Map<String, String> simplify = new HashMap<>();

        public Map<Object, String> getVelocity() {
            return velocity;
        }

        public Map<String, String> getSimplify() {
            return simplify;
        }
    }

    public static final String DEFAULT_TEMPLATE_NAME = "DEFAULT";
    public static final String ADVANCE_TEMPLATE_NAME = "ADVANCE";
    public static final String EMPTY_TEMPLATE_NAME = "--EMPTY--";
    public static String getPreviewString(String dataList, String templateCode) {
        List<String> previewData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new StringReader(dataList))) {
            String[] columns;
            while ((columns = reader.readNext()) != null) {
                previewData.add(getAppliedString(templateCode, columns));
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

    public static List<DatatsetDTO> getDatasetRecords(CsvSeparatorEnum separator, String dataset, boolean forceUpdate) {
        if (records != null && !forceUpdate) {
            return records;
        }

        records = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(separator.getSeparator()).build();
        try (StringReader reader = new StringReader(dataset)) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
            String[] columns;
            while ((columns = csvReader.readNext()) != null) {
                records.add(getDatasetRecord(columns));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return records;
    }

    private static DatatsetDTO getDatasetRecord(String[] values) {
        DatatsetDTO dto = new DatatsetDTO();

        int i = 0;
        for (String value: values) {
            // velocity keys
            dto.getVelocity().put(i, value); // int i => value
            dto.getVelocity().put(String.format("%s", i), value); // string i => value

            // simple keys
            dto.getSimplify().put(String.format("${%s}", i), value); // string ${i} => value
            dto.getSimplify().put(String.format("$%s", i), value); // string $i => value

            for (NameCaseEnum nameCase: NameCaseEnum.values()) {
                // velocity keys
                dto.getVelocity().put(String.format("%s.%s", i, nameCase.getCode()), NameCaseUtil.toNameCase(nameCase, value)); // string i.nameCase

                // simple keys
                dto.getSimplify().put(String.format("${%s.%s}", i, nameCase.getCode()), NameCaseUtil.toNameCase(nameCase, value)); // string ${i.nameCase}
            }
            i++;
        }

        return dto;
    }

    public static String getTemplate(String templateName) {
        InputStream contentStream = DatasetUtil.class.getClassLoader().getResourceAsStream(templateName);
        if (contentStream == null) {
            return null;
        }

        try {
            return IOUtils.toString(contentStream, StandardCharsets.UTF_8);
        } catch (Exception ignore) {

        }

        return null;
    }

    public static JButton getToolTipButton(String title, String toolTipMsg) {
        JButton button = new JButton(title, AllIcons.General.Information);
        button.setBorderPainted(false);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        new HelpTooltip().setTitle(title).setDescription(toolTipMsg).installOn(button);
        return button;
    }

    public static DatasetSnippetWindowController getToolWindowPanel(ToolWindow toolWindow) {
        for (Content content: toolWindow.getContentManager().getContents()) {
            if (content.getComponent() instanceof DatasetSnippetWindowController toolWindowPanel) {
                return toolWindowPanel;
            }
        }

        return null;
    }
}
