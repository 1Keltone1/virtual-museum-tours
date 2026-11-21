def check_graph_connectivity(halls, transition_points):
    if not halls:
        return True

    # 1. Построение графа
    graph = {}
    for hall in halls:
        graph[hall['hall_id']] = []

    for point in transition_points:
        if point['hall_id'] in graph:
            graph[point['hall_id']].append(point['target_hall_id'])

    # 2. Проверка на тупики
    if len(halls) > 1:
        for hall_id, neighbors in graph.items():
            if len(neighbors) == 0:
                return False

    # 3. Запускаем обход из каждого зала
    for start_hall in halls:
        start_id = start_hall['hall_id']
        visited = set()

        def dfs(current_hall_id):
            visited.add(current_hall_id)
            for neighbor in graph.get(current_hall_id, []):
                if neighbor not in visited:
                    dfs(neighbor)

        dfs(start_id)

        if len(visited) != len(halls):
            return False

    return True


# Блок для запуска из командной строки (демонстрация работы)
if __name__ == "__main__":
    print("========== Демо 1: корректный тур ==========")
    correct_halls = [
        {'hall_id': 101, 'name': 'Главный зал'},
        {'hall_id': 102, 'name': 'Зал краеведения'},
        {'hall_id': 103, 'name': 'Зал скульптур'},
    ]
    correct_points = [
        {'point_id': 11, 'hall_id': 101, 'target_hall_id': 102},
        {'point_id': 12, 'hall_id': 102, 'target_hall_id': 103},
        {'point_id': 13, 'hall_id': 103, 'target_hall_id': 101},
    ]
    print("Проверка связности графа тура...")
    is_connected = check_graph_connectivity(correct_halls, correct_points)
    print(f"Результат: {'Связный' if is_connected else 'Несвязный'}")

    print()
    print("========== Демо 2: тур с ошибками ==========")
    broken_halls = [
        {'hall_id': 301, 'name': 'Зал с неверным расширением'},
        {'hall_id': 302, 'name': 'Зал с неверными пропорциями'},
        {'hall_id': 303, 'name': 'Зал с петлей'},
        {'hall_id': 304, 'name': 'Зал с битой ссылкой'},
        {'hall_id': 305, 'name': 'Зал-тупик'},
    ]
    broken_points = [
        {'point_id': 11, 'hall_id': 301, 'target_hall_id': 302},
        {'point_id': 12, 'hall_id': 302, 'target_hall_id': 303},
        {'point_id': 13, 'hall_id': 303, 'target_hall_id': 303},   # петля
        {'point_id': 16, 'hall_id': 303, 'target_hall_id': 304},
        {'point_id': 14, 'hall_id': 304, 'target_hall_id': 999},   # битая ссылка
        {'point_id': 15, 'hall_id': 304, 'target_hall_id': 305},
    ]
    print("Проверка связности графа тура...")
    is_connected = check_graph_connectivity(broken_halls, broken_points)
    print(f"Результат: {'Связный' if is_connected else 'Несвязный'}")