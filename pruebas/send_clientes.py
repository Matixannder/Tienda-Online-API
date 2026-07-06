import csv
import json
import base64
import sys
import urllib.request
import urllib.error

BASE_URL = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080"

# TODO: fill in if Spring Security requires auth
AUTH_USER = "admin"
AUTH_PASS = "password123"


def post_cliente(data):
    url = f"{BASE_URL}/clientes"
    body = json.dumps(data).encode("utf-8")
    req = urllib.request.Request(url, data=body, headers={"Content-Type": "application/json"}, method="POST")
    if AUTH_PASS:
        token = base64.b64encode(f"{AUTH_USER}:{AUTH_PASS}".encode()).decode()
        req.add_header("Authorization", f"Basic {token}")
    try:
        with urllib.request.urlopen(req) as resp:
            return resp.status, json.loads(resp.read().decode("utf-8"))
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode("utf-8")


script_dir = __file__.rsplit("/", 1)[0]
csv_path = f"{script_dir}/clientes.csv"

print(f"=== Sending POST /clientes from {csv_path} ===")
with open(csv_path, newline="", encoding="utf-8") as f:
    reader = csv.DictReader(f)
    for row in reader:
        print(f"  -> {row['nombre']} {row['apellido']} ({row['correo']})", end=" ")
        status, body = post_cliente(row)
        print(f"[{status}]")
