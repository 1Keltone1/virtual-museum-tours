package tours;

// Ассоциативный класс «Связь Экспонат-Медиа» из логической модели.
public class ExhibitMedia {

    private final Exhibit exhibit;
    private final MediaFile media;
    private final int displayOrder;

    public ExhibitMedia(Exhibit exhibit, MediaFile media, int displayOrder) {
        this.exhibit = exhibit;
        this.media = media;
        this.displayOrder = displayOrder;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public MediaFile getMedia() {
        return media;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public String toString() {
        return "ExhibitMedia{exhibit=" + exhibit.getExhibitId()
                + ", media=" + media.getMediaId()
                + ", order=" + displayOrder + "}";
    }
}
