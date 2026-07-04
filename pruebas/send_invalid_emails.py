import sys
import json
import base64
import urllib.request
import urllib.error

BASE_URL = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080"

def get_email(path):
    req = urllib.request.Request(f"{BASE_URL}{path}", method="GET")
    try:
        with urllib.request.urlopen(req) as resp:
            return resp.status, json.loads(resp.read().decode("utf-8"))
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode("utf-8")


invalid_format_paths = [
    ("Sin arroba",      "/clientes/correos/juanperez.com"),
    ("Sin punto",       "/clientes/correos/juan@perezcom"),
    ("Arroba doble",    "/clientes/correos/juan@@perez.com"),
    ("Suma",        "/clientes/correos/juan+perez@com"),
    ("Vacio",           "/clientes/correos/"),
    ("Solo texto",      "/clientes/correos/abcdef"),
]

not_found_paths = [
    ("Inexistente",     "/clientes/correos/noexiste@mail.com"),
    ("Sin TLD",         "/clientes/correos/usuario@dominio"),
    ("TLD raro",        "/clientes/correos/test@dominio.xyz"),
]

print("=== Testing GET /clientes/correos/{correo} ===")
print("\n-- Expecting 400 BAD_REQUEST (invalid format) --")
for name, path in invalid_format_paths:
    status, body = get_email(path)
    expected = 400
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:20s} -> {status} (expected {expected})")
    print(body)

print("\n-- Expecting 404 NOT_FOUND (not in DB) --")
for name, path in not_found_paths:
    status, body = get_email(path)
    expected = 404
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:20s} -> {status} (expected {expected})")
    print(body)
