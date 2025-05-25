package model;

/**
 * Represents a search term used to match against alert contents.
 * Includes language, keyword text, and an indicator for ordered word matching.
 */
public class QueryTerm {

    private final int id;
    private final String text;
    private final String language;
    private final boolean keepOrder;

    /**
     * Constructor to initialize a query term.
     *
     * @param id         Unique identifier for the term
     * @param text       The keyword phrase to match
     * @param language   Language code (e.g., "en")
     * @param keepOrder  If true, word order must be preserved in matches
     */
    public QueryTerm(int id, String text, String language, boolean keepOrder) {
        this.id = id;
        this.text = text;
        this.language = language;
        this.keepOrder = keepOrder;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isKeepOrder() {
        return keepOrder;
    }

    @Override
    public String toString() {
        return "QueryTerm{" +
               "id=" + id +
               ", text='" + text + '\'' +
               ", language='" + language + '\'' +
               ", keepOrder=" + keepOrder +
               '}';
    }
}
