package tours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Зал музея с панорамой.
 Соответствует сущности «Зал» из логической модели. Помимо
 самого зала (название, URL панорамы) хранит параметры панорамы —
 width, height, extension - которые нужны для операции «Проверить
 формат панорам» (валидация соотношения сторон 2:1 и расширения).

 Содержит точки на панораме как List<Point>. Это полиморфная коллекция:
 в одном списке лежат и TransitionPoint, и ExhibitPoint, доступ к ним
 идёт через общий тип Point.
 */
public class Hall {

    private final long hallId;
    private final Tour tour;
    private final String name;
    private final String panoramaUrl;
    private final int width;
    private final int height;
    private final String extension;
    private final List<Point> points;

    public Hall(long hallId, Tour tour, String name, String panoramaUrl,
                int width, int height, String extension) {
        this.hallId = hallId;
        this.tour = tour;
        this.name = name;
        this.panoramaUrl = panoramaUrl;
        this.width = width;
        this.height = height;
        this.extension = extension;
        this.points = new ArrayList<>();
    }

    // Добавляет точку на панораму этого зала.
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     Проверяет, есть ли у зала хотя бы одна исходящая точка перехода.
     Используется в алгоритме №2 для поиска тупиков: зал без исходящих
     переходов (если он не единственный) - это тупик.
     */
    public boolean hasOutgoingTransitions() {
        return points.stream().anyMatch(p -> p instanceof TransitionPoint);
    }

    // Возвращает только точки перехода.
    public List<TransitionPoint> getTransitionPoints() {
        return points.stream()
                .filter(p -> p instanceof TransitionPoint)
                .map(p -> (TransitionPoint) p)
                .toList();
    }

    // Возвращает только точки экспонатов.
    public List<ExhibitPoint> getExhibitPoints() {
        return points.stream()
                .filter(p -> p instanceof ExhibitPoint)
                .map(p -> (ExhibitPoint) p)
                .toList();
    }

    public long getHallId() {
        return hallId;
    }

    public Tour getTour() {
        return tour;
    }

    public String getName() {
        return name;
    }

    public String getPanoramaUrl() {
        return panoramaUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getExtension() {
        return extension;
    }

    // Возвращает все точки зала для просмотра.
    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    @Override
    public String toString() {
        return "Hall{" + hallId + ", " + name + "}";
    }
}
