package com.project.mondo.adapters;

import java.util.List;
import java.util.Map;

public class WordInfo {
    private String word;
    private String pronunciation;
    private List<Suggestion> suggestions;
    private List<Definition> definitions;
    private Map<String, List<Translation>> translations;

    public WordInfo(String word, String pronunciation, List<Suggestion> suggestions, List<Definition> definitions, Map<String, List<Translation>> translations) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.suggestions = suggestions;
        this.definitions = definitions;
        this.translations = translations;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public Map<String, List<Translation>> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, List<Translation>> translations) {
        this.translations = translations;
    }

    public String getTranslation() {
        if (translations != null && !translations.isEmpty()) {
            for (List<Translation> translationList : translations.values()) {
                if (!translationList.isEmpty()) {
                    return translationList.get(0).getTranslation();
                }
            }
        }
        return "";
    }

    public static class Suggestion {
        private String text;
        private String translation;

        public Suggestion(String text, String translation) {
            this.text = text;
            this.translation = translation;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class Definition {
        private String definition;
        private String example;

        public Definition(String definition, String example) {
            this.definition = definition;
            this.example = example;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }
    }

    public static class Translation {
        private String translation;
        private List<String> reversedTranslations;
        private String frequency;

        public Translation(String translation, List<String> reversedTranslations, String frequency) {
            this.translation = translation;
            this.reversedTranslations = reversedTranslations;
            this.frequency = frequency;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }

        public List<String> getReversedTranslations() {
            return reversedTranslations;
        }

        public void setReversedTranslations(List<String> reversedTranslations) {
            this.reversedTranslations = reversedTranslations;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }
    }
}