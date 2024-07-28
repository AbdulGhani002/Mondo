package com.project.mondo.models;

import java.util.List;

public class TranslationResponse {
    private Translations translations;

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public static class Translations {
        private List<TranslationItem> noun;

        public List<TranslationItem> getNoun() {
            return noun;
        }

        public void setNoun(List<TranslationItem> noun) {
            this.noun = noun;
        }

        private List<TranslationItem> adjective;

        public List<TranslationItem> getAdjective() {
            return adjective;
        }

        public void setAdjective(List<TranslationItem> adjective) {
            this.adjective = adjective;
        }
    }

    public static class TranslationItem {
        private String translation;

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }
}
