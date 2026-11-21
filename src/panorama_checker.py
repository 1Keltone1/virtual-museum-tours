def validate_panorama_format(panoramas):
    errors = []
    allowed_extensions = {'.jpg', '.jpeg', '.png'}

    for p in panoramas:
        # 1. Проверка расширения файла
        if p['extension'].lower() not in allowed_extensions:
            errors.append(f"Ошибка: Панорама {p['panorama_id']} имеет недопустимое расширение ({p['extension']})")

        # 2. Проверка соотношения сторон
        if abs((p['width'] / p['height']) - 2.0) > 0.01:
            errors.append(f"Ошибка: Панорама {p['panorama_id']} имеет неверное соотношение сторон (должно быть 2:1)")

    return errors


if __name__ == "__main__":
    print("========== Демо 1: корректный тур ==========")
    correct_panoramas = [
        {'panorama_id': 101, 'width': 4096, 'height': 2048, 'extension': '.jpg'},
        {'panorama_id': 102, 'width': 4096, 'height': 2048, 'extension': '.jpg'},
        {'panorama_id': 103, 'width': 4096, 'height': 2048, 'extension': '.png'},
    ]
    print("Проверка формата панорам...")
    pan_errors = validate_panorama_format(correct_panoramas)
    if pan_errors:
        print(f"Найдено ошибок: {len(pan_errors)}")
        for err in pan_errors:
            print(f"  - {err}")
    else:
        print("Все панорамы корректны.")

    print()
    print("========== Демо 2: тур с ошибками ==========")
    
    broken_panoramas = [
        {'panorama_id': 301, 'width': 4096, 'height': 2048, 'extension': '.gif'},
        {'panorama_id': 302, 'width': 1000, 'height': 1000, 'extension': '.jpg'},
        {'panorama_id': 303, 'width': 4096, 'height': 2048, 'extension': '.jpg'},
        {'panorama_id': 304, 'width': 4096, 'height': 2048, 'extension': '.jpg'},
        {'panorama_id': 305, 'width': 4096, 'height': 2048, 'extension': '.jpg'},
    ]
    print("Проверка формата панорам...")
    pan_errors = validate_panorama_format(broken_panoramas)
    if pan_errors:
        print(f"Найдено ошибок: {len(pan_errors)}")
        for err in pan_errors:
            print(f"  - {err}")
    else:
        print("Все панорамы корректны.")