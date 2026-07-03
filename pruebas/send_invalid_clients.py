import sys
import json
import urllib.request
import urllib.error

BASE_URL = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080"

SEED = {
    "nombre": "Juan",
    "apellido": "Perez",
    "correo": "juan@example.com",
    "telefono": "+1234567890",
    "direccion": "Calle 123"
}

def post_cliente(data):
    body = json.dumps(data).encode("utf-8")
    req = urllib.request.Request(
        f"{BASE_URL}/clientes",
        data=body,
        headers={"Content-Type": "application/json"},
        method="POST"
    )
    try:
        with urllib.request.urlopen(req) as resp:
            return resp.status, json.loads(resp.read().decode("utf-8"))
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode("utf-8")

print("=== Creating seed client ===")
status, body = post_cliente(SEED)
print(f"  Status: {status}, Body: {body}\n")

tests = [
    ("Same email only", {
        "nombre": "Maria", "apellido": "Gomez",
        "correo": SEED["correo"],
        "telefono": "+9999999999",
        "direccion": "Avenida 456"
    }),
    ("Same phone only", {
        "nombre": "Carlos", "apellido": "Lopez",
        "correo": "carlos@example.com",
        "telefono": SEED["telefono"],
        "direccion": "Avenida 456"
    }),
    ("Same direction only", {
        "nombre": "Ana", "apellido": "Martinez",
        "correo": "ana@example.com",
        "telefono": "+8888888888",
        "direccion": SEED["direccion"]
    }),
    ("Same email + phone", {
        "nombre": "Pedro", "apellido": "Ramirez",
        "correo": SEED["correo"],
        "telefono": SEED["telefono"],
        "direccion": "Avenida 456"
    }),
    ("Same email + direction", {
        "nombre": "Luis", "apellido": "Torres",
        "correo": SEED["correo"],
        "telefono": "+7777777777",
        "direccion": SEED["direccion"]
    }),
    ("Same phone + direction", {
        "nombre": "Sofia", "apellido": "Vega",
        "correo": "sofia@example.com",
        "telefono": SEED["telefono"],
        "direccion": SEED["direccion"]
    }),
    ("Same email + phone + direction", {
        "nombre": "Diego", "apellido": "Rojas",
        "correo": SEED["correo"],
        "telefono": SEED["telefono"],
        "direccion": SEED["direccion"]
    }),
]

print("=== Testing duplicate scenarios ===")
for name, data in tests:
    status, body = post_cliente(data)
    expected = 409
    result = "PASS" if status == expected else "FAIL"
    print(f"  [{result}] {name:30s} -> {status} (expected {expected})")
