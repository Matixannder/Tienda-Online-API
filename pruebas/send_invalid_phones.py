import sys
import json
import urllib.request
import urllib.error

BASE_URL = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080"


def get_cliente(path):
    req = urllib.request.Request(f"{BASE_URL}{path}", method="GET")
    try:
        with urllib.request.urlopen(req) as resp:
            return resp.status, json.loads(resp.read().decode("utf-8"))
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode("utf-8")


invalid_format = [
    ("Letras",              "/clientes/telefonos/abcdefg"),
    ("Demasiado corto",     "/clientes/telefonos/123"),
    ("Demasiado largo",     "/clientes/telefonos/12345678901234567890"),
    ("Guiones",             "/clientes/telefonos/12-34-56-78"),
    ("Parentesis",          "/clientes/telefonos/+1(555)000-1234"),
    ("Vacio",               "/clientes/telefonos/"),
]

not_found = [
    ("Inexistente",         "/clientes/telefonos/+9998887777"),
    ("Sin prefijo",         "/clientes/telefonos/1234567890"),
]

print("=== Testing GET /clientes/telefonos/{telefono} ===")
print("\n-- Expecting 400 BAD_REQUEST (invalid format) --")
for name, path in invalid_format:
    status, body = get_cliente(path)
    expected = 400
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:20s} -> {status} (expected {expected})")

print("\n-- Expecting 404 NOT_FOUND (not in DB) --")
for name, path in not_found:
    status, body = get_cliente(path)
    expected = 404
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:20s} -> {status} (expected {expected})")
