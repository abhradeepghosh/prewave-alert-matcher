package model;

import java.util.List;

/**
 * Represents an alert with multilingual content.
 */
public class Alert {

    private final String id;
    private final List<Content> contents;

    /**
     * Constructs an Alert with the given ID and list of contents.
     *
     * @param id       Unique alert identifier
     * @param contents List of content items in different languages
     */
    public Alert(String id, List<Content> contents) {
        this.id = id;
        this.contents = contents;
    }

    public String getId() {
        return id;
    }

    public List<Content> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Alert{" +
               "id='" + id + '\'' +
               ", contents=" + contents +
               '}';
    }

    /**
     * Inner class representing the content of an alert.
     */
    public static class Content {
        private final String text;
        private final String language;
        private final String type;

        /**
         * Constructs a content item.
         *
         * @param text     The textual content
         * @param language Language code (e.g., "en", "de")
         * @param type     Type of content (e.g., "incident", "event")
         */
        public Content(String text, String language, String type) {
            this.text = text;
            this.language = language;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public String getLanguage() {
            return language;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Content{" +
                   "text='" + text + '\'' +
                   ", language='" + language + '\'' +
                   ", type='" + type + '\'' +
                   '}';
        }
    }
}
