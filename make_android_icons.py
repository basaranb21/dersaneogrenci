"""PWA ikonlarını alıp Android mipmap boyutlarına çevir.

Android klasörleri:
    mipmap-mdpi    → 48x48
    mipmap-hdpi    → 72x72
    mipmap-xhdpi   → 96x96
    mipmap-xxhdpi  → 144x144
    mipmap-xxxhdpi → 192x192
"""
import os
from PIL import Image

SRC = os.path.join(os.path.dirname(__file__), "..", "kaynak", "static", "pwa", "icon-512.png")
if not os.path.exists(SRC):
    # Alternatif yol (ZIP içi yerel)
    SRC = os.path.join(os.path.dirname(__file__), "icon-512.png")

HEDEF_BASE = os.path.join(os.path.dirname(__file__), "app", "src", "main", "res")

BOYUTLAR = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192,
}

if not os.path.exists(SRC):
    print(f"HATA: kaynak ikon yok: {SRC}")
    raise SystemExit(1)

img = Image.open(SRC).convert("RGBA")
for klasor, boyut in BOYUTLAR.items():
    d = os.path.join(HEDEF_BASE, klasor)
    os.makedirs(d, exist_ok=True)
    resized = img.resize((boyut, boyut), Image.LANCZOS)
    resized.save(os.path.join(d, "ic_launcher.png"), "PNG", optimize=True)
    resized.save(os.path.join(d, "ic_launcher_round.png"), "PNG", optimize=True)
    print(f"  {klasor}/ic_launcher.png ({boyut}x{boyut})")

print(f"\n✓ {len(BOYUTLAR) * 2} ikon oluşturuldu")
