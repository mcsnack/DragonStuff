# 🐉 DragonStuff

**DragonStuff** ist ein Minecraft-Java-Projekt mit einem **Paper-Plugin** und dem dazugehörigen **DragonStuff Resource Pack 26.3**. Gemeinsam erweitern sie das Spiel um dracheninspirierte Ausrüstung in einem dunklen, violetten Stil.

> **Projektstatus:** In Entwicklung. Das Resource Pack wird derzeit mit vollständig neu gepixelten, eigenständigen Designs überarbeitet. Angaben zu Funktionen beziehen sich auf den aktuellen Entwicklungsstand; Details können sich bis zur nächsten Veröffentlichung ändern.

## ✨ Funktionen

### Ausrüstung

| Kategorie | Gegenstände |
| --- | --- |
| Waffen | Dragon Sword |
| Werkzeuge | Dragon Pickaxe, Dragon Axe, Dragon Shovel, Dragon Hoe |
| Rüstung | Dragon Helmet, Dragon Chestplate, Dragon Leggings, Dragon Boots |
| Flügel | Dragon Wings (Elytra) |
| Schmiedematerial | Dragon-Trim |
| Rucksäcke | DragonBackPack (Tier 1–4) |

Das Plugin verwaltet die benutzerdefinierten Gegenstände, ihre Kennzeichnungen und die dazugehörigen Spielmechaniken. Das Resource Pack stellt die Texturen und Modelle bereit, einschließlich der Darstellung von Rüstung und Flügeln am Spieler.

### DragonBackPack

DragonBackPacks sind mehrstufige Rucksäcke auf Basis von Shulker-Boxen. Jeder Rucksack besitzt eine eigene ID; seine Inhalte werden separat gespeichert, damit sie beim Öffnen, Platzieren und Abbauen erhalten bleiben.

| Stufe | Inventarplätze |
| --- | ---: |
| Tier 1 | 27 |
| Tier 2 | 36 |
| Tier 3 | 45 |
| Tier 4 | 54 |

**Herstellung und Upgrades**

- **Neuen Rucksack herstellen:** Dragon-Trim + leere Shulker-Box + Netherite-Barren am Schmiedetisch.
- **Upgrade:** Einen vorhandenen DragonBackPack zusammen mit Dragon-Trim und Netherite-Barren am Schmiedetisch einsetzen.
- Befüllte normale Shulker-Boxen werden als Ausgangsmaterial nicht angenommen, damit ihr Inhalt nicht verloren geht.
- Ein Rucksack kann normal als Shulker-Block platziert werden. Beim Abbauen erhältst du den DragonBackPack mit derselben ID zurück.
- Rechtsklick auf einen platzierten Rucksack öffnet sein Inventar. In der Hand öffnet Rechtsklick in die Luft den Rucksack; alternativ funktioniert **Shift + F**.
- Der Tooltip blendet die Vanilla-Zeile **„Empty“** aus. **„Full“** wird angezeigt, sobald mindestens ein Inventarplatz belegt ist.
- DragonBackPacks können mit den normalen Minecraft-Färberezepten umgefärbt werden. UUID, Tier-Stufe und gespeicherter Inhalt bleiben dabei erhalten.
- Die neue Optik wird ausschließlich DragonBackPack-Items mit gültiger Plugin-Kennung zugewiesen. Normale Shulker-Boxen behalten ihre Vanilla-Darstellung.
- Das Item-Modell folgt der Shulker-Farbe: Orange = Fox, Black = Panda, Blue = Axolotl, Green = Frog, Lime = Alex, Light Blue = Steve, Brown = Teddy Bear, Light Gray = Rainbow Sheep, Pink = Pig, Purple = Enderman, White = Penguin, Yellow = Bee. Red, Magenta, Cyan und Gray verwenden die neutralen Varianten.

### Schmiedetisch

DragonStuff verwendet eigene Schmiederezepte mit einem **Dragon-Trim** als Vorlage. Ein Beispiel ist:

`Dragon-Trim + Netherite Chestplate + Diamond → Dragon Chestplate`

Die Dragon Wings sind ebenfalls als Teil des Ausrüstungssystems vorgesehen. Welche Rezepte in einer veröffentlichten Version verfügbar sind, hängt vom jeweiligen Plugin-Build ab.

## 📦 Bestandteile des Projekts

**Paper-Plugin**

- Stellt die benutzerdefinierten Gegenstände und ihre Eigenschaften bereit.
- Verarbeitet Schmiedetisch-Rezepte, Backpack-Inventare und die zugehörigen Spielmechaniken.
- Speichert Backpack-Inhalte und Metadaten im Plugin-Datenordner.
- Benötigt einen kompatiblen Paper-Server.

**DragonStuff Resource Pack 26.3**

- Enthält die Item-Texturen, Modelle und Equipment-Grafiken.
- Ergänzt die Darstellung der Gegenstände im Inventar und am Spieler.
- Fügt **allein** keine neuen Items oder Rezepte hinzu.

## 🛠️ Voraussetzungen

- **Minecraft Java Edition:** 26.3 (angestrebte Projektversion)
- **Server:** kompatibler Paper-Build für Minecraft 26.3
- **Java:** 25 für die aktuelle Entwicklungsumgebung
- **Build-System:** Gradle (Wrapper im Projekt)

> Prüfe vor dem Einsatz, ob dein Server-Build, die Java-Version und die veröffentlichte Plugin-Datei zueinander passen.

## 🚀 Installation

### Plugin auf dem Server

1. Lade die passende DragonStuff-Plugin-JAR aus den [Releases](https://github.com/mcsnack/DragonStuff/releases) herunter, sofern eine Veröffentlichung bereitsteht.
2. Lege die JAR in den Ordner `plugins/` deines Paper-Servers.
3. Starte den Server neu.
4. Prüfe die Serverkonsole auf Fehlermeldungen.

### Resource Pack im Minecraft-Client

1. Lade **DragonStuff Resource Pack 26.3** als ZIP herunter.
2. Öffne in Minecraft **Optionen → Ressourcenpakete → Paketordner öffnen**.
3. Kopiere die ZIP-Datei in den Ordner `resourcepacks`.
4. Aktiviere das Paket und verschiebe es bei Bedarf über ältere, möglicherweise kollidierende Packs.
5. Lade die Ressourcen bei Bedarf mit **F3 + T** neu.

**Wichtig:** Für die vollständige Funktion werden **Plugin und Resource Pack** benötigt.

#### DragonBackPack-Farbmodelle

Für die Enderman-/Farbrucksack-Optik wird zusätzlich das **DragonBackPack Colors Add-on für Minecraft 26.3** benötigt. Es enthält die 16 Item-Definitionen, Modelle und Texturen. Das Add-on kann testweise als separates Ressourcenpaket oberhalb von DragonStuff aktiviert werden. Für eine gemeinsame Veröffentlichung werden die Ordner `assets/dragonstuff/items`, `assets/dragonstuff/models/item` und `assets/dragonstuff/textures/item` in das Haupt-Resource-Pack übernommen.

Beim Färben eines DragonBackPacks wird das Vanilla-Färbeergebnis durch ein DragonBackPack mit derselben UUID, Tier-Stufe und denselben gespeicherten Inhalten ersetzt. Normale Shulker-Boxen und ihre Vanilla-Modelle bleiben unverändert.

**Hinweis zur Welt-Darstellung:** In dieser Integration wird das neue 3D-Modell für DragonBackPack-Items in Inventar, Hand und als Item-Drop verwendet. Ein platzierter Rucksack bleibt vorerst ein normal dargestellter Shulker-Block.

## 🧰 Befehle und Berechtigungen

| Befehl | Beschreibung |
| --- | --- |
| `/dragon <item>` | Gibt das angegebene DragonStuff-Item aus (z. B. `/dragon chestplate`, `/dragon sword`, `/dragon wings`, `/dragon trim`). |
| `/backpack` | Öffnet die DragonBackPack-Adminverwaltung mit allen registrierten Rucksäcken. |
| `/backpack <Spielername>` | Filtert die Adminverwaltung nach einem Spieler. |

**Berechtigung für die Backpack-Administration:** `dragonstuff.backpack.admin` (standardmäßig OP).

Für den normalen Gebrauch und das Öffnen eigener Rucksäcke ist der Admin-Befehl nicht erforderlich.

## 👨‍💻 Für Entwickler

Das Projekt wird mit Gradle gebaut. Unter Windows kannst du im Projektverzeichnis ausführen:

```powershell
.\gradlew.bat clean build
```

Die erzeugten JAR-Dateien findest du üblicherweise unter `build/libs/`.

Der Plugin-Code verwendet unter anderem die Klassen `DragonTrim`, `DragonItems`, `DragonSmithingRecipes` und `DragonSmithingListener`. Die benutzerdefinierten Items werden über Persistent Data Container (PDC) gekennzeichnet; ihre Modelle nutzen den Namespace `dragonstuff`.

## 📁 Projektaufbau

Die konkrete Verzeichnisstruktur kann sich mit der Entwicklung ändern. Eine mögliche Aufteilung ist:

```text
DragonStuff/
├── src/                      # Java-Quellcode und Plugin-Ressourcen
├── build.gradle               # Gradle-Konfiguration
├── settings.gradle
├── gradlew.bat                 # Gradle-Wrapper (Windows)
├── resourcepack/              # Dateien des Resource Packs
│   ├── README.md
│   ├── COPYRIGHT.txt
│   └── DragonStuff_Resource_Pack_26.3.zip
└── README.md                  # Diese Projektübersicht
```

## 🔧 Fehlerbehebung

- **Item zeigt eine Vanilla-Textur:** Ist das richtige Resource Pack aktiviert? Stammt das Item aus der aktuellen Plugin-Version?
- **Rüstung oder Elytra wird falsch dargestellt:** Prüfe die Equipment-Modelle im Pack und die Modellzuweisungen des Plugins.
- **Schmiederezept funktioniert nicht:** Prüfe die verwendete Dragon-Trim-Vorlage, das Basis-Item und das Zusatzmaterial. Bei eigenen Items kann die interne PDC-Kennzeichnung entscheidend sein.
- **Rucksack lässt sich nicht platzieren:** Prüfe, ob die aktuelle DragonStuff-Version installiert ist und ob ein anderes Plugin das Platzieren von Shulker-Boxen verhindert.
- **Rucksack-Inhalt scheint nicht aktuell:** Schließe das Backpack-Inventar regulär, damit die Änderungen gespeichert werden.
- **Plugin startet nicht:** Kontrolliere die Minecraft-/Paper-Version, die Java-Version und die Fehlermeldungen in der Serverkonsole.

## © Copyright, Credits und Lizenzen

**© 2026 mcsnack – DragonStuff.** Alle Rechte an den **eigenständig geschaffenen** Bestandteilen verbleiben bei ihren jeweiligen Urhebern, soweit keine gesonderte Lizenz etwas anderes bestimmt.

Die Nutzungsbedingungen für eigenständige Texturen sind in der `COPYRIGHT.txt` des Resource Packs beschrieben. **Für den Plugin-Quellcode ist eine separate Lizenzregelung maßgeblich**; aus diesem Copyright-Hinweis allein ergibt sich keine Open-Source-Lizenz.

Minecraft ist eine Marke von Mojang Studios. DragonStuff ist ein unabhängiges Fanprojekt und steht nicht in Verbindung mit Mojang Studios oder Microsoft.

## 🔗 Projektlinks

- [GitHub-Repository](https://github.com/mcsnack/DragonStuff)
- [Releases](https://github.com/mcsnack/DragonStuff/releases)

---

*DragonStuff – Dragon-inspired gear for Minecraft Java Edition.*

### 🐉 DragonStuff – Befehle


/dragon <sword | pickaxe | axe | shovel | hoe>
/dragon <helmet | chestplate | leggings | boots>
/dragon <wings | trim>


**Beispiel:** `/dragon chestplate`

*Hinweis: Für die Verwendung können OP-Rechte oder entsprechende Berechtigungen erforderlich sein.*