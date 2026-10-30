def check_graph_connectivity(halls, transition_points):
    if not halls:
        return True  # Пустой тур считаем связным

    # Шаг 1: Строим граф смежности (словарь списков)
    graph = {}
    for hall in halls:
        graph[hall['hall_id']] = []

    for point in transition_points:
        source = point['hall_id']
        target = point['target_hall_id']
        if source in graph:
            graph[source].append(target)

    # Шаг 2: Проверка на "тупики" (если залов больше одного)
    if len(halls) > 1:
        for hall_id, neighbors in graph.items():
            if len(neighbors) == 0:
                return False

    # Шаг 3: Рекурсивная функция обхода в глубину (DFS)
    visited = set()

    def dfs(current_hall_id):
        visited.add(current_hall_id)
        if current_hall_id in graph:
            for neighbor in graph[current_hall_id]:
                if neighbor not in visited:
                    dfs(neighbor)

    # Шаг 4: Запускаем обход с первого зала
    start_hall_id = halls[0]['hall_id']
    dfs(start_hall_id)

    # Шаг 5: Проверяем, что посетили все залы
    return len(visited) == len(halls)


# Блок для запуска из командной строки (демонстрация работы)
if __name__ == "__main__":
    # Тестовые данные: 3 зала
    test_halls = [
        {'hall_id': 1, 'name': 'Зал древностей'},
        {'hall_id': 2, 'name': 'Зал живописи'},
        {'hall_id': 3, 'name': 'Зал скульптур'}
    ]

    # Тест 1: Связный граф (все залы достижимы)
    test_points_connected = [
        {'point_id': 10, 'hall_id': 1, 'target_hall_id': 2},
        {'point_id': 11, 'hall_id': 2, 'target_hall_id': 3},
        {'point_id': 12, 'hall_id': 3, 'target_hall_id': 1}
    ]

    # Тест 2: Несвязный граф (зал 3 - тупик)
    test_points_disconnected = [
        {'point_id': 10, 'hall_id': 1, 'target_hall_id': 2},
        {'point_id': 11, 'hall_id': 2, 'target_hall_id': 1},
        {'point_id': 12, 'hall_id': 2, 'target_hall_id': 3}
    ]

    print("Запуск алгоритма проверки связности графа тура...")

    print("\nТест 1 (Связный граф):")
    is_connected_1 = check_graph_connectivity(test_halls, test_points_connected)
    print(f"Результат: {'Связный' if is_connected_1 else 'Несвязный'}")

    print("\nТест 2 (Несвязный граф):")
    is_connected_2 = check_graph_connectivity(test_halls, test_points_disconnected)
    print(f"Результат: {'Связный' if is_connected_2 else 'Несвязный'}")