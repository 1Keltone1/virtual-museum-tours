package tours;

/**
 * Ассоциативный класс «Связь Экспонат-Медиа» из логической модели КТ2.
 *
 * Разрешает связь M:N между Exhibit и MediaFile: у одного экспоната может
 * быть несколько медиафайлов, и один медиафайл может использоваться
 * несколькими экспонатами. Дополнительный атрибут displayOrder задаёт
 * порядок отображения файла именно у этого экспоната (зависит от обоих
 * концов связи, что соответствует 2НФ).
 *
 * Хранит ссылки на сам экспонат и на медиафайл — реализация ассоциации
 * в памяти, а не просто пару идентификаторов.
 */
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
