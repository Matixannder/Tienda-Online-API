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


tests = [
    ("Cero",           "/clientes/0"),
    ("Negativo",       "/clientes/-1"),
    ("Inexistente",    "/clientes/99999"),
    ("Vacio-path",     "/clientes/"),
]

print("=== Testing GET /clientes/{id} for error responses ===")
for name, path in tests:
    status, body = get_cliente(path)
    expected = 404
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:15s} -> {status} (expected {expected}) | {body}")
