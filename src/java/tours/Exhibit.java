package tours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Экспонат музейной коллекции внутри экскурсии. Соответствует сущности «Экспонат» из логической модели.

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

    //Создаёт связь с медиафайлом и добавляет её в конец списка.
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

    //Возвращает список связей для просмотра.
    public List<ExhibitMedia> getMediaLinks() {
        return Collections.unmodifiableList(mediaLinks);
    }

    @Override
    public String toString() {
        return "Exhibit{" + exhibitId + ", " + name + "}";
    }
}
