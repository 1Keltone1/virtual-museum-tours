package tours;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 Алгоритм №1 «Проверка связей точек перехода».
 Соответствует функции check_hotspot_links().
 Проверяет для каждой точки перехода:
   1) целевой зал существует в туре (нет «битых» ссылок);
   2) точка не ведёт в тот же зал, где находится сама (нет петель).
 */
public class HotspotLinksValidator implements TourValidator {

    @Override
    public List<String> validate(Tour tour) {
        List<String> errors = new ArrayList<>();
        if (tour.getHalls().isEmpty()) {
            return errors; // пустой тур проверять нечего
        }

        // Шаг 1: множество идентификаторов существующих залов.
        Set<Long> existingHallIds = tour.getHalls().stream()
                .map(Hall::getHallId)
                .collect(Collectors.toCollection(HashSet::new));

        // Шаг 2: проверка каждой точки перехода.
        for (TransitionPoint point : tour.getAllTransitionPoints()) {
            long sourceId = point.getHall().getHallId();
            long targetId = point.getTargetHall().getHallId();

            // Проверка 1: целевой зал существует.
            if (!existingHallIds.contains(targetId)) {
                errors.add("Ошибка: Точка " + point.getPointId()
                        + " ведёт в несуществующий зал " + targetId);
                continue;
            }
            // Проверка 2: петля.
            if (point.isSelfLoop()) {
                errors.add("Ошибка: Точка " + point.getPointId()
                        + " ведёт в тот же зал " + sourceId);
            }
        }
        return errors;
    }
}
