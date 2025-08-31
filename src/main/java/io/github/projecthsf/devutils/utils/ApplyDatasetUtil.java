package io.github.projecthsf.devutils.utils;

import com.intellij.icons.AllIcons;
import com.intellij.ide.HelpTooltip;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.opencsv.*;
import io.github.projecthsf.devutils.enums.CsvSeparatorEnum;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.service.VelocityService;
import io.github.projecthsf.devutils.toolWindow.controller.ApplyDatasetWindowController;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyDatasetUtil {
    private static DatatsetDTO dto;
    public static class DatatsetDTO {
        private List<Map<Object, String>> velocity = new ArrayList<>();
        private List<Map<String, String>> simplify = new ArrayList<>();

        public List<Map<Object, String>> getVelocity() {
            return velocity;
        }

        public List<Map<String, String>> getSimplify() {
            return simplify;
        }
    }

    public static final String DEFAULT_TEMPLATE_NAME = "DEFAULT";
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

    public static DatatsetDTO getDatasetRecords(CsvSeparatorEnum separator, String dataset, boolean forceUpdate) {
        if (dto != null && !forceUpdate) {
            return dto;
        }

        dto = new DatatsetDTO();
        CSVParser parser = new CSVParserBuilder().withSeparator(separator.getSeparator()).build();
        try (StringReader reader = new StringReader(dataset)) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
            String[] columns;
            while ((columns = csvReader.readNext()) != null) {
                getDatasetRecord(dto, columns);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return dto;
    }

    private static void getDatasetRecord(DatatsetDTO dto, String[] values) {
        Map<Object, String> velocity = new HashMap<>();
        Map<String, String> simplify = new HashMap<>();

        int i = 0;
        for (String value: values) {
            // velocity keys
            velocity.put(i, value); // int i => value
            velocity.put(String.format("%s", i), value); // string i => value

            // simple keys
            simplify.put(String.format("${%s}", i), value); // string ${i} => value
            simplify.put(String.format("$%s", i), value); // string $i => value

            for (NameCaseEnum nameCase: NameCaseEnum.values()) {
                // velocity keys
                velocity.put(String.format("%s.%s", i, nameCase.getCode()), NameCaseUtil.toNameCase(nameCase, value)); // string i.nameCase

                // simple keys
                simplify.put(String.format("${%s.%s}", i, nameCase.getCode()), NameCaseUtil.toNameCase(nameCase, value)); // string ${i.nameCase}
            }
            i++;
        }

        dto.getVelocity().add(velocity);
        dto.getSimplify().add(simplify);
    }

    public static String getTemplate(String templateName) {
        InputStream contentStream = ApplyDatasetUtil.class.getClassLoader().getResourceAsStream(templateName);
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

    public static ApplyDatasetWindowController getToolWindowPanel(ToolWindow toolWindow) {
        for (Content content: toolWindow.getContentManager().getContents()) {
            if (content.getComponent() instanceof ApplyDatasetWindowController toolWindowPanel) {
                return toolWindowPanel;
            }
        }

        return null;
    }
}
