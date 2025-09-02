package io.github.projecthsf.devutils.utils;

import com.intellij.icons.AllIcons;
import com.intellij.ide.HelpTooltip;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.opencsv.CSVReader;
import io.github.projecthsf.devutils.enums.NameCaseEnum;
import io.github.projecthsf.devutils.toolWindow.contents.ApplyDatasetWindowPanel;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ApplyDatasetUtil {
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

    public static ApplyDatasetWindowPanel getToolWindowPanel(ToolWindow toolWindow) {
        for (Content content: toolWindow.getContentManager().getContents()) {
            if (content.getComponent() instanceof ApplyDatasetWindowPanel toolWindowPanel) {
                return toolWindowPanel;
            }
        }

        return null;
    }
}
