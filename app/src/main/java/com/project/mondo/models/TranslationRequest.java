package com.project.mondo.models;

public class TranslationRequest {
    private String q;
    private String source;
    private String target;
    private String format;

    public TranslationRequest(String q, String source, String target, String format) {
        this.q = q;
        this.source = source;
        this.target = target;
        this.format = format;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
