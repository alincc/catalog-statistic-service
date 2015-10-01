package no.nb.microservices.catalogstatistic.core.newspaper.model;

/**
 * Created by alfredw on 9/21/15.
 */
public class Newspaper {
    private final String title;
    private final long numberOfEditions;
    private String firstScannedEditionDate;
    private String lastScannedEditionDate;

    public Newspaper(String title, long numberOfEditions) {
        this.title = title;
        this.numberOfEditions = numberOfEditions;
    }

    public Newspaper(String title, long numberOfEditions, String firstScannedEditionDate, String lastScannedEditionDate) {
        this.title = title;
        this.numberOfEditions = numberOfEditions;
        this.firstScannedEditionDate = firstScannedEditionDate;
        this.lastScannedEditionDate = lastScannedEditionDate;
    }

    public String getTitle() {
        return title;
    }

    public long getNumberOfEditions() {
        return numberOfEditions;
    }

    public String getFirstScannedEditionDate() {
        return firstScannedEditionDate;
    }

    public void setFirstScannedEditionDate(String firstScannedEditionDate) {
        this.firstScannedEditionDate = firstScannedEditionDate;
    }

    public String getLastScannedEditionDate() {
        return lastScannedEditionDate;
    }

    public void setLastScannedEditionDate(String lastScannedEditionDate) {
        this.lastScannedEditionDate = lastScannedEditionDate;
    }
}
