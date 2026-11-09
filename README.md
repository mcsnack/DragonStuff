# 🐉 DragonStuff

**DragonStuff** ist ein Minecraft-Java-Projekt mit einem **Paper-Plugin** und dem dazugehörigen **DragonStuff Resource Pack 26.3**. Gemeinsam erweitern sie das Spiel um dracheninspirierte Ausrüstung und zusätzliche Spielmechaniken in einem dunklen, violetten Stil.

> **Projektstatus:** In Entwicklung. Das Resource Pack wird derzeit mit eigenständigen Designs überarbeitet. Angaben zu Funktionen beziehen sich auf den jeweiligen Entwicklungsstand und können sich bis zur nächsten Veröffentlichung ändern.

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

### Schmiedetisch

DragonStuff verwendet eigene Schmiederezepte mit einem **Dragon-Trim** als Vorlage. Ein Beispiel für Ausrüstung ist:

`Dragon-Trim + Netherite Chestplate + Diamond → Dragon Chestplate`

Die Dragon Wings sind ebenfalls Teil des Ausrüstungssystems. Welche Rezepte verfügbar sind, hängt vom jeweiligen Plugin-Build ab.

## 📦 Bestandteile des Projekts

**Paper-Plugin**

- Stellt die benutzerdefinierten Gegenstände, Rucksäcke und ihre Eigenschaften bereit.
- Verarbeitet Schmiedetisch-Rezepte, Backpack-Inventare und die zugehörigen Spielmechaniken.
- Speichert Backpack-Inhalte und Metadaten im Plugin-Datenordner.
- Benötigt einen kompatiblen Paper-Server.

**DragonStuff Resource Pack 26.3**

- Enthält Item-Texturen, Modelle und Equipment-Grafiken.
- Ergänzt die Darstellung der Gegenstände im Inventar und am Spieler.
- Fügt **allein** keine neuen Items oder Rezepte hinzu.

## 🛠️ Voraussetzungen

- **Minecraft Java Edition:** 26.3 (angestrebte Projektversion)
- **Server:** kompatibler Paper-Build für Minecraft 26.3
- **Java:** 25 für die aktuelle Entwicklungsumgebung
- **Build-System:** Gradle (Wrapper im Projekt)

> Prüfe vor dem Einsatz, ob Server-Build, Java-Version, Plugin-Datei und Resource Pack zueinander passen.

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

**Wichtig:** Für die vollständige Darstellung der DragonStuff-Gegenstände werden **Plugin und Resource Pack** benötigt.

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

Wichtige Plugin-Klassen sind unter anderem `DragonTrim`, `DragonItems`, `DragonSmithingRecipes`, `DragonSmithingListener` sowie die Klassen im Paket `me.dragontrim.backpack`. Benutzerdefinierte Gegenstände und platzierte Rucksäcke werden über Persistent Data Container (PDC) gekennzeichnet; die Modelle nutzen den Namespace `dragonstuff`.

## 📁 Projektaufbau

Die konkrete Verzeichnisstruktur kann sich mit der Entwicklung ändern. Die zentralen Bereiche sind:

```text
DragonStuff/
├── src/main/java/             # Java-Quellcode des Plugins
│   └── me/dragontrim/
│       └── backpack/          # DragonBackPack-System
├── src/main/resources/        # Plugin-Konfiguration und Sprache
├── build.gradle               # Gradle-Konfiguration
├── settings.gradle
├── gradlew.bat                # Gradle-Wrapper (Windows)
├── resourcepack/              # Dateien des Resource Packs
│   ├── README.md
│   ├── COPYRIGHT.txt
│   └── DragonStuff_Resource_Pack_26.3.zip
└── README.md                  # Diese Projektübersicht
```

## 🔧 Fehlerbehebung

- **Item zeigt eine Vanilla-Textur:** Prüfe, ob das richtige Resource Pack aktiviert ist und ob das Item aus einer passenden Plugin-Version stammt.
- **Rüstung oder Elytra wird falsch dargestellt:** Prüfe, ob Plugin und Resource Pack zusammenpassen.
- **Schmiederezept funktioniert nicht:** Prüfe Dragon-Trim, Basis-Item und Zusatzmaterial. Bei eigenen Items ist die interne PDC-Kennzeichnung entscheidend.
- **Rucksack lässt sich nicht platzieren:** Prüfe, ob die aktuelle DragonStuff-Version installiert ist und ob ein anderes Plugin das Platzieren von Shulker-Boxen verhindert.
- **Rucksack-Inhalt scheint nicht aktuell:** Schließe das Backpack-Inventar regulär, damit die Änderungen gespeichert werden.
- **Plugin startet nicht:** Kontrolliere Minecraft-/Paper-Version, Java-Version und die Fehlermeldungen in der Serverkonsole.

## © Copyright, Credits und Lizenzen

**© 2026 mcsnack – DragonStuff.** Alle Rechte an den **eigenständig geschaffenen** Bestandteilen verbleiben bei ihren jeweiligen Urhebern, soweit keine gesonderte Lizenz etwas anderes bestimmt.

Die Nutzungsbedingungen für eigenständige Texturen sind in der `COPYRIGHT.txt` des Resource Packs beschrieben. **Für den Plugin-Quellcode ist eine separate Lizenzregelung maßgeblich**; aus diesem Copyright-Hinweis allein ergibt sich keine Open-Source-Lizenz.

Minecraft ist eine Marke von Mojang Studios. DragonStuff ist ein unabhängiges Fanprojekt und steht nicht in Verbindung mit Mojang Studios oder Microsoft.

## 🔗 Projektlinks

- [GitHub-Repository](https://github.com/mcsnack/DragonStuff)
- [Releases](https://github.com/mcsnack/DragonStuff/releases)

---

*DragonStuff – Dragon-inspired gear for Minecraft Java Edition.*
