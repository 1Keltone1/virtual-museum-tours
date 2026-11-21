package tours;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Алгоритм №1 «Проверка связей точек перехода».
 *
 * Соответствует процедурной функции check_hotspot_links() из КТ2.
 * Проверяет для каждой точки перехода:
 *   1) целевой зал существует в туре (нет «битых» ссылок);
 *   2) точка не ведёт в тот же зал, где находится сама (нет петель).
 *
 * Оценка сложности (как в КТ2):
 *   Пусть n — число точек перехода, m — число залов.
 *   - Формирование множества идентификаторов залов: O(m),
 *     добавление в HashSet — O(1) в среднем.
 *   - Цикл по n точкам, проверка в множестве — O(1) в среднем.
 *   Итого по времени: O(n + m). По памяти: O(m + e), где e — число ошибок.
 *
 * Использует Set<Long> для существующих залов и Stream API для
 * фильтрации — требование КТ3 «коллекции List, Map, Set».
 *
 * Реализация интерфейса TourValidator. Вызов через тип интерфейса —
 * полиморфизм.
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
            // Проверка 2: петля (точка в тот же зал, где находится сама).
            if (point.isSelfLoop()) {
                errors.add("Ошибка: Точка " + point.getPointId()
                        + " ведёт в тот же зал " + sourceId);
            }
        }
        return errors;
    }
}
