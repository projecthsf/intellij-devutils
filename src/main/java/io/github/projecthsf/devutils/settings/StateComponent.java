package io.github.projecthsf.devutils.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.projecthsf.devutils.enums.JsonDataTypeEnum;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.enums.SqlDataTypeEnum;
import io.github.projecthsf.devutils.utils.ApplyDatasetUtil;
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

        State() {
            String defaultTemplate = ApplyDatasetUtil.getTemplate("templates/java-dto-template.tpl");
            dtoTemplateMap.put(ApplyDatasetUtil.DEFAULT_TEMPLATE_NAME, defaultTemplate);

            resetDataTypeMap(LanguageEnum.SQL);
            for (SqlDataTypeEnum dataType: SqlDataTypeEnum.values()) {
                dataTypeMap.get(LanguageEnum.SQL).put(dataType.name(), dataType.getDataType());
            }

            resetDataTypeMap(LanguageEnum.JSON);
            for (JsonDataTypeEnum dataType: JsonDataTypeEnum.values()) {
                dataTypeMap.get(LanguageEnum.JSON).put(dataType.getSourceDataType(), dataType.getTargetDataType());
            }
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
}
