package io.github.projecthsf.devutils.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.projecthsf.devutils.enums.*;
import io.github.projecthsf.devutils.utils.DatasetUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/*
 * Supports storing the application settings in a persistent way.
 * The {@link com.intellij.openapi.components.State State} and {@link Storage}
 * annotations define the name of the data and the filename where these persistent
 * application settings are stored.
 */

@State(
        name = "io.github.projecthsf.devutils.settings.StateComponent",
        storages = @Storage("settings.xml")
)
public final class StateComponent implements PersistentStateComponent<StateComponent.State> {
    public static class State {
        private  Map<LanguageEnum, Map<String, String>> dataTypeMap = new HashMap<>();
        private final Map<String, String> dtoTemplateMap = new HashMap<>();
        private  Map<ActionGroupEnum, Map<ActionEnum, Boolean>> actionAndGroupMap = new HashMap<>();

        private final Map<String, ApplyDatasetState> applyDatasetMap = new HashMap<>();

        State() {
            String defaultTemplate = DatasetUtil.getTemplate("templates/java-dto-template.tpl");
            dtoTemplateMap.put(DatasetUtil.DEFAULT_TEMPLATE_NAME, defaultTemplate);

            resetDataTypeMap(LanguageEnum.SQL);
            for (SqlDataTypeEnum dataType: SqlDataTypeEnum.values()) {
                dataTypeMap.get(LanguageEnum.SQL).put(dataType.name(), dataType.getDataType());
            }

            resetDataTypeMap(LanguageEnum.JSON);
            for (JsonDataTypeEnum dataType: JsonDataTypeEnum.values()) {
                dataTypeMap.get(LanguageEnum.JSON).put(dataType.getSourceDataType(), dataType.getTargetDataType());
            }

            for (ActionEnum action: ActionEnum.values()) {
                if (!actionAndGroupMap.containsKey(action.getGroup())) {
                    actionAndGroupMap.put(action.getGroup(), new HashMap<>());
                }

                actionAndGroupMap.get(action.getGroup()).put(action, true);
            }

            String datasetSample = DatasetUtil.getTemplate("templates/applydataset-dataset-sample.tpl");

            String codeTemplateSampleDefault = DatasetUtil.getTemplate("templates/applydataset-code-template-sample-default.tpl");
            ApplyDatasetState applyDatasetState = new ApplyDatasetState(CsvSeparatorEnum.COMMA, datasetSample, codeTemplateSampleDefault);
            applyDatasetMap.put(
                    DatasetUtil.DEFAULT_TEMPLATE_NAME,
                    new ApplyDatasetState(CsvSeparatorEnum.COMMA, datasetSample, codeTemplateSampleDefault)
            );

            String codeTemplateSampleAdvance = DatasetUtil.getTemplate("templates/applydataset-code-template-sample-advance.tpl");
            applyDatasetMap.put(
                    DatasetUtil.ADVANCE_TEMPLATE_NAME,
                    new ApplyDatasetState(CsvSeparatorEnum.COMMA, datasetSample, codeTemplateSampleAdvance)
            );
        }

        public Map<String, String> getDataTypeMap(LanguageEnum language) {
            return dataTypeMap.get(language);
        }

        public void resetDataTypeMap(LanguageEnum language) {
            dataTypeMap.put(language, new HashMap<>());
        }

        public Map<String, String> getDtoTemplateMap() {
            return dtoTemplateMap;
        }

        public Map<ActionGroupEnum, Map<ActionEnum, Boolean>> getActionAndGroupMap() {
            return actionAndGroupMap;
        }

        public Map<String, ApplyDatasetState> getApplyDatasetMap() {
            return applyDatasetMap;
        }
    }

    private State myState = new State();

    public static StateComponent getInstance() {
        return ApplicationManager.getApplication().getService(StateComponent.class);
    }

    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }


    public static class ApplyDatasetState {
        private CsvSeparatorEnum csvSeparator;
        private String dataset;
        private String codeTemplate;

        public ApplyDatasetState(CsvSeparatorEnum csvSeparator, String dataset, String codeTemplate) {
            this.csvSeparator = csvSeparator;
            this.dataset = dataset;
            this.codeTemplate = codeTemplate;
        }

        public String getDataset() {
            return dataset;
        }

        public String getCodeTemplate() {
            return codeTemplate;
        }

        public CsvSeparatorEnum getCsvSeparator() {
            return csvSeparator;
        }
    }
}
