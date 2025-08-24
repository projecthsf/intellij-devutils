package io.github.projecthsf.devutils.settings;

import com.intellij.openapi.options.Configurable;
import io.github.projecthsf.devutils.enums.LanguageEnum;
import io.github.projecthsf.devutils.forms.DataTypeMapSettingForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

public  class DataTypeMapConfigurable {
    public static abstract class CommonDataTypeConfigureable implements Configurable {
        private DataTypeMapSettingForm form;
        private LanguageEnum language;
        StateComponent.State state = Objects.requireNonNull(StateComponent.getInstance().getState());
        CommonDataTypeConfigureable(LanguageEnum language) {
            this.language = language;
        }
        @Nls(capitalization = Nls.Capitalization.Title)
        @Override
        public String getDisplayName() {
            return "";
        }

        @Override
        public JComponent getPreferredFocusedComponent() {
            return null;
        }

        @Nullable
        @Override
        public JComponent createComponent() {
            form = new DataTypeMapSettingForm(language, state.getDataTypeMap(language));
            return form;
        }

        @Override
        public boolean isModified() {
            if (form.getDataTypeMap().size() != state.getDataTypeMap(language).size()) {
                return true;
            }
            for (String key : form.getDataTypeMap().keySet()) {
                if (!state.getDataTypeMap(language).containsKey(key)) {
                    return true;
                }

                if (!form.getDataTypeMap().get(key).equals(state.getDataTypeMap(language).get(key))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void apply() {
            state.resetDataTypeMap(language);
            for (String key : form.getDataTypeMap().keySet()) {
                state.getDataTypeMap(language).put(
                        key,
                        form.getDataTypeMap().get(key)
                );
            }
        }

        @Override
        public void reset() {
            form.resetForm();

        }

        @Override
        public void disposeUIResources() {
            form = null;
        }

    }

    public static class SqlDataTypeConfigureable extends CommonDataTypeConfigureable {
        SqlDataTypeConfigureable() {
            super(LanguageEnum.SQL);
        }
    }

    public static class JsonDataTypeConfigureable extends CommonDataTypeConfigureable {
        JsonDataTypeConfigureable() {
            super(LanguageEnum.JSON);
        }
    }
}
