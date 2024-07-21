package com.project.mondo.models;

import java.util.List;

public class TranslationResponse {
    private String result;
    private String pronunciation;
    private String fromPronunciation;
    private Definitions definitions;
    private List<String> examples;
    private List<Translation> translations;
    private List<ReversedTranslation> reversedTranslations;
    private String frequency;



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getFromPronunciation() {
        return fromPronunciation;
    }

    public void setFromPronunciation(String fromPronunciation) {
        this.fromPronunciation = fromPronunciation;
    }

    public Definitions getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Definitions definitions) {
        this.definitions = definitions;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<ReversedTranslation> getReversedTranslations() {
        return reversedTranslations;
    }

    public void setReversedTranslations(List<ReversedTranslation> reversedTranslations) {
        this.reversedTranslations = reversedTranslations;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public static class Translation {
        private String translation;

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class ReversedTranslation {
        private String translation;

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class Definitions {
        private List<Definition> exclamation;
        private List<Definition> noun;
        private List<Definition> verb;


        public List<Definition> getExclamation() {
            return exclamation;
        }

        public void setExclamation(List<Definition> exclamation) {
            this.exclamation = exclamation;
        }

        public List<Definition> getNoun() {
            return noun;
        }

        public void setNoun(List<Definition> noun) {
            this.noun = noun;
        }

        public List<Definition> getVerb() {
            return verb;
        }

        public void setVerb(List<Definition> verb) {
            this.verb = verb;
        }
    }

    public static class Definition {
        private String definition;
        private String example;



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
}
