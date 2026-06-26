import csv
import json
import sys
import urllib.request
import urllib.error

BASE_URL = "http://localhost:8080"

def post(ruta, datos):
    url = f"{BASE_URL}{ruta}"
    body = json.dumps(datos).encode("utf-8")
    req = urllib.request.Request(url, data=body, headers={"Content-Type": "application/json"})
    try:
        with urllib.request.urlopen(req) as resp:
            print(f"  [{resp.status}] {json.dumps(json.loads(resp.read()), indent=2)}")
    except urllib.error.HTTPError as e:
        print(f"  [{e.code}] ERROR: {e.read().decode()}")
    except urllib.error.URLError as e:
        print(e)
        print("Ocurrio un error tratando de conectarse a la API, ¿Esta corriendo la aplicación?")
        print("Saliendo")
        sys.exit(1)
        



def probar_api(ruta, archivo):
    print(f"\n=== Importando {archivo} -> POST {ruta} ===")
    with open(archivo, newline="", encoding="utf-8") as archivo_csv:
        reader = csv.DictReader(archivo_csv)
        for fila in reader:
            print(f"  -> {fila.get('nombre', fila.get('correo', 'sin correo'))}")
            post(ruta, fila)



if __name__ == "__main__":
    script_dir = __file__.rsplit("/", 1)[0]
    probar_api("/clientes", f"{script_dir}/clientes.csv")
    probar_api("/productos", f"{script_dir}/productos.csv")
    print("\n=== Importación completada ===")
