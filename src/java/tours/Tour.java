package tours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Виртуальная экскурсия — корневой агрегат модели.
 *
 * Соответствует сущности «Экскурсия» из логической модели КТ2.
 * Агрегирует залы (Tour 1 *-- N Hall, композиция — залы не существуют
 * без тура) и экспонаты (Tour 1 o-- N Exhibit, агрегация — экспонат
 * может быть перенесён в другой тур или существовать отдельно).
 *
 * Жизненный цикл статуса управляется методом publish(): экскурсию нельзя
 * опубликовать, пока валидаторы не подтвердят корректность. Сама проверка
 * выполняется снаружи (см. Main), Tour только хранит статус и ссылку.
 *
 * Хранение коллекций как List — требование КТ3 «сущности в List, Map, Set,
 * а не в массивах».
 */
public class Tour {

    private final long tourId;
    private final String title;
    private TourStatus status;
    private String publishUrl;
    private final List<Hall> halls;
    private final List<Exhibit> exhibits;

    public Tour(long tourId, String title) {
        this.tourId = tourId;
        this.title = title;
        this.status = TourStatus.DRAFT;
        this.publishUrl = null;
        this.halls = new ArrayList<>();
        this.exhibits = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        halls.add(hall);
    }

    public void addExhibit(Exhibit exhibit) {
        exhibits.add(exhibit);
    }

    /**
     * Переводит экскурсию в статус PUBLISHED и формирует уникальную ссылку.
     * В реальном приложении ссылку генерировал бы бэкенд; здесь — простой
     * шаблон на основе идентификатора тура.
     */
    public String publish() {
        this.status = TourStatus.PUBLISHED;
        this.publishUrl = "https://tours.example.ru/t/" + tourId;
        return publishUrl;
    }

    public void markReady() {
        this.status = TourStatus.READY;
    }

    public long getTourId() {
        return tourId;
    }

    public String getTitle() {
        return title;
    }

    public TourStatus getStatus() {
        return status;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    /** Возвращает все точки перехода тура, собранные из всех залов. */
    public List<TransitionPoint> getAllTransitionPoints() {
        return halls.stream()
                .flatMap(h -> h.getTransitionPoints().stream())
                .toList();
    }

    public List<Hall> getHalls() {
        return Collections.unmodifiableList(halls);
    }

    public List<Exhibit> getExhibits() {
        return Collections.unmodifiableList(exhibits);
    }

    @Override
    public String toString() {
        return "Tour{" + tourId + ", " + title + ", " + status + "}";
    }
}
