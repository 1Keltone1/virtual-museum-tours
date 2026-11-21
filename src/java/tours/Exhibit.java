package tours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Экспонат музейной коллекции внутри экскурсии.
 *
 * Соответствует сущности «Экспонат» из логической модели КТ2.
 * Принадлежит ровно одной экскурсии (связь Exhibit N --> 1 Tour).
 * Содержит список связанных медиафайлов через ExhibitMedia —
 * связь M:N с MediaFile разрешается ассоциативным классом.
 *
 * Коллекция mediaLinks хранится как List<ExhibitMedia> (не массив),
 * порядок определяется полем displayOrder каждой связи.
 */
public class Exhibit {

    private final long exhibitId;
    private final Tour tour;
    private final String name;
    private final String description;
    private final List<ExhibitMedia> mediaLinks;

    public Exhibit(long exhibitId, Tour tour, String name, String description) {
        this.exhibitId = exhibitId;
        this.tour = tour;
        this.name = name;
        this.description = description;
        this.mediaLinks = new ArrayList<>();
    }

    /**
     * Создаёт связь с медиафайлом и добавляет её в конец списка.
     * Возвращает созданную связь, чтобы вызывающий код мог при необходимости
     * переставить её по displayOrder.
     */
    public ExhibitMedia addMedia(MediaFile file, int displayOrder) {
        ExhibitMedia link = new ExhibitMedia(this, file, displayOrder);
        mediaLinks.add(link);
        return link;
    }

    public long getExhibitId() {
        return exhibitId;
    }

    public Tour getTour() {
        return tour;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /** Возвращает неизменяемый просмотр списка связей. */
    public List<ExhibitMedia> getMediaLinks() {
        return Collections.unmodifiableList(mediaLinks);
    }

    @Override
    public String toString() {
        return "Exhibit{" + exhibitId + ", " + name + "}";
    }
}
