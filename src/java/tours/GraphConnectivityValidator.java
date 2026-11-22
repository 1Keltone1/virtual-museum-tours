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
 Алгоритм №2 «Проверка связности графа тура».

 Соответствует функции check_graph_connectivity().
 Проверяет:
   1) отсутствие тупиков (зал без исходящих переходов, если он не единственный);
   2) связность графа: из любого зала можно дойти до любого другого.
 */
public class GraphConnectivityValidator implements TourValidator {

    @Override
    public List<String> validate(Tour tour) {
        List<String> errors = new ArrayList<>();
        if (tour.getHalls().isEmpty()) {
            return errors;
        }

        // Шаг 1: построение графа смежности (локально, не в поле класса).
        Map<Long, List<Long>> adjacency = buildGraph(tour);

        // Шаг 2: проверка на тупики.
        if (tour.getHalls().size() > 1) {
            for (Hall hall : tour.getHalls()) {
                if (!hall.hasOutgoingTransitions()) {
                    errors.add("Ошибка: Зал " + hall.getHallId()
                            + " является тупиком (нет исходящих переходов)");
                }
            }
        }

        // Шаг 3: проверка связности - DFS из каждого зала.
        int totalHalls = tour.getHalls().size();
        for (Hall startHall : tour.getHalls()) {
            Set<Long> visited = dfs(adjacency, startHall.getHallId());
            if (visited.size() != totalHalls) {
                errors.add("Ошибка: Из зала " + startHall.getHallId()
                        + " нельзя попасть во все остальные залы "
                        + "(достигнуто " + visited.size() + " из " + totalHalls + ")");
            }
        }
        return errors;
    }

    /**
     Заполняет карту смежности по точкам перехода тура.
     Возвращает новую Map, локальную для текущего вызова validate().
     */
    private Map<Long, List<Long>> buildGraph(Tour tour) {
        Map<Long, List<Long>> adjacency = new HashMap<>();
        for (Hall hall : tour.getHalls()) {
            adjacency.put(hall.getHallId(), new ArrayList<>());
        }
        for (TransitionPoint point : tour.getAllTransitionPoints()) {
            long from = point.getHall().getHallId();
            long to = point.getTargetHall().getHallId();.
            if (adjacency.containsKey(to)) {
                adjacency.get(from).add(to);
            }
        }
        return adjacency;
    }

    /**
     Обход в глубину из заданной вершины. Возвращает множество посещённых вершин,
     локальное для текущего вызова (новое множество на каждый обход).
     Использует явный стек (Deque) вместо рекурсии: поведение идентично
     рекурсивному DFS, но безопасно по памяти на больших графах.
     */
    private Set<Long> dfs(Map<Long, List<Long>> adjacency, long start) {
        Set<Long> visited = new HashSet<>();
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
        return visited;
    }
}