package tours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Виртуальная экскурсия - корневой агрегат модели.

 Соответствует сущности «Экскурсия» из логической модели.
 Агрегирует залы и экспонаты.

 Жизненный цикл статуса управляется методом publish(): экскурсию нельзя
 опубликовать, пока валидаторы не подтвердят корректность. Сама проверка
 выполняется снаружи (в классе Main), Tour только хранит статус и ссылку.
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
     Переводит экскурсию в статус PUBLISHED и формирует уникальную ссылку.
     (пока простой шаблон на основе идентификатора тура.)

     Сам защищает свои инварианты: тур нельзя отдать на публикацию, пока он не
     переведён в READY (см. markReady()) и пока в нём нет хотя бы одного зал.
     Тем самым статус READY реально влияет на публикацию, а Tour не
     полагается на то, что вызывающий код заранее прогнал валидаторы.
     */
    public String publish() {
        if (status != TourStatus.READY) {
            throw new IllegalStateException(
                    "Невозможно опубликовать тур в статусе " + status
                            + ": переведите тур в READY через markReady()");
        }
        if (halls.isEmpty()) {
            throw new IllegalStateException(
                    "Невозможно опубликовать тур без залов");
        }
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

    // Возвращает все точки перехода тура, собранные из всех залов.
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