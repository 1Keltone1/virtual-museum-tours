package tours;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Алгоритм №2 «Проверка связности графа тура».
 *
 * Соответствует процедурной функции check_graph_connectivity() из КТ2.
 * Проверяет:
 *   1) отсутствие тупиков (зал без исходящих переходов, если он не единственный);
 *   2) связность графа: из любого зала можно дойти до любого другого.
 *
 * Оценка сложности (как в КТ2):
 *   Пусть n — число залов (вершин), m — число точек перехода (рёбер).
 *   - Построение графа смежности: O(n + m).
 *   - Проверка тупиков: O(n).
 *   - Внешний цикл по n залам, внутри DFS за O(n + m): итого O(n*(n+m)).
 *   По времени: O(n*(n+m)). По памяти: O(n + m) — граф, множество visited,
 *   стек DFS (visited пересоздаётся перед каждым запуском).
 *
 * Реализация через интерактивный стек (Deque) вместо рекурсии: поведение
 * идентично рекурсивному DFS из КТ2, но не рискует переполнением стека
 * на больших графах. Сложность та же.
 *
 * Использует Map<Long, List<Long>> для графа и Set<Long> для visited —
 * требование КТ3 «коллекции List, Map, Set, а не массивы».
 */
public class GraphConnectivityValidator implements TourValidator {

    /** Граф смежности: hallId -> список идентификаторов смежных залов. */
    private final Map<Long, List<Long>> adjacency = new HashMap<>();

    /** Множество посещённых вершин в текущем обходе. */
    private final Set<Long> visited = new HashSet<>();

    @Override
    public List<String> validate(Tour tour) {
        List<String> errors = new ArrayList<>();
        if (tour.getHalls().isEmpty()) {
            return errors;
        }

        // Шаг 1: построение графа смежности.
        buildGraph(tour);

        // Шаг 2: проверка тупиков (только если залов больше одного).
        if (tour.getHalls().size() > 1) {
            for (Hall hall : tour.getHalls()) {
                if (!hall.hasOutgoingTransitions()) {
                    errors.add("Ошибка: Зал " + hall.getHallId()
                            + " является тупиком (нет исходящих переходов)");
                }
            }
        }

        // Шаг 3: проверка связности — DFS из каждого зала.
        int totalHalls = tour.getHalls().size();
        for (Hall startHall : tour.getHalls()) {
            visited.clear();
            dfs(startHall.getHallId());
            if (visited.size() != totalHalls) {
                errors.add("Ошибка: Из зала " + startHall.getHallId()
                        + " нельзя попасть во все остальные залы "
                        + "(достигнуто " + visited.size() + " из " + totalHalls + ")");
            }
        }
        return errors;
    }

    /** Заполняет карту смежности по точкам перехода тура. */
    private void buildGraph(Tour tour) {
        adjacency.clear();
        for (Hall hall : tour.getHalls()) {
            adjacency.put(hall.getHallId(), new ArrayList<>());
        }
        for (TransitionPoint point : tour.getAllTransitionPoints()) {
            long from = point.getHall().getHallId();
            long to = point.getTargetHall().getHallId();
            // Добавляем ребро только если целевой зал существует в туре.
            // Рёбра в несуществующие залы уже отмечены в HotspotLinksValidator
            // как «битые ссылки»; здесь они исключаются из графа, чтобы
            // проверка связности работала только по реальным залам и
            // не сообщала «достигнуто N+1 из N» из-за заглушек.
            if (adjacency.containsKey(to)) {
                adjacency.get(from).add(to);
            }
        }
    }

    /**
     * Обход в глубину из заданной вершины. Заполняет множество visited.
     * Использует явный стек (Deque) вместо рекурсии: поведение идентично
     * рекурсивному DFS, но безопасно по памяти на больших графах.
     */
    private void dfs(long start) {
        Deque<Long> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            long current = stack.pop();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            for (long neighbor : adjacency.getOrDefault(current, List.of())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
}
