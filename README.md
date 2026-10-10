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

Das Plugin verwaltet die benutzerdefinierten Gegenstände, ihre Kennzeichnungen und die dazugehörigen Spielmechaniken. Das Resource Pack stellt die Texturen und Modelle bereit, einschließlich der Darstellung von Rüstung und Flügeln am Spieler.

### Schmiedetisch

DragonStuff verwendet eigene Schmiederezepte mit einem **Dragon-Trim** als Vorlage. Ein Beispiel ist:

`Dragon-Trim + Netherite Chestplate + Diamond → Dragon Chestplate`

Die Dragon Wings sind ebenfalls als Teil des Ausrüstungssystems vorgesehen. Welche Rezepte in einer veröffentlichten Version verfügbar sind, hängt vom jeweiligen Plugin-Build ab.

## 📦 Bestandteile des Projekts

**Paper-Plugin**

- Stellt die benutzerdefinierten Gegenstände und ihre Eigenschaften bereit.
- Verarbeitet die Schmiedetisch-Rezepte und die zugehörige Item-Erkennung.
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

## 👨‍💻 Für Entwickler

Das Projekt wird mit Gradle gebaut. Unter Windows kannst du im Projektverzeichnis ausführen:

```powershell
.\gradlew.bat build
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